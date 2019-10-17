INSERT INTO t_alter_column values('c_project_user','C_Project_User_ID','NUMERIC(10)',null,null)
;

-- 2018-04-12T15:22:36.474
-- project window
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=559671
;

-- 2018-04-12T15:22:36.475
-- project window
DELETE FROM AD_Column WHERE AD_Column_ID=559671
;
-- 2018-04-12T15:24:02.079
-- project window
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,559677,543957,0,13,540961,'N','C_Project_User_ID',TO_TIMESTAMP('2018-04-12 15:24:02','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Projektkontakt',TO_TIMESTAMP('2018-04-12 15:24:02','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2018-04-12T15:24:02.082
-- project window
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559677 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-04-12T15:24:02.090
-- project window
CREATE SEQUENCE C_PROJECT_USER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;
alter table  c_project_user drop column c_project_user_id;

-- 2018-04-12T15:24:02.095
-- project window
ALTER TABLE C_Project_User ADD COLUMN C_Project_User_ID numeric(10,0) NOT NULL DEFAULT nextval('c_project_user_seq')
;

-- 2018-04-12T15:24:02.103
-- project window
ALTER TABLE C_Project_User DROP CONSTRAINT IF EXISTS c_project_user_pkey
;

-- 2018-04-12T15:24:02.104
-- project window
ALTER TABLE C_Project_User DROP CONSTRAINT IF EXISTS c_project_user_key
;

-- 2018-04-12T15:24:02.104
-- project window
ALTER TABLE C_Project_User ADD CONSTRAINT c_project_user_pkey PRIMARY KEY (C_Project_User_ID)
;

-- 2018-04-12T15:24:11.832
-- project window
INSERT INTO t_alter_column values('c_project_user','Created','TIMESTAMP WITH TIME ZONE',null,null)
;


-- 2019-07-23T19:29:14.294
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576931,0,TO_TIMESTAMP('2019-07-23 19:29:14','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Projekt (webUI)','Projekt',TO_TIMESTAMP('2019-07-23 19:29:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:29:14.297
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576931 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-23T19:29:41.132
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,576931,0,540668,TO_TIMESTAMP('2019-07-23 19:29:41','YYYY-MM-DD HH24:MI:SS'),100,'U','Projekt (webUI)','Y','N','N','N','N','Y','Projekt (webUI)','N',TO_TIMESTAMP('2019-07-23 19:29:41','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-07-23T19:29:41.134
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540668 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-07-23T19:29:41.138
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(576931) 
;

-- 2019-07-23T19:29:41.141
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540668
;

-- 2019-07-23T19:29:41.142
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540668)
;

-- 2019-07-23T19:30:09.362
-- URL zum Konzept
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Element_ID=572687, AD_Image_ID=NULL, AD_Org_ID=0, Description=NULL, EntityType='U', Help=NULL, IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='N', IsOneInstanceOnly='N', IsSOTrx='Y', Processing='N', WindowType='M', WinHeight=0, WinWidth=0,Updated=TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540668
;

-- 2019-07-23T19:30:09.368
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(572687) 
;

-- 2019-07-23T19:30:09.373
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540668
;

-- 2019-07-23T19:30:09.374
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540668)
;

-- 2019-07-23T19:30:09.380
-- URL zum Konzept
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 540668
;

-- 2019-07-23T19:30:09.381
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 540668, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 540428
;

-- 2019-07-23T19:30:09.447
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572687,0,217,541830,203,540668,'Y',TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100,'Define Project','U','N','The Project Tab is used to define the Value, Name and Description for each project.  It also is defines the tracks the amounts assigned to, committed to and used for this project. Note that when the project Type is changed, the Phases and Tasks are re-created.','Y','N','Y','Y','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Projekt','N',10,0,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:09.448
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541830 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-07-23T19:30:09.451
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572687) 
;

-- 2019-07-23T19:30:09.455
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541830)
;

-- 2019-07-23T19:30:09.457
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541830
;

-- 2019-07-23T19:30:09.457
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541830, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 541063
;

-- 2019-07-23T19:30:09.553
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5747,582266,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100,'Generate To',23,'@IsSummary@=N','U',0,'Y','N','N','N','N','N','N','Y','Generate To',10,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:09.555
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582266 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:09.560
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1491) 
;

-- 2019-07-23T19:30:09.575
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582266
;

-- 2019-07-23T19:30:09.576
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582266)
;

-- 2019-07-23T19:30:09.580
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582266
;

-- 2019-07-23T19:30:09.580
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582266, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563199
;

-- 2019-07-23T19:30:09.656
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8978,582267,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100,'Betrag / Menge der Zusage ist die Obergrenze für die Abrechnung',1,'@IsSummary@=N & @IsCommitment@=Y','U','Zusage-Betrag und -Menge sind maximal abrechenbarer Betrag und Menge. Ignoriert, wenn Betrag oder Menge gleich 0.',0,'Y','N','N','N','N','N','N','Y','Zusage ist Obergrenze',20,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:09.658
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582267 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:09.662
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2077) 
;

-- 2019-07-23T19:30:09.672
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582267
;

-- 2019-07-23T19:30:09.673
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582267)
;

-- 2019-07-23T19:30:09.676
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582267
;

-- 2019-07-23T19:30:09.677
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582267, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563200
;

-- 2019-07-23T19:30:09.749
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5749,582268,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'U','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','Verarbeitet',30,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:09.751
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582268 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:09.756
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2019-07-23T19:30:09.788
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582268
;

-- 2019-07-23T19:30:09.789
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582268)
;

-- 2019-07-23T19:30:09.792
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582268
;

-- 2019-07-23T19:30:09.793
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582268, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563201
;

-- 2019-07-23T19:30:09.869
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9856,582269,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100,'Project Category',14,'U','The Project Category determines the behavior of the project:
General - no special accounting, e.g. for Presales or general tracking
Service - no special accounting, e.g. for Service/Charge projects
Work Order - creates Project/Job WIP transactions - ability to issue material
Asset - create Project Asset transactions - ability to issue material
',0,'Y','N','N','N','N','N','N','N','Project Category',40,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:09.871
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582269 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:09.876
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2179) 
;

-- 2019-07-23T19:30:09.885
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582269
;

-- 2019-07-23T19:30:09.885
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582269)
;

-- 2019-07-23T19:30:09.889
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582269
;

-- 2019-07-23T19:30:09.889
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582269, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563202
;

-- 2019-07-23T19:30:09.968
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1349,582270,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',14,'U','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Project',50,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:09.970
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582270 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:09.975
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2019-07-23T19:30:10.077
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582270
;

-- 2019-07-23T19:30:10.078
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582270)
;

-- 2019-07-23T19:30:10.082
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582270
;

-- 2019-07-23T19:30:10.083
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582270, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563203
;

-- 2019-07-23T19:30:10.165
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3904,582271,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:10','YYYY-MM-DD HH24:MI:SS'),100,'Is this document a (legal) commitment?',1,'@IsSummary@=N','U','Commitment indicates if the document is legally binding.',0,'Y','N','N','N','N','N','N','N','Commitment',60,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:10.167
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582271 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:10.170
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1101) 
;

-- 2019-07-23T19:30:10.176
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582271
;

-- 2019-07-23T19:30:10.176
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582271)
;

-- 2019-07-23T19:30:10.181
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582271
;

-- 2019-07-23T19:30:10.181
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582271, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563204
;

-- 2019-07-23T19:30:10.248
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1351,582272,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:10','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2019-07-23 19:30:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:10.249
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582272 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:10.250
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-07-23T19:30:10.496
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582272
;

-- 2019-07-23T19:30:10.496
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582272)
;

-- 2019-07-23T19:30:10.498
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582272
;

-- 2019-07-23T19:30:10.498
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582272, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563205
;

-- 2019-07-23T19:30:10.574
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1350,582273,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:10','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2019-07-23 19:30:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:10.576
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582273 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:10.580
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-07-23T19:30:10.784
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582273
;

-- 2019-07-23T19:30:10.784
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582273)
;

-- 2019-07-23T19:30:10.786
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582273
;

-- 2019-07-23T19:30:10.787
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582273, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563206
;

