

-- 11.05.2016 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=540071
;

-- 11.05.2016 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Menu WHERE AD_Menu_ID=540071
;

-- 11.05.2016 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=540071 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- 11.05.2016 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540048
;

-- 11.05.2016 14:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540048
;



-- DROP Functions because the table they work with does not exist any more. will leave the bodies here as comments

-- Function: update_bpstats()

-- DROP FUNCTION update_bpstats();

/*CREATE OR REPLACE FUNCTION update_bpstats()
  RETURNS void AS
$BODY$DECLARE
BEGIN
update c_bpartner_stats set
ad_client_id = bps.ad_client_id,
ad_org_id = bps.ad_org_id,
c_bpartner_id = bps.c_bpartner_id,
c_bpartnerstats_id = bps.c_bpartnerstats_id, 
isactive = bps.isactive, 
updated = now(), 
updatedby = 100, 
creditused = bps.creditused, 
lifetimevalue = bps.lifetimevalue, 
openamt = bps.openamt, 
revenued30 = bps.revenued30, 
revenuem0 = bps.revenuem0, 
revenuem1 = bps.revenuem1, 
revenuem12 = bps.revenuem12, 
revenuem2 = bps.revenuem2, 
revenuem3 = bps.revenuem3, 
revenuem4 = bps.revenuem4, 
revenuem5 = bps.revenuem5, 
revenuem6 = bps.revenuem6, 
revenuem7 = bps.revenuem7, 
revenuem8 = bps.revenuem8, 
revenuem9 = bps.revenuem9, 
revenuem10 = bps.revenuem10, 
revenuem11 = bps.revenuem11, 
revenueq0 = bps.revenueq0, 
revenueq1 = bps.revenueq1, 
revenueq2 = bps.revenueq2, 
revenueq3 = bps.revenueq3, 
revenueq4 = bps.revenueq4, 
revenuey0 = bps.revenuey0, 
revenuey1 = bps.revenuey1, 
revenuey2 = bps.revenuey2, 
revenuey3 = bps.revenuey3, 
revenuey4 = bps.revenuey4, 
revenuey5 = bps.revenuey5, 
firstsale = bps.firstsale 
from c_bpartner_stats bps
where c_bpartner_stats.c_bpartner_id in (select c_bpartner_id from c_bpartner_stats)
and c_bpartner_stats.c_bpartner_id = bps.c_bpartner_id
;

END
;$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION update_bpstats()
  OWNER TO metasfresh;
  
  */
  
  
  
  
DROP FUNCTION update_bpstats() ;


-- Function: insert_bpstats()

-- DROP FUNCTION insert_bpstats();

