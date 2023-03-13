-- Column: R_Status.Color
-- 2023-03-08T10:03:57.204Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586286,543290,0,10,776,'Color',TO_TIMESTAMP('2023-03-08 12:03:57','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ui.web',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Color',0,0,TO_TIMESTAMP('2023-03-08 12:03:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-08T10:03:57.214Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586286 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-08T10:03:57.243Z
/* DDL */  select update_Column_Translation_From_AD_Element(543290) 
;

-- 2023-03-08T10:03:58.279Z
/* DDL */ SELECT public.db_alter_table('R_Status','ALTER TABLE public.R_Status ADD COLUMN Color VARCHAR(7)')
;

-- Column: R_Status.Color
-- 2023-03-08T10:04:24.408Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2023-03-08 12:04:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586286
;

-- 2023-03-08T10:04:25.184Z
INSERT INTO t_alter_column values('r_status','Color','VARCHAR(7)',null,null)
;

-- Field: Vorgang - Status -> Vorgang - Status -> Color
-- Column: R_Status.Color
-- 2023-03-08T11:13:16.815Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586286,712794,0,702,0,TO_TIMESTAMP('2023-03-08 13:13:16','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_StatusCategory_ID@=540022','D',0,'Y','Y','Y','N','N','N','N','N','Color',0,180,0,1,1,TO_TIMESTAMP('2023-03-08 13:13:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T11:13:16.827Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T11:13:16.833Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543290) 
;

-- 2023-03-08T11:13:16.853Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712794
;

-- 2023-03-08T11:13:16.859Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712794)
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Color
-- Column: R_Status.Color
-- 2023-03-08T11:13:47.460Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712794,0,702,615975,540599,'F',TO_TIMESTAMP('2023-03-08 13:13:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Color',10,0,0,TO_TIMESTAMP('2023-03-08 13:13:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Color
-- Column: R_Status.Color
-- 2023-03-08T11:14:49.403Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-03-08 13:14:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615975
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Color
-- Column: R_Status.Color
-- 2023-03-08T11:15:23.933Z
UPDATE AD_UI_Element SET IsAdvancedField='N', IsDisplayed='N',Updated=TO_TIMESTAMP('2023-03-08 13:15:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615975
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Color
-- Column: R_Status.Color
-- 2023-03-08T11:16:20.463Z
UPDATE AD_UI_Element SET SeqNo=0,Updated=TO_TIMESTAMP('2023-03-08 13:16:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615975
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Sektion
-- Column: R_Status.AD_Org_ID
-- 2023-03-08T11:16:45.016Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545397
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Status-Kategorie
-- Column: R_Status.R_StatusCategory_ID
-- 2023-03-08T11:16:45.022Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545398
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Reihenfolge
-- Column: R_Status.SeqNo
-- 2023-03-08T11:16:45.027Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545399
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Suchschlüssel
-- Column: R_Status.Value
-- 2023-03-08T11:16:45.030Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545400
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Name
-- Column: R_Status.Name
-- 2023-03-08T11:16:45.035Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545401
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Beschreibung
-- Column: R_Status.Description
-- 2023-03-08T11:16:45.038Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545402
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Kommentar/Hilfe
-- Column: R_Status.Help
-- 2023-03-08T11:16:45.041Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545403
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Aktiv
-- Column: R_Status.IsActive
-- 2023-03-08T11:16:45.044Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545404
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Standard
-- Column: R_Status.IsDefault
-- 2023-03-08T11:16:45.047Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545405
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Web Can Update
-- Column: R_Status.IsWebCanUpdate
-- 2023-03-08T11:16:45.050Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545406
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Update Status
-- Column: R_Status.Update_Status_ID
-- 2023-03-08T11:16:45.052Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545407
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Timeout in Days
-- Column: R_Status.TimeoutDays
-- 2023-03-08T11:16:45.054Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545408
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Next Status
-- Column: R_Status.Next_Status_ID
-- 2023-03-08T11:16:45.056Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545409
;

-- UI Element: Vorgang - Status -> Vorgang - Status."Offen"-Status
-- Column: R_Status.IsOpen
-- 2023-03-08T11:16:45.057Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545410
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Geschlossen
-- Column: R_Status.IsClosed
-- 2023-03-08T11:16:45.058Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545411
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Final Close
-- Column: R_Status.IsFinalClose
-- 2023-03-08T11:16:45.060Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545412
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Color
-- Column: R_Status.Color
-- 2023-03-08T11:16:45.062Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-03-08 13:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615975
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Final Close
-- Column: R_Status.IsFinalClose
-- 2023-03-08T11:23:31.788Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545412
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Geschlossen
-- Column: R_Status.IsClosed
-- 2023-03-08T11:23:32.173Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545411
;

-- UI Element: Vorgang - Status -> Vorgang - Status."Offen"-Status
-- Column: R_Status.IsOpen
-- 2023-03-08T11:23:32.759Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545410
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Next Status
-- Column: R_Status.Next_Status_ID
-- 2023-03-08T11:23:33.365Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545409
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Timeout in Days
-- Column: R_Status.TimeoutDays
-- 2023-03-08T11:23:33.704Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545408
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Update Status
-- Column: R_Status.Update_Status_ID
-- 2023-03-08T11:23:34.097Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545407
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Web Can Update
-- Column: R_Status.IsWebCanUpdate
-- 2023-03-08T11:23:34.484Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545406
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Standard
-- Column: R_Status.IsDefault
-- 2023-03-08T11:23:35.114Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545405
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Aktiv
-- Column: R_Status.IsActive
-- 2023-03-08T11:23:35.505Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545404
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Kommentar/Hilfe
-- Column: R_Status.Help
-- 2023-03-08T11:23:36.111Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545403
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Beschreibung
-- Column: R_Status.Description
-- 2023-03-08T11:23:36.580Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545402
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Name
-- Column: R_Status.Name
-- 2023-03-08T11:23:37.185Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545401
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Suchschlüssel
-- Column: R_Status.Value
-- 2023-03-08T11:23:38.108Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545400
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Reihenfolge
-- Column: R_Status.SeqNo
-- 2023-03-08T11:23:38.523Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545399
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Status-Kategorie
-- Column: R_Status.R_StatusCategory_ID
-- 2023-03-08T11:23:38.931Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545398
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Sektion
-- Column: R_Status.AD_Org_ID
-- 2023-03-08T11:23:39.428Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545397
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Color
-- Column: R_Status.Color
-- 2023-03-08T11:23:43.860Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-03-08 13:23:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615975
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Color
-- Column: R_Status.Color
-- 2023-03-08T11:24:17.288Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-03-08 13:24:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615975
;

-- 2023-03-08T13:58:18.948Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582124,0,'CalendarColor',TO_TIMESTAMP('2023-03-08 15:58:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CalendarColor','Calendar Color',TO_TIMESTAMP('2023-03-08 15:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T13:58:18.962Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582124 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: R_Status.CalendarColor
-- 2023-03-08T13:58:29.763Z
UPDATE AD_Column SET AD_Element_ID=582124, ColumnName='CalendarColor', Description=NULL, Help=NULL, Name='CalendarColor',Updated=TO_TIMESTAMP('2023-03-08 15:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586286
;

-- 2023-03-08T13:58:29.787Z
UPDATE AD_Field SET Name='CalendarColor', Description=NULL, Help=NULL WHERE AD_Column_ID=586286
;

-- 2023-03-08T13:58:29.789Z
/* DDL */  select update_Column_Translation_From_AD_Element(582124) 
;

-- 2023-03-08T13:58:30.387Z
/* DDL */ SELECT public.db_alter_table('R_Status','ALTER TABLE public.R_Status ADD COLUMN CalendarColor VARCHAR(7)')
;

SELECT public.db_alter_table('R_Status', 'alter table R_Status drop column Color');-- UI Element: Vorgang - Status -> Vorgang - Status.Color
-- Column: R_Status.CalendarColor
-- 2023-03-08T14:02:24.427Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615975
;

-- 2023-03-08T14:02:24.429Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712794
;

-- Field: Vorgang - Status -> Vorgang - Status -> CalendarColor
-- Column: R_Status.CalendarColor
-- 2023-03-08T14:02:24.440Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712794
;

-- 2023-03-08T14:02:24.441Z
DELETE FROM AD_Field WHERE AD_Field_ID=712794
;

-- Field: Vorgang - Status -> Vorgang - Status -> CalendarColor
-- Column: R_Status.CalendarColor
-- 2023-03-08T14:02:49.412Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586286,712808,0,702,0,TO_TIMESTAMP('2023-03-08 16:02:49','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','CalendarColor',0,180,0,1,1,TO_TIMESTAMP('2023-03-08 16:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-08T14:02:49.420Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-08T14:02:49.425Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582124) 
;

-- 2023-03-08T14:02:49.429Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712808
;

-- 2023-03-08T14:02:49.436Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712808)
;

-- UI Element: Vorgang - Status -> Vorgang - Status.CalendarColor
-- Column: R_Status.CalendarColor
-- 2023-03-08T14:03:03.106Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712808,0,702,615986,540599,'F',TO_TIMESTAMP('2023-03-08 16:03:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'CalendarColor',10,0,0,TO_TIMESTAMP('2023-03-08 16:03:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Vorgang - Status -> Vorgang - Status.CalendarColor
-- Column: R_Status.CalendarColor
-- 2023-03-08T14:04:07.591Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-08 16:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615986
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Web Can Update
-- Column: R_Status.IsWebCanUpdate
-- 2023-03-08T14:04:07.596Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-08 16:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545406
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Update Status
-- Column: R_Status.Update_Status_ID
-- 2023-03-08T14:04:07.629Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-08 16:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545407
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Timeout in Days
-- Column: R_Status.TimeoutDays
-- 2023-03-08T14:04:07.634Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-03-08 16:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545408
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Next Status
-- Column: R_Status.Next_Status_ID
-- 2023-03-08T14:04:07.639Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-03-08 16:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545409
;

-- UI Element: Vorgang - Status -> Vorgang - Status."Offen"-Status
-- Column: R_Status.IsOpen
-- 2023-03-08T14:04:07.643Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-03-08 16:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545410
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Geschlossen
-- Column: R_Status.IsClosed
-- 2023-03-08T14:04:07.647Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-03-08 16:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545411
;

-- UI Element: Vorgang - Status -> Vorgang - Status.Final Close
-- Column: R_Status.IsFinalClose
-- 2023-03-08T14:04:07.650Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-03-08 16:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545412
;

-- 2023-03-09T05:22:51.177Z
UPDATE AD_Element_Trl SET Description='Color used in the resource calendar in order to better distinguish resource planning and reservation. A hexadecimal code shall be used to represent the color( e.g. #FFFFFF)',Updated=TO_TIMESTAMP('2023-03-09 07:22:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='en_US'
;

-- 2023-03-09T05:22:51.225Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'en_US') 
;

-- 2023-03-09T05:22:53.174Z
UPDATE AD_Element_Trl SET Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).',Updated=TO_TIMESTAMP('2023-03-09 07:22:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='de_CH'
;

-- 2023-03-09T05:22:53.175Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'de_CH') 
;

-- 2023-03-09T05:22:56.259Z
UPDATE AD_Element_Trl SET Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).',Updated=TO_TIMESTAMP('2023-03-09 07:22:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='de_DE'
;

-- 2023-03-09T05:22:56.260Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'de_DE') 
;

-- 2023-03-09T05:22:56.266Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582124,'de_DE') 
;

-- 2023-03-09T05:22:56.268Z
UPDATE AD_Column SET ColumnName='CalendarColor', Name='CalendarColor', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL WHERE AD_Element_ID=582124
;

-- 2023-03-09T05:22:56.274Z
UPDATE AD_Process_Para SET ColumnName='CalendarColor', Name='CalendarColor', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL, AD_Element_ID=582124 WHERE UPPER(ColumnName)='CALENDARCOLOR' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-09T05:22:56.289Z
UPDATE AD_Process_Para SET ColumnName='CalendarColor', Name='CalendarColor', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL WHERE AD_Element_ID=582124 AND IsCentrallyMaintained='Y'
;

-- 2023-03-09T05:22:56.297Z
UPDATE AD_Field SET Name='CalendarColor', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582124) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582124)
;

-- 2023-03-09T05:22:56.529Z
UPDATE AD_Tab SET Name='CalendarColor', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582124
;

-- 2023-03-09T05:22:56.531Z
UPDATE AD_WINDOW SET Name='CalendarColor', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL WHERE AD_Element_ID = 582124
;

-- 2023-03-09T05:22:56.532Z
UPDATE AD_Menu SET   Name = 'CalendarColor', Description = 'Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582124
;

-- 2023-03-09T05:24:33.225Z
UPDATE AD_Element_Trl SET Name='Calendar Color',Updated=TO_TIMESTAMP('2023-03-09 07:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='de_CH'
;

-- 2023-03-09T05:24:33.248Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'de_CH') 
;

-- 2023-03-09T05:24:35.491Z
UPDATE AD_Element_Trl SET Name='Calendar Color',Updated=TO_TIMESTAMP('2023-03-09 07:24:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='de_DE'
;

-- 2023-03-09T05:24:35.492Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'de_DE') 
;

-- 2023-03-09T05:24:35.497Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582124,'de_DE') 
;

-- 2023-03-09T05:24:35.520Z
UPDATE AD_Column SET ColumnName='CalendarColor', Name='Calendar Color', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL WHERE AD_Element_ID=582124
;

-- 2023-03-09T05:24:35.522Z
UPDATE AD_Process_Para SET ColumnName='CalendarColor', Name='Calendar Color', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL, AD_Element_ID=582124 WHERE UPPER(ColumnName)='CALENDARCOLOR' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-09T05:24:35.523Z
UPDATE AD_Process_Para SET ColumnName='CalendarColor', Name='Calendar Color', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL WHERE AD_Element_ID=582124 AND IsCentrallyMaintained='Y'
;

-- 2023-03-09T05:24:35.524Z
UPDATE AD_Field SET Name='Calendar Color', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582124) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582124)
;

-- 2023-03-09T05:24:35.536Z
UPDATE AD_PrintFormatItem pi SET PrintName='Calendar Color', Name='Calendar Color' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582124)
;

