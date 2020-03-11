drop function if exists report.fresh_pricelist_details_template_report(numeric, numeric, numeric, character varying, numeric);
CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_template_report(IN p_c_bpartner_id numeric, IN p_m_pricelist_version_id numeric, IN p_alt_pricelist_version_id numeric, IN p_ad_language character varying,
                                                                          IN p_c_bpartner_location_id numeric)
    RETURNS TABLE
            (
                prodvalue               text,
                customerproductnumber   text,
                productcategory         text,
                productname             text,
                attributes              text,
                itemproductname         text,
                qty                     numeric,
                uomsymbol               text,
                pricestd                numeric,
                m_product_price_id      integer,
                c_bpartner_id           numeric,
                m_hu_pi_item_product_id integer,
                uom_x12de355            text,
                c_bpartner_location_id  numeric,
                qtycuspertu             numeric,
                m_product_id            integer
            )
AS
$BODY$
--
SELECT plc.value                                                                 AS prodvalue,
       plc.customerproductnumber                                                 as customerproductnumber,
       plc.productcategory                                                       as productcategory,
       plc.productname                                                           as productname,
       plc.attributes                                                            as attributes,
       plc.itemproductname                                                       as itemproductname,
       -- if packing instructions exist, we will display the qty one as it refers to one TU (Transportation Unit).
       CASE WHEN plc.itemproductname IS NOT NULL THEN 1 ELSE plc.qtycuspertu END AS qty,
       plc.uomsymbol                                                             as uomsymbol,
       plc.pricestd                                                              as pricestd,
       plc.M_ProductPrice_ID                                                     as m_productprice_id,
       $1                                                                        as c_bpartner_id,
       plc.M_HU_PI_Item_Product_ID                                               as m_hu_pi_item_product_id,
       plc.UOM_X12DE355                                                          as uom_x12de355,
       $5                                                                        as c_bpartner_location_id,
       plc.qtycuspertu                                                           as qtycuspertu,
       plc.M_Product_ID                                                          as m_product_id
FROM report.fresh_PriceList_Details_Report($1, $2, $3, $4) plc;
--
$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;


-- 2020-02-18T13:19:51.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584657,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2020-02-18 15:19:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','Y','N','Y','N','Y','Y','@PREFIX@de/metas/reports/purchase_trace/report.xls',0,'PricelistExcelExporter','N','Y','SELECT * FROM de_metas_endcustomer_fresh_reports.trace_report(@AD_Org_ID/NULL@, @C_Period_St_ID@, @C_Period_End_ID@,@C_Activity_ID/NULL@, @C_BPartner_ID/NULL@, @M_Product_ID/NULL@, ''N'', @M_AttributeSetInstance_ID/NULL@, ''@ad_language@'');','JasperReportsSQL',TO_TIMESTAMP('2020-02-18 15:19:50','YYYY-MM-DD HH24:MI:SS'),100,'RV_PricelistExporter')
;

-- 2020-02-18T13:19:51.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584657 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-02-18T13:37:34.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/reports/pricelist/report.xls',Updated=TO_TIMESTAMP('2020-02-18 15:37:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584657
;

-- 2020-02-18T13:40:34.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@AD_Org_ID/NULL@, @C_Period_St_ID@, @C_Period_End_ID@,@C_Activity_ID/NULL@, @C_BPartner_ID/NULL@, @M_Product_ID/NULL@, ''N'', @M_AttributeSetInstance_ID/NULL@, ''@ad_language@'');',Updated=TO_TIMESTAMP('2020-02-18 15:40:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584657
;

-- 2020-02-18T13:41:06.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=584657
;

-- 2020-02-18T13:41:06.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=584657
;

-- 2020-02-18T13:41:33.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584658,'N','de.metas.pricing.process.M_Pricelist_Excel','N',TO_TIMESTAMP('2020-02-18 15:41:33','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','N','N','N','Y','Y','@PREFIX@de/metas/reports/pricelist/report.jasper','@PREFIX@de/metas/reports/pricelist/report_TabularView.jasper',0,'Pricelist Exporter','N','Y','Java',TO_TIMESTAMP('2020-02-18 15:41:33','YYYY-MM-DD HH24:MI:SS'),100,'RV_Fresh_PriceList_Exporter')
;

-- 2020-02-18T13:41:33.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584658 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-02-18T13:44:24.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=584658
;

-- 2020-02-18T13:44:24.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=584658
;

-- 2020-02-18T13:46:31.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584659,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2020-02-18 15:46:30','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','N','N','Y','N','Y','N','Y','Y','@PREFIX@de/metas/reports/pricelist/report.xls',0,'PriceList Exporter','N','Y','SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_Product_ID/NULL@, ''N'', @M_AttributeSetInstance_ID/NULL@, ''@ad_language@'');','JasperReportsSQL',TO_TIMESTAMP('2020-02-18 15:46:30','YYYY-MM-DD HH24:MI:SS'),100,'RV_Fresh_PriceList_Exporter')
;

