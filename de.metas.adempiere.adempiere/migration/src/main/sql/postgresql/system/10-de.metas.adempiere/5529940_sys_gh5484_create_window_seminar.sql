-- 2019-09-05T14:41:37.884Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577040,0,TO_TIMESTAMP('2019-09-05 16:41:37','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Seminar','Seminar',TO_TIMESTAMP('2019-09-05 16:41:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:41:37.887Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577040 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T14:42:21.607Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,577040,0,540680,TO_TIMESTAMP('2019-09-05 16:42:21','YYYY-MM-DD HH24:MI:SS'),100,'U','Seminar','Y','N','N','N','N','Y','Seminar','N',TO_TIMESTAMP('2019-09-05 16:42:21','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-09-05T14:42:21.610Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540680 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-09-05T14:42:21.640Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(577040) 
;

-- 2019-09-05T14:42:21.645Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540680
;

-- 2019-09-05T14:42:21.649Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540680)
;

-- 2019-09-05T14:42:35.112Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Element_ID=572687, AD_Image_ID=NULL, AD_Org_ID=0, Description=NULL, EntityType='U', Help=NULL, IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='N', IsOneInstanceOnly='N', IsSOTrx='Y', Processing='N', WindowType='M', WinHeight=0, WinWidth=0,Updated=TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540680
;

-- 2019-09-05T14:42:35.116Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(572687) 
;

-- 2019-09-05T14:42:35.119Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540680
;

-- 2019-09-05T14:42:35.120Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540680)
;

-- 2019-09-05T14:42:35.125Z
-- URL zum Konzept
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 540680
;

-- 2019-09-05T14:42:35.126Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 540680, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 540668
;

-- 2019-09-05T14:42:35.187Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572687,0,217,541865,203,540680,'Y',TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Define Project','U','N','The Project Tab is used to define the Value, Name and Description for each project.  It also is defines the tracks the amounts assigned to, committed to and used for this project. Note that when the project Type is changed, the Phases and Tasks are re-created.','Y','N','Y','Y','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Projekt','N',10,0,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:35.189Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541865 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-05T14:42:35.193Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572687) 
;

-- 2019-09-05T14:42:35.197Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541865)
;

-- 2019-09-05T14:42:35.200Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541865
;

-- 2019-09-05T14:42:35.201Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541865, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 541830
;

-- 2019-09-05T14:42:35.277Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5747,583478,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Generate To',23,'@IsSummary@=N','U',0,'Y','N','N','N','N','N','N','Y','Generate To',10,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:35.281Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583478 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:35.285Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1491) 
;

-- 2019-09-05T14:42:35.302Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583478
;

-- 2019-09-05T14:42:35.302Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583478)
;

-- 2019-09-05T14:42:35.304Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583478
;

-- 2019-09-05T14:42:35.304Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583478, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582266
;

-- 2019-09-05T14:42:35.362Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8978,583479,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Betrag / Menge der Zusage ist die Obergrenze für die Abrechnung',1,'@IsSummary@=N & @IsCommitment@=Y','U','Zusage-Betrag und -Menge sind maximal abrechenbarer Betrag und Menge. Ignoriert, wenn Betrag oder Menge gleich 0.',0,'Y','N','N','N','N','N','N','Y','Zusage ist Obergrenze',20,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:35.364Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583479 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:35.367Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2077) 
;

-- 2019-09-05T14:42:35.375Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583479
;

-- 2019-09-05T14:42:35.376Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583479)
;

-- 2019-09-05T14:42:35.377Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583479
;

-- 2019-09-05T14:42:35.378Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583479, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582267
;

-- 2019-09-05T14:42:35.445Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5749,583480,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'U','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','Verarbeitet',30,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:35.446Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583480 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:35.450Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2019-09-05T14:42:35.512Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583480
;

-- 2019-09-05T14:42:35.512Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583480)
;

-- 2019-09-05T14:42:35.515Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583480
;

-- 2019-09-05T14:42:35.515Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583480, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582268
;

-- 2019-09-05T14:42:35.574Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9856,583481,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Project Category',14,'U','The Project Category determines the behavior of the project:
General - no special accounting, e.g. for Presales or general tracking
Service - no special accounting, e.g. for Service/Charge projects
Work Order - creates Project/Job WIP transactions - ability to issue material
Asset - create Project Asset transactions - ability to issue material
',0,'Y','N','N','N','N','N','N','N','Project Category',40,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:35.576Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583481 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:35.580Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2179) 
;

-- 2019-09-05T14:42:35.593Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583481
;

-- 2019-09-05T14:42:35.593Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583481)
;

-- 2019-09-05T14:42:35.596Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583481
;

-- 2019-09-05T14:42:35.597Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583481, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582269
;

-- 2019-09-05T14:42:35.658Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1349,583482,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',14,'U','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','Project',50,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:35.660Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583482 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:35.664Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2019-09-05T14:42:35.742Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583482
;

-- 2019-09-05T14:42:35.742Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583482)
;

-- 2019-09-05T14:42:35.745Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583482
;

-- 2019-09-05T14:42:35.746Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583482, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582270
;

-- 2019-09-05T14:42:35.798Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3904,583483,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Is this document a (legal) commitment?',1,'@IsSummary@=N','U','Commitment indicates if the document is legally binding.',0,'Y','N','N','N','N','N','N','N','Commitment',60,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:35.802Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583483 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:35.806Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1101) 
;

-- 2019-09-05T14:42:35.818Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583483
;

-- 2019-09-05T14:42:35.819Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583483)
;

-- 2019-09-05T14:42:35.824Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583483
;

-- 2019-09-05T14:42:35.824Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583483, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582271
;

-- 2019-09-05T14:42:35.910Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1351,583484,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2019-09-05 16:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:35.911Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583484 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:35.913Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-05T14:42:36.260Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583484
;

-- 2019-09-05T14:42:36.260Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583484)
;

-- 2019-09-05T14:42:36.262Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583484
;

-- 2019-09-05T14:42:36.262Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583484, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582272
;

-- 2019-09-05T14:42:36.368Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1350,583485,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:36','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2019-09-05 16:42:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:36.369Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583485 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:36.371Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-05T14:42:36.554Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583485
;

-- 2019-09-05T14:42:36.554Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583485)
;

-- 2019-09-05T14:42:36.556Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583485
;

-- 2019-09-05T14:42:36.556Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583485, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582273
;

-- 2019-09-05T14:42:36.650Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2010,583486,577009,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:36','YYYY-MM-DD HH24:MI:SS'),100,20,'U',0,'Y','Y','Y','N','N','N','N','N','Projekt Nummer',30,30,-1,1,1,TO_TIMESTAMP('2019-09-05 16:42:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:36.651Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583486 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:36.653Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577009) 
;

-- 2019-09-05T14:42:36.656Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583486
;

-- 2019-09-05T14:42:36.656Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583486)
;

-- 2019-09-05T14:42:36.658Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583486
;

-- 2019-09-05T14:42:36.658Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583486, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582274
;

-- 2019-09-05T14:42:36.712Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5752,583487,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:36','YYYY-MM-DD HH24:MI:SS'),100,'',14,'@IsSummary@=N','U','',0,'Y','Y','Y','Y','N','N','N','Y','Verkaufsmitarbeiter',40,40,1,1,TO_TIMESTAMP('2019-09-05 16:42:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:36.713Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583487 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:36.715Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063) 
;

-- 2019-09-05T14:42:36.728Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583487
;

-- 2019-09-05T14:42:36.729Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583487)
;

-- 2019-09-05T14:42:36.730Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583487
;

-- 2019-09-05T14:42:36.730Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583487, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582275
;

-- 2019-09-05T14:42:36.792Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1356,583488,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:36','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',60,'U','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',0,'Y','Y','Y','N','N','N','N','N','Name',50,50,999,1,TO_TIMESTAMP('2019-09-05 16:42:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:36.793Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583488 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:36.794Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2019-09-05T14:42:37.002Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583488
;

-- 2019-09-05T14:42:37.002Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583488)
;

-- 2019-09-05T14:42:37.006Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583488
;

-- 2019-09-05T14:42:37.007Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583488, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582276
;

-- 2019-09-05T14:42:37.093Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1358,583489,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100,60,'U',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',60,60,999,1,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:37.094Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583489 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:37.097Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-09-05T14:42:37.222Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583489
;

-- 2019-09-05T14:42:37.223Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583489)
;

-- 2019-09-05T14:42:37.224Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583489
;

-- 2019-09-05T14:42:37.225Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583489, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582277
;

-- 2019-09-05T14:42:37.318Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1352,583490,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',70,70,1,1,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:37.319Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583490 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:37.322Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-05T14:42:37.628Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583490
;

-- 2019-09-05T14:42:37.628Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583490)
;

-- 2019-09-05T14:42:37.630Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583490
;

-- 2019-09-05T14:42:37.630Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583490, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582278
;

-- 2019-09-05T14:42:37.729Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1360,583491,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag',1,'U','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.',0,'Y','Y','Y','N','N','N','N','Y','Zusammenfassungseintrag',80,80,1,1,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:37.730Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583491 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:37.732Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(416) 
;

-- 2019-09-05T14:42:37.747Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583491
;

-- 2019-09-05T14:42:37.747Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583491)
;

-- 2019-09-05T14:42:37.749Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583491
;

-- 2019-09-05T14:42:37.750Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583491, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582279
;

-- 2019-09-05T14:42:37.808Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5750,583492,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100,'Optional weitere Information für ein Dokument',60,'U','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben',0,'Y','Y','Y','N','N','N','N','N','Notiz',90,90,999,1,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:37.810Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583492 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:37.812Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115) 
;

-- 2019-09-05T14:42:37.835Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583492
;

-- 2019-09-05T14:42:37.835Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583492)
;

-- 2019-09-05T14:42:37.837Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583492
;

-- 2019-09-05T14:42:37.838Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583492, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582280
;

-- 2019-09-05T14:42:37.889Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15469,583493,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100,'Project Line Level',1,'@IsSummary@=N','U','Level on which Project Lines are maintained',0,'Y','Y','Y','N','N','N','N','N','Line Level',100,100,1,1,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:37.890Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583493 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:37.894Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3035) 
;

-- 2019-09-05T14:42:37.897Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583493
;

-- 2019-09-05T14:42:37.898Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583493)
;

-- 2019-09-05T14:42:37.901Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583493
;

-- 2019-09-05T14:42:37.901Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583493, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582281
;

-- 2019-09-05T14:42:37.970Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8757,583494,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100,'Type of the project',23,'','U','Type of the project with optional phases of the project with standard performance information',0,'Y','Y','Y','N','N','N','N','N','Projekt - Projektart',110,110,1,1,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:37.972Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583494 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:37.975Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2033) 
;

