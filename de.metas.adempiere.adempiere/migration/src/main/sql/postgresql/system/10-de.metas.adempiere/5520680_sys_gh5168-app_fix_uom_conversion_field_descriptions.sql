-- 2019-04-29T16:40:14.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=217 AND AD_Language='fr_CH'
;

-- 2019-04-29T16:40:14.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=217 AND AD_Language='it_CH'
;

-- 2019-04-29T16:40:14.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=217 AND AD_Language='en_GB'
;

-- 2019-04-29T16:41:18.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit, in die eine bestimmte Menge konvertiert werden soll', Help='', IsTranslated='Y', Name='Ziel-Maßeinheit', PrintName='Ziel-Maßeinheit',Updated=TO_TIMESTAMP('2019-04-29 16:41:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='de_CH'
;

-- 2019-04-29T16:41:18.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'de_CH') 
;

-- 2019-04-29T16:41:31.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ziel-Maßeinheit', PrintName='Ziel-Maßeinheit',Updated=TO_TIMESTAMP('2019-04-29 16:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='de_DE'
;

-- 2019-04-29T16:41:31.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'de_DE') 
;

-- 2019-04-29T16:41:31.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(217,'de_DE') 
;

-- 2019-04-29T16:41:31.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_To_ID', Name='Ziel-Maßeinheit', Description='Zielmaßeinheit', Help='"Maßeinheit Nach" gibt die Zielmaßeinheit für eine Umrechnung an.' WHERE AD_Element_ID=217
;

-- 2019-04-29T16:41:31.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_To_ID', Name='Ziel-Maßeinheit', Description='Zielmaßeinheit', Help='"Maßeinheit Nach" gibt die Zielmaßeinheit für eine Umrechnung an.', AD_Element_ID=217 WHERE UPPER(ColumnName)='C_UOM_TO_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-29T16:41:31.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_To_ID', Name='Ziel-Maßeinheit', Description='Zielmaßeinheit', Help='"Maßeinheit Nach" gibt die Zielmaßeinheit für eine Umrechnung an.' WHERE AD_Element_ID=217 AND IsCentrallyMaintained='Y'
;

-- 2019-04-29T16:41:31.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ziel-Maßeinheit', Description='Zielmaßeinheit', Help='"Maßeinheit Nach" gibt die Zielmaßeinheit für eine Umrechnung an.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=217) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 217)
;

-- 2019-04-29T16:41:31.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ziel-Maßeinheit', Name='Ziel-Maßeinheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=217)
;

-- 2019-04-29T16:41:31.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ziel-Maßeinheit', Description='Zielmaßeinheit', Help='"Maßeinheit Nach" gibt die Zielmaßeinheit für eine Umrechnung an.', CommitWarning = NULL WHERE AD_Element_ID = 217
;

-- 2019-04-29T16:41:31.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ziel-Maßeinheit', Description='Zielmaßeinheit', Help='"Maßeinheit Nach" gibt die Zielmaßeinheit für eine Umrechnung an.' WHERE AD_Element_ID = 217
;

-- 2019-04-29T16:41:31.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ziel-Maßeinheit', Description = 'Zielmaßeinheit', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 217
;

-- 2019-04-29T16:41:41.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit, in die eine bestimmte Menge konvertiert werden soll', Help='',Updated=TO_TIMESTAMP('2019-04-29 16:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='de_DE'
;

-- 2019-04-29T16:41:41.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'de_DE') 
;

-- 2019-04-29T16:41:41.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(217,'de_DE') 
;

-- 2019-04-29T16:41:41.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_To_ID', Name='Ziel-Maßeinheit', Description='Maßeinheit, in die eine bestimmte Menge konvertiert werden soll', Help='' WHERE AD_Element_ID=217
;

-- 2019-04-29T16:41:41.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_To_ID', Name='Ziel-Maßeinheit', Description='Maßeinheit, in die eine bestimmte Menge konvertiert werden soll', Help='', AD_Element_ID=217 WHERE UPPER(ColumnName)='C_UOM_TO_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-29T16:41:41.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_To_ID', Name='Ziel-Maßeinheit', Description='Maßeinheit, in die eine bestimmte Menge konvertiert werden soll', Help='' WHERE AD_Element_ID=217 AND IsCentrallyMaintained='Y'
;

-- 2019-04-29T16:41:41.252
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ziel-Maßeinheit', Description='Maßeinheit, in die eine bestimmte Menge konvertiert werden soll', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=217) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 217)
;

-- 2019-04-29T16:41:41.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ziel-Maßeinheit', Description='Maßeinheit, in die eine bestimmte Menge konvertiert werden soll', Help='', CommitWarning = NULL WHERE AD_Element_ID = 217
;

-- 2019-04-29T16:41:41.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ziel-Maßeinheit', Description='Maßeinheit, in die eine bestimmte Menge konvertiert werden soll', Help='' WHERE AD_Element_ID = 217
;

-- 2019-04-29T16:41:41.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ziel-Maßeinheit', Description = 'Maßeinheit, in die eine bestimmte Menge konvertiert werden soll', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 217
;

-- 2019-04-29T16:41:44.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='',Updated=TO_TIMESTAMP('2019-04-29 16:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='nl_NL'
;

-- 2019-04-29T16:41:44.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'nl_NL') 
;

-- 2019-04-29T16:42:18.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='UOM into which a given quantity shall be converted', Help='', PrintName='Target-UOM',Updated=TO_TIMESTAMP('2019-04-29 16:42:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='en_US'
;

-- 2019-04-29T16:42:18.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'en_US') 
;

-- 2019-04-29T16:42:30.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Target-UoM',Updated=TO_TIMESTAMP('2019-04-29 16:42:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='en_US'
;

-- 2019-04-29T16:42:30.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'en_US') 
;

