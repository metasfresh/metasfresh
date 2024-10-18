/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 Adempiere, Inc. All Rights Reserved.                    *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.process;

import de.metas.common.util.time.SystemTime;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.time.Year;

/**
 * Insert AD_Sequence records that restart sequence at every year into
 * AD_Sequence_No table if the record does not exists
 *
 * @author Elaine
 */
public class UpdateSequenceNo extends JavaProcess
{

	private String year;

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] parameters = this.getParametersAsArray();
		for (final ProcessInfoParameter p : parameters)
		{
			if (p.getParameterName().equals("CalendarYear"))
			{
				year = p.getParameter().toString();
				return;
			}
		}
		year = SystemTime.asZonedDateTime().getYear() + "";
	}

	@Override
	protected String doIt() throws Exception
	{
		PreparedStatement insertStmt = null;
		try
		{
			insertStmt = DB
					.prepareStatement(
							"INSERT INTO AD_Sequence_No(AD_SEQUENCE_ID, CALENDARYEAR,CALENDARMONTH,CALENDARDAY, "
									+ "AD_CLIENT_ID, AD_ORG_ID, ISACTIVE, CREATED, CREATEDBY, "
									+ "UPDATED, UPDATEDBY, CURRENTNEXT) "
									+ "(SELECT AD_Sequence_ID, ?, ?, ?, "
									+ "AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, "
									+ "Updated, UpdatedBy, StartNo "
									+ "FROM AD_Sequence a "
									+ "WHERE RestartFrequency IS NOT NULL AND NOT EXISTS ( "
									+ "SELECT AD_Sequence_ID "
									+ "FROM AD_Sequence_No b "
									+ "WHERE a.AD_Sequence_ID = b.AD_Sequence_ID "
									+ "AND CalendarYear = ?"
									+ "AND CalendarMonth = ?"
									+ "AND CalendarDay = ?)) ",
							get_TrxName());
			insertStmt.setString(1, year);
			insertStmt.setString(4, year);

			final Year selectedYear = Year.of(Integer.parseInt(year));
			for (int month = 1; month <= 12; month++)
			{
				final String monthAsString = month + "";
				final int days = selectedYear.atMonth(month).lengthOfMonth();
				for (int day = 1; day <= days; day++)
				{
					final String dayAsString = day + "";
					insertStmt.setString(2, monthAsString);
					insertStmt.setString(3, dayAsString);
					insertStmt.setString(5, monthAsString);
					insertStmt.setString(6, dayAsString);
					insertStmt.executeUpdate();
				}
			}
		}
		finally
		{
			DB.close(insertStmt);
		}
		return "Sequence No updated successfully";
	}
}
