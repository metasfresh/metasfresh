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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.dimension.DimensionSpec;
import de.metas.dimension.IDimensionspecDAO;
import de.metas.document.sequence.DocSequenceId;
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
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ISerialNoBL;
import org.adempiere.mm.attributes.api.SerialNoContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class PPOrderProductAttributeBL implements IPPOrderProductAttributeBL
{
	private static final transient Logger logger = LogManager.getLogger(PPOrderProductAttributeBL.class);

	@Override
	public void updateHUAttributes(@NonNull final Collection<I_M_HU> husToUpdate, @NonNull final PPOrderId fromPPOrderId, @Nullable final PPOrderBOMLineId coByProductOrderBOMLineId)
	{
		// Skip it if there are no HUs to update
		if (husToUpdate.isEmpty())
		{
			return;
		}

		logger.trace("updateHUAttributes: fromPPOrderId={}, husToUpdate={}, coByProductOrderBOMLineId={}", fromPPOrderId, husToUpdate, coByProductOrderBOMLineId);

		//
		// Fetch PP_Order's attributes that shall be propagated to HUs
		final IPPOrderDAO ppOrdersRepo = Services.get(IPPOrderDAO.class);
		final I_PP_Order fromPPOrder = ppOrdersRepo.getById(fromPPOrderId);
		final AttributesMap attributesMap = getAttributesMap(fromPPOrder);
		logger.trace("Using {}", attributesMap);

		//
		// SerialNo info (if any)
		// SerialNo is calculated only for MainProducts, and not for lines (By or Co Products)
		final Optional<SerialNoContext> serialNoContext;
		if (coByProductOrderBOMLineId == null)
		{
			serialNoContext = extractSerialNoContext(fromPPOrder);
		}
		else
		{
			serialNoContext = Optional.empty();
		}
		logger.trace("Using {}", serialNoContext);

		//
		// Stop here if there is nothing to do
		if (attributesMap.isEmpty() && !serialNoContext.isPresent())
		{
			logger.trace("Skip updating because there is nothing to update from");
			return;
		}

		//
		// Update the attributes of given HUs
		final Set<Integer> huIdsUpdated = new HashSet<>();
		husToUpdate.forEach(hu -> {
			updateHUAttributesFromAttributesMap(hu, attributesMap, serialNoContext);
			huIdsUpdated.add(hu.getM_HU_ID());
		});

		//
		// Make sure the HUs of the already existing receipt are also updated.
		// TODO: consider doing this when some new PP_Order_ProductAttributes where added/changed
		{
			final Collection<I_M_HU> husAlreadyReceived = getAllReceivedHUs(fromPPOrderId, huIdsUpdated); // exclude those which were already updated above
			husAlreadyReceived.forEach(hu -> {
				updateHUAttributesFromAttributesMap(hu, attributesMap, serialNoContext);
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

	private Optional<SerialNoContext> extractSerialNoContext(final I_PP_Order ppOrder)
	{
		final IPPOrderBOMBL orderBOMBL = Services.get(IPPOrderBOMBL.class);
		final IProductDAO productsRepo = Services.get(IProductDAO.class);

		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		final DocSequenceId serialNoSequenceId = orderBOMBL.getSerialNoSequenceId(ppOrderId).orElse(null);
		if (serialNoSequenceId == null)
		{
			return Optional.empty();
		}

		final ProductId finishedGoodsProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final String finishedGoodsProductValue = productsRepo.retrieveProductValueByProductId(finishedGoodsProductId);

		final SerialNoContext serialNoContext = SerialNoContext.builder()
				.sequenceId(serialNoSequenceId)
				.clientId(ClientId.ofRepoId(ppOrder.getAD_Client_ID()))
				.productNo(finishedGoodsProductValue)
				.build();

		return Optional.of(serialNoContext);
	}

	/**
	 * @return M_Attribute_IDs to be transferred from PP_Order to HUs
	 */
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
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(ppOrder.getM_AttributeSetInstance_ID());
		if (asiId.isNone())
		{
			return ImmutableSet.of();
		}

		final I_M_AttributeSetInstance ppOrderASI = Services.get(IAttributeDAO.class).getAttributeSetInstanceById(asiId);
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

	private static final Collection<I_M_HU> getAllReceivedHUs(final PPOrderId ppOrderId, final Set<Integer> excludeHUIds)
	{
		final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final Map<Integer, I_M_HU> receivedHUs = new HashMap<>();
		final List<I_PP_Cost_Collector> existingReceipts = ppCostCollectorDAO.getReceiptsByOrderId(ppOrderId);
		for (final I_PP_Cost_Collector receiptCollector : existingReceipts)
		{
			// Need assignments to take the created HUs
			final List<I_M_HU_Assignment> assignments = huAssignmentDAO.retrieveTopLevelHUAssignmentsForModel(receiptCollector);
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
	 */
	private static void updateHUAttributesFromAttributesMap(
			final I_M_HU hu,
			final AttributesMap from,
			final Optional<SerialNoContext> serialNoContext)
	{
		// Stop here if there is nothing to do
		if (from.isEmpty() && !serialNoContext.isPresent())
		{
			return;
		}

		// services
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		final ISerialNoBL serialNoBL = Services.get(ISerialNoBL.class);
		final IHUAttributesDAO huAttributesRepo = Services.get(IHUAttributesDAO.class);

		final AttributeId serialNoAttributeId = serialNoContext.isPresent()
				? attributesRepo.getAttributeIdByCode(AttributeConstants.ATTR_SerialNo)
				: null;

		final List<I_M_HU_Attribute> existingHUAttributes = huAttributesRepo.retrieveAttributesOrdered(hu).getHuAttributes();
		for (final I_M_HU_Attribute existingHUAttribute : existingHUAttributes)
		{
			final AttributeId attributeId = AttributeId.ofRepoId(existingHUAttribute.getM_Attribute_ID());

			//
			// SerialNo
			if (serialNoAttributeId != null
					&& serialNoAttributeId.equals(attributeId)
					&& serialNoContext.isPresent()
					&& Check.isEmpty(existingHUAttribute.getValue(), true))
			{
				final String serialNo = serialNoBL.getAndIncrementSerialNo(serialNoContext.get()).orElse(null);
				if (serialNo != null)
				{
					existingHUAttribute.setValue(serialNo);
					existingHUAttribute.setValueNumber(null);
					huAttributesRepo.save(existingHUAttribute);
					logger.trace("Updated SerialNo {}/{} to {}", hu, existingHUAttribute, serialNo);

					// IMPORTANT: If we have a configured SerialNo we shall use it.
					// In this case we shall ignore the SerialNo which was collected from issued HUs.
					continue;
				}
			}

			//
			// Update HU attribute from order's collected attribute
			if (from.getByAttributeId(attributeId) != null)
			{
				final AttributeWithValue attributeWithValue = from.getByAttributeId(attributeId);

				// TODO: shall we skip it if attribute is null and isTransferIfNull=false

				existingHUAttribute.setValue(attributeWithValue.getValueString());
				existingHUAttribute.setValueNumber(attributeWithValue.getValueNumber());
				huAttributesRepo.save(existingHUAttribute);
				logger.trace("Updated {}/{} from {}", hu, existingHUAttribute, attributeWithValue);
			}
		}
	}

	private static BigDecimal getValueNumberOrNull(final I_PP_Order_ProductAttribute ppOrderAttribute)
	{
		return InterfaceWrapperHelper.isNull(ppOrderAttribute, I_PP_Order_ProductAttribute.COLUMNNAME_ValueNumber) ? null : ppOrderAttribute.getValueNumber();
	}

	@VisibleForTesting
	@ToString
	static class AttributesMap
	{
		private final Map<AttributeId, AttributeWithValue> map = new HashMap<>();

		public boolean isEmpty()
		{
			return map.isEmpty();
		}

		public AttributeWithValue getByAttributeId(final AttributeId attributeId)
		{
			return map.get(attributeId);
		}

		/**
		 * This method inserts or updates the given map for the given {@code ppOrderAttribute}. See {@link PPOrderProductAttributeBL_AttributesMap_putOrMerge_Tests} for how it shall behave.
		 *
		 * @param ppOrderAttribute
		 */
		@SuppressWarnings("JavadocReference")
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
			final AttributeId attributeId = AttributeId.ofRepoId(ppOrderAttribute.getM_Attribute_ID());
			final String valueString = ppOrderAttribute.getValue();
			final BigDecimal valueNumber = getValueNumberOrNull(ppOrderAttribute);

			final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
			final de.metas.handlingunits.model.I_M_Attribute attribute = attributesRepo.getAttributeById(attributeId, de.metas.handlingunits.model.I_M_Attribute.class);

			return AttributeWithValue.newInstance(attribute, valueString, valueNumber);
		}

	}

	@VisibleForTesting
	@Value
	static final class AttributeWithValue
	{
		private static AttributeWithValue newInstance(final de.metas.handlingunits.model.I_M_Attribute attribute, final String valueString, final BigDecimal valueNumber)
		{
			final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
			final boolean transferWhenNull = attribute.isTransferWhenNull();
			final boolean stickWithNullValue = false;
			return new AttributeWithValue(attributeId, transferWhenNull, stickWithNullValue, valueString, valueNumber);
		}

		private final AttributeId attributeId;
		@Getter(AccessLevel.PRIVATE)
		private final boolean transferWhenNull; // task #810

		private final String valueString;
		private final BigDecimal valueNumber;

		/**
		 * true if {@link #isNullValue()} and this shall be preserved while merging attributes (usually because there was some error in incompatibility in attribute values)
		 */
		@Getter(AccessLevel.PRIVATE)
		private final boolean stickWithNullValue;

		private AttributeWithValue(
				final AttributeId attributeId,
				final boolean transferWhenNull,
				final boolean stickWithNullValue,
				final String valueString,
				final BigDecimal valueNumber)
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

		/**
		 * @return true if value(string) and valueNumber are null
		 */
		private boolean isNullValue()
		{
			return valueNumber == null && valueString == null;
		}

		/**
		 * @return true if this attribute and <code>other</code> have the values equal
		 */
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
		 * @return <ul>
		 * <li>a new {@link AttributeWithValue} instance containing current values, merged with given <code>ppOrderAttribute</code> values
		 * <li>this if there is no change
		 * </ul>
		 */
		private AttributeWithValue combineToNew(final AttributeWithValue from)
		{
			// Make sure the "from" is compatible with this attribute (shall not happen)
			Check.assumeEquals(getAttributeId(), from.getAttributeId(), "attributeId shall match: this={}, from={}", this, from);
			Check.assumeEquals(isTransferWhenNull(), from.isTransferWhenNull(), "IsTransferWhenNull flag shall match: this={}, from={}", this, from);

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
			final List<I_M_HU_Attribute> huAttributes = huAttributesDAO.retrieveAttributesOrdered(hu).getHuAttributes();

			ppOrderProductAttributeDAO.addPPOrderProductAttributes(costCollector, huAttributes);
		}
	}

	@Override
	public void addPPOrderProductAttributesFromIssueCandidate(final I_PP_Order_Qty issueCandidate)
	{
		final I_M_HU topLevelHU = issueCandidate.getM_HU();

		final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);
		final IPPOrderProductAttributeDAO ppOrderProductAttributeDAO = Services.get(IPPOrderProductAttributeDAO.class);
		final List<I_M_HU_Attribute> huAttributes = huAttributesDAO.retrieveAttributesOrdered(topLevelHU).getHuAttributes();

		ppOrderProductAttributeDAO.addPPOrderProductAttributesFromIssueCandidate(issueCandidate, huAttributes);
	}

}
