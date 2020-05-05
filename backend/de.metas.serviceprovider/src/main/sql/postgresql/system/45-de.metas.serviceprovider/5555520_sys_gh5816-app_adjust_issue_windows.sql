-- 2020-03-30T06:48:44.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2020-03-30 09:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566321
;

-- 2020-03-30T06:48:48.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2020-03-30 09:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566320
;

-- 2020-03-30T06:50:16.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570148,598521,0,542297,TO_TIMESTAMP('2020-03-30 09:50:16','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','External ID',TO_TIMESTAMP('2020-03-30 09:50:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T06:50:16.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598521 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T06:50:16.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2020-03-30T06:50:17.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598521
;

-- 2020-03-30T06:50:17.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598521)
;

-- 2020-03-30T06:50:17.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570149,598522,0,542297,TO_TIMESTAMP('2020-03-30 09:50:17','YYYY-MM-DD HH24:MI:SS'),100,'External issue number ( e.g. github issue number )',255,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','ExternalIssueNo',TO_TIMESTAMP('2020-03-30 09:50:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T06:50:17.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598522 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T06:50:17.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577606) 
;

-- 2020-03-30T06:50:17.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598522
;

-- 2020-03-30T06:50:17.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598522)
;

-- 2020-03-30T06:50:17.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570153,598523,0,542297,TO_TIMESTAMP('2020-03-30 09:50:17','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',10,'de.metas.serviceprovider','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2020-03-30 09:50:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T06:50:17.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598523 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T06:50:17.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2020-03-30T06:50:17.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598523
;

-- 2020-03-30T06:50:17.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598523)
;

