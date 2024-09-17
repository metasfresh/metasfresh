package de.metas.product;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.BPartnerProduct;
import de.metas.bpartner_product.CreateBPartnerProductRequest;
import de.metas.i18n.IModelTranslationMap;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

	@NonNull
	public ImmutableList<BPartnerProduct> getByProductId(@NonNull final ProductId productId)
	{

		return queryBL.createQueryBuilder(I_C_BPartner_Product.class)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, productId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list()
				.stream()
				.map(ProductRepository::ofBPartnerProductRecord)
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
		return productRecord.map(ProductRepository::ofBPartnerProductRecord).orElse(null);
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

		return ofBPartnerProductRecord(bPartnerProduct);
	}

	public void updateBPartnerProduct(@NonNull final BPartnerProduct request)
	{
		final I_C_BPartner_Product record = toBPartnerProductRecord(request);
		saveRecord(record);
	}

	@NonNull
	public static BPartnerProduct ofBPartnerProductRecord(@NonNull final I_C_BPartner_Product record)
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

		return Product.builder()
				.id(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.productNo(productRecord.getValue())
				.name(modelTranslationMap.getColumnTrl(I_M_Product.COLUMNNAME_Name, productRecord.getName()))
				.description(modelTranslationMap.getColumnTrl(I_M_Product.COLUMNNAME_Description, productRecord.getDescription()))
				.documentNote(modelTranslationMap.getColumnTrl(I_M_Product.COLUMNNAME_DocumentNote, productRecord.getDocumentNote()))
				.productCategoryId(ProductCategoryId.ofRepoIdOrNull(productRecord.getM_Product_Category_ID()))
				.uomId(UomId.ofRepoId(productRecord.getC_UOM_ID()))
				.discontinued(productRecord.isDiscontinued())
				.discontinuedFrom(TimeUtil.asLocalDate(productRecord.getDiscontinuedFrom(), zoneId))
				.manufacturerId(manufacturerId > 0 ? BPartnerId.ofRepoId(manufacturerId) : null)
				.packageSize(productRecord.getPackageSize())
				.weight(productRecord.getWeight())
				.stocked(productRecord.isStocked())
				.commodityNumberId(CommodityNumberId.ofRepoIdOrNull(productRecord.getM_CommodityNumber_ID()))
				.active(productRecord.isActive())
				.productType(productRecord.getProductType())
				.gtin(productRecord.getGTIN())
				.ean(productRecord.getUPC())
				.orgId(OrgId.ofRepoId(productRecord.getAD_Org_ID()))
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

		record.setC_BPartner_ID(bPartnerProduct.getBPartnerId().getRepoId());
		record.setIsActive(bPartnerProduct.getActive() != null ? bPartnerProduct.getActive() : record.isActive());
		record.setSeqNo(bPartnerProduct.getSeqNo() != null ? bPartnerProduct.getSeqNo() : record.getSeqNo());
		record.setProductNo(bPartnerProduct.getProductNo());
		record.setDescription(bPartnerProduct.getDescription());
		record.setEAN_CU(bPartnerProduct.getCuEAN());
		record.setCustomerLabelName(bPartnerProduct.getCustomerLabelName());
		record.setGTIN(bPartnerProduct.getGtin());
		record.setIngredients(bPartnerProduct.getIngredients());
		record.setIsCurrentVendor(bPartnerProduct.getCurrentVendor() != null ? bPartnerProduct.getCurrentVendor() : record.isCurrentVendor());
		record.setIsExcludedFromSale(bPartnerProduct.getIsExcludedFromSales() != null ? bPartnerProduct.getIsExcludedFromSales() : record.isExcludedFromSale());
		record.setExclusionFromSaleReason(bPartnerProduct.getExclusionFromSalesReason());
		record.setIsDropShip(bPartnerProduct.getDropShip() != null ? bPartnerProduct.getDropShip() : record.isDropShip());
		record.setUsedForVendor(Boolean.TRUE.equals(bPartnerProduct.getUsedForVendor()));

		if (bPartnerProduct.getIsExcludedFromPurchase() != null)
		{
			record.setIsExcludedFromPurchase(bPartnerProduct.getIsExcludedFromPurchase());
		}

		record.setExclusionFromPurchaseReason(bPartnerProduct.getExclusionFromPurchaseReason());

		return record;
	}
}
