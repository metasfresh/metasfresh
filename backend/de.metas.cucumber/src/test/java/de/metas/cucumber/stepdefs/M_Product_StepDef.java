/*
 * #%L
 * de.metas.cucumber
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
 * MERCHANTABILITY or FITNEcleanupSS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs;

import de.metas.common.util.CoalesceUtil;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductType;
import de.metas.uom.UomId;
import de.metas.util.Services;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

public class M_Product_StepDef
{
	public static final ProductCategoryId PRODUCT_CATEGORY_ID = ProductCategoryId.ofRepoId(1000000);

	private final StepDefData<I_M_Product> productTable;
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	public M_Product_StepDef(@NonNull final StepDefData<I_M_Product> productTable)
	{
		this.productTable = productTable;
	}

	@Given("metasfresh contains M_Products:")
	public void metasfresh_contains_m_product(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String productName = tableRow.get("Name");
			final String productValue = CoalesceUtil.coalesce(tableRow.get("Value"), productName);

			final I_M_Product productRecord = CoalesceUtil.coalesceSuppliers(
					() -> productDAO.retrieveProductByValue(productValue),
					() -> newInstanceOutOfTrx(I_M_Product.class));
			productRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			productRecord.setValue(productValue);
			productRecord.setName(productName);
			productRecord.setC_UOM_ID(UomId.toRepoId(UomId.EACH));
			productRecord.setProductType(ProductType.Item.getCode());
			productRecord.setIsStocked(true);
			productRecord.setM_Product_Category_ID(PRODUCT_CATEGORY_ID.getRepoId());
			InterfaceWrapperHelper.saveRecord(productRecord);

			final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "M_Product");
			productTable.put(recordIdentifier, productRecord);
		}
	}
}
