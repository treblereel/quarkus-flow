package io.quarkiverse.flow.casehub.engine.internal.history;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.flow.casehub.api.context.StateContext;
import io.quarkiverse.flow.casehub.engine.internal.engine.CaseMetaInfo;
import io.quarkiverse.flow.casehub.engine.internal.event.CaseEventType;
import io.smallrye.mutiny.Uni;

@CaseEventTracker(eventType = CaseEventType.CASE_EXECUTION_STARTED)
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class CaseCreatedInterceptor {

    @Inject
    HistoryService historyService;

    @AroundInvoke
    Object execute(InvocationContext ctx) throws Exception {
        CaseEventTracker caseCreated = ctx.getMethod().getAnnotation(CaseEventTracker.class);
        if (caseCreated == null) {
            throw new RuntimeException("CaseCreatedInterceptor invoked on a method without @CaseEventTracker annotation");
        }

        CaseMetaInfo caseMetaInfo = (CaseMetaInfo) ctx.getParameters()[0];
        StateContext snapshot = caseMetaInfo.getContext().snapshot();
        Object proceedResult = ctx.proceed();
        JsonNode diff = snapshot.diff(caseMetaInfo.getContext());

        if (!(proceedResult instanceof Uni<?> uni)) {
            throw new IllegalStateException("@CaseEventTracker method must return Uni<T> to be used with reactive interceptor");
        }

        return uni.call(() -> historyService.persistCaseEvent(caseCreated.eventType(), caseMetaInfo, diff));
    }
}
