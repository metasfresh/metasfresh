
-- Value: FEC_SectionCode_Doesnt_Match_With_Document
-- 2023-07-14T14:58:43.251Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545291,0,TO_TIMESTAMP('2023-07-14 17:58:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','ForexContract.SectionCode ({0}) doesn''t match the Document.SectionCode ({1})','E',TO_TIMESTAMP('2023-07-14 17:58:42','YYYY-MM-DD HH24:MI:SS'),100,'FEC_SectionCode_Doesnt_Match_With_Document')
;

-- 2023-07-14T14:58:43.270Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545291 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

