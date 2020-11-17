-- 2017-10-09T08:43:39.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540372,'DocAction IN ''CO''',TO_TIMESTAMP('2017-10-09 08:43:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','M_ForeCast_DocActions','S',TO_TIMESTAMP('2017-10-09 08:43:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-09T08:55:34.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Es müssen Produktions- oder Verteilungsdatensätze ausgewählt werden',Updated=TO_TIMESTAMP('2017-10-09 08:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544534
;

-- 2017-10-09T08:58:27.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544535,0,TO_TIMESTAMP('2017-10-09 08:58:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Fertig gestellte Prognose-Dokumente können nicht mehr geändert werden, weil das zu Inkonsistenzen in der Material-Dispo führen würde.','I',TO_TIMESTAMP('2017-10-09 08:58:27','YYYY-MM-DD HH24:MI:SS'),100,'M_Forecast_DocAction_Not_Allowed_After_Completion')
;

-- 2017-10-09T08:58:27.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544535 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

