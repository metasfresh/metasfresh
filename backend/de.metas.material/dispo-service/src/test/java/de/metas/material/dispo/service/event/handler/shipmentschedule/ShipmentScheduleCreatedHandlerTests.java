package de.metas.material.dispo.service.event.handler.shipmentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RepositoryTestHelper;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDetail;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-material-dispo-service
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

@ExtendWith(AdempiereTestWatcher.class)
public class ShipmentScheduleCreatedHandlerTests
{
	private static final int shipmentScheduleId = 76;

	private static final int orderLineId = 86;

	private static final WarehouseId toWarehouseId = WarehouseId.ofRepoId(30);

	private ShipmentScheduleCreatedHandler shipmentScheduleCreatedHandler;
	private AvailableToPromiseRepository atpRepository;

	private DimensionService dimensionService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new MDCandidateDimensionFactory());
		dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);

		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo, candidateRepositoryRetrieval);

		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);

		atpRepository = Mockito.spy(AvailableToPromiseRepository.class);

		final StockCandidateService stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryWriteService);

		final SupplyCandidateHandler supplyCandidateHandler = new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService);

		final CandidateChangeService candidateChangeHandler = new CandidateChangeService(ImmutableList.of(
				new DemandCandiateHandler(
						candidateRepositoryRetrieval,
						candidateRepositoryWriteService,
						postMaterialEventService,
						atpRepository,
						stockCandidateService,
						supplyCandidateHandler)));

		shipmentScheduleCreatedHandler = new ShipmentScheduleCreatedHandler(
				candidateChangeHandler,
				candidateRepositoryRetrieval);
	}

	@Test
	public void handleEvent()
	{
		performTest(shipmentScheduleCreatedHandler, atpRepository);
	}

	public static int performTest(
			@NonNull final ShipmentScheduleCreatedHandler shipmentScheduleEventHandler,
			@NonNull final AvailableToPromiseRepository atpRepository)
	{
		final ShipmentScheduleCreatedEvent event = createShipmentScheduleTestEvent();

		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(
				atpRepository,
				event.getMaterialDescriptor(),
				"0"); // ATP-quantity = 0

		shipmentScheduleEventHandler.handleEvent(event);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(1);

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);

		assertThat(demandRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo());
		assertThat(stockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());

		assertThat(demandRecord.getQty()).isEqualByComparingTo("10");
		assertThat(demandRecord.getC_BPartner_Customer_ID()).isEqualTo(BPARTNER_ID.getRepoId());
		assertThat(demandRecord.isReservedForCustomer()).isFalse();

		assertThat(stockRecord.getQty()).isEqualByComparingTo("-10"); // the stock is unbalanced, because there is no existing stock and no supply
		assertThat(stockRecord.getC_BPartner_Customer_ID()).isLessThanOrEqualTo(0); // the stock record has no bpartner, because reservedForCustomer==false

		final List<I_MD_Candidate_Demand_Detail> demandDetailRecords = POJOLookupMap.get().getRecords(I_MD_Candidate_Demand_Detail.class);
		assertThat(demandDetailRecords).hasSize(1);
		assertThat(demandDetailRecords.get(0).getM_ShipmentSchedule_ID()).isEqualTo(event.getShipmentScheduleId());

		return event.getShipmentScheduleId();
	}

	public static ShipmentScheduleCreatedEvent createShipmentScheduleTestEvent()
	{
		return ShipmentScheduleCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.materialDescriptor(MaterialDescriptor.builder()
						.date(NOW)
						.productDescriptor(createProductDescriptor())
						.customerId(BPARTNER_ID)
						.quantity(BigDecimal.TEN)
						.warehouseId(toWarehouseId)
						.build())
				.shipmentScheduleDetail(ShipmentScheduleDetail.builder()
												.orderedQuantity(BigDecimal.TEN)
												.orderedQuantityDelta(BigDecimal.TEN)
												.reservedQuantity(new BigDecimal("20"))
												.reservedQuantityDelta(new BigDecimal("20"))
												.build())
				.shipmentScheduleId(shipmentScheduleId)
				.documentLineDescriptor(OrderLineDescriptor.builder()
						.orderLineId(orderLineId)
						.orderId(30)
						.build())
				.build();
	}
}
