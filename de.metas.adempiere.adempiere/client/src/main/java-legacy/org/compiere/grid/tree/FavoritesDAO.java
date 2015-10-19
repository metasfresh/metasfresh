package org.compiere.grid.tree;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.compiere.util.Env;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

class FavoritesDAO
{
	public void add(final int adTreeId, final int Node_ID) throws DBException
	{
		final Properties ctx = Env.getCtx();
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		final int AD_Org_ID = Env.getAD_Org_ID(ctx);
		final int AD_User_ID = Env.getAD_User_ID(ctx);

		final StringBuilder sql = new StringBuilder()
				.append("INSERT INTO AD_TreeBar "
						+ "(AD_Tree_ID,AD_User_ID,Node_ID, "
						+ "AD_Client_ID,AD_Org_ID, "
						+ "IsActive,Created,CreatedBy,Updated,UpdatedBy)VALUES (")
				.append(adTreeId).append(",").append(AD_User_ID).append(",").append(Node_ID).append(",")
				.append(AD_Client_ID).append(",").append(AD_Org_ID).append(",")
				.append("'Y',now(),").append(AD_User_ID).append(",now(),").append(AD_User_ID).append(")");
		// if already exist, will result in ORA-00001: unique constraint (ADEMPIERE.AD_TREEBAR_KEY)

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_None);
	}

	public boolean remove(final int adTreeId, final int Node_ID)
	{
		final Properties ctx = Env.getCtx();
		final int AD_User_ID = Env.getAD_User_ID(ctx);

		final StringBuilder sql = new StringBuilder()
				.append("DELETE FROM AD_TreeBar WHERE AD_Tree_ID=").append(adTreeId)
				.append(" AND AD_User_ID=").append(AD_User_ID)
				.append(" AND Node_ID=").append(Node_ID);

		final int no = DB.executeUpdate(sql.toString(), false, ITrx.TRXNAME_None);
		return no == 1;
	}
}
