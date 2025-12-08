// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DatevAcctExportLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DatevAcctExportLine extends org.compiere.model.PO implements I_DatevAcctExportLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -733529787L;

    /** Standard Constructor */
    public X_DatevAcctExportLine (final Properties ctx, final int DatevAcctExportLine_ID, @Nullable final String trxName)
    {
      super (ctx, DatevAcctExportLine_ID, trxName);
    }

    /** Load Constructor */
    public X_DatevAcctExportLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setBelegfeld_1 (final @Nullable java.lang.String Belegfeld_1)
	{
		set_Value (COLUMNNAME_Belegfeld_1, Belegfeld_1);
	}

	@Override
	public java.lang.String getBelegfeld_1() 
	{
		return get_ValueAsString(COLUMNNAME_Belegfeld_1);
	}

	@Override
	public void setBelegfeld_2 (final @Nullable java.lang.String Belegfeld_2)
	{
		set_Value (COLUMNNAME_Belegfeld_2, Belegfeld_2);
	}

	@Override
	public java.lang.String getBelegfeld_2() 
	{
		return get_ValueAsString(COLUMNNAME_Belegfeld_2);
	}

	@Override
	public void setBU (final @Nullable java.lang.String BU)
	{
		set_Value (COLUMNNAME_BU, BU);
	}

	@Override
	public java.lang.String getBU() 
	{
		return get_ValueAsString(COLUMNNAME_BU);
	}

	@Override
	public void setBuchungstext (final @Nullable java.lang.String Buchungstext)
	{
		set_Value (COLUMNNAME_Buchungstext, Buchungstext);
	}

	@Override
	public java.lang.String getBuchungstext() 
	{
		return get_ValueAsString(COLUMNNAME_Buchungstext);
	}

	@Override
	public org.compiere.model.I_DatevAcctExport getDatevAcctExport()
	{
		return get_ValueAsPO(COLUMNNAME_DatevAcctExport_ID, org.compiere.model.I_DatevAcctExport.class);
	}

	@Override
	public void setDatevAcctExport(final org.compiere.model.I_DatevAcctExport DatevAcctExport)
	{
		set_ValueFromPO(COLUMNNAME_DatevAcctExport_ID, org.compiere.model.I_DatevAcctExport.class, DatevAcctExport);
	}

	@Override
	public void setDatevAcctExport_ID (final int DatevAcctExport_ID)
	{
		if (DatevAcctExport_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DatevAcctExport_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DatevAcctExport_ID, DatevAcctExport_ID);
	}

	@Override
	public int getDatevAcctExport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DatevAcctExport_ID);
	}

	@Override
	public void setDatevAcctExportLine_ID (final int DatevAcctExportLine_ID)
	{
		if (DatevAcctExportLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DatevAcctExportLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DatevAcctExportLine_ID, DatevAcctExportLine_ID);
	}

	@Override
	public int getDatevAcctExportLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DatevAcctExportLine_ID);
	}

	@Override
	public void setDatev_Kost1 (final int Datev_Kost1)
	{
		set_Value (COLUMNNAME_Datev_Kost1, Datev_Kost1);
	}

	@Override
	public int getDatev_Kost1() 
	{
		return get_ValueAsInt(COLUMNNAME_Datev_Kost1);
	}

	@Override
	public void setDatev_Kost2 (final int Datev_Kost2)
	{
		set_Value (COLUMNNAME_Datev_Kost2, Datev_Kost2);
	}

	@Override
	public int getDatev_Kost2() 
	{
		return get_ValueAsInt(COLUMNNAME_Datev_Kost2);
	}

	@Override
	public void setDatum (final @Nullable java.lang.String Datum)
	{
		set_Value (COLUMNNAME_Datum, Datum);
	}

	@Override
	public java.lang.String getDatum() 
	{
		return get_ValueAsString(COLUMNNAME_Datum);
	}

	@Override
	public void setFS (final int FS)
	{
		set_Value (COLUMNNAME_FS, FS);
	}

	@Override
	public int getFS() 
	{
		return get_ValueAsInt(COLUMNNAME_FS);
	}

	@Override
	public void setGegenkonto (final @Nullable java.lang.String Gegenkonto)
	{
		set_Value (COLUMNNAME_Gegenkonto, Gegenkonto);
	}

	@Override
	public java.lang.String getGegenkonto() 
	{
		return get_ValueAsString(COLUMNNAME_Gegenkonto);
	}

	@Override
	public void setKonto (final @Nullable java.lang.String Konto)
	{
		set_Value (COLUMNNAME_Konto, Konto);
	}

	@Override
	public java.lang.String getKonto() 
	{
		return get_ValueAsString(COLUMNNAME_Konto);
	}

	@Override
	public void setLeistungsdatum (final @Nullable java.lang.String Leistungsdatum)
	{
		set_Value (COLUMNNAME_Leistungsdatum, Leistungsdatum);
	}

	@Override
	public java.lang.String getLeistungsdatum() 
	{
		return get_ValueAsString(COLUMNNAME_Leistungsdatum);
	}

	@Override
	public void setSkonto (final @Nullable java.lang.String Skonto)
	{
		set_Value (COLUMNNAME_Skonto, Skonto);
	}

	@Override
	public java.lang.String getSkonto() 
	{
		return get_ValueAsString(COLUMNNAME_Skonto);
	}

	@Override
	public void setUmsatz (final @Nullable java.lang.String Umsatz)
	{
		set_Value (COLUMNNAME_Umsatz, Umsatz);
	}

	@Override
	public java.lang.String getUmsatz() 
	{
		return get_ValueAsString(COLUMNNAME_Umsatz);
	}

	@Override
	public void setZI_Art (final @Nullable java.lang.String ZI_Art)
	{
		set_Value (COLUMNNAME_ZI_Art, ZI_Art);
	}

	@Override
	public java.lang.String getZI_Art() 
	{
		return get_ValueAsString(COLUMNNAME_ZI_Art);
	}

	@Override
	public void setZI_Inhalt (final @Nullable java.lang.String ZI_Inhalt)
	{
		set_Value (COLUMNNAME_ZI_Inhalt, ZI_Inhalt);
	}

	@Override
	public java.lang.String getZI_Inhalt() 
	{
		return get_ValueAsString(COLUMNNAME_ZI_Inhalt);
	}
}