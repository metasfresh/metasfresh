-- Run mode: WEBUI

-- Name: C_OrderLine_M_Product_Category_MaxNetAmount
-- 2025-06-10T10:55:41.903Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540732,TO_TIMESTAMP('2025-06-10 10:55:41.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','C_OrderLine_M_Product_Category_MaxNetAmount','S',TO_TIMESTAMP('2025-06-10 10:55:41.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Value: C_OrderLine_M_Product_Category_MaxNetAmount
-- 2025-06-10T10:56:23.649Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545557,0,TO_TIMESTAMP('2025-06-10 10:56:23.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','The maximum net amount of the product category was exceeded.','I',TO_TIMESTAMP('2025-06-10 10:56:23.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_OrderLine_M_Product_Category_MaxNetAmount')
;

-- 2025-06-10T10:56:23.662Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545557 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_OrderLine_M_Product_Category_MaxNetAmount
-- 2025-06-10T10:56:33.591Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-10 10:56:33.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545557
;

-- 2025-06-10T10:56:56.892Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsCreateWarningOnTarget,IsDebug,Name,Severity,Updated,UpdatedBy,Validation_Rule_ID,Warning_Message_ID) VALUES (540032,0,0,260,TO_TIMESTAMP('2025-06-10 10:56:56.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','C_OrderLine_M_Product_Category_MaxNetAmount','E',TO_TIMESTAMP('2025-06-10 10:56:56.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540732,545557)
;



-- Name: C_OrderLine_M_Product_Category_MaxNetAmount
-- 2025-06-10T12:23:01.204Z
UPDATE AD_Val_Rule SET Code='NOT EXISTS (SELECT 1 from
M_Product_Category pc 
JOIN M_Product p ON pc.M_Product_Category_ID = p.M_Product_Category_ID
JOIN M_Product_Category_MaxNetAmount pcm ON pc.M_Product_Category_ID = pcm.M_Product_Category_ID
WHERE p.M_Product_ID = C_OrderLine.M_Product_ID
AND pcm.MaxNetAmount < C_OrderLine.LineNetAmt)',
Updated=TO_TIMESTAMP('2025-06-10 12:23:01.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
UpdatedBy=100 WHERE AD_Val_Rule_ID=540732
;

-- Name: C_OrderLine_M_Product_Category_MaxNetAmount
-- 2025-06-10T12:23:10.685Z
UPDATE AD_Val_Rule SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-10 12:23:10.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540732
;




-- 2025-06-10T13:11:09.845Z
INSERT INTO AD_BusinessRule_WarningTarget (AD_BusinessRule_ID,AD_BusinessRule_WarningTarget_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,LookupSQL,SeqNo,Updated,UpdatedBy) VALUES (540032,540005,0,0,259,TO_TIMESTAMP('2025-06-10 13:11:09.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','(SELECT C_Order_ID from C_OrderLine)',10,TO_TIMESTAMP('2025-06-10 13:11:09.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Run mode: WEBUI

-- 2025-06-10T15:38:55.743Z
UPDATE AD_BusinessRule_WarningTarget SET LookupSQL='(SELECT C_Order_ID from C_Order o WHERE o.C_Order_ID = C_OrderLine.C_Order_ID)',Updated=TO_TIMESTAMP('2025-06-10 15:38:55.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_BusinessRule_WarningTarget_ID=540005
;



-- Value: C_OrderLine_M_Product_Category_MaxNetAmount
-- 2025-06-10T16:32:20.316Z
UPDATE AD_Message SET MsgText='The maximum net amount for the product category of the product in the order line was exceeded. {}',Updated=TO_TIMESTAMP('2025-06-10 16:32:20.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545557
;

-- 2025-06-10T16:32:20.318Z
UPDATE AD_Message_Trl trl SET MsgText='The maximum net amount for the product category of the product in the order line was exceeded. {}' WHERE AD_Message_ID=545557 AND AD_Language='de_DE'
;

-- Value: C_OrderLine_M_Product_Category_MaxNetAmount
-- 2025-06-10T16:32:29.870Z
UPDATE AD_Message_Trl SET MsgText='The maximum net amount for the product category of the product in the order line was exceeded. {}',Updated=TO_TIMESTAMP('2025-06-10 16:32:29.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545557
;

-- 2025-06-10T16:32:29.877Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_OrderLine_M_Product_Category_MaxNetAmount
-- 2025-06-10T16:32:34.429Z
UPDATE AD_Message_Trl SET MsgText='The maximum net amount for the product category of the product in the order line was exceeded. {}',Updated=TO_TIMESTAMP('2025-06-10 16:32:34.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545557
;

-- 2025-06-10T16:32:34.436Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_OrderLine_M_Product_Category_MaxNetAmount
-- 2025-06-10T16:32:38.144Z
UPDATE AD_Message_Trl SET MsgText='The maximum net amount for the product category of the product in the order line was exceeded. {}',Updated=TO_TIMESTAMP('2025-06-10 16:32:38.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545557
;

-- 2025-06-10T16:32:38.150Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;





