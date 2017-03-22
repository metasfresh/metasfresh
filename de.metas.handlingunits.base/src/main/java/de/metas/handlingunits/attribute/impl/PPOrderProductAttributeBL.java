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
import java.util.function.BiFunction;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import com.google.common.annotations.VisibleForTesting;

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

		final List<I_M_Attribute> attributesSetInPPOrder = new ArrayList<>();

		if (ppOrderASI != null)
		{
			final List<I_M_AttributeInstance> ppOrderAttributeInstances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(ppOrderASI);

			for (final I_M_AttributeInstance ppOrderAttributeInstance : ppOrderAttributeInstances)
			{
				if (ppOrderAttributeInstance.getValue() != null || ppOrderAttributeInstance.getValueNumber() != null && ppOrderAttributeInstance.getValueNumber().signum() != 0)
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
			putOrMergeInMap(attributesMap, ppOrderAttribute);
		}

		createHUAttributes(attributesMap, hu);

		// Make sure the HUs of the already existing receipt are also updated.
		updateExistingReceiptHUAttributes(attributesMap, ppOrder);
	}

	/**
	 * This method inserts or updates the given map for the given {@code ppOrderAttribute}. See {@link PPOrderProductAttributeBLputOrMergeInMapTests} for how it shall behave.
	 *
	 * @param attributesMap
	 * @param ppOrderAttribute
	 */
	@VisibleForTesting
	void putOrMergeInMap(
			final Map<Integer, AttributesWithValues> attributesMap,
			final I_PP_Order_ProductAttribute ppOrderAttribute)
	{
		final de.metas.handlingunits.model.I_M_Attribute attribute = InterfaceWrapperHelper.create(ppOrderAttribute.getM_Attribute(), de.metas.handlingunits.model.I_M_Attribute.class);

		final Integer attributeID = attribute.getM_Attribute_ID();

		if (!attributesMap.containsKey(attributeID))
		{
			final AttributesWithValues attributeWithValue = new AttributesWithValues();
			attributeWithValue.setTransferWhenNull(attribute.isTransferWhenNull()); // set this one first, it alters the behavior of the setValue*() methods
			attributeWithValue.setAttributeID(attributeID);
			attributeWithValue.setValue(ppOrderAttribute.getValue());
			attributeWithValue.setValueNumber(getValueNumberOrNull(ppOrderAttribute));

			attributesMap.put(attributeID, attributeWithValue);
		}
		else
		{
			final AttributesWithValues oldAttribute = attributesMap.get(attributeID);
			final AttributesWithValues newAttribute = computeValidAttributes(oldAttribute, ppOrderAttribute);
			if (newAttribute.isEmpty() && !oldAttribute.isEmpty())
			{
				// we switched from not-empty to empty. that means that differing values were encountered.
				Check.assume(newAttribute.stickWithNullValue,
						"old attribute with values + ppOrderAttribute = new atribute without values shall imply stickWithNullValue=true; oldAttribute={}, newAttribute={}, ppOrderAttribute={}",
						oldAttribute, newAttribute, ppOrderAttribute);
				attributesMap.put(attributeID, newAttribute);
			}
			else
			{
				attributesMap.put(attributeID, newAttribute); // we put the empty attribute
			}
		}
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
		final List<I_M_HU_Attribute> existingHUAttributes = Services.get(IHUAttributesDAO.class).retrieveAttributesOrdered(hu);

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

		final AttributesWithValues resultingAttribute = AttributesWithValues.copy(existingAttribute);

		// task #810
		// In case the attribute has the flag IsTransferWhenNull on true, if there is a value set (new or old), propagate it
		final String resultingValue = computeResulting(
				existingAttribute.getValue(),
				ppOrderAttribute.getValue(),
				isTransferWhenNull,
				(oldVal, newVal) -> oldVal.trim().equals(newVal.trim()) // IMPORTANT: the computeResulting method won't apply this function if either oldVal or newVal is null!
		);
		resultingAttribute.setValue(resultingValue);
		// task #810
		// Same for value number: In case the attribute has the flag IsTransferWhenValue on true, if there is a value set (new or old), propagate it
		final BigDecimal resultingValueNumber = computeResulting(
				existingAttribute.getValueNumber(),
				getValueNumberOrNull(ppOrderAttribute),
				isTransferWhenNull,
				(oldVal, newVal) -> oldVal.compareTo(newVal) == 0 // IMPORTANT: the computeResulting method won't apply this function of oldVal==null!);
		);
		resultingAttribute.setValueNumber(resultingValueNumber);

		return resultingAttribute;

	}

	private <T> T computeResulting(
			final T existingValue,
			final T newValue,
			final boolean isTransferWhenNull,
			final BiFunction<T, T, Boolean> equalityFunction)
	{
		final T resultingValue;

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
			else if (!equalityFunction.apply(existingValue, newValue))
			{
				// In case both values are set but they are different, leave the result null
				resultingValue = null;
			}
			else
			{
				resultingValue = existingValue; // defaut
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
		else if (!equalityFunction.apply(existingValue, newValue))
		{
			// In case both values are set but they are different, leave the result null
			resultingValue = null;
		}
		else
		{
			resultingValue = existingValue; // default
		}
		return resultingValue;
	}

	private BigDecimal getValueNumberOrNull(final I_PP_Order_ProductAttribute ppOrderAttribute)
	{
		return InterfaceWrapperHelper.isNull(ppOrderAttribute, org.eevolution.model.I_PP_Order_ProductAttribute.COLUMNNAME_ValueNumber)
				? null
				: ppOrderAttribute.getValueNumber();
	}

	@VisibleForTesting
	static class AttributesWithValues
	{
		private Integer attributeID;
		private String value;
		private BigDecimal valueNumber;

		private boolean stickWithNullValue = false;

		// task #810: Introducing IsTransferWhenNull
		private boolean isTransferWhenNull;

		static AttributesWithValues newInstance()
		{
			return new AttributesWithValues();
		}

		static AttributesWithValues copy(final AttributesWithValues original)
		{
			final AttributesWithValues copy = new AttributesWithValues();
			copy.attributeID = original.attributeID;
			copy.isTransferWhenNull = original.isTransferWhenNull;
			copy.stickWithNullValue = original.stickWithNullValue;
			copy.value = original.value;
			copy.valueNumber = original.valueNumber;
			return copy;
		}

		public Integer getAttributeID()
		{
			return attributeID;
		}

		public void setAttributeID(final Integer attributeID)
		{
			this.attributeID = attributeID;
		}

		public String getValue()
		{
			return value;
		}

		public void setValue(final String value)
		{
			if (stickWithNullValue)
			{
				return; // ignore
			}

			final boolean emptyBefore = isEmpty();
			this.value = value == null ? value : value.trim();
			final boolean emptyAfter = isEmpty();

			if (!emptyBefore && emptyAfter)
			{
				// If a attribute becomes empty, we stick with that, because it implies that at least two different values were seen
				stickWithNullValue = true;
			}
		}

		public BigDecimal getValueNumber()
		{
			return valueNumber;
		}

		public void setValueNumber(final BigDecimal valueNumber)
		{
			if (stickWithNullValue)
			{
				return; // ignore
			}

			final boolean emptyBefore = isEmpty();
			this.valueNumber = valueNumber;
			final boolean emptyAfter = isEmpty();

			if (!emptyBefore && emptyAfter)
			{
				// If a attribute becomes empty, we stick with that, because it implies that at least two different values were seen
				stickWithNullValue = true;
			}
		}

		/**
		 * Tell if at least one of Value or ValueNumber are not null
		 */
		public boolean isEmpty()
		{
			return valueNumber == null && value == null;
		}

		public void setTransferWhenNull(final boolean isTransferWhenNull)
		{
			this.isTransferWhenNull = isTransferWhenNull;
		}

		@Override
		public String toString()
		{
			return "AttributesWithValues [attributeID=" + attributeID + ", value=" + value + ", valueNumber=" + valueNumber + ", stickWithNullValue=" + stickWithNullValue + ", isTransferWhenNull=" + isTransferWhenNull + "]";
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
