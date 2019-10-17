-- 2019-01-13T13:31:15.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-13 13:31:15','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preisgrundlage',PrintName='Preisgrundlage' WHERE AD_Element_ID=543953 AND AD_Language='de_CH'
;

-- 2019-01-13T13:31:15.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543953,'de_CH') 
;

-- 2019-01-13T13:31:24.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-13 13:31:24','YYYY-MM-DD HH24:MI:SS'),Name='Price base',PrintName='Price base' WHERE AD_Element_ID=543953 AND AD_Language='en_US'
;

-- 2019-01-13T13:31:24.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543953,'en_US') 
;

-- 2019-01-13T13:31:33.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-13 13:31:33','YYYY-MM-DD HH24:MI:SS'),Name='Preisgrundlage',PrintName='Preisgrundlage' WHERE AD_Element_ID=543953 AND AD_Language='nl_NL'
;

-- 2019-01-13T13:31:33.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543953,'nl_NL') 
;

-- 2019-01-13T13:31:40.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-13 13:31:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preisgrundlage',PrintName='Preisgrundlage' WHERE AD_Element_ID=543953 AND AD_Language='de_DE'
;

-- 2019-01-13T13:31:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543953,'de_DE') 
;

-- 2019-01-13T13:31:41.030
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543953,'de_DE') 
;

-- 2019-01-13T13:31:41.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PriceBase', Name='Preisgrundlage', Description=NULL, Help=NULL WHERE AD_Element_ID=543953
;

-- 2019-01-13T13:31:41.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceBase', Name='Preisgrundlage', Description=NULL, Help=NULL, AD_Element_ID=543953 WHERE UPPER(ColumnName)='PRICEBASE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-13T13:31:41.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceBase', Name='Preisgrundlage', Description=NULL, Help=NULL WHERE AD_Element_ID=543953 AND IsCentrallyMaintained='Y'
;

-- 2019-01-13T13:31:41.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preisgrundlage', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543953) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543953)
;

-- 2019-01-13T13:31:41.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preisgrundlage', Name='Preisgrundlage' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543953)
;

-- 2019-01-13T13:31:41.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Preisgrundlage', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543953
;

-- 2019-01-13T13:31:41.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Preisgrundlage', Description=NULL, Help=NULL WHERE AD_Element_ID = 543953
;

-- 2019-01-13T13:31:41.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Preisgrundlage', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543953
;

-- 2019-01-13T13:47:45.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575937,0,TO_TIMESTAMP('2019-01-13 13:47:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pricing','Y','Festpreis','Festpreis',TO_TIMESTAMP('2019-01-13 13:47:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-13T13:47:45.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575937 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-13T13:47:50.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-13 13:47:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575937 AND AD_Language='de_CH'
;

-- 2019-01-13T13:47:50.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575937,'de_CH') 
;

-- 2019-01-13T13:47:53.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-13 13:47:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575937 AND AD_Language='de_DE'
;

-- 2019-01-13T13:47:53.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575937,'de_DE') 
;

-- 2019-01-13T13:47:53.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575937,'de_DE') 
;

-- 2019-01-13T13:48:03.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-13 13:48:03','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Fixed price',PrintName='Fixed price' WHERE AD_Element_ID=575937 AND AD_Language='en_US'
;

-- 2019-01-13T13:48:03.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575937,'en_US') 
;

-- 2019-01-13T13:48:07.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=622222
;

-- 2019-01-13T13:48:08.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,957,625056,563197,0,233,TO_TIMESTAMP('2019-01-13 13:48:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-01-13 13:48:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-13T13:48:12.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=575937, Description=NULL, Help=NULL, Name='Festpreis',Updated=TO_TIMESTAMP('2019-01-13 13:48:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563197
;

-- 2019-01-13T13:48:12.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625056
;

-- 2019-01-13T13:48:13.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575937,625057,563197,0,233,TO_TIMESTAMP('2019-01-13 13:48:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-01-13 13:48:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-13T13:48:13.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575937) 
;

-- 2019-01-13T13:49:25.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1229 AND AD_Language='fr_CH'
;

-- 2019-01-13T13:49:25.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1229 AND AD_Language='it_CH'
;

-- 2019-01-13T13:49:25.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1229 AND AD_Language='en_GB'
;

-- 2019-01-13T13:49:44.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-13 13:49:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='',Help='' WHERE AD_Element_ID=1229 AND AD_Language='de_CH'
;

-- 2019-01-13T13:49:44.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1229,'de_CH') 
;

-- 2019-01-13T13:49:54.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-13 13:49:54','YYYY-MM-DD HH24:MI:SS'),Description='',Help='' WHERE AD_Element_ID=1229 AND AD_Language='de_DE'
;

-- 2019-01-13T13:49:54.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1229,'de_DE') 
;

