DROP VIEW IF EXISTS report.fresh_M_Package_SSCC_Label_Report
;

CREATE VIEW report.fresh_M_Package_SSCC_Label_Report
            (org_address, sscc, p_customervalue, priceactual, p_name, cu_per_tu, tu_per_lu, net_weight, gross_weight,
             order_docno, p_value, lotcode, paletno, customer, ad_language, m_hu_id, M_Package_ID,
             ad_pinstance_id, lotnumberdate, C_OrderLine_ID)

AS
SELECT (SELECT (((COALESCE(org_bp.name, ''::character varying)::text || ', '::text) ||
                 COALESCE(org_l.postal, ''::character varying)::text) || ' '::text) ||
               COALESCE(org_l.city, ''::character varying)::text
        FROM ad_org org
                 JOIN ad_orginfo org_info ON org.ad_org_id = org_info.ad_org_id
                 JOIN c_bpartner_location org_bpl ON org_info.orgbp_location_id = org_bpl.c_bpartner_location_id
                 JOIN c_location org_l ON org_bpl.c_location_id = org_l.c_location_id
                 JOIN c_bpartner org_bp ON org_bpl.c_bpartner_id = org_bp.c_bpartner_id
        WHERE org.ad_org_id = o.ad_org_id)                                                                                                      AS org_address,


       package.ipa_sscc18                                                                                                                       AS sscc,
       bp_product.productno                                                                                                                     AS p_customervalue,
       NULL::numeric                                                                                                                            AS priceactual,
       COALESCE(pt.name, p.name)                                                                                                                AS p_name,

       piip.qty                                                                                                                                 AS cu_per_tu,
       shippingPackage.qtytu                                                                                                                    AS tu_per_lu,
       NULL::NUMERIC                                                                                                                            AS net_weight,
       NULL::NUMERIC                                                                                                                            AS gross_weight,
       o.documentno                                                                                                                             AS order_docno,
       p.value                                                                                                                                  AS p_value,
       NULL::CHARACTER VARYING                                                                                                                  AS lotcode,
       sscc18_extract_serialnumber(package.ipa_sscc18::TEXT)                                                                                    AS paletno,
       ((COALESCE(bp.name, ''::CHARACTER VARYING)::TEXT || ' '::TEXT) || COALESCE(hol.name, bpl.name, ''::CHARACTER VARYING)::TEXT) || ''::TEXT AS customer,
       bp.ad_language,
       NULL::NUMERIC                                                                                                                            AS m_hu_id,
       package.m_package_id,
       t_sel.ad_pinstance_id,
       ''::TEXT                                                                                                                                 AS lotnumberdate,
       ol.c_orderline_id

FROM C_Order o
         JOIN t_selection t_sel ON t_sel.t_selection_id = o.c_order_id AND t_sel.ad_pinstance_id = (SELECT ad_pinstance_id
                                                                                                    FROM t_selection
                                                                                                    WHERE t_selection_id = o.c_order_id
                                                                                                    ORDER BY ad_pinstance_id DESC
                                                                                                    LIMIT 1)
         JOIN M_ShippingPackage shippingPackage ON o.c_order_id = shippingPackage.c_order_id
         JOIN M_Package package ON shippingPackage.m_package_id = package.m_package_id
         JOIN C_OrderLine ol ON shippingPackage.c_orderline_id = ol.c_orderline_id
         JOIN M_HU_PI_Item_Product piip ON ol.m_product_id = piip.m_product_id AND ol.m_hu_pi_item_product_id = piip.m_hu_pi_item_product_id
         JOIN m_product p ON p.m_product_id = ol.m_product_id
         JOIN c_bpartner bp ON bp.c_bpartner_id = o.c_bpartner_id
         LEFT JOIN c_bpartner_location hol ON hol.c_bpartner_location_id = o.handover_location_id
         LEFT JOIN c_bpartner_location bpl ON bpl.c_bpartner_location_id = o.c_bpartner_location_id
         LEFT JOIN C_BPartner_Product bp_product ON bp_product.c_bpartner_id = bp.c_bpartner_id AND bp_product.m_product_id = p.m_product_id
         LEFT JOIN m_product_trl pt ON p.m_product_id = pt.m_product_id AND bp.ad_language::TEXT = pt.ad_language::TEXT
;


alter table report.fresh_M_Package_SSCC_Label_Report
    owner to postgres;
