-- 2017-11-08T14:47:35.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543470,0,'IsShipmentDateToday',TO_TIMESTAMP('2017-11-08 14:47:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','IsShipmentDateToday','IsShipmentDateToday',TO_TIMESTAMP('2017-11-08 14:47:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-08T14:47:35.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543470 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-11-08T14:49:43.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543470,0,540458,541236,20,'IsShipmentDateToday',TO_TIMESTAMP('2017-11-08 14:49:43','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inout',0,'Y','N','Y','N','Y','N','Lieferdatum heute',30,TO_TIMESTAMP('2017-11-08 14:49:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-08T14:49:43.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541236 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-11-08T14:51:14.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-08 14:51:14','YYYY-MM-DD HH24:MI:SS'),Name='Shipment Date Today' WHERE AD_Process_Para_ID=541236 AND AD_Language='en_US'
;

-- 2017-11-08T14:52:23.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-08 14:52:23','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Lieferdatum heute',PrintName='Lieferdatum heute' WHERE AD_Element_ID=543470 AND AD_Language='de_CH'
;

-- 2017-11-08T14:52:23.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543470,'de_CH') 
;

-- 2017-11-08T14:52:41.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-08 14:52:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Shipment Date Today',PrintName='Shipment Date Today' WHERE AD_Element_ID=543470 AND AD_Language='en_US'
;

-- 2017-11-08T14:52:41.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543470,'en_US') 
;

