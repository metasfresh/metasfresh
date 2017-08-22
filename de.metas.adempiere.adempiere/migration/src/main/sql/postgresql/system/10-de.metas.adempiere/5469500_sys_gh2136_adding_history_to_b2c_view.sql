-- View: public.x_bpartner_history

DROP VIEW public.x_bpartner_history;

CREATE OR REPLACE VIEW public.x_bpartner_history AS 
 SELECT r.c_bpartner_id,
    r.documentno,
    r.summary AS description,
    rt.name AS typ,
    r.ad_client_id,
    r.ad_org_id,
    r.created,
    r.createdby,
    r.updated,
    r.updatedby,
    '417'::text AS ad_table_id,
    r.r_request_id AS record_id,
    417 * 1000000 + r.r_request_id AS x_bpartner_history_id
   FROM r_request r
     LEFT JOIN r_requesttype rt ON rt.r_requesttype_id = r.r_requesttype_id
UNION
 SELECT o.c_bpartner_id,
    o.documentno,
    o.description,
    dt.name AS typ,
    o.ad_client_id,
    o.ad_org_id,
    o.created,
    o.createdby,
    o.updated,
    o.updatedby,
    '259'::text AS ad_table_id,
    o.c_order_id AS record_id,
    259 * 1000000 +  o.c_order_id AS x_bpartner_history_id
   FROM c_order o
     LEFT JOIN c_doctype dt ON dt.c_doctype_id = o.c_doctype_id
UNION
 SELECT i.c_bpartner_id,
    i.documentno,
    i.description,
    dt.name AS typ,
    i.ad_client_id,
    i.ad_org_id,
    i.created,
    i.createdby,
    i.updated,
    i.updatedby,
    '318'::text AS ad_table_id,
    i.c_invoice_id AS record_id,
    318 * 1000000 +  i.c_invoice_id AS x_bpartner_history_id
   FROM c_invoice i
     LEFT JOIN c_doctype dt ON dt.c_doctype_id = i.c_doctype_id
UNION
 SELECT m.c_bpartner_id,
    m.documentno,
    m.description,
    dt.name AS typ,
    m.ad_client_id,
    m.ad_org_id,
    m.created,
    m.createdby,
    m.updated,
    m.updatedby,
    '319'::text AS ad_table_id,
    m.m_inout_id AS record_id,
    319 * 1000000  + m.m_inout_id AS x_bpartner_history_id
   FROM m_inout m
     LEFT JOIN c_doctype dt ON dt.c_doctype_id = m.c_doctype_id;

ALTER TABLE public.x_bpartner_history
  OWNER TO metasfresh;