-- 2019-09-05T14:42:37.978Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583494
;

-- 2019-09-05T14:42:37.978Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583494)
;

-- 2019-09-05T14:42:37.981Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583494
;

-- 2019-09-05T14:42:37.981Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583494, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582282
;

-- 2019-09-05T14:42:38.034Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8755,583495,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100,'Status oder Phase dieser Projektart',14,'@IsSummary@=N','U','Phase of the project with standard performance information with standard work',0,'Y','Y','Y','N','N','N','N','Y','Standard-Phase',120,120,1,1,TO_TIMESTAMP('2019-09-05 16:42:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.036Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583495 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.039Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2032) 
;

-- 2019-09-05T14:42:38.042Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583495
;

-- 2019-09-05T14:42:38.042Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583495)
;

-- 2019-09-05T14:42:38.045Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583495
;

-- 2019-09-05T14:42:38.046Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583495, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582283
;

-- 2019-09-05T14:42:38.098Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5745,583496,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftragseingangs',14,'@IsSummary@=N','U','The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.',0,'Y','Y','Y','N','N','N','N','N','Datum Auftragseingang',130,130,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.099Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583496 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.102Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1556) 
;

-- 2019-09-05T14:42:38.110Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583496
;

-- 2019-09-05T14:42:38.111Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583496)
;

-- 2019-09-05T14:42:38.114Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583496
;

-- 2019-09-05T14:42:38.114Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583496, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582284
;

-- 2019-09-05T14:42:38.175Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5746,583497,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Finish or (planned) completion date',14,'@IsSummary@=N','U','Dieses Datum gibt das erwartete oder tatsächliche Projektende an',0,'Y','Y','Y','N','N','N','N','Y','Projektabschluss',140,140,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.176Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583497 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.180Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1557) 
;

-- 2019-09-05T14:42:38.182Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583497
;

-- 2019-09-05T14:42:38.183Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583497)
;

-- 2019-09-05T14:42:38.186Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583497
;

-- 2019-09-05T14:42:38.186Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583497, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582285
;

-- 2019-09-05T14:42:38.242Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3902,104,583498,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'@IsSummary@=N','U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',150,150,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.244Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583498 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.247Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2019-09-05T14:42:38.254Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583498
;

-- 2019-09-05T14:42:38.254Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583498)
;

-- 2019-09-05T14:42:38.257Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583498
;

-- 2019-09-05T14:42:38.258Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583498, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582286
;

-- 2019-09-05T14:42:38.309Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14095,583499,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner (Agent or Sales Rep)',10,'@IsSummary@=N','U',0,'Y','Y','Y','N','N','N','N','Y','BPartner (Agent)',160,160,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.311Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583499 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.314Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2810) 
;

-- 2019-09-05T14:42:38.320Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583499
;

-- 2019-09-05T14:42:38.320Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583499)
;

-- 2019-09-05T14:42:38.323Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583499
;

-- 2019-09-05T14:42:38.324Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583499, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582287
;

-- 2019-09-05T14:42:38.387Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5798,583500,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',14,'@IsSummary@=N','U','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','N','N','N','N','N','Standort',170,170,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.388Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583500 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.391Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2019-09-05T14:42:38.398Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583500
;

-- 2019-09-05T14:42:38.398Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583500)
;

-- 2019-09-05T14:42:38.400Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583500
;

-- 2019-09-05T14:42:38.401Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583500, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582288
;

-- 2019-09-05T14:42:38.456Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5797,583501,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'@IsSummary@=N','U','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','Y','Ansprechpartner',180,180,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.457Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583501 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.459Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2019-09-05T14:42:38.463Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583501
;

-- 2019-09-05T14:42:38.464Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583501)
;

-- 2019-09-05T14:42:38.466Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583501
;

-- 2019-09-05T14:42:38.466Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583501, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582289
;

-- 2019-09-05T14:42:38.521Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5796,583502,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',14,'@IsSummary@=N','U','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.',0,'Y','Y','Y','N','N','N','N','N','Zahlungsbedingung',190,190,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.523Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583502 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.526Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(204) 
;

-- 2019-09-05T14:42:38.543Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583502
;

-- 2019-09-05T14:42:38.543Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583502)
;

-- 2019-09-05T14:42:38.546Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583502
;

-- 2019-09-05T14:42:38.546Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583502, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582290
;

-- 2019-09-05T14:42:38.599Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5794,583503,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Referenz-Nummer des Kunden',20,'@IsSummary@=N','U','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','Y','Y','N','N','N','N','Y','Referenz',200,200,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.600Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583503 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.603Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2019-09-05T14:42:38.622Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583503
;

-- 2019-09-05T14:42:38.623Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583503)
;

-- 2019-09-05T14:42:38.625Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583503
;

-- 2019-09-05T14:42:38.625Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583503, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582291
;

-- 2019-09-05T14:42:38.692Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9637,583504,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',14,'@IsSummary@=N','U','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','Y','Y','N','N','N','N','N','Lager',210,210,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.693Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583504 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.696Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2019-09-05T14:42:38.715Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583504
;

-- 2019-09-05T14:42:38.715Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583504)
;

-- 2019-09-05T14:42:38.718Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583504
;

-- 2019-09-05T14:42:38.718Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583504, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582292
;

-- 2019-09-05T14:42:38.791Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5795,583505,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',14,'@IsSummary@=N & @$Element_MC@=Y','U','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','Y','Y','N','N','N','N','Y','Werbemassnahme',220,220,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.792Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583505 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.794Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550) 
;

-- 2019-09-05T14:42:38.813Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583505
;

-- 2019-09-05T14:42:38.813Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583505)
;

-- 2019-09-05T14:42:38.820Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583505
;

-- 2019-09-05T14:42:38.821Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583505, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582293
;

-- 2019-09-05T14:42:38.882Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5753,583506,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine einzelne Version der Preisliste',14,'@IsSummary@=N','U','Jede Preisliste kann verschiedene Versionen haben. Die übliche Verwendung ist zur Anzeige eines zeitlichen Gültigkeitsbereiches einer Preisliste. ',0,'Y','Y','Y','N','N','N','N','N','Version Preisliste',230,230,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.883Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583506 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.885Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(450) 
;

-- 2019-09-05T14:42:38.888Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583506
;

-- 2019-09-05T14:42:38.888Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583506)
;

-- 2019-09-05T14:42:38.890Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583506
;

-- 2019-09-05T14:42:38.890Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583506, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582294
;

-- 2019-09-05T14:42:38.944Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3901,583507,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',14,'@IsSummary@=N','U','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','Y','N','N','N','N','Y','Währung',240,240,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:38.945Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583507 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:38.947Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2019-09-05T14:42:38.962Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583507
;

-- 2019-09-05T14:42:38.963Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583507)
;

-- 2019-09-05T14:42:38.964Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583507
;

-- 2019-09-05T14:42:38.964Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583507, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582295
;

-- 2019-09-05T14:42:39.020Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5755,103,583508,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100,'VK Total',26,'@IsSummary@=N','U','The Planned Amount indicates the anticipated amount for this project or project line.',0,'Y','Y','Y','N','N','N','N','N','VK Total',250,250,1,1,TO_TIMESTAMP('2019-09-05 16:42:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.021Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583508 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.022Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1564) 
;

-- 2019-09-05T14:42:39.023Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583508
;

-- 2019-09-05T14:42:39.024Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583508)
;

-- 2019-09-05T14:42:39.025Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583508
;

-- 2019-09-05T14:42:39.025Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583508, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582296
;

-- 2019-09-05T14:42:39.082Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5756,103,583509,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'',26,'@IsSummary@=N','U','',0,'Y','Y','Y','N','N','N','N','Y','Geplante Menge',260,260,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.083Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583509 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.085Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1568) 
;

-- 2019-09-05T14:42:39.086Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583509
;

-- 2019-09-05T14:42:39.087Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583509)
;

-- 2019-09-05T14:42:39.088Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583509
;

-- 2019-09-05T14:42:39.089Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583509, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582297
;

-- 2019-09-05T14:42:39.153Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5757,583510,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'Project''s planned margin amount',26,'@IsSummary@=N','U','The Planned Margin Amount indicates the anticipated margin amount for this project or project line.',0,'Y','Y','Y','N','N','N','N','N','DB1',270,270,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.154Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583510 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.157Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1566) 
;

-- 2019-09-05T14:42:39.163Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583510
;

-- 2019-09-05T14:42:39.164Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583510)
;

-- 2019-09-05T14:42:39.166Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583510
;

-- 2019-09-05T14:42:39.166Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583510, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582298
;

-- 2019-09-05T14:42:39.220Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,15449,583511,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Rule for the project',1,'@IsSummary@=N','U','The Invoice Rule for the project determines how orders (and consequently invoices) are created.  The selection on project level can be overwritten on Phase or Task',0,'Y','Y','Y','N','N','N','N','Y','Rechnungsstellung',280,280,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.221Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583511 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.223Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3031) 
;

-- 2019-09-05T14:42:39.225Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583511
;

-- 2019-09-05T14:42:39.226Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583511)
;

-- 2019-09-05T14:42:39.228Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583511
;

-- 2019-09-05T14:42:39.229Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583511, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582299
;

-- 2019-09-05T14:42:39.282Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3907,583512,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'The (legal) commitment amount',26,'@IsSummary@=N','U','The commitment amount is independent from the planned amount. You would use the planned amount for your realistic estimation, which might be higher or lower than the commitment amount.',0,'Y','Y','Y','N','N','N','N','N','Committed Amount',290,290,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.284Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583512 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.286Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1081) 
;

-- 2019-09-05T14:42:39.290Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583512
;

-- 2019-09-05T14:42:39.291Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583512)
;

-- 2019-09-05T14:42:39.293Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583512
;

-- 2019-09-05T14:42:39.293Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583512, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582300
;

-- 2019-09-05T14:42:39.340Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8759,583513,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'The (legal) commitment Quantity',26,'@IsSummary@=N','U','The commitment amount is independent from the planned amount. You would use the planned amount for your realistic estimation, which might be higher or lower than the commitment amount.',0,'Y','Y','Y','N','N','N','N','Y','Committed Quantity',300,300,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.341Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583513 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.344Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2036) 
;

-- 2019-09-05T14:42:39.347Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583513
;

-- 2019-09-05T14:42:39.347Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583513)
;

-- 2019-09-05T14:42:39.351Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583513
;

-- 2019-09-05T14:42:39.351Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583513, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582301
;

