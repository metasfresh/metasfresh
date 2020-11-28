/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package de.metas.esb.edi.model;


/** Generated Interface for EDI_Desadv_NullDelivery_C_OrderLine_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_Desadv_NullDelivery_C_OrderLine_v 
{

    /** TableName=EDI_Desadv_NullDelivery_C_OrderLine_v */
    public static final String Table_Name = "EDI_Desadv_NullDelivery_C_OrderLine_v";

    /** AD_Table_ID=540653 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLine_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLine();

	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

    /** Column definition for C_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv_NullDelivery_C_OrderLine_v, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new org.adempiere.model.ModelColumn<I_EDI_Desadv_NullDelivery_C_OrderLine_v, org.compiere.model.I_C_OrderLine>(I_EDI_Desadv_NullDelivery_C_OrderLine_v.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_Desadv_ID (int EDI_Desadv_ID);

	/**
	 * Get DESADV.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEDI_Desadv_ID();

	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv();

	public void setEDI_Desadv(de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv);

    /** Column definition for EDI_Desadv_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_Desadv_NullDelivery_C_OrderLine_v, de.metas.esb.edi.model.I_EDI_Desadv> COLUMN_EDI_Desadv_ID = new org.adempiere.model.ModelColumn<I_EDI_Desadv_NullDelivery_C_OrderLine_v, de.metas.esb.edi.model.I_EDI_Desadv>(I_EDI_Desadv_NullDelivery_C_OrderLine_v.class, "EDI_Desadv_ID", de.metas.esb.edi.model.I_EDI_Desadv.class);
    /** Column name EDI_Desadv_ID */
    public static final String COLUMNNAME_EDI_Desadv_ID = "EDI_Desadv_ID";
}
