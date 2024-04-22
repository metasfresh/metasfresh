package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.job.repository.DefaultPickingJobLoaderSupportingServicesFactory;
import de.metas.handlingunits.picking.job.repository.MockedPickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.picking.job.service.TestRecorder;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.shipmentschedule.api.ShipmentService;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.handlingunits.util.HUTracerInstance;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_Picking_Config_V2;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityTU;
import de.metas.test.SnapshotFunctionFactory;
import de.metas.uom.UomId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
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
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class PickingJobTestHelper
{
	public static final Function<Object, String> snapshotSerializeFunction = SnapshotFunctionFactory.newFunction();

	//
	// Services
	private final HUTestHelper huTestHelper;
	public final HUReservationService huReservationService;
	public final HUQRCodesRepository huQRCodesRepository;
	public final IProductBL productBL;
	public final PickingCandidateRepository pickingCandidateRepository;
	public final PickingConfigRepositoryV2 pickingConfigRepo;
	public final PickingJobService pickingJobService;
	public final HUTracerInstance huTracer;

	//
	// Master data
	public final OrgId orgId;
	public final I_C_UOM uomEach;
	public final LocatorId shipFromLocatorId;
	public final BPartnerLocationId shipToBPLocationId;

	public PickingJobTestHelper()
	{
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();
		SystemTime.setFixedTimeSource(LocalDate.parse("2021-01-01").atStartOfDay(MockedPickingJobLoaderSupportingServices.ZONE_ID));

		// User one record ID sequence for each table
		// because most of the tests are using snapshot testing.
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		productBL = Services.get(IProductBL.class);
		huReservationService = new HUReservationService(new HUReservationRepository());
		huQRCodesRepository = new HUQRCodesRepository();

		pickingCandidateRepository = new PickingCandidateRepository();
		SpringContextHolder.registerJUnitBean(pickingCandidateRepository); // needed for HUPickingSlotBL

		pickingConfigRepo = new PickingConfigRepositoryV2();

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		final PickingJobRepository pickingJobRepository = new PickingJobRepository();
		final PickingJobSlotService pickingJobSlotService = new PickingJobSlotService(pickingJobRepository);
		final HUQRCodesService huQRCodeService = new HUQRCodesService(huQRCodesRepository, new GlobalQRCodeService());
		final InventoryService inventoryService = InventoryService.newInstanceForUnitTesting();
		pickingJobService = new PickingJobService(
				pickingJobRepository,
				new PickingJobLockService(new InMemoryShipmentScheduleLockRepository()),
				pickingJobSlotService,
				new PickingCandidateService(
						new PickingConfigRepository(),
						pickingCandidateRepository,
						new HuId2SourceHUsService(new HUTraceRepository()),
						huReservationService,
						bpartnerBL,
						inventoryService
				),
				new PickingJobHUReservationService(huReservationService),
				new DefaultPickingJobLoaderSupportingServicesFactory(
						pickingJobSlotService,
						bpartnerBL,
						huQRCodeService
				),
				pickingConfigRepo,
				ShipmentService.getInstance(),
				huQRCodeService,
				inventoryService,
				huReservationService);

		huTracer = new HUTracerInstance()
				.dumpAttributes(false)
				.dumpItemStorage(false)
				.dumpHUReservations(huReservationService);

		//
		// Master data
		orgId = AdempiereTestHelper.createOrgWithTimeZone(MockedPickingJobLoaderSupportingServices.ZONE_ID);
		uomEach = huTestHelper.uomEach;
		shipToBPLocationId = createBPartnerAndLocationId("BPartner1");
		shipFromLocatorId = createLocatorId(createWarehouseId("warehouse"), "wh_loc");
		createPickingConfigV2(true);
	}

	@NonNull
	@SuppressWarnings("SameParameterValue")
	private BPartnerLocationId createBPartnerAndLocationId(final String name)
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

	public PickingSlotId createPickingSlot(final String pickingSlot)
	{
		final I_M_PickingSlot record = newInstance(I_M_PickingSlot.class);
		record.setPickingSlot(pickingSlot);
		record.setIsDynamic(true);
		save(record);
		return PickingSlotId.ofRepoId(record.getM_PickingSlot_ID());
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
	private void createPackageable(
			@NonNull final OrderAndLineId orderAndLineId,
			@NonNull final ProductId productId,
			@Nullable final HUPIItemProductId huPIItemProductId,
			@NonNull final String qtyToDeliver,
			@Nullable final Instant date)
	{
		final BigDecimal qtyToDeliverBD = new BigDecimal(qtyToDeliver);
		final Instant dateEffective = date != null ? date : SystemTime.asInstant();

		final I_M_ShipmentSchedule sched = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		sched.setAD_Org_ID(orgId.getRepoId());
		sched.setC_BPartner_ID(shipToBPLocationId.getBpartnerId().getRepoId());
		sched.setC_BPartner_Location_ID(shipToBPLocationId.getRepoId());
		sched.setM_Warehouse_ID(shipFromLocatorId.getWarehouseId().getRepoId());
		sched.setM_Product_ID(productId.getRepoId());
		sched.setM_HU_PI_Item_Product_ID(HUPIItemProductId.toRepoId(huPIItemProductId));
		sched.setQtyOrdered(qtyToDeliverBD);
		sched.setQtyToDeliver(qtyToDeliverBD);
		sched.setC_Order_ID(orderAndLineId.getOrderRepoId());
		sched.setC_OrderLine_ID(orderAndLineId.getOrderLineRepoId());
		sched.setDeliveryDate(Timestamp.from(dateEffective));
		sched.setPreparationDate(Timestamp.from(dateEffective));
		save(sched);

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
		item.setHandOver_Partner_ID(sched.getC_BPartner_ID());
		item.setHandOver_Location_ID(sched.getC_BPartner_Location_ID());
		item.setBPartnerAddress_Override("deliveryRenderedAddress");
		item.setM_Warehouse_ID(sched.getM_Warehouse_ID());
		item.setShipmentAllocation_BestBefore_Policy(ShipmentAllocationBestBeforePolicy.Expiring_First.getCode());
		item.setM_Product_ID(productId.getRepoId());
		item.setPackTo_HU_PI_Item_Product_ID(CoalesceUtil.firstGreaterThanZero(
				sched.getM_HU_PI_Item_Product_Override_ID(),
				sched.getM_HU_PI_Item_Product_ID()));
		item.setC_OrderSO_ID(sched.getC_Order_ID());
		item.setC_OrderLineSO_ID(sched.getC_OrderLine_ID());
		item.setDeliveryDate(sched.getDeliveryDate());
		item.setPreparationDate(sched.getPreparationDate());
		save(item);
	}

	public HUPIItemProductId createTUPackingInstructionsId(final String name, final ProductId productId, final Quantity cusPerTU)
	{
		final I_M_HU_PI tuPI = huTestHelper.createHUDefinition(name, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuPIItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		final I_M_HU_PI_Item_Product tuPIItemProduct = huTestHelper.assignProduct(tuPIItem, productId, cusPerTU);
		return HUPIItemProductId.ofRepoId(tuPIItemProduct.getM_HU_PI_Item_Product_ID());
	}

	public LUPackingInstructions createLUPackingInstructions(final String name, final HUPIItemProductId tuPackingInstructionId, final QuantityTU qtyTU)
	{
		final I_M_HU_PI luPI = huTestHelper.createHUDefinition(name, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		final I_M_HU_PI tuPI = huTestHelper.handlingUnitsBL().getPI(tuPackingInstructionId);
		final I_M_HU_PI_Item luPIItem = huTestHelper.createHU_PI_Item_IncludedHU(luPI, tuPI, qtyTU.toBigDecimal());

		return LUPackingInstructions.builder()
				.luPackingInstructionsId(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()))
				.luPIItem(luPIItem)
				.tuPackingInstructionId(tuPackingInstructionId)
				.build();
	}

	public HuId createVHU(final ProductId productId, final String qtyStr)
	{
		final Quantity qty = Quantity.of(qtyStr, productBL.getStockUOM(productId));
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

	public TestRecorder newTestRecorder()
	{
		return new TestRecorder(huTracer, snapshotSerializeFunction);
	}

	public String toJson(final Object obj)
	{
		return snapshotSerializeFunction.apply(obj);
	}

	public void dumpHU(final String title, final HuId huId)
	{
		huTracer.dump(title, huId);
	}
}
