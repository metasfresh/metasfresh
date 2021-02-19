-- 2021-02-02T13:14:13.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, ColumnSQL, Created, CreatedBy, DDL_NoForeignKey,
                       EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete,
                       IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence,
                       MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 572624, 542326, 0, 10, 540270, 'packingmaterialname', '( CASE WHEN IsPackingMaterial = ''Y'' THEN (select p.name from M_Product where p.M_Product_ID = C_Invoice_Candidate.M_Product_ID) ELSE NULL END )', TO_TIMESTAMP('2021-02-02 15:14:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'de.metas.invoicecandidate', 0, 500, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N',  'N', 'N', 'N', 'N', 'N', 0, 'packingmaterialname', 0, 0, TO_TIMESTAMP('2021-02-02 15:14:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2021-02-02T13:14:13.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Column_ID = 572624
  AND NOT EXISTS(SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2021-02-02T13:14:13.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_Column_Translation_From_AD_Element(542326)
;

-- 2021-02-02T13:14:54.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID, IsDisplayed, DisplayLength, SortNo, IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, AD_Client_ID, IsActive, Created, CreatedBy, IsReadOnly, ColumnDisplayLength, IncludedTabHeight, Updated, UpdatedBy, AD_Field_ID, IsDisplayedGrid, SeqNo, SeqNoGrid, SpanX, SpanY, AD_Column_ID, Name, AD_Org_ID, EntityType)
VALUES (540279, 'Y', 0, 0, 'N', 'N', 'N', 'N', 0, 'Y', TO_TIMESTAMP('2021-02-02 15:14:53', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 0, 0, TO_TIMESTAMP('2021-02-02 15:14:53', 'YYYY-MM-DD HH24:MI:SS'), 100, 628825, 'Y', 1170, 530, 1, 1, 572624, 'packingmaterialname', 0, 'D')
;

-- 2021-02-02T13:14:54.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Help, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Help,
       t.Name,
       t.Description,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Field_ID = 628825
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2021-02-02T13:14:54.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(542326)
;

-- 2021-02-02T13:14:54.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 628825
;

-- 2021-02-02T13:14:54.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(628825)
;

-- 2021-02-02T13:15:32.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsFacetFilter='Y', MaxFacetsToFetch=0, Updated=TO_TIMESTAMP('2021-02-02 15:15:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 572624
;

-- 2021-02-02T13:23:30.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ColumnSQL='( CASE WHEN IsPackagingMaterial = ''Y'' THEN (select p.name from M_Product where p.M_Product_ID = C_Invoice_Candidate.M_Product_ID) ELSE NULL END )', Updated=TO_TIMESTAMP('2021-02-02 15:23:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 572624
;

-- 2021-02-02T13:24:05.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ColumnSQL='( CASE WHEN IsPackagingMaterial = ''Y'' THEN (select p.name from M_Product p where p.M_Product_ID = C_Invoice_Candidate.M_Product_ID) ELSE NULL END )', Updated=TO_TIMESTAMP('2021-02-02 15:24:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 572624
;

create index if not exists m_product_m_product_id_name on m_product (m_product_id, name);






















-- 2021-02-02T15:22:56.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Packing Material', PrintName='Packing Material',Updated=TO_TIMESTAMP('2021-02-02 17:22:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542326 AND AD_Language='fr_CH'
;

-- 2021-02-02T15:22:56.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542326,'fr_CH') 
;

-- 2021-02-02T15:23:08.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-02 17:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542326 AND AD_Language='fr_CH'
;

-- 2021-02-02T15:23:08.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542326,'fr_CH') 
;

-- 2021-02-02T15:23:24.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packing Material', PrintName='Packing Material',Updated=TO_TIMESTAMP('2021-02-02 17:23:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542326 AND AD_Language='en_GB'
;

-- 2021-02-02T15:23:24.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542326,'en_GB') 
;

-- 2021-02-02T15:23:31.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packing Material', PrintName='Packing Material',Updated=TO_TIMESTAMP('2021-02-02 17:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542326 AND AD_Language='de_CH'
;

-- 2021-02-02T15:23:31.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542326,'de_CH') 
;

-- 2021-02-02T15:23:35.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packing Material', PrintName='Packing Material',Updated=TO_TIMESTAMP('2021-02-02 17:23:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542326 AND AD_Language='en_US'
;

-- 2021-02-02T15:23:35.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542326,'en_US') 
;

-- 2021-02-02T15:25:05.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packing Material', PrintName='Packing Material',Updated=TO_TIMESTAMP('2021-02-02 17:25:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542326 AND AD_Language='de_DE'
;

-- 2021-02-02T15:25:05.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542326,'de_DE') 
;

-- 2021-02-02T15:25:05.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542326,'de_DE') 
;

-- 2021-02-02T15:25:05.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='packingmaterialname', Name='Packing Material', Description=NULL, Help=NULL WHERE AD_Element_ID=542326
;

-- 2021-02-02T15:25:05.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='packingmaterialname', Name='Packing Material', Description=NULL, Help=NULL, AD_Element_ID=542326 WHERE UPPER(ColumnName)='PACKINGMATERIALNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-02T15:25:05.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='packingmaterialname', Name='Packing Material', Description=NULL, Help=NULL WHERE AD_Element_ID=542326 AND IsCentrallyMaintained='Y'
;

-- 2021-02-02T15:25:05.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Packing Material', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542326) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542326)
;

-- 2021-02-02T15:25:05.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Packing Material', Name='Packing Material' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542326)
;

-- 2021-02-02T15:25:05.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Packing Material', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542326
;

-- 2021-02-02T15:25:05.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Packing Material', Description=NULL, Help=NULL WHERE AD_Element_ID = 542326
;

-- 2021-02-02T15:25:05.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Packing Material', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542326
;

-- 2021-02-02T15:25:27.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsFacetFilter='N',Updated=TO_TIMESTAMP('2021-02-02 17:25:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552552
;

