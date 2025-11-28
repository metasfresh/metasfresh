/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.rest_api.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.common.rest_api.request.JsonConfidentialType;
import de.metas.common.rest_api.request.JsonRequestPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JsonRRequestTest
{
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp()
    {
        objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module());
        // Keep dates as strings per @JsonFormat, avoid timestamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void deserialization_then_serialization_yields_same_string() throws IOException
    {
        // Build a JSON string with ALL fields in the same order as in JsonRRequest,
        // using codes for enums and explicit nulls for nullable fields to ensure exact string match.
        final String inputJson = "{" +
                "\"requestId\":null," +
                "\"orgCode\":\"Org-A\"," +
                "\"requestType\":\"Support\"," +
                "\"bpartnerIdentifier\":null," +
                "\"userIdentifier\":null," +
                // enums as AD codes
                "\"priority\":\"3\"," +
                "\"dueType\":null," +
                "\"summary\":\"Help needed\"," +
                "\"confidentialityLevel\":\"I\"," +
                "\"isEscalated\":null," +
                "\"isSelfService\":null," +
                "\"orderIdentifier\":null," +
                "\"vendorIdentifier\":null," +
                // date format yyyy-MM-dd
                "\"dateDelivered\":\"2025-11-28\"," +
                "\"productIdentifier\":null," +
                "\"inOutId\":null," +
                "\"qualityNote\":null," +
                "\"nextAction\":null," +
                "\"salesRepIdentifier\":null," +
                "\"result\":null," +
                "\"status\":null" +
                "}";

        final JsonRRequest parsed = objectMapper.readValue(inputJson, JsonRRequest.class);

        final String serialized = objectMapper.writeValueAsString(parsed);

        assertEquals(inputJson, serialized);
    }

    @Test
    void roundtrip_serialization_deserialization_yields_equal_object() throws IOException
    {
        final JsonRRequest original = JsonRRequest.builder()
                .orgCode("MyOrg")
                .requestType("Incident")
                .bpartnerIdentifier("111111")
                .userIdentifier("222222")
                .priority(JsonRequestPriority.Urgent)
                .dueType("D")
                .summary("Machine down in line 3")
                .confidentialityLevel(JsonConfidentialType.PartnerConfidential)
                .isEscalated(true)
                .isSelfService(false)
                .orderIdentifier("SO-9001")
                .vendorIdentifier("333333")
                .dateDelivered(LocalDate.of(2025, 11, 27))
                .productIdentifier("P-100")
                .qualityNote("Please prioritize")
                .nextAction("Dispatch technician")
                .salesRepIdentifier("SR-5")
                .result("Replaced faulty sensor")
                .status("Closed")
                .build();

        final String json = objectMapper.writeValueAsString(original);

        final JsonRRequest parsed = objectMapper.readValue(json, JsonRRequest.class);

        assertEquals(original, parsed);
    }

    @Test
    void serialization_outputsCodesAndDatePattern() throws JsonProcessingException
    {
        final JsonRRequest request = JsonRRequest.builder()
                .orgCode("org-1")
                .requestType("Support")
                .summary("Test summary")
                .dateDelivered(LocalDate.of(2025, 1, 2))
                .priority(JsonRequestPriority.High) // should serialize to code "3"
                .confidentialityLevel(JsonConfidentialType.Internal) // should serialize to code "I"
                .build();

        final String json = objectMapper.writeValueAsString(request);

        final JsonNode root = readTree(json);

        assertEquals("org-1", root.path("orgCode").asText());
        assertEquals("Support", root.path("requestType").asText());
        assertEquals("Test summary", root.path("summary").asText());

        // verify date format yyyy-MM-dd
        assertEquals("2025-01-02", root.path("dateDelivered").asText());

        // enums should serialize to AD codes
        assertEquals("3", root.path("priority").asText());
        assertEquals("I", root.path("confidentialityLevel").asText());
    }

    @Test
    void deserialization_acceptsHumanReadableEnumNames() throws IOException
    {
        final String json = "{" +
                "\"orgCode\":\"org-2\"," +
                "\"requestType\":\"Support\"," +
                "\"summary\":\"Another summary\"," +
                "\"dateDelivered\":\"2025-06-15\"," +
                // human-readable names for enums
                "\"priority\":\"Medium\"," +
                "\"confidentialityLevel\":\"PrivateInformation\"" +
                "}";

        final JsonRRequest request = objectMapper.readValue(json, JsonRRequest.class);

        assertEquals("org-2", request.getOrgCode());
        assertEquals("Support", request.getRequestType());
        assertEquals("Another summary", request.getSummary());
        assertEquals(LocalDate.of(2025, 6, 15), request.getDateDelivered());

        assertNotNull(request.getPriority());
        assertEquals("5", request.getPriority().getCode()); // Medium -> "5"

        assertNotNull(request.getConfidentialityLevel());
        assertEquals("P", request.getConfidentialityLevel().getCode()); // PrivateInformation -> "P"
    }

    @Test
    void deserialization_acceptsCodesForEnums() throws IOException
    {
        final String json = "{" +
                "\"orgCode\":\"org-3\"," +
                "\"requestType\":\"Incident\"," +
                "\"summary\":\"Summary text\"," +
                "\"dateDelivered\":\"2025-12-31\"," +
                // raw AD codes
                "\"priority\":\"1\"," +
                "\"confidentialityLevel\":\"C\"" +
                "}";

        final JsonRRequest request = objectMapper.readValue(json, JsonRRequest.class);

        assertEquals("org-3", request.getOrgCode());
        assertEquals("Incident", request.getRequestType());
        assertEquals("Summary text", request.getSummary());
        assertEquals(LocalDate.of(2025, 12, 31), request.getDateDelivered());

        assertNotNull(request.getPriority());
        assertEquals("1", request.getPriority().getCode()); // Urgent

        assertNotNull(request.getConfidentialityLevel());
        assertEquals("C", request.getConfidentialityLevel().getCode()); // PartnerConfidential
    }

    private JsonNode readTree(final String json) throws JsonProcessingException
    {
        return objectMapper.readTree(json);
    }
}
