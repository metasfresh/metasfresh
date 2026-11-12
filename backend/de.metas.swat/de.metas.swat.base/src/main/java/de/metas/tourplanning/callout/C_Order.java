package de.metas.tourplanning.callout;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.IOrderBL;
import de.metas.tourplanning.api.IOrderDeliveryDayBL;
import de.metas.util.Services;

@Callout(I_C_Order.class)
public class C_Order
{
	private static final String SYSCONFIG_DatePromisedOffsetDays = "de.metas.order.DatePromisedOffsetDays";

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_DateOrdered, I_C_Order.COLUMNNAME_M_Shipper_ID })
	public void setDatePromised(final I_C_Order order, final ICalloutField field)
	{
		if (field.isRecordCopyingMode())
		{
			return;
		}

		if (!order.isSOTrx())
		{
			return;
		}

		final LocalDate dateOrdered = TimeUtil.asLocalDate(order.getDateOrdered());
		if (dateOrdered == null)
		{
			return;
		}

		// Lead-time forward calculation supersedes the flat offset when it applies.
		// Setting DatePromised here re-triggers setPreparationDate via the webui Document.setValue -> fieldCalloutExecutor.execute chain.
		final IOrderDeliveryDayBL orderDeliveryDayBL = Services.get(IOrderDeliveryDayBL.class);
		if (orderDeliveryDayBL.setDatePromisedFromLeadTime(order))
		{
			return;
		}

		// When the forward lead-time feature governs this order (sysconfig on + shipper has a lead time), the per-shipper
		// lead time OWNS DatePromised even if the forward calc did not re-set it just now (e.g. IsFixedDatePromised=Y):
		// the legacy flat DatePromisedOffsetDays must not overwrite it. When the feature is off, the
		// legacy offset keeps its original behaviour.
		if (orderDeliveryDayBL.isLeadTimeForwardCalcActive(order))
		{
			return;
		}

		// The legacy flat-offset recompute keeps its original DateOrdered-only trigger (the lead-time path above also
		// fires on M_Shipper_ID): a mere shipper change must not overwrite a manually set DatePromised for customers
		// that use de.metas.order.DatePromisedOffsetDays.
		if (!I_C_Order.COLUMNNAME_DateOrdered.equals(field.getColumnName()))
		{
			return;
		}

		final int datePromisedOffsetDays = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_DatePromisedOffsetDays, -1);
		if (datePromisedOffsetDays <= 0)
		{
			return;
		}

		final ZoneId timeZone = Services.get(IOrderBL.class).getTimeZone(order);

		final ZonedDateTime datePromised = dateOrdered
				.plusDays(datePromisedOffsetDays)
				.atStartOfDay(timeZone);
		order.setDatePromised(TimeUtil.asTimestamp(datePromised));
	}

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_C_BPartner_Location_ID, I_C_Order.COLUMNNAME_DatePromised })
	public void setPreparationDate(final I_C_Order order, final ICalloutField field)
	{
		// The user needs to make sure there is a *proper* PreparationDate (task 08931).
		// If we are currently copying this record, it's fine to fallback to DatePromised (task 09000).
		final boolean fallbackToDatePromised = field.isRecordCopyingMode();

		Services.get(IOrderDeliveryDayBL.class).setPreparationDateAndTour(order, fallbackToDatePromised);
	}
}