-- 2019-09-05T14:42:39.405Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8753,105,583514,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'The amount invoiced',26,'@IsSummary@=N','U','The amount invoiced',0,'Y','Y','Y','N','N','N','Y','N','Invoiced Amount',310,310,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.406Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583514 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.409Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2044) 
;

-- 2019-09-05T14:42:39.411Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583514
;

-- 2019-09-05T14:42:39.411Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583514)
;

-- 2019-09-05T14:42:39.413Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583514
;

-- 2019-09-05T14:42:39.414Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583514, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582302
;

-- 2019-09-05T14:42:39.464Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8756,583515,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'The quantity invoiced',26,'@IsSummary@=N','U',0,'Y','Y','Y','N','N','N','Y','Y','Berechnete Menge',320,320,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.465Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583515 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.468Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2045) 
;

-- 2019-09-05T14:42:39.470Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583515
;

-- 2019-09-05T14:42:39.470Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583515)
;

-- 2019-09-05T14:42:39.472Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583515
;

-- 2019-09-05T14:42:39.473Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583515, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582303
;

-- 2019-09-05T14:42:39.529Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8758,583516,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'Total Project Balance',26,'@IsSummary@=N','U','The project balance is the sum of all invoices and payments',0,'Y','Y','Y','N','N','N','Y','N','Project Balance',330,330,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.529Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583516 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.531Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2054) 
;

-- 2019-09-05T14:42:39.532Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583516
;

-- 2019-09-05T14:42:39.533Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583516)
;

-- 2019-09-05T14:42:39.534Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583516
;

-- 2019-09-05T14:42:39.535Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583516, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582304
;

-- 2019-09-05T14:42:39.588Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8754,583517,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'Copy From Record',23,'@IsSummary@=N','U','Copy From Record',0,'Y','Y','Y','N','N','N','N','N','Copy From',340,340,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.589Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583517 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.592Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2037) 
;

-- 2019-09-05T14:42:39.593Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583517
;

-- 2019-09-05T14:42:39.593Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583517)
;

-- 2019-09-05T14:42:39.595Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583517
;

-- 2019-09-05T14:42:39.595Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583517, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582305
;

-- 2019-09-05T14:42:39.648Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9861,583518,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,23,'@IsSummary@=N','U',0,'Y','Y','Y','N','N','N','N','N','Process Now',350,350,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.649Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583518 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.652Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2019-09-05T14:42:39.695Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583518
;

-- 2019-09-05T14:42:39.696Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583518)
;

-- 2019-09-05T14:42:39.698Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583518
;

-- 2019-09-05T14:42:39.698Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583518, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582306
;

-- 2019-09-05T14:42:39.763Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559693,583519,0,541865,0,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','N','N','N','N','N','N','Projektstatus',360,1,1,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.765Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583519 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:39.767Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544077) 
;

-- 2019-09-05T14:42:39.769Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583519
;

-- 2019-09-05T14:42:39.769Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583519)
;

-- 2019-09-05T14:42:39.772Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583519
;

-- 2019-09-05T14:42:39.772Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583519, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582478
;

-- 2019-09-05T14:42:39.837Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541865,541419,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'1000028')
;

-- 2019-09-05T14:42:39.845Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541419 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-09-05T14:42:39.849Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541419
;

-- 2019-09-05T14:42:39.850Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541419, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541389
;

-- 2019-09-05T14:42:39.911Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541819,541419,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:39.983Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541819,542751,TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,'primary',TO_TIMESTAMP('2019-09-05 16:42:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.077Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583494,0,541865,542751,560699,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektart',5,0,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.150Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583486,0,541865,542751,560700,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Projektnummer',10,70,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.213Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583488,0,541865,542751,560701,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Name',20,50,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.321Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583519,0,541865,542751,560702,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektstatus',25,0,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.370Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541819,542752,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.438Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583489,0,541865,542752,560703,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Beschreibung',10,30,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.495Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541820,541419,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.562Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541820,542753,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','active',5,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.620Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583490,0,541865,542753,560704,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Aktiv',10,10,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.671Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541820,542754,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','partner',10,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.729Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583498,0,541865,542754,560705,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Kunde',10,40,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.775Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583487,0,541865,542754,560706,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Aussendienst',30,20,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.830Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583496,0,541865,542754,560707,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Datum AE',40,0,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.889Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583497,0,541865,542754,560708,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektabschluss',50,0,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:40.941Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541820,542755,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.004Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583484,0,541865,542755,560709,'F',TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2019-09-05 16:42:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.067Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583485,0,541865,542755,560710,'F',TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.111Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,5799,572582,0,541866,417,540680,'Y',TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Vorgänge',1349,'N',60,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.112Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541866 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-05T14:42:41.114Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572582) 
;

-- 2019-09-05T14:42:41.118Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541866)
;

-- 2019-09-05T14:42:41.119Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541866
;

-- 2019-09-05T14:42:41.120Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541866, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 541831
;

-- 2019-09-05T14:42:41.160Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8774,583520,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'This is a Self-Service entry or this entry can be changed via Self-Service',1,'U','Self-Service allows users to enter data or update their data.  The flag indicates, that this record was entered or created via Self-Service or that the user can change it via the Self-Service functionality.',0,'Y','N','N','N','N','N','Y','Y','Self-Service',10,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.160Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583520 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.162Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2063) 
;

-- 2019-09-05T14:42:41.174Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583520
;

-- 2019-09-05T14:42:41.174Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583520)
;

-- 2019-09-05T14:42:41.180Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583520
;

-- 2019-09-05T14:42:41.180Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583520, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582307
;

-- 2019-09-05T14:42:41.219Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5441,114,583521,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'Text templates for mailings',14,'U','The Mail Template indicates the mail template for return messages. Mail text can include variables.  The priority of parsing is User/Contact, Business Partner and then the underlying business object (like Request, Dunning, Workflow object).<br>
So, @Name@ would resolve into the User name (if user is defined defined), then Business Partner name (if business partner is defined) and then the Name of the business object if it has a Name.<br>
For Multi-Lingual systems, the template is translated based on the Business Partner''s language selection.',0,'Y','N','N','N','N','N','N','Y','Mail Template',20,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.220Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583521 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.221Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1515) 
;

-- 2019-09-05T14:42:41.233Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583521
;

-- 2019-09-05T14:42:41.233Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583521)
;

-- 2019-09-05T14:42:41.234Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583521
;

-- 2019-09-05T14:42:41.234Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583521, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582308
;

-- 2019-09-05T14:42:41.279Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13078,583522,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',14,'@AD_Table_ID@!0','U','The Database Table provides the information of the table definition',0,'Y','N','N','N','N','N','Y','N','DB-Tabelle',30,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.280Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583522 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.281Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2019-09-05T14:42:41.298Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583522
;

-- 2019-09-05T14:42:41.298Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583522)
;

-- 2019-09-05T14:42:41.300Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583522
;

-- 2019-09-05T14:42:41.300Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583522, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582309
;

-- 2019-09-05T14:42:41.365Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13490,583523,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'Related Request (Master Issue, ..)',14,'U','Request related to this request',0,'Y','N','N','N','N','N','N','Y','Related Request',40,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.366Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583523 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.369Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2710) 
;

-- 2019-09-05T14:42:41.373Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583523
;

-- 2019-09-05T14:42:41.373Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583523)
;

-- 2019-09-05T14:42:41.375Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583523
;

-- 2019-09-05T14:42:41.376Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583523, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582310
;

-- 2019-09-05T14:42:41.427Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13488,114,583524,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'Responsibility Role',14,'U','The Role determines security and access a user who has this Role will have in the System.',0,'Y','N','N','N','N','N','N','Y','Rolle',50,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.428Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583524 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.430Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(123) 
;

-- 2019-09-05T14:42:41.443Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583524
;

-- 2019-09-05T14:42:41.444Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583524)
;

-- 2019-09-05T14:42:41.446Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583524
;

-- 2019-09-05T14:42:41.446Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583524, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582311
;

-- 2019-09-05T14:42:41.509Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13575,583525,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)',20,'U','The Start Date indicates the first or starting date',0,'Y','N','N','N','N','N','N','N','Anfangsdatum',60,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.510Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583525 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.513Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(574) 
;

-- 2019-09-05T14:42:41.521Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583525
;

-- 2019-09-05T14:42:41.521Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583525)
;

-- 2019-09-05T14:42:41.524Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583525
;

-- 2019-09-05T14:42:41.525Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583525, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582312
;

-- 2019-09-05T14:42:41.577Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13496,114,583526,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'Menge, die bereits in Rechnung gestellt wurde',26,'@IsInvoiced@=Y','U',0,'Y','N','N','N','N','N','N','Y','Berechn. Menge',70,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.578Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583526 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.581Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(529) 
;

-- 2019-09-05T14:42:41.590Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583526
;

-- 2019-09-05T14:42:41.590Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583526)
;

-- 2019-09-05T14:42:41.594Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583526
;

-- 2019-09-05T14:42:41.594Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583526, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582313
;

-- 2019-09-05T14:42:41.653Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13494,114,583527,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'End of the time span',20,'U',0,'Y','N','N','N','N','N','N','Y','Enddatum',80,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.655Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583527 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.657Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2714) 
;

-- 2019-09-05T14:42:41.662Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583527
;

-- 2019-09-05T14:42:41.662Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583527)
;

-- 2019-09-05T14:42:41.668Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583527
;

-- 2019-09-05T14:42:41.669Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583527, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582314
;

-- 2019-09-05T14:42:41.718Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13493,114,583528,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'',20,'U',0,'Y','N','N','N','N','N','N','N','Startdatum',90,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.719Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583528 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.721Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2713) 
;

-- 2019-09-05T14:42:41.724Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583528
;

-- 2019-09-05T14:42:41.724Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583528)
;

-- 2019-09-05T14:42:41.726Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583528
;

-- 2019-09-05T14:42:41.726Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583528, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582315
;

-- 2019-09-05T14:42:41.768Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13495,114,583529,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'Quantity used for this event',26,'U',0,'Y','N','N','N','N','N','N','Y','Quantity Used',100,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.768Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583529 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.770Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2715) 
;

-- 2019-09-05T14:42:41.772Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583529
;

-- 2019-09-05T14:42:41.773Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583529)
;

-- 2019-09-05T14:42:41.774Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583529
;

-- 2019-09-05T14:42:41.775Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583529, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582316
;

-- 2019-09-05T14:42:41.832Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5418,101,583530,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',110,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:41.832Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583530 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:41.834Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-05T14:42:41.977Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583530
;

-- 2019-09-05T14:42:41.977Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583530)
;

-- 2019-09-05T14:42:41.979Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583530
;

