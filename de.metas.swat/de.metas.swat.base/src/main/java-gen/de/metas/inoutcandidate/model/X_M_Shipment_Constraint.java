/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Shipment_Constraint
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Shipment_Constraint extends org.compiere.model.PO implements I_M_Shipment_Constraint, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -193814760L;

    /** Standard Constructor */
    public X_M_Shipment_Constraint (Properties ctx, int M_Shipment_Constraint_ID, String trxName)
    {
      super (ctx, M_Shipment_Constraint_ID, trxName);
      /** if (M_Shipment_Constraint_ID == 0)
        {
			setIsDeliveryStop (false); // N
			setM_Shipment_Constraint_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Shipment_Constraint (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner getBill_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setBill_BPartner(org.compiere.model.I_C_BPartner Bill_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class, Bill_BPartner);
	}

	/** Set Rechnungspartner.
		@param Bill_BPartner_ID 
		Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	/** Get Rechnungspartner.
		@return Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Delivery stop.
		@param IsDeliveryStop Delivery stop	  */
	@Override
	public void setIsDeliveryStop (boolean IsDeliveryStop)
	{
		set_Value (COLUMNNAME_IsDeliveryStop, Boolean.valueOf(IsDeliveryStop));
	}

	/** Get Delivery stop.
		@return Delivery stop	  */
	@Override
	public boolean isDeliveryStop () 
	{
		Object oo = get_Value(COLUMNNAME_IsDeliveryStop);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Shipment constraint.
		@param M_Shipment_Constraint_ID Shipment constraint	  */
	@Override
	public void setM_Shipment_Constraint_ID (int M_Shipment_Constraint_ID)
	{
		if (M_Shipment_Constraint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipment_Constraint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipment_Constraint_ID, Integer.valueOf(M_Shipment_Constraint_ID));
	}

	/** Get Shipment constraint.
		@return Shipment constraint	  */
	@Override
	public int getM_Shipment_Constraint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipment_Constraint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Source document.
		@param SourceDoc_Record_ID Source document	  */
	@Override
	public void setSourceDoc_Record_ID (int SourceDoc_Record_ID)
	{
		if (SourceDoc_Record_ID < 1) 
			set_Value (COLUMNNAME_SourceDoc_Record_ID, null);
		else 
			set_Value (COLUMNNAME_SourceDoc_Record_ID, Integer.valueOf(SourceDoc_Record_ID));
	}

	/** Get Source document.
		@return Source document	  */
	@Override
	public int getSourceDoc_Record_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SourceDoc_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getSourceDoc_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SourceDoc_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setSourceDoc_Table(org.compiere.model.I_AD_Table SourceDoc_Table)
	{
		set_ValueFromPO(COLUMNNAME_SourceDoc_Table_ID, org.compiere.model.I_AD_Table.class, SourceDoc_Table);
	}

	/** Set Source document (table).
		@param SourceDoc_Table_ID Source document (table)	  */
	@Override
	public void setSourceDoc_Table_ID (int SourceDoc_Table_ID)
	{
		if (SourceDoc_Table_ID < 1) 
			set_Value (COLUMNNAME_SourceDoc_Table_ID, null);
		else 
			set_Value (COLUMNNAME_SourceDoc_Table_ID, Integer.valueOf(SourceDoc_Table_ID));
	}

	/** Get Source document (table).
		@return Source document (table)	  */
	@Override
	public int getSourceDoc_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SourceDoc_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}