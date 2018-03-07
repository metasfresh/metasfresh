/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Pharma_Product
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_I_Pharma_Product extends org.compiere.model.PO implements I_I_Pharma_Product, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -28956965L;

    /** Standard Constructor */
    public X_I_Pharma_Product (Properties ctx, int I_Pharma_Product_ID, String trxName)
    {
      super (ctx, I_Pharma_Product_ID, trxName);
      /** if (I_Pharma_Product_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_I_Pharma_Product (Properties ctx, ResultSet rs, String trxName)
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

	/** Set A00ANBNR4.
		@param A00ANBNR4 A00ANBNR4	  */
	@Override
	public void setA00ANBNR4 (int A00ANBNR4)
	{
		set_Value (COLUMNNAME_A00ANBNR4, Integer.valueOf(A00ANBNR4));
	}

	/** Get A00ANBNR4.
		@return A00ANBNR4	  */
	@Override
	public int getA00ANBNR4 ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A00ANBNR4);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A00ARTNR.
		@param A00ARTNR A00ARTNR	  */
	@Override
	public void setA00ARTNR (java.lang.String A00ARTNR)
	{
		set_Value (COLUMNNAME_A00ARTNR, A00ARTNR);
	}

	/** Get A00ARTNR.
		@return A00ARTNR	  */
	@Override
	public java.lang.String getA00ARTNR ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00ARTNR);
	}

	/**
	 * A00ATYP AD_Reference_ID=540791
	 * Reference name: Pharma_ArticleType
	 */
	public static final int A00ATYP_AD_Reference_ID=540791;
	/** 0 = 0 */
	public static final String A00ATYP_0 = "0";
	/** 1 = 1 */
	public static final String A00ATYP_1 = "1";
	/** 3 = 3 */
	public static final String A00ATYP_3 = "3";
	/** 4 = 4 */
	public static final String A00ATYP_4 = "4";
	/** Set A00ATYP.
		@param A00ATYP A00ATYP	  */
	@Override
	public void setA00ATYP (java.lang.String A00ATYP)
	{

		set_Value (COLUMNNAME_A00ATYP, A00ATYP);
	}

	/** Get A00ATYP.
		@return A00ATYP	  */
	@Override
	public java.lang.String getA00ATYP ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00ATYP);
	}

	/** Set A00DARFO.
		@param A00DARFO A00DARFO	  */
	@Override
	public void setA00DARFO (java.lang.String A00DARFO)
	{
		set_Value (COLUMNNAME_A00DARFO, A00DARFO);
	}

	/** Get A00DARFO.
		@return A00DARFO	  */
	@Override
	public java.lang.String getA00DARFO ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00DARFO);
	}

	/** Set A00GDAT.
		@param A00GDAT A00GDAT	  */
	@Override
	public void setA00GDAT (java.sql.Timestamp A00GDAT)
	{
		set_Value (COLUMNNAME_A00GDAT, A00GDAT);
	}

	/** Get A00GDAT.
		@return A00GDAT	  */
	@Override
	public java.sql.Timestamp getA00GDAT ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_A00GDAT);
	}

	/** Set A00GTIN.
		@param A00GTIN A00GTIN	  */
	@Override
	public void setA00GTIN (java.lang.String A00GTIN)
	{
		set_Value (COLUMNNAME_A00GTIN, A00GTIN);
	}

	/** Get A00GTIN.
		@return A00GTIN	  */
	@Override
	public java.lang.String getA00GTIN ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00GTIN);
	}

	/** Set A00HMNR.
		@param A00HMNR A00HMNR	  */
	@Override
	public void setA00HMNR (int A00HMNR)
	{
		set_Value (COLUMNNAME_A00HMNR, Integer.valueOf(A00HMNR));
	}

	/** Get A00HMNR.
		@return A00HMNR	  */
	@Override
	public int getA00HMNR ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A00HMNR);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A00LKZ.
		@param A00LKZ A00LKZ	  */
	@Override
	public void setA00LKZ (java.lang.String A00LKZ)
	{
		set_Value (COLUMNNAME_A00LKZ, A00LKZ);
	}

	/** Get A00LKZ.
		@return A00LKZ	  */
	@Override
	public java.lang.String getA00LKZ ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00LKZ);
	}

	/** Set A00PBEZ.
		@param A00PBEZ A00PBEZ	  */
	@Override
	public void setA00PBEZ (java.lang.String A00PBEZ)
	{
		set_Value (COLUMNNAME_A00PBEZ, A00PBEZ);
	}

	/** Get A00PBEZ.
		@return A00PBEZ	  */
	@Override
	public java.lang.String getA00PBEZ ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00PBEZ);
	}

	/**
	 * A00PGEINH AD_Reference_ID=540790
	 * Reference name: Pharma_UOM
	 */
	public static final int A00PGEINH_AD_Reference_ID=540790;
	/** CM = CM */
	public static final String A00PGEINH_CM = "CM";
	/** FL = FL */
	public static final String A00PGEINH_FL = "FL";
	/** G = G */
	public static final String A00PGEINH_G = "G";
	/** KG = KG */
	public static final String A00PGEINH_KG = "KG";
	/** L = L */
	public static final String A00PGEINH_L = "L";
	/** M = M */
	public static final String A00PGEINH_M = "M";
	/** MG = MG */
	public static final String A00PGEINH_MG = "MG";
	/** ML = ML */
	public static final String A00PGEINH_ML = "ML";
	/** P = P */
	public static final String A00PGEINH_P = "P";
	/** ST = ST */
	public static final String A00PGEINH_ST = "ST";
	/** UG = UG */
	public static final String A00PGEINH_UG = "UG";
	/** Set A00PGEINH.
		@param A00PGEINH A00PGEINH	  */
	@Override
	public void setA00PGEINH (java.lang.String A00PGEINH)
	{

		set_Value (COLUMNNAME_A00PGEINH, A00PGEINH);
	}

	/** Get A00PGEINH.
		@return A00PGEINH	  */
	@Override
	public java.lang.String getA00PGEINH ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00PGEINH);
	}

	/** Set A00PGMENG.
		@param A00PGMENG A00PGMENG	  */
	@Override
	public void setA00PGMENG (java.lang.String A00PGMENG)
	{
		set_Value (COLUMNNAME_A00PGMENG, A00PGMENG);
	}

	/** Get A00PGMENG.
		@return A00PGMENG	  */
	@Override
	public java.lang.String getA00PGMENG ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00PGMENG);
	}

	/** Set A00PNAM.
		@param A00PNAM A00PNAM	  */
	@Override
	public void setA00PNAM (java.lang.String A00PNAM)
	{
		set_Value (COLUMNNAME_A00PNAM, A00PNAM);
	}

	/** Get A00PNAM.
		@return A00PNAM	  */
	@Override
	public java.lang.String getA00PNAM ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00PNAM);
	}

	/** Set A00PPN.
		@param A00PPN A00PPN	  */
	@Override
	public void setA00PPN (BigDecimal A00PPN)
	{
		set_Value (COLUMNNAME_A00PPN, A00PPN);
	}

	/** Get A00PPN.
		@return A00PPN	  */
	@Override
	public BigDecimal getA00PPN ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A00PPN);
		if (bd == null)
		{
			return BigDecimal.ZERO;
		}
		return bd;
	}

	/** Set A00PZN.
		@param A00PZN A00PZN	  */
	@Override
	public void setA00PZN (java.lang.String A00PZN)
	{
		set_Value (COLUMNNAME_A00PZN, A00PZN);
	}

	/** Get A00PZN.
		@return A00PZN	  */
	@Override
	public java.lang.String getA00PZN ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00PZN);
	}

	/** Set A00SSATZ.
		@param A00SSATZ A00SSATZ	  */
	@Override
	public void setA00SSATZ (java.lang.String A00SSATZ)
	{
		set_Value (COLUMNNAME_A00SSATZ, A00SSATZ);
	}

	/** Get A00SSATZ.
		@return A00SSATZ	  */
	@Override
	public java.lang.String getA00SSATZ ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00SSATZ);
	}

	/**
	 * A00WGA AD_Reference_ID=540792
	 * Reference name: Pharma_Group
	 */
	public static final int A00WGA_AD_Reference_ID=540792;
	/** 00 = 00 */
	public static final String A00WGA_00 = "00";
	/** 01 = 01 */
	public static final String A00WGA_01 = "01";
	/** 02 = 02 */
	public static final String A00WGA_02 = "02";
	/** 03 = 03 */
	public static final String A00WGA_03 = "03";
	/** 04 = 04 */
	public static final String A00WGA_04 = "04";
	/** 05 = 05 */
	public static final String A00WGA_05 = "05";
	/** 06 = 06 */
	public static final String A00WGA_06 = "06";
	/** 07 = 07 */
	public static final String A00WGA_07 = "07";
	/** 08 = 08 */
	public static final String A00WGA_08 = "08";
	/** 55 = 55 */
	public static final String A00WGA_55 = "55";
	/** Set A00WGA.
		@param A00WGA A00WGA	  */
	@Override
	public void setA00WGA (java.lang.String A00WGA)
	{

		set_Value (COLUMNNAME_A00WGA, A00WGA);
	}

	/** Get A00WGA.
		@return A00WGA	  */
	@Override
	public java.lang.String getA00WGA ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A00WGA);
	}

	/** Set A01AB130A2.
		@param A01AB130A2 A01AB130A2	  */
	@Override
	public void setA01AB130A2 (int A01AB130A2)
	{
		set_Value (COLUMNNAME_A01AB130A2, Integer.valueOf(A01AB130A2));
	}

	/** Get A01AB130A2.
		@return A01AB130A2	  */
	@Override
	public int getA01AB130A2 ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A01AB130A2);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A01AEP.
		@param A01AEP A01AEP	  */
	@Override
	public void setA01AEP (java.math.BigDecimal A01AEP)
	{
		set_Value (COLUMNNAME_A01AEP, A01AEP);
	}

	/** Get A01AEP.
		@return A01AEP	  */
	@Override
	public java.math.BigDecimal getA01AEP ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A01AEP);
		if (bd == null)
		{
			return BigDecimal.ZERO;
		}
		return bd;
	}

	/** Set A01AEPPPU.
		@param A01AEPPPU A01AEPPPU	  */
	@Override
	public void setA01AEPPPU (int A01AEPPPU)
	{
		set_Value (COLUMNNAME_A01AEPPPU, Integer.valueOf(A01AEPPPU));
	}

	/** Get A01AEPPPU.
		@return A01AEPPPU	  */
	@Override
	public int getA01AEPPPU ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A01AEPPPU);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A01AMPVAMG.
		@param A01AMPVAMG A01AMPVAMG	  */
	@Override
	public void setA01AMPVAMG (java.lang.String A01AMPVAMG)
	{
		set_Value (COLUMNNAME_A01AMPVAMG, A01AMPVAMG);
	}

	/** Get A01AMPVAMG.
		@return A01AMPVAMG	  */
	@Override
	public java.lang.String getA01AMPVAMG ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01AMPVAMG);
	}

	/** Set A01AMPVSGB.
		@param A01AMPVSGB A01AMPVSGB	  */
	@Override
	public void setA01AMPVSGB (java.lang.String A01AMPVSGB)
	{
		set_Value (COLUMNNAME_A01AMPVSGB, A01AMPVSGB);
	}

	/** Get A01AMPVSGB.
		@return A01AMPVSGB	  */
	@Override
	public java.lang.String getA01AMPVSGB ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01AMPVSGB);
	}

	/** Set A01APU.
		@param A01APU A01APU	  */
	@Override
	public void setA01APU (java.math.BigDecimal A01APU)
	{
		set_Value (COLUMNNAME_A01APU, A01APU);
	}

	/** Get A01APU.
		@return A01APU	  */
	@Override
	public java.math.BigDecimal getA01APU ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A01APU);
		if (bd == null)
		{
			return BigDecimal.ZERO;
		}
		return bd;
	}

	/** Set A01APU783A.
		@param A01APU783A A01APU783A	  */
	@Override
	public void setA01APU783A (int A01APU783A)
	{
		set_Value (COLUMNNAME_A01APU783A, Integer.valueOf(A01APU783A));
	}

	/** Get A01APU783A.
		@return A01APU783A	  */
	@Override
	public int getA01APU783A ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A01APU783A);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A01AVP.
		@param A01AVP A01AVP	  */
	@Override
	public void setA01AVP (java.math.BigDecimal A01AVP)
	{
		set_Value (COLUMNNAME_A01AVP, A01AVP);
	}

	/** Get A01AVP.
		@return A01AVP	  */
	@Override
	public java.math.BigDecimal getA01AVP ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A01AVP);
		if (bd == null)
		{
			return BigDecimal.ZERO;
		}
		return bd;
	}

	/** Set A01AVPPPU.
		@param A01AVPPPU A01AVPPPU	  */
	@Override
	public void setA01AVPPPU (int A01AVPPPU)
	{
		set_Value (COLUMNNAME_A01AVPPPU, Integer.valueOf(A01AVPPPU));
	}

	/** Get A01AVPPPU.
		@return A01AVPPPU	  */
	@Override
	public int getA01AVPPPU ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A01AVPPPU);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A01DPPUAPU.
		@param A01DPPUAPU A01DPPUAPU	  */
	@Override
	public void setA01DPPUAPU (java.lang.String A01DPPUAPU)
	{
		set_Value (COLUMNNAME_A01DPPUAPU, A01DPPUAPU);
	}

	/** Get A01DPPUAPU.
		@return A01DPPUAPU	  */
	@Override
	public java.lang.String getA01DPPUAPU ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01DPPUAPU);
	}

	/** Set A01GDAT.
		@param A01GDAT A01GDAT	  */
	@Override
	public void setA01GDAT (java.sql.Timestamp A01GDAT)
	{
		set_Value (COLUMNNAME_A01GDAT, A01GDAT);
	}

	/** Get A01GDAT.
		@return A01GDAT	  */
	@Override
	public java.sql.Timestamp getA01GDAT ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_A01GDAT);
	}

	/** Set A01KAEP.
		@param A01KAEP A01KAEP	  */
	@Override
	public void setA01KAEP (java.math.BigDecimal A01KAEP)
	{
		set_Value (COLUMNNAME_A01KAEP, A01KAEP);
	}

	/** Get A01KAEP.
		@return A01KAEP	  */
	@Override
	public java.math.BigDecimal getA01KAEP ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A01KAEP);
		if (bd == null)
		{
			return BigDecimal.ZERO;
		}
		return bd;
	}

	/**
	 * A01MWST AD_Reference_ID=540801
	 * Reference name: Pharma_Tax
	 */
	public static final int A01MWST_AD_Reference_ID=540801;
	/** 0 = 0 */
	public static final String A01MWST_0 = "0";
	/** 1 = 1 */
	public static final String A01MWST_1 = "1";
	/** 2 = 2 */
	public static final String A01MWST_2 = "2";
	/** Set A01MWST.
		@param A01MWST A01MWST	  */
	@Override
	public void setA01MWST (java.lang.String A01MWST)
	{

		set_Value (COLUMNNAME_A01MWST, A01MWST);
	}

	/** Get A01MWST.
		@return A01MWST	  */
	@Override
	public java.lang.String getA01MWST ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01MWST);
	}

	/** Set A01PPU.
		@param A01PPU A01PPU	  */
	@Override
	public void setA01PPU (int A01PPU)
	{
		set_Value (COLUMNNAME_A01PPU, Integer.valueOf(A01PPU));
	}

	/** Get A01PPU.
		@return A01PPU	  */
	@Override
	public int getA01PPU ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A01PPU);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A01RESERV1.
		@param A01RESERV1 A01RESERV1	  */
	@Override
	public void setA01RESERV1 (int A01RESERV1)
	{
		set_Value (COLUMNNAME_A01RESERV1, Integer.valueOf(A01RESERV1));
	}

	/** Get A01RESERV1.
		@return A01RESERV1	  */
	@Override
	public int getA01RESERV1 ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A01RESERV1);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A01RESERV2.
		@param A01RESERV2 A01RESERV2	  */
	@Override
	public void setA01RESERV2 (int A01RESERV2)
	{
		set_Value (COLUMNNAME_A01RESERV2, Integer.valueOf(A01RESERV2));
	}

	/** Get A01RESERV2.
		@return A01RESERV2	  */
	@Override
	public int getA01RESERV2 ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A01RESERV2);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A01RESERV3.
		@param A01RESERV3 A01RESERV3	  */
	@Override
	public void setA01RESERV3 (int A01RESERV3)
	{
		set_Value (COLUMNNAME_A01RESERV3, Integer.valueOf(A01RESERV3));
	}

	/** Get A01RESERV3.
		@return A01RESERV3	  */
	@Override
	public int getA01RESERV3 ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A01RESERV3);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A01SAB130A.
		@param A01SAB130A A01SAB130A	  */
	@Override
	public void setA01SAB130A (java.lang.String A01SAB130A)
	{
		set_Value (COLUMNNAME_A01SAB130A, A01SAB130A);
	}

	/** Get A01SAB130A.
		@return A01SAB130A	  */
	@Override
	public java.lang.String getA01SAB130A ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01SAB130A);
	}

	/** Set A01SAEP.
		@param A01SAEP A01SAEP	  */
	@Override
	public void setA01SAEP (java.lang.String A01SAEP)
	{
		set_Value (COLUMNNAME_A01SAEP, A01SAEP);
	}

	/** Get A01SAEP.
		@return A01SAEP	  */
	@Override
	public java.lang.String getA01SAEP ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01SAEP);
	}

	/** Set A01SAPU.
		@param A01SAPU A01SAPU	  */
	@Override
	public void setA01SAPU (java.lang.String A01SAPU)
	{
		set_Value (COLUMNNAME_A01SAPU, A01SAPU);
	}

	/** Get A01SAPU.
		@return A01SAPU	  */
	@Override
	public java.lang.String getA01SAPU ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01SAPU);
	}

	/** Set A01SAVP.
		@param A01SAVP A01SAVP	  */
	@Override
	public void setA01SAVP (java.lang.String A01SAVP)
	{
		set_Value (COLUMNNAME_A01SAVP, A01SAVP);
	}

	/** Get A01SAVP.
		@return A01SAVP	  */
	@Override
	public java.lang.String getA01SAVP ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01SAVP);
	}

	/** Set A01SKAEP.
		@param A01SKAEP A01SKAEP	  */
	@Override
	public void setA01SKAEP (java.lang.String A01SKAEP)
	{
		set_Value (COLUMNNAME_A01SKAEP, A01SKAEP);
	}

	/** Get A01SKAEP.
		@return A01SKAEP	  */
	@Override
	public java.lang.String getA01SKAEP ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01SKAEP);
	}

	/** Set A01SUVP.
		@param A01SUVP A01SUVP	  */
	@Override
	public void setA01SUVP (java.lang.String A01SUVP)
	{
		set_Value (COLUMNNAME_A01SUVP, A01SUVP);
	}

	/** Get A01SUVP.
		@return A01SUVP	  */
	@Override
	public java.lang.String getA01SUVP ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01SUVP);
	}

	/** Set A01SZBV.
		@param A01SZBV A01SZBV	  */
	@Override
	public void setA01SZBV (java.lang.String A01SZBV)
	{
		set_Value (COLUMNNAME_A01SZBV, A01SZBV);
	}

	/** Get A01SZBV.
		@return A01SZBV	  */
	@Override
	public java.lang.String getA01SZBV ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A01SZBV);
	}

	/** Set A01UVP.
		@param A01UVP A01UVP	  */
	@Override
	public void setA01UVP (java.math.BigDecimal A01UVP)
	{
		set_Value (COLUMNNAME_A01UVP, A01UVP);
	}

	/** Get A01UVP.
		@return A01UVP	  */
	@Override
	public java.math.BigDecimal getA01UVP ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A01UVP);
		if (bd == null)
		{
			return BigDecimal.ZERO;
		}
		return bd;
	}

	/** Set A01ZBV.
		@param A01ZBV A01ZBV	  */
	@Override
	public void setA01ZBV (java.math.BigDecimal A01ZBV)
	{
		set_Value (COLUMNNAME_A01ZBV, A01ZBV);
	}

	/** Get A01ZBV.
		@return A01ZBV	  */
	@Override
	public java.math.BigDecimal getA01ZBV ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A01ZBV);
		if (bd == null)
		{
			return BigDecimal.ZERO;
		}
		return bd;
	}

	/**
	 * A02130A18 AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02130A18_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02130A18_0 = "0";
	/** 1 = 1 */
	public static final String A02130A18_1 = "1";
	/** Set A02130A18.
		@param A02130A18 A02130A18	  */
	@Override
	public void setA02130A18 (java.lang.String A02130A18)
	{

		set_Value (COLUMNNAME_A02130A18, A02130A18);
	}

	/** Get A02130A18.
		@return A02130A18	  */
	@Override
	public java.lang.String getA02130A18 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02130A18);
	}

	/**
	 * A02130A1A8 AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02130A1A8_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02130A1A8_0 = "0";
	/** 1 = 1 */
	public static final String A02130A1A8_1 = "1";
	/** Set A02130A1A8.
		@param A02130A1A8 A02130A1A8	  */
	@Override
	public void setA02130A1A8 (java.lang.String A02130A1A8)
	{

		set_Value (COLUMNNAME_A02130A1A8, A02130A1A8);
	}

	/** Get A02130A1A8.
		@return A02130A1A8	  */
	@Override
	public java.lang.String getA02130A1A8 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02130A1A8);
	}

	/**
	 * A0247AMG AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A0247AMG_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A0247AMG_0 = "0";
	/** 1 = 1 */
	public static final String A0247AMG_1 = "1";
	/** Set A0247AMG.
		@param A0247AMG A0247AMG	  */
	@Override
	public void setA0247AMG (java.lang.String A0247AMG)
	{

		set_Value (COLUMNNAME_A0247AMG, A0247AMG);
	}

	/** Get A0247AMG.
		@return A0247AMG	  */
	@Override
	public java.lang.String getA0247AMG ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A0247AMG);
	}

	/**
	 * A0252B AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A0252B_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A0252B_0 = "0";
	/** 1 = 1 */
	public static final String A0252B_1 = "1";
	/** Set A0252B.
		@param A0252B A0252B	  */
	@Override
	public void setA0252B (java.lang.String A0252B)
	{

		set_Value (COLUMNNAME_A0252B, A0252B);
	}

	/** Get A0252B.
		@return A0252B	  */
	@Override
	public java.lang.String getA0252B ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A0252B);
	}

	/**
	 * A02AM AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02AM_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02AM_0 = "0";
	/** 1 = 1 */
	public static final String A02AM_1 = "1";
	/** Set A02AM.
		@param A02AM A02AM	  */
	@Override
	public void setA02AM (java.lang.String A02AM)
	{

		set_Value (COLUMNNAME_A02AM, A02AM);
	}

	/** Get A02AM.
		@return A02AM	  */
	@Override
	public java.lang.String getA02AM ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02AM);
	}

	/**
	 * A02ANTHRO AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02ANTHRO_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02ANTHRO_0 = "0";
	/** 1 = 1 */
	public static final String A02ANTHRO_1 = "1";
	/** Set A02ANTHRO.
		@param A02ANTHRO A02ANTHRO	  */
	@Override
	public void setA02ANTHRO (java.lang.String A02ANTHRO)
	{

		set_Value (COLUMNNAME_A02ANTHRO, A02ANTHRO);
	}

	/** Get A02ANTHRO.
		@return A02ANTHRO	  */
	@Override
	public java.lang.String getA02ANTHRO ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02ANTHRO);
	}

	/**
	 * A02APPFL AD_Reference_ID=540789
	 * Reference name: Pharma_IsPharmacy
	 */
	public static final int A02APPFL_AD_Reference_ID=540789;
	/** 0 = 0 */
	public static final String A02APPFL_0 = "0";
	/** 1 = 1 */
	public static final String A02APPFL_1 = "1";
	/** 2 = 2 */
	public static final String A02APPFL_2 = "2";
	/** Set A02APPFL.
		@param A02APPFL A02APPFL	  */
	@Override
	public void setA02APPFL (java.lang.String A02APPFL)
	{

		set_Value (COLUMNNAME_A02APPFL, A02APPFL);
	}

	/** Get A02APPFL.
		@return A02APPFL	  */
	@Override
	public java.lang.String getA02APPFL ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02APPFL);
	}

	/**
	 * A02AUSNREI AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02AUSNREI_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02AUSNREI_0 = "0";
	/** 1 = 1 */
	public static final String A02AUSNREI_1 = "1";
	/** Set A02AUSNREI.
		@param A02AUSNREI A02AUSNREI	  */
	@Override
	public void setA02AUSNREI (java.lang.String A02AUSNREI)
	{

		set_Value (COLUMNNAME_A02AUSNREI, A02AUSNREI);
	}

	/** Get A02AUSNREI.
		@return A02AUSNREI	  */
	@Override
	public java.lang.String getA02AUSNREI ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02AUSNREI);
	}

	/**
	 * A02BATTG AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02BATTG_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02BATTG_0 = "0";
	/** 1 = 1 */
	public static final String A02BATTG_1 = "1";
	/** Set A02BATTG.
		@param A02BATTG A02BATTG	  */
	@Override
	public void setA02BATTG (java.lang.String A02BATTG)
	{

		set_Value (COLUMNNAME_A02BATTG, A02BATTG);
	}

	/** Get A02BATTG.
		@return A02BATTG	  */
	@Override
	public java.lang.String getA02BATTG ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02BATTG);
	}

	/**
	 * A02BIOSIEG AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02BIOSIEG_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02BIOSIEG_0 = "0";
	/** 1 = 1 */
	public static final String A02BIOSIEG_1 = "1";
	/** Set A02BIOSIEG.
		@param A02BIOSIEG A02BIOSIEG	  */
	@Override
	public void setA02BIOSIEG (java.lang.String A02BIOSIEG)
	{

		set_Value (COLUMNNAME_A02BIOSIEG, A02BIOSIEG);
	}

	/** Get A02BIOSIEG.
		@return A02BIOSIEG	  */
	@Override
	public java.lang.String getA02BIOSIEG ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02BIOSIEG);
	}

	/**
	 * A02BIOTECH AD_Reference_ID=540797
	 * Reference name: Pharma_Biotech
	 */
	public static final int A02BIOTECH_AD_Reference_ID=540797;
	/** 0 = 0 */
	public static final String A02BIOTECH_0 = "0";
	/** 1 = 1 */
	public static final String A02BIOTECH_1 = "1";
	/** 2 = 2 */
	public static final String A02BIOTECH_2 = "2";
	/** 3 = 3 */
	public static final String A02BIOTECH_3 = "3";
	/** Set A02BIOTECH.
		@param A02BIOTECH A02BIOTECH	  */
	@Override
	public void setA02BIOTECH (java.lang.String A02BIOTECH)
	{

		set_Value (COLUMNNAME_A02BIOTECH, A02BIOTECH);
	}

	/** Get A02BIOTECH.
		@return A02BIOTECH	  */
	@Override
	public java.lang.String getA02BIOTECH ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02BIOTECH);
	}

	/**
	 * A02BIOZID AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02BIOZID_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02BIOZID_0 = "0";
	/** 1 = 1 */
	public static final String A02BIOZID_1 = "1";
	/** Set A02BIOZID.
		@param A02BIOZID A02BIOZID	  */
	@Override
	public void setA02BIOZID (java.lang.String A02BIOZID)
	{

		set_Value (COLUMNNAME_A02BIOZID, A02BIOZID);
	}

	/** Get A02BIOZID.
		@return A02BIOZID	  */
	@Override
	public java.lang.String getA02BIOZID ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02BIOZID);
	}

	/** Set A02BOPST.
		@param A02BOPST A02BOPST	  */
	@Override
	public void setA02BOPST (int A02BOPST)
	{
		set_Value (COLUMNNAME_A02BOPST, Integer.valueOf(A02BOPST));
	}

	/** Get A02BOPST.
		@return A02BOPST	  */
	@Override
	public int getA02BOPST ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A02BOPST);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * A02BTM AD_Reference_ID=540785
	 * Reference name: Pharma_IsNarcotic
	 */
	public static final int A02BTM_AD_Reference_ID=540785;
	/** 0 = 0 */
	public static final String A02BTM_0 = "0";
	/** 1 = 1 */
	public static final String A02BTM_1 = "1";
	/** 2 = 2 */
	public static final String A02BTM_2 = "2";
	/** Set A02BTM.
		@param A02BTM A02BTM	  */
	@Override
	public void setA02BTM (java.lang.String A02BTM)
	{

		set_Value (COLUMNNAME_A02BTM, A02BTM);
	}

	/** Get A02BTM.
		@return A02BTM	  */
	@Override
	public java.lang.String getA02BTM ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02BTM);
	}

	/**
	 * A02DIAET AD_Reference_ID=540796
	 * Reference name: Pharma_Dietetic
	 */
	public static final int A02DIAET_AD_Reference_ID=540796;
	/** 0 = 0 */
	public static final String A02DIAET_0 = "0";
	/** 1 = 1 */
	public static final String A02DIAET_1 = "1";
	/** 99 = 99 */
	public static final String A02DIAET_99 = "99";
	/** Set A02DIAET.
		@param A02DIAET A02DIAET	  */
	@Override
	public void setA02DIAET (java.lang.String A02DIAET)
	{

		set_Value (COLUMNNAME_A02DIAET, A02DIAET);
	}

	/** Get A02DIAET.
		@return A02DIAET	  */
	@Override
	public java.lang.String getA02DIAET ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02DIAET);
	}

	/** Set A02DROCH.
		@param A02DROCH A02DROCH	  */
	@Override
	public void setA02DROCH (java.lang.String A02DROCH)
	{
		set_Value (COLUMNNAME_A02DROCH, A02DROCH);
	}

	/** Get A02DROCH.
		@return A02DROCH	  */
	@Override
	public java.lang.String getA02DROCH ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02DROCH);
	}

	/** Set A02EB130B.
		@param A02EB130B A02EB130B	  */
	@Override
	public void setA02EB130B (java.lang.String A02EB130B)
	{
		set_Value (COLUMNNAME_A02EB130B, A02EB130B);
	}

	/** Get A02EB130B.
		@return A02EB130B	  */
	@Override
	public java.lang.String getA02EB130B ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02EB130B);
	}

	/**
	 * A02ELEKEAR AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02ELEKEAR_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02ELEKEAR_0 = "0";
	/** 1 = 1 */
	public static final String A02ELEKEAR_1 = "1";
	/** Set A02ELEKEAR.
		@param A02ELEKEAR A02ELEKEAR	  */
	@Override
	public void setA02ELEKEAR (java.lang.String A02ELEKEAR)
	{

		set_Value (COLUMNNAME_A02ELEKEAR, A02ELEKEAR);
	}

	/** Get A02ELEKEAR.
		@return A02ELEKEAR	  */
	@Override
	public java.lang.String getA02ELEKEAR ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02ELEKEAR);
	}

	/**
	 * A02ELEKTRO AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02ELEKTRO_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02ELEKTRO_0 = "0";
	/** 1 = 1 */
	public static final String A02ELEKTRO_1 = "1";
	/** Set A02ELEKTRO.
		@param A02ELEKTRO A02ELEKTRO	  */
	@Override
	public void setA02ELEKTRO (java.lang.String A02ELEKTRO)
	{

		set_Value (COLUMNNAME_A02ELEKTRO, A02ELEKTRO);
	}

	/** Get A02ELEKTRO.
		@return A02ELEKTRO	  */
	@Override
	public java.lang.String getA02ELEKTRO ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02ELEKTRO);
	}

	/**
	 * A02EXPLOS AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02EXPLOS_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02EXPLOS_0 = "0";
	/** 1 = 1 */
	public static final String A02EXPLOS_1 = "1";
	/** Set A02EXPLOS.
		@param A02EXPLOS A02EXPLOS	  */
	@Override
	public void setA02EXPLOS (java.lang.String A02EXPLOS)
	{

		set_Value (COLUMNNAME_A02EXPLOS, A02EXPLOS);
	}

	/** Get A02EXPLOS.
		@return A02EXPLOS	  */
	@Override
	public java.lang.String getA02EXPLOS ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02EXPLOS);
	}

	/** Set A02GDAT.
		@param A02GDAT A02GDAT	  */
	@Override
	public void setA02GDAT (java.sql.Timestamp A02GDAT)
	{
		set_Value (COLUMNNAME_A02GDAT, A02GDAT);
	}

	/** Get A02GDAT.
		@return A02GDAT	  */
	@Override
	public java.sql.Timestamp getA02GDAT ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_A02GDAT);
	}

	/**
	 * A02GENER AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02GENER_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02GENER_0 = "0";
	/** 1 = 1 */
	public static final String A02GENER_1 = "1";
	/** Set A02GENER.
		@param A02GENER A02GENER	  */
	@Override
	public void setA02GENER (java.lang.String A02GENER)
	{

		set_Value (COLUMNNAME_A02GENER, A02GENER);
	}

	/** Get A02GENER.
		@return A02GENER	  */
	@Override
	public java.lang.String getA02GENER ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02GENER);
	}

	/** Set A02HMZV.
		@param A02HMZV A02HMZV	  */
	@Override
	public void setA02HMZV (java.lang.String A02HMZV)
	{
		set_Value (COLUMNNAME_A02HMZV, A02HMZV);
	}

	/** Get A02HMZV.
		@return A02HMZV	  */
	@Override
	public java.lang.String getA02HMZV ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02HMZV);
	}

	/**
	 * A02HOMOEO AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02HOMOEO_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02HOMOEO_0 = "0";
	/** 1 = 1 */
	public static final String A02HOMOEO_1 = "1";
	/** Set A02HOMOEO.
		@param A02HOMOEO A02HOMOEO	  */
	@Override
	public void setA02HOMOEO (java.lang.String A02HOMOEO)
	{

		set_Value (COLUMNNAME_A02HOMOEO, A02HOMOEO);
	}

	/** Get A02HOMOEO.
		@return A02HOMOEO	  */
	@Override
	public java.lang.String getA02HOMOEO ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02HOMOEO);
	}

	/**
	 * A02KOSMET AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02KOSMET_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02KOSMET_0 = "0";
	/** 1 = 1 */
	public static final String A02KOSMET_1 = "1";
	/** Set A02KOSMET.
		@param A02KOSMET A02KOSMET	  */
	@Override
	public void setA02KOSMET (java.lang.String A02KOSMET)
	{

		set_Value (COLUMNNAME_A02KOSMET, A02KOSMET);
	}

	/** Get A02KOSMET.
		@return A02KOSMET	  */
	@Override
	public java.lang.String getA02KOSMET ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02KOSMET);
	}

	/**
	 * A02LEBENSM AD_Reference_ID=540795
	 * Reference name: Pharma_Food
	 */
	public static final int A02LEBENSM_AD_Reference_ID=540795;
	/** 0 = 0 */
	public static final String A02LEBENSM_0 = "0";
	/** 1 = 1 */
	public static final String A02LEBENSM_1 = "1";
	/** 99 = 99 */
	public static final String A02LEBENSM_99 = "99";
	/** Set A02LEBENSM.
		@param A02LEBENSM A02LEBENSM	  */
	@Override
	public void setA02LEBENSM (java.lang.String A02LEBENSM)
	{

		set_Value (COLUMNNAME_A02LEBENSM, A02LEBENSM);
	}

	/** Get A02LEBENSM.
		@return A02LEBENSM	  */
	@Override
	public java.lang.String getA02LEBENSM ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02LEBENSM);
	}

	/**
	 * A02LIFSTYL AD_Reference_ID=540794
	 * Reference name: Pharma_LifeStyle
	 */
	public static final int A02LIFSTYL_AD_Reference_ID=540794;
	/** 0 = 0 */
	public static final String A02LIFSTYL_0 = "0";
	/** 1 = 1 */
	public static final String A02LIFSTYL_1 = "1";
	/** 2 = 2 */
	public static final String A02LIFSTYL_2 = "2";
	/** Set A02LIFSTYL.
		@param A02LIFSTYL A02LIFSTYL	  */
	@Override
	public void setA02LIFSTYL (java.lang.String A02LIFSTYL)
	{

		set_Value (COLUMNNAME_A02LIFSTYL, A02LIFSTYL);
	}

	/** Get A02LIFSTYL.
		@return A02LIFSTYL	  */
	@Override
	public java.lang.String getA02LIFSTYL ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02LIFSTYL);
	}

	/** Set A02MELDENR.
		@param A02MELDENR A02MELDENR	  */
	@Override
	public void setA02MELDENR (int A02MELDENR)
	{
		set_Value (COLUMNNAME_A02MELDENR, Integer.valueOf(A02MELDENR));
	}

	/** Get A02MELDENR.
		@return A02MELDENR	  */
	@Override
	public int getA02MELDENR ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A02MELDENR);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * A02MP AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02MP_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02MP_0 = "0";
	/** 1 = 1 */
	public static final String A02MP_1 = "1";
	/** Set A02MP.
		@param A02MP A02MP	  */
	@Override
	public void setA02MP (java.lang.String A02MP)
	{

		set_Value (COLUMNNAME_A02MP, A02MP);
	}

	/** Get A02MP.
		@return A02MP	  */
	@Override
	public java.lang.String getA02MP ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02MP);
	}

	/** Set A02NEGLIS.
		@param A02NEGLIS A02NEGLIS	  */
	@Override
	public void setA02NEGLIS (java.lang.String A02NEGLIS)
	{
		set_Value (COLUMNNAME_A02NEGLIS, A02NEGLIS);
	}

	/** Get A02NEGLIS.
		@return A02NEGLIS	  */
	@Override
	public java.lang.String getA02NEGLIS ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02NEGLIS);
	}

	/**
	 * A02NEM AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02NEM_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02NEM_0 = "0";
	/** 1 = 1 */
	public static final String A02NEM_1 = "1";
	/** Set A02NEM.
		@param A02NEM A02NEM	  */
	@Override
	public void setA02NEM (java.lang.String A02NEM)
	{

		set_Value (COLUMNNAME_A02NEM, A02NEM);
	}

	/** Get A02NEM.
		@return A02NEM	  */
	@Override
	public java.lang.String getA02NEM ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02NEM);
	}

	/**
	 * A02PHYTO AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02PHYTO_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02PHYTO_0 = "0";
	/** 1 = 1 */
	public static final String A02PHYTO_1 = "1";
	/** Set A02PHYTO.
		@param A02PHYTO A02PHYTO	  */
	@Override
	public void setA02PHYTO (java.lang.String A02PHYTO)
	{

		set_Value (COLUMNNAME_A02PHYTO, A02PHYTO);
	}

	/** Get A02PHYTO.
		@return A02PHYTO	  */
	@Override
	public java.lang.String getA02PHYTO ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02PHYTO);
	}

	/**
	 * A02PSM AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02PSM_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02PSM_0 = "0";
	/** 1 = 1 */
	public static final String A02PSM_1 = "1";
	/** Set A02PSM.
		@param A02PSM A02PSM	  */
	@Override
	public void setA02PSM (java.lang.String A02PSM)
	{

		set_Value (COLUMNNAME_A02PSM, A02PSM);
	}

	/** Get A02PSM.
		@return A02PSM	  */
	@Override
	public java.lang.String getA02PSM ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02PSM);
	}

	/** Set A02RESERV1.
		@param A02RESERV1 A02RESERV1	  */
	@Override
	public void setA02RESERV1 (java.lang.String A02RESERV1)
	{
		set_Value (COLUMNNAME_A02RESERV1, A02RESERV1);
	}

	/** Get A02RESERV1.
		@return A02RESERV1	  */
	@Override
	public java.lang.String getA02RESERV1 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02RESERV1);
	}

	/** Set A02RESERV2.
		@param A02RESERV2 A02RESERV2	  */
	@Override
	public void setA02RESERV2 (java.lang.String A02RESERV2)
	{
		set_Value (COLUMNNAME_A02RESERV2, A02RESERV2);
	}

	/** Get A02RESERV2.
		@return A02RESERV2	  */
	@Override
	public java.lang.String getA02RESERV2 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02RESERV2);
	}

	/** Set A02RESERV3.
		@param A02RESERV3 A02RESERV3	  */
	@Override
	public void setA02RESERV3 (java.lang.String A02RESERV3)
	{
		set_Value (COLUMNNAME_A02RESERV3, A02RESERV3);
	}

	/** Get A02RESERV3.
		@return A02RESERV3	  */
	@Override
	public java.lang.String getA02RESERV3 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02RESERV3);
	}

	/**
	 * A02SDB AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02SDB_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02SDB_0 = "0";
	/** 1 = 1 */
	public static final String A02SDB_1 = "1";
	/** Set A02SDB.
		@param A02SDB A02SDB	  */
	@Override
	public void setA02SDB (java.lang.String A02SDB)
	{

		set_Value (COLUMNNAME_A02SDB, A02SDB);
	}

	/** Get A02SDB.
		@return A02SDB	  */
	@Override
	public java.lang.String getA02SDB ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02SDB);
	}

	/**
	 * A02STERIL AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02STERIL_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02STERIL_0 = "0";
	/** 1 = 1 */
	public static final String A02STERIL_1 = "1";
	/** Set A02STERIL.
		@param A02STERIL A02STERIL	  */
	@Override
	public void setA02STERIL (java.lang.String A02STERIL)
	{

		set_Value (COLUMNNAME_A02STERIL, A02STERIL);
	}

	/** Get A02STERIL.
		@return A02STERIL	  */
	@Override
	public java.lang.String getA02STERIL ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02STERIL);
	}

	/**
	 * A02TFG AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02TFG_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02TFG_0 = "0";
	/** 1 = 1 */
	public static final String A02TFG_1 = "1";
	/** Set A02TFG.
		@param A02TFG A02TFG	  */
	@Override
	public void setA02TFG (java.lang.String A02TFG)
	{

		set_Value (COLUMNNAME_A02TFG, A02TFG);
	}

	/** Get A02TFG.
		@return A02TFG	  */
	@Override
	public java.lang.String getA02TFG ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02TFG);
	}

	/** Set A02TIERAM.
		@param A02TIERAM A02TIERAM	  */
	@Override
	public void setA02TIERAM (java.lang.String A02TIERAM)
	{
		set_Value (COLUMNNAME_A02TIERAM, A02TIERAM);
	}

	/** Get A02TIERAM.
		@return A02TIERAM	  */
	@Override
	public java.lang.String getA02TIERAM ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02TIERAM);
	}

	/**
	 * A02TREZEP AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02TREZEP_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02TREZEP_0 = "0";
	/** 1 = 1 */
	public static final String A02TREZEP_1 = "1";
	/** Set A02TREZEP.
		@param A02TREZEP A02TREZEP	  */
	@Override
	public void setA02TREZEP (java.lang.String A02TREZEP)
	{

		set_Value (COLUMNNAME_A02TREZEP, A02TREZEP);
	}

	/** Get A02TREZEP.
		@return A02TREZEP	  */
	@Override
	public java.lang.String getA02TREZEP ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02TREZEP);
	}

	/**
	 * A02VSPFL AD_Reference_ID=540783
	 * Reference name: Pharma_IsPrescription
	 */
	public static final int A02VSPFL_AD_Reference_ID=540783;
	/** 0 = 0 */
	public static final String A02VSPFL_0 = "0";
	/** 1 = 1 */
	public static final String A02VSPFL_1 = "1";
	/** 2 = 2 */
	public static final String A02VSPFL_2 = "2";
	/** 3 = 3 */
	public static final String A02VSPFL_3 = "3";
	/** Set A02VSPFL.
		@param A02VSPFL A02VSPFL	  */
	@Override
	public void setA02VSPFL (java.lang.String A02VSPFL)
	{

		set_Value (COLUMNNAME_A02VSPFL, A02VSPFL);
	}

	/** Get A02VSPFL.
		@return A02VSPFL	  */
	@Override
	public java.lang.String getA02VSPFL ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02VSPFL);
	}

	/** Set A02WEEEREG.
		@param A02WEEEREG A02WEEEREG	  */
	@Override
	public void setA02WEEEREG (int A02WEEEREG)
	{
		set_Value (COLUMNNAME_A02WEEEREG, Integer.valueOf(A02WEEEREG));
	}

	/** Get A02WEEEREG.
		@return A02WEEEREG	  */
	@Override
	public int getA02WEEEREG ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A02WEEEREG);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/**
	 * A02WIRKSTO AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A02WIRKSTO_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A02WIRKSTO_0 = "0";
	/** 1 = 1 */
	public static final String A02WIRKSTO_1 = "1";
	/** Set A02WIRKSTO.
		@param A02WIRKSTO A02WIRKSTO	  */
	@Override
	public void setA02WIRKSTO (java.lang.String A02WIRKSTO)
	{

		set_Value (COLUMNNAME_A02WIRKSTO, A02WIRKSTO);
	}

	/** Get A02WIRKSTO.
		@return A02WIRKSTO	  */
	@Override
	public java.lang.String getA02WIRKSTO ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02WIRKSTO);
	}

	/** Set A02ZULREG.
		@param A02ZULREG A02ZULREG	  */
	@Override
	public void setA02ZULREG (java.lang.String A02ZULREG)
	{
		set_Value (COLUMNNAME_A02ZULREG, A02ZULREG);
	}

	/** Get A02ZULREG.
		@return A02ZULREG	  */
	@Override
	public java.lang.String getA02ZULREG ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A02ZULREG);
	}

	/** Set A03GDAT.
		@param A03GDAT A03GDAT	  */
	@Override
	public void setA03GDAT (java.sql.Timestamp A03GDAT)
	{
		set_Value (COLUMNNAME_A03GDAT, A03GDAT);
	}

	/** Get A03GDAT.
		@return A03GDAT	  */
	@Override
	public java.sql.Timestamp getA03GDAT ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_A03GDAT);
	}

	/** Set A03VKSTAT.
		@param A03VKSTAT A03VKSTAT	  */
	@Override
	public void setA03VKSTAT (java.lang.String A03VKSTAT)
	{
		set_Value (COLUMNNAME_A03VKSTAT, A03VKSTAT);
	}

	/** Get A03VKSTAT.
		@return A03VKSTAT	  */
	@Override
	public java.lang.String getA03VKSTAT ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A03VKSTAT);
	}

	/** Set A03VTSTAT.
		@param A03VTSTAT A03VTSTAT	  */
	@Override
	public void setA03VTSTAT (java.lang.String A03VTSTAT)
	{
		set_Value (COLUMNNAME_A03VTSTAT, A03VTSTAT);
	}

	/** Get A03VTSTAT.
		@return A03VTSTAT	  */
	@Override
	public java.lang.String getA03VTSTAT ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A03VTSTAT);
	}

	/**
	 * A03VWGAPO AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A03VWGAPO_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A03VWGAPO_0 = "0";
	/** 1 = 1 */
	public static final String A03VWGAPO_1 = "1";
	/** Set A03VWGAPO.
		@param A03VWGAPO A03VWGAPO	  */
	@Override
	public void setA03VWGAPO (java.lang.String A03VWGAPO)
	{

		set_Value (COLUMNNAME_A03VWGAPO, A03VWGAPO);
	}

	/** Get A03VWGAPO.
		@return A03VWGAPO	  */
	@Override
	public java.lang.String getA03VWGAPO ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A03VWGAPO);
	}

	/**
	 * A03VWGGH AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A03VWGGH_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A03VWGGH_0 = "0";
	/** 1 = 1 */
	public static final String A03VWGGH_1 = "1";
	/** Set A03VWGGH.
		@param A03VWGGH A03VWGGH	  */
	@Override
	public void setA03VWGGH (java.lang.String A03VWGGH)
	{

		set_Value (COLUMNNAME_A03VWGGH, A03VWGGH);
	}

	/** Get A03VWGGH.
		@return A03VWGGH	  */
	@Override
	public java.lang.String getA03VWGGH ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A03VWGGH);
	}

	/**
	 * A03VWGKAP AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A03VWGKAP_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A03VWGKAP_0 = "0";
	/** 1 = 1 */
	public static final String A03VWGKAP_1 = "1";
	/** Set A03VWGKAP.
		@param A03VWGKAP A03VWGKAP	  */
	@Override
	public void setA03VWGKAP (java.lang.String A03VWGKAP)
	{

		set_Value (COLUMNNAME_A03VWGKAP, A03VWGKAP);
	}

	/** Get A03VWGKAP.
		@return A03VWGKAP	  */
	@Override
	public java.lang.String getA03VWGKAP ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A03VWGKAP);
	}

	/**
	 * A03VWGSEH AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A03VWGSEH_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A03VWGSEH_0 = "0";
	/** 1 = 1 */
	public static final String A03VWGSEH_1 = "1";
	/** Set A03VWGSEH.
		@param A03VWGSEH A03VWGSEH	  */
	@Override
	public void setA03VWGSEH (java.lang.String A03VWGSEH)
	{

		set_Value (COLUMNNAME_A03VWGSEH, A03VWGSEH);
	}

	/** Get A03VWGSEH.
		@return A03VWGSEH	  */
	@Override
	public java.lang.String getA03VWGSEH ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A03VWGSEH);
	}

	/** Set A04BREITE.
		@param A04BREITE A04BREITE	  */
	@Override
	public void setA04BREITE (java.lang.String A04BREITE)
	{
		set_Value (COLUMNNAME_A04BREITE, A04BREITE);
	}

	/** Get A04BREITE.
		@return A04BREITE	  */
	@Override
	public java.lang.String getA04BREITE ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A04BREITE);
	}

	/** Set A04GDAT.
		@param A04GDAT A04GDAT	  */
	@Override
	public void setA04GDAT (java.sql.Timestamp A04GDAT)
	{
		set_Value (COLUMNNAME_A04GDAT, A04GDAT);
	}

	/** Get A04GDAT.
		@return A04GDAT	  */
	@Override
	public java.sql.Timestamp getA04GDAT ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_A04GDAT);
	}

	/** Set A04GWCHT.
		@param A04GWCHT A04GWCHT	  */
	@Override
	public void setA04GWCHT (java.lang.String A04GWCHT)
	{
		set_Value (COLUMNNAME_A04GWCHT, A04GWCHT);
	}

	/** Get A04GWCHT.
		@return A04GWCHT	  */
	@Override
	public java.lang.String getA04GWCHT ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A04GWCHT);
	}

	/** Set A04HOEHE.
		@param A04HOEHE A04HOEHE	  */
	@Override
	public void setA04HOEHE (java.lang.String A04HOEHE)
	{
		set_Value (COLUMNNAME_A04HOEHE, A04HOEHE);
	}

	/** Get A04HOEHE.
		@return A04HOEHE	  */
	@Override
	public java.lang.String getA04HOEHE ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A04HOEHE);
	}

	/** Set A04LAENGE.
		@param A04LAENGE A04LAENGE	  */
	@Override
	public void setA04LAENGE (java.lang.String A04LAENGE)
	{
		set_Value (COLUMNNAME_A04LAENGE, A04LAENGE);
	}

	/** Get A04LAENGE.
		@return A04LAENGE	  */
	@Override
	public java.lang.String getA04LAENGE ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A04LAENGE);
	}

	/** Set A04MBEST.
		@param A04MBEST A04MBEST	  */
	@Override
	public void setA04MBEST (java.lang.String A04MBEST)
	{
		set_Value (COLUMNNAME_A04MBEST, A04MBEST);
	}

	/** Get A04MBEST.
		@return A04MBEST	  */
	@Override
	public java.lang.String getA04MBEST ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A04MBEST);
	}

	/**
	 * A04VPART AD_Reference_ID=540793
	 * Reference name: Pharma_PackageType
	 */
	public static final int A04VPART_AD_Reference_ID=540793;
	/** 1 = 1 */
	public static final String A04VPART_1 = "1";
	/** 2 = 2 */
	public static final String A04VPART_2 = "2";
	/** 3 = 3 */
	public static final String A04VPART_3 = "3";
	/** 4 = 4 */
	public static final String A04VPART_4 = "4";
	/** 5 = 5 */
	public static final String A04VPART_5 = "5";
	/** 6 = 6 */
	public static final String A04VPART_6 = "6";
	/** 7 = 7 */
	public static final String A04VPART_7 = "7";
	/** 8 = 8 */
	public static final String A04VPART_8 = "8";
	/** 9 = 9 */
	public static final String A04VPART_9 = "9";
	/** 10 = 10 */
	public static final String A04VPART_10 = "10";
	/** 11 = 11 */
	public static final String A04VPART_11 = "11";
	/** 12 = 12 */
	public static final String A04VPART_12 = "12";
	/** 13 = 13 */
	public static final String A04VPART_13 = "13";
	/** 14 = 14 */
	public static final String A04VPART_14 = "14";
	/** 15 = 15 */
	public static final String A04VPART_15 = "15";
	/** 16 = 16 */
	public static final String A04VPART_16 = "16";
	/** 17 = 17 */
	public static final String A04VPART_17 = "17";
	/** 18 = 18 */
	public static final String A04VPART_18 = "18";
	/** 19 = 19 */
	public static final String A04VPART_19 = "19";
	/** 20 = 20 */
	public static final String A04VPART_20 = "20";
	/** 21 = 21 */
	public static final String A04VPART_21 = "21";
	/** 22 = 22 */
	public static final String A04VPART_22 = "22";
	/** 99 = 99 */
	public static final String A04VPART_99 = "99";
	/** Set A04VPART.
		@param A04VPART A04VPART	  */
	@Override
	public void setA04VPART (java.lang.String A04VPART)
	{

		set_Value (COLUMNNAME_A04VPART, A04VPART);
	}

	/** Get A04VPART.
		@return A04VPART	  */
	@Override
	public java.lang.String getA04VPART ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A04VPART);
	}

	/**
	 * A05EICH AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A05EICH_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A05EICH_0 = "0";
	/** 1 = 1 */
	public static final String A05EICH_1 = "1";
	/** Set A05EICH.
		@param A05EICH A05EICH	  */
	@Override
	public void setA05EICH (java.lang.String A05EICH)
	{

		set_Value (COLUMNNAME_A05EICH, A05EICH);
	}

	/** Get A05EICH.
		@return A05EICH	  */
	@Override
	public java.lang.String getA05EICH ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A05EICH);
	}

	/**
	 * A05FEUCHT AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A05FEUCHT_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A05FEUCHT_0 = "0";
	/** 1 = 1 */
	public static final String A05FEUCHT_1 = "1";
	/** Set A05FEUCHT.
		@param A05FEUCHT A05FEUCHT	  */
	@Override
	public void setA05FEUCHT (java.lang.String A05FEUCHT)
	{

		set_Value (COLUMNNAME_A05FEUCHT, A05FEUCHT);
	}

	/** Get A05FEUCHT.
		@return A05FEUCHT	  */
	@Override
	public java.lang.String getA05FEUCHT ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A05FEUCHT);
	}

	/** Set A05GDAT.
		@param A05GDAT A05GDAT	  */
	@Override
	public void setA05GDAT (java.sql.Timestamp A05GDAT)
	{
		set_Value (COLUMNNAME_A05GDAT, A05GDAT);
	}

	/** Get A05GDAT.
		@return A05GDAT	  */
	@Override
	public java.sql.Timestamp getA05GDAT ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_A05GDAT);
	}

	/**
	 * A05KKETTE AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A05KKETTE_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A05KKETTE_0 = "0";
	/** 1 = 1 */
	public static final String A05KKETTE_1 = "1";
	/** Set A05KKETTE.
		@param A05KKETTE A05KKETTE	  */
	@Override
	public void setA05KKETTE (java.lang.String A05KKETTE)
	{

		set_Value (COLUMNNAME_A05KKETTE, A05KKETTE);
	}

	/** Get A05KKETTE.
		@return A05KKETTE	  */
	@Override
	public java.lang.String getA05KKETTE ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A05KKETTE);
	}

	/**
	 * A05LAGE AD_Reference_ID=540788
	 * Reference name: Pharma_PositionSenstive
	 */
	public static final int A05LAGE_AD_Reference_ID=540788;
	/** 0 = 0 */
	public static final String A05LAGE_0 = "0";
	/** 1 = 1 */
	public static final String A05LAGE_1 = "1";
	/** 2 = 2 */
	public static final String A05LAGE_2 = "2";
	/** Set A05LAGE.
		@param A05LAGE A05LAGE	  */
	@Override
	public void setA05LAGE (java.lang.String A05LAGE)
	{

		set_Value (COLUMNNAME_A05LAGE, A05LAGE);
	}

	/** Get A05LAGE.
		@return A05LAGE	  */
	@Override
	public java.lang.String getA05LAGE ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A05LAGE);
	}

	/**
	 * A05LICHT AD_Reference_ID=540786
	 * Reference name: Pharma_Photosensitive
	 */
	public static final int A05LICHT_AD_Reference_ID=540786;
	/** 0 = 0 */
	public static final String A05LICHT_0 = "0";
	/** 1 = 1 */
	public static final String A05LICHT_1 = "1";
	/** 2 = 2 */
	public static final String A05LICHT_2 = "2";
	/** Set A05LICHT.
		@param A05LICHT A05LICHT	  */
	@Override
	public void setA05LICHT (java.lang.String A05LICHT)
	{

		set_Value (COLUMNNAME_A05LICHT, A05LICHT);
	}

	/** Get A05LICHT.
		@return A05LICHT	  */
	@Override
	public java.lang.String getA05LICHT ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A05LICHT);
	}

	/** Set A05LZEICH.
		@param A05LZEICH A05LZEICH	  */
	@Override
	public void setA05LZEICH (int A05LZEICH)
	{
		set_Value (COLUMNNAME_A05LZEICH, Integer.valueOf(A05LZEICH));
	}

	/** Get A05LZEICH.
		@return A05LZEICH	  */
	@Override
	public int getA05LZEICH ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A05LZEICH);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A05LZEIT.
		@param A05LZEIT A05LZEIT	  */
	@Override
	public void setA05LZEIT (int A05LZEIT)
	{
		set_Value (COLUMNNAME_A05LZEIT, Integer.valueOf(A05LZEIT));
	}

	/** Get A05LZEIT.
		@return A05LZEIT	  */
	@Override
	public int getA05LZEIT ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A05LZEIT);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A05TMAX.
		@param A05TMAX A05TMAX	  */
	@Override
	public void setA05TMAX (java.lang.String A05TMAX)
	{
		set_Value (COLUMNNAME_A05TMAX, A05TMAX);
	}

	/** Get A05TMAX.
		@return A05TMAX	  */
	@Override
	public java.lang.String getA05TMAX ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A05TMAX);
	}

	/** Set A05TMIN.
		@param A05TMIN A05TMIN	  */
	@Override
	public void setA05TMIN (java.lang.String A05TMIN)
	{
		set_Value (COLUMNNAME_A05TMIN, A05TMIN);
	}

	/** Get A05TMIN.
		@return A05TMIN	  */
	@Override
	public java.lang.String getA05TMIN ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A05TMIN);
	}

	/**
	 * A05VFDAT AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A05VFDAT_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A05VFDAT_0 = "0";
	/** 1 = 1 */
	public static final String A05VFDAT_1 = "1";
	/** Set A05VFDAT.
		@param A05VFDAT A05VFDAT	  */
	@Override
	public void setA05VFDAT (java.lang.String A05VFDAT)
	{

		set_Value (COLUMNNAME_A05VFDAT, A05VFDAT);
	}

	/** Get A05VFDAT.
		@return A05VFDAT	  */
	@Override
	public java.lang.String getA05VFDAT ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A05VFDAT);
	}

	/**
	 * A05ZERBR AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A05ZERBR_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A05ZERBR_0 = "0";
	/** 1 = 1 */
	public static final String A05ZERBR_1 = "1";
	/** Set A05ZERBR.
		@param A05ZERBR A05ZERBR	  */
	@Override
	public void setA05ZERBR (java.lang.String A05ZERBR)
	{

		set_Value (COLUMNNAME_A05ZERBR, A05ZERBR);
	}

	/** Get A05ZERBR.
		@return A05ZERBR	  */
	@Override
	public java.lang.String getA05ZERBR ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A05ZERBR);
	}

	/** Set A06GDAT.
		@param A06GDAT A06GDAT	  */
	@Override
	public void setA06GDAT (java.sql.Timestamp A06GDAT)
	{
		set_Value (COLUMNNAME_A06GDAT, A06GDAT);
	}

	/** Get A06GDAT.
		@return A06GDAT	  */
	@Override
	public java.sql.Timestamp getA06GDAT ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_A06GDAT);
	}

	/** Set A06PZNNF.
		@param A06PZNNF A06PZNNF	  */
	@Override
	public void setA06PZNNF (int A06PZNNF)
	{
		set_Value (COLUMNNAME_A06PZNNF, Integer.valueOf(A06PZNNF));
	}

	/** Get A06PZNNF.
		@return A06PZNNF	  */
	@Override
	public int getA06PZNNF ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A06PZNNF);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A06PZNORIG.
		@param A06PZNORIG A06PZNORIG	  */
	@Override
	public void setA06PZNORIG (int A06PZNORIG)
	{
		set_Value (COLUMNNAME_A06PZNORIG, Integer.valueOf(A06PZNORIG));
	}

	/** Get A06PZNORIG.
		@return A06PZNORIG	  */
	@Override
	public int getA06PZNORIG ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A06PZNORIG);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A09GDAT.
		@param A09GDAT A09GDAT	  */
	@Override
	public void setA09GDAT (java.sql.Timestamp A09GDAT)
	{
		set_Value (COLUMNNAME_A09GDAT, A09GDAT);
	}

	/** Get A09GDAT.
		@return A09GDAT	  */
	@Override
	public java.sql.Timestamp getA09GDAT ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_A09GDAT);
	}

	/**
	 * A09THGP AD_Reference_ID=540798
	 * Reference name: Pharma_PackSize
	 */
	public static final int A09THGP_AD_Reference_ID=540798;
	/** 0 = 0 */
	public static final String A09THGP_0 = "0";
	/** 1 = 1 */
	public static final String A09THGP_1 = "1";
	/** 2 = 2 */
	public static final String A09THGP_2 = "2";
	/** 3 = 3 */
	public static final String A09THGP_3 = "3";
	/** 4 = 4 */
	public static final String A09THGP_4 = "4";
	/** Set A09THGP.
		@param A09THGP A09THGP	  */
	@Override
	public void setA09THGP (java.lang.String A09THGP)
	{

		set_Value (COLUMNNAME_A09THGP, A09THGP);
	}

	/** Get A09THGP.
		@return A09THGP	  */
	@Override
	public java.lang.String getA09THGP ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A09THGP);
	}

	/** Set A11GDAT.
		@param A11GDAT A11GDAT	  */
	@Override
	public void setA11GDAT (java.sql.Timestamp A11GDAT)
	{
		set_Value (COLUMNNAME_A11GDAT, A11GDAT);
	}

	/** Get A11GDAT.
		@return A11GDAT	  */
	@Override
	public java.sql.Timestamp getA11GDAT ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_A11GDAT);
	}

	/**
	 * A11VERIFIZ AD_Reference_ID=540782
	 * Reference name: Pharma_YesNo
	 */
	public static final int A11VERIFIZ_AD_Reference_ID=540782;
	/** 0 = 0 */
	public static final String A11VERIFIZ_0 = "0";
	/** 1 = 1 */
	public static final String A11VERIFIZ_1 = "1";
	/** Set A11VERIFIZ.
		@param A11VERIFIZ A11VERIFIZ	  */
	@Override
	public void setA11VERIFIZ (java.lang.String A11VERIFIZ)
	{

		set_Value (COLUMNNAME_A11VERIFIZ, A11VERIFIZ);
	}

	/** Get A11VERIFIZ.
		@return A11VERIFIZ	  */
	@Override
	public java.lang.String getA11VERIFIZ ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_A11VERIFIZ);
	}

	/** Set A11VERIPFL.
		@param A11VERIPFL A11VERIPFL	  */
	@Override
	public void setA11VERIPFL (int A11VERIPFL)
	{
		set_Value (COLUMNNAME_A11VERIPFL, Integer.valueOf(A11VERIPFL));
	}

	/** Get A11VERIPFL.
		@return A11VERIPFL	  */
	@Override
	public int getA11VERIPFL ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A11VERIPFL);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A11VERIREA.
		@param A11VERIREA A11VERIREA	  */
	@Override
	public void setA11VERIREA (int A11VERIREA)
	{
		set_Value (COLUMNNAME_A11VERIREA, Integer.valueOf(A11VERIREA));
	}

	/** Get A11VERIREA.
		@return A11VERIREA	  */
	@Override
	public int getA11VERIREA ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A11VERIREA);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set A11VERITES.
		@param A11VERITES A11VERITES	  */
	@Override
	public void setA11VERITES (int A11VERITES)
	{
		set_Value (COLUMNNAME_A11VERITES, Integer.valueOf(A11VERITES));
	}

	/** Get A11VERITES.
		@return A11VERITES	  */
	@Override
	public int getA11VERITES ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A11VERITES);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getAEP_Price_List() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AEP_Price_List_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setAEP_Price_List(org.compiere.model.I_M_PriceList AEP_Price_List)
	{
		set_ValueFromPO(COLUMNNAME_AEP_Price_List_ID, org.compiere.model.I_M_PriceList.class, AEP_Price_List);
	}

	/** Set Price List AEP.
		@param AEP_Price_List_ID Price List AEP	  */
	@Override
	public void setAEP_Price_List_ID (int AEP_Price_List_ID)
	{
		if (AEP_Price_List_ID < 1)
		{
			set_Value (COLUMNNAME_AEP_Price_List_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_AEP_Price_List_ID, Integer.valueOf(AEP_Price_List_ID));
		}
	}

	/** Get Price List AEP.
		@return Price List AEP	  */
	@Override
	public int getAEP_Price_List_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AEP_Price_List_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getAPU_Price_List() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_APU_Price_List_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setAPU_Price_List(org.compiere.model.I_M_PriceList APU_Price_List)
	{
		set_ValueFromPO(COLUMNNAME_APU_Price_List_ID, org.compiere.model.I_M_PriceList.class, APU_Price_List);
	}

	/** Set Price List APU.
		@param APU_Price_List_ID Price List APU	  */
	@Override
	public void setAPU_Price_List_ID (int APU_Price_List_ID)
	{
		if (APU_Price_List_ID < 1)
		{
			set_Value (COLUMNNAME_APU_Price_List_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_APU_Price_List_ID, Integer.valueOf(APU_Price_List_ID));
		}
	}

	/** Get Price List APU.
		@return Price List APU	  */
	@Override
	public int getAPU_Price_List_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_APU_Price_List_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getAVP_Price_List() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AVP_Price_List_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setAVP_Price_List(org.compiere.model.I_M_PriceList AVP_Price_List)
	{
		set_ValueFromPO(COLUMNNAME_AVP_Price_List_ID, org.compiere.model.I_M_PriceList.class, AVP_Price_List);
	}

	/** Set Price List AVP.
		@param AVP_Price_List_ID Price List AVP	  */
	@Override
	public void setAVP_Price_List_ID (int AVP_Price_List_ID)
	{
		if (AVP_Price_List_ID < 1)
		{
			set_Value (COLUMNNAME_AVP_Price_List_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_AVP_Price_List_ID, Integer.valueOf(AVP_Price_List_ID));
		}
	}

	/** Get Price List AVP.
		@return Price List AVP	  */
	@Override
	public int getAVP_Price_List_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AVP_Price_List_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	/** Set Data import.
		@param C_DataImport_ID Data import	  */
	@Override
	public void setC_DataImport_ID (int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_DataImport_ID, Integer.valueOf(C_DataImport_ID));
		}
	}

	/** Get Data import.
		@return Data import	  */
	@Override
	public int getC_DataImport_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DataImport_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg
		Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public java.lang.String getI_ErrorMsg ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/**
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	/** Set Importiert.
		@param I_IsImported
		Ist dieser Import verarbeitet worden?
	  */
	@Override
	public void setI_IsImported (java.lang.String I_IsImported)
	{

		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	/** Get Importiert.
		@return Ist dieser Import verarbeitet worden?
	  */
	@Override
	public java.lang.String getI_IsImported ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_IsImported);
	}

	/** Set Pharma Product.
		@param I_Pharma_Product_ID Pharma Product	  */
	@Override
	public void setI_Pharma_Product_ID (int I_Pharma_Product_ID)
	{
		if (I_Pharma_Product_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_I_Pharma_Product_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_I_Pharma_Product_ID, Integer.valueOf(I_Pharma_Product_ID));
		}
	}

	/** Get Pharma Product.
		@return Pharma Product	  */
	@Override
	public int getI_Pharma_Product_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Pharma_Product_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getKAEP_Price_List() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_KAEP_Price_List_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setKAEP_Price_List(org.compiere.model.I_M_PriceList KAEP_Price_List)
	{
		set_ValueFromPO(COLUMNNAME_KAEP_Price_List_ID, org.compiere.model.I_M_PriceList.class, KAEP_Price_List);
	}

	/** Set Price List KAEP.
		@param KAEP_Price_List_ID Price List KAEP	  */
	@Override
	public void setKAEP_Price_List_ID (int KAEP_Price_List_ID)
	{
		if (KAEP_Price_List_ID < 1)
		{
			set_Value (COLUMNNAME_KAEP_Price_List_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_KAEP_Price_List_ID, Integer.valueOf(KAEP_Price_List_ID));
		}
	}

	/** Get Price List KAEP.
		@return Price List KAEP	  */
	@Override
	public int getKAEP_Price_List_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_KAEP_Price_List_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.model.I_M_DosageForm getM_DosageForm() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_DosageForm_ID, de.metas.vertical.pharma.model.I_M_DosageForm.class);
	}

	@Override
	public void setM_DosageForm(de.metas.vertical.pharma.model.I_M_DosageForm M_DosageForm)
	{
		set_ValueFromPO(COLUMNNAME_M_DosageForm_ID, de.metas.vertical.pharma.model.I_M_DosageForm.class, M_DosageForm);
	}

	/** Set Dosage Form.
		@param M_DosageForm_ID Dosage Form	  */
	@Override
	public void setM_DosageForm_ID (int M_DosageForm_ID)
	{
		if (M_DosageForm_ID < 1)
		{
			set_Value (COLUMNNAME_M_DosageForm_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_M_DosageForm_ID, Integer.valueOf(M_DosageForm_ID));
		}
	}

	/** Get Dosage Form.
		@return Dosage Form	  */
	@Override
	public int getM_DosageForm_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DosageForm_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class);
	}

	@Override
	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class, M_Product_Category);
	}

	/** Set Produkt Kategorie.
		@param M_Product_Category_ID
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1)
		{
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
		}
	}

	/** Get Produkt Kategorie.
		@return Kategorie eines Produktes
	  */
	@Override
	public int getM_Product_Category_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
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
		{
			set_Value (COLUMNNAME_M_Product_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
		}
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getPackage_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Package_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setPackage_UOM(org.compiere.model.I_C_UOM Package_UOM)
	{
		set_ValueFromPO(COLUMNNAME_Package_UOM_ID, org.compiere.model.I_C_UOM.class, Package_UOM);
	}

	/** Set Package UOM.
		@param Package_UOM_ID
		UOM of the package
	  */
	@Override
	public void setPackage_UOM_ID (int Package_UOM_ID)
	{
		if (Package_UOM_ID < 1)
		{
			set_Value (COLUMNNAME_Package_UOM_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_Package_UOM_ID, Integer.valueOf(Package_UOM_ID));
		}
	}

	/** Get Package UOM.
		@return UOM of the package
	  */
	@Override
	public int getPackage_UOM_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Package_UOM_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
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
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_PriceList getUVP_Price_List() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_UVP_Price_List_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setUVP_Price_List(org.compiere.model.I_M_PriceList UVP_Price_List)
	{
		set_ValueFromPO(COLUMNNAME_UVP_Price_List_ID, org.compiere.model.I_M_PriceList.class, UVP_Price_List);
	}

	/** Set Price List UVP.
		@param UVP_Price_List_ID Price List UVP	  */
	@Override
	public void setUVP_Price_List_ID (int UVP_Price_List_ID)
	{
		if (UVP_Price_List_ID < 1)
		{
			set_Value (COLUMNNAME_UVP_Price_List_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_UVP_Price_List_ID, Integer.valueOf(UVP_Price_List_ID));
		}
	}

	/** Get Price List UVP.
		@return Price List UVP	  */
	@Override
	public int getUVP_Price_List_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_UVP_Price_List_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getZBV_Price_List() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_ZBV_Price_List_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setZBV_Price_List(org.compiere.model.I_M_PriceList ZBV_Price_List)
	{
		set_ValueFromPO(COLUMNNAME_ZBV_Price_List_ID, org.compiere.model.I_M_PriceList.class, ZBV_Price_List);
	}

	/** Set Price List ZBV.
		@param ZBV_Price_List_ID Price List ZBV	  */
	@Override
	public void setZBV_Price_List_ID (int ZBV_Price_List_ID)
	{
		if (ZBV_Price_List_ID < 1)
		{
			set_Value (COLUMNNAME_ZBV_Price_List_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_ZBV_Price_List_ID, Integer.valueOf(ZBV_Price_List_ID));
		}
	}

	/** Get Price List ZBV.
		@return Price List ZBV	  */
	@Override
	public int getZBV_Price_List_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZBV_Price_List_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}
}