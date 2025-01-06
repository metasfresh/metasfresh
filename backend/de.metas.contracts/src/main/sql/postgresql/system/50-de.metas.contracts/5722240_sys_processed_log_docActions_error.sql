-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.PROCESSED_LOGS_EXISTS
-- 2024-04-18T08:42:19.527Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-18 10:42:19.527','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545332
;

-- Value: de.metas.contracts.modular.PROCESSED_LOGS_EXISTS
-- 2024-04-18T08:42:27.493Z
UPDATE AD_Message_Trl SET MsgText='Logeinträge, zum ausgewählten Datensatz wurden bereits verarbeitet! Eine Neuerstellung ist nicht mehr möglich.',Updated=TO_TIMESTAMP('2024-04-18 10:42:27.493','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545332
;

-- Value: de.metas.contracts.modular.PROCESSED_LOGS_EXISTS
-- 2024-04-18T08:42:33.602Z
UPDATE AD_Message_Trl SET MsgText='Logeinträge, zum ausgewählten Datensatz wurden bereits verarbeitet! Eine Neuerstellung ist nicht mehr möglich.',Updated=TO_TIMESTAMP('2024-04-18 10:42:33.602','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545332
;

-- 2024-04-18T08:42:33.603Z
UPDATE AD_Message SET MsgText='Logeinträge, zum ausgewählten Datensatz wurden bereits verarbeitet! Eine Neuerstellung ist nicht mehr möglich.' WHERE AD_Message_ID=545332
;

-- Value: de.metas.contracts.modular.PROCESSED_LOGS_EXISTS
-- 2024-04-18T08:42:37.400Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-18 10:42:37.4','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545332
;

-- Value: de.metas.contracts.modular.PROCESSED_LOGS_EXISTS
-- 2024-04-18T08:42:41.419Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-18 10:42:41.419','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545332
;

-- Value: de.metas.contracts.modular.calculation.CalculationMethodService.DocActionNotAllowedForProcessedLogsError
-- 2024-04-18T08:45:28.757Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545391,0,TO_TIMESTAMP('2024-04-18 10:45:28.616','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Dokumentaktion ist nicht erlaubt, da modulare Logeinträge, zu diesem Dokument bereits verarbeitet wurden. ','E',TO_TIMESTAMP('2024-04-18 10:45:28.616','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.calculation.CalculationMethodService.DocActionNotAllowedForProcessedLogsError')
;

-- 2024-04-18T08:45:28.761Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545391 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.calculation.CalculationMethodService.DocActionNotAllowedForProcessedLogsError
-- 2024-04-18T08:45:33.423Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-18 10:45:33.423','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545391
;

-- Value: de.metas.contracts.modular.calculation.CalculationMethodService.DocActionNotAllowedForProcessedLogsError
-- 2024-04-18T08:45:34.473Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-18 10:45:34.473','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545391
;

-- Value: de.metas.contracts.modular.calculation.CalculationMethodService.DocActionNotAllowedForProcessedLogsError
-- 2024-04-18T08:46:31.313Z
UPDATE AD_Message_Trl SET MsgText='Document action is not allowed, because modular logs related to this document are already processed.',Updated=TO_TIMESTAMP('2024-04-18 10:46:31.313','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545391
;

-- Value: de.metas.contracts.modular.calculation.CalculationMethodService.DocActionNotAllowedForProcessedLogsError
-- 2024-04-18T10:31:44.718Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-18 12:31:44.718','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545391
;

