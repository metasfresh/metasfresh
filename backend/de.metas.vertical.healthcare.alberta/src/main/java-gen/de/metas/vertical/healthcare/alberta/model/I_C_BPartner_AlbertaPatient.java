package de.metas.vertical.healthcare.alberta.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_BPartner_AlbertaPatient
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_AlbertaPatient 
{

	String Table_Name = "C_BPartner_AlbertaPatient";

//	/** AD_Table_ID=541644 */
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
	 * Set Created by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_CreatedBy_ID (int AD_User_CreatedBy_ID);

	/**
	 * Get Created by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_CreatedBy_ID();

	String COLUMNNAME_AD_User_CreatedBy_ID = "AD_User_CreatedBy_ID";

	/**
	 * Set Field nurse.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_FieldNurse_ID (int AD_User_FieldNurse_ID);

	/**
	 * Get Field nurse.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_FieldNurse_ID();

	String COLUMNNAME_AD_User_FieldNurse_ID = "AD_User_FieldNurse_ID";

	/**
	 * Set Updated by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_UpdatedBy_ID (int AD_User_UpdatedBy_ID);

	/**
	 * Get Updated by.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_UpdatedBy_ID();

	String COLUMNNAME_AD_User_UpdatedBy_ID = "AD_User_UpdatedBy_ID";

	/**
	 * Set Alberta patient data.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_AlbertaPatient_ID (int C_BPartner_AlbertaPatient_ID);

	/**
	 * Get Alberta patient data.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_AlbertaPatient_ID();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_C_BPartner_AlbertaPatient_ID = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "C_BPartner_AlbertaPatient_ID", null);
	String COLUMNNAME_C_BPartner_AlbertaPatient_ID = "C_BPartner_AlbertaPatient_ID";

	/**
	 * Set Discharging hospital.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Hospital_ID (int C_BPartner_Hospital_ID);

	/**
	 * Get Discharging hospital.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Hospital_ID();

	String COLUMNNAME_C_BPartner_Hospital_ID = "C_BPartner_Hospital_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Payer.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Payer_ID (int C_BPartner_Payer_ID);

	/**
	 * Get Payer.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Payer_ID();

	String COLUMNNAME_C_BPartner_Payer_ID = "C_BPartner_Payer_ID";

	/**
	 * Set CareDegree.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCareDegree (@Nullable BigDecimal CareDegree);

	/**
	 * Get CareDegree.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCareDegree();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_CareDegree = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "CareDegree", null);
	String COLUMNNAME_CareDegree = "CareDegree";

	/**
	 * Set Classification.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClassification (@Nullable String Classification);

	/**
	 * Get Classification.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getClassification();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_Classification = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "Classification", null);
	String COLUMNNAME_Classification = "Classification";

	/**
	 * Set Copayment exempt from.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopaymentFrom (@Nullable java.sql.Timestamp CopaymentFrom);

	/**
	 * Get Copayment exempt from.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCopaymentFrom();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_CopaymentFrom = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "CopaymentFrom", null);
	String COLUMNNAME_CopaymentFrom = "CopaymentFrom";

	/**
	 * Set Copayment exempt to.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopaymentTo (@Nullable java.sql.Timestamp CopaymentTo);

	/**
	 * Get Copayment exempt to.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCopaymentTo();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_CopaymentTo = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "CopaymentTo", null);
	String COLUMNNAME_CopaymentTo = "CopaymentTo";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Set Created.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreatedAt (@Nullable java.sql.Timestamp CreatedAt);

	/**
	 * Get Created.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreatedAt();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_CreatedAt = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "CreatedAt", null);
	String COLUMNNAME_CreatedAt = "CreatedAt";

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
	 * Set Deactivation comment.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeactivationComment (@Nullable String DeactivationComment);

	/**
	 * Get Deactivation comment.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDeactivationComment();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_DeactivationComment = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "DeactivationComment", null);
	String COLUMNNAME_DeactivationComment = "DeactivationComment";

	/**
	 * Set Deactivated at.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeactivationDate (@Nullable java.sql.Timestamp DeactivationDate);

	/**
	 * Get Deactivated at.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDeactivationDate();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_DeactivationDate = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "DeactivationDate", null);
	String COLUMNNAME_DeactivationDate = "DeactivationDate";

	/**
	 * Set Reason for deactivation.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeactivationReason (@Nullable String DeactivationReason);

	/**
	 * Get Reason for deactivation.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDeactivationReason();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_DeactivationReason = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "DeactivationReason", null);
	String COLUMNNAME_DeactivationReason = "DeactivationReason";

	/**
	 * Set Discharge date.
	 * Last discharge date from the hospital
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDischargeDate (@Nullable java.sql.Timestamp DischargeDate);

	/**
	 * Get Discharge date.
	 * Last discharge date from the hospital
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDischargeDate();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_DischargeDate = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "DischargeDate", null);
	String COLUMNNAME_DischargeDate = "DischargeDate";

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

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IV Therapy.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsIVTherapy (boolean IsIVTherapy);

	/**
	 * Get IV Therapy.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isIVTherapy();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_IsIVTherapy = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "IsIVTherapy", null);
	String COLUMNNAME_IsIVTherapy = "IsIVTherapy";

	/**
	 * Set Transfer patient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsTransferPatient (boolean IsTransferPatient);

	/**
	 * Get Transfer patient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isTransferPatient();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_IsTransferPatient = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "IsTransferPatient", null);
	String COLUMNNAME_IsTransferPatient = "IsTransferPatient";

	/**
	 * Set Insurance number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNumberOfInsured (@Nullable String NumberOfInsured);

	/**
	 * Get Insurance number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getNumberOfInsured();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_NumberOfInsured = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "NumberOfInsured", null);
	String COLUMNNAME_NumberOfInsured = "NumberOfInsured";

	/**
	 * Set Payer type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayerType (@Nullable String PayerType);

	/**
	 * Get Payer type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getPayerType();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_PayerType = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "PayerType", null);
	String COLUMNNAME_PayerType = "PayerType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Set Last update.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUpdatedAt (@Nullable java.sql.Timestamp UpdatedAt);

	/**
	 * Get Last update.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdatedAt();

	ModelColumn<I_C_BPartner_AlbertaPatient, Object> COLUMN_UpdatedAt = new ModelColumn<>(I_C_BPartner_AlbertaPatient.class, "UpdatedAt", null);
	String COLUMNNAME_UpdatedAt = "UpdatedAt";

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
}
