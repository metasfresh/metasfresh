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

/** Generated Model for C_Currency_Acct
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Currency_Acct extends org.compiere.model.PO implements I_C_Currency_Acct, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 841852383L;

    /** Standard Constructor */
    public X_C_Currency_Acct (Properties ctx, int C_Currency_Acct_ID, String trxName)
    {
      super (ctx, C_Currency_Acct_ID, trxName);
      /** if (C_Currency_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Currency_ID (0);
			setRealizedGain_Acct (0);
			setRealizedLoss_Acct (0);
			setUnrealizedGain_Acct (0);
			setUnrealizedLoss_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_C_Currency_Acct (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Rules for accounting
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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
	public org.compiere.model.I_C_ValidCombination getRealizedGain_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setRealizedGain_A(org.compiere.model.I_C_ValidCombination RealizedGain_A)
	{
		set_ValueFromPO(COLUMNNAME_RealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class, RealizedGain_A);
	}

	/** Set Realisierte Währungsgewinne.
		@param RealizedGain_Acct 
		Konto für Realisierte Währungsgewinne
	  */
	@Override
	public void setRealizedGain_Acct (int RealizedGain_Acct)
	{
		set_Value (COLUMNNAME_RealizedGain_Acct, Integer.valueOf(RealizedGain_Acct));
	}

	/** Get Realisierte Währungsgewinne.
		@return Konto für Realisierte Währungsgewinne
	  */
	@Override
	public int getRealizedGain_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RealizedGain_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getRealizedLoss_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setRealizedLoss_A(org.compiere.model.I_C_ValidCombination RealizedLoss_A)
	{
		set_ValueFromPO(COLUMNNAME_RealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class, RealizedLoss_A);
	}

	/** Set Realisierte Währungsverluste.
		@param RealizedLoss_Acct 
		Konto für realisierte Währungsverluste
	  */
	@Override
	public void setRealizedLoss_Acct (int RealizedLoss_Acct)
	{
		set_Value (COLUMNNAME_RealizedLoss_Acct, Integer.valueOf(RealizedLoss_Acct));
	}

	/** Get Realisierte Währungsverluste.
		@return Konto für realisierte Währungsverluste
	  */
	@Override
	public int getRealizedLoss_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RealizedLoss_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getUnrealizedGain_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_UnrealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setUnrealizedGain_A(org.compiere.model.I_C_ValidCombination UnrealizedGain_A)
	{
		set_ValueFromPO(COLUMNNAME_UnrealizedGain_Acct, org.compiere.model.I_C_ValidCombination.class, UnrealizedGain_A);
	}

	/** Set Nicht realisierte Währungsgewinne.
		@param UnrealizedGain_Acct 
		Konto für nicht realisierte Währungsgewinne
	  */
	@Override
	public void setUnrealizedGain_Acct (int UnrealizedGain_Acct)
	{
		set_Value (COLUMNNAME_UnrealizedGain_Acct, Integer.valueOf(UnrealizedGain_Acct));
	}

	/** Get Nicht realisierte Währungsgewinne.
		@return Konto für nicht realisierte Währungsgewinne
	  */
	@Override
	public int getUnrealizedGain_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnrealizedGain_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getUnrealizedLoss_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_UnrealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setUnrealizedLoss_A(org.compiere.model.I_C_ValidCombination UnrealizedLoss_A)
	{
		set_ValueFromPO(COLUMNNAME_UnrealizedLoss_Acct, org.compiere.model.I_C_ValidCombination.class, UnrealizedLoss_A);
	}

	/** Set Nicht realisierte Währungsverluste.
		@param UnrealizedLoss_Acct 
		Konto für nicht realisierte Währungsverluste
	  */
	@Override
	public void setUnrealizedLoss_Acct (int UnrealizedLoss_Acct)
	{
		set_Value (COLUMNNAME_UnrealizedLoss_Acct, Integer.valueOf(UnrealizedLoss_Acct));
	}

	/** Get Nicht realisierte Währungsverluste.
		@return Konto für nicht realisierte Währungsverluste
	  */
	@Override
	public int getUnrealizedLoss_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UnrealizedLoss_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}