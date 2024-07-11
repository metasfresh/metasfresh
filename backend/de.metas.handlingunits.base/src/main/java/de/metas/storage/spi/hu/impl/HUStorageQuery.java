package de.metas.storage.spi.hu.impl;

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

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.age.AgeAttributesService;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.storage.IStorageQuery;
import de.metas.storage.IStorageRecord;
import de.metas.storage.spi.hu.IHUStorageBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A HU-based IStorageQuery implementation.
 * <p>
 * <b>IMPORTANT</b> this implementation will ignore HUs that are located in a <code>M_Locator</code> with {@link I_M_Locator#COLUMNNAME_IsAfterPickingLocator IsAfterPickingLocator} <code>='Y'</code>,
 * because HUs on such a locator are actually bound to be shipped in the very nearest future and are considered to be not "there" for normal storage stuff anymore.
 */
@EqualsAndHashCode
@ToString
public class HUStorageQuery implements IStorageQuery
{
	public static HUStorageQuery cast(final IStorageQuery storageQuery)
	{
		Check.assumeInstanceOf(storageQuery, HUStorageQuery.class, "storageQuery");
		return (HUStorageQuery)storageQuery;
	}

	private final IHUQueryBuilder huQueryBuilder;
	private transient ImmutableSet<AttributeId> _availableAttributeIds;
	private final Set<ProductId> _productIds = new HashSet<>();

	/* package */ HUStorageQuery()
	{
		// services
		IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		huQueryBuilder = handlingUnitsDAO.createHUQueryBuilder();

		//
		// BPartner: we will accept without BP or with the BPs which is specified in query
		huQueryBuilder.setOnlyIfAssignedToBPartner(false);

		// consider only VHUs
		huQueryBuilder.setOnlyTopLevelHUs(false);
		huQueryBuilder.addPIVersionToInclude(HuPackingInstructionsVersionId.VIRTUAL);

		// consider only those HUs which are supposed to be considered for (e.g. not "shipped")
		final List<String> huStatusesQtyOnHand = Services.get(IHUStatusBL.class).getQtyOnHandStatuses();
		for (final String huStatus : huStatusesQtyOnHand)
		{
			huQueryBuilder.addHUStatusToInclude(huStatus);
		}

		// by default, consider only HUs which are *not* in an after-picking locator (08123)
		huQueryBuilder.setExcludeAfterPickingLocator(true);
	}

	public final IHUQueryBuilder createHUQueryBuilder()
	{
		return huQueryBuilder.copy();
	}

	public final IQueryBuilder<I_M_HU_Storage> createQueryBuilder_for_M_HU_Storages(final IContextAware context)
	{
		final IQueryBuilder<I_M_HU_Storage> huStorageQueryBuilder = huQueryBuilder.copy()
				.setContext(context)
				.createQueryBuilder()
				.andCollectChildren(I_M_HU_Storage.COLUMN_M_HU_ID, I_M_HU_Storage.class);

		huStorageQueryBuilder.addNotEqualsFilter(I_M_HU_Storage.COLUMN_Qty, BigDecimal.ZERO);

		// Filter by in scope M_Product_IDs
		if (!_productIds.isEmpty())
		{
			huStorageQueryBuilder.addInArrayOrAllFilter(I_M_HU_Storage.COLUMNNAME_M_Product_ID, _productIds);
		}

		huStorageQueryBuilder.orderBy()
				.addColumn(I_M_HU_Storage.COLUMNNAME_M_HU_ID);

		return huStorageQueryBuilder;
	}

	@Override
	public boolean matches(final IStorageRecord storageRecord)
	{
		if (storageRecord == null)
		{
			return false;
		}

		//
		// Check if Product matches
		final Set<ProductId> queryProductIds = getProductIds();
		if (!queryProductIds.isEmpty())
		{
			final ProductId recordProductId = storageRecord.getProductId();
			if (!queryProductIds.contains(recordProductId))
			{
				return false;
			}
		}

		//
		// Make sure we are not dealing with an After-Picking locator (08123)
		final I_M_Locator recordLocator = InterfaceWrapperHelper.create(storageRecord.getLocator(), I_M_Locator.class);
		if (recordLocator.isAfterPickingLocator())
		{
			return false;
		}

		//
		// Check if Warehouse/Locator matches
		final Set<Integer> queryWarehouseIds = getWarehouseIds();
		if (!queryWarehouseIds.isEmpty())
		{
			final int recordWarehouseId = recordLocator.getM_Warehouse_ID();
			if (!queryWarehouseIds.contains(recordWarehouseId))
			{
				return false;
			}
		}

		//
		// Check if BPartner matches
		final Set<BPartnerId> queryBPartnerIds = getBPartnerIds();
		if (!queryBPartnerIds.isEmpty())
		{
			final I_C_BPartner bpartner = storageRecord.getC_BPartner();
			Integer bpartnerRepoId = bpartner == null ? null : bpartner.getC_BPartner_ID();
			bpartnerRepoId = bpartnerRepoId != null && bpartnerRepoId <= 0 ? null : bpartnerRepoId; // make sure if is <=0 then to use NULL which means ANY
			final BPartnerId bPartnerId = BPartnerId.ofRepoIdOrNull(bpartnerRepoId);
			if (!queryBPartnerIds.contains(bPartnerId))
			{
				return false;
			}
		}

		//
		// Check if required attributes are matching
		final IAttributeSet recordAttributes = storageRecord.getAttributes();
		return huQueryBuilder.matches(recordAttributes);
	}

	@Override
	public IStorageQuery addProductId(@NonNull final ProductId productId)
	{
		_productIds.add(productId);
		huQueryBuilder.addOnlyWithProductId(productId);
		return this;
	}

	@NonNull
	private Set<ProductId> getProductIds()
	{
		return _productIds;
	}

	@Override
	public IStorageQuery addBPartnerId(@Nullable final BPartnerId bpartnerId)
	{
		huQueryBuilder.addOnlyInBPartnerId(bpartnerId);
		return this;
	}

	@NonNull
	private Set<BPartnerId> getBPartnerIds()
	{
		return huQueryBuilder.getOnlyInBPartnerIds();
	}

	@Override
	public IStorageQuery addWarehouseId(@NonNull final WarehouseId warehouseId)
	{
		huQueryBuilder.addOnlyInWarehouseId(warehouseId);
		return this;
	}

	@Override
	public IStorageQuery addWarehouseIds(@NonNull final Collection<WarehouseId> warehouseIds)
	{
		huQueryBuilder.addOnlyInWarehouseIds(warehouseIds);
		return this;
	}

	private Set<Integer> getWarehouseIds()
	{
		return huQueryBuilder
				.getOnlyInWarehouseIds()
				.stream()
				.map(WarehouseId::getRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Adds a filter for the given attribute, <b>if</b> it is relevant according to {@link IHUStorageBL#getAvailableAttributeIds()}.
	 */
	@Override
	public IStorageQuery addAttribute(
			@NonNull final I_M_Attribute attribute,
			final String attributeValueType,
			@Nullable final Object attributeValue)
	{
		final AgeAttributesService ageAttributesService = SpringContextHolder.instance.getBean(AgeAttributesService.class);

		// Skip null values because in this case user filled nothing => so we accept any value
		if (attributeValue == null)
		{
			return this;
		}

		//
		// Make sure given attribute available to be used by our HU Storage implementations.
		// If we filtered by other attributes we would get NO result.
		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
		final Set<AttributeId> availableAttributeIds = getAvailableAttributeIds();
		if (!availableAttributeIds.contains(attributeId))
		{
			return this;
		}

		//
		// Add attribute query restrictions
		if (HUAttributeConstants.ATTR_Age.equals(AttributeCode.ofString(attribute.getValue())))
		{
			// "explode" the age-attribute value to a range, using before- and after-intervals from the masterdata
			final List<Object> ageValues = ageAttributesService.extractMatchingValues(getBPartnerIds(), getProductIds(), attributeValue);

			huQueryBuilder.addOnlyWithAttributeInList(attribute, attributeValueType, ageValues);
		}
		else
		{
			final List<Object> attributeValues = Collections.singletonList(attributeValue);

			huQueryBuilder.addOnlyWithAttributeInList(attribute, attributeValueType, attributeValues);
		}

		return this;
	}

	@Override
	public IStorageQuery addAttributes(@NonNull final IAttributeSet attributeSet)
	{
		for (final I_M_Attribute attribute : attributeSet.getAttributes())
		{
			// Skip attributes which were newly generated just to have an complete attribute set,
			// because it makes no sense to filter by those and because it could be that we will get no result.
			// Case: ASIAttributeStorge is generating new AIs for those attributes which are defined in M_HU_PI_Attribute but which were missing in given ASI.
			// If we would filter by those too, it's a big chance that we would get no result.
			if (attributeSet.isNew(attribute))
			{
				continue;
			}

			final Object value = attributeSet.getValue(attribute);

			final String attributeValueType = attributeSet.getAttributeValueType(attribute);
			addAttribute(attribute, attributeValueType, value);
		}

		return this;
	}

	private Set<AttributeId> getAvailableAttributeIds()
	{
		if (_availableAttributeIds == null)
		{
			final IHUStorageBL huStorageBL = Services.get(IHUStorageBL.class);
			_availableAttributeIds = ImmutableSet.copyOf(huStorageBL.getAvailableAttributeIds());
		}
		return _availableAttributeIds;
	}

	@Override
	public IStorageQuery setExcludeAfterPickingLocator(final boolean excludeAfterPickingLocator)
	{
		huQueryBuilder.setExcludeAfterPickingLocator(excludeAfterPickingLocator);
		return this;
	}

	@Override
	public IStorageQuery setExcludeReservedToOtherThan(@NonNull final OrderLineId orderLineId)
	{
		huQueryBuilder.setExcludeReservedToOtherThan(HUReservationDocRef.ofSalesOrderLineId(orderLineId));
		return this;
	}

	@Override
	public IStorageQuery setExcludeReserved()
	{
		huQueryBuilder.setExcludeReserved();
		return this;
	}

	@Override
	public IStorageQuery setOnlyActiveHUs(final boolean onlyActiveHUs)
	{
		huQueryBuilder.setOnlyActiveHUs(onlyActiveHUs);
		return this;
	}
}
