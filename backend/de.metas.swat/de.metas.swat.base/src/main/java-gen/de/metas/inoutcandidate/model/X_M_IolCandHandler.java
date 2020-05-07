/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_IolCandHandler
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_IolCandHandler extends org.compiere.model.PO implements I_M_IolCandHandler, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 81926115L;

    /** Standard Constructor */
    public X_M_IolCandHandler (Properties ctx, int M_IolCandHandler_ID, String trxName)
    {
      super (ctx, M_IolCandHandler_ID, trxName);
      /** if (M_IolCandHandler_ID == 0)
        {
			setClassname (null);
			setM_IolCandHandler_ID (0);
			setTableName (null);
        } */
    }

    /** Load Constructor */
    public X_M_IolCandHandler (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Java-Klasse.
		@param Classname Java-Klasse	  */
	@Override
	public void setClassname (java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	/** Get Java-Klasse.
		@return Java-Klasse	  */
	@Override
	public java.lang.String getClassname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Classname);
	}

	/** Set M_IolCandHandler.
		@param M_IolCandHandler_ID M_IolCandHandler	  */
	@Override
	public void setM_IolCandHandler_ID (int M_IolCandHandler_ID)
	{
		if (M_IolCandHandler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_ID, Integer.valueOf(M_IolCandHandler_ID));
	}

	/** Get M_IolCandHandler.
		@return M_IolCandHandler	  */
	@Override
	public int getM_IolCandHandler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_IolCandHandler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name der DB-Tabelle.
		@param TableName Name der DB-Tabelle	  */
	@Override
	public void setTableName (java.lang.String TableName)
	{
		set_Value (COLUMNNAME_TableName, TableName);
	}

	/** Get Name der DB-Tabelle.
		@return Name der DB-Tabelle	  */
	@Override
	public java.lang.String getTableName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TableName);
	}
}