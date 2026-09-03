-- Run mode: SWING_CLIENT

-- 2025-12-17T10:11:21.771Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584376,0,'IntrastatCode',TO_TIMESTAMP('2025-12-17 10:11:21.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Intrastat Code','Intrastat Code',TO_TIMESTAMP('2025-12-17 10:11:21.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-17T10:11:21.784Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584376 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Region.IntrastatCode
-- 2025-12-17T10:12:29.664Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591783,584376,0,10,164,'XX','IntrastatCode',TO_TIMESTAMP('2025-12-17 10:12:29.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Intrastat Code',0,0,TO_TIMESTAMP('2025-12-17 10:12:29.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-17T10:12:29.668Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591783 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-17T10:12:29.687Z
/* DDL */  select update_Column_Translation_From_AD_Element(584376)
;

-- 2025-12-17T10:12:32.223Z
/* DDL */ SELECT public.db_alter_table('C_Region','ALTER TABLE public.C_Region ADD COLUMN IntrastatCode VARCHAR(3)')
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Intrastat Code
-- Column: C_Region.IntrastatCode
-- 2025-12-17T10:13:20.023Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591783,760938,0,136,TO_TIMESTAMP('2025-12-17 10:13:19.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,3,'D','Y','N','N','N','N','N','N','N','Intrastat Code',TO_TIMESTAMP('2025-12-17 10:13:19.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-17T10:13:20.028Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760938 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-17T10:13:20.036Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584376)
;

-- 2025-12-17T10:13:20.047Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760938
;

-- 2025-12-17T10:13:20.052Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760938)
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Intrastat Code
-- Column: C_Region.IntrastatCode
-- 2025-12-17T10:13:56.968Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760938,0,136,540556,641278,'F',TO_TIMESTAMP('2025-12-17 10:13:56.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Intrastat Code',10,0,0,TO_TIMESTAMP('2025-12-17 10:13:56.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Land
-- Column: C_Region.C_Country_ID
-- 2025-12-17T10:29:23.635Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-12-17 10:29:23.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=354
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Sektion
-- Column: C_Region.AD_Org_ID
-- 2025-12-17T10:29:24.846Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-12-17 10:29:24.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=2018
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Mandant
-- Column: C_Region.AD_Client_ID
-- 2025-12-17T10:29:25.764Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-12-17 10:29:25.763000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=350
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Name
-- Column: C_Region.Name
-- 2025-12-17T10:29:26.624Z
-- old: @hasRegion@ causing problems on new
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-12-17 10:29:26.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=351
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Beschreibung
-- Column: C_Region.Description
-- 2025-12-17T10:29:27.459Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-12-17 10:29:27.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=352
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Aktiv
-- Column: C_Region.IsActive
-- 2025-12-17T10:29:28.273Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-12-17 10:29:28.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=353
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Standard
-- Column: C_Region.IsDefault
-- 2025-12-17T10:29:30.054Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-12-17 10:29:30.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=3051
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Sektion
-- Column: C_Region.AD_Org_ID
-- 2025-12-17T10:30:12.381Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-12-17 10:30:12.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545177
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Land
-- Column: C_Region.C_Country_ID
-- 2025-12-17T10:30:12.388Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-12-17 10:30:12.388000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545178
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Name
-- Column: C_Region.Name
-- 2025-12-17T10:30:12.394Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-12-17 10:30:12.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545179
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Beschreibung
-- Column: C_Region.Description
-- 2025-12-17T10:30:12.399Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-12-17 10:30:12.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545180
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Aktiv
-- Column: C_Region.IsActive
-- 2025-12-17T10:30:12.404Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-12-17 10:30:12.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545181
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Standard
-- Column: C_Region.IsDefault
-- 2025-12-17T10:30:12.411Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-12-17 10:30:12.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545182
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Intrastat Code
-- Column: C_Region.IntrastatCode
-- 2025-12-17T10:30:12.423Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-12-17 10:30:12.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641278
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Intrastat Code
-- Column: C_Region.IntrastatCode
-- 2025-12-17T10:32:36.714Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2025-12-17 10:32:36.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641278
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Beschreibung
-- Column: C_Region.Description
-- 2025-12-17T10:35:11.324Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-12-17 10:35:11.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545180
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Sektion
-- Column: C_Region.AD_Org_ID
-- 2025-12-17T10:35:24.653Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-12-17 10:35:24.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545177
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Name
-- Column: C_Region.Name
-- 2025-12-17T10:35:25.450Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-12-17 10:35:25.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545179
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Standard
-- Column: C_Region.IsDefault
-- 2025-12-17T10:35:28.904Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-12-17 10:35:28.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545182
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Aktiv
-- Column: C_Region.IsActive
-- 2025-12-17T10:35:31.973Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-12-17 10:35:31.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545181
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Land
-- Column: C_Region.C_Country_ID
-- 2025-12-17T10:35:38.142Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-12-17 10:35:38.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545178
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Name
-- Column: C_Region.Name
-- 2025-12-17T10:35:38.151Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-12-17 10:35:38.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545179
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Beschreibung
-- Column: C_Region.Description
-- 2025-12-17T10:35:38.158Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-12-17 10:35:38.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545180
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Aktiv
-- Column: C_Region.IsActive
-- 2025-12-17T10:35:38.165Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-12-17 10:35:38.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545181
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Standard
-- Column: C_Region.IsDefault
-- 2025-12-17T10:35:38.173Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-12-17 10:35:38.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545182
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Intrastat Code
-- Column: C_Region.IntrastatCode
-- 2025-12-17T10:35:38.180Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-12-17 10:35:38.180000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641278
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Name
-- Column: C_Region.Name
-- 2025-12-17T10:37:24.302Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-12-17 10:37:24.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545179
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Beschreibung
-- Column: C_Region.Description
-- 2025-12-17T10:37:24.307Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-12-17 10:37:24.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545180
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Intrastat Code
-- Column: C_Region.IntrastatCode
-- 2025-12-17T10:37:24.313Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-12-17 10:37:24.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641278
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Standard
-- Column: C_Region.IsDefault
-- 2025-12-17T10:37:24.322Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-12-17 10:37:24.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545182
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Aktiv
-- Column: C_Region.IsActive
-- 2025-12-17T10:37:24.327Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-12-17 10:37:24.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545181
;

-- UI Element: Land, Region, Stadt(122,D) -> Region(136,D) -> main -> 10 -> default.Sektion
-- Column: C_Region.AD_Org_ID
-- 2025-12-17T10:37:24.334Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-12-17 10:37:24.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545177
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Region
-- Column: C_Region.C_Region_ID
-- 2025-12-17T10:56:08.227Z
UPDATE AD_Field SET SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2025-12-17 10:56:08.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=349
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Mandant
-- Column: C_Region.AD_Client_ID
-- 2025-12-17T10:56:23.867Z
UPDATE AD_Field SET SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2025-12-17 10:56:23.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=350
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Sektion
-- Column: C_Region.AD_Org_ID
-- 2025-12-17T10:56:35.817Z
UPDATE AD_Field SET SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-12-17 10:56:35.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=2018
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Land
-- Column: C_Region.C_Country_ID
-- 2025-12-17T10:56:42.452Z
UPDATE AD_Field SET SeqNoGrid=NULL,Updated=TO_TIMESTAMP('2025-12-17 10:56:42.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=354
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Name
-- Column: C_Region.Name
-- 2025-12-17T10:56:50.703Z
UPDATE AD_Field SET SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-12-17 10:56:50.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=351
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Beschreibung
-- Column: C_Region.Description
-- 2025-12-17T10:57:18.684Z
UPDATE AD_Field SET SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-12-17 10:57:18.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=352
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Intrastat Code
-- Column: C_Region.IntrastatCode
-- 2025-12-17T10:57:24.907Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-12-17 10:57:24.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=760938
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Aktiv
-- Column: C_Region.IsActive
-- 2025-12-17T10:57:32.808Z
UPDATE AD_Field SET SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-12-17 10:57:32.808000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=353
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Standard
-- Column: C_Region.IsDefault
-- 2025-12-17T10:57:39.080Z
UPDATE AD_Field SET SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-12-17 10:57:39.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=3051
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Standard
-- Column: C_Region.IsDefault
-- 2025-12-17T10:57:59.501Z
UPDATE AD_Field SET SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-12-17 10:57:59.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=3051
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Region
-- Column: C_Region.C_Region_ID
-- 2025-12-17T10:59:40.173Z
UPDATE AD_Field SET SeqNo=NULL,Updated=TO_TIMESTAMP('2025-12-17 10:59:40.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=349
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Mandant
-- Column: C_Region.AD_Client_ID
-- 2025-12-17T10:59:43.425Z
UPDATE AD_Field SET SeqNo=NULL,Updated=TO_TIMESTAMP('2025-12-17 10:59:43.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=350
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Land
-- Column: C_Region.C_Country_ID
-- 2025-12-17T10:59:49.762Z
UPDATE AD_Field SET SeqNo=NULL,Updated=TO_TIMESTAMP('2025-12-17 10:59:49.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=354
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Name
-- Column: C_Region.Name
-- 2025-12-17T10:59:56.654Z
UPDATE AD_Field SET SeqNo=10,Updated=TO_TIMESTAMP('2025-12-17 10:59:56.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=351
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Beschreibung
-- Column: C_Region.Description
-- 2025-12-17T11:00:03.973Z
UPDATE AD_Field SET SeqNo=20,Updated=TO_TIMESTAMP('2025-12-17 11:00:03.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=352
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Intrastat Code
-- Column: C_Region.IntrastatCode
-- 2025-12-17T11:00:08.584Z
UPDATE AD_Field SET SeqNo=30,Updated=TO_TIMESTAMP('2025-12-17 11:00:08.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=760938
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Standard
-- Column: C_Region.IsDefault
-- 2025-12-17T11:00:13.123Z
UPDATE AD_Field SET SeqNo=40,Updated=TO_TIMESTAMP('2025-12-17 11:00:13.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=3051
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Aktiv
-- Column: C_Region.IsActive
-- 2025-12-17T11:00:17.840Z
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2025-12-17 11:00:17.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=353
;

-- Field: Land, Region, Stadt(122,D) -> Region(136,D) -> Sektion
-- Column: C_Region.AD_Org_ID
-- 2025-12-17T11:00:23.638Z
UPDATE AD_Field SET SeqNo=60,Updated=TO_TIMESTAMP('2025-12-17 11:00:23.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=2018
;

