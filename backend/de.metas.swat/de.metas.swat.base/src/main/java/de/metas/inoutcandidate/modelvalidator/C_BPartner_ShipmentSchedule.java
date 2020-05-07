package de.metas.inoutcandidate.modelvalidator;

import java.util.Iterator;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;

@Validator(I_C_BPartner.class)
public class C_BPartner_ShipmentSchedule
{
	/**
	 * 
	 * 
	 * @param bPartner
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
					I_C_BPartner.COLUMNNAME_AllowConsolidateInOut })
	public void inValidateScheds(final I_C_BPartner bPartner)
	{
		//
		// Services
		final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);

		// using iterator because there might be a lot of scheds to update
		final Iterator<? extends I_M_ShipmentSchedule> scheds = shipmentSchedulePA.retrieveForBPartner(bPartner);

		for (final I_M_ShipmentSchedule sched : IteratorUtils.asIterable(scheds))
		{
			final boolean isSOTrx = true;
			final boolean isBPAllowConsolidateInOut = bpartnerBL.isAllowConsolidateInOutEffective(bPartner, isSOTrx);
			final boolean allowConsChg = isBPAllowConsolidateInOut != sched.isAllowConsolidateInOut();

			if (!allowConsChg)
			{
				continue;
			}
			
			sched.setAllowConsolidateInOut(isBPAllowConsolidateInOut);
			InterfaceWrapperHelper.save(sched, trxName);

			// note that we do not need to invalidate the current sched explicitly..it will be updated as part of the segment, unless it has delivery rule force..and if it has that rule, then the
			// bpartner change makes no difference to it, anyways.
			Services.get(IShipmentScheduleInvalidateBL.class).invalidateSegmentForShipmentSchedule(sched);

		}
	}
}