-- 2019-01-13T13:49:54.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1229,'de_DE') 
;

-- 2019-01-13T13:49:54.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Std_AddAmt', Name='Aufschlag auf Standardpreis', Description='', Help='' WHERE AD_Element_ID=1229
;

-- 2019-01-13T13:49:54.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Std_AddAmt', Name='Aufschlag auf Standardpreis', Description='', Help='', AD_Element_ID=1229 WHERE UPPER(ColumnName)='STD_ADDAMT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-13T13:49:54.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Std_AddAmt', Name='Aufschlag auf Standardpreis', Description='', Help='' WHERE AD_Element_ID=1229 AND IsCentrallyMaintained='Y'
;

-- 2019-01-13T13:49:55.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aufschlag auf Standardpreis', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1229) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1229)
;

-- 2019-01-13T13:49:55.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Aufschlag auf Standardpreis', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 1229
;

-- 2019-01-13T13:49:55.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Aufschlag auf Standardpreis', Description='', Help='' WHERE AD_Element_ID = 1229
;

-- 2019-01-13T13:49:55.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Aufschlag auf Standardpreis', Description='', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1229
;

-- 2019-01-13T13:56:24.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=185,Updated=TO_TIMESTAMP('2019-01-13 13:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552443
;


-- 2019-01-14T08:21:26.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET EntityType='de.metas.pricing',Updated=TO_TIMESTAMP('2019-01-14 08:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000093
;

-- 2019-01-14T08:22:56.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 08:22:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='',Help='' WHERE AD_Element_ID=957 AND AD_Language='fr_CH'
;

-- 2019-01-14T08:22:56.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(957,'fr_CH') 
;

-- 2019-01-14T08:23:01.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=957 AND AD_Language='it_CH'
;

-- 2019-01-14T08:23:03.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=957 AND AD_Language='en_GB'
;

-- 2019-01-14T08:23:10.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 08:23:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='',Help='' WHERE AD_Element_ID=957 AND AD_Language='de_CH'
;

-- 2019-01-14T08:23:10.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(957,'de_CH') 
;

-- 2019-01-14T08:23:24.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 08:23:24','YYYY-MM-DD HH24:MI:SS'),Description='',Help='' WHERE AD_Element_ID=957 AND AD_Language='nl_NL'
;

-- 2019-01-14T08:23:24.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(957,'nl_NL') 
;

-- 2019-01-14T08:23:29.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 08:23:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='',Help='' WHERE AD_Element_ID=957 AND AD_Language='de_DE'
;

-- 2019-01-14T08:23:29.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(957,'de_DE') 
;

-- 2019-01-14T08:23:29.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(957,'de_DE') 
;

-- 2019-01-14T08:23:29.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PriceStd', Name='Standardpreis', Description='', Help='' WHERE AD_Element_ID=957
;

-- 2019-01-14T08:23:29.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceStd', Name='Standardpreis', Description='', Help='', AD_Element_ID=957 WHERE UPPER(ColumnName)='PRICESTD' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-14T08:23:29.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceStd', Name='Standardpreis', Description='', Help='' WHERE AD_Element_ID=957 AND IsCentrallyMaintained='Y'
;

-- 2019-01-14T08:23:29.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Standardpreis', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=957) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 957)
;

-- 2019-01-14T08:23:29.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Standardpreis', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 957
;

-- 2019-01-14T08:23:29.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Standardpreis', Description='', Help='' WHERE AD_Element_ID = 957
;

-- 2019-01-14T08:23:29.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Standardpreis', Description='', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 957
;

-- 2019-01-14T08:23:36.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 08:23:36','YYYY-MM-DD HH24:MI:SS'),Description='' WHERE AD_Element_ID=957 AND AD_Language='en_US'
;

-- 2019-01-14T08:23:36.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(957,'en_US') 
;

----------------------------------------------


-- 2019-01-14T11:01:18.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='', Name='M_DiscountSchemaBreak',Updated=TO_TIMESTAMP('2019-01-14 11:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=476
;

-- 2019-01-14T11:21:32.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575939,0,'PricingSystemSurcharge',TO_TIMESTAMP('2019-01-14 11:21:32','YYYY-MM-DD HH24:MI:SS'),100,'Aufschlag auf den Preis, der aus dem Preissystem resultieren würde','de.metas.pricing','Y','Preisaufschlag','Preisaufschlag',TO_TIMESTAMP('2019-01-14 11:21:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-14T11:21:32.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575939 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-14T11:21:37.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 11:21:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575939 AND AD_Language='de_CH'
;

-- 2019-01-14T11:21:37.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575939,'de_CH') 
;

-- 2019-01-14T11:21:39.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 11:21:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575939 AND AD_Language='de_DE'
;

-- 2019-01-14T11:21:39.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575939,'de_DE') 
;

-- 2019-01-14T11:21:39.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575939,'de_DE') 
;

-- 2019-01-14T11:22:39.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 11:22:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Surcharge',PrintName='Surcharge',Description='Surcharge on the price that would result from the pricing system' WHERE AD_Element_ID=575939 AND AD_Language='en_US'
;

-- 2019-01-14T11:22:39.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575939,'en_US') 
;

