-- 13.10.2016 12:58
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544207,0,TO_TIMESTAMP('2016-10-13 12:58:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','Die Menge {1} konnte nicht in die Preiseinheit {2} konvertiert werden. Bitte prüfen Sie die Mengen-Umrechnungsregeln für das Produkt {3}.','I',TO_TIMESTAMP('2016-10-13 12:58:28','YYYY-MM-DD HH24:MI:SS'),100,'InvoiceCandBL_Unable_To_Convert_Qty')
;

-- 13.10.2016 12:58
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544207 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 13.10.2016 12:58
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Die Menge (oder der Betrag) {0} konnte nicht in die Preiseinheit {1} umgerechnet werden. Bitte prüfen Sie die Mengen-Umrechnungsregeln für das Produkt {2}.',Updated=TO_TIMESTAMP('2016-10-13 12:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544207
;

-- 13.10.2016 12:58
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544207
;

