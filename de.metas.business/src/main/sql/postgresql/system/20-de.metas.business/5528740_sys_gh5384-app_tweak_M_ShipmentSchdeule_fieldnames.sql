-- 2019-08-11T20:25:43.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576974, Description=NULL, Help=NULL, Name='Beauftragt (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-11 22:25:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501597
;

-- 2019-08-11T20:25:43.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576974) 
;

-- 2019-08-11T20:25:43.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=501597
;

-- 2019-08-11T20:25:43.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(501597)
;

-- 2019-08-11T20:27:08.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Auf Packzettel (Lagereinheit)', PrintName='Auf Packzettel (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-11 22:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541238 AND AD_Language='fr_CH'
;

-- 2019-08-11T20:27:08.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541238,'fr_CH') 
;

-- 2019-08-11T20:27:11.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2019-08-11 22:27:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541238 AND AD_Language='fr_CH'
;

-- 2019-08-11T20:27:11.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541238,'fr_CH') 
;

-- 2019-08-11T20:27:20.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Auf Packzettel (Lagereinheit)', PrintName='Auf Packzettel (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-11 22:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541238 AND AD_Language='de_CH'
;

-- 2019-08-11T20:27:20.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541238,'de_CH') 
;

-- 2019-08-11T20:27:41.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Picked (stock UOM)', PrintName='Picked (stock UOM)',Updated=TO_TIMESTAMP('2019-08-11 22:27:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541238 AND AD_Language='en_US'
;

-- 2019-08-11T20:27:41.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541238,'en_US') 
;

-- 2019-08-11T20:28:17.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Auf Packzettel (Lagereinheit)', PrintName='Auf Packzettel (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-11 22:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541238 AND AD_Language='de_DE'
;

-- 2019-08-11T20:28:17.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541238,'de_DE') 
;

-- 2019-08-11T20:28:17.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541238,'de_DE') 
;

-- 2019-08-11T20:28:17.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyPickList', Name='Auf Packzettel (Lagereinheit)', Description=NULL, Help=NULL WHERE AD_Element_ID=541238
;

-- 2019-08-11T20:28:17.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPickList', Name='Auf Packzettel (Lagereinheit)', Description=NULL, Help=NULL, AD_Element_ID=541238 WHERE UPPER(ColumnName)='QTYPICKLIST' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-11T20:28:17.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPickList', Name='Auf Packzettel (Lagereinheit)', Description=NULL, Help=NULL WHERE AD_Element_ID=541238 AND IsCentrallyMaintained='Y'
;

-- 2019-08-11T20:28:17.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auf Packzettel (Lagereinheit)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541238) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541238)
;

-- 2019-08-11T20:28:17.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auf Packzettel (Lagereinheit)', Name='Auf Packzettel (Lagereinheit)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541238)
;

-- 2019-08-11T20:28:17.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auf Packzettel (Lagereinheit)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541238
;

-- 2019-08-11T20:28:17.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auf Packzettel (Lagereinheit)', Description=NULL, Help=NULL WHERE AD_Element_ID = 541238
;

-- 2019-08-11T20:28:17.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auf Packzettel (Lagereinheit)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541238
;

-- 2019-08-11T20:30:46.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577004,0,'M_ShipmentSchedule_QtyToDeliver',TO_TIMESTAMP('2019-08-11 22:30:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Zu Liefern (Lagereinheit)','Zu Liefern (Lagereinheit)',TO_TIMESTAMP('2019-08-11 22:30:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-11T20:30:46.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577004 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-08-11T20:30:49.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-11 22:30:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577004 AND AD_Language='de_CH'
;

-- 2019-08-11T20:30:49.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577004,'de_CH') 
;

-- 2019-08-11T20:30:53.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-11 22:30:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577004 AND AD_Language='de_DE'
;

-- 2019-08-11T20:30:53.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577004,'de_DE') 
;

