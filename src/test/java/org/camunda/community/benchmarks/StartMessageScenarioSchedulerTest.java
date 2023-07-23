package org.camunda.community.benchmarks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.camunda.community.benchmarks.model.Message;
import org.camunda.community.benchmarks.model.MessagesScenario;
import org.camunda.community.benchmarks.utils.JsonUtils;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public class StartMessageScenarioSchedulerTest {

    @Test
    void testReplacePlaceHolders() throws StreamReadException, DatabindException, IOException {
        MessagesScenario scenario = JsonUtils.fromJsonInputStream(
            this.getClass().getResourceAsStream("/bpmn/sample-msg-scenario.json"),
            MessagesScenario.class);
        StartMessageScenarioScheduler scheduler = new StartMessageScenarioScheduler(null, null, null);
        String count = "42";
        scheduler.replacePlaceHolders(scenario, count);
        Message msg = scenario.getMessageSequence().get(0);
        assertEquals("Order-42", msg.getCorrelationKey());
        assertEquals("Product-1-42", ((List) msg.getVariables().get("items")).get(0));
        assertEquals("Package-P42", ((Map) ((List) msg.getVariables().get("packages")).get(0)).get("id"));
    }
}