-- 2023-03-09T05:24:35.539Z
UPDATE AD_Tab SET Name='Calendar Color', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582124
;

-- 2023-03-09T05:24:35.540Z
UPDATE AD_WINDOW SET Name='Calendar Color', Description='Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', Help=NULL WHERE AD_Element_ID = 582124
;

-- 2023-03-09T05:24:35.540Z
UPDATE AD_Menu SET   Name = 'Calendar Color', Description = 'Farbe, die im Ressourcenkalender verwendet wird, um Ressourcenplanung und -reservierung besser unterscheiden zu können. Zur Darstellung der Farbe ist ein hexadezimaler Code zu verwenden (z. B. #FFFFFF).', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582124
;

-- 2023-03-09T05:24:42.149Z
UPDATE AD_Element_Trl SET Name=' Calendar Color',Updated=TO_TIMESTAMP('2023-03-09 07:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='en_US'
;

-- 2023-03-09T05:24:42.150Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'en_US') 
;

-- 2023-03-09T05:25:01.968Z
UPDATE AD_Element_Trl SET Name='Calendar Color',Updated=TO_TIMESTAMP('2023-03-09 07:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='en_US'
;

-- 2023-03-09T05:25:01.968Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'en_US') 
;

-- 2023-03-09T05:25:04.034Z
UPDATE AD_Element_Trl SET Name='Calendar Color',Updated=TO_TIMESTAMP('2023-03-09 07:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='nl_NL'
;

-- 2023-03-09T05:25:04.034Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'nl_NL') 
;

