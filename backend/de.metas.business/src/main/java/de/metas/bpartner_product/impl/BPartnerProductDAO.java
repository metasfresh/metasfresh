/**
 *
 */
package de.metas.bpartner_product.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_BannedManufacturer;
import org.compiere.model.I_M_Product;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.bpartner_product.ProductExclude;
import de.metas.bpartner_product.ProductExclude.ProductExcludeBuilder;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.organization.OrgId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * @author cg
 *
 */
public class BPartnerProductDAO implements IBPartnerProductDAO
{
	@Override
	public List<I_C_BPartner_Product> retrieveBPartnerForProduct(
			final Properties ctx,
			final BPartnerId Vendor_ID,
			final ProductId productId,
			final OrgId orgId)
	{
		// the original was using table M_Product_PO instead of C_BPartner_Product

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<org.compiere.model.I_C_BPartner_Product> queryFilters = queryBL.createCompositeQueryFilter(org.compiere.model.I_C_BPartner_Product.class);
		queryFilters.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_UsedForVendor, true);

		// FRESH-334 only the BP_Products of the given org or of the org 0 are eligible

		queryFilters.addInArrayOrAllFilter(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY);

		if (Vendor_ID != null)
		{
			queryFilters.addEqualsFilter(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, Vendor_ID);
		}
		else
		{
			queryFilters.addEqualsFilter(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_IsCurrentVendor, true);
		}

