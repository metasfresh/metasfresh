// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Workflow
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Workflow extends org.compiere.model.PO implements I_AD_Workflow, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -544179067L;

    /** Standard Constructor */
    public X_AD_Workflow (final Properties ctx, final int AD_Workflow_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Workflow_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Workflow (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AccessLevel AD_Reference_ID=5
	 * Reference name: AD_Table Access Levels
	 */
	public static final int ACCESSLEVEL_AD_Reference_ID=5;
	/** Organization = 1 */
	public static final String ACCESSLEVEL_Organization = "1";
	/** ClientPlusOrganization = 3 */
	public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
	/** SystemOnly = 4 */
	public static final String ACCESSLEVEL_SystemOnly = "4";
	/** All = 7 */
	public static final String ACCESSLEVEL_All = "7";
	/** SystemPlusClient = 6 */
	public static final String ACCESSLEVEL_SystemPlusClient = "6";
	/** ClientOnly = 2 */
	public static final String ACCESSLEVEL_ClientOnly = "2";
	@Override
	public void setAccessLevel (final java.lang.String AccessLevel)
	{
		set_Value (COLUMNNAME_AccessLevel, AccessLevel);
	}

	@Override
	public java.lang.String getAccessLevel() 
	{
		return get_ValueAsString(COLUMNNAME_AccessLevel);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAD_User_InCharge_ID (final int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, AD_User_InCharge_ID);
	}

	@Override
	public int getAD_User_InCharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_InCharge_ID);
	}

	@Override
	public void setAD_WF_Node_ID (final int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Node_ID, AD_WF_Node_ID);
	}

	@Override
	public int getAD_WF_Node_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Node_ID);
	}

	@Override
	public void setAD_WF_Responsible_ID (final int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, AD_WF_Responsible_ID);
	}

	@Override
	public int getAD_WF_Responsible_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Responsible_ID);
	}

	@Override
	public void setAD_Workflow_ID (final int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, AD_Workflow_ID);
	}

	@Override
	public int getAD_Workflow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Workflow_ID);
	}

	@Override
	public org.compiere.model.I_AD_WorkflowProcessor getAD_WorkflowProcessor()
	{
		return get_ValueAsPO(COLUMNNAME_AD_WorkflowProcessor_ID, org.compiere.model.I_AD_WorkflowProcessor.class);
	}

	@Override
	public void setAD_WorkflowProcessor(final org.compiere.model.I_AD_WorkflowProcessor AD_WorkflowProcessor)
	{
		set_ValueFromPO(COLUMNNAME_AD_WorkflowProcessor_ID, org.compiere.model.I_AD_WorkflowProcessor.class, AD_WorkflowProcessor);
	}

	@Override
	public void setAD_WorkflowProcessor_ID (final int AD_WorkflowProcessor_ID)
	{
		if (AD_WorkflowProcessor_ID < 1) 
			set_Value (COLUMNNAME_AD_WorkflowProcessor_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WorkflowProcessor_ID, AD_WorkflowProcessor_ID);
	}

	@Override
	public int getAD_WorkflowProcessor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WorkflowProcessor_ID);
	}

	@Override
	public void setAuthor (final java.lang.String Author)
	{
		set_Value (COLUMNNAME_Author, Author);
	}

	@Override
	public java.lang.String getAuthor() 
	{
		return get_ValueAsString(COLUMNNAME_Author);
	}

	@Override
	public void setCost (final BigDecimal Cost)
	{
		set_Value (COLUMNNAME_Cost, Cost);
	}

	@Override
	public BigDecimal getCost() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Cost);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setDocValueLogic (final @Nullable java.lang.String DocValueLogic)
	{
		set_Value (COLUMNNAME_DocValueLogic, DocValueLogic);
	}

	@Override
	public java.lang.String getDocValueLogic() 
	{
		return get_ValueAsString(COLUMNNAME_DocValueLogic);
	}

	@Override
	public void setDuration (final int Duration)
	{
		set_Value (COLUMNNAME_Duration, Duration);
	}

	@Override
	public int getDuration() 
	{
		return get_ValueAsInt(COLUMNNAME_Duration);
	}

	@Override
	public void setDurationLimit (final int DurationLimit)
	{
		set_Value (COLUMNNAME_DurationLimit, DurationLimit);
	}

	@Override
	public int getDurationLimit() 
	{
		return get_ValueAsInt(COLUMNNAME_DurationLimit);
	}

	/** 
	 * DurationUnit AD_Reference_ID=299
	 * Reference name: WF_DurationUnit
	 */
	public static final int DURATIONUNIT_AD_Reference_ID=299;
	/** Year = Y */
	public static final String DURATIONUNIT_Year = "Y";
	/** Month = M */
	public static final String DURATIONUNIT_Month = "M";
	/** Day = D */
	public static final String DURATIONUNIT_Day = "D";
	/** Hour = h */
	public static final String DURATIONUNIT_Hour = "h";
	/** Minute = m */
	public static final String DURATIONUNIT_Minute = "m";
	/** Second = s */
	public static final String DURATIONUNIT_Second = "s";
	@Override
	public void setDurationUnit (final @Nullable java.lang.String DurationUnit)
	{
		set_Value (COLUMNNAME_DurationUnit, DurationUnit);
	}

	@Override
	public java.lang.String getDurationUnit() 
	{
		return get_ValueAsString(COLUMNNAME_DurationUnit);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setIsBetaFunctionality (final boolean IsBetaFunctionality)
	{
		set_Value (COLUMNNAME_IsBetaFunctionality, IsBetaFunctionality);
	}

	@Override
	public boolean isBetaFunctionality() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBetaFunctionality);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsValid (final boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, IsValid);
	}

	@Override
	public boolean isValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsValid);
	}

	@Override
	public void setMovingTime (final int MovingTime)
	{
		set_Value (COLUMNNAME_MovingTime, MovingTime);
	}

	@Override
	public int getMovingTime() 
	{
		return get_ValueAsInt(COLUMNNAME_MovingTime);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setOverlapUnits (final @Nullable BigDecimal OverlapUnits)
	{
		set_Value (COLUMNNAME_OverlapUnits, OverlapUnits);
	}

	@Override
	public BigDecimal getOverlapUnits() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OverlapUnits);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPriority (final int Priority)
	{
		set_Value (COLUMNNAME_Priority, Priority);
	}

	@Override
	public int getPriority() 
	{
		return get_ValueAsInt(COLUMNNAME_Priority);
	}

	/** 
	 * ProcessType AD_Reference_ID=53224
	 * Reference name: PP_Process Type
	 */
	public static final int PROCESSTYPE_AD_Reference_ID=53224;
	/** Batch Flow  = BF */
	public static final String PROCESSTYPE_BatchFlow = "BF";
	/** Continuous Flow = CF */
	public static final String PROCESSTYPE_ContinuousFlow = "CF";
	/** Dedicate Repetititive Flow = DR */
	public static final String PROCESSTYPE_DedicateRepetititiveFlow = "DR";
	/** Job Shop = JS */
	public static final String PROCESSTYPE_JobShop = "JS";
	/** Mixed Repetitive Flow = MR */
	public static final String PROCESSTYPE_MixedRepetitiveFlow = "MR";
	/** Plant = PL */
	public static final String PROCESSTYPE_Plant = "PL";
	@Override
	public void setProcessType (final @Nullable java.lang.String ProcessType)
	{
		set_Value (COLUMNNAME_ProcessType, ProcessType);
	}

	@Override
	public java.lang.String getProcessType() 
	{
		return get_ValueAsString(COLUMNNAME_ProcessType);
	}

	/** 
	 * PublishStatus AD_Reference_ID=310
	 * Reference name: _PublishStatus
	 */
	public static final int PUBLISHSTATUS_AD_Reference_ID=310;
	/** Released = R */
	public static final String PUBLISHSTATUS_Released = "R";
	/** Test = T */
	public static final String PUBLISHSTATUS_Test = "T";
	/** Under Revision = U */
	public static final String PUBLISHSTATUS_UnderRevision = "U";
	/** Löschen = V */
	public static final String PUBLISHSTATUS_Loeschen = "V";
	@Override
	public void setPublishStatus (final java.lang.String PublishStatus)
	{
		set_Value (COLUMNNAME_PublishStatus, PublishStatus);
	}

	@Override
	public java.lang.String getPublishStatus() 
	{
		return get_ValueAsString(COLUMNNAME_PublishStatus);
	}

	@Override
	public void setQtyBatchSize (final @Nullable BigDecimal QtyBatchSize)
	{
		set_Value (COLUMNNAME_QtyBatchSize, QtyBatchSize);
	}

	@Override
	public BigDecimal getQtyBatchSize() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyBatchSize);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQueuingTime (final int QueuingTime)
	{
		set_Value (COLUMNNAME_QueuingTime, QueuingTime);
	}

	@Override
	public int getQueuingTime() 
	{
		return get_ValueAsInt(COLUMNNAME_QueuingTime);
	}

	@Override
	public org.compiere.model.I_S_Resource getS_Resource()
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(final org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	@Override
	public void setS_Resource_ID (final int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, S_Resource_ID);
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
	}

	@Override
	public void setSetupTime (final int SetupTime)
	{
		set_Value (COLUMNNAME_SetupTime, SetupTime);
	}

	@Override
	public int getSetupTime() 
	{
		return get_ValueAsInt(COLUMNNAME_SetupTime);
	}

	@Override
	public void setUnitsCycles (final @Nullable BigDecimal UnitsCycles)
	{
		set_Value (COLUMNNAME_UnitsCycles, UnitsCycles);
	}

	@Override
	public BigDecimal getUnitsCycles() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UnitsCycles);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setValidateWorkflow (final @Nullable java.lang.String ValidateWorkflow)
	{
		set_Value (COLUMNNAME_ValidateWorkflow, ValidateWorkflow);
	}

	@Override
	public java.lang.String getValidateWorkflow() 
	{
		return get_ValueAsString(COLUMNNAME_ValidateWorkflow);
	}

	@Override
	public void setValidFrom (final @Nullable java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setVersion (final int Version)
	{
		set_Value (COLUMNNAME_Version, Version);
	}

	@Override
	public int getVersion() 
	{
		return get_ValueAsInt(COLUMNNAME_Version);
	}

	@Override
	public void setWaitingTime (final int WaitingTime)
	{
		set_Value (COLUMNNAME_WaitingTime, WaitingTime);
	}

	@Override
	public int getWaitingTime() 
	{
		return get_ValueAsInt(COLUMNNAME_WaitingTime);
	}

	/** 
	 * WorkflowType AD_Reference_ID=328
	 * Reference name: AD_Workflow Type
	 */
	public static final int WORKFLOWTYPE_AD_Reference_ID=328;
	/** General = G */
	public static final String WORKFLOWTYPE_General = "G";
	/** Document Process = P */
	public static final String WORKFLOWTYPE_DocumentProcess = "P";
	/** Document Value = V */
	public static final String WORKFLOWTYPE_DocumentValue = "V";
	/** Manufacturing = M */
	public static final String WORKFLOWTYPE_Manufacturing = "M";
	/** Quality = Q */
	public static final String WORKFLOWTYPE_Quality = "Q";
	/** Repair = R */
	public static final String WORKFLOWTYPE_Repair = "R";
	@Override
	public void setWorkflowType (final java.lang.String WorkflowType)
	{
		set_Value (COLUMNNAME_WorkflowType, WorkflowType);
	}

	@Override
	public java.lang.String getWorkflowType() 
	{
		return get_ValueAsString(COLUMNNAME_WorkflowType);
	}

	@Override
	public void setWorkingTime (final int WorkingTime)
	{
		set_Value (COLUMNNAME_WorkingTime, WorkingTime);
	}

	@Override
	public int getWorkingTime() 
	{
		return get_ValueAsInt(COLUMNNAME_WorkingTime);
	}

	@Override
	public void setYield (final int Yield)
	{
		set_Value (COLUMNNAME_Yield, Yield);
	}

	@Override
	public int getYield() 
	{
		return get_ValueAsInt(COLUMNNAME_Yield);
	}
}