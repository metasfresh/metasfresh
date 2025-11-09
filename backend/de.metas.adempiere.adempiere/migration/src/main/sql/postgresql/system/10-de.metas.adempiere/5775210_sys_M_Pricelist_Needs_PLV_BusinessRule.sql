-- Run mode: WEBUI

-- Name: M_Pricelist has PLV
-- 2025-10-30T15:55:34.449Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540755,TO_TIMESTAMP('2025-10-30 15:55:34.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','M_Pricelist has PLV','S',TO_TIMESTAMP('2025-10-30 15:55:34.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_Pricelist has PLV
-- 2025-10-30T15:55:42.443Z
UPDATE AD_Val_Rule SET EntityType='D',Updated=TO_TIMESTAMP('2025-10-30 15:55:42.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540755
;

-- Name: M_Pricelist has PLV
-- 2025-10-30T15:56:45.242Z
UPDATE AD_Val_Rule SET 
Code='exists (select 1 from m_pricelist_Version plv where plv.m_Pricelist_ID = M_Pricelist.M_Pricelist_ID and plv.isActive = ''Y'')',
Updated=TO_TIMESTAMP('2025-10-30 15:56:45.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540755
;

-- Value: M_Pricelist_MustHave_PLV
-- 2025-10-30T15:57:57.988Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545603,0,TO_TIMESTAMP('2025-10-30 15:57:57.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','In order to use this pricelist for product prices, in an order etc., please create a pricelist version as well.','I',TO_TIMESTAMP('2025-10-30 15:57:57.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_Pricelist_MustHave_PLV')
;

-- 2025-10-30T15:57:57.993Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545603 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: M_Pricelist_MustHave_PLV
-- 2025-10-30T15:58:03.313Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2025-10-30 15:58:03.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545603
;

-- Value: M_Pricelist_MustHave_PLV
-- 2025-10-30T15:58:09.539Z
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2025-10-30 15:58:09.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545603
;

-- Value: M_Pricelist_MustHave_PLV
-- 2025-10-30T15:58:13.710Z
UPDATE AD_Message SET MsgType='I',Updated=TO_TIMESTAMP('2025-10-30 15:58:13.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545603
;

-- Value: M_Pricelist_MustHave_PLV
-- 2025-10-30T15:58:53.939Z
UPDATE AD_Message_Trl SET MsgText='Um diese Preisliste für das Anlegen von Produktpreisen, in einem Auftrag usw. benutzen zu können, lege bitte noch eine Preislisten Version an.',Updated=TO_TIMESTAMP('2025-10-30 15:58:53.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545603
;

-- 2025-10-30T15:58:53.942Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: M_Pricelist_MustHave_PLV
-- 2025-10-30T15:58:58.716Z
UPDATE AD_Message_Trl SET MsgText='Um diese Preisliste für das Anlegen von Produktpreisen, in einem Auftrag usw. benutzen zu können, lege bitte noch eine Preislisten Version an.',Updated=TO_TIMESTAMP('2025-10-30 15:58:58.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545603
;

-- 2025-10-30T15:58:58.717Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-30T15:59:32.208Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsCreateWarningOnTarget,IsDebug,Name,Severity,Updated,UpdatedBy,Validation_Rule_ID,Warning_Message_ID) VALUES (540037,0,0,255,TO_TIMESTAMP('2025-10-30 15:59:32.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Pricelist Needs a Pricelist Version','N',TO_TIMESTAMP('2025-10-30 15:59:32.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540755,545603)
;

-- 2025-10-30T16:00:10.352Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540037,540039,0,0,TO_TIMESTAMP('2025-10-30 16:00:10.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y',255,'M_Pricelist_ID',TO_TIMESTAMP('2025-10-30 16:00:10.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-30T16:00:56.413Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540037,540040,0,0,TO_TIMESTAMP('2025-10-30 16:00:56.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','Y','Y',295,'M_Pricelist_ID',TO_TIMESTAMP('2025-10-30 16:00:56.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

