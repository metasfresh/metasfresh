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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for GL_DistributionLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_GL_DistributionLine extends org.compiere.model.PO implements I_GL_DistributionLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2055862632L;

    /** Standard Constructor */
    public X_GL_DistributionLine (Properties ctx, int GL_DistributionLine_ID, String trxName)
    {
      super (ctx, GL_DistributionLine_ID, trxName);
      /** if (GL_DistributionLine_ID == 0)
        {
			setGL_Distribution_ID (0);
			setGL_DistributionLine_ID (0);
			setLine (0);
// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM GL_DistributionLine WHERE GL_Distribution_ID=@GL_Distribution_ID@
			setOverwriteAcct (false);
			setOverwriteActivity (false);
			setOverwriteBPartner (false);
			setOverwriteCampaign (false);
			setOverwriteLocFrom (false);
			setOverwriteLocTo (false);
			setOverwriteOrg (false);
			setOverwriteOrgTrx (false);
			setOverwriteProduct (false);
			setOverwriteProject (false);
			setOverwriteSalesRegion (false);
			setOverwriteUser1 (false);
			setOverwriteUser2 (false);
			setPercent (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_GL_DistributionLine (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Konto.
		@param Account_ID 
		Account used
	  */
	@Override
	public void setAccount_ID (int Account_ID)
	{
		if (Account_ID < 1) 
			set_Value (COLUMNNAME_Account_ID, null);
		else 
			set_Value (COLUMNNAME_Account_ID, Integer.valueOf(Account_ID));
	}

	/** Get Konto.
		@return Account used
	  */
	@Override
	public int getAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_OrgTrx_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx)
	{
		set_ValueFromPO(COLUMNNAME_AD_OrgTrx_ID, org.compiere.model.I_AD_Org.class, AD_OrgTrx);
	}

	/** Set Buchende Organisation.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	@Override
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Buchende Organisation.
		@return Performing or initiating organization
	  */
	@Override
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class);
	}

	@Override
	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity)
	{
		set_ValueFromPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class, C_Activity);
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
		Identifies a Business Partner
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
		@return Identifies a Business Partner
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
	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	/** Set Werbemassnahme.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	@Override
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Werbemassnahme.
		@return Marketing Campaign
	  */
	@Override
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Location getC_LocFrom() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_LocFrom_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_LocFrom(org.compiere.model.I_C_Location C_LocFrom)
	{
		set_ValueFromPO(COLUMNNAME_C_LocFrom_ID, org.compiere.model.I_C_Location.class, C_LocFrom);
	}

	/** Set Von Ort.
		@param C_LocFrom_ID 
		Location that inventory was moved from
	  */
	@Override
	public void setC_LocFrom_ID (int C_LocFrom_ID)
	{
		if (C_LocFrom_ID < 1) 
			set_Value (COLUMNNAME_C_LocFrom_ID, null);
		else 
			set_Value (COLUMNNAME_C_LocFrom_ID, Integer.valueOf(C_LocFrom_ID));
	}

	/** Get Von Ort.
		@return Location that inventory was moved from
	  */
	@Override
	public int getC_LocFrom_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_LocFrom_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Location getC_LocTo() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_LocTo_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_LocTo(org.compiere.model.I_C_Location C_LocTo)
	{
		set_ValueFromPO(COLUMNNAME_C_LocTo_ID, org.compiere.model.I_C_Location.class, C_LocTo);
	}

	/** Set Nach Ort.
		@param C_LocTo_ID 
		Location that inventory was moved to
	  */
	@Override
	public void setC_LocTo_ID (int C_LocTo_ID)
	{
		if (C_LocTo_ID < 1) 
			set_Value (COLUMNNAME_C_LocTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_LocTo_ID, Integer.valueOf(C_LocTo_ID));
	}

	/** Get Nach Ort.
		@return Location that inventory was moved to
	  */
	@Override
	public int getC_LocTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_LocTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_ID, org.compiere.model.I_C_Project.class);
	}

	@Override
	public void setC_Project(org.compiere.model.I_C_Project C_Project)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_ID, org.compiere.model.I_C_Project.class, C_Project);
	}

	/** Set Projekt.
		@param C_Project_ID 
		Financial Project
	  */
	@Override
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Projekt.
		@return Financial Project
	  */
	@Override
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_SalesRegion getC_SalesRegion() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class);
	}

	@Override
	public void setC_SalesRegion(org.compiere.model.I_C_SalesRegion C_SalesRegion)
	{
		set_ValueFromPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class, C_SalesRegion);
	}

	/** Set Vertriebsgebiet.
		@param C_SalesRegion_ID 
		Sales coverage region
	  */
	@Override
	public void setC_SalesRegion_ID (int C_SalesRegion_ID)
	{
		if (C_SalesRegion_ID < 1) 
			set_Value (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_Value (COLUMNNAME_C_SalesRegion_ID, Integer.valueOf(C_SalesRegion_ID));
	}

	/** Get Vertriebsgebiet.
		@return Sales coverage region
	  */
	@Override
	public int getC_SalesRegion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_SalesRegion_ID);
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

	@Override
	public org.compiere.model.I_GL_Distribution getGL_Distribution() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GL_Distribution_ID, org.compiere.model.I_GL_Distribution.class);
	}

	@Override
	public void setGL_Distribution(org.compiere.model.I_GL_Distribution GL_Distribution)
	{
		set_ValueFromPO(COLUMNNAME_GL_Distribution_ID, org.compiere.model.I_GL_Distribution.class, GL_Distribution);
	}

	/** Set Hauptbuch - Aufteilung.
		@param GL_Distribution_ID 
		General Ledger Distribution
	  */
	@Override
	public void setGL_Distribution_ID (int GL_Distribution_ID)
	{
		if (GL_Distribution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_Distribution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_Distribution_ID, Integer.valueOf(GL_Distribution_ID));
	}

	/** Get Hauptbuch - Aufteilung.
		@return General Ledger Distribution
	  */
	@Override
	public int getGL_Distribution_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Distribution_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GL Distribution Line.
		@param GL_DistributionLine_ID 
		General Ledger Distribution Line
	  */
	@Override
	public void setGL_DistributionLine_ID (int GL_DistributionLine_ID)
	{
		if (GL_DistributionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_DistributionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_DistributionLine_ID, Integer.valueOf(GL_DistributionLine_ID));
	}

	/** Get GL Distribution Line.
		@return General Ledger Distribution Line
	  */
	@Override
	public int getGL_DistributionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_DistributionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zeile Nr..
		@param Line 
		Unique line for this document
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Unique line for this document
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
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

	@Override
	public org.compiere.model.I_AD_Org getOrg() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Org_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setOrg(org.compiere.model.I_AD_Org Org)
	{
		set_ValueFromPO(COLUMNNAME_Org_ID, org.compiere.model.I_AD_Org.class, Org);
	}

	/** Set Organisation.
		@param Org_ID 
		Organizational entity within client
	  */
	@Override
	public void setOrg_ID (int Org_ID)
	{
		if (Org_ID < 1) 
			set_Value (COLUMNNAME_Org_ID, null);
		else 
			set_Value (COLUMNNAME_Org_ID, Integer.valueOf(Org_ID));
	}

	/** Get Organisation.
		@return Organizational entity within client
	  */
	@Override
	public int getOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Overwrite Account.
		@param OverwriteAcct 
		Overwrite the account segment Account with the value specified
	  */
	@Override
	public void setOverwriteAcct (boolean OverwriteAcct)
	{
		set_Value (COLUMNNAME_OverwriteAcct, Boolean.valueOf(OverwriteAcct));
	}

	/** Get Overwrite Account.
		@return Overwrite the account segment Account with the value specified
	  */
	@Override
	public boolean isOverwriteAcct () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteAcct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Activity.
		@param OverwriteActivity 
		Overwrite the account segment Activity with the value specified
	  */
	@Override
	public void setOverwriteActivity (boolean OverwriteActivity)
	{
		set_Value (COLUMNNAME_OverwriteActivity, Boolean.valueOf(OverwriteActivity));
	}

	/** Get Overwrite Activity.
		@return Overwrite the account segment Activity with the value specified
	  */
	@Override
	public boolean isOverwriteActivity () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteActivity);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Bus.Partner.
		@param OverwriteBPartner 
		Overwrite the account segment Business Partner with the value specified
	  */
	@Override
	public void setOverwriteBPartner (boolean OverwriteBPartner)
	{
		set_Value (COLUMNNAME_OverwriteBPartner, Boolean.valueOf(OverwriteBPartner));
	}

	/** Get Overwrite Bus.Partner.
		@return Overwrite the account segment Business Partner with the value specified
	  */
	@Override
	public boolean isOverwriteBPartner () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteBPartner);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Campaign.
		@param OverwriteCampaign 
		Overwrite the account segment Campaign with the value specified
	  */
	@Override
	public void setOverwriteCampaign (boolean OverwriteCampaign)
	{
		set_Value (COLUMNNAME_OverwriteCampaign, Boolean.valueOf(OverwriteCampaign));
	}

	/** Get Overwrite Campaign.
		@return Overwrite the account segment Campaign with the value specified
	  */
	@Override
	public boolean isOverwriteCampaign () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteCampaign);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Location From.
		@param OverwriteLocFrom 
		Overwrite the account segment Location From with the value specified
	  */
	@Override
	public void setOverwriteLocFrom (boolean OverwriteLocFrom)
	{
		set_Value (COLUMNNAME_OverwriteLocFrom, Boolean.valueOf(OverwriteLocFrom));
	}

	/** Get Overwrite Location From.
		@return Overwrite the account segment Location From with the value specified
	  */
	@Override
	public boolean isOverwriteLocFrom () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteLocFrom);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Location To.
		@param OverwriteLocTo 
		Overwrite the account segment Location From with the value specified
	  */
	@Override
	public void setOverwriteLocTo (boolean OverwriteLocTo)
	{
		set_Value (COLUMNNAME_OverwriteLocTo, Boolean.valueOf(OverwriteLocTo));
	}

	/** Get Overwrite Location To.
		@return Overwrite the account segment Location From with the value specified
	  */
	@Override
	public boolean isOverwriteLocTo () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteLocTo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Organization.
		@param OverwriteOrg 
		Overwrite the account segment Organization with the value specified
	  */
	@Override
	public void setOverwriteOrg (boolean OverwriteOrg)
	{
		set_Value (COLUMNNAME_OverwriteOrg, Boolean.valueOf(OverwriteOrg));
	}

	/** Get Overwrite Organization.
		@return Overwrite the account segment Organization with the value specified
	  */
	@Override
	public boolean isOverwriteOrg () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteOrg);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Trx Organuzation.
		@param OverwriteOrgTrx 
		Overwrite the account segment Transaction Organization with the value specified
	  */
	@Override
	public void setOverwriteOrgTrx (boolean OverwriteOrgTrx)
	{
		set_Value (COLUMNNAME_OverwriteOrgTrx, Boolean.valueOf(OverwriteOrgTrx));
	}

	/** Get Overwrite Trx Organuzation.
		@return Overwrite the account segment Transaction Organization with the value specified
	  */
	@Override
	public boolean isOverwriteOrgTrx () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteOrgTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Product.
		@param OverwriteProduct 
		Overwrite the account segment Product with the value specified
	  */
	@Override
	public void setOverwriteProduct (boolean OverwriteProduct)
	{
		set_Value (COLUMNNAME_OverwriteProduct, Boolean.valueOf(OverwriteProduct));
	}

	/** Get Overwrite Product.
		@return Overwrite the account segment Product with the value specified
	  */
	@Override
	public boolean isOverwriteProduct () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteProduct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Project.
		@param OverwriteProject 
		Overwrite the account segment Project with the value specified
	  */
	@Override
	public void setOverwriteProject (boolean OverwriteProject)
	{
		set_Value (COLUMNNAME_OverwriteProject, Boolean.valueOf(OverwriteProject));
	}

	/** Get Overwrite Project.
		@return Overwrite the account segment Project with the value specified
	  */
	@Override
	public boolean isOverwriteProject () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteProject);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Sales Region.
		@param OverwriteSalesRegion 
		Overwrite the account segment Sales Region with the value specified
	  */
	@Override
	public void setOverwriteSalesRegion (boolean OverwriteSalesRegion)
	{
		set_Value (COLUMNNAME_OverwriteSalesRegion, Boolean.valueOf(OverwriteSalesRegion));
	}

	/** Get Overwrite Sales Region.
		@return Overwrite the account segment Sales Region with the value specified
	  */
	@Override
	public boolean isOverwriteSalesRegion () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteSalesRegion);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite User1.
		@param OverwriteUser1 
		Overwrite the account segment User 1 with the value specified
	  */
	@Override
	public void setOverwriteUser1 (boolean OverwriteUser1)
	{
		set_Value (COLUMNNAME_OverwriteUser1, Boolean.valueOf(OverwriteUser1));
	}

	/** Get Overwrite User1.
		@return Overwrite the account segment User 1 with the value specified
	  */
	@Override
	public boolean isOverwriteUser1 () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteUser1);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite User2.
		@param OverwriteUser2 
		Overwrite the account segment User 2 with the value specified
	  */
	@Override
	public void setOverwriteUser2 (boolean OverwriteUser2)
	{
		set_Value (COLUMNNAME_OverwriteUser2, Boolean.valueOf(OverwriteUser2));
	}

	/** Get Overwrite User2.
		@return Overwrite the account segment User 2 with the value specified
	  */
	@Override
	public boolean isOverwriteUser2 () 
	{
		Object oo = get_Value(COLUMNNAME_OverwriteUser2);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Percent.
		@param Percent 
		Percentage
	  */
	@Override
	public void setPercent (java.math.BigDecimal Percent)
	{
		set_Value (COLUMNNAME_Percent, Percent);
	}

	/** Get Percent.
		@return Percentage
	  */
	@Override
	public java.math.BigDecimal getPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Percent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	/** Set Nutzer 1.
		@param User1_ID 
		User defined list element #1
	  */
	@Override
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get Nutzer 1.
		@return User defined list element #1
	  */
	@Override
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	/** Set Nutzer 2.
		@param User2_ID 
		User defined list element #2
	  */
	@Override
	public void setUser2_ID (int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
	}

	/** Get Nutzer 2.
		@return User defined list element #2
	  */
	@Override
	public int getUser2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}