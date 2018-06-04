package de.metas.material.dispo.service.event;

import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.Mutable;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import com.google.common.collect.ImmutableList;

import de.metas.event.log.EventLogUserService;
import de.metas.event.log.EventLogUserService.InvokeHandlerandLogRequest;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandiateHandler;
import de.metas.material.dispo.service.event.handler.ForecastCreatedHandler;
import de.metas.material.dispo.service.event.handler.ShipmentScheduleCreatedHandler;
import de.metas.material.dispo.service.event.handler.ShipmentScheduleCreatedHandlerTests;
import de.metas.material.dispo.service.event.handler.TransactionEventHandler;
import de.metas.material.dispo.service.event.handler.ddorder.DDOrderAdvisedHandler;
import de.metas.material.dispo.service.event.handler.pporder.PPOrderAdvisedHandler;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.MaterialEventHandlerRegistry;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderAdvisedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import mockit.Delegate;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/*
 * #%L
 * metasfresh-material-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * This is kind of a bunch of "module tests".
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MaterialEventHandlerRegistryTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	public static final int fromWarehouseId = 10;
	public static final int intermediateWarehouseId = 20;
	public static final int toWarehouseId = 30;

	private MaterialEventHandlerRegistry materialEventListener;

	@Mocked
	private PostMaterialEventService postMaterialEventService;

	@Mocked
	private EventLogUserService eventLogUserService;

	@Mocked
	private AvailableToPromiseRepository availableToPromiseRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();
		final SupplyProposalEvaluator supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepositoryRetrieval);

		final CandidateRepositoryWriteService candidateRepositoryCommands = new CandidateRepositoryWriteService();

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands);

		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(ImmutableList.of(
				new DemandCandiateHandler(
						candidateRepositoryRetrieval,
						candidateRepositoryCommands,
						postMaterialEventService,
						availableToPromiseRepository,
						stockCandidateService),
				new SupplyCandiateHandler(
						candidateRepositoryRetrieval,
						candidateRepositoryCommands,
						stockCandidateService)));

		final RequestMaterialOrderService candidateService = new RequestMaterialOrderService(
				candidateRepositoryRetrieval,
				postMaterialEventService);

		final DDOrderAdvisedHandler distributionAdvisedEventHandler = new DDOrderAdvisedHandler(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands,
				candidateChangeHandler,
				supplyProposalEvaluator,
				new RequestMaterialOrderService(candidateRepositoryRetrieval, postMaterialEventService));

		final PPOrderAdvisedHandler ppOrderAdvisedHandler = new PPOrderAdvisedHandler(
				candidateChangeHandler,
				candidateRepositoryRetrieval,
				candidateService);

		final ForecastCreatedHandler forecastCreatedEventHandler = new ForecastCreatedHandler(candidateChangeHandler);

		final TransactionEventHandler transactionEventHandler = new TransactionEventHandler(
				candidateChangeHandler,
				candidateRepositoryRetrieval,
				postMaterialEventService);

		final ShipmentScheduleCreatedHandler shipmentScheduleEventHandler = new ShipmentScheduleCreatedHandler(candidateChangeHandler);

		@SuppressWarnings("rawtypes")
		final Optional<Collection<MaterialEventHandler>> handlers = Optional.of(ImmutableList.of(
				distributionAdvisedEventHandler,
				ppOrderAdvisedHandler,
				forecastCreatedEventHandler,
				transactionEventHandler,
				shipmentScheduleEventHandler));

		setupEventLogUserServiceOnlyInvokesHandler();

		materialEventListener = new MaterialEventHandlerRegistry(handlers, eventLogUserService);
	}

	/**
	 * For these tests, {@link EventLogUserService} shall not do any actual logging.
	 */
	@SuppressWarnings("rawtypes")
	private void setupEventLogUserServiceOnlyInvokesHandler()
	{
		// @formatter:off
		new Expectations()
		{{
			eventLogUserService.invokeHandlerAndLog((InvokeHandlerandLogRequest)any);
			result = new Delegate()
			{
				@SuppressWarnings("unused")
				void delegateMethod(final InvokeHandlerandLogRequest request)
				{
					request.getInvokaction().run();
				}
			};
		}};	// @formatter:on
	}

	@Test
	public void test_shipmentScheduleCreatedEvent_then_distributionAdvisedEvent()
	{
		final ShipmentScheduleCreatedEvent shipmentScheduleEvent = ShipmentScheduleCreatedHandlerTests.createShipmentScheduleTestEvent();
		final MaterialDescriptor orderedMaterial = shipmentScheduleEvent.getMaterialDescriptor();

		final Date shipmentScheduleEventTime = orderedMaterial.getDate();

		// Whenever asked, (by DemandCandidateHandler, in this case), we say that we need more.
		// This is required to make the DemandCandidateHandler fire a supplyRequiredEvent.
		new Expectations(CandidateRepositoryRetrieval.class)
		{{
			availableToPromiseRepository.retrieveAvailableStockQtySum((AvailableToPromiseMultiQuery)any); minTimes = 0;
			result = new BigDecimal("-10");
		}}; // @formatter:on

		materialEventListener.onEvent(shipmentScheduleEvent);

		// guard - we expect one for the shipment-schedule demand, two for the distribution demand + supply and 2 stocks (one of them shared between shipment-demand and distr-supply)
		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2);

		final Mutable<SupplyRequiredDescriptor> supplyRequiredDescriptor = new Mutable<>();
		// @formatter:off
		new Verifications()
		{{
			MaterialEvent event;
			postMaterialEventService.postEventAfterNextCommit(event = withCapture());
			assertThat(event).isInstanceOf(SupplyRequiredEvent.class);
			supplyRequiredDescriptor.setValue(((SupplyRequiredEvent)event).getSupplyRequiredDescriptor());
		}};	// @formatter:on

		// create a distributionAdvisedEvent event which matches the shipmentscheduleEvent that we processed in testShipmentScheduleEvent()
		final DDOrderAdvisedEvent ddOrderAdvisedEvent = DDOrderAdvisedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.fromWarehouseId(fromWarehouseId)
				.toWarehouseId(toWarehouseId)
				.supplyRequiredDescriptor(supplyRequiredDescriptor.getValue())
				.ddOrder(DDOrder.builder()
						.orgId(ORG_ID)
						.plantId(800)
						.productPlanningId(810)
						.shipperId(820)
						.datePromised(shipmentScheduleEventTime)
						.line(DDOrderLine.builder()
								.productDescriptor(orderedMaterial)
								.bPartnerId(orderedMaterial.getBPartnerId())
								.qty(BigDecimal.TEN)
								.durationDays(0)
								.networkDistributionLineId(900)
								.build())
						.build())
				.build();
		ddOrderAdvisedEvent.validate();
		materialEventListener.onEvent(ddOrderAdvisedEvent);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(5); // one for the shipment-schedule demand, two for the distribution demand + supply and 2 stocks (one of them shared between shipment-demand and distr-supply)
		final I_MD_Candidate toWarehouseDemand = DispoTestUtils.filter(CandidateType.DEMAND, toWarehouseId).get(0);
		final I_MD_Candidate toWarehouseSharedStock = DispoTestUtils.filter(CandidateType.STOCK, toWarehouseId).get(0);
		final I_MD_Candidate toWarehouseSupply = DispoTestUtils.filter(CandidateType.SUPPLY, toWarehouseId).get(0);

		final I_MD_Candidate fromWarehouseDemand = DispoTestUtils.filter(CandidateType.DEMAND, fromWarehouseId).get(0);
		final I_MD_Candidate toWarehouseStock = DispoTestUtils.filter(CandidateType.STOCK, fromWarehouseId).get(0);

		final List<I_MD_Candidate> allRecordsBySeqNo = DispoTestUtils.sortBySeqNo(DispoTestUtils.retrieveAllRecords());
		assertThat(allRecordsBySeqNo).containsExactly(
				toWarehouseDemand,
				toWarehouseSharedStock,
				toWarehouseSupply,
				fromWarehouseDemand,
				toWarehouseStock);

		assertThat(toWarehouseDemand.getQty()).isEqualByComparingTo("10");
		assertThat(toWarehouseSharedStock.getQty()).isZero();
		assertThat(toWarehouseSupply.getQty()).isEqualByComparingTo("10");
		assertThat(fromWarehouseDemand.getQty()).isEqualByComparingTo("10");
		assertThat(toWarehouseStock.getQty()).isEqualByComparingTo("-10");
	}

	@Test
	@Ignore("You can extend on this one when starting with https://github.com/metasfresh/metasfresh/issues/2684")
	public void testShipmentScheduleEvent_then_Shipment()
	{
		final ShipmentScheduleCreatedEvent shipmentScheduleEvent = ShipmentScheduleCreatedHandlerTests.createShipmentScheduleTestEvent();

		final MaterialDescriptor orderedMaterial = shipmentScheduleEvent.getMaterialDescriptor();
		final Date shipmentScheduleEventTime = orderedMaterial.getDate();
		final Timestamp twoHoursAfterShipmentSched = TimeUtil.addHours(shipmentScheduleEventTime, 2);

		materialEventListener.onEvent(shipmentScheduleEvent);

		final TransactionCreatedEvent transactionEvent = TransactionCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.materialDescriptor(orderedMaterial.withDate(twoHoursAfterShipmentSched))
				.build();

		materialEventListener.onEvent(transactionEvent);

		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(2);
	}
}
