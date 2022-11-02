-- 2022-11-02T15:16:06.929Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545189,0,TO_TIMESTAMP('2022-11-02 17:16:06.71','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','The value of the attribute {0} must be unique so it cannot be set for a HU with qty >1 and unit of measure {2}.','E',TO_TIMESTAMP('2022-11-02 17:16:06.71','YYYY-MM-DD HH24:MI:SS.US'),100,'M_HU_UniqueAttribute_HUQtyError')
;

-- 2022-11-02T15:16:06.935Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545189 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-11-02T15:21:14.363Z
UPDATE AD_Message SET MsgText='The value of the attribute {0} must be unique so it cannot be set for a HU with qty >1 and unit of measure {1}.',Updated=TO_TIMESTAMP('2022-11-02 17:21:14.361','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545189
;

-- 2022-11-02T15:22:58.296Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545190,0,TO_TIMESTAMP('2022-11-02 17:22:58.175','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','The HU {0} with the attribute {1} with value {2} for product {3} already exists.','E',TO_TIMESTAMP('2022-11-02 17:22:58.175','YYYY-MM-DD HH24:MI:SS.US'),100,'M_HU_UniqueAttribute_DuplicateValue_Error')
;

-- 2022-11-02T15:22:58.298Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545190 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