-- 2020-03-30T06:52:30.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,598522,0,542297,566625,543488,'F',TO_TIMESTAMP('2020-03-30 09:52:30','YYYY-MM-DD HH24:MI:SS'),100,'External issue number ( e.g. github issue number )','Y','N','N','Y','N','N','N',0,'ExternalIssueNo',50,0,0,TO_TIMESTAMP('2020-03-30 09:52:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T06:52:50.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2020-03-30 09:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566625
;

-- 2020-03-30T06:52:56.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2020-03-30 09:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566319
;

-- 2020-03-30T06:54:35.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,597927,0,542297,566626,543492,'F',TO_TIMESTAMP('2020-03-30 09:54:35','YYYY-MM-DD HH24:MI:SS'),100,'Ursprünglich geplanter oder erwarteter Aufwand.','Y','N','N','Y','N','N','N',0,'Budgetiert',40,0,0,TO_TIMESTAMP('2020-03-30 09:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T06:57:13.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,570176,577612,0,542323,541467,540859,'N',TO_TIMESTAMP('2020-03-30 09:57:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','N','N','S_ExternalIssueDetail','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'S_ExternalIssueDetail','N',20,1,TO_TIMESTAMP('2020-03-30 09:57:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T06:57:13.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542323 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2020-03-30T06:57:13.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(577612) 
;

-- 2020-03-30T06:57:13.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542323)
;

-- 2020-03-30T07:00:15.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2020-03-30 10:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542323
;

-- 2020-03-30T07:00:40.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570160,598524,0,542323,TO_TIMESTAMP('2020-03-30 10:00:40','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.serviceprovider','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2020-03-30 10:00:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:00:40.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598524 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T07:00:40.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2020-03-30T07:00:40.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598524
;

-- 2020-03-30T07:00:40.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598524)
;

-- 2020-03-30T07:00:41.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570161,598525,0,542323,TO_TIMESTAMP('2020-03-30 10:00:40','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.serviceprovider','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2020-03-30 10:00:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:00:41.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598525 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T07:00:41.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2020-03-30T07:00:41.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598525
;

-- 2020-03-30T07:00:41.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598525)
;

-- 2020-03-30T07:00:41.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570169,598526,0,542323,TO_TIMESTAMP('2020-03-30 10:00:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.serviceprovider','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2020-03-30 10:00:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:00:41.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598526 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T07:00:41.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2020-03-30T07:00:41.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598526
;

-- 2020-03-30T07:00:41.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598526)
;

-- 2020-03-30T07:00:41.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570174,598527,0,542323,TO_TIMESTAMP('2020-03-30 10:00:41','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.serviceprovider','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2020-03-30 10:00:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:00:41.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598527 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T07:00:41.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2020-03-30T07:00:41.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598527
;

-- 2020-03-30T07:00:41.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598527)
;

-- 2020-03-30T07:00:41.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570175,598528,0,542323,TO_TIMESTAMP('2020-03-30 10:00:41','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Detail value',TO_TIMESTAMP('2020-03-30 10:00:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:00:41.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598528 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T07:00:41.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577613) 
;

-- 2020-03-30T07:00:41.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598528
;

-- 2020-03-30T07:00:41.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598528)
;

-- 2020-03-30T07:00:42.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570176,598529,0,542323,TO_TIMESTAMP('2020-03-30 10:00:41','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Budget-Issue',TO_TIMESTAMP('2020-03-30 10:00:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:00:42.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598529 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T07:00:42.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577512) 
;

-- 2020-03-30T07:00:42.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598529
;

-- 2020-03-30T07:00:42.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598529)
;

-- 2020-03-30T07:00:42.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570177,598530,0,542323,TO_TIMESTAMP('2020-03-30 10:00:42','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Aufwands-Issue',TO_TIMESTAMP('2020-03-30 10:00:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:00:42.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598530 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T07:00:42.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577376) 
;

-- 2020-03-30T07:00:42.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598530
;

-- 2020-03-30T07:00:42.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598530)
;

-- 2020-03-30T07:03:09.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET TabLevel=0,Updated=TO_TIMESTAMP('2020-03-30 10:03:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542297
;

-- 2020-03-30T07:03:19.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=569964,Updated=TO_TIMESTAMP('2020-03-30 10:03:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542323
;

-- 2020-03-30T07:07:47.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542323,541824,TO_TIMESTAMP('2020-03-30 10:07:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-03-30 10:07:47','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-03-30T07:07:47.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541824 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-03-30T07:07:48.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542319,541824,TO_TIMESTAMP('2020-03-30 10:07:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-03-30 10:07:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:07:48.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542319,543534,TO_TIMESTAMP('2020-03-30 10:07:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2020-03-30 10:07:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:09:44.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,598528,0,542323,566627,543534,'F',TO_TIMESTAMP('2020-03-30 10:09:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Detail value',10,0,0,TO_TIMESTAMP('2020-03-30 10:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:10:37.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,598527,0,542323,566628,543534,'F',TO_TIMESTAMP('2020-03-30 10:10:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Art',20,0,0,TO_TIMESTAMP('2020-03-30 10:10:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:10:43.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-03-30 10:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566628
;

-- 2020-03-30T07:10:43.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-03-30 10:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566627
;

-- 2020-03-30T07:13:53.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2020-03-30 10:13:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566626
;

-- 2020-03-30T07:13:56.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2020-03-30 10:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566337
;

-- 2020-03-30T07:17:13.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2020-03-30 10:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566300
;

-- 2020-03-30T07:17:33.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570156,598531,0,542126,TO_TIMESTAMP('2020-03-30 10:17:33','YYYY-MM-DD HH24:MI:SS'),100,'External issue number ( e.g. github issue number )',255,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','ExternalIssueNo',TO_TIMESTAMP('2020-03-30 10:17:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:17:33.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598531 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T07:17:33.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577606) 
;

-- 2020-03-30T07:17:33.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598531
;

-- 2020-03-30T07:17:33.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598531)
;

-- 2020-03-30T07:17:33.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570157,598532,0,542126,TO_TIMESTAMP('2020-03-30 10:17:33','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Meilenstein',TO_TIMESTAMP('2020-03-30 10:17:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:17:33.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598532 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T07:17:33.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577514) 
;

-- 2020-03-30T07:17:33.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598532
;

-- 2020-03-30T07:17:33.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598532)
;

-- 2020-03-30T07:17:33.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570178,598533,0,542126,TO_TIMESTAMP('2020-03-30 10:17:33','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',10,'de.metas.serviceprovider','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2020-03-30 10:17:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T07:17:33.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598533 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T07:17:33.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2020-03-30T07:17:33.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598533
;

-- 2020-03-30T07:17:33.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598533)
;

-- 2020-03-30T08:20:57.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542286,543557,TO_TIMESTAMP('2020-03-30 11:20:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','effort',30,TO_TIMESTAMP('2020-03-30 11:20:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:21:05.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2020-03-30 11:21:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543485
;

-- 2020-03-30T08:21:15.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2020-03-30 11:21:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543557
;

-- 2020-03-30T08:21:48.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543557, SeqNo=10,Updated=TO_TIMESTAMP('2020-03-30 11:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566303
;

-- 2020-03-30T08:22:09.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543557, SeqNo=20,Updated=TO_TIMESTAMP('2020-03-30 11:22:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566302
;

-- 2020-03-30T08:22:37.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=566300
;

-- 2020-03-30T08:23:02.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,598531,0,542126,566820,543484,'F',TO_TIMESTAMP('2020-03-30 11:23:02','YYYY-MM-DD HH24:MI:SS'),100,'External issue number ( e.g. github issue number )','Y','N','N','Y','N','N','N',0,'ExternalIssueNo',120,0,0,TO_TIMESTAMP('2020-03-30 11:23:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:23:31.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2020-03-30 11:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566820
;

-- 2020-03-30T08:24:32.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,597942,0,542126,566821,543557,'F',TO_TIMESTAMP('2020-03-30 11:24:32','YYYY-MM-DD HH24:MI:SS'),100,'Ursprünglich geplanter oder erwarteter Aufwand.','Y','N','N','Y','N','N','N',0,'Budgetiert',30,0,0,TO_TIMESTAMP('2020-03-30 11:24:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:25:12.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2020-03-30 11:25:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566303
;

-- 2020-03-30T08:26:57.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,570177,577612,0,542338,541467,540758,'N',TO_TIMESTAMP('2020-03-30 11:26:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','N','N','S_ExternalIssueDetail','Y','N','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'S_ExternalIssueDetail',569667,'N',30,1,TO_TIMESTAMP('2020-03-30 11:26:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:26:57.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542338 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2020-03-30T08:26:57.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(577612) 
;

-- 2020-03-30T08:26:57.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542338)
;

-- 2020-03-30T08:27:05.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- 2020-03-30T08:28:13.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570160,598830,0,542338,TO_TIMESTAMP('2020-03-30 11:28:13','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.serviceprovider','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2020-03-30 11:28:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:28:13.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T08:28:13.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2020-03-30T08:28:14.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598830
;

-- 2020-03-30T08:28:14.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598830)
;

-- 2020-03-30T08:28:14.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570161,598831,0,542338,TO_TIMESTAMP('2020-03-30 11:28:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.serviceprovider','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2020-03-30 11:28:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:28:14.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T08:28:14.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2020-03-30T08:28:14.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598831
;

-- 2020-03-30T08:28:14.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598831)
;

-- 2020-03-30T08:28:14.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570169,598832,0,542338,TO_TIMESTAMP('2020-03-30 11:28:14','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.serviceprovider','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2020-03-30 11:28:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:28:14.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T08:28:14.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2020-03-30T08:28:14.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598832
;

-- 2020-03-30T08:28:14.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598832)
;

-- 2020-03-30T08:28:15.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570174,598833,0,542338,TO_TIMESTAMP('2020-03-30 11:28:14','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.serviceprovider','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2020-03-30 11:28:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:28:15.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T08:28:15.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2020-03-30T08:28:15.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598833
;

-- 2020-03-30T08:28:15.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598833)
;

-- 2020-03-30T08:28:15.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570175,598834,0,542338,TO_TIMESTAMP('2020-03-30 11:28:15','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Detail value',TO_TIMESTAMP('2020-03-30 11:28:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:28:15.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T08:28:15.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577613) 
;

-- 2020-03-30T08:28:15.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598834
;

-- 2020-03-30T08:28:15.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598834)
;

-- 2020-03-30T08:28:15.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570176,598835,0,542338,TO_TIMESTAMP('2020-03-30 11:28:15','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Budget-Issue',TO_TIMESTAMP('2020-03-30 11:28:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:28:15.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T08:28:15.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577512) 
;

-- 2020-03-30T08:28:15.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598835
;

-- 2020-03-30T08:28:15.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598835)
;

-- 2020-03-30T08:28:15.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570177,598836,0,542338,TO_TIMESTAMP('2020-03-30 11:28:15','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Aufwands-Issue',TO_TIMESTAMP('2020-03-30 11:28:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:28:15.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-30T08:28:15.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577376) 
;

-- 2020-03-30T08:28:15.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598836
;

-- 2020-03-30T08:28:15.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598836)
;

-- 2020-03-30T08:29:55.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542338,541839,TO_TIMESTAMP('2020-03-30 11:29:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-03-30 11:29:54','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2020-03-30T08:29:55.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541839 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-03-30T08:30:32.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542335,541839,TO_TIMESTAMP('2020-03-30 11:30:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-03-30 11:30:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:30:55.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542335,543558,TO_TIMESTAMP('2020-03-30 11:30:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','details',10,'primary',TO_TIMESTAMP('2020-03-30 11:30:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:31:11.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,598834,0,542338,566822,543558,'F',TO_TIMESTAMP('2020-03-30 11:31:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Detail value',10,0,0,TO_TIMESTAMP('2020-03-30 11:31:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:31:30.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,598833,0,542338,566823,543558,'F',TO_TIMESTAMP('2020-03-30 11:31:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Art',20,0,0,TO_TIMESTAMP('2020-03-30 11:31:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:31:41.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2020-03-30 11:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566822
;

-- 2020-03-30T08:31:46.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2020-03-30 11:31:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566823
;

-- 2020-03-30T08:33:01.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2020-03-30 11:33:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566823
;

-- 2020-03-30T08:33:01.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2020-03-30 11:33:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566822
;

-- 2020-03-30T08:37:11.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2020-03-30 11:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542295
;

-- 2020-03-30T08:37:29.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2020-03-30 11:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542295
;

-- 2020-03-30T08:37:39.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=NULL, SeqNo=20,Updated=TO_TIMESTAMP('2020-03-30 11:37:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542338
;

-- 2020-03-30T08:37:53.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=30,Updated=TO_TIMESTAMP('2020-03-30 11:37:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542295
;

-- 2020-03-30T08:40:29.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsGridModeOnly='Y',Updated=TO_TIMESTAMP('2020-03-30 11:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542126
;

-- 2020-03-30T08:41:59.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsGridModeOnly='Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-03-30 11:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542323
;

-- 2020-03-30T08:44:48.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570181,577612,0,13,541467,'S_ExternalIssueDetail_ID',TO_TIMESTAMP('2020-03-30 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.serviceprovider',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'S_ExternalIssueDetail',0,0,TO_TIMESTAMP('2020-03-30 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-03-30T08:44:48.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570181 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-03-30T08:44:48.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577612) 
;

-- 2020-03-30T08:45:34.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('S_ExternalIssueDetail','ALTER TABLE public.S_ExternalIssueDetail ADD COLUMN S_ExternalIssueDetail_ID NUMERIC(10)')
;

-- 2020-03-30T08:45:34.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE S_ExternalIssueDetail ADD CONSTRAINT S_ExternalIssueDetail_Key PRIMARY KEY (S_ExternalIssueDetail_ID)
;

-- 2020-03-30T08:51:07.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,598523,0,542297,566826,543492,'F',TO_TIMESTAMP('2020-03-30 11:51:07','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','N','N',0,'Ansprechpartner',50,0,0,TO_TIMESTAMP('2020-03-30 11:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:51:29.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2020-03-30 11:51:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566826
;

-- 2020-03-30T08:51:35.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2020-03-30 11:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566323
;

-- 2020-03-30T08:51:40.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2020-03-30 11:51:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566324
;

-- 2020-03-30T08:51:43.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2020-03-30 11:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566626
;

-- 2020-03-30T08:53:14.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2020-03-30 11:53:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566303
;

-- 2020-03-30T08:53:31.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,598533,0,542126,566827,543557,'F',TO_TIMESTAMP('2020-03-30 11:53:31','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','N','N',0,'Ansprechpartner',10,0,0,TO_TIMESTAMP('2020-03-30 11:53:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:56:43.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577618,0,TO_TIMESTAMP('2020-03-30 11:56:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Assignee','Assignee',TO_TIMESTAMP('2020-03-30 11:56:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-03-30T08:56:43.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577618 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-03-30T08:57:10.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577618, Description=NULL, Help=NULL, Name='Assignee',Updated=TO_TIMESTAMP('2020-03-30 11:57:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=598533
;

-- 2020-03-30T08:57:10.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577618) 
;

-- 2020-03-30T08:57:10.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598533
;

-- 2020-03-30T08:57:10.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598533)
;

-- 2020-03-30T08:57:43.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577618, Description=NULL, Help=NULL, Name='Assignee',Updated=TO_TIMESTAMP('2020-03-30 11:57:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=598523
;

-- 2020-03-30T08:57:43.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577618) 
;

-- 2020-03-30T08:57:43.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598523
;

-- 2020-03-30T08:57:43.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598523)
;