-- 2019-07-23T19:30:10.871
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2010,582274,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:10','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',20,'U','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','N','Projektnummer',30,30,-1,1,1,TO_TIMESTAMP('2019-07-23 19:30:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:10.873
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582274 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:10.876
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2019-07-23T19:30:10.910
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582274
;

-- 2019-07-23T19:30:10.911
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582274)
;

-- 2019-07-23T19:30:10.914
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582274
;

-- 2019-07-23T19:30:10.914
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582274, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563207
;

-- 2019-07-23T19:30:10.994
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5752,582275,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:10','YYYY-MM-DD HH24:MI:SS'),100,'',14,'@IsSummary@=N','U','',0,'Y','Y','Y','Y','N','N','N','Y','Aussendienst',40,40,1,1,TO_TIMESTAMP('2019-07-23 19:30:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:10.996
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582275 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:11
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063) 
;

-- 2019-07-23T19:30:11.034
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582275
;

-- 2019-07-23T19:30:11.035
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582275)
;

-- 2019-07-23T19:30:11.038
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582275
;

-- 2019-07-23T19:30:11.038
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582275, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563208
;

-- 2019-07-23T19:30:11.102
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1356,582276,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',60,'U','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',0,'Y','Y','Y','N','N','N','N','N','Name',50,50,999,1,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:11.104
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582276 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:11.107
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-07-23T19:30:11.234
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582276
;

-- 2019-07-23T19:30:11.235
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582276)
;

-- 2019-07-23T19:30:11.237
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582276
;

-- 2019-07-23T19:30:11.237
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582276, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563209
;

-- 2019-07-23T19:30:11.317
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1358,582277,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100,60,'U',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',60,60,999,1,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:11.318
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582277 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:11.320
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-07-23T19:30:11.379
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582277
;

-- 2019-07-23T19:30:11.379
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582277)
;

-- 2019-07-23T19:30:11.381
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582277
;

-- 2019-07-23T19:30:11.381
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582277, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563210
;

-- 2019-07-23T19:30:11.455
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1352,582278,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',70,70,1,1,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:11.457
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582278 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:11.460
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-07-23T19:30:11.692
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582278
;

-- 2019-07-23T19:30:11.693
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582278)
;

-- 2019-07-23T19:30:11.694
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582278
;

-- 2019-07-23T19:30:11.695
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582278, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563211
;

-- 2019-07-23T19:30:11.776
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1360,582279,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag',1,'U','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.',0,'Y','Y','Y','N','N','N','N','Y','Zusammenfassungseintrag',80,80,1,1,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:11.777
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582279 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:11.780
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(416) 
;

-- 2019-07-23T19:30:11.798
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582279
;

-- 2019-07-23T19:30:11.799
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582279)
;

-- 2019-07-23T19:30:11.801
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582279
;

-- 2019-07-23T19:30:11.802
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582279, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563212
;

-- 2019-07-23T19:30:11.872
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5750,582280,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information für ein Dokument',60,'U','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',0,'Y','Y','Y','N','N','N','N','N','Notiz',90,90,999,1,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:11.873
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582280 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:11.877
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2019-07-23T19:30:11.892
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582280
;

-- 2019-07-23T19:30:11.892
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582280)
;

-- 2019-07-23T19:30:11.895
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582280
;

-- 2019-07-23T19:30:11.896
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582280, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563213
;

-- 2019-07-23T19:30:11.972
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15469,582281,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Project Line Level',1,'@IsSummary@=N','U','Level on which Project Lines are maintained',0,'Y','Y','Y','N','N','N','N','N','Line Level',100,100,1,1,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:11.973
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582281 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:11.977
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3035) 
;

-- 2019-07-23T19:30:11.980
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582281
;

-- 2019-07-23T19:30:11.981
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582281)
;

-- 2019-07-23T19:30:11.984
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582281
;

-- 2019-07-23T19:30:11.985
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582281, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563214
;

-- 2019-07-23T19:30:12.072
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8757,582282,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100,'Type of the project',23,'@IsSummary@=N','U','Type of the project with optional phases of the project with standard performance information',0,'Y','Y','Y','N','N','N','N','N','Projekt - Projektart',110,110,1,1,TO_TIMESTAMP('2019-07-23 19:30:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.073
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582282 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.077
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2033) 
;

-- 2019-07-23T19:30:12.080
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582282
;

-- 2019-07-23T19:30:12.081
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582282)
;

-- 2019-07-23T19:30:12.084
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582282
;

-- 2019-07-23T19:30:12.085
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582282, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563215
;

-- 2019-07-23T19:30:12.153
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8755,582283,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Status oder Phase dieser Projektart',14,'@IsSummary@=N','U','Phase of the project with standard performance information with standard work',0,'Y','Y','Y','N','N','N','N','Y','Standard-Phase',120,120,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.154
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582283 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.158
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2032) 
;

-- 2019-07-23T19:30:12.161
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582283
;

-- 2019-07-23T19:30:12.161
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582283)
;

-- 2019-07-23T19:30:12.164
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582283
;

-- 2019-07-23T19:30:12.165
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582283, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563216
;

-- 2019-07-23T19:30:12.246
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5745,582284,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftragseingangs',14,'@IsSummary@=N','U','The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.',0,'Y','Y','Y','N','N','N','N','N','Datum AE',130,130,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.247
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582284 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.250
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1556) 
;

-- 2019-07-23T19:30:12.253
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582284
;

-- 2019-07-23T19:30:12.254
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582284)
;

-- 2019-07-23T19:30:12.257
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582284
;

-- 2019-07-23T19:30:12.258
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582284, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563217
;

-- 2019-07-23T19:30:12.327
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5746,582285,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Finish or (planned) completion date',14,'@IsSummary@=N','U','Dieses Datum gibt das erwartete oder tatsächliche Projektende an',0,'Y','Y','Y','N','N','N','N','Y','Projektabschluss',140,140,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.329
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582285 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.331
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1557) 
;

-- 2019-07-23T19:30:12.334
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582285
;

-- 2019-07-23T19:30:12.334
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582285)
;

-- 2019-07-23T19:30:12.336
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582285
;

-- 2019-07-23T19:30:12.337
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582285, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563218
;

-- 2019-07-23T19:30:12.406
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3902,104,582286,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'@IsSummary@=N','U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',150,150,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.407
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582286 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.408
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2019-07-23T19:30:12.412
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582286
;

-- 2019-07-23T19:30:12.412
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582286)
;

-- 2019-07-23T19:30:12.414
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582286
;

-- 2019-07-23T19:30:12.414
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582286, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563219
;

-- 2019-07-23T19:30:12.482
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14095,582287,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner (Agent or Sales Rep)',10,'@IsSummary@=N','U',0,'Y','Y','Y','N','N','N','N','Y','BPartner (Agent)',160,160,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.483
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582287 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.485
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2810) 
;

-- 2019-07-23T19:30:12.487
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582287
;

-- 2019-07-23T19:30:12.488
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582287)
;

-- 2019-07-23T19:30:12.489
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582287
;

-- 2019-07-23T19:30:12.490
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582287, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563220
;

-- 2019-07-23T19:30:12.560
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5798,582288,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',14,'@IsSummary@=N','U','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','N','N','N','N','N','Standort',170,170,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.561
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582288 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.562
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2019-07-23T19:30:12.564
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582288
;

-- 2019-07-23T19:30:12.564
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582288)
;

-- 2019-07-23T19:30:12.565
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582288
;

-- 2019-07-23T19:30:12.566
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582288, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563221
;

-- 2019-07-23T19:30:12.631
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5797,582289,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'@IsSummary@=N','U','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','Y','Ansprechpartner',180,180,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.633
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582289 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.635
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2019-07-23T19:30:12.639
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582289
;

-- 2019-07-23T19:30:12.640
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582289)
;

-- 2019-07-23T19:30:12.642
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582289
;

-- 2019-07-23T19:30:12.642
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582289, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563222
;

-- 2019-07-23T19:30:12.736
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5796,582290,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',14,'@IsSummary@=N','U','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','Y','Y','N','N','N','N','N','Zahlungsbedingung',190,190,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.737
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582290 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.739
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(204) 
;

-- 2019-07-23T19:30:12.749
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582290
;

-- 2019-07-23T19:30:12.750
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582290)
;