-- 2019-09-05T14:42:41.979Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583530, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582317
;

-- 2019-09-05T14:42:42.056Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12927,105,583531,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100,'Date when last alert were sent',14,'U','The last alert date is updated when a reminder email is sent',0,'Y','N','N','N','N','N','Y','Y','Last Alert',120,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.057Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583531 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.059Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2629) 
;

-- 2019-09-05T14:42:42.067Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583531
;

-- 2019-09-05T14:42:42.068Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583531)
;

-- 2019-09-05T14:42:42.069Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583531
;

-- 2019-09-05T14:42:42.070Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583531, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582318
;

-- 2019-09-05T14:42:42.120Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555043,114,583532,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','N','N','N','N','N','Y','N','Request Type Interner Name',130,0,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.121Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583532 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.123Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543175) 
;

-- 2019-09-05T14:42:42.125Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583532
;

-- 2019-09-05T14:42:42.125Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583532)
;

-- 2019-09-05T14:42:42.128Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583532
;

-- 2019-09-05T14:42:42.128Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583532, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582319
;

-- 2019-09-05T14:42:42.180Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13497,114,583533,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Product/Resource/Service used in Request',26,'U','Invoicing uses the Product used.',0,'Y','N','N','N','N','N','N','N','Product Used',140,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.181Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583533 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.183Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2716) 
;

-- 2019-09-05T14:42:42.201Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583533
;

-- 2019-09-05T14:42:42.201Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583533)
;

-- 2019-09-05T14:42:42.204Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583533
;

-- 2019-09-05T14:42:42.204Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583533, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582320
;

-- 2019-09-05T14:42:42.268Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13489,583534,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Is this invoiced?',1,'U','If selected, invoices are created',0,'Y','N','N','N','N','N','N','Y','Invoiced',150,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.269Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583534 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.271Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(387) 
;

-- 2019-09-05T14:42:42.296Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583534
;

-- 2019-09-05T14:42:42.297Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583534)
;

-- 2019-09-05T14:42:42.299Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583534
;

-- 2019-09-05T14:42:42.300Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583534, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582321
;

-- 2019-09-05T14:42:42.368Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13499,583535,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'The generated invoice for this request',14,'U','The optionally generated invoice for the request',0,'Y','N','N','N','N','N','Y','Y','Request Invoice',160,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.369Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583535 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.372Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2717) 
;

-- 2019-09-05T14:42:42.375Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583535
;

-- 2019-09-05T14:42:42.375Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583535)
;

-- 2019-09-05T14:42:42.378Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583535
;

-- 2019-09-05T14:42:42.378Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583535, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582322
;

-- 2019-09-05T14:42:42.438Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13576,583536,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Close Date',20,'U','The Start Date indicates the last or final date',0,'Y','N','N','N','N','N','N','N','Close Date',170,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.440Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583536 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.443Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2721) 
;

-- 2019-09-05T14:42:42.457Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583536
;

-- 2019-09-05T14:42:42.457Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583536)
;

-- 2019-09-05T14:42:42.460Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583536
;

-- 2019-09-05T14:42:42.460Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583536, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582323
;

-- 2019-09-05T14:42:42.536Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13487,583537,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Type of Confidentiality',14,'U',0,'Y','N','N','N','N','N','N','N','Confidentiality',180,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.537Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583537 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.540Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2709) 
;

-- 2019-09-05T14:42:42.556Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583537
;

-- 2019-09-05T14:42:42.557Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583537)
;

-- 2019-09-05T14:42:42.560Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583537
;

-- 2019-09-05T14:42:42.560Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583537, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582324
;

-- 2019-09-05T14:42:42.621Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5425,104,583538,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Amount associated with this request',26,'U','The Request Amount indicates any amount that is associated with this request.  For example, a warranty amount or refund amount.',0,'Y','N','N','N','N','N','N','N','Request Amount',190,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.623Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583538 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.626Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1520) 
;

-- 2019-09-05T14:42:42.653Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583538
;

-- 2019-09-05T14:42:42.653Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583538)
;

-- 2019-09-05T14:42:42.656Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583538
;

-- 2019-09-05T14:42:42.657Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583538, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582325
;

-- 2019-09-05T14:42:42.726Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13491,114,583539,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Confidentiality of the individual entry',14,'U',0,'Y','N','N','N','N','N','N','Y','Entry Confidentiality',200,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.727Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583539 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.729Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2711) 
;

-- 2019-09-05T14:42:42.740Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583539
;

-- 2019-09-05T14:42:42.741Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583539)
;

-- 2019-09-05T14:42:42.743Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583539
;

-- 2019-09-05T14:42:42.744Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583539, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582326
;

-- 2019-09-05T14:42:42.804Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5429,583540,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'This request has been escalated',1,'U','The Escalated checkbox indicates that this request has been escalated or raised in importance.',0,'Y','N','N','N','N','N','Y','N','Escalated',210,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.805Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583540 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.814Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1509) 
;

-- 2019-09-05T14:42:42.821Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583540
;

-- 2019-09-05T14:42:42.822Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583540)
;

-- 2019-09-05T14:42:42.824Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583540
;

-- 2019-09-05T14:42:42.825Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583540, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582327
;

-- 2019-09-05T14:42:42.882Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10580,583541,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Asset used internally or by customers',26,'U','An asset is either created by purchasing or by delivering a product.  An asset can be used internally or be a customer asset.',0,'Y','N','N','N','N','N','N','Y','Asset',220,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.883Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583541 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.886Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1884) 
;

-- 2019-09-05T14:42:42.903Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583541
;

-- 2019-09-05T14:42:42.904Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583541)
;

-- 2019-09-05T14:42:42.906Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583541
;

-- 2019-09-05T14:42:42.907Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583541, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582328
;

-- 2019-09-05T14:42:42.970Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13483,583542,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Request Category',14,'U','Category or Topic of the Request ',0,'Y','N','N','N','N','N','N','N','Category',230,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:42.971Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583542 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:42.974Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2705) 
;

-- 2019-09-05T14:42:42.984Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583542
;

-- 2019-09-05T14:42:42.985Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583542)
;

-- 2019-09-05T14:42:42.987Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583542
;

-- 2019-09-05T14:42:42.988Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583542, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582329
;

-- 2019-09-05T14:42:43.046Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5416,583543,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,10,1,1,TO_TIMESTAMP('2019-09-05 16:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:43.047Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583543 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:43.050Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-05T14:42:43.310Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583543
;

-- 2019-09-05T14:42:43.310Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583543)
;

-- 2019-09-05T14:42:43.312Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583543
;

-- 2019-09-05T14:42:43.312Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583543, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582330
;

-- 2019-09-05T14:42:43.388Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5417,583544,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:43.389Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583544 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:43.391Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-05T14:42:43.545Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583544
;

-- 2019-09-05T14:42:43.545Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583544)
;

-- 2019-09-05T14:42:43.546Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583544
;

-- 2019-09-05T14:42:43.547Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583544, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582331
;

-- 2019-09-05T14:42:43.699Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7791,583545,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100,'','Type of request (e.g. Inquiry, Complaint, ..)',14,'U','Request Types are used for processing and categorizing requests. Options are Account Inquiry, Warranty Issue, etc.',0,'Y','Y','Y','N','N','N','N','N','Vorgangsart',25,25,1,1,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:43.700Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583545 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:43.703Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1894) 
;

-- 2019-09-05T14:42:43.713Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583545
;

-- 2019-09-05T14:42:43.714Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583545)
;

-- 2019-09-05T14:42:43.716Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583545
;

-- 2019-09-05T14:42:43.716Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583545, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582332
;

-- 2019-09-05T14:42:43.775Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5433,583546,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',26,'U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',30,30,1,1,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:43.776Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583546 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:43.779Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2019-09-05T14:42:43.784Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583546
;

-- 2019-09-05T14:42:43.784Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583546)
;

-- 2019-09-05T14:42:43.786Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583546
;

-- 2019-09-05T14:42:43.787Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583546, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582333
;

-- 2019-09-05T14:42:43.852Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5423,583547,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',20,'U','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','Y','Nr.',40,40,3,1,1,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:43.852Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583547 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:43.854Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2019-09-05T14:42:43.856Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583547
;

-- 2019-09-05T14:42:43.856Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583547)
;

-- 2019-09-05T14:42:43.858Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583547
;

-- 2019-09-05T14:42:43.858Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583547, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582334
;

-- 2019-09-05T14:42:43.923Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5434,583548,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',14,'U','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','Y','Ansprechpartner',50,50,1,1,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:43.923Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583548 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:43.925Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2019-09-05T14:42:43.928Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583548
;

-- 2019-09-05T14:42:43.928Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583548)
;

-- 2019-09-05T14:42:43.929Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583548
;

-- 2019-09-05T14:42:43.929Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583548, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582335
;

-- 2019-09-05T14:42:43.983Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556050,583549,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Zulieferant',55,55,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:43.984Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583549 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:43.986Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543273) 
;

-- 2019-09-05T14:42:43.988Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583549
;

-- 2019-09-05T14:42:43.988Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583549)
;

-- 2019-09-05T14:42:43.990Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583549
;

-- 2019-09-05T14:42:43.990Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583549, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582336
;

-- 2019-09-05T14:42:44.045Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5799,583550,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','Project',60,60,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.047Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583550 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.050Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2019-09-05T14:42:44.070Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583550
;

-- 2019-09-05T14:42:44.070Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583550)
;

-- 2019-09-05T14:42:44.073Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583550
;

-- 2019-09-05T14:42:44.073Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583550, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582337
;

-- 2019-09-05T14:42:44.134Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5436,583551,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag',26,'U','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.',0,'Y','Y','Y','N','N','N','N','N','Auftrag',70,70,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.134Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583551 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.136Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2019-09-05T14:42:44.151Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583551
;

-- 2019-09-05T14:42:44.151Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583551)
;

-- 2019-09-05T14:42:44.153Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583551
;

-- 2019-09-05T14:42:44.153Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583551, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582338
;

-- 2019-09-05T14:42:44.212Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555039,583552,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Ware geliefert wurde',0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','Lieferdatum',80,80,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.213Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583552 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.216Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(264) 
;

-- 2019-09-05T14:42:44.219Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583552
;

-- 2019-09-05T14:42:44.220Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583552)
;

-- 2019-09-05T14:42:44.222Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583552
;

-- 2019-09-05T14:42:44.222Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583552, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582339
;

-- 2019-09-05T14:42:44.279Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5437,583553,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','The Invoice Document.',0,'Y','Y','Y','N','N','N','N','Y','Rechnung',90,90,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.280Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583553 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.283Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008) 
;

-- 2019-09-05T14:42:44.301Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583553
;

