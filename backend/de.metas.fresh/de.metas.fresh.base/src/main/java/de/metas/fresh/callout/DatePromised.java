package de.metas.fresh.callout;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;

import de.metas.order.IOrderBL;
import de.metas.util.Services;

/**
 * Order DatePromised
 * 
 */
// task 06706
public class DatePromised extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_C_Order order = calloutRecord.getModel(I_C_Order.class);

		final ZoneId timeZone = Services.get(IOrderBL.class).getTimeZone(order);

		final ZonedDateTime datePromised = SystemTime.asZonedDateTime(timeZone);
		final ZonedDateTime datePromisedEndOfDay = datePromised.toLocalDate().atTime(LocalTime.MAX).atZone(timeZone);

		order.setDatePromised(TimeUtil.asTimestamp(datePromisedEndOfDay));
	}
}
