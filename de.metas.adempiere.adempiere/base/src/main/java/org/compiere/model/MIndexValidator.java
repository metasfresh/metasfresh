/**
 * 
 */
package org.compiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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



/**
 * PO Index Validator (optional)
 * 
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class MIndexValidator implements ModelValidator
{
	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	/**
	 * Does nothing, the code is commented out. See the javadoc of {@link #modelChange(PO, int)}.
	 */
	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();
		//
// @formatter:off
//		final Map<String, List<MIndexTable>> indexes = MIndexTable.getAllByTable(Env.getCtx(), true);
//		for (Entry<String, List<MIndexTable>> e : indexes.entrySet())
//		{
//			String tableName = e.getKey();
//			engine.addModelChange(tableName, this);
//		}
// @formatter:on
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	/**
	 * Does nothing, the code is commented out for the time being. Probably the whole class will be removed.<br>
	 * Don't use this stuff. We have a physical DB-index, and if a DBUniqueConstraintException is thrown anywhere in ADempiere, the exception code will identify the MIndexTable (if any) and get its
	 * errorMsg (if any).
	 */
	@Override
	public String modelChange(PO po, int type) throws Exception
	{
// @formatter:off
//		if (type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)
//		{
//			final List<MIndexTable> indexes = MIndexTable.getAllByTable(po.getCtx(), false).get(po.get_TableName());
//			if (indexes != null)
//			{
//				final Properties ctx = po.getCtx();
//				final String trxName = po.get_TrxName();
//				final String poWhereClause = po.get_WhereClause(true);
//
//				for (final MIndexTable index : indexes)
//				{
//					// Skip inactive indexes
//					if (!index.isActive())
//					{
//						continue;
//					}
//					
//					// Only UNIQUE indexes need to be validated
//					if (!index.isUnique())
//					{
//						continue;
//					}
//					
//					if (!index.isWhereClauseMatched(ctx, poWhereClause, trxName))
//					{
//						// there is no need to go with further validation since our PO is not subject of current index
//						continue;
//					}
//
//					index.validateData(po, trxName);
//				}
//			}
//		}
// @formatter:on
		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}
}
