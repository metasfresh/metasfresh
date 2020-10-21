DROP VIEW IF EXISTS rv_unposted
;

CREATE OR REPLACE VIEW rv_unposted AS
(
    SELECT gl_journal.ad_client_id,
           gl_journal.ad_org_id,
           gl_journal.created,
           gl_journal.createdby,
           gl_journal.updated,
           gl_journal.updatedby,
           gl_journal.isactive,
           gl_journal.documentno,
           gl_journal.datedoc,
           gl_journal.dateacct,
           224                      AS ad_table_id,
           gl_journal.gl_journal_id AS record_id,
           'N'::char(1)             AS issotrx,
           gl_journal.posted,
           gl_journal.PostingError_Issue_ID,
           gl_journal.processing,
           gl_journal.processed,
           gl_journal.docstatus
    FROM gl_journal
    WHERE gl_journal.posted <> 'Y'
      AND gl_journal.docstatus <> 'VO'
      AND gl_journal.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT pi.ad_client_id,
           pi.ad_org_id,
           pi.created,
           pi.createdby,
           pi.updated,
           pi.updatedby,
           pi.isactive,
           (p.name::text || '_'::text) || pi.line::text AS documentno,
           pi.movementdate                              AS datedoc,
           pi.movementdate                              AS dateacct,
           623                                          AS ad_table_id,
           pi.c_projectissue_id                         AS record_id,
           'N'::char(1)                                 AS issotrx,
           pi.posted,
           pi.PostingError_Issue_ID,
           pi.processing,
           pi.processed,
           'CO'                                         AS docstatus
    FROM c_projectissue pi
             JOIN c_project p ON pi.c_project_id = p.c_project_id
    WHERE pi.posted <> 'Y'
      AND pi.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT c_invoice.ad_client_id,
           c_invoice.ad_org_id,
           c_invoice.created,
           c_invoice.createdby,
           c_invoice.updated,
           c_invoice.updatedby,
           c_invoice.isactive,
           c_invoice.documentno,
           c_invoice.dateinvoiced AS datedoc,
           c_invoice.dateacct,
           318                    AS ad_table_id,
           c_invoice.c_invoice_id AS record_id,
           c_invoice.issotrx,
           c_invoice.posted,
           c_invoice.PostingError_Issue_ID,
           c_invoice.processing,
           c_invoice.processed,
           c_invoice.docstatus
    FROM c_invoice
    WHERE c_invoice.posted <> 'Y'
      AND c_invoice.docstatus <> 'VO'
      AND c_invoice.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT m_inout.ad_client_id,
           m_inout.ad_org_id,
           m_inout.created,
           m_inout.createdby,
           m_inout.updated,
           m_inout.updatedby,
           m_inout.isactive,
           m_inout.documentno,
           m_inout.movementdate AS datedoc,
           m_inout.dateacct,
           319                  AS ad_table_id,
           m_inout.m_inout_id   AS record_id,
           m_inout.issotrx,
           m_inout.posted,
           m_inout.PostingError_Issue_ID,
           m_inout.processing,
           m_inout.processed,
           m_inout.docstatus
    FROM m_inout
    WHERE m_inout.posted <> 'Y'
      AND m_inout.docstatus <> 'VO'
      AND m_inout.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT m_inventory.ad_client_id,
           m_inventory.ad_org_id,
           m_inventory.created,
           m_inventory.createdby,
           m_inventory.updated,
           m_inventory.updatedby,
           m_inventory.isactive,
           m_inventory.documentno,
           m_inventory.movementdate   AS datedoc,
           m_inventory.movementdate   AS dateacct,
           321                        AS ad_table_id,
           m_inventory.m_inventory_id AS record_id,
           'N'::char(1)               AS issotrx,
           m_inventory.posted,
           m_inventory.PostingError_Issue_ID,
           m_inventory.processing,
           m_inventory.processed,
           m_inventory.docstatus
    FROM m_inventory
    WHERE m_inventory.posted <> 'Y'
      AND m_inventory.docstatus <> 'VO'
      AND m_inventory.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT m_movement.ad_client_id,
           m_movement.ad_org_id,
           m_movement.created,
           m_movement.createdby,
           m_movement.updated,
           m_movement.updatedby,
           m_movement.isactive,
           m_movement.documentno,
           m_movement.movementdate  AS datedoc,
           m_movement.movementdate  AS dateacct,
           323                      AS ad_table_id,
           m_movement.m_movement_id AS record_id,
           'N'::char(1)             AS issotrx,
           m_movement.posted,
           m_movement.PostingError_Issue_ID,
           m_movement.processing,
           m_movement.processed,
           m_movement.docstatus
    FROM m_movement
    WHERE m_movement.posted <> 'Y'
      AND m_movement.docstatus <> 'VO'
      AND m_movement.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT c_cash.ad_client_id,
           c_cash.ad_org_id,
           c_cash.created,
           c_cash.createdby,
           c_cash.updated,
           c_cash.updatedby,
           c_cash.isactive,
           c_cash.name          AS documentno,
           c_cash.statementdate AS datedoc,
           c_cash.dateacct,
           407                  AS ad_table_id,
           c_cash.c_cash_id     AS record_id,
           'N'::char(1)         AS issotrx,
           c_cash.posted,
           c_cash.PostingError_Issue_ID,
           c_cash.processing,
           c_cash.processed,
           c_cash.docstatus
    FROM c_cash
    WHERE c_cash.posted <> 'Y'
      AND c_cash.docstatus <> 'VO'
      AND c_cash.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT c_payment.ad_client_id,
           c_payment.ad_org_id,
           c_payment.created,
           c_payment.createdby,
           c_payment.updated,
           c_payment.updatedby,
           c_payment.isactive,
           c_payment.documentno,
           c_payment.datetrx      AS datedoc,
           c_payment.datetrx      AS dateacct,
           335                    AS ad_table_id,
           c_payment.c_payment_id AS record_id,
           'N'::char(1)           AS issotrx,
           c_payment.posted,
           c_payment.PostingError_Issue_ID,
           c_payment.processing,
           c_payment.processed,
           c_payment.docstatus
    FROM c_payment
    WHERE c_payment.posted <> 'Y'
      AND c_payment.docstatus <> 'VO'
      AND c_payment.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT c_allocationhdr.ad_client_id,
           c_allocationhdr.ad_org_id,
           c_allocationhdr.created,
           c_allocationhdr.createdby,
           c_allocationhdr.updated,
           c_allocationhdr.updatedby,
           c_allocationhdr.isactive,
           c_allocationhdr.documentno,
           c_allocationhdr.datetrx            AS datedoc,
           c_allocationhdr.datetrx            AS dateacct,
           735                                AS ad_table_id,
           c_allocationhdr.c_allocationhdr_id AS record_id,
           'N'::char(1)                       AS issotrx,
           c_allocationhdr.posted,
           c_allocationhdr.PostingError_Issue_ID,
           c_allocationhdr.processing,
           c_allocationhdr.processed,
           c_allocationhdr.docstatus
    FROM c_allocationhdr
    WHERE c_allocationhdr.posted <> 'Y'
      AND c_allocationhdr.docstatus <> 'VO'
      AND c_allocationhdr.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT c_bankstatement.ad_client_id,
           c_bankstatement.ad_org_id,
           c_bankstatement.created,
           c_bankstatement.createdby,
           c_bankstatement.updated,
           c_bankstatement.updatedby,
           c_bankstatement.isactive,
           c_bankstatement.name               AS documentno,
           c_bankstatement.statementdate      AS datedoc,
           c_bankstatement.statementdate      AS dateacct,
           392                                AS ad_table_id,
           c_bankstatement.c_bankstatement_id AS record_id,
           'N'::char(1)                       AS issotrx,
           c_bankstatement.posted,
           c_bankstatement.PostingError_Issue_ID,
           c_bankstatement.processing,
           c_bankstatement.processed,
           c_bankstatement.docstatus
    FROM c_bankstatement
    WHERE c_bankstatement.posted <> 'Y'
      AND c_bankstatement.docstatus <> 'VO'
      AND c_bankstatement.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT m_matchinv.ad_client_id,
           m_matchinv.ad_org_id,
           m_matchinv.created,
           m_matchinv.createdby,
           m_matchinv.updated,
           m_matchinv.updatedby,
           m_matchinv.isactive,
           m_matchinv.documentno,
           m_matchinv.datetrx       AS datedoc,
           m_matchinv.datetrx       AS dateacct,
           472                      AS ad_table_id,
           m_matchinv.m_matchinv_id AS record_id,
           'N'::char(1)             AS issotrx,
           m_matchinv.posted,
           m_matchinv.PostingError_Issue_ID,
           m_matchinv.processing,
           m_matchinv.processed,
           'CO'                     AS docstatus
    FROM m_matchinv
    WHERE m_matchinv.posted <> 'Y'
      AND m_matchinv.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT m_matchpo.ad_client_id,
           m_matchpo.ad_org_id,
           m_matchpo.created,
           m_matchpo.createdby,
           m_matchpo.updated,
           m_matchpo.updatedby,
           m_matchpo.isactive,
           m_matchpo.documentno,
           m_matchpo.datetrx      AS datedoc,
           m_matchpo.datetrx      AS dateacct,
           473                    AS ad_table_id,
           m_matchpo.m_matchpo_id AS record_id,
           'N'::char(1)           AS issotrx,
           m_matchpo.posted,
           m_matchpo.PostingError_Issue_ID,
           m_matchpo.processing,
           m_matchpo.processed,
           'CO'                   AS docstatus
    FROM m_matchpo
    WHERE m_matchpo.posted <> 'Y'
      AND m_matchpo.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT c_order.ad_client_id,
           c_order.ad_org_id,
           c_order.created,
           c_order.createdby,
           c_order.updated,
           c_order.updatedby,
           c_order.isactive,
           c_order.documentno,
           c_order.dateordered AS datedoc,
           c_order.dateacct,
           259                 AS ad_table_id,
           c_order.c_order_id  AS record_id,
           c_order.issotrx,
           c_order.posted,
           c_order.PostingError_Issue_ID,
           c_order.processing,
           c_order.processed,
           c_order.docstatus
    FROM c_order
    WHERE c_order.posted <> 'Y'
      AND (c_order.docstatus <> ALL (ARRAY ['VO', 'WP']))
      AND c_order.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT m_requisition.ad_client_id,
           m_requisition.ad_org_id,
           m_requisition.created,
           m_requisition.createdby,
           m_requisition.updated,
           m_requisition.updatedby,
           m_requisition.isactive,
           m_requisition.documentno,
           m_requisition.daterequired     AS datedoc,
           m_requisition.daterequired     AS dateacct,
           702                            AS ad_table_id,
           m_requisition.m_requisition_id AS record_id,
           'N'::char(1)                   AS issotrx,
           m_requisition.posted,
           m_requisition.PostingError_Issue_ID,
           m_requisition.processing,
           m_requisition.processed,
           m_requisition.docstatus
    FROM m_requisition
    WHERE m_requisition.posted <> 'Y'
      AND m_requisition.docstatus <> 'VO'
      AND m_requisition.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT pp_order.ad_client_id,
           pp_order.ad_org_id,
           pp_order.created,
           pp_order.createdby,
           pp_order.updated,
           pp_order.updatedby,
           pp_order.isactive,
           pp_order.documentno,
           pp_order.dateordered AS datedoc,
           pp_order.dateordered AS dateacct,
           53027                AS ad_table_id,
           pp_order.pp_order_id AS record_id,
           'N'::char(1)         AS issotrx,
           pp_order.posted,
           pp_order.PostingError_Issue_ID,
           pp_order.processing,
           pp_order.processed,
           pp_order.docstatus
    FROM pp_order
    WHERE pp_order.posted <> 'Y'
      AND pp_order.docstatus <> 'VO'
      AND pp_order.processed = 'Y'
)
--
UNION ALL
--
(
    SELECT pp_cost_collector.ad_client_id,
           pp_cost_collector.ad_org_id,
           pp_cost_collector.created,
           pp_cost_collector.createdby,
           pp_cost_collector.updated,
           pp_cost_collector.updatedby,
           pp_cost_collector.isactive,
           pp_cost_collector.documentno,
           pp_cost_collector.movementdate         AS datedoc,
           pp_cost_collector.dateacct             AS dateacct,
           53035                                  AS ad_table_id,
           pp_cost_collector.pp_cost_collector_id AS record_id,
           'N'::char(1)                           AS issotrx,
           pp_cost_collector.posted,
           pp_cost_collector.PostingError_Issue_ID,
           pp_cost_collector.processing,
           pp_cost_collector.processed,
           pp_cost_collector.docstatus
    FROM pp_cost_collector
    WHERE pp_cost_collector.posted <> 'Y'
      AND pp_cost_collector.docstatus <> 'VO'
      AND pp_cost_collector.processed = 'Y'
)
;



























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

































DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate(numeric)
;
DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate(numeric, char(1), numeric)
;

CREATE OR REPLACE FUNCTION "de_metas_acct".product_costs_recreate(p_M_Product_ID  numeric = NULL,
                                                                  p_ReorderDocs   char(1) = 'Y',
                                                                  p_M_Product_IDs numeric[] = NULL)
    RETURNS text
AS
$BODY$
DECLARE
    v_productIds numeric[];
    rowcount     integer;
    v_result     text := '';
BEGIN
    IF (p_M_Product_ID IS NOT NULL AND p_M_Product_ID > 0) THEN
        v_productIds := ARRAY [p_M_Product_ID];
        -- RAISE EXCEPTION 'Product shall be > 0 but it was %', p_M_Product_ID;
    ELSE
        v_productIds := p_M_Product_IDs;
    END IF;

    IF (v_productIds IS NULL OR array_length(v_productIds, 1) <= 0) THEN
        RAISE EXCEPTION
            'No products provided. p_M_Product_ID(=%) or p_M_Product_IDs(=%) shall be set',
            p_M_Product_ID, p_M_Product_IDs;
    END IF;

    v_result := v_result || array_length(v_productIds, 1) || ' products; ';

    DELETE
    FROM m_cost c
    WHERE c.m_product_id = ANY (v_productIds)
    -- AND NOT exists(SELECT 1 FROM m_costelement ce WHERE ce.m_costelement_id = c.m_costelement_id AND ce.costingmethod = 'S') -- exclude standard costing
    ;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' M_Cost(s) deleted; ';

    DELETE
    FROM m_costdetail
    WHERE m_product_id = ANY (v_productIds);
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' M_CostDetail(s) deleted; ';

    DELETE
    FROM pp_order_cost poc
    WHERE exists(
                  SELECT 1
                  FROM pp_order o
                  WHERE o.pp_order_id = poc.pp_order_id
                    AND o.m_product_id = ANY (v_productIds)
              );
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' PP_Order_Cost(s) deleted; ';


    SELECT count(1)
    INTO rowcount
    FROM (
             SELECT "de_metas_acct".fact_acct_unpost(t.tablename, t.record_id, 'Y')
             FROM (
                      SELECT DISTINCT tablename,
                                      record_id,
                                      m_product_id,
                                      dateacct,
                                      tablename_prio,
                                      least(record_id, reversal_id)
                      FROM "de_metas_acct".accountable_docs_and_lines_v
                      WHERE m_product_id = ANY (v_productIds)
                      ORDER BY m_product_id,
                               dateacct,
                               tablename_prio,
                               least(record_id, reversal_id),
                               record_id
                  ) t
         ) t;
    v_result := v_result || rowcount || ' document(s) unposted; ';

    IF (p_ReorderDocs = 'Y') THEN
        PERFORM "de_metas_acct".accounting_docs_to_repost_reorder();
        v_result := v_result || 'reordered enqueued docs';
    END IF;

    RETURN v_result;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100
