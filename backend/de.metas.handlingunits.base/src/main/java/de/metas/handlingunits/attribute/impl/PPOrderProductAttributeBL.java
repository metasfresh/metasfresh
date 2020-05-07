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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import de.metas.dimension.DimensionSpec;
import de.metas.dimension.IDimensionspecDAO;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeBL;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_PP_Order_ProductAttribute;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.logging.LogManager;
import lombok.ToString;

public class PPOrderProductAttributeBL implements IPPOrderProductAttributeBL
{
	private static final transient Logger logger = LogManager.getLogger(PPOrderProductAttributeBL.class);

	@Override
	public void updateHUAttributes(final Collection<I_M_HU> husToUpdate, final int fromPPOrderId)
	{
		final I_PP_Order fromPPOrder = InterfaceWrapperHelper.load(fromPPOrderId, I_PP_Order.class);
		Preconditions.checkNotNull(fromPPOrder, "fromPPOrder not found found ID=%s", fromPPOrderId);

		// Skip it if there are no HUs to update
		if (husToUpdate.isEmpty())
		{
			return;
		}

		logger.trace("updateHUAttributes: fromPPOrderId={}, husToUpdate={}", fromPPOrderId, husToUpdate);

		//
		// Fetch PP_Order's attributes that shall be propagated to HUs
		final AttributesMap attributesMap = getAttributesMap(fromPPOrder);
		if (attributesMap.isEmpty())
		{
			logger.trace("Skip updating because there were no attributes");
			return;
		}
		logger.trace("Using {}", attributesMap);

		//
		// Update the attributes of given HUs
		final Set<Integer> huIdsUpdated = new HashSet<>();
		husToUpdate.forEach(hu -> {
			updateHUAttributesFromAttributesMap(hu, attributesMap);
			huIdsUpdated.add(hu.getM_HU_ID());
		});

		//
		// Make sure the HUs of the already existing receipt are also updated.
		// TODO: consider doing this when some new PP_Order_ProductAttributes where added/changed
		{
			final Collection<I_M_HU> husAlreadyReceived = getAllReceivedHUs(fromPPOrder, huIdsUpdated); // exclude those which were already updated above
			husAlreadyReceived.forEach(hu -> {
				updateHUAttributesFromAttributesMap(hu, attributesMap);
				huIdsUpdated.add(hu.getM_HU_ID());
			});
		}
	}

	private static AttributesMap getAttributesMap(final I_PP_Order ppOrder)
	{
		final AttributesMap attributesMap = new AttributesMap();

		// Fetch all attributes to be transferred from PP_Order to HU
		final Set<Integer> attributeIdsToBeTransfered = getAttributeIdsToBeTransferred();
		if (attributeIdsToBeTransfered.isEmpty())
		{
			return attributesMap; // empty
		}

		// Fetch all attributes from PP_Order's ASI (which have a non-null value)
		final Set<Integer> attributeIdsSetInPPOrder = extractPPOrderASIAttributeIds(ppOrder);

		//
		// Collect all attributes which were directly assigned to given PP_Order
		final IPPOrderProductAttributeDAO ppOrderProductAttributeDAO = Services.get(IPPOrderProductAttributeDAO.class);
		final List<I_PP_Order_ProductAttribute> ppOrderAttributes = ppOrderProductAttributeDAO.retrieveProductAttributesForPPOrder(ppOrder.getPP_Order_ID());
		for (final I_PP_Order_ProductAttribute ppOrderAttribute : ppOrderAttributes)
		{
			final int attributeId = ppOrderAttribute.getM_Attribute_ID();

			// The attribute is not to transfer
			// See DIM_Dimension_Spec with InternalName = 'PP_Order_ProductAttribute_Transfer'
			final boolean isAttributeToTransfer = attributeIdsToBeTransfered.contains(attributeId);
			if (!isAttributeToTransfer)
			{
				continue;
			}

			// In case the attribute is coming from the PPOrder's ASI, leave it like it is
			final boolean isSetInPPOrder = attributeIdsSetInPPOrder.contains(attributeId);
			if (isSetInPPOrder)
			{
				continue;
			}

			attributesMap.putOrMerge(ppOrderAttribute);
		}

		return attributesMap;
	}

