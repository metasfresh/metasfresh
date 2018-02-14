/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_BestellungPosition
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_BestellungPosition extends org.compiere.model.PO implements I_MSV3_BestellungPosition, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 561841583L;

    /** Standard Constructor */
    public X_MSV3_BestellungPosition (Properties ctx, int MSV3_BestellungPosition_ID, String trxName)
    {
      super (ctx, MSV3_BestellungPosition_ID, trxName);
      /** if (MSV3_BestellungPosition_ID == 0)
        {
			setMSV3_BestellungAuftrag_ID (0);
			setMSV3_BestellungPosition_ID (0);
			setMSV3_Liefervorgabe (null);
        } */
    }

    /** Load Constructor */
    public X_MSV3_BestellungPosition (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag getMSV3_BestellungAuftrag() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_BestellungAuftrag_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag.class);
	}

	@Override
	public void setMSV3_BestellungAuftrag(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag MSV3_BestellungAuftrag)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_BestellungAuftrag_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAuftrag.class, MSV3_BestellungAuftrag);
	}

	/** Set MSV3_BestellungAuftrag.
		@param MSV3_BestellungAuftrag_ID MSV3_BestellungAuftrag	  */
	@Override
	public void setMSV3_BestellungAuftrag_ID (int MSV3_BestellungAuftrag_ID)
	{
		if (MSV3_BestellungAuftrag_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAuftrag_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAuftrag_ID, Integer.valueOf(MSV3_BestellungAuftrag_ID));
	}

	/** Get MSV3_BestellungAuftrag.
		@return MSV3_BestellungAuftrag	  */
	@Override
	public int getMSV3_BestellungAuftrag_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungAuftrag_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MSV3_BestellungPosition.
		@param MSV3_BestellungPosition_ID MSV3_BestellungPosition	  */
	@Override
	public void setMSV3_BestellungPosition_ID (int MSV3_BestellungPosition_ID)
	{
		if (MSV3_BestellungPosition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungPosition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungPosition_ID, Integer.valueOf(MSV3_BestellungPosition_ID));
	}

	/** Get MSV3_BestellungPosition.
		@return MSV3_BestellungPosition	  */
	@Override
	public int getMSV3_BestellungPosition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungPosition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MSV3_Liefervorgabe AD_Reference_ID=540821
	 * Reference name: MSV3_Liefervorgabe
	 */
	public static final int MSV3_LIEFERVORGABE_AD_Reference_ID=540821;
	/** Normal = Normal */
	public static final String MSV3_LIEFERVORGABE_Normal = "Normal";
	/** MaxVerbund = MaxVerbund */
	public static final String MSV3_LIEFERVORGABE_MaxVerbund = "MaxVerbund";
	/** MaxNachlieferung = MaxNachlieferung */
	public static final String MSV3_LIEFERVORGABE_MaxNachlieferung = "MaxNachlieferung";
	/** MaxDispo = MaxDispo */
	public static final String MSV3_LIEFERVORGABE_MaxDispo = "MaxDispo";
	/** Set Liefervorgabe.
		@param MSV3_Liefervorgabe Liefervorgabe	  */
	@Override
	public void setMSV3_Liefervorgabe (java.lang.String MSV3_Liefervorgabe)
	{

		set_Value (COLUMNNAME_MSV3_Liefervorgabe, MSV3_Liefervorgabe);
	}

	/** Get Liefervorgabe.
		@return Liefervorgabe	  */
	@Override
	public java.lang.String getMSV3_Liefervorgabe () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Liefervorgabe);
	}

	/** Set MSV3_Menge.
		@param MSV3_Menge MSV3_Menge	  */
	@Override
	public void setMSV3_Menge (int MSV3_Menge)
	{
		set_Value (COLUMNNAME_MSV3_Menge, Integer.valueOf(MSV3_Menge));
	}

	/** Get MSV3_Menge.
		@return MSV3_Menge	  */
	@Override
	public int getMSV3_Menge () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Menge);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MSV3_Pzn.
		@param MSV3_Pzn MSV3_Pzn	  */
	@Override
	public void setMSV3_Pzn (java.lang.String MSV3_Pzn)
	{
		set_Value (COLUMNNAME_MSV3_Pzn, MSV3_Pzn);
	}

	/** Get MSV3_Pzn.
		@return MSV3_Pzn	  */
	@Override
	public java.lang.String getMSV3_Pzn () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Pzn);
	}
}