/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
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
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);

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

	@And("metasfresh contains C_UOMs:")
	public void metasfresh_contains_C_UOMs(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String uomIdentifier = DataTableUtil.extractStringForColumnName(row, TABLECOLUMN_IDENTIFIER);

			final String x12de355 = DataTableUtil.extractStringForColumnName(row, I_C_UOM.COLUMNNAME_X12DE355);
			final String name = DataTableUtil.extractStringForColumnName(row, I_C_UOM.COLUMNNAME_Name);
			final String symbol = DataTableUtil.extractStringForColumnName(row, I_C_UOM.COLUMNNAME_UOMSymbol);
			final int stdPrecision = DataTableUtil.extractIntForColumnName(row, I_C_UOM.COLUMNNAME_StdPrecision);
			final int costingPrecision = DataTableUtil.extractIntForColumnName(row, I_C_UOM.COLUMNNAME_CostingPrecision);
			final String uomType = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + I_C_UOM.COLUMNNAME_UOMType);

			final I_C_UOM uomRecord = CoalesceUtil.coalesceSuppliersNotNull(
					() -> uomDao.getByX12DE355IfExists(X12DE355.ofCode(x12de355)).orElse(null),
					() -> InterfaceWrapperHelper.newInstance(I_C_UOM.class));

			uomRecord.setX12DE355(x12de355);
			uomRecord.setName(name);
			uomRecord.setUOMSymbol(symbol);
			uomRecord.setStdPrecision(stdPrecision);
			uomRecord.setCostingPrecision(costingPrecision);
			if (EmptyUtil.isNotBlank(uomType))
			{
				uomRecord.setUOMType(DataTableUtil.nullToken2Null(uomType));
			}

			InterfaceWrapperHelper.saveRecord(uomRecord);

			uomTable.putOrReplace(uomIdentifier, uomRecord);
		}
	}
}
