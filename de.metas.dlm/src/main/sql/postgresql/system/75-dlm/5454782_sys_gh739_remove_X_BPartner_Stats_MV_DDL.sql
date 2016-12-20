
--Drop and recreate the view without the X_BPartner_Stats_MV table
DROP VIEW IF EXISTS v_bpartnercockpit;

CREATE OR REPLACE VIEW v_bpartnercockpit AS 
 SELECT l.c_location_id AS v_bpartnercockpit_id, bp.ad_client_id, bp.ad_org_id, bp.isactive, bp.created, bp.createdby, bp.updated, bp.updatedby, bp.c_bpartner_id, bp.value, bp.name AS suchname, bp.name2, 
 bp.description, bp.c_bp_group_id, bp.isonetime, bp.isprospect, bp.isvendor, bp.ad_language, bp.so_creditlimit, stats.so_creditused, stats.so_creditused - bp.so_creditlimit AS so_creditavailable, bp.c_paymentterm_id, 
 bp.m_discountschema_id, bp.c_dunning_id, bp.freightcostrule, bp.deliveryviarule, bp.salesrep_id, stats.socreditstatus, bp.issalesrep, bp.iscustomer, c.ad_user_id, 
 c.name AS contactname, c.firstname, c.lastname, c.description AS contactdescription, c.email, c.emailuser, c.c_greeting_id AS bpcontactgreeting, c.comments, c.phone, c.phone2, c.fax, 
 c.notificationtype, l.c_bpartner_location_id, a.postal, a.city, a.address1, a.address2, a.address3, a.address4, a.c_region_id, a.c_country_id, cc.name AS countryname, ''::bpchar AS createso, 
 stats.totalopenbalance,
 btrim((((((COALESCE(bp.name::text, ''::text) || ' '::text) || COALESCE(bp.name2::text, ''::text)) || ' '::text) || COALESCE(c.firstname, ''::character varying)::text) || ' '::text) || COALESCE(c.name, ''::character varying)::text) AS autosuche, 
 bpcs.search, bp.iscompany, c.isdefaultcontact, ((
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
        END AS anschrifttyp, ( SELECT sum(s.statsdlcustomers) AS sum
           FROM c_sponsor s
          WHERE bp.c_bpartner_id = s.c_bpartner_id
          GROUP BY s.c_bpartner_id) AS dl_endk, ( SELECT sum(s.statsdlsalesreps) AS sum
           FROM c_sponsor s
          WHERE bp.c_bpartner_id = s.c_bpartner_id
          GROUP BY s.c_bpartner_id) AS dl_vp, l.isshipto, l.isshiptodefault, l.isbillto, l.isbilltodefault, bp.companyname, c.title, COALESCE(c.c_greeting_id, bp.c_greeting_id) AS c_greeting_id
   FROM c_bpartner bp
   JOIN c_bpartner_stats stats on bp.c_bpartner_id = stats.c_bpartner_id
   LEFT JOIN c_bpartner_location l ON bp.c_bpartner_id = l.c_bpartner_id AND l.isactive = 'Y'::bpchar
   LEFT JOIN ad_user c ON bp.c_bpartner_id = c.c_bpartner_id AND (c.c_bpartner_location_id IS NULL OR c.c_bpartner_location_id = l.c_bpartner_location_id) AND c.isactive = 'Y'::bpchar
   LEFT JOIN c_location a ON l.c_location_id = a.c_location_id
   LEFT JOIN c_country cc ON a.c_country_id = cc.c_country_id
   LEFT JOIN x_bpartner_cockpit_search_mv bpcs ON bp.c_bpartner_id = bpcs.c_bpartner_id
  WHERE bp.iscustomer = 'Y'::bpchar OR bp.isprospect = 'Y'::bpchar;


DROP FUNCTION IF EXISTS x_bpartner_stats_mv_c_invoice_i_ft() CASCADE;
DROP FUNCTION IF EXISTS x_bpartner_stats_mv_c_invoice_u_ft() CASCADE;

DROP FUNCTION IF EXISTS x_bpartner_stats_mv_c_payment_i_ft() CASCADE;
DROP FUNCTION IF EXISTS x_bpartner_stats_mv_c_payment_u_ft() CASCADE;

DROP FUNCTION IF EXISTS x_bpartner_stats_mv_c_subscriptioncontrol_i_ft() CASCADE;
DROP FUNCTION IF EXISTS x_bpartner_stats_mv_c_subscriptioncontrol_u_ft() CASCADE;

DROP VIEW IF EXISTS x_bpartner_stats_v;
DROP TABLE IF EXISTS x_bpartner_stats_mv;
