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
import de.metas.cucumber.stepdefs.M_HU_PackagingCode_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
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
	private final M_InOut_StepDefData shipmentTable;
	private final M_InOutLine_StepDefData shipmentLineTable;
	private final M_HU_PackagingCode_StepDefData huPackagingCodeTable;

	public EDI_Desadv_Pack_Item_StepDef(
			@NonNull final EDI_Desadv_Pack_StepDefData packTable,
			@NonNull final EDI_Desadv_Pack_Item_StepDefData packItemTable,
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final M_InOutLine_StepDefData shipmentLineTable,
			@NonNull final M_HU_PackagingCode_StepDefData huPackagingCodeTable)
	{
		this.packTable = packTable;
		this.packItemTable = packItemTable;
		this.shipmentTable = shipmentTable;
		this.shipmentLineTable = shipmentLineTable;
		this.huPackagingCodeTable = huPackagingCodeTable;
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
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerTU).append(" : ").append(itemRecord.getQtyCUsPerTU()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerTU_InInvoiceUOM).append(" : ").append(itemRecord.getQtyCUsPerTU_InInvoiceUOM()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerLU).append(" : ").append(itemRecord.getQtyCUsPerLU()).append(" ; ")
						.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerLU_InInvoiceUOM).append(" : ").append(itemRecord.getQtyCUsPerLU_InInvoiceUOM()).append(" ; ")
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

		final IQueryBuilder<I_EDI_Desadv_Pack_Item> queryBuilder = queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID, packID);

		final BigDecimal movementQty = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty);
		if (movementQty != null)
		{
			queryBuilder.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty, movementQty);
		}

		final Supplier<Boolean> packItemFound = () -> queryBuilder
				.create()
				.firstOnly(I_EDI_Desadv_Pack_Item.class) != null;

		StepDefUtil.tryAndWait(timeoutSec, 500, packItemFound, () -> logCurrentContext(packID, movementQty));

		final BigDecimal qtyCUsPerTU = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerTU);
		final BigDecimal qtyCUsPerTU_InInvoiceUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerTU_InInvoiceUOM);
		final BigDecimal qtyCUsPerLU = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerLU);
		final BigDecimal qtyCUsPerLU_InInvoiceUOM = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerLU_InInvoiceUOM);
		final BigDecimal qtyItemCapacity = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyItemCapacity);
		final Integer qtyTu = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyTU);

		final I_EDI_Desadv_Pack_Item desadvPackItemRecord = queryBuilder.create().firstOnlyNotNull(I_EDI_Desadv_Pack_Item.class);
		final int packItemId = desadvPackItemRecord.getEDI_Desadv_Pack_Item_ID();

		final SoftAssertions softly = new SoftAssertions();
		if (qtyCUsPerTU != null)
		{
			softly.assertThat(desadvPackItemRecord.getQtyCUsPerTU()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyCUsPerTU", packIdentifier, packItemId).isEqualByComparingTo(qtyCUsPerTU);
		}

		if (qtyCUsPerTU_InInvoiceUOM != null)
		{
			softly.assertThat(desadvPackItemRecord.getQtyCUsPerTU_InInvoiceUOM()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyCUsPerTU_InInvoiceUOM", packIdentifier, packItemId).isEqualByComparingTo(qtyCUsPerTU_InInvoiceUOM);
		}

		if (qtyCUsPerLU != null)
		{
			softly.assertThat(desadvPackItemRecord.getQtyCUsPerLU()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyCUsPerLU", packIdentifier, packItemId).isEqualByComparingTo(qtyCUsPerLU);
		}

		if (qtyCUsPerLU_InInvoiceUOM != null)
		{
			softly.assertThat(desadvPackItemRecord.getQtyCUsPerLU_InInvoiceUOM()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyCUsPerLU_InInvoiceUOM", packIdentifier, packItemId).isEqualByComparingTo(qtyCUsPerLU_InInvoiceUOM);
		}

		if (qtyItemCapacity != null)
		{
			softly.assertThat(desadvPackItemRecord.getQtyItemCapacity()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyItemCapacity", packIdentifier, packItemId).isEqualByComparingTo(qtyItemCapacity);
		}

		if (qtyTu != null)
		{
			softly.assertThat(desadvPackItemRecord.getQtyTU()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyTU", packIdentifier, packItemId).isEqualByComparingTo(qtyTu);
		}

		final String shipmentIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOut_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(shipmentIdentifier))
		{
			final I_M_InOut shipmentRecord = shipmentTable.get(shipmentIdentifier);

			softly.assertThat(desadvPackItemRecord.getM_InOut_ID()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - M_InOut_ID", packIdentifier, packItemId).isEqualTo(shipmentRecord.getM_InOut_ID());
		}

		final String shipmentLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(shipmentLineIdentifier))
		{
			final I_M_InOutLine shipmentLine = shipmentLineTable.get(shipmentLineIdentifier);

			softly.assertThat(desadvPackItemRecord.getM_InOutLine_ID()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - M_InOutLine_ID", packIdentifier, packItemId).isEqualTo(shipmentLine.getM_InOutLine_ID());
		}

		final String lotNumber = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_LotNumber);
		if (Check.isNotBlank(lotNumber))
		{
			softly.assertThat(desadvPackItemRecord.getLotNumber()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - LotNumber", packIdentifier, packItemId).isEqualTo(DataTableUtil.nullToken2Null(lotNumber));
		}

		final String nullableBestBeforeDateString = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_BestBeforeDate);
		if (Check.isNotBlank(nullableBestBeforeDateString))
		{
			if (DataTableUtil.nullToken2Null(nullableBestBeforeDateString) == null)
			{
				softly.assertThat(desadvPackItemRecord.getBestBeforeDate()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - BestBeforeDate", packIdentifier, packItemId).isNull();
			}
			else
			{
				softly.assertThat(desadvPackItemRecord.getBestBeforeDate()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - BestBeforeDate", packIdentifier, packItemId).isEqualTo(TimeUtil.parseTimestamp(nullableBestBeforeDateString));
			}
		}

		final String huPackagingCodeTuIdentifier = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_M_HU_PackagingCode_TU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(huPackagingCodeTuIdentifier))
		{
			final int huPackingCodeTuId = DataTableUtil.nullToken2Null(huPackagingCodeTuIdentifier) == null
					? 0
					: huPackagingCodeTable.get(huPackagingCodeTuIdentifier).getM_HU_PackagingCode_ID();

			softly.assertThat(desadvPackItemRecord.getM_HU_PackagingCode_TU_ID()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - M_HU_PackagingCode_TU_ID", packIdentifier, packItemId).isEqualTo(huPackingCodeTuId);
		}

		final String gtinTuPackingMaterial = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_EDI_Desadv_Pack_Item.COLUMNNAME_GTIN_TU_PackingMaterial);
		if (Check.isNotBlank(gtinTuPackingMaterial))
		{
			softly.assertThat(desadvPackItemRecord.getGTIN_TU_PackingMaterial()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - GTIN_TU_PackingMaterial", packIdentifier, packItemId).isEqualTo(DataTableUtil.nullToken2Null(gtinTuPackingMaterial));
		}

		softly.assertAll();

		final String packItemIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_Item_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		packItemTable.put(packItemIdentifier, desadvPackItemRecord);
	}

	private void logCurrentContext(
			@NonNull final Integer packID,
			@Nullable final BigDecimal movementQty)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID).append(" : ").append(packID).append("\n");

		if (movementQty != null)
		{
			message.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty).append(" : ").append(movementQty).append("\n");
		}

		logItemRecords(message);
	}

	private void truncateEDIDesadvPackItem()
	{
		DB.executeUpdateAndThrowExceptionOnFail("DELETE FROM EDI_Desadv_Pack_Item", ITrx.TRXNAME_None);
	}
}
