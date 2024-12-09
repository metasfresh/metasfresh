<<<<<<< HEAD
/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_POS
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_POS extends org.compiere.model.PO implements I_C_POS, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1749933333L;

    /** Standard Constructor */
    public X_C_POS (Properties ctx, int C_POS_ID, String trxName)
    {
      super (ctx, C_POS_ID, trxName);
      /** if (C_POS_ID == 0)
        {
			setC_CashBook_ID (0);
			setC_POS_ID (0);
			setIsModifyPrice (false);
// N
			setM_PriceList_ID (0);
			setM_Warehouse_ID (0);
			setName (null);
			setSalesRep_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_POS (Properties ctx, ResultSet rs, String trxName)
=======
// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS extends org.compiere.model.PO implements I_C_POS, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2043683520L;

    /** Standard Constructor */
    public X_C_POS (final Properties ctx, final int C_POS_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }

<<<<<<< HEAD
    /** AccessLevel
      * @return 2 - Client 
      */
    @Override
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_POS[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Auto Logout Delay.
		@param AutoLogoutDelay 
		Automatically logout if terminal inactive for this period
	  */
	@Override
	public void setAutoLogoutDelay (int AutoLogoutDelay)
	{
		set_Value (COLUMNNAME_AutoLogoutDelay, Integer.valueOf(AutoLogoutDelay));
	}

	/** Get Auto Logout Delay.
		@return Automatically logout if terminal inactive for this period
	  */
	@Override
	public int getAutoLogoutDelay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AutoLogoutDelay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CashDrawer.
		@param CashDrawer CashDrawer	  */
	@Override
	public void setCashDrawer (java.lang.String CashDrawer)
	{
		set_Value (COLUMNNAME_CashDrawer, CashDrawer);
	}

	/** Get CashDrawer.
		@return CashDrawer	  */
	@Override
	public java.lang.String getCashDrawer () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CashDrawer);
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartnerCashTrx() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartnerCashTrx_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartnerCashTrx(org.compiere.model.I_C_BPartner C_BPartnerCashTrx)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartnerCashTrx_ID, org.compiere.model.I_C_BPartner.class, C_BPartnerCashTrx);
	}

	/** Set Vorlage Geschäftspartner.
		@param C_BPartnerCashTrx_ID 
		Business Partner used for creating new Business Partners on the fly
	  */
	@Override
	public void setC_BPartnerCashTrx_ID (int C_BPartnerCashTrx_ID)
=======

	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAutoLogoutDelay (final int AutoLogoutDelay)
	{
		set_Value (COLUMNNAME_AutoLogoutDelay, AutoLogoutDelay);
	}

	@Override
	public int getAutoLogoutDelay() 
	{
		return get_ValueAsInt(COLUMNNAME_AutoLogoutDelay);
	}

	@Override
	public void setCashLastBalance (final BigDecimal CashLastBalance)
	{
		set_Value (COLUMNNAME_CashLastBalance, CashLastBalance);
	}

	@Override
	public BigDecimal getCashLastBalance() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CashLastBalance);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_BPartnerCashTrx_ID (final int C_BPartnerCashTrx_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BPartnerCashTrx_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerCashTrx_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_C_BPartnerCashTrx_ID, Integer.valueOf(C_BPartnerCashTrx_ID));
	}

	/** Get Vorlage Geschäftspartner.
		@return Business Partner used for creating new Business Partners on the fly
	  */
	@Override
	public int getC_BPartnerCashTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerCashTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException
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
=======
			set_Value (COLUMNNAME_C_BPartnerCashTrx_ID, C_BPartnerCashTrx_ID);
	}

	@Override
	public int getC_BPartnerCashTrx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartnerCashTrx_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
<<<<<<< HEAD
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

	@Override
	public org.compiere.model.I_C_CashBook getC_CashBook() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_CashBook_ID, org.compiere.model.I_C_CashBook.class);
	}

	@Override
	public void setC_CashBook(org.compiere.model.I_C_CashBook C_CashBook)
	{
		set_ValueFromPO(COLUMNNAME_C_CashBook_ID, org.compiere.model.I_C_CashBook.class, C_CashBook);
	}

	/** Set Kassenbuch.
		@param C_CashBook_ID 
		Cash Book for recording petty cash transactions
	  */
	@Override
	public void setC_CashBook_ID (int C_CashBook_ID)
	{
		if (C_CashBook_ID < 1) 
			set_Value (COLUMNNAME_C_CashBook_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashBook_ID, Integer.valueOf(C_CashBook_ID));
	}

	/** Get Kassenbuch.
		@return Cash Book for recording petty cash transactions
	  */
	@Override
	public int getC_CashBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CashBook_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Document type or rules
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Document type or rules
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set POS-Terminal.
		@param C_POS_ID 
		Point of Sales Terminal
	  */
	@Override
	public void setC_POS_ID (int C_POS_ID)
