/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_OrderTax
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_OrderTax extends org.compiere.model.PO implements I_C_OrderTax, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -121295612L;

    /** Standard Constructor */
    public X_C_OrderTax (Properties ctx, int C_OrderTax_ID, String trxName)
    {
      super (ctx, C_OrderTax_ID, trxName);
      /** if (C_OrderTax_ID == 0)
        {
			setC_Order_ID (0);
			setC_OrderTax_ID (0);
			setC_Tax_ID (0);
			setIsPackagingTax (false);
// N
			setIsTaxIncluded (false);
			setIsWholeTax (false);
// N
			setProcessed (false);
			setTaxAmt (Env.ZERO);
			setTaxBaseAmt (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_OrderTax (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Order
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Order
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Order Tax.
		@param C_OrderTax_ID Order Tax	  */
	@Override
	public void setC_OrderTax_ID (int C_OrderTax_ID)
	{
		if (C_OrderTax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderTax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderTax_ID, Integer.valueOf(C_OrderTax_ID));
	}

	/** Get Order Tax.
		@return Order Tax	  */
	@Override
	public int getC_OrderTax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderTax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Tax_ID, org.compiere.model.I_C_Tax.class);
	}

	@Override
	public void setC_Tax(org.compiere.model.I_C_Tax C_Tax)
	{
		set_ValueFromPO(COLUMNNAME_C_Tax_ID, org.compiere.model.I_C_Tax.class, C_Tax);
	}

	/** Set Steuer.
		@param C_Tax_ID 
		Tax identifier
	  */
	@Override
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Steuer.
		@return Tax identifier
	  */
	@Override
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packaging Tax.
		@param IsPackagingTax Packaging Tax	  */
	@Override
	public void setIsPackagingTax (boolean IsPackagingTax)
	{
		set_Value (COLUMNNAME_IsPackagingTax, Boolean.valueOf(IsPackagingTax));
	}

	/** Get Packaging Tax.
		@return Packaging Tax	  */
	@Override
	public boolean isPackagingTax () 
	{
		Object oo = get_Value(COLUMNNAME_IsPackagingTax);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Preis inklusive Steuern.
		@param IsTaxIncluded 
		Tax is included in the price 
	  */
	@Override
	public void setIsTaxIncluded (boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
	}

	/** Get Preis inklusive Steuern.
		@return Tax is included in the price 
	  */
	@Override
	public boolean isTaxIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Whole Tax.
		@param IsWholeTax 
		If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount
	  */
	@Override
	public void setIsWholeTax (boolean IsWholeTax)
	{
		set_Value (COLUMNNAME_IsWholeTax, Boolean.valueOf(IsWholeTax));
	}

	/** Get Whole Tax.
		@return If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount
	  */
	@Override
	public boolean isWholeTax () 
	{
		Object oo = get_Value(COLUMNNAME_IsWholeTax);
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

	/** Set Steuerbetrag.
		@param TaxAmt 
		Tax Amount for a document
	  */
	@Override
	public void setTaxAmt (java.math.BigDecimal TaxAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Steuerbetrag.
		@return Tax Amount for a document
	  */
	@Override
	public java.math.BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bezugswert.
		@param TaxBaseAmt 
		Base for calculating the tax amount
	  */
	@Override
	public void setTaxBaseAmt (java.math.BigDecimal TaxBaseAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TaxBaseAmt, TaxBaseAmt);
	}

	/** Get Bezugswert.
		@return Base for calculating the tax amount
	  */
	@Override
	public java.math.BigDecimal getTaxBaseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxBaseAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}