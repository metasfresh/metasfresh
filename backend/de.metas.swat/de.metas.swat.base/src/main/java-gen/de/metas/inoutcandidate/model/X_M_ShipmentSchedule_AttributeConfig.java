/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShipmentSchedule_AttributeConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ShipmentSchedule_AttributeConfig extends org.compiere.model.PO implements I_M_ShipmentSchedule_AttributeConfig, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1460016934L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule_AttributeConfig (Properties ctx, int M_ShipmentSchedule_AttributeConfig_ID, String trxName)
    {
      super (ctx, M_ShipmentSchedule_AttributeConfig_ID, trxName);
      /** if (M_ShipmentSchedule_AttributeConfig_ID == 0)
        {
			setM_IolCandHandler_ID (0);
			setM_ShipmentSchedule_AttributeConfig_ID (0);
			setOnlyIfInReferencedASI (false);
        } */
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule_AttributeConfig (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class);
	}

	@Override
	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class, M_Attribute);
	}

	/** Set Merkmal.
		@param M_Attribute_ID 
		Produkt-Merkmal
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Merkmal.
		@return Produkt-Merkmal
	  */
	@Override
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
	}

	@Override
	public void setM_IolCandHandler(de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler)
	{
		set_ValueFromPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class, M_IolCandHandler);
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

	/** Set M_ShipmentSchedule_AttributeConfig.
		@param M_ShipmentSchedule_AttributeConfig_ID M_ShipmentSchedule_AttributeConfig	  */
	@Override
	public void setM_ShipmentSchedule_AttributeConfig_ID (int M_ShipmentSchedule_AttributeConfig_ID)
	{
		if (M_ShipmentSchedule_AttributeConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_AttributeConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_AttributeConfig_ID, Integer.valueOf(M_ShipmentSchedule_AttributeConfig_ID));
	}

	/** Get M_ShipmentSchedule_AttributeConfig.
		@return M_ShipmentSchedule_AttributeConfig	  */
	@Override
	public int getM_ShipmentSchedule_AttributeConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_AttributeConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Nur falls in ref. Datensatz.
		@param OnlyIfInReferencedASI 
		Das Attribut wird nur berücksichtigt, wenn es in der Merkmalssatzinstanz des referenzierenden Datensatzes vorkommt
	  */
	@Override
	public void setOnlyIfInReferencedASI (boolean OnlyIfInReferencedASI)
	{
		set_Value (COLUMNNAME_OnlyIfInReferencedASI, Boolean.valueOf(OnlyIfInReferencedASI));
	}

	/** Get Nur falls in ref. Datensatz.
		@return Das Attribut wird nur berücksichtigt, wenn es in der Merkmalssatzinstanz des referenzierenden Datensatzes vorkommt
	  */
	@Override
	public boolean isOnlyIfInReferencedASI () 
	{
		Object oo = get_Value(COLUMNNAME_OnlyIfInReferencedASI);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}