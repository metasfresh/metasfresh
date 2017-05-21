package de.metas.material.dispo.service.event;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.util.TimeUtil;
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
import de.metas.material.dispo.service.CandidateFactory;
import de.metas.material.dispo.service.event.DistributionPlanEventHandler;
import de.metas.material.dispo.service.event.MDEventListener;
import de.metas.material.dispo.service.event.ProductionPlanEventHandler;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator;
import de.metas.material.event.DistributionPlanEvent;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ShipmentScheduleEvent;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
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
	private static final int orderLineId = 86;

	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	public static final Date t0 = SystemTime.asDate();

	private static final Date t1 = TimeUtil.addMinutes(t0, 10);

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

		final CandidateChangeHandler candidateChangeHandler = new CandidateChangeHandler(candidateRepository, new CandidateFactory(candidateRepository), materialEventService);

		final CandidateService candidateService = new CandidateService(candidateRepository, new MaterialEventService(de.metas.event.Type.LOCAL));

		mdEventListener = new MDEventListener(
				candidateChangeHandler,
				new DistributionPlanEventHandler(
						candidateRepository,
						candidateChangeHandler,
						supplyProposalEvaluator,
						new CandidateService(candidateRepository, materialEventService)),
				new ProductionPlanEventHandler(candidateChangeHandler, candidateService));
	}

	/**
	 * This test is more for myself, to figure out how the system works :-$
	 */
	@Test
	public void testShipmentScheduleEvent()
	{
		final ShipmentScheduleEvent event = ShipmentScheduleEvent.builder()
				.eventDescr(new EventDescr(org.getAD_Client_ID(), org.getAD_Org_ID()))
				.materialDescr(MaterialDescriptor.builder()
						.date(t1)
						.productId(productId)
						.qty(BigDecimal.TEN)
						.warehouseId(toWarehouseId)
						.build())
				.reference(TableRecordReference.of("someTable", 4))
				.orderLineId(orderLineId)
				.build();
		mdEventListener.onEvent(event);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords.size(), is(2));

		assertThat(DispoTestUtils.filter(Type.DEMAND).size(), is(1));
		assertThat(DispoTestUtils.filter(Type.STOCK).size(), is(1));

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(Type.DEMAND).get(0);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);

		assertThat(demandRecord.getSeqNo(), is(stockRecord.getSeqNo() - 1)); // the demand record shall be displayed first
		assertThat(stockRecord.getMD_Candidate_Parent_ID(), is(demandRecord.getMD_Candidate_ID()));

		assertThat(demandRecord.getQty(), comparesEqualTo(BigDecimal.TEN));
		assertThat(stockRecord.getQty(), comparesEqualTo(BigDecimal.TEN.negate())); // the stock is unbalanced, because there is no existing stock and no supply
	}

	/**
	 * This test is more for myself, to figure out how the system works :-$
	 */
	@Test
	public void testShipmentScheduleEven_then_DistributionPlanevent()
	{
		testShipmentScheduleEvent();

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
						.datePromised(t1)
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

		assertThat(DispoTestUtils.retrieveAllRecords().size(), is(5)); // one for the shipment-schedule demand, two for the distribution demand + supply and 2 stocks (one of them shared between shipment-demand and distr-supply)
		final I_MD_Candidate toWarehouseDemand = DispoTestUtils.filter(Type.DEMAND, toWarehouseId).get(0);
		final I_MD_Candidate toWarehouseSharedStock = DispoTestUtils.filter(Type.STOCK, toWarehouseId).get(0);
		final I_MD_Candidate toWarehouseSupply = DispoTestUtils.filter(Type.SUPPLY, toWarehouseId).get(0);

		assertThat(toWarehouseDemand.getSeqNo(), is(toWarehouseSharedStock.getSeqNo() - 1));
		assertThat(toWarehouseSharedStock.getSeqNo(), is(toWarehouseSupply.getSeqNo() - 1));

		assertThat(toWarehouseDemand.getQty(), comparesEqualTo(BigDecimal.TEN));
		assertThat(toWarehouseSharedStock.getQty(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(toWarehouseSupply.getQty(), comparesEqualTo(BigDecimal.TEN));
	}
}
