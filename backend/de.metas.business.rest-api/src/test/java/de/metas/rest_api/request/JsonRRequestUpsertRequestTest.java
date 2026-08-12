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
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.request.JsonConfidentialType;
import de.metas.common.rest_api.request.JsonRequestPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JsonRRequestUpsertRequestTest
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
        // Build a JSON string with ALL fields in the same order as in JsonRRequestUpsertRequest,
        // using codes for enums and explicit nulls for nullable fields to ensure exact string match.
        final String inputJson = "{" +
                "\"orgCode\":\"Org-A\"," +
                "\"requestType\":\"Support\"," +
                "\"bpartnerIdentifier\":\"ext-Other-123\"," +
                "\"userIdentifier\":\"ext-Other-1234\"," +
                // enums as AD codes
                "\"priority\":\"3\"," +
                "\"summary\":\"Help needed\"," +
                "\"confidentialityLevel\":\"I\"," +
                "\"vendorIdentifier\":\"ext-Other-123456\"," +
                // date format yyyy-MM-dd
                "\"dateDelivered\":\"2025-11-28\"," +
                "\"dateTrx\":\"2025-11-20\"," +
                "\"reminderDate\":\"2025-12-05\"," +
                "\"projectValue\":\"PRJ-42\"," +
                "\"productIdentifier\":\"ext-Other-1234567\"," +
                "\"orderId\":12345," +
                "\"inOutId\":123456," +
                "\"invoiceId\":654321," +
                "\"paymentId\":777888," +
                "\"qualityNote\":\"NoQualityProblem\"," +
                "\"salesRepIdentifier\":\"ext-Other-12345678\"," +
                "\"statusName\":\"MyStatus\"" +
                "}";

        final JsonRRequestUpsertRequest parsed = objectMapper.readValue(inputJson, JsonRRequestUpsertRequest.class);

        final String serialized = objectMapper.writeValueAsString(parsed);

        assertEquals(inputJson, serialized);
    }

    @Test
    void roundtrip_serialization_deserialization_yields_equal_object() throws IOException
    {
        final JsonRRequestUpsertRequest original = JsonRRequestUpsertRequest.builder()
                .orgCode("MyOrg")
                .requestType("Incident")
                .bpartnerIdentifier("111111")
                .userIdentifier("222222")
                .priority(JsonRequestPriority.Urgent)
                .summary("Machine down in line 3")
                .confidentialityLevel(JsonConfidentialType.PartnerConfidential)
                .orderId(JsonMetasfreshId.of(9001))
                .vendorIdentifier("333333")
                .dateDelivered(LocalDate.of(2025, 11, 27))
                .productIdentifier("P-100")
                .qualityNote("Please prioritize")
                .salesRepIdentifier("SR-5")
                .build();

        final String json = objectMapper.writeValueAsString(original);

        final JsonRRequestUpsertRequest parsed = objectMapper.readValue(json, JsonRRequestUpsertRequest.class);

        assertEquals(original, parsed);
    }

    @Test
    void serialization_outputsCodesAndDatePattern() throws JsonProcessingException
    {
        final JsonRRequestUpsertRequest request = JsonRRequestUpsertRequest.builder()
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

        final JsonRRequestUpsertRequest request = objectMapper.readValue(json, JsonRRequestUpsertRequest.class);

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

        final JsonRRequestUpsertRequest request = objectMapper.readValue(json, JsonRRequestUpsertRequest.class);

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
