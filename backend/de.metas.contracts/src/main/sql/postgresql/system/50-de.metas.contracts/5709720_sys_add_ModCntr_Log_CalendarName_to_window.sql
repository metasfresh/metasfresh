-- Run mode: SWING_CLIENT

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Kalender
-- Column: I_ModCntr_Log.C_Calendar_ID
-- 2023-11-07T08:56:23.509Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587629,721752,0,547233,0,TO_TIMESTAMP('2023-11-07 10:56:23.222','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnung des Buchführungs-Kalenders',0,'de.metas.contracts','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.',0,'Y','Y','Y','N','N','N','N','N','N','Kalender',0,80,0,1,1,TO_TIMESTAMP('2023-11-07 10:56:23.222','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-07T08:56:23.519Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721752 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-07T08:56:23.549Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(190)
;

-- 2023-11-07T08:56:23.576Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721752
;

-- 2023-11-07T08:56:23.580Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721752)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Kalendername
-- Column: I_ModCntr_Log.CalendarName
-- 2023-11-07T08:56:38.815Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587630,721753,0,547233,0,TO_TIMESTAMP('2023-11-07 10:56:38.68','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Kalendername',0,90,0,1,1,TO_TIMESTAMP('2023-11-07 10:56:38.68','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-07T08:56:38.817Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721753 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-07T08:56:38.818Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582789)
;

-- 2023-11-07T08:56:38.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721753
;

-- 2023-11-07T08:56:38.821Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721753)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Kalender
-- Column: I_ModCntr_Log.C_Calendar_ID
-- 2023-11-07T14:18:50.858Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721752,0,547233,551191,621212,'F',TO_TIMESTAMP('2023-11-07 16:18:50.579','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnung des Buchführungs-Kalenders','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','N','Y','N','N','N',0,'Kalender',70,0,0,TO_TIMESTAMP('2023-11-07 16:18:50.579','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Kalendername
-- Column: I_ModCntr_Log.CalendarName
-- 2023-11-07T14:19:33.903Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721753,0,547233,551191,621213,'F',TO_TIMESTAMP('2023-11-07 16:19:33.78','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Kalendername',80,0,0,TO_TIMESTAMP('2023-11-07 16:19:33.78','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Kalender
-- Column: I_ModCntr_Log.C_Calendar_ID
-- 2023-11-07T14:19:43.001Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=621212
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Kalendername
-- Column: I_ModCntr_Log.CalendarName
-- 2023-11-07T14:19:50.863Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2023-11-07 16:19:50.863','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621213
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10
-- UI Element Group: qty
-- 2023-11-07T14:20:51.200Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=547100, SeqNo=30,Updated=TO_TIMESTAMP('2023-11-07 16:20:51.2','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551189
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: resolved
-- 2023-11-07T14:21:32.878Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547101,551283,TO_TIMESTAMP('2023-11-07 16:21:32.757','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','resolved',30,TO_TIMESTAMP('2023-11-07 16:21:32.757','YYYY-MM-DD HH24:MI:SS.US'),100)
;

