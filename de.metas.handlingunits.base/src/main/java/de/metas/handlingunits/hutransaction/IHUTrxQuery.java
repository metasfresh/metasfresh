package de.metas.handlingunits.hutransaction;

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


public interface IHUTrxQuery
{

	void setParent_M_HU_Trx_Line_ID(int parent_M_HU_Trx_Line_ID);

	int getParent_M_HU_Trx_Line_ID();

	void setExclude_M_HU_Trx_Line_ID(int exclude_M_HU_Trx_Line_ID);

	int getExclude_M_HU_Trx_Line_ID();

	void setM_HU_Trx_Hdr_ID(int m_HU_Trx_Hdr_ID);

	int getM_HU_Trx_Hdr_ID();

	void setM_HU_Item_ID(int Ref_HU_Item_ID);

	int getM_HU_Item_ID();

	void setAD_Table_ID(int aD_Table_ID);

	int getAD_Table_ID();

	void setRecord_ID(int record_ID);

	int getRecord_ID();
	
	void setM_HU_ID(int m_hu_ID);
	
	int getM_HU_ID();
}
