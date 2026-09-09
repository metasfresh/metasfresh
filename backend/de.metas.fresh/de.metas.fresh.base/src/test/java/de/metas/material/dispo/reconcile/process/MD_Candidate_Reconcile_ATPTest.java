package de.metas.material.dispo.reconcile.process;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.reconcile.AtpDivergence;
import de.metas.material.dispo.reconcile.AtpReconciliationCommand;
import de.metas.material.dispo.reconcile.AtpReconciliationRunLog;
import de.metas.material.dispo.reconcile.AtpTargetCalculator;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfo;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Exercises the batched-drain loop in {@link MD_Candidate_Reconcile_ATP#doIt()} directly - the seam the class's own
 * Javadoc says exists for exactly this ("package-private methods so the per-key reconciliation step can be
 * exercised from a test in this package without going through the process framework"). {@code doIt()} itself is
 * {@code protected}, which a test in the same package may call directly; {@link StockRepository} is swapped for a
 * Mockito mock registered via {@link SpringContextHolder#registerJUnitBean(Class, Object)} so no real DB or Spring
 * context is needed - this mirrors the JavaProcess unit-test pattern already used in this codebase (see
 * {@code de.metas.bpartner.process.CBPartnerUpdateMemoTest}: {@code process.init(ProcessInfo...)} then reflection
 * onto the {@code @Param} field, then {@code doIt()} called directly).
 * <p>
 * {@link AtpReconciliationCommand} is faked with a plain subclass ({@link FakeReconciliationCommand}) instead of a
 * Mockito mock: the {@code MAX_LOOPS} test below drives it through 10,000 * 500 = 5,000,000 calls, and a Mockito
 * mock keeps every one of those invocations in memory for potential verification, which reliably blows the test
 * JVM's heap ("GC overhead limit exceeded", reproduced while writing this test) - a plain method override has no
 * such bookkeeping.
 * <p>
 * Before this test existed, the {@code BATCH_SIZE}/{@code MAX_LOOPS} pagination logic was exercised by nothing at
 * any layer: the cucumber scenarios for this process never approach 500 rows, so a selection spanning more than one
 * page, and the {@code MAX_LOOPS} backstop, were both untested.
 */
class MD_Candidate_Reconcile_ATPTest
{
	private static final ClientId CLIENT_ID = ClientId.ofRepoId(1000000);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1000001);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(1000002);

	/** {@code MD_Candidate_Reconcile_ATP.BATCH_SIZE} - kept in lock-step with that private constant. */
	private static final int BATCH_SIZE = 500;

	/** {@code MD_Candidate_Reconcile_ATP.MAX_LOOPS} - kept in lock-step with that private constant. */
	private static final int MAX_LOOPS = 10_000;

	private static final AtpReconciliationRunLog NO_DIVERGENCE =
			AtpReconciliationRunLog.empty(AtpDivergence.of(BigDecimal.ZERO, BigDecimal.ZERO));

	private StockRepository stockRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		stockRepository = Mockito.mock(StockRepository.class);
		SpringContextHolder.registerJUnitBean(StockRepository.class, stockRepository);
	}

	@Test
	void selectionLargerThanOneBatch_pagesThroughEveryKeyExactlyOnce() throws Exception
	{
		// 750 = one full batch (BATCH_SIZE) plus a partial second page, so pagination MUST advance for the second
		// page's 250 keys to be seen at all
		final List<StockDataRecordIdentifier> allKeys = distinctKeys(BATCH_SIZE + 250);

		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenAnswer(invocation -> {
					final int limit = invocation.getArgument(3);
					final int offset = invocation.getArgument(4);
					final int from = Math.min(offset, allKeys.size());
					final int to = Math.min(offset + limit, allKeys.size());
					return ImmutableList.copyOf(allKeys.subList(from, to));
				});

		final FakeReconciliationCommand reconciliationCommand = new FakeReconciliationCommand(NO_DIVERGENCE, true);
		SpringContextHolder.registerJUnitBean(AtpReconciliationCommand.class, reconciliationCommand);

		final MD_Candidate_Reconcile_ATP process = newProcess();

		final String result = process.doIt();

		assertThat(result).isEqualTo(JavaProcess.MSG_OK);

		// retrieveKeys must have been called for offset 0 (full batch) and offset BATCH_SIZE (the trailing partial
		// batch, which is what actually stops the loop) - no more, no fewer
		final ArgumentCaptor<Integer> offsetCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(stockRepository, times(2)).retrieveKeys(any(), any(), any(), anyInt(), offsetCaptor.capture());
		assertThat(offsetCaptor.getAllValues()).containsExactly(0, BATCH_SIZE);

		// every key handed out by the fake repository must have reached reconcileOneKey exactly once: nothing
		// silently truncated (all 750 seen) and nothing double-processed (no key seen twice)
		final List<StockDataRecordIdentifier> processedKeys = reconciliationCommand.getKeysSeen();
		assertThat(processedKeys).hasSize(allKeys.size());
		assertThat(new HashSet<>(processedKeys)).hasSize(allKeys.size()); // no duplicates
		assertThat(processedKeys).containsExactlyInAnyOrderElementsOf(allKeys); // nothing missing, nothing foreign
	}

	/**
	 * Pins the regression the previous commit fixed: a run in a JVM without the material disposition engine must
	 * fail fast even when the selection matches no key at all - never report the misleading "Reconciled 0 of 0
	 * matching key(s)" success that a per-key-only bean resolution would produce (the loop body never executes, so
	 * the bean would never be resolved and the missing engine would never surface).
	 * <p>
	 * Neither of the two tests above can reproduce this: both register a fake {@link AtpReconciliationCommand} as a
	 * JUnit bean before calling {@code doIt()} (see {@link SpringContextHolder#registerJUnitBean(Class, Object)}), so
	 * the bean is always present. Reproducing the actual defect needs {@link SpringContextHolder#instance}{@code
	 * .getBean(AtpReconciliationCommand.class)} to hit its {@code catch (NoSuchBeanDefinitionException e)} branch in
	 * {@code reconciliationCommand()} - and {@link SpringContextHolder#getBean} only ever throws that specific
	 * exception when a real, non-null {@link org.springframework.context.ApplicationContext} is registered on the
	 * holder and genuinely lacks the bean; with no context registered at all it throws a plain,
	 * uncaught {@link AdempiereException} instead ("This unit test requires a spring ApplicationContext"), which
	 * would escape {@code doIt()} without ever exercising the fix.
	 * <p>
	 * So this test builds a real, profile-less {@link AnnotationConfigApplicationContext} scanning
	 * {@code de.metas.material.dispo.reconcile} - the webapi's actual situation, exactly as
	 * {@link de.metas.material.dispo.AtpReconcileContextStartupTest#webapiLikeContext_startsWithoutTheMaterialDispoEngine()}
	 * proves it for the package as a whole - and registers it on {@link SpringContextHolder#instance} for the
	 * duration of the call. {@link AtpReconciliationCommand} is {@code @Profile}-guarded, so that context genuinely
	 * has no such bean, and {@code context.getBean(AtpReconciliationCommand.class)} throws Spring's own
	 * {@code NoSuchBeanDefinitionException} - the exact branch {@code reconciliationCommand()} is written to catch.
	 * <p>
	 * <b>Why the mocks are registered as plain singletons, not via a {@code @Configuration} class of this test's
	 * own.</b> {@link de.metas.material.dispo.AtpReconcileContextStartupTest}'s Javadoc records that its first
	 * version placed such a config <i>inside</i> the scanned package, where the recursive
	 * {@link org.springframework.context.annotation.ComponentScan} picked it up and silently supplied the very bean
	 * whose absence the test exists to prove. This test class sits in {@code
	 * de.metas.material.dispo.reconcile.process} - itself under the scanned package, because {@code doIt()} is only
	 * callable from the same package - so a {@code @Configuration}/{@code @Component} class declared here would fall
	 * into exactly that trap. Registering the collaborator mocks as plain (unannotated) singleton beans instead means
	 * nothing in this file carries an annotation the scan could ever find.
	 */
	@Test
	void profileLessJvm_failsFastEvenWhenSelectionMatchesNothing() throws Exception
	{
		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenReturn(ImmutableList.of());

		try (final AnnotationConfigApplicationContext profileLessContext = newProfileLessReconcilePackageContext())
		{
			SpringContextHolder.instance.setApplicationContext(profileLessContext);
			try
			{
				final MD_Candidate_Reconcile_ATP process = newProcess();

				assertThatThrownBy(process::doIt)
						.as("a profile-less JVM must fail fast even on an empty selection - never reach the"
								+ " '0 of 0' success return")
						.isInstanceOf(AdempiereException.class)
						.hasMessageContaining(Profiles.PROFILE_MaterialDispo);
			}
			finally
			{
				SpringContextHolder.instance.clearApplicationContext();
			}
		}
	}

	/**
	 * @return a real, profile-less context scanning {@code de.metas.material.dispo.reconcile} - see this test's own
	 * Javadoc for why its two collaborator mocks are registered as plain singletons rather than through a
	 * {@code @Configuration} class of this test's own.
	 */
	private static AnnotationConfigApplicationContext newProfileLessReconcilePackageContext()
	{
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getBeanFactory().registerSingleton("stockRepository", Mockito.mock(StockRepository.class));
		context.getBeanFactory().registerSingleton("candidateRepositoryRetrieval", Mockito.mock(CandidateRepositoryRetrieval.class));
		context.scan("de.metas.material.dispo.reconcile");
		context.refresh();
		return context;
	}

	@Test
	void selectionThatNeverShrinksBelowAFullBatch_abortsAfterMaxLoops() throws Exception
	{
		// a pagination bug that never advances (e.g. a stuck OFFSET): every page comes back full, so the drain loop
		// never sees the batch.size() < BATCH_SIZE signal that would end it normally
		final List<StockDataRecordIdentifier> fullBatch = distinctKeys(BATCH_SIZE);
		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenReturn(ImmutableList.copyOf(fullBatch));

		// no key recording here: 10,000 rounds * 500 keys/round = 5,000,000 calls, and retaining that many keys
		// would itself blow the test heap
		final FakeReconciliationCommand reconciliationCommand = new FakeReconciliationCommand(NO_DIVERGENCE, false);
		SpringContextHolder.registerJUnitBean(AtpReconciliationCommand.class, reconciliationCommand);

		final MD_Candidate_Reconcile_ATP process = newProcess();

		assertThatThrownBy(process::doIt)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("aborted after " + MAX_LOOPS)
				.hasMessageContaining("never shrank below a full batch");

		// exactly MAX_LOOPS rounds must have been drawn before the backstop fires - not one more, not one fewer
		verify(stockRepository, times(MAX_LOOPS)).retrieveKeys(any(), any(), any(), anyInt(), anyInt());
		assertThat(reconciliationCommand.getCallCount()).isEqualTo(((long) MAX_LOOPS) * BATCH_SIZE);
	}

	private static MD_Candidate_Reconcile_ATP newProcess() throws Exception
	{
		final MD_Candidate_Reconcile_ATP process = new MD_Candidate_Reconcile_ATP();
		process.init(ProcessInfo.builder().setCtx(Env.getCtx()).build());

		final Field isDryRunField = MD_Candidate_Reconcile_ATP.class.getDeclaredField("p_IsDryRun");
		isDryRunField.setAccessible(true);
		isDryRunField.set(process, false);

		return process;
	}

	private static List<StockDataRecordIdentifier> distinctKeys(final int count)
	{
		final List<StockDataRecordIdentifier> keys = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
		{
			keys.add(StockDataRecordIdentifier.builder()
					.clientId(CLIENT_ID)
					.orgId(ORG_ID)
					.warehouseId(WAREHOUSE_ID)
					.productId(ProductId.ofRepoId(2_000_000 + i))
					.storageAttributesKey(AttributesKey.NONE)
					.build());
		}
		return keys;
	}

	/**
	 * A plain (non-Mockito) test double for {@link AtpReconciliationCommand} - see the class Javadoc for why a
	 * Mockito mock is unsuitable at the call volume {@link #selectionThatNeverShrinksBelowAFullBatch_abortsAfterMaxLoops()}
	 * drives it through. The constructor args are never exercised, since {@link #reconcileAndLog} is fully
	 * overridden below.
	 */
	private static final class FakeReconciliationCommand extends AtpReconciliationCommand
	{
		private final AtpReconciliationRunLog resultToReturn;
		private final boolean recordKeys;
		private final List<StockDataRecordIdentifier> keysSeen = new ArrayList<>();
		private long callCount;

		FakeReconciliationCommand(@NonNull final AtpReconciliationRunLog resultToReturn, final boolean recordKeys)
		{
			super(
					AtpTargetCalculator.newInstanceForUnitTesting(),
					Mockito.mock(CandidateChangeService.class),
					Mockito.mock(CandidateRepositoryRetrieval.class));
			this.resultToReturn = resultToReturn;
			this.recordKeys = recordKeys;
		}

		@Override
		public AtpReconciliationRunLog reconcileAndLog(
				@NonNull final StockDataRecordIdentifier key,
				@NonNull final Instant date,
				final boolean dryRun,
				@Nullable final Instant livenessCutoff)
		{
			callCount++;
			if (recordKeys)
			{
				keysSeen.add(key);
			}
			return resultToReturn;
		}

		List<StockDataRecordIdentifier> getKeysSeen()
		{
			return keysSeen;
		}

		long getCallCount()
		{
			return callCount;
		}
	}
}
