package de.metas.material.dispo.service.event;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.event.log.EventLogUserService;
import de.metas.event.log.EventLogUserService.InvokeHandlerAndLogRequest;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.dispo.service.event.handler.ForecastCreatedHandler;
import de.metas.material.dispo.service.event.handler.TransactionEventHandler;
import de.metas.material.dispo.service.event.handler.ddorder.DDOrderAdvisedHandler;
import de.metas.material.dispo.service.event.handler.shipmentschedule.ShipmentScheduleCreatedHandler;
import de.metas.material.dispo.service.event.handler.shipmentschedule.ShipmentScheduleCreatedHandlerTests;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.MaterialEventHandlerRegistry;
import de.metas.material.event.MaterialEventObserver;
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
import de.metas.order.OrderLineRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static org.assertj.core.api.Assertions.*;

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
 */
@ExtendWith(AdempiereTestWatcher.class)
public class MaterialEventHandlerRegistryTests
{
	public static final WarehouseId fromWarehouseId = WarehouseId.ofRepoId(10);
	public static final WarehouseId intermediateWarehouseId = WarehouseId.ofRepoId(20);
	public static final WarehouseId toWarehouseId = WarehouseId.ofRepoId(30);

	private MaterialEventHandlerRegistry materialEventListener;

	private PostMaterialEventService postMaterialEventService;
	private EventLogUserService eventLogUserService;
	private AvailableToPromiseRepository availableToPromiseRepository;

