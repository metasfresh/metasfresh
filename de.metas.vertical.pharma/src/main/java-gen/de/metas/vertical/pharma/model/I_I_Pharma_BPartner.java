package de.metas.vertical.pharma.model;


/** Generated Interface for I_Pharma_BPartner
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_Pharma_BPartner 
{

    /** TableName=I_Pharma_BPartner */
    public static final String Table_Name = "I_Pharma_BPartner";

    /** AD_Table_ID=541197 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_AD_Client>(I_I_Pharma_BPartner.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_AD_Org>(I_I_Pharma_BPartner.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set b00adrnr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00adrnr (java.lang.String b00adrnr);

	/**
	 * Get b00adrnr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00adrnr();

    /** Column definition for b00adrnr */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00adrnr = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00adrnr", null);
    /** Column name b00adrnr */
    public static final String COLUMNNAME_b00adrnr = "b00adrnr";

	/**
	 * Set b00email.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00email (java.lang.String b00email);

	/**
	 * Get b00email.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00email();

    /** Column definition for b00email */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00email = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00email", null);
    /** Column name b00email */
    public static final String COLUMNNAME_b00email = "b00email";

	/**
	 * Set b00email2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00email2 (java.lang.String b00email2);

	/**
	 * Get b00email2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00email2();

    /** Column definition for b00email2 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00email2 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00email2", null);
    /** Column name b00email2 */
    public static final String COLUMNNAME_b00email2 = "b00email2";

	/**
	 * Set b00fax1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00fax1 (java.lang.String b00fax1);

	/**
	 * Get b00fax1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00fax1();

    /** Column definition for b00fax1 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00fax1 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00fax1", null);
    /** Column name b00fax1 */
    public static final String COLUMNNAME_b00fax1 = "b00fax1";

	/**
	 * Set b00fax2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00fax2 (java.lang.String b00fax2);

	/**
	 * Get b00fax2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00fax2();

    /** Column definition for b00fax2 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00fax2 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00fax2", null);
    /** Column name b00fax2 */
    public static final String COLUMNNAME_b00fax2 = "b00fax2";

	/**
	 * Set b00gdat.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00gdat (java.sql.Timestamp b00gdat);

	/**
	 * Get b00gdat.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getb00gdat();

    /** Column definition for b00gdat */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00gdat = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00gdat", null);
    /** Column name b00gdat */
    public static final String COLUMNNAME_b00gdat = "b00gdat";

	/**
	 * Set b00gherlau.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00gherlau (java.lang.String b00gherlau);

	/**
	 * Get b00gherlau.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00gherlau();

    /** Column definition for b00gherlau */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00gherlau = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00gherlau", null);
    /** Column name b00gherlau */
    public static final String COLUMNNAME_b00gherlau = "b00gherlau";

	/**
	 * Set b00herster.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00herster (java.lang.String b00herster);

	/**
	 * Get b00herster.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00herster();

    /** Column definition for b00herster */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00herster = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00herster", null);
    /** Column name b00herster */
    public static final String COLUMNNAME_b00herster = "b00herster";

	/**
	 * Set b00hnrb.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00hnrb (java.lang.String b00hnrb);

	/**
	 * Get b00hnrb.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00hnrb();

    /** Column definition for b00hnrb */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00hnrb = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00hnrb", null);
    /** Column name b00hnrb */
    public static final String COLUMNNAME_b00hnrb = "b00hnrb";

	/**
	 * Set b00hnrbz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00hnrbz (java.lang.String b00hnrbz);

	/**
	 * Get b00hnrbz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00hnrbz();

    /** Column definition for b00hnrbz */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00hnrbz = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00hnrbz", null);
    /** Column name b00hnrbz */
    public static final String COLUMNNAME_b00hnrbz = "b00hnrbz";

	/**
	 * Set b00hnrv.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00hnrv (java.lang.String b00hnrv);

	/**
	 * Get b00hnrv.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00hnrv();

    /** Column definition for b00hnrv */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00hnrv = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00hnrv", null);
    /** Column name b00hnrv */
    public static final String COLUMNNAME_b00hnrv = "b00hnrv";

	/**
	 * Set b00hnrvz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00hnrvz (java.lang.String b00hnrvz);

	/**
	 * Get b00hnrvz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00hnrvz();

    /** Column definition for b00hnrvz */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00hnrvz = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00hnrvz", null);
    /** Column name b00hnrvz */
    public static final String COLUMNNAME_b00hnrvz = "b00hnrvz";

	/**
	 * Set b00homepag.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00homepag (java.lang.String b00homepag);

	/**
	 * Get b00homepag.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00homepag();

    /** Column definition for b00homepag */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00homepag = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00homepag", null);
    /** Column name b00homepag */
    public static final String COLUMNNAME_b00homepag = "b00homepag";

	/**
	 * Set b00land.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00land (java.lang.String b00land);

	/**
	 * Get b00land.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00land();

    /** Column definition for b00land */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00land = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00land", null);
    /** Column name b00land */
    public static final String COLUMNNAME_b00land = "b00land";

	/**
	 * Set b00lkz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00lkz (java.lang.String b00lkz);

	/**
	 * Get b00lkz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00lkz();

    /** Column definition for b00lkz */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00lkz = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00lkz", null);
    /** Column name b00lkz */
    public static final String COLUMNNAME_b00lkz = "b00lkz";

	/**
	 * Set b00name1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00name1 (java.lang.String b00name1);

	/**
	 * Get b00name1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00name1();

    /** Column definition for b00name1 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00name1 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00name1", null);
    /** Column name b00name1 */
    public static final String COLUMNNAME_b00name1 = "b00name1";

	/**
	 * Set b00name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00name2 (java.lang.String b00name2);

	/**
	 * Get b00name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00name2();

    /** Column definition for b00name2 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00name2 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00name2", null);
    /** Column name b00name2 */
    public static final String COLUMNNAME_b00name2 = "b00name2";

	/**
	 * Set b00name3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00name3 (java.lang.String b00name3);

	/**
	 * Get b00name3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00name3();

    /** Column definition for b00name3 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00name3 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00name3", null);
    /** Column name b00name3 */
    public static final String COLUMNNAME_b00name3 = "b00name3";

	/**
	 * Set b00ortpf.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00ortpf (java.lang.String b00ortpf);

	/**
	 * Get b00ortpf.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00ortpf();

    /** Column definition for b00ortpf */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00ortpf = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00ortpf", null);
    /** Column name b00ortpf */
    public static final String COLUMNNAME_b00ortpf = "b00ortpf";

	/**
	 * Set b00ortzu.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00ortzu (java.lang.String b00ortzu);

	/**
	 * Get b00ortzu.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00ortzu();

    /** Column definition for b00ortzu */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00ortzu = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00ortzu", null);
    /** Column name b00ortzu */
    public static final String COLUMNNAME_b00ortzu = "b00ortzu";

	/**
	 * Set b00pf1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00pf1 (java.lang.String b00pf1);

	/**
	 * Get b00pf1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00pf1();

    /** Column definition for b00pf1 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00pf1 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00pf1", null);
    /** Column name b00pf1 */
    public static final String COLUMNNAME_b00pf1 = "b00pf1";

	/**
	 * Set b00plzgk1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00plzgk1 (java.lang.String b00plzgk1);

	/**
	 * Get b00plzgk1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00plzgk1();

    /** Column definition for b00plzgk1 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00plzgk1 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00plzgk1", null);
    /** Column name b00plzgk1 */
    public static final String COLUMNNAME_b00plzgk1 = "b00plzgk1";

	/**
	 * Set b00plzpf1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00plzpf1 (java.lang.String b00plzpf1);

	/**
	 * Get b00plzpf1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00plzpf1();

    /** Column definition for b00plzpf1 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00plzpf1 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00plzpf1", null);
    /** Column name b00plzpf1 */
    public static final String COLUMNNAME_b00plzpf1 = "b00plzpf1";

	/**
	 * Set b00plzzu1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00plzzu1 (java.lang.String b00plzzu1);

	/**
	 * Get b00plzzu1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00plzzu1();

    /** Column definition for b00plzzu1 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00plzzu1 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00plzzu1", null);
    /** Column name b00plzzu1 */
    public static final String COLUMNNAME_b00plzzu1 = "b00plzzu1";

	/**
	 * Set b00regnr9.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00regnr9 (java.lang.String b00regnr9);

	/**
	 * Get b00regnr9.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00regnr9();

    /** Column definition for b00regnr9 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00regnr9 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00regnr9", null);
    /** Column name b00regnr9 */
    public static final String COLUMNNAME_b00regnr9 = "b00regnr9";

	/**
	 * Set b00sname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00sname (java.lang.String b00sname);

	/**
	 * Get b00sname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00sname();

    /** Column definition for b00sname */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00sname = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00sname", null);
    /** Column name b00sname */
    public static final String COLUMNNAME_b00sname = "b00sname";

	/**
	 * Set b00ssatz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00ssatz (java.lang.String b00ssatz);

	/**
	 * Get b00ssatz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00ssatz();

    /** Column definition for b00ssatz */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00ssatz = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00ssatz", null);
    /** Column name b00ssatz */
    public static final String COLUMNNAME_b00ssatz = "b00ssatz";

	/**
	 * Set b00str.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00str (java.lang.String b00str);

	/**
	 * Get b00str.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00str();

    /** Column definition for b00str */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00str = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00str", null);
    /** Column name b00str */
    public static final String COLUMNNAME_b00str = "b00str";

	/**
	 * Set b00tel1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00tel1 (java.lang.String b00tel1);

	/**
	 * Get b00tel1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00tel1();

    /** Column definition for b00tel1 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00tel1 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00tel1", null);
    /** Column name b00tel1 */
    public static final String COLUMNNAME_b00tel1 = "b00tel1";

	/**
	 * Set b00tel2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setb00tel2 (java.lang.String b00tel2);

	/**
	 * Get b00tel2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getb00tel2();

    /** Column definition for b00tel2 */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_b00tel2 = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "b00tel2", null);
    /** Column name b00tel2 */
    public static final String COLUMNNAME_b00tel2 = "b00tel2";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_C_BPartner>(I_I_Pharma_BPartner.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_C_BPartner_Location>(I_I_Pharma_BPartner.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_C_Country>(I_I_Pharma_BPartner.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Data import.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DataImport_ID();

	public org.compiere.model.I_C_DataImport getC_DataImport();

	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport);

    /** Column definition for C_DataImport_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_C_DataImport>(I_I_Pharma_BPartner.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
    /** Column name C_DataImport_ID */
    public static final String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_AD_User>(I_I_Pharma_BPartner.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (java.lang.String I_IsImported);

	/**
	 * Get Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Pharma BPartners.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_Pharma_BPartner_ID (int I_Pharma_BPartner_ID);

	/**
	 * Get Import Pharma BPartners.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_Pharma_BPartner_ID();

    /** Column definition for I_Pharma_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_I_Pharma_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "I_Pharma_BPartner_ID", null);
    /** Column name I_Pharma_BPartner_ID */
    public static final String COLUMNNAME_I_Pharma_BPartner_ID = "I_Pharma_BPartner_ID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, Object>(I_I_Pharma_BPartner.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_Pharma_BPartner, org.compiere.model.I_AD_User>(I_I_Pharma_BPartner.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
