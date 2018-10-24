package de.metas.handlingunits.impl;

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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.NotQueryFilter;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.ModelColumn;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Attribute;

import de.metas.dimension.DimensionSpec;
import de.metas.dimension.IDimensionspecDAO;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHULockBL;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Reservation;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.picking.IHUPickingSlotDAO;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.order.OrderLineId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * {@link IHUQueryBuilder} implementation.
 *
 * NOTE to developer: if you want to add a new filtering parameter, please make sure you are handling the parameters in following places:
 * <ul>
 * <li>{@link #createQueryFilter()} - creates the actual {@link I_M_HU} filter to be used. Here you will add your filters based on your new parameter value.
 * <li>{@link #copy()} - make sure when copy is invoked, your new parameter is copied
 * <li>{@link #hashCode()}, {@link #equals(Object)} - make sure your new parameter is checked
 * </ul>
 *
 * @author tsa
 *
 */
/* package */class HUQueryBuilder implements IHUQueryBuilder
{

	//
	// Services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IHULockBL huLockBL = Services.get(IHULockBL.class);
	private final transient IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);
	private final transient HUReservationRepository huReservationRepository;

	@ToStringBuilder(skip = true)
	private Object _contextProvider;

	private boolean onlyContextClient = true;

	/**
	 * Shall we select only those HUs which are top level (i.e. not included in other HUs)?
	 *
	 * Default: true because in most of the cases we are looking for top level HUs
	 */
	private boolean huItemParentNull = true;
	private int parentHUItemId = -1;
	private int parentHUId = -1;

	private final Set<WarehouseId> _onlyInWarehouseIds = new HashSet<>();
	private boolean _notInAnyWarehouse = false;

	@ToStringBuilder(skip = true)
	private final Set<WarehouseId> _onlyInWarehouseIdsRO = Collections.unmodifiableSet(_onlyInWarehouseIds);

	private final Set<Integer> _onlyInLocatorIds = new HashSet<>();
	private boolean _excludeAfterPickingLocator = false;
	/**
	 * Flag to set determine if the query shall only retrieve the HUs from AfterPicking locators or not
	 *
	 * Introduced in 08544
	 */
	private boolean _includeAfterPickingLocator = false;

	private boolean onlyIfAssignedToBPartner = false;
	private final Set<Integer> _onlyInBpartnerIds = new HashSet<>();
	@ToStringBuilder(skip = true)
	private final Set<Integer> _onlyInBpartnerIdsRO = Collections.unmodifiableSet(_onlyInBpartnerIds);
	private final Set<Integer> _onlyWithBPartnerLocationIds = new HashSet<>();
	private final Set<Integer> _onlyWithProductIds = new HashSet<>();

	private Boolean _emptyStorageOnly = null;

	private boolean _allowEmptyStorage = false;

	/** M_Attribute_ID to {@link HUAttributeQueryFilterVO} */
	private final Map<Integer, HUAttributeQueryFilterVO> onlyAttributeId2values = new HashMap<>();

	/** M_Attribute_ID to {@link HUAttributeQueryFilterVO} for barcode */
	private final Map<Integer, HUAttributeQueryFilterVO> _barcodeAttributesIds2Value = new HashMap<>();

	private final Set<String> _huStatusesToInclude = new HashSet<>();
	private final Set<String> _huStatusesToExclude = new HashSet<>();
	private boolean onlyActiveHUs = true;

	/**
	 * {@code null} means "no restriction". Empty means that no HU matches.
	 */
	private Set<Integer> _onlyHUIds = null;

	private final Set<Integer> _huIdsToExclude = new HashSet<>();
	private final Set<HuPackingInstructionsVersionId> _huPIVersionIdsToInclude = new HashSet<>();
	private boolean _excludeHUsOnPickingSlot = false;

	private OrderLineId _excludeReservedToOtherThanOrderLineId = null;
	private boolean _excludeReserved = false;

	private IQuery<I_M_HU> huSubQueryFilter = null;

	/**
	 * Other Filters to be applied
	 */
	private ICompositeQueryFilter<I_M_HU> otherFilters = null;

	/**
	 * Internal barcode
	 */
	private String barcode = null;

	private Boolean locked = null;

	//
	//
	// Data retrieval options
	private boolean _errorIfNoHUs = false;
	private String _errorIfNoHUs_ADMessage = null;

	public HUQueryBuilder(@NonNull final HUReservationRepository huReservationRepository)
	{
		this.huReservationRepository = huReservationRepository;
	}

	private HUQueryBuilder(final HUQueryBuilder from)
	{
		this.huReservationRepository = from.huReservationRepository;
		this._contextProvider = from._contextProvider;
		this.huItemParentNull = from.huItemParentNull;
		this.parentHUItemId = from.parentHUItemId;
		this.parentHUId = from.parentHUId;
		this._onlyInWarehouseIds.addAll(from._onlyInWarehouseIds);
		this._notInAnyWarehouse = from._notInAnyWarehouse;
		this._onlyInLocatorIds.addAll(from._onlyInLocatorIds);
		this._excludeAfterPickingLocator = from._excludeAfterPickingLocator;
		this._includeAfterPickingLocator = from._includeAfterPickingLocator;
		this.onlyIfAssignedToBPartner = from.onlyIfAssignedToBPartner;

		this._onlyInBpartnerIds.addAll(from._onlyInBpartnerIds);
		this._onlyWithBPartnerLocationIds.addAll(from._onlyWithBPartnerLocationIds);
		this._onlyWithProductIds.addAll(from._onlyWithProductIds);
		this._emptyStorageOnly = from._emptyStorageOnly;

		for (final Map.Entry<Integer, HUAttributeQueryFilterVO> e : from.onlyAttributeId2values.entrySet())
		{
			final Integer attributeId = e.getKey();
			final HUAttributeQueryFilterVO attributeFilterVO = e.getValue();
			final HUAttributeQueryFilterVO attributeFilterVOCopy = attributeFilterVO == null ? null : attributeFilterVO.copy();
			this.onlyAttributeId2values.put(attributeId, attributeFilterVOCopy);
		}

		// task 827
		// copy barcode attributes
		for (final Map.Entry<Integer, HUAttributeQueryFilterVO> e : from._barcodeAttributesIds2Value.entrySet())
		{
			final Integer attributeId = e.getKey();
			final HUAttributeQueryFilterVO attributeFilterVO = e.getValue();
			final HUAttributeQueryFilterVO attributeFilterVOCopy = attributeFilterVO == null ? null : attributeFilterVO.copy();
			this._barcodeAttributesIds2Value.put(attributeId, attributeFilterVOCopy);
		}

		this._huStatusesToInclude.addAll(from._huStatusesToInclude);
		this._huStatusesToExclude.addAll(from._huStatusesToExclude);
		this.onlyActiveHUs = from.onlyActiveHUs;

		this._onlyHUIds = from._onlyHUIds == null ? null : new HashSet<>(from._onlyHUIds);

		this._huIdsToExclude.addAll(from._huIdsToExclude);
		this._huPIVersionIdsToInclude.addAll(from._huPIVersionIdsToInclude);
		this._excludeHUsOnPickingSlot = from._excludeHUsOnPickingSlot;
		this._excludeReservedToOtherThanOrderLineId = from._excludeReservedToOtherThanOrderLineId;
		this._excludeReserved = from._excludeReserved;

		this.huSubQueryFilter = from.huSubQueryFilter == null ? null : from.huSubQueryFilter.copy();

		this.otherFilters = from.otherFilters == null ? null : from.otherFilters.copy();

		this.barcode = from.barcode;
		this.locked = from.locked;

		this._errorIfNoHUs = from._errorIfNoHUs;
		this._errorIfNoHUs_ADMessage = from._errorIfNoHUs_ADMessage;
	}

	@Override
	public HUQueryBuilder copy()
	{
		return new HUQueryBuilder(this);
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				// .append(_contextProvider) // nop
				.append(onlyContextClient)
				.append(huItemParentNull)
				.append(parentHUItemId)
				.append(parentHUId)
				.append(_onlyInWarehouseIds)
				.append(_notInAnyWarehouse)
				.append(_onlyInLocatorIds)
				.append(_excludeAfterPickingLocator)
				.append(_includeAfterPickingLocator)
				.append(onlyIfAssignedToBPartner)
				.append(_onlyInBpartnerIds)
				.append(_onlyWithBPartnerLocationIds)
				.append(_onlyWithProductIds)
				.append(_emptyStorageOnly)
				.append(onlyAttributeId2values)
				.append(_barcodeAttributesIds2Value)
				.append(_huStatusesToInclude)
				.append(_huStatusesToExclude)
				.append(onlyActiveHUs)
				.append(_onlyHUIds)
				.append(_huIdsToExclude)
				.append(_huPIVersionIdsToInclude)
				.append(_excludeHUsOnPickingSlot)
				.append(_excludeReservedToOtherThanOrderLineId)
				.append(_excludeReserved)
				.append(otherFilters)
				.append(huSubQueryFilter)
				.append(barcode)
				.append(locked)
				.append(_errorIfNoHUs)
				.append(_errorIfNoHUs_ADMessage)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object otherObj)
	{
		if (this == otherObj)
		{
			return true;
		}
		final HUQueryBuilder other = EqualsBuilder.getOther(this, otherObj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				// .append(_contextProvider) // nop
				.append(onlyContextClient, other.onlyContextClient)
				.append(huItemParentNull, other.huItemParentNull)
				.append(parentHUItemId, other.parentHUItemId)
				.append(parentHUId, other.parentHUId)
				.append(_onlyInWarehouseIds, other._onlyInWarehouseIds)
				.append(_notInAnyWarehouse, other._notInAnyWarehouse)
				.append(_onlyInLocatorIds, other._onlyInLocatorIds)
				.append(_excludeAfterPickingLocator, other._excludeAfterPickingLocator)
				.append(onlyIfAssignedToBPartner, other.onlyIfAssignedToBPartner)
				.append(_onlyInBpartnerIds, other._onlyInBpartnerIds)
				.append(_onlyWithBPartnerLocationIds, other._onlyWithBPartnerLocationIds)
				.append(_onlyWithProductIds, other._onlyWithProductIds)
				.append(_emptyStorageOnly, other._emptyStorageOnly)
				.append(onlyAttributeId2values, other.onlyAttributeId2values)
				.append(_barcodeAttributesIds2Value, other._barcodeAttributesIds2Value)
				.append(_huStatusesToInclude, other._huStatusesToInclude)
				.append(_huStatusesToExclude, other._huStatusesToExclude)
				.append(onlyActiveHUs, other.onlyActiveHUs)
				.append(_onlyHUIds, other._onlyHUIds)
				.append(_huIdsToExclude, other._huIdsToExclude)
				.append(_huPIVersionIdsToInclude, other._huPIVersionIdsToInclude)
				.append(_excludeHUsOnPickingSlot, other._excludeHUsOnPickingSlot)
				.append(_excludeReservedToOtherThanOrderLineId, other._excludeReservedToOtherThanOrderLineId)
				.append(_excludeReserved, other._excludeReserved)
				.append(otherFilters, other.otherFilters)
				.append(huSubQueryFilter, other.huSubQueryFilter)
				.append(barcode, other.barcode)
				.append(locked, other.locked)
				.append(_errorIfNoHUs, other._errorIfNoHUs)
				.append(_errorIfNoHUs_ADMessage, other._errorIfNoHUs_ADMessage)
				.isEqual();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getAttributesSummary()
	{
		if (onlyAttributeId2values == null || onlyAttributeId2values.isEmpty())
		{
			return "";
		}

		final StringBuilder sb = new StringBuilder();
		for (final HUAttributeQueryFilterVO attributeFilterVO : onlyAttributeId2values.values())
		{
			final String attributeSummary = attributeFilterVO.getSummary();
			if (Check.isEmpty(attributeSummary, true))
			{
				continue;
			}
			if (sb.length() > 0)
			{
				sb.append("\n");
			}
			sb.append(attributeSummary);
		}
		return sb.toString();
	}

	@Override
	public IQueryBuilder<I_M_HU> createQueryBuilder()
	{
		//
		// get Query context
		final Object contextProvider = getContextProvider();
		final IQueryBuilder<I_M_HU> queryBuilder = queryBL.createQueryBuilder(I_M_HU.class, contextProvider);

		// Only those HUs which are from our AD_Client
		if (onlyContextClient)
		{
			queryBuilder.addOnlyContextClient();
		}

		//
		// Create and add Query Filters
		final ICompositeQueryFilter<I_M_HU> filters = createQueryFilter();
		queryBuilder.addFiltersUnboxed(filters);

		//
		// ORDER BY
		queryBuilder.orderBy()
				.addColumn(I_M_HU.COLUMN_M_HU_ID); // just to have a predictable order

		return queryBuilder;
	}

	@Override
	public final ICompositeQueryFilter<I_M_HU> createQueryFilter()
	{
		final ICompositeQueryFilter<I_M_HU> filters = queryBL.createCompositeQueryFilter(I_M_HU.class);

		//
		// Only Active HUs
		if (onlyActiveHUs)
		{
			filters.addOnlyActiveRecordsFilter();
		}

		//
		// Filter by Warehouses
		final Set<WarehouseId> onlyInWarehouseIds = getOnlyInWarehouseIds();
		if (!onlyInWarehouseIds.isEmpty()
				|| _excludeAfterPickingLocator
				|| _includeAfterPickingLocator)
		{
			final IQueryBuilder<I_M_Locator> locatorsQueryBuilder = queryBL
					.createQueryBuilder(I_M_Locator.class, getContextProvider());

			if (!onlyInWarehouseIds.isEmpty())
			{
				locatorsQueryBuilder.addInArrayOrAllFilter(I_M_Locator.COLUMN_M_Warehouse_ID, onlyInWarehouseIds);
			}
			// Make sure _includeAfterPickingLocator and _excludeAfterPickingLocator are not both selected
			Check.assume(!(_includeAfterPickingLocator && _excludeAfterPickingLocator), "Cannot both include and exclude AfterPickingLocator");

			if (_excludeAfterPickingLocator)
			{
				locatorsQueryBuilder.addEqualsFilter(de.metas.handlingunits.model.I_M_Locator.COLUMNNAME_IsAfterPickingLocator, false);
			}
			if (_includeAfterPickingLocator)
			{
				locatorsQueryBuilder.addEqualsFilter(de.metas.handlingunits.model.I_M_Locator.COLUMNNAME_IsAfterPickingLocator, true);
			}

			final IQuery<I_M_Locator> locatorsQuery = locatorsQueryBuilder.create();
			filters.addInSubQueryFilter(I_M_HU.COLUMN_M_Locator_ID,
					I_M_Locator.COLUMN_M_Locator_ID, locatorsQuery);
		}

		if (_notInAnyWarehouse)
		{
			filters.addEqualsFilter(I_M_HU.COLUMN_M_Locator_ID, null);
		}

		//
		// Filter by Locators
		final Set<Integer> onlyInLocatorIds = getOnlyInLocatorIds();
		if (!onlyInLocatorIds.isEmpty())
		{
			filters.addInArrayFilter(I_M_HU.COLUMN_M_Locator_ID, onlyInLocatorIds);
		}

		//
		// Enforce M_HU.C_BPartner_ID to be set
		if (onlyIfAssignedToBPartner)
		{
			filters.addNotEqualsFilter(I_M_HU.COLUMN_C_BPartner_ID, null);
		}
		//
		// Filter by C_BPartner_ID
		final Set<Integer> onlyWithBPartnerIds = getOnlyInBPartnerIds();
		if (!onlyWithBPartnerIds.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_M_HU.COLUMN_C_BPartner_ID, onlyWithBPartnerIds);
		}

		// Filter by C_BPartner_Location_ID
		if (!_onlyWithBPartnerLocationIds.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_M_HU.COLUMN_C_BPartner_Location_ID, _onlyWithBPartnerLocationIds);
		}

		//
		// Filter only those HUs which contains our products restriction
		final Set<Integer> onlyWithProductIds = getOnlyWithProductIds();
		if (!onlyWithProductIds.isEmpty())
		{
			final IQueryBuilder<I_M_HU_Storage> huStoragesQueryBuilder = queryBL.createQueryBuilder(I_M_HU_Storage.class, getContextProvider())
					.addOnlyActiveRecordsFilter()
					.addInArrayOrAllFilter(I_M_HU_Storage.COLUMN_M_Product_ID, onlyWithProductIds);

			if (!_allowEmptyStorage)
			{
				huStoragesQueryBuilder.addNotEqualsFilter(I_M_HU_Storage.COLUMN_Qty, BigDecimal.ZERO);
			}

			final IQuery<I_M_HU_Storage> huStoragesQuery = huStoragesQueryBuilder.create();

			filters.addInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID,
					I_M_HU_Storage.COLUMN_M_HU_ID, huStoragesQuery);
		}

		//
		// Empty storage filter
		if (_emptyStorageOnly != null)
		{
			final IQuery<I_M_HU_Storage> notEmptyHUStoragesQuery = queryBL.createQueryBuilder(I_M_HU_Storage.class, getContextProvider())
					.addNotEqualsFilter(I_M_HU_Storage.COLUMN_Qty, BigDecimal.ZERO)
					.addOnlyActiveRecordsFilter()
					.create();

			// Empty Storage Only
			if (_emptyStorageOnly)
			{
				// FIXME: not sure it's ok!!!
				// We must rewrite (Not)InSubQueryFilter using EXISTS

				// return only entries with empty storages
				filters.addNotInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID, I_M_HU_Storage.COLUMN_M_HU_ID, notEmptyHUStoragesQuery);
			}
			// Not Empty Storage Only
			else
			{
				// entries with empty storages are excluded
				filters.addInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID, I_M_HU_Storage.COLUMN_M_HU_ID, notEmptyHUStoragesQuery);
			}
		}

		//
		// Filter Top Level HUs / Included HUs
		if (huItemParentNull)
		{
			filters.addEqualsFilter(I_M_HU.COLUMN_M_HU_Item_Parent_ID, null);
		}
		else
		{
			if (parentHUItemId > 0)
			{
				filters.addEqualsFilter(I_M_HU.COLUMN_M_HU_Item_Parent_ID, parentHUItemId);
			}
			if (parentHUId > 0)
			{
				final IQuery<I_M_HU_Item> parentHUItemQuery = queryBL.createQueryBuilder(I_M_HU_Item.class, getContextProvider())
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_M_HU_Item.COLUMNNAME_M_HU_ID, parentHUId)
						.create();
				filters.addInSubQueryFilter(I_M_HU.COLUMN_M_HU_Item_Parent_ID,
						I_M_HU_Item.COLUMN_M_HU_Item_ID,
						parentHUItemQuery);
			}
		}

		//
		// Filter by HU Status:
		// include
		final Set<String> huStatusesToInclude = getHUStatusesToInclude();
		if (huStatusesToInclude != null && !huStatusesToInclude.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_M_HU.COLUMN_HUStatus, huStatusesToInclude);
		}
		// exclude
		final Set<String> huStatusesToExclude = getHUStatusesToExclude();
		if (huStatusesToExclude != null && !huStatusesToExclude.isEmpty())
		{
			filters.addNotInArrayFilter(I_M_HU.COLUMN_HUStatus, huStatusesToExclude);
		}

		//
		// Filter by Attributes
		if (!onlyAttributeId2values.isEmpty())
		{
			// Iterate attribute filters and add a restriction for each of them
			// because each of them needs to be individually valid
			for (final HUAttributeQueryFilterVO attributeFilterVO : onlyAttributeId2values.values())
			{
				attributeFilterVO.appendQueryFilterTo(getContextProvider(), filters);
			}
		}

		//
		// Filter by internal barcode
		if (!Check.isEmpty(barcode, true))
		{
			if (!_barcodeAttributesIds2Value.isEmpty())
			{
				final ICompositeQueryFilter<I_M_HU> barcodeFilter = queryBL.createCompositeQueryFilter(I_M_HU.class);

				// an HU will be barcode-identified either if it has barcode attributes or value with the value inserted as barcode
				barcodeFilter.setJoinOr();
				barcodeFilter.addEqualsFilter(I_M_HU.COLUMN_Value, barcode.trim());

				for (final HUAttributeQueryFilterVO attributeFilterVO : _barcodeAttributesIds2Value.values())
				{
					attributeFilterVO.appendQueryFilterTo(getContextProvider(), barcodeFilter);
				}

				filters.addFilter(barcodeFilter);
			}
			// task #827 filter by hu value, as before
			else
			{
				filters.addEqualsFilter(I_M_HU.COLUMN_Value, barcode.trim());
			}

		}

		//
		// Include only specific HUs
		if (_onlyHUIds != null)
		{
			filters.addInArrayFilter(I_M_HU.COLUMN_M_HU_ID, _onlyHUIds);
		}

		//
		// Exclude specified HUs
		if (_huIdsToExclude != null && !_huIdsToExclude.isEmpty())
		{
			filters.addNotInArrayFilter(I_M_HU.COLUMN_M_HU_ID, _huIdsToExclude);
		}

		//
		// Include specified HU PI Versions
		final Set<HuPackingInstructionsVersionId> huPIVersionIdsToInclude = getPIVersionIdsToInclude();
		if (!huPIVersionIdsToInclude.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_M_HU.COLUMN_M_HU_PI_Version_ID, huPIVersionIdsToInclude);
		}

		//
		// Filter locked option
		final Boolean locked = this.locked;
		if (locked != null)
		{
			// only locked
			if (locked)
			{
				filters.addFilter(huLockBL.isLockedFilter());
			}
			// only not locked
			else
			{
				filters.addFilter(huLockBL.isNotLockedFilter());
			}
		}

		//
		// Apply other filters
		if (otherFilters != null && !otherFilters.isEmpty())
		{
			filters.addFilter(otherFilters);
		}

		//
		// Apply in sub-query filter
		if (huSubQueryFilter != null)
		{
			filters.addInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID, I_M_HU.COLUMN_M_HU_ID, huSubQueryFilter);
		}

		//
		// Exclude those HUs which are currently on a picking slot
		if (_excludeHUsOnPickingSlot)
		{
			final IQueryFilter<I_M_HU> husOnPickingSlotFilter = huPickingSlotDAO.createHUOnPickingSlotQueryFilter(getContextProvider());
			final IQueryFilter<I_M_HU> husNotOnPickingSlotFilter = new NotQueryFilter<>(husOnPickingSlotFilter);
			filters.addFilter(husNotOnPickingSlotFilter);
		}

		if (_excludeReservedToOtherThanOrderLineId != null)
		{
			final IQuery<I_M_HU_Reservation> //
			excludeSubQuery = huReservationRepository.createQueryReservedToOtherThan(_excludeReservedToOtherThanOrderLineId);

			final ICompositeQueryFilter<I_M_HU> //
			notReservedToOtherOrderLineFilter = queryBL
					.createCompositeQueryFilter(I_M_HU.class)
					.setJoinOr()
					.addEqualsFilter(I_M_HU.COLUMN_IsReserved, false)
					.addNotInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID, I_M_HU_Reservation.COLUMN_VHU_ID, excludeSubQuery);

			filters.addFilter(notReservedToOtherOrderLineFilter);
		}
		else if (_excludeReserved)
		{
			filters.addEqualsFilter(I_M_HU.COLUMN_IsReserved, false);
		}

		return filters;
	}

	@Override
	public IQuery<I_M_HU> createQuery()
	{
		return createQueryBuilder()
				.create();
	}

	@Override
	public Set<HuId> listIds()
	{
		final IQuery<I_M_HU> query = createQuery();
		return query.listIds(HuId::ofRepoId);
	}

	@Override
	public List<I_M_HU> list()
	{
		return list(IQuery.NO_LIMIT);
	}

	@Override
	public List<I_M_HU> list(final int limit)
	{
		final IQuery<I_M_HU> query = createQuery();
		final List<I_M_HU> hus = query
				.setLimit(limit)
				.list();

		if (hus.isEmpty())
		{
			throwErrorNoHUsFoundIfNeeded(query);
		}

		return hus;
	}

	@Override
	public I_M_HU firstOnly()
	{
		final IQuery<I_M_HU> query = createQuery();
		final I_M_HU hu = query
				.firstOnly(I_M_HU.class);

		if (hu == null)
		{
			throwErrorNoHUsFoundIfNeeded(query);
		}

		return hu;
	}

	@Override
	public I_M_HU first()
	{
		final IQuery<I_M_HU> query = createQuery();
		final I_M_HU hu = query
				.first(I_M_HU.class);

		if (hu == null)
		{
			throwErrorNoHUsFoundIfNeeded(query);
		}

		return hu;
	}

	@Override
	public int count()
	{
		return createQuery()
				.count();
	}

	@Override
	public <T> List<T> collect(final ModelColumn<I_M_HU, T> huColumn)
	{
		return createQueryBuilder()
				.andCollect(huColumn)
				.create()
				.list();
	}

	@Override
	public IHUQueryBuilder setContext(final Properties ctx, final String trxName)
	{
		final PlainContextAware contextProvider = PlainContextAware.newWithTrxName(ctx, trxName);
		return setContext(contextProvider);
	}

	@Override
	public IHUQueryBuilder setContext(final Object contextProvider)
	{
		_contextProvider = contextProvider;
		return this;
	}

	private final Object getContextProvider()
	{
		if (_contextProvider == null)
		{
			// context provider is optional; if it was not set, then use this default one
			_contextProvider = PlainContextAware.newWithThreadInheritedTrx();
		}

		Check.assumeNotNull(_contextProvider, "contextProvider not null");
		return _contextProvider;
	}

	@Override
	public IHUQueryBuilder onlyContextClient(final boolean onlyContextClient)
	{
		this.onlyContextClient = onlyContextClient;
		return this;
	}

	@Override
	public IHUQueryBuilder setM_HU_Item_Parent_ID(final int huItemId)
	{
		parentHUItemId = huItemId;
		return this;
	}

	@Override
	public IHUQueryBuilder setM_HU_Parent_ID(final int parentHUId)
	{
		this.parentHUId = parentHUId;
		return this;
	}

	@Override
	public IHUQueryBuilder setOnlyTopLevelHUs()
	{
		return setOnlyTopLevelHUs(true);
	}

	@Override
	public IHUQueryBuilder setOnlyTopLevelHUs(final boolean onlyTopLevelHUs)
	{
		huItemParentNull = onlyTopLevelHUs;
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyInWarehouseIds(final Collection<WarehouseId> warehouseIds)
	{
		if (warehouseIds != null && !warehouseIds.isEmpty())
		{
			_onlyInWarehouseIds.addAll(warehouseIds);
		}

		updateNotInAnyWarehouseFlag();
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyInWarehouseId(final WarehouseId warehouseId)
	{
		_onlyInWarehouseIds.add(warehouseId);
		updateNotInAnyWarehouseFlag();
		return this;
	}

	private void updateNotInAnyWarehouseFlag()
	{
		_notInAnyWarehouse = _onlyInWarehouseIds.isEmpty();
	}

	@Override
	public IHUQueryBuilder addOnlyInWarehouses(final Collection<? extends I_M_Warehouse> warehouses)
	{
		if (warehouses == null || warehouses.isEmpty())
		{
			return addOnlyInWarehouseIds(Collections.<WarehouseId> emptyList());
		}

		final Set<WarehouseId> warehouseIds = new HashSet<>(warehouses.size());
		for (final I_M_Warehouse warehouse : warehouses)
		{
			if (warehouse == null)
			{
				continue;
			}
			final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
			warehouseIds.add(warehouseId);
		}
		return addOnlyInWarehouseIds(warehouseIds);
	}

	@Override
	public Set<WarehouseId> getOnlyInWarehouseIds()
	{
		return _onlyInWarehouseIdsRO;
	}

	@Override
	public HUQueryBuilder addOnlyInLocatorId(final int locatorId)
	{
		Check.assume(locatorId > 0, "locatorId > 0");
		_onlyInLocatorIds.add(locatorId);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyInLocatorIds(final Collection<Integer> locatorIds)
	{
		if (locatorIds != null && !locatorIds.isEmpty())
		{
			_onlyInLocatorIds.addAll(locatorIds);
		}
		return this;
	}

	private Set<Integer> getOnlyInLocatorIds()
	{
		return _onlyInLocatorIds;
	}

	@Override
	public Set<Integer> getOnlyInBPartnerIds()
	{
		return _onlyInBpartnerIdsRO;
	}

	@Override
	public IHUQueryBuilder addOnlyWithProductIds(final Collection<Integer> productIds)
	{
		if (productIds != null && !productIds.isEmpty())
		{
			_onlyWithProductIds.addAll(productIds);
		}
		else
		{
			// Adding this element just to make sure we are filtering by warehouses
			// NOTE: decided to do this instead of throwing an exception here because the provided IDs collection is empty
			_onlyWithProductIds.add(-1);
		}

		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithProductId(final int productId)
	{
		_onlyWithProductIds.add(productId);

		return this;
	}

	public Set<Integer> getOnlyWithProductIds()
	{
		return _onlyWithProductIds;
	}

	@Override
	public IHUQueryBuilder addOnlyWithProduct(final I_M_Product product)
	{
		if (product == null)
		{
			return addOnlyWithProductIds(Collections.<Integer> emptyList());
		}
		final int productId = product.getM_Product_ID();
		return addOnlyWithProductId(productId);
	}

	@Override
	public IHUQueryBuilder setAllowEmptyStorage()
	{
		_allowEmptyStorage = true;
		return this;
	}

	@Override
	public IHUQueryBuilder setEmptyStorageOnly()
	{
		_emptyStorageOnly = true;
		return this;
	}

	@Override
	public IHUQueryBuilder setNotEmptyStorageOnly()
	{
		_emptyStorageOnly = false;
		return this;
	}

	@Override
	public IHUQueryBuilder setOnlyActiveHUs(final boolean onlyActiveHUs)
	{
		this.onlyActiveHUs = onlyActiveHUs;
		return this;
	}

	@Override
	public IHUQueryBuilder setHUStatus(final String huStatus)
	{
		_huStatusesToInclude.clear();
		if (huStatus != null)
		{
			_huStatusesToInclude.add(huStatus);
		}

		return this;
	}

	@Override
	public IHUQueryBuilder addHUStatusToInclude(final String huStatus)
	{
		Check.assumeNotEmpty(huStatus, "huStatus not empty");
		_huStatusesToInclude.add(huStatus);
		return this;
	}

	@Override
	public IHUQueryBuilder addHUStatusesToInclude(@NonNull final Collection<String> huStatuses)
	{
		huStatuses.stream().filter(huStatus -> !Check.isEmpty(huStatus, true)).forEach(this::addHUStatusToInclude);
		return this;
	}

	private Set<String> getHUStatusesToInclude()
	{
		if (_huStatusesToInclude == null)
		{
			return Collections.emptySet();
		}
		return _huStatusesToInclude;
	}

	@Override
	public IHUQueryBuilder addHUStatusToExclude(final String huStatus)
	{
		Check.assumeNotEmpty(huStatus, "huStatus not empty");
		_huStatusesToExclude.add(huStatus);
		return this;
	}

	@Override
	public IHUQueryBuilder addHUStatusesToExclude(final Collection<String> huStatuses)
	{
		huStatuses.stream().filter(huStatus -> !Check.isEmpty(huStatus, true)).forEach(this::addHUStatusToExclude);
		return this;
	}

	private Set<String> getHUStatusesToExclude()
	{
		if (_huStatusesToExclude == null)
		{
			return Collections.emptySet();
		}
		return _huStatusesToExclude;
	}

	@Override
	public IHUQueryBuilder addOnlyInBPartnerId(final Integer bPartnerId)
	{
		// NOTE: we are also accepting bPartnerId=null => for search HUs without a BPartner
		_onlyInBpartnerIds.add(bPartnerId);

		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithBPartnerLocationId(final Integer bpartnerLocationId)
	{
		// NOTE: we are also accepting bpartnerLocationId=null => for search HUs without a BPartner Location
		_onlyWithBPartnerLocationIds.add(bpartnerLocationId);

		return this;
	}

	/**
	 * Retrieves an existing or new de.metas.handlingunits.impl.HUAttributeQueryFilterVO entry for the given attribute and type.
	 * Note: The entry will be included in the onlyAttributeId2values and not in the barcode attributes list
	 *
	 * @param attribute
	 * @param attributeValueType
	 * @return
	 */
	private final HUAttributeQueryFilterVO getAttributeFilterVO(final I_M_Attribute attribute, final String attributeValueType)
	{
		return getAttributeFilterVO(onlyAttributeId2values, attribute, attributeValueType);
	}

	/**
	 * Possibility to put the attribute in a given map.
	 *
	 * @param targetMap
	 * @param attribute
	 * @param attributeValueType
	 * @return
	 */
	private final HUAttributeQueryFilterVO getAttributeFilterVO(final Map<Integer, HUAttributeQueryFilterVO> targetMap, final I_M_Attribute attribute, final String attributeValueType)
	{
		Check.assumeNotNull(attribute, "attribute not null");

		final int attributeId = attribute.getM_Attribute_ID();
		HUAttributeQueryFilterVO attributeFilterVO = targetMap.get(attributeId);
		if (attributeFilterVO == null)
		{
			attributeFilterVO = new HUAttributeQueryFilterVO(attribute, attributeValueType);
			targetMap.put(attributeId, attributeFilterVO);
		}
		else
		{
			attributeFilterVO.setAttributeValueType(attributeValueType);
		}

		return attributeFilterVO;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttribute(final I_M_Attribute attribute, final Object value)
	{
		final HUAttributeQueryFilterVO attributeFilterVO = getAttributeFilterVO(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown);
		attributeFilterVO.addValue(value);

		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttribute(final String attributeName, final Object value)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(attributeName, I_M_Attribute.class);
		return addOnlyWithAttribute(attribute, value);
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttribute(final AttributeId attributeId, final Object value)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).getAttributeById(attributeId);
		return addOnlyWithAttribute(attribute, value);
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttributeInList(final I_M_Attribute attribute, final String attributeValueType, final List<? extends Object> values)
	{
		Check.assumeNotNull(values, "values not null");
		final HUAttributeQueryFilterVO attributeFilterVO = getAttributeFilterVO(attribute, attributeValueType);
		attributeFilterVO.addValues(values);

		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttributeInList(final String attributeName, final Object... values)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(attributeName);
		final List<Object> valuesAsList = Arrays.asList(values);
		addOnlyWithAttributeInList(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown, valuesAsList);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttributeNotNull(final String attributeName)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(attributeName);
		getAttributeFilterVO(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown)
				.setMatchingType(HUAttributeQueryFilterVO.AttributeValueMatchingType.NotNull);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttributeMissingOrNull(final String attributeName)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(attributeName);
		getAttributeFilterVO(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown)
				.setMatchingType(HUAttributeQueryFilterVO.AttributeValueMatchingType.MissingOrNull);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttributes(ImmutableAttributeSet attributeSet)
	{
		for (final I_M_Attribute attribute : attributeSet.getAttributes())
		{
			final Object value = attributeSet.getValue(attribute);
			addOnlyWithAttribute(attribute, value);
		}
		return this;
	}

	@Override
	public boolean matches(final IAttributeSet attributes)
	{
		Check.assumeNotNull(attributes, "attributes not null");

		// If there is no attribute restrictions we can accept this "attributes" right away
		if (onlyAttributeId2values == null || onlyAttributeId2values.isEmpty())
		{
			return true;
		}

		for (final HUAttributeQueryFilterVO attributeFilter : onlyAttributeId2values.values())
		{
			if (!attributeFilter.matches(attributes))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public IHUQueryBuilder setOnlyWithBarcode(final String barcode)
	{
		this.barcode = barcode;
		loadBarcodeAttributes(barcode);
		return this;
	}

	private void loadBarcodeAttributes(final String barcode)
	{
		final String dimBarcodeAttributesInternalName = HUConstants.DIM_Barcode_Attributes;

		final DimensionSpec barcodeDimSpec = Services.get(IDimensionspecDAO.class).retrieveForInternalNameOrNull(dimBarcodeAttributesInternalName);
		if (barcodeDimSpec == null)
		{
			return; // no barcode dimension spec. Nothing to do
		}

		final List<I_M_Attribute> barcodeAttributes = barcodeDimSpec.retrieveAttributes();

		for (final I_M_Attribute attribute : barcodeAttributes)
		{
			// Barcode must be a String attribute. In the database, this is forced by a validation rule
			if (!attribute.getAttributeValueType().equals(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40))
			{
				continue;
			}

			final HUAttributeQueryFilterVO barcodeAttributeFilterVO = getAttributeFilterVO(_barcodeAttributesIds2Value, attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown);
			barcodeAttributeFilterVO.addValue(barcode);
		}

	}

	@Override
	public IHUQueryBuilder setOnlyIfAssignedToBPartner(final boolean onlyIfAssignedToBPartner)
	{
		this.onlyIfAssignedToBPartner = onlyIfAssignedToBPartner;
		return this;
	}

	@Override
	public IHUQueryBuilder addFilter(final IQueryFilter<I_M_HU> filter)
	{
		Check.assumeNotNull(filter, "filter not null");

		if (otherFilters == null)
		{
			otherFilters = queryBL.createCompositeQueryFilter(I_M_HU.class);
		}
		otherFilters.addFilter(filter);

		return this;
	}

	@Override
	public IHUQueryBuilder setInSubQueryFilter(final IQuery<I_M_HU> huSubQueryFilter)
	{
		// Check.assumeNotNull(huSubQueryFilter, "filter not null");
		this.huSubQueryFilter = huSubQueryFilter;
		return this;
	}

	@Override
	public IHUQueryBuilder onlyLocked()
	{
		this.locked = true;
		return this;
	}

	@Override
	public IHUQueryBuilder onlyNotLocked()
	{
		this.locked = false;
		return this;
	}

	@Override
	public IHUQueryBuilder setErrorIfNoHUs(final String errorADMessage)
	{
		final boolean errorIfNoHUs = true;
		return setErrorIfNoHUs(errorIfNoHUs, errorADMessage);
	}

	@Override
	public IHUQueryBuilder setErrorIfNoHUs(final boolean errorIfNoHUs, final String errorADMessage)
	{
		_errorIfNoHUs = errorIfNoHUs;
		_errorIfNoHUs_ADMessage = errorADMessage;
		return this;
	}

	@Override
	public boolean isErrorIfNoItems()
	{
		return _errorIfNoHUs;
	}

	/**
	 * Throws "No Items Found" exception if it's configured
	 *
	 * @param query
	 */
	private final void throwErrorNoHUsFoundIfNeeded(final IQuery<I_M_HU> query) throws HUException
	{
		if (!_errorIfNoHUs)
		{
			return;
		}

		String message;
		if (!Check.isEmpty(_errorIfNoHUs_ADMessage))
		{
			message = "@" + _errorIfNoHUs_ADMessage + "@";
		}
		else
		{
			message = "@NotFound@ @M_HU_ID@";
		}

		// Add detailed info
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			message += "\n\nQuery: " + query;
		}

		throw new HUException(message);
	}

	@Override
	public HUQueryBuilder addOnlyHUIds(@Nullable final Collection<Integer> onlyHUIds)
	{
		if (onlyHUIds == null)
		{
			return this;
		}

		if (_onlyHUIds == null)
		{
			_onlyHUIds = new HashSet<>();
		}
		_onlyHUIds.addAll(onlyHUIds);

		return this;
	}

	@Override
	public HUQueryBuilder addHUsToExclude(final Collection<I_M_HU> husToExclude)
	{
		if (husToExclude == null || husToExclude.isEmpty())
		{
			return this;
		}

		for (final I_M_HU hu : husToExclude)
		{
			final int huId = hu.getM_HU_ID();
			_huIdsToExclude.add(huId);
		}

		return this;
	}

	@Override
	public HUQueryBuilder addHUIdsToExclude(final Collection<Integer> huIdsToExclude)
	{
		if (huIdsToExclude == null || huIdsToExclude.isEmpty())
		{
			return this;
		}

		_huIdsToExclude.addAll(huIdsToExclude);

		return this;
	}

	@Override
	public HUQueryBuilder addPIVersionToInclude(@NonNull final HuPackingInstructionsVersionId huPIVersionId)
	{
		_huPIVersionIdsToInclude.add(huPIVersionId);
		return this;
	}

	private final Set<HuPackingInstructionsVersionId> getPIVersionIdsToInclude()
	{
		return _huPIVersionIdsToInclude;
	}

	@Override
	public HUQueryBuilder setExcludeAfterPickingLocator(final boolean excludeAfterPickingLocator)
	{
		_excludeAfterPickingLocator = excludeAfterPickingLocator;
		return this;
	}

	@Override
	public HUQueryBuilder setIncludeAfterPickingLocator(final boolean includeAfterPickingLocator)
	{
		_includeAfterPickingLocator = includeAfterPickingLocator;
		return this;
	}

	@Override
	public HUQueryBuilder setExcludeHUsOnPickingSlot(final boolean excludeHUsOnPickingSlot)
	{
		_excludeHUsOnPickingSlot = excludeHUsOnPickingSlot;
		return this;
	}

	@Override
	public IHUQueryBuilder setExcludeReservedToOtherThan(@NonNull final OrderLineId orderLineId)
	{
		_excludeReservedToOtherThanOrderLineId = orderLineId;
		return this;
	}

	@Override
	public IHUQueryBuilder setExcludeReserved()
	{
		_excludeReserved = true;
		return this;
	}
}