	private DimensionService dimensionService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new MDCandidateDimensionFactory());
		dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		postMaterialEventService = Mockito.mock(PostMaterialEventService.class);
		eventLogUserService = Mockito.spy(EventLogUserService.class);
		availableToPromiseRepository = Mockito.mock(AvailableToPromiseRepository.class);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final SupplyProposalEvaluator supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepositoryRetrieval);

		final CandidateRepositoryWriteService candidateRepositoryCommands = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo);

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands);

		final SupplyCandidateHandler supplyCandidateHandler = new SupplyCandidateHandler(
				candidateRepositoryCommands,
				stockCandidateService);
		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(ImmutableList.of(
				new DemandCandiateHandler(
						candidateRepositoryRetrieval,
						candidateRepositoryCommands,
						postMaterialEventService,
						availableToPromiseRepository,
						stockCandidateService,
						supplyCandidateHandler),
				supplyCandidateHandler));

		final DDOrderAdvisedHandler distributionAdvisedEventHandler = new DDOrderAdvisedHandler(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands,
				candidateChangeHandler,
				supplyProposalEvaluator,
				new RequestMaterialOrderService(candidateRepositoryRetrieval, postMaterialEventService, new OrderLineRepository()),
				new DDOrderDetailRequestHandler(),
				new MainDataRequestHandler());

		final ForecastCreatedHandler forecastCreatedEventHandler = new ForecastCreatedHandler(candidateChangeHandler);

		final TransactionEventHandler transactionEventHandler = new TransactionEventHandler(
				candidateChangeHandler,
				candidateRepositoryRetrieval,
				postMaterialEventService);

		final ShipmentScheduleCreatedHandler shipmentScheduleEventHandler = new ShipmentScheduleCreatedHandler(
				candidateChangeHandler,
				candidateRepositoryRetrieval);

		@SuppressWarnings("rawtypes")
		final Optional<Collection<MaterialEventHandler>> handlers = Optional.of(ImmutableList.of(
				distributionAdvisedEventHandler,
				forecastCreatedEventHandler,
				transactionEventHandler,
				shipmentScheduleEventHandler));

		setupEventLogUserServiceOnlyInvokesHandler();

		materialEventListener = new MaterialEventHandlerRegistry(handlers, eventLogUserService, new MaterialEventObserver());
	}

	/**
	 * For these tests, {@link EventLogUserService} shall not do any actual logging.
	 */
	private void setupEventLogUserServiceOnlyInvokesHandler()
	{
		Mockito.doAnswer(invocation -> {
			final InvokeHandlerAndLogRequest request = (InvokeHandlerAndLogRequest)invocation.getArguments()[0];
			request.getInvokaction().run();
			return null; // void
		})
				.when(eventLogUserService)
				.invokeHandlerAndLog(Mockito.any());
	}

	@Test
	public void test_shipmentScheduleCreatedEvent_then_distributionAdvisedEvent()
	{
		//
		// given
		final ShipmentScheduleCreatedEvent shipmentScheduleEvent = ShipmentScheduleCreatedHandlerTests.createShipmentScheduleTestEvent();
		final MaterialDescriptor orderedMaterial = shipmentScheduleEvent.getMaterialDescriptor();

		final Instant shipmentScheduleEventTime = orderedMaterial.getDate();

		// Whenever asked, (by DemandCandidateHandler, in this case), we say that we need more.
		// This is required to make the DemandCandidateHandler fire a supplyRequiredEvent.
		Mockito.when(availableToPromiseRepository.retrieveAvailableStockQtySum(Mockito.any(AvailableToPromiseMultiQuery.class)))
				.thenReturn(new BigDecimal("-10"));

		materialEventListener.onEvent(shipmentScheduleEvent);

		// guard - we expect one pair for the demand and one for the corresponding (not-yet-specific) supply
		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(4);

		final ArgumentCaptor<MaterialEvent> eventCaptor = ArgumentCaptor.forClass(MaterialEvent.class);
		Mockito.verify(postMaterialEventService)
				.postEventAfterNextCommit(eventCaptor.capture());
		final SupplyRequiredEvent event = (SupplyRequiredEvent)eventCaptor.getValue();
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();

		// create a distributionAdvisedEvent event which matches the shipmentscheduleEvent that we processed in testShipmentScheduleEvent()
		final DDOrderAdvisedEvent ddOrderAdvisedEvent = DDOrderAdvisedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.fromWarehouseId(fromWarehouseId)
				.toWarehouseId(toWarehouseId)
				.supplyRequiredDescriptor(supplyRequiredDescriptor)
				.ddOrder(DDOrder.builder()
								 .orgId(ORG_ID)
								 .plantId(800)
								 .productPlanningId(810)
								 .shipperId(820)
								 .datePromised(shipmentScheduleEventTime)
								 .line(DDOrderLine.builder()
											   .productDescriptor(orderedMaterial)
											   .bPartnerId(orderedMaterial.getCustomerId().getRepoId())
											   .qty(BigDecimal.TEN)
											   .durationDays(0)
											   .networkDistributionLineId(900)
											   .build())
								 .build())
				.build();
		ddOrderAdvisedEvent.validate();

		//
		// when
		materialEventListener.onEvent(ddOrderAdvisedEvent);

		//
		// then
		// one for the shipment-schedule demand, one for shipment-schedule-supply that is now updated from the ddOrder even; then a new demand + supply; and one stock for each
		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(8);

		// demand created before we processed the ddOrderAdvisedEvent
		final I_MD_Candidate toWarehouseDemand = DispoTestUtils.filter(CandidateType.DEMAND, toWarehouseId).get(0);
		final I_MD_Candidate toWarehouseDemandStock = DispoTestUtils.retrieveStockCandidate(toWarehouseDemand);

		// supply created "unspecific" before we processed the ddOrderAdvisedEvent, and then enriched with the ddOrderAdvisedEvent's data
		final I_MD_Candidate toWarehouseSupply = DispoTestUtils.filter(CandidateType.SUPPLY, toWarehouseId).get(0);
		final I_MD_Candidate toWarehouseSupplyStock = DispoTestUtils.retrieveStockCandidate(toWarehouseSupply);

		// demand  created when we processed the ddOrderAdvisedEvent
		final I_MD_Candidate fromWarehouseDemand = DispoTestUtils.filter(CandidateType.DEMAND, fromWarehouseId).get(0);
		final I_MD_Candidate fromWarehouseDemandStock = DispoTestUtils.retrieveStockCandidate(fromWarehouseDemand);

		final I_MD_Candidate fromWarehouseSupply = DispoTestUtils.filter(CandidateType.SUPPLY, fromWarehouseId).get(0);
		final I_MD_Candidate fromWarehouseSupplyStock = DispoTestUtils.retrieveStockCandidate(fromWarehouseSupply);

		final List<I_MD_Candidate> allRecordsBySeqNo = DispoTestUtils.sortBySeqNo(DispoTestUtils.retrieveAllRecords());
		assertThat(allRecordsBySeqNo).containsOnly(
				toWarehouseDemand,
				toWarehouseDemandStock,
				toWarehouseSupplyStock,
				toWarehouseSupply,
				fromWarehouseDemand,
				fromWarehouseDemandStock,
				fromWarehouseSupply,
				fromWarehouseSupplyStock);
		assertThat(allRecordsBySeqNo).containsSubsequence(
				toWarehouseDemand,
				toWarehouseSupply,
				fromWarehouseDemand,
				fromWarehouseSupply);

		assertThat(toWarehouseDemand.getQty()).isEqualByComparingTo("10");
		assertThat(toWarehouseDemandStock.getQty()).isEqualByComparingTo("-10");
		assertThat(toWarehouseSupplyStock.getQty()).isEqualByComparingTo("0");
		assertThat(toWarehouseSupply.getQty()).isEqualByComparingTo("10");

		assertThat(fromWarehouseDemand.getQty()).isEqualByComparingTo("10");
		assertThat(fromWarehouseDemandStock.getQty()).isEqualByComparingTo("-10");
		assertThat(fromWarehouseSupply.getQty()).isEqualByComparingTo("10");
		assertThat(fromWarehouseSupplyStock.getQty()).isEqualByComparingTo("0");
	}

	@Test
	@Disabled("You can extend on this one when starting with https://github.com/metasfresh/metasfresh/issues/2684")
	public void testShipmentScheduleEvent_then_Shipment()
	{
		final ShipmentScheduleCreatedEvent shipmentScheduleEvent = ShipmentScheduleCreatedHandlerTests.createShipmentScheduleTestEvent();

		final MaterialDescriptor orderedMaterial = shipmentScheduleEvent.getMaterialDescriptor();
		final Instant shipmentScheduleEventTime = orderedMaterial.getDate();
		final Instant twoHoursAfterShipmentSched = shipmentScheduleEventTime.plus(2, ChronoUnit.HOURS);

		materialEventListener.onEvent(shipmentScheduleEvent);

		final TransactionCreatedEvent transactionEvent = TransactionCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.materialDescriptor(orderedMaterial.withDate(twoHoursAfterShipmentSched))
				.build();

		materialEventListener.onEvent(transactionEvent);

		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(2);
	}
}
