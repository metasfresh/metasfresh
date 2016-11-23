/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
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
package org.compiere.print.layout;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MQuery;
import org.compiere.model.X_AD_PInstance_Log;
import org.compiere.print.MPrintTableFormat;
import org.compiere.util.DB;
import org.compiere.util.Msg;

import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessInfoLog;

/**
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>FR [ 1966406 ] Report Engine: AD_PInstance_Logs should be displayed
 */
public class PInstanceLogElement extends GridElement {
	private int m_effectiveRowCount = 0;
	
	public PInstanceLogElement(Properties ctx, MQuery query, MPrintTableFormat tFormat)
	{
		super (calculateRowCount(query, ctx), 4);
		//
		final int AD_PInstance_ID = query.getAD_PInstance_ID();
		if (AD_PInstance_ID > 0)
		{
			final List<ProcessInfoLog> logs = Services.get(IADPInstanceDAO.class).retrieveProcessInfoLogs(AD_PInstance_ID);
			for (int r = 0; r < logs.size(); r++)
			{
				final ProcessInfoLog logRecord = logs.get(r);
				int col = 0;
				String msg = logRecord.getP_Msg();
				if (!Check.isEmpty(msg, true)) {
					String s = Msg.parseTranslation(ctx, msg);
					setData (r, col++, s, tFormat.getParameter_Font(), tFormat.getParameter_Color());
				}
				BigDecimal num = logRecord.getP_Number();
				if (num != null) {
					String s = num.toString();
					setData (r, col++, s, tFormat.getParameter_Font(), tFormat.getParameter_Color());
				}
				Timestamp date = logRecord.getP_Date();
				if (date != null) {
					String s = date.toString();
					setData (r, col++, s, tFormat.getParameter_Font(), tFormat.getParameter_Color());
				}
				//
				if (col > 0)
					m_effectiveRowCount++;
			}
		}
	}
	
	/**
	 * @return number of rows effectively added
	 */
	public int getEffectiveRowCount() {
		return m_effectiveRowCount;
	}
	
	private static int calculateRowCount(MQuery query, Properties ctx) {
		int AD_PInstance_ID = query.getAD_PInstance_ID();
		if (AD_PInstance_ID > 0) {
			String sql = "SELECT COUNT(*) FROM "+X_AD_PInstance_Log.Table_Name
							+" WHERE "+X_AD_PInstance_Log.COLUMNNAME_AD_PInstance_ID+"=?";
			int no = DB.getSQLValue(null, sql, AD_PInstance_ID);
			if (no > 0) {
				return no;
			}
		}
		return 0;
	}
}
