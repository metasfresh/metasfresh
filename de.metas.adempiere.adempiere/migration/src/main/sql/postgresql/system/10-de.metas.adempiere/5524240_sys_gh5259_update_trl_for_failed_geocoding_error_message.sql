-- 2019-06-11T08:26:14.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544928,0,TO_TIMESTAMP('2019-06-11 08:26:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Coordinates for location not found.','I',TO_TIMESTAMP('2019-06-11 08:26:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.document.filter.provider.locationAreaSearch.LocationAreaSearchDocumentFilterConverter.CoordinatesForLocationNotFound')
;

-- 2019-06-11T08:26:14.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544928 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-06-11T08:30:39.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-11 08:30:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544928
;

-- 2019-06-11T08:30:56.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Zur eingegebenen Adresse wurden keine Koordinaten gefunden', Value='de.metas.ui.web.document.filter.provider.locationAreaSearch.LocationAreaSearchDocumentFilterConverter.NoCoordinatesFoundForTheGivenLocation',Updated=TO_TIMESTAMP('2019-06-11 08:30:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544928
;

-- 2019-06-11T08:31:11.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Zur eingegebenen Adresse wurden keine Koordinaten gefunden',Updated=TO_TIMESTAMP('2019-06-11 08:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544928
;

-- 2019-06-11T08:31:13.903
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No coordinates found for the given location',Updated=TO_TIMESTAMP('2019-06-11 08:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544928
;

-- 2019-06-11T08:31:16.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No coordinates found for the given location',Updated=TO_TIMESTAMP('2019-06-11 08:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544928
;

