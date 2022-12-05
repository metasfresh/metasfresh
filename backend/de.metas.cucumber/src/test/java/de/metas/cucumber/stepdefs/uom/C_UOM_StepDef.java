/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.uom;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_UOM_StepDef
{
	private final C_UOM_StepDefData uomTable;
	private final M_Product_StepDefData productTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public C_UOM_StepDef(@NonNull final C_UOM_StepDefData uomTable, @NonNull final M_Product_StepDefData productTable)
	{
		this.uomTable = uomTable;
		this.productTable = productTable;
	}

	@And("load C_UOM:")
	public void load_C_UOM(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String uomCode = DataTableUtil.extractStringForColumnName(row, I_C_UOM.COLUMNNAME_X12DE355);

			final I_C_UOM uomRecord = queryBL.createQueryBuilder(I_C_UOM.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_UOM.COLUMNNAME_X12DE355, uomCode)
					.create()
					.firstOnlyOrNull(I_C_UOM.class);

			assertThat(uomRecord).isNotNull();

			final String uomIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + TABLECOLUMN_IDENTIFIER);
			uomTable.putOrReplace(uomIdentifier, uomRecord);
		}
	}

	@And("load C_UOM for product:")
	public void load_C_UOM_for_product(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();

			final I_C_UOM uomRecord = queryBL.createQueryBuilder(I_C_UOM.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_UOM.COLUMNNAME_C_UOM_ID, product.getC_UOM_ID())
					.create()
					.firstOnlyOrNull(I_C_UOM.class);

			assertThat(uomRecord).isNotNull();

			final String uomIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + TABLECOLUMN_IDENTIFIER);
			uomTable.putOrReplace(uomIdentifier, uomRecord);
		}
	}
}
