-- POS invoice card settlements: German wording "Erstattung" -> "Rückerstattung" (the established glossary term).
-- English translations are not changed. Each _Trl update only touches rows that still carry the old German text
-- (de_DE, de_CH and the untranslated fr_CH copies), so a real translation in another language is left alone.

-- Status list C_POS_InvoiceCardSettlement.Status: CR (Credit Refund Pending)
UPDATE AD_Ref_List SET Name='Guthaben – Rückerstattung offen', Updated=TO_TIMESTAMP('2026-11-19 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Ref_List_ID=544393;
UPDATE AD_Ref_List_Trl SET Name='Guthaben – Rückerstattung offen', Updated=TO_TIMESTAMP('2026-11-19 12:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Ref_List_ID=544393 AND Name='Guthaben – Erstattung offen';

-- Status list C_POS_InvoiceCardSettlement.Status: RF (Refunded)
UPDATE AD_Ref_List SET Name='Rückerstattet', Updated=TO_TIMESTAMP('2026-11-19 12:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Ref_List_ID=544394;
UPDATE AD_Ref_List_Trl SET Name='Rückerstattet', Updated=TO_TIMESTAMP('2026-11-19 12:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Ref_List_ID=544394 AND Name='Erstattet';

-- Process C_POS_InvoiceCardSettlement_MarkRefunded (no menu entry; launched from the settlements window)
UPDATE AD_Process SET Name='Als rückerstattet markieren', Description='Markiert die Kartenzahlung als rückerstattet, nachdem die Rückerstattung beim Kartenanbieter erfolgt ist. Die Notiz ist Pflicht.', Updated=TO_TIMESTAMP('2026-11-19 12:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_ID=585698;
UPDATE AD_Process_Trl SET Name='Als rückerstattet markieren', Description='Markiert die Kartenzahlung als rückerstattet, nachdem die Rückerstattung beim Kartenanbieter erfolgt ist. Die Notiz ist Pflicht.', Updated=TO_TIMESTAMP('2026-11-19 12:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_ID=585698 AND Name='Als erstattet markieren';

-- Message de.metas.pos.InvoiceCardSettlement.CreditRefundPending
UPDATE AD_Message SET MsgText='Kartenzahlung {0} für Rechnung {1} wurde als Kundenguthaben gebucht – bitte rückerstatten oder zuordnen.', Updated=TO_TIMESTAMP('2026-11-19 12:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545999;
UPDATE AD_Message_Trl SET MsgText='Kartenzahlung {0} für Rechnung {1} wurde als Kundenguthaben gebucht – bitte rückerstatten oder zuordnen.', Updated=TO_TIMESTAMP('2026-11-19 12:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545999 AND MsgText='Kartenzahlung {0} für Rechnung {1} wurde als Kundenguthaben gebucht – bitte erstatten oder zuordnen.';

-- Message de.metas.pos.InvoiceCardSettlement.RetryRefusedSumUpNotReachable
UPDATE AD_Message SET MsgText='SumUp konnte nicht gefragt werden, ob die Kartenzahlung inzwischen rückerstattet wurde. Bitte später erneut versuchen.', Updated=TO_TIMESTAMP('2026-11-19 12:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546002;
UPDATE AD_Message_Trl SET MsgText='SumUp konnte nicht gefragt werden, ob die Kartenzahlung inzwischen rückerstattet wurde. Bitte später erneut versuchen.', Updated=TO_TIMESTAMP('2026-11-19 12:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546002 AND MsgText='SumUp konnte nicht gefragt werden, ob die Kartenzahlung inzwischen erstattet wurde. Bitte später erneut versuchen.';

-- Element POS_InvoiceCardSettlement_ResolutionNote (process parameter "Notiz"): description + help
UPDATE AD_Element_Trl SET Description='Pflichtangabe: Wie wurde der Fall geklärt bzw. rückerstattet?', Updated=TO_TIMESTAMP('2026-11-19 12:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585612 AND Description='Pflichtangabe: Wie wurde der Fall geklärt bzw. erstattet?';
UPDATE AD_Element_Trl SET Help='Tragen Sie hier ein, was unternommen wurde (z. B. Zahlung manuell zugeordnet oder Rückerstattung im SumUp-Dashboard vorgenommen). Der Text wird als Notiz am Eintrag gespeichert.', Updated=TO_TIMESTAMP('2026-11-19 12:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585612 AND Help='Tragen Sie hier ein, was unternommen wurde (z. B. Zahlung manuell zugeordnet oder Erstattung im SumUp-Dashboard vorgenommen). Der Text wird als Notiz am Eintrag gespeichert.';

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585612, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585612, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585612, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585612, 'fr_CH');
