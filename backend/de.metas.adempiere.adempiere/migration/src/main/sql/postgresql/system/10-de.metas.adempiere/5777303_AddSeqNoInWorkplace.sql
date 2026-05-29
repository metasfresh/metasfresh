

-- Column: C_Workplace.SeqNo
-- 2025-11-19T08:18:08.352Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591579,566,0,11,542375,'XX','SeqNo',TO_TIMESTAMP('2025-11-19 08:18:08.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM C_Workplace','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',0,22,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2025-11-19 08:18:08.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-19T08:18:08.366Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591579 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-19T08:18:08.373Z
/* DDL */  select update_Column_Translation_From_AD_Element(566)
;

-- Column: C_Workplace.SeqNo
-- 2025-11-19T08:21:18.222Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-11-19 08:21:18.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591579
;

-- 2025-11-19T08:21:19.463Z
/* DDL */ SELECT public.db_alter_table('C_Workplace','ALTER TABLE public.C_Workplace ADD COLUMN SeqNo NUMERIC(10)')
;

-- Column: C_Workplace.SeqNo
-- 2025-11-19T08:21:23.363Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-11-19 08:21:23.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591579
;

-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Reihenfolge
-- Column: C_Workplace.SeqNo
-- 2025-11-19T08:56:25.906Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591579,756207,0,547260,0,TO_TIMESTAMP('2025-11-19 08:56:25.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',0,'D',0,'"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Reihenfolge',0,0,40,0,1,1,TO_TIMESTAMP('2025-11-19 08:56:25.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-19T08:56:25.917Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756207 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-19T08:56:25.924Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2025-11-19T08:56:25.984Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756207
;

-- 2025-11-19T08:56:25.989Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756207)
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> limits.Reihenfolge
-- Column: C_Workplace.SeqNo
-- 2025-11-19T09:06:39.650Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756207,0,547260,553770,638757,'F',TO_TIMESTAMP('2025-11-19 09:06:39.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','Y','N','Y','N','N','N',0,'Reihenfolge',70,0,0,TO_TIMESTAMP('2025-11-19 09:06:39.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> limits.Reihenfolge
-- Column: C_Workplace.SeqNo
-- 2025-11-19T09:06:52.202Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-11-19 09:06:52.201000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638757
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Name
-- Column: C_Workplace.Name
-- 2025-11-19T09:06:52.211Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-11-19 09:06:52.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621124
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Lager
-- Column: C_Workplace.M_Warehouse_ID
-- 2025-11-19T09:06:52.220Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-19 09:06:52.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621125
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> description.Beschreibung
-- Column: C_Workplace.Description
-- 2025-11-19T09:06:52.228Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-11-19 09:06:52.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621126
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Workplace.IsActive
-- 2025-11-19T09:06:52.236Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-11-19 09:06:52.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621127
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> orgs.Sektion
-- Column: C_Workplace.AD_Org_ID
-- 2025-11-19T09:06:52.243Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-11-19 09:06:52.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621128
;

-- Field: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> Reihenfolge
-- Column: C_Workplace.SeqNo
-- 2025-11-19T09:27:59.888Z
UPDATE AD_Field SET SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-11-19 09:27:59.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756207
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Name
-- Column: C_Workplace.Name
-- 2025-11-19T09:30:17.333Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-11-19 09:30:17.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621124
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Lager
-- Column: C_Workplace.M_Warehouse_ID
-- 2025-11-19T09:30:17.344Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-11-19 09:30:17.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621125
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> description.Beschreibung
-- Column: C_Workplace.Description
-- 2025-11-19T09:30:17.349Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-19 09:30:17.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621126
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Workplace.IsActive
-- 2025-11-19T09:30:17.356Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-11-19 09:30:17.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621127
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> limits.Reihenfolge
-- Column: C_Workplace.SeqNo
-- 2025-11-19T09:30:17.361Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-11-19 09:30:17.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638757
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> limits.Reihenfolge
-- Column: C_Workplace.SeqNo
-- 2025-11-19T09:30:55.915Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-11-19 09:30:55.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638757
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Name
-- Column: C_Workplace.Name
-- 2025-11-19T09:30:55.921Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-11-19 09:30:55.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621124
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Lager
-- Column: C_Workplace.M_Warehouse_ID
-- 2025-11-19T09:30:55.925Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-19 09:30:55.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621125
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> description.Beschreibung
-- Column: C_Workplace.Description
-- 2025-11-19T09:30:55.930Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-11-19 09:30:55.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621126
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Workplace.IsActive
-- 2025-11-19T09:30:55.935Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-11-19 09:30:55.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621127
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Reihenfolge
-- Column: C_Workplace.SeqNo
-- 2025-11-19T09:31:14.328Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551256, SeqNo=50,Updated=TO_TIMESTAMP('2025-11-19 09:31:14.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638757
;

-- UI Element: Arbeitsplatz(541744,D) -> Arbeitsplatz(547260,D) -> main -> 10 -> main.Reihenfolge
-- Column: C_Workplace.SeqNo
-- 2025-11-19T09:34:13.083Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2025-11-19 09:34:13.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638757
;

