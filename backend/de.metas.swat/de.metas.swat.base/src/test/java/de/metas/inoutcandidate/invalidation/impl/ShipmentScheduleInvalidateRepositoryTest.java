package de.metas.inoutcandidate.invalidation.impl;

import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegmentBuilder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import de.metas.process.PInstanceId;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Tests {@link ShipmentScheduleInvalidateRepository#buildShipmentScheduleWhereClause(String, IShipmentScheduleSegment, List)}
 * for the warehouse-derived segment support (warehouse branch + empty-locator guard).
 */
public class ShipmentScheduleInvalidateRepositoryTest
{
	private static final String SS_ALIAS = I_M_ShipmentSchedule.Table_Name + ".";

	/** effective warehouse column, mirroring the production builder */
	private static final String WAREHOUSE_COLUMN =
			"COALESCE(" + SS_ALIAS + I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID
					+ ", " + SS_ALIAS + I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID + ")";

	/** the top-level warehouse predicate emitted by the warehouse branch for a single warehouse (DB.buildSqlList single value -> "col=?") */
	private static final String WAREHOUSE_BRANCH_PREDICATE = "(" + WAREHOUSE_COLUMN + "=?)";

	private static final String LOCATOR_SUBSELECT = "EXISTS (select 1 from " + org.compiere.model.I_M_Locator.Table_Name + " loc";

	private ShipmentScheduleInvalidateRepository repository;
	private Method buildShipmentScheduleWhereClause;
	private Method buildMarkAllToRecomputeSql;

	@BeforeEach
	public void beforeEach() throws Exception
	{
		AdempiereTestHelper.get().init();

		repository = new ShipmentScheduleInvalidateRepository();

		buildShipmentScheduleWhereClause = ShipmentScheduleInvalidateRepository.class
				.getDeclaredMethod("buildShipmentScheduleWhereClause", String.class, IShipmentScheduleSegment.class, List.class);
		buildShipmentScheduleWhereClause.setAccessible(true);

		buildMarkAllToRecomputeSql = ShipmentScheduleInvalidateRepository.class
				.getDeclaredMethod("buildMarkAllToRecomputeSql", PInstanceId.class, QueryLimit.class);
		buildMarkAllToRecomputeSql.setAccessible(true);
	}

	private String buildWhereClause(final IShipmentScheduleSegment segment, final List<Object> sqlParams) throws Exception
	{
		return (String)buildShipmentScheduleWhereClause.invoke(repository, SS_ALIAS, segment, sqlParams);
	}

	private String buildMarkAllToRecomputeSql(final PInstanceId pinstanceId, final QueryLimit maxToProcess) throws Exception
	{
		return (String)buildMarkAllToRecomputeSql.invoke(repository, pinstanceId, maxToProcess);
	}

	@Test
	public void warehouseSegment_matchesByEffectiveWarehouseColumn_andNoLocatorSubselect() throws Exception
	{
		// note: bpartnerId(2) is required so the segment is not "invalid" (see IShipmentScheduleSegment.isInvalid())
		final ImmutableShipmentScheduleSegment segment = new ShipmentScheduleSegmentBuilder()
				.productId(1)
				.bpartnerId(2)
				.warehouseId(WarehouseId.ofRepoId(50))
				.build();

		final List<Object> sqlParams = new ArrayList<>();
		final String whereClause = buildWhereClause(segment, sqlParams);

		assertThat(whereClause)
				.as("warehouse-derived segment must match by the schedule's effective warehouse column")
				.contains(WAREHOUSE_BRANCH_PREDICATE);
		assertThat(whereClause)
				.as("the product branch must still emit its predicate")
				.contains(SS_ALIAS + I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID + "=?");
		assertThat(whereClause)
				.as("warehouse-derived segment must NOT emit the M_Locator EXISTS sub-select")
				.doesNotContain(LOCATOR_SUBSELECT);
		assertThat(sqlParams)
				.as("the warehouse repo-id must be collected as an SQL parameter")
				.contains(50);
	}

	@Test
	public void locatorSegment_stillUsesLocatorSubselect_andNoBareWarehousePredicate() throws Exception
	{
		final ImmutableShipmentScheduleSegment segment = new ShipmentScheduleSegmentBuilder()
				.productId(1)
				.bpartnerId(2)
				.locatorId(555)
				.build();

		final List<Object> sqlParams = new ArrayList<>();
		final String whereClause = buildWhereClause(segment, sqlParams);

		assertThat(whereClause)
				.as("locator segment must still use the M_Locator EXISTS sub-select")
				.contains(LOCATOR_SUBSELECT)
				.contains("loc." + org.compiere.model.I_M_Locator.COLUMNNAME_M_Locator_ID + "=?");
		assertThat(whereClause)
				.as("locator segment must NOT emit a top-level warehouse predicate (locator path unchanged)")
				.doesNotContain(WAREHOUSE_BRANCH_PREDICATE);
		assertThat(sqlParams)
				.as("the locator repo-id must be collected as an SQL parameter")
				.contains(555);
	}

	/**
	 * Tests {@link ShipmentScheduleInvalidateRepository#buildMarkAllToRecomputeSql(PInstanceId, QueryLimit)},
	 * the SQL-building seam behind {@code markAllToRecomputeOutOfTrx}. The method issues raw SQL via
	 * {@code DB.executeUpdateAndThrowExceptionOnFail} on {@code TRXNAME_None}, which this module's JUnit
	 * tests cannot exercise end-to-end (no real-DB harness; POJOWrapper does not back raw SQL) -- so
	 * correctness is verified on the generated SQL shape, mirroring the existing
	 * {@code buildShipmentScheduleWhereClause} tests above.
	 */
	@Test
	public void markAllToRecomputeOutOfTrx_noLimit_keepsCurrentUnboundedStatementVerbatim() throws Exception
	{
		final PInstanceId pinstanceId = PInstanceId.ofRepoId(12345);

		final String sql = buildMarkAllToRecomputeSql(pinstanceId, QueryLimit.NO_LIMIT);

		final String expectedSql = " UPDATE " + I_M_ShipmentSchedule_Recompute.Table_Name + " sr " +
				"SET AD_Pinstance_ID=" + pinstanceId.getRepoId() +
				" FROM (" +
				"	SELECT s.M_ShipmentSchedule_ID " +
				"	FROM M_ShipmentSchedule s " +
				") data " +
				" WHERE data.M_ShipmentSchedule_ID=sr.M_ShipmentSchedule_ID "
				+ " AND AD_PInstance_ID IS NULL";

		assertThat(sql)
				.as("the NO_LIMIT branch must keep the current unbounded statement verbatim")
				.isEqualTo(expectedSql);
	}

	@Test
	public void markAllToRecomputeOutOfTrx_limited_boundsToNDistinctSchedulesInLowestIdOrder_andTagsAllTheirMarkerRows() throws Exception
	{
		final PInstanceId pinstanceId = PInstanceId.ofRepoId(777);
		final int n = 3;

		final String sql = buildMarkAllToRecomputeSql(pinstanceId, QueryLimit.ofInt(n));

		assertThat(sql)
				.as("must tag by schedule id, so ALL duplicate recompute markers of a selected schedule get tagged "
						+ "(not just the first N recompute rows)")
				.contains("sr.M_ShipmentSchedule_ID IN (")
				.as("must select only currently-untagged markers")
				.contains("sr.AD_PInstance_ID IS NULL")
				.as("must select DISTINCT schedule ids, so duplicated markers count once toward the N-schedule bound")
				.contains("SELECT DISTINCT sr2.M_ShipmentSchedule_ID")
				.as("must keep the existence join to M_ShipmentSchedule, preserving the old query's "
						+ "\"only schedules that still exist\" filter")
				.contains("JOIN M_ShipmentSchedule s ON s.M_ShipmentSchedule_ID = sr2.M_ShipmentSchedule_ID")
				.as("must scope the candidate schedule ids to currently-untagged markers too")
				.contains("sr2.AD_PInstance_ID IS NULL")
				.as("must order deterministically by the lowest schedule id first, so a second call advances to the next N")
				.contains("ORDER BY sr2.M_ShipmentSchedule_ID")
				.as("must cap to exactly N distinct schedules")
				.contains("LIMIT " + n)
				.as("must tag with the given pinstance id")
				.contains("SET AD_Pinstance_ID=" + pinstanceId.getRepoId());
	}
}
