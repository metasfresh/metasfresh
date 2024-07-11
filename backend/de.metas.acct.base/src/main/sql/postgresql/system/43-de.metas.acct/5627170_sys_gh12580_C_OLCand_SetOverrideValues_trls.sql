UPDATE AD_Process
SET classname='de.metas.ordercandidate.process.C_OLCand_SetOverrideValues', updated=TO_TIMESTAMP('2022-02-022 12:12:12', 'YYYY-MM-DD HH24:MI:SS'), updatedBy=100
WHERE classname = 'de.metas.ordercandidate.process.OLCandSetOverrideValues'
;

-- 2022-02-22T16:27:44.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545098,0,TO_TIMESTAMP('2022-02-22 18:27:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kein Standort verfügbar und keine GLN für die betreffenden Einträge vorhanden.','E',TO_TIMESTAMP('2022-02-22 18:27:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate.process.C_OLCand_SetOverrideValues.NoGLNs')
;

-- 2022-02-22T16:27:44.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545098 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-02-22T16:28:35.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545100,0,TO_TIMESTAMP('2022-02-22 18:28:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kein Standort mit GLN: {0} für den betreffenden Geschäftspartner vorhanden.','E',TO_TIMESTAMP('2022-02-22 18:28:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate.process.C_OLCand_SetOverrideValues.NoLocationForGLN')
;

-- 2022-02-22T16:28:35.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545100 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-02-22T16:28:48.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No location with GLN: {0} found for the given business partner.',Updated=TO_TIMESTAMP('2022-02-22 18:28:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545100
;

-- 2022-02-22T16:29:05.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No location provided and no GLN found for the given records.',Updated=TO_TIMESTAMP('2022-02-22 18:29:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545098
;


-- 2022-02-22T16:46:53.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,Updated,UpdatedBy) VALUES (0,580609,0,TO_TIMESTAMP('2022-02-22 18:46:53','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners. Bleibt das Feld leer und wird ein Geschäftspartner angegeben, wird ein mit diesem Partner verknüpfter Standort mit derselben GLN verwendet.','D','Identifiziert die Adresse des Geschäftspartners','Y','Standort abw.','Identifiziert die (Liefer-) Adresse des Geschäftspartners. Bleibt das Feld leer und wird ein Geschäftspartner angegeben, wird ein mit diesem Partner verknüpfter Standort mit derselben GLN verwendet.','Identifiziert die Adresse des Geschäftspartners','Standort','Standort','Standort abw.',TO_TIMESTAMP('2022-02-22 18:46:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-22T16:46:53.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580609 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-02-22T16:47:23.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Identifies the (delivery) address of the business partner. If left blank and a business partner is provided, a location associated with this partner that has the same GLN will be used.', Help='Identifies the (delivery) address of the business partner. If left blank and a business partner is provided, a location associated with this partner that has the same GLN will be used.', PO_Description='Identifies the (delivery) address of the business partner. If left blank and a business partner is provided, a location associated with this partner that has the same GLN will be used.', PO_Help='Identifies the (delivery) address of the business partner. If left blank and a business partner is provided, a location associated with this partner that has the same GLN will be used.',Updated=TO_TIMESTAMP('2022-02-22 18:47:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580609 AND AD_Language='en_US'
;

-- 2022-02-22T16:47:23.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580609,'en_US')
;

-- 2022-02-22T16:48:34.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='C_BPartner_Location_Override_ID',Updated=TO_TIMESTAMP('2022-02-22 18:48:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580609
;

-- 2022-02-22T16:48:34.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_Location_Override_ID', Name='Standort abw.', Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners. Bleibt das Feld leer und wird ein Geschäftspartner angegeben, wird ein mit diesem Partner verknüpfter Standort mit derselben GLN verwendet.', Help='Identifiziert die Adresse des Geschäftspartners' WHERE AD_Element_ID=580609
;

-- 2022-02-22T16:48:34.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Location_Override_ID', Name='Standort abw.', Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners. Bleibt das Feld leer und wird ein Geschäftspartner angegeben, wird ein mit diesem Partner verknüpfter Standort mit derselben GLN verwendet.', Help='Identifiziert die Adresse des Geschäftspartners', AD_Element_ID=580609 WHERE UPPER(ColumnName)='C_BPARTNER_LOCATION_OVERRIDE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-02-22T16:48:34.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Location_Override_ID', Name='Standort abw.', Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners. Bleibt das Feld leer und wird ein Geschäftspartner angegeben, wird ein mit diesem Partner verknüpfter Standort mit derselben GLN verwendet.', Help='Identifiziert die Adresse des Geschäftspartners' WHERE AD_Element_ID=580609 AND IsCentrallyMaintained='Y'
;

-- 2022-02-22T16:55:50.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=540533
;

-- 2022-02-22T16:55:50.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=540533
;

-- 2022-02-22T16:56:37.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,580609,0,540437,542211,18,159,540227,'C_BPartner_Location_Override_ID',TO_TIMESTAMP('2022-02-22 18:56:37','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners. Bleibt das Feld leer und wird ein Geschäftspartner angegeben, wird ein mit diesem Partner verknüpfter Standort mit derselben GLN verwendet.','de.metas.ordercandidate',0,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','Standort abw.',20,TO_TIMESTAMP('2022-02-22 18:56:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-22T16:56:37.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542211 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-02-22T16:56:54.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Location_Override_ID',Updated=TO_TIMESTAMP('2022-02-22 18:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542211
;

