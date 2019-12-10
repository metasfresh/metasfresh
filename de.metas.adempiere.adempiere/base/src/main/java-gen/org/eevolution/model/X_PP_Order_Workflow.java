/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Order_Workflow
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Order_Workflow extends org.compiere.model.PO implements I_PP_Order_Workflow, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 535405891L;

    /** Standard Constructor */
    public X_PP_Order_Workflow (Properties ctx, int PP_Order_Workflow_ID, String trxName)
    {
      super (ctx, PP_Order_Workflow_ID, trxName);
      /** if (PP_Order_Workflow_ID == 0)
        {
			setAD_Workflow_ID (0);
			setDuration (0); // 0
			setDurationLimit (0);
			setDurationUnit (null); // h
			setPP_Order_ID (0);
			setPP_Order_Workflow_ID (0);
			setWaitingTime (0);
        } */
    }

    /** Load Constructor */
    public X_PP_Order_Workflow (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_WF_Node_Template getAD_WF_Node_Template()
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Node_Template_ID, org.compiere.model.I_AD_WF_Node_Template.class);
	}

	@Override
	public void setAD_WF_Node_Template(org.compiere.model.I_AD_WF_Node_Template AD_WF_Node_Template)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Node_Template_ID, org.compiere.model.I_AD_WF_Node_Template.class, AD_WF_Node_Template);
	}

	/** Set Workflow Steps Template.
		@param AD_WF_Node_Template_ID Workflow Steps Template	  */
	@Override
	public void setAD_WF_Node_Template_ID (int AD_WF_Node_Template_ID)
	{
		if (AD_WF_Node_Template_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Node_Template_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Node_Template_ID, Integer.valueOf(AD_WF_Node_Template_ID));
	}

	/** Get Workflow Steps Template.
		@return Workflow Steps Template	  */
	@Override
	public int getAD_WF_Node_Template_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Node_Template_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Workflow getAD_Workflow()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class);
	}

	@Override
	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow)
	{
		set_ValueFromPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class, AD_Workflow);
	}

	/** Set Workflow.
		@param AD_Workflow_ID 
		Workflow or combination of tasks
	  */
	@Override
	public void setAD_Workflow_ID (int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_Value (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
	}

	/** Get Workflow.
		@return Workflow or combination of tasks
	  */
	@Override
	public int getAD_Workflow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Workflow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Duration.
		@param Duration 
		Normal Duration in Duration Unit
	  */
	@Override
	public void setDuration (int Duration)
	{
		set_Value (COLUMNNAME_Duration, Integer.valueOf(Duration));
	}

	/** Get Duration.
		@return Normal Duration in Duration Unit
	  */
	@Override
	public int getDuration () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Duration);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Duration Limit.
		@param DurationLimit 
		Maximum Duration in Duration Unit
	  */
	@Override
	public void setDurationLimit (int DurationLimit)
	{
		set_Value (COLUMNNAME_DurationLimit, Integer.valueOf(DurationLimit));
	}

	/** Get Duration Limit.
		@return Maximum Duration in Duration Unit
	  */
	@Override
	public int getDurationLimit () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DurationLimit);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Set Duration Unit.
		@param DurationUnit 
		Unit of Duration
	  */
	@Override
	public void setDurationUnit (java.lang.String DurationUnit)
	{

		set_Value (COLUMNNAME_DurationUnit, DurationUnit);
	}

	/** Get Duration Unit.
		@return Unit of Duration
	  */
	@Override
	public java.lang.String getDurationUnit () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DurationUnit);
	}

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Moving Time.
		@param MovingTime Moving Time	  */
	@Override
	public void setMovingTime (int MovingTime)
	{
		set_Value (COLUMNNAME_MovingTime, Integer.valueOf(MovingTime));
	}

	/** Get Moving Time.
		@return Moving Time	  */
	@Override
	public int getMovingTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MovingTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Overlap Units.
		@param OverlapUnits 
		Overlap Units are number of units that must be completed before they are moved the next activity
	  */
	@Override
	public void setOverlapUnits (java.math.BigDecimal OverlapUnits)
	{
		set_Value (COLUMNNAME_OverlapUnits, OverlapUnits);
	}

	/** Get Overlap Units.
		@return Overlap Units are number of units that must be completed before they are moved the next activity
	  */
	@Override
	public java.math.BigDecimal getOverlapUnits () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OverlapUnits);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	/** Set Produktionsauftrag.
		@param PP_Order_ID Produktionsauftrag	  */
	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Produktionsauftrag.
		@return Produktionsauftrag	  */
	@Override
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Order_Node getPP_Order_Node()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Node_ID, org.eevolution.model.I_PP_Order_Node.class);
	}

	@Override
	public void setPP_Order_Node(org.eevolution.model.I_PP_Order_Node PP_Order_Node)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Node_ID, org.eevolution.model.I_PP_Order_Node.class, PP_Order_Node);
	}

	/** Set Manufacturing Order Activity.
		@param PP_Order_Node_ID 
		Workflow Node (activity), step or process
	  */
	@Override
	public void setPP_Order_Node_ID (int PP_Order_Node_ID)
	{
		if (PP_Order_Node_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_Node_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_Node_ID, Integer.valueOf(PP_Order_Node_ID));
	}

	/** Get Manufacturing Order Activity.
		@return Workflow Node (activity), step or process
	  */
	@Override
	public int getPP_Order_Node_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Node_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manufacturing Order Workflow.
		@param PP_Order_Workflow_ID Manufacturing Order Workflow	  */
	@Override
	public void setPP_Order_Workflow_ID (int PP_Order_Workflow_ID)
	{
		if (PP_Order_Workflow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Workflow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Workflow_ID, Integer.valueOf(PP_Order_Workflow_ID));
	}

	/** Get Manufacturing Order Workflow.
		@return Manufacturing Order Workflow	  */
	@Override
	public int getPP_Order_Workflow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Workflow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Set Process Type.
		@param ProcessType Process Type	  */
	@Override
	public void setProcessType (java.lang.String ProcessType)
	{

		set_Value (COLUMNNAME_ProcessType, ProcessType);
	}

	/** Get Process Type.
		@return Process Type	  */
	@Override
	public java.lang.String getProcessType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProcessType);
	}

	/** Set Qty Batch Size.
		@param QtyBatchSize Qty Batch Size	  */
	@Override
	public void setQtyBatchSize (java.math.BigDecimal QtyBatchSize)
	{
		set_Value (COLUMNNAME_QtyBatchSize, QtyBatchSize);
	}

	/** Get Qty Batch Size.
		@return Qty Batch Size	  */
	@Override
	public java.math.BigDecimal getQtyBatchSize () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBatchSize);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Queuing Time.
		@param QueuingTime Queuing Time	  */
	@Override
	public void setQueuingTime (int QueuingTime)
	{
		set_Value (COLUMNNAME_QueuingTime, Integer.valueOf(QueuingTime));
	}

	/** Get Queuing Time.
		@return Queuing Time	  */
	@Override
	public int getQueuingTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QueuingTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_S_Resource getS_Resource()
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	/** Set Ressource.
		@param S_Resource_ID 
		Resource
	  */
	@Override
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_Value (COLUMNNAME_S_Resource_ID, null);
		else 
			set_Value (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	/** Get Ressource.
		@return Resource
	  */
	@Override
	public int getS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Setup Time.
		@param SetupTime 
		Setup time before starting Production
	  */
	@Override
	public void setSetupTime (int SetupTime)
	{
		set_Value (COLUMNNAME_SetupTime, Integer.valueOf(SetupTime));
	}

	/** Get Setup Time.
		@return Setup time before starting Production
	  */
	@Override
	public int getSetupTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SetupTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Units by Cycles.
		@param UnitsCycles 
		The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	  */
	@Override
	public void setUnitsCycles (java.math.BigDecimal UnitsCycles)
	{
		set_Value (COLUMNNAME_UnitsCycles, UnitsCycles);
	}

	/** Get Units by Cycles.
		@return The Units by Cycles are defined for process type  Flow Repetitive Dedicated and  indicated the product to be manufactured on a production line for duration unit.
	  */
	@Override
	public java.math.BigDecimal getUnitsCycles () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UnitsCycles);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Waiting Time.
		@param WaitingTime 
		Workflow Simulation Waiting time
	  */
	@Override
	public void setWaitingTime (int WaitingTime)
	{
		set_Value (COLUMNNAME_WaitingTime, Integer.valueOf(WaitingTime));
	}

	/** Get Waiting Time.
		@return Workflow Simulation Waiting time
	  */
	@Override
	public int getWaitingTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WaitingTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Working Time.
		@param WorkingTime 
		Workflow Simulation Execution Time
	  */
	@Override
	public void setWorkingTime (int WorkingTime)
	{
		set_Value (COLUMNNAME_WorkingTime, Integer.valueOf(WorkingTime));
	}

	/** Get Working Time.
		@return Workflow Simulation Execution Time
	  */
	@Override
	public int getWorkingTime () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WorkingTime);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Yield %.
		@param Yield 
		The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	@Override
	public void setYield (int Yield)
	{
		set_Value (COLUMNNAME_Yield, Integer.valueOf(Yield));
	}

	/** Get Yield %.
		@return The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	@Override
	public int getYield () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Yield);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}