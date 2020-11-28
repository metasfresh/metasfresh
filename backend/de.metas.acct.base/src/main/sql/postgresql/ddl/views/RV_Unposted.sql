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
