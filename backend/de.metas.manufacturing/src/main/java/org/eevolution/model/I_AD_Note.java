package org.eevolution.model;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


public interface I_AD_Note extends org.compiere.model.I_AD_Note
{
	public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	@Override
	public void setM_Warehouse_ID(int M_Warehouse_ID);

	@Override
	public int getM_Warehouse_ID();

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException;

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

	public static final String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

	@Override
	public void setPP_Plant_ID(int PP_Plant_ID);

	@Override
	public int getPP_Plant_ID();

	@Override
	public org.compiere.model.I_S_Resource getPP_Plant() throws RuntimeException;

	@Override
	public void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant);

	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	@Override
	public void setM_Product_ID(int M_Product_ID);

	@Override
	public int getM_Product_ID();

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException;

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product);
}
