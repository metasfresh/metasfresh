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

import java.sql.PreparedStatement;

import org.compiere.util.DB;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * Insert AD_Sequence records that restart sequence at every year into
 * AD_Sequence_No table if the record does not exists
 * 
 * @author Elaine
 * 
 */
public class UpdateSequenceNo extends SvrProcess {

	private String year;

	@Override
	protected void prepare() {
		ProcessInfoParameter[] parameters = this.getParametersAsArray();
		for (ProcessInfoParameter p : parameters) {
			if (p.getParameterName().equals("CalendarYear")) {
				year = p.getParameter().toString();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		PreparedStatement insertStmt = null;
		try {
			insertStmt = DB
					.prepareStatement(
							"INSERT INTO AD_Sequence_No(AD_SEQUENCE_ID, CALENDARYEAR, "
									+ "AD_CLIENT_ID, AD_ORG_ID, ISACTIVE, CREATED, CREATEDBY, "
									+ "UPDATED, UPDATEDBY, CURRENTNEXT) "
									+ "(SELECT AD_Sequence_ID, '" + year + "', "
									+ "AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, "
									+ "Updated, UpdatedBy, StartNo "
									+ "FROM AD_Sequence a "
									+ "WHERE StartNewYear = 'Y' AND NOT EXISTS ( "
									+ "SELECT AD_Sequence_ID "
									+ "FROM AD_Sequence_No b "
									+ "WHERE a.AD_Sequence_ID = b.AD_Sequence_ID "
									+ "AND CalendarYear = ?)) ",
							get_TrxName());
			insertStmt.setString(1, year);
			insertStmt.executeUpdate();
		} finally {
			DB.close(insertStmt);
		}

		return "Sequence No updated successfully";
	}
}
