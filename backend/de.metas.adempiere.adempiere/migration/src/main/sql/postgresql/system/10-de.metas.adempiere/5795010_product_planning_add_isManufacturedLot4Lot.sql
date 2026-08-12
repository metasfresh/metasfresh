-- Run mode: SWING_CLIENT

-- 2026-03-23T07:23:30.596Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584693,0,'IsManufacturedLot4Lot',TO_TIMESTAMP('2026-03-23 07:23:30.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Produziert Lot for Lot','Produziert Lot for Lot',TO_TIMESTAMP('2026-03-23 07:23:30.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-23T07:23:30.609Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584693 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsManufacturedLot4Lot
-- 2026-03-23T07:24:05.509Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Manufactured Lot for Lot',Updated=TO_TIMESTAMP('2026-03-23 07:24:05.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584693 AND AD_Language='en_US'
;

-- 2026-03-23T07:24:05.510Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-23T07:24:05.996Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584693,'en_US')
;

-- Element: IsManufacturedLot4Lot
-- 2026-03-23T07:24:07.088Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-23 07:24:07.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584693 AND AD_Language='de_CH'
;

-- 2026-03-23T07:24:07.091Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584693,'de_CH')
;

-- Element: IsManufacturedLot4Lot
-- 2026-03-23T07:24:08.798Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-23 07:24:08.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584693 AND AD_Language='de_DE'
;

-- 2026-03-23T07:24:08.800Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584693,'de_DE')
;

-- 2026-03-23T07:24:08.803Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584693,'de_DE')
;

-- Column: PP_Product_Planning.IsManufacturedLot4Lot
-- 2026-03-23T07:28:48.305Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592260,584693,0,20,53020,'XX','IsManufacturedLot4Lot',TO_TIMESTAMP('2026-03-23 07:28:48.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'Produziert Lot for Lot',0,0,TO_TIMESTAMP('2026-03-23 07:28:48.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-03-23T07:28:48.312Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592260 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-03-23T07:28:48.319Z
/* DDL */  select update_Column_Translation_From_AD_Element(584693)
;

-- 2026-03-23T07:50:37.744Z
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN IsManufacturedLot4Lot CHAR(1) DEFAULT ''N'' CHECK (IsManufacturedLot4Lot IN (''Y'',''N'')) NOT NULL')
;

-- Field: Produkt Plandaten(540750,D) -> Produktplanung(542102,D) -> Produziert Lot for Lot
-- Column: PP_Product_Planning.IsManufacturedLot4Lot
-- 2026-03-23T07:58:12.204Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592260,775479,0,542102,TO_TIMESTAMP('2026-03-23 07:58:12.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Produziert Lot for Lot',TO_TIMESTAMP('2026-03-23 07:58:12.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-23T07:58:12.208Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=775479 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-23T07:58:12.214Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584693)
;

-- 2026-03-23T07:58:12.225Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=775479
;

-- 2026-03-23T07:58:12.229Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(775479)
;

-- UI Element: Produkt Plandaten(540750,D) -> Produktplanung(542102,D) -> main -> 20 -> flags.Produziert Lot for Lot
-- Column: PP_Product_Planning.IsManufacturedLot4Lot
-- 2026-03-23T07:59:29.720Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,775479,0,542102,543143,648880,'F',TO_TIMESTAMP('2026-03-23 07:59:29.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Produziert Lot for Lot',72,0,0,TO_TIMESTAMP('2026-03-23 07:59:29.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Produkt Plandaten(540750,D) -> Produktplanung(542102,D) -> Produziert Lot for Lot
-- Column: PP_Product_Planning.IsManufacturedLot4Lot
-- 2026-03-23T07:59:53.241Z
UPDATE AD_Field SET DisplayLogic='@IsManufactured/''N''@=''Y''',Updated=TO_TIMESTAMP('2026-03-23 07:59:53.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=775479
;