-- 2023-03-09T05:25:57.310Z
UPDATE AD_Element_Trl SET Description='Color used in the resource calendar in order to better distinguish resource planning and reservation. A hexadecimal code shall be used to represent the color (e.g. #FFFFFF)',Updated=TO_TIMESTAMP('2023-03-09 07:25:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='en_US'
;

-- 2023-03-09T05:25:57.311Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'en_US') 
;

-- 2023-03-09T11:58:23.881Z
UPDATE AD_Element_Trl SET Description='Im Ressourcenkalender verwendete Farbe zur besseren Unterscheidung von Ressourcenplanung und -reservierung. Zur Darstellung der Farbe ist ein Hexadezimal-Code zu verwenden (z.B. #FFFFFF).',Updated=TO_TIMESTAMP('2023-03-09 13:58:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='de_CH'
;

-- 2023-03-09T11:58:23.932Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'de_CH')
;

-- 2023-03-09T11:58:27.803Z
UPDATE AD_Element_Trl SET Description='Im Ressourcenkalender verwendete Farbe zur besseren Unterscheidung von Ressourcenplanung und -reservierung. Zur Darstellung der Farbe ist ein Hexadezimal-Code zu verwenden (z.B. #FFFFFF).',Updated=TO_TIMESTAMP('2023-03-09 13:58:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582124 AND AD_Language='de_DE'
;

-- 2023-03-09T11:58:27.804Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582124,'de_DE')
;

