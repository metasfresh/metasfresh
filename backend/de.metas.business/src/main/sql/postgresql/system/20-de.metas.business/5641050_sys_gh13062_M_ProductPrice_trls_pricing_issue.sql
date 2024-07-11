-- 2022-05-27T14:52:57.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Defines the order of prices, e.g. when outputting all prices for a specific price list version',Updated=TO_TIMESTAMP('2022-05-27 17:52:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001870 AND AD_Language='en_US'
;

-- 2022-05-27T14:52:57.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001870,'en_US') 
;

-- 2022-05-27T14:54:33.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If there are several matching prices for a price list version/product combination, the price with the smallest value is used.',Updated=TO_TIMESTAMP('2022-05-27 17:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543277 AND AD_Language='en_US'
;

-- 2022-05-27T14:54:33.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543277,'en_US') 
;

-- 2022-05-27T14:55:31.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If set and the data record (e.g. order line) for which a price is determined has a packing instruction or characteristics, then this price is only used if there is a match. If the data record does not have attributes or packing instruction, then these are inserted into the data record in each case.', Name='Attribute and packing instruction', PrintName='Attribute and packing instruction',Updated=TO_TIMESTAMP('2022-05-27 17:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580944 AND AD_Language='en_US'
;

-- 2022-05-27T14:55:31.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580944,'en_US') 
;

-- 2022-05-27T14:57:20.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580946,0,TO_TIMESTAMP('2022-05-27 17:57:20','YYYY-MM-DD HH24:MI:SS'),100,'Verfügbare Merkmale sind diejenigen, die als preisrelevant markiert sind und die den Typ "Liste" haben','D','Y','Merkmale','Merkmale',TO_TIMESTAMP('2022-05-27 17:57:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-27T14:57:21.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580946 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-05-27T14:58:06.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Available attributes are those that are marked as price relevant and have attribute type set as list', Name='Attributes', PrintName='Attributes',Updated=TO_TIMESTAMP('2022-05-27 17:58:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580946 AND AD_Language='en_US'
;

-- 2022-05-27T14:58:06.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580946,'en_US') 
;



-- 2022-05-27T14:58:49.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580946, Description='Verfügbare Merkmale sind diejenigen, die als preisrelevant markiert sind und die den Typ "Liste" haben', Help=NULL, Name='Merkmale',Updated=TO_TIMESTAMP('2022-05-27 17:58:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557831
;

-- 2022-05-27T14:58:49.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580946) 
;

-- 2022-05-27T14:58:49.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=557831
;

-- 2022-05-27T14:58:49.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(557831)
;

-- 2022-05-27T14:59:47.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='For a TU unit of measurement, the container capacity of this packing instruction is used for conversion.',Updated=TO_TIMESTAMP('2022-05-27 17:59:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001085 AND AD_Language='en_US'
;

-- 2022-05-27T14:59:47.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001085,'en_US') 
;

-- 2022-05-27T15:00:29.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Indicates whether the price unit is a TU unit of measurement', Name='Price per TU', PrintName='Price per TU',Updated=TO_TIMESTAMP('2022-05-27 18:00:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580945 AND AD_Language='en_US'
;

-- 2022-05-27T15:00:29.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580945,'en_US') 
;

