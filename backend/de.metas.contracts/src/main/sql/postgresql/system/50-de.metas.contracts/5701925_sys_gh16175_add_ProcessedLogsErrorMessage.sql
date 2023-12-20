
-- Value: de.metas.contracts.modular.PROCESSED_LOGS_EXISTS
-- 2023-09-08T09:37:07.219044600Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545332,0,TO_TIMESTAMP('2023-09-08 12:37:07.008','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','There are logs that have been already processed for the selected record! Recomputing them is no longer possible.','E',TO_TIMESTAMP('2023-09-08 12:37:07.008','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.PROCESSED_LOGS_EXISTS')
;

-- 2023-09-08T09:37:07.225967500Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545332 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.PROCESSED_LOGS_EXISTS
-- 2023-09-08T09:37:19.920493Z
UPDATE AD_Message_Trl SET MsgText='Logeinträge, zum ausgewählten Datensatz wurden bereits bereits verarbeitet! Eine Neuerstellung ist nicht mehr möglich.',Updated=TO_TIMESTAMP('2023-09-08 12:37:19.92','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545332
;

-- Value: de.metas.contracts.modular.PROCESSED_LOGS_EXISTS
-- 2023-09-08T09:37:24.569115Z
UPDATE AD_Message_Trl SET MsgText='Logeinträge, zum ausgewählten Datensatz wurden bereits bereits verarbeitet! Eine Neuerstellung ist nicht mehr möglich.',Updated=TO_TIMESTAMP('2023-09-08 12:37:24.569','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545332
;

-- 2023-09-08T09:37:24.570124200Z
UPDATE AD_Message SET MsgText='Logeinträge, zum ausgewählten Datensatz wurden bereits bereits verarbeitet! Eine Neuerstellung ist nicht mehr möglich.' WHERE AD_Message_ID=545332
;
