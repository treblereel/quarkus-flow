package io.quarkiverse.flow.casehub.engine.internal.history;

import static io.quarkiverse.flow.casehub.engine.internal.history.CaseCreatedInterceptor.*;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import com.fasterxml.jackson.databind.JsonNode;

import io.quarkiverse.flow.casehub.api.context.StateContext;
import io.quarkiverse.flow.casehub.engine.internal.engine.CaseMetaInfo;
import io.quarkiverse.flow.casehub.engine.internal.event.CaseStatus;
import io.smallrye.mutiny.Uni;

@CaseEventTracker(status = CaseStatus.NONE)
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

        long start = System.currentTimeMillis();

        Object proceedResult = ctx.proceed();

        JsonNode diff = snapshot.diff(caseMetaInfo.getContext());

        System.out.println("StateContext diff after method " +
                ctx.getMethod().getDeclaringClass().getSimpleName() +
                "." + ctx.getMethod().getName() + ":\n" + diff.toPrettyString());

        if (!(proceedResult instanceof Uni<?> uni)) {
            throw new IllegalStateException("@CaseEventTracker method must return Uni<T> to be used with reactive interceptor");
        }

        return uni
                .call(() -> historyService.persistCaseEvent(caseCreated.status(), caseMetaInfo, diff))
                .eventually(() -> {
                    long time = System.currentTimeMillis() - start;
                    System.out.println(
                            ctx.getMethod().getDeclaringClass().getSimpleName() +
                                    "." + ctx.getMethod().getName() +
                                    " executed in " + time + " ms");
                });
    }

}
