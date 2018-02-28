package de.metas.inoutcandidate.modelvalidator;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;

@Validator(I_C_Order.class)
public class C_Order_ShipmentSchedule
{
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void createMissingShipmentSchedules(final I_C_Order order)
	{
		if(Services.get(IShipmentScheduleBL.class).isMissingSchedsCreatedLater())
		{
			return; // nothing to do now
		}

		CreateMissingShipmentSchedulesWorkpackageProcessor.schedule(getContextAware(order));
	}

}
