package de.metas.handlingunits.picking.candidate.commands;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.handlingunits.HUConstants;
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
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.inout.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

class ProcessPickingCandidatesCommandTestHelper
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	public final HUTestHelper huTestHelper;
	public final PickingCandidateRepository pickingCandidateRepository;
	public final InventoryService inventoryService;

	public final I_C_UOM uomEach;
	public final LocatorId shipFromLocatorId;
	public final BPartnerLocationId shipToBPLocationId = BPartnerLocationId.ofRepoId(666, 666);

	public ProcessPickingCandidatesCommandTestHelper()
	{
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();
		pickingCandidateRepository = new PickingCandidateRepository();
		inventoryService = InventoryService.newInstanceForUnitTesting();

		createDocType(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector);
		createDimensionSpec(HUConstants.DIM_PP_Order_ProductAttribute_To_Transfer);

		uomEach = huTestHelper.uomEach;
		final WarehouseId warehouseId = createWarehouseId("warehouse");
		shipFromLocatorId = createLocatorId(warehouseId);
	}

	public ProductId createProduct(final String name, final I_C_UOM uom)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
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

	@SuppressWarnings("SameParameterValue")
	private void createDocType(final String docBaseType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setName(docBaseType);
		docType.setDocBaseType(docBaseType);
		saveRecord(docType);
	}

	@SuppressWarnings("SameParameterValue")
	private void createDimensionSpec(final String internalName)
	{
		final I_DIM_Dimension_Spec dim = InterfaceWrapperHelper.newInstance(I_DIM_Dimension_Spec.class);
		dim.setInternalName(internalName);
		InterfaceWrapperHelper.save(dim);
	}

	public PackToSpec createTUPackingInstructions(final ProductId productId, final Quantity cusPerTU)
	{
		final I_M_HU_PI tuPI = huTestHelper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuPIItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		final I_M_HU_PI_Item_Product huPIItemProduct = huTestHelper.assignProduct(tuPIItem, productId, cusPerTU);
		return PackToSpec.ofTUPackingInstructionsId(HUPIItemProductId.ofRepoId(huPIItemProduct.getM_HU_PI_Item_Product_ID()));
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

	public List<I_PP_Cost_Collector> getCostCollectors(final PickingCandidateId pickingCandidateId)
	{
		return queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_M_Picking_Candidate_ID, pickingCandidateId)
				.orderBy(I_PP_Cost_Collector.COLUMNNAME_PP_Cost_Collector_ID)
				.create()
				.list();
	}

	public ShipmentScheduleId createShipmentSchedule(final @NonNull ProductId productId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		shipmentSchedule.setM_Warehouse_ID(shipFromLocatorId.getWarehouseId().getRepoId());
		shipmentSchedule.setC_BPartner_ID(shipToBPLocationId.getBpartnerId().getRepoId());
		shipmentSchedule.setC_BPartner_Location_ID(shipToBPLocationId.getRepoId());
		shipmentSchedule.setM_Product_ID(productId.getRepoId());
		saveRecord(shipmentSchedule);
		return ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
	}

}
