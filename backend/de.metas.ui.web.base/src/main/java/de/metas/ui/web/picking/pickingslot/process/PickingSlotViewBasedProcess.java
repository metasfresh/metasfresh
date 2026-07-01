package de.metas.ui.web.picking.pickingslot.process;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.process.IProcessPrecondition;
import de.metas.ui.web.picking.packageable.PackageableView;
import de.metas.ui.web.picking.packageable.filters.ProductBarcodeFilterData;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

abstract class PickingSlotViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final PickingJobHUService pickingJobHUService = SpringContextHolder.instance.getBean(PickingJobHUService.class);

	private I_M_ShipmentSchedule _shipmentSchedule; // lazy

	@Override
	protected final PickingSlotView getView()
	{
		return PickingSlotView.cast(super.getView());
	}

	protected PickingSlotView getPickingSlotView()
	{
		return getView();
	}

	@Override
	protected final PickingSlotRow getSingleSelectedRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}

	protected final PickingSlotRow getSingleSelectedPickingSlotRow()
	{
		return getSingleSelectedRow();
	}

	protected final void invalidatePickingSlotsView()
	{
		invalidateView();
	}

	protected final ShipmentScheduleId getCurrentShipmentScheduleId()
	{
		return getPickingSlotView().getCurrentShipmentScheduleId();
	}

	protected final I_M_ShipmentSchedule getCurrentShipmentSchedule()
	{
		I_M_ShipmentSchedule shipmentSchedule = _shipmentSchedule;
		if (shipmentSchedule == null)
		{
			final ShipmentScheduleId shipmentScheduleId = getCurrentShipmentScheduleId();
			_shipmentSchedule = shipmentSchedule = shipmentSchedulesRepo.getById(shipmentScheduleId, I_M_ShipmentSchedule.class);
		}
		return shipmentSchedule;
	}

	protected final I_C_UOM getCurrentShipmentScheuduleUOM()
	{
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();
		return shipmentScheduleBL.getUomOfProduct(shipmentSchedule);
	}

	protected PackageableView getPackageableView()
	{
		final ViewId packageableViewId = getPickingSlotView().getParentViewId();
		return PackageableView.cast(getViewsRepo().getView(packageableViewId));
	}

	protected final void invalidatePackablesView()
	{
		invalidateParentView();
	}

	protected Optional<ProductBarcodeFilterData> getBarcodeFilterData()
	{
		return Optional.ofNullable(getPackageableView().getBarcodeFilterData());
	}

	/**
	 * Desktop-picking parity with the mobile close-LU fix (me03 #30763, AC8): a picking HU materialised without a
	 * {@code C_BPartner_ID} misses the per-BPartner {@code M_HU_Label_Config} (the label lookup keys on the HU's own
	 * bpartner), so the SSCC label auto-print is skipped — or, for {@code failOnMissingLabelConfig=true} callers, the
	 * print throws. Resolve the consignee from the current shipment schedule and stamp it (only-if-unset) BEFORE the
	 * label print, so the unchanged label-matching path selects the correct per-BPartner config. Reuses the same
	 * guarded stamp method as the mobile fix; the {@code M_HU} {@code updateChildren} interceptor cascades the
	 * bpartner + location to child TUs/CUs.
	 */
	protected final void stampConsigneeIfNotSet(@NonNull final HuId huId)
	{
		final BPartnerLocationId bpLocationId = getConsigneeLocationOrNull(getCurrentShipmentSchedule());
		if (bpLocationId == null)
		{
			return;
		}

		pickingJobHUService.setBPartnerAndLocationIfNotSet(huId, bpLocationId);
	}

	/**
	 * The effective consignee delivery location of the schedule, or {@code null} if none is resolvable.
	 * <p>
	 * {@link IShipmentScheduleEffectiveBL#getBPartnerLocationId(I_M_ShipmentSchedule)} resolves through the
	 * value-object factory {@code BPartnerLocationId.ofRepoId(...)}, which <b>throws</b> (not returns null) when the
	 * effective delivery-location FK is unset — so we guard the optional FK before calling, per the module convention
	 * (guard an optional FK {@code <= 0} before {@code ofRepoId}). A schedule without a resolvable delivery location
	 * simply gets no stamp (label matching then behaves as before this fix) rather than aborting the print.
	 */
	@Nullable
	private BPartnerLocationId getConsigneeLocationOrNull(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final boolean hasEffectiveLocation = shipmentSchedule.getC_BP_Location_Override_ID() > 0
				|| shipmentSchedule.getC_BPartner_Location_ID() > 0;
		return hasEffectiveLocation
				? shipmentScheduleEffectiveBL.getBPartnerLocationId(shipmentSchedule)
				: null;
	}
}
