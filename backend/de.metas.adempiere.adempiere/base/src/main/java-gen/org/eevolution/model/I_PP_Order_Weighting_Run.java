package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_Order_Weighting_Run
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Order_Weighting_Run 
{

	String Table_Name = "PP_Order_Weighting_Run";

//	/** AD_Table_ID=542254 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_Created = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Document Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Document Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateDoc();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_DateDoc = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "DateDoc", null);
	String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_Description = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Tolerance Excheeded.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsToleranceExceeded (boolean IsToleranceExceeded);

	/**
	 * Get Tolerance Excheeded.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isToleranceExceeded();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_IsToleranceExceeded = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "IsToleranceExceeded", null);
	String COLUMNNAME_IsToleranceExceeded = "IsToleranceExceeded";

	/**
	 * Set Max Weight.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMaxWeight (BigDecimal MaxWeight);

	/**
	 * Get Max Weight.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMaxWeight();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_MaxWeight = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "MaxWeight", null);
	String COLUMNNAME_MaxWeight = "MaxWeight";

	/**
	 * Set Min Weight.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMinWeight (BigDecimal MinWeight);

	/**
	 * Get Min Weight.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMinWeight();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_MinWeight = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "MinWeight", null);
	String COLUMNNAME_MinWeight = "MinWeight";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Manufacturing Order BOM Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID);

	/**
	 * Get Manufacturing Order BOM Line.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_BOMLine_ID();

	@Nullable org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine();

	void setPP_Order_BOMLine(@Nullable org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine);

	ModelColumn<I_PP_Order_Weighting_Run, org.eevolution.model.I_PP_Order_BOMLine> COLUMN_PP_Order_BOMLine_ID = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order_BOMLine.class);
	String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_ID();

	org.eevolution.model.I_PP_Order getPP_Order();

	void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

	ModelColumn<I_PP_Order_Weighting_Run, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Weighting Run.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Weighting_Run_ID (int PP_Order_Weighting_Run_ID);

	/**
	 * Get Weighting Run.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Weighting_Run_ID();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_PP_Order_Weighting_Run_ID = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "PP_Order_Weighting_Run_ID", null);
	String COLUMNNAME_PP_Order_Weighting_Run_ID = "PP_Order_Weighting_Run_ID";

	/**
	 * Set Weighting Specifications.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Weighting_Spec_ID (int PP_Weighting_Spec_ID);

	/**
	 * Get Weighting Specifications.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Weighting_Spec_ID();

	org.eevolution.model.I_PP_Weighting_Spec getPP_Weighting_Spec();

	void setPP_Weighting_Spec(org.eevolution.model.I_PP_Weighting_Spec PP_Weighting_Spec);

	ModelColumn<I_PP_Order_Weighting_Run, org.eevolution.model.I_PP_Weighting_Spec> COLUMN_PP_Weighting_Spec_ID = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "PP_Weighting_Spec_ID", org.eevolution.model.I_PP_Weighting_Spec.class);
	String COLUMNNAME_PP_Weighting_Spec_ID = "PP_Weighting_Spec_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_Processed = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Target Weight.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTargetWeight (BigDecimal TargetWeight);

	/**
	 * Get Target Weight.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTargetWeight();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_TargetWeight = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "TargetWeight", null);
	String COLUMNNAME_TargetWeight = "TargetWeight";

	/**
	 * Set Tolerance %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTolerance_Perc (BigDecimal Tolerance_Perc);

	/**
	 * Get Tolerance %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTolerance_Perc();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_Tolerance_Perc = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "Tolerance_Perc", null);
	String COLUMNNAME_Tolerance_Perc = "Tolerance_Perc";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Required Weight Checks.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWeightChecksRequired (int WeightChecksRequired);

	/**
	 * Get Required Weight Checks.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWeightChecksRequired();

	ModelColumn<I_PP_Order_Weighting_Run, Object> COLUMN_WeightChecksRequired = new ModelColumn<>(I_PP_Order_Weighting_Run.class, "WeightChecksRequired", null);
	String COLUMNNAME_WeightChecksRequired = "WeightChecksRequired";
}
