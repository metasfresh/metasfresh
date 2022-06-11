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

package de.metas.cucumber.stepdefs.hu;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_ID;
import static de.metas.handlingunits.model.I_M_HU_Storage.COLUMNNAME_M_HU_Storage_ID;
import static de.metas.handlingunits.model.I_M_HU_Storage.COLUMNNAME_M_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_Storage.COLUMNNAME_Qty;
import static de.metas.handlingunits.model.I_M_HU_Storage.COLUMN_M_HU_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class M_HU_Storage_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_M_HU> huTable;
	private final StepDefData<I_M_HU_Storage> huStorageTable;
	private final StepDefData<I_M_Product> productTable;

	public M_HU_Storage_StepDef(
			@NonNull final StepDefData<I_M_HU> huTable,
			@NonNull final StepDefData<I_M_HU_Storage> huStorageTable,
			@NonNull final StepDefData<I_M_Product> productTable)
	{
		this.huTable = huTable;
		this.huStorageTable = huStorageTable;
		this.productTable = productTable;
	}

	@And("validate M_HU_Storage:")
	public void validate_M_HU_Storage(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU hu = huTable.get(huIdentifier);

			final I_M_HU_Storage huStorageRecord = queryBL.createQueryBuilder(I_M_HU_Storage.class)
					.addEqualsFilter(COLUMN_M_HU_ID, hu.getM_HU_ID())
					.orderByDescending(COLUMN_M_HU_ID)
					.create()
					.firstOnlyOrNull(I_M_HU_Storage.class);

			assertThat(huStorageRecord).isNotNull();

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();
			assertThat(huStorageRecord.getM_Product_ID()).isEqualTo(product.getM_Product_ID());

			final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_Qty);
			assertThat(huStorageRecord.getQty()).isEqualTo(qty);

			final String huStorageIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_Storage_ID + "." + TABLECOLUMN_IDENTIFIER);
			huStorageTable.putOrReplace(huStorageIdentifier, huStorageRecord);
		}
	}
}
