-- Run mode: SWING_CLIENT

-- 2025-09-25T14:31:55.097Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584033,0,'IsFullAccess',TO_TIMESTAMP('2025-09-25 14:31:54.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Grant Full Access','Grant Full Access',TO_TIMESTAMP('2025-09-25 14:31:54.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T14:31:55.100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584033 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-09-25T14:32:17.364Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=584033
;

-- 2025-09-25T14:32:17.370Z
DELETE FROM AD_Element WHERE AD_Element_ID=584033
;

-- 2025-09-25T14:32:32.597Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584034,0,'IsAllowAllActions',TO_TIMESTAMP('2025-09-25 14:32:32.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Allow all Actions','Allow all Actions',TO_TIMESTAMP('2025-09-25 14:32:32.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T14:32:32.600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584034 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: Mobile_Application_Access.IsAllowAllActions
-- 2025-09-25T14:32:41.756Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591125,584034,0,20,542446,'XX','IsAllowAllActions',TO_TIMESTAMP('2025-09-25 14:32:41.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow all Actions',0,0,TO_TIMESTAMP('2025-09-25 14:32:41.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-25T14:32:41.758Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591125 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-25T14:32:41.762Z
/* DDL */  select update_Column_Translation_From_AD_Element(584034)
;

-- 2025-09-25T14:32:42.354Z
/* DDL */ SELECT public.db_alter_table('Mobile_Application_Access','ALTER TABLE public.Mobile_Application_Access ADD COLUMN IsAllowAllActions CHAR(1) DEFAULT ''Y'' CHECK (IsAllowAllActions IN (''Y'',''N'')) NOT NULL')
;

-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Allow all Actions
-- Column: Mobile_Application_Access.IsAllowAllActions
-- 2025-09-25T14:32:58.416Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591125,754207,0,547621,TO_TIMESTAMP('2025-09-25 14:32:58.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Allow all Actions',TO_TIMESTAMP('2025-09-25 14:32:58.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T14:32:58.418Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754207 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-25T14:32:58.421Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584034)
;

-- 2025-09-25T14:32:58.424Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754207
;

-- 2025-09-25T14:32:58.425Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754207)
;

-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Allow all Actions
-- Column: Mobile_Application_Access.IsAllowAllActions
-- 2025-09-25T14:33:09.947Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2025-09-25 14:33:09.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754207
;

-- Tab: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D)
-- UI Section: main
-- 2025-09-25T14:33:35.355Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547621,546962,TO_TIMESTAMP('2025-09-25 14:33:35.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-09-25 14:33:35.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-09-25T14:33:35.358Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546962 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main
-- UI Column: 10
-- 2025-09-25T14:33:39.102Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548477,546962,TO_TIMESTAMP('2025-09-25 14:33:38.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-25 14:33:38.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main
-- UI Column: 20
-- 2025-09-25T14:33:40.240Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548478,546962,TO_TIMESTAMP('2025-09-25 14:33:40.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-09-25 14:33:40.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 20
-- UI Element Group: flags
-- 2025-09-25T14:33:47.886Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548478,553548,TO_TIMESTAMP('2025-09-25 14:33:46.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-09-25 14:33:46.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 20 -> flags.Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2025-09-25T14:33:56.653Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731874,0,547621,553548,637298,'F',TO_TIMESTAMP('2025-09-25 14:33:56.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2025-09-25 14:33:56.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 20 -> flags.Allow all Actions
-- Column: Mobile_Application_Access.IsAllowAllActions
-- 2025-09-25T14:34:05.847Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754207,0,547621,553548,637299,'F',TO_TIMESTAMP('2025-09-25 14:34:05.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Allow all Actions',20,0,0,TO_TIMESTAMP('2025-09-25 14:34:05.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 10
-- UI Element Group: primary
-- 2025-09-25T14:34:31.461Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548477,553549,TO_TIMESTAMP('2025-09-25 14:34:31.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','primary',10,'primary',TO_TIMESTAMP('2025-09-25 14:34:31.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 10 -> primary.Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2025-09-25T14:34:41.527Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731876,0,547621,553549,637300,'F',TO_TIMESTAMP('2025-09-25 14:34:41.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Mobile Application',10,0,0,TO_TIMESTAMP('2025-09-25 14:34:41.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 10 -> primary.Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2025-09-25T14:34:49.469Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-09-25 14:34:49.469000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637300
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 20 -> flags.Allow all Actions
-- Column: Mobile_Application_Access.IsAllowAllActions
-- 2025-09-25T14:34:49.476Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-25 14:34:49.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637299
;

-- UI Element: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> main -> 20 -> flags.Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2025-09-25T14:34:49.481Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-25 14:34:49.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637298
;

-- Field: Mobile Application Role Access(541827,D) -> Mobile Application Role Access(547621,D) -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2025-09-25T14:35:00.771Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2025-09-25 14:35:00.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=731876
;

-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Allow all Actions
-- Column: Mobile_Application_Access.IsAllowAllActions
-- 2025-09-25T14:35:13.421Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591125,754208,0,547620,TO_TIMESTAMP('2025-09-25 14:35:12.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Allow all Actions',TO_TIMESTAMP('2025-09-25 14:35:12.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T14:35:13.422Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-25T14:35:13.424Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584034)
;

-- 2025-09-25T14:35:13.427Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754208
;

-- 2025-09-25T14:35:13.427Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754208)
;

-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Allow all Actions
-- Column: Mobile_Application_Access.IsAllowAllActions
-- 2025-09-25T14:35:29.304Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2025-09-25 14:35:29.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754208
;

-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2025-09-25T14:35:29.323Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2025-09-25 14:35:29.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=731868
;

-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Allow all Actions
-- Column: Mobile_Application_Access.IsAllowAllActions
-- 2025-09-25T14:35:39.661Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-25 14:35:39.661000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754208
;

-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Aktiv
-- Column: Mobile_Application_Access.IsActive
-- 2025-09-25T14:35:39.669Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-25 14:35:39.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=731868
;

-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Role Access(547620,D) -> Mobile Application
-- Column: Mobile_Application_Access.Mobile_Application_ID
-- 2025-09-25T14:35:46.838Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2025-09-25 14:35:46.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=731870
;





















































-- Run mode: SWING_CLIENT

-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:24:30.192Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591126,583308,0,30,542534,'XX','Mobile_Application_ID',TO_TIMESTAMP('2025-09-25 15:24:29.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Mobile Application',0,0,TO_TIMESTAMP('2025-09-25 15:24:29.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-25T15:24:30.197Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591126 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-25T15:24:30.546Z
/* DDL */  select update_Column_Translation_From_AD_Element(583308)
;

-- 2025-09-25T15:24:32.284Z
/* DDL */ CREATE TABLE public.Mobile_Application_Action_Access (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Role_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Mobile_Application_Action_Access_ID NUMERIC(10) NOT NULL, Mobile_Application_Action_ID NUMERIC(10) NOT NULL, Mobile_Application_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADRole_MobileApplicationActionAccess FOREIGN KEY (AD_Role_ID) REFERENCES public.AD_Role DEFERRABLE INITIALLY DEFERRED, CONSTRAINT Mobile_Application_Action_Access_Key PRIMARY KEY (Mobile_Application_Action_Access_ID), CONSTRAINT MobileApplicationAction_MobileApplicationActionAccess FOREIGN KEY (Mobile_Application_Action_ID) REFERENCES public.Mobile_Application_Action DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MobileApplication_MobileApplicationActionAccess FOREIGN KEY (Mobile_Application_ID) REFERENCES public.Mobile_Application DEFERRABLE INITIALLY DEFERRED)
;

-- Name: Mobile_Application_Action by Mobile_Application_ID
-- 2025-09-25T15:25:10.212Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540743,'Mobile_Application_Action.Mobile_Application_ID=@Mobile_Application_ID@',TO_TIMESTAMP('2025-09-25 15:25:10.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Mobile_Application_Action by Mobile_Application_ID','S',TO_TIMESTAMP('2025-09-25 15:25:10.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: Mobile_Application_Action_Access.Mobile_Application_Action_ID
-- 2025-09-25T15:25:23.719Z
UPDATE AD_Column SET AD_Val_Rule_ID=540743,Updated=TO_TIMESTAMP('2025-09-25 15:25:23.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591124
;

-- Field: Mobile Application Action Access(541950,D) -> Mobile Application Action Access(548439,D) -> Mobile Application
-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:25:37.441Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591126,754209,0,548439,TO_TIMESTAMP('2025-09-25 15:25:37.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Mobile Application',TO_TIMESTAMP('2025-09-25 15:25:37.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T15:25:37.445Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754209 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-25T15:25:37.450Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583308)
;

-- 2025-09-25T15:25:37.477Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754209
;

-- 2025-09-25T15:25:37.483Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754209)
;

-- Field: Mobile Application Action Access(541950,D) -> Mobile Application Action Access(548439,D) -> Mobile Application Action
-- Column: Mobile_Application_Action_Access.Mobile_Application_Action_ID
-- 2025-09-25T15:25:52.372Z
UPDATE AD_Field SET SortNo=3.000000000000,Updated=TO_TIMESTAMP('2025-09-25 15:25:52.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754200
;

-- Field: Mobile Application Action Access(541950,D) -> Mobile Application Action Access(548439,D) -> Mobile Application
-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:26:06.809Z
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2025-09-25 15:26:06.808000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754209
;

-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:26:17.536Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-09-25 15:26:17.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591126
;

-- UI Element: Mobile Application Action Access(541950,D) -> Mobile Application Action Access(548439,D) -> main -> 10 -> primary.Mobile Application
-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:26:56.984Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754209,0,548439,553544,637301,'F',TO_TIMESTAMP('2025-09-25 15:26:56.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Mobile Application',30,0,0,TO_TIMESTAMP('2025-09-25 15:26:56.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile Application Action Access(541950,D) -> Mobile Application Action Access(548439,D) -> main -> 10 -> primary.Mobile Application Action
-- Column: Mobile_Application_Action_Access.Mobile_Application_Action_ID
-- 2025-09-25T15:27:10.049Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2025-09-25 15:27:10.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637294
;

-- UI Element: Mobile Application Action Access(541950,D) -> Mobile Application Action Access(548439,D) -> main -> 10 -> primary.Mobile Application
-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:27:13.591Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2025-09-25 15:27:13.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637301
;

-- UI Element: Mobile Application Action Access(541950,D) -> Mobile Application Action Access(548439,D) -> main -> 10 -> primary.Mobile Application
-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:27:25.066Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-25 15:27:25.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637301
;

-- UI Element: Mobile Application Action Access(541950,D) -> Mobile Application Action Access(548439,D) -> main -> 10 -> primary.Mobile Application Action
-- Column: Mobile_Application_Action_Access.Mobile_Application_Action_ID
-- 2025-09-25T15:27:25.073Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-25 15:27:25.073000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637294
;

-- UI Element: Mobile Application Action Access(541950,D) -> Mobile Application Action Access(548439,D) -> main -> 20 -> flags.Aktiv
-- Column: Mobile_Application_Action_Access.IsActive
-- 2025-09-25T15:27:25.080Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-25 15:27:25.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637295
;

-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Action Access(548440,D) -> Mobile Application
-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:27:36.919Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591126,754210,0,548440,TO_TIMESTAMP('2025-09-25 15:27:36.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Mobile Application',TO_TIMESTAMP('2025-09-25 15:27:36.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-25T15:27:36.923Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754210 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-25T15:27:36.926Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583308)
;

-- 2025-09-25T15:27:36.929Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754210
;

-- 2025-09-25T15:27:36.930Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754210)
;

-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Action Access(548440,D) -> Mobile Application
-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:27:44.918Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2025-09-25 15:27:44.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754210
;

-- Field: Rolle - Verwaltung(111,D) -> Mobile Application Action Access(548440,D) -> Mobile Application Action
-- Column: Mobile_Application_Action_Access.Mobile_Application_Action_ID
-- 2025-09-25T15:27:48.764Z
UPDATE AD_Field SET SortNo=2.000000000000,Updated=TO_TIMESTAMP('2025-09-25 15:27:48.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754206
;

-- Column: Mobile_Application_Action.Mobile_Application_ID
-- 2025-09-25T15:28:12.842Z
UPDATE AD_Column SET IsIdentifier='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-09-25 15:28:12.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591110
;

-- UI Element: Rolle - Verwaltung(111,D) -> Mobile Application Action Access(548440,D) -> main -> 10 -> primary.Mobile Application
-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:28:39.713Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754210,0,548440,553546,637302,'F',TO_TIMESTAMP('2025-09-25 15:28:39.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Mobile Application',20,0,0,TO_TIMESTAMP('2025-09-25 15:28:39.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Rolle - Verwaltung(111,D) -> Mobile Application Action Access(548440,D) -> main -> 10 -> primary.Mobile Application
-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:28:50.294Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2025-09-25 15:28:50.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637302
;

-- UI Element: Rolle - Verwaltung(111,D) -> Mobile Application Action Access(548440,D) -> main -> 10 -> primary.Mobile Application Action
-- Column: Mobile_Application_Action_Access.Mobile_Application_Action_ID
-- 2025-09-25T15:28:53.011Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2025-09-25 15:28:53.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637296
;

-- UI Element: Rolle - Verwaltung(111,D) -> Mobile Application Action Access(548440,D) -> main -> 10 -> primary.Mobile Application
-- Column: Mobile_Application_Action_Access.Mobile_Application_ID
-- 2025-09-25T15:29:06.934Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-09-25 15:29:06.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637302
;

-- UI Element: Rolle - Verwaltung(111,D) -> Mobile Application Action Access(548440,D) -> main -> 10 -> primary.Mobile Application Action
-- Column: Mobile_Application_Action_Access.Mobile_Application_Action_ID
-- 2025-09-25T15:29:06.942Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-25 15:29:06.942000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637296
;

-- UI Element: Rolle - Verwaltung(111,D) -> Mobile Application Action Access(548440,D) -> main -> 20 -> flags.Aktiv
-- Column: Mobile_Application_Action_Access.IsActive
-- 2025-09-25T15:29:06.948Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-25 15:29:06.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637297
;

