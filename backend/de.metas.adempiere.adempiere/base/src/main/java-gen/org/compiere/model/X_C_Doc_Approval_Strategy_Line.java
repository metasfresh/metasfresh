// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Doc_Approval_Strategy_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Doc_Approval_Strategy_Line extends org.compiere.model.PO implements I_C_Doc_Approval_Strategy_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 593857391L;

    /** Standard Constructor */
    public X_C_Doc_Approval_Strategy_Line (final Properties ctx, final int C_Doc_Approval_Strategy_Line_ID, @Nullable final String trxName)
    {
      super (ctx, C_Doc_Approval_Strategy_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Doc_Approval_Strategy_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public org.compiere.model.I_C_Doc_Approval_Strategy getC_Doc_Approval_Strategy()
	{
		return get_ValueAsPO(COLUMNNAME_C_Doc_Approval_Strategy_ID, org.compiere.model.I_C_Doc_Approval_Strategy.class);
	}

	@Override
	public void setC_Doc_Approval_Strategy(final org.compiere.model.I_C_Doc_Approval_Strategy C_Doc_Approval_Strategy)
	{
		set_ValueFromPO(COLUMNNAME_C_Doc_Approval_Strategy_ID, org.compiere.model.I_C_Doc_Approval_Strategy.class, C_Doc_Approval_Strategy);
	}

	@Override
	public void setC_Doc_Approval_Strategy_ID (final int C_Doc_Approval_Strategy_ID)
	{
		if (C_Doc_Approval_Strategy_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Approval_Strategy_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Approval_Strategy_ID, C_Doc_Approval_Strategy_ID);
	}

	@Override
	public int getC_Doc_Approval_Strategy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Approval_Strategy_ID);
	}

	@Override
	public void setC_Doc_Approval_Strategy_Line_ID (final int C_Doc_Approval_Strategy_Line_ID)
	{
		if (C_Doc_Approval_Strategy_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Approval_Strategy_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Approval_Strategy_Line_ID, C_Doc_Approval_Strategy_Line_ID);
	}

	@Override
	public int getC_Doc_Approval_Strategy_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Approval_Strategy_Line_ID);
	}

	@Override
	public org.compiere.model.I_C_Job getC_Job()
	{
		return get_ValueAsPO(COLUMNNAME_C_Job_ID, org.compiere.model.I_C_Job.class);
	}

	@Override
	public void setC_Job(final org.compiere.model.I_C_Job C_Job)
	{
		set_ValueFromPO(COLUMNNAME_C_Job_ID, org.compiere.model.I_C_Job.class, C_Job);
	}

	@Override
	public void setC_Job_ID (final int C_Job_ID)
	{
		if (C_Job_ID < 1) 
			set_Value (COLUMNNAME_C_Job_ID, null);
		else 
			set_Value (COLUMNNAME_C_Job_ID, C_Job_ID);
	}

	@Override
	public int getC_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Job_ID);
	}

	/** 
	 * IsProjectManagerSet AD_Reference_ID=540528
	 * Reference name: Yes_No
	 */
	public static final int ISPROJECTMANAGERSET_AD_Reference_ID=540528;
	/** Yes = Y */
	public static final String ISPROJECTMANAGERSET_Yes = "Y";
	/** No = N */
	public static final String ISPROJECTMANAGERSET_No = "N";
	@Override
	public void setIsProjectManagerSet (final @Nullable java.lang.String IsProjectManagerSet)
	{
		set_Value (COLUMNNAME_IsProjectManagerSet, IsProjectManagerSet);
	}

	@Override
	public java.lang.String getIsProjectManagerSet() 
	{
		return get_ValueAsString(COLUMNNAME_IsProjectManagerSet);
	}

	@Override
	public void setMinimumAmt (final @Nullable BigDecimal MinimumAmt)
	{
		set_Value (COLUMNNAME_MinimumAmt, MinimumAmt);
	}

	@Override
	public BigDecimal getMinimumAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MinimumAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	/** 
	 * SupervisorCheckStrategy AD_Reference_ID=541844
	 * Reference name: C_Doc_Approval_Strategy_Line_SupervisorCheckStrategy
	 */
	public static final int SUPERVISORCHECKSTRATEGY_AD_Reference_ID=541844;
	/** DoNotCheck = N */
	public static final String SUPERVISORCHECKSTRATEGY_DoNotCheck = "N";
	/** AllMatching = A */
	public static final String SUPERVISORCHECKSTRATEGY_AllMatching = "A";
	/** FirstMatching = F */
	public static final String SUPERVISORCHECKSTRATEGY_FirstMatching = "F";
	@Override
	public void setSupervisorCheckStrategy (final @Nullable java.lang.String SupervisorCheckStrategy)
	{
		set_Value (COLUMNNAME_SupervisorCheckStrategy, SupervisorCheckStrategy);
	}

	@Override
	public java.lang.String getSupervisorCheckStrategy() 
	{
		return get_ValueAsString(COLUMNNAME_SupervisorCheckStrategy);
	}

	/** 
	 * Type AD_Reference_ID=541843
	 * Reference name: C_Doc_Approval_Strategy_Line_Type
	 */
	public static final int TYPE_AD_Reference_ID=541843;
	/** ProjectManager = PM */
	public static final String TYPE_ProjectManager = "PM";
	/** Job = JOB */
	public static final String TYPE_Job = "JOB";
	/** Requestor = REQUESTOR */
	public static final String TYPE_Requestor = "REQUESTOR";
	/** WorkflowResponsible = WFResponsible */
	public static final String TYPE_WorkflowResponsible = "WFResponsible";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}
}