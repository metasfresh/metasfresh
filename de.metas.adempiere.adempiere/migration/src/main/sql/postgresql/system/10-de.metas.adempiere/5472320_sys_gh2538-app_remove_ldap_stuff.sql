--
-- AD_LdapAccess
--


DELETE FROM ad_ui_elementgroup WHERE ad_ui_column_ID IN (select ad_ui_column_ID FROM ad_ui_column WHERE ad_ui_section_ID IN (select ad_ui_section_id from ad_ui_section where AD_Tab_ID=851));
DELETE FROM ad_ui_column WHERE ad_ui_section_ID IN (select ad_ui_section_id from ad_ui_section where AD_Tab_ID=851);
DELETE FROM ad_ui_section_trl WHERE ad_ui_section_id IN (select ad_ui_section_ID FROM ad_ui_section WHERE AD_Tab_ID=851);
DELETE FROM ad_ui_section WHERE AD_Tab_ID=851; -- LDAP-Zugriff in Nutzer
DELETE FROM AD_UI_Element WHERE AD_Tab_ID=851; -- LDAP-Zugriff in Nutzer

DROP TABLE AD_LdapAccess;

-- 2017-09-22T07:53:47.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=851
;

-- 2017-09-22T07:53:47.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab WHERE AD_Tab_ID=851
;

-- 2017-09-22T07:57:57.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=852
;

-- 2017-09-22T07:57:57.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Tab WHERE AD_Tab_ID=852
;

-- 2017-09-22T07:58:06.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=904
;

-- 2017-09-22T07:58:06.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=904
;

-- 2017-09-22T07:59:12.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=3096
;

-- 2017-09-22T07:59:12.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=3096
;

--
-- AD_LdapProcessorLog
--

DELETE FROM AD_Menu where (ad_window_id)=(389); -- window LDAP-Server-- 2017-09-22T08:01:00.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=389
;

-- 2017-09-22T08:01:00.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=389
;

--
-- Table AD_LdapProcessorLog
--
-- 2017-09-22T08:01:53.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=903
;

-- 2017-09-22T08:01:53.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=903
;

DROP TABLE AD_LdapProcessorLog;

--
-- Table AD_LdapProcessor
--
-- 2017-09-22T08:02:35.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=902
;

-- 2017-09-22T08:02:35.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=902
;

DROP TABLE AD_LdapProcessor;


--
-- REmove columns from ad_system
--
-- LDAPDomain AD_Element_ID=2549
DELETE FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2549);
DELETE FROM AD_Column WHERE AD_Element_ID=2549;
DELETE FROM AD_Element WHERE AD_Element_ID=2549;

ALTER TABLE AD_System DROP COLUMN LDAPDomain;

-- LDAPHost AD_Element_ID=2550
DELETE FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2550);
DELETE FROM AD_Column WHERE AD_Element_ID=2550;
DELETE FROM AD_Element WHERE AD_Element_ID=2550;

ALTER TABLE AD_System DROP COLUMN LDAPHost;


-- LDAPUser AD_Element_ID=2546
DELETE FROM ad_ui_element WHERE AD_Field_ID IN (select AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2546));
DELETE FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2546);
DELETE FROM exp_formatline WHERE AD_Column_ID IN (select AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2546);
DELETE FROM AD_Column WHERE AD_Element_ID=2546;
DELETE FROM AD_Element WHERE AD_Element_ID=2546;
DROP VIEW public.rv_bpartner;

ALTER TABLE AD_User DROP COLUMN LDAPUser;

CREATE OR REPLACE VIEW public.rv_bpartner AS 
SELECT 
    bp.ad_client_id,
    bp.ad_org_id,
    bp.isactive,
    bp.created,
    bp.createdby,
    bp.updated,
    bp.updatedby,
    bp.c_bpartner_id,
    bp.value,
    bp.name,
    bp.name2,
    bp.description,
    bp.issummary,
    bp.c_bp_group_id,
    bp.isonetime,
    bp.isprospect,
    bp.isvendor,
    bp.iscustomer,
    bp.isemployee,
    bp.issalesrep,
    bp.referenceno,
    bp.duns,
    bp.url,
    bp.ad_language,
    bp.taxid,
    bp.istaxexempt,
    bp.c_invoiceschedule_id,
    bp.rating,
    bp.salesvolume,
    bp.numberemployees,
    bp.naics,
    bp.firstsale,
    bp.acqusitioncost,
    bp.potentiallifetimevalue,
    stats.actuallifetimevalue,
    bp.shareofcustomer,
    bp.paymentrule,
    bp.so_creditlimit,
    stats.so_creditused,
    stats.so_creditused - bp.so_creditlimit AS so_creditavailable,
    bp.c_paymentterm_id,
    bp.m_pricelist_id,
    bp.m_discountschema_id,
    bp.c_dunning_id,
    bp.isdiscountprinted,
    bp.so_description,
    bp.poreference,
    bp.paymentrulepo,
    bp.po_pricelist_id,
    bp.po_discountschema_id,
    bp.po_paymentterm_id,
    bp.documentcopies,
    bp.c_greeting_id,
    bp.invoicerule,
    bp.deliveryrule,
    bp.freightcostrule,
    bp.deliveryviarule,
    bp.salesrep_id,
    bp.sendemail,
    bp.bpartner_parent_id,
    bp.invoice_printformat_id,
    stats.socreditstatus,
    bp.shelflifeminpct,
    bp.ad_orgbp_id,
    bp.flatdiscount,
    stats.totalopenbalance,
    c.ad_user_id,
    c.name AS contactname,
    c.description AS contactdescription,
    c.email,
    c.supervisor_id,
    c.emailuser,
    c.c_greeting_id AS bpcontactgreeting,
    c.title,
    c.comments,
    c.phone,
    c.phone2,
    c.fax,
    c.birthday,
    c.ad_orgtrx_id,
    c.emailverify,
    c.emailverifydate,
    c.notificationtype,
    l.c_bpartner_location_id,
    a.postal,
    a.city,
    a.address1,
    a.address2,
    a.address3,
    a.c_region_id,
    COALESCE(r.name, a.regionname) AS regionname,
    a.c_country_id,
    cc.name AS countryname,
    sp.sponsorno
FROM x_bpartner_cockpit_search_mv bpcs
     LEFT JOIN c_bpartner bp ON bp.c_bpartner_id = bpcs.c_bpartner_id
     LEFT JOIN c_bpartner_stats stats ON bp.c_bpartner_id = stats.c_bpartner_id
     LEFT JOIN c_bpartner_location l ON bp.c_bpartner_id = l.c_bpartner_id AND l.isactive = 'Y'::bpchar
     LEFT JOIN ad_user c ON bp.c_bpartner_id = c.c_bpartner_id AND (c.c_bpartner_location_id IS NULL OR c.c_bpartner_location_id = l.c_bpartner_location_id) AND c.isactive = 'Y'::bpchar
     LEFT JOIN c_location a ON l.c_location_id = a.c_location_id
     LEFT JOIN c_region r ON a.c_region_id = r.c_region_id
     JOIN c_country cc ON a.c_country_id = cc.c_country_id
     LEFT JOIN c_sponsor sp ON bp.c_bpartner_id = sp.c_bpartner_id
     LEFT JOIN c_sponsor_salesrep ssr ON sp.c_sponsor_id = ssr.c_sponsor_id;

