-- Column: M_PriceList_Version.C_Region_ID
-- Column: M_PriceList_Version.C_Region_ID
-- 2024-09-12T07:34:01.833Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588976,209,0,19,295,153,'C_Region_ID',TO_TIMESTAMP('2024-09-12 07:34:01.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Identifiziert eine geographische Region','D',0,10,'"Region" bezeichnet eine Region oder einen Bundesstaat in diesem Land.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Region',0,0,TO_TIMESTAMP('2024-09-12 07:34:01.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-09-12T07:34:01.842Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588976 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-12T07:34:01.845Z
/* DDL */  select update_Column_Translation_From_AD_Element(209)
;

-- 2024-09-12T07:34:06.523Z
/* DDL */ SELECT public.db_alter_table('M_PriceList_Version','ALTER TABLE public.M_PriceList_Version ADD COLUMN C_Region_ID NUMERIC(10)')
;

-- 2024-09-12T07:34:06.613Z
ALTER TABLE M_PriceList_Version ADD CONSTRAINT CRegion_MPriceListVersion FOREIGN KEY (C_Region_ID) REFERENCES public.C_Region DEFERRABLE INITIALLY DEFERRED
;

-- Field: Preisliste -> Preislisten Version -> Region
-- Column: M_PriceList_Version.C_Region_ID
-- Field: Preisliste(540321,D) -> Preislisten Version(540777,D) -> Region
-- Column: M_PriceList_Version.C_Region_ID
-- 2024-09-12T07:36:30.657Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588976,729880,0,540777,TO_TIMESTAMP('2024-09-12 07:36:30.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert eine geographische Region',10,'D','"Region" bezeichnet eine Region oder einen Bundesstaat in diesem Land.','Y','N','N','N','N','N','N','N','Region',TO_TIMESTAMP('2024-09-12 07:36:30.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T07:36:30.658Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T07:36:30.661Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(209)
;

-- 2024-09-12T07:36:30.666Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729880
;

-- 2024-09-12T07:36:30.667Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729880)
;

-- UI Element: Preisliste -> Preislisten Version.Region
-- Column: M_PriceList_Version.C_Region_ID
-- UI Element: Preisliste(540321,D) -> Preislisten Version(540777,D) -> main -> 10 -> default.Region
-- Column: M_PriceList_Version.C_Region_ID
-- 2024-09-12T07:37:26.603Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729880,0,540777,540086,625334,'F',TO_TIMESTAMP('2024-09-12 07:37:26.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert eine geographische Region','"Region" bezeichnet eine Region oder einen Bundesstaat in diesem Land.','Y','N','N','Y','N','N','N',0,'Region',100,0,0,TO_TIMESTAMP('2024-09-12 07:37:26.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preisliste -> Preislisten Version.Region
-- Column: M_PriceList_Version.C_Region_ID
-- UI Element: Preisliste(540321,D) -> Preislisten Version(540777,D) -> main -> 10 -> default.Region
-- Column: M_PriceList_Version.C_Region_ID
-- 2024-09-12T07:37:50.878Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-09-12 07:37:50.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625334
;

-- UI Element: Preisliste -> Preislisten Version.Basis-Preislistenversion
-- Column: M_PriceList_Version.M_Pricelist_Version_Base_ID
-- UI Element: Preisliste(540321,D) -> Preislisten Version(540777,D) -> main -> 10 -> default.Basis-Preislistenversion
-- Column: M_PriceList_Version.M_Pricelist_Version_Base_ID
-- 2024-09-12T07:37:50.888Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-12 07:37:50.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=541388
;

-- 2024-09-12T07:46:08.536Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583253,0,TO_TIMESTAMP('2024-09-12 07:46:08.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lieferregion für kalkulierte Zuschläge','D','Y','Lieferregion','Lieferregion',TO_TIMESTAMP('2024-09-12 07:46:08.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T07:46:08.539Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583253 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2024-09-12T07:46:44.417Z
UPDATE AD_Element_Trl SET Description='Delivery Region for calculated surcharge', Name='Delivery Region', PrintName='Delivery Region',Updated=TO_TIMESTAMP('2024-09-12 07:46:44.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583253 AND AD_Language='en_US'
;

-- 2024-09-12T07:46:44.420Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583253,'en_US')
;

-- Field: Preisliste -> Preislisten Version -> Lieferregion
-- Column: M_PriceList_Version.C_Region_ID
-- Field: Preisliste(540321,D) -> Preislisten Version(540777,D) -> Lieferregion
-- Column: M_PriceList_Version.C_Region_ID
-- 2024-09-12T07:47:14.660Z
UPDATE AD_Field SET AD_Name_ID=583253, Description='Lieferregion für kalkulierte Zuschläge', Help=NULL, Name='Lieferregion',Updated=TO_TIMESTAMP('2024-09-12 07:47:14.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729880
;

-- 2024-09-12T07:47:14.661Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583253)
;

-- 2024-09-12T07:47:14.666Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729880
;

-- 2024-09-12T07:47:14.667Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729880)
;

-- Column: M_PriceList_Version.C_Region_ID
-- Column: M_PriceList_Version.C_Region_ID
-- 2024-09-12T07:52:23.990Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=157, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-09-12 07:52:23.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=588976
;

