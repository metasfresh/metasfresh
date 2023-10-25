-- Value: de.metas.contracts.flatrate.process.reject.cancelInterimOrModular
-- 2023-08-28T12:45:31.708453900Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545328,0,TO_TIMESTAMP('2023-08-28 14:45:29.549','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Modulare Verträge und Vorfinanzierungsverträge werden nicht unterstützt.','E',TO_TIMESTAMP('2023-08-28 14:45:29.549','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.flatrate.process.reject.cancelInterimOrModular')
;

-- 2023-08-28T12:45:31.738554100Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545328 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.flatrate.process.reject.cancelInterimOrModular
-- 2023-08-28T12:45:44.011697Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-08-28 14:45:44.011','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545328
;

-- Value: de.metas.contracts.flatrate.process.reject.cancelInterimOrModular
-- 2023-08-28T12:45:49.746614300Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-08-28 14:45:49.746','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545328
;

-- Value: de.metas.contracts.flatrate.process.reject.cancelInterimOrModular
-- 2023-08-28T12:46:11.404681700Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Modular and Interim Contracts aren''t supported.',Updated=TO_TIMESTAMP('2023-08-28 14:46:11.404','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545328
;