;






























DROP FUNCTION IF EXISTS product_costs_recreate_allProducts_reorder ()
;
DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate_allProducts_reorder ()
;


CREATE FUNCTION "de_metas_acct".product_costs_recreate_allProducts_reorder() RETURNS text


    LANGUAGE plpgsql
AS
$$
DECLARE
    rowcount integer;
    v_result text := '';
BEGIN
    DELETE
    FROM m_cost c
    WHERE TRUE
    -- AND NOT exists(SELECT 1 FROM m_costelement ce WHERE ce.m_costelement_id = c.m_costelement_id AND ce.costingmethod = 'S') -- exclude standard costing
    ;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' M_Cost(s) deleted; ';
    RAISE NOTICE '% M_Cost(s) deleted', rowcount;


    DELETE
    FROM m_costdetail
    WHERE 1 = 1;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' M_CostDetail(s) deleted; ';
    RAISE NOTICE '% M_CostDetail(s) deleted; ', rowcount;

    DELETE
    FROM pp_order_cost poc
    WHERE 1 = 1;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' PP_Order_Cost(s) deleted; ';
    RAISE NOTICE '% PP_Order_Cost(s) deleted; ', rowcount;


    SELECT count(1)
    INTO rowcount
    FROM (
             SELECT "de_metas_acct".fact_acct_unpost(t.tablename, t.record_id, 'Y')
             FROM (
                      SELECT DISTINCT tablename,
                                      record_id,
                                      dateacct,
                                      tablename_prio,
                                      least(record_id, reversal_id)
                      FROM "de_metas_acct".accountable_docs_and_lines_v
                      WHERE M_Product_ID IS NOT NULL
                      ORDER BY dateacct,
                               tablename_prio,
                               least(record_id, reversal_id),
                               record_id
                  ) t
         ) t;
    v_result := v_result || rowcount || ' document(s) unposted; ';
    RAISE NOTICE '% document(s) unposted; ', rowcount;

    RETURN v_result;
END;
$$
;




