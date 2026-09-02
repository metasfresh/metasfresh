-- 2020-06-24T10:16:04.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544985,0,TO_TIMESTAMP('2020-06-24 13:16:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Virtuelle Bestände können nicht manuell erstellt werden!','E',TO_TIMESTAMP('2020-06-24 13:16:04','YYYY-MM-DD HH24:MI:SS'),100,'MSG_WEBUI_ADD_VIRTUAL_INV_NOT_ALLOWED')
;

-- 2020-06-24T10:16:04.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544985 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-06-24T10:16:36.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Virtual inventories cannot be created manually!',Updated=TO_TIMESTAMP('2020-06-24 13:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544985
;

update c_doctype set name = 'Virtueller Bestand', printname = 'Virtueller Bestand' where c_doctype_id = 540975
;

update c_doctype_trl set name = 'Virtueller Bestand', printname = 'Virtueller Bestand' where c_doctype_id = 540975 and ad_language = 'de_CH'
;

update c_doctype_trl set name = 'Virtueller Bestand', printname = 'Virtueller Bestand' where c_doctype_id = 540975 and ad_language = 'nl_NL'
;

update c_doctype_trl set name = 'Virtueller Bestand', printname = 'Virtueller Bestand' where c_doctype_id = 540975 and ad_language = 'de_DE'
;

update c_doctype_trl set name = 'Virtual Inventory', printname = 'Virtual Inventory' where c_doctype_id = 540975 and ad_language = 'en_US'
;