-- 2019-09-05T14:42:44.302Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583553)
;

-- 2019-09-05T14:42:44.304Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583553
;

-- 2019-09-05T14:42:44.305Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583553, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582340
;

-- 2019-09-05T14:42:44.374Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5439,583554,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',26,'U','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',100,100,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.375Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583554 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.378Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2019-09-05T14:42:44.432Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583554
;

-- 2019-09-05T14:42:44.433Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583554)
;

-- 2019-09-05T14:42:44.441Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583554
;

-- 2019-09-05T14:42:44.441Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583554, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582341
;

-- 2019-09-05T14:42:44.509Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5438,583555,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Zahlung',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).',0,'Y','Y','Y','N','N','N','N','Y','Zahlung',110,110,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.511Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583555 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.514Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1384) 
;

-- 2019-09-05T14:42:44.525Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583555
;

-- 2019-09-05T14:42:44.526Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583555)
;

-- 2019-09-05T14:42:44.528Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583555
;

-- 2019-09-05T14:42:44.529Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583555, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582342
;

-- 2019-09-05T14:42:44.590Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5435,583556,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Marketing Campaign',14,'@$Element_MC@=Y','U','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','Y','Y','N','N','N','N','Y','Werbemassnahme',115,115,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.591Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583556 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.594Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550) 
;

-- 2019-09-05T14:42:44.611Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583556
;

-- 2019-09-05T14:42:44.612Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583556)
;

-- 2019-09-05T14:42:44.614Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583556
;

-- 2019-09-05T14:42:44.615Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583556, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582343
;

-- 2019-09-05T14:42:44.662Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13573,583557,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document',26,'U','The Material Shipment / Receipt ',0,'Y','Y','Y','N','N','N','N','N','Lieferung/Wareneingang',120,120,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.664Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583557 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.667Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1025) 
;

-- 2019-09-05T14:42:44.670Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583557
;

-- 2019-09-05T14:42:44.671Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583557)
;

-- 2019-09-05T14:42:44.673Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583557
;

-- 2019-09-05T14:42:44.674Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583557, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582344
;

-- 2019-09-05T14:42:44.725Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13574,583558,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Return Material Authorization',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','A Return Material Authorization may be required to accept returns and to create Credit Memos',0,'Y','Y','Y','N','N','N','N','Y','RMA',130,130,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.727Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583558 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.730Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2412) 
;

-- 2019-09-05T14:42:44.742Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583558
;

-- 2019-09-05T14:42:44.742Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583558)
;

-- 2019-09-05T14:42:44.745Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583558
;

-- 2019-09-05T14:42:44.767Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583558, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582345
;

-- 2019-09-05T14:42:44.832Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555040,583559,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','Rücklieferung',140,140,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.833Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583559 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.836Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543174) 
;

-- 2019-09-05T14:42:44.838Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583559
;

-- 2019-09-05T14:42:44.838Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583559)
;

-- 2019-09-05T14:42:44.841Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583559
;

-- 2019-09-05T14:42:44.841Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583559, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582346
;

-- 2019-09-05T14:42:44.905Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13079,583560,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',23,'@AD_Table_ID@!0','U','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','Y','Y','N','N','N','Y','Y','Datensatz-ID',150,150,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.907Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583560 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.910Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2019-09-05T14:42:44.934Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583560
;

-- 2019-09-05T14:42:44.934Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583560)
;

-- 2019-09-05T14:42:44.937Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583560
;

-- 2019-09-05T14:42:44.937Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583560, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582347
;

-- 2019-09-05T14:42:44.991Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13498,114,583561,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Erfassung der zugehörigen Kostenstelle',0,'Y','Y','Y','N','N','N','N','N','Kostenstelle',160,160,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:44.992Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583561 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:44.995Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2019-09-05T14:42:45.011Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583561
;

-- 2019-09-05T14:42:45.012Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583561)
;

-- 2019-09-05T14:42:45.014Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583561
;

-- 2019-09-05T14:42:45.015Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583561, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582348
;

-- 2019-09-05T14:42:45.077Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5445,114,583562,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Date that this request should be acted on',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','The Date Next Action indicates the next scheduled date for an action to occur for this request.',0,'Y','Y','Y','N','N','N','N','N','Date next action',180,180,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.079Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583562 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.081Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1503) 
;

-- 2019-09-05T14:42:45.085Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583562
;

-- 2019-09-05T14:42:45.085Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583562)
;

-- 2019-09-05T14:42:45.088Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583562
;

-- 2019-09-05T14:42:45.088Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583562, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582349
;

-- 2019-09-05T14:42:45.151Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5427,114,583563,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Status of the next action for this Request',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','The Due Type indicates if this request is Due, Overdue or Scheduled.',0,'Y','Y','Y','N','N','N','N','Y','Due type',190,190,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.153Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583563 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.156Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1504) 
;

-- 2019-09-05T14:42:45.158Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583563
;

-- 2019-09-05T14:42:45.159Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583563)
;

-- 2019-09-05T14:42:45.161Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583563
;

-- 2019-09-05T14:42:45.162Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583563, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582350
;

-- 2019-09-05T14:42:45.220Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13482,114,583564,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Request Group',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Group of requests (e.g. version numbers, responsibility, ...)',0,'Y','Y','Y','N','N','N','N','Y','Group',220,220,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.221Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583564 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.224Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2704) 
;

-- 2019-09-05T14:42:45.228Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583564
;

-- 2019-09-05T14:42:45.229Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583564)
;

-- 2019-09-05T14:42:45.231Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583564
;

-- 2019-09-05T14:42:45.232Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583564, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582351
;

-- 2019-09-05T14:42:45.285Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13485,114,583565,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Request Resolution',14,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U','Resolution status (e.g. Fixed, Rejected, ..)',0,'Y','Y','Y','N','N','N','N','N','Resolution',240,240,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.287Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583565 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.289Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2707) 
;

-- 2019-09-05T14:42:45.292Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583565
;

-- 2019-09-05T14:42:45.292Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583565)
;

-- 2019-09-05T14:42:45.294Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583565
;

-- 2019-09-05T14:42:45.294Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583565, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582352
;

-- 2019-09-05T14:42:45.354Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5426,114,583566,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this request is of a high, medium or low priority.',14,'U','The Priority indicates the importance of this request.',0,'Y','Y','Y','N','N','N','N','N','Priority',260,260,2,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.355Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583566 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.357Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1514) 
;

-- 2019-09-05T14:42:45.361Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583566
;

-- 2019-09-05T14:42:45.361Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583566)
;

-- 2019-09-05T14:42:45.363Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583566
;

-- 2019-09-05T14:42:45.363Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583566, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582353
;

-- 2019-09-05T14:42:45.422Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555046,114,583567,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','PerformanceType',265,265,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.422Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583567 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.424Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543176) 
;

-- 2019-09-05T14:42:45.425Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583567
;

-- 2019-09-05T14:42:45.425Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583567)
;

-- 2019-09-05T14:42:45.427Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583567
;

-- 2019-09-05T14:42:45.428Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583567, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582354
;

-- 2019-09-05T14:42:45.480Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13486,114,583568,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Priority of the issue for the User',0,'@R_RequestType_InternalName/-1@ !''A_CustomerComplaint'' & @R_RequestType_InternalName/-1@ ! ''B_VendorComplaint'' & @R_RequestType_InternalName/-1@ ! ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','Y','User Importance',270,270,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.480Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583568 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.482Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2708) 
;

-- 2019-09-05T14:42:45.483Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583568
;

-- 2019-09-05T14:42:45.484Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583568)
;

-- 2019-09-05T14:42:45.485Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583568
;

-- 2019-09-05T14:42:45.485Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583568, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582355
;

-- 2019-09-05T14:42:45.521Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555041,114,583569,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,0,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U',0,'Y','Y','Y','N','N','N','N','N','Qualität-Notiz',275,275,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.521Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583569 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.523Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543213) 
;

-- 2019-09-05T14:42:45.524Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583569
;

-- 2019-09-05T14:42:45.524Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583569)
;

-- 2019-09-05T14:42:45.525Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583569
;

-- 2019-09-05T14:42:45.525Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583569, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582356
;

-- 2019-09-05T14:42:45.568Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5428,114,583570,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Textual summary of this request',60,'U','The Summary allows free form text entry of a recap of this request.',0,'Y','Y','Y','N','N','N','N','N','Summary',280,280,999,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.568Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583570 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.570Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1521) 
;

-- 2019-09-05T14:42:45.572Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583570
;

-- 2019-09-05T14:42:45.572Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583570)
;

-- 2019-09-05T14:42:45.573Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583570
;

-- 2019-09-05T14:42:45.573Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583570, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582357
;

