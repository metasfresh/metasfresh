/**
 * 
 */
package de.metas.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
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
 * @author tsa
 * 
 */
public interface I_M_Product_Category extends org.compiere.model.I_M_Product_Category
{
	// public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";
	// public void setC_DocType_ID(int C_DocType_ID);
	// public int getC_DocType_ID();

	public static final String COLUMNNAME_IsSummary = "IsSummary";
	@Override
	public void setIsSummary(boolean IsSummary);
	@Override
	public boolean isSummary();

	@Override
	public de.metas.adempiere.model.I_M_Product_Category getM_Product_Category_Parent();
	
	//09779
	public static final String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";
	
	public void setIsPackagingMaterial(boolean IsPackagingMaterial);
	
	public boolean isPackagingMaterial();
}
