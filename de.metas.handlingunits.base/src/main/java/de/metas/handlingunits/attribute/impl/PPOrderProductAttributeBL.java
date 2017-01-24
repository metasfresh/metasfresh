package de.metas.handlingunits.attribute.impl;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import de.metas.dimension.IDimensionSpecAttributeDAO;
import de.metas.dimension.IDimensionspecDAO;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeBL;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_PP_Order_ProductAttribute;

public class PPOrderProductAttributeBL implements IPPOrderProductAttributeBL
{

	@Override
	public void updateHUAttributes(final I_PP_Order ppOrder, final I_M_HU hu)
	{
		final Map<Integer, AttributesWithValues> attributesMap = new HashMap<>();

		final String dimPPOrderAttributesToTransferName = HUConstants.DIM_PP_Order_ProductAttribute_To_Transfer;

		final IContextAware ppOrderCtxAware = InterfaceWrapperHelper.getContextAware(ppOrder);
		final I_DIM_Dimension_Spec dimPPOrderProductAttributesToTransfer = Services.get(IDimensionspecDAO.class).retrieveForInternalName(dimPPOrderAttributesToTransferName, ppOrderCtxAware);

		final List<I_M_Attribute> attributesToBeTransfered = Services.get(IDimensionSpecAttributeDAO.class)
				.retrieveAttributesForDimensionSpec(dimPPOrderProductAttributesToTransfer);

		// Also exclude from transfer the attributes that were already set from the pp_Order's ASI
		final I_M_AttributeSetInstance ppOrderASI = ppOrder.getM_AttributeSetInstance();

		List<I_M_Attribute> attributesSetInPPOrder = new ArrayList<>();

		if (ppOrderASI != null)
		{
			final List<I_M_AttributeInstance> ppOrderAttributeInstances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(ppOrderASI);

			for (final I_M_AttributeInstance ppOrderAttributeInstance : ppOrderAttributeInstances)
			{
				if (ppOrderAttributeInstance.getValue() != null || (ppOrderAttributeInstance.getValueNumber() != null && ppOrderAttributeInstance.getValueNumber().signum() != 0))
				{
					attributesSetInPPOrder.add(ppOrderAttributeInstance.getM_Attribute());
				}
			}
		}

		// Services
		final IPPOrderProductAttributeDAO ppOrderProductAttributeDAO = Services.get(IPPOrderProductAttributeDAO.class);

		final List<I_PP_Order_ProductAttribute> ppOrderAttributes = ppOrderProductAttributeDAO.retrieveProductAttributesForPPOrder(ppOrder);

		for (final I_PP_Order_ProductAttribute ppOrderAttribute : ppOrderAttributes)

		{
			final de.metas.handlingunits.model.I_M_Attribute attribute = InterfaceWrapperHelper.create(ppOrderAttribute.getM_Attribute(), de.metas.handlingunits.model.I_M_Attribute.class);

			final boolean isAttributeToTransfer = attributesToBeTransfered.contains(attribute);

			// The attribute is not to transfer
			// See DIM_Dimension_Spec with InternalName = 'PP_Order_ProductAttribute_Transfer'
			if (!isAttributeToTransfer)
			{
				continue;
			}

			// In case the attribute is coming from the PPOrder's ASI, leave it like it is
			final boolean isSetInPPOrder = attributesSetInPPOrder.contains(attribute);
			if (isSetInPPOrder)
			{
				continue;
			}

			final Integer attributeID = attribute.getM_Attribute_ID();

			if (!attributesMap.containsKey(attributeID))
			{
				final AttributesWithValues attributeWithValue = new AttributesWithValues();
				attributeWithValue.setAttributeID(attributeID);
				attributeWithValue.setValue(ppOrderAttribute.getValue());
				attributeWithValue.setValueNumber(ppOrderAttribute.getValueNumber());
				attributeWithValue.setTransferWhenNull(attribute.isTransferWhenNull());

				attributesMap.put(attributeID, attributeWithValue);
			}

			else
			{
				final AttributesWithValues existingAttribute = attributesMap.get(attributeID);

				if (!existingAttribute.isValid())
				{
					// If there is already an entry that is not valid ( both value and valueNumber are null)
					// it means the attribute will be with null values every time from now on
					continue;
				}

				final AttributesWithValues resultingAttribute = computeValidAttributes(existingAttribute, ppOrderAttribute);

				attributesMap.put(attributeID, resultingAttribute);
			}
		}

		createHUAttributes(attributesMap, hu);

		// Make sure the HUs of the already existing receipt are also updated.
		updateExistingReceiptHUAttributes(attributesMap, ppOrder);
	}

	/**
	 * For the HUs of the already existing receipts, update the HUAttributes values with the ones that were updated
	 *
	 * @param attributesMap
	 * @param ppOrder
	 */
	private void updateExistingReceiptHUAttributes(final Map<Integer, AttributesWithValues> attributesMap, final I_PP_Order ppOrder)
	{
		// existing receipts
		final List<I_PP_Cost_Collector> existingReceipts = Services.get(IPPCostCollectorDAO.class).retrieveExistingReceiptCostCollector(ppOrder);

		for (final I_PP_Cost_Collector receiptCollector : existingReceipts)
		{
			// Need assignments to take the created HUs
			final List<I_M_HU_Assignment> assignments = Services.get(IHUAssignmentDAO.class).retrieveHUAssignmentsForModel(receiptCollector);

			for (final I_M_HU_Assignment assignment : assignments)
			{
				createHUAttributes(attributesMap, assignment.getM_HU());
			}
		}
	}