-- 2019-07-23T19:30:12.752
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582290
;

-- 2019-07-23T19:30:12.752
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582290, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563223
;

-- 2019-07-23T19:30:12.828
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5794,582291,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',20,'@IsSummary@=N','U','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','Y','Y','N','N','N','N','Y','Referenz',200,200,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.829
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582291 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.832
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2019-07-23T19:30:12.847
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582291
;

-- 2019-07-23T19:30:12.847
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582291)
;

-- 2019-07-23T19:30:12.850
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582291
;

-- 2019-07-23T19:30:12.851
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582291, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563224
;

-- 2019-07-23T19:30:12.943
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9637,582292,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',14,'@IsSummary@=N','U','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','Y','Y','N','N','N','N','N','Lager',210,210,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:12.944
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582292 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:12.947
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2019-07-23T19:30:12.965
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582292
;

-- 2019-07-23T19:30:12.965
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582292)
;

-- 2019-07-23T19:30:12.968
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582292
;

-- 2019-07-23T19:30:12.968
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582292, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563225
;

-- 2019-07-23T19:30:13.048
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5795,582293,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',14,'@IsSummary@=N & @$Element_MC@=Y','U','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','Y','Y','N','N','N','N','Y','Werbemassnahme',220,220,1,1,TO_TIMESTAMP('2019-07-23 19:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.049
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582293 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.051
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550) 
;

-- 2019-07-23T19:30:13.068
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582293
;

-- 2019-07-23T19:30:13.068
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582293)
;

-- 2019-07-23T19:30:13.071
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582293
;

-- 2019-07-23T19:30:13.071
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582293, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563226
;

-- 2019-07-23T19:30:13.143
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5753,582294,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste',14,'@IsSummary@=N','U','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ',0,'Y','Y','Y','N','N','N','N','N','Version Preisliste',230,230,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.145
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582294 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.148
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(450) 
;

-- 2019-07-23T19:30:13.156
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582294
;

-- 2019-07-23T19:30:13.157
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582294)
;

-- 2019-07-23T19:30:13.159
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582294
;

-- 2019-07-23T19:30:13.160
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582294, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563227
;

-- 2019-07-23T19:30:13.231
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3901,582295,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',14,'@IsSummary@=N','U','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','Y','N','N','N','N','Y','Währung',240,240,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.233
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582295 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.236
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2019-07-23T19:30:13.263
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582295
;

-- 2019-07-23T19:30:13.264
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582295)
;

-- 2019-07-23T19:30:13.266
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582295
;

-- 2019-07-23T19:30:13.267
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582295, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563228
;

-- 2019-07-23T19:30:13.336
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5755,103,582296,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'VK Total',26,'@IsSummary@=N','U','The Planned Amount indicates the anticipated amount for this project or project line.',0,'Y','Y','Y','N','N','N','N','N','VK Total',250,250,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.337
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582296 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.340
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1564) 
;

-- 2019-07-23T19:30:13.342
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582296
;

-- 2019-07-23T19:30:13.343
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582296)
;

-- 2019-07-23T19:30:13.345
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582296
;

-- 2019-07-23T19:30:13.345
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582296, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563229
;

-- 2019-07-23T19:30:13.414
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5756,103,582297,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'',26,'@IsSummary@=N','U','',0,'Y','Y','Y','N','N','N','N','Y','Geplante Menge',260,260,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.415
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582297 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.417
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1568) 
;

-- 2019-07-23T19:30:13.418
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582297
;

-- 2019-07-23T19:30:13.419
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582297)
;

-- 2019-07-23T19:30:13.421
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582297
;

-- 2019-07-23T19:30:13.421
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582297, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563230
;

-- 2019-07-23T19:30:13.494
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5757,582298,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'Project''s planned margin amount',26,'@IsSummary@=N','U','The Planned Margin Amount indicates the anticipated margin amount for this project or project line.',0,'Y','Y','Y','N','N','N','N','N','DB1',270,270,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.495
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582298 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.498
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1566) 
;

-- 2019-07-23T19:30:13.501
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582298
;

-- 2019-07-23T19:30:13.502
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582298)
;

-- 2019-07-23T19:30:13.505
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582298
;

-- 2019-07-23T19:30:13.506
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582298, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563231
;

-- 2019-07-23T19:30:13.578
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15449,582299,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Rule for the project',1,'@IsSummary@=N','U','The Invoice Rule for the project determines how orders (and consequently invoices) are created.  The selection on project level can be overwritten on Phase or Task',0,'Y','Y','Y','N','N','N','N','Y','Rechnungsstellung',280,280,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.579
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582299 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.582
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3031) 
;

-- 2019-07-23T19:30:13.584
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582299
;

-- 2019-07-23T19:30:13.585
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582299)
;

-- 2019-07-23T19:30:13.587
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582299
;

-- 2019-07-23T19:30:13.587
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582299, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563232
;

-- 2019-07-23T19:30:13.660
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3907,582300,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'The (legal) commitment amount',26,'@IsSummary@=N','U','The commitment amount is independent from the planned amount. You would use the planned amount for your realistic estimation, which might be higher or lower than the commitment amount.',0,'Y','Y','Y','N','N','N','N','N','Committed Amount',290,290,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.661
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582300 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.664
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1081) 
;

-- 2019-07-23T19:30:13.669
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582300
;

-- 2019-07-23T19:30:13.670
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582300)
;

-- 2019-07-23T19:30:13.672
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582300
;

-- 2019-07-23T19:30:13.672
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582300, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563233
;

-- 2019-07-23T19:30:13.743
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8759,582301,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'The (legal) commitment Quantity',26,'@IsSummary@=N','U','The commitment amount is independent from the planned amount. You would use the planned amount for your realistic estimation, which might be higher or lower than the commitment amount.',0,'Y','Y','Y','N','N','N','N','Y','Committed Quantity',300,300,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.744
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582301 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.748
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2036) 
;

-- 2019-07-23T19:30:13.750
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582301
;

-- 2019-07-23T19:30:13.751
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582301)
;

-- 2019-07-23T19:30:13.754
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582301
;

-- 2019-07-23T19:30:13.755
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582301, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563234
;

-- 2019-07-23T19:30:13.826
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8753,105,582302,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'The amount invoiced',26,'@IsSummary@=N','U','The amount invoiced',0,'Y','Y','Y','N','N','N','Y','N','Invoiced Amount',310,310,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.827
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582302 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.830
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2044) 
;

-- 2019-07-23T19:30:13.833
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582302
;

-- 2019-07-23T19:30:13.833
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582302)
;

-- 2019-07-23T19:30:13.836
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582302
;

-- 2019-07-23T19:30:13.837
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582302, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563235
;

-- 2019-07-23T19:30:13.902
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8756,582303,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'The quantity invoiced',26,'@IsSummary@=N','U',0,'Y','Y','Y','N','N','N','Y','Y','Berechnete Menge',320,320,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.904
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.906
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2045) 
;

-- 2019-07-23T19:30:13.909
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582303
;

-- 2019-07-23T19:30:13.910
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582303)
;

-- 2019-07-23T19:30:13.913
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582303
;

-- 2019-07-23T19:30:13.914
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582303, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563236
;

-- 2019-07-23T19:30:13.980
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8758,582304,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'Total Project Balance',26,'@IsSummary@=N','U','The project balance is the sum of all invoices and payments',0,'Y','Y','Y','N','N','N','Y','N','Project Balance',330,330,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:13.981
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:13.985
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2054) 
;

-- 2019-07-23T19:30:13.986
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582304
;

-- 2019-07-23T19:30:13.987
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582304)
;

-- 2019-07-23T19:30:13.991
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582304
;

-- 2019-07-23T19:30:13.992
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582304, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563237
;

-- 2019-07-23T19:30:14.067
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8754,582305,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100,'Copy From Record',23,'@IsSummary@=N','U','Copy From Record',0,'Y','Y','Y','N','N','N','N','N','Copy From',340,340,1,1,TO_TIMESTAMP('2019-07-23 19:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:14.069
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:14.072
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2037) 
;

-- 2019-07-23T19:30:14.076
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582305
;

-- 2019-07-23T19:30:14.076
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582305)
;

-- 2019-07-23T19:30:14.079
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582305
;

