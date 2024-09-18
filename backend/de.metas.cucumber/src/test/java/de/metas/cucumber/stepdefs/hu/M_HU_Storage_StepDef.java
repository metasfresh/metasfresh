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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product;

import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_ID;
import static de.metas.handlingunits.model.I_M_HU_Storage.COLUMNNAME_M_HU_Storage_ID;
import static de.metas.handlingunits.model.I_M_HU_Storage.COLUMNNAME_M_Product_ID;
import static de.metas.handlingunits.model.I_M_HU_Storage.COLUMNNAME_Qty;
import static de.metas.handlingunits.model.I_M_HU_Storage.COLUMN_M_HU_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class M_HU_Storage_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_HU_StepDefData huTable;
	private final M_HU_Storage_StepDefData huStorageTable;
	private final M_Product_StepDefData productTable;

	public M_HU_Storage_StepDef(
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final M_HU_Storage_StepDefData huStorageTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.huTable = huTable;
		this.huStorageTable = huStorageTable;
		this.productTable = productTable;
	}

	@And("validate M_HU_Storage:")
	public void validate_M_HU_Storage(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final HuId huId = huTable.getId(row.getAsIdentifier(COLUMNNAME_M_HU_ID));

			final I_M_HU_Storage huStorageRecord = queryBL.createQueryBuilder(I_M_HU_Storage.class)
					.addEqualsFilter(COLUMN_M_HU_ID, huId)
					.orderByDescending(COLUMN_M_HU_ID)
					.create()
					.firstOnlyOrNull(I_M_HU_Storage.class);

			assertThat(huStorageRecord).isNotNull();

			row.getAsOptionalIdentifier(COLUMNNAME_M_Product_ID)
					.map(productTable::getId)
					.ifPresent(expectedProductId -> assertThat(ProductId.ofRepoId(huStorageRecord.getM_Product_ID())).isEqualTo(expectedProductId));
			row.getAsOptionalBigDecimal(COLUMNNAME_Qty)
					.ifPresent((expectedQty) -> assertThat(huStorageRecord.getQty()).isEqualTo(expectedQty));

			row.getAsOptionalIdentifier(COLUMNNAME_M_HU_Storage_ID)
					.ifPresent((huStorageIdentifier) -> huStorageTable.putOrReplace(huStorageIdentifier, huStorageRecord));
		});
	}

	@And("update M_HU_Storage:")
	public void update_M_HU_Storages(@NonNull final DataTable dataTable) throws Throwable
	{
		DataTableRows.of(dataTable).forEach(this::update_M_HU_Storage);
	}

	private void update_M_HU_Storage(@NonNull final DataTableRow tableRow)
	{
		final I_M_HU_Storage huStorage = tableRow.getAsIdentifier(COLUMNNAME_M_HU_Storage_ID).lookupIn(huStorageTable);
		assertThat(huStorage).isNotNull();
		tableRow.getAsOptionalBigDecimal(COLUMNNAME_Qty).ifPresent(huStorage::setQty);
		tableRow.getAsOptionalIdentifier(COLUMNNAME_M_Product_ID)
				.map(productTable::get)
				.map(I_M_Product::getM_Product_ID)
				.ifPresent(huStorage::setM_Product_ID);

		saveRecord(huStorage);
	}
}
