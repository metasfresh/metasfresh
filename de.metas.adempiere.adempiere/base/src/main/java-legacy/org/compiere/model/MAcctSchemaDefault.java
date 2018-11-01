/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.AcctSchemaId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.OrgId;
import org.compiere.util.KeyNamePair;

import de.metas.util.Services;
import lombok.NonNull;

/**
 * Default Accounts for MAcctSchema
 *
 * @author Jorg Janke
 * @author victor.perez@e-evolution.com, www.e-evolution.com
 *         <li>RF [ 2214883 ] Remove SQL code and Replace for Query http://sourceforge.net/tracker/index.php?func=detail&aid=2214883&group_id=176962&atid=879335
 * @version $Id: MAcctSchemaDefault.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MAcctSchemaDefault extends X_C_AcctSchema_Default
{
	/**
	 *
	 */
	private static final long serialVersionUID = 199959007595802866L;

	/**
	 * Get Accounting Schema Default Info
	 *
	 * @param ctx context
	 * @param acctSchemaId id
	 * @return defaults
	 */
	public static MAcctSchemaDefault get(@NonNull final AcctSchemaId acctSchemaId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_AcctSchema_Default.class)
				.addEqualsFilter(I_C_AcctSchema_Default.COLUMN_C_AcctSchema_ID, acctSchemaId)
				.create()
				.firstOnly(MAcctSchemaDefault.class);
	}	// get

	public MAcctSchemaDefault(final Properties ctx, final int C_AcctSchema_ID, final String trxName)
	{
		super(ctx, C_AcctSchema_ID, trxName);
	}

	public MAcctSchemaDefault(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Get Acct Info list
	 *
	 * @return list
	 */
	public List<KeyNamePair> getAcctInfo()
	{
		final ArrayList<KeyNamePair> list = new ArrayList<>();
		for (int i = 0; i < get_ColumnCount(); i++)
		{
			final String columnName = get_ColumnName(i);
			if (columnName.endsWith("Acct"))
			{
				final int accountId = get_ValueAsInt(i);
				list.add(new KeyNamePair(accountId, columnName));
			}
		}
		return list;
	}	// getAcctInfo

	/**
	 * Set Value (don't use)
	 *
	 * @param columnName column name
	 * @param value value
	 * @return true if value set
	 */
	public boolean setValue(final String columnName, final Integer value)
	{
		return super.set_Value(columnName, value);
	}	// setValue

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (getAD_Org_ID() != OrgId.ANY.getRepoId())
		{
			setAD_Org_ID(OrgId.ANY.getRepoId());
		}
		return true;
	}
}
