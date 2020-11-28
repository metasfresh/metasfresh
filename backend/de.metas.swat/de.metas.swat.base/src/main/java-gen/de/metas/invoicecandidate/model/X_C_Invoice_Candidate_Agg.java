/** Generated Model - DO NOT CHANGE */
package de.metas.invoicecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Candidate_Agg
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice_Candidate_Agg extends org.compiere.model.PO implements I_C_Invoice_Candidate_Agg, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1517917749L;

    /** Standard Constructor */
    public X_C_Invoice_Candidate_Agg (Properties ctx, int C_Invoice_Candidate_Agg_ID, String trxName)
    {
      super (ctx, C_Invoice_Candidate_Agg_ID, trxName);
      /** if (C_Invoice_Candidate_Agg_ID == 0)
        {
			setC_Invoice_Candidate_Agg_ID (0);
			setName (null);
			setSeqNo (0);
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Candidate_Agg (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Aggregator.
		@param C_Invoice_Candidate_Agg_ID 
		Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	  */
	@Override
	public void setC_Invoice_Candidate_Agg_ID (int C_Invoice_Candidate_Agg_ID)
	{
		if (C_Invoice_Candidate_Agg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Agg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Agg_ID, Integer.valueOf(C_Invoice_Candidate_Agg_ID));
	}

	/** Get Aggregator.
		@return Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	  */
	@Override
	public int getC_Invoice_Candidate_Agg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_Agg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	@Override
	public de.metas.invoicecandidate.model.I_M_ProductGroup getM_ProductGroup()
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductGroup_ID, de.metas.invoicecandidate.model.I_M_ProductGroup.class);
	}

	@Override
	public void setM_ProductGroup(de.metas.invoicecandidate.model.I_M_ProductGroup M_ProductGroup)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductGroup_ID, de.metas.invoicecandidate.model.I_M_ProductGroup.class, M_ProductGroup);
	}

	/** Set Produktgruppe.
		@param M_ProductGroup_ID 
		Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	  */
	@Override
	public void setM_ProductGroup_ID (int M_ProductGroup_ID)
	{
		if (M_ProductGroup_ID < 1) 
			set_Value (COLUMNNAME_M_ProductGroup_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductGroup_ID, Integer.valueOf(M_ProductGroup_ID));
	}

	/** Get Produktgruppe.
		@return Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	  */
	@Override
	public int getM_ProductGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}