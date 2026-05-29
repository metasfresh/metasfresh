-- Run mode: SWING_CLIENT

-- 2025-08-01T06:50:35.534Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583842,0,'IsUpdateLocationName',TO_TIMESTAMP('2025-08-01 06:50:35.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Adressnamen aktualisieren','Adressnamen aktualisieren',TO_TIMESTAMP('2025-08-01 06:50:35.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-01T06:50:35.541Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583842 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsUpdateLocationName
-- 2025-08-01T06:51:01.300Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Update location name',Updated=TO_TIMESTAMP('2025-08-01 06:51:01.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583842 AND AD_Language='en_US'
;

-- 2025-08-01T06:51:01.301Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-01T06:51:01.488Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583842,'en_US')
;

-- Element: IsUpdateLocationName
-- 2025-08-01T06:51:08.614Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-01 06:51:08.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583842 AND AD_Language='de_CH'
;

-- 2025-08-01T06:51:08.621Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583842,'de_CH')
;

-- Element: IsUpdateLocationName
-- 2025-08-01T06:51:11.305Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-01 06:51:11.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583842 AND AD_Language='de_DE'
;

-- 2025-08-01T06:51:11.310Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583842,'de_DE')
;

-- 2025-08-01T06:51:11.312Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583842,'de_DE')
;

-- Column: I_BPartner.IsUpdateLocationName
-- 2025-08-01T06:54:57.708Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590588,583842,0,20,533,'XX','IsUpdateLocationName',TO_TIMESTAMP('2025-08-01 06:54:57.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Adressnamen aktualisieren',0,0,TO_TIMESTAMP('2025-08-01 06:54:57.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-01T06:54:57.709Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590588 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-01T07:06:01.305Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN IsUpdateLocationName CHAR(1) DEFAULT ''N'' CHECK (IsUpdateLocationName IN (''Y'',''N'')) NOT NULL')
;

-- 2025-08-01T06:54:57.713Z
/* DDL */  select update_Column_Translation_From_AD_Element(583842)
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Adressnamen aktualisieren
-- Column: I_BPartner.IsUpdateLocationName
-- 2025-08-01T06:55:58.037Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590588,751750,0,441,TO_TIMESTAMP('2025-08-01 06:55:57.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Adressnamen aktualisieren',TO_TIMESTAMP('2025-08-01 06:55:57.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-01T06:55:58.040Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751750 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-01T06:55:58.043Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583842)
;

-- 2025-08-01T06:55:58.049Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751750
;

-- 2025-08-01T06:55:58.052Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751750)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> main -> 10 -> address.Adressnamen aktualisieren
-- Column: I_BPartner.IsUpdateLocationName
-- 2025-08-01T06:56:53.295Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,751750,0,441,541264,636087,'F',TO_TIMESTAMP('2025-08-01 06:56:53.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Adressnamen aktualisieren',120,0,0,TO_TIMESTAMP('2025-08-01 06:56:53.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

