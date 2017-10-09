package de.metas.material.dispo.service.event.handler;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidateService;
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.ProductionPlanEvent;
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

public class ProdcutionPlanEventHandlerTests
{
	@Rule
	public final AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();
	
	public static final Date t0 = SystemTime.asDate();

	private static final Date t1 = TimeUtil.addMinutes(t0, 10);

	private static final Date t2 = TimeUtil.addMinutes(t0, 20);

	public static final int rawProduct1Id = 50;

	public static final int rawProduct2Id = 55;

	public static final int productId = 40;

	public static final int intermediateWarehouseId = 20;

	private TableRecordReference reference;

	private final BigDecimal eleven = BigDecimal.TEN.add(BigDecimal.ONE);

	private I_AD_Org org;

	@Mocked
	private MaterialEventService materialEventService;

	private ProductionPlanEventHandler productionPlanEventHandler;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		reference = TableRecordReference.of("someTable", 4);

		org = newInstance(I_AD_Org.class);
		save(org);

		final CandidateRepository candidateRepository = new CandidateRepository();
		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(candidateRepository, new StockCandidateService(candidateRepository), materialEventService);
		final CandidateService candidateService = new CandidateService(
				candidateRepository, 
				MaterialEventService.createLocalServiceThatIsReadyToUse());

