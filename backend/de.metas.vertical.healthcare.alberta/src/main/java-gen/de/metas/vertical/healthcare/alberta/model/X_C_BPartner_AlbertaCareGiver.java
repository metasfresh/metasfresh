// Generated Model - DO NOT CHANGE
package de.metas.vertical.healthcare.alberta.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_AlbertaCareGiver
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_AlbertaCareGiver extends org.compiere.model.PO implements I_C_BPartner_AlbertaCareGiver, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1861339184L;

    /** Standard Constructor */
    public X_C_BPartner_AlbertaCareGiver (final Properties ctx, final int C_BPartner_AlbertaCareGiver_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_AlbertaCareGiver_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_AlbertaCareGiver (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_AlbertaCareGiver_ID (final int C_BPartner_AlbertaCareGiver_ID)
	{
		if (C_BPartner_AlbertaCareGiver_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_AlbertaCareGiver_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_AlbertaCareGiver_ID, C_BPartner_AlbertaCareGiver_ID);
	}

	@Override
	public int getC_BPartner_AlbertaCareGiver_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_AlbertaCareGiver_ID);
	}

	@Override
	public void setC_BPartner_Caregiver_ID (final int C_BPartner_Caregiver_ID)
	{
		if (C_BPartner_Caregiver_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Caregiver_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Caregiver_ID, C_BPartner_Caregiver_ID);
	}

	@Override
	public int getC_BPartner_Caregiver_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Caregiver_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setIsLegalCarer (final boolean IsLegalCarer)
	{
		set_Value (COLUMNNAME_IsLegalCarer, IsLegalCarer);
	}

	@Override
	public boolean isLegalCarer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsLegalCarer);
	}

	/** 
	 * Type_Contact AD_Reference_ID=541321
	 * Reference name: ContactType_List
	 */
	public static final int TYPE_CONTACT_AD_Reference_ID=541321;
	/** Unknown = 0 */
	public static final String TYPE_CONTACT_Unknown = "0";
	/** Ehemann = 1 */
	public static final String TYPE_CONTACT_Ehemann = "1";
	/** Ehefrau = 2 */
	public static final String TYPE_CONTACT_Ehefrau = "2";
	/** Lebensgefährte/in = 3 */
	public static final String TYPE_CONTACT_LebensgefaehrteIn = "3";
	/** Sohn = 4 */
	public static final String TYPE_CONTACT_Sohn = "4";
	/** Tochter = 5 */
	public static final String TYPE_CONTACT_Tochter = "5";
	/** Enkel = 6 */
	public static final String TYPE_CONTACT_Enkel = "6";
	/** Enkelin = 7 */
	public static final String TYPE_CONTACT_Enkelin = "7";
	/** Urenkel = 8 */
	public static final String TYPE_CONTACT_Urenkel = "8";
	/** Urenkelin = 9 */
	public static final String TYPE_CONTACT_Urenkelin = "9";
	/** Mutter = 10 */
	public static final String TYPE_CONTACT_Mutter = "10";
	/** Vater = 11 */
	public static final String TYPE_CONTACT_Vater = "11";
	/** Onkel = 12 */
	public static final String TYPE_CONTACT_Onkel = "12";
	/** Tante = 13 */
	public static final String TYPE_CONTACT_Tante = "13";
	/** Nichte = 14 */
	public static final String TYPE_CONTACT_Nichte = "14";
	/** Neffe = 15 */
	public static final String TYPE_CONTACT_Neffe = "15";
	/** Großonkel = 16 */
	public static final String TYPE_CONTACT_Grossonkel = "16";
	/** Großtante = 17 */
	public static final String TYPE_CONTACT_Grosstante = "17";
	/** Großnichte = 18 */
	public static final String TYPE_CONTACT_Grossnichte = "18";
	/** Großneffe = 19 */
	public static final String TYPE_CONTACT_Grossneffe = "19";
	/** Schwester = 20 */
	public static final String TYPE_CONTACT_Schwester = "20";
	/** Bruder = 21 */
	public static final String TYPE_CONTACT_Bruder = "21";
	/** Cousin = 22 */
	public static final String TYPE_CONTACT_Cousin = "22";
	/** Cousine = 23 */
	public static final String TYPE_CONTACT_Cousine = "23";
	/** Schwager = 24 */
	public static final String TYPE_CONTACT_Schwager = "24";
	/** Schwägerin = 25 */
	public static final String TYPE_CONTACT_Schwaegerin = "25";
	/** Schwagersbruder = 26 */
	public static final String TYPE_CONTACT_Schwagersbruder = "26";
	/** Schwagersschwester = 27 */
	public static final String TYPE_CONTACT_Schwagersschwester = "27";
	/** Schwiegermutter = 28 */
	public static final String TYPE_CONTACT_Schwiegermutter = "28";
	/** Schwiegervater = 29 */
	public static final String TYPE_CONTACT_Schwiegervater = "29";
	/** Schwiegeronkel = 30 */
	public static final String TYPE_CONTACT_Schwiegeronkel = "30";
	/** Schwiegertante = 31 */
	public static final String TYPE_CONTACT_Schwiegertante = "31";
	/** Schwiegerkind = 32 */
	public static final String TYPE_CONTACT_Schwiegerkind = "32";
	/** Schwiegersohn = 33 */
	public static final String TYPE_CONTACT_Schwiegersohn = "33";
	/** Schwiegertochter = 34 */
	public static final String TYPE_CONTACT_Schwiegertochter = "34";
	/** Schwiegerenkel = 35 */
	public static final String TYPE_CONTACT_Schwiegerenkel = "35";
	/** Schwiegerenkelin = 36 */
	public static final String TYPE_CONTACT_Schwiegerenkelin = "36";
	/** Stiefmutter = 37 */
	public static final String TYPE_CONTACT_Stiefmutter = "37";
	/** Stiefvater = 38 */
	public static final String TYPE_CONTACT_Stiefvater = "38";
	/** Stiefschwester = 39 */
	public static final String TYPE_CONTACT_Stiefschwester = "39";
	/** Stiefbruder = 40 */
	public static final String TYPE_CONTACT_Stiefbruder = "40";
	/** Stieftochter = 41 */
	public static final String TYPE_CONTACT_Stieftochter = "41";
	/** Stiefsohn = 42 */
	public static final String TYPE_CONTACT_Stiefsohn = "42";
	/** Stiefenkel = 43 */
	public static final String TYPE_CONTACT_Stiefenkel = "43";
	/** Stiefurenkel = 44 */
	public static final String TYPE_CONTACT_Stiefurenkel = "44";
	/** Pflegesohn = 45 */
	public static final String TYPE_CONTACT_Pflegesohn = "45";
	/** Pflegetochter = 46 */
	public static final String TYPE_CONTACT_Pflegetochter = "46";
	/** Pflegemutter = 47 */
	public static final String TYPE_CONTACT_Pflegemutter = "47";
	/** Pflegevater = 48 */
	public static final String TYPE_CONTACT_Pflegevater = "48";
	/** Halbschwester = 49 */
	public static final String TYPE_CONTACT_Halbschwester = "49";
	/** Halbbruder = 50 */
	public static final String TYPE_CONTACT_Halbbruder = "50";
	/** Halbonkel = 51 */
	public static final String TYPE_CONTACT_Halbonkel = "51";
	/** Halbtante = 52 */
	public static final String TYPE_CONTACT_Halbtante = "52";
	/** Halbcousin = 53 */
	public static final String TYPE_CONTACT_Halbcousin = "53";
	/** Halbcousine = 54 */
	public static final String TYPE_CONTACT_Halbcousine = "54";
	/** Nachbarin = 55 */
	public static final String TYPE_CONTACT_Nachbarin = "55";
	/** Nachbar = 56 */
	public static final String TYPE_CONTACT_Nachbar = "56";
	/** Betreuungsbüro = 57 */
	public static final String TYPE_CONTACT_Betreuungsbuero = "57";
	/** Freund(in) = 58 */
	public static final String TYPE_CONTACT_FreundIn = "58";
	@Override
	public void setType_Contact (final @Nullable String Type_Contact)
	{
		set_Value (COLUMNNAME_Type_Contact, Type_Contact);
	}

	@Override
	public String getType_Contact()
	{
		return get_ValueAsString(COLUMNNAME_Type_Contact);
	}
}