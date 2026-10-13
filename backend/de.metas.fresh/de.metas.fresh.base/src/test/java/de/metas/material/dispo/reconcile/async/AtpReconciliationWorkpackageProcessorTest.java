package de.metas.material.dispo.reconcile.async;

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
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.material.cockpit.model.I_MD_Stock;
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
import de.metas.product.ProductId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.Params;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * The consuming half of the write-path split: whatever {@code MD_Candidate_Reconcile_ATP} enqueued must be
 * reconciled here, in the app server, through {@link AtpReconciliationCommand}.
 * <p>
 * Two behaviours are pinned, and they are the two ends of the same design decision:
 * <ul>
 * <li>{@link #enqueuedRun_isReconciledThroughTheReconciliationCommand()} - the work package's parameters are read
 * back into the run the operator launched, every matching key goes through the command, and the command sees the
 * <b>enqueued</b> run date rather than "whenever the queue got around to it".</li>
 * <li>{@link #appServerWithoutTheMaterialDispoProfile_failsWithAnActionableMessage()} - the guard belongs here
 * because this is where the scenario is real: a deployment whose <i>app server</i> does not list the
 * material-disposition profile in {@code de.metas.spring.profiles.active}. Without it, that deployment fails the
 * work package with Spring's bare {@code NoSuchBeanDefinitionException} naming only the type, buried in the queue
 * where no operator is watching a process window.</li>
 * </ul>
 * {@link IWorkpackageProcessor#setParameters(IParams)} is public API, so the processor can be driven directly
 * with a hand-built {@link Params} - no queue, no database.
 */
class AtpReconciliationWorkpackageProcessorTest
{
	private static final ClientId CLIENT_ID = ClientId.ofRepoId(1000000);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1000001);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(1000002);
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1000003);

	private static final Instant RUN_DATE = Instant.parse("2024-09-25T06:00:00Z");
	private static final Instant LIVENESS_CUTOFF = Instant.parse("2024-09-23T00:00:00Z");

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
	void enqueuedRun_isReconciledThroughTheReconciliationCommand()
	{
		final List<StockDataRecordIdentifier> keys = distinctKeys(2);
		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenAnswer(invocation -> (int)invocation.getArgument(4) == 0 ? ImmutableList.copyOf(keys) : ImmutableList.of());

		// both keys come back with a non-zero divergence, so both must be reconciled and counted
		final RecordingReconciliationCommand reconciliationCommand = new RecordingReconciliationCommand(
				AtpReconciliationRunLog.empty(AtpDivergence.of(new BigDecimal("100"), BigDecimal.ZERO)));
		SpringContextHolder.registerJUnitBean(AtpReconciliationCommand.class, reconciliationCommand);

		final AtpReconciliationWorkpackageProcessor processor = new AtpReconciliationWorkpackageProcessor();
		processor.setParameters(enqueuedParams());

		final IWorkpackageProcessor.Result result = processor.processWorkPackage(newWorkPackage(), null);

		assertThat(result).isEqualTo(IWorkpackageProcessor.Result.SUCCESS);

		assertThat(reconciliationCommand.getKeysSeen())
				.as("every key of the enqueued selection must be reconciled, exactly once")
				.containsExactlyElementsOf(keys);

		assertThat(reconciliationCommand.getDatesSeen())
				.as("the run date has to be the one carried on the work package, not the time the queue drained it")
				.containsOnly(RUN_DATE);
		assertThat(reconciliationCommand.getLivenessCutoffsSeen())
				.as("the operator's liveness cutoff must survive the trip through the queue")
				.containsOnly(LIVENESS_CUTOFF);
		assertThat(reconciliationCommand.getDryRunFlagsSeen())
				.as("a dry run is previewed synchronously and is never enqueued, so this path never dry-runs")
				.containsOnly(false);

		// the selection filters must have been rebuilt from the work package's parameters and pushed into the
		// repository query - a filter lost here would reconcile far more than the operator asked for
		Mockito.verify(stockRepository).retrieveKeys(WAREHOUSE_ID, PRODUCT_ID, null, 500, 0);
	}

	/**
	 * Reproducing the missing-bean branch needs a real, non-null {@code ApplicationContext} that genuinely lacks
	 * {@link AtpReconciliationCommand}: {@code SpringContextHolder.getBean} only throws Spring's
	 * {@code NoSuchBeanDefinitionException} - the exact exception the guard catches - when a context is registered
	 * and does not have the bean; with no context at all it throws a plain {@code AdempiereException} that would
	 * never reach the guard. So a profile-less context scanning the reconcile package stands in for such an app
	 * server, exactly as {@code de.metas.material.dispo.AtpReconcileContextStartupTest} does for the webapi.
	 */
	@Test
	void appServerWithoutTheMaterialDispoProfile_failsWithAnActionableMessage()
	{
		when(stockRepository.retrieveKeys(any(), any(), any(), anyInt(), anyInt()))
				.thenReturn(ImmutableList.of());

		try (final AnnotationConfigApplicationContext profileLessContext = newProfileLessReconcilePackageContext())
		{
			SpringContextHolder.instance.setApplicationContext(profileLessContext);
			try
			{
				final AtpReconciliationWorkpackageProcessor processor = new AtpReconciliationWorkpackageProcessor();
				processor.setParameters(enqueuedParams());

				final I_C_Queue_WorkPackage workPackage = newWorkPackage();

				assertThatThrownBy(() -> processor.processWorkPackage(workPackage, null))
						.as("the operator must be told which profile is missing and where to switch it on - not"
								+ " left with a bare NoSuchBeanDefinitionException in the queue")
						.isInstanceOf(AdempiereException.class)
						.hasMessageContaining(Profiles.PROFILE_MaterialDispo)
						.hasMessageContaining("de.metas.spring.profiles.active")
						.hasMessageContaining("app server");
			}
			finally
			{
				SpringContextHolder.instance.clearApplicationContext();
			}
		}
	}

	/**
	 * @return a real, profile-less context scanning {@code de.metas.material.dispo.reconcile}. The collaborator
	 * mocks are registered as plain (unannotated) singletons rather than through a {@code @Configuration} class of
	 * this test's own: this test class sits <i>under</i> the scanned package, so an annotated config here would be
	 * picked up by the recursive scan itself and could hand the context the very bean whose absence is the point -
	 * the trap {@code AtpReconcileContextStartupTest}'s Javadoc records falling into once already.
	 */
	private AnnotationConfigApplicationContext newProfileLessReconcilePackageContext()
	{
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getBeanFactory().registerSingleton("stockRepository", stockRepository);
		context.getBeanFactory().registerSingleton("candidateRepositoryRetrieval", Mockito.mock(CandidateRepositoryRetrieval.class));
		context.scan("de.metas.material.dispo.reconcile");
		context.refresh();
		return context;
	}

	/**
	 * @return exactly the parameters {@link AtpReconciliationEnqueueService#enqueue} writes - built here by hand
	 * from the same names, so this test also pins that wire format from the consuming side. The <i>value types</i>
	 * are the ones a real {@code C_Queue_WorkPackage_Param} row hands back rather than the ones the producer put
	 * in: {@code WorkpackageParamDAO} stores a date/time parameter in {@code P_Date} and an integer one in
	 * {@code P_Number}, so the consumer sees a {@link Timestamp} and a {@link BigDecimal}. Reading back through
	 * that conversion is part of what has to work.
	 * <p>
	 * The product-category filter is deliberately absent, matching an operator who left it empty: the enqueue
	 * side omits an unset filter entirely, so "not restricted" must be read off the parameter's absence.
	 */
	private static IParams enqueuedParams()
	{
		final Map<String, Object> values = new LinkedHashMap<>();
		values.put("RunDate", Timestamp.from(RUN_DATE));
		values.put("LivenessCutoffDate", Timestamp.from(LIVENESS_CUTOFF));
		values.put(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, BigDecimal.valueOf(WAREHOUSE_ID.getRepoId()));
		values.put(I_MD_Stock.COLUMNNAME_M_Product_ID, BigDecimal.valueOf(PRODUCT_ID.getRepoId()));
		return Params.ofMap(values);
	}

	private static I_C_Queue_WorkPackage newWorkPackage()
	{
		final I_C_Queue_WorkPackage workPackage = InterfaceWrapperHelper.newInstance(I_C_Queue_WorkPackage.class);
		InterfaceWrapperHelper.save(workPackage);
		return workPackage;
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
	 * A plain (non-Mockito) test double for {@link AtpReconciliationCommand} that records every argument it was
	 * called with - the house device in this feature's tests (see {@code MD_Candidate_Reconcile_ATPTest} and
	 * {@code MD_Candidate_ATP_Divergence_ReportTest}), chosen there because a Mockito mock retains every
	 * invocation. The constructor args are never exercised, since {@link #reconcileAndLog} is fully overridden.
	 */
	private static final class RecordingReconciliationCommand extends AtpReconciliationCommand
	{
		private final AtpReconciliationRunLog resultToReturn;
		private final List<StockDataRecordIdentifier> keysSeen = new ArrayList<>();
		private final List<Instant> datesSeen = new ArrayList<>();
		private final List<Instant> livenessCutoffsSeen = new ArrayList<>();
		private final List<Boolean> dryRunFlagsSeen = new ArrayList<>();

		RecordingReconciliationCommand(@NonNull final AtpReconciliationRunLog resultToReturn)
		{
			super(
					AtpTargetCalculator.newInstanceForUnitTesting(),
					Mockito.mock(CandidateChangeService.class),
					Mockito.mock(CandidateRepositoryRetrieval.class));
			this.resultToReturn = resultToReturn;
		}

		@Override
		public AtpReconciliationRunLog reconcileAndLog(
				@NonNull final StockDataRecordIdentifier key,
				@NonNull final Instant date,
				final boolean dryRun,
				@Nullable final Instant livenessCutoff)
		{
			keysSeen.add(key);
			datesSeen.add(date);
			livenessCutoffsSeen.add(livenessCutoff);
			dryRunFlagsSeen.add(dryRun);
			return resultToReturn;
		}

		List<StockDataRecordIdentifier> getKeysSeen() {return keysSeen;}

		List<Instant> getDatesSeen() {return datesSeen;}

		List<Instant> getLivenessCutoffsSeen() {return livenessCutoffsSeen;}

		List<Boolean> getDryRunFlagsSeen() {return dryRunFlagsSeen;}
	}
}