		productionPlanEventHandler = new ProductionPlanEventHandler(candidateChangeHandler, candidateService);
	}

	@Test
	public void testProductionPlanEvent()
	{
		final ProductionPlanEvent productionPlanEvent = createProductionPlanEvent();
		perform_testProductionPlanEvent(productionPlanEvent);
	}

	@Test
	public void testProductionPlanEvent_invoke_twice()
	{
		final ProductionPlanEvent productionPlanEvent = createProductionPlanEvent();
		perform_testProductionPlanEvent(productionPlanEvent);
		perform_testProductionPlanEvent(productionPlanEvent);
	}

	private void perform_testProductionPlanEvent(final ProductionPlanEvent productionPlanEvent)
	{
		productionPlanEventHandler.handleProductionPlanEvent(productionPlanEvent);

		assertThat(DispoTestUtils.filter(Type.SUPPLY).size(), is(1)); //
		assertThat(DispoTestUtils.filter(Type.DEMAND).size(), is(2)); // we have two different inputs
		assertThat(DispoTestUtils.filter(Type.STOCK).size(), is(3)); // one stock record per supply, one per demand

		final I_MD_Candidate t2Stock = DispoTestUtils.filter(Type.STOCK, t2).get(0);
		assertThat(t2Stock.getQty(), comparesEqualTo(BigDecimal.ONE));
		assertThat(t2Stock.getM_Product_ID(), is(productId));
		assertThat(t2Stock.getMD_Candidate_GroupId(), greaterThan(0)); // stock candidates have their own groupIds too
		assertThat(t2Stock.getMD_Candidate_Parent_ID(), lessThanOrEqualTo(0));

		final I_MD_Candidate t2Supply = DispoTestUtils.filter(Type.SUPPLY, t2).get(0);
		assertThat(t2Supply.getQty(), comparesEqualTo(BigDecimal.ONE));
		assertThat(t2Supply.getM_Product_ID(), is(productId));
		assertThat(t2Supply.getMD_Candidate_Parent_ID(), is(t2Stock.getMD_Candidate_ID()));
		assertThat(t2Supply.getMD_Candidate_GroupId(), not(is(t2Stock.getMD_Candidate_GroupId()))); // stock candidates' groupIds are different from supply/demand groups' groupIds

		final int supplyDemandGroupId = t2Supply.getMD_Candidate_GroupId();
		assertThat(supplyDemandGroupId, greaterThan(0));

		final I_MD_Candidate t1Product1Demand = DispoTestUtils.filter(Type.DEMAND, t1, rawProduct1Id).get(0);
		assertThat(t1Product1Demand.getQty(), comparesEqualTo(BigDecimal.TEN));
		assertThat(t1Product1Demand.getM_Product_ID(), is(rawProduct1Id));
		assertThat(t1Product1Demand.getMD_Candidate_GroupId(), is(supplyDemandGroupId));
		// no parent relationship between production supply and demand because it can be m:n
		// assertThat(t1Product1Demand.getMD_Candidate_Parent_ID(), is(t2Supply.getMD_Candidate_ID()));

		final I_MD_Candidate t1Product1Stock = DispoTestUtils.filter(Type.STOCK, t1, rawProduct1Id).get(0);
		assertThat(t1Product1Stock.getQty(), comparesEqualTo(BigDecimal.TEN.negate()));
		assertThat(t1Product1Stock.getM_Product_ID(), is(rawProduct1Id));
		assertThat(t1Product1Stock.getMD_Candidate_GroupId(), greaterThan(0));  // stock candidates have their own groupIds too
		assertThat(t1Product1Stock.getMD_Candidate_GroupId(), not(is(supplyDemandGroupId)));  // stock candidates' groupIds are different from supply/demand groups' groupIds
		assertThat(t1Product1Stock.getMD_Candidate_GroupId(), not(is(t2Stock.getMD_Candidate_GroupId())));  // stock candidates' groupIds are different if they are about different products or warehouses

		assertThat(t1Product1Stock.getMD_Candidate_Parent_ID(), is(t1Product1Demand.getMD_Candidate_ID()));

		final I_MD_Candidate t1Product2Demand = DispoTestUtils.filter(Type.DEMAND, t1, rawProduct2Id).get(0);
		assertThat(t1Product2Demand.getQty(), comparesEqualTo(eleven));
		assertThat(t1Product2Demand.getM_Product_ID(), is(rawProduct2Id));
		assertThat(t1Product2Demand.getMD_Candidate_GroupId(), is(supplyDemandGroupId));
		// no parent relationship between production supply and demand because it can be m:n
		// assertThat(t1Product2Demand.getMD_Candidate_Parent_ID(), is(t2Supply.getMD_Candidate_ID()));

		final I_MD_Candidate t1Product2Stock = DispoTestUtils.filter(Type.STOCK, t1, rawProduct2Id).get(0);
		assertThat(t1Product2Stock.getQty(), comparesEqualTo(eleven.negate()));
		assertThat(t1Product2Stock.getM_Product_ID(), is(rawProduct2Id));
		assertThat(t1Product2Stock.getMD_Candidate_GroupId(), greaterThan(0)); // stock candidates have their own groupIds too
		assertThat(t1Product2Stock.getMD_Candidate_Parent_ID(), is(t1Product2Demand.getMD_Candidate_ID()));
		assertThat(t1Product2Stock.getMD_Candidate_GroupId(), not(is(t1Product1Stock.getMD_Candidate_GroupId())));  // stock candidates' groupIds are different if they are about different products or warehouses
	}

	private ProductionPlanEvent createProductionPlanEvent()
	{
		final ProductionPlanEvent productionPlanEvent = ProductionPlanEvent.builder()
				.eventDescr(new EventDescr(org.getAD_Client_ID(), org.getAD_Org_ID()))
				.ppOrder(PPOrder.builder()
						.orgId(org.getAD_Org_ID())
						.datePromised(t2)
						.dateStartSchedule(t1)
						.productId(productId)
						.quantity(BigDecimal.ONE)
						.warehouseId(intermediateWarehouseId)
						.plantId(120)
						.uomId(130)
						.productPlanningId(140)
						.line(PPOrderLine.builder()
								.description("descr1")
								.productId(rawProduct1Id)
								.qtyRequired(BigDecimal.TEN)
								.productBomLineId(1020)
								.receipt(false)
								.build())
						.line(PPOrderLine.builder()
								.description("descr2")
								.productId(rawProduct2Id)
								.qtyRequired(eleven)
								.productBomLineId(1030)
								.receipt(false)
								.build())
						.build())
				.reference(reference)
				.build();
		return productionPlanEvent;
	}

}
