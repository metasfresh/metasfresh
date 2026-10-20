package de.metas.inoutcandidate.invalidation.impl;

import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleSegmentBuilder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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

	/** Transactions start lazily: the flag write starts the trx, and the business change follows in it. */
	@Test
	public void pendingFlagPredicate_unstartedThreadTrx_matchesOwnTrxOnly() throws Exception
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final String trxName = trxManager.createTrxName("test-unstarted", true);
		assertThat(trxManager.getTrx(trxName).isActive()).as("setup: created, never started").isFalse();

		final String previousTrxName = trxManager.setThreadInheritedTrxName(trxName);
		try
		{
			assertThat(pendingFlagPredicate())
					.as("an unstarted thread trx is not committed; a flag of another transaction must not suppress ours")
					.contains(OWN_TRX_TERM);
		}
		finally
		{
			trxManager.setThreadInheritedTrxName(previousTrxName);
		}
	}

	/** The out-of-trx path must not query in the thread-inherited trx: that would restart a committed trx that may never commit again. */
	@Test
	public void enqueueShipmentScheduleRecompute_outOfTrx_queriesOutOfTrx()
	{
		final IQueryBL queryBLSpy = Mockito.spy(Services.get(IQueryBL.class));
		Services.registerService(IQueryBL.class, queryBLSpy);
		final ShipmentScheduleInvalidateRepository repositoryWithSpy = new ShipmentScheduleInvalidateRepository();

		repositoryWithSpy.enqueueShipmentScheduleRecompute(Env.getCtx(), ITrx.TRXNAME_None, "chunk-uuid");

		verify(queryBLSpy).createQueryBuilder(eq(I_M_ShipmentSchedule_Recompute.class), any(Properties.class), isNull());
		verify(queryBLSpy, never()).createQueryBuilder(eq(I_M_ShipmentSchedule_Recompute.class), any(Properties.class), eq(ITrx.TRXNAME_ThreadInherited));
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
			assertThat(new java.util.HashSet<>(allParams).containsAll(segment.getLocatorIds())).as("the locators of every segment must be bound").isTrue();
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
	public void storageSegmentInserts_segmentInDangerousBand_isWidened_everyStatementWithinBudget()
	{
		// 33,000 locators + product + bpartner = 33,002 params > 32,767: sending it would kill the connection
		final IShipmentScheduleSegment dangerous = segmentWithLocators(1, 33_000);
		final IShipmentScheduleSegment small = segmentWithLocators(2, 10);

		final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts = buildInserts(java.util.Arrays.asList(small, dangerous));

		final List<Object> allParams = new ArrayList<>();
		for (final ShipmentScheduleInvalidateRepository.StorageSegmentsInsert insert : inserts)
		{
			assertThat(insert.getSqlParams().size()).isLessThanOrEqualTo(ShipmentScheduleInvalidateRepository.MAX_SQL_PARAMS_PER_STATEMENT);
			assertParamCountMatchesPlaceholders(insert);
			allParams.addAll(insert.getSqlParams());
		}
		assertThat(allParams).as("the widened segment must still be flagged by its product").contains(1);
		assertThat(new java.util.HashSet<>(allParams).contains(1_000_001 + 20_000)).as("the locator restriction of the oversized segment is dropped").isFalse();
		assertThat(new java.util.HashSet<>(allParams).containsAll(small.getLocatorIds())).as("the small segment is untouched").isTrue();
	}

	@Test
	public void storageSegmentInserts_segmentWithTooManyProducts_keepsTheSmallerBPartnerDimension_withinBudget()
	{
		final List<Integer> productIds = new ArrayList<>();
		for (int i = 1; i <= 70_000; i++)
		{
			productIds.add(i);
		}
		final IShipmentScheduleSegment huge = ImmutableShipmentScheduleSegment.builder().productIds(productIds).bpartnerId(2).locatorId(3).build();

		final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts = buildInserts(java.util.Collections.singletonList(huge));

		assertStatementsCoverAndWithinBudget(inserts);
		assertThat(inserts).as("the bpartner dimension fits, so one statement").hasSize(1);
		final ShipmentScheduleInvalidateRepository.StorageSegmentsInsert insert = inserts.get(0);
		assertThat(insert.getSqlParams().subList(4, insert.getSqlParams().size()))
				.as("after the 4 statement params only the bpartner is bound: no product id (that would under-invalidate)")
				.containsExactly(2);
		assertThat(insert.getSql()).as("no product predicate").doesNotContain(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID);
	}

	@Test
	public void storageSegmentInserts_segmentWithTooManyProductsAndBPartners_partitionsTheSmallerDimension()
	{
		final List<Integer> productIds = new ArrayList<>();
		final List<Integer> bpartnerIds = new ArrayList<>();
		for (int i = 1; i <= 70_000; i++)
		{
			productIds.add(i);
			bpartnerIds.add(i);
		}
		final IShipmentScheduleSegment huge = ImmutableShipmentScheduleSegment.builder().productIds(productIds).bpartnerIds(bpartnerIds).locatorId(3).build();

		final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts = buildInserts(java.util.Collections.singletonList(huge));

		assertStatementsCoverAndWithinBudget(inserts);
		assertThat(inserts).hasSizeGreaterThan(1);
		final java.util.Set<Object> allParams = new java.util.HashSet<>();
		inserts.forEach(insert -> allParams.addAll(insert.getSqlParams()));
		assertThat(allParams.containsAll(productIds)).isTrue();
	}

	@Test
	public void invalidateStorageSegments_outOfTrx_laterChunkFails_stillEnqueuesForCommittedChunks()
	{
		final ShipmentScheduleInvalidateRepository spy = org.mockito.Mockito.spy(new ShipmentScheduleInvalidateRepository());
		org.mockito.Mockito.doReturn(1).doThrow(new RuntimeException("test: chunk 2 failed")).when(spy).executeStorageSegmentsInsert(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
		org.mockito.Mockito.doNothing().when(spy).enqueueShipmentScheduleRecompute(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());

		final List<IShipmentScheduleSegment> segments = java.util.Arrays.asList(segmentWithLocators(1, 20_000), segmentWithLocators(2, 20_000));

		org.assertj.core.api.Assertions.assertThatThrownBy(() -> spy.invalidateStorageSegments(segments, null))
				.hasMessageContaining("chunk 2 failed");

		org.mockito.Mockito.verify(spy).enqueueShipmentScheduleRecompute(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.isNull(), org.mockito.ArgumentMatchers.any());
	}

	@Test
	public void invalidateStorageSegments_inTrx_laterChunkFails_doesNotEnqueue()
	{
		final ShipmentScheduleInvalidateRepository spy = org.mockito.Mockito.spy(new ShipmentScheduleInvalidateRepository());
		org.mockito.Mockito.doReturn(1).doThrow(new RuntimeException("test: chunk 2 failed")).when(spy).executeStorageSegmentsInsert(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());

		final List<IShipmentScheduleSegment> segments = java.util.Arrays.asList(segmentWithLocators(1, 20_000), segmentWithLocators(2, 20_000));

		org.assertj.core.api.Assertions.assertThatThrownBy(() -> spy.invalidateStorageSegmentsInThreadInheritedTrx(segments))
				.hasMessageContaining("chunk 2 failed");

		org.mockito.Mockito.verify(spy, org.mockito.Mockito.never()).enqueueShipmentScheduleRecompute(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
	}

	@Test
	public void storageSegmentInserts_oversizedBPartnerDimension_isWidened_withinBudget()
	{
		final List<Integer> bpartnerIds = new ArrayList<>();
		for (int i = 1; i <= 33_000; i++)
		{
			bpartnerIds.add(i);
		}
		final IShipmentScheduleSegment segment = ImmutableShipmentScheduleSegment.builder().productId(7).bpartnerIds(bpartnerIds).locatorId(3).build();

		final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts = buildInserts(java.util.Collections.singletonList(segment));

		assertThat(inserts).isNotEmpty();
		final java.util.Set<Object> allParams = new java.util.HashSet<>();
		for (final ShipmentScheduleInvalidateRepository.StorageSegmentsInsert insert : inserts)
		{
			assertThat(insert.getSqlParams().size()).isLessThanOrEqualTo(ShipmentScheduleInvalidateRepository.MAX_SQL_PARAMS_PER_STATEMENT);
			allParams.addAll(insert.getSqlParams());
		}
		assertThat(allParams.contains(7)).as("still flagged by its product").isTrue();
	}

	@Test
	public void storageSegmentInserts_oversizedWarehouseDimension_isWidened_withinBudget()
	{
		final List<Integer> warehouseIds = new ArrayList<>();
		for (int i = 1; i <= 33_000; i++)
		{
			warehouseIds.add(i);
		}
		final IShipmentScheduleSegment segment = ImmutableShipmentScheduleSegment.builder().productId(7).bpartnerId(2).warehouseIds(warehouseIds).build();

		final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts = buildInserts(java.util.Collections.singletonList(segment));

		assertThat(inserts).isNotEmpty();
		final java.util.Set<Object> allParams = new java.util.HashSet<>();
		for (final ShipmentScheduleInvalidateRepository.StorageSegmentsInsert insert : inserts)
		{
			assertThat(insert.getSqlParams().size()).isLessThanOrEqualTo(ShipmentScheduleInvalidateRepository.MAX_SQL_PARAMS_PER_STATEMENT);
			allParams.addAll(insert.getSqlParams());
		}
		assertThat(allParams.contains(7)).as("still flagged by its product").isTrue();
	}

	@Test
	public void storageSegmentInserts_buildsTheWhereClauseOncePerInBudgetSegment()
	{
		final ShipmentScheduleInvalidateRepository spy = org.mockito.Mockito.spy(new ShipmentScheduleInvalidateRepository());
		final List<IShipmentScheduleSegment> segments = new ArrayList<>();
		for (int productId = 1; productId <= 5; productId++)
		{
			segments.add(segmentWithLocators(productId, 3));
		}

		spy.buildStorageSegmentsInserts(segments, null, "chunk-uuid", "true");

		org.mockito.Mockito.verify(spy, org.mockito.Mockito.times(5)).buildShipmentScheduleWhereClause(
				org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.anyList());
	}

	private void assertStatementsCoverAndWithinBudget(final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts)
	{
		assertThat(inserts).as("a widened segment must never be dropped: at least one statement").isNotEmpty();
		for (final ShipmentScheduleInvalidateRepository.StorageSegmentsInsert insert : inserts)
		{
			assertThat(insert.getSqlParams().size()).isLessThanOrEqualTo(ShipmentScheduleInvalidateRepository.MAX_SQL_PARAMS_PER_STATEMENT);
			assertParamCountMatchesPlaceholders(insert);
		}
	}

	@Test
	public void storageSegmentInserts_anyProductWithOversizedBPartners_isNotDropped_keepsBPartnerRestriction()
	{
		final List<Integer> bpartnerIds = new ArrayList<>();
		for (int i = 1; i <= 33_000; i++)
		{
			bpartnerIds.add(i);
		}
		// product 0 = "any product"
		final IShipmentScheduleSegment segment = ImmutableShipmentScheduleSegment.builder().productId(0).bpartnerIds(bpartnerIds).locatorId(3).build();

		final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts = buildInserts(java.util.Collections.singletonList(segment));

		assertStatementsCoverAndWithinBudget(inserts);
		final java.util.Set<Object> allParams = new java.util.HashSet<>();
		inserts.forEach(insert -> allParams.addAll(insert.getSqlParams()));
		assertThat(allParams.containsAll(bpartnerIds)).as("the bpartner restriction is the smallest fitting dimension and is kept").isTrue();
	}

	@Test
	public void storageSegmentInserts_anyProductAnyBPartnerWithOversizedLocators_isNotDropped()
	{
		final IShipmentScheduleSegment segment = ImmutableShipmentScheduleSegment.builder()
				.productId(0)
				.anyBPartner()
				.locatorIds(segmentWithLocators(1, 33_000).getLocatorIds())
				.build();

		final List<ShipmentScheduleInvalidateRepository.StorageSegmentsInsert> inserts = buildInserts(java.util.Collections.singletonList(segment));

		assertStatementsCoverAndWithinBudget(inserts);
		assertThat(inserts).hasSize(1);
		assertThat(inserts.get(0).getSqlParams()).as("only the 4 statement params: nothing restricts the segment").hasSize(4);
		assertThat(inserts.get(0).getSql()).as("match-all clause").contains("(true)");
	}
}
