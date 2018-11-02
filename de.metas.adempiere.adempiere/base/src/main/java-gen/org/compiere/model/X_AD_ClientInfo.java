/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_ClientInfo
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_ClientInfo extends org.compiere.model.PO implements I_AD_ClientInfo, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -205749806L;

    /** Standard Constructor */
    public X_AD_ClientInfo (Properties ctx, int AD_ClientInfo_ID, String trxName)
    {
      super (ctx, AD_ClientInfo_ID, trxName);
      /** if (AD_ClientInfo_ID == 0)
        {
			setIsDiscountLineAmt (false);
        } */
    }

    /** Load Constructor */
    public X_AD_ClientInfo (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Tree getAD_Tree_Activity() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_Activity_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_Activity(org.compiere.model.I_AD_Tree AD_Tree_Activity)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_Activity_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_Activity);
	}

	/** Set Baum Aktivität.
		@param AD_Tree_Activity_ID 
		Tree to determine activity hierarchy
	  */
	@Override
	public void setAD_Tree_Activity_ID (int AD_Tree_Activity_ID)
	{
		if (AD_Tree_Activity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Activity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Activity_ID, Integer.valueOf(AD_Tree_Activity_ID));
	}

	/** Get Baum Aktivität.
		@return Tree to determine activity hierarchy
	  */
	@Override
	public int getAD_Tree_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_BPartner_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_BPartner(org.compiere.model.I_AD_Tree AD_Tree_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_BPartner_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_BPartner);
	}

	/** Set Primärbaum Geschäftspartner.
		@param AD_Tree_BPartner_ID 
		Tree to determine business partner hierarchy
	  */
	@Override
	public void setAD_Tree_BPartner_ID (int AD_Tree_BPartner_ID)
	{
		if (AD_Tree_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_BPartner_ID, Integer.valueOf(AD_Tree_BPartner_ID));
	}

	/** Get Primärbaum Geschäftspartner.
		@return Tree to determine business partner hierarchy
	  */
	@Override
	public int getAD_Tree_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_Campaign() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_Campaign_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_Campaign(org.compiere.model.I_AD_Tree AD_Tree_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_Campaign_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_Campaign);
	}

	/** Set Baum Kampagne.
		@param AD_Tree_Campaign_ID 
		Tree to determine marketing campaign hierarchy
	  */
	@Override
	public void setAD_Tree_Campaign_ID (int AD_Tree_Campaign_ID)
	{
		if (AD_Tree_Campaign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Campaign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Campaign_ID, Integer.valueOf(AD_Tree_Campaign_ID));
	}

	/** Get Baum Kampagne.
		@return Tree to determine marketing campaign hierarchy
	  */
	@Override
	public int getAD_Tree_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_Menu() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_Menu_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_Menu(org.compiere.model.I_AD_Tree AD_Tree_Menu)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_Menu_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_Menu);
	}

	/** Set Primärbaum Menü.
		@param AD_Tree_Menu_ID 
		Tree of the menu
	  */
	@Override
	public void setAD_Tree_Menu_ID (int AD_Tree_Menu_ID)
	{
		if (AD_Tree_Menu_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Menu_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Menu_ID, Integer.valueOf(AD_Tree_Menu_ID));
	}

	/** Get Primärbaum Menü.
		@return Tree of the menu
	  */
	@Override
	public int getAD_Tree_Menu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Menu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_Org() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_Org_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_Org(org.compiere.model.I_AD_Tree AD_Tree_Org)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_Org_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_Org);
	}

	/** Set Primärbaum Organisation.
		@param AD_Tree_Org_ID 
		Tree to determine organizational hierarchy
	  */
	@Override
	public void setAD_Tree_Org_ID (int AD_Tree_Org_ID)
	{
		if (AD_Tree_Org_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Org_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Org_ID, Integer.valueOf(AD_Tree_Org_ID));
	}

	/** Get Primärbaum Organisation.
		@return Tree to determine organizational hierarchy
	  */
	@Override
	public int getAD_Tree_Org_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_Product_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_Product(org.compiere.model.I_AD_Tree AD_Tree_Product)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_Product_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_Product);
	}

	/** Set Primärbaum Produkt.
		@param AD_Tree_Product_ID 
		Tree to determine product hierarchy
	  */
	@Override
	public void setAD_Tree_Product_ID (int AD_Tree_Product_ID)
	{
		if (AD_Tree_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Product_ID, Integer.valueOf(AD_Tree_Product_ID));
	}

	/** Get Primärbaum Produkt.
		@return Tree to determine product hierarchy
	  */
	@Override
	public int getAD_Tree_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_Project() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_Project_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_Project(org.compiere.model.I_AD_Tree AD_Tree_Project)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_Project_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_Project);
	}

	/** Set Primärbaum Projekt.
		@param AD_Tree_Project_ID 
		Tree to determine project hierarchy
	  */
	@Override
	public void setAD_Tree_Project_ID (int AD_Tree_Project_ID)
	{
		if (AD_Tree_Project_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Project_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_Project_ID, Integer.valueOf(AD_Tree_Project_ID));
	}

	/** Get Primärbaum Projekt.
		@return Tree to determine project hierarchy
	  */
	@Override
	public int getAD_Tree_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_SalesRegion() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_SalesRegion_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_SalesRegion(org.compiere.model.I_AD_Tree AD_Tree_SalesRegion)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_SalesRegion_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_SalesRegion);
	}

	/** Set Primärbaum Vertriebsgebiet.
		@param AD_Tree_SalesRegion_ID 
		Tree to determine sales regional hierarchy
	  */
	@Override
	public void setAD_Tree_SalesRegion_ID (int AD_Tree_SalesRegion_ID)
	{
		if (AD_Tree_SalesRegion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_SalesRegion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_SalesRegion_ID, Integer.valueOf(AD_Tree_SalesRegion_ID));
	}

	/** Get Primärbaum Vertriebsgebiet.
		@return Tree to determine sales regional hierarchy
	  */
	@Override
	public int getAD_Tree_SalesRegion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_SalesRegion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

