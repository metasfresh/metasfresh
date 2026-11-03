-- Run mode: SWING_CLIENT

-- Value: C_Invoice_Proforma_Allocate_Order
-- Classname: de.metas.invoice.proforma.process.C_Invoice_Proforma_Allocate_Order
-- 2026-04-13T06:17:36.112Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,CSVFieldQuote,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsIncludeCSVHeaderRow,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,ProcedureName,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585609,'Y','de.metas.invoice.proforma.process.C_Invoice_Proforma_Allocate_Order','N',TO_TIMESTAMP('2026-04-13 06:17:35.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'"','de.metas.invoice','Y','N','N','N','Y','Y','N','N','N','N','Y','N','Y',0,'Bestellung zuordnen','json','','N','N','xls','Java',TO_TIMESTAMP('2026-04-13 06:17:35.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Invoice_Proforma_Allocate_Order')
;

-- 2026-04-13T06:17:36.123Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585609 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Invoice_Proforma_Allocate_Order(de.metas.invoice.proforma.process.C_Invoice_Proforma_Allocate_Order)
-- 2026-04-13T06:17:54.907Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Allocate Purchase Order',Updated=TO_TIMESTAMP('2026-04-13 06:17:54.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585609
;

-- 2026-04-13T06:17:54.910Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_Invoice_Proforma_Allocate_Order(de.metas.invoice.proforma.process.C_Invoice_Proforma_Allocate_Order)
-- 2026-04-13T06:17:55.363Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-13 06:17:55.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585609
;

-- Process: C_Invoice_Proforma_Allocate_Order(de.metas.invoice.proforma.process.C_Invoice_Proforma_Allocate_Order)
-- 2026-04-13T06:17:56.485Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-13 06:17:56.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585609
;

-- Name: Proforma allocatable Purchase Order
-- 2026-04-13T06:26:42.687Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540776, 'C_Order.IsSOTrx=''N''
and (
    C_Order.Bill_BPartner_ID = (select C_BPartner_ID from C_Invoice where C_Invoice_ID = @C_Invoice_ID/-1@)
    or (C_Order.Bill_BPartner_ID is null and C_Order.C_BPartner_ID = (select C_BPartner_ID from C_Invoice where C_Invoice_ID = @C_Invoice_ID/-1@))
)
and C_Order.DocStatus in (''CO'', ''CL'')
and exists (
    select 1
    from C_PaymentTerm pt
    inner join C_PaymentTerm_Break ptb on pt.C_PaymentTerm_ID = ptb.C_PaymentTerm_ID
    where pt.C_PaymentTerm_ID = C_Order.C_PaymentTerm_ID
      and ptb.ReferenceDateType = ''LC''
      and ptb.IsActive = ''Y''
)
and not exists (
    select 1
    from C_Proforma_Order_Alloc poa
    where (poa.C_Order_ID = C_Order.C_Order_ID
      or poa.C_Invoice_ID = @C_Invoice_ID@)
      and poa.isActive = ''Y''
)',TO_TIMESTAMP('2026-04-13 06:26:42.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Proforma allocatable Purchase Order','S',TO_TIMESTAMP('2026-04-13 06:26:42.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Process: C_Invoice_Proforma_Allocate_Order(de.metas.invoice.proforma.process.C_Invoice_Proforma_Allocate_Order)
-- ParameterName: C_Order_ID
-- 2026-04-13T06:27:25.136Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,0,585609,543178,19,540776,'C_Order_ID',TO_TIMESTAMP('2026-04-13 06:27:25.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellung','de.metas.invoice',0,'','Y','N','Y','N','N','N','Bestellung',10,'N',TO_TIMESTAMP('2026-04-13 06:27:25.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-13T06:27:25.140Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543178 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-04-13T06:29:06.581Z
UPDATE AD_Process_Para_Trl SET Description='Purchase Order', Name='Purchase Order',Updated=TO_TIMESTAMP('2026-04-13 06:29:06.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=543178
;

-- 2026-04-13T06:29:06.589Z
UPDATE AD_Process_Para base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Para_Trl trl  WHERE trl.AD_Process_Para_ID=base.AD_Process_Para_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-13T06:29:13.147Z
UPDATE AD_Process_Para_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-13 06:29:13.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=543178
;

-- 2026-04-13T06:29:13.806Z
UPDATE AD_Process_Para_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-13 06:29:13.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=543178
;

-- 2026-04-13T06:29:15.002Z
UPDATE AD_Process_Para_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-13 06:29:15.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=543178
;

-- Process: C_Invoice_Proforma_Allocate_Order(de.metas.invoice.proforma.process.C_Invoice_Proforma_Allocate_Order)
-- ParameterName: C_Order_ID
-- 2026-04-13T11:57:49.583Z
UPDATE AD_Process_Para SET AD_Reference_ID=30, AD_Reference_Value_ID=290,Updated=TO_TIMESTAMP('2026-04-13 11:57:49.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543178
;

-- Process: C_Invoice_Proforma_Allocate_Order(de.metas.invoice.proforma.process.C_Invoice_Proforma_Allocate_Order)
-- Table: C_Invoice
-- EntityType: de.metas.invoice
-- 2026-04-13T07:42:15.742Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585609,318,541637,TO_TIMESTAMP('2026-04-13 07:42:15.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoice','Y',TO_TIMESTAMP('2026-04-13 07:42:15.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N')
;