	/** @return M_Attribute_IDs to be transferred from PP_Order to HUs */
	private static Set<Integer> getAttributeIdsToBeTransferred()
	{
		final DimensionSpec dimPPOrderProductAttributesToTransfer = Services.get(IDimensionspecDAO.class).retrieveForInternalNameOrNull(HUConstants.DIM_PP_Order_ProductAttribute_To_Transfer);
		Check.errorIf(dimPPOrderProductAttributesToTransfer == null,
				"Unable to load DIM_Dimension_Spec record with InternalName={}", HUConstants.DIM_PP_Order_ProductAttribute_To_Transfer);

		final Set<Integer> attributeIdsToBeTransferred = dimPPOrderProductAttributesToTransfer.retrieveAttributes()
				.stream()
				.map(I_M_Attribute::getM_Attribute_ID)
				.collect(ImmutableSet.toImmutableSet());
		return attributeIdsToBeTransferred;
	}

	private static Set<Integer> extractPPOrderASIAttributeIds(final I_PP_Order ppOrder)
	{
		final int asiId = ppOrder.getM_AttributeSetInstance_ID();
		if (asiId <= 0)
		{
			return ImmutableSet.of();
		}

		final I_M_AttributeSetInstance ppOrderASI = InterfaceWrapperHelper.load(asiId, I_M_AttributeSetInstance.class);
		if (ppOrderASI == null)
		{
			return ImmutableSet.of();
		}

		//
		// Collect all attributes from PP_Order's ASI
		final Set<Integer> attributesSetInPPOrder = new HashSet<>();
		final List<I_M_AttributeInstance> ppOrderAttributeInstances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(ppOrderASI);
		for (final I_M_AttributeInstance ppOrderAttributeInstance : ppOrderAttributeInstances)
		{
			if (ppOrderAttributeInstance.getValue() != null
					|| ppOrderAttributeInstance.getValueNumber() != null && ppOrderAttributeInstance.getValueNumber().signum() != 0)
			{
				attributesSetInPPOrder.add(ppOrderAttributeInstance.getM_Attribute_ID());
			}
		}

		return attributesSetInPPOrder;
	}

	private static final Collection<I_M_HU> getAllReceivedHUs(final I_PP_Order ppOrder, final Set<Integer> excludeHUIds)
	{
		final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final Map<Integer, I_M_HU> receivedHUs = new HashMap<>();
		final List<I_PP_Cost_Collector> existingReceipts = ppCostCollectorDAO.retrieveExistingReceiptCostCollector(ppOrder);
		for (final I_PP_Cost_Collector receiptCollector : existingReceipts)
		{
			// Need assignments to take the created HUs
			final List<I_M_HU_Assignment> assignments = huAssignmentDAO.retrieveHUAssignmentsForModel(receiptCollector);
			for (final I_M_HU_Assignment assignment : assignments)
			{
				final int huId = assignment.getM_HU_ID();

				// Exclude HU if required
				if (excludeHUIds != null && excludeHUIds.contains(huId))
				{
					continue;
				}

				receivedHUs.computeIfAbsent(huId, k -> assignment.getM_HU());
			}
		}

		return receivedHUs.values();
	}

	/**
	 * Set the correct values in the already existing attributes of the HU
	 *
	 * @param hu
	 * @param from
	 */
	private static void updateHUAttributesFromAttributesMap(final I_M_HU hu, final AttributesMap from)
	{
		if (from.isEmpty())
		{
			return;
		}

		final List<I_M_HU_Attribute> existingHUAttributes = Services.get(IHUAttributesDAO.class).retrieveAttributesOrdered(hu);
		for (final I_M_HU_Attribute huAttribute : existingHUAttributes)
		{
			final int attributeId = huAttribute.getM_Attribute_ID();
			final AttributeWithValue attributeWithValue = from.getByAttributeId(attributeId);
			if (attributeWithValue == null)
			{
				// the attribute was not used in PPOrder. Nothing to modify
				continue;
			}

			// TODO: shall we skip it if attribute is null and isTransferIfNull=false

			huAttribute.setValue(attributeWithValue.getValueString());
			huAttribute.setValueNumber(attributeWithValue.getValueNumber());
			InterfaceWrapperHelper.save(huAttribute);
			logger.trace("Updated {}/{} from {}", hu, huAttribute, attributeWithValue);
		}
	}

