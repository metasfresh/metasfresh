package de.metas.handlingunits.attributes.impl;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.StaticHUAssert;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.exceptions.InvalidAttributeValueException;
import de.metas.handlingunits.attribute.impl.PlainAttributeValue;
import de.metas.handlingunits.attribute.propagation.impl.HUAttributePropagationContext;
import de.metas.handlingunits.attribute.propagation.impl.NoPropagationHUAttributePropagator;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.ValueNamePair;
import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttributeValueTest extends AbstractHUTest
{
	private AttributesTestHelper attributesTestHelper;

	@Override
	protected void initialize()
	{
		attributesTestHelper = new AttributesTestHelper();
	}

	@Test
	public void testInvalidAttributeType_OnSet()
	{
		assertThatThrownBy(() -> {
			final I_M_Attribute attribute = new AttributesTestHelper().createM_Attribute("A1", "UnknownType", true);

			final IAttributeValue av = new PlainAttributeValue(NullAttributeStorage.instance, attribute);
			av.setValue(new HUAttributePropagationContext(NullAttributeStorage.instance, new NoPropagationHUAttributePropagator(), attribute), "value");

			StaticHUAssert.assertMock("mock");
		})
				.isInstanceOf(InvalidAttributeValueException.class);
	}

	@Test
	public void testInvalidAttributeType_OnGet()
	{
		assertThatThrownBy(() -> {
			final I_M_Attribute attribute = new AttributesTestHelper().createM_Attribute("A1", "UnknownType", true);

			final IAttributeValue av = new PlainAttributeValue(NullAttributeStorage.instance, attribute);
			av.getValue();

			StaticHUAssert.assertMock("mock");
		})
				.isInstanceOf(InvalidAttributeValueException.class);
	}

	@Test
	public void testMandatoryAttributeSettingToNull()
	{
		assertThatThrownBy(() -> {
			final I_M_Attribute attribute = new AttributesTestHelper().createM_Attribute("A1", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
			attribute.setIsMandatory(true);
			InterfaceWrapperHelper.save(attribute);

			final IAttributeValue av = new PlainAttributeValue(NullAttributeStorage.instance, attribute);

			// set it to some not null value first, because else, validation is not checked if value did not changed
			av.setValue(new HUAttributePropagationContext(NullAttributeStorage.instance, new NoPropagationHUAttributePropagator(), attribute), "some value");

			// Expect exception:
			av.setValue(new HUAttributePropagationContext(NullAttributeStorage.instance, new NoPropagationHUAttributePropagator(), attribute), null);

			StaticHUAssert.assertMock("mock");
		})
				.isInstanceOf(InvalidAttributeValueException.class);

	}

	@Test
	public void test_isStringValue_isNumericValue()
	{
		final IAttributeValue avString = new PlainAttributeValue(NullAttributeStorage.instance,
				attributesTestHelper.createM_Attribute("StringAttribute", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true));

		Assert.assertTrue("Attribute " + avString.getM_Attribute() + " shall be a string attribute", avString.isStringValue());
		Assert.assertFalse("Attribute " + avString.getM_Attribute() + " shall not be a numeric attribute", avString.isNumericValue());

		final IAttributeValue avList = new PlainAttributeValue(NullAttributeStorage.instance,
				attributesTestHelper.createM_Attribute("ListAttribute", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true));
		Assert.assertTrue("Attribute " + avList.getM_Attribute() + " shall be a string attribute", avList.isStringValue());
		Assert.assertFalse("Attribute " + avList.getM_Attribute() + " shall not be a numeric attribute", avList.isNumericValue());

		final IAttributeValue avNumber = new PlainAttributeValue(NullAttributeStorage.instance,
				attributesTestHelper.createM_Attribute("NumberAttribute", X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true));
		Assert.assertFalse("Attribute " + avNumber.getM_Attribute() + " shall not be a string attribute", avNumber.isStringValue());
		Assert.assertTrue("Attribute " + avNumber.getM_Attribute() + " shall be a numeric attribute", avNumber.isNumericValue());
	}

	/**
	 * Tests {@link IAttributeValue#getAvailableValues()} in case of a high volume attribute.
	 * <p>
	 * Expectations:
	 * <ul>
	 * <li>if there is no current value, empty list shall be returned.
	 * <li>if there is a current value set, a list containing only that value shall be returned.
	 * </ul>
	 */
	@Test
	@Disabled // TODO remove when M_Attribute.AD_Val_Rule is removed, or un-ignore
	public void test_HighVolumeAttribute()
	{
		// Create a dummy value which we will set it to our attribute.
		final I_AD_Val_Rule adValRule = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_Val_Rule.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(adValRule);

		//
		// Create the high volume attribute
		final I_M_Attribute attribute = new AttributesTestHelper().createM_Attribute("HighVolumeListAttribute", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		// attribute.setAD_Val_Rule(adValRule); // TODO clean up
		attribute.setIsHighVolume(true);
		InterfaceWrapperHelper.save(attribute);
		//
		Assert.assertTrue("Attribute shall be considered as HighVolume", Services.get(IAttributeDAO.class).isHighVolumeValuesList(attribute));
		//
		helper.createAttributeListValue(attribute, "1", "Value1");
		helper.createAttributeListValue(attribute, "2", "Value2");
		helper.createAttributeListValue(attribute, "3", "Value3");

		//
		// Create the Attribute Value on which we will do the tests
		final PlainAttributeValue attributeValue = new PlainAttributeValue(NullAttributeStorage.instance, attribute);

		//
		// Expectation: when M_Attribute.AD_Val_Rule_ID is set, getAvailableValues shall return only current value or empty if no value was set
		{
			assertThat(attributeValue.getAvailableValues()).as("Empty available values are expected for high volume attribute, when there is no current value").isEmpty();

			//
			// Set current value as "1" and test
			final IAttributeValueContext attributeValueContext = new HUAttributePropagationContext(NullAttributeStorage.instance, new NoPropagationHUAttributePropagator(), attribute);
			attributeValue.setValue(attributeValueContext, "1");
			Assert.assertEquals("Only current value shall be in available values for high volume attribute",
					Collections.singletonList(ValueNamePair.of("1", "Value1")),
					attributeValue.getAvailableValues());

			//
			// Set current value as "2" and test
			attributeValue.setValue(attributeValueContext, "2");
			Assert.assertEquals("Only current value shall be in available values for high volume attribute",
					Collections.singletonList(ValueNamePair.of("2", "Value2")),
					attributeValue.getAvailableValues());
		}
	}
}
