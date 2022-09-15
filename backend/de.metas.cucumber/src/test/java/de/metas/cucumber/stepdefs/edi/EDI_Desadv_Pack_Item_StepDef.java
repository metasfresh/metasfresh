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

package de.metas.cucumber.stepdefs.edi;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.IQuery;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class EDI_Desadv_Pack_Item_StepDef
{
	private final static Logger logger = LogManager.getLogger(EDI_Desadv_Pack_Item_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EDI_Desadv_Pack_StepDefData packTable;
	private final EDI_Desadv_Pack_Item_StepDefData packItemTable;

	public EDI_Desadv_Pack_Item_StepDef(
			@NonNull final EDI_Desadv_Pack_StepDefData packTable,
			@NonNull final EDI_Desadv_Pack_Item_StepDefData packItemTable)
	{
		this.packTable = packTable;
		this.packItemTable = packItemTable;
	}

	@Given("metasfresh initially has no EDI_Desadv_Pack_Item data")
	public void setupMD_Stock_Data()
	{
		truncateEDIDesadvPackItem();
	}

	@Then("^after not more than (.*)s, the EDI_Desadv_Pack_Item has only the following records:$")
	public void packItemsAreFound(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			packItemIsFound(row, timeoutSec);
		}

		final int storedItemsSize = queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.count();

		final int expectedItemsNo = dataTable.size();

		if (storedItemsSize != expectedItemsNo)
		{
			final StringBuilder message = new StringBuilder();

			message.append("Expected to find: ").append(expectedItemsNo)
					.append("EDI_Desadv_Pack_Item records, but got: ").append(storedItemsSize)
					.append(" See:\n");

			logItemRecords(message);
		}

		assertThat(storedItemsSize).isEqualTo(dataTable.size());
	}

	@Then("^after not more than (.*)s, there are no records in EDI_Desadv_Pack_Item$")
	public void tableIsEmpty(final int timeoutSec) throws InterruptedException
	{
		final Supplier<Boolean> emptyTable = () -> queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.create()
				.count() == 0;

		final StringBuilder message = new StringBuilder();

		message.append("Expected no EDI_Desadv_Pack_Item records, but found the following").append("\n");

		StepDefUtil.tryAndWait(timeoutSec, 500, emptyTable, () -> logItemRecords(message));
	}

	private void logItemRecords(@NonNull final StringBuilder message)
	{
		message.append("EDI_Desadv_Pack_Item records:").append("\n");

		queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_EDI_Desadv_Pack_Item.class)
				.forEach(itemRecord -> message
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_Item_ID).append(" : ").append(itemRecord.getEDI_Desadv_Pack_Item_ID()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID).append(" : ").append(itemRecord.getEDI_Desadv_Pack_ID()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOut_ID).append(" : ").append(itemRecord.getM_InOut_ID()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID).append(" : ").append(itemRecord.getM_InOutLine_ID()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty).append(" : ").append(itemRecord.getMovementQty()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCU).append(" : ").append(itemRecord.getQtyCU()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerLU).append(" : ").append(itemRecord.getQtyCUsPerLU()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyItemCapacity).append(" : ").append(itemRecord.getQtyItemCapacity()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyTU).append(" : ").append(itemRecord.getQtyTU()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for EDI_Desadv_Pack_Item records, see current context: \n" + message);
	}

	private void packItemIsFound(
			@NonNull final Map<String, String> tableRow,
			final int timeoutSec) throws InterruptedException
	{
		final String packIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Integer packID = packTable.getOptional(packIdentifier)
				.map(I_EDI_Desadv_Pack::getEDI_Desadv_Pack_ID)
				.orElseGet(() -> Integer.parseInt(packIdentifier));

		final IQuery<I_EDI_Desadv_Pack_Item> query = queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID, packID)
				.create();

		final Supplier<Boolean> packItemFound = () -> query
				.firstOnly(I_EDI_Desadv_Pack_Item.class) != null;

		StepDefUtil.tryAndWait(timeoutSec, 500, packItemFound, () -> logCurrentContext(packID));

		final BigDecimal movementQty = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty);
		final BigDecimal qtyCU = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCU);
		final BigDecimal qtyCUsPerLU = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerLU);
		final BigDecimal qtyItemCapacity = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyItemCapacity);
		final Integer qtyTu = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyTU);

		final I_EDI_Desadv_Pack_Item desadvPackItemRecord = query.firstNotNull(I_EDI_Desadv_Pack_Item.class);

		assertThat(desadvPackItemRecord.getMovementQty()).isEqualTo(movementQty);
		assertThat(desadvPackItemRecord.getQtyCU()).isEqualTo(qtyCU);
		assertThat(desadvPackItemRecord.getQtyCUsPerLU()).isEqualTo(qtyCUsPerLU);
		assertThat(desadvPackItemRecord.getQtyItemCapacity()).isEqualTo(qtyItemCapacity);
		assertThat(desadvPackItemRecord.getQtyTU()).isEqualTo(qtyTu);

		final String packItemIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_Item_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		packItemTable.put(packItemIdentifier, desadvPackItemRecord);
	}

	private void logCurrentContext(@NonNull final Integer packID)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID).append(" : ").append(packID).append("\n");

		logItemRecords(message);
	}

	private void truncateEDIDesadvPackItem()
	{
		DB.executeUpdateEx("DELETE FROM EDI_Desadv_Pack_Item", ITrx.TRXNAME_None);
	}
}
