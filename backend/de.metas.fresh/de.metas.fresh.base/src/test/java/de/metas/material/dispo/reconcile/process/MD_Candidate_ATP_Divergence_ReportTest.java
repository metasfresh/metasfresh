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
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.reconcile.AtpDivergence;
import de.metas.material.dispo.reconcile.AtpTargetCalculator;
import de.metas.material.dispo.reconcile.SourceDocumentLivenessService;
import de.metas.material.dispo.reconcile.SourceDocumentRepository;
import de.metas.material.dispo.reconcile.UncoveredSourceDocumentService;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Exercises the batched-drain loops in {@link MD_Candidate_ATP_Divergence_Report#doIt()} directly, the same seam
 * the sibling {@link MD_Candidate_Reconcile_ATPTest} exercises: {@code doIt()} is {@code protected}, callable
 * directly from a test in this package, with every collaborator swapped for a test double registered via
 * {@link SpringContextHolder#registerJUnitBean(Class, Object)} - no real DB or Spring context needed.
 * <p>
 * {@link #keySelectionThatNeverShrinksBelowAFullBatch_abortsAfterMaxLoops()} drives {@code computeDivergence}
 * through {@code MAX_LOOPS * BATCH_SIZE} = 5,000,000 calls, so - exactly as documented on the sibling
 * {@link MD_Candidate_Reconcile_ATPTest} - {@link AtpTargetCalculator} is faked with a plain subclass
 * ({@link FakeAtpTargetCalculator}) there instead of a Mockito mock: a mock keeps every invocation in memory for
 * potential verification, which reliably blows the test JVM's heap ("GC overhead limit exceeded", reproduced
 * while writing this test). The other two tests stay well under any volume where that matters, so they use an
 * ordinary Mockito mock.
 * <p>
 * Before this test existed, the {@code BATCH_SIZE}/{@code MAX_LOOPS} pagination logic of this brand-new process
 * was exercised by nothing at any layer.
 */
class MD_Candidate_ATP_Divergence_ReportTest
{
	private static final ClientId CLIENT_ID = ClientId.ofRepoId(1000000);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1000001);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(1000002);

	/** {@code MD_Candidate_ATP_Divergence_Report.BATCH_SIZE} - kept in lock-step with that private constant. */
	private static final int BATCH_SIZE = 500;

	/** {@code MD_Candidate_ATP_Divergence_Report.MAX_LOOPS} - kept in lock-step with that private constant. */
	private static final int MAX_LOOPS = 10_000;

	private StockRepository stockRepository;
	private UncoveredSourceDocumentService uncoveredSourceDocumentService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		stockRepository = Mockito.mock(StockRepository.class);
		SpringContextHolder.registerJUnitBean(StockRepository.class, stockRepository);

		uncoveredSourceDocumentService = Mockito.mock(UncoveredSourceDocumentService.class);
		SpringContextHolder.registerJUnitBean(UncoveredSourceDocumentService.class, uncoveredSourceDocumentService);

		// the two uncovered-document selections are irrelevant to the key-pagination tests below; default them
		// to empty so those tests only exercise the retrieveKeys/computeDivergence pagination
		when(uncoveredSourceDocumentService.retrieveOpenShipmentScheduleIdsWithoutCandidate(any(), any(), any(), anyInt(), anyInt()))
				.thenReturn(ImmutableList.of());
		when(uncoveredSourceDocumentService.retrieveOpenReceiptScheduleIdsWithoutCandidate(any(), any(), any(), anyInt(), anyInt()))
				.thenReturn(ImmutableList.of());
	}

	@Test
	void keySelectionLargerThanOneBatch_computesDivergenceForEveryKeyExactlyOnce() throws Exception
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

		// 750 calls - safe as an ordinary Mockito mock (see the class Javadoc for why the MAX_LOOPS test below
		// cannot use one)
		final AtpTargetCalculator atpTargetCalculator = Mockito.mock(AtpTargetCalculator.class);
		SpringContextHolder.registerJUnitBean(AtpTargetCalculator.class, atpTargetCalculator);
		when(atpTargetCalculator.computeDivergence(any(), any()))
				.thenReturn(AtpDivergence.of(BigDecimal.ZERO, BigDecimal.ZERO));

		final MD_Candidate_ATP_Divergence_Report process = newProcess();

		final String result = process.doIt();

		assertThat(result).isEqualTo(JavaProcess.MSG_OK);

		// retrieveKeys must have been called for offset 0 (full batch) and offset BATCH_SIZE (the trailing partial
		// batch, which is what actually stops the loop) - no more, no fewer
		final ArgumentCaptor<Integer> offsetCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(stockRepository, times(2)).retrieveKeys(any(), any(), any(), anyInt(), offsetCaptor.capture());
		assertThat(offsetCaptor.getAllValues()).containsExactly(0, BATCH_SIZE);

		// every key handed out by the fake repository must have reached computeDivergence exactly once: nothing
		// silently truncated
		verify(atpTargetCalculator, times(allKeys.size())).computeDivergence(any(), any());
	}

	@Test
	void keySelectionThatNeverShrinksBelowAFullBatch_abortsAfterMaxLoops() throws Exception
	{
		// a pagination bug that never advances (e.g. a stuck OFFSET): every page comes back full, so the drain loop
		// never sees the batch.size() < BATCH_SIZE signal that would end it normally
		final List<StockDataRecordIdentifier> fullBatch = distinctKeys(BATCH_SIZE);
		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenReturn(ImmutableList.copyOf(fullBatch));

		// no key recording here: 10,000 rounds * 500 keys/round = 5,000,000 calls, and a Mockito mock would keep
		// every one of those invocations in memory for potential verification - see the class Javadoc
		final FakeAtpTargetCalculator atpTargetCalculator = new FakeAtpTargetCalculator();
		SpringContextHolder.registerJUnitBean(AtpTargetCalculator.class, atpTargetCalculator);

		final MD_Candidate_ATP_Divergence_Report process = newProcess();

		assertThatThrownBy(process::doIt)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("aborted after " + MAX_LOOPS)
				.hasMessageContaining("never shrank below a full batch");

		// exactly MAX_LOOPS rounds must have been drawn before the backstop fires - not one more, not one fewer
		verify(stockRepository, times(MAX_LOOPS)).retrieveKeys(any(), any(), any(), anyInt(), anyInt());
		assertThat(atpTargetCalculator.getCallCount()).isEqualTo(((long) MAX_LOOPS) * BATCH_SIZE);
	}

	@Test
	void uncoveredDocumentSelectionLargerThanOneBatch_reportsEveryIdExactlyOnce() throws Exception
	{
		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenReturn(ImmutableList.of());

		final List<Integer> allShipmentScheduleIds = new ArrayList<>();
		for (int i = 0; i < BATCH_SIZE + 250; i++)
		{
			allShipmentScheduleIds.add(2_000_000 + i);
		}
		when(uncoveredSourceDocumentService.retrieveOpenShipmentScheduleIdsWithoutCandidate(any(), any(), any(), anyInt(), anyInt()))
				.thenAnswer(invocation -> {
					final int limit = invocation.getArgument(3);
					final int offset = invocation.getArgument(4);
					final int from = Math.min(offset, allShipmentScheduleIds.size());
					final int to = Math.min(offset + limit, allShipmentScheduleIds.size());
					return ImmutableList.copyOf(allShipmentScheduleIds.subList(from, to));
				});

		final MD_Candidate_ATP_Divergence_Report process = newProcess();

		final String result = process.doIt();

		assertThat(result).isEqualTo(JavaProcess.MSG_OK);

		final ArgumentCaptor<Integer> offsetCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(uncoveredSourceDocumentService, times(2))
				.retrieveOpenShipmentScheduleIdsWithoutCandidate(any(), any(), any(), anyInt(), offsetCaptor.capture());
		assertThat(offsetCaptor.getAllValues()).containsExactly(0, BATCH_SIZE);
	}

	private static MD_Candidate_ATP_Divergence_Report newProcess() throws Exception
	{
		final MD_Candidate_ATP_Divergence_Report process = new MD_Candidate_ATP_Divergence_Report();
		process.init(ProcessInfo.builder().setCtx(Env.getCtx()).build());
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
	 * A plain (non-Mockito) test double for {@link AtpTargetCalculator} - see the class Javadoc for why a Mockito
	 * mock is unsuitable at the call volume {@link #keySelectionThatNeverShrinksBelowAFullBatch_abortsAfterMaxLoops()}
	 * drives it through. The constructor args are never exercised, since {@link #computeDivergence} is fully
	 * overridden below.
	 */
	private static final class FakeAtpTargetCalculator extends AtpTargetCalculator
	{
		private long callCount;

		FakeAtpTargetCalculator()
		{
			super(
					Mockito.mock(StockRepository.class),
					Mockito.mock(CandidateRepositoryRetrieval.class),
					new SourceDocumentLivenessService(Mockito.mock(SourceDocumentRepository.class)));
		}

		@Override
		public AtpDivergence computeDivergence(
				@NonNull final StockDataRecordIdentifier key,
				@NonNull final Instant date)
		{
			callCount++;
			return AtpDivergence.of(BigDecimal.ZERO, BigDecimal.ZERO);
		}

		long getCallCount()
		{
			return callCount;
		}
	}
}
