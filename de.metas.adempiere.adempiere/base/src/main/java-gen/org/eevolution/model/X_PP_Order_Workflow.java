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
/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for PP_Order_Workflow
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Order_Workflow extends org.compiere.model.PO implements I_PP_Order_Workflow, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 78776406L;

    /** Standard Constructor */
    public X_PP_Order_Workflow (Properties ctx, int PP_Order_Workflow_ID, String trxName)
    {
      super (ctx, PP_Order_Workflow_ID, trxName);
      /** if (PP_Order_Workflow_ID == 0)
        {
			setAccessLevel (null);
			setAD_Workflow_ID (0);
			setAuthor (null);
			setCost (Env.ZERO);
			setDuration (0);
// 0
			setDurationLimit (0);
			setDurationUnit (null);
// h
			setEntityType (null);
// U
			setName (null);
			setPP_Order_ID (0);
			setPP_Order_Workflow_ID (0);
			setPriority (0);
			setPublishStatus (null);
// U
			setVersion (0);
			setWaitingTime (0);
        } */
    }

    /** Load Constructor */
    public X_PP_Order_Workflow (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    @Override
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_PP_Order_Workflow[")
        .append(get_ID()).append("]");
      return sb.toString();
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
	/** Set Berechtigungsstufe.
		@param AccessLevel 
		Access Level required
	  */
	@Override
	public void setAccessLevel (java.lang.String AccessLevel)
	{

		set_Value (COLUMNNAME_AccessLevel, AccessLevel);
	}

	/** Get Berechtigungsstufe.
		@return Access Level required
	  */
	@Override
	public java.lang.String getAccessLevel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccessLevel);
	}

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_WF_Node getAD_WF_Node() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Node_ID, org.compiere.model.I_AD_WF_Node.class);
	}

	@Override
	public void setAD_WF_Node(org.compiere.model.I_AD_WF_Node AD_WF_Node)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Node_ID, org.compiere.model.I_AD_WF_Node.class, AD_WF_Node);
	}

	/** Set Knoten.
		@param AD_WF_Node_ID 
		Workflow Node (activity), step or process
	  */
	@Override
	public void setAD_WF_Node_ID (int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
	}

	/** Get Knoten.
		@return Workflow Node (activity), step or process
	  */
	@Override
	public int getAD_WF_Node_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Node_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Responsible_ID, org.compiere.model.I_AD_WF_Responsible.class);
	}

	@Override
	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Responsible_ID, org.compiere.model.I_AD_WF_Responsible.class, AD_WF_Responsible);
	}

	/** Set Workflow - Verantwortlicher.
		@param AD_WF_Responsible_ID 
		Responsible for Workflow Execution
	  */
	@Override
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
	}

	/** Get Workflow - Verantwortlicher.
		@return Responsible for Workflow Execution
	  */
	@Override
	public int getAD_WF_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException
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

	@Override
	public org.compiere.model.I_AD_WorkflowProcessor getAD_WorkflowProcessor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WorkflowProcessor_ID, org.compiere.model.I_AD_WorkflowProcessor.class);
	}

	@Override
	public void setAD_WorkflowProcessor(org.compiere.model.I_AD_WorkflowProcessor AD_WorkflowProcessor)
	{
		set_ValueFromPO(COLUMNNAME_AD_WorkflowProcessor_ID, org.compiere.model.I_AD_WorkflowProcessor.class, AD_WorkflowProcessor);
	}

	/** Set Workflow - Prozessor.
		@param AD_WorkflowProcessor_ID 
		Workflow Processor Server
	  */
	@Override
	public void setAD_WorkflowProcessor_ID (int AD_WorkflowProcessor_ID)
	{
		if (AD_WorkflowProcessor_ID < 1) 
			set_Value (COLUMNNAME_AD_WorkflowProcessor_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WorkflowProcessor_ID, Integer.valueOf(AD_WorkflowProcessor_ID));
	}

	/** Get Workflow - Prozessor.
		@return Workflow Processor Server
	  */
	@Override
	public int getAD_WorkflowProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WorkflowProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Author.
		@param Author 
		Author/Creator of the Entity
	  */
	@Override
	public void setAuthor (java.lang.String Author)
	{
		set_Value (COLUMNNAME_Author, Author);
	}

	/** Get Author.
		@return Author/Creator of the Entity
	  */
	@Override
	public java.lang.String getAuthor () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Author);
	}

	/** Set Kosten.
		@param Cost 
		Cost information
	  */
	@Override
	public void setCost (java.math.BigDecimal Cost)
	{
		set_Value (COLUMNNAME_Cost, Cost);
	}

	/** Get Kosten.
		@return Cost information
	  */
	@Override
	public java.math.BigDecimal getCost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Cost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Beleg Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Beleg Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
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

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
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

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public org.compiere.util.KeyNamePair getKeyNamePair() 
    {
        return new org.compiere.util.KeyNamePair(get_ID(), getName());
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
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order() throws RuntimeException
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
	public org.eevolution.model.I_PP_Order_Node getPP_Order_Node() throws RuntimeException
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

	/** Set Priorität.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	@Override
	public void setPriority (int Priority)
	{
		set_Value (COLUMNNAME_Priority, Integer.valueOf(Priority));
	}

	/** Get Priorität.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	@Override
	public int getPriority () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Priority);
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
	/** Set Publication Status.
		@param PublishStatus 
		Status of Publication
	  */
	@Override
	public void setPublishStatus (java.lang.String PublishStatus)
	{

		set_Value (COLUMNNAME_PublishStatus, PublishStatus);
	}

	/** Get Publication Status.
		@return Status of Publication
	  */
	@Override
	public java.lang.String getPublishStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PublishStatus);
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
			 return Env.ZERO;
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
	public org.compiere.model.I_S_Resource getS_Resource() throws RuntimeException
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
			 return Env.ZERO;
		return bd;
	}

	/** Set Workflow validieren.
		@param ValidateWorkflow Workflow validieren	  */
	@Override
	public void setValidateWorkflow (java.lang.String ValidateWorkflow)
	{
		set_Value (COLUMNNAME_ValidateWorkflow, ValidateWorkflow);
	}

	/** Get Workflow validieren.
		@return Workflow validieren	  */
	@Override
	public java.lang.String getValidateWorkflow () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ValidateWorkflow);
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Valid from including this date (first day)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Gültig bis.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Valid to including this date (last day)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Version.
		@param Version 
		Version of the table definition
	  */
	@Override
	public void setVersion (int Version)
	{
		set_Value (COLUMNNAME_Version, Integer.valueOf(Version));
	}

	/** Get Version.
		@return Version of the table definition
	  */
	@Override
	public int getVersion () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Version);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** 
	 * WorkflowType AD_Reference_ID=108
	 * Reference name: AD_Window Types
	 */
	public static final int WORKFLOWTYPE_AD_Reference_ID=108;
	/** Single Record = S */
	public static final String WORKFLOWTYPE_SingleRecord = "S";
	/** Verwalten = M */
	public static final String WORKFLOWTYPE_Verwalten = "M";
	/** Transaktion = T */
	public static final String WORKFLOWTYPE_Transaktion = "T";
	/** Query Only = Q */
	public static final String WORKFLOWTYPE_QueryOnly = "Q";
	/** Set Workflow Type.
		@param WorkflowType 
		Type of Worflow
	  */
	@Override
	public void setWorkflowType (java.lang.String WorkflowType)
	{

		set_Value (COLUMNNAME_WorkflowType, WorkflowType);
	}

	/** Get Workflow Type.
		@return Type of Worflow
	  */
	@Override
	public java.lang.String getWorkflowType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WorkflowType);
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