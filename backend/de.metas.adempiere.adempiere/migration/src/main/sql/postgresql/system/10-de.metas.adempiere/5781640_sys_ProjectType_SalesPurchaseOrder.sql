-- Run mode: SWING_CLIENT

-- Reference: C_ProjectType Category
-- Value: O
-- ValueName: SalesPurchaseOrder
-- 2025-12-17T09:16:19.936Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,288,544086,TO_TIMESTAMP('2025-12-17 09:16:19.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Auftrag/Bestellung',TO_TIMESTAMP('2025-12-17 09:16:19.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'O','SalesPurchaseOrder')
;

-- 2025-12-17T09:16:19.943Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544086 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_ProjectType Category -> O_SalesPurchaseOrder
-- 2025-12-17T09:16:38.763Z
UPDATE AD_Ref_List_Trl SET Name='Sales/Purchase Order',Updated=TO_TIMESTAMP('2025-12-17 09:16:38.763000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544086
;

-- 2025-12-17T09:16:38.765Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_ProjectType_One_SOPO_Per_Org
-- 2025-12-17T20:22:58.460Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545620,0,TO_TIMESTAMP('2025-12-17 20:22:58.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Pro Organisation ist nur ein Projekt-Art ''Auftrag/Bestellung'' erlaubt.','E',TO_TIMESTAMP('2025-12-17 20:22:58.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_ProjectType_One_SOPO_Per_Org')
;

-- 2025-12-17T20:22:58.466Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545620 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_ProjectType_One_SOPO_Per_Org
-- 2025-12-17T20:23:31.908Z
UPDATE AD_Message_Trl SET MsgText='Only one ''Sales/Purchase Order'' project type per organization is allowed.',Updated=TO_TIMESTAMP('2025-12-17 20:23:31.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545620
;

-- 2025-12-17T20:23:31.910Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-17T20:46:06.794Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (1000000,1000000,556577,TO_TIMESTAMP('2025-12-17 20:46:04.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,100,1,'Y','N','N','N','Sales/Purchase Order',1000000,TO_TIMESTAMP('2025-12-17 20:46:04.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-17T20:46:06.927Z
UPDATE AD_Sequence SET IsAutoSequence='Y',Updated=TO_TIMESTAMP('2025-12-17 20:46:06.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Sequence_ID=556577
;

-- 2025-12-17T20:46:18.399Z
UPDATE AD_Sequence SET CurrentNext=1,Updated=TO_TIMESTAMP('2025-12-17 20:46:18.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Sequence_ID=556577
;

-- 2025-12-17T20:46:26.789Z
UPDATE AD_Sequence SET Prefix='O',Updated=TO_TIMESTAMP('2025-12-17 20:46:26.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Sequence_ID=556577
;

-- 2025-12-17T20:46:54.180Z
INSERT INTO C_ProjectType (AD_Client_ID,AD_Org_ID,AD_Sequence_ProjectValue_ID,C_ProjectType_ID,Created,CreatedBy,IsActive,Name,ProjectCategory,Updated,UpdatedBy) VALUES (1000000,1000000,556577,540014,TO_TIMESTAMP('2025-12-17 20:46:54.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Sales/Purchase Order','O',TO_TIMESTAMP('2025-12-17 20:46:54.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

UPDATE C_ProjectType SET IsActive='N' WHERE C_ProjectType_ID = 540014
;