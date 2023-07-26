/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_ElementValue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_ElementValue extends org.compiere.model.PO implements I_C_ElementValue, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1304206948L;

    /** Standard Constructor */
    public X_C_ElementValue (Properties ctx, int C_ElementValue_ID, String trxName)
    {
      super (ctx, C_ElementValue_ID, trxName);
      /** if (C_ElementValue_ID == 0)
        {
			setAccountSign (null); // N
			setAccountType (null); // E
			setC_Element_ID (0);
			setC_ElementValue_ID (0);
			setIsAutoTaxAccount (false); // N
			setIsMandatoryActivity (false); // N
			setIsSummary (false);
			setName (null);
			setPostActual (true); // Y
			setPostBudget (true); // Y
			setPostEncumbrance (true); // Y
			setPostStatistical (true); // Y
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_C_ElementValue (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Kostenstelle.
		@param C_Activity_ID 
		Kostenstelle
	  */
	@Override
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Kostenstelle.
		@return Kostenstelle
	  */
	@Override
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bankverbindung des Geschäftspartners
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bankverbindung des Geschäftspartners
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Element getC_Element()
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
			set_ValueNoCheck (COLUMNNAME_C_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Element_ID, Integer.valueOf(C_Element_ID));
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

	/** Set Kontenart.
		@param C_ElementValue_ID 
		Account Element
	  */
	@Override
	public void setC_ElementValue_ID (int C_ElementValue_ID)
	{
		if (C_ElementValue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ElementValue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ElementValue_ID, Integer.valueOf(C_ElementValue_ID));
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

	/** Set Steuer.
		@param C_Tax_ID 
		Steuerart
	  */
	@Override
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Steuer.
		@return Steuerart
	  */
	@Override
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
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

	/** Set Automatic tax account.
		@param IsAutoTaxAccount 
		If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	  */
	@Override
	public void setIsAutoTaxAccount (boolean IsAutoTaxAccount)
	{
		set_Value (COLUMNNAME_IsAutoTaxAccount, Boolean.valueOf(IsAutoTaxAccount));
	}

	/** Get Automatic tax account.
		@return If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	  */
	@Override
	public boolean isAutoTaxAccount () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoTaxAccount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bankkonto.
		@param IsBankAccount 
		Indicates if this is the Bank Account
	  */
	@Override
	public void setIsBankAccount (boolean IsBankAccount)
	{
		set_Value (COLUMNNAME_IsBankAccount, Boolean.valueOf(IsBankAccount));
	}

	/** Get Bankkonto.
		@return Indicates if this is the Bank Account
	  */
	@Override
	public boolean isBankAccount () 
	{
		Object oo = get_Value(COLUMNNAME_IsBankAccount);
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

	/** Set Foreign Currency Account.
		@param IsForeignCurrency 
		Balances in foreign currency accounts are held in the nominated currency
	  */
	@Override
	public void setIsForeignCurrency (boolean IsForeignCurrency)
	{
		set_Value (COLUMNNAME_IsForeignCurrency, Boolean.valueOf(IsForeignCurrency));
	}

	/** Get Foreign Currency Account.
		@return Balances in foreign currency accounts are held in the nominated currency
	  */
	@Override
	public boolean isForeignCurrency () 
	{
		Object oo = get_Value(COLUMNNAME_IsForeignCurrency);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kostenstelle ist Pflichtangabe.
		@param IsMandatoryActivity Kostenstelle ist Pflichtangabe	  */
	@Override
	public void setIsMandatoryActivity (boolean IsMandatoryActivity)
	{
		set_Value (COLUMNNAME_IsMandatoryActivity, Boolean.valueOf(IsMandatoryActivity));
	}

	/** Get Kostenstelle ist Pflichtangabe.
		@return Kostenstelle ist Pflichtangabe	  */
	@Override
	public boolean isMandatoryActivity () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatoryActivity);
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

	@Override
	public org.compiere.model.I_C_ElementValue getParent()
	{
		return get_ValueAsPO(COLUMNNAME_Parent_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setParent(org.compiere.model.I_C_ElementValue Parent)
	{
		set_ValueFromPO(COLUMNNAME_Parent_ID, org.compiere.model.I_C_ElementValue.class, Parent);
	}

	/** Set Parent.
		@param Parent_ID 
		Parent of Entity
	  */
	@Override
	public void setParent_ID (int Parent_ID)
	{
		if (Parent_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Parent_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Parent_ID, Integer.valueOf(Parent_ID));
	}

	/** Get Parent.
		@return Parent of Entity
	  */
	@Override
	public int getParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_ValueNoCheck (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
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

	/** Set Gültig ab.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Valid from including this date (first day)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Gültig bis.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	@Override
	public void setValidTo (java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Valid to including this date (last day)
	  */
	@Override
	public java.sql.Timestamp getValidTo () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
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