-- 2019-09-05T14:42:45.632Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5444,114,583571,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Next Action to be taken',14,'@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint''','U','The Next Action indicates the next action to be taken on this request.',0,'Y','Y','Y','N','N','N','N','N','Next action',285,285,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.633Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583571 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.635Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1513) 
;

-- 2019-09-05T14:42:45.636Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583571
;

-- 2019-09-05T14:42:45.636Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583571)
;

-- 2019-09-05T14:42:45.637Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583571
;

-- 2019-09-05T14:42:45.638Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583571, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582358
;

-- 2019-09-05T14:42:45.709Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5432,114,583572,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'',14,'U','',0,'Y','Y','Y','N','N','N','Y','N','N','Verkaufsmitarbeiter',290,290,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.709Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583572 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.711Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063) 
;

-- 2019-09-05T14:42:45.718Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583572
;

-- 2019-09-05T14:42:45.718Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583572)
;

-- 2019-09-05T14:42:45.720Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583572
;

-- 2019-09-05T14:42:45.720Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583572, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582359
;

-- 2019-09-05T14:42:45.775Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13492,114,583573,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Request Standard Response ',14,'U','Text blocks to be copied into request response text',0,'Y','Y','Y','N','N','N','N','N','Standard Response',300,300,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.776Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583573 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.778Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2712) 
;

-- 2019-09-05T14:42:45.780Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583573
;

-- 2019-09-05T14:42:45.781Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583573)
;

-- 2019-09-05T14:42:45.783Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583573
;

-- 2019-09-05T14:42:45.783Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583573, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582360
;

-- 2019-09-05T14:42:45.828Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5443,114,583574,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Result of the action taken',60,'U','The Result indicates the result of any action taken on this request.',0,'Y','Y','Y','N','N','N','N','N','Ergebnis',310,310,999,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.829Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583574 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.832Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(546) 
;

-- 2019-09-05T14:42:45.840Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583574
;

-- 2019-09-05T14:42:45.841Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583574)
;

-- 2019-09-05T14:42:45.843Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583574
;

-- 2019-09-05T14:42:45.844Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583574, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582361
;

-- 2019-09-05T14:42:45.905Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13484,114,583575,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Request Status',14,'U','Status if the request (open, closed, investigating, ..)',0,'Y','Y','Y','N','N','N','N','N','Status',320,320,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.907Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583575 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.910Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2706) 
;

-- 2019-09-05T14:42:45.914Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583575
;

-- 2019-09-05T14:42:45.914Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583575)
;

-- 2019-09-05T14:42:45.917Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583575
;

-- 2019-09-05T14:42:45.917Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583575, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582362
;

-- 2019-09-05T14:42:45.982Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5430,105,583576,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Date this request was last acted on',20,'U','The Date Last Action indicates that last time that the request was acted on.',0,'Y','Y','Y','N','N','N','Y','N','Date last action',340,340,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:45.983Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583576 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:45.985Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1502) 
;

-- 2019-09-05T14:42:45.988Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583576
;

-- 2019-09-05T14:42:45.988Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583576)
;

-- 2019-09-05T14:42:45.990Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583576
;

-- 2019-09-05T14:42:45.990Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583576, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582363
;

-- 2019-09-05T14:42:46.048Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,Included_Tab_ID,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5415,105,583577,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100,'Request from a Business Partner or Prospect',14,'U','The Request identifies a unique request from a Business Partner or Prospect.',0,740,'Y','Y','Y','N','N','N','N','N','Aufgabe',350,350,1,1,TO_TIMESTAMP('2019-09-05 16:42:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:46.050Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583577 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:46.052Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1516) 
;

-- 2019-09-05T14:42:46.059Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583577
;

-- 2019-09-05T14:42:46.059Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583577)
;

-- 2019-09-05T14:42:46.061Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583577
;

-- 2019-09-05T14:42:46.062Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583577, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582364
;

-- 2019-09-05T14:42:46.114Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5431,105,583578,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'Result of last contact',60,'U','The Last Result identifies the result of the last contact made.',0,'Y','Y','Y','N','N','N','Y','Y','Last Result',355,355,999,1,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:46.115Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583578 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:46.117Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(431) 
;

-- 2019-09-05T14:42:46.118Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583578
;

-- 2019-09-05T14:42:46.119Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583578)
;

-- 2019-09-05T14:42:46.120Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583578
;

-- 2019-09-05T14:42:46.121Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583578, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582365
;

-- 2019-09-05T14:42:46.179Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5419,105,583579,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',20,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','Y','N','Erstellt',360,360,1,1,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:46.180Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583579 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:46.182Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-09-05T14:42:46.247Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583579
;

-- 2019-09-05T14:42:46.248Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583579)
;

-- 2019-09-05T14:42:46.249Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583579
;

-- 2019-09-05T14:42:46.250Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583579, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582366
;

-- 2019-09-05T14:42:46.307Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5420,105,583580,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',14,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','Y','Y','Erstellt durch',370,370,1,1,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:46.309Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583580 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:46.311Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-09-05T14:42:46.381Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583580
;

-- 2019-09-05T14:42:46.381Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583580)
;

-- 2019-09-05T14:42:46.384Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583580
;

-- 2019-09-05T14:42:46.384Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583580, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582367
;

-- 2019-09-05T14:42:46.434Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5421,105,583581,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',20,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','Y','N','N','N','Y','N','Aktualisiert',380,380,1,1,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:46.439Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583581 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:46.441Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-09-05T14:42:46.488Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583581
;

-- 2019-09-05T14:42:46.488Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583581)
;

-- 2019-09-05T14:42:46.490Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583581
;

-- 2019-09-05T14:42:46.490Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583581, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582368
;

-- 2019-09-05T14:42:46.549Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5422,105,583582,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',14,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','Y','N','N','N','Y','Y','Aktualisiert durch',390,390,1,1,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:46.550Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583582 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:46.552Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-09-05T14:42:46.619Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583582
;

-- 2019-09-05T14:42:46.619Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583582)
;

-- 2019-09-05T14:42:46.621Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583582
;

-- 2019-09-05T14:42:46.622Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583582, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582369
;

-- 2019-09-05T14:42:46.686Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5447,105,583583,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'1=2','U','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','Y','Y','Verarbeitet',400,400,1,1,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:46.688Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583583 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:46.691Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2019-09-05T14:42:46.696Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583583
;

-- 2019-09-05T14:42:46.697Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583583)
;

-- 2019-09-05T14:42:46.699Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583583
;

-- 2019-09-05T14:42:46.700Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583583, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582370
;

-- 2019-09-05T14:42:46.744Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557025,583584,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Wiedervorlage Datum',410,410,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:46.745Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583584 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:46.748Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543388) 
;

-- 2019-09-05T14:42:46.750Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583584
;

-- 2019-09-05T14:42:46.750Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583584)
;

-- 2019-09-05T14:42:46.753Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583584
;

-- 2019-09-05T14:42:46.753Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583584, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582371
;

-- 2019-09-05T14:42:46.826Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557045,583585,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum',0,'U','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.',0,'Y','Y','Y','N','N','N','N','N','Vorgangsdatum',420,420,-1,1,1,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:46.827Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583585 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:46.830Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1297) 
;

-- 2019-09-05T14:42:46.841Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583585
;

-- 2019-09-05T14:42:46.842Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583585)
;

-- 2019-09-05T14:42:46.844Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583585
;

-- 2019-09-05T14:42:46.844Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583585, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582372
;

-- 2019-09-05T14:42:46.895Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,540359,583586,0,541866,0,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,40,'U',0,'Y','N','N','N','N','N','N','N','Actual Phone',430,999,1,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:46.896Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583586 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:46.898Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540063) 
;

-- 2019-09-05T14:42:46.913Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583586
;

-- 2019-09-05T14:42:46.913Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583586)
;

-- 2019-09-05T14:42:46.915Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583586
;

-- 2019-09-05T14:42:46.916Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583586, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582373
;

-- 2019-09-05T14:42:46.968Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541866,541420,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'1000029')
;

-- 2019-09-05T14:42:46.969Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541420 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-09-05T14:42:46.972Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541420
;

-- 2019-09-05T14:42:46.973Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541420, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541390
;

-- 2019-09-05T14:42:47.024Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541821,541420,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-05 16:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.066Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541821,542756,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.118Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583585,0,541866,542756,560711,'F',TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Vorgangsdatum',10,40,0,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.180Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583547,0,541866,542756,560712,'F',TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Nr.',20,10,0,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.237Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583545,0,541866,542756,560713,'F',TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Request Type_',30,20,0,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.305Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583575,0,541866,542756,560714,'F',TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','status',40,50,0,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.373Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583570,0,541866,542756,560715,'F',TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Summary_',50,60,0,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.429Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583572,0,541866,542756,560716,'F',TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Aussendienst',60,30,0,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.489Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,559675,543957,0,541867,540961,540680,'Y',TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Projektkontakt',1349,'N',70,1,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.491Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541867 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-05T14:42:47.493Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(543957) 
;

-- 2019-09-05T14:42:47.495Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541867)
;

-- 2019-09-05T14:42:47.498Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541867
;

-- 2019-09-05T14:42:47.498Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541867, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 541832
;

-- 2019-09-05T14:42:47.556Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559664,583587,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',10,1,1,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.557Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583587 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:47.559Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-05T14:42:47.692Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583587
;

-- 2019-09-05T14:42:47.692Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583587)
;

-- 2019-09-05T14:42:47.696Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583587
;

-- 2019-09-05T14:42:47.696Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583587, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582374
;

-- 2019-09-05T14:42:47.851Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559665,583588,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','N','Sektion',20,1,1,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:47.852Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583588 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:47.853Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-05T14:42:47.997Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583588
;

-- 2019-09-05T14:42:47.997Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583588)
;

-- 2019-09-05T14:42:47.998Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583588
;

-- 2019-09-05T14:42:47.999Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583588, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582375
;

-- 2019-09-05T14:42:48.066Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559666,583589,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100,'-1','User within the system - Internal or Business Partner Contact',10,'U','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','N','Ansprechpartner',30,1,1,TO_TIMESTAMP('2019-09-05 16:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:48.067Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583589 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:48.069Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2019-09-05T14:42:48.072Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583589
;

-- 2019-09-05T14:42:48.072Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583589)
;

-- 2019-09-05T14:42:48.074Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583589
;

-- 2019-09-05T14:42:48.074Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583589, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582376
;

-- 2019-09-05T14:42:48.136Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559669,583590,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100,255,'U',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',40,1,1,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:48.137Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583590 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:48.139Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-09-05T14:42:48.203Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583590
;

-- 2019-09-05T14:42:48.204Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583590)
;

-- 2019-09-05T14:42:48.205Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583590
;

-- 2019-09-05T14:42:48.205Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583590, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582377
;

-- 2019-09-05T14:42:48.260Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559670,583591,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',50,1,1,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:48.261Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583591 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:48.262Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-05T14:42:48.487Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583591
;

-- 2019-09-05T14:42:48.487Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583591)
;

-- 2019-09-05T14:42:48.488Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583591
;

-- 2019-09-05T14:42:48.489Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583591, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582378
;

-- 2019-09-05T14:42:48.632Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559675,583592,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'U','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','Project',60,1,1,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:48.633Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583592 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:48.636Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2019-09-05T14:42:48.655Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583592
;

-- 2019-09-05T14:42:48.656Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583592)
;

-- 2019-09-05T14:42:48.658Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583592
;

-- 2019-09-05T14:42:48.658Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583592, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582379
;

-- 2019-09-05T14:42:48.704Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559677,583593,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','N','N','N','N','N','N','N','Projektkontakt',70,1,1,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:48.705Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583593 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:48.707Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543957) 
;

-- 2019-09-05T14:42:48.708Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583593
;

-- 2019-09-05T14:42:48.708Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583593)
;

-- 2019-09-05T14:42:48.710Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583593
;

-- 2019-09-05T14:42:48.710Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583593, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582380
;

-- 2019-09-05T14:42:48.757Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559668,583594,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',0,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','Erstellt durch',80,10,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:48.758Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583594 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:48.760Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-09-05T14:42:48.798Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583594
;

-- 2019-09-05T14:42:48.799Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583594)
;

-- 2019-09-05T14:42:48.801Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583594
;

-- 2019-09-05T14:42:48.801Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583594, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582386
;

-- 2019-09-05T14:42:48.851Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559667,583595,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',0,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','Erstellt',90,20,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:48.853Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583595 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:48.855Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-09-05T14:42:48.902Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583595
;

-- 2019-09-05T14:42:48.903Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583595)
;

-- 2019-09-05T14:42:48.905Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583595
;

-- 2019-09-05T14:42:48.905Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583595, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582387
;

-- 2019-09-05T14:42:48.962Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559672,583596,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',0,'U','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','Y','N','N','N','N','N','Aktualisiert',100,30,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:48.964Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583596 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:48.967Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2019-09-05T14:42:49.006Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583596
;

-- 2019-09-05T14:42:49.006Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583596)
;

-- 2019-09-05T14:42:49.009Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583596
;

-- 2019-09-05T14:42:49.009Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583596, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582388
;

-- 2019-09-05T14:42:49.056Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559673,583597,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',0,'U','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','Y','N','N','N','N','N','Aktualisiert durch',110,40,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.058Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583597 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:49.060Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2019-09-05T14:42:49.100Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583597
;

-- 2019-09-05T14:42:49.101Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583597)
;

-- 2019-09-05T14:42:49.103Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583597
;

-- 2019-09-05T14:42:49.103Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583597, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582389
;

-- 2019-09-05T14:42:49.163Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560808,583598,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,2,'U',0,'Y','Y','N','N','N','N','N','N','Projektrolle',120,1,1,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.164Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583598 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:49.167Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544216) 
;

-- 2019-09-05T14:42:49.168Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583598
;

-- 2019-09-05T14:42:49.169Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583598)
;

-- 2019-09-05T14:42:49.171Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583598
;

-- 2019-09-05T14:42:49.172Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583598, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582448
;

-- 2019-09-05T14:42:49.235Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568516,583599,0,541867,0,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID@','Bezeichnet einen Geschäftspartner',0,'U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner',120,50,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.237Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583599 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:49.240Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2019-09-05T14:42:49.246Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583599
;

-- 2019-09-05T14:42:49.246Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583599)
;

-- 2019-09-05T14:42:49.249Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583599
;

-- 2019-09-05T14:42:49.249Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583599, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582484
;

-- 2019-09-05T14:42:49.300Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541867,541421,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'1000030')
;

-- 2019-09-05T14:42:49.301Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541421 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-09-05T14:42:49.303Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541421
;

-- 2019-09-05T14:42:49.304Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541421, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541391
;

-- 2019-09-05T14:42:49.368Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541822,541421,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.424Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541822,542757,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','std',10,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.496Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583599,0,541867,542757,560717,'F',TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Geschäftspartner',5,10,0,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.545Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583589,0,541867,542757,560718,'F',TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Ansprechpartner',10,20,0,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.594Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583590,0,541867,542757,560719,'F',TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Beschreibung',20,30,0,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.642Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583598,0,541867,542757,560720,'F',TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Projektrolle',30,40,0,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.690Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,563235,572726,0,541868,541144,540680,'Y',TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','AD_AttachmentEntry_ReferencedRecord_v','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Dokumente',1349,'N',80,1,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.692Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541868 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-09-05T14:42:49.694Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572726) 
;

-- 2019-09-05T14:42:49.699Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541868)
;

-- 2019-09-05T14:42:49.700Z
-- URL zum Konzept
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 541868
;

-- 2019-09-05T14:42:49.701Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 541868, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 541838
;

-- 2019-09-05T14:42:49.773Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563223,583600,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,22,'U',0,'Y','N','N','N','N','N','N','N','AD_AttachmentEntry_ReferencedRecord_v',10,1,1,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.774Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583600 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:49.775Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544432) 
;

-- 2019-09-05T14:42:49.776Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583600
;

-- 2019-09-05T14:42:49.776Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583600)
;

-- 2019-09-05T14:42:49.777Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583600
;

-- 2019-09-05T14:42:49.778Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583600, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582463
;

-- 2019-09-05T14:42:49.822Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563224,583601,575858,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Über dieses Feld kann man zu einer editierbaren Version des Datensatzes springen.',10,'U',0,'Y','N','N','N','N','N','N','N','Editierbarer Datensatz',20,1,1,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.824Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583601 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:49.825Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575858) 
;

-- 2019-09-05T14:42:49.827Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583601
;

-- 2019-09-05T14:42:49.827Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583601)
;

-- 2019-09-05T14:42:49.830Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583601
;

-- 2019-09-05T14:42:49.830Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583601, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582464
;

-- 2019-09-05T14:42:49.893Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563225,583602,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','Mandant',30,1,1,TO_TIMESTAMP('2019-09-05 16:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:49.894Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583602 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:49.897Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-09-05T14:42:50.077Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583602
;

-- 2019-09-05T14:42:50.077Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583602)
;

-- 2019-09-05T14:42:50.079Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583602
;

-- 2019-09-05T14:42:50.079Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583602, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582465
;

-- 2019-09-05T14:42:50.132Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563226,583603,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','N','N','N','N','N','N','Sektion',40,1,1,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:50.133Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583603 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:50.134Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-09-05T14:42:50.330Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583603
;

-- 2019-09-05T14:42:50.331Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583603)
;

-- 2019-09-05T14:42:50.333Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583603
;

-- 2019-09-05T14:42:50.333Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583603, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582466
;

-- 2019-09-05T14:42:50.388Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563227,583604,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',22,'U','The Database Table provides the information of the table definition',0,'Y','Y','N','N','N','N','N','N','DB-Tabelle',50,1,1,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:50.388Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583604 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:50.390Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2019-09-05T14:42:50.402Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583604
;

-- 2019-09-05T14:42:50.402Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583604)
;

-- 2019-09-05T14:42:50.403Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583604
;

-- 2019-09-05T14:42:50.404Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583604, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582467
;

-- 2019-09-05T14:42:50.458Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563228,583605,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100,'Binärer Wert',0,'U','Das Feld "Binärwert" speichert binäre Werte.',0,'Y','N','N','N','N','N','N','N','Binärwert',60,1,1,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:50.459Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583605 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:50.461Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(174) 
;

-- 2019-09-05T14:42:50.462Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583605
;

-- 2019-09-05T14:42:50.462Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583605)
;

-- 2019-09-05T14:42:50.464Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583605
;

-- 2019-09-05T14:42:50.464Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583605, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582468
;

-- 2019-09-05T14:42:50.527Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563229,583606,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100,60,'U',0,'Y','Y','N','N','N','N','N','N','Content type',70,1,1,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:50.528Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583606 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:50.529Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543391) 
;

-- 2019-09-05T14:42:50.530Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583606
;

-- 2019-09-05T14:42:50.530Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583606)
;

-- 2019-09-05T14:42:50.532Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583606
;

-- 2019-09-05T14:42:50.532Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583606, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582469
;

-- 2019-09-05T14:42:50.587Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563232,583607,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100,2000,'U',0,'Y','Y','N','N','N','N','N','N','Beschreibung',80,1,1,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:50.588Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583607 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:50.589Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-09-05T14:42:50.654Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583607
;

-- 2019-09-05T14:42:50.654Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583607)
;

-- 2019-09-05T14:42:50.656Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583607
;

-- 2019-09-05T14:42:50.656Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583607, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582470
;

-- 2019-09-05T14:42:50.700Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563233,583608,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL',2000,'U','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)',0,'Y','Y','N','N','N','N','N','N','File Name',90,1,1,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:50.700Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583608 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:50.702Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2295) 
;

-- 2019-09-05T14:42:50.704Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583608
;

-- 2019-09-05T14:42:50.704Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583608)
;

-- 2019-09-05T14:42:50.705Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583608
;

-- 2019-09-05T14:42:50.706Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583608, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582471
;

-- 2019-09-05T14:42:50.749Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563234,583609,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','N','N','N','N','N','N','Aktiv',100,1,1,TO_TIMESTAMP('2019-09-05 16:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:50.749Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583609 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:50.751Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-09-05T14:42:51.057Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583609
;

-- 2019-09-05T14:42:51.057Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583609)
;

-- 2019-09-05T14:42:51.058Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583609
;

-- 2019-09-05T14:42:51.059Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583609, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582472
;

-- 2019-09-05T14:42:51.119Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563235,583610,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',22,'U','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','Y','N','N','N','N','N','N','Datensatz-ID',110,1,1,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.120Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583610 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:51.122Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2019-09-05T14:42:51.141Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583610
;

-- 2019-09-05T14:42:51.142Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583610)
;

-- 2019-09-05T14:42:51.144Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583610
;

-- 2019-09-05T14:42:51.144Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583610, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582473
;

-- 2019-09-05T14:42:51.229Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563236,583611,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'',1,'U','',0,'Y','Y','N','N','N','N','N','N','Art',120,1,1,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.231Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583611 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:51.234Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2019-09-05T14:42:51.239Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583611
;

-- 2019-09-05T14:42:51.240Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583611)
;

-- 2019-09-05T14:42:51.242Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583611
;

-- 2019-09-05T14:42:51.243Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583611, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582474
;

-- 2019-09-05T14:42:51.307Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563239,583612,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Full URL address - e.g. http://www.adempiere.org',2000,'U','The URL defines an fully qualified web address like http://www.adempiere.org',0,'Y','Y','N','N','N','N','N','N','URL',130,1,1,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.308Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583612 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:51.311Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(983) 
;

-- 2019-09-05T14:42:51.321Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583612
;

-- 2019-09-05T14:42:51.322Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583612)
;

-- 2019-09-05T14:42:51.325Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583612
;

-- 2019-09-05T14:42:51.325Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583612, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582475
;

-- 2019-09-05T14:42:51.392Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563230,583613,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',0,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','Erstellt',10,10,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.393Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583613 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:51.396Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-09-05T14:42:51.449Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583613
;

-- 2019-09-05T14:42:51.450Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583613)
;

-- 2019-09-05T14:42:51.452Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583613
;

-- 2019-09-05T14:42:51.452Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583613, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582476
;

-- 2019-09-05T14:42:51.506Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563231,583614,0,541868,0,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',0,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','Erstellt durch',20,20,0,1,1,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.507Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583614 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-05T14:42:51.510Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-09-05T14:42:51.561Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583614
;

-- 2019-09-05T14:42:51.562Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583614)
;

-- 2019-09-05T14:42:51.564Z
-- URL zum Konzept
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 583614
;

-- 2019-09-05T14:42:51.565Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 583614, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 582477
;

-- 2019-09-05T14:42:51.608Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541868,541422,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-09-05T14:42:51.609Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541422 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-09-05T14:42:51.611Z
-- URL zum Konzept
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541422
;

-- 2019-09-05T14:42:51.612Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 541422, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541395
;

-- 2019-09-05T14:42:51.655Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541823,541422,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.700Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541823,542758,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.759Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583613,0,541868,542758,560721,'F',TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Erstellt',10,0,0,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.810Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583614,0,541868,542758,560722,'F',TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Erstellt durch',20,0,0,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.866Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583608,0,541868,542758,560723,'F',TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'File Name',30,0,0,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.930Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583607,0,541868,542758,560724,'F',TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',40,0,0,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:42:51.980Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583601,0,541868,542758,560725,'F',TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Anhang_',50,0,0,TO_TIMESTAMP('2019-09-05 16:42:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:43:41.014Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=577040,Updated=TO_TIMESTAMP('2019-09-05 16:43:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540680
;

-- 2019-09-05T14:43:41.018Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(577040) 
;

-- 2019-09-05T14:43:41.031Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540680
;

-- 2019-09-05T14:43:41.036Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540680)
;

-- 2019-09-05T14:53:53.353Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,583500,0,541865,542754,560726,'F',TO_TIMESTAMP('2019-09-05 16:53:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Standort',12,0,0,TO_TIMESTAMP('2019-09-05 16:53:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:55:02.098Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577041,0,TO_TIMESTAMP('2019-09-05 16:55:02','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Seminarort','Seminarort',TO_TIMESTAMP('2019-09-05 16:55:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:55:02.101Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577041 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T14:55:08.243Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=577041, Description=NULL, Help=NULL, Name='Seminarort',Updated=TO_TIMESTAMP('2019-09-05 16:55:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583500
;

-- 2019-09-05T14:55:08.245Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577041) 
;

-- 2019-09-05T14:55:08.249Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583500
;

-- 2019-09-05T14:55:08.249Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583500)
;

-- 2019-09-05T14:56:09.514Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577042,0,TO_TIMESTAMP('2019-09-05 16:56:09','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Seminarpartner','Seminarpartner',TO_TIMESTAMP('2019-09-05 16:56:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:56:09.516Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577042 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T14:56:14.177Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=577042, Description=NULL, Help=NULL, Name='Seminarpartner',Updated=TO_TIMESTAMP('2019-09-05 16:56:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583498
;

-- 2019-09-05T14:56:14.178Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577042) 
;

-- 2019-09-05T14:56:14.181Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583498
;

-- 2019-09-05T14:56:14.181Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583498)
;

-- 2019-09-05T14:57:29.140Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577043,0,TO_TIMESTAMP('2019-09-05 16:57:29','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Seminarname','Seminarname',TO_TIMESTAMP('2019-09-05 16:57:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:57:29.142Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577043 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T14:57:34.111Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=577043, Description=NULL, Help=NULL, Name='Seminarname',Updated=TO_TIMESTAMP('2019-09-05 16:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583488
;

-- 2019-09-05T14:57:34.112Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577043) 
;

-- 2019-09-05T14:57:34.114Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583488
;

-- 2019-09-05T14:57:34.114Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583488)
;

-- 2019-09-05T14:57:50.704Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577044,0,TO_TIMESTAMP('2019-09-05 16:57:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Seminarstatus','Seminarstatus',TO_TIMESTAMP('2019-09-05 16:57:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T14:57:50.706Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577044 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T14:57:55.148Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=577044, Name='Seminarstatus',Updated=TO_TIMESTAMP('2019-09-05 16:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583519
;

-- 2019-09-05T14:57:55.151Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577044) 
;

-- 2019-09-05T14:57:55.152Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583519
;

-- 2019-09-05T14:57:55.152Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583519)
;

-- 2019-09-05T14:58:29.383Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541034,TO_TIMESTAMP('2019-09-05 16:58:29','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Seminarstatus',TO_TIMESTAMP('2019-09-05 16:58:29','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-09-05T14:58:29.385Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541034 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-09-05T14:58:49.067Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,13570,13562,0,541034,776,TO_TIMESTAMP('2019-09-05 16:58:49','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',TO_TIMESTAMP('2019-09-05 16:58:49','YYYY-MM-DD HH24:MI:SS'),100,'R_StatusCategory_ID=540009')
;

-- 2019-09-05T14:59:21.647Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='R_StatusCategory_ID=1000001',Updated=TO_TIMESTAMP('2019-09-05 16:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541034
;

-- 2019-09-05T15:06:28.013Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Reference_ID=18, AD_Reference_Value_ID=541034,Updated=TO_TIMESTAMP('2019-09-05 17:06:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583519
;

-- 2019-09-05T15:52:10.877Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577045,0,TO_TIMESTAMP('2019-09-05 17:52:10','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','organisiert von','organisiert von',TO_TIMESTAMP('2019-09-05 17:52:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T15:52:10.881Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577045 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T15:52:20.883Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=577045, Name='organisiert von',Updated=TO_TIMESTAMP('2019-09-05 17:52:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583487
;

-- 2019-09-05T15:52:20.886Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577045) 
;

-- 2019-09-05T15:52:20.895Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583487
;

-- 2019-09-05T15:52:20.897Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583487)
;

-- 2019-09-05T15:54:03.996Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=53281, Description=NULL, Help=NULL, Name='geplanter Beginn',Updated=TO_TIMESTAMP('2019-09-05 17:54:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583496
;

-- 2019-09-05T15:54:03.998Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53281) 
;

-- 2019-09-05T15:54:04.017Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583496
;

-- 2019-09-05T15:54:04.020Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583496)
;

-- 2019-09-05T15:54:45.880Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577046,0,TO_TIMESTAMP('2019-09-05 17:54:45','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Serminarende','Serminarende',TO_TIMESTAMP('2019-09-05 17:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T15:54:45.882Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577046 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T15:54:52.078Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577047,0,TO_TIMESTAMP('2019-09-05 17:54:52','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Serminarbeginn','Serminarbeginn',TO_TIMESTAMP('2019-09-05 17:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T15:54:52.079Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577047 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T15:56:21.248Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Seminarende', PrintName='Seminarende',Updated=TO_TIMESTAMP('2019-09-05 17:56:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577046 AND AD_Language='de_DE'
;

-- 2019-09-05T15:56:21.253Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577046,'de_DE') 
;

-- 2019-09-05T15:56:21.284Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(577046,'de_DE') 
;

-- 2019-09-05T15:56:21.286Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Seminarende', Description=NULL, Help=NULL WHERE AD_Element_ID=577046
;

-- 2019-09-05T15:56:21.287Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Seminarende', Description=NULL, Help=NULL WHERE AD_Element_ID=577046 AND IsCentrallyMaintained='Y'
;

-- 2019-09-05T15:56:21.288Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Seminarende', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577046) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577046)
;

-- 2019-09-05T15:56:21.313Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Seminarende', Name='Seminarende' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577046)
;

-- 2019-09-05T15:56:21.314Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Seminarende', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577046
;

-- 2019-09-05T15:56:21.316Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Seminarende', Description=NULL, Help=NULL WHERE AD_Element_ID = 577046
;

-- 2019-09-05T15:56:21.317Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Seminarende', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577046
;

-- 2019-09-05T15:56:24.470Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Seminarende', PrintName='Seminarende',Updated=TO_TIMESTAMP('2019-09-05 17:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577046 AND AD_Language='de_CH'
;

-- 2019-09-05T15:56:24.471Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577046,'de_CH') 
;

-- 2019-09-05T15:57:01.720Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Seminarbeginn', PrintName='Seminarbeginn',Updated=TO_TIMESTAMP('2019-09-05 17:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577047 AND AD_Language='en_US'
;

-- 2019-09-05T15:57:01.721Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577047,'en_US') 
;

-- 2019-09-05T15:57:08.232Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Seminarbeginn', PrintName='Seminarbeginn',Updated=TO_TIMESTAMP('2019-09-05 17:57:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577047 AND AD_Language='de_CH'
;

-- 2019-09-05T15:57:08.233Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577047,'de_CH') 
;

-- 2019-09-05T15:57:12.765Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Seminarbeginn', PrintName='Seminarbeginn',Updated=TO_TIMESTAMP('2019-09-05 17:57:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577047 AND AD_Language='de_DE'
;

-- 2019-09-05T15:57:12.767Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577047,'de_DE') 
;

-- 2019-09-05T15:57:12.778Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(577047,'de_DE') 
;

-- 2019-09-05T15:57:12.779Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Seminarbeginn', Description=NULL, Help=NULL WHERE AD_Element_ID=577047
;

-- 2019-09-05T15:57:12.780Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Seminarbeginn', Description=NULL, Help=NULL WHERE AD_Element_ID=577047 AND IsCentrallyMaintained='Y'
;

-- 2019-09-05T15:57:12.780Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Seminarbeginn', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577047) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577047)
;

-- 2019-09-05T15:57:12.789Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Seminarbeginn', Name='Seminarbeginn' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577047)
;

-- 2019-09-05T15:57:12.790Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Seminarbeginn', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577047
;

-- 2019-09-05T15:57:12.792Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Seminarbeginn', Description=NULL, Help=NULL WHERE AD_Element_ID = 577047
;

-- 2019-09-05T15:57:12.793Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Seminarbeginn', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577047
;

-- 2019-09-05T15:57:21.735Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=577046, Description=NULL, Help=NULL, Name='Seminarende',Updated=TO_TIMESTAMP('2019-09-05 17:57:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583497
;

-- 2019-09-05T15:57:21.737Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577046) 
;

-- 2019-09-05T15:57:21.741Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583497
;

-- 2019-09-05T15:57:21.742Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583497)
;

-- 2019-09-05T15:57:27.384Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=NULL, Description='Datum des Auftragseingangs', Help='The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.', Name='Datum Auftragseingang',Updated=TO_TIMESTAMP('2019-09-05 17:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583496
;

-- 2019-09-05T15:57:27.387Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1556) 
;

-- 2019-09-05T15:57:27.397Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583496
;

-- 2019-09-05T15:57:27.398Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583496)
;

-- 2019-09-05T15:57:43.081Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=577047, Description=NULL, Help=NULL, Name='Seminarbeginn',Updated=TO_TIMESTAMP('2019-09-05 17:57:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583496
;

-- 2019-09-05T15:57:43.082Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577047) 
;

-- 2019-09-05T15:57:43.086Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583496
;

-- 2019-09-05T15:57:43.087Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(583496)
;

-- 2019-09-05T15:58:35.862Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577048,0,TO_TIMESTAMP('2019-09-05 17:58:35','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Seminarbeteiligte','Seminarbeteiligte',TO_TIMESTAMP('2019-09-05 17:58:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T15:58:35.863Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577048 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T15:58:46.724Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Element_ID=577048, Name='Seminarbeteiligte',Updated=TO_TIMESTAMP('2019-09-05 17:58:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541867
;

-- 2019-09-05T15:58:46.727Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(577048) 
;

-- 2019-09-05T15:58:46.730Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541867)
;

