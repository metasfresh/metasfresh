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

import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.Properties;

@Deprecated
public class MAttribute extends X_M_Attribute
{
	/**
	 *
	 */
	private static final long serialVersionUID = 7869800574413317999L;

	@SuppressWarnings("unused")
	public MAttribute(final Properties ctx, final int M_Attribute_ID, final String trxName)
	{
		super(ctx, M_Attribute_ID, trxName);
		if (is_new())
		{
			setAttributeValueType(ATTRIBUTEVALUETYPE_StringMax40);
			setIsInstanceAttribute(false);
			setIsMandatory(false);
		}
	}

	@SuppressWarnings("unused")
	public MAttribute(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		return "MAttribute[" + get_ID() + "-" + getName()
				+ ",Type=" + getAttributeValueType()
				+ ",Instance=" + isInstanceAttribute()
				+ "]";
	}    // toString

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		// Changed to Instance Attribute
		if (!newRecord
				&& is_ValueChanged(COLUMNNAME_IsInstanceAttribute)
				&& isInstanceAttribute())
		{
			final String sql = "UPDATE M_AttributeSet mas "
					+ "SET IsInstanceAttribute='Y' "
					+ "WHERE IsInstanceAttribute='N'"
					+ " AND EXISTS (SELECT * FROM M_AttributeUse mau "
					+ "WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID"
					+ " AND mau.M_Attribute_ID=" + getM_Attribute_ID() + ")";
			final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, get_TrxName());
			log.debug("AttributeSet Instance set #{}", no);
		}
		return success;
	}

}
