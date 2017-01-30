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

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.NotQueryFilter;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.IHUPickingSlotDAO;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Storage;

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

	@ToStringBuilder(skip = true)
	private Object _contextProvider;

	/**
	 * Shall we select only those HUs which are top level (i.e. not included in other HUs)?
	 *
	 * Default: true because in most of the cases we are looking for top level HUs
	 */
	private boolean huItemParentNull = true;
	private int parentHUItemId = -1;
	private int parentHUId = -1;

	private final Set<Integer> _onlyInWarehouseIds = new HashSet<>();
	@ToStringBuilder(skip = true)
	private final Set<Integer> _onlyInWarehouseIdsRO = Collections.unmodifiableSet(_onlyInWarehouseIds);
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
	private Boolean _emptyStorage = null;
	/** M_Attribute_ID to {@link HUAttributeQueryFilterVO} */
	private final Map<Integer, HUAttributeQueryFilterVO> onlyAttributeId2values = new HashMap<>();
	private final Set<String> _huStatusesToInclude = new HashSet<>();
	private final Set<String> _huStatusesToExclude = new HashSet<>();
	private final Set<Integer> _onlyHUIds = new HashSet<>();
	private final Set<Integer> _huIdsToExclude = new HashSet<>();
	private final Set<Integer> _huPIVersionIdsToInclude = new HashSet<>();
	private boolean _excludeHUsOnPickingSlot = false;

	private IQuery<I_M_HU> huSubQueryFilter = null;

	/**
	 * Other Filters to be applied
	 */
	private ICompositeQueryFilter<I_M_HU> otherFilters = null;

	/**
	 * Internal barcode
	 */
	private String barcode = null;

	private boolean onlyLocked = false;

	//
	//
	// Data retrieval options
	private boolean _errorIfNoHUs = false;
	private String _errorIfNoHUs_ADMessage = null;

	@Override
	public HUQueryBuilder copy()
	{
		final HUQueryBuilder copy = new HUQueryBuilder();

		copy._contextProvider = _contextProvider;
		copy.huItemParentNull = huItemParentNull;
		copy.parentHUItemId = parentHUItemId;
		copy.parentHUId = parentHUId;
		copy._onlyInWarehouseIds.addAll(_onlyInWarehouseIds);
		copy._excludeAfterPickingLocator = _excludeAfterPickingLocator;
		copy._includeAfterPickingLocator = _includeAfterPickingLocator;
		copy.onlyIfAssignedToBPartner = onlyIfAssignedToBPartner;

		copy._onlyInBpartnerIds.addAll(_onlyInBpartnerIds);
		copy._onlyWithBPartnerLocationIds.addAll(_onlyWithBPartnerLocationIds);
		copy._onlyWithProductIds.addAll(_onlyWithProductIds);
		copy._emptyStorage = this._emptyStorage;
		for (final Map.Entry<Integer, HUAttributeQueryFilterVO> e : onlyAttributeId2values.entrySet())
		{
			final Integer attributeId = e.getKey();
			final HUAttributeQueryFilterVO attributeFilterVO = e.getValue();
			final HUAttributeQueryFilterVO attributeFilterVOCopy = attributeFilterVO == null ? null : attributeFilterVO.copy();
			copy.onlyAttributeId2values.put(attributeId, attributeFilterVOCopy);
		}
		copy._huStatusesToInclude.addAll(_huStatusesToInclude);
		copy._huStatusesToExclude.addAll(_huStatusesToExclude);
		copy._onlyHUIds.addAll(_onlyHUIds);
		copy._huIdsToExclude.addAll(_huIdsToExclude);
		copy._huPIVersionIdsToInclude.addAll(_huPIVersionIdsToInclude);
		copy._excludeHUsOnPickingSlot = _excludeHUsOnPickingSlot;

		copy.otherFilters = otherFilters == null ? null : otherFilters.copy();

		copy.barcode = barcode;
		copy.onlyLocked = onlyLocked;

		copy._errorIfNoHUs = _errorIfNoHUs;
		copy._errorIfNoHUs_ADMessage = _errorIfNoHUs_ADMessage;

		return copy;
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				// .append(_contextProvider) // nop
				.append(huItemParentNull)
				.append(parentHUItemId)
				.append(parentHUId)
				.append(_onlyInWarehouseIds)
				.append(_excludeAfterPickingLocator)
				.append(_includeAfterPickingLocator)
				.append(onlyIfAssignedToBPartner)
				.append(_onlyInBpartnerIds)
				.append(_onlyWithBPartnerLocationIds)
				.append(_onlyWithProductIds)
				.append(_emptyStorage)
				.append(onlyAttributeId2values)
				.append(_huStatusesToInclude)
				.append(_huStatusesToExclude)
				.append(_onlyHUIds)
				.append(_huIdsToExclude)
				.append(_huPIVersionIdsToInclude)
				.append(_excludeHUsOnPickingSlot)
				.append(otherFilters)
				.append(huSubQueryFilter)
				.append(barcode)
				.append(onlyLocked)
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
				.append(huItemParentNull, other.huItemParentNull)
				.append(parentHUItemId, other.parentHUItemId)
				.append(parentHUId, other.parentHUId)
				.append(_onlyInWarehouseIds, other._onlyInWarehouseIds)
				.append(_excludeAfterPickingLocator, other._excludeAfterPickingLocator)
				.append(onlyIfAssignedToBPartner, other.onlyIfAssignedToBPartner)
				.append(_onlyInBpartnerIds, other._onlyInBpartnerIds)
				.append(_onlyWithBPartnerLocationIds, other._onlyWithBPartnerLocationIds)
				.append(_onlyWithProductIds, other._onlyWithProductIds)
				.append(_emptyStorage, other._emptyStorage)
				.append(onlyAttributeId2values, other.onlyAttributeId2values)
				.append(_huStatusesToInclude, other._huStatusesToInclude)
				.append(_huStatusesToExclude, other._huStatusesToExclude)
				.append(_onlyHUIds, other._onlyHUIds)
				.append(_huIdsToExclude, other._huIdsToExclude)
				.append(_huPIVersionIdsToInclude, other._huPIVersionIdsToInclude)
				.append(_excludeHUsOnPickingSlot, other._excludeHUsOnPickingSlot)
				.append(otherFilters, other.otherFilters)
				.append(huSubQueryFilter, other.huSubQueryFilter)
				.append(barcode, other.barcode)
				.append(onlyLocked, other.onlyLocked)
				.append(_errorIfNoHUs, other._errorIfNoHUs)
				.append(_errorIfNoHUs_ADMessage, other._errorIfNoHUs_ADMessage)
				.isEqual();
	}

	@Override
	public HUQueryBuilder clone()
	{
		return copy();
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
		queryBuilder.addOnlyContextClient();

		//
		// Create and add Query Filters
		final IQueryFilter<I_M_HU> filters = createQueryFilter();
		queryBuilder.filter(filters);

		//
		// ORDER BY
		queryBuilder.orderBy()
				.addColumn(I_M_HU.COLUMN_M_HU_ID); // just to have a predictable order

		return queryBuilder;
	}

	@Override
	public final IQueryFilter<I_M_HU> createQueryFilter()
	{
		final ICompositeQueryFilter<I_M_HU> filters = queryBL.createCompositeQueryFilter(I_M_HU.class);

		//
		// Only Active HUs
		filters.addOnlyActiveRecordsFilter();

		//
		// Filter by Warehouses
		final Set<Integer> onlyInWarehouseIds = getOnlyInWarehouseIds();
		if (!onlyInWarehouseIds.isEmpty() || _excludeAfterPickingLocator || _includeAfterPickingLocator)
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
			final IQuery<I_M_HU_Storage> huStoragesQuery = queryBL.createQueryBuilder(I_M_HU_Storage.class, getContextProvider())
					.addInArrayOrAllFilter(I_M_HU_Storage.COLUMN_M_Product_ID, onlyWithProductIds)
					.addNotEqualsFilter(I_M_HU_Storage.COLUMN_Qty, BigDecimal.ZERO)
					.addOnlyActiveRecordsFilter()
					.create();

			filters.addInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID,
					I_M_HU_Storage.COLUMN_M_HU_ID, huStoragesQuery);
		}

		//
		// Empty storage filter
		if (_emptyStorage != null)
		{
			final IQuery<I_M_HU_Storage> notEmptyHUStoragesQuery = queryBL.createQueryBuilder(I_M_HU_Storage.class, getContextProvider())
					.addNotEqualsFilter(I_M_HU_Storage.COLUMN_Qty, BigDecimal.ZERO)
					.addOnlyActiveRecordsFilter()
					.create();

			// Empty Storage Only
			if (_emptyStorage)
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
			filters.addEqualsFilter(I_M_HU.COLUMN_Value, barcode.trim());
		}

		//
		// Include only specific HUs
		if (_onlyHUIds != null && !_onlyHUIds.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_M_HU.COLUMN_M_HU_ID, _onlyHUIds);
		}

		//
		// Exclude specified HUs
		if (_huIdsToExclude != null && !_huIdsToExclude.isEmpty())
		{
			filters.addNotInArrayFilter(I_M_HU.COLUMN_M_HU_ID, _huIdsToExclude);
		}

		//
		// Include specified HU PI Versions
		final Set<Integer> huPIVersionIdsToInclude = getPIVersionIdsToInclude();
		if (!huPIVersionIdsToInclude.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_M_HU.COLUMN_M_HU_PI_Version_ID, huPIVersionIdsToInclude);
		}

		//
		// Filter only Locked records
		if (onlyLocked)
		{
			filters.addEqualsFilter(I_M_HU.COLUMN_Locked, true);
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
			final IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);
			final IQueryFilter<I_M_HU> husOnPickingSlotFilter = huPickingSlotDAO.createHUOnPickingSlotQueryFilter(getContextProvider());
			final IQueryFilter<I_M_HU> husNotOnPickingSlotFilter = new NotQueryFilter<>(husOnPickingSlotFilter);
			filters.addFilter(husNotOnPickingSlotFilter);
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
		final PlainContextAware contextProvider = new PlainContextAware(ctx, trxName);
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
		Check.assumeNotNull(_contextProvider, "contextProvider not null");
		return _contextProvider;
	}

	private final Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(getContextProvider());
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
	public IHUQueryBuilder addOnlyInWarehouseIds(final Collection<Integer> warehouseIds)
	{
		if (warehouseIds != null && !warehouseIds.isEmpty())
		{
			_onlyInWarehouseIds.addAll(warehouseIds);
		}
		else
		{
			// Adding this element just to make sure we are filtering by warehouses
			// NOTE: decided to do this instead of throwing an exception here because the provided IDs collection is empty
			_onlyInWarehouseIds.add(-1);
		}

		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyInWarehouseId(final int warehouseId)
	{
		_onlyInWarehouseIds.add(warehouseId);

		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyInWarehouses(final Collection<? extends I_M_Warehouse> warehouses)
	{
		if (warehouses == null || warehouses.isEmpty())
		{
			return addOnlyInWarehouseIds(Collections.<Integer> emptyList());
		}

		final Set<Integer> warehouseIds = new HashSet<Integer>(warehouses.size());
		for (final I_M_Warehouse warehouse : warehouses)
		{
			if (warehouse == null)
			{
				continue;
			}
			final int warehouseId = warehouse.getM_Warehouse_ID();
			warehouseIds.add(warehouseId);
		}
		return addOnlyInWarehouseIds(warehouseIds);
	}

	@Override
	public Set<Integer> getOnlyInWarehouseIds()
	{
		return _onlyInWarehouseIdsRO;
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
	public IHUQueryBuilder setEmptyStorageOnly()
	{
		_emptyStorage = true;
		return this;
	}

	@Override
	public IHUQueryBuilder setNotEmptyStorageOnly()
	{
		_emptyStorage = false;
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

	private final HUAttributeQueryFilterVO getAttributeFilterVO(final I_M_Attribute attribute, final String attributeValueType)
	{
		Check.assumeNotNull(attribute, "attribute not null");

		final int attributeId = attribute.getM_Attribute_ID();
		HUAttributeQueryFilterVO attributeFilterVO = onlyAttributeId2values.get(attributeId);
		if (attributeFilterVO == null)
		{
			attributeFilterVO = new HUAttributeQueryFilterVO(attribute, attributeValueType);
			onlyAttributeId2values.put(attributeId, attributeFilterVO);
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
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(getCtx(), attributeName, I_M_Attribute.class);
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
	public IHUQueryBuilder addOnlyWithAttributeInList(final String attributeName, final Object ... values)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(getCtx(), attributeName, I_M_Attribute.class);
		final List<Object> valuesAsList = Arrays.asList(values);
		addOnlyWithAttributeInList(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown, valuesAsList);
		return this;
	}
	
	@Override
	public IHUQueryBuilder addOnlyWithAttributeNotNull(final String attributeName)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(getCtx(), attributeName, I_M_Attribute.class);
		getAttributeFilterVO(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown)
				.setMatchingType(HUAttributeQueryFilterVO.AttributeValueMatchingType.NotNull);
		return this;
	}
	
	@Override
	public IHUQueryBuilder addOnlyWithAttributeMissingOrNull(final String attributeName)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(getCtx(), attributeName, I_M_Attribute.class);
		getAttributeFilterVO(attribute, HUAttributeQueryFilterVO.ATTRIBUTEVALUETYPE_Unknown)
				.setMatchingType(HUAttributeQueryFilterVO.AttributeValueMatchingType.MissingOrNull);
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
		return this;
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
	public IHUQueryBuilder setOnlyLocked(final boolean onlyLocked)
	{
		this.onlyLocked = onlyLocked;
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
	public HUQueryBuilder addOnlyHUIds(final Collection<Integer> onlyHUIds)
	{
		if (onlyHUIds == null || onlyHUIds.isEmpty())
		{
			return this;
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
	public HUQueryBuilder addPIVersionToInclude(final int huPIVersionId)
	{
		Check.assume(huPIVersionId > 0, "huPIVersionId > 0");
		_huPIVersionIdsToInclude.add(huPIVersionId);
		return this;
	}

	private final Set<Integer> getPIVersionIdsToInclude()
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
}
