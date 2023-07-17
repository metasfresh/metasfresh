/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.pricing;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_ProductPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class M_ProductScalePrice_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_ProductPrice_StepDefData productPriceTable;
	private final M_ProductScalePrice_StepDefData productScalePriceTable;

	public M_ProductScalePrice_StepDef(
			@NonNull final M_ProductPrice_StepDefData productPriceTable,
			@NonNull final M_ProductScalePrice_StepDefData productScalePriceTable)
	{
		this.productPriceTable = productPriceTable;
		this.productScalePriceTable = productScalePriceTable;
	}

	@And("metasfresh contains M_ProductScalePrices:")
	public void metasfresh_contains_m_product_scale_price(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createM_ProductScalePrice(tableRow);
		}
	}

	private void createM_ProductScalePrice(final Map<String, String> tableRow)
	{
		final String productPriceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ProductScalePrice.COLUMNNAME_M_ProductPrice_ID);
		final I_M_ProductPrice productPrice = productPriceTable.get(productPriceIdentifier);
		final BigDecimal priceStd = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ProductScalePrice.COLUMNNAME_PriceStd);
		final BigDecimal priceLimit = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ProductScalePrice.COLUMNNAME_PriceLimit);
		final BigDecimal priceList = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ProductScalePrice.COLUMNNAME_PriceList);
		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ProductScalePrice.COLUMNNAME_Qty);

		final I_M_ProductScalePrice productScalePrice =
				CoalesceUtil.coalesceSuppliers(
						() -> queryBL.createQueryBuilder(I_M_ProductScalePrice.class)
								.addEqualsFilter(I_M_ProductScalePrice.COLUMNNAME_M_ProductPrice_ID, productPrice.getM_ProductPrice_ID())
								.addEqualsFilter(I_M_ProductScalePrice.COLUMNNAME_Qty, qty)
								.create()
								.firstOnlyOrNull(I_M_ProductScalePrice.class),
						() -> InterfaceWrapperHelper.newInstance(I_M_ProductScalePrice.class));

		assertThat(productScalePrice).isNotNull();
		productScalePrice.setM_ProductPrice_ID(productPrice.getM_ProductPrice_ID());
		productScalePrice.setPriceLimit(priceLimit);
		productScalePrice.setPriceList(priceList);
		productScalePrice.setPriceStd(priceStd);
		productScalePrice.setQty(qty);

		saveRecord(productScalePrice);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_M_ProductScalePrice.Table_Name);
		productScalePriceTable.putOrReplace(recordIdentifier, productScalePrice);
	}

}
