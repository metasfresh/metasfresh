-- Run mode: SWING_CLIENT

-- Tab: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch)
-- UI Section: main
-- 2025-11-10T09:40:35.295Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544164,547039,TO_TIMESTAMP('2025-11-10 10:40:34.515','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2025-11-10 10:40:34.515','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2025-11-10T09:40:35.358Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547039 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main
-- UI Column: 10
-- 2025-11-10T09:40:45.631Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548588,547039,TO_TIMESTAMP('2025-11-10 10:40:44.804','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2025-11-10 10:40:44.804','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main
-- UI Column: 20
-- 2025-11-10T09:41:05.133Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548589,547039,TO_TIMESTAMP('2025-11-10 10:41:04.368','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2025-11-10 10:41:04.368','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10
-- UI Element Group: Config
-- 2025-11-10T09:42:59.050Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548588,553741,TO_TIMESTAMP('2025-11-10 10:42:58.25','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Config',10,'primary',TO_TIMESTAMP('2025-11-10 10:42:58.25','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.Volltextsuche Konfiguration
-- Column: ES_FTS_Filter.ES_FTS_Config_ID
-- 2025-11-10T09:44:46.369Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650512,0,544164,553741,638562,'F',TO_TIMESTAMP('2025-11-10 10:44:45.547','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Volltextsuche Konfiguration',10,0,0,TO_TIMESTAMP('2025-11-10 10:44:45.547','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.Ereignisart
-- Column: ES_FTS_Index_Queue.EventType
-- 2025-11-10T09:46:29.664Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650506,0,544164,553741,638563,'F',TO_TIMESTAMP('2025-11-10 10:46:28.857','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Ereignisart',20,0,0,TO_TIMESTAMP('2025-11-10 10:46:28.857','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.DB-Tabelle
-- Column: ES_FTS_Index_Queue.AD_Table_ID
-- 2025-11-10T09:47:22.062Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650500,0,544164,553741,638564,'F',TO_TIMESTAMP('2025-11-10 10:47:21.262','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','N','N',0,'DB-Tabelle',30,0,0,TO_TIMESTAMP('2025-11-10 10:47:21.262','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.Datensatz-ID
-- Column: ES_FTS_Index_Queue.Record_ID
-- 2025-11-10T09:48:05.401Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650501,0,544164,553741,638565,'F',TO_TIMESTAMP('2025-11-10 10:48:04.604','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','N','N',0,'Datensatz-ID',40,0,0,TO_TIMESTAMP('2025-11-10 10:48:04.604','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10
-- UI Element Group: Log
-- 2025-11-10T09:48:18.481Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548588,553742,TO_TIMESTAMP('2025-11-10 10:48:17.729','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Log',20,TO_TIMESTAMP('2025-11-10 10:48:17.729','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Log.Probleme
-- Column: ES_FTS_Index_Queue.AD_Issue_ID
-- 2025-11-10T09:48:50.286Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650505,0,544164,553742,638566,'F',TO_TIMESTAMP('2025-11-10 10:48:49.498','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Probleme',10,0,0,TO_TIMESTAMP('2025-11-10 10:48:49.498','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Log.Processing Tag
-- Column: ES_FTS_Index_Queue.ProcessingTag
-- 2025-11-10T09:49:13.023Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650502,0,544164,553742,638567,'F',TO_TIMESTAMP('2025-11-10 10:49:12.215','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Processing Tag',20,0,0,TO_TIMESTAMP('2025-11-10 10:49:12.215','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20
-- UI Element Group: Flags
-- 2025-11-10T09:49:34.220Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548589,553743,TO_TIMESTAMP('2025-11-10 10:49:33.447','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Flags',10,TO_TIMESTAMP('2025-11-10 10:49:33.447','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Flags.Verarbeitet
-- Column: ES_FTS_Index_Queue.Processed
-- 2025-11-10T09:50:15.162Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650503,0,544164,553743,638568,'F',TO_TIMESTAMP('2025-11-10 10:50:14.326','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',10,0,0,TO_TIMESTAMP('2025-11-10 10:50:14.326','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Flags.Fehler
-- Column: ES_FTS_Index_Queue.IsError
-- 2025-11-10T09:50:41.995Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650504,0,544164,553743,638569,'F',TO_TIMESTAMP('2025-11-10 10:50:41.187','YYYY-MM-DD HH24:MI:SS.US'),100,'Ein Fehler ist bei der Durchführung aufgetreten','Y','N','N','Y','N','N','N',0,'Fehler',20,0,0,TO_TIMESTAMP('2025-11-10 10:50:41.187','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20
-- UI Element Group: Dates
-- 2025-11-10T09:51:07.346Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548589,553744,TO_TIMESTAMP('2025-11-10 10:51:06.615','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Dates',20,TO_TIMESTAMP('2025-11-10 10:51:06.615','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> Erstellt
-- Column: ES_FTS_Index_Queue.Created
-- 2025-11-10T09:52:21.619Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575208,755955,0,544164,0,TO_TIMESTAMP('2025-11-10 10:52:20.448','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde',0,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','N','Erstellt',0,90,0,1,1,TO_TIMESTAMP('2025-11-10 10:52:20.448','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-11-10T09:52:21.681Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-10T09:52:21.770Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2025-11-10T09:52:21.916Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755955
;

-- 2025-11-10T09:52:21.976Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755955)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Dates.Erstellt
-- Column: ES_FTS_Index_Queue.Created
-- 2025-11-10T09:53:52.391Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755955,0,544164,553744,638570,'F',TO_TIMESTAMP('2025-11-10 10:53:51.599','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','N','N',0,'Erstellt',10,0,0,TO_TIMESTAMP('2025-11-10 10:53:51.599','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20
-- UI Element Group: Org
-- 2025-11-10T09:54:05.643Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548589,553745,TO_TIMESTAMP('2025-11-10 10:54:04.897','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Org',30,TO_TIMESTAMP('2025-11-10 10:54:04.897','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Org.Organisation
-- Column: ES_FTS_Index_Queue.AD_Org_ID
-- 2025-11-10T09:55:07.015Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650497,0,544164,553745,638571,'F',TO_TIMESTAMP('2025-11-10 10:55:06.213','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2025-11-10 10:55:06.213','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.Volltextsuche Konfiguration
-- Column: ES_FTS_Filter.ES_FTS_Config_ID
-- 2025-11-10T09:56:53.490Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-11-10 10:56:53.49','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638562
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.Ereignisart
-- Column: ES_FTS_Index_Queue.EventType
-- 2025-11-10T09:56:53.852Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-11-10 10:56:53.852','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638563
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.DB-Tabelle
-- Column: ES_FTS_Index_Queue.AD_Table_ID
-- 2025-11-10T09:56:54.213Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-10 10:56:54.213','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638564
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.Datensatz-ID
-- Column: ES_FTS_Index_Queue.Record_ID
-- 2025-11-10T09:56:54.570Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-11-10 10:56:54.569','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638565
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Dates.Erstellt
-- Column: ES_FTS_Index_Queue.Created
-- 2025-11-10T09:56:54.932Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-11-10 10:56:54.931','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638570
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Flags.Fehler
-- Column: ES_FTS_Index_Queue.IsError
-- 2025-11-10T09:56:55.561Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-11-10 10:56:55.561','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638569
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Flags.Verarbeitet
-- Column: ES_FTS_Index_Queue.Processed
-- 2025-11-10T09:56:55.924Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-11-10 10:56:55.924','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638568
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Org.Organisation
-- Column: ES_FTS_Index_Queue.AD_Org_ID
-- 2025-11-10T09:56:56.281Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-11-10 10:56:56.281','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638571
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.FTS Modelle zur Index-Warteschlange
-- Column: ES_FTS_Index_Queue.ES_FTS_Index_Queue_ID
-- 2025-11-10T09:58:30.339Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650499,0,544164,553741,638572,'F',TO_TIMESTAMP('2025-11-10 10:58:29.55','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'FTS Modelle zur Index-Warteschlange',5,0,0,TO_TIMESTAMP('2025-11-10 10:58:29.55','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.FTS Modelle zur Index-Warteschlange
-- Column: ES_FTS_Index_Queue.ES_FTS_Index_Queue_ID
-- 2025-11-10T09:58:42.281Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-11-10 10:58:42.281','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638572
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.Volltextsuche Konfiguration
-- Column: ES_FTS_Filter.ES_FTS_Config_ID
-- 2025-11-10T09:58:42.661Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-11-10 10:58:42.661','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638562
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.Ereignisart
-- Column: ES_FTS_Index_Queue.EventType
-- 2025-11-10T09:58:43.017Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-10 10:58:43.017','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638563
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.DB-Tabelle
-- Column: ES_FTS_Index_Queue.AD_Table_ID
-- 2025-11-10T09:58:43.382Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-11-10 10:58:43.382','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638564
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.Datensatz-ID
-- Column: ES_FTS_Index_Queue.Record_ID
-- 2025-11-10T09:58:43.744Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-11-10 10:58:43.744','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638565
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Dates.Erstellt
-- Column: ES_FTS_Index_Queue.Created
-- 2025-11-10T09:58:44.104Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-11-10 10:58:44.104','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638570
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Flags.Fehler
-- Column: ES_FTS_Index_Queue.IsError
-- 2025-11-10T09:58:44.455Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-11-10 10:58:44.455','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638569
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Flags.Verarbeitet
-- Column: ES_FTS_Index_Queue.Processed
-- 2025-11-10T09:58:44.811Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-11-10 10:58:44.811','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638568
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 20 -> Org.Organisation
-- Column: ES_FTS_Index_Queue.AD_Org_ID
-- 2025-11-10T09:58:45.171Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-11-10 10:58:45.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638571
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.Ereignisart
-- Column: ES_FTS_Index_Queue.EventType
-- 2025-11-10T10:00:05.937Z
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2025-11-10 11:00:05.937','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638563
;

-- UI Element: FTS Modelle zur Index-Warteschlange(541181,de.metas.elasticsearch) -> FTS Modelle zur Index-Warteschlange(544164,de.metas.elasticsearch) -> main -> 10 -> Config.DB-Tabelle
-- Column: ES_FTS_Index_Queue.AD_Table_ID
-- 2025-11-10T10:00:17.392Z
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2025-11-10 11:00:17.392','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=638564
;

-- Column: ES_FTS_Index_Queue.Processed
-- 2025-11-10T10:01:07.121Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-11-10 11:01:07.121','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575217
;

-- Column: ES_FTS_Index_Queue.IsError
-- 2025-11-10T10:01:39.220Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-11-10 11:01:39.22','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575218
;

-- Column: ES_FTS_Index_Queue.AD_Table_ID
-- 2025-11-10T10:02:11.842Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-11-10 11:02:11.842','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575214
;

-- Column: ES_FTS_Index_Queue.Created
-- 2025-11-10T10:02:38.858Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-11-10 11:02:38.858','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575208
;

-- Column: ES_FTS_Index_Queue.ES_FTS_Config_ID
-- 2025-11-10T10:03:01.955Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-11-10 11:03:01.955','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575302
;

-- Column: ES_FTS_Index_Queue.EventType
-- 2025-11-10T10:03:22.189Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-11-10 11:03:22.189','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575220
;

-- Column: ES_FTS_Index_Queue.ProcessingTag
-- 2025-11-10T10:03:54.395Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-11-10 11:03:54.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575216
;

-- Column: ES_FTS_Index_Queue.Record_ID
-- 2025-11-10T10:04:10.778Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-11-10 11:04:10.778','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575215
;

-- Column: ES_FTS_Index_Queue.ES_FTS_Config_ID
-- 2025-11-10T10:05:08.302Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2025-11-10 11:05:08.302','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575302
;

-- Column: ES_FTS_Index_Queue.EventType
-- 2025-11-10T10:05:18.105Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2025-11-10 11:05:18.105','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575220
;

-- Column: ES_FTS_Index_Queue.AD_Table_ID
-- 2025-11-10T10:05:28.309Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2025-11-10 11:05:28.309','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575214
;

-- Column: ES_FTS_Index_Queue.Record_ID
-- 2025-11-10T10:05:37.212Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2025-11-10 11:05:37.212','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575215
;

-- Column: ES_FTS_Index_Queue.Created
-- 2025-11-10T10:05:47.074Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2025-11-10 11:05:47.074','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575208
;

-- Column: ES_FTS_Index_Queue.ProcessingTag
-- 2025-11-10T10:05:55.936Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2025-11-10 11:05:55.935','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575216
;

-- Column: ES_FTS_Index_Queue.IsError
-- 2025-11-10T10:06:04.922Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2025-11-10 11:06:04.922','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575218
;

-- Column: ES_FTS_Index_Queue.Processed
-- 2025-11-10T10:06:13.948Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2025-11-10 11:06:13.948','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575217
;

-- Column: ES_FTS_Index_Queue.AD_Org_ID
-- 2025-11-10T10:06:23.308Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2025-11-10 11:06:23.308','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=575207
;

