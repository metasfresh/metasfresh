
package de.metas.common.externalsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class JsonExternalSystemRequestTest
{
	final ObjectMapper mapper = new ObjectMapper();

	@Test
	void serializeDeserialize() throws IOException
	{
		final String sw6ConfigMappings = getExternalSystemShopware6ConfigMappings();
		final String sw6UOMMappings = getExternalSystemShopware6UOMMappings();

		final JsonExternalSystemRequest requestDeserialized = JsonExternalSystemRequest.builder()
				.externalSystemChildConfigValue("childValue")
				.externalSystemName(JsonExternalSystemName.of("externalSystem"))
				.externalSystemConfigId(JsonMetasfreshId.of(1))
				.orgCode("orgCode")
				.command("command")
				.traceId("traceId")
				.writeAuditEndpoint("writeAuditEndpoint")
				.parameter("parameterName1", "parameterValue1")
				.parameter("parameterName2", "parameterValue2")
				.parameter("configMappings", sw6ConfigMappings)
				.parameter("UOMMappings", sw6UOMMappings)
				.build();

		final String valueAsString = mapper.writeValueAsString(requestDeserialized);

		final JsonExternalSystemRequest readValue = mapper.readValue(valueAsString, JsonExternalSystemRequest.class);

		assertThat(readValue).isEqualTo(requestDeserialized);

		final List<JsonExternalSystemShopware6ConfigMapping> externalSystemShopware6ConfigMappings =
				mapper.readValue(requestDeserialized.getParameters().get("configMappings"),
								 mapper.getTypeFactory().constructCollectionType(List.class, JsonExternalSystemShopware6ConfigMapping.class));

		final JsonExternalSystemShopware6ConfigMapping externalSystemShopware6ConfigMapping = externalSystemShopware6ConfigMappings.get(0);
		assertThat(externalSystemShopware6ConfigMapping).isNotNull();
		assertThat(externalSystemShopware6ConfigMapping.getDescription()).isEqualTo("testDescription");
		assertThat(externalSystemShopware6ConfigMapping.getPaymentRule()).isEqualTo("testPaymentRule");
		assertThat(externalSystemShopware6ConfigMapping.getSw6CustomerGroup()).isEqualTo("testSw6CustomerGroup");
		assertThat(externalSystemShopware6ConfigMapping.getSw6PaymentMethod()).isEqualTo("testSw6PaymentMethod");
		assertThat(externalSystemShopware6ConfigMapping.getPaymentTermValue()).isEqualTo("testPaymentTerm");
		assertThat(externalSystemShopware6ConfigMapping.getDocTypeOrder()).isEqualTo("testDocTypeOrder");
		assertThat(externalSystemShopware6ConfigMapping.getSeqNo()).isEqualTo(10);
		assertThat(externalSystemShopware6ConfigMapping.getSeqNo()).isEqualTo(10);

		final JsonUOMMappings uomMappings = mapper.readValue(requestDeserialized.getParameters().get("UOMMappings"), JsonUOMMappings.class);

		final JsonUOMMapping jsonUOMMapping = uomMappings.getJsonUOMMappingList().get(0);
		assertThat(jsonUOMMapping).isNotNull();
		assertThat(jsonUOMMapping.getExternalCode()).isEqualTo("externalCode");
		assertThat(jsonUOMMapping.getUom().getCode()).isEqualTo("code");
		assertThat(jsonUOMMapping.getUom().getId().getValue()).isEqualTo(2);
	}

	private String getExternalSystemShopware6ConfigMappings() throws JsonProcessingException
	{

		final JsonExternalSystemShopware6ConfigMapping externalSystemShopware6ConfigMapping =
				JsonExternalSystemShopware6ConfigMapping.builder()
						.sw6PaymentMethod("testSw6PaymentMethod")
						.sw6CustomerGroup("testSw6CustomerGroup")
						.paymentTermValue("testPaymentTerm")
						.paymentRule("testPaymentRule")
						.docTypeOrder("testDocTypeOrder")
						.description("testDescription")
						.bPartnerLocationSyncAdvice(SyncAdvise.CREATE_OR_MERGE)
						.bPartnerSyncAdvice(SyncAdvise.READ_ONLY)
						.seqNo(10)
						.build();

		final List<JsonExternalSystemShopware6ConfigMapping> externalSystemShopware6ConfigMappings = new ArrayList<>();
		externalSystemShopware6ConfigMappings.add(externalSystemShopware6ConfigMapping);

		return mapper.writeValueAsString(externalSystemShopware6ConfigMappings);

	}

	private String getExternalSystemShopware6UOMMappings() throws JsonProcessingException
	{
		final JsonUOMMapping jsonUOMMapping =
				JsonUOMMapping.builder()
						.externalCode("externalCode")
						.uom(JsonUOM.builder()
									 .id(JsonMetasfreshId.of(2))
									 .code("code")
									 .build())
						.build();

		final List<JsonUOMMapping> uomMappings = new ArrayList<>();
		uomMappings.add(jsonUOMMapping);

		final JsonUOMMappings jsonUOMMappings = JsonUOMMappings.builder()
				.jsonUOMMappingList(uomMappings)
				.build();

		return mapper.writeValueAsString(jsonUOMMappings);
	}
}