-- 2019-04-29T16:42:39.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ziel-Maßeinheit', PrintName='Ziel-Maßeinheit',Updated=TO_TIMESTAMP('2019-04-29 16:42:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='nl_NL'
;

-- 2019-04-29T16:42:39.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'nl_NL') 
;

-- 2019-04-29T16:42:44.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit, in die eine bestimmte Menge konvertiert werden soll',Updated=TO_TIMESTAMP('2019-04-29 16:42:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=217 AND AD_Language='nl_NL'
;

-- 2019-04-29T16:42:44.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(217,'nl_NL') 
;

-- 2019-04-29T16:47:32.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=466 AND AD_Language='fr_CH'
;

-- 2019-04-29T16:47:32.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=466 AND AD_Language='it_CH'
;

-- 2019-04-29T16:47:32.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=466 AND AD_Language='en_GB'
;

-- 2019-04-29T16:50:57.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576693,0,'C_UOM_Conversion_MultiplyRate',TO_TIMESTAMP('2019-04-29 16:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Beispiel: Für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60, da eine Minute 1/60 eine Stunde ist.','D','Y','Faktor für Ziel-Maßeinheit','Faktor für Ziel-Maßeinheit',TO_TIMESTAMP('2019-04-29 16:50:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-29T16:50:57.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576693 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-29T16:51:03.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-29 16:51:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='de_CH'
;

-- 2019-04-29T16:51:03.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'de_CH') 
;

-- 2019-04-29T16:51:05.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-29 16:51:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='de_DE'
;

-- 2019-04-29T16:51:05.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'de_DE') 
;

-- 2019-04-29T16:51:05.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576693,'de_DE') 
;

-- 2019-04-29T16:52:12.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Example: for UOM minute and target-UOM hour, the factor is 1/60, because a minute is 1/60 of an hour', IsTranslated='Y', Name='Factor for Target-UOM', PrintName='Factor for Target-UOM',Updated=TO_TIMESTAMP('2019-04-29 16:52:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576693 AND AD_Language='en_US'
;

-- 2019-04-29T16:52:12.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576693,'en_US') 
;

-- 2019-04-29T16:52:26.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576693, Description='Beispiel: Für Maßeinheit Minute und Ziel-Maßeinheit Stunde beträgt der Faktor 1/60, da eine Minute 1/60 eine Stunde ist.', Help=NULL, Name='Faktor für Ziel-Maßeinheit',Updated=TO_TIMESTAMP('2019-04-29 16:52:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=463
;

-- 2019-04-29T16:52:26.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576693) 
;

-- 2019-04-29T16:52:26.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=463
;

-- 2019-04-29T16:52:26.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(463)
;

-- 2019-04-29T16:52:57.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=286 AND AD_Language='fr_CH'
;

-- 2019-04-29T16:52:57.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=286 AND AD_Language='it_CH'
;

-- 2019-04-29T16:52:57.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=286 AND AD_Language='en_GB'
;

-- 2019-04-29T16:53:35.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Der Divisor ist der Kehrwert des Umrechnungsfaktors.', Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-29 16:53:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=286 AND AD_Language='de_CH'
;

-- 2019-04-29T16:53:35.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(286,'de_CH') 
;

-- 2019-04-29T16:54:23.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='The divide rate is the factor''s reciprocal', Help='',Updated=TO_TIMESTAMP('2019-04-29 16:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=286 AND AD_Language='en_US'
;

-- 2019-04-29T16:54:23.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(286,'en_US') 
;

-- 2019-04-29T16:54:27.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-29 16:54:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=286 AND AD_Language='de_DE'
;

-- 2019-04-29T16:54:27.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(286,'de_DE') 
;

-- 2019-04-29T16:54:27.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(286,'de_DE') 
;

-- 2019-04-29T16:54:38.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Der Divisor ist der Kehrwert des Umrechnungsfaktors.', Help='',Updated=TO_TIMESTAMP('2019-04-29 16:54:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=286 AND AD_Language='de_DE'
;

-- 2019-04-29T16:54:38.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(286,'de_DE') 
;

-- 2019-04-29T16:54:38.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(286,'de_DE') 
;

-- 2019-04-29T16:54:38.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DivideRate', Name='Divisor', Description='Der Divisor ist der Kehrwert des Umrechnungsfaktors.', Help='' WHERE AD_Element_ID=286
;

-- 2019-04-29T16:54:38.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DivideRate', Name='Divisor', Description='Der Divisor ist der Kehrwert des Umrechnungsfaktors.', Help='', AD_Element_ID=286 WHERE UPPER(ColumnName)='DIVIDERATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-29T16:54:38.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DivideRate', Name='Divisor', Description='Der Divisor ist der Kehrwert des Umrechnungsfaktors.', Help='' WHERE AD_Element_ID=286 AND IsCentrallyMaintained='Y'
;

-- 2019-04-29T16:54:38.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Divisor', Description='Der Divisor ist der Kehrwert des Umrechnungsfaktors.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=286) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 286)
;

-- 2019-04-29T16:54:38.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Divisor', Description='Der Divisor ist der Kehrwert des Umrechnungsfaktors.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 286
;

-- 2019-04-29T16:54:38.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Divisor', Description='Der Divisor ist der Kehrwert des Umrechnungsfaktors.', Help='' WHERE AD_Element_ID = 286
;

-- 2019-04-29T16:54:38.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Divisor', Description = 'Der Divisor ist der Kehrwert des Umrechnungsfaktors.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 286
;

/* Fix existing minute=>hour conversion rate(s) */
UPDATE C_UOM_Conversion
SET MultiplyRate=0.016666666667, DivideRate=60
WHERE C_UOM_ID=103 /*Minute*/ AND C_UOM_To_ID=101 /*Hour*/;
