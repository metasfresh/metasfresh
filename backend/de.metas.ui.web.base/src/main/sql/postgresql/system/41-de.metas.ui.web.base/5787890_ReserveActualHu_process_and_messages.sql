-- Run mode: SWING_CLIENT

-- Value: WEBUI_C_OrderLineSO_Make_HUReservation
-- Classname: de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Make_HUReservation
-- 2026-02-12T09:59:57.788Z
UPDATE AD_Process SET Name='Menge für Auftragszeile reservieren',Updated=TO_TIMESTAMP('2026-02-12 09:59:57.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540983
;

-- 2026-02-12T09:59:57.792Z
UPDATE AD_Process_Trl trl SET Name='Menge für Auftragszeile reservieren' WHERE AD_Process_ID=540983 AND AD_Language='de_DE'
;

-- Process: WEBUI_C_OrderLineSO_Make_HUReservation(de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Make_HUReservation)
-- 2026-02-12T10:00:05.941Z
UPDATE AD_Process_Trl SET Name='Menge für Auftragszeile reservieren',Updated=TO_TIMESTAMP('2026-02-12 10:00:05.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=540983
;

-- 2026-02-12T10:00:05.943Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: WEBUI_C_OrderLineSO_Make_HUReservation_ActualHUs
-- Classname: de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Make_HUReservation_ActualHUs
-- 2026-02-12T10:00:48.952Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585569,'Y','de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Make_HUReservation_ActualHUs','N',TO_TIMESTAMP('2026-02-12 10:00:48.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'HU für Auftragszeile reservieren','json','N','N','xls','Java',TO_TIMESTAMP('2026-02-12 10:00:48.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'WEBUI_C_OrderLineSO_Make_HUReservation_ActualHUs')
;

-- 2026-02-12T10:00:48.953Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585569 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: WEBUI_C_OrderLineSO_Make_HUReservation_ActualHUs(de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Make_HUReservation_ActualHUs)
-- 2026-02-12T10:01:07.623Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Reserve HU for Order Line',Updated=TO_TIMESTAMP('2026-02-12 10:01:07.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585569
;

-- 2026-02-12T10:01:07.624Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- SysConfig Name: de.metas.ui.web.order.sales.hu.reservation.HuReservation.ActualHUs.Default
-- SysConfig Value: N
-- 2026-02-12T18:37:17.699Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541794,'S',TO_TIMESTAMP('2026-02-12 18:37:17.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'If set to true, the default process shown in the order `Reserve` process is the one that reserves the whole HU. If set to true, the older process that reserves quantity from the selected HUs is shown as default.','D','Y','de.metas.ui.web.order.sales.hu.reservation.HuReservation.ActualHUs.Default',TO_TIMESTAMP('2026-02-12 18:37:17.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- Value: HUContainsMoreQuantityThanNeeded
-- 2026-02-12T18:42:42.013Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545627,0,TO_TIMESTAMP('2026-02-12 18:42:41.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','HU enthält mehr Menge = {0} als benötigt = {1}','E',TO_TIMESTAMP('2026-02-12 18:42:41.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HUContainsMoreQuantityThanNeeded')
;

-- 2026-02-12T18:42:42.016Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545627 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: HUContainsMoreQuantityThanNeeded
-- 2026-02-12T18:43:14.591Z
UPDATE AD_Message_Trl SET MsgText='HU contains more quantity = {} than needed = {}',Updated=TO_TIMESTAMP('2026-02-12 18:43:14.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545627
;

-- 2026-02-12T18:43:14.592Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: HUContainsMoreQuantityThanNeeded
-- 2026-02-12T18:43:23.614Z
UPDATE AD_Message_Trl SET MsgText='HU enthält mehr Menge = {} als benötigt = {}',Updated=TO_TIMESTAMP('2026-02-12 18:43:23.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545627
;

-- 2026-02-12T18:43:23.615Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: HUContainsMoreQuantityThanNeeded
-- 2026-02-12T18:43:24.732Z
UPDATE AD_Message_Trl SET MsgText='HU enthält mehr Menge = {} als benötigt = {}',Updated=TO_TIMESTAMP('2026-02-12 18:43:24.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545627
;

-- 2026-02-12T18:43:24.733Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: HUContainsMoreQuantityThanNeeded
-- 2026-02-12T18:43:26.638Z
UPDATE AD_Message_Trl SET MsgText='HU enthält mehr Menge = {} als benötigt = {}',Updated=TO_TIMESTAMP('2026-02-12 18:43:26.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545627
;

-- 2026-02-12T18:43:26.639Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

