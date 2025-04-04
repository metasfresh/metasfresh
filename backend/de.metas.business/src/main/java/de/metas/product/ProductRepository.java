package de.metas.product;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.BPartnerProduct;
import de.metas.bpartner_product.BPartnerProductQuery;
import de.metas.bpartner_product.CreateBPartnerProductRequest;
import de.metas.ean13.EAN13;
import de.metas.i18n.IModelTranslationMap;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.uom.UomId;
import de.metas.util.InSetPredicate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class ProductRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@VisibleForTesting
	public static ProductRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ProductRepository();
	}

	@NonNull
	public ImmutableList<BPartnerProduct> getByProductId(@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_Product.class)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list()
				.stream()
				.map(ProductRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public void inactivateBpartnerProducts(@NonNull final List<BPartnerId> bPartnerIdList, @NonNull final ProductId productId)
	{

		queryBL.createQueryBuilder(I_C_BPartner_Product.class)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId)
				.addInArrayFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, bPartnerIdList)
				.addOnlyActiveRecordsFilter()
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_BPartner_Product.COLUMNNAME_IsActive, false)
				.execute();
	}

	public Product getById(@NonNull final ProductId id)
	{
		final I_M_Product productRecord = loadOutOfTrx(id.getRepoId(), I_M_Product.class);
		return ofProductRecord(productRecord);
	}

	@NonNull
	public Optional<Product> getOptionalById(@NonNull final ProductId id)
	{
		return queryBL.createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_ID, id)
				.create()
				.firstOnlyOptional(I_M_Product.class)
				.map(this::ofProductRecord);
	}

	@Nullable
	public BPartnerProduct getByIdOrNull(@NonNull final ProductId id, @NonNull final BPartnerId bpartnerId)
	{
		final Optional<I_C_BPartner_Product> productRecord = getRecordById(id, bpartnerId);
		return productRecord.map(ProductRepository::fromRecord).orElse(null);
	}

	public ImmutableList<Product> getByIds(@NonNull final Set<ProductId> ids)
	{
		final List<I_M_Product> productRecords = queryBL
				.createQueryBuilder(I_M_Product.class)
				.addInArrayFilter(I_M_Product.COLUMNNAME_M_Product_ID, ids)
				.create()
				.list();

		final ImmutableList.Builder<Product> products = ImmutableList.builder();
		for (final I_M_Product productRecord : productRecords)
		{
			products.add(ofProductRecord(productRecord));
		}
		return products.build();
	}

	@NonNull
	public Product createProduct(@NonNull final CreateProductRequest request)
	{
		final I_M_Product product = newInstance(I_M_Product.class);

		if (request.getProductValue() != null)
		{
			product.setValue(request.getProductValue());
		}
		product.setName(request.getProductName());
		product.setM_Product_Category_ID(request.getProductCategoryId().getRepoId());
		product.setProductType(request.getProductType());
		product.setC_UOM_ID(request.getUomId().getRepoId());
		product.setIsPurchased(request.isPurchased());
		product.setUPC(request.getEan());
		product.setGTIN(request.getGtin());
		product.setDescription(request.getDescription());
		product.setAD_Org_ID(request.getOrgId().getRepoId());

		final boolean isDiscontinued = Boolean.TRUE.equals(request.getDiscontinued());

		if (isDiscontinued)
		{
			final ZoneId zoneId = orgDAO.getTimeZone(request.getOrgId());
			product.setDiscontinuedFrom(product.getDiscontinuedFrom() != null
					? TimeUtil.asTimestamp(request.getDiscontinuedFrom(), zoneId)
					: TimeUtil.asTimestamp(Instant.now()));
		}
		else
		{
			product.setDiscontinuedFrom(null);
		}

		product.setDiscontinued(isDiscontinued);

		if (request.getActive() != null)
		{
			product.setIsActive(request.getActive());
		}

		if (request.getStocked() != null)
		{
			product.setIsStocked(request.getStocked());
		}

		saveRecord(product);

		return ofProductRecord(product);
	}

	public void updateProduct(@NonNull final Product request)
	{
		final I_M_Product record = toProductRecord(request);
		saveRecord(record);
	}

	@SuppressWarnings("UnusedReturnValue")
	public BPartnerProduct createBPartnerProduct(@NonNull final CreateBPartnerProductRequest request)
	{
		final I_C_BPartner_Product bPartnerProduct = newInstance(I_C_BPartner_Product.class);

		bPartnerProduct.setM_Product_ID(request.getProductId().getRepoId());
		bPartnerProduct.setC_BPartner_ID(request.getBPartnerId().getRepoId());

		if (request.getActive() != null)
		{
			bPartnerProduct.setIsActive(request.getActive());

		}

		if (request.getSeqNo() != null)
		{
			bPartnerProduct.setSeqNo(request.getSeqNo());

		}

		bPartnerProduct.setProductNo(request.getProductNo());
		bPartnerProduct.setDescription(request.getDescription());
		bPartnerProduct.setEAN_CU(request.getCuEAN());
		bPartnerProduct.setGTIN(request.getGtin());
		bPartnerProduct.setCustomerLabelName(request.getCustomerLabelName());
		bPartnerProduct.setIngredients(request.getIngredients());
		bPartnerProduct.setShelfLifeMinPct(0); // FIXME
		bPartnerProduct.setShelfLifeMinDays(0); // FIXME

		if (request.getUsedForVendor() != null)
		{
			bPartnerProduct.setUsedForVendor(request.getUsedForVendor());
		}
		if (request.getUsedForCustomer() != null)
		{
			bPartnerProduct.setUsedForCustomer(request.getUsedForCustomer());
		}

		if (request.getDropShip() != null)
		{
			bPartnerProduct.setIsDropShip(request.getDropShip());

		}

		if (request.getIsExcludedFromSales() != null)
		{
			bPartnerProduct.setIsExcludedFromSale(request.getIsExcludedFromSales());

			if (request.getIsExcludedFromSales())
			{
				bPartnerProduct.setExclusionFromSaleReason(request.getExclusionFromSalesReason());
			}
		}

		if (request.getCurrentVendor() != null)
		{
			bPartnerProduct.setIsCurrentVendor(request.getCurrentVendor());

		}

		if (request.getIsExcludedFromPurchase() != null)
		{
			bPartnerProduct.setIsExcludedFromPurchase(request.getIsExcludedFromPurchase());

			if (request.getIsExcludedFromPurchase())
			{
				bPartnerProduct.setExclusionFromPurchaseReason(request.getExclusionFromPurchaseReason());
			}
		}

		saveRecord(bPartnerProduct);

		return fromRecord(bPartnerProduct);
	}

	public void updateBPartnerProduct(@NonNull final BPartnerProduct request)
	{
		final I_C_BPartner_Product record = toBPartnerProductRecord(request);
		saveRecord(record);
	}

	@NonNull
	@VisibleForTesting
	public static BPartnerProduct fromRecord(@NonNull final I_C_BPartner_Product record)
	{
		return BPartnerProduct.builder()
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.bPartnerId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.active(record.isActive())
				.seqNo(record.getSeqNo())
				.productNo(record.getProductNo())
				.description(record.getDescription())
				.cuEAN(record.getEAN_CU())
				.customerLabelName(record.getCustomerLabelName())
				.gtin(record.getGTIN())
				.ingredients(record.getIngredients())
				.currentVendor(record.isCurrentVendor())
				.isExcludedFromSales(record.isExcludedFromSale())
				.exclusionFromSalesReason(record.getExclusionFromSaleReason())
				.isExcludedFromPurchase(record.isExcludedFromPurchase())
				.exclusionFromPurchaseReason(record.getExclusionFromPurchaseReason())
				.dropShip(record.isDropShip())
				.usedForVendor(record.isUsedForVendor())
				.usedForCustomer(record.isUsedForCustomer())
				.build();
	}

	@NonNull
	private I_M_Product getRecordById(@NonNull final ProductId id)
	{
		return queryBL.createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_ID, id)
				.create()
				.firstOnlyNotNull(I_M_Product.class);
	}

	@NonNull
	private Optional<I_C_BPartner_Product> getRecordById(@NonNull final ProductId productId, @NonNull final BPartnerId bPartnerId)
	{
		return queryBL.createQueryBuilder(I_C_BPartner_Product.class)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId)
				.create()
				.firstOnlyOptional(I_C_BPartner_Product.class);
	}

	@NonNull
	private Product ofProductRecord(@NonNull final I_M_Product productRecord)
	{
		final int manufacturerId = productRecord.getManufacturer_ID();

		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(productRecord);

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(productRecord.getAD_Org_ID()));

		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(productRecord.getM_Product_Category_ID());

		return Product.builder()
				.id(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.productNo(productRecord.getValue())
				.name(modelTranslationMap.getColumnTrl(I_M_Product.COLUMNNAME_Name, productRecord.getName()))
				.value(productRecord.getValue())
				.description(modelTranslationMap.getColumnTrl(I_M_Product.COLUMNNAME_Description, productRecord.getDescription()))
				.documentNote(modelTranslationMap.getColumnTrl(I_M_Product.COLUMNNAME_DocumentNote, productRecord.getDocumentNote()))
				.productCategoryId(productCategoryId)
				.productCategoryName(productDAO.getProductCategoryById(productCategoryId).getName())
				.uomId(UomId.ofRepoId(productRecord.getC_UOM_ID()))
				.discontinued(productRecord.isDiscontinued())
				.discontinuedFrom(TimeUtil.asLocalDate(productRecord.getDiscontinuedFrom(), zoneId))
				.manufacturerId(BPartnerId.ofRepoIdOrNull(manufacturerId))
				.packageSize(productRecord.getPackageSize())
				.weight(productRecord.getWeight())
				.stocked(productRecord.isStocked())
				.commodityNumberId(CommodityNumberId.ofRepoIdOrNull(productRecord.getM_CommodityNumber_ID()))
				.active(productRecord.isActive())
				.productType(productRecord.getProductType())
				.gtin(productRecord.getGTIN())
				.ean(productRecord.getUPC())
				.orgId(OrgId.ofRepoId(productRecord.getAD_Org_ID()))
				.procurementStatus(productRecord.getProcurementStatus())
				.build();
	}

	@NonNull
	private I_M_Product toProductRecord(@NonNull final Product product)
	{
		final I_M_Product record = getRecordById(product.getId());

		final boolean isDiscontinued = Boolean.TRUE.equals(product.getDiscontinued());

		if (isDiscontinued)
		{
			final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(record.getAD_Org_ID()));

			record.setDiscontinuedFrom(product.getDiscontinuedFrom() != null
					? TimeUtil.asTimestamp(product.getDiscontinuedFrom(), zoneId)
					: TimeUtil.asTimestamp(Instant.now()));
		}
		else
		{
			record.setDiscontinuedFrom(null);
		}

		record.setDiscontinued(isDiscontinued);
		record.setValue(product.getProductNo());
		record.setName(product.getName().getDefaultValue());
		record.setDescription(Strings.emptyToNull(product.getDescription().getDefaultValue()));
		record.setC_UOM_ID(product.getUomId().getRepoId());
		record.setManufacturer_ID(BPartnerId.toRepoId(product.getManufacturerId()));
		record.setPackageSize(product.getPackageSize());
		record.setWeight(product.getWeight());
		record.setIsStocked(product.isStocked());
		record.setM_CommodityNumber_ID(CommodityNumberId.toRepoId(product.getCommodityNumberId()));
		record.setIsActive(product.getActive() != null ? product.getActive() : record.isActive());
		record.setProductType(product.getProductType());
		record.setGTIN(product.getGtin());
		record.setUPC(product.getEan());
		record.setAD_Org_ID(product.getOrgId().getRepoId());
		record.setM_Product_Category_ID(product.getProductCategoryId() != null ? product.getProductCategoryId().getRepoId() : record.getM_Product_Category_ID());

		return record;
	}

	@NonNull
	private I_C_BPartner_Product toBPartnerProductRecord(@NonNull final BPartnerProduct bPartnerProduct)
	{
		final I_C_BPartner_Product record = getRecordById(bPartnerProduct.getProductId(), bPartnerProduct.getBPartnerId())
				.orElseThrow(() -> new AdempiereException("No BPartner product record found for "
						+ bPartnerProduct.getProductId() + " " + bPartnerProduct.getBPartnerId()));

		updateRecord(record, bPartnerProduct);

		return record;
	}

	private static void updateRecord(@NonNull final I_C_BPartner_Product record, @NonNull final BPartnerProduct from)
	{
		record.setC_BPartner_ID(from.getBPartnerId().getRepoId());
		record.setIsActive(from.getActive() != null ? from.getActive() : record.isActive());
		record.setSeqNo(from.getSeqNo() != null ? from.getSeqNo() : record.getSeqNo());
		record.setProductNo(from.getProductNo());
		record.setDescription(from.getDescription());
		record.setEAN_CU(from.getCuEAN());
		record.setCustomerLabelName(from.getCustomerLabelName());
		record.setGTIN(from.getGtin());
		record.setIngredients(from.getIngredients());
		record.setIsCurrentVendor(from.getCurrentVendor() != null ? from.getCurrentVendor() : record.isCurrentVendor());
		record.setIsExcludedFromSale(from.getIsExcludedFromSales() != null ? from.getIsExcludedFromSales() : record.isExcludedFromSale());
		record.setExclusionFromSaleReason(from.getExclusionFromSalesReason());
		record.setIsDropShip(from.getDropShip() != null ? from.getDropShip() : record.isDropShip());
		record.setUsedForVendor(Boolean.TRUE.equals(from.getUsedForVendor()));
		record.setUsedForCustomer(Boolean.TRUE.equals(from.getUsedForCustomer()));

		if (from.getIsExcludedFromPurchase() != null)
		{
			record.setIsExcludedFromPurchase(from.getIsExcludedFromPurchase());
		}

		record.setExclusionFromPurchaseReason(from.getExclusionFromPurchaseReason());
	}

	public void updateBPartnerProductsByQuery(@NonNull final BPartnerProductQuery query, @NonNull final UnaryOperator<BPartnerProduct> updater)
	{
		toSqlQuery(query)
				.forEach(record -> {
					final BPartnerProduct bpartnerProduct = fromRecord(record);
					final BPartnerProduct bpartnerProductChanged = updater.apply(bpartnerProduct);
					if (!Objects.equals(bpartnerProduct, bpartnerProductChanged))
					{
						updateRecord(record, bpartnerProductChanged);
						saveRecord(record);
					}
				});
	}

	private IQuery<I_C_BPartner_Product> toSqlQuery(@NonNull final BPartnerProductQuery query)
	{
		final IQueryBuilder<I_C_BPartner_Product> sqlQueryBuilder = queryBL.createQueryBuilder(I_C_BPartner_Product.class)
				.orderBy(I_C_BPartner_Product.COLUMNNAME_M_Product_ID)
				.orderBy(I_C_BPartner_Product.COLUMNNAME_SeqNo)
				.orderBy(I_C_BPartner_Product.COLUMNNAME_C_BPartner_Product_ID);

		final InSetPredicate<EAN13> cuEANs = query.getCuEANs();
		if (cuEANs != null)
		{
			cuEANs.apply(new InSetPredicate.CaseConsumer<EAN13>()
			{
				@Override
				public void anyValue() {}

				@Override
				public void noValue()
				{
					sqlQueryBuilder.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_EAN_CU, null);
				}

				@Override
				public void onlyValues(final Set<EAN13> onlyValues)
				{
					final ImmutableSet<String> stringValues = onlyValues.stream()
							.map(EAN13::getAsString)
							.collect(ImmutableSet.toImmutableSet());
					sqlQueryBuilder.addInArrayFilter(I_C_BPartner_Product.COLUMNNAME_EAN_CU, stringValues);
				}
			});
		}

		return sqlQueryBuilder.create();
	}
}
