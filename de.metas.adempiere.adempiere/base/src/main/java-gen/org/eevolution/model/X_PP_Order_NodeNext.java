/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Order_NodeNext
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Order_NodeNext extends org.compiere.model.PO implements I_PP_Order_NodeNext, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -769631220L;

    /** Standard Constructor */
    public X_PP_Order_NodeNext (Properties ctx, int PP_Order_NodeNext_ID, String trxName)
    {
      super (ctx, PP_Order_NodeNext_ID, trxName);
      /** if (PP_Order_NodeNext_ID == 0)
        {
			setAD_WF_Node_ID (0);
			setEntityType (null); // U
			setPP_Order_ID (0);
			setPP_Order_Node_ID (0);
			setSeqNo (0); // 10
        } */
    }

    /** Load Constructor */
    public X_PP_Order_NodeNext (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_WF_Node getAD_WF_Next() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Next_ID, org.compiere.model.I_AD_WF_Node.class);
	}

	@Override
	public void setAD_WF_Next(org.compiere.model.I_AD_WF_Node AD_WF_Next)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Next_ID, org.compiere.model.I_AD_WF_Node.class, AD_WF_Next);
	}

	/** Set Nächster Knoten.
		@param AD_WF_Next_ID 
		Next Node in workflow
	  */
	@Override
	public void setAD_WF_Next_ID (int AD_WF_Next_ID)
	{
		if (AD_WF_Next_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Next_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Next_ID, Integer.valueOf(AD_WF_Next_ID));
	}

	/** Get Nächster Knoten.
		@return Next Node in workflow
	  */
	@Override
	public int getAD_WF_Next_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Next_ID);
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

	/** Set Std User Workflow.
		@param IsStdUserWorkflow 
		Standard Manual User Approval Workflow
	  */
	@Override
	public void setIsStdUserWorkflow (boolean IsStdUserWorkflow)
	{
		set_Value (COLUMNNAME_IsStdUserWorkflow, Boolean.valueOf(IsStdUserWorkflow));
	}

	/** Get Std User Workflow.
		@return Standard Manual User Approval Workflow
	  */
	@Override
	public boolean isStdUserWorkflow () 
	{
		Object oo = get_Value(COLUMNNAME_IsStdUserWorkflow);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
	public org.eevolution.model.I_PP_Order_Node getPP_Order_Next() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_Next_ID, org.eevolution.model.I_PP_Order_Node.class);
	}

	@Override
	public void setPP_Order_Next(org.eevolution.model.I_PP_Order_Node PP_Order_Next)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_Next_ID, org.eevolution.model.I_PP_Order_Node.class, PP_Order_Next);
	}

	/** Set Manufacturing Order Activity Next.
		@param PP_Order_Next_ID Manufacturing Order Activity Next	  */
	@Override
	public void setPP_Order_Next_ID (int PP_Order_Next_ID)
	{
		if (PP_Order_Next_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_Next_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_Next_ID, Integer.valueOf(PP_Order_Next_ID));
	}

	/** Get Manufacturing Order Activity Next.
		@return Manufacturing Order Activity Next	  */
	@Override
	public int getPP_Order_Next_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_Next_ID);
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
			set_ValueNoCheck (COLUMNNAME_PP_Order_Node_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_Node_ID, Integer.valueOf(PP_Order_Node_ID));
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

	/** Set Manufacturing Order Activity Next.
		@param PP_Order_NodeNext_ID Manufacturing Order Activity Next	  */
	@Override
	public void setPP_Order_NodeNext_ID (int PP_Order_NodeNext_ID)
	{
		if (PP_Order_NodeNext_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_NodeNext_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_NodeNext_ID, Integer.valueOf(PP_Order_NodeNext_ID));
	}

	/** Get Manufacturing Order Activity Next.
		@return Manufacturing Order Activity Next	  */
	@Override
	public int getPP_Order_NodeNext_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_NodeNext_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Method of ordering records; lowest number comes first
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transition Code.
		@param TransitionCode 
		Code resulting in TRUE of FALSE
	  */
	@Override
	public void setTransitionCode (java.lang.String TransitionCode)
	{
		set_Value (COLUMNNAME_TransitionCode, TransitionCode);
	}

	/** Get Transition Code.
		@return Code resulting in TRUE of FALSE
	  */
	@Override
	public java.lang.String getTransitionCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransitionCode);
	}
}