DROP VIEW IF EXISTS report.fresh_HU_SSCC_Label_Report;

CREATE VIEW report.fresh_HU_SSCC_Label_Report(org_address, sscc, p_customervalue, priceactual, p_name, cu_per_tu, tu_per_lu, net_weight, gross_weight, order_docno, p_value, lotcode, paletno, customer, ad_language, m_hu_id, lotnumberdate) AS
SELECT (SELECT (((COALESCE(org_bp.name, ''::character varying)::text || ', '::text) || COALESCE(org_l.postal, ''::character varying)::text) || ' '::text) || COALESCE(org_l.city, ''::character varying)::text
        FROM ad_org org
                 JOIN ad_orginfo org_info ON org.ad_org_id = org_info.ad_org_id AND org_info.isactive = 'Y'::bpchar
                 JOIN c_bpartner_location org_bpl ON org_info.orgbp_location_id = org_bpl.c_bpartner_location_id AND org_bpl.isactive = 'Y'::bpchar
                 JOIN c_location org_l ON org_bpl.c_location_id = org_l.c_location_id AND org_l.isactive = 'Y'::bpchar
                 JOIN c_bpartner org_bp ON org_bpl.c_bpartner_id = org_bp.c_bpartner_id AND org_bp.isactive = 'Y'::bpchar
                 LEFT JOIN m_hu_assignment huas ON lu.m_hu_id = huas.m_hu_id AND (huas.ad_table_id = ANY (ARRAY [get_table_id('M_ReceiptSchedule'::character varying), get_table_id('PP_Order'::character varying), get_table_id('PP_Order_BOMLine'::character varying), get_table_id('M_InOutLine'::character varying)])) AND huas.isactive = 'Y'::bpchar
                 LEFT JOIN m_receiptschedule rs ON huas.ad_table_id = get_table_id('M_ReceiptSchedule'::character varying) AND rs.m_receiptschedule_id = huas.record_id AND rs.ad_org_id = org.ad_org_id AND rs.isactive = 'Y'::bpchar
                 LEFT JOIN pp_order ppo ON huas.ad_table_id = get_table_id('PP_Order'::character varying) AND ppo.pp_order_id = huas.record_id AND ppo.ad_org_id = org.ad_org_id AND ppo.isactive = 'Y'::bpchar
                 LEFT JOIN pp_order_bomline ppl ON huas.ad_table_id = get_table_id('PP_Order_BOMLine'::character varying) AND ppl.pp_order_bomline_id = huas.record_id AND ppl.ad_org_id = org.ad_org_id AND ppl.isactive = 'Y'::bpchar
                 LEFT JOIN m_inoutline iol ON huas.ad_table_id = get_table_id('M_InOutLine'::character varying) AND iol.m_inoutline_id = huas.record_id AND iol.ad_org_id = org.ad_org_id AND iol.isactive = 'Y'::bpchar
        WHERE CASE
                  WHEN huas.m_hu_id IS NOT NULL THEN huas.m_hu_id = lu.m_hu_id AND (iol.ad_org_id = org.ad_org_id OR ppl.ad_org_id = org.ad_org_id OR ppo.ad_org_id = org.ad_org_id OR rs.ad_org_id = org.ad_org_id)
                                                ELSE lu.ad_org_id = org.ad_org_id
              END
          AND org.isactive = 'Y'::bpchar
        LIMIT 1)                                                                                                                     AS org_address,
       lua_sscc.value                                                                                                                AS sscc,
       bpp.productno                                                                                                                 AS p_customervalue,
       (SELECT AVG(ol.priceactual) AS avg
        FROM m_hu_trx_line hutl
                 JOIN m_receiptschedule rs ON hutl.record_id = rs.m_receiptschedule_id AND rs.isactive = 'Y'::bpchar
                 JOIN c_orderline ol ON rs.record_id = ol.c_orderline_id AND ol.isactive = 'Y'::bpchar
        WHERE hutl.m_hu_id = lu.m_hu_id
          AND hutl.isactive = 'Y'::bpchar
          AND hutl.ad_table_id = ((SELECT ad_table.ad_table_id
                                   FROM ad_table
                                   WHERE ad_table.tablename::text = 'M_ReceiptSchedule'::text
                                     AND ad_table.isactive = 'Y'::bpchar))
          AND rs.ad_table_id = ((SELECT ad_table.ad_table_id
                                 FROM ad_table
                                 WHERE ad_table.tablename::text = 'C_OrderLine'::text
                                   AND ad_table.isactive = 'Y'::bpchar)))                                                            AS priceactual,
       COALESCE(pt.name, p.name)                                                                                                     AS p_name,
       qty.cu_per_tu,
       qty.tu_per_lu,
       lua_netw.valuenumber                                                                                                          AS net_weight,
       lua_grow.valuenumber                                                                                                          AS gross_weight,
       (SELECT MAX(o.documentno::text) AS max
        FROM m_hu_trx_line hutl
                 JOIN m_receiptschedule rs ON hutl.record_id = rs.m_receiptschedule_id AND rs.isactive = 'Y'::bpchar
                 JOIN c_orderline ol ON rs.record_id = ol.c_orderline_id AND ol.isactive = 'Y'::bpchar
                 JOIN c_order o ON ol.c_order_id = o.c_order_id AND o.isactive = 'Y'::bpchar
        WHERE hutl.m_hu_id = lu.m_hu_id
          AND hutl.isactive = 'Y'::bpchar
          AND hutl.ad_table_id = ((SELECT ad_table.ad_table_id
                                   FROM ad_table
                                   WHERE ad_table.tablename::text = 'M_ReceiptSchedule'::text
                                     AND ad_table.isactive = 'Y'::bpchar))
          AND rs.ad_table_id = ((SELECT ad_table.ad_table_id
                                 FROM ad_table
                                 WHERE ad_table.tablename::text = 'C_OrderLine'::text
                                   AND ad_table.isactive = 'Y'::bpchar)))                                                            AS order_docno,
       p.value                                                                                                                       AS p_value,
       lua_lot.value                                                                                                                 AS lotcode,
       lu.value                                                                                                                      AS paletno,
       ((COALESCE(bp.name, ''::character varying)::text || ' '::text) || COALESCE(bpl.name, ''::character varying)::text) || '
