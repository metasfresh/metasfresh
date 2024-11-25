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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;

/**
 * Attribute Use Model
 *
 * @author Jorg Janke
 * @version $Id: MAttributeUse.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 * @deprecated will be removed after the before/after save/delete logic will be extracted
 */
@Deprecated
public class MAttributeUse extends X_M_AttributeUse
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3727204159034073907L;

	public MAttributeUse(final Properties ctx, final int M_AttributeUse_ID, final String trxName)
	{
		super(ctx, M_AttributeUse_ID, trxName);
	}

	public MAttributeUse(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	private static final void updateM_AttributeSet(final int attributeSetId)
	{
		// TODO: translate it to Query API
		
		// Update M_AttributeSet.IsInstanceAttribute='Y'
		{
			final String sql = "UPDATE M_AttributeSet mas"
					+ " SET IsInstanceAttribute='Y' "
					+ "WHERE M_AttributeSet_ID=" + attributeSetId
					+ " AND IsInstanceAttribute='N'"
					+ " AND (IsSerNo='Y' OR IsLot='Y' OR IsGuaranteeDate='Y'"
					+ " OR EXISTS (SELECT * FROM M_AttributeUse mau"
					+ " INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) "
					+ "WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID"
					+ " AND mau.IsActive='Y' AND ma.IsActive='Y'"
					+ " AND ma.IsInstanceAttribute='Y')"
					+ ")";
			DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
		}

		// Update M_AttributeSet.IsInstanceAttribute='N'
		{
			final String sql = "UPDATE M_AttributeSet mas"
					+ " SET IsInstanceAttribute='N' "
					+ "WHERE M_AttributeSet_ID=" + attributeSetId
					+ " AND IsInstanceAttribute='Y'"
					+ "	AND IsSerNo='N' AND IsLot='N' AND IsGuaranteeDate='N'"
					+ " AND NOT EXISTS (SELECT * FROM M_AttributeUse mau"
					+ " INNER JOIN M_Attribute ma ON (mau.M_Attribute_ID=ma.M_Attribute_ID) "
					+ "WHERE mau.M_AttributeSet_ID=mas.M_AttributeSet_ID"
					+ " AND mau.IsActive='Y' AND ma.IsActive='Y'"
					+ " AND ma.IsInstanceAttribute='Y')";
			DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
		}
	}

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		updateM_AttributeSet(getM_AttributeSet_ID());
		return success;
	}

	@Override
	protected boolean afterDelete(final boolean success)
	{
		updateM_AttributeSet(getM_AttributeSet_ID());
		return success;
	}

}