//	@Override
//	public org.compiere.model.I_C_AcctSchema getC_AcctSchema1() throws RuntimeException
//	{
//		return get_ValueAsPO(COLUMNNAME_C_AcctSchema1_ID, org.compiere.model.I_C_AcctSchema.class);
//	}
//
//	@Override
//	public void setC_AcctSchema1(org.compiere.model.I_C_AcctSchema C_AcctSchema1)
//	{
//		set_ValueFromPO(COLUMNNAME_C_AcctSchema1_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema1);
//	}

	/** Set Primäres Buchführungsschema.
		@param C_AcctSchema1_ID 
		Primary rules for accounting
	  */
	@Override
	public void setC_AcctSchema1_ID (int C_AcctSchema1_ID)
	{
		if (C_AcctSchema1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema1_ID, Integer.valueOf(C_AcctSchema1_ID));
	}

	/** Get Primäres Buchführungsschema.
		@return Primary rules for accounting
	  */
	@Override
	public int getC_AcctSchema1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	{
		if (C_BPartnerCashTrx_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerCashTrx_ID, null);
		else 
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
	public org.compiere.model.I_C_Calendar getC_Calendar() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	/** Set Kalender.
		@param C_Calendar_ID 
		Accounting Calendar Name
	  */
	@Override
	public void setC_Calendar_ID (int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
	}

	/** Get Kalender.
		@return Accounting Calendar Name
	  */
	@Override
	public int getC_Calendar_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Calendar_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM_Length() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_Length_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM_Length(org.compiere.model.I_C_UOM C_UOM_Length)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_Length_ID, org.compiere.model.I_C_UOM.class, C_UOM_Length);
	}

	/** Set Maßeinheit für Länge.
		@param C_UOM_Length_ID 
		Standard Unit of Measure for Length
	  */
	@Override
	public void setC_UOM_Length_ID (int C_UOM_Length_ID)
	{
		if (C_UOM_Length_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Length_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Length_ID, Integer.valueOf(C_UOM_Length_ID));
	}

	/** Get Maßeinheit für Länge.
		@return Standard Unit of Measure for Length
	  */
	@Override
	public int getC_UOM_Length_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Length_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM_Time() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_Time_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM_Time(org.compiere.model.I_C_UOM C_UOM_Time)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_Time_ID, org.compiere.model.I_C_UOM.class, C_UOM_Time);
	}

	/** Set Maßeinheit für Zeit.
		@param C_UOM_Time_ID 
		Standard Unit of Measure for Time
	  */
	@Override
	public void setC_UOM_Time_ID (int C_UOM_Time_ID)
	{
		if (C_UOM_Time_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Time_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Time_ID, Integer.valueOf(C_UOM_Time_ID));
	}

	/** Get Maßeinheit für Zeit.
		@return Standard Unit of Measure for Time
	  */
	@Override
	public int getC_UOM_Time_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Time_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM_Volume() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_Volume_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM_Volume(org.compiere.model.I_C_UOM C_UOM_Volume)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_Volume_ID, org.compiere.model.I_C_UOM.class, C_UOM_Volume);
	}

	/** Set Maßeinheit für Volumen.
		@param C_UOM_Volume_ID 
		Standard Unit of Measure for Volume
	  */
	@Override
	public void setC_UOM_Volume_ID (int C_UOM_Volume_ID)
	{
		if (C_UOM_Volume_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Volume_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Volume_ID, Integer.valueOf(C_UOM_Volume_ID));
	}

	/** Get Maßeinheit für Volumen.
		@return Standard Unit of Measure for Volume
	  */
	@Override
	public int getC_UOM_Volume_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Volume_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM_Weight() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_Weight_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM_Weight(org.compiere.model.I_C_UOM C_UOM_Weight)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_Weight_ID, org.compiere.model.I_C_UOM.class, C_UOM_Weight);
	}

	/** Set Maßeinheit für Gewicht.
		@param C_UOM_Weight_ID 
		Standard Unit of Measure for Weight
	  */
	@Override
	public void setC_UOM_Weight_ID (int C_UOM_Weight_ID)
	{
		if (C_UOM_Weight_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Weight_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Weight_ID, Integer.valueOf(C_UOM_Weight_ID));
	}

	/** Get Maßeinheit für Gewicht.
		@return Standard Unit of Measure for Weight
	  */
	@Override
	public int getC_UOM_Weight_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Weight_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rabatt aus Zeilenbeträgen berechnet.
		@param IsDiscountLineAmt 
		Payment Discount calculation does not include Taxes and Charges
	  */
	@Override
	public void setIsDiscountLineAmt (boolean IsDiscountLineAmt)
	{
		set_Value (COLUMNNAME_IsDiscountLineAmt, Boolean.valueOf(IsDiscountLineAmt));
	}

	/** Get Rabatt aus Zeilenbeträgen berechnet.
		@return Payment Discount calculation does not include Taxes and Charges
	  */
	@Override
	public boolean isDiscountLineAmt () 
	{
		Object oo = get_Value(COLUMNNAME_IsDiscountLineAmt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Tage Protokoll aufheben.
		@param KeepLogDays 
		Number of days to keep the log entries
	  */
	@Override
	public void setKeepLogDays (int KeepLogDays)
	{
		set_Value (COLUMNNAME_KeepLogDays, Integer.valueOf(KeepLogDays));
	}

	/** Get Tage Protokoll aufheben.
		@return Number of days to keep the log entries
	  */
	@Override
	public int getKeepLogDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_KeepLogDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Image getLogo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setLogo(org.compiere.model.I_AD_Image Logo)
	{
		set_ValueFromPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class, Logo);
	}

	/** Set Logo.
		@param Logo_ID Logo	  */
	@Override
	public void setLogo_ID (int Logo_ID)
	{
		if (Logo_ID < 1) 
			set_Value (COLUMNNAME_Logo_ID, null);
		else 
			set_Value (COLUMNNAME_Logo_ID, Integer.valueOf(Logo_ID));
	}

	/** Get Logo.
		@return Logo	  */
	@Override
	public int getLogo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Logo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Image getLogoReport() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_LogoReport_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setLogoReport(org.compiere.model.I_AD_Image LogoReport)
	{
		set_ValueFromPO(COLUMNNAME_LogoReport_ID, org.compiere.model.I_AD_Image.class, LogoReport);
	}

	/** Set Logo Report.
		@param LogoReport_ID Logo Report	  */
	@Override
	public void setLogoReport_ID (int LogoReport_ID)
	{
		if (LogoReport_ID < 1) 
			set_Value (COLUMNNAME_LogoReport_ID, null);
		else 
			set_Value (COLUMNNAME_LogoReport_ID, Integer.valueOf(LogoReport_ID));
	}

	/** Get Logo Report.
		@return Logo Report	  */
	@Override
	public int getLogoReport_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LogoReport_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Image getLogoWeb() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_LogoWeb_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setLogoWeb(org.compiere.model.I_AD_Image LogoWeb)
	{
		set_ValueFromPO(COLUMNNAME_LogoWeb_ID, org.compiere.model.I_AD_Image.class, LogoWeb);
	}

	/** Set Logo Web.
		@param LogoWeb_ID Logo Web	  */
	@Override
	public void setLogoWeb_ID (int LogoWeb_ID)
	{
		if (LogoWeb_ID < 1) 
			set_Value (COLUMNNAME_LogoWeb_ID, null);
		else 
			set_Value (COLUMNNAME_LogoWeb_ID, Integer.valueOf(LogoWeb_ID));
	}

	/** Get Logo Web.
		@return Logo Web	  */
	@Override
	public int getLogoWeb_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LogoWeb_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_ProductFreight() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductFreight_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_ProductFreight(org.compiere.model.I_M_Product M_ProductFreight)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductFreight_ID, org.compiere.model.I_M_Product.class, M_ProductFreight);
	}

	/** Set Produkt für Fracht.
		@param M_ProductFreight_ID Produkt für Fracht	  */
	@Override
	public void setM_ProductFreight_ID (int M_ProductFreight_ID)
	{
		if (M_ProductFreight_ID < 1) 
			set_Value (COLUMNNAME_M_ProductFreight_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductFreight_ID, Integer.valueOf(M_ProductFreight_ID));
	}

	/** Get Produkt für Fracht.
		@return Produkt für Fracht	  */
	@Override
	public int getM_ProductFreight_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductFreight_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}