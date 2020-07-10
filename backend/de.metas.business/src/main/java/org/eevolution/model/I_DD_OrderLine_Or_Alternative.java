package org.eevolution.model;

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
 * Contains common elements of {@link I_DD_OrderLine} and {@link I_DD_OrderLine_Alternative}
 *
 * @author al
 */
public interface I_DD_OrderLine_Or_Alternative
{
	// @formatter:off
	int getAD_Client_ID();
	org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_AD_Client>(I_DD_OrderLine_Or_Alternative.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    // @formatter:on

	// @formatter:off
	void setAD_Org_ID (int AD_Org_ID);
	int getAD_Org_ID();
	org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;
	void setAD_Org(org.compiere.model.I_AD_Org AD_Org);
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_AD_Org>(I_DD_OrderLine_Or_Alternative.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    // @formatter:on

	// @formatter:off
	java.sql.Timestamp getCreated();
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object>(I_DD_OrderLine_Or_Alternative.class, "Created", null);
    String COLUMNNAME_Created = "Created";

	// @formatter:off
	int getCreatedBy();
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_AD_User>(I_DD_OrderLine_Or_Alternative.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    String COLUMNNAME_CreatedBy = "CreatedBy";
    // @formatter:on

	// @formatter:off
	void setC_UOM_ID (int C_UOM_ID);
	int getC_UOM_ID();
	org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException;
	void setC_UOM(org.compiere.model.I_C_UOM C_UOM);
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_C_UOM>(I_DD_OrderLine_Or_Alternative.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    String COLUMNNAME_C_UOM_ID = "C_UOM_ID";
    // @formatter:on

	// @formatter:off
	void setDD_OrderLine_ID (int DD_OrderLine_ID);
	int getDD_OrderLine_ID();
	void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine);
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.eevolution.model.I_DD_OrderLine>(I_DD_OrderLine_Or_Alternative.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
    String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";
    // @formatter:on

	// @formatter:off
	void setIsActive (boolean IsActive);
	boolean isActive();
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object>(I_DD_OrderLine_Or_Alternative.class, "IsActive", null);
    String COLUMNNAME_IsActive = "IsActive";
    // @formatter:on

	// @formatter:off
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);
	int getM_AttributeSetInstance_ID();
	org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException;
	void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_M_AttributeSetInstance>(I_DD_OrderLine_Or_Alternative.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
    // @formatter:on

	// @formatter:off
	void setM_Product_ID (int M_Product_ID);
	int getM_Product_ID();
	org.compiere.model.I_M_Product getM_Product() throws RuntimeException;
	void setM_Product(org.compiere.model.I_M_Product M_Product);
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_M_Product>(I_DD_OrderLine_Or_Alternative.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    String COLUMNNAME_M_Product_ID = "M_Product_ID";
    // @formatter:on

	// @formatter:off
    void setQtyDelivered (java.math.BigDecimal QtyDelivered);
	java.math.BigDecimal getQtyDelivered();
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object>(I_DD_OrderLine_Or_Alternative.class, "QtyDelivered", null);
    String COLUMNNAME_QtyDelivered = "QtyDelivered";
    // @formatter:on

	// @formatter:off
	void setQtyInTransit (java.math.BigDecimal QtyInTransit);
	java.math.BigDecimal getQtyInTransit();
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object> COLUMN_QtyInTransit = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object>(I_DD_OrderLine_Or_Alternative.class, "QtyInTransit", null);
    String COLUMNNAME_QtyInTransit = "QtyInTransit";
    // @formatter:on

	// @formatter:off
	void setQtyOrdered (java.math.BigDecimal QtyOrdered);
	java.math.BigDecimal getQtyOrdered();
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object>(I_DD_OrderLine_Or_Alternative.class, "QtyOrdered", null);
    String COLUMNNAME_QtyOrdered = "QtyOrdered";
    // @formatter:on

	// @formatter:off
	java.sql.Timestamp getUpdated();
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, Object>(I_DD_OrderLine_Or_Alternative.class, "Updated", null);
    String COLUMNNAME_Updated = "Updated";
    // @formatter:on

	// @formatter:off
	int getUpdatedBy();
    org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DD_OrderLine_Or_Alternative, org.compiere.model.I_AD_User>(I_DD_OrderLine_Or_Alternative.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    String COLUMNNAME_UpdatedBy = "UpdatedBy";
    // @formatter:on
}
