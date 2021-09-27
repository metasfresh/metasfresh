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
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
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
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class M_ShipmentSchedule_StepDef
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_C_OrderLine> orderLineTable;
	private final StepDefData<I_C_Order> orderTable;
	private final StepDefData<I_M_ShipmentSchedule> shipmentScheduleTable;

	public M_ShipmentSchedule_StepDef(
			@NonNull final StepDefData<I_C_OrderLine> orderLineTable,
			@NonNull final StepDefData<I_C_Order> orderTable,
			@NonNull final StepDefData<I_M_ShipmentSchedule> shipmentScheduleTable)
	{
		this.orderLineTable = orderLineTable;
		this.orderTable = orderTable;
		this.shipmentScheduleTable = shipmentScheduleTable;
	}

	/**
	 * Match the shipment scheds and load them with their identifier into the shipmentScheduleTable.
	 */
	@Then("^after not more than (.*)s, M_ShipmentSchedules are found:$")
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
}
