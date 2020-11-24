-- 2020-07-08T11:06:32.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=572882, Description='Related Purchase Orders', Help=NULL, Name='Bestellungen',Updated=TO_TIMESTAMP('2020-07-08 14:06:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552533
;

-- 2020-07-08T11:06:32.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572882) 
;

-- 2020-07-08T11:06:32.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=552533
;

-- 2020-07-08T11:06:32.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(552533)
;

-- 2020-07-08T11:09:04.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='UOM for Dimensions', PrintName='UOM for Dimensions',Updated=TO_TIMESTAMP('2020-07-08 14:09:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542186 AND AD_Language='en_GB'
;

-- 2020-07-08T11:09:04.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542186,'en_GB') 
;

-- 2020-07-08T11:09:37.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Unit of measurement for the dimensions (height, width, length) of the packaging material, and basic unit of measurement for all volume specifications.', Name='UOM for Dimensions', PrintName='UOM for Dimensions',Updated=TO_TIMESTAMP('2020-07-08 14:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542186 AND AD_Language='en_US'
;

-- 2020-07-08T11:09:37.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542186,'en_US') 
;

-- 2020-07-08T11:09:44.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Unit of measurement for the dimensions (height, width, length) of the packaging material, and basic unit of measurement for all volume specifications.', Help='',Updated=TO_TIMESTAMP('2020-07-08 14:09:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542186 AND AD_Language='en_GB'
;

-- 2020-07-08T11:09:44.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542186,'en_GB') 
;

-- 2020-07-08T11:09:50.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:09:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542186 AND AD_Language='en_GB'
;

-- 2020-07-08T11:09:50.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542186,'en_GB') 
;

-- 2020-07-08T11:09:58.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', Help='', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:09:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542186 AND AD_Language='de_CH'
;

-- 2020-07-08T11:09:58.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542186,'de_CH') 
;

-- 2020-07-08T11:10:04.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:10:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542186 AND AD_Language='de_DE'
;

-- 2020-07-08T11:10:04.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542186,'de_DE') 
;

-- 2020-07-08T11:10:04.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542186,'de_DE') 
;

-- 2020-07-08T11:10:04.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_UOM_Dimension_ID', Name='Einheit Abessungen', Description='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', Help='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.' WHERE AD_Element_ID=542186
;

-- 2020-07-08T11:10:04.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Dimension_ID', Name='Einheit Abessungen', Description='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', Help='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', AD_Element_ID=542186 WHERE UPPER(ColumnName)='C_UOM_DIMENSION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-07-08T11:10:04.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_UOM_Dimension_ID', Name='Einheit Abessungen', Description='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', Help='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.' WHERE AD_Element_ID=542186 AND IsCentrallyMaintained='Y'
;

-- 2020-07-08T11:10:04.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Einheit Abessungen', Description='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', Help='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542186) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542186)
;

-- 2020-07-08T11:10:04.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Einheit Abessungen', Description='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', Help='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', CommitWarning = NULL WHERE AD_Element_ID = 542186
;

-- 2020-07-08T11:10:04.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Einheit Abessungen', Description='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', Help='Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.' WHERE AD_Element_ID = 542186
;

-- 2020-07-08T11:10:04.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Einheit Abessungen', Description = 'Maßeinheit für die Abmessungen (Höhe, Breite, Länge) des Packmittels, sowie Grundmaßeinheit für alle Volumenangaben.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542186
;

-- 2020-07-08T11:11:39.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Closed', PrintName='Closed',Updated=TO_TIMESTAMP('2020-07-08 14:11:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001611 AND AD_Language='en_US'
;

-- 2020-07-08T11:11:39.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001611,'en_US') 
;

-- 2020-07-08T11:11:42.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001611 AND AD_Language='de_DE'
;

-- 2020-07-08T11:11:42.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001611,'de_DE') 
;

-- 2020-07-08T11:11:42.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1001611,'de_DE') 
;

-- 2020-07-08T11:11:44.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001611 AND AD_Language='de_CH'
;

-- 2020-07-08T11:11:44.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001611,'de_CH') 
;

-- 2020-07-08T11:18:48.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Current Valid Version', PrintName='Current Valid Version',Updated=TO_TIMESTAMP('2020-07-08 14:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542147 AND AD_Language='en_US'
;

-- 2020-07-08T11:18:48.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542147,'en_US') 
;

-- 2020-07-08T11:18:51.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:18:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542147 AND AD_Language='de_CH'
;

-- 2020-07-08T11:18:51.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542147,'de_CH') 
;

-- 2020-07-08T11:18:55.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Current Valid Version', PrintName='Current Valid Version',Updated=TO_TIMESTAMP('2020-07-08 14:18:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542147 AND AD_Language='en_GB'
;

-- 2020-07-08T11:18:55.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542147,'en_GB') 
;

-- 2020-07-08T11:19:00.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542147 AND AD_Language='de_DE'
;

-- 2020-07-08T11:19:00.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542147,'de_DE') 
;

-- 2020-07-08T11:19:00.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542147,'de_DE') 
;

-- 2020-07-08T11:20:47.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573731 AND AD_Language='de_DE'
;

-- 2020-07-08T11:20:47.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573731,'de_DE') 
;

-- 2020-07-08T11:20:47.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(573731,'de_DE') 
;

-- 2020-07-08T11:20:57.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Attributes', PrintName='Attributes',Updated=TO_TIMESTAMP('2020-07-08 14:20:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573731 AND AD_Language='en_US'
;

-- 2020-07-08T11:20:57.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573731,'en_US') 
;

-- 2020-07-08T11:21:00.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:21:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573731 AND AD_Language='de_CH'
;

-- 2020-07-08T11:21:00.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573731,'de_CH') 
;

