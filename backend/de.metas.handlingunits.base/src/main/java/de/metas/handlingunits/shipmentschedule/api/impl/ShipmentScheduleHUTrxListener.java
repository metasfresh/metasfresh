package de.metas.handlingunits.shipmentschedule.api.impl;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.hutransaction.IHUTrxListener;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUFactory;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUSupportingServices;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.util.lang.IAutoCloseable;

import javax.annotation.Nullable;

/**
 * NOTE: can be accessed only via {@link #instance} to make sure we are not registering it twice
 *
 * @author tsa
 */
public final class ShipmentScheduleHUTrxListener implements IHUTrxListener
{
	public static final ShipmentScheduleHUTrxListener instance = new ShipmentScheduleHUTrxListener();

	private static final ThreadLocal<Boolean> updateAllocationLUAndTUForCUThreadLocal = new ThreadLocal<>();

	public static IAutoCloseable temporaryEnableUpdateAllocationLUAndTUForCU()
	{
		final Boolean previousValue = updateAllocationLUAndTUForCUThreadLocal.get();
		updateAllocationLUAndTUForCUThreadLocal.set(true);
		return () -> {
			updateAllocationLUAndTUForCUThreadLocal.set(previousValue);
		};
	}

	private boolean isUpdateAllocationLUAndTUForCU()
	{
		final Boolean enabled = updateAllocationLUAndTUForCUThreadLocal.get();
		return enabled != null && enabled;
	}

	private ShipmentScheduleHUTrxListener()
	{
		super();
	}

	/**
	 * If the given {@code trxLine} references a {@code M_ShipmentSchedule} record, then that record's picked qty value is decreased by the trxLine's qty.
	 */
	@Override
	public void trxLineProcessed(final IHUContext huContext, final I_M_HU_Trx_Line trxLine)
	{
		// We are looking for only those transactions where we have HU_Item set and referenced model pointing to shipment schedule
		if (trxLine.getVHU_Item_ID() <= 0)
		{
			return;
		}

		//
		// Find the shipment schedule
		final I_M_ShipmentSchedule shipmentSchedule = findShipmentSchedule(trxLine);
		if (shipmentSchedule == null)
		{
			return;
		}

		//
		// Get the VHU
		final I_M_HU_Item vhuItem = trxLine.getVHU_Item();
		final I_M_HU vhu = vhuItem.getM_HU();
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		if (!handlingUnitsBL.isVirtual(vhu))
		{
			// TODO: move this assumption to higher level
			throw new HUException("HU " + vhu + " shall be a virtual handling unit."
					+ "\n @HUStatus@: " + vhu.getHUStatus()
					+ "\n @VHU_ID@: " + handlingUnitsBL.getDisplayName(vhu)
					+ "\n @M_HU_ID@: " + handlingUnitsBL.getDisplayName(trxLine.getM_HU())
					+ "\n @M_HU_Trx_Line_ID@: " + trxLine
					+ "\n @M_HU_Trx_Line_ID@ - @M_Product_ID@: " + Services.get(IProductBL.class).getProductValueAndName(ProductId.ofRepoIdOrNull(trxLine.getM_Product_ID()))
					+ "\n @M_HU_Trx_Line_ID@ - @Qty@: " + trxLine.getQty());
		}

		//
		// Get QtyPicked
		// * positive means qty was allocated(added) to VHU
		// * negative means qty was un-allocated(removed) from VHU
		final Quantity qtyPicked = Quantity.of(trxLine.getQty(), IHUTrxBL.extractUOMOrNull(trxLine));

		final StockQtyAndUOMQty stockQtyAndUomQty = CatchWeightHelper.extractQtys(huContext, ProductId.ofRepoId(trxLine.getM_Product_ID()), qtyPicked, vhu);

		//
		// Link VHU to shipment schedule
		final boolean anonymousHuPickedOnTheFly = false;
		final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
		final ShipmentScheduleWithHUFactory factory = ShipmentScheduleWithHUFactory.builder()
				.supportingServices(ShipmentScheduleWithHUSupportingServices.getInstance())
				.huContext(huContext)
				.build();
		huShipmentScheduleBL.addQtyPickedAndUpdateHU(shipmentSchedule, stockQtyAndUomQty, vhu, factory, anonymousHuPickedOnTheFly);
	}

