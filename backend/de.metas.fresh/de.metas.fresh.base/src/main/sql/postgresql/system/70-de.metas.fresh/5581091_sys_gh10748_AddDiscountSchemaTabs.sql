-- 2021-03-04T08:20:13.059Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573019,578798,0,543460,541586,540431,'Y',TO_TIMESTAMP('2021-03-04 09:20:07','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','M_DiscountSchemaBreak_V','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Gebrauchte Produkte','N',20,1,TO_TIMESTAMP('2021-03-04 09:20:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:20:13.168Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=543460 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-03-04T08:20:13.171Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(578798) 
;

-- 2021-03-04T08:20:13.181Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543460)
;

-- 2021-03-04T08:20:15.948Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573012,633102,0,543460,TO_TIMESTAMP('2021-03-04 09:20:15','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-03-04 09:20:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:20:15.957Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:20:15.997Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-03-04T08:20:20.554Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633102
;

-- 2021-03-04T08:20:20.555Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633102)
;

-- 2021-03-04T08:20:20.647Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573013,633103,0,543460,TO_TIMESTAMP('2021-03-04 09:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-03-04 09:20:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:20:20.650Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:20:20.651Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-03-04T08:20:21.866Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633103
;

-- 2021-03-04T08:20:21.867Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633103)
;

-- 2021-03-04T08:20:21.968Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573018,633104,0,543460,TO_TIMESTAMP('2021-03-04 09:20:21','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'U','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2021-03-04 09:20:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:20:21.969Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:20:21.970Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2021-03-04T08:20:22.531Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633104
;

-- 2021-03-04T08:20:22.532Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633104)
;

-- 2021-03-04T08:20:22.605Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573019,633105,0,543460,TO_TIMESTAMP('2021-03-04 09:20:22','YYYY-MM-DD HH24:MI:SS'),100,'Schema um den prozentualen Rabatt zu berechnen',10,'U','Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.','Y','N','N','N','N','N','N','N','Rabatt Schema',TO_TIMESTAMP('2021-03-04 09:20:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:20:22.606Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:20:22.608Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1714) 
;

-- 2021-03-04T08:20:22.665Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633105
;

-- 2021-03-04T08:20:22.665Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633105)
;

-- 2021-03-04T08:20:52.350Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543460,542636,TO_TIMESTAMP('2021-03-04 09:20:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','Main',10,TO_TIMESTAMP('2021-03-04 09:20:47','YYYY-MM-DD HH24:MI:SS'),100,'Main')
;

-- 2021-03-04T08:20:52.374Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=542636 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-03-04T08:21:04.586Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543341,542636,TO_TIMESTAMP('2021-03-04 09:21:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-03-04 09:21:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:21:21.511Z
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=543341
;

-- 2021-03-04T08:21:24.175Z
-- URL zum Konzept
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=542636
;

-- 2021-03-04T08:21:24.191Z
-- URL zum Konzept
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=542636
;

-- 2021-03-04T08:22:37.691Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633102
;

-- 2021-03-04T08:22:37.712Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=633102
;

-- 2021-03-04T08:22:37.716Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=633102
;

-- 2021-03-04T08:22:37.726Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633103
;

-- 2021-03-04T08:22:37.727Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=633103
;

-- 2021-03-04T08:22:37.729Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=633103
;

-- 2021-03-04T08:22:37.733Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633104
;

-- 2021-03-04T08:22:37.734Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=633104
;

-- 2021-03-04T08:22:37.735Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=633104
;

-- 2021-03-04T08:22:37.739Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633105
;

-- 2021-03-04T08:22:37.739Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=633105
;

-- 2021-03-04T08:22:37.741Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=633105
;

-- 2021-03-04T08:22:37.742Z
-- URL zum Konzept
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=543460
;

-- 2021-03-04T08:22:37.743Z
-- URL zum Konzept
DELETE FROM AD_Tab WHERE AD_Tab_ID=543460
;

-- 2021-03-04T08:23:29.024Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=573413, Description='Trade Discount Schema', Help='Ein Rabatt-Schema berechnet den prozentualen Rabatt', Name='Rabatt-Schema',Updated=TO_TIMESTAMP('2021-03-04 09:23:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=233
;

-- 2021-03-04T08:23:29.029Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Trade Discount Schema', IsActive='Y', Name='Rabatt-Schema',Updated=TO_TIMESTAMP('2021-03-04 09:23:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=1000093
;

-- 2021-03-04T08:23:29.033Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Trade Discount Schema', IsActive='Y', Name='Rabatt-Schema',Updated=TO_TIMESTAMP('2021-03-04 09:23:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=310
;

-- 2021-03-04T08:23:29.101Z
-- URL zum Konzept
UPDATE AD_WF_Node SET Description='Trade Discount Schema', Help='Ein Rabatt-Schema berechnet den prozentualen Rabatt', Name='Rabatt-Schema',Updated=TO_TIMESTAMP('2021-03-04 09:23:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=246
;

-- 2021-03-04T08:23:29.103Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(573413) 
;

-- 2021-03-04T08:23:29.108Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=233
;

-- 2021-03-04T08:23:29.113Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(233)
;

-- 2021-03-04T08:24:07.497Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573019,1715,0,543461,541586,233,'Y',TO_TIMESTAMP('2021-03-04 09:24:02','YYYY-MM-DD HH24:MI:SS'),100,'Trade Discount Break','U','N','Trade discount based on breaks (steps)','N','M_DiscountSchemaBreak_V','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Discount Schema Break','N',40,1,TO_TIMESTAMP('2021-03-04 09:24:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:24:07.562Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=543461 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-03-04T08:24:07.564Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(1715) 
;

-- 2021-03-04T08:24:07.568Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543461)
;

-- 2021-03-04T08:24:14.387Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2021-03-04 09:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543461
;

-- 2021-03-04T08:24:19.269Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573012,633106,0,543461,TO_TIMESTAMP('2021-03-04 09:24:19','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-03-04 09:24:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:24:19.272Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:24:19.275Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-03-04T08:24:22.342Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633106
;

-- 2021-03-04T08:24:22.342Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633106)
;

-- 2021-03-04T08:24:22.413Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573013,633107,0,543461,TO_TIMESTAMP('2021-03-04 09:24:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-03-04 09:24:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:24:22.414Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:24:22.415Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-03-04T08:24:23.068Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633107
;

-- 2021-03-04T08:24:23.068Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633107)
;

-- 2021-03-04T08:24:23.169Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573018,633108,0,543461,TO_TIMESTAMP('2021-03-04 09:24:23','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'U','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2021-03-04 09:24:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:24:23.170Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:24:23.172Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2021-03-04T08:24:23.519Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633108
;

-- 2021-03-04T08:24:23.520Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633108)
;

-- 2021-03-04T08:24:23.573Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573019,633109,0,543461,TO_TIMESTAMP('2021-03-04 09:24:23','YYYY-MM-DD HH24:MI:SS'),100,'Schema um den prozentualen Rabatt zu berechnen',10,'U','Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.','Y','N','N','N','N','N','N','N','Rabatt Schema',TO_TIMESTAMP('2021-03-04 09:24:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:24:23.574Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:24:23.576Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1714) 
;

-- 2021-03-04T08:24:23.590Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633109
;

-- 2021-03-04T08:24:23.590Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633109)
;

-- 2021-03-04T08:25:33.905Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543461,542637,TO_TIMESTAMP('2021-03-04 09:25:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-03-04 09:25:28','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-03-04T08:25:33.906Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=542637 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-03-04T08:25:36.250Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543342,542637,TO_TIMESTAMP('2021-03-04 09:25:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-03-04 09:25:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:25:45.310Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543342,545015,TO_TIMESTAMP('2021-03-04 09:25:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','Main',10,TO_TIMESTAMP('2021-03-04 09:25:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:26:09.319Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,633108,0,543461,545015,578499,TO_TIMESTAMP('2021-03-04 09:26:04','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',10,0,0,TO_TIMESTAMP('2021-03-04 09:26:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:26:20.331Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,633107,0,543461,545015,578500,TO_TIMESTAMP('2021-03-04 09:26:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',20,0,0,TO_TIMESTAMP('2021-03-04 09:26:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:26:38.350Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,633106,0,543461,545015,578501,TO_TIMESTAMP('2021-03-04 09:26:38','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',30,0,0,TO_TIMESTAMP('2021-03-04 09:26:38','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2021-03-04T08:28:34.234Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573020,633110,0,543461,TO_TIMESTAMP('2021-03-04 09:28:34','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-03-04 09:28:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:28:34.236Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:28:34.238Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-03-04T08:28:35.047Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633110
;

-- 2021-03-04T08:28:35.051Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633110)
;

-- 2021-03-04T08:28:54.570Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,633110,0,543461,545015,578502,TO_TIMESTAMP('2021-03-04 09:28:49','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',40,0,0,TO_TIMESTAMP('2021-03-04 09:28:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:29:12.153Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-03-04 09:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578499
;

-- 2021-03-04T08:29:12.159Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-03-04 09:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578502
;

-- 2021-03-04T08:29:12.162Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-03-04 09:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578500
;

-- 2021-03-04T08:29:12.166Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-03-04 09:29:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578501
;


-- 2021-03-04T08:37:40.946Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='Mian',Updated=TO_TIMESTAMP('2021-03-04 09:37:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545015
;

-- 2021-03-04T08:38:04.626Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='Main',Updated=TO_TIMESTAMP('2021-03-04 09:38:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545015
;

-- 2021-03-04T08:38:21.127Z
-- URL zum Konzept
UPDATE AD_UI_Section SET Name='',Updated=TO_TIMESTAMP('2021-03-04 09:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=542637
;

-- 2021-03-04T08:39:01.387Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='default', UIStyle='primary',Updated=TO_TIMESTAMP('2021-03-04 09:39:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545015
;

-- 2021-03-04T08:39:16.350Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2021-03-04 09:39:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578502
;

-- 2021-03-04T08:41:59.966Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573018,578799,0,543462,541586,140,'Y',TO_TIMESTAMP('2021-03-04 09:41:54','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','M_DiscountSchemaBreak_V','Y','N','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','N',0,'M_DiscountSchemaBreak_V_ID','N',190,1,TO_TIMESTAMP('2021-03-04 09:41:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:41:59.968Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=543462 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-03-04T08:41:59.971Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(578799) 
;

-- 2021-03-04T08:41:59.975Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543462)
;

-- 2021-03-04T08:42:10.686Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573012,633111,0,543462,TO_TIMESTAMP('2021-03-04 09:42:10','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-03-04 09:42:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:42:10.711Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:42:10.714Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-03-04T08:42:12.095Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633111
;

-- 2021-03-04T08:42:12.096Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633111)
;

-- 2021-03-04T08:42:12.180Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573013,633112,0,543462,TO_TIMESTAMP('2021-03-04 09:42:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-03-04 09:42:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:42:12.181Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:42:12.183Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-03-04T08:42:12.670Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633112
;

-- 2021-03-04T08:42:12.670Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633112)
;

-- 2021-03-04T08:42:12.750Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573018,633113,0,543462,TO_TIMESTAMP('2021-03-04 09:42:12','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'U','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2021-03-04 09:42:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:42:12.751Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:42:12.753Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2021-03-04T08:42:12.839Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633113
;

-- 2021-03-04T08:42:12.840Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633113)
;

-- 2021-03-04T08:42:12.906Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573019,633114,0,543462,TO_TIMESTAMP('2021-03-04 09:42:12','YYYY-MM-DD HH24:MI:SS'),100,'Schema um den prozentualen Rabatt zu berechnen',10,'U','Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.','Y','N','N','N','N','N','N','N','Rabatt Schema',TO_TIMESTAMP('2021-03-04 09:42:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:42:12.908Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:42:12.910Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1714) 
;

-- 2021-03-04T08:42:12.919Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633114
;

-- 2021-03-04T08:42:12.920Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633114)
;

-- 2021-03-04T08:42:12.970Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573020,633115,0,543462,TO_TIMESTAMP('2021-03-04 09:42:12','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-03-04 09:42:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:42:12.971Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633115 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:42:12.973Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-03-04T08:42:13.708Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633115
;

-- 2021-03-04T08:42:13.708Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633115)
;

-- 2021-03-04T08:42:13.821Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573021,633116,0,543462,TO_TIMESTAMP('2021-03-04 09:42:13','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','N','N','N','N','N','N','N','M_DiscountSchemaBreak_V_ID',TO_TIMESTAMP('2021-03-04 09:42:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:42:13.822Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=633116 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-04T08:42:13.824Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578799) 
;

-- 2021-03-04T08:42:13.825Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=633116
;

-- 2021-03-04T08:42:13.826Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(633116)
;

-- 2021-03-04T08:42:21.431Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543462,542638,TO_TIMESTAMP('2021-03-04 09:42:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-03-04 09:42:21','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-03-04T08:42:21.433Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=542638 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-03-04T08:42:25.328Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543343,542638,TO_TIMESTAMP('2021-03-04 09:42:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-03-04 09:42:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:42:41.024Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,543343,545016,TO_TIMESTAMP('2021-03-04 09:42:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2021-03-04 09:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:42:49.889Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,633113,0,543462,545016,578503,TO_TIMESTAMP('2021-03-04 09:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',10,0,0,TO_TIMESTAMP('2021-03-04 09:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:42:58.733Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,633115,0,543462,545016,578504,TO_TIMESTAMP('2021-03-04 09:42:58','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',20,0,0,TO_TIMESTAMP('2021-03-04 09:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:43:16.750Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,633112,0,543462,545016,578505,TO_TIMESTAMP('2021-03-04 09:43:11','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',30,0,0,TO_TIMESTAMP('2021-03-04 09:43:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:43:25.929Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,633111,0,543462,545016,578506,TO_TIMESTAMP('2021-03-04 09:43:25','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',40,0,0,TO_TIMESTAMP('2021-03-04 09:43:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-04T08:43:42.085Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-03-04 09:43:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578503
;

-- 2021-03-04T08:43:42.089Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-03-04 09:43:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578504
;

-- 2021-03-04T08:43:42.106Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-03-04 09:43:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578505
;

-- 2021-03-04T08:43:42.110Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-03-04 09:43:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578506
;

-- 2021-03-04T08:44:28.130Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2021-03-04 09:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578503
;

-- 2021-03-04T08:44:28.134Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-03-04 09:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578504
;

-- 2021-03-04T08:44:28.139Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-03-04 09:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578505
;

-- 2021-03-04T08:44:28.143Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-03-04 09:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578506
;

-- 2021-03-04T08:44:40.742Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_Field_ID=633114, Description='Schema um den prozentualen Rabatt zu berechnen', Help='Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.', Name='Rabatt Schema',Updated=TO_TIMESTAMP('2021-03-04 09:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578503
;

-- 2021-03-04T08:45:02.862Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-03-04 09:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578503
;

-- 2021-03-04T08:45:02.867Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-03-04 09:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578504
;

-- 2021-03-04T08:45:02.870Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-03-04 09:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578505
;

-- 2021-03-04T08:45:02.874Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-03-04 09:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578506
;

-- 2021-03-04T08:46:12.865Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Element_ID=572518, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Rabatt-Schema',Updated=TO_TIMESTAMP('2021-03-04 09:46:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543462
;

-- 2021-03-04T08:46:12.866Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572518) 
;

-- 2021-03-04T08:46:12.870Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543462)
;

-- 2021-03-04T08:46:55.586Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Element_ID=578798, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Gebrauchte Produkte',Updated=TO_TIMESTAMP('2021-03-04 09:46:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543461
;

-- 2021-03-04T08:46:55.587Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(578798) 
;

-- 2021-03-04T08:46:55.591Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543461)
;




UPDATE AD_Tab set IsActive = 'N' where AD_Tab_id=543462;

UPDATE AD_Tab set IsActive = 'N' where AD_Tab_id=543461;

