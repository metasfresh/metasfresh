-- 2021-11-05T05:29:37.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545074,0,TO_TIMESTAMP('2021-11-05 07:29:37','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Das Produkt der Stückliste muss mit dem Produkt des Produktionsauftrags übereinstimmen','I',TO_TIMESTAMP('2021-11-05 07:29:37','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_BOM_Doesnt_Match')
;

-- 2021-11-05T05:29:37.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545074 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-11-05T05:29:55.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The BOM product needs to match the manufacturing order''s product',Updated=TO_TIMESTAMP('2021-11-05 07:29:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545074
;

-- 2021-11-05T05:32:43.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545075,0,TO_TIMESTAMP('2021-11-05 07:32:43','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Das Produkt der Stückliste muss mit dem Produkt der Stücklistenversion übereinstimmen','E',TO_TIMESTAMP('2021-11-05 07:32:43','YYYY-MM-DD HH24:MI:SS'),100,'PP_Product_BOMVersions_BOM_Doesnt_Match')
;

-- 2021-11-05T05:32:43.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545075 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-11-05T05:32:58.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The BOM product needs to match the BOM version''s product',Updated=TO_TIMESTAMP('2021-11-05 07:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545075
;

-- 2021-11-05T05:33:11.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2021-11-05 07:33:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545074
;

-- 2021-11-05T05:34:40.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545076,0,TO_TIMESTAMP('2021-11-05 07:34:40','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Das Produkt kann nicht geändert werden wenn es schon eine Stücklistenversion gibt','E',TO_TIMESTAMP('2021-11-05 07:34:40','YYYY-MM-DD HH24:MI:SS'),100,'PP_Product_BOMVersions_BOMs_Already_Linked')
;

-- 2021-11-05T05:34:40.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545076 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-11-05T05:34:49.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The product can''t be changed if there is already a BOM version',Updated=TO_TIMESTAMP('2021-11-05 07:34:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545076
;

-- 2021-11-05T05:36:35.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545077,0,TO_TIMESTAMP('2021-11-05 07:36:35','YYYY-MM-DD HH24:MI:SS'),100,'EE04','Y','Das Plandaten-Produkt mit mit dem Stücklistenprodukt übereinstimmen','E',TO_TIMESTAMP('2021-11-05 07:36:35','YYYY-MM-DD HH24:MI:SS'),100,'PP_Product_Planning_BOM_Doesnt_Match')
;

-- 2021-11-05T05:36:35.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545077 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-11-05T05:36:42.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The planning''s product needs to match the BOM''s product',Updated=TO_TIMESTAMP('2021-11-05 07:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545077
;

-- 2021-11-05T05:38:08.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545078,0,TO_TIMESTAMP('2021-11-05 07:38:08','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Es wurde keine Standard-Stückliste für das Produkt {0} gefunden','E',TO_TIMESTAMP('2021-11-05 07:38:08','YYYY-MM-DD HH24:MI:SS'),100,'NO_Default_PP_Product_BOM_For_Product')
;

-- 2021-11-05T05:38:08.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545078 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-11-05T05:38:20.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No Default BOM found for product {0}',Updated=TO_TIMESTAMP('2021-11-05 07:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545078
;

-- 2021-11-05T06:06:32.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Stücklistenversion', Name='Stücklistenversion', PrintName='Stücklistenversion',Updated=TO_TIMESTAMP('2021-11-05 08:06:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53245 AND AD_Language='nl_NL'
;

-- 2021-11-05T06:06:32.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53245,'nl_NL') 
;

-- 2021-11-05T06:06:38.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Stücklistenversion', Name='Stücklistenversion', PrintName='Stücklistenversion',Updated=TO_TIMESTAMP('2021-11-05 08:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53245 AND AD_Language='de_DE'
;

-- 2021-11-05T06:06:38.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53245,'de_DE') 
;

-- 2021-11-05T06:06:38.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(53245,'de_DE') 
;

-- 2021-11-05T06:06:38.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PP_Product_BOM_ID', Name='Stücklistenversion', Description='Stücklistenversion', Help=NULL WHERE AD_Element_ID=53245
;

-- 2021-11-05T06:06:38.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PP_Product_BOM_ID', Name='Stücklistenversion', Description='Stücklistenversion', Help=NULL, AD_Element_ID=53245 WHERE UPPER(ColumnName)='PP_PRODUCT_BOM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-05T06:06:38.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PP_Product_BOM_ID', Name='Stücklistenversion', Description='Stücklistenversion', Help=NULL WHERE AD_Element_ID=53245 AND IsCentrallyMaintained='Y'
;