/*

CREATE OR REPLACE FUNCTION insert_bpstats()
  RETURNS void AS
$BODY$DECLARE
BEGIN
-- Drop Views
DROP VIEW adempiere.v_bpartnercockpit;
DROP VIEW adempiere.v_bpartner_stats;

-- Drop View
DROP TABLE adempiere.c_bpartner_stats;

-- Tabelle
CREATE TABLE adempiere.c_bpartner_stats
(
  ad_client_id numeric(10) NOT NULL,
  ad_org_id numeric(10) NOT NULL,
  c_bpartnerstats_id numeric(10) NOT NULL,
  created timestamp without time zone NOT NULL,
  createdby numeric(10) NOT NULL,
  isactive character(1) NOT NULL DEFAULT 'Y'::bpchar,
  updated timestamp without time zone NOT NULL,
  updatedby numeric(10) NOT NULL,
  revenuey5 numeric,
  creditused numeric,
  lifetimevalue numeric,
  openamt numeric,
  revenued30 numeric,
  revenuem0 numeric,
  revenuem1 numeric,
  revenuem10 numeric,
  revenuem11 numeric,
  revenuem12 numeric,
  revenuem2 numeric,
  revenuem3 numeric,
  revenuem4 numeric,
  revenuem5 numeric,
  revenuem6 numeric,
  revenuem7 numeric,
  revenuem8 numeric,
  revenuem9 numeric,
  revenueq0 numeric,
  revenueq1 numeric,
  revenueq2 numeric,
  revenueq3 numeric,
  revenueq4 numeric,
  revenuey0 numeric,
  revenuey1 numeric,
  revenuey2 numeric,
  revenuey3 numeric,
  revenuey4 numeric,
  c_bpartner_id numeric(10),
  firstsale timestamp without time zone,
  CONSTRAINT c_bpartner_stats_key PRIMARY KEY (c_bpartnerstats_id),
  CONSTRAINT c_bpartner_id UNIQUE (c_bpartner_id),
  CONSTRAINT c_bpartner_stats_isactive_check CHECK (isactive = ANY (ARRAY['Y'::bpchar, 'N'::bpchar]))
) 
WITHOUT OIDS;
ALTER TABLE adempiere.c_bpartner_stats OWNER TO adempiere;


-- Index: adempiere.c_bpartner_stats_bpartner_id

-- DROP INDEX adempiere.c_bpartner_stats_bpartner_id;

CREATE INDEX c_bpartner_stats_bpartner_id
  ON adempiere.c_bpartner_stats
  USING btree
  (c_bpartnerstats_id);



-- View: "adempiere.v_bpartnercockpit"
CREATE OR REPLACE VIEW adempiere.v_bpartnercockpit AS 
 SELECT l.c_location_id AS v_bpartnercockpit_id, bp.ad_client_id, bp.ad_org_id, bp.isactive, bp.created, bp.createdby, bp.updated, bp.updatedby, bp.c_bpartner_id, bp.value, bp.name AS suchname, bp.name2, bp.description, bp.c_bp_group_id, bp.isonetime, bp.isprospect, bp.isvendor, bp.ad_language, bp.so_creditlimit, bp.so_creditused, bp.so_creditused - bp.so_creditlimit AS so_creditavailable, bp.c_paymentterm_id, bp.m_discountschema_id, bp.c_dunning_id, bp.c_greeting_id, bp.freightcostrule, bp.deliveryviarule, bp.salesrep_id, bp.socreditstatus, bp.issalesrep, bp.iscustomer, c.ad_user_id, c.name AS contactname, c.description AS contactdescription, c.email, c.emailuser, c.c_greeting_id AS bpcontactgreeting, c.comments, c.phone, c.phone2, c.fax, c.notificationtype, l.c_bpartner_location_id, a.postal, a.city, a.address1, a.address2, a.address3, a.c_region_id, cc.name AS countryname, ''::bpchar AS createso, bps.firstsale, bp.totalopenbalance, bps.revenuey0 AS revenueoneyear, (bp.name::text || ' '::text) || c.name::text AS autosuchname, bps.creditused, bps.lifetimevalue, bps.openamt, bps.revenued30, bps.revenuem0, bps.revenuem1, bps.revenuem12, bps.revenuem2, bps.revenuem3, bps.revenuem4, bps.revenuem5, bps.revenuem6, bps.revenuem7, bps.revenuem8, bps.revenuem9, bps.revenuem10, bps.revenuem11, bps.revenueq0, bps.revenueq1, bps.revenueq2, bps.revenueq3, bps.revenueq4, bps.revenuey0, bps.revenuey1, bps.revenuey2, bps.revenuey3, bps.revenuey4, bps.revenuey5, ((
        CASE
            WHEN l.isshipto = 'Y'::bpchar AND l.isshiptodefault = 'N'::bpchar THEN 'Lieferung '::text
            WHEN l.isshipto = 'Y'::bpchar AND l.isshiptodefault = 'Y'::bpchar THEN 'Lieferung (S) '::text
            WHEN l.isshipto = 'N'::bpchar THEN ''::text
            ELSE NULL::text
        END || 
        CASE
            WHEN l.isbillto = 'Y'::bpchar AND l.isbilltodefault = 'N'::bpchar THEN 'Rechnung '::text
            WHEN l.isbillto = 'Y'::bpchar AND l.isbilltodefault = 'Y'::bpchar THEN 'Rechnung (S) '::text
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
        END AS anschrifttyp
   FROM c_bpartner bp
   LEFT JOIN c_bpartner_location l ON bp.c_bpartner_id = l.c_bpartner_id AND l.isactive = 'Y'::bpchar
   LEFT JOIN ad_user c ON bp.c_bpartner_id = c.c_bpartner_id AND (c.c_bpartner_location_id IS NULL OR c.c_bpartner_location_id = l.c_bpartner_location_id) AND c.isactive = 'Y'::bpchar
   LEFT JOIN c_location a ON l.c_location_id = a.c_location_id
   LEFT JOIN c_country cc ON a.c_country_id = cc.c_country_id
   LEFT JOIN c_bpartner_stats bps ON bp.c_bpartner_id = bps.c_bpartner_id;

ALTER TABLE adempiere.v_bpartnercockpit OWNER TO adempiere;

-- View: "adempiere.v_bpartner_stats"
CREATE OR REPLACE VIEW adempiere.v_bpartner_stats AS 
 SELECT bp.c_bpartner_id, bp.ad_client_id, bp.ad_org_id, COALESCE(( SELECT sum(currencybase(invoiceopen(i.c_invoice_id, i.c_invoicepayschedule_id), i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND i.ispaid = 'N'::bpchar AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS creditused, COALESCE(( SELECT sum(currencybase(invoiceopen(i.c_invoice_id, i.c_invoicepayschedule_id), i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id) * i.multiplierap) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.ispaid = 'N'::bpchar AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) - COALESCE(( SELECT sum(currencybase(paymentavailable(p.c_payment_id), p.c_currency_id, p.datetrx::timestamp with time zone, p.ad_client_id, p.ad_org_id)) AS sum
           FROM c_payment_v p
          WHERE p.c_bpartner_id = bp.c_bpartner_id AND p.isallocated = 'N'::bpchar AND p.c_charge_id IS NULL AND (p.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS openamt, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS lifetimevalue, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND i.dateinvoiced <= now()::date AND i.dateinvoiced >= (now()::date - '1 mon'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenued30, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date::timestamp with time zone) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem0, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '1 mon'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem1, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '2 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem2, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '3 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem3, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '4 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem4, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '5 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem5, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '6 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem6, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '7 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem7, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '8 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem8, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '9 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem9, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '10 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem10, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '11 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem11, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('month'::text, i.dateinvoiced) = date_trunc('month'::text, now()::date - '1 year'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuem12, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('quarter'::text, i.dateinvoiced) = date_trunc('quarter'::text, now()::date::timestamp with time zone) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenueq0, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('quarter'::text, i.dateinvoiced) = date_trunc('quarter'::text, now()::date - '3 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenueq1, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('quarter'::text, i.dateinvoiced) = date_trunc('quarter'::text, now()::date - '6 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenueq2, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('quarter'::text, i.dateinvoiced) = date_trunc('quarter'::text, now()::date - '9 mons'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenueq3, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('quarter'::text, i.dateinvoiced) = date_trunc('quarter'::text, now()::date - '1 year'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenueq4, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('year'::text, i.dateinvoiced) = date_trunc('year'::text, now()::date::timestamp with time zone) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuey0, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('year'::text, i.dateinvoiced) = date_trunc('year'::text, now()::date - '1 year'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuey1, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('year'::text, i.dateinvoiced) = date_trunc('year'::text, now()::date - '2 years'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuey2, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('year'::text, i.dateinvoiced) = date_trunc('year'::text, now()::date - '3 years'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuey3, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('year'::text, i.dateinvoiced) = date_trunc('year'::text, now()::date - '4 years'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuey4, COALESCE(( SELECT sum(currencybase(i.grandtotal, i.c_currency_id, i.dateinvoiced::timestamp with time zone, i.ad_client_id, i.ad_org_id)) AS sum
           FROM c_invoice_v i
          WHERE i.c_bpartner_id = bp.c_bpartner_id AND i.issotrx = 'Y'::bpchar AND date_trunc('year'::text, i.dateinvoiced) = date_trunc('year'::text, now()::date - '5 years'::interval) AND (i.docstatus = ANY (ARRAY['CO'::bpchar, 'CL'::bpchar]))), 0::numeric) AS revenuey5, ( SELECT min(c_invoice.dateinvoiced)::date AS min
           FROM c_invoice
          WHERE c_invoice.c_bpartner_id = bp.c_bpartner_id
          GROUP BY c_invoice.c_bpartner_id) AS firstsale, ( SELECT count(1) AS count
           FROM c_subscriptioncontrol sc
          WHERE sc.c_bpartner_id = bp.c_bpartner_id AND sc.subscriptionstatus = 'Ru'::bpchar) AS anzahlabos
   FROM c_bpartner bp;

ALTER TABLE adempiere.v_bpartner_stats OWNER TO adempiere;


insert into c_bpartner_stats 
( ad_client_id, ad_org_id, c_bpartner_id, c_bpartnerstats_id, created, createdby, isactive, updated, updatedby, 
  creditused, lifetimevalue, openamt, revenued30, revenuem0, revenuem1, 
  revenuem12, revenuem2, revenuem3, revenuem4, revenuem5, revenuem6, revenuem7, revenuem8, revenuem9, revenuem10, revenuem11, 
  revenueq0, revenueq1, revenueq2, revenueq3, revenueq4, revenuey0, revenuey1, revenuey2, revenuey3, revenuey4, revenuey5, firstsale  
)
(select bps.ad_client_id, bps.ad_org_id, bps.c_bpartner_id, nextval('c_bpartner_stats_id'::regclass), now(), 100, 'Y', now(), 100, 
  bps.creditused, bps.lifetimevalue, bps.openamt, bps.revenued30, bps.revenuem0, bps.revenuem1, 
  bps.revenuem12, bps.revenuem2, bps.revenuem3, bps.revenuem4, bps.revenuem5, bps.revenuem6, bps.revenuem7, bps.revenuem8, bps.revenuem9, bps.revenuem10, bps.revenuem11, 
  bps.revenueq0, bps.revenueq1, bps.revenueq2, bps.revenueq3, bps.revenueq4, bps.revenuey0, bps.revenuey1, bps.revenuey2, bps.revenuey3, bps.revenuey4, bps.revenuey5, bps.firstsale
from v_bpartner_stats bps
where bps.c_bpartner_id in (select bp.c_bpartner_id from c_bpartner bp left join c_bpartner_stats bps on bp.c_bpartner_id = bps.c_bpartner_id where bps.c_bpartner_id is null)
);
END
;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION insert_bpstats()
  OWNER TO metasfresh;

  
  
  */

DROP FUNCTION insert_bpstats();
