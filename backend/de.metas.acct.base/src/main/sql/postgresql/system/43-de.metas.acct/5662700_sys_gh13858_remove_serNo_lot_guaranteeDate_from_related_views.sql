--C_Invoice_LineTax_v
SELECT public.db_alter_view('C_Invoice_LineTax_v','SELECT il.ad_client_id,
       il.ad_org_id,
       il.isactive,
       il.created,
       il.createdby,
       il.updated,
       il.updatedby,
       ''en_US''::text                                                                                                                                                AS ad_language,
       il.c_invoice_id,
       il.c_invoiceline_id,
       il.c_tax_id,
       il.taxamt,
       il.linetotalamt,
       t.taxindicator,
       il.line,
       p.m_product_id,
       CASE
           WHEN il.qtyinvoiced <> 0::numeric OR il.m_product_id IS NOT NULL THEN il.qtyinvoiced
                                                                            ELSE NULL::numeric
       END                                                                                                                                                          AS qtyinvoiced,
       CASE
           WHEN il.qtyentered <> 0::numeric OR il.m_product_id IS NOT NULL THEN il.qtyentered
                                                                           ELSE NULL::numeric
       END                                                                                                                                                          AS qtyentered,
       CASE
           WHEN il.qtyentered <> 0::numeric OR il.m_product_id IS NOT NULL THEN uom.uomsymbol
                                                                           ELSE NULL::character varying
       END                                                                                                                                                          AS uomsymbol,
       COALESCE(c.name, (p.name::text || COALESCE(productattribute(il.m_attributesetinstance_id), ''''::character varying)::text)::character varying, il.description) AS name,
       CASE
           WHEN COALESCE(c.name, p.name) IS NOT NULL THEN il.description
                                                     ELSE NULL::character varying
       END                                                                                                                                                          AS description,
       p.documentnote,
       p.upc,
       p.sku,
       COALESCE(pp.vendorproductno, p.value)                                                                                                                        AS productvalue,
       ra.description                                                                                                                                               AS resourcedescription,
       CASE
           WHEN i.isdiscountprinted = ''Y''::bpchar AND il.pricelist <> 0::numeric THEN il.pricelist
                                                                                 ELSE NULL::numeric
       END                                                                                                                                                          AS pricelist,
       CASE
           WHEN i.isdiscountprinted = ''Y''::bpchar AND il.pricelist <> 0::numeric AND il.qtyentered <> 0::numeric THEN il.pricelist * il.qtyinvoiced / il.qtyentered
                                                                                                                 ELSE NULL::numeric
       END                                                                                                                                                          AS priceenteredlist,
       CASE
           WHEN i.isdiscountprinted = ''Y''::bpchar AND il.pricelist > il.priceactual AND il.pricelist <> 0::numeric THEN (il.pricelist - il.priceactual) / il.pricelist * 100::numeric
                                                                                                                   ELSE NULL::numeric
       END                                                                                                                                                          AS discount,
       CASE
           WHEN il.priceactual <> 0::numeric OR il.m_product_id IS NOT NULL THEN il.priceactual
                                                                            ELSE NULL::numeric
       END                                                                                                                                                          AS priceactual,
       CASE
           WHEN il.priceentered <> 0::numeric OR il.m_product_id IS NOT NULL THEN il.priceentered
                                                                             ELSE NULL::numeric
       END                                                                                                                                                          AS priceentered,
       CASE
           WHEN il.linenetamt <> 0::numeric OR il.m_product_id IS NOT NULL THEN il.linenetamt
                                                                           ELSE NULL::numeric
       END                                                                                                                                                          AS linenetamt,
       il.m_attributesetinstance_id,
       asi.m_attributeset_id,
       p.description                                                                                                                                                AS productdescription,
       p.imageurl,
       il.c_campaign_id,
       il.c_project_id,
       il.c_activity_id,
       il.c_projectphase_id,
       il.c_projecttask_id
FROM c_invoiceline il
         JOIN c_uom uom ON il.c_uom_id = uom.c_uom_id
         JOIN c_invoice i ON il.c_invoice_id = i.c_invoice_id
         LEFT JOIN c_tax t ON il.c_tax_id = t.c_tax_id
         LEFT JOIN m_product p ON il.m_product_id = p.m_product_id
         LEFT JOIN c_charge c ON il.c_charge_id = c.c_charge_id
         LEFT JOIN c_bpartner_product pp ON il.m_product_id = pp.m_product_id AND i.c_bpartner_id = pp.c_bpartner_id
         LEFT JOIN s_resourceassignment ra ON il.s_resourceassignment_id = ra.s_resourceassignment_id
         LEFT JOIN m_attributesetinstance asi ON il.m_attributesetinstance_id = asi.m_attributesetinstance_id
UNION
SELECT il.ad_client_id,
       il.ad_org_id,
       il.isactive,
       il.created,
       il.createdby,
       il.updated,
       il.updatedby,
       ''en_US''::text                    AS ad_language,
       il.c_invoice_id,
       il.c_invoiceline_id,
       il.c_tax_id,
       il.taxamt,
       il.linetotalamt,
       t.taxindicator,
       il.line + bl.line / 100::numeric AS line,
       p.m_product_id,
       CASE
           WHEN bl.isqtypercentage = ''N''::bpchar THEN il.qtyinvoiced * bl.qtybom
                                                 ELSE il.qtyinvoiced * (bl.qtybatch / 100::numeric)
       END                              AS qtyinvoiced,
       CASE
           WHEN bl.isqtypercentage = ''N''::bpchar THEN il.qtyentered * bl.qtybom
                                                 ELSE il.qtyentered * (bl.qtybatch / 100::numeric)
       END                              AS qtyentered,
       uom.uomsymbol,
       p.name,
       b.description,
       p.documentnote,
       p.upc,
       p.sku,
       p.value                          AS productvalue,
       NULL::character varying          AS resourcedescription,
       NULL::numeric                    AS pricelist,
       NULL::numeric                    AS priceenteredlist,
       NULL::numeric                    AS discount,
       NULL::numeric                    AS priceactual,
       NULL::numeric                    AS priceentered,
       NULL::numeric                    AS linenetamt,
       il.m_attributesetinstance_id,
       asi.m_attributeset_id,
       p.description                    AS productdescription,
       p.imageurl,
       il.c_campaign_id,
       il.c_project_id,
       il.c_activity_id,
       il.c_projectphase_id,
       il.c_projecttask_id
FROM pp_product_bom b
         JOIN c_invoiceline il ON b.m_product_id = il.m_product_id
         JOIN m_product bp ON bp.m_product_id = il.m_product_id AND bp.isbom = ''Y''::bpchar AND bp.isverified = ''Y''::bpchar AND bp.isinvoiceprintdetails = ''Y''::bpchar
         JOIN pp_product_bomline bl ON bl.pp_product_bom_id = b.pp_product_bom_id
         JOIN m_product p ON bl.m_product_id = p.m_product_id
         JOIN c_uom uom ON p.c_uom_id = uom.c_uom_id
         LEFT JOIN c_tax t ON il.c_tax_id = t.c_tax_id
         LEFT JOIN m_attributesetinstance asi ON il.m_attributesetinstance_id = asi.m_attributesetinstance_id
UNION
SELECT il.ad_client_id,
       il.ad_org_id,
       il.isactive,
       il.created,
       il.createdby,
       il.updated,
       il.updatedby,
       ''en_US''::text                     AS ad_language,
       il.c_invoice_id,
       il.c_invoiceline_id,
       NULL::numeric                     AS c_tax_id,
       NULL::numeric                     AS taxamt,
       NULL::numeric                     AS linetotalamt,
       NULL::character varying           AS taxindicator,
       il.line,
       NULL::numeric                     AS m_product_id,
       NULL::numeric                     AS qtyinvoiced,
       NULL::numeric                     AS qtyentered,
       NULL::character varying           AS uomsymbol,
       il.description                    AS name,
       NULL::character varying           AS description,
       NULL::character varying           AS documentnote,
       NULL::character varying           AS upc,
       NULL::character varying           AS sku,
       NULL::character varying           AS productvalue,
       NULL::character varying           AS resourcedescription,
       NULL::numeric                     AS pricelist,
       NULL::numeric                     AS priceenteredlist,
       NULL::numeric                     AS discount,
       NULL::numeric                     AS priceactual,
       NULL::numeric                     AS priceentered,
       NULL::numeric                     AS linenetamt,
       NULL::numeric                     AS m_attributesetinstance_id,
       NULL::numeric                     AS m_attributeset_id,
       NULL::character varying           AS productdescription,
       NULL::character varying           AS imageurl,
       NULL::numeric                     AS c_campaign_id,
       NULL::numeric                     AS c_project_id,
       NULL::numeric                     AS c_activity_id,
       NULL::numeric                     AS c_projectphase_id,
       NULL::numeric                     AS c_projecttask_id
FROM c_invoiceline il
WHERE il.c_uom_id IS NULL
UNION
SELECT c_invoice.ad_client_id,
       c_invoice.ad_org_id,
       c_invoice.isactive,
       c_invoice.created,
       c_invoice.createdby,
       c_invoice.updated,
       c_invoice.updatedby,
       ''en_US''::text                     AS ad_language,
       c_invoice.c_invoice_id,
       NULL::numeric                     AS c_invoiceline_id,
       NULL::numeric                     AS c_tax_id,
       NULL::numeric                     AS taxamt,
       NULL::numeric                     AS linetotalamt,
       NULL::character varying           AS taxindicator,
       9998                              AS line,
       NULL::numeric                     AS m_product_id,
       NULL::numeric                     AS qtyinvoiced,
       NULL::numeric                     AS qtyentered,
       NULL::character varying           AS uomsymbol,
       NULL::character varying           AS name,
       NULL::character varying           AS description,
       NULL::character varying           AS documentnote,
       NULL::character varying           AS upc,
       NULL::character varying           AS sku,
       NULL::character varying           AS productvalue,
       NULL::character varying           AS resourcedescription,
       NULL::numeric                     AS pricelist,
       NULL::numeric                     AS priceenteredlist,
       NULL::numeric                     AS discount,
       NULL::numeric                     AS priceactual,
       NULL::numeric                     AS priceentered,
       NULL::numeric                     AS linenetamt,
       NULL::numeric                     AS m_attributesetinstance_id,
       NULL::numeric                     AS m_attributeset_id,
       NULL::character varying           AS productdescription,
       NULL::character varying           AS imageurl,
       NULL::numeric                     AS c_campaign_id,
       NULL::numeric                     AS c_project_id,
       NULL::numeric                     AS c_activity_id,
       NULL::numeric                     AS c_projectphase_id,
       NULL::numeric                     AS c_projecttask_id
FROM c_invoice
UNION
SELECT it.ad_client_id,
       it.ad_org_id,
       it.isactive,
       it.created,
       it.createdby,
       it.updated,
       it.updatedby,
       ''en_US''::text                     AS ad_language,
       it.c_invoice_id,
       NULL::numeric                     AS c_invoiceline_id,
       it.c_tax_id,
       NULL::numeric                     AS taxamt,
       NULL::numeric                     AS linetotalamt,
       t.taxindicator,
       9999                              AS line,
       NULL::numeric                     AS m_product_id,
       NULL::numeric                     AS qtyinvoiced,
       NULL::numeric                     AS qtyentered,
       NULL::character varying           AS uomsymbol,
       t.name,
       NULL::character varying           AS description,
       NULL::character varying           AS documentnote,
       NULL::character varying           AS upc,
       NULL::character varying           AS sku,
       NULL::character varying           AS productvalue,
       NULL::character varying           AS resourcedescription,
       NULL::numeric                     AS pricelist,
       NULL::numeric                     AS priceenteredlist,
       NULL::numeric                     AS discount,
       CASE
           WHEN it.istaxincluded = ''Y''::bpchar THEN it.taxamt
                                               ELSE it.taxbaseamt
       END                               AS priceactual,
       CASE
           WHEN it.istaxincluded = ''Y''::bpchar THEN it.taxamt
                                               ELSE it.taxbaseamt
       END                               AS priceentered,
       CASE
           WHEN it.istaxincluded = ''Y''::bpchar THEN NULL::numeric
                                               ELSE it.taxamt
       END                               AS linenetamt,
       NULL::numeric                     AS m_attributesetinstance_id,
       NULL::numeric                     AS m_attributeset_id,
       NULL::character varying           AS productdescription,
       NULL::character varying           AS imageurl,
       NULL::numeric                     AS c_campaign_id,
       NULL::numeric                     AS c_project_id,
       NULL::numeric                     AS c_activity_id,
       NULL::numeric                     AS c_projectphase_id,
       NULL::numeric                     AS c_projecttask_id
FROM c_invoicetax it
         JOIN c_tax t ON it.c_tax_id = t.c_tax_id;
');

-- rv_orderdetail
SELECT public.db_alter_view('rv_orderdetail','SELECT l.ad_client_id,
       l.ad_org_id,
       l.isactive,
       l.created,
       l.createdby,
       l.updated,
       l.updatedby,
       o.c_order_id,
       o.docstatus,
       o.docaction,
       o.c_doctype_id,
       o.isapproved,
       o.iscreditapproved,
       o.salesrep_id,
       o.bill_bpartner_id,
       o.bill_location_id,
       o.bill_user_id,
       o.isdropship,
       l.c_bpartner_id,
       l.c_bpartner_location_id,
       o.ad_user_id,
       o.poreference,
       o.c_currency_id,
       o.issotrx,
       l.c_campaign_id,
       l.c_project_id,
       l.c_activity_id,
       l.c_projectphase_id,
       l.c_projecttask_id,
       l.c_orderline_id,
       l.dateordered,
       l.datepromised,
       l.m_product_id,
       l.m_warehouse_id,
       l.m_attributesetinstance_id,
       productattribute(l.m_attributesetinstance_id)  AS productattribute,
       pasi.m_attributeset_id,
       l.c_uom_id,
       l.qtyentered,
       l.qtyordered,
       l.qtyreserved,
       l.qtydelivered,
       l.qtyinvoiced,
       l.priceactual,
       l.priceentered,
       l.qtyordered - l.qtydelivered                  AS qtytodeliver,
       l.qtyordered - l.qtyinvoiced                   AS qtytoinvoice,
       (l.qtyordered - l.qtyinvoiced) * l.priceactual AS netamttoinvoice,
       l.qtylostsales,
       l.qtylostsales * l.priceactual                 AS amtlostsales,
       CASE
           WHEN l.pricelist = 0::numeric THEN 0::numeric
                                         ELSE ROUND((l.pricelist - l.priceactual) / l.pricelist * 100::numeric, 2)
       END                                            AS discount,
       CASE
           WHEN l.pricelimit = 0::numeric THEN 0::numeric
                                          ELSE ROUND((l.priceactual - l.pricelimit) / l.pricelimit * 100::numeric, 2)
       END                                            AS margin,
       CASE
           WHEN l.pricelimit = 0::numeric THEN 0::numeric
                                          ELSE (l.priceactual - l.pricelimit) * l.qtydelivered
       END                                            AS marginamt
FROM c_order o
         JOIN c_orderline l ON o.c_order_id = l.c_order_id
         LEFT JOIN m_attributesetinstance pasi ON l.m_attributesetinstance_id = pasi.m_attributesetinstance_id;
');

--m_inout_line_v
SELECT public.db_alter_view('m_inout_line_v','SELECT iol.ad_client_id,
       iol.ad_org_id,
       iol.isactive,
       iol.created,
       iol.createdby,
       iol.updated,
       iol.updatedby,
       ''de_DE''::text                                                                                                        AS ad_language,
       iol.m_inout_id,
       iol.m_inoutline_id,
       iol.line,
       p.m_product_id,
       CASE
           WHEN iol.movementqty <> 0::numeric OR iol.m_product_id IS NOT NULL THEN iol.movementqty
                                                                              ELSE NULL::numeric
       END                                                                                                                  AS movementqty,
       CASE
           WHEN iol.qtyentered <> 0::numeric OR iol.m_product_id IS NOT NULL THEN iol.qtyentered
                                                                             ELSE NULL::numeric
       END                                                                                                                  AS qtyentered,
       CASE
           WHEN iol.movementqty <> 0::numeric OR iol.m_product_id IS NOT NULL THEN uom.uomsymbol
                                                                              ELSE NULL::character varying
       END                                                                                                                  AS uomsymbol,
       ol.qtyordered,
       ol.qtydelivered,
       CASE
           WHEN iol.movementqty <> 0::numeric OR iol.m_product_id IS NOT NULL THEN ol.qtyordered - ol.qtydelivered
                                                                              ELSE NULL::numeric
       END                                                                                                                  AS qtybackordered,
       COALESCE(p.name::text || productattribute(iol.m_attributesetinstance_id)::text, c.name::text, iol.description::text) AS name,
       CASE
           WHEN COALESCE(c.name, p.name) IS NOT NULL THEN iol.description
                                                     ELSE NULL::character varying
       END                                                                                                                  AS description,
       p.documentnote,
       p.upc,
       p.sku,
       p.value                                                                                                              AS productvalue,
       iol.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       iol.m_attributesetinstance_id,
       asi.m_attributeset_id,
       p.description                                                                                                        AS productdescription,
       p.imageurl,
       iol.c_campaign_id,
       iol.c_project_id,
       iol.c_activity_id,
       iol.c_projectphase_id,
       iol.c_projecttask_id
FROM m_inoutline iol
         JOIN c_uom uom ON iol.c_uom_id = uom.c_uom_id
         LEFT JOIN m_product p ON iol.m_product_id = p.m_product_id
         LEFT JOIN m_attributesetinstance asi ON iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
         LEFT JOIN m_locator l ON iol.m_locator_id = l.m_locator_id
         LEFT JOIN c_orderline ol ON iol.c_orderline_id = ol.c_orderline_id
         LEFT JOIN c_charge c ON iol.c_charge_id = c.c_charge_id
UNION
SELECT iol.ad_client_id,
       iol.ad_org_id,
       iol.isactive,
       iol.created,
       iol.createdby,
       iol.updated,
       iol.updatedby,
       ''de_DE''::text                     AS ad_language,
       iol.m_inout_id,
       iol.m_inoutline_id,
       iol.line + bl.line / 100::numeric AS line,
       p.m_product_id,
       CASE
           WHEN bl.isqtypercentage = ''N''::bpchar THEN iol.movementqty * bl.qtybom
                                                 ELSE iol.movementqty * (bl.qtybatch / 100::numeric)
       END                               AS movementqty,
       CASE
           WHEN bl.isqtypercentage = ''N''::bpchar THEN iol.qtyentered * bl.qtybom
                                                 ELSE iol.qtyentered * (bl.qtybatch / 100::numeric)
       END                               AS qtyentered,
       uom.uomsymbol,
       NULL::numeric                     AS qtyordered,
       NULL::numeric                     AS qtydelivered,
       NULL::numeric                     AS qtybackordered,
       p.name,
       b.description,
       p.documentnote,
       p.upc,
       p.sku,
       p.value                           AS productvalue,
       iol.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       iol.m_attributesetinstance_id,
       asi.m_attributeset_id,
       p.description                     AS productdescription,
       p.imageurl,
       iol.c_campaign_id,
       iol.c_project_id,
       iol.c_activity_id,
       iol.c_projectphase_id,
       iol.c_projecttask_id
FROM pp_product_bom b
         JOIN m_inoutline iol ON b.m_product_id = iol.m_product_id
         JOIN m_product bp ON bp.m_product_id = iol.m_product_id AND bp.isbom = ''Y''::bpchar AND bp.isverified = ''Y''::bpchar AND bp.ispicklistprintdetails = ''Y''::bpchar
         JOIN pp_product_bomline bl ON bl.pp_product_bom_id = b.pp_product_bom_id
         JOIN m_product p ON bl.m_product_id = p.m_product_id
         JOIN c_uom uom ON p.c_uom_id = uom.c_uom_id
         LEFT JOIN m_attributesetinstance asi ON iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
         LEFT JOIN m_locator l ON iol.m_locator_id = l.m_locator_id;
');

-- m_inout_lineconfirm_v

SELECT public.db_alter_view('m_inout_lineconfirm_v','SELECT iolc.ad_client_id,
       iolc.ad_org_id,
       iolc.isactive,
       iolc.created,
       iolc.createdby,
       iolc.updated,
       iolc.updatedby,
       ''de_DE''::character varying        AS ad_language,
       iolc.m_inoutlineconfirm_id,
       iolc.m_inoutconfirm_id,
       iolc.targetqty,
       iolc.confirmedqty,
       iolc.differenceqty,
       iolc.scrappedqty,
       iolc.description,
       iolc.processed,
       iol.m_inout_id,
       iol.m_inoutline_id,
       iol.line,
       p.m_product_id,
       iol.movementqty,
       uom.uomsymbol,
       ol.qtyordered - ol.qtydelivered   AS qtybackordered,
       COALESCE(p.name, iol.description) AS name,
       CASE
           WHEN p.name IS NOT NULL THEN iol.description
                                   ELSE NULL::character varying
       END                               AS shipdescription,
       p.documentnote,
       p.upc,
       p.sku,
       p.value                           AS productvalue,
       iol.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       iol.m_attributesetinstance_id,
       asi.m_attributeset_id
FROM m_inoutlineconfirm iolc
         JOIN m_inoutline iol ON iolc.m_inoutline_id = iol.m_inoutline_id
         JOIN c_uom uom ON iol.c_uom_id = uom.c_uom_id
         LEFT JOIN m_product p ON iol.m_product_id = p.m_product_id
         LEFT JOIN m_attributesetinstance asi ON iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
         LEFT JOIN m_locator l ON iol.m_locator_id = l.m_locator_id
         LEFT JOIN c_orderline ol ON iol.c_orderline_id = ol.c_orderline_id;');

-- rv_storage
SELECT public.db_alter_view('rv_storage','SELECT s.ad_client_id,
       s.ad_org_id,
       s.m_product_id,
       p.value,
       p.name,
       p.description,
       p.upc,
       p.sku,
       p.c_uom_id,
       p.m_product_category_id,
       p.classification,
       p.weight,
       p.volume,
       p.versionno,
       p.guaranteedays,
       p.guaranteedaysmin,
       s.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       s.qtyonhand,
       s.qtyreserved,
       s.qtyonhand - s.qtyreserved                                                                       AS qtyavailable,
       s.qtyordered,
       s.datelastinventory,
       s.m_attributesetinstance_id,
       asi.m_attributeset_id,
       NULL                              AS shelflifedays,
       NULL AS goodfordays,
           NULL::numeric
                                                                                                     AS shelfliferemainingpct
FROM m_storage s
         JOIN m_locator l ON s.m_locator_id = l.m_locator_id
         JOIN m_product p ON s.m_product_id = p.m_product_id
         LEFT JOIN m_attributesetinstance asi ON s.m_attributesetinstance_id = asi.m_attributesetinstance_id;
');

--c_invoice_linetax_vt
SELECT public.db_alter_view('c_invoice_linetax_vt','SELECT il.ad_client_id,
       il.ad_org_id,
       il.isactive,
       il.created,
       il.createdby,
       il.updated,
       il.updatedby,
       uom.ad_language,
       il.c_invoice_id,
       il.c_invoiceline_id,
       il.c_tax_id,
       il.taxamt,
       il.linetotalamt,
       t.taxindicator,
       il.line,
       p.m_product_id,
       CASE
           WHEN il.qtyinvoiced <> 0::numeric OR il.m_product_id IS NOT NULL THEN il.qtyinvoiced
                                                                            ELSE NULL::numeric
       END                                                                                                                                                                             AS qtyinvoiced,
       CASE
           WHEN il.qtyentered <> 0::numeric OR il.m_product_id IS NOT NULL THEN il.qtyentered
                                                                           ELSE NULL::numeric
       END                                                                                                                                                                             AS qtyentered,
       CASE
           WHEN il.qtyentered <> 0::numeric OR il.m_product_id IS NOT NULL THEN uom.uomsymbol
                                                                           ELSE NULL::character varying
       END                                                                                                                                                                             AS uomsymbol,
       COALESCE(c.name, (COALESCE(pt.name, p.name)::text || COALESCE(productattribute(il.m_attributesetinstance_id), ''''::character varying)::text)::character varying, il.description) AS name,
       CASE
           WHEN COALESCE(c.name, pt.name, p.name) IS NOT NULL THEN il.description
                                                              ELSE NULL::character varying
       END                                                                                                                                                                             AS description,
       COALESCE(pt.documentnote, p.documentnote)                                                                                                                                       AS documentnote,
       p.upc,
       p.sku,
       COALESCE(pp.vendorproductno, p.value)                                                                                                                                           AS productvalue,
       ra.description                                                                                                                                                                  AS resourcedescription,
       CASE
           WHEN i.isdiscountprinted = ''Y''::bpchar AND il.pricelist <> 0::numeric THEN il.pricelist
                                                                                 ELSE NULL::numeric
       END                                                                                                                                                                             AS pricelist,
       CASE
           WHEN i.isdiscountprinted = ''Y''::bpchar AND il.pricelist <> 0::numeric AND il.qtyentered <> 0::numeric THEN il.pricelist * il.qtyinvoiced / il.qtyentered
                                                                                                                 ELSE NULL::numeric
       END                                                                                                                                                                             AS priceenteredlist,
       CASE
           WHEN i.isdiscountprinted = ''Y''::bpchar AND il.pricelist > il.priceactual AND il.pricelist <> 0::numeric THEN (il.pricelist - il.priceactual) / il.pricelist * 100::numeric
                                                                                                                   ELSE NULL::numeric
       END                                                                                                                                                                             AS discount,
       CASE
           WHEN il.priceactual <> 0::numeric OR il.m_product_id IS NOT NULL THEN il.priceactual
                                                                            ELSE NULL::numeric
       END                                                                                                                                                                             AS priceactual,
       CASE
           WHEN il.priceentered <> 0::numeric OR il.m_product_id IS NOT NULL THEN il.priceentered
                                                                             ELSE NULL::numeric
       END                                                                                                                                                                             AS priceentered,
       CASE
           WHEN il.linenetamt <> 0::numeric OR il.m_product_id IS NOT NULL THEN il.linenetamt
                                                                           ELSE NULL::numeric
       END                                                                                                                                                                             AS linenetamt,
       il.m_attributesetinstance_id,
       asi.m_attributeset_id,
       pt.description                                                                                                                                                                  AS productdescription,
       p.imageurl,
       il.c_campaign_id,
       il.c_project_id,
       il.c_activity_id,
       il.c_projectphase_id,
       il.c_projecttask_id
FROM c_invoiceline il
         JOIN c_uom_trl uom ON il.c_uom_id = uom.c_uom_id
         JOIN c_invoice i ON il.c_invoice_id = i.c_invoice_id
         LEFT JOIN c_tax_trl t ON il.c_tax_id = t.c_tax_id AND uom.ad_language::text = t.ad_language::text
         LEFT JOIN m_product p ON il.m_product_id = p.m_product_id
         LEFT JOIN c_charge_trl c ON il.c_charge_id = c.c_charge_id
         LEFT JOIN c_bpartner_product pp ON il.m_product_id = pp.m_product_id AND i.c_bpartner_id = pp.c_bpartner_id
         LEFT JOIN m_product_trl pt ON il.m_product_id = pt.m_product_id AND uom.ad_language::text = pt.ad_language::text
         LEFT JOIN s_resourceassignment ra ON il.s_resourceassignment_id = ra.s_resourceassignment_id
         LEFT JOIN m_attributesetinstance asi ON il.m_attributesetinstance_id = asi.m_attributesetinstance_id
UNION
SELECT il.ad_client_id,
       il.ad_org_id,
       il.isactive,
       il.created,
       il.createdby,
       il.updated,
       il.updatedby,
       uom.ad_language,
       il.c_invoice_id,
       il.c_invoiceline_id,
       il.c_tax_id,
       il.taxamt,
       il.linetotalamt,
       t.taxindicator,
       il.line + bl.line / 100::numeric          AS line,
       p.m_product_id,
       CASE
           WHEN bl.isqtypercentage = ''N''::bpchar THEN il.qtyinvoiced * bl.qtybom
                                                 ELSE il.qtyinvoiced * (bl.qtybatch / 100::numeric)
       END                                       AS qtyinvoiced,
       CASE
           WHEN bl.isqtypercentage = ''N''::bpchar THEN il.qtyentered * bl.qtybom
                                                 ELSE il.qtyentered * (bl.qtybatch / 100::numeric)
       END                                       AS qtyentered,
       uom.uomsymbol,
       COALESCE(pt.name, p.name)                 AS name,
       b.description,
       COALESCE(pt.documentnote, p.documentnote) AS documentnote,
       p.upc,
       p.sku,
       p.value                                   AS productvalue,
       NULL::character varying                   AS resourcedescription,
       NULL::numeric                             AS pricelist,
       NULL::numeric                             AS priceenteredlist,
       NULL::numeric                             AS discount,
       NULL::numeric                             AS priceactual,
       NULL::numeric                             AS priceentered,
       NULL::numeric                             AS linenetamt,
       il.m_attributesetinstance_id,
       asi.m_attributeset_id,
       pt.description                            AS productdescription,
       p.imageurl,
       il.c_campaign_id,
       il.c_project_id,
       il.c_activity_id,
       il.c_projectphase_id,
       il.c_projecttask_id
FROM pp_product_bom b
         JOIN c_invoiceline il ON b.m_product_id = il.m_product_id
         JOIN m_product bp ON bp.m_product_id = il.m_product_id AND bp.isbom = ''Y''::bpchar AND bp.isverified = ''Y''::bpchar AND bp.isinvoiceprintdetails = ''Y''::bpchar
         JOIN pp_product_bomline bl ON bl.pp_product_bom_id = b.pp_product_bom_id
         JOIN m_product p ON bl.m_product_id = p.m_product_id
         JOIN c_uom_trl uom ON p.c_uom_id = uom.c_uom_id
         JOIN m_product_trl pt ON bl.m_product_id = pt.m_product_id AND uom.ad_language::text = pt.ad_language::text
         LEFT JOIN c_tax t ON il.c_tax_id = t.c_tax_id
         LEFT JOIN m_attributesetinstance asi ON il.m_attributesetinstance_id = asi.m_attributesetinstance_id
UNION
SELECT il.ad_client_id,
       il.ad_org_id,
       il.isactive,
       il.created,
       il.createdby,
       il.updated,
       il.updatedby,
       l.ad_language,
       il.c_invoice_id,
       il.c_invoiceline_id,
       NULL::numeric                     AS c_tax_id,
       NULL::numeric                     AS taxamt,
       NULL::numeric                     AS linetotalamt,
       NULL::character varying           AS taxindicator,
       il.line,
       NULL::numeric                     AS m_product_id,
       NULL::numeric                     AS qtyinvoiced,
       NULL::numeric                     AS qtyentered,
       NULL::character varying           AS uomsymbol,
       il.description                    AS name,
       NULL::character varying           AS description,
       NULL::character varying           AS documentnote,
       NULL::character varying           AS upc,
       NULL::character varying           AS sku,
       NULL::character varying           AS productvalue,
       NULL::character varying           AS resourcedescription,
       NULL::numeric                     AS pricelist,
       NULL::numeric                     AS priceenteredlist,
       NULL::numeric                     AS discount,
       NULL::numeric                     AS priceactual,
       NULL::numeric                     AS priceentered,
       NULL::numeric                     AS linenetamt,
       NULL::numeric                     AS m_attributesetinstance_id,
       NULL::numeric                     AS m_attributeset_id,
       NULL::character varying           AS productdescription,
       NULL::character varying           AS imageurl,
       NULL::numeric                     AS c_campaign_id,
       NULL::numeric                     AS c_project_id,
       NULL::numeric                     AS c_activity_id,
       NULL::numeric                     AS c_projectphase_id,
       NULL::numeric                     AS c_projecttask_id
FROM c_invoiceline il,
     ad_language l
WHERE il.c_uom_id IS NULL
  AND l.isbaselanguage = ''N''::bpchar
  AND l.issystemlanguage = ''Y''::bpchar
UNION
SELECT i.ad_client_id,
       i.ad_org_id,
       i.isactive,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       l.ad_language,
       i.c_invoice_id,
       NULL::numeric                     AS c_invoiceline_id,
       NULL::numeric                     AS c_tax_id,
       NULL::numeric                     AS taxamt,
       NULL::numeric                     AS linetotalamt,
       NULL::character varying           AS taxindicator,
       9998                              AS line,
       NULL::numeric                     AS m_product_id,
       NULL::numeric                     AS qtyinvoiced,
       NULL::numeric                     AS qtyentered,
       NULL::character varying           AS uomsymbol,
       NULL::character varying           AS name,
       NULL::character varying           AS description,
       NULL::character varying           AS documentnote,
       NULL::character varying           AS upc,
       NULL::character varying           AS sku,
       NULL::character varying           AS productvalue,
       NULL::character varying           AS resourcedescription,
       NULL::numeric                     AS pricelist,
       NULL::numeric                     AS priceenteredlist,
       NULL::numeric                     AS discount,
       NULL::numeric                     AS priceactual,
       NULL::numeric                     AS priceentered,
       NULL::numeric                     AS linenetamt,
       NULL::numeric                     AS m_attributesetinstance_id,
       NULL::numeric                     AS m_attributeset_id,
       NULL::character varying           AS productdescription,
       NULL::character varying           AS imageurl,
       NULL::numeric                     AS c_campaign_id,
       NULL::numeric                     AS c_project_id,
       NULL::numeric                     AS c_activity_id,
       NULL::numeric                     AS c_projectphase_id,
       NULL::numeric                     AS c_projecttask_id
FROM c_invoice i,
     ad_language l
WHERE l.isbaselanguage = ''N''::bpchar
  AND l.issystemlanguage = ''Y''::bpchar
UNION
SELECT it.ad_client_id,
       it.ad_org_id,
       it.isactive,
       it.created,
       it.createdby,
       it.updated,
       it.updatedby,
       t.ad_language,
       it.c_invoice_id,
       NULL::numeric                     AS c_invoiceline_id,
       it.c_tax_id,
       NULL::numeric                     AS taxamt,
       NULL::numeric                     AS linetotalamt,
       t.taxindicator,
       9999                              AS line,
       NULL::numeric                     AS m_product_id,
       NULL::numeric                     AS qtyinvoiced,
       NULL::numeric                     AS qtyentered,
       NULL::character varying           AS uomsymbol,
       t.name,
       NULL::character varying           AS description,
       NULL::character varying           AS documentnote,
       NULL::character varying           AS upc,
       NULL::character varying           AS sku,
       NULL::character varying           AS productvalue,
       NULL::character varying           AS resourcedescription,
       NULL::numeric                     AS pricelist,
       NULL::numeric                     AS priceenteredlist,
       NULL::numeric                     AS discount,
       CASE
           WHEN it.istaxincluded = ''Y''::bpchar THEN it.taxamt
                                               ELSE it.taxbaseamt
       END                               AS priceactual,
       CASE
           WHEN it.istaxincluded = ''Y''::bpchar THEN it.taxamt
                                               ELSE it.taxbaseamt
       END                               AS priceentered,
       CASE
           WHEN it.istaxincluded = ''Y''::bpchar THEN NULL::numeric
                                               ELSE it.taxamt
       END                               AS linenetamt,
       NULL::numeric                     AS m_attributesetinstance_id,
       NULL::numeric                     AS m_attributeset_id,
       NULL::character varying           AS productdescription,
       NULL::character varying           AS imageurl,
       NULL::numeric                     AS c_campaign_id,
       NULL::numeric                     AS c_project_id,
       NULL::numeric                     AS c_activity_id,
       NULL::numeric                     AS c_projectphase_id,
       NULL::numeric                     AS c_projecttask_id
FROM c_invoicetax it
         JOIN c_tax_trl t ON it.c_tax_id = t.c_tax_id;
');

--rv_c_invoiceline

SELECT public.db_alter_view('rv_c_invoiceline','SELECT il.ad_client_id,
       il.ad_org_id,
       il.isactive,
       il.created,
       il.createdby,
       il.updated,
       il.updatedby,
       il.c_invoiceline_id,
       i.c_invoice_id,
       i.salesrep_id,
       i.c_bpartner_id,
       i.c_bp_group_id,
       il.m_product_id,
       p.m_product_category_id,
       i.dateinvoiced,
       i.dateacct,
       i.issotrx,
       i.c_doctype_id,
       i.docstatus,
       i.ispaid,
       il.c_campaign_id,
       il.c_project_id,
       il.c_activity_id,
       il.c_projectphase_id,
       il.c_projecttask_id,
       il.qtyinvoiced * i.multiplier::numeric                                          AS qtyinvoiced,
       il.qtyentered * i.multiplier::numeric                                           AS qtyentered,
       il.m_attributesetinstance_id,
       productattribute(il.m_attributesetinstance_id)                                  AS productattribute,
       pasi.m_attributeset_id,
       il.pricelist,
       il.priceactual,
       il.pricelimit,
       il.priceentered,
       CASE
           WHEN il.pricelist = 0::numeric THEN 0::numeric
                                          ELSE ROUND((il.pricelist - il.priceactual) / il.pricelist * 100::numeric, 2)
       END                                                                             AS discount,
       CASE
           WHEN il.pricelimit = 0::numeric THEN 0::numeric
                                           ELSE ROUND((il.priceactual - il.pricelimit) / il.pricelimit * 100::numeric, 2)
       END                                                                             AS margin,
       CASE
           WHEN il.pricelimit = 0::numeric THEN 0::numeric
                                           ELSE (il.priceactual - il.pricelimit) * il.qtyinvoiced
       END                                                                             AS marginamt,
       ROUND(i.multiplier::numeric * il.linenetamt, 2)                                 AS linenetamt,
       ROUND(i.multiplier::numeric * il.pricelist * il.qtyinvoiced, 2)                 AS linelistamt,
       CASE
           WHEN COALESCE(il.pricelimit, 0::numeric) = 0::numeric THEN ROUND(i.multiplier::numeric * il.linenetamt, 2)
                                                                 ELSE ROUND(i.multiplier::numeric * il.pricelimit * il.qtyinvoiced, 2)
       END                                                                             AS linelimitamt,
       ROUND(i.multiplier::numeric * il.pricelist * il.qtyinvoiced - il.linenetamt, 2) AS linediscountamt,
       CASE
           WHEN COALESCE(il.pricelimit, 0::numeric) = 0::numeric THEN 0::numeric
                                                                 ELSE ROUND(i.multiplier::numeric * il.linenetamt - il.pricelimit * il.qtyinvoiced, 2)
       END                                                                             AS lineoverlimitamt
FROM rv_c_invoice i
         JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
         LEFT JOIN m_product p ON il.m_product_id = p.m_product_id
         LEFT JOIN m_attributesetinstance pasi ON il.m_attributesetinstance_id = pasi.m_attributesetinstance_id;
');

--rv_inoutdetails
SELECT public.db_alter_view('rv_inoutdetails','SELECT h.ad_client_id,
       h.ad_org_id,
       l.isactive,
       l.created,
       l.createdby,
       l.updated,
       l.updatedby,
       h.m_inout_id,
       h.issotrx,
       h.documentno,
       h.docaction,
       h.docstatus,
       h.posted,
       h.processed,
       h.c_doctype_id,
       h.description,
       h.c_order_id,
       h.dateordered,
       h.movementtype,
       h.movementdate,
       h.dateacct,
       h.c_bpartner_id,
       h.c_bpartner_location_id,
       h.ad_user_id,
       h.salesrep_id,
       h.m_warehouse_id,
       h.poreference,
       h.deliveryrule,
       h.freightcostrule,
       h.freightamt,
       h.deliveryviarule,
       h.m_shipper_id,
       h.priorityrule,
       h.dateprinted,
       h.pickdate,
       h.ad_orgtrx_id,
       h.c_project_id,
       h.c_campaign_id,
       h.c_activity_id,
       h.user1_id,
       h.user2_id,
       h.datereceived,
       h.isapproved,
       h.isindispute,
       l.m_inoutline_id,
       l.line,
       l.description                                 AS linedescription,
       l.c_orderline_id,
       l.m_locator_id,
       l.m_product_id,
       l.c_uom_id,
       l.m_attributesetinstance_id,
       productattribute(l.m_attributesetinstance_id) AS productattribute,
       pasi.m_attributeset_id,
       l.movementqty,
       l.qtyentered,
       l.isdescription,
       l.confirmedqty,
       l.pickedqty,
       l.scrappedqty,
       l.targetqty,
       loc.value                                     AS locatorvalue,
       loc.x,
       loc.y,
       loc.z
FROM m_inout h
         JOIN m_inoutline l ON h.m_inout_id = l.m_inout_id
         LEFT JOIN m_locator loc ON l.m_locator_id = loc.m_locator_id
         LEFT JOIN m_attributesetinstance pasi ON l.m_attributesetinstance_id = pasi.m_attributesetinstance_id;
');

--rv_dd_orderdetail
SELECT public.db_alter_view('rv_dd_orderdetail','SELECT l.ad_client_id,
       l.ad_org_id,
       l.isactive,
       l.created,
       l.createdby,
       l.updated,
       l.updatedby,
       o.dd_order_id,
       o.c_order_id,
       o.docstatus,
       o.docaction,
       o.c_doctype_id,
       o.isapproved,
       o.salesrep_id,
       o.isdropship,
       o.c_bpartner_id,
       bp.c_bp_group_id,
       o.ad_user_id,
       o.poreference,
       o.issotrx,
       l.c_campaign_id,
       l.c_project_id,
       l.c_activity_id,
       l.dd_orderline_id,
       l.dateordered,
       l.datepromised,
       l.m_product_id,
       l.m_locator_id,
       l.m_locatorto_id,
       l.m_attributesetinstance_id,
       productattribute(l.m_attributesetinstance_id)   AS productattribute,
       l.m_attributesetinstanceto_id,
       productattribute(l.m_attributesetinstanceto_id) AS productattributeto,
       pasi.m_attributeset_id,
       l.c_uom_id,
       l.qtyentered,
       l.qtyordered,
       l.qtyreserved,
       l.qtydelivered,
       l.confirmedqty,
       l.qtyintransit,
       l.targetqty,
       l.qtyordered - l.qtydelivered                   AS qtytodeliver,
       l.description
FROM dd_order o
         JOIN dd_orderline l ON l.dd_order_id = o.dd_order_id
         JOIN c_bpartner bp ON bp.c_bpartner_id = o.c_bpartner_id
         LEFT JOIN m_attributesetinstance pasi ON l.m_attributesetinstance_id = pasi.m_attributesetinstance_id
         LEFT JOIN m_attributesetinstance pasito ON l.m_attributesetinstanceto_id = pasito.m_attributesetinstance_id;
');

--m_inout_lineconfirm_vt
SELECT public.db_alter_view('m_inout_lineconfirm_vt','SELECT iolc.ad_client_id,
       iolc.ad_org_id,
       iolc.isactive,
       iolc.created,
       iolc.createdby,
       iolc.updated,
       iolc.updatedby,
       uom.ad_language,
       iolc.m_inoutlineconfirm_id,
       iolc.m_inoutconfirm_id,
       iolc.targetqty,
       iolc.confirmedqty,
       iolc.differenceqty,
       iolc.scrappedqty,
       iolc.description,
       iolc.processed,
       iol.m_inout_id,
       iol.m_inoutline_id,
       iol.line,
       p.m_product_id,
       iol.movementqty,
       uom.uomsymbol,
       ol.qtyordered - ol.qtydelivered                      AS qtybackordered,
       COALESCE(COALESCE(pt.name, p.name), iol.description) AS name,
       CASE
           WHEN COALESCE(pt.name, p.name) IS NOT NULL THEN iol.description
                                                      ELSE NULL::character varying
       END                                                  AS shipdescription,
       COALESCE(pt.documentnote, p.documentnote)            AS documentnote,
       p.upc,
       p.sku,
       p.value                                              AS productvalue,
       iol.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       iol.m_attributesetinstance_id,
       asi.m_attributeset_id
FROM m_inoutlineconfirm iolc
         JOIN m_inoutline iol ON iolc.m_inoutline_id = iol.m_inoutline_id
         JOIN c_uom_trl uom ON iol.c_uom_id = uom.c_uom_id
         LEFT JOIN m_product p ON iol.m_product_id = p.m_product_id
         LEFT JOIN m_product_trl pt ON iol.m_product_id = pt.m_product_id AND uom.ad_language::text = pt.ad_language::text
         LEFT JOIN m_attributesetinstance asi ON iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
         LEFT JOIN m_locator l ON iol.m_locator_id = l.m_locator_id
         LEFT JOIN c_orderline ol ON iol.c_orderline_id = ol.c_orderline_id;
');

--m_inout_line_vt
SELECT public.db_alter_view('m_inout_line_vt','SELECT iol.ad_client_id,
       iol.ad_org_id,
       iol.isactive,
       iol.created,
       iol.createdby,
       iol.updated,
       iol.updatedby,
       uom.ad_language,
       iol.m_inout_id,
       iol.m_inoutline_id,
       iol.line,
       p.m_product_id,
       CASE
           WHEN iol.movementqty <> 0::numeric OR iol.m_product_id IS NOT NULL THEN iol.movementqty
                                                                              ELSE NULL::numeric
       END                                                                                                                                     AS movementqty,
       CASE
           WHEN iol.qtyentered <> 0::numeric OR iol.m_product_id IS NOT NULL THEN iol.qtyentered
                                                                             ELSE NULL::numeric
       END                                                                                                                                     AS qtyentered,
       CASE
           WHEN iol.movementqty <> 0::numeric OR iol.m_product_id IS NOT NULL THEN uom.uomsymbol
                                                                              ELSE NULL::character varying
       END                                                                                                                                     AS uomsymbol,
       ol.qtyordered,
       ol.qtydelivered,
       CASE
           WHEN iol.movementqty <> 0::numeric OR iol.m_product_id IS NOT NULL THEN ol.qtyordered - ol.qtydelivered
                                                                              ELSE NULL::numeric
       END                                                                                                                                     AS qtybackordered,
       COALESCE(COALESCE(pt.name, p.name)::text || productattribute(iol.m_attributesetinstance_id)::text, c.name::text, iol.description::text) AS name,
       CASE
           WHEN COALESCE(pt.name, p.name, c.name) IS NOT NULL THEN iol.description
                                                              ELSE NULL::character varying
       END                                                                                                                                     AS description,
       COALESCE(pt.documentnote, p.documentnote)                                                                                               AS documentnote,
       p.upc,
       p.sku,
       p.value                                                                                                                                 AS productvalue,
       iol.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       iol.m_attributesetinstance_id,
       asi.m_attributeset_id,
       pt.description                                                                                                                          AS productdescription,
       p.imageurl,
       iol.c_campaign_id,
       iol.c_project_id,
       iol.c_activity_id,
       iol.c_projectphase_id,
       iol.c_projecttask_id
FROM m_inoutline iol
         JOIN c_uom_trl uom ON iol.c_uom_id = uom.c_uom_id
         LEFT JOIN m_product p ON iol.m_product_id = p.m_product_id
         LEFT JOIN m_product_trl pt ON iol.m_product_id = pt.m_product_id AND uom.ad_language::text = pt.ad_language::text
         LEFT JOIN m_attributesetinstance asi ON iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
         LEFT JOIN m_locator l ON iol.m_locator_id = l.m_locator_id
         LEFT JOIN c_orderline ol ON iol.c_orderline_id = ol.c_orderline_id
         LEFT JOIN c_charge c ON iol.c_charge_id = c.c_charge_id
UNION
SELECT iol.ad_client_id,
       iol.ad_org_id,
       iol.isactive,
       iol.created,
       iol.createdby,
       iol.updated,
       iol.updatedby,
       uom.ad_language,
       iol.m_inout_id,
       iol.m_inoutline_id,
       iol.line + bl.line / 100::numeric         AS line,
       p.m_product_id,
       CASE
           WHEN bl.isqtypercentage = ''N''::bpchar THEN iol.movementqty * bl.qtybom
                                                 ELSE iol.movementqty * (bl.qtybatch / 100::numeric)
       END                                       AS movementqty,
       CASE
           WHEN bl.isqtypercentage = ''N''::bpchar THEN iol.qtyentered * bl.qtybom
                                                 ELSE iol.qtyentered * (bl.qtybatch / 100::numeric)
       END                                       AS qtyentered,
       uom.uomsymbol,
       NULL::numeric                             AS qtyordered,
       NULL::numeric                             AS qtydelivered,
       NULL::numeric                             AS qtybackordered,
       COALESCE(pt.name, p.name)                 AS name,
       b.description,
       COALESCE(pt.documentnote, p.documentnote) AS documentnote,
       p.upc,
       p.sku,
       p.value                                   AS productvalue,
       iol.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       iol.m_attributesetinstance_id,
       asi.m_attributeset_id,
       pt.description                            AS productdescription,
       p.imageurl,
       iol.c_campaign_id,
       iol.c_project_id,
       iol.c_activity_id,
       iol.c_projectphase_id,
       iol.c_projecttask_id
FROM pp_product_bom b
         JOIN m_inoutline iol ON b.m_product_id = iol.m_product_id
         JOIN m_product bp ON bp.m_product_id = iol.m_product_id AND bp.isbom = ''Y''::bpchar AND bp.isverified = ''Y''::bpchar AND bp.ispicklistprintdetails = ''Y''::bpchar
         JOIN pp_product_bomline bl ON bl.pp_product_bom_id = b.pp_product_bom_id
         JOIN m_product p ON bl.m_product_id = p.m_product_id
         JOIN c_uom_trl uom ON p.c_uom_id = uom.c_uom_id
         JOIN m_product_trl pt ON bl.m_product_id = pt.m_product_id AND uom.ad_language::text = pt.ad_language::text
         LEFT JOIN m_attributesetinstance asi ON iol.m_attributesetinstance_id = asi.m_attributesetinstance_id
         LEFT JOIN m_locator l ON iol.m_locator_id = l.m_locator_id;
');

--rv_costdetail
SELECT public.db_alter_view('rv_costdetail','SELECT c.ad_client_id,
       c.ad_org_id,
       c.isactive,
       c.created,
       c.createdby,
       c.updated,
       c.updatedby,
       p.m_product_id,
       p.value,
       p.name,
       p.upc,
       p.isbom,
       p.producttype,
       p.m_product_category_id,
       c.m_inoutline_id,
       c.c_invoiceline_id,
       asi.m_attributesetinstance_id,
       asi.m_attributeset_id,
       acct.c_acctschema_id,
       acct.c_currency_id,
       c.amt,
       c.qty,
       c.description,
       c.processed
FROM m_costdetail c
         JOIN m_product p ON c.m_product_id = p.m_product_id
         JOIN c_acctschema acct ON c.c_acctschema_id = acct.c_acctschema_id
         JOIN m_attributesetinstance asi ON c.m_attributesetinstance_id = asi.m_attributesetinstance_id;
');

--rv_asset_delivery
SELECT public.db_alter_view('rv_asset_delivery','SELECT ad.a_asset_delivery_id,
       ad.ad_client_id,
       ad.ad_org_id,
       ad.isactive,
       ad.created,
       ad.createdby,
       ad.updated,
       ad.updatedby,
       a.a_asset_id,
       a.a_asset_group_id,
       a.m_product_id,
       a.assetservicedate,
       a.c_bpartner_id,
       ad.ad_user_id,
       ad.movementdate,
       ad.versionno,
       ad.m_inoutline_id,
       ad.email,
       ad.messageid,
       ad.deliveryconfirmation,
       ad.url,
       ad.remote_addr,
       ad.remote_host,
       ad.referrer,
       ad.description
FROM a_asset_delivery ad
         JOIN a_asset a ON a.a_asset_id = ad.a_asset_id;
');

--rv_asset_customer
SELECT public.db_alter_view('rv_asset_customer','SELECT a.a_asset_id,
       a.ad_client_id,
       a.ad_org_id,
       a.isactive,
       a.created,
       a.createdby,
       a.updated,
       a.updatedby,
       a.value,
       a.name,
       a.description,
       a.help,
       a.a_asset_group_id,
       a.m_product_id,
       a.versionno,
       a.assetservicedate,
       a.c_bpartner_id,
       a.c_bpartner_location_id,
       a.ad_user_id,
       (SELECT COUNT(*) AS count
        FROM a_asset_delivery ad
        WHERE a.a_asset_id = ad.a_asset_id) AS deliverycount
FROM a_asset a
WHERE a.c_bpartner_id IS NOT NULL;
');

--rv_asset_summonth
SELECT public.db_alter_view('rv_asset_summonth','SELECT a.ad_client_id,
       a.ad_org_id,
       a.isactive,
       a.created,
       a.createdby,
       a.updated,
       a.updatedby,
       a.a_asset_id,
       a.a_asset_group_id,
       a.m_product_id,
       a.value,
       a.name,
       a.description,
       a.help,
       a.assetservicedate,
       a.c_bpartner_id,
       a.ad_user_id,
       a.versionno,
       firstof(ad.movementdate, ''MM''::character varying) AS movementdate,
       COUNT(*)                                          AS deliverycount
FROM a_asset a
         JOIN a_asset_delivery ad ON a.a_asset_id = ad.a_asset_id
GROUP BY a.ad_client_id, a.ad_org_id, a.isactive, a.created, a.createdby, a.updated, a.updatedby, a.a_asset_id, a.a_asset_group_id, a.m_product_id, a.value, a.name, a.description, a.help, a.assetservicedate, a.c_bpartner_id, a.ad_user_id, a.versionno, (firstof(ad.movementdate, ''MM''::character varying))
;
');
