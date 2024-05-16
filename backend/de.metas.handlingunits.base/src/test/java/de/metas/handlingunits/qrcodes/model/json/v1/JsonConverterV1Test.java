package de.metas.handlingunits.qrcodes.model.json.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.product.ProductId;
import de.metas.test.SnapshotFunctionFactory;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

class JsonConverterV1Test
{
	@BeforeAll
	static void beforeAll() {start(AdempiereTestHelper.SNAPSHOT_CONFIG, SnapshotFunctionFactory.newFunction());}

	private static HUQRCode newStandardHUQRCode()
	{
		return HUQRCode.builder()
				.id(HUQRCodeUniqueId.ofUUID(UUID.fromString("53c5f490-f46d-4aae-a357-fefc2c0d76b2")))
				.packingInfo(HUQRCodePackingInfo.builder()
						.huUnitType(HUQRCodeUnitType.TU)
						.packingInstructionsId(HuPackingInstructionsId.ofRepoId(123))
						.caption("Some TU")
						.build())
				.product(HUQRCodeProductInfo.builder()
						.id(ProductId.ofRepoId(111))
						.code("productCode")
						.name("productName")
						.build())
				.attributes(ImmutableList.of(
						HUQRCodeAttribute.builder()
								.code(AttributeCode.ofString("A1"))
								.displayName("Attribute 1")
								.value("value")
								.build(),
						HUQRCodeAttribute.builder()
								.code(AttributeCode.ofString("A2"))
								.displayName("Attribute 2")
								.value("value")
								.valueRendered("rendered value")
								.build()
				))
				.build();
	}

	public void testSerializeDeserialize(final Object obj) throws IOException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = jsonObjectMapper.writeValueAsString(obj);
		final Object objDeserialized = jsonObjectMapper.readValue(json, obj.getClass());
		assertThat(objDeserialized)
				.usingRecursiveComparison()
				.isEqualTo(obj);
	}

	@Test
	public void toJson_fromJson() throws IOException
	{
		final HUQRCode qrCode = newStandardHUQRCode();
		final JsonHUQRCodeV1 jsonObj = JsonConverterV1.toJson(qrCode);
		testSerializeDeserialize(jsonObj);

		final HUQRCode qrCodeDeserialized = JsonConverterV1.fromJson(jsonObj);
		assertThat(qrCodeDeserialized).usingRecursiveComparison().isEqualTo(qrCode);
	}

	/**
	 * Makes sure that the JSON format is not changing.
	 * This is important because those QR codes will be printed and sticked to actual HUs in the warehouse.
	 */
	@Test
	public void checkJsonFormatIsNotChanging()
	{
		final JsonHUQRCodeV1 json = JsonConverterV1.toJson(newStandardHUQRCode());
		expect(json).toMatchSnapshot();
	}
}