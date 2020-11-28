/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Commission_Share
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Commission_Share extends org.compiere.model.PO implements I_C_Commission_Share, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1777351709L;

    /** Standard Constructor */
    public X_C_Commission_Share (Properties ctx, int C_Commission_Share_ID, String trxName)
    {
      super (ctx, C_Commission_Share_ID, trxName);
      /** if (C_Commission_Share_ID == 0)
        {
			setC_BPartner_SalesRep_ID (0);
			setC_Commission_Instance_ID (0);
			setC_Commission_Share_ID (0);
			setC_Flatrate_Term_ID (0);
			setCommission_Product_ID (0);
			setIsSimulation (false); // N
			setLevelHierarchy (0);
			setPointsSum_Forecasted (BigDecimal.ZERO); // 0
			setPointsSum_Invoiceable (BigDecimal.ZERO); // 0
			setPointsSum_Invoiced (BigDecimal.ZERO); // 0
			setPointsSum_Settled (BigDecimal.ZERO); // 0
			setPointsSum_ToSettle (BigDecimal.ZERO); // 0
        } */
    }

    /** Load Constructor */
    public X_C_Commission_Share (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Zugeordneter Vertriebspartner.
		@param C_BPartner_SalesRep_ID Zugeordneter Vertriebspartner	  */
	@Override
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, Integer.valueOf(C_BPartner_SalesRep_ID));
	}

	/** Get Zugeordneter Vertriebspartner.
		@return Zugeordneter Vertriebspartner	  */
	@Override
	public int getC_BPartner_SalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public I_C_Commission_Instance getC_Commission_Instance()
	{
		return get_ValueAsPO(COLUMNNAME_C_Commission_Instance_ID, I_C_Commission_Instance.class);
	}

	@Override
	public void setC_Commission_Instance(I_C_Commission_Instance C_Commission_Instance)
	{
		set_ValueFromPO(COLUMNNAME_C_Commission_Instance_ID, I_C_Commission_Instance.class, C_Commission_Instance);
	}

	/** Set Provisionsvorgang.
		@param C_Commission_Instance_ID Provisionsvorgang	  */
	@Override
	public void setC_Commission_Instance_ID (int C_Commission_Instance_ID)
	{
		if (C_Commission_Instance_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, Integer.valueOf(C_Commission_Instance_ID));
	}

	/** Get Provisionsvorgang.
		@return Provisionsvorgang	  */
	@Override
	public int getC_Commission_Instance_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Instance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Buchauszug.
		@param C_Commission_Share_ID Buchauszug	  */
	@Override
	public void setC_Commission_Share_ID (int C_Commission_Share_ID)
	{
		if (C_Commission_Share_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, Integer.valueOf(C_Commission_Share_ID));
	}

	/** Get Buchauszug.
		@return Buchauszug	  */
	@Override
	public int getC_Commission_Share_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Share_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public I_C_CommissionSettingsLine getC_CommissionSettingsLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_CommissionSettingsLine_ID, I_C_CommissionSettingsLine.class);
	}

	@Override
	public void setC_CommissionSettingsLine(I_C_CommissionSettingsLine C_CommissionSettingsLine)
	{
		set_ValueFromPO(COLUMNNAME_C_CommissionSettingsLine_ID, I_C_CommissionSettingsLine.class, C_CommissionSettingsLine);
	}

	/** Set Einstellungsdetail.
		@param C_CommissionSettingsLine_ID Einstellungsdetail	  */
	@Override
	public void setC_CommissionSettingsLine_ID (int C_CommissionSettingsLine_ID)
	{
		if (C_CommissionSettingsLine_ID < 1)
			set_Value (COLUMNNAME_C_CommissionSettingsLine_ID, null);
		else
			set_Value (COLUMNNAME_C_CommissionSettingsLine_ID, Integer.valueOf(C_CommissionSettingsLine_ID));
	}

	/** Get Einstellungsdetail.
		@return Einstellungsdetail	  */
	@Override
	public int getC_CommissionSettingsLine_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CommissionSettingsLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pauschale - Vertragsperiode.
		@param C_Flatrate_Term_ID Pauschale - Vertragsperiode	  */
	@Override
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1)
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/** Get Pauschale - Vertragsperiode.
		@return Pauschale - Vertragsperiode	  */
	@Override
	public int getC_Flatrate_Term_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionsprodukt.
		@param Commission_Product_ID
		Produkt in dessen Einheit Provisionspunkte geführt und abgerechnet werden
	  */
	@Override
	public void setCommission_Product_ID (int Commission_Product_ID)
	{
		if (Commission_Product_ID < 1)
			set_Value (COLUMNNAME_Commission_Product_ID, null);
		else
			set_Value (COLUMNNAME_Commission_Product_ID, Integer.valueOf(Commission_Product_ID));
	}

	/** Get Provisionsprodukt.
		@return Produkt in dessen Einheit Provisionspunkte geführt und abgerechnet werden
	  */
	@Override
	public int getCommission_Product_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Commission_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Planspiel.
		@param IsSimulation Planspiel	  */
	@Override
	public void setIsSimulation (boolean IsSimulation)
	{
		set_Value (COLUMNNAME_IsSimulation, Boolean.valueOf(IsSimulation));
	}

	/** Get Planspiel.
		@return Planspiel	  */
	@Override
	public boolean isSimulation ()
	{
		Object oo = get_Value(COLUMNNAME_IsSimulation);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Hierarchie-Ebene.
		@param LevelHierarchy Hierarchie-Ebene	  */
	@Override
	public void setLevelHierarchy (int LevelHierarchy)
	{
		set_ValueNoCheck (COLUMNNAME_LevelHierarchy, Integer.valueOf(LevelHierarchy));
	}

	/** Get Hierarchie-Ebene.
		@return Hierarchie-Ebene	  */
	@Override
	public int getLevelHierarchy ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LevelHierarchy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beauftragte Punktzahl.
		@param PointsSum_Forecasted Beauftragte Punktzahl	  */
	@Override
	public void setPointsSum_Forecasted (BigDecimal PointsSum_Forecasted)
	{
		set_Value (COLUMNNAME_PointsSum_Forecasted, PointsSum_Forecasted);
	}

	/** Get Beauftragte Punktzahl.
		@return Beauftragte Punktzahl	  */
	@Override
	public BigDecimal getPointsSum_Forecasted ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_Forecasted);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Fakturierbare Punktzahl.
		@param PointsSum_Invoiceable Fakturierbare Punktzahl	  */
	@Override
	public void setPointsSum_Invoiceable (BigDecimal PointsSum_Invoiceable)
	{
		set_ValueNoCheck (COLUMNNAME_PointsSum_Invoiceable, PointsSum_Invoiceable);
	}

	/** Get Fakturierbare Punktzahl.
		@return Fakturierbare Punktzahl	  */
	@Override
	public BigDecimal getPointsSum_Invoiceable ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_Invoiceable);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Fakturierte Punktzahl.
		@param PointsSum_Invoiced Fakturierte Punktzahl	  */
	@Override
	public void setPointsSum_Invoiced (BigDecimal PointsSum_Invoiced)
	{
		set_Value (COLUMNNAME_PointsSum_Invoiced, PointsSum_Invoiced);
	}

	/** Get Fakturierte Punktzahl.
		@return Fakturierte Punktzahl	  */
	@Override
	public BigDecimal getPointsSum_Invoiced ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_Invoiced);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abgerechnete Punktzahl.
		@param PointsSum_Settled Abgerechnete Punktzahl	  */
	@Override
	public void setPointsSum_Settled (BigDecimal PointsSum_Settled)
	{
		set_Value (COLUMNNAME_PointsSum_Settled, PointsSum_Settled);
	}

	/** Get Abgerechnete Punktzahl.
		@return Abgerechnete Punktzahl	  */
	@Override
	public BigDecimal getPointsSum_Settled ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_Settled);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abzurechnende Punktzahl.
		@param PointsSum_ToSettle Abzurechnende Punktzahl	  */
	@Override
	public void setPointsSum_ToSettle (BigDecimal PointsSum_ToSettle)
	{
		set_Value (COLUMNNAME_PointsSum_ToSettle, PointsSum_ToSettle);
	}

	/** Get Abzurechnende Punktzahl.
		@return Abzurechnende Punktzahl	  */
	@Override
	public BigDecimal getPointsSum_ToSettle ()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_ToSettle);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}
