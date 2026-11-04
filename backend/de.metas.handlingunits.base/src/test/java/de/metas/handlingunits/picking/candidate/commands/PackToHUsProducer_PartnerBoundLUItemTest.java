package de.metas.handlingunits.picking.candidate.commands;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Regression guard for the pick-to path ({@link PackToHUsProducer}): a CU is packed into a new TU on a pallet whose
 * pallet-to-TU item is bound to the ship-to partner only. First onto a new pallet, then onto that (now existing) pallet.
 * This path carries the ship-to partner itself and must keep working.
 */
public class PackToHUsProducer_PartnerBoundLUItemTest
{
	private LUTUProducerDestinationTestSupport data;
	private HUTestHelper helper;
	private IHandlingUnitsBL handlingUnitsBL;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private I_M_HU_PI piLU;
	private I_M_HU_PI_Item_Product piTUItemProduct;
	private BPartnerLocationId shipTo;
	private I_M_HU sourceLU;

	private void setup()
	{
		data = new LUTUProducerDestinationTestSupport();
		helper = data.helper;
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		SpringContextHolder.registerJUnitBean(HUQRCodesService.newInstanceForUnitTesting());
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository());

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setValue("Customer");
		bpartner.setName("Customer");
		saveRecord(bpartner);
		final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation.setIsShipTo(true);
		saveRecord(bpartnerLocation);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		shipTo = BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocation.getC_BPartner_Location_ID());

		final I_M_HU_PI piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuMaterialItem = helper.createHU_PI_Item_Material(piTU);
		piTUItemProduct = helper.assignProduct(tuMaterialItem, helper.pSaladProductId, BigDecimal.ONE, helper.uomEach);
		helper.createHU_PI_Item_PackingMaterial(piTU, helper.pmIFCO);

		piLU = helper.createHUDefinition("Pallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item luItem = helper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("100"), bpartnerId); // the ONLY item is bound to the ship-to partner
		helper.createHU_PI_Item_PackingMaterial(piLU, helper.pmPalet);

		// source pallet (partner = ship-to) with one aggregate TU of 5 x 1 piece; the TU itself has no partner
		final LUTUProducerDestination producer = new LUTUProducerDestination();
		producer.setLocatorId(data.defaultLocatorId);
		producer.setLUItemPI(luItem);
		producer.setLUPI(piLU);
		producer.setTUPI(piTU);
		producer.setMaxTUsPerLU(Integer.MAX_VALUE);
		producer.addCUPerTU(helper.pSaladProductId, BigDecimal.ONE, helper.uomEach);
		producer.setBPartnerId(bpartnerId);
		helper.load(producer, helper.pSaladProductId, new BigDecimal("5"), helper.uomEach);
		sourceLU = producer.getCreatedHUs().get(0);
		Services.get(IHUStatusBL.class).setHUStatus(helper.createMutableHUContextOutOfTransaction(), sourceLU, X_M_HU.HUSTATUS_Active);
		saveRecord(sourceLU);
		for (final I_M_HU tu : handlingUnitsDAO.retrieveIncludedHUs(sourceLU))
		{
			tu.setC_BPartner_ID(-1);
			tu.setHUStatus(X_M_HU.HUSTATUS_Active);
			saveRecord(tu);
			for (final I_M_HU vhu : handlingUnitsDAO.retrieveIncludedHUs(tu))
			{
				vhu.setC_BPartner_ID(-1);
				vhu.setHUStatus(X_M_HU.HUSTATUS_Active);
				saveRecord(vhu);
			}
		}
	}

	private LUTUResult packOneCU(final LUPickingTarget luTarget)
	{
		// new producer per pick, like production (one PickingJobPickCommand per request)
		final PackToHUsProducer producer = PackToHUsProducer.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.huPIItemProductBL(Services.get(IHUPIItemProductBL.class))
				.uomConversionBL(Services.get(IUOMConversionBL.class))
				.inventoryService(InventoryService.newInstanceForUnitTesting())
				.build();
		final PackToHUsProducer.PackToInfo packToInfo = producer.extractPackToInfo(
				helper.pSaladProductId,
				PackToSpec.ofTUPackingInstructionsId(HUPIItemProductId.ofRepoId(piTUItemProduct.getM_HU_PI_Item_Product_ID())),
				luTarget,
				null,
				shipTo,
				data.defaultLocatorId);
		return producer.packToHU(PackToHUsProducer.PackToHURequest.builder()
				.huContext(helper.createMutableHUContext())
				.pickFromHUId(HuId.ofRepoId(sourceLU.getM_HU_ID()))
				.packToInfo(packToInfo)
				.productId(helper.pSaladProductId)
				.qtyPicked(Quantity.of(BigDecimal.ONE, helper.uomEach))
				.documentRef(TableRecordReference.of("M_HU", sourceLU.getM_HU_ID()))
				.recordLeafCUsAsTUParts(true)
				.build());
	}

	private int countTUs(final I_M_HU lu)
	{
		int count = 0;
		for (final I_M_HU child : handlingUnitsDAO.retrieveIncludedHUs(lu))
		{
			if (handlingUnitsBL.isTransportUnitOrAggregate(child))
			{
				count += handlingUnitsBL.getTUsCount(child).toInt();
			}
		}
		return count;
	}

	@Test
	void packToNewLU_thenToTheSameLUAsExisting_itemBoundToShipToPartner()
	{
		setup();

		// 1st pick: onto a new pallet
		final LUTUResult result1 = packOneCU(LUPickingTarget.ofPackingInstructions(HuPackingInstructionsId.ofRepoId(piLU.getM_HU_PI_ID()), "Pallet"));
		Assertions.assertThat(result1.getLURecords()).hasSize(1);
		final I_M_HU pickingLU = result1.getLURecords().get(0);
		Assertions.assertThat(countTUs(pickingLU)).as("TUs on picking LU after 1st pick").isEqualTo(1);

		// 2nd pick: onto that (now existing) pallet
		final HuId pickingLUId = HuId.ofRepoId(pickingLU.getM_HU_ID());
		final LUTUResult result2 = packOneCU(LUPickingTarget.ofExistingHU(pickingLUId, SpringContextHolder.instance.getBean(HUQRCodesService.class).getOrCreateQRCodesByHuId(pickingLUId).get(0)));
		Assertions.assertThat(result2.getLURecords()).hasSize(1);
		Assertions.assertThat(countTUs(pickingLU)).as("TUs on picking LU after 2nd pick").isEqualTo(2);
	}
}
