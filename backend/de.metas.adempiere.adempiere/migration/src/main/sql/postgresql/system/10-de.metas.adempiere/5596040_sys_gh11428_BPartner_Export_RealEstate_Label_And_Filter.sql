-- 2021-07-01T07:22:24.742Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579334,0,544119,541700,541174,'Y',TO_TIMESTAMP('2021-07-01 10:22:24','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_BPartner_Attribute2','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'BPartner Attribute2','N',20,1,TO_TIMESTAMP('2021-07-01 10:22:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:22:24.786Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544119 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-07-01T07:22:24.818Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579334) 
;

-- 2021-07-01T07:22:24.846Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544119)
;

-- 2021-07-01T07:22:39.347Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=574380, Parent_Column_ID=574881,Updated=TO_TIMESTAMP('2021-07-01 10:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544119
;

-- 2021-07-01T07:22:58.741Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574344,649899,0,544119,TO_TIMESTAMP('2021-07-01 10:22:58','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-07-01 10:22:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:22:58.779Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-01T07:22:58.811Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-07-01T07:22:59.096Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649899
;

-- 2021-07-01T07:22:59.143Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649899)
;

-- 2021-07-01T07:22:59.581Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574345,649900,0,544119,TO_TIMESTAMP('2021-07-01 10:22:59','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-07-01 10:22:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:22:59.613Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649900 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-01T07:22:59.644Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-07-01T07:22:59.929Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649900
;

-- 2021-07-01T07:22:59.960Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649900)
;

-- 2021-07-01T07:23:00.430Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574348,649901,0,544119,TO_TIMESTAMP('2021-07-01 10:23:00','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-07-01 10:23:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:23:00.469Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649901 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-01T07:23:00.500Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-07-01T07:23:00.770Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649901
;

-- 2021-07-01T07:23:00.802Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649901)
;

-- 2021-07-01T07:23:01.272Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574351,649902,0,544119,TO_TIMESTAMP('2021-07-01 10:23:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','BPartner Attribute2',TO_TIMESTAMP('2021-07-01 10:23:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:23:01.303Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649902 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-01T07:23:01.329Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579334) 
;

-- 2021-07-01T07:23:01.360Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649902
;

-- 2021-07-01T07:23:01.388Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649902)
;

-- 2021-07-01T07:23:01.852Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574352,649903,0,544119,TO_TIMESTAMP('2021-07-01 10:23:01','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','N','N','N','N','N','N','N','Merkmal2',TO_TIMESTAMP('2021-07-01 10:23:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:23:01.890Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649903 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-01T07:23:01.915Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579335) 
;

-- 2021-07-01T07:23:01.947Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649903
;

-- 2021-07-01T07:23:01.990Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649903)
;

-- 2021-07-01T07:23:02.454Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574380,649904,0,544119,TO_TIMESTAMP('2021-07-01 10:23:02','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2021-07-01 10:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:23:02.476Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649904 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-01T07:23:02.507Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2021-07-01T07:23:02.554Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649904
;

-- 2021-07-01T07:23:02.586Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(649904)
;

-- 2021-07-01T07:24:19.058Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-07-01 10:24:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649903
;

-- 2021-07-01T07:24:38.994Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544119,543260,TO_TIMESTAMP('2021-07-01 10:24:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-07-01 10:24:38','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-07-01T07:24:39.079Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543260 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-07-01T07:24:45.256Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544080,543260,TO_TIMESTAMP('2021-07-01 10:24:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-07-01 10:24:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:25:05.941Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544080,546169,TO_TIMESTAMP('2021-07-01 10:25:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','real estate',10,TO_TIMESTAMP('2021-07-01 10:25:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:25:33.001Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649903,0,544119,546169,586927,'F',TO_TIMESTAMP('2021-07-01 10:25:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Merkmal2',10,0,0,TO_TIMESTAMP('2021-07-01 10:25:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:26:27.688Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,544118,546167,586928,'L',TO_TIMESTAMP('2021-07-01 10:26:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',649903,544119,0,'Real Estate',8,0,0,TO_TIMESTAMP('2021-07-01 10:26:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T07:27:25.397Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-01 10:27:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544119
;

-- 2021-07-01T07:28:28.038Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsAllowFiltering='Y',Updated=TO_TIMESTAMP('2021-07-01 10:28:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586928
;

-- 2021-07-01T07:37:21.274Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-01 10:37:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649902
;

-- 2021-07-01T07:38:08.712Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-01 10:38:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649903
;

-- 2021-07-01T07:38:10.021Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-01 10:38:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649900
;

-- 2021-07-01T07:38:11.392Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-01 10:38:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649901
;

-- 2021-07-01T07:38:14.297Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-07-01 10:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649904
;