	private I_M_ShipmentSchedule findShipmentSchedule(final I_M_HU_Trx_Line trxLine)
	{
		//
		// Get the shipment schedule directly from trxLine (if any)
		final IHUTrxBL trxBL = Services.get(IHUTrxBL.class);
		final I_M_ShipmentSchedule shipmentSchedule_Direct = trxBL.getReferencedObjectOrNull(trxLine, I_M_ShipmentSchedule.class);
		if (shipmentSchedule_Direct != null)
		{
			return shipmentSchedule_Direct;
		}

		//
		// Get the shipment schedule from VHU (if any)
		final I_M_ShipmentSchedule shipmentSchedule_FromVHU = getM_ShipmentScheduleFromVHU(trxLine);
		if (shipmentSchedule_FromVHU != null)
		{
			return shipmentSchedule_FromVHU;
		}

		//
		// No shipment schedule was found
		return null;
	}

	/**
	 * Gets the shipment schedule where the "from VHU" is assigned.
	 *
	 * @return {@link I_M_ShipmentSchedule} or null
	 */
	@Nullable
	private I_M_ShipmentSchedule getM_ShipmentScheduleFromVHU(final I_M_HU_Trx_Line trxLine)
	{
		//
		// Get VHU Item from trxLine
		// If there is no VHU item on this transaction line, there is nothing to do
		final int vhuItemId = trxLine.getVHU_Item_ID();
		if (vhuItemId <= 0)
		{
			return null;
		}

		//
		// Get VHU Item from counterpart trxLine
		final I_M_HU_Trx_Line trxLineCounterpart = trxLine.getParent_HU_Trx_Line();
		Check.assumeNotNull(trxLineCounterpart, "trxLineCounterpart not null");
		final int counterpart_vhuItemId = trxLineCounterpart.getVHU_Item_ID();
		if (counterpart_vhuItemId <= 0)
		{
			return null;
		}

		//
		// Figure out which is the old VHU and which is the new VHU
		// NOTE: we do that by comparing the IDs
		final I_M_HU_Item vhuItemFrom;
		if (vhuItemId == counterpart_vhuItemId)
		{
			// transferring from VHU to same VHU... that shall not be possible
			// wtf?? shall not happen
			return null;
		}
		else if (vhuItemId < counterpart_vhuItemId)
		{
			vhuItemFrom = trxLine.getVHU_Item();
		}
		else
		// if (vhuItemId > counterpart_vhuItemId)
		{
			vhuItemFrom = trxLineCounterpart.getVHU_Item();
		}
		final I_M_HU vhuFrom = vhuItemFrom.getM_HU();

		//
		// Optimization: if the HU Status is not Picked then it's not about shipment schedules, so we don't care
		if (!X_M_HU.HUSTATUS_Picked.equals(vhuFrom.getHUStatus()))
		{
			return null;
		}

		//
		// Retrieve M_ShipmentSchedule from M_ShipmentSchedule_QtyPicked which is linking to our VHU
		// NOTE: we assume that a VHU can be assigned to ONLY one Shipment Schedule
		final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
		return huShipmentScheduleDAO.retrieveSchedsQtyPickedForVHUQuery(vhuFrom)
				.andCollect(de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked.COLUMN_M_ShipmentSchedule_ID)
				.create()
				.firstOnly(I_M_ShipmentSchedule.class);
	}

	/**
	 * Move all assignments to shipment schedules, from given TU to it's new top level LU
	 */
	@Override
	public void huParentChanged(final I_M_HU hu, final I_M_HU_Item parentHUItemOld)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

		if (isUpdateAllocationLUAndTUForCU() && handlingUnitsBL.isPureVirtual(hu))
		{
			huShipmentScheduleBL.updateAllocationLUAndTUForCU(hu);
		}
		else if (handlingUnitsBL.isTransportUnitOrVirtual(hu))
		{
			huShipmentScheduleBL.updateAllocationLUForTU(hu);
		}

		@SuppressWarnings("UnnecessaryLocalVariable") final I_M_HU tuHU = hu;
		Services.get(IHUShipmentScheduleBL.class).updateAllocationLUForTU(tuHU);
	}
}
