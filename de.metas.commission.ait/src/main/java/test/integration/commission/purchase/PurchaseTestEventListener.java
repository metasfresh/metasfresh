package test.integration.commission.purchase;

/*
 * #%L
 * de.metas.commission.ait
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.adempiere.model.POWrapper;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable;

import test.integration.swat.purchase.PurchaseTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.event.TestEvent;
import de.metas.adempiere.ait.test.annotation.ITEventListener;
import de.metas.commission.dashboard.DPCommisionView;
import de.metas.commission.interfaces.I_C_Invoice;

public class PurchaseTestEventListener
{

	@ITEventListener(
			driver = PurchaseTestDriver.class,
			eventTypes = EventType.INVOICE_EMPLOYEE_CREATE_AFTER,
			tasks = "02218: B2B Monat der Abrechnung wird falsch angezeigt (2011101110000073)")
	public void onEmployeeInvoiceCreated(final TestEvent evt)
	{
		final I_C_Invoice invoice = POWrapper.create(evt.getObj(), I_C_Invoice.class);

		final String sql = "SELECT * FROM " + DPCommisionView.TableName + " WHERE AD_Table_ID=" + I_C_Invoice.Table_ID + " AND Record_ID=" + invoice.getC_Invoice_ID();

		Trx.run(new TrxRunnable()
		{
			@Override
			public void run(String trxName)
			{
				final PreparedStatement pstmt = DB.prepareStatement(sql, trxName);
				try
				{
					final ResultSet rs = pstmt.executeQuery();
					assertTrue("SQL gives no results; SQL=" + sql, rs.next());

					final String monthActual = rs.getString("month");
					final String monthExpected = mkExpectedMonthStr(invoice);

					assertEquals("Result has wrong 'month' column" + invoice.toString(), monthExpected, monthActual);

					assertFalse("SQL gives more than one result; SQL=" + sql, rs.next());
				}
				catch (SQLException e)
				{
					fail(e.getMessage());
				}
			}
		});
	}

	private String mkExpectedMonthStr(final I_C_Invoice invoice)
	{
		final StringBuilder monthExpected = new StringBuilder();

		final Calendar cal = new GregorianCalendar();
		cal.setTime(invoice.getDateInvoiced());
		final int year = cal.get(Calendar.YEAR);
		final int month = cal.get(Calendar.MONTH) + 1;

		monthExpected.append(year);
		monthExpected.append("/");
		if (month < 10)
		{
			monthExpected.append("0");
			monthExpected.append(month);
		}
		else
		{
			monthExpected.append(month);
		}
		return monthExpected.toString();
	}
}
