/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_BestellungAntwortPosition
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_BestellungAntwortPosition extends org.compiere.model.PO implements I_MSV3_BestellungAntwortPosition, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2142401190L;

    /** Standard Constructor */
    public X_MSV3_BestellungAntwortPosition (Properties ctx, int MSV3_BestellungAntwortPosition_ID, String trxName)
    {
      super (ctx, MSV3_BestellungAntwortPosition_ID, trxName);
      /** if (MSV3_BestellungAntwortPosition_ID == 0)
        {
			setMSV3_BestellungAntwortPosition_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_BestellungAntwortPosition (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Purchase candidate.
		@param C_PurchaseCandidate_ID Purchase candidate	  */
	@Override
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID)
	{
		if (C_PurchaseCandidate_ID < 1) 
			set_Value (COLUMNNAME_C_PurchaseCandidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_PurchaseCandidate_ID, Integer.valueOf(C_PurchaseCandidate_ID));
	}

	/** Get Purchase candidate.
		@return Purchase candidate	  */
	@Override
	public int getC_PurchaseCandidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PurchaseCandidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MSV3_BestellLiefervorgabe AD_Reference_ID=540821
	 * Reference name: MSV3_Liefervorgabe
	 */
	public static final int MSV3_BESTELLLIEFERVORGABE_AD_Reference_ID=540821;
	/** Normal = Normal */
	public static final String MSV3_BESTELLLIEFERVORGABE_Normal = "Normal";
	/** MaxVerbund = MaxVerbund */
	public static final String MSV3_BESTELLLIEFERVORGABE_MaxVerbund = "MaxVerbund";
	/** MaxNachlieferung = MaxNachlieferung */
	public static final String MSV3_BESTELLLIEFERVORGABE_MaxNachlieferung = "MaxNachlieferung";
	/** MaxDispo = MaxDispo */
	public static final String MSV3_BESTELLLIEFERVORGABE_MaxDispo = "MaxDispo";
	/** Set BestellLiefervorgabe.
		@param MSV3_BestellLiefervorgabe BestellLiefervorgabe	  */
	@Override
	public void setMSV3_BestellLiefervorgabe (java.lang.String MSV3_BestellLiefervorgabe)
	{

		set_Value (COLUMNNAME_MSV3_BestellLiefervorgabe, MSV3_BestellLiefervorgabe);
	}

	/** Get BestellLiefervorgabe.
		@return BestellLiefervorgabe	  */
	@Override
	public java.lang.String getMSV3_BestellLiefervorgabe () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_BestellLiefervorgabe);
	}

	/** Set BestellMenge.
		@param MSV3_BestellMenge BestellMenge	  */
	@Override
	public void setMSV3_BestellMenge (int MSV3_BestellMenge)
	{
		set_Value (COLUMNNAME_MSV3_BestellMenge, Integer.valueOf(MSV3_BestellMenge));
	}

	/** Get BestellMenge.
		@return BestellMenge	  */
	@Override
	public int getMSV3_BestellMenge () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellMenge);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BestellPzn.
		@param MSV3_BestellPzn BestellPzn	  */
	@Override
	public void setMSV3_BestellPzn (java.lang.String MSV3_BestellPzn)
	{
		set_Value (COLUMNNAME_MSV3_BestellPzn, MSV3_BestellPzn);
	}

	/** Get BestellPzn.
		@return BestellPzn	  */
	@Override
	public java.lang.String getMSV3_BestellPzn () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_BestellPzn);
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwortAuftrag getMSV3_BestellungAntwortAuftrag() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_BestellungAntwortAuftrag_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwortAuftrag.class);
	}

	@Override
	public void setMSV3_BestellungAntwortAuftrag(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwortAuftrag MSV3_BestellungAntwortAuftrag)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_BestellungAntwortAuftrag_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwortAuftrag.class, MSV3_BestellungAntwortAuftrag);
	}

	/** Set MSV3_BestellungAntwortAuftrag.
		@param MSV3_BestellungAntwortAuftrag_ID MSV3_BestellungAntwortAuftrag	  */
	@Override
	public void setMSV3_BestellungAntwortAuftrag_ID (int MSV3_BestellungAntwortAuftrag_ID)
	{
		if (MSV3_BestellungAntwortAuftrag_ID < 1) 
			set_Value (COLUMNNAME_MSV3_BestellungAntwortAuftrag_ID, null);
		else 
			set_Value (COLUMNNAME_MSV3_BestellungAntwortAuftrag_ID, Integer.valueOf(MSV3_BestellungAntwortAuftrag_ID));
	}

	/** Get MSV3_BestellungAntwortAuftrag.
		@return MSV3_BestellungAntwortAuftrag	  */
	@Override
	public int getMSV3_BestellungAntwortAuftrag_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungAntwortAuftrag_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MSV3_BestellungAntwortPosition.
		@param MSV3_BestellungAntwortPosition_ID MSV3_BestellungAntwortPosition	  */
	@Override
	public void setMSV3_BestellungAntwortPosition_ID (int MSV3_BestellungAntwortPosition_ID)
	{
		if (MSV3_BestellungAntwortPosition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAntwortPosition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAntwortPosition_ID, Integer.valueOf(MSV3_BestellungAntwortPosition_ID));
	}

	/** Get MSV3_BestellungAntwortPosition.
		@return MSV3_BestellungAntwortPosition	  */
	@Override
	public int getMSV3_BestellungAntwortPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungAntwortPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution getMSV3_BestellungSubstitution() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_BestellungSubstitution_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution.class);
	}

	@Override
	public void setMSV3_BestellungSubstitution(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution MSV3_BestellungSubstitution)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_BestellungSubstitution_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Substitution.class, MSV3_BestellungSubstitution);
	}

	/** Set BestellungSubstitution.
		@param MSV3_BestellungSubstitution_ID BestellungSubstitution	  */
	@Override
	public void setMSV3_BestellungSubstitution_ID (int MSV3_BestellungSubstitution_ID)
	{
		if (MSV3_BestellungSubstitution_ID < 1) 
			set_Value (COLUMNNAME_MSV3_BestellungSubstitution_ID, null);
		else 
			set_Value (COLUMNNAME_MSV3_BestellungSubstitution_ID, Integer.valueOf(MSV3_BestellungSubstitution_ID));
	}

	/** Get BestellungSubstitution.
		@return BestellungSubstitution	  */
	@Override
	public int getMSV3_BestellungSubstitution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungSubstitution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}