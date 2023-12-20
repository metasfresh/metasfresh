-- 2023-01-16T12:47:16.812Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581923,0,'IsCheckDescriptionForMaterialType',TO_TIMESTAMP('2023-01-16 14:47:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Check also description for material type','Check also description for material type',TO_TIMESTAMP('2023-01-16 14:47:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T12:47:16.817Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581923 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_SAP.IsCheckDescriptionForMaterialType
-- 2023-01-16T12:47:40.381Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585491,581923,0,20,542238,'IsCheckDescriptionForMaterialType',TO_TIMESTAMP('2023-01-16 14:47:40','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Check also description for material type',0,0,TO_TIMESTAMP('2023-01-16 14:47:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-16T12:47:40.385Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585491 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-16T12:47:40.390Z
/* DDL */  select update_Column_Translation_From_AD_Element(581923) 
;

-- 2023-01-16T12:47:42.967Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN IsCheckDescriptionForMaterialType CHAR(1) DEFAULT ''N'' CHECK (IsCheckDescriptionForMaterialType IN (''Y'',''N''))')
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> Check also description for material type
-- Column: ExternalSystem_Config_SAP.IsCheckDescriptionForMaterialType
-- 2023-01-16T12:48:24.073Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585491,710160,0,546671,TO_TIMESTAMP('2023-01-16 14:48:23','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Check also description for material type',TO_TIMESTAMP('2023-01-16 14:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T12:48:24.079Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710160 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-16T12:48:24.088Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581923) 
;

-- 2023-01-16T12:48:24.135Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710160
;

-- 2023-01-16T12:48:24.143Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710160)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 20 -> flag.Check also description for material type
-- Column: ExternalSystem_Config_SAP.IsCheckDescriptionForMaterialType
-- 2023-01-16T12:48:45.381Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710160,0,546671,614659,550017,'F',TO_TIMESTAMP('2023-01-16 14:48:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Check also description for material type',20,0,0,TO_TIMESTAMP('2023-01-16 14:48:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-16T12:49:53.518Z
UPDATE AD_Element SET Description='If enabled, the process also looks into the dat file''s product description column for material types that are mapped to a product category.',Updated=TO_TIMESTAMP('2023-01-16 14:49:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581923
;

-- 2023-01-16T12:49:53.526Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581923,'de_DE')
;

-- Element: IsCheckDescriptionForMaterialType
-- 2023-01-16T12:50:07.100Z
UPDATE AD_Element_Trl SET Description='If enabled, the process also looks into the dat file''s product description column for material types that are mapped to a product category.',Updated=TO_TIMESTAMP('2023-01-16 14:50:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581923 AND AD_Language='de_CH'
;

-- 2023-01-16T12:50:07.102Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581923,'de_CH')
;

-- Element: IsCheckDescriptionForMaterialType
-- 2023-01-16T12:50:11.630Z
UPDATE AD_Element_Trl SET Description='If enabled, the process also looks into the dat file''s product description column for material types that are mapped to a product category.',Updated=TO_TIMESTAMP('2023-01-16 14:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581923 AND AD_Language='de_DE'
;

-- 2023-01-16T12:50:11.633Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581923,'de_DE')
;

-- 2023-01-16T12:50:11.634Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581923,'de_DE')
;

-- Element: IsCheckDescriptionForMaterialType
-- 2023-01-16T12:50:13.423Z
UPDATE AD_Element_Trl SET Description='If enabled, the process also looks into the dat file''s product description column for material types that are mapped to a product category.',Updated=TO_TIMESTAMP('2023-01-16 14:50:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581923 AND AD_Language='en_US'
;

-- 2023-01-16T12:50:13.425Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581923,'en_US')
;

-- Element: IsCheckDescriptionForMaterialType
-- 2023-01-16T12:50:15.641Z
UPDATE AD_Element_Trl SET Description='If enabled, the process also looks into the dat file''s product description column for material types that are mapped to a product category.',Updated=TO_TIMESTAMP('2023-01-16 14:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581923 AND AD_Language='fr_CH'
;

-- 2023-01-16T12:50:15.642Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581923,'fr_CH')
;

-- Element: IsCheckDescriptionForMaterialType
-- 2023-01-16T12:50:18.616Z
UPDATE AD_Element_Trl SET Description='If enabled, the process also looks into the dat file''s product description column for material types that are mapped to a product category.',Updated=TO_TIMESTAMP('2023-01-16 14:50:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581923 AND AD_Language='nl_NL'
;

-- 2023-01-16T12:50:18.618Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581923,'nl_NL') 
;

-- Element: IsCheckDescriptionForMaterialType
-- 2023-01-16T16:00:50.141Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, sucht der Prozess auch in der Produktbeschreibungsspalte der dat-Datei nach Materialtypen, die einer Produktkategorie zugeordnet sind.', Name='Auch Beschreibung für Materialtyp prüfen', PrintName='Auch Beschreibung für Materialtyp prüfen',Updated=TO_TIMESTAMP('2023-01-16 18:00:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581923 AND AD_Language='de_CH'
;

-- 2023-01-16T16:00:50.191Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581923,'de_CH')
;

-- Element: IsCheckDescriptionForMaterialType
-- 2023-01-16T16:01:01.206Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, sucht der Prozess auch in der Produktbeschreibungsspalte der dat-Datei nach Materialtypen, die einer Produktkategorie zugeordnet sind.', Name='Auch Beschreibung für Materialtyp prüfen', PrintName='Auch Beschreibung für Materialtyp prüfen',Updated=TO_TIMESTAMP('2023-01-16 18:01:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581923 AND AD_Language='de_DE'
;

-- 2023-01-16T16:01:01.208Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581923,'de_DE')
;

-- 2023-01-16T16:01:01.213Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581923,'de_DE')
;

-- Element: IsCheckDescriptionForMaterialType
-- 2023-01-16T16:01:09.166Z
UPDATE AD_Element_Trl SET Description='Wenn diese Option aktiviert ist, sucht der Prozess auch in der Produktbeschreibungsspalte der dat-Datei nach Materialtypen, die einer Produktkategorie zugeordnet sind.', Name='Auch Beschreibung für Materialtyp prüfen', PrintName='Auch Beschreibung für Materialtyp prüfen',Updated=TO_TIMESTAMP('2023-01-16 18:01:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581923 AND AD_Language='fr_CH'
;

-- 2023-01-16T16:01:09.171Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581923,'fr_CH')
;