	/**
	 * Set the correct values in the already existing attributes of the HU
	 *
	 * @param attributesMap
	 * @param hu
	 */
	private void createHUAttributes(final Map<Integer, AttributesWithValues> attributesMap, final I_M_HU hu)
	{
		// List<I_M_HU_Attribute>
		List<I_M_HU_Attribute> existingHUAttributes = Services.get(IHUAttributesDAO.class).retrieveAttributesOrdered(hu);

		for (final I_M_HU_Attribute huAttribute : existingHUAttributes)
		{
			final Integer attributeID = huAttribute.getM_Attribute_ID();
			final AttributesWithValues attributeWithValues = attributesMap.get(attributeID);

			if (attributeWithValues == null)
			{
				// the attribute was not used in PPOrder. Nothing to modify
				continue;
			}

			huAttribute.setValue(attributeWithValues.getValue());
			huAttribute.setValueNumber(attributeWithValues.getValueNumber());

			InterfaceWrapperHelper.save(huAttribute);
		}
	}

	/**
	 * @param existingAttribute
	 * @param ppOrderAttribute
	 * @return
	 *         <li>the existingAttribute if it has identical values with the ppOrderAttribute,
	 *         <li>a new AttributeWithValue with null values otherwise
	 */
	private AttributesWithValues computeValidAttributes(
			final AttributesWithValues existingAttribute,
			final I_PP_Order_ProductAttribute ppOrderAttribute)
	{
		// #810: flag to decide if an attribute value shall be propagated even if there are already HUs without this attribute set
		final boolean isTransferWhenNull = existingAttribute.isTransferWhenNull;

		final String existingValue = existingAttribute.getValue();
		final BigDecimal existingValueNumber = existingAttribute.getValueNumber();

		final String newValue = ppOrderAttribute.getValue();
		final BigDecimal newValueNumber = ppOrderAttribute.getValueNumber();

		final String resultingValue;
		final BigDecimal resultingValueNumber;

		// task #810
		// In case the attribute has the flag IsTransferWhenNull on true, if there is a value set (new or old), propagate it
		if (isTransferWhenNull)
		{
			if (existingValue == null)
			{
				resultingValue = newValue;
			}

			else if (newValue == null)
			{
				resultingValue = existingValue;
			}

			// In case both values are set but they are different, leave the result null
			else
			{
				resultingValue = null;
			}
		}

		else if (existingValue == null)
		{
			resultingValue = null; // the existing value is null so the result is null
		}
		else if (newValue == null)
		{
			resultingValue = null; // the new value is null so the result is null
		}
		else if (newValue.equals(existingValue))
		{
			resultingValue = newValue; // the attributes have the same value so the result is the same
		}
		else
		{
			resultingValue = null; // the values are different
		}

		// task #810
		// Same for value number: In case the attribute has the flag IsTransferWhenValue on true, if there is a value set (new or old), propagate it
		if (isTransferWhenNull)
		{
			if (existingValueNumber == null)
			{
				resultingValueNumber = newValueNumber;
			}

			else if (newValueNumber == null)
			{
				resultingValueNumber = existingValueNumber;
			}

			// In case both values are set but they are different, leave the result null
			else
			{
				resultingValueNumber = null;
			}
		}
		else if (existingValueNumber == null)
		{
			resultingValueNumber = null; // the existing valueNumber is null so the result is null
		}
		else if (newValue == null)
		{
			resultingValueNumber = null; // the new valueNumber is null so the result is null
		}
		else if (newValue.equals(existingValueNumber))
		{
			resultingValueNumber = newValueNumber; // the attributes have the same valueNumber so the result is the same
		}
		else
		{
			resultingValueNumber = null; // the valueNumbers are different
		}

		final AttributesWithValues resultingAttribute = new AttributesWithValues();
		resultingAttribute.setTransferWhenNull(existingAttribute.isTransferWhenNull);
		resultingAttribute.setAttributeID(existingAttribute.getAttributeID());
		resultingAttribute.setValue(resultingValue);
		resultingAttribute.setValueNumber(resultingValueNumber);

		return resultingAttribute;

	}

	private static class AttributesWithValues
	{

		public AttributesWithValues()
		{
			super();
		}

		private Integer attributeID;
		private String value;
		private BigDecimal valueNumber;

		// task #810: Introducing IsTransferWhenNull
		private boolean isTransferWhenNull;

		public Integer getAttributeID()
		{
			return attributeID;
		}

		public void setAttributeID(Integer attributeID)
		{
			this.attributeID = attributeID;
		}

		public String getValue()
		{
			return value;
		}

		public void setValue(String value)
		{
			this.value = value;
		}

		public BigDecimal getValueNumber()
		{
			return valueNumber;
		}

		public void setValueNumber(BigDecimal valueNumber)
		{
			this.valueNumber = valueNumber;
		}

		/**
		 * Tell if at least one of Value or ValueNumber are not null
		 */
		public boolean isValid()
		{
			if (valueNumber != null || value != null)
			{
				return true;
			}

			return false;
		}

		public void setTransferWhenNull(boolean isTransferWhenNull)
		{
			this.isTransferWhenNull = isTransferWhenNull;
		}

	}

	@Override
	public void addPPOrderProductAttributes(final de.metas.handlingunits.model.I_PP_Cost_Collector costCollector)
	{
		final List<I_M_HU> topLevelHUs = Services.get(IHUAssignmentDAO.class).retrieveTopLevelHUsForModel(costCollector);

		for (final I_M_HU hu : topLevelHUs)
		{
			final List<I_M_HU_Attribute> huAttributes = Services.get(IHUAttributesDAO.class).retrieveAttributesOrdered(hu);

			Services.get(IPPOrderProductAttributeDAO.class).addPPOrderProductAttributes(costCollector, huAttributes);

		}

	}
}
