package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_QRCode;
import de.metas.handlingunits.model.I_M_HU_QRCode_Assignment;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.product.ProductId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class HUQRCodesRepositoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private HUQRCode generateRandomQRCode()
	{
		return HUQRCode.builder()
				.id(HUQRCodeUniqueId.ofUUID(UUID.randomUUID()))
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
	void createNew_then_getFirstQRCodeByHuId_and_check()
	{
		final HUQRCode qrCode = generateRandomQRCode();

		final HUQRCodesRepository repo = new HUQRCodesRepository();
		repo.createNew(qrCode, HuId.ofRepoId(667));

		final HUQRCode qrCodeLoaded = repo.getFirstQRCodeByHuId(HuId.ofRepoId(667))
				.orElseThrow(() -> new AdempiereException("No HUQRCode found"));

		assertThat(qrCodeLoaded)
				.usingRecursiveComparison()
				.isEqualTo(qrCode);
	}

	/**
	 * Check how was saved in DB.
	 * We need this because some fields are not used by our (java) application,
	 * but are used in Jasper Reports (via SQL)
	 */
	@Test
	void createNew_and_check_DB_record()
	{
		final HUQRCode qrCode = generateRandomQRCode();

		final HUQRCodesRepository repo = new HUQRCodesRepository();
		repo.createNew(qrCode, HuId.ofRepoId(667));

		final I_M_HU_QRCode record = POJOLookupMap.get().getFirstOnly(I_M_HU_QRCode.class);

		assertThat(record.getUniqueId()).isEqualTo(qrCode.getId().getAsString());
		assertThat(record.getDisplayableQRCode()).isEqualTo(qrCode.toDisplayableQRCode());
		assertThat(record.getRenderedQRCode()).isEqualTo(qrCode.toGlobalQRCode().getAsString());
		assertThat(record.getattributes()).isEqualTo(qrCode.toGlobalQRCode().getPayloadAsJson());

		I_M_HU_QRCode_Assignment assignment = POJOLookupMap.get().getFirstOnly(I_M_HU_QRCode_Assignment.class);
		assertThat(assignment.getM_HU_ID()).isEqualTo(667);
		assertThat(assignment.getM_HU_QRCode_ID()).isEqualTo(record.getM_HU_QRCode_ID());

	}
}