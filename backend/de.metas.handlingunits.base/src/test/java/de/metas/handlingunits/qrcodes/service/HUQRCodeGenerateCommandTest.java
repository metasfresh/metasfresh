package de.metas.handlingunits.qrcodes.service;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ExtendWith(SnapshotExtension.class)
class HUQRCodeGenerateCommandTest
{
	private Expect expect;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@SuppressWarnings("SameParameterValue")
	private ProductId product(final int id, final String value, final String name)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setM_Product_ID(id);
		product.setValue(value);
		product.setName(name);
		InterfaceWrapperHelper.save(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private AttributeId attribute(final String value, final String name, AttributeValueType valueType)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		attribute.setValue(value);
		attribute.setName(name);
		attribute.setAttributeValueType(valueType.getCode());
		InterfaceWrapperHelper.save(attribute);
		return AttributeId.ofRepoId(attribute.getM_Attribute_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private AttributeValueId attributeListItem(final int id, final AttributeId attributeId, final String name)
	{
		final I_M_AttributeValue record = InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class);
		record.setM_AttributeValue_ID(id);
		record.setM_Attribute_ID(attributeId.getRepoId());
		record.setValue(name + "_Value");
		record.setName(name);
		InterfaceWrapperHelper.save(record);
		return AttributeValueId.ofRepoId(record.getM_AttributeValue_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private HuPackingInstructionsId packingInstructions(final int id, final String name, final String unitType)
	{
		final I_M_HU_PI pi = InterfaceWrapperHelper.newInstance(I_M_HU_PI.class);
		pi.setM_HU_PI_ID(id);
		pi.setName(name);
		InterfaceWrapperHelper.save(pi);

		final I_M_HU_PI_Version piVersion = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class);
		piVersion.setM_HU_PI_ID(pi.getM_HU_PI_ID());
		piVersion.setIsCurrent(true);
		piVersion.setHU_UnitType(unitType);
		InterfaceWrapperHelper.save(piVersion);

		return HuPackingInstructionsId.ofRepoId(pi.getM_HU_PI_ID());
	}

	private static HUQRCodeGenerateCommand.HUQRCodeGenerateCommandBuilder newCommand()
	{
		return HUQRCodeGenerateCommand.builder()
				.handlingUnitsBL(Services.get(IHandlingUnitsBL.class))
				.productBL(Services.get(IProductBL.class))
				.attributeDAO(Services.get(IAttributeDAO.class));
	}

	@Test
	void standardTest()
	{
		final HuPackingInstructionsId piId = packingInstructions(70003, "Some TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final ProductId productId = product(60001, "P1", "Product 1");
		final AttributeId attributeId1 = attribute("A1", "Attribute 1", AttributeValueType.STRING);
		final AttributeId attributeId2 = attribute("A2", "Attribute 2", AttributeValueType.NUMBER);
		final AttributeId attributeId3 = attribute("A3", "Attribute 3", AttributeValueType.DATE);
		final AttributeId attributeId4 = attribute("A4", "Attribute 4", AttributeValueType.LIST);
		final AttributeValueId attributeId4_itemId1 = attributeListItem(60002, attributeId4, "A4_Item1");

		final List<HUQRCode> qrCodes = newCommand()
				.randomUUIDGenerator(new MockedUniqueUUIDGenerator(
						UUID.fromString("53c5f490-f46d-4aae-a357-fefc2c0d76b2"),
						UUID.fromString("64ad6577-fd95-4e47-8e65-f4648d747319")
				))
				.request(HUQRCodeGenerateRequest.builder()
						.count(2)
						.huPackingInstructionsId(piId)
						.productId(productId)
						.attributes(ImmutableList.of(
								HUQRCodeGenerateRequest.Attribute.builder()
										.attributeId(attributeId1)
										.valueString("valueString")
										.build(),
								HUQRCodeGenerateRequest.Attribute.builder()
										.attributeId(attributeId2)
										.valueNumber(new BigDecimal("12.3456"))
										.build(),
								HUQRCodeGenerateRequest.Attribute.builder()
										.attributeId(attributeId3)
										.valueDate(LocalDate.parse("2021-11-12"))
										.build(),
								HUQRCodeGenerateRequest.Attribute.builder()
										.attributeId(attributeId4)
										.valueListId(attributeId4_itemId1)
										.build()
						))
						.build())
				.build()
				.execute();

		expect.serializer("orderedJson").toMatchSnapshot(qrCodes);
	}
}