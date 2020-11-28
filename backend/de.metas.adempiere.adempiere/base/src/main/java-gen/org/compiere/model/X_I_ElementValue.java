/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_ElementValue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_ElementValue extends org.compiere.model.PO implements I_I_ElementValue, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 837100963L;

    /** Standard Constructor */
    public X_I_ElementValue (Properties ctx, int I_ElementValue_ID, String trxName)
    {
      super (ctx, I_ElementValue_ID, trxName);
      /** if (I_ElementValue_ID == 0)
        {
			setI_ElementValue_ID (0);
			setI_IsImported (false);
        } */
    }

    /** Load Constructor */
    public X_I_ElementValue (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * AccountSign AD_Reference_ID=118
	 * Reference name: C_ElementValue Account Sign
	 */
	public static final int ACCOUNTSIGN_AD_Reference_ID=118;
	/** Natural = N */
	public static final String ACCOUNTSIGN_Natural = "N";
	/** Debit = D */
	public static final String ACCOUNTSIGN_Debit = "D";
	/** Credit = C */
	public static final String ACCOUNTSIGN_Credit = "C";
	/** Set Kontovorzeichen.
		@param AccountSign 
		Indicates the Natural Sign of the Account as a Debit or Credit
	  */
	@Override
	public void setAccountSign (java.lang.String AccountSign)
	{

		set_Value (COLUMNNAME_AccountSign, AccountSign);
	}

	/** Get Kontovorzeichen.
		@return Indicates the Natural Sign of the Account as a Debit or Credit
	  */
	@Override
	public java.lang.String getAccountSign () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountSign);
	}

	/** 
	 * AccountType AD_Reference_ID=117
	 * Reference name: C_ElementValue AccountType
	 */
	public static final int ACCOUNTTYPE_AD_Reference_ID=117;
	/** Asset = A */
	public static final String ACCOUNTTYPE_Asset = "A";
	/** Liability = L */
	public static final String ACCOUNTTYPE_Liability = "L";
	/** Revenue = R */
	public static final String ACCOUNTTYPE_Revenue = "R";
	/** Expense = E */
	public static final String ACCOUNTTYPE_Expense = "E";
	/** OwnerSEquity = O */
	public static final String ACCOUNTTYPE_OwnerSEquity = "O";
	/** Memo = M */
	public static final String ACCOUNTTYPE_Memo = "M";
	/** Set Kontenart.
		@param AccountType 
		Indicates the type of account
	  */
	@Override
	public void setAccountType (java.lang.String AccountType)
	{

		set_Value (COLUMNNAME_AccountType, AccountType);
	}

	/** Get Kontenart.
		@return Indicates the type of account
	  */
	@Override
	public java.lang.String getAccountType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountType);
	}

	@Override
	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	/** Set Spalte.
		@param AD_Column_ID 
		Column in the table
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Spalte.
		@return Column in the table
	  */
	@Override
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Element getC_Element() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Element_ID, org.compiere.model.I_C_Element.class);
	}

	@Override
	public void setC_Element(org.compiere.model.I_C_Element C_Element)
	{
		set_ValueFromPO(COLUMNNAME_C_Element_ID, org.compiere.model.I_C_Element.class, C_Element);
	}

	/** Set Element.
		@param C_Element_ID 
		Accounting Element
	  */
	@Override
	public void setC_Element_ID (int C_Element_ID)
	{
		if (C_Element_ID < 1) 
			set_Value (COLUMNNAME_C_Element_ID, null);
		else 
			set_Value (COLUMNNAME_C_Element_ID, Integer.valueOf(C_Element_ID));
	}

	/** Get Element.
		@return Accounting Element
	  */
	@Override
	public int getC_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ElementValue getC_ElementValue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ElementValue_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setC_ElementValue(org.compiere.model.I_C_ElementValue C_ElementValue)
	{
		set_ValueFromPO(COLUMNNAME_C_ElementValue_ID, org.compiere.model.I_C_ElementValue.class, C_ElementValue);
	}

	/** Set Kontenart.
		@param C_ElementValue_ID 
		Account Element
	  */
	@Override
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_Value (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
	}

	/** Get Kontenart.
		@return Account Element
	  */
	@Override
	public int getC_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Standard-Konto.
		@param Default_Account 
		Name of the Default Account Column
	  */
	@Override
	public void setDefault_Account (java.lang.String Default_Account)
	{
		set_Value (COLUMNNAME_Default_Account, Default_Account);
	}

	/** Get Standard-Konto.
		@return Name of the Default Account Column
	  */
	@Override
	public java.lang.String getDefault_Account () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Default_Account);
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

	/** Set Element Name.
		@param ElementName 
		Name of the Element
	  */
	@Override
	public void setElementName (java.lang.String ElementName)
	{
		set_Value (COLUMNNAME_ElementName, ElementName);
	}

	/** Get Element Name.
		@return Name of the Element
	  */
	@Override
	public java.lang.String getElementName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ElementName);
	}

	/** Set Import - Kontendefinition.
		@param I_ElementValue_ID 
		Import Account Value
	  */
	@Override
	public void setI_ElementValue_ID (int I_ElementValue_ID)
	{
		if (I_ElementValue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_ElementValue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_ElementValue_ID, Integer.valueOf(I_ElementValue_ID));
	}

	/** Get Import - Kontendefinition.
		@return Import Account Value
	  */
	@Override
	public int getI_ElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_ElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Messages generated from import process
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Importiert.
		@param I_IsImported 
		Has this import been processed
	  */
	@Override
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Importiert.
		@return Has this import been processed
	  */
	@Override
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Belegartgesteuert.
		@param IsDocControlled 
		Control account - If an account is controlled by a document, you cannot post manually to it
	  */
	@Override
	public void setIsDocControlled (boolean IsDocControlled)
	{
		set_Value (COLUMNNAME_IsDocControlled, Boolean.valueOf(IsDocControlled));
	}

	/** Get Belegartgesteuert.
		@return Control account - If an account is controlled by a document, you cannot post manually to it
	  */
	@Override
	public boolean isDocControlled () 
	{
		Object oo = get_Value(COLUMNNAME_IsDocControlled);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zusammenfassungseintrag.
		@param IsSummary 
		This is a summary entity
	  */
	@Override
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Zusammenfassungseintrag.
		@return This is a summary entity
	  */
	@Override
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	@Override
	public org.compiere.model.I_C_ElementValue getParentElementValue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_ParentElementValue_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setParentElementValue(org.compiere.model.I_C_ElementValue ParentElementValue)
	{
		set_ValueFromPO(COLUMNNAME_ParentElementValue_ID, org.compiere.model.I_C_ElementValue.class, ParentElementValue);
	}

	/** Set Übergeordnetes Konto.
		@param ParentElementValue_ID 
		The parent (summary) account
	  */
	@Override
	public void setParentElementValue_ID (int ParentElementValue_ID)
	{
		if (ParentElementValue_ID < 1) 
			set_Value (COLUMNNAME_ParentElementValue_ID, null);
		else 
			set_Value (COLUMNNAME_ParentElementValue_ID, Integer.valueOf(ParentElementValue_ID));
	}

	/** Get Übergeordnetes Konto.
		@return The parent (summary) account
	  */
	@Override
	public int getParentElementValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ParentElementValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Schlüssel Übergeordnetes Konto .
		@param ParentValue 
		Key if the Parent
	  */
	@Override
	public void setParentValue (java.lang.String ParentValue)
	{
		set_Value (COLUMNNAME_ParentValue, ParentValue);
	}

	/** Get Schlüssel Übergeordnetes Konto .
		@return Key if the Parent
	  */
	@Override
	public java.lang.String getParentValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ParentValue);
	}

	/** Set Buchen "Ist".
		@param PostActual 
		Actual Values can be posted
	  */
	@Override
	public void setPostActual (boolean PostActual)
	{
		set_Value (COLUMNNAME_PostActual, Boolean.valueOf(PostActual));
	}

	/** Get Buchen "Ist".
		@return Actual Values can be posted
	  */
	@Override
	public boolean isPostActual () 
	{
		Object oo = get_Value(COLUMNNAME_PostActual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Buchen "Budget".
		@param PostBudget 
		Budget values can be posted
	  */
	@Override
	public void setPostBudget (boolean PostBudget)
	{
		set_Value (COLUMNNAME_PostBudget, Boolean.valueOf(PostBudget));
	}

	/** Get Buchen "Budget".
		@return Budget values can be posted
	  */
	@Override
	public boolean isPostBudget () 
	{
		Object oo = get_Value(COLUMNNAME_PostBudget);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Buchen "Reservierung".
		@param PostEncumbrance 
		Post commitments to this account
	  */
	@Override
	public void setPostEncumbrance (boolean PostEncumbrance)
	{
		set_Value (COLUMNNAME_PostEncumbrance, Boolean.valueOf(PostEncumbrance));
	}

	/** Get Buchen "Reservierung".
		@return Post commitments to this account
	  */
	@Override
	public boolean isPostEncumbrance () 
	{
		Object oo = get_Value(COLUMNNAME_PostEncumbrance);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Buchen "statistisch".
		@param PostStatistical 
		Post statistical quantities to this account?
	  */
	@Override
	public void setPostStatistical (boolean PostStatistical)
	{
		set_Value (COLUMNNAME_PostStatistical, Boolean.valueOf(PostStatistical));
	}

	/** Get Buchen "statistisch".
		@return Post statistical quantities to this account?
	  */
	@Override
	public boolean isPostStatistical () 
	{
		Object oo = get_Value(COLUMNNAME_PostStatistical);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
}