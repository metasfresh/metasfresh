/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_DesadvLine_SSCC
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_DesadvLine_SSCC extends org.compiere.model.PO implements I_EDI_DesadvLine_SSCC, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 637122702L;

    /** Standard Constructor */
    public X_EDI_DesadvLine_SSCC (Properties ctx, int EDI_DesadvLine_SSCC_ID, String trxName)
    {
      super (ctx, EDI_DesadvLine_SSCC_ID, trxName);
      /** if (EDI_DesadvLine_SSCC_ID == 0)
        {
			setEDI_Desadv_ID (0);
			setEDI_DesadvLine_ID (0);
			setEDI_DesadvLine_SSCC_ID (0);
			setIPA_SSCC18 (null);
			setIsManual_IPA_SSCC18 (false); // N
        } */
    }

    /** Load Constructor */
    public X_EDI_DesadvLine_SSCC (Properties ctx, ResultSet rs, String trxName)
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
			set_Value (COLUMNNAME_EDI_Desadv_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_Desadv_ID, Integer.valueOf(EDI_Desadv_ID));
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

	/** Set EDI_DesadvLine.
		@param EDI_DesadvLine_ID EDI_DesadvLine	  */
	@Override
	public void setEDI_DesadvLine_ID (int EDI_DesadvLine_ID)
	{
		if (EDI_DesadvLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_ID, Integer.valueOf(EDI_DesadvLine_ID));
	}

	/** Get EDI_DesadvLine.
		@return EDI_DesadvLine	  */
	@Override
	public int getEDI_DesadvLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_DesadvLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI Lieferavis SSCC (DESADV).
		@param EDI_DesadvLine_SSCC_ID EDI Lieferavis SSCC (DESADV)	  */
	@Override
	public void setEDI_DesadvLine_SSCC_ID (int EDI_DesadvLine_SSCC_ID)
	{
		if (EDI_DesadvLine_SSCC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_SSCC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_DesadvLine_SSCC_ID, Integer.valueOf(EDI_DesadvLine_SSCC_ID));
	}

	/** Get EDI Lieferavis SSCC (DESADV).
		@return EDI Lieferavis SSCC (DESADV)	  */
	@Override
	public int getEDI_DesadvLine_SSCC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_DesadvLine_SSCC_ID);
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

	/** Set Menge CU.
		@param QtyCU Menge CU	  */
	@Override
	public void setQtyCU (java.math.BigDecimal QtyCU)
	{
		set_Value (COLUMNNAME_QtyCU, QtyCU);
	}

	/** Get Menge CU.
		@return Menge CU	  */
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