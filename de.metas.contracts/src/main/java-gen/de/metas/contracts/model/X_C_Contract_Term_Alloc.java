/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Contract_Term_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Contract_Term_Alloc extends org.compiere.model.PO implements I_C_Contract_Term_Alloc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1640019780L;

    /** Standard Constructor */
    public X_C_Contract_Term_Alloc (Properties ctx, int C_Contract_Term_Alloc_ID, String trxName)
    {
      super (ctx, C_Contract_Term_Alloc_ID, trxName);
      /** if (C_Contract_Term_Alloc_ID == 0)
        {
			setC_Contract_Term_Alloc_ID (0);
			setC_Flatrate_Term_ID (0);
			setC_OLCand_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Contract_Term_Alloc (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	/** Set Prozess-Instanz.
		@param AD_PInstance_ID 
		Instanz eines Prozesses
	  */
	@Override
	public void setAD_PInstance_ID (int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PInstance_ID, Integer.valueOf(AD_PInstance_ID));
	}

	/** Get Prozess-Instanz.
		@return Instanz eines Prozesses
	  */
	@Override
	public int getAD_PInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auftragskandidat - Laufender Vertrag.
		@param C_Contract_Term_Alloc_ID Auftragskandidat - Laufender Vertrag	  */
	@Override
	public void setC_Contract_Term_Alloc_ID (int C_Contract_Term_Alloc_ID)
	{
		if (C_Contract_Term_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Contract_Term_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Contract_Term_Alloc_ID, Integer.valueOf(C_Contract_Term_Alloc_ID));
	}

	/** Get Auftragskandidat - Laufender Vertrag.
		@return Auftragskandidat - Laufender Vertrag	  */
	@Override
	public int getC_Contract_Term_Alloc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Contract_Term_Alloc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	/** Set Pauschale - Vertragsperiode.
		@param C_Flatrate_Term_ID Pauschale - Vertragsperiode	  */
	@Override
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/** Get Pauschale - Vertragsperiode.
		@return Pauschale - Vertragsperiode	  */
	@Override
	public int getC_Flatrate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.ordercandidate.model.I_C_OLCand getC_OLCand() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OLCand_ID, de.metas.ordercandidate.model.I_C_OLCand.class);
	}

	@Override
	public void setC_OLCand(de.metas.ordercandidate.model.I_C_OLCand C_OLCand)
	{
		set_ValueFromPO(COLUMNNAME_C_OLCand_ID, de.metas.ordercandidate.model.I_C_OLCand.class, C_OLCand);
	}

	/** Set Auftragskandidat.
		@param C_OLCand_ID Auftragskandidat	  */
	@Override
	public void setC_OLCand_ID (int C_OLCand_ID)
	{
		if (C_OLCand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_ID, Integer.valueOf(C_OLCand_ID));
	}

	/** Get Auftragskandidat.
		@return Auftragskandidat	  */
	@Override
	public int getC_OLCand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OLCand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		throw new IllegalArgumentException ("DocStatus is virtual column");	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}
}