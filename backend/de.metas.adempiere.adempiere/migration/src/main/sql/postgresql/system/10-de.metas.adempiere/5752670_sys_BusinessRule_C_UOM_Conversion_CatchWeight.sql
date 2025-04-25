-- Value: C_UOM_Conversion_No_Catchweight_Flag
-- 2025-04-24T08:14:06.878Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545536,0,TO_TIMESTAMP('2025-04-24 08:14:06.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','For Catch Weight, the checkbox for "Target is catch UOM" must be ticked as well.','I',TO_TIMESTAMP('2025-04-24 08:14:06.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_UOM_Conversion_No_Catchweight_Flag')
;

-- 2025-04-24T08:14:06.879Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545536 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_UOM_Conversion_No_Catchweight_Flag
-- 2025-04-24T08:14:13.400Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2025-04-24 08:14:13.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545536
;

-- Value: C_UOM_Conversion_No_Catchweight_Flag
-- 2025-04-24T08:14:27.182Z
UPDATE AD_Message_Trl SET MsgText='Für Catch Weight muss noch der Haken in "Ziel ist Catch-Maßeinheit" gesetzt werden. ',Updated=TO_TIMESTAMP('2025-04-24 08:14:27.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545536
;

-- 2025-04-24T08:14:27.184Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_UOM_Conversion_No_Catchweight_Flag
-- 2025-04-24T08:14:27.269Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-24 08:14:27.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545536
;

-- Value: C_UOM_Conversion_No_Catchweight_Flag
-- 2025-04-24T08:14:35.049Z
UPDATE AD_Message_Trl SET MsgText='Für Catch Weight muss noch der Haken in "Ziel ist Catch-Maßeinheit" gesetzt werden. ',Updated=TO_TIMESTAMP('2025-04-24 08:14:35.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545536
;

-- 2025-04-24T08:14:35.050Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_UOM_Conversion_No_Catchweight_Flag
-- 2025-04-24T08:14:35.158Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-24 08:14:35.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545536
;

-- Name: C_UOM_Conversion with catch weight flag
-- 2025-04-24T08:16:02.398Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540715,TO_TIMESTAMP('2025-04-24 08:16:02.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','C_UOM_Conversion with catch weight flag','S',TO_TIMESTAMP('2025-04-24 08:16:02.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: C_UOM_Conversion with catch weight flag
-- 2025-04-24T08:16:06.187Z
UPDATE AD_Val_Rule SET EntityType='D',Updated=TO_TIMESTAMP('2025-04-24 08:16:06.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540715
;


-- 2025-04-24T08:23:18.744Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsDebug,Name,Updated,UpdatedBy,Validation_Rule_ID,Warning_Message_ID) VALUES (540014,0,0,175,TO_TIMESTAMP('2025-04-24 08:23:18.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','C_UOM_Conversion with catch weight flag',TO_TIMESTAMP('2025-04-24 08:23:18.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540715,545536)
;

-- 2025-04-24T08:27:04.074Z
INSERT INTO AD_BusinessRule_Precondition (AD_BusinessRule_ID,AD_BusinessRule_Precondition_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,PreconditionSQL,PreconditionType,Updated,UpdatedBy) VALUES (540014,540008,0,0,TO_TIMESTAMP('2025-04-24 08:27:04.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','select case when uomtype = ''WE'' then true else false end from c_uom uom where uom.c_uom_ID = c_uom_conversion.c_uom_to_id','S',TO_TIMESTAMP('2025-04-24 08:27:04.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-24T08:29:44.412Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540014,540019,0,0,TO_TIMESTAMP('2025-04-24 08:29:44.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y',175,'C_UOM_Conversion_ID',TO_TIMESTAMP('2025-04-24 08:29:44.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-25T09:25:50.334Z
DELETE FROM AD_BusinessRule_Precondition WHERE AD_BusinessRule_Precondition_ID=540008
;


-- Name: C_UOM_Conversion with catch weight flag
-- 2025-04-25T09:30:51.216Z
UPDATE AD_Val_Rule SET Code='iscatchuomforproduct = ''N''
   OR (iscatchuomforproduct = ''Y'' AND EXISTS
    (SELECT 1
     FROM C_UOM targetUOM
     WHERE C_UOM_Conversion.c_uom_to_id = targetUOM.c_uom_id
       AND targetUOM.uomtype = ''WE''))',Updated=TO_TIMESTAMP('2025-04-25 09:30:51.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540715
;

