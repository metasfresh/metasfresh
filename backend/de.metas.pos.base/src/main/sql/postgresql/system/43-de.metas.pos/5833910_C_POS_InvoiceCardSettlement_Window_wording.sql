-- Wording fixes for the card settlement back-office window (migration 5833900):
-- Note parameter gets its own element with a required-field wording, IsAbandoned is 'Released as abandoned' in English,
-- SumUp transaction gets a German label.
-- IDs allocated from the central ID server.

INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585612 /*From ID Server*/,0,'POS_InvoiceCardSettlement_ResolutionNote',TO_TIMESTAMP('2026-11-09 15:00:01','YYYY-MM-DD HH24:MI:SS'),100,'Pflichtangabe: Wie wurde der Fall geklärt bzw. erstattet?','de.metas.pos','Tragen Sie hier ein, was unternommen wurde (z. B. Zahlung manuell zugeordnet oder Erstattung im SumUp-Dashboard vorgenommen). Der Text wird als Notiz am Eintrag gespeichert.','Y','Notiz','Notiz',TO_TIMESTAMP('2026-11-09 15:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,Description,Help,PO_Name,PO_PrintName,PO_Description,PO_Help,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Element_ID,t.Name,t.PrintName,t.Description,t.Help,t.PO_Name,t.PO_PrintName,t.PO_Description,t.PO_Help,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585612 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET Name='Note',PrintName='Note',Description='Required: how was the case resolved or refunded?',Help='Enter what was done (e.g. payment allocated manually, or refund made in the SumUp dashboard). The text is stored as the note on the entry.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 15:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585612 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 15:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585612 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 15:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585612 AND AD_Language='de_CH'
;
UPDATE AD_Process_Para SET AD_Element_ID=585612,IsCentrallyMaintained='N',Updated=TO_TIMESTAMP('2026-11-09 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID IN (543374,543375)
;
SELECT update_Process_Para_Translation_From_AD_Element(585612)
;
UPDATE AD_Element_Trl SET Name='Released as abandoned',PrintName='Released as abandoned',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 15:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585600 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585600)
;
UPDATE AD_Ref_List_Trl SET Name='Released as abandoned',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 15:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=544397 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET Name='SumUp-Transaktion',PrintName='SumUp-Transaktion',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 15:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583303 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='SumUp-Transaktion',PrintName='SumUp-Transaktion',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 15:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583303 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-09 15:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583303 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583303)
;
