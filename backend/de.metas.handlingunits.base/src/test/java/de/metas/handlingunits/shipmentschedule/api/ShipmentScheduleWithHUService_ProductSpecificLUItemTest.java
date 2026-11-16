package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PrepareForShipmentSchedulesRequest;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * When picked TUs get wrapped into LUs at shipment time, the "is there an LU packing instruction for this TU?" check
 * has to be product-aware: a TU whose LU PI only has an item restricted to the schedule's product must not be skipped,
 * while for any other product that item stays invisible (behaviour unchanged).
 */
class ShipmentScheduleWithHUService_ProductSpecificLUItemTest extends AbstractHUTest
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private ProductId productXY;
	private ProductId otherProduct;
	private I_M_HU_PI tuPI;
	private I_M_HU_PI luPI;
	private ShipmentScheduleWithHUService service;

	@Override
	protected void initialize()
	{
		productXY = pTomatoId;
		otherProduct = pSaladId;

		tuPI = helper.createHUDefinition("IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		helper.assignProduct(helper.createHU_PI_Item_Material(tuPI), productXY, BigDecimal.TEN, uomEach);

		// the LU PI has ONLY an item for product XY, no generic one
		luPI = helper.createHUDefinition("Pallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item productItem = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("40"), (BPartnerId)null);
		InterfaceWrapperHelper.setValue(productItem, I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID, null);
		InterfaceWrapperHelper.setValue(productItem, I_M_HU_PI_Item.COLUMNNAME_M_Product_ID, productXY.getRepoId());
		InterfaceWrapperHelper.save(productItem);

		Services.get(ISysConfigBL.class).setValue(ShipmentScheduleWithHUService.SYSCONFIG_PACK_CUS_TO_TU, false, ClientId.SYSTEM, OrgId.ANY);
		service = ShipmentScheduleWithHUService.newInstanceForUnitTesting();
	}

	@Test
	void tuWithOnlyProductSpecificLUItem_isWrappedIntoLU_forThatProduct()
	{
		final I_M_HU tuHU = pickTUFor(productXY);
		InterfaceWrapperHelper.refresh(tuHU);

		assertThat(handlingUnitsDAO.retrieveParent(tuHU)).as("TU must get an LU parent").isNotNull();
	}

	@Test
	void tuWithOnlyProductSpecificLUItem_isNotWrappedIntoLU_forOtherProduct()
	{
		final I_M_HU tuHU = pickTUFor(otherProduct);
		InterfaceWrapperHelper.refresh(tuHU);

		assertThat(handlingUnitsDAO.retrieveParent(tuHU)).as("no LU item for this product: unchanged behaviour, TU stays without LU").isNull();
	}

	private I_M_HU pickTUFor(final ProductId productId)
	{
		final I_M_HU_PI_Version tuPIVersion = handlingUnitsDAO.retrievePICurrentVersion(tuPI);

		// the service is always called within a transaction: its HU context takes the trx from the shipment schedule record
		return Services.get(ITrxManager.class).callInNewTrx(() -> {
			final IContextAware ctx = PlainContextAware.newWithThreadInheritedTrx();

			final I_M_HU tuHU = InterfaceWrapperHelper.newInstance(I_M_HU.class, ctx);
			tuHU.setM_HU_PI_Version_ID(tuPIVersion.getM_HU_PI_Version_ID());
			tuHU.setHUStatus(X_M_HU.HUSTATUS_Picked);
			InterfaceWrapperHelper.save(tuHU);

			final I_M_ShipmentSchedule schedule = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class, ctx);
			schedule.setM_Product_ID(productId.getRepoId());
			InterfaceWrapperHelper.save(schedule);

			final I_M_ShipmentSchedule_QtyPicked qtyPicked = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_QtyPicked.class, ctx);
			qtyPicked.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
			qtyPicked.setM_TU_HU_ID(tuHU.getM_HU_ID());
			qtyPicked.setQtyPicked(BigDecimal.TEN);
			InterfaceWrapperHelper.save(qtyPicked);

			service.prepareShipmentSchedulesWithHU(PrepareForShipmentSchedulesRequest.builder()
					.schedules(ShipmentScheduleAndJobSchedulesCollection.ofShipmentSchedules(ImmutableList.of(schedule)))
					.quantityTypeToUse(M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY)
					.build());
			return tuHU;
		});
	}
}