'::text AS customer,
       bp.ad_language,
       lu.m_hu_id,
       "de.metas.handlingunits".hu_lotnumberdate_tostring(lua_lotnumberdate.valuedate::date)                                         AS lotnumberdate
FROM m_hu lu
         JOIN m_hu_storage lus ON lu.m_hu_id = lus.m_hu_id AND lus.isactive = 'Y'::bpchar
         LEFT JOIN c_bpartner bp ON lu.c_bpartner_id = bp.c_bpartner_id AND bp.isactive = 'Y'::bpchar
         LEFT JOIN c_bpartner_location bpl ON lu.c_bpartner_location_id = bpl.c_bpartner_location_id AND bpl.isactive = 'Y'::bpchar
         JOIN m_product p ON lus.m_product_id = p.m_product_id AND p.isactive = 'Y'::bpchar
         LEFT JOIN m_product_trl pt ON p.m_product_id = pt.m_product_id AND bp.ad_language::text = pt.ad_language::text AND pt.isactive = 'Y'::bpchar
         LEFT JOIN c_bpartner_product bpp ON bp.c_bpartner_id = bpp.c_bpartner_id AND p.m_product_id = bpp.m_product_id AND bpp.isactive = 'Y'::bpchar
         JOIN (SELECT lui.m_hu_id,
                      COALESCE(
                              CASE
                                  WHEN val.qty IS NOT NULL THEN AVG(tus.qty) / val.qty
                                                           ELSE AVG(tus.qty)
                              END, 0::numeric)  AS cu_per_tu,
                      COUNT(tu.m_hu_id)::bigint AS tu_per_lu,
                      tus.m_product_id
               FROM m_hu_item lui
                        JOIN m_hu_pi_item lupii ON lui.m_hu_pi_item_id = lupii.m_hu_pi_item_id AND lupii.itemtype::text = 'HU'::text AND lupii.isactive = 'Y'::bpchar
                        JOIN m_hu tu ON lui.m_hu_item_id = tu.m_hu_item_parent_id
                        JOIN m_hu_storage tus ON tu.m_hu_id = tus.m_hu_id AND tus.isactive = 'Y'::bpchar
                        LEFT JOIN LATERAL "de.metas.handlingunits".get_tu_values_from_aggregation(tu.m_hu_id) val(huvalue, qty) ON TRUE
               WHERE lui.isactive = 'Y'::bpchar
               GROUP BY lui.m_hu_id, val.qty, tus.m_product_id) qty ON lu.m_hu_id = qty.m_hu_id AND qty.m_product_id = p.m_product_id
         LEFT JOIN m_hu_attribute lua_sscc ON lu.m_hu_id = lua_sscc.m_hu_id AND lua_sscc.isactive = 'Y'::bpchar AND lua_sscc.m_attribute_id = ((SELECT m_attribute.m_attribute_id
                                                                                                                                                FROM m_attribute
                                                                                                                                                WHERE m_attribute.name::text = 'SSCC18'::text
                                                                                                                                                  AND m_attribute.isactive = 'Y'::bpchar))
         LEFT JOIN m_hu_attribute lua_netw ON lu.m_hu_id = lua_netw.m_hu_id AND lua_netw.isactive = 'Y'::bpchar AND lua_netw.m_attribute_id = ((SELECT m_attribute.m_attribute_id
                                                                                                                                                FROM m_attribute
                                                                                                                                                WHERE m_attribute.name::text = 'Gewicht Netto'::text
                                                                                                                                                  AND m_attribute.isactive = 'Y'::bpchar))
         LEFT JOIN m_hu_attribute lua_grow ON lu.m_hu_id = lua_grow.m_hu_id AND lua_grow.isactive = 'Y'::bpchar AND lua_grow.m_attribute_id = ((SELECT m_attribute.m_attribute_id
                                                                                                                                                FROM m_attribute
                                                                                                                                                WHERE m_attribute.name::text = 'Gewicht Brutto kg'::text
                                                                                                                                                  AND m_attribute.isactive = 'Y'::bpchar))
         LEFT JOIN m_hu_attribute lua_lot ON lu.m_hu_id = lua_lot.m_hu_id AND lua_lot.isactive = 'Y'::bpchar AND lua_lot.m_attribute_id = ((SELECT m_attribute.m_attribute_id
                                                                                                                                            FROM m_attribute
                                                                                                                                            WHERE m_attribute.name::text ~~* 'Lot-Nummer'::text
                                                                                                                                              AND m_attribute.isactive = 'Y'::bpchar))
         LEFT JOIN m_hu_attribute lua_lotnumberdate ON lu.m_hu_id = lua_lotnumberdate.m_hu_id AND lua_lotnumberdate.isactive = 'Y'::bpchar AND lua_lotnumberdate.m_attribute_id = ((SELECT m_attribute.m_attribute_id
                                                                                                                                                                                    FROM m_attribute
                                                                                                                                                                                    WHERE m_attribute.name::text = 'Tageslot Datum'::text
                                                                                                                                                                                      AND m_attribute.isactive = 'Y'::bpchar))
;

