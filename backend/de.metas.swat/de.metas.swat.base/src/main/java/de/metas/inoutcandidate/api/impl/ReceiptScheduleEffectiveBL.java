package de.metas.inoutcandidate.api.impl;

import de.metas.common.util.CoalesceUtil;
import de.metas.inoutcandidate.api.IReceiptScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ReceiptScheduleEffectiveBL implements IReceiptScheduleEffectiveBL
{

	@Override
	public ZonedDateTime getMovementDate(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final ZoneId timeZone = (Services.get(IOrgDAO.class))
				.getTimeZone(OrgId.ofRepoIdOrAny(receiptSchedule.getAD_Org_ID()));

		return TimeUtil.asZonedDateTime(
				CoalesceUtil.coalesceSuppliers(
						receiptSchedule::getMovementDate_Override,
						receiptSchedule::getMovementDate), timeZone);
	}
}