		queryFilters.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId);

		return queryBL
				.createQueryBuilder(org.compiere.model.I_C_BPartner_Product.class, ctx, ITrx.TRXNAME_None)
				.filter(queryFilters)
				// FRESH-334 order by orgID descending. The non 0 org has priority over *
				.orderBy()
				.addColumn(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.list(I_C_BPartner_Product.class);
	}

	@Override
	public List<I_C_BPartner_Product> retrieveForProductIds(@NonNull final Set<ProductId> productIds)
	{
		if (productIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilderOutOfTrx(org.compiere.model.I_C_BPartner_Product.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productIds)
				.create()
				.list(I_C_BPartner_Product.class);
	}

	@Override
	public I_C_BPartner_Product retrieveByVendorId(
			@NonNull final BPartnerId vendorId,
			@NonNull final ProductId productId,
			@NonNull final OrgId orgId)
	{
		return retrieveByVendorIds(ImmutableSet.of(vendorId), productId, orgId)
				.get(vendorId);
	}

	@Override
	public Map<BPartnerId, I_C_BPartner_Product> retrieveByVendorIds(
			@NonNull final Set<BPartnerId> vendorIds,
			@NonNull final ProductId productId,
			@NonNull final OrgId orgId)
	{
		return retrieveAllVendorsQuery(productId, orgId)
				.addInArrayFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, vendorIds)
				.orderByDescending(I_C_BPartner_Product.COLUMNNAME_IsCurrentVendor) // current vendors first
				.orderByDescending(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID)
				.create()
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(record -> BPartnerId.ofRepoId(record.getC_BPartner_ID())));
	}

	private IQueryBuilder<I_C_BPartner_Product> retrieveAllVendorsQuery(
			final ProductId productId,
			final OrgId orgId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilderOutOfTrx(org.compiere.model.I_C_BPartner_Product.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_UsedForVendor, true)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId);
	}

	@Override
	public I_C_BPartner_Product retrieveBPartnerProductAssociation(
			@NonNull final I_C_BPartner partner,
			@NonNull final I_M_Product product,
			final OrgId orgId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(partner);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		return retrieveBPartnerProductAssociation(ctx, bpartnerId, productId, orgId);
	}

	@Override
	public I_C_BPartner_Product retrieveBPartnerProductAssociation(
			final Properties ctx,
			final BPartnerId bpartnerId,
			final ProductId productId,
			final OrgId orgId)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		return retrieveBPartnerProductAssociation(ctx, bpartnerId, productId, orgId, trxName);
	}

	@Cached(cacheName = I_C_BPartner_Product.Table_Name + "#By#C_BPartner_ID#M_Product_ID", expireMinutes = 10)
	public I_C_BPartner_Product retrieveBPartnerProductAssociation(
			@CacheCtx final Properties ctx,
			final BPartnerId bpartnerId,
			final ProductId productId,
			final OrgId orgId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_C_BPartner_Product> bPartnerProductAssociationsQueryBuilder = retrieveBPartnerProductAssociationsQueryBuilder(ctx,
				bpartnerId,
				productId,
				orgId,
				trxName);

		return bPartnerProductAssociationsQueryBuilder
				.orderBy()
				.addColumn(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.first(I_C_BPartner_Product.class);
	}

	@Cached(cacheName = I_C_BPartner_Product.Table_Name + "#By#C_BPartner_ID#M_Product_ID", expireMinutes = 10)
	@Override
	public List<I_C_BPartner_Product> retrieveAllBPartnerProductAssociations(
			@CacheCtx final Properties ctx,
			final BPartnerId bpartnerId,
			final ProductId productId,
			final OrgId orgId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_C_BPartner_Product> bPartnerProductAssociationsQueryBuilder = retrieveBPartnerProductAssociationsQueryBuilder(ctx,
				bpartnerId,
				productId,
				orgId,
				trxName);

		return bPartnerProductAssociationsQueryBuilder
				.create()
				.list(I_C_BPartner_Product.class);
	}

	@Cached(cacheName = I_C_BPartner_Product.Table_Name + "#By#C_BPartner_ID#M_Product_ID", expireMinutes = 10)
	public IQueryBuilder<I_C_BPartner_Product> retrieveBPartnerProductAssociationsQueryBuilder(
			@CacheCtx final Properties ctx,
			final BPartnerId bpartnerId,
			final ProductId productId,
			final OrgId orgId,
			@CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Product.class, ctx, trxName)
				//
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId)
				// FRESH-334 support the case when BP PRoduct is for org 0
				.addInArrayOrAllFilter(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY);
	}

	@Override
	public I_C_BPartner_Product retrieveBPProductForCustomer(final I_C_BPartner partner, final I_M_Product product, final OrgId orgId)
	{
		// query BL
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// make sure we only pick from the BP product entries for the product given as parameter
		final ICompositeQueryFilter<I_C_BPartner_Product> productQueryFilter = queryBL.createCompositeQueryFilter(I_C_BPartner_Product.class)
				.addEqualsFilter(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addInArrayOrAllFilter(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY);

		final ICompositeQueryFilter<I_C_BPartner_Product> customerQueryFilter = queryBL.createCompositeQueryFilter(I_C_BPartner_Product.class)
				.addEqualsFilter(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_UsedForCustomer, true)
				.addNotEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_Vendor_ID, null);

		// the bp product entry must:
		// Either be used for customer, have the bp given as parameter and the BP vendor not null
		// OR have the isCurrentVendor on true
		final ICompositeQueryFilter<I_C_BPartner_Product> customerOrCurrentVendorQueryFilter = queryBL.createCompositeQueryFilter(I_C_BPartner_Product.class)
				.setJoinOr()
				.addFilter(customerQueryFilter)

				.addEqualsFilter(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_IsCurrentVendor, true);

		final ICompositeQueryFilter<I_C_BPartner_Product> queryFilters = queryBL.createCompositeQueryFilter(I_C_BPartner_Product.class);
		queryFilters.addFilter(productQueryFilter);
		queryFilters.addFilter(customerOrCurrentVendorQueryFilter);

		final IQueryOrderBy bppOrderBy = queryBL.createQueryOrderByBuilder(I_C_BPartner_Product.class)
				.addColumn(org.compiere.model.I_C_BPartner_Product.COLUMNNAME_C_BPartner_Vendor_ID, Direction.Ascending, Nulls.Last)
				.addColumn(I_C_BPartner_Product.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.createQueryOrderBy();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Product.class, partner)
				.addOnlyActiveRecordsFilter()
				.filter(queryFilters)

				.create()
				.setOrderBy(bppOrderBy)
				.first(I_C_BPartner_Product.class);
	}

	@Override
	public Optional<ProductId> getProductIdByCustomerProductNo(
			@NonNull final BPartnerId customerId,
			@NonNull final String customerProductNo)
	{
		Check.assumeNotEmpty(customerProductNo, "customerProductNo shall not be empty");

		final I_C_BPartner_Product record = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner_Product.class)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, customerId)
				.addEqualsFilter(I_C_BPartner_Product.COLUMN_ProductNo, customerProductNo)
				.addEqualsFilter(I_C_BPartner_Product.COLUMN_UsedForCustomer, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_C_BPartner_Product.class);

		if (record == null)
		{
			return Optional.empty();
		}
		else
		{
			final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
			return Optional.of(productId);
		}
	}

	@Override
	public Optional<ProductId> getProductIdByCustomerProductName(
			@NonNull final BPartnerId customerId,
			@NonNull final String customerProductName)
	{
		Check.assumeNotEmpty(customerProductName, "customerProductName shall not be empty");

		final I_C_BPartner_Product record = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner_Product.class)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, customerId)
				.addEqualsFilter(I_C_BPartner_Product.COLUMN_ProductName, customerProductName)
				.addEqualsFilter(I_C_BPartner_Product.COLUMN_UsedForCustomer, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_C_BPartner_Product.class);

		if (record == null)
		{
			return Optional.empty();
		}
		else
		{
			final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
			return Optional.of(productId);
		}
	}

	@Override
	public List<ProductExclude> retrieveAllProductSalesExcludes()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner_Product.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_IsExcludedFromSale, true)
				.create()
				.stream()
				.map(bpartnerProduct -> toProductExclude(bpartnerProduct))
				.collect(ImmutableList.toImmutableList());
	}

	private static ProductExclude toProductExclude(@NonNull final I_C_BPartner_Product bpartnerProduct)
	{
		return ProductExclude.builder()
				.productId(ProductId.ofRepoId(bpartnerProduct.getM_Product_ID()))
				.bpartnerId(BPartnerId.ofRepoId(bpartnerProduct.getC_BPartner_ID()))
				.reason(bpartnerProduct.getExclusionFromSaleReason())
				.build();
	}

	@Override
	public Optional<ProductExclude> getExcludedFromSaleToCustomer(@NonNull final ProductId productId, @NonNull final BPartnerId partnerId)
	{
		final Optional<ProductExclude> productExcluded = getExcludedProductFromSaleToCustomer(productId, partnerId);
		final Optional<ProductExclude> manufacturerExcluded = getBannedManufacturerFromSaleToCustomer(productId, partnerId);

		if (productExcluded.isPresent() || manufacturerExcluded.isPresent())
		{
			final ProductExcludeBuilder builder = ProductExclude.builder()
					.bpartnerId(partnerId)
					.productId(productId);

			if (productExcluded.isPresent())
			{
				builder.reason(productExcluded.get().getReason());
			}

			if (manufacturerExcluded.isPresent())
			{
				builder.reason(manufacturerExcluded.get().getReason());
			}

			return Optional.of(builder.build());
		}

		return Optional.empty();
	}

	private Optional<ProductExclude> getExcludedProductFromSaleToCustomer(@NonNull final ProductId productId, @NonNull final BPartnerId partnerId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_C_BPartner_Product bpartnerProduct = queryBL
				.createQueryBuilderOutOfTrx(I_C_BPartner_Product.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_IsExcludedFromSale, true)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId.getRepoId())
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, partnerId.getRepoId())
				.create()
				.firstOnly(I_C_BPartner_Product.class);

		if (bpartnerProduct == null)
		{
			return Optional.empty();
		}

		final ProductExclude productExclude = ProductExclude.builder()
				.bpartnerId(partnerId)
				.productId(productId)
				.reason(bpartnerProduct.getExclusionFromSaleReason())
				.build();

		return Optional.of(productExclude);
	}

	private Optional<ProductExclude> getBannedManufacturerFromSaleToCustomer(@NonNull final ProductId productId, @NonNull final BPartnerId partnerId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ProductRepository productRepo = Adempiere.getBean(ProductRepository.class);
		final Product product = productRepo.getById(productId);
		final BPartnerId manufacturerId = product.getManufacturerId();

		final I_M_BannedManufacturer bannedManufacturer = queryBL
				.createQueryBuilderOutOfTrx(I_M_BannedManufacturer.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_BannedManufacturer.COLUMNNAME_C_BPartner_ID, partnerId.getRepoId())
				.addEqualsFilter(I_M_BannedManufacturer.COLUMNNAME_Manufacturer_ID, manufacturerId == null ? -1 : manufacturerId.getRepoId())
				.create()
				.firstOnly(I_M_BannedManufacturer.class);

		if (bannedManufacturer == null)
		{
			return Optional.empty();
		}

		final ProductExclude productExclude = ProductExclude.builder()
				.bpartnerId(partnerId)
				.productId(productId)
				.reason(bannedManufacturer.getExclusionFromSaleReason())
				.build();

		return Optional.of(productExclude);
	}
}
