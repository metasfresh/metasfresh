-- 2022-01-18T13:48:17.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird das Feld "Exportdatum" im Fenster "Datev Export v2" nach einem Datenexport aktualisiert.', Name='Exportdatum aktualisieren', PrintName='Exportdatum aktualisieren',Updated=TO_TIMESTAMP('2022-01-18 14:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580483 AND AD_Language='de_CH'
;

-- 2022-01-18T13:48:17.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580483,'de_CH') 
;

-- 2022-01-18T13:48:39.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird das Feld "Exportdatum" im Fenster "Datev Export v2" nach einem Datenexport aktualisiert.', Name='Exportdatum aktualisieren', PrintName='Exportdatum aktualisieren',Updated=TO_TIMESTAMP('2022-01-18 14:48:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580483 AND AD_Language='de_DE'
;

-- 2022-01-18T13:48:39.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580483,'de_DE') 
;

-- 2022-01-18T13:48:39.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580483,'de_DE') 
;

-- 2022-01-18T13:48:39.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsUpdateExportDate', Name='Exportdatum aktualisieren', Description='Wenn angehakt, wird das Feld "Exportdatum" im Fenster "Datev Export v2" nach einem Datenexport aktualisiert.', Help=NULL WHERE AD_Element_ID=580483
;


-- 2022-01-18T13:48:53.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If ticked, the "Export date" field in the "Datev Export v2" window will be updated after a data export.',Updated=TO_TIMESTAMP('2022-01-18 14:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580483 AND AD_Language='en_US'
;

-- 2022-01-18T13:48:53.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580483,'en_US') 
;

-- 2022-01-18T13:49:16.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird das Feld "Exportdatum" im Fenster "Datev Export v2" nach einem Datenexport aktualisiert.', Name='Exportdatum aktualisieren', PrintName='Exportdatum aktualisieren',Updated=TO_TIMESTAMP('2022-01-18 14:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580483 AND AD_Language='nl_NL'
;

-- 2022-01-18T13:49:16.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580483,'nl_NL') 
;

-- 2022-01-18T13:49:24.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-01-18 14:49:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580483 AND AD_Language='de_DE'
;

-- 2022-01-18T13:49:24.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580483,'de_DE') 
;

-- 2022-01-18T13:49:24.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580483,'de_DE') 
;

-- 2022-01-18T13:52:18.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579092,676765,0,245,TO_TIMESTAMP('2022-01-18 14:52:17','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, wird das Feld "Exportdatum" im Fenster "Datev Export v2" nach einem Datenexport aktualisiert.',1,'D','Y','N','N','N','N','N','N','N','Exportdatum aktualisieren',TO_TIMESTAMP('2022-01-18 14:52:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-18T13:52:18.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=676765 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-18T13:52:18.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580483) 
;

-- 2022-01-18T13:52:18.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=676765
;

-- 2022-01-18T13:52:18.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(676765)
;

-- 2022-01-18T13:56:14.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-01-18 14:56:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=676765
;

-- 2022-01-18T13:57:34.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=390,Updated=TO_TIMESTAMP('2022-01-18 14:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=676765
;

-- 2022-01-18T14:17:56.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type@=Excel',Updated=TO_TIMESTAMP('2022-01-18 15:17:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=676765
;


