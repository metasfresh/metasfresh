package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADReferenceService;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.repository.DefaultPickingJobLoaderSupportingServicesFactory;
import de.metas.handlingunits.picking.job.repository.MockedPickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.picking.job.service.external.salesorder.PickingJobSalesOrderService;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.PickingJobShipmentScheduleService;
import de.metas.handlingunits.picking.job.service.external.warehouse.PickingJobWarehouseService;
import de.metas.handlingunits.picking.job.shipment.PickingShipmentService;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesRequest;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.labels.HULabelConfigRepository;
import de.metas.handlingunits.report.labels.HULabelConfigService;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.handlingunits.util.HUTracerInstance;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.location.impl.DummyDocumentLocationBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.picking.api.impl.PackagingDAO;
import de.metas.picking.model.I_M_Picking_Config_V2;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.test.MetasfreshSnapshotFunction;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceCreateRequest;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.UnaryOperator;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class PickingJobTestHelper
{
	public static final MetasfreshSnapshotFunction snapshotSerializer = new MetasfreshSnapshotFunction();

	//
	// Services
	public final PickingJobWarehouseService warehouseService;
	public final MobileUIPickingUserProfileService configService;
	public final PickingJobService pickingJobService;
	public final HUTracerInstance huTracer;
	public final PickingJobScheduleService pickingJobScheduleService;
	public final PickingJobBPartnerService bpartnerService;
	public final PickingJobHUService huService;
	//
	private final IProductBL productBL;
	private final IOrderBL orderBL;
	private final PackagingDAO packagingDAO;
	private final WorkplaceService workplaceService;
	private final HUTestHelper huTestHelper;
	private final HUQRCodesRepository huQRCodesRepository;

	//
	// Master data
	public final OrgId orgId;
	public final I_C_UOM uomEach;
	public final LocatorId shipFromLocatorId;
	public final BPartnerLocationId shipToBPLocationId;
	public final PickingSlotId pickingSlotId;
	public final Workplace workplace;

	public PickingJobTestHelper()
	{
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();
		SystemTime.setFixedTimeSource(LocalDate.parse("2021-01-01").atStartOfDay(MockedPickingJobLoaderSupportingServices.ZONE_ID));

		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());

		// User one record ID sequence for each table
		// because most of the tests are using snapshot testing.
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		orderBL = Services.get(IOrderBL.class);
		packagingDAO = (PackagingDAO)Services.get(IPackagingDAO.class);

		productBL = Services.get(IProductBL.class);
		final HUReservationService huReservationService = new HUReservationService(new HUReservationRepository());
		huQRCodesRepository = new HUQRCodesRepository();

		final PickingCandidateRepository pickingCandidateRepository = new PickingCandidateRepository();
		SpringContextHolder.registerJUnitBean(pickingCandidateRepository); // needed for HUPickingSlotBL

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		this.bpartnerService = new PickingJobBPartnerService(
				bpartnerBL,
				DummyDocumentLocationBL.newInstanceForUnitTesting()
		);

		final PickingJobRepository pickingJobRepository = new PickingJobRepository();
		final HUQRCodesService huQRCodeService = HUQRCodesService.newInstanceForUnitTesting();
		final InventoryService inventoryService = InventoryService.newInstanceForUnitTesting();
		this.configService = MobileUIPickingUserProfileService.newInstanceForUnitTesting();
		final PickingCandidateService pickingCandidateService = new PickingCandidateService(
				new PickingConfigRepository(),
				pickingCandidateRepository,
				new HuId2SourceHUsService(new HUTraceRepository()),
				huReservationService,
				bpartnerBL,
				ADReferenceService.newMocked(),
				inventoryService);
		final PickingJobSlotService pickingJobSlotService = new PickingJobSlotService(
				PickingSlotService.newInstanceForUnitTesting(),
				pickingJobRepository);
		final PickingJobLockService pickingJobLockService = new PickingJobLockService(new InMemoryShipmentScheduleLockRepository());
		pickingJobScheduleService = PickingJobScheduleService.newInstanceForUnitTesting();
		this.workplaceService = new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository());
		this.warehouseService = new PickingJobWarehouseService(workplaceService);
		final HULabelService huLabelService = new HULabelService(
				new HULabelConfigService(new HULabelConfigRepository()),
				huQRCodeService
		);

		this.huService = new PickingJobHUService(
				configService,
				warehouseService,
				huQRCodeService,
				huLabelService,
				huReservationService,
				inventoryService);

		final DefaultPickingJobLoaderSupportingServicesFactory defaultPickingJobLoaderSupportingServicesFactory = new DefaultPickingJobLoaderSupportingServicesFactory(
				configService,
				new PickingJobSalesOrderService(),
				warehouseService,
				bpartnerService,
				new PickingJobProductService(),
				pickingJobSlotService,
				pickingJobLockService,
				huService
		);

		pickingJobService = new PickingJobService(
				bpartnerService,
				warehouseService,
				new PickingJobProductService(),
				new PickingJobShipmentScheduleService(),
				pickingJobRepository,
				pickingJobLockService,
				pickingJobSlotService,
				pickingCandidateService,
				defaultPickingJobLoaderSupportingServicesFactory,
				PickingShipmentService.newInstanceForUnitTesting(),
				configService,
				pickingJobScheduleService,
				huService
		);

		huTracer = new HUTracerInstance()
				.dumpAttributes(false)
				.dumpItemStorage(false)
				.dumpHUReservations(huReservationService);

		final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL = Mockito.mock(IShipmentScheduleInvalidateBL.class);
		Services.registerService(IShipmentScheduleInvalidateBL.class, shipmentScheduleInvalidateBL);

		//
		// Master data
		orgId = AdempiereTestHelper.createOrgWithTimeZone(MockedPickingJobLoaderSupportingServices.ZONE_ID);
		uomEach = huTestHelper.uomEach;
		shipToBPLocationId = createBPartnerAndLocationId("BPartner1");
		final WarehouseId shipFromWarehouseId = createWarehouseId("warehouse");
		shipFromLocatorId = createLocatorId(shipFromWarehouseId, "wh_loc");
		createPickingConfigV2(true);
		this.pickingSlotId = createPickingSlot();

		this.workplace = workplaceService.create(WorkplaceCreateRequest.builder()
				.name("workplace")
				.warehouseId(shipFromWarehouseId)
				.build());
	}

	private PickingSlotId createPickingSlot()
	{
		final I_M_PickingSlot pickingSlot = InterfaceWrapperHelper.newInstance(I_M_PickingSlot.class);
		pickingSlot.setPickingSlot("PICKING_SLOT");
		InterfaceWrapperHelper.save(pickingSlot);

		return PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID());
	}

	@NonNull
	@SuppressWarnings("SameParameterValue")
	public BPartnerLocationId createBPartnerAndLocationId(final String name)
	{
		final I_C_BPartner bpartner = BusinessTestHelper.createBPartner(name);
		final I_C_BPartner_Location bpartnerLocation = BusinessTestHelper.createBPartnerLocation(bpartner);
		return BPartnerLocationId.ofRepoId(bpartnerLocation.getC_BPartner_ID(), bpartnerLocation.getC_BPartner_Location_ID());
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

	@SuppressWarnings("SameParameterValue")
	private LocatorId createLocatorId(final WarehouseId warehouseId, final String locatorValue)
	{
		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(warehouseId.getRepoId());
		locator.setValue(locatorValue);
		saveRecord(locator);
		return LocatorId.ofRepoId(warehouseId, locator.getM_Locator_ID());
	}

	public void updateMobileProfile(UnaryOperator<MobileUIPickingUserProfile> updater)
	{
		configService.update(updater);
	}

	public OrderAndLineId createOrderAndLineId(final String documentNo)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocumentNo(documentNo);
		save(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		save(orderLine);

		return OrderAndLineId.ofRepoIds(orderLine.getC_Order_ID(), orderLine.getC_OrderLine_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private void createPickingConfigV2(final boolean considerAttributes)
	{
		final I_M_Picking_Config_V2 pickingConfigV2 = newInstance(I_M_Picking_Config_V2.class);
		pickingConfigV2.setIsConsiderAttributes(considerAttributes);
		save(pickingConfigV2);
	}

	@Builder(builderMethodName = "packageable", builderClassName = "$PackageableBuilder")
	private Packageable createPackageable(
			@NonNull final OrderAndLineId orderAndLineId,
			@Nullable final BPartnerLocationId shipToBPLocationId,
			@NonNull final ProductId productId,
			@Nullable final HUPIItemProductId huPIItemProductId,
			@NonNull final String qtyToDeliver,
			@Nullable final Instant date,
			@Nullable final UserId lockedBy,
			boolean assignToWorkplace)
	{
		final BPartnerLocationId shipToBPLocationIdEffective = shipToBPLocationId != null ? shipToBPLocationId : this.shipToBPLocationId;
		final BigDecimal qtyToDeliverBD = new BigDecimal(qtyToDeliver);
		final Instant dateEffective = date != null ? date : SystemTime.asInstant();

		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setAD_Org_ID(orgId.getRepoId());
		shipmentSchedule.setC_BPartner_ID(shipToBPLocationIdEffective.getBpartnerId().getRepoId());
		shipmentSchedule.setC_BPartner_Location_ID(shipToBPLocationIdEffective.getRepoId());
		shipmentSchedule.setM_Warehouse_ID(shipFromLocatorId.getWarehouseId().getRepoId());
		shipmentSchedule.setM_Product_ID(productId.getRepoId());
		shipmentSchedule.setM_HU_PI_Item_Product_ID(HUPIItemProductId.toRepoId(huPIItemProductId));
		shipmentSchedule.setQtyOrdered(qtyToDeliverBD);
		shipmentSchedule.setQtyToDeliver(qtyToDeliverBD);
		shipmentSchedule.setC_Order_ID(orderAndLineId.getOrderRepoId());
		shipmentSchedule.setC_OrderLine_ID(orderAndLineId.getOrderLineRepoId());
		shipmentSchedule.setDeliveryDate(Timestamp.from(dateEffective));
		shipmentSchedule.setPreparationDate(Timestamp.from(dateEffective));
		save(shipmentSchedule);
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());

		if (assignToWorkplace)
		{
			pickingJobScheduleService.createOrUpdate(CreateOrUpdatePickingJobSchedulesRequest.builder()
					.shipmentScheduleAndJobScheduleIds(ShipmentScheduleAndJobScheduleIdSet.of(shipmentScheduleId))
					.workplaceId(workplace.getId())
					.build());
		}

		return createPackageableFromShipmentSchedule(shipmentSchedule, lockedBy);
	}

	private Packageable createPackageableFromShipmentSchedule(
			@NonNull final I_M_ShipmentSchedule sched,
			@Nullable final UserId lockedBy)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(sched.getC_Order_ID()));

		final BPartnerLocationId shipToBPLocationId = BPartnerLocationId.ofRepoId(sched.getC_BPartner_ID(), sched.getC_BPartner_Location_ID());
		final String bpName = bpartnerService.getBPartnerName(shipToBPLocationId.getBpartnerId());

		final ProductId productId = ProductId.ofRepoId(sched.getM_Product_ID());
		final UomId uomId = productBL.getStockUOMId(productId);

		final I_M_Packageable_V item = InterfaceWrapperHelper.newInstance(I_M_Packageable_V.class);
		item.setAD_Org_ID(sched.getAD_Org_ID());
		item.setM_ShipmentSchedule_ID(sched.getM_ShipmentSchedule_ID());
		item.setC_UOM_ID(uomId.getRepoId());
		item.setQtyOrdered(sched.getQtyOrdered());
		item.setQtyToDeliver(sched.getQtyToDeliver());
		item.setC_BPartner_Customer_ID(shipToBPLocationId.getBpartnerId().getRepoId());
		item.setC_BPartner_Location_ID(shipToBPLocationId.getRepoId());
		item.setBPartnerName(bpName);
		item.setHandOver_Partner_ID(shipToBPLocationId.getBpartnerId().getRepoId());
		item.setHandOver_Location_ID(shipToBPLocationId.getRepoId());
		item.setBPartnerAddress_Override("deliveryRenderedAddress");
		item.setM_Warehouse_ID(sched.getM_Warehouse_ID());
		item.setShipmentAllocation_BestBefore_Policy(ShipmentAllocationBestBeforePolicy.Expiring_First.getCode());
		item.setM_Product_ID(productId.getRepoId());
		item.setPackTo_HU_PI_Item_Product_ID(CoalesceUtil.firstGreaterThanZero(
				sched.getM_HU_PI_Item_Product_Override_ID(),
				sched.getM_HU_PI_Item_Product_ID()));
		item.setC_OrderSO_ID(sched.getC_Order_ID());
		item.setC_OrderLineSO_ID(sched.getC_OrderLine_ID());
		item.setOrderDocumentNo(order.getDocumentNo());
		item.setDeliveryDate(sched.getDeliveryDate());
		item.setPreparationDate(sched.getPreparationDate());

		if (lockedBy != null)
		{
			item.setLockedBy_User_ID(lockedBy.getRepoId());
		}

		save(item);

		return packagingDAO.toPackageable(item);
	}

	public HUInfo createVHUInfo(@NonNull final ProductId productId, @NonNull final String qtyStr, @NonNull final String qrCodeId)
	{
		final HuId huId = createVHU(productId, qtyStr);
		final HUQRCode qrCode = createQRCode(huId, qrCodeId);
		return HUInfo.builder().id(huId).qrCode(qrCode).build();
	}

	public HuId createVHU(@NonNull final ProductId productId, @NonNull final String qtyStr)
	{
		return createHU(HuPackingInstructionsId.VIRTUAL, productId, qty(qtyStr, productId));
	}

	public Quantity qty(@NonNull final String qtyStr, @NonNull final ProductId productId)
	{
		return Quantity.of(qtyStr, productBL.getStockUOM(productId));
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

	public HuId createLU(
			@NonNull final LUPackingInstructions luPackingInstructions,
			@NonNull final String totalQtyCU)
	{
		final I_M_HU_PI_Item_Product tuPIItemProduct = huTestHelper.huPIItemProductBL().getRecordById(luPackingInstructions.getTuPackingInstructionId());

		final I_M_HU lu = huTestHelper.newLUs()
				.huContext(huTestHelper.createMutableHUContextOutOfTransaction())
				.loadingUnitPIItem(luPackingInstructions.getLuPIItem())
				.tuPIItemProduct(tuPIItemProduct)
				.totalQtyCU(new BigDecimal(totalQtyCU))
				.locatorId(shipFromLocatorId)
				.huStatus(X_M_HU.HUSTATUS_Active)
				.buildSingleLU();

		return HuId.ofRepoId(lu.getM_HU_ID());
	}

	public HUQRCode getQRCode(final HuId huId)
	{
		return huQRCodesRepository.getFirstQRCodeByHuId(huId).orElseThrow(() -> new AdempiereException("No QRCode found for HU " + huId));
	}

	public HUQRCode createQRCode(@NonNull final HuId huId, @NonNull final String qrCodeId)
	{
		return createQRCode(huId, HUQRCodeUniqueId.ofJson(qrCodeId));
	}

	private HUQRCode createQRCode(@NonNull final HuId huId, @NonNull final HUQRCodeUniqueId qrCodeId)
	{
		final I_M_HU hu = huTestHelper.handlingUnitsBL().getById(huId);
		final I_M_HU_PI_Version piVersion = huTestHelper.handlingUnitsBL().getPIVersion(hu);
		final I_M_HU_PI pi = huTestHelper.handlingUnitsBL().getPI(piVersion);

		final ProductId productId = huTestHelper.handlingUnitsBL().getStorageFactory().getStorage(hu).getSingleProductIdOrNull();
		if (productId == null)
		{
			throw new AdempiereException("Only single product storages are supported");
		}

		final I_M_Product product = productBL.getById(productId);

		final HUQRCode huQRCode = HUQRCode.builder()
				.id(qrCodeId)
				.packingInfo(HUQRCodePackingInfo.builder()
						.huUnitType(HUQRCodeUnitType.ofCode(Objects.requireNonNull(piVersion.getHU_UnitType())))
						.packingInstructionsId(HuPackingInstructionsId.ofRepoId(piVersion.getM_HU_PI_ID()))
						.caption(pi.getName())
						.build())
				.product(HUQRCodeProductInfo.builder()
						.id(productId)
						.code(product.getValue())
						.name(product.getName())
						.build())
				.attributes(ImmutableList.of())
				.build();

		huQRCodesRepository.createNew(huQRCode, huId);

		return huQRCode;
	}

	public String toJson(final Object obj)
	{
		return snapshotSerializer.toJson(obj);
	}

	public void assignCurrentUserToWorkplace()
	{
		workplaceService.assignWorkplace(Env.getLoggedUserId(), workplace.getId());
	}
}
