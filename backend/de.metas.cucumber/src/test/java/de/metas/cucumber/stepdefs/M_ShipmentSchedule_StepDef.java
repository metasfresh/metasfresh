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

package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.inout.InOutId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class M_ShipmentSchedule_StepDef
{
	private final ShipmentService shipmentService = SpringContextHolder.instance.getBean(ShipmentService.class);
	private final IShipmentScheduleInvalidateRepository shipmentScheduleInvalidateRepository = Services.get(IShipmentScheduleInvalidateRepository.class);
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_OrderLine_StepDefData orderLineTable;
	private final C_Order_StepDefData orderTable;
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final M_InOut_StepDefData shipmentTable;

	public M_ShipmentSchedule_StepDef(
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable,
			@NonNull final M_InOut_StepDefData shipmentTable)
	{
		this.orderLineTable = orderLineTable;
		this.orderTable = orderTable;
		this.shipmentScheduleTable = shipmentScheduleTable;
		this.shipmentTable = shipmentTable;
	}

	/**
	 * Match the shipment scheds and load them with their identifier into the shipmentScheduleTable.
	 */
	@And("^after not more than (.*)s, M_ShipmentSchedules are found:$")
	public void thereAreShipmentSchedules(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		// create query per table row; run the queries repeatedly until they succeed or the timeout is exceeded
		// if they succeed, put them into shipmentScheduleTable
		final ShipmentScheduleQueries shipmentScheduleQueries = createShipmentScheduleQueries(dataTable);

		final Supplier<Boolean> shipmentScheduleQueryExecutor = () -> {
			if (shipmentScheduleQueries.isAllDone())
			{
				return true;
			}

			shipmentScheduleTable.putAll(shipmentScheduleQueries.executeAllRemaining());
			return shipmentScheduleQueries.isAllDone();
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, shipmentScheduleQueryExecutor);

		assertThat(shipmentScheduleQueries.isAllDone()).as("Not all M_ShipmentSchedules were created within the %s second timout", timeoutSec).isTrue();
	}

	@And("^the shipment schedule identified by (.*) is processed after not more than (.*) seconds$")
	public void processedShipmentScheduleByIdentifier(@NonNull final String identifier, final int timeoutSec) throws InterruptedException
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(identifier);

		final Supplier<Boolean> isShipmentScheduleProcessed = () -> {

			final I_M_ShipmentSchedule record = queryBL
					.createQueryBuilder(I_M_ShipmentSchedule.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
					.create()
					.firstOnlyNotNull(I_M_ShipmentSchedule.class);

			return record.isProcessed();
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, isShipmentScheduleProcessed);

		assertThat(isShipmentScheduleProcessed.get())
				.as("M_ShipmentSchedules with identifier %s was not processed within the %s second timout", identifier, timeoutSec)
				.isTrue();
	}

	@Then("the shipment-schedule is closed")
	public void assertShipmentScheduleIsClosed(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			assertShipmentScheduleIsClosed(tableRow);
		}
	}

	@And("validate that there are no M_ShipmentSchedule_Recompute records after no more than {int} seconds for order {string}")
	public void validate_no_records(final int timeoutSec, final String orderIdentifier) throws InterruptedException
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final ImmutableList<Integer> shipmentScheduleIds = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.stream()
				.map(I_M_ShipmentSchedule::getM_ShipmentSchedule_ID)
				.collect(ImmutableList.toImmutableList());

		assertThat(shipmentScheduleIds.size()).isGreaterThan(0);

		final Supplier<Boolean> noRecords = () -> {
			final List<I_M_ShipmentSchedule_Recompute> records = queryBL.createQueryBuilder(I_M_ShipmentSchedule_Recompute.class)
					.addInArrayFilter(I_M_ShipmentSchedule_Recompute.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
					.create()
					.list();

			return records.size() == 0;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, noRecords);

		assertThat(noRecords.get())
				.as("There are still records in M_ShipmentSchedules_Recompute after %s second timeout", timeoutSec)
				.isTrue();
	}

	@And("shipment is generated for the following shipment schedule")
	public void generateShipmentForSchedule(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			generateShipmentForSchedule(tableRow);
		}
	}

	@And("update shipment schedules")
	public void update_shipment_schedule(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			alterShipmentSchedule(tableRow);
		}
	}

	@And("^after not more than (.*)s, validate shipment schedules:$")
	public void validate_shipment_schedule(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateShipmentSchedule(timeoutSec, tableRow);
		}
	}

	@And("recompute shipment schedules")
	public void recompute_shipment_schedules(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_ShipmentSchedule shipmentScheduleRecord = shipmentScheduleTable.get(shipmentScheduleIdentifier);

			shipmentScheduleInvalidateRepository.invalidateShipmentSchedules(ImmutableSet.of(ShipmentScheduleId.ofRepoId(shipmentScheduleRecord.getM_ShipmentSchedule_ID())));
		}
	}

	@And("^after not more than (.*)s, shipment schedule is recomputed$")
	public void wait_for_recompute_to_finish(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_ShipmentSchedule shipmentScheduleRecord = shipmentScheduleTable.get(shipmentScheduleIdentifier);

			final Supplier<Boolean> shipmentScheduleWasRecomputed = () -> shipmentScheduleInvalidateRepository.isFlaggedForRecompute(ShipmentScheduleId.ofRepoId(shipmentScheduleRecord.getM_ShipmentSchedule_ID()));

			StepDefUtil.tryAndWait(timeoutSec, 500, shipmentScheduleWasRecomputed);
		}
	}

	private ShipmentScheduleQueries createShipmentScheduleQueries(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		final ShipmentScheduleQueries.ShipmentScheduleQueriesBuilder queries = ShipmentScheduleQueries.builder();
		for (final Map<String, String> tableRow : tableRows)
		{
			final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class);

			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID + ".Identifier");

			final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());

			final int warehouseId = DataTableUtil.extractIntOrMinusOneForColumnName(tableRow, "OPT.Warehouse_ID");

			if (warehouseId > 0)
			{
				queryBuilder.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID, warehouseId);
			}

			final IQuery<I_M_ShipmentSchedule> query = queryBuilder.create();

			final String isToRecompute = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_IsToRecompute);

			final ShipmentScheduleQuery shipmentScheduleQuery = ShipmentScheduleQuery.builder()
					.shipmentScheduleIdentifier(DataTableUtil.extractRecordIdentifier(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID))
					.query(query)
					.isToRecompute(StringUtils.toBoolean(isToRecompute, null))
					.build();
			queries.query(shipmentScheduleQuery);
		}
		return queries.build();
	}

	private void assertShipmentScheduleIsClosed(@NonNull final Map<String, String> tableRow)
	{
		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ".Identifier");
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final I_M_ShipmentSchedule refreshedSchedule = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
				.create()
				.firstOnlyNotNull(I_M_ShipmentSchedule.class);

		assertNotNull(shipmentSchedule);
		assertEquals(Boolean.TRUE, refreshedSchedule.isClosed());
	}

	private void generateShipmentForSchedule(@NonNull final Map<String, String> tableRow)
	{
		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_ShipmentSchedule shipmentScheduleRecord = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final String qtyTypeToUse = DataTableUtil.extractStringForColumnName(tableRow, "quantityTypeToUse");
		final boolean isCompleteShipment = DataTableUtil.extractBooleanForColumnName(tableRow, "isCompleteShipment");

		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentScheduleRecord.getM_ShipmentSchedule_ID());

		final GenerateShipmentsForSchedulesRequest generateShipmentsForSchedulesRequest = GenerateShipmentsForSchedulesRequest.builder()
				.scheduleIds(ImmutableSet.of(shipmentScheduleId))
				.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.ofCode(qtyTypeToUse))
				.isCompleteShipment(isCompleteShipment)
				.build();

		final String shipmentIdCandidate = DataTableUtil.extractStringForColumnName(tableRow, I_M_InOut.COLUMNNAME_M_InOut_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final ImmutableList<String> shipmentIdentifiers = StepDefUtil.extractIdentifiers(shipmentIdCandidate);

		final Set<InOutId> inOutIds = shipmentService.generateShipmentsForScheduleIds(generateShipmentsForSchedulesRequest);

		assertThat(inOutIds.size()).isEqualTo(shipmentIdentifiers.size());

		final List<InOutId> oldestFirstInOutIds = inOutIds.stream()
				.sorted()
				.collect(ImmutableList.toImmutableList());

		for (int shipmentIndex = 0; shipmentIndex < oldestFirstInOutIds.size(); shipmentIndex++)
		{
			shipmentTable.putOrReplace(shipmentIdentifiers.get(shipmentIndex), InterfaceWrapperHelper.load(oldestFirstInOutIds.get(shipmentIndex), I_M_InOut.class));
		}
	}

	private void alterShipmentSchedule(@NonNull final Map<String, String> tableRow)
	{
		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_ShipmentSchedule shipmentScheduleRecord = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final BigDecimal qtyToDeliverOverride = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override);

		shipmentScheduleRecord.setQtyToDeliver_Override(qtyToDeliverOverride);

		saveRecord(shipmentScheduleRecord);
	}

	private void validateShipmentSchedule(final int timeoutSec, @NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered);
		final BigDecimal qtyToDeliver = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver);
		final BigDecimal qtyToDeliverOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override);
		final BigDecimal qtyPicked = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_QtyPickList);
		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_QtyDelivered);
		final boolean isProcessed = DataTableUtil.extractBooleanForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_Processed);

		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + ".Identifier");
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final Supplier<Boolean> isShipmentScheduleFound = () -> queryBL
				.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver, qtyToDeliver)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override, qtyToDeliverOverride)
				.create()
				.firstOnlyOptional(I_M_ShipmentSchedule.class)
				.isPresent();

		StepDefUtil.tryAndWait(timeoutSec, 500, isShipmentScheduleFound);

		InterfaceWrapperHelper.refresh(shipmentSchedule);

		if (qtyToDeliverOverride != null)
		{
			assertThat(shipmentSchedule.getQtyToDeliver_Override().stripTrailingZeros()).isEqualTo(qtyToDeliverOverride.stripTrailingZeros());
		}
		else
		{
			assertThat(InterfaceWrapperHelper.isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override)).isTrue();
		}

		assertThat(shipmentSchedule.getQtyToDeliver().stripTrailingZeros()).isEqualTo(qtyToDeliver.stripTrailingZeros());
		assertThat(shipmentSchedule.getQtyOrdered().stripTrailingZeros()).isEqualTo(qtyOrdered.stripTrailingZeros());
		assertThat(shipmentSchedule.getQtyPickList().stripTrailingZeros()).isEqualTo(qtyPicked.stripTrailingZeros());
		assertThat(shipmentSchedule.getQtyDelivered().stripTrailingZeros()).isEqualTo(qtyDelivered.stripTrailingZeros());
		assertThat(shipmentSchedule.isProcessed()).isEqualTo(isProcessed);
	}

	@Value
	private static class ShipmentScheduleQueries
	{
		ImmutableList<ShipmentScheduleQuery> allQueries;

		HashSet<ShipmentScheduleQuery> remainingQueries;

		@Builder
		private ShipmentScheduleQueries(@Singular final ImmutableList<ShipmentScheduleQuery> queries)
		{
			this.allQueries = queries;
			this.remainingQueries = new HashSet<>(queries);
		}

		public Map<String, I_M_ShipmentSchedule> executeAllRemaining()
		{
			final ImmutableMap.Builder<String, I_M_ShipmentSchedule> result = ImmutableMap.builder();
			for (final ShipmentScheduleQuery query : allQueries)
			{
				if (!remainingQueries.contains(query))
				{
					continue;
				}
				final I_M_ShipmentSchedule shipmentSchedule = query.execute();
				if (shipmentSchedule != null)
				{
					remainingQueries.remove(query);
					result.put(query.shipmentScheduleIdentifier, shipmentSchedule);
				}
			}
			return result.build();
		}

		public boolean isAllDone()
		{
			return remainingQueries.isEmpty();
		}
	}

	@Value
	@Builder
	private static class ShipmentScheduleQuery
	{
		String shipmentScheduleIdentifier;

		IQuery<I_M_ShipmentSchedule> query;

		Boolean isToRecompute;

		IShipmentScheduleInvalidateBL scheduleInvalidateBL = Services.get(IShipmentScheduleInvalidateBL.class);

		@Nullable
		public I_M_ShipmentSchedule execute()
		{
			final I_M_ShipmentSchedule shipmentScheduleRecord = query.firstOnly(I_M_ShipmentSchedule.class);
			if (shipmentScheduleRecord == null)
			{
				return null;
			}

			if (isToRecompute != null)
			{
				final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentScheduleRecord.getM_ShipmentSchedule_ID());
				final boolean flaggedForRecompute = scheduleInvalidateBL.isFlaggedForRecompute(shipmentScheduleId);
				return flaggedForRecompute == isToRecompute ? shipmentScheduleRecord : null;
			}
			return shipmentScheduleRecord;
		}
	}
}
