/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_DesadvLine_Pack
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_DesadvLine_Pack extends org.compiere.model.PO implements I_EDI_DesadvLine_Pack, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1364769355L;

    /** Standard Constructor */
    public X_EDI_DesadvLine_Pack (Properties ctx, int EDI_DesadvLine_Pack_ID, String trxName)
    {
      super (ctx, EDI_DesadvLine_Pack_ID, trxName);
      /** if (EDI_DesadvLine_Pack_ID == 0)
        {
			setC_UOM_ID (0);
			setEDI_Desadv_ID (0);
			setEDI_DesadvLine_ID (0);
			setEDI_DesadvLine_Pack_ID (0);
			setIPA_SSCC18 (null);
			setIsManual_IPA_SSCC18 (false); // N
			setM_InOut_ID (0);
			setM_InOutLine_ID (0);
			setMovementQty (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_EDI_DesadvLine_Pack (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Mindesthaltbarkeitsdatum.
		@param BestBeforeDate Mindesthaltbarkeitsdatum	  */
	@Override
	public void setBestBeforeDate (java.sql.Timestamp BestBeforeDate)
	{
		set_Value (COLUMNNAME_BestBeforeDate, BestBeforeDate);
	}

	/** Get Mindesthaltbarkeitsdatum.
		@return Mindesthaltbarkeitsdatum	  */
	@Override
	public java.sql.Timestamp getBestBeforeDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_BestBeforeDate);
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

	@Override
	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class);
	}

	@Override
	public void setEDI_Desadv(de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class, EDI_Desadv);
	}

	/** Set DESADV.
		@param EDI_Desadv_ID DESADV	  */
	@Override
	public void setEDI_Desadv_ID (int EDI_Desadv_ID)
	{
		if (EDI_Desadv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, Integer.valueOf(EDI_Desadv_ID));
	}

	/** Get DESADV.
		@return DESADV	  */
	@Override
	public int getEDI_Desadv_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_Desadv_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_DesadvLine getEDI_DesadvLine()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_DesadvLine_ID, de.metas.esb.edi.model.I_EDI_DesadvLine.class);
	}

	@Override
	public void setEDI_DesadvLine(de.metas.esb.edi.model.I_EDI_DesadvLine EDI_DesadvLine)
	{
		set_ValueFromPO(COLUMNNAME_EDI_DesadvLine_ID, de.metas.esb.edi.model.I_EDI_DesadvLine.class, EDI_DesadvLine);
	}

	/** Set DESADV-Position.
		@param EDI_DesadvLine_ID DESADV-Position	  */
	@Override
	public void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID)
	{
		if (EDI_DesadvLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, Integer.valueOf(EDI_DesadvLine_ID));
	}

	/** Get DESADV-Position.
		@return DESADV-Position	  */
	@Override
	public int getEDI_DesadvLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_DesadvLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI Lieferavis Pack (DESADV).
		@param EDI_DesadvLine_Pack_ID EDI Lieferavis Pack (DESADV)	  */
	@Override
	public void setEDI_DesadvLine_Pack_ID (int EDI_DesadvLine_Pack_ID)
	{
		if (EDI_DesadvLine_Pack_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_Pack_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_Pack_ID, Integer.valueOf(EDI_DesadvLine_Pack_ID));
	}

	/** Get EDI Lieferavis Pack (DESADV).
		@return EDI Lieferavis Pack (DESADV)	  */
	@Override
	public int getEDI_DesadvLine_Pack_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_DesadvLine_Pack_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SSCC18.
		@param IPA_SSCC18 SSCC18	  */
	@Override
	public void setIPA_SSCC18 (java.lang.String IPA_SSCC18)
	{
		set_Value (COLUMNNAME_IPA_SSCC18, IPA_SSCC18);
	}

	/** Get SSCC18.
		@return SSCC18	  */
	@Override
	public java.lang.String getIPA_SSCC18 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IPA_SSCC18);
	}

	/** Set manuelle SSCC18.
		@param IsManual_IPA_SSCC18 
		Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.
	  */
	@Override
	public void setIsManual_IPA_SSCC18 (boolean IsManual_IPA_SSCC18)
	{
		set_Value (COLUMNNAME_IsManual_IPA_SSCC18, Boolean.valueOf(IsManual_IPA_SSCC18));
	}

	/** Get manuelle SSCC18.
		@return Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.
	  */
	@Override
	public boolean isManual_IPA_SSCC18 () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual_IPA_SSCC18);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Chargennummer.
		@param LotNumber Chargennummer	  */
	@Override
	public void setLotNumber (java.lang.String LotNumber)
	{
		set_Value (COLUMNNAME_LotNumber, LotNumber);
	}

	/** Get Chargennummer.
		@return Chargennummer	  */
	@Override
	public java.lang.String getLotNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LotNumber);
	}

	/** Set Handling Unit.
		@param M_HU_ID Handling Unit	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Unit.
		@return Handling Unit	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LU Verpackungscode.
		@param M_HU_PackagingCode_LU_ID LU Verpackungscode	  */
	@Override
	public void setM_HU_PackagingCode_LU_ID (int M_HU_PackagingCode_LU_ID)
	{
		if (M_HU_PackagingCode_LU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PackagingCode_LU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PackagingCode_LU_ID, Integer.valueOf(M_HU_PackagingCode_LU_ID));
	}

	/** Get LU Verpackungscode.
		@return LU Verpackungscode	  */
	@Override
	public int getM_HU_PackagingCode_LU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PackagingCode_LU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_HU_PackagingCode_LU_Text.
		@param M_HU_PackagingCode_LU_Text M_HU_PackagingCode_LU_Text	  */
	@Override
	public void setM_HU_PackagingCode_LU_Text (java.lang.String M_HU_PackagingCode_LU_Text)
	{
		throw new IllegalArgumentException ("M_HU_PackagingCode_LU_Text is virtual column");	}

	/** Get M_HU_PackagingCode_LU_Text.
		@return M_HU_PackagingCode_LU_Text	  */
	@Override
	public java.lang.String getM_HU_PackagingCode_LU_Text () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_M_HU_PackagingCode_LU_Text);
	}

	/** Set TU Verpackungscode.
		@param M_HU_PackagingCode_TU_ID TU Verpackungscode	  */
	@Override
	public void setM_HU_PackagingCode_TU_ID (int M_HU_PackagingCode_TU_ID)
	{
		if (M_HU_PackagingCode_TU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PackagingCode_TU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PackagingCode_TU_ID, Integer.valueOf(M_HU_PackagingCode_TU_ID));
	}

	/** Get TU Verpackungscode.
		@return TU Verpackungscode	  */
	@Override
	public int getM_HU_PackagingCode_TU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PackagingCode_TU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_HU_PackagingCode_TU_Text.
		@param M_HU_PackagingCode_TU_Text M_HU_PackagingCode_TU_Text	  */
	@Override
	public void setM_HU_PackagingCode_TU_Text (java.lang.String M_HU_PackagingCode_TU_Text)
	{
		throw new IllegalArgumentException ("M_HU_PackagingCode_TU_Text is virtual column");	}

	/** Get M_HU_PackagingCode_TU_Text.
		@return M_HU_PackagingCode_TU_Text	  */
	@Override
	public java.lang.String getM_HU_PackagingCode_TU_Text () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_M_HU_PackagingCode_TU_Text);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
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
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
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

	/** Set Bewegungs-Menge.
		@param MovementQty 
		Menge eines bewegten Produktes.
	  */
	@Override
	public void setMovementQty (java.math.BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Bewegungs-Menge.
		@return Menge eines bewegten Produktes.
	  */
	@Override
	public java.math.BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge CU.
		@param QtyCU 
		Menge der CUs pro Einzelgebinde (normalerweise TU)
	  */
	@Override
	public void setQtyCU (java.math.BigDecimal QtyCU)
	{
		set_Value (COLUMNNAME_QtyCU, QtyCU);
	}

	/** Get Menge CU.
		@return Menge der CUs pro Einzelgebinde (normalerweise TU)
	  */
	@Override
	public java.math.BigDecimal getQtyCU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCU);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge CU/LU.
		@param QtyCUsPerLU Menge CU/LU	  */
	@Override
	public void setQtyCUsPerLU (java.math.BigDecimal QtyCUsPerLU)
	{
		set_Value (COLUMNNAME_QtyCUsPerLU, QtyCUsPerLU);
	}

	/** Get Menge CU/LU.
		@return Menge CU/LU	  */
	@Override
	public java.math.BigDecimal getQtyCUsPerLU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCUsPerLU);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Verpackungskapazität.
		@param QtyItemCapacity 
		Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes
	  */
	@Override
	public void setQtyItemCapacity (java.math.BigDecimal QtyItemCapacity)
	{
		set_Value (COLUMNNAME_QtyItemCapacity, QtyItemCapacity);
	}

	/** Get Verpackungskapazität.
		@return Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes
	  */
	@Override
	public java.math.BigDecimal getQtyItemCapacity () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyItemCapacity);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set TU Anzahl.
		@param QtyTU TU Anzahl	  */
	@Override
	public void setQtyTU (int QtyTU)
	{
		set_Value (COLUMNNAME_QtyTU, Integer.valueOf(QtyTU));
	}

	/** Get TU Anzahl.
		@return TU Anzahl	  */
	@Override
	public int getQtyTU () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyTU);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}