-- 2021-11-05T06:06:38.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Stücklistenversion', Description='Stücklistenversion', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53245) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53245)
;

-- 2021-11-05T06:06:38.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Stücklistenversion', Name='Stücklistenversion' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53245)
;

-- 2021-11-05T06:06:38.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Stücklistenversion', Description='Stücklistenversion', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 53245
;

-- 2021-11-05T06:06:38.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Stücklistenversion', Description='Stücklistenversion', Help=NULL WHERE AD_Element_ID = 53245
;

-- 2021-11-05T06:06:38.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Stücklistenversion', Description = 'Stücklistenversion', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 53245
;

-- 2021-11-05T06:06:43.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Stücklistenversion', Name='Stücklistenversion', PrintName='Stücklistenversion',Updated=TO_TIMESTAMP('2021-11-05 08:06:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53245 AND AD_Language='de_CH'
;

-- 2021-11-05T06:06:43.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53245,'de_CH') 
;

-- 2021-11-05T06:06:59.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='BOM & Formula Version', Name='BOM & Formula Version', PrintName='BOM & Formula Version',Updated=TO_TIMESTAMP('2021-11-05 08:06:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53245 AND AD_Language='en_GB'
;

-- 2021-11-05T06:06:59.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53245,'en_GB') 
;

-- 2021-11-05T06:07:04.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Stücklistenversion', Name='Stücklistenversion', PrintName='Stücklistenversion',Updated=TO_TIMESTAMP('2021-11-05 08:07:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53245 AND AD_Language='it_CH'
;

-- 2021-11-05T06:07:04.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53245,'it_CH') 
;

-- 2021-11-05T06:07:09.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Stücklistenversion', Name='Stücklistenversion', PrintName='Stücklistenversion',Updated=TO_TIMESTAMP('2021-11-05 08:07:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53245 AND AD_Language='fr_CH'
;

-- 2021-11-05T06:07:09.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53245,'fr_CH') 
;

-- 2021-11-05T06:09:50.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Stückliste', PrintName='Stückliste',Updated=TO_TIMESTAMP('2021-11-05 08:09:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580087 AND AD_Language='de_CH'
;

-- 2021-11-05T06:09:50.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580087,'de_CH') 
;

-- 2021-11-05T06:09:52.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Stückliste', PrintName='Stückliste',Updated=TO_TIMESTAMP('2021-11-05 08:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580087 AND AD_Language='de_DE'
;

-- 2021-11-05T06:09:52.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580087,'de_DE') 
;

-- 2021-11-05T06:09:52.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580087,'de_DE') 
;

-- 2021-11-05T06:09:52.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PP_Product_BOMVersions_ID', Name='Stückliste', Description=NULL, Help=NULL WHERE AD_Element_ID=580087
;

-- 2021-11-05T06:09:52.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PP_Product_BOMVersions_ID', Name='Stückliste', Description=NULL, Help=NULL, AD_Element_ID=580087 WHERE UPPER(ColumnName)='PP_PRODUCT_BOMVERSIONS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-05T06:09:52.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PP_Product_BOMVersions_ID', Name='Stückliste', Description=NULL, Help=NULL WHERE AD_Element_ID=580087 AND IsCentrallyMaintained='Y'
;

-- 2021-11-05T06:09:52.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Stückliste', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580087) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580087)
;

-- 2021-11-05T06:09:52.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Stückliste', Name='Stückliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580087)
;

-- 2021-11-05T06:09:52.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Stückliste', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580087
;

-- 2021-11-05T06:09:52.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Stückliste', Description=NULL, Help=NULL WHERE AD_Element_ID = 580087
;

-- 2021-11-05T06:09:52.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Stückliste', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580087
;

-- 2021-11-05T06:09:57.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Stückliste', PrintName='Stückliste',Updated=TO_TIMESTAMP('2021-11-05 08:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580087 AND AD_Language='nl_NL'
;

-- 2021-11-05T06:09:57.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580087,'nl_NL') 
;

-- 2021-11-05T06:10:06.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='BOM & Formula', PrintName='BOM & Formula',Updated=TO_TIMESTAMP('2021-11-05 08:10:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580087 AND AD_Language='en_US'
;

-- 2021-11-05T06:10:06.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580087,'en_US') 
;

