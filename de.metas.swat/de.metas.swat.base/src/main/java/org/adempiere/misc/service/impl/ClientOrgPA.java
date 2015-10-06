package org.adempiere.misc.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.misc.service.IClientOrgPA;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.MClient;
import org.compiere.model.MOrgInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;

public final class ClientOrgPA implements IClientOrgPA {

	private String SQL_ORGINFO = "SELECT * FROM AD_OrgInfo WHERE AD_Org_ID=?";

	public I_AD_OrgInfo retrieveOrgInfo(final int orgId, final String trxName) {

		final PreparedStatement pstmt = DB.prepareStatement(SQL_ORGINFO,
				trxName);
		ResultSet rs = null;

		try {
			pstmt.setInt(1, orgId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return new MOrgInfo(Env.getCtx(), rs, trxName);
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			DB.close(rs, pstmt);
		}
	}

	public I_AD_Client retrieveClient(int clientId, String trxName) {
		return new MClient(Env.getCtx(), clientId, trxName);
	}
}
