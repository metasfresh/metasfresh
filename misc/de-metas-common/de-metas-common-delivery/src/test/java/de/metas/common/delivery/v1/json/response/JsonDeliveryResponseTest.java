package de.metas.common.delivery.v1.json.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonDeliveryResponseTest {

    private static ObjectMapper mapper() {
        final ObjectMapper m = new ObjectMapper();
        m.findAndRegisterModules();
        return m;
    }

    @Test
    void item_isError_and_withoutPDFContents() {
        final JsonDeliveryResponseItem okItem = JsonDeliveryResponseItem.builder()
                .lineId("10")
                .awb("AWB")
                .trackingUrl("http://trk")
                .labelPdfBase64(new byte[]{1,2})
                .build();
        final JsonDeliveryResponseItem errItem = JsonDeliveryResponseItem.builder()
                .lineId("20")
                .errorMessage("oops")
                .build();

        assertThat(okItem.isError()).isFalse();
        assertThat(errItem.isError()).isTrue();

        final JsonDeliveryResponseItem noPdf = okItem.withoutPDFContents();
        assertThat(noPdf.getLabelPdfBase64()).isNull();
        assertThat(noPdf.getLineId()).isEqualTo("10");
    }

    @Test
    void response_isError_and_withoutPDFContents_and_json_roundtrip() throws Exception {
        final JsonDeliveryResponse response = JsonDeliveryResponse.builder()
                .requestId("REQ-1")
                .item(JsonDeliveryResponseItem.builder().lineId("1").build())
                .item(JsonDeliveryResponseItem.builder().lineId("2").errorMessage("err").build())
                .build();

        assertThat(response.isError()).isTrue();

        final JsonDeliveryResponse noPdf = response.toBuilder().build().withoutPDFContents();
        assertThat(noPdf.getItems()).allMatch(i -> i.getLabelPdfBase64() == null);

        final String json = mapper().writeValueAsString(response);
        final JsonDeliveryResponse back = mapper().readValue(json, JsonDeliveryResponse.class);
        assertThat(back).isEqualTo(response);
    }
}
