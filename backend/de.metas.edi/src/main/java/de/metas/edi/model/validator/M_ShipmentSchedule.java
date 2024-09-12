package de.metas.edi.model.validator;

import de.metas.edi.api.IDesadvBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Interceptor(I_M_ShipmentSchedule.class)
@Component
public class M_ShipmentSchedule
{
	private final IDesadvBL desadvBL = Services.get(IDesadvBL.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override)
	public void updateQtyOrdered_Override(@Nonnull final I_M_ShipmentSchedule schedule)
	{
		desadvBL.updateQtyOrdered_OverrideFromShipSchedAndSave(schedule);
	}
}