-- 2023-03-09T11:58:27.827Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582124,'de_DE')
;

-- 2023-03-09T11:58:27.828Z
UPDATE AD_Column SET ColumnName='CalendarColor', Name='Calendar Color', Description='Im Ressourcenkalender verwendete Farbe zur besseren Unterscheidung von Ressourcenplanung und -reservierung. Zur Darstellung der Farbe ist ein Hexadezimal-Code zu verwenden (z.B. #FFFFFF).', Help=NULL WHERE AD_Element_ID=582124
;

-- 2023-03-09T11:58:27.829Z
UPDATE AD_Process_Para SET ColumnName='CalendarColor', Name='Calendar Color', Description='Im Ressourcenkalender verwendete Farbe zur besseren Unterscheidung von Ressourcenplanung und -reservierung. Zur Darstellung der Farbe ist ein Hexadezimal-Code zu verwenden (z.B. #FFFFFF).', Help=NULL, AD_Element_ID=582124 WHERE UPPER(ColumnName)='CALENDARCOLOR' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-09T11:58:27.830Z
UPDATE AD_Process_Para SET ColumnName='CalendarColor', Name='Calendar Color', Description='Im Ressourcenkalender verwendete Farbe zur besseren Unterscheidung von Ressourcenplanung und -reservierung. Zur Darstellung der Farbe ist ein Hexadezimal-Code zu verwenden (z.B. #FFFFFF).', Help=NULL WHERE AD_Element_ID=582124 AND IsCentrallyMaintained='Y'
;