-- 2020-02-18T13:46:31.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584659 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-02-18T13:50:42.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,584659,541732,19,'AD_Org_ID',TO_TIMESTAMP('2020-02-18 15:50:42','YYYY-MM-DD HH24:MI:SS'),100,'@AD_Org_ID@','Organisatorische Einheit des Mandanten','U',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','Sektion',10,TO_TIMESTAMP('2020-02-18 15:50:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T13:50:42.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541732 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-18T13:54:35.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,450,0,584659,541733,18,199,540243,'M_PriceList_Version_ID',TO_TIMESTAMP('2020-02-18 15:54:35','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT report.BPartner_PriceList_Version(@C_BPartner_ID@)@','Bezeichnet eine einzelne Version der Preisliste','U',0,'Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ','Y','N','Y','N','N','N','Version Preisliste',20,TO_TIMESTAMP('2020-02-18 15:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T13:54:35.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541733 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-18T13:59:47.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,584659,541734,30,'C_BPartner_ID',TO_TIMESTAMP('2020-02-18 15:59:47','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID@','Bezeichnet einen Geschäftspartner','D',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','N','N','Geschäftspartner','@C_BPartner_ID/0@>0',30,TO_TIMESTAMP('2020-02-18 15:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T13:59:47.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541734 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-18T14:02:32.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@M_PriceList_Version_ID/NULL@,  ''@ad_language@'');',Updated=TO_TIMESTAMP('2020-02-18 16:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-18T14:04:17.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584659,291,540793,TO_TIMESTAMP('2020-02-18 16:04:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2020-02-18 16:04:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2020-02-18T14:05:28.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2020-02-18 16:05:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541733
;

-- 2020-02-18T14:05:31.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2020-02-18 16:05:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541732
;

-- 2020-02-18T14:06:49.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT report.BPartner_PriceList_Version(@C_BPartner_ID@)',Updated=TO_TIMESTAMP('2020-02-18 16:06:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541733
;

-- 2020-02-18T15:38:57.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@M_PriceList_Version_ID/NULL@, ''@AD_Language@'');',Updated=TO_TIMESTAMP('2020-02-18 17:38:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-18T15:39:36.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@M_PriceList_Version_ID/NULL@, ''@#AD_Language@'');',Updated=TO_TIMESTAMP('2020-02-18 17:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-18T15:46:42.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='N', SeqNo=0,Updated=TO_TIMESTAMP('2020-02-18 17:46:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541734
;

-- 2020-02-18T15:54:39.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=542346, ColumnName='Alt_Pricelist_Version_ID', DefaultValue='@SQL=SELECT report.BPartner_Alt_Pricelist_Version(@C_BPartner_ID@)', Description=NULL, Help=NULL, Name='Vergleichs Preislistenversion',Updated=TO_TIMESTAMP('2020-02-18 17:54:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541733
;

-- 2020-02-18T15:54:50.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@M_Alt_PriceList_Version_ID/NULL@, ''@AD_Language@'');',Updated=TO_TIMESTAMP('2020-02-18 17:54:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-18T15:59:06.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=450, ColumnName='M_PriceList_Version_ID', DefaultValue='@SQL=SELECT report.BPartner_Pricelist_Version(@C_BPartner_ID@)', Description='Bezeichnet eine einzelne Version der Preisliste', EntityType='U', Help='Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ', Name='Version Preisliste',Updated=TO_TIMESTAMP('2020-02-18 17:59:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541733
;

-- 2020-02-18T16:00:43.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542346,0,584659,541742,18,199,'Alt_Pricelist_Version_ID',TO_TIMESTAMP('2020-02-18 18:00:42','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT report.BPartner_PriceList_Version(@C_BPartner_ID@)','de.metas.fresh',0,'Y','N','Y','N','N','N','Vergleichs Preislistenversion',30,TO_TIMESTAMP('2020-02-18 18:00:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T16:00:43.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541742 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-18T16:02:33.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2159,0,584659,541743,18,106,'AD_Language_ID',TO_TIMESTAMP('2020-02-18 18:02:33','YYYY-MM-DD HH24:MI:SS'),100,'''@#AD_Language@''','U',0,'Y','N','Y','N','N','N','Sprache',40,TO_TIMESTAMP('2020-02-18 18:02:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T16:02:33.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541743 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-18T16:04:58.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@M_Alt_PriceList_Version_ID/NULL@, ''@AD_Language_ID@'');',Updated=TO_TIMESTAMP('2020-02-18 18:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-18T16:05:14.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, ''@AD_Language_ID@'');',Updated=TO_TIMESTAMP('2020-02-18 18:05:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-18T16:06:53.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y',Updated=TO_TIMESTAMP('2020-02-18 18:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541734
;

-- 2020-02-18T16:07:12.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='N', SeqNo=0,Updated=TO_TIMESTAMP('2020-02-18 18:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541732
;

-- 2020-02-18T16:07:12.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=10,Updated=TO_TIMESTAMP('2020-02-18 18:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541734
;
-- 2020-02-19T08:15:27.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, @AD_Language_ID@);',Updated=TO_TIMESTAMP('2020-02-19 10:15:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T08:16:57.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, ''@AD_Language_ID@'');',Updated=TO_TIMESTAMP('2020-02-19 10:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T08:17:08.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2020-02-19 10:17:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541743
;

-- 2020-02-19T08:17:51.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2020-02-19 10:17:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541743
;

-- 2020-02-19T08:17:56.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='',Updated=TO_TIMESTAMP('2020-02-19 10:17:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541743
;

-- 2020-02-19T08:21:12.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, ''@AD_Language_ID/en_GB@'');',Updated=TO_TIMESTAMP('2020-02-19 10:21:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T08:49:47.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, ''@ad_language@'');',Updated=TO_TIMESTAMP('2020-02-19 10:49:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T08:52:13.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_PriceList_Details_Report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, ''@AD_Language_ID@'');',Updated=TO_TIMESTAMP('2020-02-19 10:52:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T08:54:14.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_endcustomer_fresh_reports.fresh_pricelist_details_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, ''@AD_Language_ID@'');',Updated=TO_TIMESTAMP('2020-02-19 10:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T09:05:24.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, ''@AD_Language_ID@'');',Updated=TO_TIMESTAMP('2020-02-19 11:05:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T12:29:16.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, ''@AD_Language_ID@'');',Updated=TO_TIMESTAMP('2020-02-19 14:29:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T13:21:02.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,189,0,584659,541746,19,159,'C_BPartner_Location_ID',TO_TIMESTAMP('2020-02-19 15:21:02','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','Standort',50,TO_TIMESTAMP('2020-02-19 15:21:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-19T13:21:02.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541746 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-19T13:22:19.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=131, DefaultValue='@Default_Bill_Location_ID@',Updated=TO_TIMESTAMP('2020-02-19 15:22:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541746
;

-- 2020-02-19T13:27:23.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, ''@AD_Language_ID@'', @C_BPartner_Location_ID@);',Updated=TO_TIMESTAMP('2020-02-19 15:27:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T13:27:33.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@, ''@AD_Language_ID@'', @C_BPartner_Location_ID/NULL@);',Updated=TO_TIMESTAMP('2020-02-19 15:27:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T13:29:30.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@,''@#AD_Language@'', @C_BPartner_Location_ID/NULL@);',Updated=TO_TIMESTAMP('2020-02-19 15:29:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-02-19T13:29:39.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2020-02-19 15:29:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541743
;
-- 2020-03-10T09:38:00.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preisliste in Excel-Tabelle exportieren',Updated=TO_TIMESTAMP('2020-03-10 11:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584659
;

-- 2020-03-10T09:38:13.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preisliste in Excel-Tabelle exportieren',Updated=TO_TIMESTAMP('2020-03-10 11:38:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584659
;

-- 2020-03-10T09:38:26.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Export Pricelist as Excel',Updated=TO_TIMESTAMP('2020-03-10 11:38:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584659
;

-- 2020-03-10T09:38:30.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preisliste in Excel-Tabelle exportieren',Updated=TO_TIMESTAMP('2020-03-10 11:38:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584659
;

-- 2020-03-10T09:38:52.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Preisliste in Excel-Tabelle exportieren',Updated=TO_TIMESTAMP('2020-03-10 11:38:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-03-10T12:38:12.274Z
-- URL zum Konzept
UPDATE AD_Process SET AllowProcessReRun='N', Classname='de.metas.impexp.excel.process.ExportToExcelProcess', IsTranslateExcelHeaders='N', IsUseBPartnerLanguage='N', JasperReport='', SQLStatement='', Type='Excel',Updated=TO_TIMESTAMP('2020-03-10 14:38:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-03-10T13:00:34.972Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2020-03-10 15:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541733
;

-- 2020-03-10T13:01:44.535Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@,''@#AD_Language@'');',Updated=TO_TIMESTAMP('2020-03-10 15:01:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2020-03-10T13:03:13.067Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsActive='N',Updated=TO_TIMESTAMP('2020-03-10 15:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541746
;

-- 2020-03-10T13:06:59.525Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsCentrallyMaintained='Y',Updated=TO_TIMESTAMP('2020-03-10 15:06:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541733
;

-- 2020-03-11T11:41:11.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y',Updated=TO_TIMESTAMP('2020-03-11 13:41:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541746
;

-- 2020-03-11T11:41:41.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,@Alt_Pricelist_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/NULL@);',Updated=TO_TIMESTAMP('2020-03-11 13:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;
