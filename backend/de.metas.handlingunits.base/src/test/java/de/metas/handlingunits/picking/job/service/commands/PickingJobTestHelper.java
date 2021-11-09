package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.repository.MockedPickingJobLoaderSupportingServicesFactory;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class PickingJobTestHelper
{
	//
	// Services
	private final HUTestHelper huTestHelper;
	public final IProductBL productBL;
	public final PickingCandidateRepository pickingCandidateRepository;
	public final PickingJobService pickingJobService;

	//
	// Master data
	public final I_C_UOM uomEach;
	public final LocatorId shipFromLocatorId;
	public final BPartnerLocationId shipToBPLocationId = BPartnerLocationId.ofRepoId(666, 666);

	public PickingJobTestHelper()
	{
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();

		productBL = Services.get(IProductBL.class);
		pickingCandidateRepository = new PickingCandidateRepository();
		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		final HUReservationService huReservationService = new HUReservationService(new HUReservationRepository());
		pickingJobService = new PickingJobService(
				new PickingJobRepository(),
				new PickingJobLockService(new InMemoryShipmentScheduleLockRepository()),
				new PickingJobSlotService(),
				new PickingCandidateService(
						new PickingConfigRepository(),
						pickingCandidateRepository,
						new HuId2SourceHUsService(new HUTraceRepository()),
						huReservationService,
						bpartnerBL
				),
				new PickingJobHUReservationService(huReservationService),
				new MockedPickingJobLoaderSupportingServicesFactory());

		uomEach = huTestHelper.uomEach;
		final WarehouseId warehouseId = createWarehouseId("warehouse");
		shipFromLocatorId = createLocatorId(warehouseId);
	}

	@SuppressWarnings("SameParameterValue")
	private WarehouseId createWarehouseId(final String name)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setValue(name);
		warehouse.setName(name);
		saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	private LocatorId createLocatorId(final WarehouseId warehouseId)
	{
		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(warehouseId.getRepoId());
		saveRecord(locator);
		return LocatorId.ofRepoId(warehouseId, locator.getM_Locator_ID());
	}

	@Builder(builderMethodName = "packageable", builderClassName = "$PackageableBuilder")
	private void createPackageable(
			@NonNull final OrderAndLineId orderAndLineId,
			@NonNull final ProductId productId,
			@NonNull final String qtyToDeliver,
			@Nullable final Instant date)
	{
		final BigDecimal qtyToDeliverBD = new BigDecimal(qtyToDeliver);
		final Instant dateEffective = date != null ? date : SystemTime.asInstant();

		final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		sched.setAD_Org_ID(OrgId.MAIN.getRepoId());
		sched.setC_BPartner_ID(shipToBPLocationId.getBpartnerId().getRepoId());
		sched.setC_BPartner_Location_ID(shipToBPLocationId.getRepoId());
		sched.setM_Warehouse_ID(shipFromLocatorId.getWarehouseId().getRepoId());
		sched.setM_Product_ID(productId.getRepoId());
		sched.setQtyOrdered(qtyToDeliverBD);
		sched.setQtyToDeliver(qtyToDeliverBD);
		sched.setC_Order_ID(orderAndLineId.getOrderRepoId());
		sched.setC_OrderLine_ID(orderAndLineId.getOrderLineRepoId());
		sched.setDeliveryDate(Timestamp.from(dateEffective));
		sched.setPreparationDate(Timestamp.from(dateEffective));
		InterfaceWrapperHelper.save(sched);

		createPackageableFromShipmentSchedule(sched);

	}

	private void createPackageableFromShipmentSchedule(final I_M_ShipmentSchedule sched)
	{
		final ProductId productId = ProductId.ofRepoId(sched.getM_Product_ID());
		final UomId uomId = productBL.getStockUOMId(productId);

		final I_M_Packageable_V item = InterfaceWrapperHelper.newInstance(I_M_Packageable_V.class);
		item.setAD_Org_ID(sched.getAD_Org_ID());
		item.setM_ShipmentSchedule_ID(sched.getM_ShipmentSchedule_ID());
		item.setC_UOM_ID(uomId.getRepoId());
		item.setQtyOrdered(sched.getQtyOrdered());
		item.setQtyToDeliver(sched.getQtyToDeliver());
		item.setC_BPartner_Customer_ID(sched.getC_BPartner_ID());
		item.setC_BPartner_Location_ID(sched.getC_BPartner_Location_ID());
		item.setBPartnerAddress_Override("deliveryRenderedAddress");
		item.setM_Warehouse_ID(sched.getM_Warehouse_ID());
		item.setShipmentAllocation_BestBefore_Policy(ShipmentAllocationBestBeforePolicy.Expiring_First.getCode());
		item.setM_Product_ID(productId.getRepoId());
		item.setC_OrderSO_ID(sched.getC_Order_ID());
		item.setC_OrderLineSO_ID(sched.getC_OrderLine_ID());
		item.setDeliveryDate(sched.getDeliveryDate());
		item.setPreparationDate(sched.getPreparationDate());
		InterfaceWrapperHelper.save(item);
	}

	public HuId createVHU(final ProductId productId, final String qtyStr)
	{
		final Quantity qty = Quantity.of(qtyStr, productBL.getStockUOM(productId));
		return createHU(HuPackingInstructionsId.VIRTUAL, productId, qty);
	}

	public HuId createVHU(final ProductId productId, final Quantity qty)
	{
		return createHU(HuPackingInstructionsId.VIRTUAL, productId, qty);
	}

	public HuId createHU(final HuPackingInstructionsId huPackingInstructionsId, final ProductId productId, final Quantity qty)
	{
		final IHUProducerAllocationDestination destination;
		HULoader.builder()
				.source(huTestHelper.createDummySourceDestination(
						productId,
						new BigDecimal("99999999"),
						qty.getUOM(),
						true))
				.destination(destination = HUProducerDestination.of(huPackingInstructionsId)
						.setMaxHUsToCreate(1) // we want one HU
						.setHUStatus(X_M_HU.HUSTATUS_Active)
						.setLocatorId(shipFromLocatorId))
				.load(AllocationUtils.builder()
						.setHUContext(huTestHelper.createMutableHUContextOutOfTransaction())
						.setProduct(productId)
						.setQuantity(qty)
						.setDate(SystemTime.asZonedDateTime())
						.setFromReferencedModel(huTestHelper.createDummyReferenceModel())
						.setForceQtyAllocation(true)
						.create());

		final I_M_HU hu = destination.getSingleCreatedHU().orElseThrow(() -> new AdempiereException("Failed creating HU"));
		return HuId.ofRepoId(hu.getM_HU_ID());
	}

	public PickingJobStepId extractSingleStepId(final PickingJob pickingJob)
	{
		return extractSingleStep(pickingJob).getId();
	}

	public PickingJobStep extractSingleStep(final PickingJob pickingJob)
	{
		return CollectionUtils.singleElement(pickingJob.streamSteps().collect(ImmutableList.toImmutableList()));
	}

}
