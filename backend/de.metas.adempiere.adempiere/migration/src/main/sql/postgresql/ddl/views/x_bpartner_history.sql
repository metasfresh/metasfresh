
DROP VIEW public.x_bpartner_history;

CREATE OR REPLACE VIEW public.x_bpartner_history AS
 SELECT r.c_bpartner_id,
    r.datetrx,
    r.documentno,
    r.summary AS description,
    rt.name AS typ,
    r.ad_client_id,
    r.ad_org_id,
    r.created,
    r.createdby,
    r.updated,
    r.updatedby,
    null::numeric As netamount,
    null::numeric As grossamount,
    '417'::text AS ad_table_id,
    r.r_request_id AS record_id,
    (417 * 1000000)::numeric + r.r_request_id AS x_bpartner_history_id
   FROM r_request r
     LEFT JOIN r_requesttype rt ON rt.r_requesttype_id = r.r_requesttype_id
UNION
 SELECT o.c_bpartner_id,
    o.dateordered AS datetrx,
    o.documentno,
    o.description,
    dt.name AS typ,
    o.ad_client_id,
    o.ad_org_id,
    o.created,
    o.createdby,
    o.updated,
    o.updatedby,
    (SELECT coalesce(sum(ot.taxbaseamt),0) from C_OrderTax ot where ot.c_order_id = o.c_order_id) As netamount,
    (SELECT coalesce(sum(ot.taxbaseamt + ot.taxamt),0) from C_OrderTax ot where ot.c_order_id = o.c_order_id) As grossamount,
    '259'::text AS ad_table_id,
    o.c_order_id AS record_id,
    (259 * 1000000)::numeric + o.c_order_id AS x_bpartner_history_id
   FROM c_order o
     LEFT JOIN c_doctype dt ON dt.c_doctype_id = o.c_doctype_id
UNION
 SELECT i.c_bpartner_id,
    i.dateinvoiced AS datetrx,
    i.documentno,
    i.description,
    dt.name AS typ,
    i.ad_client_id,
    i.ad_org_id,
    i.created,
    i.createdby,
    i.updated,
    i.updatedby,
    null::numeric As netamount,
    null::numeric As grossamount,
    '318'::text AS ad_table_id,
    i.c_invoice_id AS record_id,
    (318 * 1000000)::numeric + i.c_invoice_id AS x_bpartner_history_id
   FROM c_invoice i
     LEFT JOIN c_doctype dt ON dt.c_doctype_id = i.c_doctype_id
UNION
 SELECT m.c_bpartner_id,
    m.movementdate AS datetrx,
    m.documentno,
    m.description,
    dt.name AS typ,
    m.ad_client_id,
    m.ad_org_id,
    m.created,
    m.createdby,
    m.updated,
    m.updatedby,
    null::numeric As netamount,
    null::numeric As grossamount,
    '319'::text AS ad_table_id,
    m.m_inout_id AS record_id,
    (319 * 1000000)::numeric + m.m_inout_id AS x_bpartner_history_id
   FROM m_inout m
     LEFT JOIN c_doctype dt ON dt.c_doctype_id = m.c_doctype_id
UNION
 SELECT a.record_id AS c_bpartner_id,
    a.created AS datetrx,
    a.ad_attachmententry_id::text AS documentno,
    a.filename AS description,
    'File'::character varying AS typ,
    a.ad_client_id,
    a.ad_org_id,
    a.created,
    a.createdby,
    a.updated,
    a.updatedby,
    null::numeric As netamount,
    null::numeric As grossamount,
    a.ad_table_id::text AS ad_table_id,
    a.ad_attachmententry_id AS record_id,
    (291 * 1000000)::numeric + a.AD_AttachmentEntry_ReferencedRecord_v_id AS x_bpartner_history_id
   FROM AD_AttachmentEntry_ReferencedRecord_v a
  WHERE a.ad_table_id = 291::numeric;
