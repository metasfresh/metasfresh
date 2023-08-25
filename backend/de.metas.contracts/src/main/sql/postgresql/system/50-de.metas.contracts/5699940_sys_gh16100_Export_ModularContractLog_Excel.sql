-- Value: ModCntr_Log_Exporter
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/reports/modcntrlog/report.xls
-- 2023-08-23T18:26:44.075621Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585309,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2023-08-23 21:26:43.88','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.fresh','Y','N','N','Y','Y','N','N','N','Y','Y','N','N','@PREFIX@de/metas/reports/modcntrlog/report.xls',0,'Log für modulare Verträge exportieren','json','N','N','xls','SELECT modcntr_log.modcntr_log_id            AS contractmodulelog,
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
WHERE @SELECTION_WHERECLAUSE/false@','JasperReportsSQL',TO_TIMESTAMP('2023-08-23 21:26:43.88','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Log_Exporter')
;

-- 2023-08-23T18:26:44.095620400Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585309 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ModCntr_Log_Exporter(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: ModCntr_Log
-- EntityType: D
-- 2023-08-23T18:27:14.976620100Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585309,542338,541409,TO_TIMESTAMP('2023-08-23 21:27:14.79','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2023-08-23 21:27:14.79','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: ModCntr_Log_Exporter(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-08-24T18:47:51.544413300Z
UPDATE AD_Process_Trl SET Name='Export Modular Contract Log',Updated=TO_TIMESTAMP('2023-08-24 21:47:51.543','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585309
;
