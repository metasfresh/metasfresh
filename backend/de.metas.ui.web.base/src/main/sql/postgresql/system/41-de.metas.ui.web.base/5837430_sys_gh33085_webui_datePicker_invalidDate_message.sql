-- WebUI date widget: notification when a typed text is no date (frontend DatePicker.notifyInvalidDate)
-- IDs allocated from idserver.metas.de on 2026-11-26: AD_Message 546033, 546034; migration script 5837430

-- Value: webui.widget.datePicker.invalidDate.title
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546033/*From ID Server*/,0,TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Ungültiges Datum','I',TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),100,'webui.widget.datePicker.invalidDate.title')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=546033 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Ungültiges Datum',Updated=TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546033
;

UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Ungültiges Datum',Updated=TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546033
;

UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Invalid date',Updated=TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546033
;

UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Date invalide',Updated=TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=546033
;

-- Value: webui.widget.datePicker.invalidDate.text
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546034/*From ID Server*/,0,TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','„%(value)s“ ist kein gültiges Datum. Beispiel: %(example)s','I',TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),100,'webui.widget.datePicker.invalidDate.text')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=546034 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='„%(value)s“ ist kein gültiges Datum. Beispiel: %(example)s',Updated=TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546034
;

UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='„%(value)s“ ist kein gültiges Datum. Beispiel: %(example)s',Updated=TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546034
;

UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='"%(value)s" is not a valid date. Example: %(example)s',Updated=TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546034
;

UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='« %(value)s » n''est pas une date valide. Exemple : %(example)s',Updated=TO_TIMESTAMP('2026-11-26 18:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=546034
;
