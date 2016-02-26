package de.metas.fresh.printing.model;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface I_C_Print_Job_Instructions_v extends de.metas.printing.model.I_C_Print_Job_Instructions_v
{
	// @formatter:off
	public static final String COLUMNNAME_C_Order_MFGWarehouse_Report_ID = "C_Order_MFGWarehouse_Report_ID";
	public void setC_Order_MFGWarehouse_Report_ID(int C_Order_MFGWarehouse_Report_ID);
	public int getC_Order_MFGWarehouse_Report_ID();
	public de.metas.fresh.model.I_C_Order_MFGWarehouse_Report getC_Order_MFGWarehouse_Report();
	public void setC_Order_MFGWarehouse_Report(de.metas.fresh.model.I_C_Order_MFGWarehouse_Report C_Order_MFGWarehouse_Report);
	public static final org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions_v, de.metas.fresh.model.I_C_Order_MFGWarehouse_Report> COLUMN_C_Order_MFGWarehouse_Report_ID 
		= new org.adempiere.model.ModelColumn<I_C_Print_Job_Instructions_v, de.metas.fresh.model.I_C_Order_MFGWarehouse_Report>(
			I_C_Print_Job_Instructions_v.class, "C_Order_MFGWarehouse_Report_ID", de.metas.fresh.model.I_C_Order_MFGWarehouse_Report.class);
	// @formatter:onn
}
