package org.compiere.model;

/*
 * #%L
 * de.metas.banking.base
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

public class MDirectDebit extends X_C_DirectDebit {

	public MDirectDebit(Properties ctx, int C_DirectDebit_ID, String trxName) {
		super(ctx, C_DirectDebit_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public X_C_DirectDebitLine[] getLines() {
		
		X_C_DirectDebitLine[] retValue = null;
		
		String sql = "SELECT C_DirectDebitLine_ID FROM C_DirectDebitLine WHERE C_DirectDebit_ID = ? ";
		PreparedStatement pstmt = null;
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, this.get_ID());
			
			ResultSet rs = pstmt.executeQuery();
			List<Integer> a = new ArrayList<Integer>();
			while (rs.next()) {
				a.add(rs.getInt(1));
			}
			
			retValue = new X_C_DirectDebitLine[a.size()];
			
			int count = 0;
			for (int i : a) {
				retValue[count] = new X_C_DirectDebitLine(Env.getCtx(), i, null);
				count++;
			}
			
			rs.close();
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			pstmt = null;
		} catch (Exception e) {
			pstmt = null;
		}
		return retValue;
		
	}
}
