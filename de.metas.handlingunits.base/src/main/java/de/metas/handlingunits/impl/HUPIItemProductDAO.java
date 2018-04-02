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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.IQueryOrderByBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import com.google.common.annotations.VisibleForTesting;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.adempiere.util.cache.annotations.CacheAllowMutable;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductQuery;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import lombok.NonNull;

public class HUPIItemProductDAO implements IHUPIItemProductDAO
{
	@VisibleForTesting
	public static final int NO_HU_PI_Item_Product_ID = 100;

	@Override
	public IHUPIItemProductQuery createHUPIItemProductQuery()
	{
		return new HUPIItemProductQuery();
	}

	@Override
	public List<I_M_HU_PI_Item_Product> retrievePIMaterialItemProducts(final I_M_HU_PI_Item itemDef)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PI_Item_Product.class, itemDef)
				.filter(new EqualsQueryFilter<I_M_HU_PI_Item_Product>(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, itemDef.getM_HU_PI_Item_ID()))
				.create()
				.setOnlyActiveRecords(true)
				.list(I_M_HU_PI_Item_Product.class);
	}

	@Override
	public I_M_HU_PI_Item_Product retrieveForId(final Properties ctx, final int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 0)
		{
			return null;
		}

		return InterfaceWrapperHelper.create(ctx, M_HU_PI_Item_Product_ID, I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
	}

	@Override
	@Cached
	public I_M_HU_PI_Item_Product retrieveVirtualPIMaterialItemProduct(@CacheCtx final Properties ctx)
	{
		return retrieveForId(ctx, VIRTUAL_HU_PI_Item_Product_ID);
	}

	@Override
	public I_M_HU_PI_Item_Product retrievePIMaterialItemProduct(
			final I_M_HU_PI_Item itemDef,
			final I_M_Product product,
			final Date date)
	{
		final I_C_BPartner partner = null; // N/A
		return retrievePIMaterialItemProduct(itemDef, partner, product, date);
	}

	@Override
	public I_M_HU_PI_Item_Product retrievePIMaterialItemProduct(
			@NonNull final I_M_HU_PI_Item itemDef,
			@Nullable final I_C_BPartner partner,
			@NonNull final I_M_Product product,
			@Nullable final Date date)
	{
		final IHUPIItemProductQuery queryVO = createHUPIItemProductQuery();
		if (partner != null)
		{
			final int partnerId = partner.getC_BPartner_ID();
			queryVO.setC_BPartner_ID(partnerId);
		}
		queryVO.setM_Product_ID(product.getM_Product_ID());
		queryVO.setAllowAnyProduct(true);
		queryVO.setM_HU_PI_Item_ID(itemDef.getM_HU_PI_Item_ID());
		queryVO.setDate(date);

		//
		// Retrieve first item
		final Properties ctx = InterfaceWrapperHelper.getCtx(itemDef);
		final String trxName = InterfaceWrapperHelper.getTrxName(itemDef);
		return retrieveFirst(ctx, queryVO, trxName);
	}

	@Override
	public I_M_HU_PI_Item_Product retrievePIMaterialItemProduct(
			final I_M_HU_Item huItem,
			final I_M_Product product,
			final Date date)
	{
		final IHUPIItemProductQuery queryVO = createHUPIItemProductQuery();
		queryVO.setM_Product_ID(product.getM_Product_ID());
		queryVO.setAllowAnyProduct(true);
		queryVO.setM_HU_PI_Item_ID(huItem.getM_HU_PI_Item_ID());
		queryVO.setDate(date);

		//
		// Filter by BPartner (if any)
		final I_M_HU hu = huItem.getM_HU();
		queryVO.setC_BPartner_ID(hu.getC_BPartner_ID());

		//
		// Retrieve first item
		final Properties ctx = InterfaceWrapperHelper.getCtx(huItem);
		final String trxName = InterfaceWrapperHelper.getTrxName(huItem);
		return retrieveFirst(ctx, queryVO, trxName);
	}

	@Override
	public I_M_HU_PI_Item_Product retrieveMaterialItemProduct(
			final I_M_Product product,
			final I_C_BPartner bpartner,
			final Date date,
			final String huUnitType,
			final boolean allowInfiniteCapacity)
	{
		final I_M_Product packagingProduct = null;
		return retrieveMaterialItemProduct(product, bpartner, date, huUnitType, allowInfiniteCapacity, packagingProduct);
	}

	@Override
	public I_M_HU_PI_Item_Product retrieveMaterialItemProduct(
			final I_M_Product product,
			final I_C_BPartner bpartner,
			final Date date,
			final String huUnitType,
			final boolean allowInfiniteCapacity,
			final I_M_Product packagingProduct)
	{
		final IHUPIItemProductQuery queryVO = createHUPIItemProductQuery();

		queryVO.setC_BPartner_ID(bpartner == null ? 0 : bpartner.getC_BPartner_ID()); // guarding against empty partner & product
		queryVO.setM_Product_ID(product == null ? 0 : product.getM_Product_ID());
		queryVO.setAllowAnyProduct(false); // 06566
		queryVO.setDate(date);
		queryVO.setHU_UnitType(huUnitType);

		// 07395 also set the isInfiniteCapacity
		queryVO.setAllowInfiniteCapacity(allowInfiniteCapacity);

		// FRESH-386
		queryVO.setM_Product_Packaging_ID(packagingProduct == null ? 0 : packagingProduct.getM_Product_ID());

		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		final String trxName = InterfaceWrapperHelper.getTrxName(product);
		return retrieveFirst(ctx, queryVO, trxName);
	}

	/**
	 * WARNING: when using this method make sure queryVO is practically immutable (i.e. you created the instance locally)
	 *
	 * @param ctx
	 * @param queryVO
	 * @param trxName
	 * @return
	 */
	@Cached(cacheName = I_M_HU_PI_Item_Product.Table_Name
			+ "#By"
			+ "#IHUPIItemProductQuery")
	/* package */I_M_HU_PI_Item_Product retrieveFirst(
			@CacheCtx final Properties ctx,
			@CacheAllowMutable final IHUPIItemProductQuery queryVO,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_M_HU_PI_Item_Product> queryBuilder = createHU_PI_Item_Product_QueryBuilder(ctx, queryVO, trxName);
		return queryBuilder
				.create()
				.first(I_M_HU_PI_Item_Product.class);
	}

	@Override
	public List<I_M_HU_PI_Item_Product> retrieveHUItemProducts(
			final Properties ctx,
			final IHUPIItemProductQuery queryVO,
			final String trxName)
	{
		final IQueryBuilder<I_M_HU_PI_Item_Product> queryBuilder = createHU_PI_Item_Product_QueryBuilder(ctx, queryVO, trxName);
		return queryBuilder
				.create()
				.list(I_M_HU_PI_Item_Product.class);
	}

	private IQueryFilter<I_M_HU_PI_Item_Product> createQueryFilter(
			final Properties ctx,
			@NonNull final IHUPIItemProductQuery queryVO)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final String trxName = ITrx.TRXNAME_None;

		final ICompositeQueryFilter<I_M_HU_PI_Item_Product> filters = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class);
		filters.setJoinAnd();

		//
		// Only active records
		filters.addOnlyActiveRecordsFilter();

		//
		// Only for current AD_Client_ID
		final ICompositeQueryFilter<I_M_HU_PI_Item_Product> adClientFilter = queryBL.<I_M_HU_PI_Item_Product> createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
				.setJoinOr()
				.addOnlyContextClient(ctx);
		filters.addFilter(adClientFilter);

		//
		// Product Filtering
		{
			final ICompositeQueryFilter<I_M_HU_PI_Item_Product> productFilter = queryBL.<I_M_HU_PI_Item_Product> createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
					.setJoinOr();

			if (queryVO.getM_Product_ID() > 0)
			{
				productFilter.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, queryVO.getM_Product_ID());
			}

			if (queryVO.isAllowAnyProduct())
			{
				final IQueryFilter<I_M_HU_PI_Item_Product> anyProductFilter = queryBL.<I_M_HU_PI_Item_Product> createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
						.setJoinAnd()
						.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, null)
						.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_IsAllowAnyProduct, true);
				productFilter.addFilter(anyProductFilter);

				//
				// If we allow Any Product, then we can include to accept AD_Client_ID=0
				final IQueryFilter<I_M_HU_PI_Item_Product> clientSystemFilter = queryBL.<I_M_HU_PI_Item_Product> createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
						.setJoinAnd()
						.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_AD_Client_ID, IClientDAO.SYSTEM_CLIENT_ID)
						.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_IsAllowAnyProduct, true);
				adClientFilter.addFilter(clientSystemFilter);
			}

			if (!productFilter.isEmpty())
			{
				filters.addFilter(productFilter);
			}
		}

		//
		// Allow Infinite Capacity Filter
		if (!queryVO.isAllowInfiniteCapacity())
		{
			final ICompositeQueryFilter<I_M_HU_PI_Item_Product> infiniteCapacityFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class);
			infiniteCapacityFilter.setJoinOr();
			infiniteCapacityFilter.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMN_IsInfiniteCapacity, false);

			// We accept NoPI or VirtualPI configurations because those needs to be filtered out by other options (e.g. setAllowVirtualPI())
			infiniteCapacityFilter.addInArrayOrAllFilter(I_M_HU_PI_Item_Product.COLUMN_M_HU_PI_Item_Product_ID,
					NO_HU_PI_Item_Product_ID,
					VIRTUAL_HU_PI_Item_Product_ID);

			filters.addFilter(infiniteCapacityFilter);
		}

		//
		// Valid From/To Filtering (only if Date is specified in query)
		final Date date = queryVO.getDate();
		if (date != null)
		{
			final IQueryFilter<I_M_HU_PI_Item_Product> validDateFromFilter = queryBL.<I_M_HU_PI_Item_Product> createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
					.addCompareFilter(I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, date);
			filters.addFilter(validDateFromFilter);

			final IQueryFilter<I_M_HU_PI_Item_Product> validDateToFilter = queryBL.<I_M_HU_PI_Item_Product> createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
					.setJoinOr()
					.addCompareFilter(I_M_HU_PI_Item_Product.COLUMNNAME_ValidTo, Operator.GREATER_OR_EQUAL, date)
					// a PLV must have a ValidFrom, but has no ValidTo.
					// For this reason, ValidTo is not mandatory here neither
					.addCompareFilter(I_M_HU_PI_Item_Product.COLUMNNAME_ValidTo, Operator.EQUAL, null);
			filters.addFilter(validDateToFilter);
		}

		//
		// M_HU_PI_Item Filtering
		if (queryVO.getM_HU_PI_Item_ID() > 0)
		{
			filters.addFilter(new EqualsQueryFilter<I_M_HU_PI_Item_Product>(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, queryVO.getM_HU_PI_Item_ID()));
		}

		//
		// BPartner Filtering
		if (!queryVO.isAllowAnyPartner())
		{
			if (queryVO.getC_BPartner_ID() > 0)
			{
				final ICompositeQueryFilter<I_M_HU_PI_Item_Product> bpartnerFilter = queryBL.<I_M_HU_PI_Item_Product> createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
						// see javadoc for setJoinOr
						.setJoinOr()
						.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID, queryVO.getC_BPartner_ID())
						.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID, null);

				filters.addFilter(bpartnerFilter);
			}
			else
			{
				// BPartner is not set: in this case we shall get only those which does not have a BP
				filters.addFilter(new EqualsQueryFilter<I_M_HU_PI_Item_Product>(I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID, null));
			}
		}

		//
		// HU_UnitType filter: accept a result which has a M_HU_PI_Version with the required UnitType or a NULL unit type
		final String huUnitType = queryVO.getHU_UnitType();
		if (!Check.isEmpty(huUnitType, true))
		{
			final ICompositeQueryFilter<I_M_HU_PI_Version> piVersionFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Version.class)
					.setJoinOr()
					.addEqualsFilter(I_M_HU_PI_Version.COLUMNNAME_HU_UnitType, huUnitType)
					.addEqualsFilter(I_M_HU_PI_Version.COLUMNNAME_HU_UnitType, null);

			final IQuery<I_M_HU_PI_Version> piVersionQuery = queryBL.createQueryBuilder(I_M_HU_PI_Version.class, ctx, trxName)
					.filter(piVersionFilter)
					.create();

			final IQuery<I_M_HU_PI_Item> piItemQuery = queryBL.createQueryBuilder(I_M_HU_PI_Item.class, ctx, trxName)
					.addInSubQueryFilter(
							I_M_HU_PI_Item.COLUMN_M_HU_PI_Version_ID,
							I_M_HU_PI_Version.COLUMN_M_HU_PI_Version_ID,
							piVersionQuery)
					.addEqualsFilter(I_M_HU_PI_Item.COLUMN_ItemType, X_M_HU_PI_Item.ITEMTYPE_Material) // when we query PI_Items, we make sure that they have the correct type, just as a failsafe measure
					.create();

			filters.addInSubQueryFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Item_ID, piItemQuery);
		}

		//
		// Don't allow No HU PI
		filters.addNotEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, NO_HU_PI_Item_Product_ID);

		//
		// Don't allow Virtual PIs
		if (!queryVO.isAllowVirtualPI())
		{
			filters.addNotEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, VIRTUAL_HU_PI_Item_Product_ID);
		}

		//
		// Retain only one I_M_HU_PI_Item_Product for each distinct M_HU_PI
		if (queryVO.isOneConfigurationPerPI())
		{
			final boolean allowDifferentCapacities = queryVO.isAllowDifferentCapacities();

			final HUPIItemProductRetainOnePerPIFilter filter = new HUPIItemProductRetainOnePerPIFilter(allowDifferentCapacities);
			filters.addFilter(filter);
		}
		else
		{
			Check.assume(!queryVO.isAllowDifferentCapacities(), "'AllowDifferentCapacities' shall be false when 'IsOneConfigurationPerPI' is false: {}", queryVO);
		}

		// FRESH-386: accept PIIPs which have the given packaging product
		// That means that the PIIP's M_HU_PI_Item has a sibling with type PackingMaterial that in turn references a M_HU_PackingMaterial with our packaging M_Product
		if (queryVO.getM_Product_Packaging_ID() > 0)
		{

			final IQuery<I_M_HU_PI_Item> packingMaterialQuery = queryBL.createQueryBuilder(I_M_HU_PackingMaterial.class, ctx, trxName)
					.addEqualsFilter(I_M_HU_PackingMaterial.COLUMN_M_Product_ID, queryVO.getM_Product_Packaging_ID())
					.addOnlyActiveRecordsFilter()
					.andCollectChildren(I_M_HU_PI_Item.COLUMN_M_HU_PackingMaterial_ID, I_M_HU_PI_Item.class)
					.addEqualsFilter(I_M_HU_PI_Item.COLUMN_ItemType, X_M_HU_PI_Item.ITEMTYPE_PackingMaterial) // when we query PI_Items, we make sure that they have the correct type, just as a failsafe measure
					.addOnlyActiveRecordsFilter()
					// now we have the packaging-M_HU_PI_Item; go up to select packaging item's the M_HU_PI_Version
					.andCollect(I_M_HU_PI_Item.COLUMN_M_HU_PI_Version_ID)
					.addOnlyActiveRecordsFilter()
					// now select all M_HU_PI_Items of the M_HU_PI_Version. This includes the packaging item and it's siblings
					.andCollectChildren(I_M_HU_PI_Item.COLUMN_M_HU_PI_Version_ID, I_M_HU_PI_Item.class)
					.create();

			filters.addInSubQueryFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Item_ID, packingMaterialQuery);
		}

		return filters;
	}

	/**
	 * Create {@link IQueryOrderByBuilder}
	 *
	 * @param orderByBuilder if not null, it will be used and columns will be just appended
	 * @return updated order by filter
	 */
	/* package */IQueryOrderByBuilder<I_M_HU_PI_Item_Product> createQueryOrderByBuilder(final IQueryOrderByBuilder<I_M_HU_PI_Item_Product> orderByBuilder)
	{
		final IQueryOrderByBuilder<I_M_HU_PI_Item_Product> orderByBuilderToUse;
		if (orderByBuilder == null)
		{
			orderByBuilderToUse = Services.get(IQueryBL.class).createQueryOrderByBuilder(I_M_HU_PI_Item_Product.class);
		}
		else
		{
			orderByBuilderToUse = orderByBuilder;
		}

		orderByBuilderToUse
				// Get specific AD_Client_ID first
				.addColumn(I_M_HU_PI_Item_Product.COLUMNNAME_AD_Client_ID, Direction.Descending, Nulls.Last)
				// Group by M_HU_PI_Item_ID first
				// NOTE: in most of the cases we are not searching by M_HU_PI_Item
				// ... and BP, Product etc are more important to be first
				// Also it makes no sense to order by M_HU_PI_Item_ID
				// .addColumn(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, Direction.Ascending, Nulls.Last)

				// Get specific BPartner first
				.addColumn(I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID, Direction.Descending, Nulls.Last)
				// Get specific Product first
				.addColumn(I_M_HU_PI_Item_Product.COLUMNNAME_IsAllowAnyProduct, Direction.Descending, Nulls.Last) // Y first, N second
				.addColumn(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, Direction.Descending, Nulls.Last)
				// Get latest valid record first
				.addColumn(I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom, Direction.Descending, Nulls.Last);

		return orderByBuilderToUse;
	}

	private final IQueryBuilder<I_M_HU_PI_Item_Product> createHU_PI_Item_Product_QueryBuilder(
			final Properties ctx,
			final IHUPIItemProductQuery queryVO,
			final String trxName)
	{
		Check.assumeNotNull(queryVO, "queryVO not null");

		//
		// Final Query
		final IQueryBuilder<I_M_HU_PI_Item_Product> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_PI_Item_Product.class, ctx, trxName);

		final IQueryFilter<I_M_HU_PI_Item_Product> filter = createQueryFilter(ctx, queryVO);
		queryBuilder.filter(filter);

		//
		// Final Query Order By
		final IQueryOrderByBuilder<I_M_HU_PI_Item_Product> orderByBuilder = queryBuilder.orderBy();
		createQueryOrderByBuilder(orderByBuilder);

		//
		// Create QueryBuilder
		return queryBuilder;
	}

	@Override
	public boolean matches(final Properties ctx,
			final Collection<I_M_HU_PI_Item_Product> itemProducts,
			final IHUPIItemProductQuery queryVO)
	{
		if (itemProducts == null || itemProducts.isEmpty())
		{
			return false;
		}

		final IQueryFilter<I_M_HU_PI_Item_Product> filter = createQueryFilter(ctx, queryVO);
		// NOTE: in this case ordering is not important
		for (final I_M_HU_PI_Item_Product itemProduct : itemProducts)
		{
			if (filter.accept(itemProduct))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean matches(final Properties ctx, final IHUPIItemProductQuery queryVO, final String trxName)
	{
		Check.assumeNotNull(queryVO, "queryVO not null");

		return createHU_PI_Item_Product_QueryBuilder(ctx, queryVO, trxName)
				.create()
				.match();
	}

	@Override
	public List<I_M_HU_PI_Item_Product> retrieveAllForProduct(final I_M_Product product)
	{
		final IHUPIItemProductQuery queryVO = createHUPIItemProductQuery();
		queryVO.setM_Product_ID(product == null ? 0 : product.getM_Product_ID());
		queryVO.setAllowVirtualPI(false);
		queryVO.setAllowAnyPartner(true);

		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		final String trxName = InterfaceWrapperHelper.getTrxName(product);

		final IQueryBuilder<I_M_HU_PI_Item_Product> queryBuilder = createHU_PI_Item_Product_QueryBuilder(ctx, queryVO, trxName);
		return queryBuilder
				.create()
				.list(I_M_HU_PI_Item_Product.class);
	}

	@Override
	public List<I_M_HU_PI_Item_Product> retrieveTUs(final Properties ctx,
			final I_M_Product cuProduct,
			final I_C_BPartner bpartner)
	{
		//
		// Filter out infinite capacity configurations
		final boolean allowInfiniteCapacity = false;
		return retrieveTUs(ctx, cuProduct, bpartner, allowInfiniteCapacity);
	}

	@Override
	public List<I_M_HU_PI_Item_Product> retrieveTUs(final Properties ctx,
			@NonNull final I_M_Product cuProduct,
			final I_C_BPartner bpartner,
			final boolean allowInfiniteCapacity)
	{
		final IHUPIItemProductQuery queryVO = createHUPIItemProductQuery();

		//
		// Filter by TUs only
		final String huUnitType = X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;
		queryVO.setHU_UnitType(huUnitType);

		//
		// Filter by given cuProduct and also accept configurations for "any product".
		queryVO.setAllowAnyProduct(true);
		queryVO.setM_Product_ID(cuProduct.getM_Product_ID());

		// Filter by BPartner, if there is a BPartner specified.
		// We expect to get ALL PI Item Product records which match this
		if (bpartner != null && bpartner.getC_BPartner_ID() > 0)
		{
			queryVO.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		}

		queryVO.setAllowInfiniteCapacity(allowInfiniteCapacity);

		//
		// Filter by current date (ValidFrom >= today, ValidTo <= today)
		final Date currentDate = SystemTime.asDate();
		queryVO.setDate(currentDate);

		//
		// Do not filter out same-type PIs with different capacities
		queryVO.setOneConfigurationPerPI(true);
		queryVO.setAllowDifferentCapacities(true);

		//
		// Retrieve inital matching PI Item Products
		final List<I_M_HU_PI_Item_Product> availableHUPIItemProducts = new ArrayList<>(
				retrieveHUItemProducts(ctx, queryVO, ITrx.TRXNAME_None)); // explicitly use ArrayList in order to use .add(int, element)

		//
		// In case we have a specific BPartner, retrieve the default PI Item Product for that BPartner
		// and keep only that in our PI Item Products list (at first position).
		// The PI Item products which are for same PI, Product, Qty will be removed.
		if (bpartner != null && bpartner.getC_BPartner_ID() > 0)
		{
			final I_M_HU_PI_Item_Product originalHUPIItemProduct = retrieveMaterialItemProduct(cuProduct, bpartner, currentDate, huUnitType,
					false); // allowInfiniteCapacity = false
			if (originalHUPIItemProduct != null)     // kindda redundant check
			{
				removeDuplicatePIResultsWithoutPartner(originalHUPIItemProduct, availableHUPIItemProducts);
				availableHUPIItemProducts.add(0, originalHUPIItemProduct); // add original PI at index 0
			}
		}

		return availableHUPIItemProducts;
	}

	/**
	 * Clear entries if the PI is on the same product, uom, qty & infinite capacity flag
	 *
	 * @param originalHUPIItemProduct
	 * @param availableHUPIItemProducts
	 */
	private void removeDuplicatePIResultsWithoutPartner(final I_M_HU_PI_Item_Product originalHUPIItemProduct, final List<I_M_HU_PI_Item_Product> availableHUPIItemProducts)
	{
		final Iterator<I_M_HU_PI_Item_Product> it = availableHUPIItemProducts.iterator();
		while (it.hasNext()) // scan if we have duplicates of the original with a C_BPartner & remove them
		{
			final I_M_HU_PI_Item_Product availableHUPIItemProduct = it.next();

			final I_M_HU_PI_Version availableHUPIVersion = availableHUPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version();
			final I_M_HU_PI_Version originalHUPIVersion = originalHUPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version();
			if (availableHUPIVersion.getName().equals(originalHUPIVersion.getName())
					&& availableHUPIItemProduct.getM_Product_ID() == originalHUPIItemProduct.getM_Product_ID()
					&& availableHUPIItemProduct.getC_UOM_ID() == originalHUPIItemProduct.getC_UOM_ID()
					&& isSameQty(availableHUPIItemProduct, originalHUPIItemProduct))
			{
				it.remove();
			}
		}
	}

	/**
	 * @param piip1
	 * @param piip2
	 * @return true if both {@link I_M_HU_PI_Item_Product}s have infinite capacity or if they have matching quantities
	 */
	private boolean isSameQty(final I_M_HU_PI_Item_Product piip1, final I_M_HU_PI_Item_Product piip2)
	{
		if (piip1.isInfiniteCapacity() != piip2.isInfiniteCapacity())
		{
			return false;
		}

		final boolean isInfiniteCapacity = piip1.isInfiniteCapacity();
		if (isInfiniteCapacity)
		{
			return true;
		}

		final BigDecimal piip1Qty = piip1.getQty();
		final BigDecimal piip2Qty = piip2.getQty();
		return piip1Qty != null && piip1Qty.compareTo(piip2Qty) == 0;
	}

}
