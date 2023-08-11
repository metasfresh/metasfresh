-- Value: de.metas.printing.external.system.config.error
-- 2023-07-26T17:48:59.084Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545308,0,TO_TIMESTAMP('2023-07-26 19:48:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','External System Printing Client doesn''t support Printing Queues containing Printers with different External System Configurations.','E',TO_TIMESTAMP('2023-07-26 19:48:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing.external.system.config.error')
;

-- 2023-07-26T17:48:59.089Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545308 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.printing.external.system.config.error
-- 2023-07-26T17:49:17.887Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-26 19:49:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545308
;

-- Value: de.metas.printing.external.system.config.error
-- 2023-07-26T17:51:28.238Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Externes System Druck Client unterstützt keine Druckwarteschlangen Einträge, die Drucker mit unterschiedlichen Externes System Konfigurationen enthalten.',Updated=TO_TIMESTAMP('2023-07-26 19:51:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545308
;

-- Value: de.metas.printing.external.system.config.error
-- 2023-07-26T17:51:35.790Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Externes System Druck Client unterstützt keine Druckwarteschlangen Einträge, die Drucker mit unterschiedlichen Externes System Konfigurationen enthalten.',Updated=TO_TIMESTAMP('2023-07-26 19:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545308
;

