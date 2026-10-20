package de.metas.inoutcandidate.invalidation.impl;

import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegmentBuilder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
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
 * <p>
 * The whole-product batching is performed by the {@code M_ShipmentSchedule_TagToRecompute(numeric, integer)}
 * DB function, invoked via
 * {@link ShipmentScheduleInvalidateRepository#markAllToRecomputeOutOfTrx(de.metas.process.PInstanceId, org.adempiere.ad.dao.QueryLimit)};
 * that batching is covered end-to-end by the {@code updateInvalidShipmentSchedulesRecomputeBatching} Cucumber scenario.
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

	@BeforeEach
	public void beforeEach() throws Exception
	{
		AdempiereTestHelper.get().init();

		repository = new ShipmentScheduleInvalidateRepository();

		buildShipmentScheduleWhereClause = ShipmentScheduleInvalidateRepository.class
				.getDeclaredMethod("buildShipmentScheduleWhereClause", String.class, IShipmentScheduleSegment.class, List.class);
		buildShipmentScheduleWhereClause.setAccessible(true);
	}

	private String buildWhereClause(final IShipmentScheduleSegment segment, final List<Object> sqlParams) throws Exception
	{
		return (String)buildShipmentScheduleWhereClause.invoke(repository, SS_ALIAS, segment, sqlParams);
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

	private static final String OWN_TRX_TERM = "DBTransactionNo = pg_current_xact_id_if_assigned()::text::numeric";

	private String pendingFlagPredicate() throws Exception
	{
		final Method m = ShipmentScheduleInvalidateRepository.class.getDeclaredMethod("sqlNoPendingFlagOfOwnTrx", String.class);
		m.setAccessible(true);
		return (String)m.invoke(repository, "s.M_ShipmentSchedule_ID");
	}

	@Test
	public void pendingFlagPredicate_threadTrx_matchesOwnTrxOnly() throws Exception
	{
		final String[] predicate = new String[1];
		Services.get(ITrxManager.class).runInThreadInheritedTrx(() -> {
			try
			{
				predicate[0] = pendingFlagPredicate();
			}
			catch (final Exception e)
			{
				throw new RuntimeException(e);
			}
		});

		assertThat(predicate[0])
				.as("inside a thread-inherited trx, dedup only against the own transaction's pending flag")
				.contains(OWN_TRX_TERM)
				.contains("e.AD_PInstance_ID is NULL")
				.contains("e.M_ShipmentSchedule_ID=s.M_ShipmentSchedule_ID");
	}

	@Test
	public void pendingFlagPredicate_afterCommitCallback_matchesOwnTrxOnly() throws Exception
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final String[] predicate = new String[1];
		trxManager.runInThreadInheritedTrx(() -> trxManager.runAfterCommit(() -> {
			// like the real transaction, which restarts itself on the first DB access of the callback
			trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone).start();
			try
			{
				predicate[0] = pendingFlagPredicate();
			}
			catch (final Exception e)
			{
				throw new RuntimeException(e);
			}
		}));

		assertThat(predicate[0])
				.as("an after-commit callback runs in a restarted transaction that may write business data before flagging")
				.contains(OWN_TRX_TERM);
	}

	@Test
	public void pendingFlagPredicate_afterCommitCallbackBeforeRestart_isLegacy() throws Exception
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final String[] predicate = new String[1];
		trxManager.runInThreadInheritedTrx(() -> trxManager.runAfterCommit(() -> {
			// no DB access yet, so the trx is not restarted and nothing is uncommitted
			try
			{
				predicate[0] = pendingFlagPredicate();
			}
			catch (final Exception e)
			{
				throw new RuntimeException(e);
			}
		}));

		assertThat(predicate[0])
				.as("before a DB access restarts the trx, an after-commit callback has no open DB transaction")
				.isNotNull()
				.doesNotContain("DBTransactionNo");
	}

	@Test
	public void pendingFlagPredicate_noThreadTrx_isLegacy() throws Exception
	{
		assertThat(pendingFlagPredicate())
				.as("without a thread-inherited trx the legacy any-pending-flag dedup applies")
				.doesNotContain("DBTransactionNo")
				.contains("e.AD_PInstance_ID is NULL")
				.contains("e.M_ShipmentSchedule_ID=s.M_ShipmentSchedule_ID");
	}

	private static IShipmentScheduleSegment segmentWithLocators(final int productId, final int locatorCount)
	{
		final List<Integer> locatorIds = new ArrayList<>();
		for (int i = 1; i <= locatorCount; i++)
		{
			locatorIds.add(productId * 1_000_000 + i);
		}
		return ImmutableShipmentScheduleSegment.builder()
				.productId(productId)
				.bpartnerId(2)
				.locatorIds(locatorIds)
				.build();
	}

	private List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> buildInserts(final List<IShipmentScheduleSegment> segments)
	{
		return repository.buildStorageSegmentsInserts(segments, null, "chunk-uuid", "true");
	}

	private static void assertParamCountMatchesPlaceholders(final ShipmentScheduleInvalidateRepository.StorageSegmentsInsert insert)
	{
		assertThat(insert.getSql().chars().filter(c -> c == '?').count())
				.as("placeholders must match the parameter list")
				.isEqualTo(insert.getSqlParams().size());
	}

	@Test
	public void storageSegmentInserts_aboveParamBudget_areSplit_eachBelowBudget_allSegmentsCovered()
	{
		// 10 segments x (1 product + 1 bpartner + 5000 locators) = 50,020 params > the budget
		final List<IShipmentScheduleSegment> segments = new ArrayList<>();
		for (int productId = 1; productId <= 10; productId++)
		{
			segments.add(segmentWithLocators(productId, 5000));
		}

		final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts = buildInserts(segments);

		assertThat(inserts).as("must be split into several statements").hasSizeGreaterThan(1);
		int covered = 0;
		final List<Object> allParams = new ArrayList<>();
		for (final ShipmentScheduleInvalidateRepository.StorageSegmentsInsert insert : inserts)
		{
			assertThat(insert.getSqlParams().size())
					.as("each statement must stay below the budget")
					.isLessThanOrEqualTo(ShipmentScheduleInvalidateRepository.MAX_SQL_PARAMS_PER_STATEMENT);
			assertParamCountMatchesPlaceholders(insert);
			covered += insert.getSegmentsCount();
			allParams.addAll(insert.getSqlParams());
		}
		assertThat(covered).as("every segment must be in exactly one statement").isEqualTo(segments.size());
		for (final IShipmentScheduleSegment segment : segments)
		{
			assertThat(allParams).as("the locators of every segment must be bound").containsAll(segment.getLocatorIds());
		}
	}

	@Test
	public void storageSegmentInserts_withinBudget_isOneStatement()
	{
		final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts = buildInserts(
				java.util.Arrays.asList(segmentWithLocators(1, 10), segmentWithLocators(2, 10)));

		assertThat(inserts).hasSize(1);
		assertThat(inserts.get(0).getSegmentsCount()).isEqualTo(2);
		assertParamCountMatchesPlaceholders(inserts.get(0));
	}

	@Test
	public void storageSegmentInserts_singleOverBudgetSegment_getsItsOwnStatement_andIsNotDropped()
	{
		final IShipmentScheduleSegment huge = segmentWithLocators(1, ShipmentScheduleInvalidateRepository.MAX_SQL_PARAMS_PER_STATEMENT + 1000);
		final IShipmentScheduleSegment small = segmentWithLocators(2, 10);

		final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts = buildInserts(java.util.Arrays.asList(small, huge));

		assertThat(inserts).hasSize(2);
		assertThat(inserts.stream().mapToInt(ShipmentScheduleInvalidateRepository.StorageSegmentsInsert::getSegmentsCount).sum()).isEqualTo(2);
		assertThat(inserts.get(1).getSqlParams()).containsAll(huge.getLocatorIds());
		inserts.forEach(ShipmentScheduleInvalidateRepositoryTest::assertParamCountMatchesPlaceholders);
	}
}
