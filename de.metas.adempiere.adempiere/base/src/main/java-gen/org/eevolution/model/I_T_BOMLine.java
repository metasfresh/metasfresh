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
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for T_BOMLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_T_BOMLine 
{

    /** TableName=T_BOMLine */
    public static final String Table_Name = "T_BOMLine";

    /** AD_Table_ID=53045 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/** Set Process Instance.
	  * Instance of the process
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/** Get Process Instance.
	  * Instance of the process
	  */
	public int getAD_PInstance_ID();

	public I_AD_PInstance getAD_PInstance() throws RuntimeException;

    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/** Set Accounting Schema.
	  * Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/** Get Accounting Schema.
	  * Rules for accounting
	  */
	public int getC_AcctSchema_ID();

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException;

    /** Column name Cost */
    public static final String COLUMNNAME_Cost = "Cost";

	/** Set Cost.
	  * Cost information
	  */
	public void setCost (BigDecimal Cost);

	/** Get Cost.
	  * Cost information
	  */
	public BigDecimal getCost();

    /** Column name CostingMethod */
    public static final String COLUMNNAME_CostingMethod = "CostingMethod";

	/** Set Costing Method.
	  * Indicates how Costs will be calculated
	  */
	public void setCostingMethod (String CostingMethod);

	/** Get Costing Method.
	  * Indicates how Costs will be calculated
	  */
	public String getCostingMethod();

    /** Column name CostStandard */
    public static final String COLUMNNAME_CostStandard = "CostStandard";

	/** Set Standard Cost.
	  * Standard Costs
	  */
	public void setCostStandard (BigDecimal CostStandard);

	/** Get Standard Cost.
	  * Standard Costs
	  */
	public BigDecimal getCostStandard();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name CurrentCostPrice */
    public static final String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";

	/** Set Current Cost Price.
	  * The currently used cost price
	  */
	public void setCurrentCostPrice (BigDecimal CurrentCostPrice);

	/** Get Current Cost Price.
	  * The currently used cost price
	  */
	public BigDecimal getCurrentCostPrice();

    /** Column name CurrentCostPriceLL */
    public static final String COLUMNNAME_CurrentCostPriceLL = "CurrentCostPriceLL";

	/** Set Current Cost Price Lower Level.
	  * Current Price Lower Level Is the sum of the costs of the components of this product manufactured for this level.
	  */
	public void setCurrentCostPriceLL (BigDecimal CurrentCostPriceLL);

	/** Get Current Cost Price Lower Level.
	  * Current Price Lower Level Is the sum of the costs of the components of this product manufactured for this level.
	  */
	public BigDecimal getCurrentCostPriceLL();

    /** Column name FutureCostPrice */
    public static final String COLUMNNAME_FutureCostPrice = "FutureCostPrice";

	/** Set Future Cost Price	  */
	public void setFutureCostPrice (BigDecimal FutureCostPrice);

	/** Get Future Cost Price	  */
	public BigDecimal getFutureCostPrice();

    /** Column name FutureCostPriceLL */
    public static final String COLUMNNAME_FutureCostPriceLL = "FutureCostPriceLL";

	/** Set Future Cost Price Lower Level	  */
	public void setFutureCostPriceLL (BigDecimal FutureCostPriceLL);

	/** Get Future Cost Price Lower Level	  */
	public BigDecimal getFutureCostPriceLL();

    /** Column name Implosion */
    public static final String COLUMNNAME_Implosion = "Implosion";

	/** Set Implosion.
	  * Implosion of a Bill of Materials refers to finding all the BOM''s in which a component is used.
	  */
	public void setImplosion (boolean Implosion);

	/** Get Implosion.
	  * Implosion of a Bill of Materials refers to finding all the BOM''s in which a component is used.
	  */
	public boolean isImplosion();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsCostFrozen */
    public static final String COLUMNNAME_IsCostFrozen = "IsCostFrozen";

	/** Set Cost Frozen.
	  * Indicated that the Standard Cost is frozen
	  */
	public void setIsCostFrozen (boolean IsCostFrozen);

	/** Get Cost Frozen.
	  * Indicated that the Standard Cost is frozen
	  */
	public boolean isCostFrozen();

    /** Column name LevelNo */
    public static final String COLUMNNAME_LevelNo = "LevelNo";

	/** Set Level no	  */
	public void setLevelNo (int LevelNo);

	/** Get Level no	  */
	public int getLevelNo();

    /** Column name Levels */
    public static final String COLUMNNAME_Levels = "Levels";

	/** Set Levels	  */
	public void setLevels (String Levels);

	/** Get Levels	  */
	public String getLevels();

    /** Column name M_CostElement_ID */
    public static final String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/** Set Cost Element.
	  * Product Cost Element
	  */
	public void setM_CostElement_ID (int M_CostElement_ID);

	/** Get Cost Element.
	  * Product Cost Element
	  */
	public int getM_CostElement_ID();

	public I_M_CostElement getM_CostElement() throws RuntimeException;

    /** Column name M_CostType_ID */
    public static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

	/** Set Cost Type.
	  * Type of Cost (e.g. Current, Plan, Future)
	  */
	public void setM_CostType_ID (int M_CostType_ID);

	/** Get Cost Type.
	  * Type of Cost (e.g. Current, Plan, Future)
	  */
	public int getM_CostType_ID();

	public I_M_CostType getM_CostType() throws RuntimeException;

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name PP_Product_BOM_ID */
    public static final String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	/** Set BOM & Formula.
	  * BOM & Formula
	  */
	public void setPP_Product_BOM_ID (int PP_Product_BOM_ID);

	/** Get BOM & Formula.
	  * BOM & Formula
	  */
	public int getPP_Product_BOM_ID();

	public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException;

    /** Column name PP_Product_BOMLine_ID */
    public static final String COLUMNNAME_PP_Product_BOMLine_ID = "PP_Product_BOMLine_ID";

	/** Set BOM Line.
	  * BOM Line
	  */
	public void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID);

	/** Get BOM Line.
	  * BOM Line
	  */
	public int getPP_Product_BOMLine_ID();

	public org.eevolution.model.I_PP_Product_BOMLine getPP_Product_BOMLine() throws RuntimeException;

    /** Column name QtyBOM */
    public static final String COLUMNNAME_QtyBOM = "QtyBOM";

	/** Set Quantity.
	  * Indicate the Quantity  use in this BOM
	  */
	public void setQtyBOM (BigDecimal QtyBOM);

	/** Get Quantity.
	  * Indicate the Quantity  use in this BOM
	  */
	public BigDecimal getQtyBOM();

    /** Column name Sel_Product_ID */
    public static final String COLUMNNAME_Sel_Product_ID = "Sel_Product_ID";

	/** Set Selected Product	  */
	public void setSel_Product_ID (int Sel_Product_ID);

	/** Get Selected Product	  */
	public int getSel_Product_ID();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name T_BOMLine_ID */
    public static final String COLUMNNAME_T_BOMLine_ID = "T_BOMLine_ID";

	/** Set Temporal BOM Line	  */
	public void setT_BOMLine_ID (int T_BOMLine_ID);

	/** Get Temporal BOM Line	  */
	public int getT_BOMLine_ID();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
