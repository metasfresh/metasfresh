/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.NotQueryFilter;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
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
import org.compiere.model.I_M_Product;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
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
import de.metas.product.ProductId;
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

	private final HUQueryBuilder_Locator locators;

	private boolean onlyIfAssignedToBPartner = false;
	private final Set<BPartnerId> _onlyInBpartnerIds = new HashSet<>();
	@ToStringBuilder(skip = true)
	private final Set<BPartnerId> _onlyInBpartnerIdsRO = Collections.unmodifiableSet(_onlyInBpartnerIds);
	private final Set<Integer> _onlyWithBPartnerLocationIds = new HashSet<>();
	private final Set<ProductId> _onlyWithProductIds = new HashSet<>();

	private Boolean _emptyStorageOnly = null;
	private boolean _allowEmptyStorage = false;

	private final HUQueryBuilder_Attributes attributes;

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

	private Boolean locked = null;

	//
	//
	// Data retrieval options
	private boolean _errorIfNoHUs = false;
	private String _errorIfNoHUs_ADMessage = null;

	public HUQueryBuilder(@NonNull final HUReservationRepository huReservationRepository)
	{
		this.huReservationRepository = huReservationRepository;

		this.locators = new HUQueryBuilder_Locator();
		this.attributes = new HUQueryBuilder_Attributes();
	}

	private HUQueryBuilder(final HUQueryBuilder from)
	{
		this.huReservationRepository = from.huReservationRepository;

		this._contextProvider = from._contextProvider;
		this.huItemParentNull = from.huItemParentNull;
		this.parentHUItemId = from.parentHUItemId;
		this.parentHUId = from.parentHUId;
		this.locators = from.locators.copy();
		this.onlyIfAssignedToBPartner = from.onlyIfAssignedToBPartner;

		this._onlyInBpartnerIds.addAll(from._onlyInBpartnerIds);
		this._onlyWithBPartnerLocationIds.addAll(from._onlyWithBPartnerLocationIds);
		this._onlyWithProductIds.addAll(from._onlyWithProductIds);
		this._emptyStorageOnly = from._emptyStorageOnly;

		this.attributes = from.attributes.copy();

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
				.append(locators)
				.append(onlyIfAssignedToBPartner)
				.append(_onlyInBpartnerIds)
				.append(_onlyWithBPartnerLocationIds)
				.append(_onlyWithProductIds)
				.append(_emptyStorageOnly)
				.append(attributes)
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
				.append(locators, other.locators)
				.append(onlyIfAssignedToBPartner, other.onlyIfAssignedToBPartner)
				.append(_onlyInBpartnerIds, other._onlyInBpartnerIds)
				.append(_onlyWithBPartnerLocationIds, other._onlyWithBPartnerLocationIds)
				.append(_onlyWithProductIds, other._onlyWithProductIds)
				.append(_emptyStorageOnly, other._emptyStorageOnly)
				.append(attributes, other.attributes)
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
		return attributes.getAttributesSummary();
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

		final ICompositeQueryFilter<I_M_HU> locatorFilters = locators.createQueryFilter();
		if (!locatorFilters.isEmpty())
		{
			filters.addFilter(locatorFilters);
		}

		//
		// Enforce M_HU.C_BPartner_ID to be set
		if (onlyIfAssignedToBPartner)
		{
			filters.addNotEqualsFilter(I_M_HU.COLUMNNAME_C_BPartner_ID, null);
		}
		//
		// Filter by C_BPartner_ID
		final Set<BPartnerId> onlyWithBPartnerIds = getOnlyInBPartnerIds();
		if (!onlyWithBPartnerIds.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_M_HU.COLUMNNAME_C_BPartner_ID, onlyWithBPartnerIds);
		}

		// Filter by C_BPartner_Location_ID
		if (!_onlyWithBPartnerLocationIds.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_M_HU.COLUMNNAME_C_BPartner_Location_ID, _onlyWithBPartnerLocationIds);
		}

		//
		// Filter only those HUs which contains our products restriction
		final Set<ProductId> onlyWithProductIds = getOnlyWithProductIds();
		if (!onlyWithProductIds.isEmpty())
		{
			final IQueryBuilder<I_M_HU_Storage> huStoragesQueryBuilder = queryBL.createQueryBuilder(I_M_HU_Storage.class, getContextProvider())
					.addOnlyActiveRecordsFilter()
					.addInArrayOrAllFilter(I_M_HU_Storage.COLUMNNAME_M_Product_ID, onlyWithProductIds);

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
		final ICompositeQueryFilter<I_M_HU> attributesFilter = attributes.createQueryFilter();
		if (!attributesFilter.isEmpty())
		{
			filters.addFilter(attributesFilter);
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
			final IQueryFilter<I_M_HU> husNotOnPickingSlotFilter = NotQueryFilter.of(husOnPickingSlotFilter);
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
	public <T> List<T> collect(final String columnName, final Class<T> modelType)
	{
		return createQueryBuilder()
				.andCollect(columnName, modelType)
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
		locators.addOnlyInWarehouseIds(warehouseIds);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyInWarehouseId(@NonNull final WarehouseId warehouseId)
	{
		locators.addOnlyInWarehouseIds(ImmutableSet.of(warehouseId));
		return this;
	}

	@Override
	public Set<WarehouseId> getOnlyInWarehouseIds()
	{
		return locators.getOnlyInWarehouseIds();
	}

	@Override
	public HUQueryBuilder addOnlyInLocatorId(final int locatorId)
	{
		locators.addOnlyInLocatorId(locatorId);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyInLocatorIds(final Collection<Integer> locatorIds)
	{
		locators.addOnlyInLocatorIds(locatorIds);
		return this;
	}

	@Override
	public Set<BPartnerId> getOnlyInBPartnerIds()
	{
		return _onlyInBpartnerIdsRO;
	}

	@Override
	public IHUQueryBuilder addOnlyWithProductIds(final Collection<Integer> productIds)
	{
		if (productIds != null && !productIds.isEmpty())
		{
			_onlyWithProductIds.addAll(ProductId.ofRepoIds(productIds));
		}
		else
		{
			// Adding this element just to make sure we are filtering by warehouses
			// NOTE: decided to do this instead of throwing an exception here because the provided IDs collection is empty
			_onlyWithProductIds.add(null);
		}

		return this;
	}

	@Override
	public Set<ProductId> getOnlyWithProductIds()
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
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		return addOnlyWithProductId(productId);
	}

	@Override
	public IHUQueryBuilder addOnlyWithProductId(final ProductId productId)
	{
		_onlyWithProductIds.add(productId);
		return this;
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
	public IHUQueryBuilder addOnlyInBPartnerId(final BPartnerId bPartnerId)
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

	@Override
	public IHUQueryBuilder addOnlyWithAttribute(final I_M_Attribute attribute, final Object value)
	{
		attributes.addOnlyWithAttribute(attribute, value);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttribute(final AttributeCode attributeCode, final Object value)
	{
		attributes.addOnlyWithAttribute(attributeCode, value);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttribute(final AttributeId attributeId, final Object value)
	{
		attributes.addOnlyWithAttribute(attributeId, value);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttributeInList(final I_M_Attribute attribute, final String attributeValueType, final List<? extends Object> values)
	{
		attributes.addOnlyWithAttributeInList(attribute, attributeValueType, values);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttributeInList(final AttributeCode attributeCode, final Object... values)
	{
		attributes.addOnlyWithAttributeInList(attributeCode, values);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttributeNotNull(final AttributeCode attributeCode)
	{
		attributes.addOnlyWithAttributeNotNull(attributeCode);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttributeMissingOrNull(final AttributeCode attributeCode)
	{
		attributes.addOnlyWithAttributeMissingOrNull(attributeCode);
		return this;
	}

	@Override
	public IHUQueryBuilder addOnlyWithAttributes(ImmutableAttributeSet attributeSet)
	{
		attributes.addOnlyWithAttributes(attributeSet);
		return this;
	}

	@Override
	public boolean matches(final IAttributeSet attributes)
	{
		return this.attributes.matches(attributes);
	}

	@Override
	public IHUQueryBuilder allowSqlWhenFilteringAttributes(final boolean allow)
	{
		this.attributes.setAllowSql(allow);
		return this;
	}

	@Override
	public IHUQueryBuilder setOnlyWithBarcode(final String barcode)
	{
		attributes.setBarcode(barcode);
		return this;
	}

	@Override
	public IHUQueryBuilder setOnlyIfAssignedToBPartner(final boolean onlyIfAssignedToBPartner)
	{
		this.onlyIfAssignedToBPartner = onlyIfAssignedToBPartner;
		return this;
	}

	@Override
	public IHUQueryBuilder addFilter(@NonNull final IQueryFilter<I_M_HU> filter)
	{
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
		locators.setExcludeAfterPickingLocator(excludeAfterPickingLocator);
		return this;
	}

	@Override
	public HUQueryBuilder setIncludeAfterPickingLocator(final boolean includeAfterPickingLocator)
	{
		locators.setIncludeAfterPickingLocator(includeAfterPickingLocator);
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
