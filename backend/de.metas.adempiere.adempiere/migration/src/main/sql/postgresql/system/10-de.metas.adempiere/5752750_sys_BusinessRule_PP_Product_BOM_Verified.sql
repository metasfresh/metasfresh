
-- Value: PP_Product_BOM_NotVerified
-- 2025-04-24T14:43:57.495Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545538,0,TO_TIMESTAMP('2025-04-24 14:43:57.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','The BOM Version still needs to be verified.','I',TO_TIMESTAMP('2025-04-24 14:43:57.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PP_Product_BOM_NotVerified')
;

-- 2025-04-24T14:43:57.497Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545538 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: PP_Product_BOM_NotVerified
-- 2025-04-24T14:44:09.119Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2025-04-24 14:44:09.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545538
;

-- Value: PP_Product_BOM_NotVerified
-- 2025-04-24T14:44:23.074Z
UPDATE AD_Message_Trl SET MsgText='Die Stücklisten Version muss noch verifiziert werden.',Updated=TO_TIMESTAMP('2025-04-24 14:44:23.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545538
;

-- 2025-04-24T14:44:23.075Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: PP_Product_BOM_NotVerified
-- 2025-04-24T14:44:23.619Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-24 14:44:23.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545538
;

-- Value: PP_Product_BOM_NotVerified
-- 2025-04-24T14:44:27.997Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-24 14:44:27.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545538
;

-- Value: PP_Product_BOM_NotVerified
-- 2025-04-24T14:44:29.422Z
UPDATE AD_Message_Trl SET MsgText='Die Stücklisten Version muss noch verifiziert werden.',Updated=TO_TIMESTAMP('2025-04-24 14:44:29.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545538
;

-- 2025-04-24T14:44:29.428Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Name: M_Product_BOM verified
-- 2025-04-24T14:48:00.117Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540717,TO_TIMESTAMP('2025-04-24 14:48:00.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','M_Product_BOM verified','S',TO_TIMESTAMP('2025-04-24 14:48:00.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_Product_BOM verified
-- 2025-04-24T14:48:04.511Z
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT 1
              FROM pp_product_bom bom
                       JOIN M_Product p ON bom.m_product_id = p.m_product_id
              WHERE bom.pp_product_bom_id = PP_Product_BOM.pp_product_bom_id
                AND bom.docstatus = ''CO''
                AND p.isverified = ''N'')',Updated=TO_TIMESTAMP('2025-04-24 14:48:04.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540717
;

-- Name: M_Product_BOM verified
-- 2025-04-24T14:48:08.831Z
UPDATE AD_Val_Rule SET EntityType='D',Updated=TO_TIMESTAMP('2025-04-24 14:48:08.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540717
;

-- Name: PP_Product_BOM verified
-- 2025-04-24T14:49:19.989Z
UPDATE AD_Val_Rule SET Name='PP_Product_BOM verified',Updated=TO_TIMESTAMP('2025-04-24 14:49:19.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540717
;

-- 2025-04-24T14:49:41.095Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsDebug,Name,Updated,UpdatedBy,Validation_Rule_ID,Warning_Message_ID) VALUES (540017,0,0,53018,TO_TIMESTAMP('2025-04-24 14:49:40.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','BOM should be verified',TO_TIMESTAMP('2025-04-24 14:49:40.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540717,545538)
;

-- 2025-04-24T14:50:01.150Z
INSERT INTO AD_BusinessRule_Precondition (AD_BusinessRule_ID,AD_BusinessRule_Precondition_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,PreconditionSQL,PreconditionType,Updated,UpdatedBy) VALUES (540017,540018,0,0,TO_TIMESTAMP('2025-04-24 14:50:01.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','PP_Product_BOM.DocStatus = ''CO''','S',TO_TIMESTAMP('2025-04-24 14:50:01.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-24T14:50:33.086Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540017,540022,0,0,TO_TIMESTAMP('2025-04-24 14:50:33.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y',53018,'PP_Product_BOM_ID',TO_TIMESTAMP('2025-04-24 14:50:33.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-25T09:34:30.455Z
DELETE FROM AD_BusinessRule_Precondition WHERE AD_BusinessRule_Precondition_ID=540018
;

-- Name: PP_Product_BOM verified
-- 2025-04-25T09:38:31.265Z
UPDATE AD_Val_Rule SET Code='docstatus NOT IN (''CO'', ''CL'')
   OR (
    docstatus IN (''CO'', ''CL'')
        AND EXISTS (SELECT 1
                    FROM M_Product p
                    WHERE pp_product_bom.m_product_id = p.m_product_id
                      AND p.isverified = ''Y''))',Updated=TO_TIMESTAMP('2025-04-25 09:38:31.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540717
;

-- Name: PP_Product_BOM verified
-- 2025-04-25T11:06:04.596Z
UPDATE AD_Val_Rule SET IsActive='N',Updated=TO_TIMESTAMP('2025-04-25 11:06:04.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540717
;