-- 2019-07-23T19:30:14.080
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582305, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563238
;

-- 2019-07-23T19:30:14.149
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9861,582306,0,541830,0,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,23,'@IsSummary@=N','U',0,'Y','Y','Y','N','N','N','N','N','Process Now',350,350,1,1,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:14.150
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582306 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:14.154
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2019-07-23T19:30:14.200
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582306
;

-- 2019-07-23T19:30:14.200
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582306)
;

-- 2019-07-23T19:30:14.203
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582306
;

-- 2019-07-23T19:30:14.204
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582306, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563239
;

-- 2019-07-23T19:30:14.292
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541830,541389,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'1000028')
;

-- 2019-07-23T19:30:14.296
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541389 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-07-23T19:30:14.300
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541389
;

-- 2019-07-23T19:30:14.302
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541389, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540691
;

-- 2019-07-23T19:30:14.389
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541783,541389,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:14.476
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541783,542695,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:14.580
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582274,0,541830,542695,560249,'F',TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Suchschlüssel',10,0,0,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:14.666
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582276,0,541830,542695,560250,'F',TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Name',20,0,0,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:14.743
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582277,0,541830,542695,560251,'F',TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Beschreibung',30,0,0,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:14.814
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582278,0,541830,542695,560252,'F',TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Aktiv',40,0,0,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:14.884
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541784,541389,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:14.965
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541784,542696,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','partner',10,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.046
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582286,0,541830,542696,560253,'F',TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Kunde',10,0,0,TO_TIMESTAMP('2019-07-23 19:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.139
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582273,0,541830,542696,560254,'F',TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Sektion',20,0,0,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.213
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582275,0,541830,542696,560255,'F',TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Aussendienst',30,0,0,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.287
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,5799,572687,0,541831,417,540668,'Y',TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Vorgänge',1349,'N',60,1,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.289
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541831 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-07-23T19:30:15.291
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572687) 
;

-- 2019-07-23T19:30:15.294
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541831)
;

-- 2019-07-23T19:30:15.297
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541831
;

-- 2019-07-23T19:30:15.297
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541831, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 541070
;

-- 2019-07-23T19:30:15.372
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8774,582307,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'This is a Self-Service entry or this entry can be changed via Self-Service',1,'U','Self-Service allows users to enter data or update their data.  The flag indicates, that this record was entered or created via Self-Service or that the user can change it via the Self-Service functionality.',0,'Y','N','N','N','N','N','Y','Y','Self-Service',10,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.374
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582307 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:15.377
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2063) 
;

-- 2019-07-23T19:30:15.393
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582307
;

-- 2019-07-23T19:30:15.393
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582307)
;

-- 2019-07-23T19:30:15.396
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582307
;

-- 2019-07-23T19:30:15.397
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582307, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563359
;

-- 2019-07-23T19:30:15.480
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5441,114,582308,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'Text templates for mailings',14,'U','The Mail Template indicates the mail template for return messages. Mail text can include variables.  The priority of parsing is User/Contact, Business Partner and then the underlying business object (like Request, Dunning, Workflow object).<br>
So, @Name@ would resolve into the User name (if user is defined defined), then Business Partner name (if business partner is defined) and then the Name of the business object if it has a Name.<br>
For Multi-Lingual systems, the template is translated based on the Business Partner''s language selection.',0,'Y','N','N','N','N','N','N','Y','Mail Template',20,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.481
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:15.484
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1515) 
;

-- 2019-07-23T19:30:15.493
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582308
;

-- 2019-07-23T19:30:15.494
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582308)
;

-- 2019-07-23T19:30:15.496
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582308
;

-- 2019-07-23T19:30:15.497
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582308, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563360
;

-- 2019-07-23T19:30:15.565
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13078,582309,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',14,'@AD_Table_ID@!0','U','The Database Table provides the information of the table definition',0,'Y','N','N','N','N','N','Y','N','DB-Tabelle',30,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.566
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582309 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:15.568
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2019-07-23T19:30:15.584
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582309
;

-- 2019-07-23T19:30:15.584
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582309)
;

-- 2019-07-23T19:30:15.586
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582309
;

-- 2019-07-23T19:30:15.586
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582309, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563361
;

-- 2019-07-23T19:30:15.640
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13490,582310,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'Related Request (Master Issue, ..)',14,'U','Request related to this request',0,'Y','N','N','N','N','N','N','Y','Related Request',40,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.641
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:15.644
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2710) 
;

-- 2019-07-23T19:30:15.648
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582310
;

-- 2019-07-23T19:30:15.648
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582310)
;

-- 2019-07-23T19:30:15.650
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582310
;

-- 2019-07-23T19:30:15.651
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582310, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563362
;

-- 2019-07-23T19:30:15.752
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13488,114,582311,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role',14,'U','The Role determines security and access a user who has this Role will have in the System.',0,'Y','N','N','N','N','N','N','Y','Rolle',50,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.753
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:15.756
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(123) 
;

-- 2019-07-23T19:30:15.768
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582311
;

-- 2019-07-23T19:30:15.768
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582311)
;

-- 2019-07-23T19:30:15.771
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582311
;

-- 2019-07-23T19:30:15.771
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582311, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563363
;

-- 2019-07-23T19:30:15.850
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13575,582312,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)',20,'U','The Start Date indicates the first or starting date',0,'Y','N','N','N','N','N','N','N','Anfangsdatum',60,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.851
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:15.854
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(574) 
;

-- 2019-07-23T19:30:15.863
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582312
;

-- 2019-07-23T19:30:15.863
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582312)
;

-- 2019-07-23T19:30:15.866
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582312
;

-- 2019-07-23T19:30:15.867
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582312, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563364
;

-- 2019-07-23T19:30:15.951
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13496,114,582313,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'Menge, die bereits in Rechnung gestellt wurde',26,'@IsInvoiced@=Y','U',0,'Y','N','N','N','N','N','N','Y','Berechn. Menge',70,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:15.953
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582313 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:15.956
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(529) 
;

-- 2019-07-23T19:30:15.969
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582313
;

-- 2019-07-23T19:30:15.970
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582313)
;

-- 2019-07-23T19:30:15.973
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582313
;

-- 2019-07-23T19:30:15.973
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582313, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563365
;

-- 2019-07-23T19:30:16.030
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13494,114,582314,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100,'End of the time span',20,'U',0,'Y','N','N','N','N','N','N','Y','Enddatum',80,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:16.031
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:16.034
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2714) 
;

-- 2019-07-23T19:30:16.039
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582314
;

-- 2019-07-23T19:30:16.040
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582314)
;

-- 2019-07-23T19:30:16.043
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582314
;

-- 2019-07-23T19:30:16.044
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582314, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563366
;

-- 2019-07-23T19:30:16.117
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13493,114,582315,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100,'',20,'U',0,'Y','N','N','N','N','N','N','N','Startdatum',90,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:16.118
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582315 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:16.121
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2713) 
;

-- 2019-07-23T19:30:16.126
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582315
;

-- 2019-07-23T19:30:16.126
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582315)
;

-- 2019-07-23T19:30:16.129
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582315
;

-- 2019-07-23T19:30:16.130
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582315, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563367
;

-- 2019-07-23T19:30:16.202
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13495,114,582316,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100,'Quantity used for this event',26,'U',0,'Y','N','N','N','N','N','N','Y','Quantity Used',100,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:16.203
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:16.206
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2715) 
;

-- 2019-07-23T19:30:16.212
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582316
;

-- 2019-07-23T19:30:16.213
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582316)
;

-- 2019-07-23T19:30:16.216
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582316
;

-- 2019-07-23T19:30:16.216
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582316, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563368
;

-- 2019-07-23T19:30:16.299
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5418,101,582317,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',110,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:16.301
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582317 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:16.303
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-07-23T19:30:16.468
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582317
;

-- 2019-07-23T19:30:16.468
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582317)
;

-- 2019-07-23T19:30:16.470
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582317
;

-- 2019-07-23T19:30:16.470
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582317, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563369
;

-- 2019-07-23T19:30:16.584
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12927,105,582318,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100,'Date when last alert were sent',14,'U','The last alert date is updated when a reminder email is sent',0,'Y','N','N','N','N','N','Y','Y','Last Alert',120,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:16.585
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582318 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:16.588
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2629) 
;

