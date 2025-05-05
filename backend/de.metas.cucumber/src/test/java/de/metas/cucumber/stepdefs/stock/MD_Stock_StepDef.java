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
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class MD_Stock_StepDef
{
	private final static transient Logger logger = LogManager.getLogger(MD_Stock_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;

	public MD_Stock_StepDef(final M_Product_StepDefData productTable)
	{
		this.productTable = productTable;
	}

	@And("after not more than {int} seconds metasfresh has MD_Stock data")
	public void verify_MD_Stock_Data( final int timeoutSeconds, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> rows = dataTable.asMaps();

		final Supplier<Boolean> supplier = () -> rows.stream().allMatch(this::waitForStock);

		StepDefUtil.tryAndWait(timeoutSeconds, 500, supplier);

		for (final Map<String, String> row : rows)
		{
			validateMD_Stock(row);
		}
	}

	private boolean waitForStock(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final int productId = productTable.get(productIdentifier).getM_Product_ID();

		final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, "QtyOnHand");

		final I_MD_Stock mdStock = queryBL.createQueryBuilder(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, productId)
				.create()
				.firstOnly(I_MD_Stock.class);
		return mdStock != null && mdStock.getQtyOnHand().compareTo(qtyOnHand) == 0;
	}

	private void validateMD_Stock(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, "QtyOnHand");

		final I_M_Product product = productTable.get(productIdentifier);

		final I_MD_Stock mdStock = queryBL.createQueryBuilder(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.create()
				.firstOnly(I_MD_Stock.class);
		assertThat(mdStock).isNotNull();
		assertThat(mdStock.getQtyOnHand()).isEqualTo(qtyOnHand);
	}
}
