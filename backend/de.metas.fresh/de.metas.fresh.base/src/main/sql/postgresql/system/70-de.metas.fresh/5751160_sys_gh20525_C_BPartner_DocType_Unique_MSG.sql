-- Value: C_BPartner_DocType_Unique
-- 2025-04-05T21:30:50.267Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545525,0,TO_TIMESTAMP('2025-04-05 21:30:50.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Der Belegart {0} ist bereits für den Geschäftspartner {1} in {2} definiert.','E',TO_TIMESTAMP('2025-04-05 21:30:50.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_BPartner_DocType_Unique')
;

-- 2025-04-05T21:30:50.268Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545525 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_BPartner_DocType_Unique
-- 2025-04-05T21:31:05.930Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The document type {0} is already defined for the business partner {1} in {2}.',Updated=TO_TIMESTAMP('2025-04-05 21:31:05.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545525
;

