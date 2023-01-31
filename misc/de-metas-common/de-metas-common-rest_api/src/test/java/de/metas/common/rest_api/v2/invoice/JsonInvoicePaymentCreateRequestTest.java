package de.metas.common.rest_api.v2.invoice;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class JsonInvoicePaymentCreateRequestTest 
{
    private ObjectMapper jsonObjectMapper;

    @BeforeEach
    public void beforeEach() 
    {
        jsonObjectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .enable(MapperFeature.USE_ANNOTATIONS);
    }


    @Test
    public void jsonInvoicePaymentCreateRequest() throws Exception 
    {
        final JsonInvoicePaymentCreateRequest jsonInvoicePaymentCreateRequest = JsonInvoicePaymentCreateRequest.builder()
                .bpartnerIdentifier("bpartnerIdentifier")
                .externalPaymentId("externalPaymentId")
                .targetIBAN("targetIBAN")
                .currencyCode("currencyCode")
                .orgCode("orgCode")
                .transactionDate(LocalDate.now())
                .lines(ImmutableList.of(JsonPaymentAllocationLine.builder()
                        .amount(BigDecimal.TEN)
                        .discountAmt(BigDecimal.ONE)
                        .invoiceIdentifier("invoiceIdentifier")
                        .docBaseType("docBaseType")
                        .docSubType("docSubType")
                        .writeOffAmt(BigDecimal.ZERO)
                        .build()))
                .build();

        testSerializeDeserialize(jsonInvoicePaymentCreateRequest, JsonInvoicePaymentCreateRequest.class);
    }

    private <T> void testSerializeDeserialize(@NonNull final T json, final Class<T> clazz) throws IOException 
    {
        final String jsonString = jsonObjectMapper.writeValueAsString(json);
        final T jsonDeserialized = jsonObjectMapper.readValue(jsonString, clazz);
        assertThat(jsonDeserialized).isEqualTo(json);
    }
}