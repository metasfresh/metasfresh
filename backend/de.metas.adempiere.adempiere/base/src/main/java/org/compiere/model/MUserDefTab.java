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


import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;

/**
 * Window Tab Customization
 * @author Teo Sarca, teo.sarca@gmail.com
 * 			<li>BF [ 2726889 ] Finish User Window (AD_UserDef*) functionality
 */
public class MUserDefTab extends X_AD_UserDef_Tab
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1211944378938157092L;

	public MUserDefTab(Properties ctx, int AD_UserDef_Tab_ID, String trxName)
	{
		super(ctx, AD_UserDef_Tab_ID, trxName);
	}

	public MUserDefTab(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	public MUserDefField[] getFields(boolean reload)
	{
		if (!reload && m_fields != null)
		{
			return m_fields;
		}
		final String whereClause = MUserDefField.COLUMNNAME_AD_UserDef_Tab_ID+"=?";
		final List<MUserDefTab> list = new Query(getCtx(), MUserDefField.Table_Name, whereClause, get_TrxName())
								.setParameters(get_ID())
								.setOnlyActiveRecords(true)
								.setOrderBy(MUserDefField.COLUMNNAME_AD_Field_ID)
								.list();
		//
		m_fields = list.toArray(new MUserDefField[list.size()]);
		return m_fields;
	}
	private MUserDefField[] m_fields = null;
	
	public MUserDefField getField(int AD_Field_ID)
	{
		if (AD_Field_ID <= 0)
		{
			return null;
		}
		for (MUserDefField field : getFields(false))
		{
			if (AD_Field_ID == field.getAD_Field_ID())
			{
				return field;
			}
		}
		return null;
	}

	public void apply(GridTabVO vo)
	{
		final String name = getName();
		if (!Check.isEmpty(name) && name.length() > 1)
			vo.setName(name);
		if (!Check.isEmpty(getDescription()))
			vo.setDescription(getDescription());
		if (!Check.isEmpty(getHelp()))
			vo.setHelp(getHelp());
		//
		vo.IsSingleRow = this.isSingleRow();
		vo.setReadOnly(this.isReadOnly()); 
//		vo.IsDeleteable
//		vo.IsHighVolume
//		vo.IsInsertRecord
	}
}
