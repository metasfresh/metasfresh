drop view if exists report.fresh_edi_desadvline_sscc_label_report;
create view report.fresh_edi_desadvline_sscc_label_report
            (org_address, sscc, p_customervalue, priceactual, p_name, cu_per_tu, tu_per_lu, net_weight, gross_weight,
             order_docno, p_value, lotcode, paletno, customer, ad_language, m_hu_id, edi_desadvline_sscc_id,
             ad_pinstance_id, lotnumberdate)
as
SELECT (SELECT (((COALESCE(org_bp.name, ''::character varying)::text || ', '::text) ||
                 COALESCE(org_l.postal, ''::character varying)::text) || ' '::text) ||
               COALESCE(org_l.city, ''::character varying)::text
        FROM ad_org org
                 JOIN ad_orginfo org_info ON org.ad_org_id = org_info.ad_org_id
                 JOIN c_bpartner_location org_bpl ON org_info.orgbp_location_id = org_bpl.c_bpartner_location_id
                 JOIN c_location org_l ON org_bpl.c_location_id = org_l.c_location_id
                 JOIN c_bpartner org_bp ON org_bpl.c_bpartner_id = org_bp.c_bpartner_id
        WHERE org.ad_org_id = o.ad_org_id)                                    AS org_address,
       agg_pack.ipa_sscc18                                                    AS sscc,
       agg_pack.productno                                                     AS p_customervalue,
       NULL::numeric                                                          AS priceactual,
       COALESCE(pt.name, p.name)                                              AS p_name,
       agg_pack.qtycu                                                         AS cu_per_tu,
       agg_pack.qtytu                                                         AS tu_per_lu,
       NULL::numeric                                                          AS net_weight,
       NULL::numeric                                                          AS gross_weight,
       o.documentno                                                           AS order_docno,
       p.value                                                                AS p_value,
       NULL::character varying                                                AS lotcode,
       sscc18_extract_serialnumber(agg_pack.ipa_sscc18::text)                 AS paletno,
       ((COALESCE(bp.name, ''::character varying)::text || ' '::text) ||
        COALESCE(hol.name, bpl.name, ''::character varying)::text) || '
'::text AS customer,
       bp.ad_language,
       NULL::numeric                                                          AS m_hu_id,
       agg_pack.pack_id                                                       AS edi_desadv_pack_id,
       agg_pack.ad_pinstance_id,
       ''::text                                                               AS lotnumberdate
FROM (select dl_pack.edi_desadv_pack_id as pack_id,
             dl_pack.ipa_sscc18,
             dl_pack.edi_desadv_id,
             dl.m_product_id            as m_product_id,
             dl.productno,
             dl.edi_desadvline_id,
             max(t_sel.ad_pinstance_id) AS ad_pinstance_id,
             sum(dl_pack_item.qtycu)    AS qtycu,
             sum(dl_pack_item.qtytu)    AS qtytu
      FROM t_selection t_sel
               JOIN edi_desadv_pack dl_pack ON t_sel.t_selection_id = dl_pack.edi_desadv_pack_id
               JOIN edi_desadv_pack_item dl_pack_item ON dl_pack.edi_desadv_pack_id = dl_pack_item.edi_desadv_pack_id
               JOIN edi_desadvline dl ON dl_pack_item.edi_desadvline_id = dl.edi_desadvline_id

      group by dl_pack.edi_desadv_pack_id, dl.edi_desadvline_id, dl.productno, dl.m_product_id, t_sel.ad_pinstance_id) agg_pack
         JOIN m_product p ON p.m_product_id = agg_pack.m_product_id
         LEFT JOIN c_orderline ol ON ol.edi_desadvline_id = agg_pack.edi_desadvline_id
         LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
         JOIN edi_desadv dh ON dh.edi_desadv_id = agg_pack.edi_desadv_id
         JOIN c_bpartner bp ON bp.c_bpartner_id = dh.c_bpartner_id
         LEFT JOIN c_bpartner_location hol ON hol.c_bpartner_location_id = dh.handover_location_id
         LEFT JOIN c_bpartner_location bpl ON bpl.c_bpartner_location_id = dh.c_bpartner_location_id
         LEFT JOIN m_product_trl pt ON p.m_product_id = pt.m_product_id AND bp.ad_language::text = pt.ad_language::text;

alter table report.fresh_edi_desadvline_sscc_label_report
    owner to postgres;
