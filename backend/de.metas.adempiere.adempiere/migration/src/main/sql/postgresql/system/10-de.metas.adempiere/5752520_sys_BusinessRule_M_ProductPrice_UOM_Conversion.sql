-- Run mode: WEBUI

-- Value: M_ProductPrice_No_UOM_Conversion
-- 2025-04-23T12:04:49.003Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545529,0,TO_TIMESTAMP('2025-04-23 12:04:48.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','The price UOM does not have a UOM conversion.','I',TO_TIMESTAMP('2025-04-23 12:04:48.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ProductPrice_No_UOM_Conversion')
;

-- 2025-04-23T12:04:49.005Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) 
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
 FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545529 
 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: M_ProductPrice_No_UOM_Conversion
-- 2025-04-23T12:05:08.580Z
UPDATE AD_Message_Trl SET IsTranslated='Y',
Updated=TO_TIMESTAMP('2025-04-23 12:05:08.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 
WHERE AD_Language='de_DE' AND AD_Message_ID=545529
;

-- Value: M_ProductPrice_No_UOM_Conversion
-- 2025-04-23T12:05:10.917Z
UPDATE AD_Message_Trl SET MsgText='Für die Preiseinheit gibt es noch keine Maßeinheit Umrechnung.',
Updated=TO_TIMESTAMP('2025-04-23 12:05:10.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 
WHERE AD_Language='de_DE' AND AD_Message_ID=545529
;

-- 2025-04-23T12:05:10.918Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy 
FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: M_ProductPrice_No_UOM_Conversion
-- 2025-04-23T12:05:13.878Z
UPDATE AD_Message_Trl SET MsgTip='Für die Preiseinheit gibt es noch keine Maßeinheit Umrechnung.',
Updated=TO_TIMESTAMP('2025-04-23 12:05:13.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
 WHERE AD_Language='de_DE' AND AD_Message_ID=545529
;

-- 2025-04-23T12:05:13.880Z
UPDATE AD_Message base SET MsgTip=trl.MsgTip, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy 
FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: M_ProductPrice_No_UOLM_Conversion
-- 2025-04-23T12:05:21.615Z
UPDATE AD_Message_Trl SET MsgText='Für die Preiseinheit gibt es noch keine Maßeinheit Umrechnung.',
Updated=TO_TIMESTAMP('2025-04-23 12:05:21.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 
WHERE AD_Language='de_CH' AND AD_Message_ID=545529
;

-- 2025-04-23T12:05:21.617Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl 
 WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: M_ProductPrice_No_UOLM_Conversion
-- 2025-04-23T12:05:21.737Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-23 12:05:21.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545529
;

-- Value: M_ProductPrice_No_UOLM_Conversion
-- 2025-04-23T12:05:24.230Z
UPDATE AD_Message_Trl SET MsgTip='Für die Preiseinheit gibt es noch keine Maßeinheit Umrechnung.',
Updated=TO_TIMESTAMP('2025-04-23 12:05:24.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
 WHERE AD_Language='de_CH' AND AD_Message_ID=545529
;

-- 2025-04-23T12:05:24.231Z
UPDATE AD_Message base SET MsgTip=trl.MsgTip, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl 
 WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-23T12:05:53.112Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsDebug,Name,Updated,UpdatedBy,Validation_Rule_ID,
Warning_Message_ID) VALUES (540008,0,0,251,TO_TIMESTAMP('2025-04-23 12:05:53.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,
'Y','N','M_ProductPrice with UOM conversion',TO_TIMESTAMP('2025-04-23 12:05:53.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
100,540472,540301)
;

-- 2025-04-23T12:06:12.749Z
UPDATE AD_BusinessRule SET Warning_Message_ID=545529,
Updated=TO_TIMESTAMP('2025-04-23 12:06:12.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
UpdatedBy=100 WHERE AD_BusinessRule_ID=540008
;




-- Name: M_ProductPrice with UOM Conversion
-- 2025-04-23T12:25:31.747Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) 
VALUES (0,0,540709,TO_TIMESTAMP('2025-04-23 12:25:31.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
100,'U','Y','M_ProductPrice with UOM Conversion','S',TO_TIMESTAMP('2025-04-23 12:25:31.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_ProductPrice with UOM Conversion
-- 2025-04-23T12:25:39.975Z
UPDATE AD_Val_Rule SET Code='EXISTS (
SELECT 1
FROM M_ProductPrice pp
         JOIN M_Product p
              ON pp.M_Product_ID = p.M_Product_ID
WHERE pp.M_ProductPrice_ID = M_ProductPrice.M_ProductPrice_ID
  AND ((p.C_UOM_ID = pp.C_UOM_ID) OR (EXISTS (SELECT 1
                                              FROM C_UOM_Conversion c
                                              WHERE p.C_UOM_ID = c.C_UOM_ID
                                                AND p.M_PRODUCT_ID = c.M_Product_ID
                                                AND c.IsActive = ''Y''
                                                AND M_ProductPrice.C_UOM_ID = C.C_UOM_TO_ID))
    OR (EXISTS (SELECT 1
                FROM C_UOM_Conversion c
                WHERE p.C_UOM_ID = c.C_UOM_ID
                  AND c.M_Product_ID IS NULL
                  AND c.IsActive = ''Y''
                  AND M_ProductPrice.C_UOM_ID = c.C_UOM_TO_ID))))
',Updated=TO_TIMESTAMP('2025-04-23 12:25:39.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540709
;

-- 2025-04-23T12:26:02.769Z
UPDATE AD_BusinessRule SET Validation_Rule_ID=540709,Updated=TO_TIMESTAMP('2025-04-23 12:26:02.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_BusinessRule_ID=540008
;

-- 2025-04-23T12:26:55.669Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540008,540013,0,0,TO_TIMESTAMP('2025-04-23 12:26:55.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y',251,'m_productprice_id',TO_TIMESTAMP('2025-04-23 12:26:55.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

