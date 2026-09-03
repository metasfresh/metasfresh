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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.hu.M_HU_PackagingCode_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item;
import de.metas.inout.InOutLineId;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class EDI_Desadv_Pack_Item_StepDef
{
	private final static Logger logger = LogManager.getLogger(EDI_Desadv_Pack_Item_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EDI_Desadv_Pack_StepDefData packTable;
	private final EDI_Desadv_Pack_Item_StepDefData packItemTable;
	private final EDI_DesadvLine_StepDefData desadvLineTable;
	private final M_InOut_StepDefData shipmentTable;
	private final M_InOutLine_StepDefData shipmentLineTable;
	private final M_HU_PackagingCode_StepDefData huPackagingCodeTable;

	public EDI_Desadv_Pack_Item_StepDef(
			@NonNull final EDI_Desadv_Pack_StepDefData packTable,
			@NonNull final EDI_Desadv_Pack_Item_StepDefData packItemTable,
			@NonNull final EDI_DesadvLine_StepDefData desadvLineTable,
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final M_InOutLine_StepDefData shipmentLineTable,
			@NonNull final M_HU_PackagingCode_StepDefData huPackagingCodeTable)
	{
		this.packTable = packTable;
		this.packItemTable = packItemTable;
		this.desadvLineTable = desadvLineTable;
		this.shipmentTable = shipmentTable;
		this.shipmentLineTable = shipmentLineTable;
		this.huPackagingCodeTable = huPackagingCodeTable;
	}

	@Given("metasfresh initially has no EDI_Desadv_Pack_Item data")
	public void setupMD_Stock_Data()
	{
		truncateEDIDesadvPackItem();
	}

	/**
	 * Inserts EDI_Desadv_Pack_Item records directly into the DB so that tests can inject
	 * pack items with specific quantities (e.g. MovementQty=0) without going through the normal
	 * shipment/DESADV creation flow.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code EDI_Desadv_Pack_Item_ID} – identifier under which the created record is registered</li>
	 *   <li>{@code EDI_Desadv_Pack_ID} – identifier of an existing pack (must be registered in {@link EDI_Desadv_Pack_StepDefData})</li>
	 *   <li>{@code EDI_DesadvLine_ID} – identifier of an existing desadv line (must be registered in {@link EDI_DesadvLine_StepDefData})</li>
	 *   <li>{@code MovementQty} – movement quantity of this pack item</li>
	 *   <li>{@code QtyCUsPerLU} – number of CUs per LU</li>
	 * </ul>
	 *
	 * <p>Optional columns:
	 * <ul>
	 *   <li>{@code M_InOutLine_ID} – identifier or {@code -} for null</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Given metasfresh contains EDI_Desadv_Pack_Item:
	 *   | EDI_Desadv_Pack_Item_ID | EDI_Desadv_Pack_ID | EDI_DesadvLine_ID | MovementQty | QtyCUsPerLU | M_InOutLine_ID |
	 *   | pi_zero_qty             | myPack             | myLine            | 0           | 0           | -              |
	 * </pre>
	 */
	@Given("metasfresh contains EDI_Desadv_Pack_Item:")
	public void metasfresh_contains_edi_desadv_pack_item(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::insertPackItem);
	}

	private void insertPackItem(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier packIdentifier = row.getAsIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID);
		final EDIDesadvPackId packId = packTable.getId(packIdentifier);

		final StepDefDataIdentifier desadvLineIdentifier = row.getAsIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_DesadvLine_ID);
		final I_EDI_DesadvLine desadvLine = desadvLineTable.get(desadvLineIdentifier);

		final BigDecimal movementQty = row.getAsBigDecimal(I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty);
		final BigDecimal qtyCUsPerLU = row.getAsBigDecimal(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerLU);

		final I_EDI_Desadv_Pack_Item packItemRecord = InterfaceWrapperHelper.newInstance(I_EDI_Desadv_Pack_Item.class);
		packItemRecord.setEDI_Desadv_Pack_ID(packId.getRepoId());
		packItemRecord.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
		packItemRecord.setMovementQty(movementQty);
		packItemRecord.setQtyCUsPerLU(qtyCUsPerLU);
		// Line is mandatory (NOT NULL). The injected item only needs a non-null, distinct line value;
		// 9999 won't collide with the generator's low sequential line numbers within the same pack.
		packItemRecord.setLine(row.getAsOptionalInt(I_EDI_Desadv_Pack_Item.COLUMNNAME_Line).orElse(9999));

		row.getAsOptionalIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID)
				.filter(id -> !id.isNullPlaceholder())
				.ifPresent(inOutLineIdentifier -> {
					final InOutLineId inOutLineId = shipmentLineTable.getId(inOutLineIdentifier);
					packItemRecord.setM_InOutLine_ID(inOutLineId.getRepoId());
				});

		InterfaceWrapperHelper.saveRecord(packItemRecord);

		final StepDefDataIdentifier packItemIdentifier = row.getAsIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_Item_ID);
		packItemTable.put(packItemIdentifier, packItemRecord);
	}

	@Then("^after not more than (.*)s, the EDI_Desadv_Pack_Item has only the following records:$")
	public void packItemsAreFound(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		DataTableRows.of(table)
				.forEach(row -> packItemIsFound(row, timeoutSec));

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
			@NonNull final DataTableRow dataTableRow,
			final int timeoutSec) throws InterruptedException
	{
		final StepDefDataIdentifier packIdentifier = dataTableRow.getAsIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID);

		final EDIDesadvPackId packID = packTable.getId(packIdentifier);
		final IQueryBuilder<I_EDI_Desadv_Pack_Item> queryBuilder = queryBL.createQueryBuilder(I_EDI_Desadv_Pack_Item.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID, packID);

		final Optional<BigDecimal> movementQty = dataTableRow.getAsOptionalBigDecimal(I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty);
		movementQty.ifPresent(qty -> queryBuilder.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty, qty));

		final Optional<InOutLineId> inOutLineId = dataTableRow.getAsOptionalIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID).map(shipmentLineTable::getId);
		inOutLineId.ifPresent(
				iolId -> queryBuilder.addEqualsFilter(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID, iolId));

		final Supplier<Boolean> packItemFound = () -> queryBuilder
				.create()
				.firstOnly(I_EDI_Desadv_Pack_Item.class) != null;

		StepDefUtil.tryAndWait(timeoutSec, 500, packItemFound, () -> logCurrentContext(packID, movementQty.orElse(null), inOutLineId.orElse(null)));

		final SoftAssertions softly = new SoftAssertions();
		final I_EDI_Desadv_Pack_Item desadvPackItemRecord = queryBuilder.create().firstOnlyNotNull(I_EDI_Desadv_Pack_Item.class);
		final int packItemId = desadvPackItemRecord.getEDI_Desadv_Pack_Item_ID();

		dataTableRow.getAsOptionalBigDecimal(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerTU)
				.ifPresent(qtyCUsPerTU -> softly
						.assertThat(desadvPackItemRecord.getQtyCUsPerTU())
						.as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyCUsPerTU", packIdentifier, packItemId)
						.isEqualByComparingTo(qtyCUsPerTU));
		dataTableRow.getAsOptionalBigDecimal(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerTU_InInvoiceUOM)
				.ifPresent(qtyCUsPerTU_InInvoiceUOM -> softly
						.assertThat(desadvPackItemRecord.getQtyCUsPerTU_InInvoiceUOM())
						.as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyCUsPerTU_InInvoiceUOM", packIdentifier, packItemId)
						.isEqualByComparingTo(qtyCUsPerTU_InInvoiceUOM));

		dataTableRow.getAsOptionalBigDecimal(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerLU)
				.ifPresent(qtyCUsPerLU -> softly
						.assertThat(desadvPackItemRecord.getQtyCUsPerLU())
						.as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyCUsPerLU", packIdentifier, packItemId)
						.isEqualByComparingTo(qtyCUsPerLU));

		dataTableRow.getAsOptionalBigDecimal(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyCUsPerLU_InInvoiceUOM)
				.ifPresent(qtyCUsPerLU_InInvoiceUOM -> softly
						.assertThat(desadvPackItemRecord.getQtyCUsPerLU_InInvoiceUOM())
						.as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyCUsPerLU_InInvoiceUOM", packIdentifier, packItemId)
						.isEqualByComparingTo(qtyCUsPerLU_InInvoiceUOM));

		dataTableRow.getAsOptionalBigDecimal(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyItemCapacity)
				.ifPresent(qtyItemCapacity -> softly
						.assertThat(desadvPackItemRecord.getQtyItemCapacity())
						.as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyItemCapacity", packIdentifier, packItemId)
						.isEqualByComparingTo(qtyItemCapacity));

		dataTableRow.getAsOptionalInt(I_EDI_Desadv_Pack_Item.COLUMNNAME_QtyTU)
				.ifPresent(qtyTu -> softly
						.assertThat(desadvPackItemRecord.getQtyTU())
						.as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - QtyTU", packIdentifier, packItemId)
						.isEqualByComparingTo(qtyTu));

		dataTableRow.getAsOptionalIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOut_ID)
				.ifPresent(shipmentIdentifier -> softly
						.assertThat(desadvPackItemRecord.getM_InOut_ID())
						.as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s; M_InOut_ID.Identifier=%s - M_InOut_ID", packIdentifier, packItemId, shipmentIdentifier)
						.isEqualTo(shipmentTable.getId(shipmentIdentifier).getRepoId()));

		dataTableRow.getAsOptionalString(I_EDI_Desadv_Pack_Item.COLUMNNAME_LotNumber)
				.ifPresent(lotNumber -> softly
						.assertThat(desadvPackItemRecord.getLotNumber())
						.as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - LotNumber", packIdentifier, packItemId)
						.isEqualTo(DataTableUtil.nullToken2Null(lotNumber)));

		dataTableRow.getAsOptionalString(I_EDI_Desadv_Pack_Item.COLUMNNAME_BestBeforeDate)
				.ifPresent(nullableBestBeforeDateString -> {
					if (DataTableUtil.nullToken2Null(nullableBestBeforeDateString) == null)
					{
						softly.assertThat(desadvPackItemRecord.getBestBeforeDate()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - BestBeforeDate", packIdentifier, packItemId).isNull();
					}
					else
					{
						final Timestamp bestBeforeDateExpected = Timestamp.valueOf(LocalDate.parse(nullableBestBeforeDateString).atStartOfDay());
						softly.assertThat(desadvPackItemRecord.getBestBeforeDate()).as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - BestBeforeDate", packIdentifier, packItemId).isEqualTo(bestBeforeDateExpected);
					}
				});

		dataTableRow.getAsOptionalIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_HU_PackagingCode_TU_ID)
				.ifPresent(huPackagingCodeTuIdentifier -> {

					final int huPackingCodeTuId = huPackagingCodeTuIdentifier.isNullPlaceholder()
							? 0
							: huPackagingCodeTable.get(huPackagingCodeTuIdentifier).getM_HU_PackagingCode_ID();

					softly
							.assertThat(desadvPackItemRecord.getM_HU_PackagingCode_TU_ID())
							.as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - M_HU_PackagingCode_TU_ID", packIdentifier, packItemId)
							.isEqualTo(huPackingCodeTuId);
				});

		dataTableRow.getAsOptionalString(I_EDI_Desadv_Pack_Item.COLUMNNAME_GTIN_TU_PackingMaterial)
				.ifPresent(gtinTuPackingMaterial -> softly
						.assertThat(desadvPackItemRecord.getGTIN_TU_PackingMaterial())
						.as("EDI_Desadv_Pack_ID.Identifier=%s; EDI_Desadv_Pack_Item_ID=%s - GTIN_TU_PackingMaterial", packIdentifier, packItemId)
						.isEqualTo(DataTableUtil.nullToken2Null(gtinTuPackingMaterial)));

		softly.assertAll();

		final StepDefDataIdentifier packItemIdentifier = dataTableRow.getAsIdentifier(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_Item_ID);
		// putOrReplace (not put): a scenario may assert the same pack item at multiple checkpoints
		// (e.g. after complete, then again after re-complete) using the same identifier.
		packItemTable.putOrReplace(packItemIdentifier, desadvPackItemRecord);
	}

	private void logCurrentContext(
			@NonNull final EDIDesadvPackId packID,
			@Nullable final BigDecimal movementQty,
			@Nullable final InOutLineId inOutLineId)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_EDI_Desadv_Pack_ID).append(" : ").append(packID.getRepoId()).append("\n");

		if (movementQty != null)
		{
			message.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_MovementQty).append(" : ").append(movementQty).append("\n");
		}

		if (inOutLineId != null)
		{
			message.append(I_EDI_Desadv_Pack_Item.COLUMNNAME_M_InOutLine_ID).append(" : ").append(inOutLineId.getRepoId()).append("\n");
		}

		logItemRecords(message);
	}

	private void truncateEDIDesadvPackItem()
	{
		DB.executeUpdateAndThrowExceptionOnFail("DELETE FROM EDI_Desadv_Pack_Item", ITrx.TRXNAME_None);
	}
}
