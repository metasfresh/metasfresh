DROP VIEW IF EXISTS "de_metas_acct".accountable_docs_and_lines_v
;

CREATE OR REPLACE VIEW "de_metas_acct".accountable_docs_and_lines_v AS
(
    SELECT 'C_Order'                                                                   AS TableName,
           10::integer                                                                 AS tablename_prio,
           o.c_order_id                                                                AS Record_ID,
           NULL::numeric                                                               AS reversal_id,
           ol.c_orderline_id                                                           AS Line_ID,
           NULL::numeric                                                               AS reversalline_id,
           o.issotrx,
           o.docstatus,
           o.posted,
           o.dateacct                                                                  AS dateacct,
           --
           ol.m_product_id,
           o.c_currency_id,
           ol.priceactual                                                              AS price,
           (SELECT p.c_uom_id FROM m_product p WHERE p.m_product_id = ol.m_product_id) AS c_uom_id,
           ol.qtyordered                                                               AS qty
    FROM c_orderline ol
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
)
UNION ALL
(
    SELECT 'C_Invoice'                                                                 AS TableName,
           20::integer                                                                 AS tablename_prio,
           i.c_invoice_id                                                              AS Record_ID,
           i.reversal_id                                                               AS reversal_id,
           il.c_invoiceline_id                                                         AS Line_ID,
           NULL                                                                        AS reversalline_id,
           i.issotrx,
           i.docstatus,
           i.posted,
           i.dateacct                                                                  AS dateacct,
           --
           il.m_product_id,
           i.c_currency_id,
           il.priceactual                                                              AS price,
           (SELECT p.c_uom_id FROM m_product p WHERE p.m_product_id = il.m_product_id) AS c_uom_id,
           il.qtyinvoiced                                                              AS qty
    FROM c_invoiceline il
             INNER JOIN c_invoice i ON il.c_invoice_id = i.c_invoice_id
)
UNION ALL
(
    SELECT 'M_InOut'                                                                    AS TableName,
           30::integer                                                                  AS tablename_prio,
           io.m_inout_id                                                                AS Record_ID,
           io.reversal_id                                                               AS reversal_id,
           iol.m_inoutline_id                                                           AS Line_ID,
           iol.reversalline_id                                                          AS reversalline_id,
           io.issotrx,
           io.docstatus,
           io.posted,
           io.dateacct                                                                  AS dateacct,
           --
           iol.m_product_id,
           NULL                                                                         AS c_currency_id,
           NULL                                                                         AS price,
           (SELECT p.c_uom_id FROM m_product p WHERE p.m_product_id = iol.m_product_id) AS c_uom_id,
           iol.movementqty                                                              AS qty
    FROM m_inoutline iol
             INNER JOIN m_inout io ON iol.m_inout_id = io.m_inout_id
)
UNION ALL
(
    SELECT 'M_MatchPO'                                                                 AS TableName,
           40::integer                                                                 AS tablename_prio,
           mpo.m_matchpo_id                                                            AS Record_ID,
           NULL                                                                        AS reversal_id,
           NULL                                                                        AS Line_ID,
           NULL                                                                        AS reversalline_id,
           o.issotrx                                                                   AS issotrx,
           'CO'                                                                        AS docstatus,
           mpo.posted,
           mpo.dateacct                                                                AS dateacct,
           --
           mpo.m_product_id,
           o.c_currency_id,
           ol.priceactual                                                              AS price,
           (SELECT p.c_uom_id FROM m_product p WHERE p.m_product_id = ol.m_product_id) AS c_uom_id,
           mpo.qty                                                                     AS qty
    FROM m_matchpo mpo
             INNER JOIN c_orderline ol ON ol.c_orderline_id = mpo.c_orderline_id
             INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
)
UNION ALL
(
    SELECT 'M_MatchInv'                                                                AS TableName,
           50::integer                                                                 AS tablename_prio,
           mi.m_matchinv_id                                                            AS Record_ID,
           NULL                                                                        AS reversal_id,
           NULL                                                                        AS Line_ID,
           NULL                                                                        AS reversalline_id,
           i.issotrx,
           NULL                                                                        AS docstatus,
           mi.posted,
           mi.dateacct                                                                 AS dateacct,
           --
           mi.m_product_id,
           i.c_currency_id,
           il.priceactual                                                              AS price,
           (SELECT p.c_uom_id FROM m_product p WHERE p.m_product_id = il.m_product_id) AS c_uom_id,
           mi.qty                                                                      AS qty
    FROM m_matchinv mi
             INNER JOIN c_invoiceline il ON mi.c_invoiceline_id = il.c_invoiceline_id
             INNER JOIN c_invoice i ON i.c_invoice_id = il.c_invoice_id
)
UNION ALL
(
    SELECT 'M_Inventory'                                                                 AS TableName,
           60::integer                                                                   AS tablename_prio,
           inv.m_inventory_id                                                            AS Record_ID,
           inv.reversal_id                                                               AS reversal_id,
           invl.m_inventoryline_id                                                       AS Line_ID,
           invl.reversalline_id                                                          AS reversalline_id,
           NULL                                                                          AS issotrx,
           inv.docstatus,
           inv.posted,
           inv.movementdate                                                              AS dateacct,
           --
           invl.m_product_id,
           NULL                                                                          AS c_currency_id,
           invl.costprice                                                                AS price,
           (SELECT p.c_uom_id FROM m_product p WHERE p.m_product_id = invl.m_product_id) AS c_uom_id,
           (CASE
                WHEN coalesce(invl.qtyinternaluse, 0) != 0
                    THEN invl.qtyinternaluse
                    ELSE invl.qtycount - invl.qtybook
            END)                                                                         AS qty
    FROM m_inventoryline invl
             INNER JOIN m_inventory inv ON invl.m_inventory_id = inv.m_inventory_id
)
UNION ALL
(
    SELECT 'M_Movement'                                                                AS TableName,
           70::integer                                                                 AS tablename_prio,
           m.m_movement_id                                                             AS Record_ID,
           m.reversal_id                                                               AS reversal_id,
           ml.m_movementline_id                                                        AS Line_ID,
           ml.reversalline_id                                                          AS reversalline_id,
           NULL                                                                        AS issotrx,
           m.docstatus,
           m.posted,
           m.movementdate                                                              AS dateacct,
           --
           ml.m_product_id,
           NULL                                                                        AS c_currency_id,
           NULL                                                                        AS price,
           (SELECT p.c_uom_id FROM m_product p WHERE p.m_product_id = ml.m_product_id) AS c_uom_id,
           ml.movementqty                                                              AS qty
    FROM m_movementline ml
             INNER JOIN m_movement m ON ml.m_movement_id = m.m_movement_id
)
UNION ALL
(
    SELECT 'PP_Order'      AS TableName,
           80::integer     AS tablename_prio,
           mo.pp_order_id  AS Record_ID,
           NULL            AS reversal_id,
           NULL            AS Line_ID,
           NULL            AS reversalline_id,
           NULL            AS issotrx,
           mo.docstatus    AS docstatus,
           mo.posted       AS posted,
           mo.dateordered  AS dateacct,
           --
           mo.m_product_id AS m_product_id,
           NULL            AS c_currency_id,
           NULL            AS price,
           mo.c_uom_id     AS c_uom_id,
           mo.qtyordered   AS qty
    FROM pp_order mo
)
UNION ALL
(
    SELECT 'PP_Cost_Collector'     AS TableName,
           90::integer             AS tablename_prio,
           cc.pp_cost_collector_id AS Record_ID,
           cc.reversal_id          AS reversal_id,
           NULL                    AS Line_ID,
           NULL                    AS reversalline_id,
           NULL                    AS issotrx,
           cc.docstatus            AS docstatus,
           cc.posted               AS posted,
           cc.dateacct             AS dateacct,
           --
           cc.m_product_id         AS m_product_id,
           NULL                    AS c_currency_id,
           NULL                    AS price,
           cc.c_uom_id             AS c_uom_id,
           cc.movementqty          AS qty
    FROM pp_cost_collector cc
)
;

