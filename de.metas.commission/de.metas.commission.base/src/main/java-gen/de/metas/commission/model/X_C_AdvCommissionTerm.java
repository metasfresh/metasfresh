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
package de.metas.commission.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_AdvCommissionTerm
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_AdvCommissionTerm extends org.compiere.model.PO implements I_C_AdvCommissionTerm, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 178371593L;

    /** Standard Constructor */
    public X_C_AdvCommissionTerm (Properties ctx, int C_AdvCommissionTerm_ID, String trxName)
    {
      super (ctx, C_AdvCommissionTerm_ID, trxName);
      /** if (C_AdvCommissionTerm_ID == 0)
        {
			setC_AdvCommissionCondition_ID (0);
			setC_AdvCommissionTerm_ID (0);
			setC_AdvComSystem_ID (0);
			setC_AdvComSystem_Type_ID (0);
			setIsSalesRepFactCollector (false);
// N
			setName (null);
			setSeqNo (0);
// 0
        } */
    }

    /** Load Constructor */
    public X_C_AdvCommissionTerm (Properties ctx, ResultSet rs, String trxName)
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
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_AdvCommissionTerm[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public de.metas.commission.model.I_C_AdvCommissionCondition getC_AdvCommissionCondition() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AdvCommissionCondition_ID, de.metas.commission.model.I_C_AdvCommissionCondition.class);
	}

	@Override
	public void setC_AdvCommissionCondition(de.metas.commission.model.I_C_AdvCommissionCondition C_AdvCommissionCondition)
	{
		set_ValueFromPO(COLUMNNAME_C_AdvCommissionCondition_ID, de.metas.commission.model.I_C_AdvCommissionCondition.class, C_AdvCommissionCondition);
	}

	/** Set Provisionsvertrag.
		@param C_AdvCommissionCondition_ID Provisionsvertrag	  */
	@Override
	public void setC_AdvCommissionCondition_ID (int C_AdvCommissionCondition_ID)
	{
		if (C_AdvCommissionCondition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionCondition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionCondition_ID, Integer.valueOf(C_AdvCommissionCondition_ID));
	}

	/** Get Provisionsvertrag.
		@return Provisionsvertrag	  */
	@Override
	public int getC_AdvCommissionCondition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionCondition_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionsart.
		@param C_AdvCommissionTerm_ID Provisionsart	  */
	@Override
	public void setC_AdvCommissionTerm_ID (int C_AdvCommissionTerm_ID)
	{
		if (C_AdvCommissionTerm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionTerm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionTerm_ID, Integer.valueOf(C_AdvCommissionTerm_ID));
	}

	/** Get Provisionsart.
		@return Provisionsart	  */
	@Override
	public int getC_AdvCommissionTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AdvComSystem_ID, de.metas.commission.model.I_C_AdvComSystem.class);
	}

	@Override
	public void setC_AdvComSystem(de.metas.commission.model.I_C_AdvComSystem C_AdvComSystem)
	{
		set_ValueFromPO(COLUMNNAME_C_AdvComSystem_ID, de.metas.commission.model.I_C_AdvComSystem.class, C_AdvComSystem);
	}

	/** Set Vergütungsplan.
		@param C_AdvComSystem_ID Vergütungsplan	  */
	@Override
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID)
	{
		if (C_AdvComSystem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_ID, Integer.valueOf(C_AdvComSystem_ID));
	}

	/** Get Vergütungsplan.
		@return Vergütungsplan	  */
	@Override
	public int getC_AdvComSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.commission.model.I_C_AdvComSystem_Type getC_AdvComSystem_Type() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AdvComSystem_Type_ID, de.metas.commission.model.I_C_AdvComSystem_Type.class);
	}

	@Override
	public void setC_AdvComSystem_Type(de.metas.commission.model.I_C_AdvComSystem_Type C_AdvComSystem_Type)
	{
		set_ValueFromPO(COLUMNNAME_C_AdvComSystem_Type_ID, de.metas.commission.model.I_C_AdvComSystem_Type.class, C_AdvComSystem_Type);
	}

	/** Set Vergütungsplan - Provisionsart.
		@param C_AdvComSystem_Type_ID Vergütungsplan - Provisionsart	  */
	@Override
	public void setC_AdvComSystem_Type_ID (int C_AdvComSystem_Type_ID)
	{
		if (C_AdvComSystem_Type_ID < 1) 
			set_Value (COLUMNNAME_C_AdvComSystem_Type_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComSystem_Type_ID, Integer.valueOf(C_AdvComSystem_Type_ID));
	}

	/** Get Vergütungsplan - Provisionsart.
		@return Vergütungsplan - Provisionsart	  */
	@Override
	public int getC_AdvComSystem_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSystem_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.commission.model.I_C_AdvCommissionTerm getC_AdvComTermSRFCollector() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AdvComTermSRFCollector_ID, de.metas.commission.model.I_C_AdvCommissionTerm.class);
	}

	@Override
	public void setC_AdvComTermSRFCollector(de.metas.commission.model.I_C_AdvCommissionTerm C_AdvComTermSRFCollector)
	{
		set_ValueFromPO(COLUMNNAME_C_AdvComTermSRFCollector_ID, de.metas.commission.model.I_C_AdvCommissionTerm.class, C_AdvComTermSRFCollector);
	}

	/** Set VP-Datensammler.
		@param C_AdvComTermSRFCollector_ID 
		Referenz auf Provisionsart, die VP (Sponsor) Daten bereit hält (z.B. Rang, Kompression)
	  */
	@Override
	public void setC_AdvComTermSRFCollector_ID (int C_AdvComTermSRFCollector_ID)
	{
		if (C_AdvComTermSRFCollector_ID < 1) 
			set_Value (COLUMNNAME_C_AdvComTermSRFCollector_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComTermSRFCollector_ID, Integer.valueOf(C_AdvComTermSRFCollector_ID));
	}

	/** Get VP-Datensammler.
		@return Referenz auf Provisionsart, die VP (Sponsor) Daten bereit hält (z.B. Rang, Kompression)
	  */
	@Override
	public int getC_AdvComTermSRFCollector_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComTermSRFCollector_ID);
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
	public org.eevolution.model.I_HR_Concept getHR_Concept() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HR_Concept_ID, org.eevolution.model.I_HR_Concept.class);
	}

	@Override
	public void setHR_Concept(org.eevolution.model.I_HR_Concept HR_Concept)
	{
		set_ValueFromPO(COLUMNNAME_HR_Concept_ID, org.eevolution.model.I_HR_Concept.class, HR_Concept);
	}

	/** Set Abrechnungsposten.
		@param HR_Concept_ID Abrechnungsposten	  */
	@Override
	public void setHR_Concept_ID (int HR_Concept_ID)
	{
		if (HR_Concept_ID < 1) 
			set_Value (COLUMNNAME_HR_Concept_ID, null);
		else 
			set_Value (COLUMNNAME_HR_Concept_ID, Integer.valueOf(HR_Concept_ID));
	}

	/** Get Abrechnungsposten.
		@return Abrechnungsposten	  */
	@Override
	public int getHR_Concept_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HR_Concept_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Interner Name.
		@param InternalName 
		Eindeutiger system-interner Bezeichner des Datensatzes.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_ValueNoCheck (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Eindeutiger system-interner Bezeichner des Datensatzes.
	  */
	@Override
	public java.lang.String getInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

	/** Set Sammelt VP-Daten.
		@param IsSalesRepFactCollector 
		Provisionsart schreibt keine Daten in den Buchauszug, sonder erstellt Sponsor-bezogene Daten wie z.B. APV
	  */
	@Override
	public void setIsSalesRepFactCollector (boolean IsSalesRepFactCollector)
	{
		set_Value (COLUMNNAME_IsSalesRepFactCollector, Boolean.valueOf(IsSalesRepFactCollector));
	}

	/** Get Sammelt VP-Daten.
		@return Provisionsart schreibt keine Daten in den Buchauszug, sonder erstellt Sponsor-bezogene Daten wie z.B. APV
	  */
	@Override
	public boolean isSalesRepFactCollector () 
	{
		Object oo = get_Value(COLUMNNAME_IsSalesRepFactCollector);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Produkt-Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt-Kategorie.
		@return Kategorie eines Produktes
	  */
	@Override
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
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

	/** Set Gð¬´©g ab.
		@param ValidFrom 
		Gð¬´©g ab inklusiv (erster Tag)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gð¬´©g ab.
		@return Gð¬´©g ab inklusiv (erster Tag)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}