-- 2019-07-23T19:30:16.593
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582318
;

-- 2019-07-23T19:30:16.594
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582318)
;

-- 2019-07-23T19:30:16.596
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582318
;

-- 2019-07-23T19:30:16.597
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582318, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563370
;

-- 2019-07-23T19:30:16.667
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555043,114,582319,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','N','N','N','N','N','Y','N','Request Type Interner Name',130,0,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:16.668
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:16.670
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543175) 
;

-- 2019-07-23T19:30:16.671
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582319
;

-- 2019-07-23T19:30:16.671
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582319)
;

-- 2019-07-23T19:30:16.673
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582319
;

-- 2019-07-23T19:30:16.673
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582319, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563371
;

-- 2019-07-23T19:30:16.746
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13497,114,582320,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100,'Product/Resource/Service used in Request',26,'U','Invoicing uses the Product used.',0,'Y','N','N','N','N','N','N','N','Product Used',140,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:16.747
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:16.749
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2716) 
;

-- 2019-07-23T19:30:16.753
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582320
;

-- 2019-07-23T19:30:16.753
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582320)
;

-- 2019-07-23T19:30:16.755
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582320
;

-- 2019-07-23T19:30:16.756
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582320, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563372
;

-- 2019-07-23T19:30:16.825
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13489,582321,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100,'Is this invoiced?',1,'U','If selected, invoices are created',0,'Y','N','N','N','N','N','N','Y','Invoiced',150,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:16.826
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582321 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:16.829
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(387) 
;

-- 2019-07-23T19:30:16.838
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582321
;

-- 2019-07-23T19:30:16.838
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582321)
;

-- 2019-07-23T19:30:16.841
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582321
;

-- 2019-07-23T19:30:16.841
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582321, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563373
;

-- 2019-07-23T19:30:16.902
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13499,582322,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100,'The generated invoice for this request',14,'U','The optionally generated invoice for the request',0,'Y','N','N','N','N','N','Y','Y','Request Invoice',160,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:16.904
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582322 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:16.906
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2717) 
;

-- 2019-07-23T19:30:16.908
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582322
;

-- 2019-07-23T19:30:16.908
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582322)
;

-- 2019-07-23T19:30:16.910
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582322
;

-- 2019-07-23T19:30:16.910
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582322, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563374
;

-- 2019-07-23T19:30:16.969
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13576,582323,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100,'Close Date',20,'U','The Start Date indicates the last or final date',0,'Y','N','N','N','N','N','N','N','Close Date',170,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:16.970
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582323 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:16.972
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2721) 
;

-- 2019-07-23T19:30:16.975
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582323
;

-- 2019-07-23T19:30:16.975
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582323)
;

-- 2019-07-23T19:30:16.977
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582323
;

-- 2019-07-23T19:30:16.977
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582323, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563375
;

-- 2019-07-23T19:30:17.030
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13487,582324,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100,'Type of Confidentiality',14,'U',0,'Y','N','N','N','N','N','N','N','Confidentiality',180,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:17.031
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582324 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:17.032
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2709) 
;

-- 2019-07-23T19:30:17.034
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582324
;

-- 2019-07-23T19:30:17.035
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582324)
;

-- 2019-07-23T19:30:17.036
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582324
;

-- 2019-07-23T19:30:17.036
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582324, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563376
;

-- 2019-07-23T19:30:17.107
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5425,104,582325,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100,'Amount associated with this request',26,'U','The Request Amount indicates any amount that is associated with this request.  For example, a warranty amount or refund amount.',0,'Y','N','N','N','N','N','N','N','Request Amount',190,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:17.108
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582325 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:17.110
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1520) 
;

-- 2019-07-23T19:30:17.112
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582325
;

-- 2019-07-23T19:30:17.113
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582325)
;

-- 2019-07-23T19:30:17.114
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582325
;

-- 2019-07-23T19:30:17.114
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582325, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563377
;

-- 2019-07-23T19:30:17.201
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13491,114,582326,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100,'Confidentiality of the individual entry',14,'U',0,'Y','N','N','N','N','N','N','Y','Entry Confidentiality',200,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:17.203
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582326 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:17.206
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2711) 
;

-- 2019-07-23T19:30:17.209
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582326
;

-- 2019-07-23T19:30:17.210
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582326)
;

-- 2019-07-23T19:30:17.213
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582326
;

-- 2019-07-23T19:30:17.213
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582326, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563378
;

-- 2019-07-23T19:30:17.290
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5429,582327,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100,'This request has been escalated',1,'U','The Escalated checkbox indicates that this request has been escalated or raised in importance.',0,'Y','N','N','N','N','N','Y','N','Escalated',210,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:17.292
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582327 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:17.294
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1509) 
;

-- 2019-07-23T19:30:17.298
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582327
;

-- 2019-07-23T19:30:17.298
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582327)
;

-- 2019-07-23T19:30:17.301
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582327
;

-- 2019-07-23T19:30:17.302
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582327, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563379
;

-- 2019-07-23T19:30:17.372
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10580,582328,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100,'Asset used internally or by customers',26,'U','An asset is either created by purchasing or by delivering a product.  An asset can be used internally or be a customer asset.',0,'Y','N','N','N','N','N','N','Y','Asset',220,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:17.374
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582328 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:17.377
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1884) 
;

-- 2019-07-23T19:30:17.402
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582328
;

-- 2019-07-23T19:30:17.402
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582328)
;

-- 2019-07-23T19:30:17.405
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582328
;

-- 2019-07-23T19:30:17.405
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582328, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563380
;

-- 2019-07-23T19:30:17.473
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13483,582329,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100,'Request Category',14,'U','Category or Topic of the Request ',0,'Y','N','N','N','N','N','N','N','Category',230,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:17.475
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582329 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:17.478
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2705) 
;

-- 2019-07-23T19:30:17.483
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582329
;

-- 2019-07-23T19:30:17.484
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582329)
;

-- 2019-07-23T19:30:17.488
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582329
;

-- 2019-07-23T19:30:17.488
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582329, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563381
;

-- 2019-07-23T19:30:17.559
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5416,582330,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:17.561
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582330 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:17.564
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-07-23T19:30:17.746
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582330
;

-- 2019-07-23T19:30:17.746
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582330)
;

-- 2019-07-23T19:30:17.748
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582330
;

-- 2019-07-23T19:30:17.748
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582330, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563382
;

-- 2019-07-23T19:30:17.824
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5417,582331,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2019-07-23 19:30:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:17.826
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:17.828
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-07-23T19:30:18.004
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582331
;

-- 2019-07-23T19:30:18.004
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582331)
;

-- 2019-07-23T19:30:18.006
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582331
;

-- 2019-07-23T19:30:18.006
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582331, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563383
;

-- 2019-07-23T19:30:18.146
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7791,582332,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,'','Type of request (e.g. Inquiry, Complaint, ..)',14,'U','Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.',0,'Y','Y','Y','N','N','N','N','N','Vorgangsart',25,25,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:18.147
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582332 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:18.150
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1894) 
;

-- 2019-07-23T19:30:18.156
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582332
;

-- 2019-07-23T19:30:18.156
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582332)
;

-- 2019-07-23T19:30:18.158
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582332
;

-- 2019-07-23T19:30:18.159
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582332, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563384
;

-- 2019-07-23T19:30:18.241
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5433,582333,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',30,30,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:18.243
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582333 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:18.246
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2019-07-23T19:30:18.251
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582333
;

-- 2019-07-23T19:30:18.252
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582333)
;

-- 2019-07-23T19:30:18.254
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582333
;

-- 2019-07-23T19:30:18.255
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582333, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563385
;

-- 2019-07-23T19:30:18.327
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5423,582334,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',20,'U','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','Y','Nr.',40,40,3,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:18.328
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582334 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:18.330
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2019-07-23T19:30:18.333
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582334
;

-- 2019-07-23T19:30:18.333
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582334)
;

-- 2019-07-23T19:30:18.335
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582334
;

-- 2019-07-23T19:30:18.336
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582334, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563386
;

-- 2019-07-23T19:30:18.398
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5434,582335,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'U','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','Y','Ansprechpartner',50,50,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:18.399
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582335 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:18.402
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2019-07-23T19:30:18.406
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582335
;

