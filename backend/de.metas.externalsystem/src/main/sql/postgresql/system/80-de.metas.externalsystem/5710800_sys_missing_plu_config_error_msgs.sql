-- Value: de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingPLUConfigForProduct
-- 2023-11-16T09:34:01.240Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545359,0,TO_TIMESTAMP('2023-11-16 10:34:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y','Es konnte keine aktiven PLU Konfiguration für Produkt {0} gefunden werden.','E',TO_TIMESTAMP('2023-11-16 10:34:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingPLUConfigForProduct')
;

-- 2023-11-16T09:34:01.250Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545359 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingPLUConfigForProduct
-- 2023-11-16T09:34:18.140Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-16 10:34:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545359
;

-- Value: de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingPLUConfigForProduct
-- 2023-11-16T09:34:22.742Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-16 10:34:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545359
;

-- Value: de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingPLUConfigForProduct
-- 2023-11-16T09:34:57.447Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No active PLU configuration could be found for product {0}.',Updated=TO_TIMESTAMP('2023-11-16 10:34:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545359
;

-- Value: de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingConfigsInPLUConfigGroup
-- 2023-11-16T09:37:39.694Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545360,0,TO_TIMESTAMP('2023-11-16 10:37:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Es konnten keine aktiven Konfigurationseinträge für die PLU Konfiguration {0} gefunden werden.','E',TO_TIMESTAMP('2023-11-16 10:37:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingConfigsInPLUConfigGroup')
;

-- 2023-11-16T09:37:39.699Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545360 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingConfigsInPLUConfigGroup
-- 2023-11-16T09:37:56.650Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-16 10:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545360
;

-- Value: de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingConfigsInPLUConfigGroup
-- 2023-11-16T09:37:58.337Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-16 10:37:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545360
;

-- Value: de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingConfigsInPLUConfigGroup
-- 2023-11-16T09:38:05.337Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No active configuration entries could be found for PLU configuration {0}.',Updated=TO_TIMESTAMP('2023-11-16 10:38:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545360
;

