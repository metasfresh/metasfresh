package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BPartner_Stats
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_Stats 
{

	String Table_Name = "C_BPartner_Stats";

//	/** AD_Table_ID=540763 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Actual Life Time Value.
	 * Actual Life Time Revenue
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualLifeTimeValue (@Nullable BigDecimal ActualLifeTimeValue);

	/**
	 * Get Actual Life Time Value.
	 * Actual Life Time Revenue
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualLifeTimeValue();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_ActualLifeTimeValue = new ModelColumn<>(I_C_BPartner_Stats.class, "ActualLifeTimeValue", null);
	String COLUMNNAME_ActualLifeTimeValue = "ActualLifeTimeValue";

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
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set C_BPartner_Stats.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Stats_ID (int C_BPartner_Stats_ID);

	/**
	 * Get C_BPartner_Stats.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Stats_ID();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_C_BPartner_Stats_ID = new ModelColumn<>(I_C_BPartner_Stats.class, "C_BPartner_Stats_ID", null);
	String COLUMNNAME_C_BPartner_Stats_ID = "C_BPartner_Stats_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_Stats.class, "Created", null);
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
	 * Set Credit limit Usage.
	 * Percent of Credit used from the limit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditLimitIndicator (@Nullable java.lang.String CreditLimitIndicator);

	/**
	 * Get Credit limit Usage.
	 * Percent of Credit used from the limit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreditLimitIndicator();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_CreditLimitIndicator = new ModelColumn<>(I_C_BPartner_Stats.class, "CreditLimitIndicator", null);
	String COLUMNNAME_CreditLimitIndicator = "CreditLimitIndicator";

	/**
	 * Set Delivery credit limit indicator %.
	 * Percent of Credit used from the limit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryCreditLimitIndicator (@Nullable java.lang.String DeliveryCreditLimitIndicator);

	/**
	 * Get Delivery credit limit indicator %.
	 * Percent of Credit used from the limit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryCreditLimitIndicator();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_DeliveryCreditLimitIndicator = new ModelColumn<>(I_C_BPartner_Stats.class, "DeliveryCreditLimitIndicator", null);
	String COLUMNNAME_DeliveryCreditLimitIndicator = "DeliveryCreditLimitIndicator";

	/**
	 * Set Delivery Credit Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDelivery_CreditStatus (@Nullable java.lang.String Delivery_CreditStatus);

	/**
	 * Get Delivery Credit Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDelivery_CreditStatus();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_Delivery_CreditStatus = new ModelColumn<>(I_C_BPartner_Stats.class, "Delivery_CreditStatus", null);
	String COLUMNNAME_Delivery_CreditStatus = "Delivery_CreditStatus";

	/**
	 * Set Delivery Credit Used.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDelivery_CreditUsed (@Nullable BigDecimal Delivery_CreditUsed);

	/**
	 * Get Delivery Credit Used.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDelivery_CreditUsed();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_Delivery_CreditUsed = new ModelColumn<>(I_C_BPartner_Stats.class, "Delivery_CreditUsed", null);
	String COLUMNNAME_Delivery_CreditUsed = "Delivery_CreditUsed";

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

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_Stats.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_C_BPartner_Stats, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_BPartner_Stats.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Open Balance.
	 * Total Open Balance Amount in primary Accounting Currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOpenItems (@Nullable BigDecimal OpenItems);

	/**
	 * Get Open Balance.
	 * Total Open Balance Amount in primary Accounting Currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOpenItems();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_OpenItems = new ModelColumn<>(I_C_BPartner_Stats.class, "OpenItems", null);
	String COLUMNNAME_OpenItems = "OpenItems";

	/**
	 * Set Credit Status.
	 * Business Partner Credit Status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSOCreditStatus (@Nullable java.lang.String SOCreditStatus);

	/**
	 * Get Credit Status.
	 * Business Partner Credit Status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSOCreditStatus();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_SOCreditStatus = new ModelColumn<>(I_C_BPartner_Stats.class, "SOCreditStatus", null);
	String COLUMNNAME_SOCreditStatus = "SOCreditStatus";

	/**
	 * Set Credit Used.
	 * Current open balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSO_CreditUsed (@Nullable BigDecimal SO_CreditUsed);

	/**
	 * Get Credit Used.
	 * Current open balance
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getSO_CreditUsed();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_SO_CreditUsed = new ModelColumn<>(I_C_BPartner_Stats.class, "SO_CreditUsed", null);
	String COLUMNNAME_SO_CreditUsed = "SO_CreditUsed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_Stats, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_Stats.class, "Updated", null);
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
}
