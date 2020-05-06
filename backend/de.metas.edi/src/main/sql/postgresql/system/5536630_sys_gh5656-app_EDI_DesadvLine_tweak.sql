-- 2019-11-22T16:28:37.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyEntered',Updated=TO_TIMESTAMP('2019-11-22 17:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547269
;

-- 2019-11-22T16:29:40.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.ordercandidate',Updated=TO_TIMESTAMP('2019-11-22 17:29:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553757
;

-- 2019-11-22T17:15:29.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=21,Updated=TO_TIMESTAMP('2019-11-22 18:15:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540663
;

-- 2019-11-22T17:15:31.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=30,Updated=TO_TIMESTAMP('2019-11-22 18:15:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540664
;

-- 2019-11-22T17:15:36.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=20,Updated=TO_TIMESTAMP('2019-11-22 18:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540663
;

-- 2019-11-22T17:23:48.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577374,0,TO_TIMESTAMP('2019-11-22 18:23:48','YYYY-MM-DD HH24:MI:SS'),100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit','D','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','Y','Auftragsmenge','Auftragsmenge',TO_TIMESTAMP('2019-11-22 18:23:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-22T17:23:48.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577374 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-11-22T17:23:54.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-22 18:23:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577374 AND AD_Language='de_CH'
;

-- 2019-11-22T17:23:54.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577374,'de_CH') 
;

-- 2019-11-22T17:23:57.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-22 18:23:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577374 AND AD_Language='de_DE'
;

-- 2019-11-22T17:23:57.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577374,'de_DE') 
;

-- 2019-11-22T17:23:57.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577374,'de_DE') 
;

-- 2019-11-22T17:24:11.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Ordered quantity', PrintName='Ordered quantity',Updated=TO_TIMESTAMP('2019-11-22 18:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577374 AND AD_Language='en_US'
;

-- 2019-11-22T17:24:11.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577374,'en_US') 
;

-- 2019-11-22T17:24:18.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577374, Description='Die Eingegebene Menge basiert auf der gewählten Mengeneinheit', Help='Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt', Name='Auftragsmenge',Updated=TO_TIMESTAMP('2019-11-22 18:24:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555461
;

-- 2019-11-22T17:24:18.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577374) 
;

-- 2019-11-22T17:24:18.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=555461
;

-- 2019-11-22T17:24:18.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(555461)
;

-- 2019-11-22T17:24:34.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyEntered',Updated=TO_TIMESTAMP('2019-11-22 18:24:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549238
;

-- 2019-11-22T17:25:32.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-22 18:25:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542776 AND AD_Language='de_CH'
;

-- 2019-11-22T17:25:32.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542776,'de_CH') 
;

-- 2019-11-22T17:25:47.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Delivered quantity', PrintName='Delivered quantity',Updated=TO_TIMESTAMP('2019-11-22 18:25:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542776 AND AD_Language='en_US'
;

-- 2019-11-22T17:25:47.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542776,'en_US') 
;

-- 2019-11-22T17:25:53.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-22 18:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542776 AND AD_Language='de_DE'
;

-- 2019-11-22T17:25:53.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542776,'de_DE') 
;

-- 2019-11-22T17:25:53.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542776,'de_DE') 
;

-- 2019-11-22T17:26:17.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Liefermenge',Updated=TO_TIMESTAMP('2019-11-22 18:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555773
;

-- 2019-11-22T17:26:39.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyDeliveredInUOM',Updated=TO_TIMESTAMP('2019-11-22 18:26:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549239
;

