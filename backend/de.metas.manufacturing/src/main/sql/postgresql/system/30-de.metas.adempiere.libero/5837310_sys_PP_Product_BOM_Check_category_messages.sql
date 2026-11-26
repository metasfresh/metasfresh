-- Messages for the result of the PP_Product_BOM_Check category run.
-- The run returns one of these messages as its end message; it does not throw them.
-- Base text is German; en_US carries the English text. Both messages use MsgType 'I'.

-- Value: org.eevolution.process.PP_Product_BOM_Check.CategoryRunFailed
-- 2026-11-26T00:30:21.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546027 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-26 00:30:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','{0} von {1} Produkten konnten nicht verifiziert werden: {2}. Die Gründe stehen im Prozessprotokoll (Prozess-Revision, Register Protokoll).','I',TO_TIMESTAMP('2026-11-26 00:30:21','YYYY-MM-DD HH24:MI:SS'),100,'org.eevolution.process.PP_Product_BOM_Check.CategoryRunFailed')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546027 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='{0} of {1} products could not be verified: {2}. The reasons are in the process log (Process Audit, tab Log).',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-26 00:30:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546027
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-26 00:30:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546027
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-26 00:30:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546027
;

-- Value: org.eevolution.process.PP_Product_BOM_Check.CategoryRunOK
-- 2026-11-26T00:30:26.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546028 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-26 00:30:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Alle {0} Produkte der Kategorie wurden verifiziert, keine Fehler.','I',TO_TIMESTAMP('2026-11-26 00:30:26','YYYY-MM-DD HH24:MI:SS'),100,'org.eevolution.process.PP_Product_BOM_Check.CategoryRunOK')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546028 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='All {0} products of the category were verified, none failed.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-26 00:30:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546028
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-26 00:30:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546028
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-26 00:30:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546028
;