-- 2019-07-23T19:30:18.406
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582335)
;

-- 2019-07-23T19:30:18.409
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582335
;

-- 2019-07-23T19:30:18.409
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582335, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563387
;

-- 2019-07-23T19:30:18.480
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556050,582336,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Zulieferant',55,55,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:18.482
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582336 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:18.485
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543273) 
;

-- 2019-07-23T19:30:18.488
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582336
;

-- 2019-07-23T19:30:18.489
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582336)
;

-- 2019-07-23T19:30:18.492
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582336
;

-- 2019-07-23T19:30:18.492
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582336, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563388
;

-- 2019-07-23T19:30:18.569
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5799,582337,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','Project',60,60,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:18.571
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582337 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:18.574
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2019-07-23T19:30:18.595
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582337
;

-- 2019-07-23T19:30:18.595
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582337)
;

-- 2019-07-23T19:30:18.598
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582337
;

-- 2019-07-23T19:30:18.598
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582337, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563389
;

-- 2019-07-23T19:30:18.670
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5436,582338,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',26,'U','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.',0,'Y','Y','Y','N','N','N','N','N','Auftrag',70,70,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:18.671
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582338 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:18.674
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2019-07-23T19:30:18.691
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582338
;

-- 2019-07-23T19:30:18.691
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582338)
;

-- 2019-07-23T19:30:18.694
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582338
;

-- 2019-07-23T19:30:18.694
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582338, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563390
;

-- 2019-07-23T19:30:18.762
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555039,582339,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Ware geliefert wurde',0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','Lieferdatum',80,80,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:18.763
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582339 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:18.765
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(264) 
;

-- 2019-07-23T19:30:18.768
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582339
;

-- 2019-07-23T19:30:18.768
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582339)
;

-- 2019-07-23T19:30:18.770
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582339
;

-- 2019-07-23T19:30:18.771
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582339, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563391
;

-- 2019-07-23T19:30:18.841
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5437,582340,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','The Invoice Document.',0,'Y','Y','Y','N','N','N','N','Y','Rechnung',90,90,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:18.842
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582340 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:18.844
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008) 
;

-- 2019-07-23T19:30:18.852
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582340
;

-- 2019-07-23T19:30:18.853
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582340)
;

-- 2019-07-23T19:30:18.855
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582340
;

-- 2019-07-23T19:30:18.855
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582340, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563392
;

-- 2019-07-23T19:30:18.931
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5439,582341,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',26,'U','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',100,100,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:18.932
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582341 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:18.934
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2019-07-23T19:30:18.974
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582341
;

-- 2019-07-23T19:30:18.975
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582341)
;

-- 2019-07-23T19:30:18.977
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582341
;

-- 2019-07-23T19:30:18.977
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582341, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563393
;

-- 2019-07-23T19:30:19.056
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5438,582342,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100,'Zahlung',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).',0,'Y','Y','Y','N','N','N','N','Y','Zahlung',110,110,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.057
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582342 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.061
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1384) 
;

-- 2019-07-23T19:30:19.074
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582342
;

-- 2019-07-23T19:30:19.074
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582342)
;

-- 2019-07-23T19:30:19.077
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582342
;

-- 2019-07-23T19:30:19.078
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582342, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563394
;

-- 2019-07-23T19:30:19.150
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5435,582343,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',14,'@$Element_MC@=Y','U','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','Y','Y','N','N','N','N','Y','Werbemassnahme',115,115,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.151
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582343 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.153
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550) 
;

-- 2019-07-23T19:30:19.167
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582343
;

-- 2019-07-23T19:30:19.168
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582343)
;

-- 2019-07-23T19:30:19.170
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582343
;

-- 2019-07-23T19:30:19.170
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582343, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563395
;

-- 2019-07-23T19:30:19.228
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13573,582344,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document',26,'U','The Material Shipment / Receipt ',0,'Y','Y','Y','N','N','N','N','N','Lieferung/Wareneingang',120,120,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.229
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582344 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.231
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1025) 
;

-- 2019-07-23T19:30:19.232
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582344
;

-- 2019-07-23T19:30:19.233
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582344)
;

-- 2019-07-23T19:30:19.234
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582344
;

-- 2019-07-23T19:30:19.235
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582344, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563396
;

-- 2019-07-23T19:30:19.306
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13574,582345,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Return Material Authorization',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','A Return Material Authorization may be required to accept returns and to create Credit Memos',0,'Y','Y','Y','N','N','N','N','Y','RMA',130,130,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.307
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582345 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.309
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2412) 
;

-- 2019-07-23T19:30:19.313
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582345
;

-- 2019-07-23T19:30:19.313
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582345)
;

-- 2019-07-23T19:30:19.315
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582345
;

-- 2019-07-23T19:30:19.315
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582345, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563397
;

-- 2019-07-23T19:30:19.399
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555040,582346,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','Rücklieferung',140,140,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.400
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582346 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.401
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543174) 
;

-- 2019-07-23T19:30:19.403
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582346
;

-- 2019-07-23T19:30:19.403
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582346)
;

-- 2019-07-23T19:30:19.404
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582346
;

-- 2019-07-23T19:30:19.405
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582346, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563398
;

-- 2019-07-23T19:30:19.500
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13079,582347,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',23,'@AD_Table_ID@!0','U','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','Y','Y','N','N','N','Y','Y','Datensatz-ID',150,150,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.501
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582347 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.502
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2019-07-23T19:30:19.510
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582347
;

-- 2019-07-23T19:30:19.511
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582347)
;

-- 2019-07-23T19:30:19.512
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582347
;

-- 2019-07-23T19:30:19.512
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582347, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563399
;

-- 2019-07-23T19:30:19.591
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13498,114,582348,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Erfassung der zugehörigen Kostenstelle',0,'Y','Y','Y','N','N','N','N','N','Kostenstelle',160,160,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.592
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582348 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.593
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2019-07-23T19:30:19.602
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582348
;

-- 2019-07-23T19:30:19.602
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582348)
;

-- 2019-07-23T19:30:19.604
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582348
;

-- 2019-07-23T19:30:19.604
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582348, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563400
;

-- 2019-07-23T19:30:19.674
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5445,114,582349,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Date that this request should be acted on',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','The Date Next Action indicates the next scheduled date for an action to occur for this request.',0,'Y','Y','Y','N','N','N','N','N','Date next action',180,180,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.676
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582349 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.678
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1503) 
;

-- 2019-07-23T19:30:19.682
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582349
;

-- 2019-07-23T19:30:19.682
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582349)
;

-- 2019-07-23T19:30:19.685
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582349
;

-- 2019-07-23T19:30:19.685
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582349, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563401
;

-- 2019-07-23T19:30:19.757
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5427,114,582350,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Status of the next action for this Request',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','The Due Type indicates if this request is Due, Overdue or Scheduled.',0,'Y','Y','Y','N','N','N','N','Y','Due type',190,190,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.758
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582350 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.761
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1504) 
;

-- 2019-07-23T19:30:19.764
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582350
;

-- 2019-07-23T19:30:19.764
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582350)
;

-- 2019-07-23T19:30:19.767
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582350
;

-- 2019-07-23T19:30:19.767
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582350, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563402
;

-- 2019-07-23T19:30:19.839
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13482,114,582351,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Request Group',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Group of requests (e.g. version numbers, responsibility, ...)',0,'Y','Y','Y','N','N','N','N','Y','Group',220,220,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.840
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582351 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.842
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2704) 
;

-- 2019-07-23T19:30:19.846
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582351
;

-- 2019-07-23T19:30:19.846
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582351)
;

-- 2019-07-23T19:30:19.848
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582351
;

-- 2019-07-23T19:30:19.848
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582351, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563403
;

-- 2019-07-23T19:30:19.920
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13485,114,582352,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Request Resolution',14,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Resolution status (e.g. Fixed, Rejected, ..)',0,'Y','Y','Y','N','N','N','N','N','Resolution',240,240,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:19.921
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582352 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:19.923
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2707) 
;

-- 2019-07-23T19:30:19.925
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582352
;

-- 2019-07-23T19:30:19.925
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582352)
;

