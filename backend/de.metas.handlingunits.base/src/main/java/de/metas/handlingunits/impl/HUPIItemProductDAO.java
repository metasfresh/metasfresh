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

import de.metas.adempiere.util.cache.annotations.CacheAllowMutable;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPIItemProductQuery;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.i18n.IModelTranslationMap;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
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
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class HUPIItemProductDAO implements IHUPIItemProductDAO
{
	private final CCache<HUPIItemProductId, HUPIItemProduct> cacheById = CCache.<HUPIItemProductId, HUPIItemProduct>builder()
			.tableName(I_M_HU_PI_Item_Product.Table_Name)
			.build();

	@Override
	public HUPIItemProduct getById(@NonNull final HUPIItemProductId id)
	{
		return cacheById.getOrLoad(id, this::retrieveById);
	}

	public HUPIItemProduct retrieveById(@NonNull final HUPIItemProductId id)
	{
		final I_M_HU_PI_Item_Product record = getRecordById(id);
		return fromRecord(record);
	}

	public static HUPIItemProduct fromRecord(final I_M_HU_PI_Item_Product record)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(record);

		return HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(record.getM_HU_PI_Item_Product_ID()))
				.name(trls.getColumnTrl(I_M_HU_PI_Item_Product.COLUMNNAME_Name, record.getName()))
				.description(StringUtils.trimBlankToNull(record.getDescription()))
				.piItemId(HuPackingInstructionsItemId.ofRepoId(record.getM_HU_PI_Item_ID()))
				.productId(record.isAllowAnyProduct() ? null : ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyCUsPerTU(record.isInfiniteCapacity() ? null : Quantitys.create(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID())))
				.build();
	}

	@Override
	@NonNull
	public I_M_HU_PI_Item_Product getRecordById(@NonNull final HUPIItemProductId id)
	{
		return loadOutOfTrx(id, I_M_HU_PI_Item_Product.class);
	}

	@Override
	public IHUPIItemProductQuery createHUPIItemProductQuery()
	{
		return new HUPIItemProductQuery();
	}

	@Override
	public List<I_M_HU_PI_Item_Product> retrievePIMaterialItemProducts(final I_M_HU_PI_Item itemDef)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PI_Item_Product.class, itemDef)
				.filter(new EqualsQueryFilter<>(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, itemDef.getM_HU_PI_Item_ID()))
				.create()
				.setOnlyActiveRecords(true)
				.list(I_M_HU_PI_Item_Product.class);
	}

	@Override
	@Cached
	public I_M_HU_PI_Item_Product retrieveVirtualPIMaterialItemProduct(@CacheCtx final Properties ctx)
	{
		final I_M_HU_PI_Item_Product piip = getRecordById(HUPIItemProductId.VIRTUAL_HU);

		return Check.assumeNotNull(piip,
				"There is always a M_HU_PI_Item_Product record for HU_PI_Item_Product_ID={}",
				HUPIItemProductId.VIRTUAL_HU);
	}

	@Override
	public I_M_HU_PI_Item_Product retrievePIMaterialItemProduct(
			final I_M_HU_PI_Item itemDef,
			@NonNull final I_M_Product product,
			final ZonedDateTime date)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		final BPartnerId partner = null; // N/A
		return retrievePIMaterialItemProduct(itemDef, partner, productId, date);
	}

	@Nullable
	@Override
	public I_M_HU_PI_Item_Product retrievePIMaterialItemProduct(
			@NonNull final I_M_HU_PI_Item itemDef,
			@Nullable final BPartnerId partnerId,
			@NonNull final ProductId productId,
			@Nullable final ZonedDateTime date)
	{
		final IHUPIItemProductQuery queryVO = createHUPIItemProductQuery();
		if (partnerId != null)
		{
			queryVO.setC_BPartner_ID(partnerId.getRepoId());
		}
		queryVO.setM_Product_ID(productId.getRepoId());
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
			final ProductId productId,
			final ZonedDateTime date)
	{
		final IHUPIItemProductQuery queryVO = createHUPIItemProductQuery();
		queryVO.setM_Product_ID(productId.getRepoId());
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
			final ProductId productId,
			final BPartnerId bpartnerId,
			final ZonedDateTime date,
			final String huUnitType,
			final boolean allowInfiniteCapacity)
	{
		final ProductId packagingProductId = null;
		return retrieveMaterialItemProduct(productId, bpartnerId, date, huUnitType, allowInfiniteCapacity, packagingProductId);
	}

	@Override
	public I_M_HU_PI_Item_Product retrieveMaterialItemProduct(
			final ProductId productId,
			final BPartnerId bpartnerId,
			final ZonedDateTime date,
			final String huUnitType,
			final boolean allowInfiniteCapacity,
			@Nullable final ProductId packagingProductId)
	{
		final IHUPIItemProductQuery queryVO = createHUPIItemProductQuery();

		queryVO.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		queryVO.setM_Product_ID(productId == null ? 0 : productId.getRepoId());
		queryVO.setAllowAnyProduct(false); // 06566
		queryVO.setDate(date);
		queryVO.setHU_UnitType(huUnitType);

		// 07395 also set the isInfiniteCapacity
		queryVO.setAllowInfiniteCapacity(allowInfiniteCapacity);

		// FRESH-386
		queryVO.setM_Product_Packaging_ID(packagingProductId == null ? 0 : packagingProductId.getRepoId());

		return retrieveFirst(Env.getCtx(), queryVO, ITrx.TRXNAME_None);
	}

	/**
	 * WARNING: when using this method make sure queryVO is practically immutable (i.e. you created the instance locally)
	 */
	@Nullable
	@Cached(cacheName = I_M_HU_PI_Item_Product.Table_Name
			+ "#By"
			+ "#IHUPIItemProductQuery")
	/* package */I_M_HU_PI_Item_Product retrieveFirst(
			@CacheCtx final Properties ctx,
			@CacheAllowMutable final IHUPIItemProductQuery queryVO,
			@Nullable @CacheTrx final String trxName)
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
			@Nullable final String trxName)
	{
		final IQueryBuilder<I_M_HU_PI_Item_Product> queryBuilder = createHU_PI_Item_Product_QueryBuilder(ctx, queryVO, trxName);
		return queryBuilder
				.create()
				.list(I_M_HU_PI_Item_Product.class);
	}

	private IQueryFilter<I_M_HU_PI_Item_Product> createQueryFilter(
			@NonNull final Properties ctx,
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
		final ICompositeQueryFilter<I_M_HU_PI_Item_Product> adClientFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
				.setJoinOr()
				.addOnlyContextClient(ctx);
		filters.addFilter(adClientFilter);

		//
		// Product Filtering
		{
			final ICompositeQueryFilter<I_M_HU_PI_Item_Product> productFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
					.setJoinOr();

			if (queryVO.getM_Product_ID() > 0)
			{
				productFilter.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, queryVO.getM_Product_ID());
			}

			if (queryVO.isAllowAnyProduct())
			{
				final IQueryFilter<I_M_HU_PI_Item_Product> anyProductFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
						.setJoinAnd()
						.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_Product_ID, null)
						.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_IsAllowAnyProduct, true);
				productFilter.addFilter(anyProductFilter);

				//
				// If we allow Any Product, then we can include to accept AD_Client_ID=0
				final IQueryFilter<I_M_HU_PI_Item_Product> clientSystemFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
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
		// Product Price of a Price List Version has this Packing Item
		{
			if (queryVO.getPriceListVersionId() != null)
			{
				final IQueryFilter<I_M_PriceList_Version> plvFilter = queryBL.createCompositeQueryFilter(I_M_PriceList_Version.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID, queryVO.getPriceListVersionId());

				final IQuery<I_M_PriceList_Version> plvQuery = queryBL.createQueryBuilder(I_M_PriceList_Version.class)
						.filter(plvFilter)
						.create();

				final IQuery<I_M_ProductPrice> productPriceQuery = queryBL.createQueryBuilder(I_M_ProductPrice.class)
						.addInSubQueryFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, I_M_PriceList_Version.COLUMNNAME_M_PriceList_Version_ID, plvQuery)
						.create();

				filters.addInSubQueryFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, I_M_ProductPrice.COLUMNNAME_M_HU_PI_Item_Product_ID, productPriceQuery);
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
					HUPIItemProductId.TEMPLATE_HU,
					HUPIItemProductId.VIRTUAL_HU);

			filters.addFilter(infiniteCapacityFilter);
		}

		//
		// Valid From/To Filtering (only if Date is specified in query)
		final ZonedDateTime date = queryVO.getDate();
		if (date != null)
		{
			final IQueryFilter<I_M_HU_PI_Item_Product> validDateFromFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
					.addCompareFilter(I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, date);
			filters.addFilter(validDateFromFilter);

			final IQueryFilter<I_M_HU_PI_Item_Product> validDateToFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
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
			filters.addFilter(new EqualsQueryFilter<>(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, queryVO.getM_HU_PI_Item_ID()));
		}

		//
		// BPartner Filtering
		if (!queryVO.isAllowAnyPartner())
		{
			if (queryVO.getC_BPartner_ID() > 0)
			{
				final ICompositeQueryFilter<I_M_HU_PI_Item_Product> bpartnerFilter = queryBL.createCompositeQueryFilter(I_M_HU_PI_Item_Product.class)
						// see javadoc for setJoinOr
						.setJoinOr()
						.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID, queryVO.getC_BPartner_ID())
						.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID, null);

				filters.addFilter(bpartnerFilter);
			}
			else
			{
				// BPartner is not set: in this case we shall get only those which does not have a BP
				filters.addFilter(new EqualsQueryFilter<>(I_M_HU_PI_Item_Product.COLUMNNAME_C_BPartner_ID, null));
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
		// Don't allow Template HU PI
		filters.addNotEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, HUPIItemProductId.TEMPLATE_HU);

		//
		// Don't allow Virtual PIs
		if (!queryVO.isAllowVirtualPI())
		{
			filters.addNotEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, HUPIItemProductId.VIRTUAL_HU);
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
					.addEqualsFilter(I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID, queryVO.getM_Product_Packaging_ID())
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

		if (queryVO.isDefaultForProduct())
		{
			filters.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMN_IsDefaultForProduct, true);
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
				.addColumn(I_M_HU_PI_Item_Product.COLUMNNAME_IsDefaultForProduct, Direction.Descending, Nulls.Last)
				// Get latest valid record first
				.addColumn(I_M_HU_PI_Item_Product.COLUMNNAME_ValidFrom, Direction.Descending, Nulls.Last);

		return orderByBuilderToUse;
	}

	private IQueryBuilder<I_M_HU_PI_Item_Product> createHU_PI_Item_Product_QueryBuilder(
			@NonNull final Properties ctx,
			@NonNull final IHUPIItemProductQuery queryVO,
			@Nullable final String trxName)
	{
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
				.anyMatch();
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
													final ProductId cuProductId,
													final BPartnerId bpartnerId)
	{
		//
		// Filter out infinite capacity configurations
		final boolean allowInfiniteCapacity = false;
		return retrieveTUs(ctx, cuProductId, bpartnerId, allowInfiniteCapacity);
	}

	@Override
	public List<I_M_HU_PI_Item_Product> retrieveTUs(final Properties ctx,
													@NonNull final ProductId cuProductId,
													final BPartnerId bpartnerId,
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
		queryVO.setM_Product_ID(cuProductId.getRepoId());

		// Filter by BPartner, if there is a BPartner specified.
		// We expect to get ALL PI Item Product records which match this
		if (bpartnerId != null)
		{
			queryVO.setC_BPartner_ID(bpartnerId.getRepoId());
		}

		queryVO.setAllowInfiniteCapacity(allowInfiniteCapacity);

		//
		// Filter by current date (ValidFrom >= today, ValidTo <= today)
		final ZonedDateTime currentDate = SystemTime.asZonedDateTime();
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
		if (bpartnerId != null)
		{
			final I_M_HU_PI_Item_Product originalHUPIItemProduct = retrieveMaterialItemProduct(cuProductId, bpartnerId, currentDate, huUnitType,
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

	@Override
	public Optional<I_M_HU_PI_Item_Product> retrieveDefaultForProduct(
			@NonNull final ProductId productId,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final ZonedDateTime date)
	{
		final IHUPIItemProductQuery query = createHUPIItemProductQuery();
		query.setBPartnerId(bpartnerId);
		query.setProductId(productId);
		query.setDate(date);
		query.setDefaultForProduct(true);

		final I_M_HU_PI_Item_Product huPIItemProduct = retrieveFirst(Env.getCtx(), query, ITrx.TRXNAME_None);
		return Optional.ofNullable(huPIItemProduct);
	}

	@Override
	public Optional<HUPIItemProductId> retrieveDefaultIdForProduct(
			@NonNull final ProductId productId,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final ZonedDateTime date)
	{
		return retrieveDefaultForProduct(productId, bpartnerId, date)
				.map(huPiItemProduct -> HUPIItemProductId.ofRepoIdOrNull(huPiItemProduct.getM_HU_PI_Item_Product_ID()));
	}
}
