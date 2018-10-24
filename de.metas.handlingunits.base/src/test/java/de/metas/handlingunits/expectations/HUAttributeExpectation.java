package de.metas.handlingunits.expectations;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.TimeUtil;
import org.junit.Assert;

import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.impl.ASIAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.util.Check;
import de.metas.util.Services;

public class HUAttributeExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	public static final HUAttributeExpectation<Object> newExpectation()
	{
		return new HUAttributeExpectation<>();
	}

	private I_M_Attribute attribute;
	/** i.e. M_Attribute.Value */
	private String attributeKey;
	private I_M_HU_PI_Attribute piAttribute;
	private String valueString;
	private boolean valueStringSet;
	private BigDecimal valueNumber;
	private boolean valueNumberSet;
	private Date valueDate;
	private boolean valueDateSet;

	public HUAttributeExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	private HUAttributeExpectation()
	{
		this(null);
	}

	public HUAttributeExpectation<ParentExpectationType> assertExpected(final I_M_HU_Attribute huAttribute)
	{
		final String message = null;
		return assertExpected(message, huAttribute);
	}

	public HUAttributeExpectation<ParentExpectationType> assertExpected(final String message, final I_M_HU_Attribute huAttribute)
	{
		assertExpected(newErrorMessage(message), huAttribute);
		return this;
	}

	public HUAttributeExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final I_M_HU_Attribute huAttribute)
	{
		final ErrorMessage messageActual = ErrorMessage.derive(message)
				.addContextInfo("HU Attribute", huAttribute);

		assertNotNull(messageActual.expect("huAttribute is null"), huAttribute);

		if (attribute != null)
		{
			assertEquals(messageActual.expect("M_Attribute_ID"), attribute.getM_Attribute_ID(), huAttribute.getM_Attribute_ID());
		}
		if (attributeKey != null)
		{
			final I_M_Attribute attributeActual = Services.get(IAttributeDAO.class).getAttributeById(huAttribute.getM_Attribute_ID());
			assertNotNull(messageActual.expect("M_Attribute_ID is null"), attributeActual);
			assertEquals(messageActual.expect("M_Attribute.Value"), attributeKey, attributeActual.getValue());
		}
		if (piAttribute != null)
		{
			assertEquals(messageActual.expect("M_HU_PI_Attribute_ID"), piAttribute.getM_HU_PI_Attribute_ID(), huAttribute.getM_HU_PI_Attribute_ID());
		}
		if (valueStringSet)
		{
			assertEquals(messageActual.expect("ValueString"), valueString, huAttribute.getValue());
		}
		if (valueNumberSet)
		{
			assertEquals(messageActual.expect("ValueNumber"), valueNumber, huAttribute.getValueNumber());
		}
		if (valueDateSet)
		{
			assertEquals(messageActual.expect("ValueDate"), valueDate, huAttribute.getValueDate());
		}

		return this;
	}

	public HUAttributeExpectation<ParentExpectationType> assertExpected(final String message, final IAttributeStorage attributeStorage)
	{
		return assertExpected(newErrorMessage(message), attributeStorage);
	}

	public HUAttributeExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final IAttributeStorage attributeStorage)
	{
		ErrorMessage messageToUse = derive(message)
				.addContextInfo("Attribute Storage", attributeStorage);

		assertNotNull(messageToUse.expect("attributeStorage is null"), attributeStorage);

		//
		// Attribute
		final I_M_Attribute attributeExpected = getAttributeNotNull(messageToUse.expect("attribute not configured"));
		messageToUse = messageToUse.addContextInfo("Attribute", attributeExpected);
		assertTrue(messageToUse.expect("attribute not found"), attributeStorage.hasAttribute(attributeExpected));

		//
		// Get IAttributeValue
		final IAttributeValue attributeValueActual = attributeStorage.getAttributeValue(attributeExpected);
		assertNotNull(messageToUse.expect("No IAttributeValue found"), attributeValueActual);

		//
		// Validates values
		if (piAttribute != null)
		{
			// TODO: validate piAttribute
		}
		if (valueStringSet)
		{
			assertEquals(messageToUse.expect("ValueString"), valueString, attributeValueActual.getValueAsString());
		}
		if (valueNumberSet)
		{
			assertEquals(messageToUse.expect("ValueNumber"), valueNumber, attributeValueActual.getValueAsBigDecimal());
		}
		if (valueDateSet)
		{
			assertEquals(messageToUse.expect("ValueDate"), valueDate, attributeValueActual.getValueAsDate());
		}

		return this;
	}

	public HUAttributeExpectation<ParentExpectationType> assertExpected(final String message, final I_M_AttributeSetInstance asi)
	{
		final IAttributeStorage attributeStorage = new ASIAttributeStorageFactory().getAttributeStorage(asi);
		return assertExpected(message, attributeStorage);
	}

	/**
	 * Assert expected "HU_CostPrice" on all included VHUs of given TU
	 *
	 * @param message
	 * @param tuAttributeStorage TU's attribute storage
	 * @return
	 */
	public HUAttributeExpectation<ParentExpectationType> assertExpectedOnTU(final String message, final IAttributeStorage tuAttributeStorage)
	{
		final String prefix = (message == null ? "" : message)
				+ "\nTU Attribute Storage: " + tuAttributeStorage
				+ "\n\n";
		final Collection<IAttributeStorage> vhuAttributeStorages = tuAttributeStorage.getChildAttributeStorages(true);
		Assert.assertNotNull(prefix + "No VHU storages found on TU", vhuAttributeStorages);
		Assert.assertFalse(prefix + "No VHU storages found on TU", vhuAttributeStorages.isEmpty());

		for (final IAttributeStorage vhuAttributeStorage : vhuAttributeStorages)
		{
			assertExpected(prefix, vhuAttributeStorage);
		}

		return this;
	}

	/**
	 * Sets "HU_CostPrice" attribute to all VHUs of given TU
	 *
	 * The cost price settings will be taken from {@link #rs_PriceActual_Expectation}.
	 *
	 * @param tuHU TU
	 * @return
	 */
	public HUAttributeExpectation<ParentExpectationType> updateCostPriceToTU(final IAttributeStorage tuAttributeStorage)
	{
		final I_M_Attribute attr_CostPrice = getAttributeNotNull();
		final BigDecimal costPrice = getValueNumber();

		final String prefix = "TU Attribute Storage: " + tuAttributeStorage
				+ "\n\n";

		Assert.assertNotNull(prefix + "tuAttributeStorage shall not be null", tuAttributeStorage);

		//
		// Get VHU attribute storages
		final Collection<IAttributeStorage> vhuAttributeStorages = tuAttributeStorage.getChildAttributeStorages(true);
		Assert.assertNotNull(prefix + "No VHU storages found on TU", vhuAttributeStorages);
		Assert.assertFalse(prefix + "No VHU storages found on TU", vhuAttributeStorages.isEmpty());

		//
		// Iterate VHU attribute storages and set the CostPrice
		for (final IAttributeStorage vhuAttributeStorage : vhuAttributeStorages)
		{
			vhuAttributeStorage.setValue(attr_CostPrice, costPrice);

			// Make sure is set
			assertExpected(prefix, vhuAttributeStorage);
		}

		return this;
	}

	public HUAttributeExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute)
	{
		this.attribute = attribute;
		return this;
	}

	public HUAttributeExpectation<ParentExpectationType> attribute(final String attributeValueKey)
	{
		this.attributeKey = attributeValueKey;
		return this;
	}

	public HUAttributeExpectation<ParentExpectationType> piAttribute(final I_M_HU_PI_Attribute piAttribute)
	{
		this.piAttribute = piAttribute;
		return this;
	}

	public HUAttributeExpectation<ParentExpectationType> valueString(final String valueString)
	{
		this.valueString = valueString;
		this.valueStringSet = true;
		return this;
	}

	public HUAttributeExpectation<ParentExpectationType> valueNumber(final BigDecimal valueNumber)
	{
		this.valueNumber = valueNumber;
		this.valueNumberSet = true;
		return this;
	}

	public BigDecimal getValueNumber()
	{
		return this.valueNumber;
	}

	public HUAttributeExpectation<ParentExpectationType> valueNumber(final String valueNumberStr)
	{
		return valueNumber(new BigDecimal(valueNumberStr));
	}

	public HUAttributeExpectation<ParentExpectationType> valueDate(final Date valueDate)
	{
		this.valueDate = valueDate;
		this.valueDateSet = true;
		return this;
	}

	public final AttributeId getAttributeIdNotNull()
	{
		final I_M_Attribute attribute = getAttributeNotNull();
		return AttributeId.ofRepoId(attribute.getM_Attribute_ID());
	}

	public final I_M_Attribute getAttributeNotNull()
	{
		final ErrorMessage messageIfNotFound = newErrorMessage("")
				.expect("no cost price attribute was configured on expectation " + this);
		return getAttributeNotNull(messageIfNotFound);
	}

	private final I_M_Attribute getAttributeNotNull(final ErrorMessage messageIfNotFound)
	{
		if (attribute != null)
		{
			return attribute;
		}

		if (attributeKey != null)
		{
			return Services.get(IAttributeDAO.class).retrieveAttributeByValue(attributeKey);
		}

		// Fail
		Assert.fail(messageIfNotFound.toString());
		return null; // shall not reach this point
	}

	public I_M_HU_Attribute createHUAttribute(I_M_HU hu)
	{
		final I_M_HU_Attribute huAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class, getContext());
		huAttribute.setM_HU(hu);
		huAttribute.setAD_Org_ID(hu.getAD_Org_ID());

		huAttribute.setM_Attribute_ID(getAttributeNotNull().getM_Attribute_ID());

		Check.assumeNotNull(piAttribute, "piAttribute not null");
		huAttribute.setM_HU_PI_Attribute_ID(piAttribute.getM_HU_PI_Attribute_ID());

		if (valueStringSet)
		{
			huAttribute.setValue(valueString);
		}
		if (valueNumberSet)
		{
			huAttribute.setValueNumber(valueNumber);
		}
		if (valueDateSet)
		{
			huAttribute.setValueDate(TimeUtil.asTimestamp(valueDate));
		}

		InterfaceWrapperHelper.save(huAttribute);
		return huAttribute;
	}
}
