/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_WF_Node_Para
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_WF_Node_Para extends org.compiere.model.PO implements I_AD_WF_Node_Para, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 662604973L;

    /** Standard Constructor */
    public X_AD_WF_Node_Para (Properties ctx, int AD_WF_Node_Para_ID, String trxName)
    {
      super (ctx, AD_WF_Node_Para_ID, trxName);
      /** if (AD_WF_Node_Para_ID == 0)
        {
			setAD_WF_Node_ID (0);
			setAD_WF_Node_Para_ID (0);
			setEntityType (null);
// U
        } */
    }

    /** Load Constructor */
    public X_AD_WF_Node_Para (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Process_Para getAD_Process_Para() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_Para_ID, org.compiere.model.I_AD_Process_Para.class);
	}

	@Override
	public void setAD_Process_Para(org.compiere.model.I_AD_Process_Para AD_Process_Para)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_Para_ID, org.compiere.model.I_AD_Process_Para.class, AD_Process_Para);
	}

	/** Set Prozess-Parameter.
		@param AD_Process_Para_ID Prozess-Parameter	  */
	@Override
	public void setAD_Process_Para_ID (int AD_Process_Para_ID)
	{
		if (AD_Process_Para_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_Para_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_Para_ID, Integer.valueOf(AD_Process_Para_ID));
	}

	/** Get Prozess-Parameter.
		@return Prozess-Parameter	  */
	@Override
	public int getAD_Process_Para_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_Para_ID);
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
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
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

	/** Set Workflow Node Parameter.
		@param AD_WF_Node_Para_ID 
		Workflow Node Execution Parameter
	  */
	@Override
	public void setAD_WF_Node_Para_ID (int AD_WF_Node_Para_ID)
	{
		if (AD_WF_Node_Para_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_Para_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_Para_ID, Integer.valueOf(AD_WF_Node_Para_ID));
	}

	/** Get Workflow Node Parameter.
		@return Workflow Node Execution Parameter
	  */
	@Override
	public int getAD_WF_Node_Para_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Node_Para_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Attribute Name.
		@param AttributeName 
		Name of the Attribute
	  */
	@Override
	public void setAttributeName (java.lang.String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	/** Get Attribute Name.
		@return Name of the Attribute
	  */
	@Override
	public java.lang.String getAttributeName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeName);
	}

	/** Set Merkmals-Wert.
		@param AttributeValue 
		Value of the Attribute
	  */
	@Override
	public void setAttributeValue (java.lang.String AttributeValue)
	{
		set_Value (COLUMNNAME_AttributeValue, AttributeValue);
	}

	/** Get Merkmals-Wert.
		@return Value of the Attribute
	  */
	@Override
	public java.lang.String getAttributeValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeValue);
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
}