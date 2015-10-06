package de.metas.shipping.interfaces;

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


import java.math.BigDecimal;

public interface I_M_Package extends org.compiere.model.I_M_Package
{

	public static final String COLUMNNAME_PackageNetTotal = "PackageNetTotal";
	public BigDecimal getPackageNetTotal();
	public void setPackageNetTotal(BigDecimal PackageNetTotal);
	
	public static final String COLUMNNAME_PackageWeight = "PackageWeight";
	public BigDecimal getPackageWeight();
	public void setPackageWeight(BigDecimal PackageWeight);
	
	public static final String COLUMNNAME_IsClosed = "IsClosed";
	public boolean isClosed();
	public void setIsClosed(boolean IsClosed);
	
	public static final String COLUMNNAME_Processed = "Processed";
	public boolean isProcessed();
	public void setProcessed(boolean Processed);
	
	public static final String COLUMNNAME_M_PackagingContainer_ID = "M_PackagingContainer_ID";
	public int getM_PackagingContainer_ID();
	public void setM_PackagingContainer_ID(int M_PackagingContainer_ID);
	
	public static final String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";
	public int getM_Warehouse_Dest_ID();
	public void setM_Warehouse_Dest_ID(int M_Warehouse_Dest_ID);

	public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
	public int getC_BPartner_ID();
	public org.compiere.model.I_C_BPartner getC_BPartner();
	public void setC_BPartner_ID(int C_BPartner_ID);
	public void setC_BPartner(org.compiere.model.I_C_BPartner bpartner);

	public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";
	public int getC_BPartner_Location_ID();
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();
	public void setC_BPartner_Location_ID(int C_BPartner_Location_ID);
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location bpartnerLocation);
}
