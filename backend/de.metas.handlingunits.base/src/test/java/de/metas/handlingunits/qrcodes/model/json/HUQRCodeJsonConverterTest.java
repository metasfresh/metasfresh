package de.metas.handlingunits.qrcodes.model.json;

import com.google.common.collect.ImmutableList;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.product.ProductId;
import org.adempiere.mm.attributes.AttributeCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class HUQRCodeJsonConverterTest
{
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

	@Test
	public void toGlobalQRCode_fromQRCodeString()
	{
		final HUQRCode huQRCode = newStandardHUQRCode();

		final GlobalQRCode globalQRCode = HUQRCodeJsonConverter.toGlobalQRCode(huQRCode);
		final HUQRCode huQRCodeDeserialized = HUQRCodeJsonConverter.fromGlobalQRCodeJsonString(globalQRCode.getAsString());

		Assertions.assertThat(huQRCodeDeserialized).usingRecursiveComparison().isEqualTo(huQRCode);
	}
}