-- 2019-07-23T19:30:19.927
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582352
;

-- 2019-07-23T19:30:19.927
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582352, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563404
;

-- 2019-07-23T19:30:20.010
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5426,114,582353,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this request is of a high, medium or low priority.',14,'U','The Priority indicates the importance of this request.',0,'Y','Y','Y','N','N','N','N','N','Priority',260,260,2,1,1,TO_TIMESTAMP('2019-07-23 19:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.011
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582353 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.014
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1514) 
;

-- 2019-07-23T19:30:20.022
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582353
;

-- 2019-07-23T19:30:20.022
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582353)
;

-- 2019-07-23T19:30:20.025
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582353
;

-- 2019-07-23T19:30:20.026
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582353, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563405
;

-- 2019-07-23T19:30:20.091
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555046,114,582354,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','PerformanceType',265,265,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.092
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582354 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.096
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543176) 
;

-- 2019-07-23T19:30:20.098
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582354
;

-- 2019-07-23T19:30:20.098
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582354)
;

-- 2019-07-23T19:30:20.102
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582354
;

-- 2019-07-23T19:30:20.102
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582354, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563406
;

-- 2019-07-23T19:30:20.176
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13486,114,582355,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Priority of the issue for the User',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','User Importance',270,270,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.178
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582355 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.181
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2708) 
;

-- 2019-07-23T19:30:20.185
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582355
;

-- 2019-07-23T19:30:20.185
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582355)
;

-- 2019-07-23T19:30:20.188
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582355
;

-- 2019-07-23T19:30:20.188
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582355, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563407
;

-- 2019-07-23T19:30:20.249
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555041,114,582356,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','N','Qualität-Notiz',275,275,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.251
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582356 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.254
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543213) 
;

-- 2019-07-23T19:30:20.256
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582356
;

-- 2019-07-23T19:30:20.256
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582356)
;

-- 2019-07-23T19:30:20.259
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582356
;

-- 2019-07-23T19:30:20.260
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582356, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563408
;

-- 2019-07-23T19:30:20.329
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5428,114,582357,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Textual summary of this request',60,'U','The Summary allows free form text entry of a recap of this request.',0,'Y','Y','Y','N','N','N','N','N','Summary',280,280,999,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.331
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582357 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.334
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1521) 
;

-- 2019-07-23T19:30:20.340
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582357
;

-- 2019-07-23T19:30:20.340
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582357)
;

-- 2019-07-23T19:30:20.343
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582357
;

-- 2019-07-23T19:30:20.343
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582357, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563409
;

-- 2019-07-23T19:30:20.417
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5444,114,582358,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Next Action to be taken',14,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U','The Next Action indicates the next action to be taken on this request.',0,'Y','Y','Y','N','N','N','N','N','Next action',285,285,1,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.418
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582358 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.422
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1513) 
;

-- 2019-07-23T19:30:20.425
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582358
;

-- 2019-07-23T19:30:20.426
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582358)
;

-- 2019-07-23T19:30:20.429
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582358
;

-- 2019-07-23T19:30:20.429
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582358, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563410
;

-- 2019-07-23T19:30:20.516
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5432,114,582359,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,'',14,'U','',0,'Y','Y','Y','N','N','N','Y','N','N','Aussendienst',290,290,1,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.518
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582359 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.521
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063) 
;

-- 2019-07-23T19:30:20.538
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582359
;

-- 2019-07-23T19:30:20.538
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582359)
;

-- 2019-07-23T19:30:20.541
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582359
;

-- 2019-07-23T19:30:20.541
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582359, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563411
;

-- 2019-07-23T19:30:20.619
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13492,114,582360,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Request Standard Response ',14,'U','Text blocks to be copied into request response text',0,'Y','Y','Y','N','N','N','N','N','Standard Response',300,300,1,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.620
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582360 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.623
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2712) 
;

-- 2019-07-23T19:30:20.626
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582360
;

-- 2019-07-23T19:30:20.627
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582360)
;

-- 2019-07-23T19:30:20.629
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582360
;

-- 2019-07-23T19:30:20.630
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582360, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563412
;

-- 2019-07-23T19:30:20.688
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5443,114,582361,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Result of the action taken',60,'U','The Result indicates the result of any action taken on this request.',0,'Y','Y','Y','N','N','N','N','N','Ergebnis',310,310,999,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.689
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582361 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.692
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(546) 
;

-- 2019-07-23T19:30:20.697
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582361
;

-- 2019-07-23T19:30:20.697
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582361)
;

-- 2019-07-23T19:30:20.700
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582361
;

-- 2019-07-23T19:30:20.700
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582361, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563413
;

-- 2019-07-23T19:30:20.770
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13484,114,582362,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Request Status',14,'U','Status if the request (open, closed, investigating, ..)',0,'Y','Y','Y','N','N','N','N','N','Status',320,320,1,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.771
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582362 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.774
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2706) 
;

-- 2019-07-23T19:30:20.777
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582362
;

-- 2019-07-23T19:30:20.777
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582362)
;

-- 2019-07-23T19:30:20.780
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582362
;

-- 2019-07-23T19:30:20.780
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582362, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563414
;

-- 2019-07-23T19:30:20.852
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5430,105,582363,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Date this request was last acted on',20,'U','The Date Last Action indicates that last time that the request was acted on.',0,'Y','Y','Y','N','N','N','Y','N','Date last action',340,340,1,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.853
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582363 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.855
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1502) 
;

-- 2019-07-23T19:30:20.858
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582363
;

-- 2019-07-23T19:30:20.858
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582363)
;

-- 2019-07-23T19:30:20.860
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582363
;

-- 2019-07-23T19:30:20.860
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582363, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563415
;

-- 2019-07-23T19:30:20.942
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,Included_Tab_ID,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5415,105,582364,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Request from a Business Partner or Prospect',14,'U','The Request identifies a unique request from a Business Partner or Prospect.',0,740,'Y','Y','Y','N','N','N','N','N','Aufgabe',350,350,1,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:20.945
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582364 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:20.947
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1516) 
;

-- 2019-07-23T19:30:20.951
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582364
;

-- 2019-07-23T19:30:20.951
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582364)
;

-- 2019-07-23T19:30:20.953
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582364
;

-- 2019-07-23T19:30:20.953
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582364, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563416
;

-- 2019-07-23T19:30:21.024
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5431,105,582365,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100,'Result of last contact',60,'U','The Last Result identifies the result of the last contact made.',0,'Y','Y','Y','N','N','N','Y','Y','Last Result',355,355,999,1,TO_TIMESTAMP('2019-07-23 19:30:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:21.025
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582365 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:21.027
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(431) 
;

-- 2019-07-23T19:30:21.028
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582365
;

-- 2019-07-23T19:30:21.029
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582365)
;

-- 2019-07-23T19:30:21.030
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582365
;

-- 2019-07-23T19:30:21.030
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582365, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563417
;

-- 2019-07-23T19:30:21.102
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5419,105,582366,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',20,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','Y','N','Erstellt',360,360,1,1,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:21.104
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582366 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:21.106
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-07-23T19:30:21.142
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582366
;

-- 2019-07-23T19:30:21.142
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582366)
;

-- 2019-07-23T19:30:21.144
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582366
;

-- 2019-07-23T19:30:21.144
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582366, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563418
;

-- 2019-07-23T19:30:21.235
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5420,105,582367,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',14,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','Y','Y','Erstellt durch',370,370,1,1,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:21.236
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582367 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:21.240
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-07-23T19:30:21.288
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582367
;

-- 2019-07-23T19:30:21.288
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582367)
;

-- 2019-07-23T19:30:21.290
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582367
;

-- 2019-07-23T19:30:21.291
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582367, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563419
;

-- 2019-07-23T19:30:21.362
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5421,105,582368,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',20,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','Y','N','N','N','Y','N','Aktualisiert',380,380,1,1,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:21.363
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582368 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:21.366
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-07-23T19:30:21.409
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582368
;

-- 2019-07-23T19:30:21.410
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582368)
;

-- 2019-07-23T19:30:21.413
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582368
;

-- 2019-07-23T19:30:21.413
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582368, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563420
;

