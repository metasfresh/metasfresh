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

package de.metas.cucumber.stepdefs.shipmentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID;
import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
public class M_ShipmentSchedule_QtyPicked_StepDef
{
	private final Logger logger = LogManager.getLogger(M_ShipmentSchedule_QtyPicked_StepDef.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);

	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final M_ShipmentSchedule_QtyPicked_StepDefData shipmentScheduleQtyPickedTable;
	private final M_HU_StepDefData huTable;
	private final M_InOutLine_StepDefData shipmentLineTable;

	@And("there are no M_ShipmentSchedule_QtyPicked records created for the following shipment schedules")
	public void validate_no_m_shipmentSchedule_qtyPicked_records(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final ShipmentScheduleId shipmentScheduleId = getShipmentScheduleId(row);
			final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtyPickedRecords = retrieveRecordsOrdered(shipmentScheduleId);
			assertThat(shipmentScheduleQtyPickedRecords).isEmpty();
		});
	}

	@And("validate single M_ShipmentSchedule_QtyPicked record created for shipment schedule")
	@And("validate M_ShipmentSchedule_QtyPicked:")
	public void validateQtyPickedRows(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final ShipmentScheduleId shipmentScheduleId = getShipmentScheduleId(row);
			final List<I_M_ShipmentSchedule_QtyPicked> shipmentScheduleQtyPickedRecords = retrieveRecordsOrdered(shipmentScheduleId);
			assertThat(shipmentScheduleQtyPickedRecords).hasSize(1);

			validateQtyPickedRow(row, shipmentScheduleQtyPickedRecords.get(0));
		});
	}

	@And("^validate M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule identified by (.*)$")
	public void validateQtyPickedRowsByShipmentSchedule(
			@NonNull final String shipmentScheduleIdentifier,
			@NonNull final DataTable dataTable)
	{
		final DataTableRows rows = DataTableRows.of(dataTable);

		final ShipmentScheduleId shipmentScheduleId = shipmentScheduleTable.getId(shipmentScheduleIdentifier);
		try (final IAutoCloseable ignored = SharedTestContext.temporaryPut("shipmentScheduleId", shipmentScheduleId))
		{
			final ImmutableList<I_M_ShipmentSchedule_QtyPicked> records = retrieveRecordsOrdered(shipmentScheduleId);
			assertThat(records).hasSize(rows.size());

			DataTableRows.of(dataTable).forEach((expected, index) -> {
				final I_M_ShipmentSchedule_QtyPicked actual = records.get(index);
				SharedTestContext.put("actual", actual);

				validateQtyPickedRow(expected, actual);
			});
		}
	}

	private ShipmentScheduleId getShipmentScheduleId(final DataTableRow row)
	{
		final StepDefDataIdentifier shipmentScheduleIdentifier = row.getAsIdentifier(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID);
		return shipmentScheduleTable.getId(shipmentScheduleIdentifier);
	}

	private void validateQtyPickedRow(
			@NonNull final DataTableRow expected,
			@NonNull final I_M_ShipmentSchedule_QtyPicked actual)
	{
		logger.info("validateQtyPickedRow: expected={}, actual={}", expected, actual);

		expected.getAsOptionalBigDecimal(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_QtyPicked)
				.ifPresent(qtyPicked -> assertThat(actual.getQtyPicked()).isEqualByComparingTo(qtyPicked));
		expected.getAsOptionalBigDecimal(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_QtyDeliveredCatch)
				.ifPresent(catchWeight -> assertThat(actual.getQtyDeliveredCatch()).isEqualByComparingTo(catchWeight));
		expected.getAsOptionalIdentifier(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_Catch_UOM_ID)
				.ifPresent(uomIdentifier -> {
					final UomId uomIdExpected = uomIdentifier.isNullPlaceholder() ? null : uomDAO.getUomIdByX12DE355(X12DE355.ofCode(uomIdentifier.getAsString()));
					assertThat(UomId.ofRepoIdOrNull(actual.getCatch_UOM_ID())).isEqualTo(uomIdExpected);
				});

		expected.getAsOptionalBoolean(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_IsAnonymousHuPickedOnTheFly)
				.ifPresent(isAnonymousHuPickedOnTheFly -> assertThat(actual.isAnonymousHuPickedOnTheFly()).isEqualTo(isAnonymousHuPickedOnTheFly));

		expected.getAsOptionalIdentifier(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_VHU_ID)
				.ifPresent(huIdentifier -> validateHuId(huIdentifier, HuId.ofRepoIdOrNull(actual.getVHU_ID())));

		expected.getAsOptionalIdentifier(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_TU_HU_ID)
				.ifPresent(huIdentifier -> validateHuId(huIdentifier, HuId.ofRepoIdOrNull(actual.getM_TU_HU_ID())));
		expected.getAsOptionalBigDecimal(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_QtyTU)
				.ifPresent(qtyTU -> assertThat(actual.getQtyTU()).isEqualByComparingTo(qtyTU));

		expected.getAsOptionalIdentifier(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_LU_HU_ID)
				.ifPresent(huIdentifier -> validateHuId(huIdentifier, HuId.ofRepoIdOrNull(actual.getM_LU_HU_ID())));
		expected.getAsOptionalBigDecimal(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_QtyLU)
				.ifPresent(qtyLU -> assertThat(actual.getQtyLU()).isEqualByComparingTo(qtyLU));

		expected.getAsOptionalBoolean(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_Processed)
				.ifPresent(isProcessed -> assertThat(actual.isProcessed()).isEqualTo(isProcessed));
		expected.getAsOptionalIdentifier(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_InOutLine_ID)
				.ifPresent(shipmentLineIdentifier -> validateShipmentLineId(shipmentLineIdentifier, InOutLineId.ofRepoIdOrNull(actual.getM_InOutLine_ID())));
	}

	private void validateHuId(@NonNull final StepDefDataIdentifier expectedHuIdentifier, @Nullable final HuId actualHuId)
	{
		final String actualHuIdentifier = actualHuId != null
				? huTable.getFirstIdentifierById(actualHuId).map(StepDefDataIdentifier::getAsString).orElse("?NEW?")
				: null;

		final String description = "expectedHuIdentifier=" + expectedHuIdentifier + ", actualHuId=" + actualHuId + ", actualHuIdentifier=" + actualHuIdentifier;

		if (expectedHuIdentifier.isNullPlaceholder())
		{
			assertThat(actualHuId).as(description).isNull();
		}
		else
		{
			assertThat(actualHuId).as(description).isNotNull();
			final HuId expectedHuId = huTable.getIdOptional(expectedHuIdentifier).orElse(null);
			if (expectedHuId == null)
			{
				huTable.put(expectedHuIdentifier, handlingUnitsBL.getById(actualHuId));
			}
			else
			{
				assertThat(actualHuId).as(description).isEqualTo(expectedHuId);
			}
		}
	}

	private void validateShipmentLineId(@NonNull final StepDefDataIdentifier expectedShipmentLineIdentifier, @Nullable final InOutLineId actualShipmentLineId)
	{
		if (expectedShipmentLineIdentifier.isNullPlaceholder())
		{
			assertThat(actualShipmentLineId).isNull();
		}
		else
		{
			assertThat(expectedShipmentLineIdentifier).isNotNull();
			final InOutLineId expectedShipmentLineId = shipmentLineTable.getIdOptional(expectedShipmentLineIdentifier).orElse(null);
			if (expectedShipmentLineId == null)
			{
				shipmentLineTable.put(expectedShipmentLineIdentifier, inoutDAO.getLineById(actualShipmentLineId));
			}
			else
			{
				assertThat(actualShipmentLineId).isEqualTo(expectedShipmentLineId);
			}
		}
	}

	@And("^M_ShipmentSchedule_QtyPicked records for M_ShipmentSchedule (.*) can be located in specified order$")
	public void locate_ordered_M_ShipmentSchedule_QtyPicked_records_by_M_ShipmentSchedule(
			@NonNull final String shipmentScheduleIdentifier,
			@NonNull final DataTable dataTable)
	{
		final ShipmentScheduleId shipmentScheduleId = shipmentScheduleTable.getId(shipmentScheduleIdentifier);

		final ImmutableList<I_M_ShipmentSchedule_QtyPicked> records = retrieveRecordsOrdered(shipmentScheduleId);
		assertThat(records).hasSameSizeAs(dataTable.asMaps());

		for (int ssQtyPickedRecordIndex = 0; ssQtyPickedRecordIndex < dataTable.asMaps().size(); ssQtyPickedRecordIndex++)
		{
			final Map<String, String> currentRow = dataTable.asMaps().get(ssQtyPickedRecordIndex);

			final String shipmentScheduleQtyPickedIdentifier = DataTableUtil.extractStringForColumnName(currentRow, COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentScheduleQtyPickedTable.putOrReplace(shipmentScheduleQtyPickedIdentifier, records.get(ssQtyPickedRecordIndex));
		}
	}

	private ImmutableList<I_M_ShipmentSchedule_QtyPicked> retrieveRecordsOrdered(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_QtyPicked.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.orderBy(I_M_ShipmentSchedule_QtyPicked.COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID)
				.create()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	@And("validate M_ShipmentSchedule_QtyPicked by id")
	public void validate_M_ShipmentSchedule_QtyPicked(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked = row.getAsIdentifier(COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID).lookupIn(shipmentScheduleQtyPickedTable);
			SharedTestContext.put("shipmentScheduleQtyPicked", shipmentScheduleQtyPicked);

			validateQtyPickedRow(row, shipmentScheduleQtyPicked);
		});
	}

	@And("^load M_HU as (.*) from M_ShipmentSchedule_QtyPicked identified by (.*)$")
	public void load_HU_from_ShipmentScheduleQtyPicked(
			@NonNull final String huIdentifier,
			@NonNull final String shipmentScheduleQtyPickedIdentifier)
	{
		final I_M_ShipmentSchedule_QtyPicked record = shipmentScheduleQtyPickedTable.get(shipmentScheduleQtyPickedIdentifier);
		final HuId vhuId = HuId.ofRepoIdOrNull(record.getVHU_ID());
		assertThat(vhuId).isNotNull();

		final I_M_HU vhu = handlingUnitsBL.getById(vhuId);
		huTable.put(huIdentifier, vhu);
	}

}