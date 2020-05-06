-- 2018-02-08T19:02:54.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543858,0,'OpenItems',TO_TIMESTAMP('2018-02-08 19:02:53','YYYY-MM-DD HH24:MI:SS'),100,'','D','','Y','Offene Posten','Offene Posten',TO_TIMESTAMP('2018-02-08 19:02:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-08T19:02:54.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543858 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-02-08T19:03:31.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543858, ColumnName='OpenItems', Description='', Help='', Name='Offene Posten',Updated=TO_TIMESTAMP('2018-02-08 19:03:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554364
;

-- 2018-02-08T19:03:31.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Offene Posten', Description='', Help='' WHERE AD_Column_ID=554364
;



DROP VIEW public.v_bpartnercockpit;

/* DDL */ SELECT public.db_alter_table('C_BPartner_Stats','ALTER TABLE public.C_BPartner_Stats RENAME COLUMN TotalOpenBalance TO OpenItems')
;

CREATE OR REPLACE VIEW public.v_bpartnercockpit AS 
 SELECT l.c_location_id AS v_bpartnercockpit_id,
    bp.ad_client_id,
    bp.ad_org_id,
    bp.isactive,
    bp.created,
    bp.createdby,
    bp.updated,
    bp.updatedby,
    bp.c_bpartner_id,
    bp.value,
    bp.name AS suchname,
    bp.name2,
    bp.description,
    bp.c_bp_group_id,
    bp.isonetime,
    bp.isprospect,
    bp.isvendor,
    bp.ad_language,
    stats.so_creditused,
    bp.c_paymentterm_id,
    bp.m_discountschema_id,
    bp.c_dunning_id,
    bp.freightcostrule,
    bp.deliveryviarule,
    bp.salesrep_id,
    stats.socreditstatus,
    bp.issalesrep,
    bp.iscustomer,
    c.ad_user_id,
    c.name AS contactname,
    c.firstname,
    c.lastname,
    c.description AS contactdescription,
    c.email,
    c.emailuser,
    c.c_greeting_id AS bpcontactgreeting,
    c.comments,
    c.phone,
    c.phone2,
    c.fax,
    c.notificationtype,
    l.c_bpartner_location_id,
    a.postal,
    a.city,
    a.address1,
    a.address2,
    a.address3,
    a.address4,
    a.c_region_id,
    a.c_country_id,
    cc.name AS countryname,
    ''::bpchar AS createso,
    stats.openitems,
    btrim((((((COALESCE(bp.name::text, ''::text) || ' '::text) || COALESCE(bp.name2::text, ''::text)) || ' '::text) || COALESCE(c.firstname, ''::character varying)::text) || ' '::text) || COALESCE(c.name, ''::character varying)::text) AS autosuche,
    bpcs.search,
    bp.iscompany,
    c.isdefaultcontact,
    ((
        CASE
            WHEN l.isshipto = 'Y'::bpchar AND l.isshiptodefault = 'N'::bpchar THEN 'Liefer. '::text
            WHEN l.isshipto = 'Y'::bpchar AND l.isshiptodefault = 'Y'::bpchar THEN 'Liefer. (S) '::text
            WHEN l.isshipto = 'N'::bpchar THEN ''::text
            ELSE NULL::text
        END ||
        CASE
            WHEN l.isbillto = 'Y'::bpchar AND l.isbilltodefault = 'N'::bpchar THEN 'Rech. '::text
            WHEN l.isbillto = 'Y'::bpchar AND l.isbilltodefault = 'Y'::bpchar THEN 'Rech. (S) '::text
            WHEN l.isbillto = 'N'::bpchar THEN ''::text
            ELSE NULL::text
        END) ||
        CASE
            WHEN l.issubscriptionto = 'Y'::bpchar AND l.issubscriptionto = 'N'::bpchar THEN 'Abo '::text
            WHEN l.issubscriptionto = 'Y'::bpchar AND l.issubscriptionto = 'Y'::bpchar THEN 'Abo (S) '::text
            WHEN l.issubscriptionto = 'N'::bpchar THEN ''::text
            ELSE NULL::text
        END) ||
        CASE
            WHEN l.iscommissionto = 'Y'::bpchar AND l.iscommissionto = 'N'::bpchar THEN 'Prov '::text
            WHEN l.iscommissionto = 'Y'::bpchar AND l.iscommissionto = 'Y'::bpchar THEN 'Prov (S) '::text
            WHEN l.iscommissionto = 'N'::bpchar THEN ''::text
            ELSE NULL::text
        END AS anschrifttyp,
    ( SELECT sum(s.statsdlcustomers) AS sum
           FROM c_sponsor s
          WHERE bp.c_bpartner_id = s.c_bpartner_id
          GROUP BY s.c_bpartner_id) AS dl_endk,
    ( SELECT sum(s.statsdlsalesreps) AS sum
           FROM c_sponsor s
          WHERE bp.c_bpartner_id = s.c_bpartner_id
          GROUP BY s.c_bpartner_id) AS dl_vp,
    l.isshipto,
    l.isshiptodefault,
    l.isbillto,
    l.isbilltodefault,
    bp.companyname,
    c.title,
    COALESCE(c.c_greeting_id, bp.c_greeting_id) AS c_greeting_id
   FROM c_bpartner bp
     JOIN c_bpartner_stats stats ON bp.c_bpartner_id = stats.c_bpartner_id
     LEFT JOIN c_bpartner_location l ON bp.c_bpartner_id = l.c_bpartner_id AND l.isactive = 'Y'::bpchar
     LEFT JOIN ad_user c ON bp.c_bpartner_id = c.c_bpartner_id AND (c.c_bpartner_location_id IS NULL OR c.c_bpartner_location_id = l.c_bpartner_location_id) AND c.isactive = 'Y'::bpchar
     LEFT JOIN c_location a ON l.c_location_id = a.c_location_id
     LEFT JOIN c_country cc ON a.c_country_id = cc.c_country_id
     LEFT JOIN x_bpartner_cockpit_search_mv bpcs ON bp.c_bpartner_id = bpcs.c_bpartner_id
  WHERE bp.iscustomer = 'Y'::bpchar OR bp.isprospect = 'Y'::bpchar;

 
  

-- 2018-02-08T19:08:35.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='Y',Updated=TO_TIMESTAMP('2018-02-08 19:08:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540060
;

-- 2018-02-08T19:08:52.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543858, ColumnName='OpenItems', Description='', EntityType='de.metas.swat', Help='', IsUpdateable='N', Name='Offene Posten',Updated=TO_TIMESTAMP('2018-02-08 19:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=541699
;

-- 2018-02-08T19:08:52.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Offene Posten', Description='', Help='' WHERE AD_Column_ID=541699
;

-- 2018-02-08T19:09:01.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2018-02-08 19:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540060
;

