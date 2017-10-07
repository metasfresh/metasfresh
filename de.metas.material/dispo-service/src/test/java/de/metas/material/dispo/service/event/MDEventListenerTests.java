package de.metas.material.dispo.service.event;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Org;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidateService;
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.CandidateChangeHandler;
import de.metas.material.dispo.service.StockCandidateFactory;
import de.metas.material.dispo.service.event.handler.DistributionPlanEventHandler;
import de.metas.material.dispo.service.event.handler.ForecastEventHandler;
import de.metas.material.dispo.service.event.handler.ProductionPlanEventHandler;
import de.metas.material.dispo.service.event.handler.ReceiptScheduleEventHandler;
import de.metas.material.dispo.service.event.handler.ShipmentScheduleEventHandler;
import de.metas.material.dispo.service.event.handler.ShipmentScheduleEventHandlerTests;
import de.metas.material.dispo.service.event.handler.TransactionEventHandler;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ShipmentScheduleEvent;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.ddorder.DistributionPlanEvent;
import mockit.Mocked;

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
public class MDEventListenerTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	public static final int fromWarehouseId = 10;
	public static final int intermediateWarehouseId = 20;
	public static final int toWarehouseId = 30;

	public static final int productId = 40;

	private MDEventListener mdEventListener;

	private I_AD_Org org;

	@Mocked
	private MaterialEventService materialEventService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		org = newInstance(I_AD_Org.class);
		save(org);

		final CandidateRepository candidateRepository = new CandidateRepository();
		final SupplyProposalEvaluator supplyProposalEvaluator = new SupplyProposalEvaluator(candidateRepository);

		final StockCandidateFactory stockCandidateService = new StockCandidateFactory(candidateRepository);

		final CandidateChangeHandler candidateChangeHandler = new CandidateChangeHandler(candidateRepository, stockCandidateService, materialEventService);

		final CandidateService candidateService = new CandidateService(
				candidateRepository,
				MaterialEventService.createLocalServiceThatIsReadyToUse());

		final DistributionPlanEventHandler distributionPlanEventHandler = new DistributionPlanEventHandler(
				candidateRepository,
				candidateChangeHandler,
				supplyProposalEvaluator,
				new CandidateService(candidateRepository, materialEventService));

		final ProductionPlanEventHandler productionPlanEventHandler = new ProductionPlanEventHandler(candidateChangeHandler, candidateService);

		final ForecastEventHandler forecastEventHandler = new ForecastEventHandler(candidateChangeHandler);

		final TransactionEventHandler transactionEventHandler = new TransactionEventHandler(stockCandidateService, candidateChangeHandler);

		final ShipmentScheduleEventHandler shipmentScheduleEventHandler = new ShipmentScheduleEventHandler(candidateChangeHandler);

		final ReceiptScheduleEventHandler receiptScheduleEventHandler = new ReceiptScheduleEventHandler(candidateChangeHandler);

		mdEventListener = new MDEventListener(
				distributionPlanEventHandler,
				productionPlanEventHandler,
				forecastEventHandler,
				transactionEventHandler,
				receiptScheduleEventHandler,
				shipmentScheduleEventHandler);
	}

	/**
	 * This test is more for myself, to figure out how the system works :-$
	 */
	@Test
	public void testShipmentScheduleEvent_then_DistributionPlanevent()
	{
		final ShipmentScheduleEvent shipmentScheduleEvent = ShipmentScheduleEventHandlerTests.createShipmentScheduleTestEvent(org);
		final Date shipmentScheduleEventTime = shipmentScheduleEvent.getMaterialDescr().getDate();

		mdEventListener.onEvent(shipmentScheduleEvent);

		// create a DistributionPlanEvent event which matches the shipmentscheduleEvent that we processed in testShipmentScheduleEvent()
		final TableRecordReference reference = TableRecordReference.of("someTable", 4);
		final DistributionPlanEvent event = DistributionPlanEvent.builder()
				.eventDescr(new EventDescr(org.getAD_Client_ID(), org.getAD_Org_ID()))
				.fromWarehouseId(fromWarehouseId)
				.toWarehouseId(toWarehouseId)
				.ddOrder(DDOrder.builder()
						.orgId(org.getAD_Org_ID())
						.plantId(800)
						.productPlanningId(810)
						.shipperId(820)
						.datePromised(shipmentScheduleEventTime)
						.line(DDOrderLine.builder()
								.productId(productId)
								.qty(BigDecimal.TEN)
								.durationDays(0)
								.networkDistributionLineId(900)
								.build())
						.build())
				.reference(reference)
				.build();
		mdEventListener.onEvent(event);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(5); // one for the shipment-schedule demand, two for the distribution demand + supply and 2 stocks (one of them shared between shipment-demand and distr-supply)
		final I_MD_Candidate toWarehouseDemand = DispoTestUtils.filter(Type.DEMAND, toWarehouseId).get(0);
		final I_MD_Candidate toWarehouseSharedStock = DispoTestUtils.filter(Type.STOCK, toWarehouseId).get(0);
		final I_MD_Candidate toWarehouseSupply = DispoTestUtils.filter(Type.SUPPLY, toWarehouseId).get(0);

		assertThat(toWarehouseDemand.getSeqNo()).isEqualTo(toWarehouseSharedStock.getSeqNo() - 1);
		assertThat(toWarehouseSharedStock.getSeqNo()).isEqualTo(toWarehouseSupply.getSeqNo() - 1);

		assertThat(toWarehouseDemand.getQty()).isEqualByComparingTo(BigDecimal.TEN);
		assertThat(toWarehouseSharedStock.getQty()).isZero();
		assertThat(toWarehouseSupply.getQty()).isEqualByComparingTo(BigDecimal.TEN);
	}
}
