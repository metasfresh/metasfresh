-- SysConfig Name: PO_AllowReactivationIfReceiptsCreated
-- SysConfig Value: N
-- 2025-12-18T18:32:25.584Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541783,'S',TO_TIMESTAMP('2025-12-18 18:32:25.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Allow Reactivation of Purchase Orders even if Material Receipts are already created','D','Y','PO_AllowReactivationIfReceiptsCreated',TO_TIMESTAMP('2025-12-18 18:32:25.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- Value: ERR_ORDER_MODIFICATION_NOT_ALLOWED_RECEIPT_EXISTS
-- 2025-12-19T08:28:15.620Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,ErrorCode,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545621,0,TO_TIMESTAMP('2025-12-19 08:28:15.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','ORDER_RECEIPT_EXISTS','Y','Dieser Auftrag ist aufgrund eines vorhandenen abgeschlossenen Wareneingangs für Änderungen gesperrt','E',TO_TIMESTAMP('2025-12-19 08:28:15.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ERR_ORDER_MODIFICATION_NOT_ALLOWED_RECEIPT_EXISTS')
;

-- 2025-12-19T08:28:15.636Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545621 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ERR_ORDER_MODIFICATION_NOT_ALLOWED_RECEIPT_EXISTS
-- 2025-12-19T08:28:22.312Z
UPDATE AD_Message_Trl SET MsgText='This order is locked for changes due to the existence of a completed material receipt.',Updated=TO_TIMESTAMP('2025-12-19 08:28:22.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545621
;

-- 2025-12-19T08:28:22.312Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
