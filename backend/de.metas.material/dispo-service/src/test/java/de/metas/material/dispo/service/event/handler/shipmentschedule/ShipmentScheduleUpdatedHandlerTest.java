package de.metas.material.dispo.service.event.handler.shipmentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RepositoryTestHelper;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateQtyDetailsRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.shipmentschedule.OldShipmentScheduleData;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDetail;
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-material-dispo-service
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ExtendWith(AdempiereTestWatcher.class)
public class ShipmentScheduleUpdatedHandlerTest
{
	private static final int shipmentScheduleId = 76;
	private static final int orderLineId = 86;
	private static final WarehouseId toWarehouseId = WarehouseId.ofRepoId(30);
	private static final AttributesKey NEW_ASI_KEY = AttributesKey.ofString("2");

	private ShipmentScheduleCreatedHandler shipmentScheduleCreatedHandler;
	private ShipmentScheduleUpdatedHandler shipmentScheduleUpdatedHandler;
	private AvailableToPromiseRepository atpRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final DimensionService dimensionService = new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory()));
		SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final CandidateQtyDetailsRepository candidateQtyDetailsRepository = new CandidateQtyDetailsRepository();
		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo, candidateRepositoryRetrieval, candidateQtyDetailsRepository);

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

		shipmentScheduleUpdatedHandler = new ShipmentScheduleUpdatedHandler(
				candidateChangeHandler,
				candidateRepositoryRetrieval);
	}

	@Test
	public void handleEvent_withNoMaterialDescriptorChange_updatesExistingCandidate()
	{
		// Set up initial DEMAND+STOCK via created handler
		ShipmentScheduleCreatedHandlerTests.performTest(shipmentScheduleCreatedHandler, atpRepository);

		// Build updated material descriptor with same ASI but different qty
		final MaterialDescriptor updatedMaterialDescriptor = MaterialDescriptor.builder()
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.customerId(BPARTNER_ID)
				.quantity(new BigDecimal("12"))
				.warehouseId(toWarehouseId)
				.build();

		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(atpRepository, updatedMaterialDescriptor, "0");

		// Fire update event with same ASI, no oldShipmentScheduleData
		final ShipmentScheduleUpdatedEvent updateEvent = ShipmentScheduleUpdatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.materialDescriptor(updatedMaterialDescriptor)
				.shipmentScheduleDetail(ShipmentScheduleDetail.builder()
						.orderedQuantity(new BigDecimal("12"))
						.orderedQuantityDelta(new BigDecimal("2"))
						.reservedQuantity(new BigDecimal("20"))
						.reservedQuantityDelta(BigDecimal.ZERO)
						.oldShipmentScheduleData(null)
						.build())
				.shipmentScheduleId(shipmentScheduleId)
				.documentLineDescriptor(OrderLineDescriptor.builder()
						.orderLineId(orderLineId)
						.orderId(30)
						.build())
				.build();

		shipmentScheduleUpdatedHandler.handleEvent(updateEvent);

		// Assert: still 2 records (1 DEMAND + 1 STOCK), updated qty
		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(1);

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);

		assertThat(demandRecord.getQty()).isEqualByComparingTo("12");
		assertThat(stockRecord.getQty()).isEqualByComparingTo("-12");
		assertThat(demandRecord.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY.getAsString());
		assertThat(stockRecord.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY.getAsString());
	}

	@Test
	public void handleEvent_withASIChange_deletesOldAndCreatesNewCandidates()
	{
		// Set up initial DEMAND+STOCK via created handler (qty=10, ASI="1")
		ShipmentScheduleCreatedHandlerTests.performTest(shipmentScheduleCreatedHandler, atpRepository);

		// Build old material descriptor (matches what was created)
		final MaterialDescriptor oldMaterialDescriptor = MaterialDescriptor.builder()
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.customerId(BPARTNER_ID)
				.quantity(BigDecimal.TEN)
				.warehouseId(toWarehouseId)
				.build();

		// Build new material descriptor with different ASI
		final ProductDescriptor newProductDescriptor = ProductDescriptor.forProductAndAttributes(
				PRODUCT_ID,
				NEW_ASI_KEY,
				AttributeSetInstanceId.NONE.getRepoId());

		final MaterialDescriptor newMaterialDescriptor = MaterialDescriptor.builder()
				.date(NOW)
				.productDescriptor(newProductDescriptor)
				.customerId(BPARTNER_ID)
				.quantity(BigDecimal.TEN)
				.warehouseId(toWarehouseId)
				.build();

		// Set up ATP mocks for both old and new descriptors
		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(atpRepository, oldMaterialDescriptor, "0");
		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(atpRepository, newMaterialDescriptor, "0");

		// Fire update event with new ASI and oldShipmentScheduleData populated
		final ShipmentScheduleUpdatedEvent updateEvent = ShipmentScheduleUpdatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.materialDescriptor(newMaterialDescriptor)
				.shipmentScheduleDetail(ShipmentScheduleDetail.builder()
						.orderedQuantity(BigDecimal.TEN)
						.orderedQuantityDelta(BigDecimal.ZERO)
						.reservedQuantity(new BigDecimal("20"))
						.reservedQuantityDelta(BigDecimal.ZERO)
						.oldShipmentScheduleData(OldShipmentScheduleData.builder()
								.oldMaterialDescriptor(oldMaterialDescriptor)
								.oldOrderedQuantity(BigDecimal.TEN)
								.oldReservedQuantity(new BigDecimal("20"))
								.build())
						.build())
				.shipmentScheduleId(shipmentScheduleId)
				.documentLineDescriptor(OrderLineDescriptor.builder()
						.orderLineId(orderLineId)
						.orderId(30)
						.build())
				.build();

		shipmentScheduleUpdatedHandler.handleEvent(updateEvent);

		// Assert: 2 records total (old ones physically deleted, new ones created)
		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(1);

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);

		// Verify the new candidates have the NEW ASI key
		assertThat(demandRecord.getStorageAttributesKey()).isEqualTo(NEW_ASI_KEY.getAsString());
		assertThat(stockRecord.getStorageAttributesKey()).isEqualTo(NEW_ASI_KEY.getAsString());

		// Verify quantities
		assertThat(demandRecord.getQty()).isEqualByComparingTo("10");
		assertThat(stockRecord.getQty()).isEqualByComparingTo("-10");

		// Verify parent-child relationship and seqNo
		assertThat(stockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());
		assertThat(demandRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo());
	}

	@Test
	public void handleEvent_withASIChangeAndQtyChange_deletesOldAndCreatesNewWithCorrectQty()
	{
		// Set up initial DEMAND+STOCK via created handler (qty=10, ASI="1")
		ShipmentScheduleCreatedHandlerTests.performTest(shipmentScheduleCreatedHandler, atpRepository);

		// Build old material descriptor (matches what was created)
		final MaterialDescriptor oldMaterialDescriptor = MaterialDescriptor.builder()
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.customerId(BPARTNER_ID)
				.quantity(BigDecimal.TEN)
				.warehouseId(toWarehouseId)
				.build();

		// Build new material descriptor with different ASI AND different qty
		final ProductDescriptor newProductDescriptor = ProductDescriptor.forProductAndAttributes(
				PRODUCT_ID,
				NEW_ASI_KEY,
				AttributeSetInstanceId.NONE.getRepoId());

		final MaterialDescriptor newMaterialDescriptor = MaterialDescriptor.builder()
				.date(NOW)
				.productDescriptor(newProductDescriptor)
				.customerId(BPARTNER_ID)
				.quantity(new BigDecimal("15"))
				.warehouseId(toWarehouseId)
				.build();

		// Set up ATP mocks
		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(atpRepository, oldMaterialDescriptor, "0");
		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(atpRepository, newMaterialDescriptor, "0");

		// Fire update event with new ASI, new qty, and oldShipmentScheduleData populated
		final ShipmentScheduleUpdatedEvent updateEvent = ShipmentScheduleUpdatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.materialDescriptor(newMaterialDescriptor)
				.shipmentScheduleDetail(ShipmentScheduleDetail.builder()
						.orderedQuantity(new BigDecimal("15"))
						.orderedQuantityDelta(new BigDecimal("5"))
						.reservedQuantity(new BigDecimal("20"))
						.reservedQuantityDelta(BigDecimal.ZERO)
						.oldShipmentScheduleData(OldShipmentScheduleData.builder()
								.oldMaterialDescriptor(oldMaterialDescriptor)
								.oldOrderedQuantity(BigDecimal.TEN)
								.oldReservedQuantity(new BigDecimal("20"))
								.build())
						.build())
				.shipmentScheduleId(shipmentScheduleId)
				.documentLineDescriptor(OrderLineDescriptor.builder()
						.orderLineId(orderLineId)
						.orderId(30)
						.build())
				.build();

		shipmentScheduleUpdatedHandler.handleEvent(updateEvent);

		// Assert: 2 records total with new ASI and new qty
		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(1);

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);

		// Verify the new candidates have the NEW ASI key
		assertThat(demandRecord.getStorageAttributesKey()).isEqualTo(NEW_ASI_KEY.getAsString());
		assertThat(stockRecord.getStorageAttributesKey()).isEqualTo(NEW_ASI_KEY.getAsString());

		// Verify quantities reflect the new qty (15)
		assertThat(demandRecord.getQty()).isEqualByComparingTo("15");
		assertThat(stockRecord.getQty()).isEqualByComparingTo("-15");
	}
}
