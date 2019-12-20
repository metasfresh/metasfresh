DROP FUNCTION IF EXISTS shipmentDispositionExcelDownload(TIMESTAMP With Time Zone, numeric);

CREATE OR REPLACE FUNCTION shipmentDispositionExcelDownload(
    IN M_ShipmentSchedule_Deliverydate TIMESTAMP With Time Zone,
    IN M_BPartner_ID numeric )

    RETURNS TABLE
            (
                PreparationDate_Effective TIMESTAMP With Time Zone,
                C_Bpartner_ID character varying,
                OrderDocumentNo character varying,
                OrderLine int,
                ProductName character varying ,
                ProductValue character varying,
                QtyOrdered numeric,
                QtyDelivered numeric
            )
AS

$BODY$

SELECT
    COALESCE(sps.preparationdate_override, sps.preparationdate) as preparationdate_effective,
    CONCAT(bp.value, ' ', bp.name) as C_Bpartner_ID,
    o.documentno as orderdocumentno,
    col.line::int,
    pt.name,
    pt.value,
    sps.qtyordered_tu,
    sps.qtydelivered

FROM M_ShipmentSchedule sps
         LEFT OUTER JOIN c_bpartner bp on bp.c_bpartner_id = sps.c_bpartner_id
         LEFT OUTER JOIN c_order o on o.c_order_id = sps.c_order_id
         LEFT OUTER JOIN m_product pt on pt.m_product_id = sps.m_product_id
         LEFT OUTER JOIN c_orderline col on col.c_orderline_id = sps.c_orderline_id
WHERE M_ShipmentSchedule_Deliverydate >= COALESCE(sps.preparationdate_override, sps.preparationdate)
  AND CASE WHEN M_BPartner_ID > 0 THEN bp.c_bpartner_id = M_BPartner_ID ELSE 1=1 END
  AND sps.processed = 'N'

$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;

-- 2019-12-20T12:29:14.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541238,'Y','de.metas.impexp.excel.process.ExportToExcelProcess','N',TO_TIMESTAMP('2019-12-20 14:29:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y',0,'ShipmentDispositionExcelDownload','Y','N','Excel',TO_TIMESTAMP('2019-12-20 14:29:14','YYYY-MM-DD HH24:MI:SS'),100,'ShipmentDispositionExcelDownload')
;

-- 2019-12-20T12:29:14.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541238 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-12-20T12:30:50.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541376,0,541238,541658,16,'DeliveryDate',TO_TIMESTAMP('2019-12-20 14:30:50','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@','D',0,'Y','N','Y','N','N','N','Lieferdatum',10,TO_TIMESTAMP('2019-12-20 14:30:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-20T12:30:50.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541658 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-12-20T12:33:14.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,541238,541659,30,138,'C_BPartner_ID',TO_TIMESTAMP('2019-12-20 14:33:14','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID/-1@','Bezeichnet einen GeschÃ¤ftspartner','U',0,'Ein GeschÃ¤ftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','N','N','GeschÃ¤ftspartner',20,TO_TIMESTAMP('2019-12-20 14:33:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-20T12:33:14.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541659 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-12-20T12:33:59.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541238,500221,540772,500221,TO_TIMESTAMP('2019-12-20 14:33:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2019-12-20 14:33:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N')
;

-- 2019-12-20T12:34:42.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from shipmentdispositionexceldownload(''@DeliveryDate@'', @C_BPartner_ID/-1@)',Updated=TO_TIMESTAMP('2019-12-20 14:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541238
;

-- 2019-12-20T14:51:34.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Document No', PrintName='Document No',Updated=TO_TIMESTAMP('2019-12-20 16:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=290 AND AD_Language='en_US'
;

-- 2019-12-20T14:51:34.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(290,'en_US')
;

-- 2019-12-20T15:05:11.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Export Overdue Orders',Updated=TO_TIMESTAMP('2019-12-20 17:05:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541238
;

-- 2019-12-20T15:06:10.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='ÃœberfÃ¤llige Auftragpositionen Exportieren',Updated=TO_TIMESTAMP('2019-12-20 17:06:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541238
;

-- 2019-12-20T15:06:19.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-20 17:06:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541238
;

-- 2019-12-20T15:06:53.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-20 17:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=541238
;

-- 2019-12-20T15:07:09.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='ÃœberfÃ¤llige Auftragpositionen Exportieren', Value='ÃœberfÃ¤llige Auftragpositionen Exportieren',Updated=TO_TIMESTAMP('2019-12-20 17:07:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541238
;

-- 2019-12-20T15:07:20.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='ÃœberfÃ¤llige Auftragpositionen Exportieren',Updated=TO_TIMESTAMP('2019-12-20 17:07:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541238
;

-- 2019-12-20T15:07:41.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Export Overdue Orders',Updated=TO_TIMESTAMP('2019-12-20 17:07:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=541238
;

-- 2019-12-20T15:09:01.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=542828, ColumnName='PreparationDate_Effective', Name='Bereitstellungsdatum eff.',Updated=TO_TIMESTAMP('2019-12-20 17:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541658
;