=======
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	@Override
	public void setC_DocTypeOrder_ID (final int C_DocTypeOrder_ID)
	{
		if (C_DocTypeOrder_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeOrder_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeOrder_ID, C_DocTypeOrder_ID);
	}

	@Override
	public int getC_DocTypeOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocTypeOrder_ID);
	}

	@Override
	public void setC_POS_ID (final int C_POS_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_POS_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_C_POS_ID, Integer.valueOf(C_POS_ID));
	}

	/** Get POS-Terminal.
		@return Point of Sales Terminal
	  */
	@Override
	public int getC_POS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_POSKeyLayout getC_POSKeyLayout() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_POSKeyLayout_ID, org.compiere.model.I_C_POSKeyLayout.class);
	}

	@Override
	public void setC_POSKeyLayout(org.compiere.model.I_C_POSKeyLayout C_POSKeyLayout)
	{
		set_ValueFromPO(COLUMNNAME_C_POSKeyLayout_ID, org.compiere.model.I_C_POSKeyLayout.class, C_POSKeyLayout);
	}

	/** Set POS - Tastenanordnung.
		@param C_POSKeyLayout_ID 
		POS Function Key Layout
	  */
	@Override
	public void setC_POSKeyLayout_ID (int C_POSKeyLayout_ID)
	{
		if (C_POSKeyLayout_ID < 1) 
			set_Value (COLUMNNAME_C_POSKeyLayout_ID, null);
		else 
			set_Value (COLUMNNAME_C_POSKeyLayout_ID, Integer.valueOf(C_POSKeyLayout_ID));
	}

	/** Get POS - Tastenanordnung.
		@return POS Function Key Layout
	  */
	@Override
	public int getC_POSKeyLayout_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_POSKeyLayout_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
=======
			set_ValueNoCheck (COLUMNNAME_C_POS_ID, C_POS_ID);
	}

	@Override
	public int getC_POS_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_ID);
	}

	@Override
	public void setC_POS_Journal_ID (final int C_POS_Journal_ID)
	{
		if (C_POS_Journal_ID < 1) 
			set_Value (COLUMNNAME_C_POS_Journal_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_Journal_ID, C_POS_Journal_ID);
	}

	@Override
	public int getC_POS_Journal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Journal_ID);
	}

	@Override
	public org.compiere.model.I_C_Workplace getC_Workplace()
	{
		return get_ValueAsPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class);
	}

	@Override
	public void setC_Workplace(final org.compiere.model.I_C_Workplace C_Workplace)
	{
		set_ValueFromPO(COLUMNNAME_C_Workplace_ID, org.compiere.model.I_C_Workplace.class, C_Workplace);
	}

	@Override
	public void setC_Workplace_ID (final int C_Workplace_ID)
	{
		if (C_Workplace_ID < 1) 
			set_Value (COLUMNNAME_C_Workplace_ID, null);
		else 
			set_Value (COLUMNNAME_C_Workplace_ID, C_Workplace_ID);
	}

	@Override
	public int getC_Workplace_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Workplace_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Description, Description);
	}

<<<<<<< HEAD
	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Modify Price.
		@param IsModifyPrice 
		Allow modifying the price
	  */
	@Override
	public void setIsModifyPrice (boolean IsModifyPrice)
	{
		set_Value (COLUMNNAME_IsModifyPrice, Boolean.valueOf(IsModifyPrice));
	}

	/** Get Modify Price.
		@return Allow modifying the price
	  */
	@Override
	public boolean isModifyPrice () 
	{
		Object oo = get_Value(COLUMNNAME_IsModifyPrice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList)
	{
		set_ValueFromPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class, M_PriceList);
	}

	/** Set Preisliste.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	@Override
	public void setM_PriceList_ID (int M_PriceList_ID)
=======
	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setIsModifyPrice (final boolean IsModifyPrice)
	{
		set_Value (COLUMNNAME_IsModifyPrice, IsModifyPrice);
	}

	@Override
	public boolean isModifyPrice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsModifyPrice);
	}

	@Override
	public void setM_PriceList_ID (final int M_PriceList_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Preisliste.
		@return Unique identifier of a Price List
	  */
	@Override
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
=======
			set_Value (COLUMNNAME_M_PriceList_ID, M_PriceList_ID);
	}

	@Override
	public int getM_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Storage Warehouse and Service Point
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
=======
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Name, Name);
	}

