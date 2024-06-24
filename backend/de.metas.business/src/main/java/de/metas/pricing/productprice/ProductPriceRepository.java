/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.pricing.productprice;

import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_ProductPrice;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ProductPriceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final ProductTaxCategoryService productTaxCategoryService;

	public ProductPriceRepository(@NonNull final ProductTaxCategoryService productTaxCategoryService)
	{
		this.productTaxCategoryService = productTaxCategoryService;
	}

	@NonNull
	public ProductPrice createProductPrice(@NonNull final CreateProductPriceRequest request)
	{
		final I_M_ProductPrice record = buildNewProductPriceRecord(request);
		saveRecord(record);

		return toProductPrice(record);
	}

	@NonNull
	public ProductPrice updateProductPrice(@NonNull final ProductPrice request)
	{
		final I_M_ProductPrice record = toProductPriceRecord(request);
		saveRecord(record);

		return toProductPrice(record);
	}

	@NonNull
	public ProductPrice getById(@NonNull final ProductPriceId productPriceId)
	{
		final I_M_ProductPrice record = getRecordById(productPriceId);
		return toProductPrice(record);
	}

	@NonNull
	public ProductPrice toProductPrice(@NonNull final I_M_ProductPrice record)
	{
		return ProductPrice.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.productPriceId(ProductPriceId.ofRepoId(record.getM_ProductPrice_ID()))
				.priceListVersionId(PriceListVersionId.ofRepoId(record.getM_PriceList_Version_ID()))
				.priceLimit(record.getPriceLimit())
				.priceList(record.getPriceList())
				.priceStd(record.getPriceStd())
				.taxCategoryId(productTaxCategoryService.getTaxCategoryId(record))
				.isActive(record.isActive())
				.uomId(UomId.ofRepoId(record.getC_UOM_ID()))
				.seqNo(record.getSeqNo())
				.build();
	}

	@NonNull
	private I_M_ProductPrice buildNewProductPriceRecord(@NonNull final CreateProductPriceRequest request)
	{
		final I_M_ProductPrice record = InterfaceWrapperHelper.newInstance(I_M_ProductPrice.class);

		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setM_PriceList_Version_ID(request.getPriceListVersionId().getRepoId());

		record.setPriceLimit(request.getPriceLimit());
		record.setPriceList(request.getPriceList());
		record.setPriceStd(request.getPriceStd());

		record.setC_UOM_ID(request.getUomId().getRepoId());

		record.setC_TaxCategory_ID(request.getTaxCategoryId().getRepoId());

		if (request.getIsActive() != null)
		{
			record.setIsActive(request.getIsActive());
		}

		if (request.getSeqNo() != null)
		{
			record.setSeqNo(request.getSeqNo());
		}

		return record;
	}

	@NonNull
	private I_M_ProductPrice toProductPriceRecord(@NonNull final ProductPrice request)
	{
		final I_M_ProductPrice record = getRecordById(request.getProductPriceId());

		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setM_PriceList_Version_ID(request.getPriceListVersionId().getRepoId());

		record.setPriceLimit(request.getPriceLimit());
		record.setPriceList(request.getPriceList());
		record.setPriceStd(request.getPriceStd());

		record.setC_UOM_ID(request.getUomId().getRepoId());
		record.setC_TaxCategory_ID(request.getTaxCategoryId().getRepoId());
		record.setSeqNo(request.getSeqNo());

		record.setIsActive(request.getIsActive());

		return record;
	}

	@NonNull
	private I_M_ProductPrice getRecordById(@NonNull final ProductPriceId productPriceId)
	{
		return queryBL
				.createQueryBuilder(I_M_ProductPrice.class)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_ProductPrice_ID, productPriceId.getRepoId())
				.create()
				.firstOnlyNotNull(I_M_ProductPrice.class);
	}

	@NonNull
	 public <T extends I_M_ProductPrice> T getRecordById(@NonNull final ProductPriceId productPriceId, @NonNull final Class<T> productPriceClass)
	{

		return InterfaceWrapperHelper.load(productPriceId, productPriceClass);
	}
}
