-- Run mode: SWING_CLIENT

-- Value: de.metas.manufacturing.job.service.commands.ONLY_RECEIVE_TO_EXISTING_LU_IS_SUPPORTED
-- 2025-05-06T14:45:35.963Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2025-05-06 14:45:35.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545493
;

-- Value: de.metas.manufacturing.job.service.commands.MIXING_DIFFERENT_PRODUCTS_NOT_ALLOWED
-- 2025-05-06T14:47:59.231Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545545,0,TO_TIMESTAMP('2025-05-06 14:47:59.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Das Mischen unterschiedlicher Produkte in derselben HU ist nicht erlaubt.','E',TO_TIMESTAMP('2025-05-06 14:47:59.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.manufacturing.job.service.commands.MIXING_DIFFERENT_PRODUCTS_NOT_ALLOWED')
;

-- 2025-05-06T14:47:59.233Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545545 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.manufacturing.job.service.commands.MIXING_DIFFERENT_PRODUCTS_NOT_ALLOWED
-- 2025-05-06T14:48:07.202Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-06 14:48:07.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545545
;

-- Value: de.metas.manufacturing.job.service.commands.MIXING_DIFFERENT_PRODUCTS_NOT_ALLOWED
-- 2025-05-06T14:48:13.768Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Mixing different products in the same HU is not allowed.',Updated=TO_TIMESTAMP('2025-05-06 14:48:13.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545545
;

-- 2025-05-06T14:48:13.769Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.manufacturing.job.service.commands.MIXING_DIFFERENT_PRODUCTS_NOT_ALLOWED
-- 2025-05-06T14:48:16.420Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-06 14:48:16.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545545
;