-- 2019-08-11T20:30:53.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577004,'de_DE') 
;

-- 2019-08-11T20:31:11.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='To deliver (stock UOM)', PrintName='To deliver (stock UOM)',Updated=TO_TIMESTAMP('2019-08-11 22:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577004 AND AD_Language='en_US'
;

-- 2019-08-11T20:31:11.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577004,'en_US') 
;

-- 2019-08-11T20:31:26.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577004, Name='Zu Liefern (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-11 22:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=500238
;

-- 2019-08-11T20:31:26.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577004) 
;

-- 2019-08-11T20:31:26.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=500238
;

-- 2019-08-11T20:31:26.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(500238)
;

-- 2019-08-11T20:33:16.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Geliefert (Lagereinheit)', PrintName='Geliefert (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-11 22:33:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1000463 AND AD_Language='de_CH'
;

-- 2019-08-11T20:33:16.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000463,'de_CH') 
;

-- 2019-08-11T20:33:32.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Geliefert (Lagereinheit)', PrintName='Geliefert (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-11 22:33:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1000463 AND AD_Language='de_DE'
;

-- 2019-08-11T20:33:32.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000463,'de_DE') 
;

-- 2019-08-11T20:33:32.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1000463,'de_DE') 
;

-- 2019-08-11T20:33:32.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Geliefert (Lagereinheit)', Description='', Help='' WHERE AD_Element_ID=1000463
;

-- 2019-08-11T20:33:32.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Geliefert (Lagereinheit)', Description='', Help='' WHERE AD_Element_ID=1000463 AND IsCentrallyMaintained='Y'
;

-- 2019-08-11T20:33:32.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geliefert (Lagereinheit)', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1000463) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1000463)
;

-- 2019-08-11T20:33:32.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geliefert (Lagereinheit)', Name='Geliefert (Lagereinheit)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1000463)
;

-- 2019-08-11T20:33:32.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geliefert (Lagereinheit)', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 1000463
;

-- 2019-08-11T20:33:32.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geliefert (Lagereinheit)', Description='', Help='' WHERE AD_Element_ID = 1000463
;

-- 2019-08-11T20:33:32.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geliefert (Lagereinheit)', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1000463
;

-- 2019-08-11T20:33:58.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Shipped (stock UOM)', PrintName='Shipped (stock UOM)',Updated=TO_TIMESTAMP('2019-08-11 22:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1000463 AND AD_Language='en_US'
;

-- 2019-08-11T20:33:58.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000463,'en_US') 
;

-- 2019-08-11T20:38:09.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577005,0,'M_ShipmentSchedule_QtyOnHand',TO_TIMESTAMP('2019-08-11 22:38:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bestand (Lagereinheit)','Bestand (Lagereinheit)',TO_TIMESTAMP('2019-08-11 22:38:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-11T20:38:09.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577005 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-08-11T20:38:17.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-11 22:38:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577005 AND AD_Language='de_CH'
;

-- 2019-08-11T20:38:17.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577005,'de_CH') 
;

-- 2019-08-11T20:38:21.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-11 22:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577005 AND AD_Language='de_DE'
;

-- 2019-08-11T20:38:21.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577005,'de_DE') 
;

-- 2019-08-11T20:38:21.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577005,'de_DE') 
;

-- 2019-08-11T20:38:44.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='On hand (stock UOM)', PrintName='On hand (stock UOM)',Updated=TO_TIMESTAMP('2019-08-11 22:38:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577005 AND AD_Language='en_US'
;

-- 2019-08-11T20:38:44.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577005,'en_US') 
;

-- 2019-08-11T20:39:05.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577005, Description=NULL, Help=NULL, Name='Bestand (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-11 22:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=500234
;

-- 2019-08-11T20:39:05.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577005) 
;

-- 2019-08-11T20:39:05.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=500234
;

-- 2019-08-11T20:39:05.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(500234)
;

