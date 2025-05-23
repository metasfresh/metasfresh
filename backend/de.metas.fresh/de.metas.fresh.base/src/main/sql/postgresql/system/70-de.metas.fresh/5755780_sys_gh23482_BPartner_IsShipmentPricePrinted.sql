-- 2025-05-23T11:15:58.437Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583670,0,'IsShipmentPricePrinted',TO_TIMESTAMP('2025-05-23 11:15:57.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','D','','Y','Preis auf Lieferschein','Preis auf Lieferschein',TO_TIMESTAMP('2025-05-23 11:15:57.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-23T11:15:58.490Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583670 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsShipmentPricePrinted
-- 2025-05-23T11:16:49.698Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Price on Delivery Note', PrintName='Price on Delivery Note',Updated=TO_TIMESTAMP('2025-05-23 11:16:49.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583670 AND AD_Language='en_US'
;

-- 2025-05-23T11:16:49.818Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583670,'en_US') 
;

-- Column: C_BPartner.IsShipmentPricePrinted
-- Column: C_BPartner.IsShipmentPricePrinted
-- 2025-05-23T11:17:12.236Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590131,583670,0,20,291,'XX','IsShipmentPricePrinted',TO_TIMESTAMP('2025-05-23 11:17:11.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','','D',0,1,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Preis auf Lieferschein',0,0,TO_TIMESTAMP('2025-05-23 11:17:11.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-05-23T11:17:12.287Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590131 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-23T11:17:12.389Z
/* DDL */  select update_Column_Translation_From_AD_Element(583670) 
;

-- 2025-05-23T11:17:28.578Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsShipmentPricePrinted CHAR(1) DEFAULT ''N'' CHECK (IsShipmentPricePrinted IN (''Y'',''N'')) NOT NULL')
;

-- Field: Geschäftspartner_OLD -> Kunde -> Preis auf Lieferschein
-- Column: C_BPartner.IsShipmentPricePrinted
-- Field: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> Preis auf Lieferschein
-- Column: C_BPartner.IsShipmentPricePrinted
-- 2025-05-23T11:19:22.657Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590131,746304,0,223,0,TO_TIMESTAMP('2025-05-23 11:19:21.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',0,'D',0,'',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Preis auf Lieferschein',0,0,350,0,1,1,TO_TIMESTAMP('2025-05-23 11:19:21.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-23T11:19:22.706Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=746304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-23T11:19:22.755Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583670) 
;

-- 2025-05-23T11:19:22.814Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=746304
;

-- 2025-05-23T11:19:22.863Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(746304)
;

-- UI Element: Geschäftspartner_OLD -> Kunde.Preis auf Lieferschein
-- Column: C_BPartner.IsShipmentPricePrinted
-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Preis auf Lieferschein
-- Column: C_BPartner.IsShipmentPricePrinted
-- 2025-05-23T11:20:15.869Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,746304,0,223,540672,633244,'F',TO_TIMESTAMP('2025-05-23 11:20:15.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Preis auf Lieferschein',75,0,0,TO_TIMESTAMP('2025-05-23 11:20:15.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