	private static BigDecimal getValueNumberOrNull(final I_PP_Order_ProductAttribute ppOrderAttribute)
	{
		return InterfaceWrapperHelper.isNull(ppOrderAttribute, I_PP_Order_ProductAttribute.COLUMNNAME_ValueNumber) ? null : ppOrderAttribute.getValueNumber();
	}

	@VisibleForTesting
	static class AttributesMap
	{
		/** {@link AttributeWithValue} indexed by M_Attribute_ID */
		private final Map<Integer, AttributeWithValue> map = new HashMap<>();

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this).addValue(map).toString();
		}

		public boolean isEmpty()
		{
			return map.isEmpty();
		}

		public AttributeWithValue getByAttributeId(final int attributeId)
		{
			return map.get(attributeId);
		}

		/**
		 * This method inserts or updates the given map for the given {@code ppOrderAttribute}. See {@link PPOrderProductAttributeBL_putOrMergeInMap_Tests} for how it shall behave.
		 *
		 * @param attributesMap
		 * @param ppOrderAttribute
		 */
		public void putOrMerge(final I_PP_Order_ProductAttribute ppOrderAttribute)
		{
			final AttributeWithValue attributeWithValueToMerge = createAttributeWithValue(ppOrderAttribute);
			map.compute(attributeWithValueToMerge.getAttributeId(), (attributeId, oldAttribute) -> {
				if (oldAttribute == null)
				{
					return attributeWithValueToMerge;
				}
				else
				{
					final AttributeWithValue newAttribute = oldAttribute.combineToNew(attributeWithValueToMerge);
					return newAttribute;
				}
			});
		}

		private static final AttributeWithValue createAttributeWithValue(final I_PP_Order_ProductAttribute ppOrderAttribute)
		{
			final de.metas.handlingunits.model.I_M_Attribute attribute = InterfaceWrapperHelper.loadOutOfTrx(ppOrderAttribute.getM_Attribute_ID(), de.metas.handlingunits.model.I_M_Attribute.class);
			final String valueString = ppOrderAttribute.getValue();
			final BigDecimal valueNumber = getValueNumberOrNull(ppOrderAttribute);

			final AttributeWithValue attributeWithValue = AttributeWithValue.newInstance(attribute, valueString, valueNumber);
			return attributeWithValue;
		}

	}

	@Immutable
	@VisibleForTesting
	@ToString
	static final class AttributeWithValue
	{
		private static AttributeWithValue newInstance(final de.metas.handlingunits.model.I_M_Attribute attribute, final String valueString, final BigDecimal valueNumber)
		{
			final int attributeId = attribute.getM_Attribute_ID();
			final boolean transferWhenNull = attribute.isTransferWhenNull();
			final boolean stickWithNullValue = false;
			return new AttributeWithValue(attributeId, transferWhenNull, stickWithNullValue, valueString, valueNumber);
		}

		private final int attributeId;
		private final boolean transferWhenNull; // task #810

		private final String valueString;
		private final BigDecimal valueNumber;
		private final boolean stickWithNullValue;

		private AttributeWithValue( //
				final int attributeId, final boolean transferWhenNull //
				, final boolean stickWithNullValue //
				, final String valueString, final BigDecimal valueNumber //
		)
		{
			this.attributeId = attributeId;
			this.transferWhenNull = transferWhenNull;

			this.stickWithNullValue = stickWithNullValue;
			if (this.stickWithNullValue)
			{
				this.valueString = null;
				this.valueNumber = null;
			}
			else
			{
				this.valueString = valueString == null ? null : valueString.trim();
				this.valueNumber = valueNumber;
			}
		}

		private AttributeWithValue toStickyNull()
		{
			if (isStickWithNullValue())
			{
				return this;
			}

			final boolean stickWithNullValue = true;
			final String valueString = null;
			final BigDecimal valueNumber = null;
			return new AttributeWithValue(attributeId, transferWhenNull, stickWithNullValue, valueString, valueNumber);
		}

		public int getAttributeId()
		{
			return attributeId;
		}

		public String getValueString()
		{
			return valueString;
		}

		public BigDecimal getValueNumber()
		{
			return valueNumber;
		}

		/**
		 * @return true if value(string) and valueNumber are null
		 */
		private boolean isNullValue()
		{
			return valueNumber == null && valueString == null;
		}

		/** @return if {@link #isNullValue()} and this shall be preserved while merging attributes (usually because there was some error in incompatibility in attribute values) */
		private boolean isStickWithNullValue()
		{
			return stickWithNullValue;
		}

		private boolean isTransferWhenNull()
		{
			return transferWhenNull;
		}

		/** @return true if this attribute and <code>other</code> have the values equal */
		private boolean valueEquals(final AttributeWithValue other)
		{
			// Shortcut: same instance
			if (this == other)
			{
				return true;
			}

			// Compare valueString
			// NOTE: we assume the valueString is already normalized
			if (!Objects.equals(valueString, other.valueString))
			{
				return false;
			}

			//
			// Compare valueNumber
			// NOTE: we consider numbers equal even if the scale is different
			if (valueNumber == other.valueNumber)
			{
				// same value => OK
			}
			else if (valueNumber == null || other.valueNumber == null)
			{
				// one and only one value is null
				return false;
			}
			else if (valueNumber.compareTo(other.valueNumber) != 0)
			{
				// different values
				return false;
			}

			//
			return true;
		}

		/**
		 * Combine(merge) given attribute into this attribute.
		 *
		 * @param from
		 * @return
		 *         <ul>
		 *         <li>a new {@link AttributeWithValue} instance containing current values, merged with given <code>ppOrderAttribute</code> values
		 *         <li>this if there is no change
		 *         </ul>
		 */
		private AttributeWithValue combineToNew(final AttributeWithValue from)
		{
			// Make sure the "from" is compatible with this attribute (shall not happen)
			Preconditions.checkArgument(getAttributeId() == from.getAttributeId(), "attributeId is not compatible: this=%s, from=%s", this, from);
			Preconditions.checkArgument(isTransferWhenNull() == from.isTransferWhenNull(), "IsTransferWhenNull flag does not match:: this=%s, from=%s", this, from);

			// If this is a sticky attribute then don't combine it but return it as is
			if (isStickWithNullValue())
			{
				return this;
			}

			// flag to decide if an attribute value shall be propagated even if there are already HUs without this attribute (#810)
			final boolean isTransferWhenNull = isTransferWhenNull();

			if (isNullValue())
			{
				if (isTransferWhenNull)
				{
					return from; // this is null, consider "from" value which might be or not null
				}
				else
				{
					return toStickyNull(); // return null and stick with it
				}
			}
			else if (from.isNullValue())
			{
				if (isTransferWhenNull)
				{
					return this; // from is null, preserve current value which in this case it's not null because it was checked above
				}
				else
				{
					return toStickyNull(); // return null and stick with it
				}
			}
			else if (!valueEquals(from))
			{
				return toStickyNull(); // incompatible values => return null and stick with it
			}
			else
			{
				return this; // this equals with "from", preserve current value
			}
		}
	}

	@Override
	public void addPPOrderProductAttributes(final de.metas.handlingunits.model.I_PP_Cost_Collector costCollector)
	{
		final List<I_M_HU> topLevelHUs = Services.get(IHUAssignmentDAO.class).retrieveTopLevelHUsForModel(costCollector);

		final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);
		final IPPOrderProductAttributeDAO ppOrderProductAttributeDAO = Services.get(IPPOrderProductAttributeDAO.class);
		for (final I_M_HU hu : topLevelHUs)
		{
			final List<I_M_HU_Attribute> huAttributes = huAttributesDAO.retrieveAttributesOrdered(hu);

			ppOrderProductAttributeDAO.addPPOrderProductAttributes(costCollector, huAttributes);
		}
	}

	@Override
	public void addPPOrderProductAttributesFromIssueCandidate(final I_PP_Order_Qty issueCandidate)
	{
		final I_M_HU topLevelHU = issueCandidate.getM_HU();

		final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);
		final IPPOrderProductAttributeDAO ppOrderProductAttributeDAO = Services.get(IPPOrderProductAttributeDAO.class);
		final List<I_M_HU_Attribute> huAttributes = huAttributesDAO.retrieveAttributesOrdered(topLevelHU);

		ppOrderProductAttributeDAO.addPPOrderProductAttributesFromIssueCandidate(issueCandidate, huAttributes);
	}

}
