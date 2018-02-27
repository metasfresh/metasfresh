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
	private static final long serialVersionUID = 1196498328L;

    /** Standard Constructor */
    public X_M_PickingSlot (Properties ctx, int M_PickingSlot_ID, String trxName)
    {
      super (ctx, M_PickingSlot_ID, trxName);
      /** if (M_PickingSlot_ID == 0)
        {
			setIsDynamic (false); // N
			setIsPickingRackSystem (false); // N
			setM_Locator_ID (0);
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

	/** Set Rack System.
		@param IsPickingRackSystem Rack System	  */
	@Override
	public void setIsPickingRackSystem (boolean IsPickingRackSystem)
	{
		set_Value (COLUMNNAME_IsPickingRackSystem, Boolean.valueOf(IsPickingRackSystem));
	}

	/** Get Rack System.
		@return Rack System	  */
	@Override
	public boolean isPickingRackSystem () 
	{
		Object oo = get_Value(COLUMNNAME_IsPickingRackSystem);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Handling Units.
		@param M_HU_ID Handling Units	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Units.
		@return Handling Units	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class);
	}

	@Override
	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator)
	{
		set_ValueFromPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class, M_Locator);
	}

	/** Set Lagerort.
		@param M_Locator_ID 
		Lagerort im Lager
	  */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Lagerort.
		@return Lagerort im Lager
	  */
	@Override
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** 001.1 = 001.1 */
	public static final String PICKINGSLOT_0011 = "001.1";
	/** 001.2 = 001.2 */
	public static final String PICKINGSLOT_0012 = "001.2";
	/** 001.3 = 001.3 */
	public static final String PICKINGSLOT_0013 = "001.3";
	/** 001.4 = 001.4 */
	public static final String PICKINGSLOT_0014 = "001.4";
	/** 001.5 = 001.5 */
	public static final String PICKINGSLOT_0015 = "001.5";
	/** 002.1 = 002.1 */
	public static final String PICKINGSLOT_0021 = "002.1";
	/** 002.2 = 002.2 */
	public static final String PICKINGSLOT_0022 = "002.2";
	/** 002.3 = 002.3 */
	public static final String PICKINGSLOT_0023 = "002.3";
	/** 002.4 = 002.4 */
	public static final String PICKINGSLOT_0024 = "002.4";
	/** 002.5 = 002.5 */
	public static final String PICKINGSLOT_0025 = "002.5";
	/** 003.1 = 003.1 */
	public static final String PICKINGSLOT_0031 = "003.1";
	/** 003.2 = 003.2 */
	public static final String PICKINGSLOT_0032 = "003.2";
	/** 003.3 = 003.3 */
	public static final String PICKINGSLOT_0033 = "003.3";
	/** 003.4 = 003.4 */
	public static final String PICKINGSLOT_0034 = "003.4";
	/** 003.5 = 003.5 */
	public static final String PICKINGSLOT_0035 = "003.5";
	/** 004.1 = 004.1 */
	public static final String PICKINGSLOT_0041 = "004.1";
	/** 004.2 = 004.2 */
	public static final String PICKINGSLOT_0042 = "004.2";
	/** 004.3 = 004.3 */
	public static final String PICKINGSLOT_0043 = "004.3";
	/** 004.4 = 004.4 */
	public static final String PICKINGSLOT_0044 = "004.4";
	/** 004.5 = 004.5 */
	public static final String PICKINGSLOT_0045 = "004.5";
	/** 005.1 = 005.1 */
	public static final String PICKINGSLOT_0051 = "005.1";
	/** 005.2 = 005.2 */
	public static final String PICKINGSLOT_0052 = "005.2";
	/** 005.3 = 005.3 */
	public static final String PICKINGSLOT_0053 = "005.3";
	/** 005.4 = 005.4 */
	public static final String PICKINGSLOT_0054 = "005.4";
	/** 005.5 = 005.5 */
	public static final String PICKINGSLOT_0055 = "005.5";
	/** 006.1 = 006.1 */
	public static final String PICKINGSLOT_0061 = "006.1";
	/** 006.2 = 006.2 */
	public static final String PICKINGSLOT_0062 = "006.2";
	/** 006.3 = 006.3 */
	public static final String PICKINGSLOT_0063 = "006.3";
	/** 006.4 = 006.4 */
	public static final String PICKINGSLOT_0064 = "006.4";
	/** 006.5 = 006.5 */
	public static final String PICKINGSLOT_0065 = "006.5";
	/** 007.1 = 007.1 */
	public static final String PICKINGSLOT_0071 = "007.1";
	/** 007.2 = 007.2 */
	public static final String PICKINGSLOT_0072 = "007.2";
	/** 007.3 = 007.3 */
	public static final String PICKINGSLOT_0073 = "007.3";
	/** 007.4 = 007.4 */
	public static final String PICKINGSLOT_0074 = "007.4";
	/** 007.5 = 007.5 */
	public static final String PICKINGSLOT_0075 = "007.5";
	/** 008.1 = 008.1 */
	public static final String PICKINGSLOT_0081 = "008.1";
	/** 008.2 = 008.2 */
	public static final String PICKINGSLOT_0082 = "008.2";
	/** 008.3 = 008.3 */
	public static final String PICKINGSLOT_0083 = "008.3";
	/** 008.4 = 008.4 */
	public static final String PICKINGSLOT_0084 = "008.4";
	/** 008.5 = 008.5 */
	public static final String PICKINGSLOT_0085 = "008.5";
	/** 009.1 = 009.1 */
	public static final String PICKINGSLOT_0091 = "009.1";
	/** 009.2 = 009.2 */
	public static final String PICKINGSLOT_0092 = "009.2";
	/** 009.3 = 009.3 */
	public static final String PICKINGSLOT_0093 = "009.3";
	/** 009.4 = 009.4 */
	public static final String PICKINGSLOT_0094 = "009.4";
	/** 009.5 = 009.5 */
	public static final String PICKINGSLOT_0095 = "009.5";
	/** 010.1 = 010.1 */
	public static final String PICKINGSLOT_0101 = "010.1";
	/** 011.1 = 011.1 */
	public static final String PICKINGSLOT_0111 = "011.1";
	/** 011.2 = 011.2 */
	public static final String PICKINGSLOT_0112 = "011.2";
	/** 011.3 = 011.3 */
	public static final String PICKINGSLOT_0113 = "011.3";
	/** 011.4 = 011.4 */
	public static final String PICKINGSLOT_0114 = "011.4";
	/** 011.5 = 011.5 */
	public static final String PICKINGSLOT_0115 = "011.5";
	/** 012.1 = 012.1 */
	public static final String PICKINGSLOT_0121 = "012.1";
	/** 012.2 = 012.2 */
	public static final String PICKINGSLOT_0122 = "012.2";
	/** 012.3 = 012.3 */
	public static final String PICKINGSLOT_0123 = "012.3";
	/** 012.4 = 012.4 */
	public static final String PICKINGSLOT_0124 = "012.4";
	/** 012.5 = 012.5 */
	public static final String PICKINGSLOT_0125 = "012.5";
	/** 013.1 = 013.1 */
	public static final String PICKINGSLOT_0131 = "013.1";
	/** 013.2 = 013.2 */
	public static final String PICKINGSLOT_0132 = "013.2";
	/** 013.3 = 013.3 */
	public static final String PICKINGSLOT_0133 = "013.3";
	/** 013.4 = 013.4 */
	public static final String PICKINGSLOT_0134 = "013.4";
	/** 013.5 = 013.5 */
	public static final String PICKINGSLOT_0135 = "013.5";
	/** 014.1 = 014.1 */
	public static final String PICKINGSLOT_0141 = "014.1";
	/** 014.2 = 014.2 */
	public static final String PICKINGSLOT_0142 = "014.2";
	/** 014.3 = 014.3 */
	public static final String PICKINGSLOT_0143 = "014.3";
	/** 014.4 = 014.4 */
	public static final String PICKINGSLOT_0144 = "014.4";
	/** 014.5 = 014.5 */
	public static final String PICKINGSLOT_0145 = "014.5";
	/** 015.1 = 015.1 */
	public static final String PICKINGSLOT_0151 = "015.1";
	/** 015.2 = 015.2 */
	public static final String PICKINGSLOT_0152 = "015.2";
	/** 015.3 = 015.3 */
	public static final String PICKINGSLOT_0153 = "015.3";
	/** 015.4 = 015.4 */
	public static final String PICKINGSLOT_0154 = "015.4";
	/** 015.5 = 015.5 */
	public static final String PICKINGSLOT_0155 = "015.5";
	/** 016.1 = 016.1 */
	public static final String PICKINGSLOT_0161 = "016.1";
	/** 016.2 = 016.2 */
	public static final String PICKINGSLOT_0162 = "016.2";
	/** 016.3 = 016.3 */
	public static final String PICKINGSLOT_0163 = "016.3";
	/** 016.4 = 016.4 */
	public static final String PICKINGSLOT_0164 = "016.4";
	/** 016.5 = 016.5 */
	public static final String PICKINGSLOT_0165 = "016.5";
	/** 017.1 = 017.1 */
	public static final String PICKINGSLOT_0171 = "017.1";
	/** 017.2 = 017.2 */
	public static final String PICKINGSLOT_0172 = "017.2";
	/** 017.3 = 017.3 */
	public static final String PICKINGSLOT_0173 = "017.3";
	/** 017.4 = 017.4 */
	public static final String PICKINGSLOT_0174 = "017.4";
	/** 017.5 = 017.5 */
	public static final String PICKINGSLOT_0175 = "017.5";
	/** 018.1 = 018.1 */
	public static final String PICKINGSLOT_0181 = "018.1";
	/** 018.2 = 018.2 */
	public static final String PICKINGSLOT_0182 = "018.2";
	/** 018.3 = 018.3 */
	public static final String PICKINGSLOT_0183 = "018.3";
	/** 018.4 = 018.4 */
	public static final String PICKINGSLOT_0184 = "018.4";
	/** 018.5 = 018.5 */
	public static final String PICKINGSLOT_0185 = "018.5";
	/** 019.1 = 019.1 */
	public static final String PICKINGSLOT_0191 = "019.1";
	/** 019.2 = 019.2 */
	public static final String PICKINGSLOT_0192 = "019.2";
	/** 019.3 = 019.3 */
	public static final String PICKINGSLOT_0193 = "019.3";
	/** 019.4 = 019.4 */
	public static final String PICKINGSLOT_0194 = "019.4";
	/** 019.5 = 019.5 */
	public static final String PICKINGSLOT_0195 = "019.5";
	/** 020.1 = 020.1 */
	public static final String PICKINGSLOT_0201 = "020.1";
	/** 020.2 = 020.2 */
	public static final String PICKINGSLOT_0202 = "020.2";
	/** 020.3 = 020.3 */
	public static final String PICKINGSLOT_0203 = "020.3";
	/** 020.4 = 020.4 */
	public static final String PICKINGSLOT_0204 = "020.4";
	/** 020.5 = 020.5 */
	public static final String PICKINGSLOT_0205 = "020.5";
	/** 021.1 = 021.1 */
	public static final String PICKINGSLOT_0211 = "021.1";
	/** 021.2 = 021.2 */
	public static final String PICKINGSLOT_0212 = "021.2";
	/** 021.3 = 021.3 */
	public static final String PICKINGSLOT_0213 = "021.3";
	/** 021.4 = 021.4 */
	public static final String PICKINGSLOT_0214 = "021.4";
	/** 021.5 = 021.5 */
	public static final String PICKINGSLOT_0215 = "021.5";
	/** 022.1 = 022.1 */
	public static final String PICKINGSLOT_0221 = "022.1";
	/** 022.2 = 022.2 */
	public static final String PICKINGSLOT_0222 = "022.2";
	/** 022.3 = 022.3 */
	public static final String PICKINGSLOT_0223 = "022.3";
	/** 022.4 = 022.4 */
	public static final String PICKINGSLOT_0224 = "022.4";
	/** 022.5 = 022.5 */
	public static final String PICKINGSLOT_0225 = "022.5";
	/** 010.4 = 010.4 */
	public static final String PICKINGSLOT_0104 = "010.4";
	/** 010.5 = 010.5 */
	public static final String PICKINGSLOT_0105 = "010.5";
	/** 000.0 = 000.0 */
	public static final String PICKINGSLOT_0000 = "000.0";
	/** 000.1 = 000.1 */
	public static final String PICKINGSLOT_0001 = "000.1";
	/** 000.2 = 000.2 */
	public static final String PICKINGSLOT_0002 = "000.2";
	/** 000.3 = 000.3 */
	public static final String PICKINGSLOT_0003 = "000.3";
	/** 000.4 = 000.4 */
	public static final String PICKINGSLOT_0004 = "000.4";
	/** 000.5 = 000.5 */
	public static final String PICKINGSLOT_0005 = "000.5";
	/** 000.6 = 000.6 */
	public static final String PICKINGSLOT_0006 = "000.6";
	/** 000.7 = 000.7 */
	public static final String PICKINGSLOT_0007 = "000.7";
	/** 000.8 = 000.8 */
	public static final String PICKINGSLOT_0008 = "000.8";
	/** 000.9 = 000.9 */
	public static final String PICKINGSLOT_0009 = "000.9";
	/** 010.2 = 010.2 */
	public static final String PICKINGSLOT_0102 = "010.2";
	/** 010.3 = 010.3 */
	public static final String PICKINGSLOT_0103 = "010.3";
	/** 050.1 = 050.1 */
	public static final String PICKINGSLOT_0501 = "050.1";
	/** 050.2 = 050.2 */
	public static final String PICKINGSLOT_0502 = "050.2";
	/** 050.3 = 050.3 */
	public static final String PICKINGSLOT_0503 = "050.3";
	/** 050.4 = 050.4 */
	public static final String PICKINGSLOT_0504 = "050.4";
	/** 050.5 = 050.5 */
	public static final String PICKINGSLOT_0505 = "050.5";
	/** 051.1 = 051.1 */
	public static final String PICKINGSLOT_0511 = "051.1";
	/** 051.2 = 051.2 */
	public static final String PICKINGSLOT_0512 = "051.2";
	/** 051.3 = 051.3 */
	public static final String PICKINGSLOT_0513 = "051.3";
	/** 051.4 = 051.4 */
	public static final String PICKINGSLOT_0514 = "051.4";
	/** 051.5 = 051.5 */
	public static final String PICKINGSLOT_0515 = "051.5";
	/** 052.1 = 052.1 */
	public static final String PICKINGSLOT_0521 = "052.1";
	/** 052.2 = 052.2 */
	public static final String PICKINGSLOT_0522 = "052.2";
	/** 052.3 = 052.3 */
	public static final String PICKINGSLOT_0523 = "052.3";
	/** 052.4 = 052.4 */
	public static final String PICKINGSLOT_0524 = "052.4";
	/** 052.5 = 052.5 */
	public static final String PICKINGSLOT_0525 = "052.5";
	/** 053.1 = 053.1 */
	public static final String PICKINGSLOT_0531 = "053.1";
	/** 053.2 = 053.2 */
	public static final String PICKINGSLOT_0532 = "053.2";
	/** 053.3 = 053.3 */
	public static final String PICKINGSLOT_0533 = "053.3";
	/** 053.4 = 053.4 */
	public static final String PICKINGSLOT_0534 = "053.4";
	/** 053.5 = 053.5 */
	public static final String PICKINGSLOT_0535 = "053.5";
	/** 054.1 = 054.1 */
	public static final String PICKINGSLOT_0541 = "054.1";
	/** 054.2 = 054.2 */
	public static final String PICKINGSLOT_0542 = "054.2";
	/** 054.3 = 054.3 */
	public static final String PICKINGSLOT_0543 = "054.3";
	/** 054.4 = 054.4 */
	public static final String PICKINGSLOT_0544 = "054.4";
	/** 054.5 = 054.5 */
	public static final String PICKINGSLOT_0545 = "054.5";
	/** 055.1 = 055.1 */
	public static final String PICKINGSLOT_0551 = "055.1";
	/** 055.2 = 055.2 */
	public static final String PICKINGSLOT_0552 = "055.2";
	/** 055.3 = 055.3 */
	public static final String PICKINGSLOT_0553 = "055.3";
	/** 055.4 = 055.4 */
	public static final String PICKINGSLOT_0554 = "055.4";
	/** 055.5 = 055.5 */
	public static final String PICKINGSLOT_0555 = "055.5";
	/** 056.1 = 056.1 */
	public static final String PICKINGSLOT_0561 = "056.1";
	/** 056.2 = 056.2 */
	public static final String PICKINGSLOT_0562 = "056.2";
	/** 056.3 = 056.3 */
	public static final String PICKINGSLOT_0563 = "056.3";
	/** 056.4 = 056.4 */
	public static final String PICKINGSLOT_0564 = "056.4";
	/** 056.5 = 056.5 */
	public static final String PICKINGSLOT_0565 = "056.5";
	/** 057.1 = 057.1 */
	public static final String PICKINGSLOT_0571 = "057.1";
	/** 057.2 = 057.2 */
	public static final String PICKINGSLOT_0572 = "057.2";
	/** 057.3 = 057.3 */
	public static final String PICKINGSLOT_0573 = "057.3";
	/** 057.4 = 057.4 */
	public static final String PICKINGSLOT_0574 = "057.4";
	/** 057.5 = 057.5 */
	public static final String PICKINGSLOT_0575 = "057.5";
	/** 058.1 = 058.1 */
	public static final String PICKINGSLOT_0581 = "058.1";
	/** 058.2 = 058.2 */
	public static final String PICKINGSLOT_0582 = "058.2";
	/** 058.3 = 058.3 */
	public static final String PICKINGSLOT_0583 = "058.3";
	/** 058.4 = 058.4 */
	public static final String PICKINGSLOT_0584 = "058.4";
	/** 058.5 = 058.5 */
	public static final String PICKINGSLOT_0585 = "058.5";
	/** 059.1 = 059.1 */
	public static final String PICKINGSLOT_0591 = "059.1";
	/** 059.2 = 059.2 */
	public static final String PICKINGSLOT_0592 = "059.2";
	/** 059.3 = 059.3 */
	public static final String PICKINGSLOT_0593 = "059.3";
	/** 059.4 = 059.4 */
	public static final String PICKINGSLOT_0594 = "059.4";
	/** 059.5 = 059.5 */
	public static final String PICKINGSLOT_0595 = "059.5";
	/** 060.1 = 060.1 */
	public static final String PICKINGSLOT_0601 = "060.1";
	/** 060.2 = 060.2 */
	public static final String PICKINGSLOT_0602 = "060.2";
	/** 060.3 = 060.3 */
	public static final String PICKINGSLOT_0603 = "060.3";
	/** 060.4 = 060.4 */
	public static final String PICKINGSLOT_0604 = "060.4";
	/** 060.5 = 060.5 */
	public static final String PICKINGSLOT_0605 = "060.5";
	/** 061.1 = 061.1 */
	public static final String PICKINGSLOT_0611 = "061.1";
	/** 061.2 = 061.2 */
	public static final String PICKINGSLOT_0612 = "061.2";
	/** 061.3 = 061.3 */
	public static final String PICKINGSLOT_0613 = "061.3";
	/** 061.4 = 061.4 */
	public static final String PICKINGSLOT_0614 = "061.4";
	/** 061.5 = 061.5 */
	public static final String PICKINGSLOT_0615 = "061.5";
	/** 062.1 = 062.1 */
	public static final String PICKINGSLOT_0621 = "062.1";
	/** 062.2 = 062.2 */
	public static final String PICKINGSLOT_0622 = "062.2";
	/** 062.3 = 062.3 */
	public static final String PICKINGSLOT_0623 = "062.3";
	/** 062.4 = 062.4 */
	public static final String PICKINGSLOT_0624 = "062.4";
	/** 062.5 = 062.5 */
	public static final String PICKINGSLOT_0625 = "062.5";
	/** 063.1 = 063.1 */
	public static final String PICKINGSLOT_0631 = "063.1";
	/** 063.2 = 063.2 */
	public static final String PICKINGSLOT_0632 = "063.2";
	/** 063.3 = 063.3 */
	public static final String PICKINGSLOT_0633 = "063.3";
	/** 063.4 = 063.4 */
	public static final String PICKINGSLOT_0634 = "063.4";
	/** 063.5 = 063.5 */
	public static final String PICKINGSLOT_0635 = "063.5";
	/** 064.1 = 064.1 */
	public static final String PICKINGSLOT_0641 = "064.1";
	/** 064.2 = 064.2 */
	public static final String PICKINGSLOT_0642 = "064.2";
	/** 064.3 = 064.3 */
	public static final String PICKINGSLOT_0643 = "064.3";
	/** 064.4 = 064.4 */
	public static final String PICKINGSLOT_0644 = "064.4";
	/** 064.5 = 064.5 */
	public static final String PICKINGSLOT_0645 = "064.5";
	/** 065.1 = 065.1 */
	public static final String PICKINGSLOT_0651 = "065.1";
	/** 065.2 = 065.2 */
	public static final String PICKINGSLOT_0652 = "065.2";
	/** 065.3 = 065.3 */
	public static final String PICKINGSLOT_0653 = "065.3";
	/** 065.4 = 065.4 */
	public static final String PICKINGSLOT_0654 = "065.4";
	/** 065.5 = 065.5 */
	public static final String PICKINGSLOT_0655 = "065.5";
	/** 066.1 = 066.1 */
	public static final String PICKINGSLOT_0661 = "066.1";
	/** 066.2 = 066.2 */
	public static final String PICKINGSLOT_0662 = "066.2";
	/** 066.3 = 066.3 */
	public static final String PICKINGSLOT_0663 = "066.3";
	/** 066.4 = 066.4 */
	public static final String PICKINGSLOT_0664 = "066.4";
	/** 066.5 = 066.5 */
	public static final String PICKINGSLOT_0665 = "066.5";
	/** 067.1 = 067.1 */
	public static final String PICKINGSLOT_0671 = "067.1";
	/** 067.2 = 067.2 */
	public static final String PICKINGSLOT_0672 = "067.2";
	/** 067.3 = 067.3 */
	public static final String PICKINGSLOT_0673 = "067.3";
	/** 067.4 = 067.4 */
	public static final String PICKINGSLOT_0674 = "067.4";
	/** 067.5 = 067.5 */
	public static final String PICKINGSLOT_0675 = "067.5";
	/** 068.1 = 068.1 */
	public static final String PICKINGSLOT_0681 = "068.1";
	/** 068.2 = 068.2 */
	public static final String PICKINGSLOT_0682 = "068.2";
	/** 068.3 = 068.3 */
	public static final String PICKINGSLOT_0683 = "068.3";
	/** 068.4 = 068.4 */
	public static final String PICKINGSLOT_0684 = "068.4";
	/** 068.5 = 068.5 */
	public static final String PICKINGSLOT_0685 = "068.5";
	/** 069.1 = 069.1 */
	public static final String PICKINGSLOT_0691 = "069.1";
	/** 069.2 = 069.2 */
	public static final String PICKINGSLOT_0692 = "069.2";
	/** 069.3 = 069.3 */
	public static final String PICKINGSLOT_0693 = "069.3";
	/** 069.4 = 069.4 */
	public static final String PICKINGSLOT_0694 = "069.4";
	/** 069.5 = 069.5 */
	public static final String PICKINGSLOT_0695 = "069.5";
	/** 070.1 = 070.1 */
	public static final String PICKINGSLOT_0701 = "070.1";
	/** 070.2 = 070.2 */
	public static final String PICKINGSLOT_0702 = "070.2";
	/** 070.3 = 070.3 */
	public static final String PICKINGSLOT_0703 = "070.3";
	/** 070.4 = 070.4 */
	public static final String PICKINGSLOT_0704 = "070.4";
	/** 070.5 = 070.5 */
	public static final String PICKINGSLOT_0705 = "070.5";
	/** 100.0 = 100.0 */
	public static final String PICKINGSLOT_1000 = "100.0";
	/** 101.0 = 101.0 */
	public static final String PICKINGSLOT_1010 = "101.0";
	/** 102.0 = 102.0 */
	public static final String PICKINGSLOT_1020 = "102.0";
	/** 103.0 = 103.0 */
	public static final String PICKINGSLOT_1030 = "103.0";
	/** 104.0 = 104.0 */
	public static final String PICKINGSLOT_1040 = "104.0";
	/** 105.0 = 105.0 */
	public static final String PICKINGSLOT_1050 = "105.0";
	/** 106.0 = 106.0 */
	public static final String PICKINGSLOT_1060 = "106.0";
	/** 107.0 = 107.0 */
	public static final String PICKINGSLOT_1070 = "107.0";
	/** 108.0 = 108.0 */
	public static final String PICKINGSLOT_1080 = "108.0";
	/** 109.0 = 109.0 */
	public static final String PICKINGSLOT_1090 = "109.0";
	/** 110.0 = 110.0 */
	public static final String PICKINGSLOT_1100 = "110.0";
	/** 111.0 = 111.0 */
	public static final String PICKINGSLOT_1110 = "111.0";
	/** 112.0 = 112.0 */
	public static final String PICKINGSLOT_1120 = "112.0";
	/** 113.0 = 113.0 */
	public static final String PICKINGSLOT_1130 = "113.0";
	/** 114.0 = 114.0 */
	public static final String PICKINGSLOT_1140 = "114.0";
	/** 115.0 = 115.0 */
	public static final String PICKINGSLOT_1150 = "115.0";
	/** 116.0 = 116.0 */
	public static final String PICKINGSLOT_1160 = "116.0";
	/** 117.0 = 117.0 */
	public static final String PICKINGSLOT_1170 = "117.0";
	/** 118.0 = 118.0 */
	public static final String PICKINGSLOT_1180 = "118.0";
	/** 119.0 = 119.0 */
	public static final String PICKINGSLOT_1190 = "119.0";
	/** 120.0 = 120.0 */
	public static final String PICKINGSLOT_1200 = "120.0";
	/** 121.0 = 121.0 */
	public static final String PICKINGSLOT_1210 = "121.0";
	/** 122.0 = 122.0 */
	public static final String PICKINGSLOT_1220 = "122.0";
	/** 123.0 = 123.0 */
	public static final String PICKINGSLOT_1230 = "123.0";
	/** 124.0 = 124.0 */
	public static final String PICKINGSLOT_1240 = "124.0";
	/** 125.0 = 125.0 */
	public static final String PICKINGSLOT_1250 = "125.0";
	/** 126.0 = 126.0 */
	public static final String PICKINGSLOT_1260 = "126.0";
	/** 127.0 = 127.0 */
	public static final String PICKINGSLOT_1270 = "127.0";
	/** 128.0 = 128.0 */
	public static final String PICKINGSLOT_1280 = "128.0";
	/** 129.0 = 129.0 */
	public static final String PICKINGSLOT_1290 = "129.0";
	/** 130.0 = 130.0 */
	public static final String PICKINGSLOT_1300 = "130.0";
	/** 131.0 = 131.0 */
	public static final String PICKINGSLOT_1310 = "131.0";
	/** 132.0 = 132.0 */
	public static final String PICKINGSLOT_1320 = "132.0";
	/** 133.0 = 133.0 */
	public static final String PICKINGSLOT_1330 = "133.0";
	/** 134.0 = 134.0 */
	public static final String PICKINGSLOT_1340 = "134.0";
	/** 135.0 = 135.0 */
	public static final String PICKINGSLOT_1350 = "135.0";
	/** 136.0 = 136.0 */
	public static final String PICKINGSLOT_1360 = "136.0";
	/** 137.0 = 137.0 */
	public static final String PICKINGSLOT_1370 = "137.0";
	/** 138.0 = 138.0 */
	public static final String PICKINGSLOT_1380 = "138.0";
	/** 139.0 = 139.0 */
	public static final String PICKINGSLOT_1390 = "139.0";
	/** 140.0 = 140.0 */
	public static final String PICKINGSLOT_1400 = "140.0";
	/** 141.0 = 141.0 */
	public static final String PICKINGSLOT_1410 = "141.0";
	/** 142.0 = 142.0 */
	public static final String PICKINGSLOT_1420 = "142.0";
	/** 143.0 = 143.0 */
	public static final String PICKINGSLOT_1430 = "143.0";
	/** 144.0 = 144.0 */
	public static final String PICKINGSLOT_1440 = "144.0";
	/** 145.0 = 145.0 */
	public static final String PICKINGSLOT_1450 = "145.0";
	/** 146.0 = 146.0 */
	public static final String PICKINGSLOT_1460 = "146.0";
	/** 147.0 = 147.0 */
	public static final String PICKINGSLOT_1470 = "147.0";
	/** 148.0 = 148.0 */
	public static final String PICKINGSLOT_1480 = "148.0";
	/** 149.0 = 149.0 */
	public static final String PICKINGSLOT_1490 = "149.0";
	/** 150.0 = 150.0 */
	public static final String PICKINGSLOT_1500 = "150.0";
	/** 151.0 = 151.0 */
	public static final String PICKINGSLOT_1510 = "151.0";
	/** 152.0 = 152.0 */
	public static final String PICKINGSLOT_1520 = "152.0";
	/** 153.0 = 153.0 */
	public static final String PICKINGSLOT_1530 = "153.0";
	/** 154.0 = 154.0 */
	public static final String PICKINGSLOT_1540 = "154.0";
	/** 155.0 = 155.0 */
	public static final String PICKINGSLOT_1550 = "155.0";
	/** 156.0 = 156.0 */
	public static final String PICKINGSLOT_1560 = "156.0";
	/** 157.0 = 157.0 */
	public static final String PICKINGSLOT_1570 = "157.0";
	/** 158.0 = 158.0 */
	public static final String PICKINGSLOT_1580 = "158.0";
	/** 159.0 = 159.0 */
	public static final String PICKINGSLOT_1590 = "159.0";
	/** 160.0 = 160.0 */
	public static final String PICKINGSLOT_1600 = "160.0";
	/** 161.0 = 161.0 */
	public static final String PICKINGSLOT_1610 = "161.0";
	/** 162.0 = 162.0 */
	public static final String PICKINGSLOT_1620 = "162.0";
	/** 163.0 = 163.0 */
	public static final String PICKINGSLOT_1630 = "163.0";
	/** 164.0 = 164.0 */
	public static final String PICKINGSLOT_1640 = "164.0";
	/** 165.0 = 165.0 */
	public static final String PICKINGSLOT_1650 = "165.0";
	/** 166.0 = 166.0 */
	public static final String PICKINGSLOT_1660 = "166.0";
	/** 167.0 = 167.0 */
	public static final String PICKINGSLOT_1670 = "167.0";
	/** 168.0 = 168.0 */
	public static final String PICKINGSLOT_1680 = "168.0";
	/** 169.0 = 169.0 */
	public static final String PICKINGSLOT_1690 = "169.0";
	/** 170.0 = 170.0 */
	public static final String PICKINGSLOT_1700 = "170.0";
	/** 171.0 = 171.0 */
	public static final String PICKINGSLOT_1710 = "171.0";
	/** 172.0 = 172.0 */
	public static final String PICKINGSLOT_1720 = "172.0";
	/** 173.0 = 173.0 */
	public static final String PICKINGSLOT_1730 = "173.0";
	/** 174.0 = 174.0 */
	public static final String PICKINGSLOT_1740 = "174.0";
	/** 175.0 = 175.0 */
	public static final String PICKINGSLOT_1750 = "175.0";
	/** 176.0 = 176.0 */
	public static final String PICKINGSLOT_1760 = "176.0";
	/** 177.0 = 177.0 */
	public static final String PICKINGSLOT_1770 = "177.0";
	/** 178.0 = 178.0 */
	public static final String PICKINGSLOT_1780 = "178.0";
	/** 179.0 = 179.0 */
	public static final String PICKINGSLOT_1790 = "179.0";
	/** 180.0 = 180.0 */
	public static final String PICKINGSLOT_1800 = "180.0";
	/** 181.0 = 181.0 */
	public static final String PICKINGSLOT_1810 = "181.0";
	/** 182.0 = 182.0 */
	public static final String PICKINGSLOT_1820 = "182.0";
	/** 183.0 = 183.0 */
	public static final String PICKINGSLOT_1830 = "183.0";
	/** 184.0 = 184.0 */
	public static final String PICKINGSLOT_1840 = "184.0";
	/** 185.0 = 185.0 */
	public static final String PICKINGSLOT_1850 = "185.0";
	/** 186.0 = 186.0 */
	public static final String PICKINGSLOT_1860 = "186.0";
	/** 187.0 = 187.0 */
	public static final String PICKINGSLOT_1870 = "187.0";
	/** 188.0 = 188.0 */
	public static final String PICKINGSLOT_1880 = "188.0";
	/** 189.0 = 189.0 */
	public static final String PICKINGSLOT_1890 = "189.0";
	/** 190.0 = 190.0 */
	public static final String PICKINGSLOT_1900 = "190.0";
	/** 191.0 = 191.0 */
	public static final String PICKINGSLOT_1910 = "191.0";
	/** 192.0 = 192.0 */
	public static final String PICKINGSLOT_1920 = "192.0";
	/** 193.0 = 193.0 */
	public static final String PICKINGSLOT_1930 = "193.0";
	/** 194.0 = 194.0 */
	public static final String PICKINGSLOT_1940 = "194.0";
	/** 195.0 = 195.0 */
	public static final String PICKINGSLOT_1950 = "195.0";
	/** 196.0 = 196.0 */
	public static final String PICKINGSLOT_1960 = "196.0";
	/** 197.0 = 197.0 */
	public static final String PICKINGSLOT_1970 = "197.0";
	/** 198.0 = 198.0 */
	public static final String PICKINGSLOT_1980 = "198.0";
	/** 199.0 = 199.0 */
	public static final String PICKINGSLOT_1990 = "199.0";
	/** 200.0 = 200.0 */
	public static final String PICKINGSLOT_2000 = "200.0";
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