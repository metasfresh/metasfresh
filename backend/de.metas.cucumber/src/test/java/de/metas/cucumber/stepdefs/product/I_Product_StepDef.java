/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.product;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.pricing.M_PriceList_Version_StepDefData;
import de.metas.cucumber.stepdefs.pricing.M_ProductPrice_StepDefData;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Cucumber step definitions for verifying product import results (M_ProductPrice).
 */
@RequiredArgsConstructor
public class I_Product_StepDef
{
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_PriceList_Version_StepDefData priceListVersionTable;
	@NonNull private final M_ProductPrice_StepDefData productPriceTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Verifies that M_ProductPrice records were created/updated with the expected InvoicableQtyBasedOn value.
	 *
	 * @cucumber.columns Identifier (optional), M_Product_ID (required, identifier ref),
	 * M_PriceList_Version_ID (required, identifier ref),
	 * InvoicableQtyBasedOn (required, CatchWeight or Nominal),
	 * PriceStd (optional)
	 */
	@Then("M_ProductPrice is found:")
	public void m_productprice_is_found(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::verifyM_ProductPrice);
	}

	private void verifyM_ProductPrice(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_M_ProductPrice.COLUMNNAME_M_Product_ID);
		final I_M_Product product = productTable.get(productIdentifier);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final StepDefDataIdentifier plvIdentifier = row.getAsIdentifier(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID);
		final I_M_PriceList_Version plv = priceListVersionTable.get(plvIdentifier);
		final PriceListVersionId plvId = PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID());

		final I_M_ProductPrice productPrice = queryBL.createQueryBuilder(I_M_ProductPrice.class)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, plvId)
				.create()
				.firstOnlyNotNull(I_M_ProductPrice.class);

		// Verify InvoicableQtyBasedOn
		final String expectedInvoicableQtyBasedOn = row.getAsString(I_M_ProductPrice.COLUMNNAME_InvoicableQtyBasedOn);
		assertThat(productPrice.getInvoicableQtyBasedOn())
				.as("M_ProductPrice.InvoicableQtyBasedOn for product %s", productIdentifier)
				.isEqualTo(expectedInvoicableQtyBasedOn);

		// Verify PriceStd if specified
		row.getAsOptionalBigDecimal(I_M_ProductPrice.COLUMNNAME_PriceStd)
				.ifPresent(expectedPriceStd -> assertThat(productPrice.getPriceStd())
						.as("M_ProductPrice.PriceStd for product %s", productIdentifier)
						.isEqualByComparingTo(expectedPriceStd));

		// Store in StepDefData
		row.getAsOptionalIdentifier()
				.ifPresent(id -> productPriceTable.putOrReplace(id, productPrice));
	}
}
