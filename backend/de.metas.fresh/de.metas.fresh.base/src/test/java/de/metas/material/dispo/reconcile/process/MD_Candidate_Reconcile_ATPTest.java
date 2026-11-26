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
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.common.util.time.SystemTime;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.reconcile.AtpDivergence;
import de.metas.material.dispo.reconcile.AtpReconciliationCommand;
import de.metas.material.dispo.reconcile.AtpTargetCalculator;
import de.metas.material.dispo.reconcile.SourceDocumentLivenessService;
import de.metas.material.dispo.reconcile.SourceDocumentRepository;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfo;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Pins the fork in {@link MD_Candidate_Reconcile_ATP#doIt()}: a dry run previews inline, a real run is enqueued -
 * and neither path may ever need {@link AtpReconciliationCommand} in the JVM this process actually executes in.
 * <p>
 * {@code doIt()} is {@code protected}, which a test in the same package may call directly; the collaborators are
 * supplied through {@link SpringContextHolder#registerJUnitBean(Class, Object)} and {@link Services}, so no real
 * DB is needed. This mirrors the {@code JavaProcess} unit-test pattern already used in this codebase (see
 * {@code de.metas.bpartner.process.CBPartnerUpdateMemoTest}: {@code process.init(ProcessInfo...)} then reflection
 * onto the {@code @Param} field, then {@code doIt()} called directly).
 * <p>
 * <b>What moved out of this class with the split.</b> The batched key drain now lives in
 * {@code AtpKeySelectionDrainer}, so its pagination and {@code MAX_LOOPS} coverage moved verbatim to
 * {@code AtpKeySelectionDrainerTest}; and the "no material disposition engine" fail-fast moved to
 * {@code AtpReconciliationWorkpackageProcessorTest}, because after the split that failure can only happen where
 * the work package is drained - this process needs the bean on neither path.
 */
class MD_Candidate_Reconcile_ATPTest
{
	private static final ClientId CLIENT_ID = ClientId.ofRepoId(1000000);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1000001);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(1000002);
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1000003);
	private static final ProductCategoryId PRODUCT_CATEGORY_ID = ProductCategoryId.ofRepoId(1000004);

	/**
	 * The frozen clock of the tests below, so the {@code RunDate} the process derives from {@link SystemTime} is
	 * a value the test can name. Europe/Berlin, i.e. UTC+2 on that date.
	 */
	private static final ZonedDateTime RUN_TIME = ZonedDateTime.parse("2024-09-25T08:00:00+02:00[Europe/Berlin]");

	private StockRepository stockRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		stockRepository = Mockito.mock(StockRepository.class);
		SpringContextHolder.registerJUnitBean(StockRepository.class, stockRepository);
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	/**
	 * A <b>dry run</b> produces its whole preview in the JVM a WebUI-launched process actually executes in - the
	 * webapi, which does not activate {@link Profiles#PROFILE_MaterialDispo} - and must therefore never touch
	 * {@link AtpReconciliationCommand}, which only exists where that profile is active.
	 * <p>
	 * It needs nothing from that bean: {@link AtpTargetCalculator#computeDivergence} already returns expected,
	 * stored and difference, and is deliberately un-{@code @Profile}-guarded, so the preview is computed inline
	 * here. Resolving the command bean on this path would make the preview unavailable in exactly the JVM the
	 * operator asks for it in, failing with "the spring profile material-dispo is not active here".
	 * <p>
	 * Reproducing the webapi's situation needs a real, scanned, profile-less
	 * {@link AnnotationConfigApplicationContext} - see
	 * {@code de.metas.material.dispo.AtpReconcileContextStartupTest}, which proves that same absence for the
	 * package as a whole, and whose Javadoc records why a {@code @Configuration} class declared under the scanned
	 * package silently blinds such a test.
	 */
	@Test
	void dryRunInProfileLessJvm_reportsThePreviewWithoutTheReconciliationCommand() throws Exception
	{
		SystemTime.setFixedTimeSource(RUN_TIME);

		final List<StockDataRecordIdentifier> keys = distinctKeys(1);
		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenAnswer(invocation -> (int)invocation.getArgument(4) == 0 ? ImmutableList.copyOf(keys) : ImmutableList.of());

		// stored 0 against a target of 100, i.e. a key that diverges. Registered as a JUnit bean so it wins over
		// the real, scanned calculator (SpringContextHolder#getBeanOrSupply consults the JUnit registry first),
		// which would otherwise answer out of the mocked repositories with a meaningless 0.
		final RecordingAtpTargetCalculator atpTargetCalculator =
				new RecordingAtpTargetCalculator(AtpDivergence.of(new BigDecimal("100"), BigDecimal.ZERO));
		SpringContextHolder.registerJUnitBean(AtpTargetCalculator.class, atpTargetCalculator);

		// a real queue factory, so that "the dry run enqueued nothing" is asserted rather than merely implied by
		// the absence of one
		final IWorkPackageQueueFactory queueFactory = Mockito.mock(IWorkPackageQueueFactory.class);
		Services.registerService(IWorkPackageQueueFactory.class, queueFactory);

		try (final AnnotationConfigApplicationContext profileLessContext = newProfileLessReconcilePackageContext())
		{
			SpringContextHolder.instance.setApplicationContext(profileLessContext);
			try
			{
				assertThat(profileLessContext.getBeanNamesForType(AtpReconciliationCommand.class))
						.as("precondition: this context must genuinely lack the write-path bean, or it cannot"
								+ " stand in for the webapi")
						.isEmpty();

				final ProcessInfo processInfo = ProcessInfo.builder().setCtx(Env.getCtx()).build();
				final MD_Candidate_Reconcile_ATP process = newProcess(processInfo, true, LocalDate.of(2024, 9, 23));

				// the preview must resolve nothing beyond AtpTargetCalculator: AtpReconciliationCommand does not
				// exist in this profile-less context, so touching it here would throw
				assertThat(process.doIt()).isEqualTo(JavaProcess.MSG_OK);
			}
			finally
			{
				SpringContextHolder.instance.clearApplicationContext();
			}
		}

		assertThat(atpTargetCalculator.getKeysSeen())
				.as("the preview must be computed off the un-@Profile-guarded target calculator, for every key of"
						+ " the selection")
				.containsExactlyElementsOf(keys);
		assertThat(atpTargetCalculator.getDatesSeen())
				.as("the preview date is the run date the process pinned to now")
				.containsOnly(RUN_TIME.toInstant());
		assertThat(atpTargetCalculator.getLivenessCutoffsSeen())
				.as("the operator's liveness cutoff has to reach the preview too, or the preview would show a"
						+ " different divergence from the run it previews")
				.containsOnly(LocalDate.of(2024, 9, 23).atStartOfDay(SystemTime.zoneId()).toInstant());

		verifyNoInteractions(queueFactory);
	}

	/**
	 * The other half of the split: a <b>real</b> run does not reconcile in the webapi at all - it hands the run to
	 * the async queue, which the app server drains, where {@link Profiles#PROFILE_MaterialDispo} <i>is</i> active.
	 * So the process must enqueue exactly one work package carrying the whole run - every selection filter, the
	 * liveness cutoff and the run date - and must itself read and write nothing.
	 * <p>
	 * The parameter names are asserted as plain string literals on purpose: they are the wire format between the
	 * two JVMs, so a rename on the producing side that is not matched on the consuming side must fail here rather
	 * than silently drop a filter and reconcile the whole database.
	 */
	@Test
	void realRun_enqueuesExactlyOneWorkpackageCarryingTheRunsSelectionAndOptions() throws Exception
	{
		SystemTime.setFixedTimeSource(RUN_TIME);

		final IWorkPackageQueueFactory queueFactory = Mockito.mock(IWorkPackageQueueFactory.class);
		final IWorkPackageQueue queue = Mockito.mock(IWorkPackageQueue.class);
		final IWorkPackageBuilder workPackageBuilder = Mockito.mock(IWorkPackageBuilder.class, Mockito.RETURNS_SELF);
		final I_C_Queue_WorkPackage enqueuedWorkPackage = InterfaceWrapperHelper.newInstance(I_C_Queue_WorkPackage.class);
		InterfaceWrapperHelper.save(enqueuedWorkPackage);

		Services.registerService(IWorkPackageQueueFactory.class, queueFactory);
		when(queueFactory.getQueueForEnqueuing(any(Properties.class), ArgumentMatchers.<Class<? extends IWorkpackageProcessor>>any()))
				.thenReturn(queue);
		when(queue.newWorkPackage()).thenReturn(workPackageBuilder);
		when(workPackageBuilder.buildAndEnqueue()).thenReturn(enqueuedWorkPackage);

		final ProcessInfo processInfo = ProcessInfo.builder().setCtx(Env.getCtx()).build();
		final MD_Candidate_Reconcile_ATP process = newProcess(processInfo, false, LocalDate.of(2024, 9, 23));
		setParamField(process, "p_M_Warehouse_ID", WAREHOUSE_ID.getRepoId());
		setParamField(process, "p_M_Product_ID", PRODUCT_ID.getRepoId());
		setParamField(process, "p_M_Product_Category_ID", PRODUCT_CATEGORY_ID.getRepoId());

		assertThat(process.doIt()).isEqualTo(JavaProcess.MSG_OK);

		verify(queue, times(1)).newWorkPackage();
		verify(workPackageBuilder, times(1)).buildAndEnqueue();

		verify(workPackageBuilder).parameter("M_Warehouse_ID", WAREHOUSE_ID.getRepoId());
		verify(workPackageBuilder).parameter("M_Product_ID", PRODUCT_ID.getRepoId());
		verify(workPackageBuilder).parameter("M_Product_Category_ID", PRODUCT_CATEGORY_ID.getRepoId());
		verify(workPackageBuilder).parameter("LivenessCutoffDate", LocalDate.of(2024, 9, 23).atStartOfDay(SystemTime.zoneId()).toInstant());
		verify(workPackageBuilder).parameter("RunDate", RUN_TIME.toInstant());

		// the enqueuing JVM must not have touched the selection at all: draining it is the work package's job,
		// in the JVM that can actually reconcile
		verifyNoInteractions(stockRepository);
	}

	/**
	 * @return a real, profile-less context scanning {@code de.metas.material.dispo.reconcile}. The collaborator
	 * mocks are registered as plain (unannotated) singletons rather than through a {@code @Configuration} class of
	 * this test's own: this test class sits in {@code de.metas.material.dispo.reconcile.process} - itself under
	 * the scanned package, because {@code doIt()} is only callable from the same package - so a
	 * {@code @Configuration}/{@code @Component} class declared here would be picked up by the recursive
	 * {@link org.springframework.context.annotation.ComponentScan} and could supply the very bean whose absence
	 * is the point.
	 */
	private AnnotationConfigApplicationContext newProfileLessReconcilePackageContext()
	{
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// the very mock this test stubs: whatever bean of the scanned package the context builds must page
		// through THIS test's selection, not an unstubbed second mock that always answers "no keys"
		context.getBeanFactory().registerSingleton("stockRepository", stockRepository);
		context.getBeanFactory().registerSingleton("candidateRepositoryRetrieval", Mockito.mock(CandidateRepositoryRetrieval.class));
		context.scan("de.metas.material.dispo.reconcile");
		context.refresh();
		return context;
	}

	/**
	 * @param processInfo kept by the caller so it can read the run's resolved process log afterwards via
	 * {@link ProcessInfo#getResult()} - {@code JavaProcess.getResult()} itself is {@code protected}, hence
	 * unreachable from a test outside {@code de.metas.process}
	 * @param dryRun the value of the process's {@code IsDryRun} parameter
	 * @param livenessCutoffDate the value of the process's optional {@code LivenessCutoffDate} parameter
	 */
	private static MD_Candidate_Reconcile_ATP newProcess(
			@NonNull final ProcessInfo processInfo,
			final boolean dryRun,
			@Nullable final LocalDate livenessCutoffDate) throws Exception
	{
		final MD_Candidate_Reconcile_ATP process = new MD_Candidate_Reconcile_ATP();
		process.init(processInfo);

		setParamField(process, "p_IsDryRun", dryRun);
		if (livenessCutoffDate != null)
		{
			setParamField(process, "p_LivenessCutoffDate", livenessCutoffDate);
		}

		return process;
	}

	private static void setParamField(
			@NonNull final MD_Candidate_Reconcile_ATP process,
			@NonNull final String fieldName,
			@NonNull final Object value) throws Exception
	{
		final Field field = MD_Candidate_Reconcile_ATP.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(process, value);
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
	 * A plain (non-Mockito) test double for {@link AtpTargetCalculator} that returns one fixed divergence and
	 * records what it was asked - the same device {@code MD_Candidate_ATP_Divergence_ReportTest} uses. The
	 * constructor args are never exercised, since both {@code computeDivergence} overloads are fully overridden
	 * below.
	 */
	private static final class RecordingAtpTargetCalculator extends AtpTargetCalculator
	{
		private final AtpDivergence divergenceToReturn;
		private final List<StockDataRecordIdentifier> keysSeen = new ArrayList<>();
		private final List<Instant> datesSeen = new ArrayList<>();
		private final List<Instant> livenessCutoffsSeen = new ArrayList<>();

		RecordingAtpTargetCalculator(@NonNull final AtpDivergence divergenceToReturn)
		{
			super(
					Mockito.mock(StockRepository.class),
					Mockito.mock(CandidateRepositoryRetrieval.class),
					new SourceDocumentLivenessService(Mockito.mock(SourceDocumentRepository.class)));
			this.divergenceToReturn = divergenceToReturn;
		}

		@Override
		public AtpDivergence computeDivergence(
				@NonNull final StockDataRecordIdentifier key,
				@NonNull final Instant date)
		{
			return computeDivergence(key, date, null);
		}

		@Override
		public AtpDivergence computeDivergence(
				@NonNull final StockDataRecordIdentifier key,
				@NonNull final Instant date,
				@Nullable final Instant livenessCutoff)
		{
			keysSeen.add(key);
			datesSeen.add(date);
			livenessCutoffsSeen.add(livenessCutoff);
			return divergenceToReturn;
		}

		List<StockDataRecordIdentifier> getKeysSeen() {return keysSeen;}

		List<Instant> getDatesSeen() {return datesSeen;}

		List<Instant> getLivenessCutoffsSeen() {return livenessCutoffsSeen;}
	}
}
