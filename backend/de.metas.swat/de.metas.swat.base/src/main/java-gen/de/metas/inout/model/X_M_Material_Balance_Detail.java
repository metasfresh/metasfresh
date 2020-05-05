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
package de.metas.inout.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_Material_Balance_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Material_Balance_Detail extends org.compiere.model.PO implements I_M_Material_Balance_Detail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1927990004L;

    /** Standard Constructor */
    public X_M_Material_Balance_Detail (Properties ctx, int M_Material_Balance_Detail_ID, String trxName)
    {
      super (ctx, M_Material_Balance_Detail_ID, trxName);
      /** if (M_Material_Balance_Detail_ID == 0)
        {
			setIsForFlatrate (false);
// N
			setIsReset (false);
// N
			setM_Material_Balance_Detail_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Material_Balance_Detail (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
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
		Belegart oder Verarbeitungsvorgaben
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
		@return Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	/** Set Periode.
		@param C_Period_ID 
		Periode des Kalenders
	  */
	@Override
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Periode.
		@return Periode des Kalenders
	  */
	@Override
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beleg Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Beleg Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set IsForFlatrate.
		@param IsForFlatrate IsForFlatrate	  */
	@Override
	public void setIsForFlatrate (boolean IsForFlatrate)
	{
		set_Value (COLUMNNAME_IsForFlatrate, Boolean.valueOf(IsForFlatrate));
	}

	/** Get IsForFlatrate.
		@return IsForFlatrate	  */
	@Override
	public boolean isForFlatrate () 
	{
		Object oo = get_Value(COLUMNNAME_IsForFlatrate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsReset.
		@param IsReset IsReset	  */
	@Override
	public void setIsReset (boolean IsReset)
	{
		set_Value (COLUMNNAME_IsReset, Boolean.valueOf(IsReset));
	}

	/** Get IsReset.
		@return IsReset	  */
	@Override
	public boolean isReset () 
	{
		Object oo = get_Value(COLUMNNAME_IsReset);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/** Set Lieferung/Wareneingang.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Lieferung/Wareneingang.
		@return Material Shipment Document
	  */
	@Override
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	/** Set Versand-/Wareneingangsposition.
		@param M_InOutLine_ID 
		Position auf Versand- oder Wareneingangsbeleg
	  */
	@Override
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Versand-/Wareneingangsposition.
		@return Position auf Versand- oder Wareneingangsbeleg
	  */
	@Override
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.inout.model.I_M_Material_Balance_Config getM_Material_Balance_Config() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Material_Balance_Config_ID, de.metas.inout.model.I_M_Material_Balance_Config.class);
	}

	@Override
	public void setM_Material_Balance_Config(de.metas.inout.model.I_M_Material_Balance_Config M_Material_Balance_Config)
	{
		set_ValueFromPO(COLUMNNAME_M_Material_Balance_Config_ID, de.metas.inout.model.I_M_Material_Balance_Config.class, M_Material_Balance_Config);
	}

	/** Set M_Material_Balance_Config.
		@param M_Material_Balance_Config_ID M_Material_Balance_Config	  */
	@Override
	public void setM_Material_Balance_Config_ID (int M_Material_Balance_Config_ID)
	{
		if (M_Material_Balance_Config_ID < 1) 
			set_Value (COLUMNNAME_M_Material_Balance_Config_ID, null);
		else 
			set_Value (COLUMNNAME_M_Material_Balance_Config_ID, Integer.valueOf(M_Material_Balance_Config_ID));
	}

	/** Get M_Material_Balance_Config.
		@return M_Material_Balance_Config	  */
	@Override
	public int getM_Material_Balance_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Balance_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_Material_Balance_Detail.
		@param M_Material_Balance_Detail_ID M_Material_Balance_Detail	  */
	@Override
	public void setM_Material_Balance_Detail_ID (int M_Material_Balance_Detail_ID)
	{
		if (M_Material_Balance_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Balance_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Balance_Detail_ID, Integer.valueOf(M_Material_Balance_Detail_ID));
	}

	/** Get M_Material_Balance_Detail.
		@return M_Material_Balance_Detail	  */
	@Override
	public int getM_Material_Balance_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Balance_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bewegungs-Datum.
		@param MovementDate 
		Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	  */
	@Override
	public void setMovementDate (java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	/** Get Bewegungs-Datum.
		@return Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	  */
	@Override
	public java.sql.Timestamp getMovementDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MovementDate);
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set QtyIncoming.
		@param QtyIncoming QtyIncoming	  */
	@Override
	public void setQtyIncoming (java.math.BigDecimal QtyIncoming)
	{
		set_Value (COLUMNNAME_QtyIncoming, QtyIncoming);
	}

	/** Get QtyIncoming.
		@return QtyIncoming	  */
	@Override
	public java.math.BigDecimal getQtyIncoming () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyIncoming);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyOutgoing.
		@param QtyOutgoing QtyOutgoing	  */
	@Override
	public void setQtyOutgoing (java.math.BigDecimal QtyOutgoing)
	{
		set_Value (COLUMNNAME_QtyOutgoing, QtyOutgoing);
	}

	/** Get QtyOutgoing.
		@return QtyOutgoing	  */
	@Override
	public java.math.BigDecimal getQtyOutgoing () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOutgoing);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ResetDate.
		@param ResetDate ResetDate	  */
	@Override
	public void setResetDate (java.sql.Timestamp ResetDate)
	{
		set_Value (COLUMNNAME_ResetDate, ResetDate);
	}

	/** Get ResetDate.
		@return ResetDate	  */
	@Override
	public java.sql.Timestamp getResetDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ResetDate);
	}

	/** Set ResetDateEffective.
		@param ResetDateEffective ResetDateEffective	  */
	@Override
	public void setResetDateEffective (java.sql.Timestamp ResetDateEffective)
	{
		set_Value (COLUMNNAME_ResetDateEffective, ResetDateEffective);
	}

	/** Get ResetDateEffective.
		@return ResetDateEffective	  */
	@Override
	public java.sql.Timestamp getResetDateEffective () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ResetDateEffective);
	}
}