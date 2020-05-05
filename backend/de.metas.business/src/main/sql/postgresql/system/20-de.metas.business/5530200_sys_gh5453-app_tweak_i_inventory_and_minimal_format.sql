-- 2019-09-10T05:16:02.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET Description='Einfaches Datenformat für Inventurbelege mit wenigen Feldern', Name='Inventur Minimal',Updated=TO_TIMESTAMP('2019-09-10 07:16:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540044
;

-- 2019-09-10T05:16:23.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=9764,Updated=TO_TIMESTAMP('2019-09-10 07:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541368
;

-- 2019-09-10T05:16:25.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2019-09-10 07:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541369
;

-- 2019-09-10T05:16:32.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=4,Updated=TO_TIMESTAMP('2019-09-10 07:16:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541370
;

-- 2019-09-10T05:17:49.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.',Updated=TO_TIMESTAMP('2019-09-10 07:17:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1037 AND AD_Language='fr_CH'
;

-- 2019-09-10T05:17:49.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1037,'fr_CH') 
;

-- 2019-09-10T05:17:57.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-10 07:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1037 AND AD_Language='de_CH'
;

-- 2019-09-10T05:17:57.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1037,'de_CH') 
;

-- 2019-09-10T05:18:04.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-10 07:18:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1037 AND AD_Language='de_DE'
;

-- 2019-09-10T05:18:04.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1037,'de_DE') 
;

-- 2019-09-10T05:18:04.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1037,'de_DE') 
;

-- 2019-09-10T05:18:04.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MovementDate', Name='Bewegungsdatum', Description='Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.' WHERE AD_Element_ID=1037
;

-- 2019-09-10T05:18:04.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MovementDate', Name='Bewegungsdatum', Description='Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.', AD_Element_ID=1037 WHERE UPPER(ColumnName)='MOVEMENTDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-10T05:18:04.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MovementDate', Name='Bewegungsdatum', Description='Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.' WHERE AD_Element_ID=1037 AND IsCentrallyMaintained='Y'
;

-- 2019-09-10T05:18:04.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bewegungsdatum', Description='Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1037) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1037)
;

-- 2019-09-10T05:18:04.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Bewegungsdatum', Description='Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.', CommitWarning = NULL WHERE AD_Element_ID = 1037
;

-- 2019-09-10T05:18:04.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Bewegungsdatum', Description='Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', Help='"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.' WHERE AD_Element_ID = 1037
;

-- 2019-09-10T05:18:04.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Bewegungsdatum', Description = 'Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1037
;

-- 2019-09-10T05:18:58.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577058,0,'InventoryDate',TO_TIMESTAMP('2019-09-10 07:18:58','YYYY-MM-DD HH24:MI:SS'),100,'Datum zu dem die Inventur gilt, d.h. Belegedatum des Inventurbelegs','D','Y','Inventurdatum','Inventurdatum',TO_TIMESTAMP('2019-09-10 07:18:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-10T05:18:58.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577058 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-10T05:19:13.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Document date of the inventory document', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-10 07:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577058 AND AD_Language='en_US'
;

-- 2019-09-10T05:19:13.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577058,'en_US') 
;

-- 2019-09-10T05:19:17.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-10 07:19:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577058 AND AD_Language='de_DE'
;

-- 2019-09-10T05:19:17.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577058,'de_DE') 
;

-- 2019-09-10T05:19:17.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577058,'de_DE') 
;

-- 2019-09-10T05:19:19.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-10 07:19:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577058 AND AD_Language='de_CH'
;

-- 2019-09-10T05:19:19.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577058,'de_CH') 
;

-- 2019-09-10T05:28:13.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=577058, ColumnName='InventoryDate', Description='Datum zu dem die Inventur gilt, d.h. Belegedatum des Inventurbelegs', Help=NULL, Name='Inventurdatum',Updated=TO_TIMESTAMP('2019-09-10 07:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=9764
;

-- 2019-09-10T05:28:13.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Inventurdatum', Description='Datum zu dem die Inventur gilt, d.h. Belegedatum des Inventurbelegs', Help=NULL WHERE AD_Column_ID=9764
;

-- 2019-09-10T05:28:13.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577058) 
;

/* DDL */  select db_alter_table('I_Inventory', 'ALTER TABLE I_Inventory RENAME COLUMN MovementDate TO InventoryDate;');