-- 2019-01-14T11:23:09.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=575939, ColumnName='PricingSystemSurcharge', Description='Aufschlag auf den Preis, der aus dem Preissystem resultieren würde', EntityType='de.metas.pricing', Help=NULL, Name='Preisaufschlag',Updated=TO_TIMESTAMP('2019-01-14 11:23:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559658
;

-- 2019-01-14T11:23:09.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preisaufschlag', Description='Aufschlag auf den Preis, der aus dem Preissystem resultieren würde', Help=NULL WHERE AD_Column_ID=559658
;

-- 2019-01-14T11:29:47.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-01-14 11:29:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559658
;

-- 2019-01-14T11:30:47.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='PricingSystemSurchargeAmt',Updated=TO_TIMESTAMP('2019-01-14 11:30:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575939
;

-- 2019-01-14T11:30:47.006
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PricingSystemSurchargeAmt', Name='Preisaufschlag', Description='Aufschlag auf den Preis, der aus dem Preissystem resultieren würde', Help=NULL WHERE AD_Element_ID=575939
;

-- 2019-01-14T11:30:47.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PricingSystemSurchargeAmt', Name='Preisaufschlag', Description='Aufschlag auf den Preis, der aus dem Preissystem resultieren würde', Help=NULL, AD_Element_ID=575939 WHERE UPPER(ColumnName)='PRICINGSYSTEMSURCHARGEAMT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-14T11:30:47.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PricingSystemSurchargeAmt', Name='Preisaufschlag', Description='Aufschlag auf den Preis, der aus dem Preissystem resultieren würde', Help=NULL WHERE AD_Element_ID=575939 AND IsCentrallyMaintained='Y'
;

-- 2019-01-14T11:59:25.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=575939, ColumnName='PricingSystemSurchargeAmt', Description='Aufschlag auf den Preis, der aus dem Preissystem resultieren würde', EntityType='de.metas.pricing', Help=NULL, IsMandatory='Y', Name='Preisaufschlag',Updated=TO_TIMESTAMP('2019-01-14 11:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559787
;

-- 2019-01-14T11:59:25.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preisaufschlag', Description='Aufschlag auf den Preis, der aus dem Preissystem resultieren würde', Help=NULL WHERE AD_Column_ID=559787
;

-- 2019-01-14T12:00:03.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='I_DiscountSchema',Updated=TO_TIMESTAMP('2019-01-14 12:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540963
;

-- 2019-01-14T12:01:40.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-01-14 12:01:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559787
;

-- 2019-01-14T12:05:29.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsAlwaysUpdateable='Y', MandatoryLogic='(@PriceBase/P@ = P & @PricingSystemSurchargeAmt/0@ ! 0) | @PriceBase/P@ = F', TechnicalNote='This is the currency of PriceStd or PricingSystemSurchargeAmt; only relevant if @PriceBase@ = F, or if @PriceBase@ = P and a PricingSystemSurchargeAmt != 0 was set',Updated=TO_TIMESTAMP('2019-01-14 12:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560678
;

-- 2019-01-14T12:06:19.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', MandatoryLogic='(@PriceBase/P@ = P & @PricingSystemSurchargeAmt/0@ ! 0) | @PriceBase/P@ = F', TechnicalNote='This is the currency of PriceStd or PricingSystemSurchargeAmt; only relevant if @PriceBase@ = F, or if @PriceBase@ = P and a PricingSystemSurchargeAmt != 0 was set.',Updated=TO_TIMESTAMP('2019-01-14 12:06:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559787
;

-- 2019-01-14T12:06:52.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', MandatoryLogic='', TechnicalNote='',Updated=TO_TIMESTAMP('2019-01-14 12:06:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559787
;

-- 2019-01-14T12:07:13.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='(@PriceBase/P@ = P & @PricingSystemSurchargeAmt/0@ ! 0) | @PriceBase/P@ = F', TechnicalNote='This is the currency of PriceStd or PricingSystemSurchargeAmt; only relevant if @PriceBase@ = F, or if @PriceBase@ = P and a PricingSystemSurchargeAmt != 0 was set.',Updated=TO_TIMESTAMP('2019-01-14 12:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560679
;

-- 2019-01-14T12:07:31.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='This is the currency of PriceStd or PricingSystemSurchargeAmt; only relevant if @PriceBase@ = F, or if @PriceBase@ = P and a PricingSystemSurchargeAmt != 0 was set.',Updated=TO_TIMESTAMP('2019-01-14 12:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560678
;

-- 2019-01-14T12:07:51.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@PriceBase/P@ = F',Updated=TO_TIMESTAMP('2019-01-14 12:07:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559786
;

-- 2019-01-14T12:24:46.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FixedStdPrice',Updated=TO_TIMESTAMP('2019-01-14 12:24:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1746
;

-- 2019-01-14T12:24:46.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FixedStdPrice', Name='Fixed Standard Price', Description='Fixed Standard Price (not calculated)', Help=NULL WHERE AD_Element_ID=1746
;

-- 2019-01-14T12:24:46.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FixedStdPrice', Name='Fixed Standard Price', Description='Fixed Standard Price (not calculated)', Help=NULL, AD_Element_ID=1746 WHERE UPPER(ColumnName)='FIXEDSTDPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-14T12:24:46.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FixedStdPrice', Name='Fixed Standard Price', Description='Fixed Standard Price (not calculated)', Help=NULL WHERE AD_Element_ID=1746 AND IsCentrallyMaintained='Y'
;

-- 2019-01-14T12:25:19.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='PriceStdFixed',Updated=TO_TIMESTAMP('2019-01-14 12:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1746
;

-- 2019-01-14T12:25:19.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PriceStdFixed', Name='Fixed Standard Price', Description='Fixed Standard Price (not calculated)', Help=NULL WHERE AD_Element_ID=1746
;

-- 2019-01-14T12:25:19.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceStdFixed', Name='Fixed Standard Price', Description='Fixed Standard Price (not calculated)', Help=NULL, AD_Element_ID=1746 WHERE UPPER(ColumnName)='PRICESTDFIXED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-14T12:25:19.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceStdFixed', Name='Fixed Standard Price', Description='Fixed Standard Price (not calculated)', Help=NULL WHERE AD_Element_ID=1746 AND IsCentrallyMaintained='Y'
;

-- 2019-01-14T12:26:48.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=1746, AD_Reference_ID=37, ColumnName='PriceStdFixed', Description='Fixed Standard Price (not calculated)', Help=NULL, Name='Fixed Standard Price',Updated=TO_TIMESTAMP('2019-01-14 12:26:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559786
;

-- 2019-01-14T12:26:48.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fixed Standard Price', Description='Fixed Standard Price (not calculated)', Help=NULL WHERE AD_Column_ID=559786
;

-- 2019-01-14T12:27:08.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=1746, ColumnName='PriceStdFixed', Description='Fixed Standard Price (not calculated)', Help=NULL, Name='Fixed Standard Price',Updated=TO_TIMESTAMP('2019-01-14 12:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559659
;

-- 2019-01-14T12:27:08.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fixed Standard Price', Description='Fixed Standard Price (not calculated)', Help=NULL WHERE AD_Column_ID=559659
;

-- 2019-01-14T12:27:33.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1746 AND AD_Language='fr_CH'
;

-- 2019-01-14T12:27:33.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1746 AND AD_Language='it_CH'
;

-- 2019-01-14T12:27:33.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1746 AND AD_Language='en_GB'
;

-- 2019-01-14T12:28:29.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 12:28:29','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Festpreis',PrintName='Festpreis',Description='' WHERE AD_Element_ID=1746 AND AD_Language='de_CH'
;

-- 2019-01-14T12:28:29.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1746,'de_CH') 
;

-- 2019-01-14T12:28:45.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 12:28:45','YYYY-MM-DD HH24:MI:SS'),Name='Fixed price',PrintName='Fixed price',Description='' WHERE AD_Element_ID=1746 AND AD_Language='en_US'
;

-- 2019-01-14T12:28:45.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1746,'en_US') 
;

-- 2019-01-14T12:28:58.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 12:28:58','YYYY-MM-DD HH24:MI:SS'),Name='Festpreis',PrintName='Festpreis',Description='' WHERE AD_Element_ID=1746 AND AD_Language='nl_NL'
;

-- 2019-01-14T12:28:58.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1746,'nl_NL') 
;

-- 2019-01-14T12:29:26.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 12:29:26','YYYY-MM-DD HH24:MI:SS'),Description='Festpreis, ohne ggf. zusätzliche Rabatte' WHERE AD_Element_ID=1746 AND AD_Language='nl_NL'
;

-- 2019-01-14T12:29:26.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1746,'nl_NL') 
;

-- 2019-01-14T12:29:37.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-14 12:29:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Festpreis',PrintName='Festpreis',Description='Festpreis, ohne ggf. zusätzliche Rabatte' WHERE AD_Element_ID=1746 AND AD_Language='de_DE'
;

-- 2019-01-14T12:29:37.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1746,'de_DE') 
;

-- 2019-01-14T12:29:37.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1746,'de_DE') 
;

-- 2019-01-14T12:29:37.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PriceStdFixed', Name='Festpreis', Description='Festpreis, ohne ggf. zusätzliche Rabatte', Help=NULL WHERE AD_Element_ID=1746
;

-- 2019-01-14T12:29:37.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceStdFixed', Name='Festpreis', Description='Festpreis, ohne ggf. zusätzliche Rabatte', Help=NULL, AD_Element_ID=1746 WHERE UPPER(ColumnName)='PRICESTDFIXED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-14T12:29:37.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PriceStdFixed', Name='Festpreis', Description='Festpreis, ohne ggf. zusätzliche Rabatte', Help=NULL WHERE AD_Element_ID=1746 AND IsCentrallyMaintained='Y'
;

-- 2019-01-14T12:29:37.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Festpreis', Description='Festpreis, ohne ggf. zusätzliche Rabatte', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1746) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1746)
;

-- 2019-01-14T12:29:37.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Festpreis', Name='Festpreis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1746)
;

-- 2019-01-14T12:29:37.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Festpreis', Description='Festpreis, ohne ggf. zusätzliche Rabatte', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 1746
;

-- 2019-01-14T12:29:37.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Festpreis', Description='Festpreis, ohne ggf. zusätzliche Rabatte', Help=NULL WHERE AD_Element_ID = 1746
;

-- 2019-01-14T12:29:37.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Festpreis', Description='Festpreis, ohne ggf. zusätzliche Rabatte', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1746
;

-- 2019-01-14T12:30:31.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625057
;

-- 2019-01-14T12:30:31.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,575937,625058,563197,0,233,TO_TIMESTAMP('2019-01-14 12:30:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-01-14 12:30:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-14T12:30:32.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Festpreis, ohne ggf. zusätzliche Rabatte', Name='Festpreis',Updated=TO_TIMESTAMP('2019-01-14 12:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563197
;

-- 2019-01-14T12:30:32.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625058
;

-- 2019-01-14T12:30:32.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1746,625059,563197,0,233,TO_TIMESTAMP('2019-01-14 12:30:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-01-14 12:30:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-14T12:30:32.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1746) 
;

-- 2019-01-14T12:30:42.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=575937
;

-- 2019-01-14T12:30:42.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=575937
;

-- 2019-01-14T12:31:40.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@PriceBase/F@ = F',Updated=TO_TIMESTAMP('2019-01-14 12:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563197
;

-- 2019-01-14T12:32:17.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='(@PriceBase/P@ = P & @PricingSystemSurchargeAmt/0@ ! 0) | @PriceBase/P@ = F',Updated=TO_TIMESTAMP('2019-01-14 12:32:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565109
;
