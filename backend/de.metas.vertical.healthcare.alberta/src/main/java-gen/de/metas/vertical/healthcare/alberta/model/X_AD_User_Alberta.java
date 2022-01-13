/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

// Generated Model - DO NOT CHANGE
package de.metas.vertical.healthcare.alberta.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_User_Alberta
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_User_Alberta extends org.compiere.model.PO implements I_AD_User_Alberta, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -606801886L;

    /** Standard Constructor */
    public X_AD_User_Alberta (final Properties ctx, final int AD_User_Alberta_ID, @Nullable final String trxName)
    {
      super (ctx, AD_User_Alberta_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_User_Alberta (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_Alberta_ID (final int AD_User_Alberta_ID)
	{
		if (AD_User_Alberta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_Alberta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_Alberta_ID, AD_User_Alberta_ID);
	}

	@Override
	public int getAD_User_Alberta_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Alberta_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	/** 
	 * Gender AD_Reference_ID=541317
	 * Reference name: Gender_List
	 */
	public static final int GENDER_AD_Reference_ID=541317;
	/** Unbekannt = 0 */
	public static final String GENDER_Unbekannt = "0";
	/** Weiblich = 1 */
	public static final String GENDER_Weiblich = "1";
	/** Männlich = 2 */
	public static final String GENDER_Maennlich = "2";
	/** Divers = 3 */
	public static final String GENDER_Divers = "3";
	@Override
	public void setGender (final @Nullable java.lang.String Gender)
	{
		set_Value (COLUMNNAME_Gender, Gender);
	}

	@Override
	public java.lang.String getGender() 
	{
		return get_ValueAsString(COLUMNNAME_Gender);
	}

	@Override
	public void setTimestamp (final @Nullable java.sql.Timestamp Timestamp)
	{
		set_Value (COLUMNNAME_Timestamp, Timestamp);
	}

	@Override
	public java.sql.Timestamp getTimestamp() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Timestamp);
	}

	/** 
	 * Title AD_Reference_ID=541318
	 * Reference name: Title_List
	 */
	public static final int TITLE_AD_Reference_ID=541318;
	/** Unbekannt = 0 */
	public static final String TITLE_Unbekannt = "0";
	/** Dr. = 1 */
	public static final String TITLE_Dr = "1";
	/** Prof. Dr. = 2 */
	public static final String TITLE_ProfDr = "2";
	/** Dipl. Ing. = 3 */
	public static final String TITLE_DiplIng = "3";
	/** Dipl. Med. = 4 */
	public static final String TITLE_DiplMed = "4";
	/** Dipl. Psych. = 5 */
	public static final String TITLE_DiplPsych = "5";
	/** Dr. Dr. = 6 */
	public static final String TITLE_DrDr = "6";
	/** Dr. med. = 7 */
	public static final String TITLE_DrMed = "7";
	/** Prof. Dr. Dr. = 8 */
	public static final String TITLE_ProfDrDr = "8";
	/** Prof. = 9 */
	public static final String TITLE_Prof = "9";
	/** Prof. Dr. med. = 10 */
	public static final String TITLE_ProfDrMed = "10";
	/** Rechtsanwalt = 11 */
	public static final String TITLE_Rechtsanwalt = "11";
	/** Rechtsanwältin = 12 */
	public static final String TITLE_Rechtsanwaeltin = "12";
	/** Schwester (Orden) = 13 */
	public static final String TITLE_SchwesterOrden = "13";
	@Override
	public void setTitle (final @Nullable java.lang.String Title)
	{
		set_Value (COLUMNNAME_Title, Title);
	}

	@Override
	public java.lang.String getTitle() 
	{
		return get_ValueAsString(COLUMNNAME_Title);
	}
}