-- 2020-07-08T11:23:06.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Packing Material', PrintName='Packing Material',Updated=TO_TIMESTAMP('2020-07-08 14:23:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542185 AND AD_Language='en_US'
;

-- 2020-07-08T11:23:06.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542185,'en_US') 
;

-- 2020-07-08T11:23:09.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:23:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542185 AND AD_Language='de_DE'
;

-- 2020-07-08T11:23:09.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542185,'de_DE') 
;

-- 2020-07-08T11:23:09.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542185,'de_DE') 
;

-- 2020-07-08T11:23:13.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542185 AND AD_Language='de_CH'
;

-- 2020-07-08T11:23:13.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542185,'de_CH') 
;

-- 2020-07-08T11:23:17Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packing Material', PrintName='Packing Material',Updated=TO_TIMESTAMP('2020-07-08 14:23:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542185 AND AD_Language='en_GB'
;

-- 2020-07-08T11:23:17.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542185,'en_GB') 
;

-- 2020-07-08T11:38:01.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Infinite Capacity', PrintName='Infinite Capacity',Updated=TO_TIMESTAMP('2020-07-08 14:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=56560 AND AD_Language='it_CH'
;

-- 2020-07-08T11:38:01.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(56560,'it_CH') 
;

-- 2020-07-08T11:38:07.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Infinite Capacity', PrintName='Infinite Capacity',Updated=TO_TIMESTAMP('2020-07-08 14:38:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=56560 AND AD_Language='en_GB'
;

-- 2020-07-08T11:38:07.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(56560,'en_GB') 
;

-- 2020-07-08T11:38:09.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:38:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=56560 AND AD_Language='de_CH'
;

-- 2020-07-08T11:38:09.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(56560,'de_CH') 
;

-- 2020-07-08T11:38:12.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Infinite Capacity', PrintName='Infinite Capacity',Updated=TO_TIMESTAMP('2020-07-08 14:38:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=56560 AND AD_Language='en_US'
;

-- 2020-07-08T11:38:12.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(56560,'en_US') 
;

-- 2020-07-08T11:38:15.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 14:38:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=56560 AND AD_Language='de_DE'
;

-- 2020-07-08T11:38:15.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(56560,'de_DE') 
;

-- 2020-07-08T11:38:15.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(56560,'de_DE') 
;

-- 2020-07-08T12:09:24.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Packing Instruction',Updated=TO_TIMESTAMP('2020-07-08 15:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542135 AND AD_Language='en_US'
;

-- 2020-07-08T12:09:24.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542135,'en_US') 
;

-- 2020-07-08T12:09:35.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packing Instruction', PrintName='Packing Instruction',Updated=TO_TIMESTAMP('2020-07-08 15:09:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542135 AND AD_Language='en_GB'
;

-- 2020-07-08T12:09:35.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542135,'en_GB') 
;

-- 2020-07-08T12:09:40.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 15:09:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542135 AND AD_Language='de_CH'
;

-- 2020-07-08T12:09:40.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542135,'de_CH') 
;

-- 2020-07-08T12:09:45.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 15:09:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542135 AND AD_Language='de_DE'
;

-- 2020-07-08T12:09:45.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542135,'de_DE') 
;

-- 2020-07-08T12:09:45.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542135,'de_DE') 
;

-- 2020-07-08T12:15:32.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Item', PrintName='Item',Updated=TO_TIMESTAMP('2020-07-08 15:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573852 AND AD_Language='en_US'
;

-- 2020-07-08T12:15:32.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573852,'en_US') 
;

-- 2020-07-08T12:15:36.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 15:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573852 AND AD_Language='de_DE'
;

-- 2020-07-08T12:15:36.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573852,'de_DE') 
;

-- 2020-07-08T12:15:36.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(573852,'de_DE') 
;

-- 2020-07-08T12:17:17.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Item Type', PrintName='Item Type',Updated=TO_TIMESTAMP('2020-07-08 15:17:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542144 AND AD_Language='en_GB'
;

-- 2020-07-08T12:17:17.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542144,'en_GB') 
;

-- 2020-07-08T12:17:19.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 15:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542144 AND AD_Language='de_CH'
;

-- 2020-07-08T12:17:19.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542144,'de_CH') 
;

-- 2020-07-08T12:17:23.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Item Type', PrintName='Item Type',Updated=TO_TIMESTAMP('2020-07-08 15:17:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542144 AND AD_Language='en_US'
;

-- 2020-07-08T12:17:23.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542144,'en_US') 
;

-- 2020-07-08T12:17:26.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 15:17:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542144 AND AD_Language='de_DE'
;

-- 2020-07-08T12:17:26.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542144,'de_DE') 
;

-- 2020-07-08T12:17:26.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542144,'de_DE') 
;

-- 2020-07-08T13:00:43.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packing Instruction Item', PrintName='Packing Instruction Item',Updated=TO_TIMESTAMP('2020-07-08 16:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542133 AND AD_Language='en_GB'
;

-- 2020-07-08T13:00:43.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542133,'en_GB') 
;

-- 2020-07-08T13:00:45.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 16:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542133 AND AD_Language='de_CH'
;

-- 2020-07-08T13:00:45.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542133,'de_CH') 
;

-- 2020-07-08T13:00:49.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packing Instruction Item', PrintName='Packing Instruction Item',Updated=TO_TIMESTAMP('2020-07-08 16:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542133 AND AD_Language='en_US'
;

-- 2020-07-08T13:00:49.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542133,'en_US') 
;

-- 2020-07-08T13:00:52.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-08 16:00:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542133 AND AD_Language='de_DE'
;

-- 2020-07-08T13:00:52.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542133,'de_DE') 
;

-- 2020-07-08T13:00:52.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542133,'de_DE') 
;

