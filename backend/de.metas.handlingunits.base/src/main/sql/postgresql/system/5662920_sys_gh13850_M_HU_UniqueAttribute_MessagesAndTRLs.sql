-- 2022-11-02T15:16:06.929Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545189,0,TO_TIMESTAMP('2022-11-02 17:16:06.71','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','The value of the attribute {0} must be unique so it cannot be set for a HU with qty >1 and unit of measure {2}.','E',TO_TIMESTAMP('2022-11-02 17:16:06.71','YYYY-MM-DD HH24:MI:SS.US'),100,'M_HU_UniqueAttribute_HUQtyError')
;

-- 2022-11-02T15:16:06.935Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545189 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-11-02T15:21:14.363Z
UPDATE AD_Message SET MsgText='The value of the attribute {0} must be unique so it cannot be set for a HU with qty >1 and unit of measure {1}.',Updated=TO_TIMESTAMP('2022-11-02 17:21:14.361','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545189
;

-- 2022-11-02T15:22:58.296Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545190,0,TO_TIMESTAMP('2022-11-02 17:22:58.175','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','The HU {0} with the attribute {1} with value {2} for product {3} already exists.','E',TO_TIMESTAMP('2022-11-02 17:22:58.175','YYYY-MM-DD HH24:MI:SS.US'),100,'M_HU_UniqueAttribute_DuplicateValue_Error')
;

-- 2022-11-02T15:22:58.298Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545190 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;




-- 2022-11-02T17:13:17.003Z
UPDATE AD_Message_Trl SET MsgText='Die HU {0} mit dem Merkmal {1} mit dem Wert {2} für das Produkt {3} existiert bereits.',Updated=TO_TIMESTAMP('2022-11-02 19:13:16.998','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545190
;

-- 2022-11-02T17:13:26.141Z
UPDATE AD_Message_Trl SET MsgText='Die HU {0} mit dem Merkmal {1} mit dem Wert {2} für das Produkt {3} existiert bereits.',Updated=TO_TIMESTAMP('2022-11-02 19:13:26.138','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545190
;

-- 2022-11-02T17:13:50.366Z
UPDATE AD_Message_Trl SET MsgText='The value of the attribute {0} of the handling unit {1} must be unique so it cannot be set for an HU with a quantity larger than 1 and {2} as its unit of measure.',Updated=TO_TIMESTAMP('2022-11-02 19:13:50.363','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545189
;

-- 2022-11-02T17:13:59.315Z
UPDATE AD_Message SET MsgText='The value of the attribute {0} of the handling unit {1} must be unique so it cannot be set for an HU with a quantity larger than 1 and {2} as its unit of measure.',Updated=TO_TIMESTAMP('2022-11-02 19:13:59.313','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545189
;

-- 2022-11-02T17:14:09.676Z
UPDATE AD_Message_Trl SET MsgText=' Der Wert des Merkmals {0} der Handling Unit {1} muss eindeutig sein, sodass er nicht für eine HU mit einer Menge größer als 1 und {2} als Maßeinheit gesetzt werden kann.',Updated=TO_TIMESTAMP('2022-11-02 19:14:09.673','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545189
;

-- 2022-11-02T17:14:15.225Z
UPDATE AD_Message_Trl SET MsgText=' Der Wert des Merkmals {0} der Handling Unit {1} muss eindeutig sein, sodass er nicht für eine HU mit einer Menge größer als 1 und {2} als Maßeinheit gesetzt werden kann.',Updated=TO_TIMESTAMP('2022-11-02 19:14:15.222','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545189
;

-- 2022-11-02T17:14:18.315Z
UPDATE AD_Message_Trl SET MsgText=' Der Wert des Merkmals {0} der Handling Unit {1} muss eindeutig sein, sodass er nicht für eine HU mit einer Menge größer als 1 und {2} als Maßeinheit gesetzt werden kann.',Updated=TO_TIMESTAMP('2022-11-02 19:14:18.312','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545189
;

-- 2022-11-02T17:15:15.966Z
UPDATE AD_Index_Table SET BeforeChangeWarning='', ErrorMsg='An active, picked or issued handling unit with the same attribute value already exists.',Updated=TO_TIMESTAMP('2022-11-02 19:15:15.964','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540711
;

-- 2022-11-02T17:15:19.557Z
DROP INDEX IF EXISTS m_hu_uniqueattribute_unique_product_attribute_and_value
;

-- 2022-11-02T17:15:19.559Z
CREATE UNIQUE INDEX M_HU_UniqueAttribute_Unique_Product_Attribute_And_Value ON M_HU_UniqueAttribute (M_Product_ID,M_Attribute_ID,Value) WHERE IsActive = 'Y' AND Value IS NOT NULL
;

-- 2022-11-02T17:15:38.412Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Es existiert bereits eine aktive, kommissionierte oder zugeteilte Handling Unit mit demselben Merkmalswert.',Updated=TO_TIMESTAMP('2022-11-02 19:15:38.409','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540711 AND AD_Language='de_CH'
;

-- 2022-11-02T17:15:41.561Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Es existiert bereits eine aktive, kommissionierte oder zugeteilte Handling Unit mit demselben Merkmalswert.',Updated=TO_TIMESTAMP('2022-11-02 19:15:41.559','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540711 AND AD_Language='nl_NL'
;

-- 2022-11-02T17:15:45.667Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Es existiert bereits eine aktive, kommissionierte oder zugeteilte Handling Unit mit demselben Merkmalswert.',Updated=TO_TIMESTAMP('2022-11-02 19:15:45.665','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540711 AND AD_Language='de_DE'
;

-- 2022-11-02T17:15:56.912Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='An active, picked or issued handling unit with the same attribute value already exists.',Updated=TO_TIMESTAMP('2022-11-02 19:15:56.91','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540711 AND AD_Language='en_US'
;

-- 2022-11-02T17:16:05.953Z
DROP INDEX IF EXISTS m_hu_uniqueattribute_unique_product_attribute_and_value
;

-- 2022-11-02T17:16:05.954Z
CREATE UNIQUE INDEX M_HU_UniqueAttribute_Unique_Product_Attribute_And_Value ON M_HU_UniqueAttribute (M_Product_ID,M_Attribute_ID,Value) WHERE IsActive = 'Y' AND Value IS NOT NULL
;

-- Window: Packvorschrift Version, InternalName=M_HU_PI_Version
-- 2022-11-02T17:18:02.130Z
UPDATE AD_Window SET AD_Element_ID=573730, Description=NULL, Help=NULL, Name='Packvorschrift Version',Updated=TO_TIMESTAMP('2022-11-02 19:18:02.128','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=540344
;

-- 2022-11-02T17:18:02.157Z
/* DDL */  select update_window_translation_from_ad_element(573730) 
;

-- 2022-11-02T17:18:02.168Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540344
;

-- 2022-11-02T17:18:02.172Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(540344)
;

-- 2022-11-02T17:18:41.767Z
UPDATE AD_Element_Trl SET Description='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.', Help='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.',Updated=TO_TIMESTAMP('2022-11-02 19:18:41.764','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581629 AND AD_Language='de_CH'
;

-- 2022-11-02T17:18:41.773Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581629,'de_CH') 
;

-- 2022-11-02T17:18:47.254Z
UPDATE AD_Element_Trl SET Description='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.', Help='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.',Updated=TO_TIMESTAMP('2022-11-02 19:18:47.252','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581629 AND AD_Language='de_DE'
;

-- 2022-11-02T17:18:47.256Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581629,'de_DE') 
;

-- 2022-11-02T17:18:47.262Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581629,'de_DE') 
;

-- 2022-11-02T17:18:47.263Z
UPDATE AD_Column SET ColumnName=NULL, Name='Unique', Description='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.', Help='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.' WHERE AD_Element_ID=581629
;

-- 2022-11-02T17:18:47.264Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Unique', Description='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.', Help='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.' WHERE AD_Element_ID=581629 AND IsCentrallyMaintained='Y'
;

-- 2022-11-02T17:18:47.265Z
UPDATE AD_Field SET Name='Unique', Description='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.', Help='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581629) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581629)
;

-- 2022-11-02T17:18:47.273Z
UPDATE AD_Tab SET Name='Unique', Description='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.', Help='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.', CommitWarning = NULL WHERE AD_Element_ID = 581629
;

-- 2022-11-02T17:18:47.275Z
UPDATE AD_WINDOW SET Name='Unique', Description='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.', Help='Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.' WHERE AD_Element_ID = 581629
;

-- 2022-11-02T17:18:47.276Z
UPDATE AD_Menu SET   Name = 'Unique', Description = 'Bei Aktivierung kann es nur eine aktive, zugeteilte oder kommissionierte Handling Unit für ein Produkt mit demselben Nicht-Null-Wert für dieses Merkmal geben.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581629
;

-- 2022-11-02T17:18:58.055Z
UPDATE AD_Element_Trl SET Description='When activated, there can only be one active, issued or picked handling unit for a product with the same non-null value for this attribute.', Help='When activated, there can only be one active, issued or picked handling unit for a product with the same non-null value for this attribute.',Updated=TO_TIMESTAMP('2022-11-02 19:18:58.053','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581629 AND AD_Language='en_US'
;

-- 2022-11-02T17:18:58.056Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581629,'en_US') 
;

-- 2022-11-02T17:19:04.655Z
UPDATE AD_Element_Trl SET Description='When activated, there can only be one active, issued or picked handling unit for a product with the same non-null value for this attribute.', Help='When activated, there can only be one active, issued or picked handling unit for a product with the same non-null value for this attribute.',Updated=TO_TIMESTAMP('2022-11-02 19:19:04.653','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581629 AND AD_Language='nl_NL'
;

-- 2022-11-02T17:19:04.657Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581629,'nl_NL') 
;

-- 2022-11-02T17:20:12.890Z
UPDATE AD_Element_Trl SET Name='Handling Unit - Unique Attributes', PrintName='Handling Unit - Unique Attributes',Updated=TO_TIMESTAMP('2022-11-02 19:20:12.887','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581628 AND AD_Language='en_US'
;

-- 2022-11-02T17:20:12.891Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581628,'en_US') 
;

-- 2022-11-02T17:20:22.814Z
UPDATE AD_Element_Trl SET Name='Handling Unit - Eindeutige Merkmale', PrintName='Handling Unit - Eindeutige Merkmale',Updated=TO_TIMESTAMP('2022-11-02 19:20:22.812','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581628 AND AD_Language='nl_NL'
;

-- 2022-11-02T17:20:22.816Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581628,'nl_NL') 
;

-- 2022-11-02T17:20:27.871Z
UPDATE AD_Element_Trl SET Name='Handling Unit - Eindeutige Merkmale', PrintName='Handling Unit - Eindeutige Merkmale',Updated=TO_TIMESTAMP('2022-11-02 19:20:27.868','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581628 AND AD_Language='de_DE'
;

-- 2022-11-02T17:20:27.872Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581628,'de_DE') 
;

-- 2022-11-02T17:20:27.879Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581628,'de_DE') 
;

-- 2022-11-02T17:20:27.882Z
UPDATE AD_Column SET ColumnName='M_HU_UniqueAttribute_ID', Name='Handling Unit - Eindeutige Merkmale', Description=NULL, Help=NULL WHERE AD_Element_ID=581628
;

-- 2022-11-02T17:20:27.884Z
UPDATE AD_Process_Para SET ColumnName='M_HU_UniqueAttribute_ID', Name='Handling Unit - Eindeutige Merkmale', Description=NULL, Help=NULL, AD_Element_ID=581628 WHERE UPPER(ColumnName)='M_HU_UNIQUEATTRIBUTE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-02T17:20:27.885Z
UPDATE AD_Process_Para SET ColumnName='M_HU_UniqueAttribute_ID', Name='Handling Unit - Eindeutige Merkmale', Description=NULL, Help=NULL WHERE AD_Element_ID=581628 AND IsCentrallyMaintained='Y'
;

-- 2022-11-02T17:20:27.886Z
UPDATE AD_Field SET Name='Handling Unit - Eindeutige Merkmale', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581628) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581628)
;

-- 2022-11-02T17:20:27.895Z
UPDATE AD_PrintFormatItem pi SET PrintName='Handling Unit - Eindeutige Merkmale', Name='Handling Unit - Eindeutige Merkmale' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581628)
;

-- 2022-11-02T17:20:27.897Z
UPDATE AD_Tab SET Name='Handling Unit - Eindeutige Merkmale', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581628
;

-- 2022-11-02T17:20:27.899Z
UPDATE AD_WINDOW SET Name='Handling Unit - Eindeutige Merkmale', Description=NULL, Help=NULL WHERE AD_Element_ID = 581628
;

-- 2022-11-02T17:20:27.901Z
UPDATE AD_Menu SET   Name = 'Handling Unit - Eindeutige Merkmale', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581628
;

-- 2022-11-02T17:20:34.799Z
UPDATE AD_Element_Trl SET Name='Handling Unit - Eindeutige Merkmale', PrintName='Handling Unit - Eindeutige Merkmale',Updated=TO_TIMESTAMP('2022-11-02 19:20:34.797','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581628 AND AD_Language='de_CH'
;

-- 2022-11-02T17:20:34.800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581628,'de_CH') 
;





-- 2022-11-02T17:39:27.094Z
UPDATE AD_Message_Trl SET MsgText='The value of the attribute {0} of the handling unit {1} must be unique so it cannot be set for an HU with a quantity larger than 1 and {2} as its unit of measure.',Updated=TO_TIMESTAMP('2022-11-02 19:39:27.088','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545189
;

