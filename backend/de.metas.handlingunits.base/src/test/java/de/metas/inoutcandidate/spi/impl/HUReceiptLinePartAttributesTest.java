package de.metas.inoutcandidate.spi.impl;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class HUReceiptLinePartAttributesTest
{
	private HUTestHelper helper;
	private IHUAttributesDAO huAttributesDAO;
	private I_M_Attribute numberAttribute;
	private I_M_HU_PI piTU;

	@BeforeEach
	void init()
	{
		helper = HUTestHelper.newInstanceOutOfTrx();
		huAttributesDAO = Services.get(IHUAttributesDAO.class);

		numberAttribute = new AttributesTestHelper().createM_Attribute("NumberAttributeUsedInASI", X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);

		piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item piItemMaterial = helper.createHU_PI_Item_Material(piTU);
		helper.assignProduct(piItemMaterial, helper.pTomatoProductId, BigDecimal.TEN, helper.uomEach);
		helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(numberAttribute)
				.setM_HU_PI(piTU)
				.setUseInASI(true));
	}

	@ParameterizedTest
	@CsvSource({ "0, 0.00", "10, 10.0", "1.5, 1.50" })
	void numericValuesDifferingOnlyInScale_produceEqualKeys(final String value1, final String value2)
	{
		final I_M_HU tu1 = createTUWithNumberAttributeValue(new BigDecimal(value1));
		final I_M_HU tu2 = createTUWithNumberAttributeValue(new BigDecimal(value2));

		assertThat(aggregationKeyOf(tu1)).isEqualTo(aggregationKeyOf(tu2));
	}

	@ParameterizedTest
	@CsvSource({ "0, 0.01", "10, 1", "1.5, 15" })
	void differentNumericValues_produceDifferentKeys(final String value1, final String value2)
	{
		final I_M_HU tu1 = createTUWithNumberAttributeValue(new BigDecimal(value1));
		final I_M_HU tu2 = createTUWithNumberAttributeValue(new BigDecimal(value2));

		assertThat(aggregationKeyOf(tu1)).isNotEqualTo(aggregationKeyOf(tu2));
	}

	/**
	 * Writes the value straight into the persisted {@link I_M_HU_Attribute} record, i.e. with the scale it has in the database.
	 */
	private I_M_HU createTUWithNumberAttributeValue(final BigDecimal value)
	{
		final I_M_HU tu = helper.createHUs(helper.createMutableHUContextOutOfTransaction(), piTU, helper.pTomatoProductId, BigDecimal.TEN, helper.uomEach).get(0);

		final I_M_HU_Attribute huAttribute = huAttributesDAO.retrieveAttribute(tu, AttributeId.ofRepoId(numberAttribute.getM_Attribute_ID()));
		huAttribute.setValueNumber(value);
		InterfaceWrapperHelper.saveRecord(huAttribute);

		return tu;
	}

	private Object aggregationKeyOf(final I_M_HU tu)
	{
		return HUReceiptLinePartAttributes.newInstance(helper.createMutableHUContextOutOfTransaction(), tu).getAttributeStorageAggregationKey();
	}
}
