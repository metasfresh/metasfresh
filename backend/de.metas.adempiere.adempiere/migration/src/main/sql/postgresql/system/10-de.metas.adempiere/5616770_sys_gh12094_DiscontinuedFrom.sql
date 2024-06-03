-- 2021-12-02T10:09:00.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580348,0,'DiscontinuedFrom',TO_TIMESTAMP('2021-12-02 12:09:00','YYYY-MM-DD HH24:MI:SS'),100,'If a product is marked as discontinued via the checkbox, then this field can be used to specify from which date onwards it shall be discontinued','D','Y','Discontinued from','Discontinued from',TO_TIMESTAMP('2021-12-02 12:09:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-02T10:09:00.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580348 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-12-02T10:09:26.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.', Name='Eingestellt ab', PrintName='Eingestellt ab',Updated=TO_TIMESTAMP('2021-12-02 12:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580348 AND AD_Language='de_CH'
;

-- 2021-12-02T10:09:26.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580348,'de_CH') 
;

-- 2021-12-02T10:09:36.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.', Name='Eingestellt ab', PrintName='Eingestellt ab',Updated=TO_TIMESTAMP('2021-12-02 12:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580348 AND AD_Language='de_DE'
;

-- 2021-12-02T10:09:36.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580348,'de_DE') 
;

-- 2021-12-02T10:09:36.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580348,'de_DE') 
;

-- 2021-12-02T10:09:36.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DiscontinuedFrom', Name='Eingestellt ab', Description='Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.', Help=NULL WHERE AD_Element_ID=580348
;

-- 2021-12-02T10:09:36.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DiscontinuedFrom', Name='Eingestellt ab', Description='Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.', Help=NULL, AD_Element_ID=580348 WHERE UPPER(ColumnName)='DISCONTINUEDFROM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-02T10:09:36.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DiscontinuedFrom', Name='Eingestellt ab', Description='Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.', Help=NULL WHERE AD_Element_ID=580348 AND IsCentrallyMaintained='Y'
;

-- 2021-12-02T10:09:36.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Eingestellt ab', Description='Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580348) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580348)
;

-- 2021-12-02T10:09:36.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Eingestellt ab', Name='Eingestellt ab' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580348)
;

-- 2021-12-02T10:09:36.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Eingestellt ab', Description='Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580348
;

-- 2021-12-02T10:09:36.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Eingestellt ab', Description='Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.', Help=NULL WHERE AD_Element_ID = 580348
;

-- 2021-12-02T10:09:36.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Eingestellt ab', Description = 'Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580348
;

-- 2021-12-02T10:09:45.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.', Name='Eingestellt ab', PrintName='Eingestellt ab',Updated=TO_TIMESTAMP('2021-12-02 12:09:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580348 AND AD_Language='nl_NL'
;

-- 2021-12-02T10:09:45.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580348,'nl_NL') 
;

-- 2021-12-02T10:11:37.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578802,580348,0,15,208,'DiscontinuedFrom',TO_TIMESTAMP('2021-12-02 12:11:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eingestellt ab',0,0,TO_TIMESTAMP('2021-12-02 12:11:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-12-02T10:11:37.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578802 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-12-02T10:11:37.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580348) 
;

-- 2021-12-02T10:13:36.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@Discontinued@ = ''Y''',Updated=TO_TIMESTAMP('2021-12-02 12:13:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578802
;

-- 2021-12-02T10:16:14.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN DiscontinuedFrom TIMESTAMP WITHOUT TIME ZONE')
;

-- 2021-12-02T10:16:42.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578802,673389,0,180,TO_TIMESTAMP('2021-12-02 12:16:42','YYYY-MM-DD HH24:MI:SS'),100,'Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.',7,'D','Y','N','N','N','N','N','N','N','Eingestellt ab',TO_TIMESTAMP('2021-12-02 12:16:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-02T10:16:42.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=673389 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-12-02T10:16:42.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580348) 
;

-- 2021-12-02T10:16:42.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=673389
;

-- 2021-12-02T10:16:42.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(673389)
;

-- 2021-12-02T10:17:35.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000005,547602,TO_TIMESTAMP('2021-12-02 12:17:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','discontinuedInfo',12,TO_TIMESTAMP('2021-12-02 12:17:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-02T10:18:11.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=547602, SeqNo=10,Updated=TO_TIMESTAMP('2021-12-02 12:18:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575102
;

-- 2021-12-02T10:18:36.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,673389,0,180,597511,547602,'F',TO_TIMESTAMP('2021-12-02 12:18:36','YYYY-MM-DD HH24:MI:SS'),100,'Wenn ein Produkt über das Kontrollkästchen als eingestellt gekennzeichnet ist, kann in diesem Feld angegeben werden, ab welchem Datum es nicht mehr verfügbar ist.','Y','N','N','Y','N','N','N',0,'Eingestellt ab',20,0,0,TO_TIMESTAMP('2021-12-02 12:18:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-02T10:19:54.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Discontinued@ = ''Y''',Updated=TO_TIMESTAMP('2021-12-02 12:19:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=673389
;

update m_product
set discontinuedfrom = '1970-01-01', updatedby = 99, updated = getdate()
where discontinued = 'Y';