<<<<<<< HEAD
	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public org.compiere.util.KeyNamePair getKeyNamePair() 
    {
        return new org.compiere.util.KeyNamePair(get_ID(), getName());
    }

	@Override
	public org.compiere.model.I_C_POSKeyLayout getOSK_KeyLayout() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_OSK_KeyLayout_ID, org.compiere.model.I_C_POSKeyLayout.class);
	}

	@Override
	public void setOSK_KeyLayout(org.compiere.model.I_C_POSKeyLayout OSK_KeyLayout)
	{
		set_ValueFromPO(COLUMNNAME_OSK_KeyLayout_ID, org.compiere.model.I_C_POSKeyLayout.class, OSK_KeyLayout);
	}

	/** Set On Screen Keyboard layout.
		@param OSK_KeyLayout_ID 
		The key layout to use for on screen keyboard for text fields.
	  */
	@Override
	public void setOSK_KeyLayout_ID (int OSK_KeyLayout_ID)
	{
		if (OSK_KeyLayout_ID < 1) 
			set_Value (COLUMNNAME_OSK_KeyLayout_ID, null);
		else 
			set_Value (COLUMNNAME_OSK_KeyLayout_ID, Integer.valueOf(OSK_KeyLayout_ID));
	}

	/** Get On Screen Keyboard layout.
		@return The key layout to use for on screen keyboard for text fields.
	  */
	@Override
	public int getOSK_KeyLayout_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OSK_KeyLayout_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_POSKeyLayout getOSNP_KeyLayout() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_OSNP_KeyLayout_ID, org.compiere.model.I_C_POSKeyLayout.class);
	}

	@Override
	public void setOSNP_KeyLayout(org.compiere.model.I_C_POSKeyLayout OSNP_KeyLayout)
	{
		set_ValueFromPO(COLUMNNAME_OSNP_KeyLayout_ID, org.compiere.model.I_C_POSKeyLayout.class, OSNP_KeyLayout);
	}

	/** Set On Screen Number Pad layout.
		@param OSNP_KeyLayout_ID 
		The key layout to use for on screen number pad for numeric fields.
	  */
	@Override
	public void setOSNP_KeyLayout_ID (int OSNP_KeyLayout_ID)
	{
		if (OSNP_KeyLayout_ID < 1) 
			set_Value (COLUMNNAME_OSNP_KeyLayout_ID, null);
		else 
			set_Value (COLUMNNAME_OSNP_KeyLayout_ID, Integer.valueOf(OSNP_KeyLayout_ID));
	}

	/** Get On Screen Number Pad layout.
		@return The key layout to use for on screen number pad for numeric fields.
	  */
	@Override
	public int getOSNP_KeyLayout_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OSNP_KeyLayout_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Drucker.
		@param PrinterName 
		Name of the Printer
	  */
	@Override
	public void setPrinterName (java.lang.String PrinterName)
=======
	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	/** 
	 * POSPaymentProcessor AD_Reference_ID=541896
	 * Reference name: POSPaymentProcessor
	 */
	public static final int POSPAYMENTPROCESSOR_AD_Reference_ID=541896;
	/** SumUp = sumup */
	public static final String POSPAYMENTPROCESSOR_SumUp = "sumup";
	@Override
	public void setPOSPaymentProcessor (final @Nullable java.lang.String POSPaymentProcessor)
	{
		set_Value (COLUMNNAME_POSPaymentProcessor, POSPaymentProcessor);
	}

	@Override
	public java.lang.String getPOSPaymentProcessor() 
	{
		return get_ValueAsString(COLUMNNAME_POSPaymentProcessor);
	}

	@Override
	public void setPrinterName (final @Nullable java.lang.String PrinterName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_PrinterName, PrinterName);
	}

<<<<<<< HEAD
	/** Get Drucker.
		@return Name of the Printer
	  */
	@Override
	public java.lang.String getPrinterName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrinterName);
	}

	@Override
	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setSalesRep(org.compiere.model.I_AD_User SalesRep)
	{
		set_ValueFromPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class, SalesRep);
	}

	/** Set Vertriebsbeauftragter.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	@Override
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Vertriebsbeauftragter.
		@return Sales Representative or Company Agent
	  */
	@Override
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
=======
	@Override
	public java.lang.String getPrinterName() 
	{
		return get_ValueAsString(COLUMNNAME_PrinterName);
	}

	@Override
	public void setSUMUP_Config_ID (final int SUMUP_Config_ID)
	{
		if (SUMUP_Config_ID < 1) 
			set_Value (COLUMNNAME_SUMUP_Config_ID, null);
		else 
			set_Value (COLUMNNAME_SUMUP_Config_ID, SUMUP_Config_ID);
	}

	@Override
	public int getSUMUP_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SUMUP_Config_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}