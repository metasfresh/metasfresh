-- 2021-07-01T12:18:14.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545042,0,TO_TIMESTAMP('2021-07-01 15:18:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The selected warehouse needs to have a plant set','E',TO_TIMESTAMP('2021-07-01 15:18:14','YYYY-MM-DD HH24:MI:SS'),100,'Fresh_QtyOnHand_Line.MissingWarehousePlant')
;

-- 2021-07-01T12:18:14.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545042 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-07-01T12:18:23.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Im gewählten Lager muss eine Produktionsstätte hinterlegt sein',Updated=TO_TIMESTAMP('2021-07-01 15:18:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545042
;

-- 2021-07-01T12:18:25.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Im gewählten Lager muss eine Produktionsstätte hinterlegt sein',Updated=TO_TIMESTAMP('2021-07-01 15:18:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545042
;

-- 2021-07-01T12:18:30.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Im gewählten Lager muss eine Produktionsstätte hinterlegt sein',Updated=TO_TIMESTAMP('2021-07-01 15:18:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545042
;

-- 2021-07-01T12:27:18.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Im gewählten Lager muss eine Produktionsstätte hinterlegt sein',Updated=TO_TIMESTAMP('2021-07-01 15:27:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545042
;

