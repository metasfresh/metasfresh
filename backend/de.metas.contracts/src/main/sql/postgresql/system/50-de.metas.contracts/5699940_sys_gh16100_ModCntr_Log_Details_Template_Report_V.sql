DROP VIEW IF EXISTS ModCntr_Log_Details_Template_Report_V
;

CREATE OR REPLACE VIEW ModCntr_Log_Details_Template_Report_V AS
SELECT modcntr_log.modcntr_log_id            AS contractmodulelog,
       modcntr_log.ad_table_id               AS tableid,
       modcntr_log.record_id                 AS recordid,
       modcntr_log.modcntr_log_documenttype  AS documenttype,
       modcntr_log.issotrx                   AS sotrx,
       contract.documentno                   AS contractdocumentnumber,
       product.value                         AS productvalue,
       modcntr_log.qty                       AS qty,
       uom.uomsymbol                         AS uom,
       modcntr_log.amount                    AS amount,
       currency.iso_code                     AS currencycode,
       year.fiscalyear                       AS fiscalyear,
       module.name                           AS contractmodulevalue,
       producerBPartner.c_bpartner_id        AS businesspartner,
       invoiceBPartner.c_bpartner_id         AS invoicepartner,
       collectionPointBPartner.c_bpartner_id AS collectionpoint,
       modcntr_log.datetrx                   AS transactiondate,
       warehouse.name                        AS warehouse,
       modcntr_log.processed                 AS processed
FROM modcntr_log
         INNER JOIN c_year year ON year.c_year_id = modcntr_log.harvesting_year_id
         LEFT JOIN c_flatrate_term contract ON contract.c_flatrate_term_id = modcntr_log.c_flatrate_term_id
         LEFT JOIN m_product product ON product.m_product_id = modcntr_log.m_product_id
         LEFT JOIN c_uom uom ON uom.c_uom_id = modcntr_log.c_uom_id
         LEFT JOIN c_currency currency ON currency.c_currency_id = modcntr_log.c_currency_id
         LEFT JOIN modcntr_module module ON module.modcntr_type_id = modcntr_log.modcntr_type_id
         LEFT JOIN c_bpartner producerBPartner ON producerBPartner.c_bpartner_id = modcntr_log.producer_bpartner_id
         LEFT JOIN c_bpartner invoiceBPartner ON invoiceBPartner.c_bpartner_id = modcntr_log.bill_bpartner_id
         LEFT JOIN c_bpartner collectionPointBPartner ON collectionPointBPartner.c_bpartner_id = modcntr_log.collectionpoint_bpartner_id
         LEFT JOIN m_warehouse warehouse ON warehouse.m_warehouse_id = modcntr_log.m_warehouse_id
;
