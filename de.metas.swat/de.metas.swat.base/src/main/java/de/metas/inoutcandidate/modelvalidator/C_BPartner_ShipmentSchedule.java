package de.metas.inoutcandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;
import de.metas.lang.SOTrx;
import de.metas.util.Services;

@Validator(I_C_BPartner.class)
public class C_BPartner_ShipmentSchedule
{
	/**
	 * 
	 * 
	 * @param bpartner
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
					I_C_BPartner.COLUMNNAME_AllowConsolidateInOut })
	public void inValidateScheds(final I_C_BPartner bpartner)
	{
		//
		// Services
		final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

		final boolean isBPAllowConsolidateInOut = bpartnerBL.isAllowConsolidateInOutEffective(bpartner, SOTrx.SALES);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		shipmentSchedulesRepo
				.streamUnprocessedByPartnerIdAndAllowConsolidateInOut(bpartnerId, !isBPAllowConsolidateInOut)
				.forEach(sched -> setAllowConsolidateInOutAndSave(sched, isBPAllowConsolidateInOut));
	}

	private void setAllowConsolidateInOutAndSave(final I_M_ShipmentSchedule sched, final boolean allowConsolidateInOut)
	{
		if (sched.isAllowConsolidateInOut() == allowConsolidateInOut)
		{
			return;
		}

		sched.setAllowConsolidateInOut(allowConsolidateInOut);
		InterfaceWrapperHelper.saveRecord(sched);

		// note that we do not need to invalidate the current sched explicitly..
		// it will be updated as part of the segment, unless it has delivery rule force..
		// and if it has that rule, then the partner change makes no difference to it, anyways.
		Services.get(IShipmentScheduleInvalidateBL.class).notifySegmentChangedForShipmentSchedule(sched);
	}
}
