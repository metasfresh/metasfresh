-- Value: Delivery Planning Export (Excel)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@/de/metas/docs/deliveryplanning/deliveryplanning.xls
-- 2023-02-01T16:18:42.371Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585207,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2023-02-01 18:18:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','Y','Y','N','N','Y','Y','N','Y','@PREFIX@/de/metas/docs/deliveryplanning/deliveryplanning.xls',0,'Delivery Planning Export Excel','json','N','N','xls','
SELECT div.documentno,
    COALESCE(deliveryL.bpartnername, deliveryBP.name) || E''\n'' || deliveryL.address AS shipToLocation_name,
    p.name                                                                          AS productName,
    p.value                                                                         AS productCode,
    w.name                                                                          AS warehouseName,
    fromC.name                                                                      AS originCountry,
    div.deliverydate                                         AS deliveryDate,
    m_delivery_planning.batch,
    m_delivery_planning.releaseno,
    m_delivery_planning.plannedloadingdate                  AS plannedloadingdate,
    m_delivery_planning.actualloadingdate                 AS actualloadingdate,
    m_delivery_planning.plannedloadedquantity,
    m_delivery_planning.actualloadqty,
    m_delivery_planning.planneddeliverydate                AS planneddeliverydate,
    m_delivery_planning.actualdeliverydate                   AS actualdeliverydate,
    m_delivery_planning.planneddischargequantity,
    m_delivery_planning.actualdischargequantity
    FROM m_delivery_planning_delivery_instructions_v div
    INNER JOIN m_delivery_planning ON m_delivery_planning.m_delivery_planning_id = div.m_delivery_planning_id
    INNER JOIN c_bpartner_location deliveryL ON div.c_bpartner_location_delivery_id = deliveryL.c_bpartner_location_id
    INNER JOIN c_bpartner_location loadingL ON div.c_bpartner_location_loading_id = loadingL.c_bpartner_location_id
    INNER JOIN c_bpartner deliveryBP ON deliveryL.c_bpartner_id = deliveryBP.c_bpartner_id
    LEFT JOIN m_product p ON m_delivery_planning.m_product_id = p.m_product_id
    LEFT JOIN m_warehouse W ON m_delivery_planning.m_warehouse_id = W.m_warehouse_id
    LEFT JOIN c_country fromC ON m_delivery_planning.c_origincountry_id = fromC.c_country_id WHERE @SELECTION_WHERECLAUSE/false@
;','JasperReportsSQL',TO_TIMESTAMP('2023-02-01 18:18:42','YYYY-MM-DD HH24:MI:SS'),100,'Delivery Planning Export (Excel)')
;

-- 2023-02-01T16:18:42.373Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585207 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: Export Delivery Planning Lines (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@/de/metas/docs/deliveryplanning/deliveryplanning.xls
-- 2023-02-01T16:20:23.386Z
UPDATE AD_Process SET Name='Export Delivery Planning Lines', Value='Export Delivery Planning Lines (Jasper)',Updated=TO_TIMESTAMP('2023-02-01 18:20:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585207
;

-- 2023-02-01T16:20:23.387Z
UPDATE AD_Process_Trl trl SET Name='Export Delivery Planning Lines' WHERE AD_Process_ID=585207 AND AD_Language='de_DE'
;

-- Process: Export Delivery Planning Lines (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-02-01T16:20:42.250Z
UPDATE AD_Process_Trl SET Name='Export Delivery Planning Lines',Updated=TO_TIMESTAMP('2023-02-01 18:20:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585207
;

-- Process: Export Delivery Planning Lines (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-02-01T16:22:30.211Z
UPDATE AD_Process_Trl SET Name='Export Lieferplanungszeilen',Updated=TO_TIMESTAMP('2023-02-01 18:22:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585207
;

-- Process: Export Delivery Planning Lines (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-02-01T16:22:32.058Z
UPDATE AD_Process_Trl SET Name='Export Lieferplanungszeilen',Updated=TO_TIMESTAMP('2023-02-01 18:22:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585207
;

-- 2023-02-01T16:22:32.059Z
UPDATE AD_Process SET Name='Export Lieferplanungszeilen' WHERE AD_Process_ID=585207
;

-- Process: Export Delivery Planning Lines (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- 2023-02-01T16:22:33.722Z
UPDATE AD_Process_Trl SET Name='Export Lieferplanungszeilen',Updated=TO_TIMESTAMP('2023-02-01 18:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585207
;

-- Process: Export Delivery Planning Lines (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: M_Delivery_Planning
-- EntityType: D
-- 2023-02-01T18:07:29.419Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585207,542259,541348,TO_TIMESTAMP('2023-02-01 20:07:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-02-01 20:07:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Value: Export Delivery Planning Lines (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@/de/metas/docs/deliveryplanning/deliveryplanning.xls
-- 2023-02-03T11:46:40.243Z
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2023-02-03 13:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585207
;
