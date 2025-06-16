-- Run mode: WEBUI

-- Name: C_Conversion_Rate_Rule_Limits
-- 2025-06-16T16:31:56.993Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540734,TO_TIMESTAMP('2025-06-16 16:31:56.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','C_Conversion_Rate_Rule_Limits','S',TO_TIMESTAMP('2025-06-16 16:31:56.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: C_Conversion_Rate_Rule_Limits
-- 2025-06-16T16:31:59.943Z
UPDATE AD_Val_Rule SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-16 16:31:59.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540734
;

-- Name: C_Conversion_Rate_Rule_Limits
-- 2025-06-16T16:37:36.207Z
UPDATE AD_Val_Rule SET Code='NOT EXISTS
          (SELECT 1
           FROM C_ConversionRate_Rule crr
           WHERE crr.c_currency_id = C_Conversion_Rate.c_currency_id
             AND crr.c_currency_to_id = C_Conversion_Rate.c_currency_id_to
             AND crr.isActive = ''Y''
             AND ((crr.multiplyrate_min IS NOT NULL
             AND crr.multiplyrate_min > C_Conversion_Rate.MultiplyRate)
OR
             (crr.multiplyrate_max IS NOT NULL
             AND crr.multiplyrate_max < C_Conversion_Rate.MultiplyRate))',Updated=TO_TIMESTAMP('2025-06-16 16:37:36.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540734
;

-- Value: C
-- 2025-06-16T16:38:37.574Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545562,0,TO_TIMESTAMP('2025-06-16 16:38:37.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Conversion rate is outside the allowed bounds. {}','I',TO_TIMESTAMP('2025-06-16 16:38:37.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C')
;

-- 2025-06-16T16:38:37.576Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545562 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_Conversion_Rate_OutOfBounds
-- 2025-06-16T16:38:54.718Z
UPDATE AD_Message SET Value='C_Conversion_Rate_OutOfBounds',Updated=TO_TIMESTAMP('2025-06-16 16:38:54.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545562
;

-- Value: C_Conversion_Rate_OutOfBounds
-- 2025-06-16T16:38:59.520Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-16 16:38:59.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545562
;

-- 2025-06-16T16:39:09.935Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsCreateWarningOnTarget,IsDebug,Name,Severity,Updated,UpdatedBy,Validation_Rule_ID,Warning_Message_ID) VALUES (540033,0,0,140,TO_TIMESTAMP('2025-06-16 16:39:09.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','C_ConversionRate_Rule Limits','E',TO_TIMESTAMP('2025-06-16 16:39:09.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540734,545562)
;

-- 2025-06-16T16:39:35.591Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540033,540037,0,0,TO_TIMESTAMP('2025-06-16 16:39:35.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y',140,'C_Conversion_Rate_ID',TO_TIMESTAMP('2025-06-16 16:39:35.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Value: C_Conversion_Rate_OutOfBounds
-- 2025-06-16T16:43:20.433Z
UPDATE AD_Message_Trl SET MsgText='Der Währungskurs liegt außerhalb der erlaubten Grenzen. {}',Updated=TO_TIMESTAMP('2025-06-16 16:43:20.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545562
;

-- 2025-06-16T16:43:20.435Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_Conversion_Rate_OutOfBounds
-- 2025-06-16T16:43:22.972Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-16 16:43:22.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545562
;

-- Value: C_Conversion_Rate_OutOfBounds
-- 2025-06-16T16:43:33.729Z
UPDATE AD_Message_Trl SET MsgText='Der Währungskurs liegt außerhalb der erlaubten Grenzen. {}',Updated=TO_TIMESTAMP('2025-06-16 16:43:33.729000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545562
;

-- 2025-06-16T16:43:33.731Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_Conversion_Rate_OutOfBounds
-- 2025-06-16T16:43:33.824Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-16 16:43:33.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545562
;

-- 2025-06-16T16:44:10.610Z
UPDATE AD_BusinessRule SET Name='C_ConversionRate_Rule_Limits',Updated=TO_TIMESTAMP('2025-06-16 16:44:10.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_BusinessRule_ID=540033
;