-- 2019-07-23T19:30:21.485
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5422,105,582369,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',14,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','Y','N','N','N','Y','Y','Aktualisiert durch',390,390,1,1,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:21.486
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582369 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:21.488
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-07-23T19:30:21.517
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582369
;

-- 2019-07-23T19:30:21.518
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582369)
;

-- 2019-07-23T19:30:21.520
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582369
;

-- 2019-07-23T19:30:21.520
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582369, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563421
;

-- 2019-07-23T19:30:21.639
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5447,105,582370,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'1=2','U','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','Y','Y','Verarbeitet',400,400,1,1,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:21.639
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582370 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:21.642
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2019-07-23T19:30:21.644
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582370
;

-- 2019-07-23T19:30:21.644
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582370)
;

-- 2019-07-23T19:30:21.646
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582370
;

-- 2019-07-23T19:30:21.646
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582370, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563422
;

-- 2019-07-23T19:30:21.715
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557025,582371,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Wiedervorlage Datum',410,410,0,1,1,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:21.716
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582371 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:21.719
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543388) 
;

-- 2019-07-23T19:30:21.721
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582371
;

-- 2019-07-23T19:30:21.721
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582371)
;

-- 2019-07-23T19:30:21.724
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582371
;

-- 2019-07-23T19:30:21.724
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582371, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563423
;

-- 2019-07-23T19:30:21.809
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557045,582372,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum',0,'U','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.',0,'Y','Y','Y','N','N','N','N','N','Vorgangsdatum',420,420,-1,1,1,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:21.810
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582372 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:21.813
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1297) 
;

-- 2019-07-23T19:30:21.818
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582372
;

-- 2019-07-23T19:30:21.819
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582372)
;

-- 2019-07-23T19:30:21.821
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582372
;

-- 2019-07-23T19:30:21.822
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582372, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563424
;

-- 2019-07-23T19:30:21.892
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540359,582373,0,541831,0,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,40,'U',0,'Y','N','N','N','N','N','N','N','Actual Phone',430,999,1,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:21.893
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582373 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:21.896
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540063) 
;

-- 2019-07-23T19:30:21.901
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582373
;

-- 2019-07-23T19:30:21.902
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582373)
;

-- 2019-07-23T19:30:21.905
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582373
;

-- 2019-07-23T19:30:21.905
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582373, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563425
;

-- 2019-07-23T19:30:21.979
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541831,541390,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,'1000029')
;

-- 2019-07-23T19:30:21.979
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541390 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-07-23T19:30:21.981
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541390
;

-- 2019-07-23T19:30:21.982
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541390, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540692
;

-- 2019-07-23T19:30:22.052
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541785,541390,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-07-23 19:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:22.112
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541785,542697,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:22.179
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582372,0,541831,542697,560256,'F',TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Vorgangsdatum',10,0,0,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:22.260
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582334,0,541831,542697,560257,'F',TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Nr.',20,0,0,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:22.339
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582332,0,541831,542697,560258,'F',TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Request Type_',30,0,0,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:22.429
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582362,0,541831,542697,560259,'F',TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','status',40,0,0,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:22.502
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582357,0,541831,542697,560260,'F',TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Summary_',50,0,0,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:22.572
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582359,0,541831,542697,560261,'F',TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Aussendienst',60,0,0,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:22.642
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,NULL,572687,0,541832,540961,540668,'Y',TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Projektkontakt',1349,'N',70,1,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:22.644
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541832 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-07-23T19:30:22.646
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572687) 
;

-- 2019-07-23T19:30:22.650
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541832)
;

-- 2019-07-23T19:30:22.652
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541832
;

-- 2019-07-23T19:30:22.652
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541832, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 541071
;

-- 2019-07-23T19:30:22.728
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559664,582374,0,541832,0,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,1,1,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:22.729
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582374 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:22.732
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-07-23T19:30:22.925
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582374
;

-- 2019-07-23T19:30:22.926
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582374)
;

-- 2019-07-23T19:30:22.927
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582374
;

-- 2019-07-23T19:30:22.928
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582374, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563428
;

-- 2019-07-23T19:30:23.001
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559665,582375,0,541832,0,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','N','Sektion',20,1,1,TO_TIMESTAMP('2019-07-23 19:30:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:23.002
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582375 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:23.006
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-07-23T19:30:23.192
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582375
;

-- 2019-07-23T19:30:23.192
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582375)
;

-- 2019-07-23T19:30:23.193
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582375
;

-- 2019-07-23T19:30:23.194
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582375, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563429
;

-- 2019-07-23T19:30:23.346
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559666,582376,0,541832,0,TO_TIMESTAMP('2019-07-23 19:30:23','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',10,'U','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','N','Ansprechpartner',30,1,1,TO_TIMESTAMP('2019-07-23 19:30:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:23.347
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582376 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:23.349
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2019-07-23T19:30:23.352
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582376
;

-- 2019-07-23T19:30:23.352
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582376)
;

-- 2019-07-23T19:30:23.354
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582376
;

-- 2019-07-23T19:30:23.354
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582376, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563430
;

-- 2019-07-23T19:30:23.440
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559669,582377,0,541832,0,TO_TIMESTAMP('2019-07-23 19:30:23','YYYY-MM-DD HH24:MI:SS'),100,255,'U',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',40,1,1,TO_TIMESTAMP('2019-07-23 19:30:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:23.441
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582377 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:23.444
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-07-23T19:30:23.522
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582377
;

-- 2019-07-23T19:30:23.522
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582377)
;

-- 2019-07-23T19:30:23.524
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582377
;

-- 2019-07-23T19:30:23.525
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582377, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563431
;

-- 2019-07-23T19:30:23.603
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559670,582378,0,541832,0,TO_TIMESTAMP('2019-07-23 19:30:23','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',50,1,1,TO_TIMESTAMP('2019-07-23 19:30:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:23.605
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582378 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:23.608
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-07-23T19:30:23.818
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582378
;

-- 2019-07-23T19:30:23.819
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582378)
;

-- 2019-07-23T19:30:23.820
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582378
;

-- 2019-07-23T19:30:23.820
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582378, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563432
;

-- 2019-07-23T19:30:23.935
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559675,582379,0,541832,0,TO_TIMESTAMP('2019-07-23 19:30:23','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'U','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','Project',60,1,1,TO_TIMESTAMP('2019-07-23 19:30:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:23.936
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582379 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:23.937
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2019-07-23T19:30:23.948
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582379
;

-- 2019-07-23T19:30:23.948
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582379)
;

-- 2019-07-23T19:30:23.949
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582379
;

-- 2019-07-23T19:30:23.949
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582379, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563434
;

-- 2019-07-23T19:30:24.063
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559677,582380,0,541832,0,TO_TIMESTAMP('2019-07-23 19:30:23','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','N','N','N','N','N','N','N','Projektkontakt',70,1,1,TO_TIMESTAMP('2019-07-23 19:30:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:24.064
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582380 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-23T19:30:24.066
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543957) 
;

-- 2019-07-23T19:30:24.068
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582380
;

-- 2019-07-23T19:30:24.068
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582380)
;

-- 2019-07-23T19:30:24.070
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 582380
;

-- 2019-07-23T19:30:24.071
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 582380, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563435
;

-- 2019-07-23T19:30:24.144
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541832,541391,TO_TIMESTAMP('2019-07-23 19:30:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-07-23 19:30:24','YYYY-MM-DD HH24:MI:SS'),100,'1000030')
;

-- 2019-07-23T19:30:24.145
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541391 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-07-23T19:30:24.147
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541391
;

-- 2019-07-23T19:30:24.148
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541391, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540693
;

-- 2019-07-23T19:30:24.218
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541786,541391,TO_TIMESTAMP('2019-07-23 19:30:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-07-23 19:30:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:24.280
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541786,542698,TO_TIMESTAMP('2019-07-23 19:30:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-07-23 19:30:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:24.345
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582376,0,541832,542698,560262,'F',TO_TIMESTAMP('2019-07-23 19:30:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Ansprechpartner',10,0,0,TO_TIMESTAMP('2019-07-23 19:30:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-23T19:30:24.424
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582377,0,541832,542698,560263,'F',TO_TIMESTAMP('2019-07-23 19:30:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2019-07-23 19:30:24','YYYY-MM-DD HH24:MI:SS'),100)
;

