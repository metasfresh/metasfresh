-- 2022-01-03T14:29:05.233Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545089,0,TO_TIMESTAMP('2022-01-03 15:29:05','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','PDF-Datei wurde erstellt und enthÃ¤lt 3 Rechnungen.','I',TO_TIMESTAMP('2022-01-03 15:29:05','YYYY-MM-DD HH24:MI:SS'),100,'AutomaticallyInvoicePdfPrintinAsyncBatchListener_Pdf_Done')
;

-- 2022-01-03T14:29:05.235Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545089 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-01-03T14:29:33.789Z
-- URL zum Konzept
UPDATE AD_Message SET MsgText='PDF-Datei wurde erstellt.',Updated=TO_TIMESTAMP('2022-01-03 15:29:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545089
;

-- 2022-01-03T14:30:02.368Z
-- URL zum Konzept
UPDATE AD_Message SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2022-01-03 15:30:02','YYYY-MM-DD HH24:MI:SS'),MsgText='PDF file was created.',UpdatedBy=100 WHERE AD_Message_ID=545089
;

UPDATE AD_Message_trl SET MsgText='PDF file was created.',UpdatedBy=100 WHERE AD_Message_ID=545089
;
