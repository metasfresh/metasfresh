-- Run mode: SWING_CLIENT

-- Column: AD_PInstance.CreatedBy
-- 2026-05-21T20:11:24.757Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540401, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-05-21 20:11:24.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8224
;

-- 2026-05-21T20:17:11.894Z
UPDATE AD_User SET IsSystemUser='Y', Login='Migration', Value='migratio',Updated=TO_TIMESTAMP('2026-05-21 20:17:11.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_User_ID=99
;
-- Column: AD_PInstance_Log.P_Date
-- 2026-05-21T20:23:02.585Z
UPDATE AD_Column SET AD_Reference_ID=16, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-05-21 20:23:02.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8782
;

-- 2026-05-21T20:25:21.953Z
INSERT INTO t_alter_column values('ad_pinstance_log','P_Date','TIMESTAMP WITH TIME ZONE',null,null)
;

-- UI Element: Prozess-Revision(332,D) -> Protokoll(665,D) -> main -> 10 -> default.Process Message
-- Column: AD_PInstance_Log.P_Msg
-- 2026-05-21T20:29:02.936Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-05-21 20:29:02.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547987
;

-- UI Element: Prozess-Revision(332,D) -> Protokoll(665,D) -> main -> 10 -> default.Process Number
-- Column: AD_PInstance_Log.P_Number
-- 2026-05-21T20:29:10.069Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-05-21 20:29:10.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547986
;

-- UI Element: Prozess-Revision(332,D) -> Protokoll(665,D) -> main -> 10 -> default.Process Date
-- Column: AD_PInstance_Log.P_Date
-- 2026-05-21T20:29:33.132Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-05-21 20:29:33.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547985
;

-- UI Element: Prozess-Revision(332,D) -> Protokoll(665,D) -> main -> 10 -> default.Eintrag-Nr
-- Column: AD_PInstance_Log.Log_ID
-- 2026-05-21T20:30:52.267Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-05-21 20:30:52.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547984
;

-- Column: AD_PInstance.AD_Table_ID
-- 2026-05-22T07:34:10.878Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-05-22 07:34:10.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551938
;

-- Column: AD_PInstance.ErrorMsg
-- 2026-05-22T07:34:47.012Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-05-22 07:34:47.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=3433
;

-- Column: AD_PInstance.IsProcessing
-- 2026-05-22T07:35:19.692Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-05-22 07:35:19.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2783
;

-- Column: AD_PInstance.AD_User_ID
-- 2026-05-22T07:35:44.563Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-05-22 07:35:44.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=5951
;

-- Field: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> Erstellt durch
-- Column: AD_PInstance.CreatedBy
-- 2026-05-22T08:01:44.957Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8224,780261,0,663,0,TO_TIMESTAMP('2026-05-22 08:01:44.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',0,'D',0,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Erstellt durch',0,0,160,0,1,1,TO_TIMESTAMP('2026-05-22 08:01:44.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-22T08:01:45.024Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-22T08:01:45.116Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2026-05-22T08:01:45.232Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780261
;

-- 2026-05-22T08:01:45.297Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780261)
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> default.Erstellt durch
-- Column: AD_PInstance.CreatedBy
-- 2026-05-22T08:03:02.504Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,780261,0,663,541066,651710,'F',TO_TIMESTAMP('2026-05-22 08:03:01.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','N','N',0,'Erstellt durch',40,0,0,TO_TIMESTAMP('2026-05-22 08:03:01.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> default.Erstellt durch
-- Column: AD_PInstance.CreatedBy
-- 2026-05-22T08:03:12.984Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2026-05-22 08:03:12.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=651710
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.Ablaufsteuerung
-- Column: AD_PInstance.AD_Scheduler_ID
-- 2026-05-22T08:04:13.833Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691444,0,663,541068,651711,'F',TO_TIMESTAMP('2026-05-22 08:04:13.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Schedule Processes','Asynchrone Ausführung von Prozessen definieren','Y','N','N','Y','N','N','N',0,'Ablaufsteuerung',60,0,0,TO_TIMESTAMP('2026-05-22 08:04:13.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> default.Erstellt durch
-- Column: AD_PInstance.CreatedBy
-- 2026-05-22T08:05:08.923Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-05-22 08:05:08.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=651710
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> default.Aktualisiert
-- Column: AD_PInstance.Updated
-- 2026-05-22T08:05:09.282Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-05-22 08:05:09.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547990
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> details.Benutzer
-- Column: AD_PInstance.AD_User_ID
-- 2026-05-22T08:05:09.635Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-05-22 08:05:09.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547992
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> details.Sprache
-- Column: AD_PInstance.AD_Language
-- 2026-05-22T08:05:09.992Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-05-22 08:05:09.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547991
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> details.Rolle
-- Column: AD_PInstance.AD_Role_ID
-- 2026-05-22T08:05:10.343Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-05-22 08:05:10.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547993
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.In Verarbeitung
-- Column: AD_PInstance.IsProcessing
-- 2026-05-22T08:05:10.692Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-05-22 08:05:10.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547996
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.DB Tabelle
-- Column: AD_PInstance.AD_Table_ID
-- 2026-05-22T08:05:11.041Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-05-22 08:05:11.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547997
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.Datensatz
-- Column: AD_PInstance.Record_ID
-- 2026-05-22T08:05:11.397Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-05-22 08:05:11.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547998
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.Fehlermeldung
-- Column: AD_PInstance.ErrorMsg
-- 2026-05-22T08:05:11.742Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-05-22 08:05:11.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548001
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.Ergebnis
-- Column: AD_PInstance.Result
-- 2026-05-22T08:05:12.095Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-05-22 08:05:12.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548002
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> org.Sektion
-- Column: AD_PInstance.AD_Org_ID
-- 2026-05-22T08:05:12.455Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-05-22 08:05:12.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547999
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.Ablaufsteuerung
-- Column: AD_PInstance.AD_Scheduler_ID
-- 2026-05-22T08:05:30.322Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=651711
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> default.Ablaufsteuerung
-- Column: AD_PInstance.AD_Scheduler_ID
-- 2026-05-22T08:05:47.930Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-05-22 08:05:47.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=605265
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> default.Erstellt
-- Column: AD_PInstance.Created
-- 2026-05-22T08:05:48.272Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-05-22 08:05:48.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547989
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> default.Erstellt durch
-- Column: AD_PInstance.CreatedBy
-- 2026-05-22T08:05:48.613Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-05-22 08:05:48.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=651710
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> default.Aktualisiert
-- Column: AD_PInstance.Updated
-- 2026-05-22T08:05:48.962Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-05-22 08:05:48.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547990
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> details.Benutzer
-- Column: AD_PInstance.AD_User_ID
-- 2026-05-22T08:05:49.309Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-05-22 08:05:49.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547992
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> details.Sprache
-- Column: AD_PInstance.AD_Language
-- 2026-05-22T08:05:49.660Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-05-22 08:05:49.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547991
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 10 -> details.Rolle
-- Column: AD_PInstance.AD_Role_ID
-- 2026-05-22T08:05:50.004Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-05-22 08:05:50.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547993
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.In Verarbeitung
-- Column: AD_PInstance.IsProcessing
-- 2026-05-22T08:05:50.367Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-05-22 08:05:50.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547996
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.DB Tabelle
-- Column: AD_PInstance.AD_Table_ID
-- 2026-05-22T08:05:50.725Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-05-22 08:05:50.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547997
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.Datensatz
-- Column: AD_PInstance.Record_ID
-- 2026-05-22T08:05:51.072Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-05-22 08:05:51.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547998
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.Fehlermeldung
-- Column: AD_PInstance.ErrorMsg
-- 2026-05-22T08:05:51.432Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-05-22 08:05:51.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548001
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> flags.Ergebnis
-- Column: AD_PInstance.Result
-- 2026-05-22T08:05:51.783Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-05-22 08:05:51.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548002
;

-- UI Element: Prozess-Revision(332,D) -> Prozess-Revision(663,D) -> main -> 20 -> org.Sektion
-- Column: AD_PInstance.AD_Org_ID
-- 2026-05-22T08:05:52.135Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-05-22 08:05:52.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547999
;

