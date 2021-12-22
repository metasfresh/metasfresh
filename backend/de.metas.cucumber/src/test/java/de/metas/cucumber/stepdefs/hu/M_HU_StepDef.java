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

package de.metas.cucumber.stepdefs.hu;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;

import java.util.Map;
import java.util.Optional;

import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_HUStatus;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_IsActive;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_ID;
import static de.metas.handlingunits.model.I_M_HU_Item_Storage.COLUMNNAME_Qty;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_ID;

public class M_HU_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_M_HU> huTable;
	private final M_Product_StepDefData productTable;

	public M_HU_StepDef(
			@NonNull final StepDefData<I_M_HU> huTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.huTable = huTable;
		this.productTable = productTable;
	}

	@And("all the hu data is reset")
	public void reset_data()
	{
		DB.executeUpdateEx("TRUNCATE TABLE m_hu cascade", ITrx.TRXNAME_None);
	}

	@And("^after not more than (.*)s, M_HU are found:$")
	public void is_HU_found(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		for (final Map<String, String> row : table.asMaps())
		{
			findHU(row, timeoutSec);
		}
	}

	@And("M_HU are validated:")
	public void validate_HU(@NonNull final DataTable table)
	{
		for (final Map<String, String> row : table.asMaps())
		{
			validateHU(row);
		}
	}

	@And("M_HU_Storage are validated")
	public void validate_HU_Storage(@NonNull final DataTable table)
	{
		for (final Map<String, String> row : table.asMaps())
		{
			validateHUStorage(row);
		}
	}

	private void validateHU(@NonNull final Map<String, String> row)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_M_HU huRecord = InterfaceWrapperHelper.load(huTable.get(huIdentifier).getM_HU_ID(), I_M_HU.class);

		final String huStatus = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_HUStatus);
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsActive);

		assertThat(huRecord).isNotNull();
		assertThat(huRecord.getHUStatus()).isEqualTo(huStatus);
		assertThat(huRecord.isActive()).isEqualTo(isActive);
	}

	private void findHU(@NonNull final Map<String, String> row, @NonNull final Integer timeoutSec) throws InterruptedException
	{
		final String huStatus = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_HUStatus);
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsActive);

		StepDefUtil.tryAndWait(timeoutSec, 500, this::isHUFound);

		final Optional<I_M_HU> huOptional = getHuRecord();

		assertThat(huOptional).isPresent();
		assertThat(huOptional.get().getHUStatus()).isEqualTo(huStatus);
		assertThat(huOptional.get().isActive()).isEqualTo(isActive);

		huTable.putOrReplace(DataTableUtil.extractRecordIdentifier(row, I_M_HU.COLUMNNAME_M_HU_ID), huOptional.get());
	}

	private boolean isHUFound()
	{
		return getHuRecord().isPresent();
	}

	private Optional<I_M_HU> getHuRecord()
	{
		return queryBL.createQueryBuilder(I_M_HU.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_M_HU.class);
	}

	private void validateHUStorage(@NonNull final Map<String, String> row)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_HU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_M_HU huRecord = huTable.get(huIdentifier);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final Optional<I_M_HU_Storage> huStorageRecord = getHuStorageRecord(huRecord);

		final String qty = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Qty);

		assertThat(huStorageRecord).isPresent();
		assertThat(huStorageRecord.get().getM_Product_ID()).isEqualTo(productRecord.getM_Product_ID());
		assertThat(huStorageRecord.get().getQty()).isEqualTo(qty);
	}

	private Optional<I_M_HU_Storage> getHuStorageRecord(@NonNull final I_M_HU huRecord)
	{
		return queryBL.createQueryBuilder(I_M_HU_Storage.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Storage.COLUMNNAME_M_HU_ID, huRecord.getM_HU_ID())
				.create()
				.firstOnlyOptional(I_M_HU_Storage.class);
	}
}