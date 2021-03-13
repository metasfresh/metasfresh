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
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_ProductPrice;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ProductPriceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull
	public ProductPrice createProductPrice(@NonNull final CreateProductPrice request)
	{
		final I_M_ProductPrice record = buildCreateProductPrice(request);
		saveRecord(record);

		return toProductPrice(record);
	}

	@NonNull
	public ProductPrice updateProductPrice(@NonNull final ProductPrice request)
	{
		final I_M_ProductPrice record = buildProductPrice(request);
		saveRecord(record);

		return toProductPrice(record);
	}

	private I_M_ProductPrice buildCreateProductPrice(@NonNull final CreateProductPrice request)
	{
		final I_M_ProductPrice record = InterfaceWrapperHelper.newInstance(I_M_ProductPrice.class);

		final I_C_UOM UOMRecord = productBL.getStockUOM(request.getProductId());

		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setM_PriceList_Version_ID(request.getPriceListVersionId().getRepoId());

		record.setPriceLimit(request.getPriceLimit());
		record.setPriceList(request.getPriceList());
		record.setPriceStd(request.getPriceStd());

		record.setC_UOM_ID(UOMRecord.getC_UOM_ID());

		record.setC_TaxCategory_ID(1000009);
		// todo florina record.setC_TaxCategory_ID(request.getTaxCategoryId().getRepoId());
		//todo florina set internal name

		record.setIsActive(request.getIsActive());
		return record;
	}

	private I_M_ProductPrice buildProductPrice(@NonNull final ProductPrice request)
	{
		final I_M_ProductPrice record = getRecordOrNull(request.getProductPriceId());

		final I_C_UOM UOMRecord = productBL.getStockUOM(request.getProductId());

		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setM_Product_ID(request.getProductId().getRepoId());
		record.setM_PriceList_Version_ID(request.getPriceListVersionId().getRepoId());

		record.setPriceLimit(request.getPriceLimit());
		record.setPriceList(request.getPriceList());
		record.setPriceStd(request.getPriceStd());

		record.setC_UOM_ID(UOMRecord.getC_UOM_ID());

		record.setC_TaxCategory_ID(1000009);
		// todo florina record.setC_TaxCategory_ID(request.getTaxCategoryId().getRepoId());
		//todo florina set internal name

		record.setIsActive(request.getIsActive());

		return record;
	}

	@Nullable
	private I_M_ProductPrice getRecordOrNull(@NonNull final ProductPriceId productPriceId)
	{
		return queryBL
				.createQueryBuilder(I_M_ProductPrice.class)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_M_Product_ID, productPriceId)
				.create()
				.firstOnly(I_M_ProductPrice.class);
	}

	private ProductPrice toProductPrice(@NonNull final I_M_ProductPrice record)
	{
		return ProductPrice.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.priceListVersionId(PriceListVersionId.ofRepoId(record.getM_PriceList_Version_ID()))
				.priceLimit(record.getPriceLimit())
				.priceList(record.getPriceList())
				.priceStd(record.getPriceStd())
				.taxCategoryId(TaxCategoryId.ofRepoId(record.getC_TaxCategory_ID()))
				.isActive(record.isActive())
				.build();
	}
}
