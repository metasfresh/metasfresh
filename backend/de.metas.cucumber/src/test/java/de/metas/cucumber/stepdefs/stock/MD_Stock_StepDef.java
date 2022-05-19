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

package de.metas.cucumber.stepdefs.stock;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class MD_Stock_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_M_Product> productTable;

	public MD_Stock_StepDef(final StepDefData<I_M_Product> productTable)
	{
		this.productTable = productTable;
	}

	@Given("metasfresh initially has no MD_Stock data")
	public void setupMD_Stock_Data()
	{
		truncateMDStockData();
	}

	@And("metasfresh has MD_Stock data")
	public void verify_MD_Stock_Data(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			validateMD_Stock(dataTableRow);
		}
	}

	private void truncateMDStockData()
	{
		DB.executeUpdateEx("TRUNCATE TABLE M_Transaction cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE M_InventoryLine cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE M_Inventory cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE M_Cost cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE MD_Candidate cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE MD_Stock", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE m_hu_item_storage cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE m_hu_storage cascade", ITrx.TRXNAME_None);
		DB.executeUpdateEx("TRUNCATE TABLE m_hu_trx_line cascade", ITrx.TRXNAME_None);
	}

	private void validateMD_Stock(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, "QtyOnHand");

		final I_M_Product product = productTable.get(productIdentifier);

		final I_MD_Stock mdStock = queryBL.createQueryBuilder(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.create()
				.firstOnlyNotNull(I_MD_Stock.class);

		assertThat(mdStock).isNotNull();
		assertThat(mdStock.getQtyOnHand()).isEqualTo(qtyOnHand);
	}
}