-- 2023-03-09T11:58:27.831Z
UPDATE AD_Field SET Name='Calendar Color', Description='Im Ressourcenkalender verwendete Farbe zur besseren Unterscheidung von Ressourcenplanung und -reservierung. Zur Darstellung der Farbe ist ein Hexadezimal-Code zu verwenden (z.B. #FFFFFF).', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582124) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582124)
;

-- 2023-03-09T11:58:27.886Z
UPDATE AD_Tab SET Name='Calendar Color', Description='Im Ressourcenkalender verwendete Farbe zur besseren Unterscheidung von Ressourcenplanung und -reservierung. Zur Darstellung der Farbe ist ein Hexadezimal-Code zu verwenden (z.B. #FFFFFF).', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582124
;

-- 2023-03-09T11:58:27.887Z
UPDATE AD_WINDOW SET Name='Calendar Color', Description='Im Ressourcenkalender verwendete Farbe zur besseren Unterscheidung von Ressourcenplanung und -reservierung. Zur Darstellung der Farbe ist ein Hexadezimal-Code zu verwenden (z.B. #FFFFFF).', Help=NULL WHERE AD_Element_ID = 582124
;

-- 2023-03-09T11:58:27.888Z
UPDATE AD_Menu SET   Name = 'Calendar Color', Description = 'Im Ressourcenkalender verwendete Farbe zur besseren Unterscheidung von Ressourcenplanung und -reservierung. Zur Darstellung der Farbe ist ein Hexadezimal-Code zu verwenden (z.B. #FFFFFF).', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582124
;

-- 2023-03-09T14:38:19.962Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541599,'S',TO_TIMESTAMP('2023-03-09 16:38:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.project.workorder.calendar.WOProjectExternalIdPrefix',TO_TIMESTAMP('2023-03-09 16:38:19','YYYY-MM-DD HH24:MI:SS'),100,'VA ')
;

-- 2023-03-10T08:31:40.779Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545255,0,TO_TIMESTAMP('2023-03-10 10:31:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invalid {0}! Hexadecimal color code pattern required!','E',TO_TIMESTAMP('2023-03-10 10:31:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.project.status.interceptor.InvalidHexadecimalColorCodePattern')
;

-- 2023-03-10T08:31:40.788Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545255 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-03-10T08:31:46.137Z
UPDATE AD_Message_Trl SET MsgText='Ungültig {0}! Hexadezimales Farbcode-Muster erforderlich!',Updated=TO_TIMESTAMP('2023-03-10 10:31:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545255
;

-- 2023-03-10T08:31:49.134Z
UPDATE AD_Message_Trl SET MsgText='Ungültig {0}! Hexadezimales Farbcode-Muster erforderlich!',Updated=TO_TIMESTAMP('2023-03-10 10:31:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545255
;

-- 2023-03-10T10:04:30.196Z
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2023-03-10 12:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541599
;

