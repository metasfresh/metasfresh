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
package de.metas.picking.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_PickingSlot
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_PickingSlot extends org.compiere.model.PO implements I_M_PickingSlot, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -823061095L;

    /** Standard Constructor */
    public X_M_PickingSlot (Properties ctx, int M_PickingSlot_ID, String trxName)
    {
      super (ctx, M_PickingSlot_ID, trxName);
      /** if (M_PickingSlot_ID == 0)
        {
			setIsDynamic (false);
// N
			setM_PickingSlot_ID (0);
			setM_Warehouse_ID (0);
			setPickingSlot (null);
        } */
    }

    /** Load Constructor */
    public X_M_PickingSlot (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_PickingSlot[")
        .append(get_ID()).append("]");
      return sb.toString();
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
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Is Dynamic.
		@param IsDynamic Is Dynamic	  */
	@Override
	public void setIsDynamic (boolean IsDynamic)
	{
		set_Value (COLUMNNAME_IsDynamic, Boolean.valueOf(IsDynamic));
	}

	/** Get Is Dynamic.
		@return Is Dynamic	  */
	@Override
	public boolean isDynamic () 
	{
		Object oo = get_Value(COLUMNNAME_IsDynamic);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Picking Slot.
		@param M_PickingSlot_ID Picking Slot	  */
	@Override
	public void setM_PickingSlot_ID (int M_PickingSlot_ID)
	{
		if (M_PickingSlot_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PickingSlot_ID, Integer.valueOf(M_PickingSlot_ID));
	}

	/** Get Picking Slot.
		@return Picking Slot	  */
	@Override
	public int getM_PickingSlot_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PickingSlot_ID);
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
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * PickingSlot AD_Reference_ID=540426
	 * Reference name: PickingSlot
	 */
	public static final int PICKINGSLOT_AD_Reference_ID=540426;
	/** 01.1 = 01.1 */
	public static final String PICKINGSLOT_011 = "01.1";
	/** 01.2 = 01.2 */
	public static final String PICKINGSLOT_012 = "01.2";
	/** 01.3 = 01.3 */
	public static final String PICKINGSLOT_013 = "01.3";
	/** 01.4 = 01.4 */
	public static final String PICKINGSLOT_014 = "01.4";
	/** 01.5 = 01.5 */
	public static final String PICKINGSLOT_015 = "01.5";
	/** 02.1 = 02.1 */
	public static final String PICKINGSLOT_021 = "02.1";
	/** 02.2 = 02.2 */
	public static final String PICKINGSLOT_022 = "02.2";
	/** 02.3 = 02.3 */
	public static final String PICKINGSLOT_023 = "02.3";
	/** 02.4 = 02.4 */
	public static final String PICKINGSLOT_024 = "02.4";
	/** 02.5 = 02.5 */
	public static final String PICKINGSLOT_025 = "02.5";
	/** 03.1 = 03.1 */
	public static final String PICKINGSLOT_031 = "03.1";
	/** 03.2 = 03.2 */
	public static final String PICKINGSLOT_032 = "03.2";
	/** 03.3 = 03.3 */
	public static final String PICKINGSLOT_033 = "03.3";
	/** 03.4 = 03.4 */
	public static final String PICKINGSLOT_034 = "03.4";
	/** 03.5 = 03.5 */
	public static final String PICKINGSLOT_035 = "03.5";
	/** 04.1 = 04.1 */
	public static final String PICKINGSLOT_041 = "04.1";
	/** 04.2 = 04.2 */
	public static final String PICKINGSLOT_042 = "04.2";
	/** 04.3 = 04.3 */
	public static final String PICKINGSLOT_043 = "04.3";
	/** 04.4 = 04.4 */
	public static final String PICKINGSLOT_044 = "04.4";
	/** 04.5 = 04.5 */
	public static final String PICKINGSLOT_045 = "04.5";
	/** 05.1 = 05.1 */
	public static final String PICKINGSLOT_051 = "05.1";
	/** 05.2 = 05.2 */
	public static final String PICKINGSLOT_052 = "05.2";
	/** 05.3 = 05.3 */
	public static final String PICKINGSLOT_053 = "05.3";
	/** 05.4 = 05.4 */
	public static final String PICKINGSLOT_054 = "05.4";
	/** 05.5 = 05.5 */
	public static final String PICKINGSLOT_055 = "05.5";
	/** 06.1 = 06.1 */
	public static final String PICKINGSLOT_061 = "06.1";
	/** 06.2 = 06.2 */
	public static final String PICKINGSLOT_062 = "06.2";
	/** 06.3 = 06.3 */
	public static final String PICKINGSLOT_063 = "06.3";
	/** 06.4 = 06.4 */
	public static final String PICKINGSLOT_064 = "06.4";
	/** 06.5 = 06.5 */
	public static final String PICKINGSLOT_065 = "06.5";
	/** 07.1 = 07.1 */
	public static final String PICKINGSLOT_071 = "07.1";
	/** 07.2 = 07.2 */
	public static final String PICKINGSLOT_072 = "07.2";
	/** 07.3 = 07.3 */
	public static final String PICKINGSLOT_073 = "07.3";
	/** 07.4 = 07.4 */
	public static final String PICKINGSLOT_074 = "07.4";
	/** 07.5 = 07.5 */
	public static final String PICKINGSLOT_075 = "07.5";
	/** 08.1 = 08.1 */
	public static final String PICKINGSLOT_081 = "08.1";
	/** 08.2 = 08.2 */
	public static final String PICKINGSLOT_082 = "08.2";
	/** 08.3 = 08.3 */
	public static final String PICKINGSLOT_083 = "08.3";
	/** 08.4 = 08.4 */
	public static final String PICKINGSLOT_084 = "08.4";
	/** 08.5 = 08.5 */
	public static final String PICKINGSLOT_085 = "08.5";
	/** 09.1 = 09.1 */
	public static final String PICKINGSLOT_091 = "09.1";
	/** 09.2 = 09.2 */
	public static final String PICKINGSLOT_092 = "09.2";
	/** 09.3 = 09.3 */
	public static final String PICKINGSLOT_093 = "09.3";
	/** 09.4 = 09.4 */
	public static final String PICKINGSLOT_094 = "09.4";
	/** 09.5 = 09.5 */
	public static final String PICKINGSLOT_095 = "09.5";
	/** 10.1 = 10.1 */
	public static final String PICKINGSLOT_101 = "10.1";
	/** 11.1 = 11.1 */
	public static final String PICKINGSLOT_111 = "11.1";
	/** 11.2 = 11.2 */
	public static final String PICKINGSLOT_112 = "11.2";
	/** 11.3 = 11.3 */
	public static final String PICKINGSLOT_113 = "11.3";
	/** 11.4 = 11.4 */
	public static final String PICKINGSLOT_114 = "11.4";
	/** 11.5 = 11.5 */
	public static final String PICKINGSLOT_115 = "11.5";
	/** 12.1 = 12.1 */
	public static final String PICKINGSLOT_121 = "12.1";
	/** 12.2 = 12.2 */
	public static final String PICKINGSLOT_122 = "12.2";
	/** 12.3 = 12.3 */
	public static final String PICKINGSLOT_123 = "12.3";
	/** 12.4 = 12.4 */
	public static final String PICKINGSLOT_124 = "12.4";
	/** 12.5 = 12.5 */
	public static final String PICKINGSLOT_125 = "12.5";
	/** 13.1 = 13.1 */
	public static final String PICKINGSLOT_131 = "13.1";
	/** 13.2 = 13.2 */
	public static final String PICKINGSLOT_132 = "13.2";
	/** 13.3 = 13.3 */
	public static final String PICKINGSLOT_133 = "13.3";
	/** 13.4 = 13.4 */
	public static final String PICKINGSLOT_134 = "13.4";
	/** 13.5 = 13.5 */
	public static final String PICKINGSLOT_135 = "13.5";
	/** 14.1 = 14.1 */
	public static final String PICKINGSLOT_141 = "14.1";
	/** 14.2 = 14.2 */
	public static final String PICKINGSLOT_142 = "14.2";
	/** 14.3 = 14.3 */
	public static final String PICKINGSLOT_143 = "14.3";
	/** 14.4 = 14.4 */
	public static final String PICKINGSLOT_144 = "14.4";
	/** 14.5 = 14.5 */
	public static final String PICKINGSLOT_145 = "14.5";
	/** 15.1 = 15.1 */
	public static final String PICKINGSLOT_151 = "15.1";
	/** 15.2 = 15.2 */
	public static final String PICKINGSLOT_152 = "15.2";
	/** 15.3 = 15.3 */
	public static final String PICKINGSLOT_153 = "15.3";
	/** 15.4 = 15.4 */
	public static final String PICKINGSLOT_154 = "15.4";
	/** 15.5 = 15.5 */
	public static final String PICKINGSLOT_155 = "15.5";
	/** 16.1 = 16.1 */
	public static final String PICKINGSLOT_161 = "16.1";
	/** 16.2 = 16.2 */
	public static final String PICKINGSLOT_162 = "16.2";
	/** 16.3 = 16.3 */
	public static final String PICKINGSLOT_163 = "16.3";
	/** 16.4 = 16.4 */
	public static final String PICKINGSLOT_164 = "16.4";
	/** 16.5 = 16.5 */
	public static final String PICKINGSLOT_165 = "16.5";
	/** 17.1 = 17.1 */
	public static final String PICKINGSLOT_171 = "17.1";
	/** 17.2 = 17.2 */
	public static final String PICKINGSLOT_172 = "17.2";
	/** 17.3 = 17.3 */
	public static final String PICKINGSLOT_173 = "17.3";
	/** 17.4 = 17.4 */
	public static final String PICKINGSLOT_174 = "17.4";
	/** 17.5 = 17.5 */
	public static final String PICKINGSLOT_175 = "17.5";
	/** 18.1 = 18.1 */
	public static final String PICKINGSLOT_181 = "18.1";
	/** 18.2 = 18.2 */
	public static final String PICKINGSLOT_182 = "18.2";
	/** 18.3 = 18.3 */
	public static final String PICKINGSLOT_183 = "18.3";
	/** 18.4 = 18.4 */
	public static final String PICKINGSLOT_184 = "18.4";
	/** 18.5 = 18.5 */
	public static final String PICKINGSLOT_185 = "18.5";
	/** 19.1 = 19.1 */
	public static final String PICKINGSLOT_191 = "19.1";
	/** 19.2 = 19.2 */
	public static final String PICKINGSLOT_192 = "19.2";
	/** 19.3 = 19.3 */
	public static final String PICKINGSLOT_193 = "19.3";
	/** 19.4 = 19.4 */
	public static final String PICKINGSLOT_194 = "19.4";
	/** 19.5 = 19.5 */
	public static final String PICKINGSLOT_195 = "19.5";
	/** 20.1 = 20.1 */
	public static final String PICKINGSLOT_201 = "20.1";
	/** 20.2 = 20.2 */
	public static final String PICKINGSLOT_202 = "20.2";
	/** 20.3 = 20.3 */
	public static final String PICKINGSLOT_203 = "20.3";
	/** 20.4 = 20.4 */
	public static final String PICKINGSLOT_204 = "20.4";
	/** 20.5 = 20.5 */
	public static final String PICKINGSLOT_205 = "20.5";
	/** 21.1 = 21.1 */
	public static final String PICKINGSLOT_211 = "21.1";
	/** 21.2 = 21.2 */
	public static final String PICKINGSLOT_212 = "21.2";
	/** 21.3 = 21.3 */
	public static final String PICKINGSLOT_213 = "21.3";
	/** 21.4 = 21.4 */
	public static final String PICKINGSLOT_214 = "21.4";
	/** 21.5 = 21.5 */
	public static final String PICKINGSLOT_215 = "21.5";
	/** 22.1 = 22.1 */
	public static final String PICKINGSLOT_221 = "22.1";
	/** 22.2 = 22.2 */
	public static final String PICKINGSLOT_222 = "22.2";
	/** 22.3 = 22.3 */
	public static final String PICKINGSLOT_223 = "22.3";
	/** 22.4 = 22.4 */
	public static final String PICKINGSLOT_224 = "22.4";
	/** 22.5 = 22.5 */
	public static final String PICKINGSLOT_225 = "22.5";
	/** 10.4 = 10.4 */
	public static final String PICKINGSLOT_104 = "10.4";
	/** 10.5 = 10.5 */
	public static final String PICKINGSLOT_105 = "10.5";
	/** 00.0 = 00.0 */
	public static final String PICKINGSLOT_000 = "00.0";
	/** 00.1 = 00.1 */
	public static final String PICKINGSLOT_001 = "00.1";
	/** 00.2 = 00.2 */
	public static final String PICKINGSLOT_002 = "00.2";
	/** 00.3 = 00.3 */
	public static final String PICKINGSLOT_003 = "00.3";
	/** 00.4 = 00.4 */
	public static final String PICKINGSLOT_004 = "00.4";
	/** 00.5 = 00.5 */
	public static final String PICKINGSLOT_005 = "00.5";
	/** 00.6 = 00.6 */
	public static final String PICKINGSLOT_006 = "00.6";
	/** 00.7 = 00.7 */
	public static final String PICKINGSLOT_007 = "00.7";
	/** 00.8 = 00.8 */
	public static final String PICKINGSLOT_008 = "00.8";
	/** 00.9 = 00.9 */
	public static final String PICKINGSLOT_009 = "00.9";
	/** 10.2 = 10.2 */
	public static final String PICKINGSLOT_102 = "10.2";
	/** 10.3 = 10.3 */
	public static final String PICKINGSLOT_103 = "10.3";
	/** Set PickingSlot.
		@param PickingSlot PickingSlot	  */
	@Override
	public void setPickingSlot (java.lang.String PickingSlot)
	{

		set_Value (COLUMNNAME_PickingSlot, PickingSlot);
	}

	/** Get PickingSlot.
		@return PickingSlot	  */
	@Override
	public java.lang.String getPickingSlot () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PickingSlot);
	}
}