/*
SELECT *
FROM "de_metas_acct".accountable_docs_and_lines_v
WHERE TRUE
  AND m_product_id = 1000038
ORDER BY 1,
         m_product_id,
         dateacct,
         tablename_prio,
         least(record_id, reversal_id),
         record_id
;
*/

/*
SELECT *
FROM (
         SELECT t.m_product_id,
                (SELECT p.value || '_' || p.name FROM m_product p WHERE p.m_product_id = t.m_product_id) AS product,
                count(1)                                                                                 AS count_all,
                sum(CASE WHEN t.TableName = 'C_Order' THEN 1 ELSE 0 END)                                 AS count_orders,
                sum(CASE WHEN t.TableName = 'C_Invoice' THEN 1 ELSE 0 END)                               AS count_invoices,
                sum(CASE WHEN t.TableName = 'M_MatchPO' THEN 1 ELSE 0 END)                               AS count_match_po,
                sum(CASE WHEN t.TableName = 'M_MatchInv' THEN 1 ELSE 0 END)                              AS count_match_inv,
                sum(CASE WHEN t.TableName = 'M_InOut' THEN 1 ELSE 0 END)                                 AS count_inout,
                sum(CASE WHEN t.TableName = 'M_Inventory' THEN 1 ELSE 0 END)                             AS count_inventory,
                sum(CASE WHEN t.TableName = 'M_Movement' THEN 1 ELSE 0 END)                              AS count_movement,
                sum(CASE WHEN t.TableName = 'PP_Cost_Collector' THEN 1 ELSE 0 END)                       AS count_pp_cost_collector
         FROM "de_metas_acct".accountable_docs_and_lines_v t
         WHERE TRUE
         GROUP BY m_product_id
     ) t
WHERE TRUE
  AND count_invoices > 0
  AND count_inout > 0
ORDER BY t.count_movement DESC;
*/
