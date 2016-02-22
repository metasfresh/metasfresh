package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import de.metas.handlingunits.IHUTrxQuery;

/* package */class HUTrxQuery implements IHUTrxQuery
{
	private int M_HU_Trx_Hdr_ID = -1;
	private int Exclude_M_HU_Trx_Line_ID = -1;
	private int Parent_M_HU_Trx_Line_ID = -1;
	private int Ref_HU_Item_ID = -1;
	private int AD_Table_ID = -1;
	private int Record_ID = -1;
	private int M_HU_ID = -1;

	@Override
	public String toString()
	{
		return "HUTrxQuery ["
				+ "M_HU_Trx_Hdr_ID=" + M_HU_Trx_Hdr_ID
				+ ", Exclude_M_HU_Trx_Line_ID=" + Exclude_M_HU_Trx_Line_ID
				+ ", Parent_M_HU_Trx_Line_ID=" + Parent_M_HU_Trx_Line_ID
				+ ", Ref_HU_Item_ID=" + Ref_HU_Item_ID
				+ ", AD_Table_ID/Record_ID=" + AD_Table_ID + "/" + Record_ID
				+ "]";
	}

	@Override
	public int getM_HU_Trx_Hdr_ID()
	{
		return M_HU_Trx_Hdr_ID;
	}

	@Override
	public void setM_HU_Trx_Hdr_ID(final int m_HU_Trx_Hdr_ID)
	{
		M_HU_Trx_Hdr_ID = m_HU_Trx_Hdr_ID;
	}

	@Override
	public int getExclude_M_HU_Trx_Line_ID()
	{
		return Exclude_M_HU_Trx_Line_ID;
	}

	@Override
	public void setExclude_M_HU_Trx_Line_ID(final int exclude_M_HU_Trx_Line_ID)
	{
		Exclude_M_HU_Trx_Line_ID = exclude_M_HU_Trx_Line_ID;
	}

	@Override
	public int getParent_M_HU_Trx_Line_ID()
	{
		return Parent_M_HU_Trx_Line_ID;
	}

	@Override
	public void setParent_M_HU_Trx_Line_ID(final int parent_M_HU_Trx_Line_ID)
	{
		Parent_M_HU_Trx_Line_ID = parent_M_HU_Trx_Line_ID;
	}

	@Override
	public void setM_HU_Item_ID(final int Ref_HU_Item_ID)
	{
		this.Ref_HU_Item_ID = Ref_HU_Item_ID;
	}

	@Override
	public int getM_HU_Item_ID()
	{
		return Ref_HU_Item_ID;
	}

	@Override
	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	@Override
	public void setAD_Table_ID(final int aD_Table_ID)
	{
		AD_Table_ID = aD_Table_ID;
	}

	@Override
	public int getRecord_ID()
	{
		return Record_ID;
	}

	@Override
	public void setRecord_ID(final int record_ID)
	{
		Record_ID = record_ID;
	}

	@Override
	public void setM_HU_ID(int m_hu_ID)
	{
		M_HU_ID = m_hu_ID;
		
	}

	@Override
	public int getM_HU_ID()
	{
		return M_HU_ID;
	}
}