-- 2021-11-05T06:10:54.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bei Wareneing. mit Ziellager', PrintName='Bei Wareneing. mit Ziellager',Updated=TO_TIMESTAMP('2021-11-05 08:10:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543911 AND AD_Language='de_CH'
;

-- 2021-11-05T06:10:54.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543911,'de_CH') 
;

-- 2021-11-05T06:10:58.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bei Wareneing. mit Ziellager', PrintName='Bei Wareneing. mit Ziellager',Updated=TO_TIMESTAMP('2021-11-05 08:10:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543911 AND AD_Language='nl_NL'
;

-- 2021-11-05T06:10:58.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543911,'nl_NL') 
;

-- 2021-11-05T06:11:01.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bei Wareneing. mit Ziellager', PrintName='Bei Wareneing. mit Ziellager',Updated=TO_TIMESTAMP('2021-11-05 08:11:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543911 AND AD_Language='de_DE'
;

-- 2021-11-05T06:11:01.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543911,'de_DE') 
;

-- 2021-11-05T06:11:01.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543911,'de_DE') 
;

-- 2021-11-05T06:11:01.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='OnMaterialReceiptWithDestWarehouse', Name='Bei Wareneing. mit Ziellager', Description=NULL, Help='' WHERE AD_Element_ID=543911
;

-- 2021-11-05T06:11:01.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OnMaterialReceiptWithDestWarehouse', Name='Bei Wareneing. mit Ziellager', Description=NULL, Help='', AD_Element_ID=543911 WHERE UPPER(ColumnName)='ONMATERIALRECEIPTWITHDESTWAREHOUSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-05T06:11:01.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OnMaterialReceiptWithDestWarehouse', Name='Bei Wareneing. mit Ziellager', Description=NULL, Help='' WHERE AD_Element_ID=543911 AND IsCentrallyMaintained='Y'
;

-- 2021-11-05T06:11:01.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bei Wareneing. mit Ziellager', Description=NULL, Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543911) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543911)
;

-- 2021-11-05T06:11:01.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bei Wareneing. mit Ziellager', Name='Bei Wareneing. mit Ziellager' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543911)
;

-- 2021-11-05T06:11:01.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Bei Wareneing. mit Ziellager', Description=NULL, Help='', CommitWarning = NULL WHERE AD_Element_ID = 543911
;

-- 2021-11-05T06:11:01.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Bei Wareneing. mit Ziellager', Description=NULL, Help='' WHERE AD_Element_ID = 543911
;

-- 2021-11-05T06:11:01.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Bei Wareneing. mit Ziellager', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543911
;

-- 2021-11-05T06:11:12.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='On material rcpt with target WH', PrintName='On material rcpt with target WH',Updated=TO_TIMESTAMP('2021-11-05 08:11:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543911 AND AD_Language='en_US'
;

-- 2021-11-05T06:11:12.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543911,'en_US') 
;

-- 2021-11-05T06:12:17.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Distributionsauftrag erst.',Updated=TO_TIMESTAMP('2021-11-05 08:12:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=541592
;

-- 2021-11-05T06:12:21.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Distributionsauftrag erst.',Updated=TO_TIMESTAMP('2021-11-05 08:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541592
;

-- 2021-11-05T06:12:24.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Distributionsauftrag erst.',Updated=TO_TIMESTAMP('2021-11-05 08:12:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=541592
;

-- 2021-11-05T06:12:32.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Create distribution order',Updated=TO_TIMESTAMP('2021-11-05 08:12:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541592
;

-- 2021-11-05T06:12:44.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET EntityType='D', Name='Distributionsauftrag erst.',Updated=TO_TIMESTAMP('2021-11-05 08:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541592
;

-- 2021-11-05T06:13:06.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Create movement',Updated=TO_TIMESTAMP('2021-11-05 08:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=541591
;

-- 2021-11-05T06:13:13.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Warenbewegung erst.',Updated=TO_TIMESTAMP('2021-11-05 08:13:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541591
;

-- 2021-11-05T06:13:16.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Warenbewegung erst.',Updated=TO_TIMESTAMP('2021-11-05 08:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=541591
;

-- 2021-11-05T06:13:20.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Warenbewegung erst.',Updated=TO_TIMESTAMP('2021-11-05 08:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=541591
;

-- 2021-11-05T06:13:27.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Warenbewegung erst.',Updated=TO_TIMESTAMP('2021-11-05 08:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541591
;

