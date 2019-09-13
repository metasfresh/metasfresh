
-- 2019-07-29T12:42:26.229Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,544432,0,541838,541144,540668,'Y',TO_TIMESTAMP('2019-07-29 14:42:26','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','AD_AttachmentEntry_ReferencedRecord_v','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'AD_AttachmentEntry_ReferencedRecord_v','N',80,0,TO_TIMESTAMP('2019-07-29 14:42:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:26.230Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541838 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-07-29T12:42:26.233Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(544432) 
;

-- 2019-07-29T12:42:26.236Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541838)
;

-- 2019-07-29T12:42:32.856Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563223,582463,0,541838,TO_TIMESTAMP('2019-07-29 14:42:32','YYYY-MM-DD HH24:MI:SS'),100,22,'U','Y','N','N','N','N','N','N','N','AD_AttachmentEntry_ReferencedRecord_v',TO_TIMESTAMP('2019-07-29 14:42:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:32.857Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582463 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:32.860Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544432) 
;

-- 2019-07-29T12:42:32.863Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582463
;

-- 2019-07-29T12:42:32.864Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582463)
;

-- 2019-07-29T12:42:32.936Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563224,582464,0,541838,TO_TIMESTAMP('2019-07-29 14:42:32','YYYY-MM-DD HH24:MI:SS'),100,10,'U','Y','N','N','N','N','N','N','N','Anhang',TO_TIMESTAMP('2019-07-29 14:42:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:32.937Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582464 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:32.940Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543390) 
;

-- 2019-07-29T12:42:32.942Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582464
;

-- 2019-07-29T12:42:32.943Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582464)
;

-- 2019-07-29T12:42:32.998Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563225,582465,0,541838,TO_TIMESTAMP('2019-07-29 14:42:32','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-07-29 14:42:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:32.999Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582465 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:33.002Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-07-29T12:42:33.159Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582465
;

-- 2019-07-29T12:42:33.159Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582465)
;

-- 2019-07-29T12:42:33.253Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563226,582466,0,541838,TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:33.256Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582466 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:33.260Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-07-29T12:42:33.414Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582466
;

-- 2019-07-29T12:42:33.415Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582466)
;

-- 2019-07-29T12:42:33.517Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563227,582467,0,541838,TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',22,'U','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:33.518Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582467 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:33.522Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2019-07-29T12:42:33.538Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582467
;

-- 2019-07-29T12:42:33.539Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582467)
;

-- 2019-07-29T12:42:33.608Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563228,582468,0,541838,TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100,'Binärer Wert',0,'U','Das Feld "Binärwert" speichert binäre Werte.','Y','N','N','N','N','N','N','N','Binärwert',TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:33.610Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582468 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:33.612Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(174) 
;

-- 2019-07-29T12:42:33.617Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582468
;

-- 2019-07-29T12:42:33.618Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582468)
;

-- 2019-07-29T12:42:33.691Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563229,582469,0,541838,TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100,60,'U','Y','N','N','N','N','N','N','N','Content type',TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:33.693Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582469 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:33.696Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543391) 
;

-- 2019-07-29T12:42:33.697Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582469
;

-- 2019-07-29T12:42:33.698Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582469)
;

-- 2019-07-29T12:42:33.773Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563232,582470,0,541838,TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100,2000,'U','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:33.775Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582470 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:33.778Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2019-07-29T12:42:33.860Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582470
;

-- 2019-07-29T12:42:33.861Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582470)
;

-- 2019-07-29T12:42:33.920Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563233,582471,0,541838,TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL',2000,'U','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','N','N','N','N','N','N','File Name',TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:33.921Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582471 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:33.924Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2295) 
;

-- 2019-07-29T12:42:33.926Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582471
;

-- 2019-07-29T12:42:33.926Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582471)
;

-- 2019-07-29T12:42:33.988Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563234,582472,0,541838,TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-07-29 14:42:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:33.990Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582472 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:33.995Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-07-29T12:42:34.201Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582472
;

-- 2019-07-29T12:42:34.201Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582472)
;

-- 2019-07-29T12:42:34.332Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563235,582473,0,541838,TO_TIMESTAMP('2019-07-29 14:42:34','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',22,'U','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2019-07-29 14:42:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:34.333Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582473 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:34.336Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2019-07-29T12:42:34.351Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582473
;

-- 2019-07-29T12:42:34.351Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582473)
;

-- 2019-07-29T12:42:34.428Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563236,582474,0,541838,TO_TIMESTAMP('2019-07-29 14:42:34','YYYY-MM-DD HH24:MI:SS'),100,'',1,'U','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2019-07-29 14:42:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:34.429Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582474 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:34.433Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2019-07-29T12:42:34.437Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582474
;

-- 2019-07-29T12:42:34.438Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582474)
;

-- 2019-07-29T12:42:34.508Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563239,582475,0,541838,TO_TIMESTAMP('2019-07-29 14:42:34','YYYY-MM-DD HH24:MI:SS'),100,'Full URL address - e.g. http://www.adempiere.org',2000,'U','The URL defines an fully qualified web address like http://www.adempiere.org','Y','N','N','N','N','N','N','N','URL',TO_TIMESTAMP('2019-07-29 14:42:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:42:34.509Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582475 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:42:34.512Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(983) 
;

-- 2019-07-29T12:42:34.518Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582475
;

-- 2019-07-29T12:42:34.518Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582475)
;

-- 2019-07-29T12:42:47.942Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 14:42:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582466
;

-- 2019-07-29T12:42:48.954Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 14:42:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582467
;

-- 2019-07-29T12:42:50.089Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 14:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582469
;

-- 2019-07-29T12:42:51.174Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 14:42:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582470
;

-- 2019-07-29T12:42:52.066Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 14:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582471
;

-- 2019-07-29T12:42:53.009Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 14:42:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582472
;

-- 2019-07-29T12:42:56.208Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 14:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582473
;

-- 2019-07-29T12:42:56.929Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 14:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582474
;

-- 2019-07-29T12:43:56.247Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 14:43:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582475
;

-- 2019-07-29T12:44:01.535Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-07-29 14:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582470
;

-- 2019-07-29T12:44:39.036Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2019-07-29 14:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541838
;

-- 2019-07-29T12:45:49.632Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=563235, Parent_Column_ID=1349,Updated=TO_TIMESTAMP('2019-07-29 14:45:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541838
;

-- 2019-07-29T12:45:52.736Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541838,541395,TO_TIMESTAMP('2019-07-29 14:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-07-29 14:45:52','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-07-29T12:45:52.737Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541395 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-07-29T12:45:52.801Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541790,541395,TO_TIMESTAMP('2019-07-29 14:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-07-29 14:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:45:52.867Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541790,542702,TO_TIMESTAMP('2019-07-29 14:45:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-07-29 14:45:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:47:04.320Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563230,582476,0,541838,0,TO_TIMESTAMP('2019-07-29 14:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',0,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','Erstellt',10,10,0,1,1,TO_TIMESTAMP('2019-07-29 14:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:47:04.322Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582476 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:47:04.326Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2019-07-29T12:47:04.377Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582476
;

-- 2019-07-29T12:47:04.377Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582476)
;

-- 2019-07-29T12:47:09.864Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563231,582477,0,541838,0,TO_TIMESTAMP('2019-07-29 14:47:09','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',0,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','Erstellt durch',20,20,0,1,1,TO_TIMESTAMP('2019-07-29 14:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:47:09.865Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582477 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:47:09.869Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2019-07-29T12:47:09.914Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582477
;

-- 2019-07-29T12:47:09.914Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582477)
;

-- 2019-07-29T12:47:26.473Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582476,0,541838,542702,560302,'F',TO_TIMESTAMP('2019-07-29 14:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Erstellt',10,0,0,TO_TIMESTAMP('2019-07-29 14:47:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:47:35.920Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582477,0,541838,542702,560303,'F',TO_TIMESTAMP('2019-07-29 14:47:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Erstellt durch',20,0,0,TO_TIMESTAMP('2019-07-29 14:47:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:47:49.302Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582471,0,541838,542702,560304,'F',TO_TIMESTAMP('2019-07-29 14:47:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'File Name',30,0,0,TO_TIMESTAMP('2019-07-29 14:47:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:48:05.610Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582470,0,541838,542702,560305,'F',TO_TIMESTAMP('2019-07-29 14:48:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',40,0,0,TO_TIMESTAMP('2019-07-29 14:48:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:48:17.631Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582473,0,541838,542702,560306,'F',TO_TIMESTAMP('2019-07-29 14:48:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Datensatz-ID',50,0,0,TO_TIMESTAMP('2019-07-29 14:48:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:48:27.547Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 14:48:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582470
;

-- 2019-07-29T12:49:28.362Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560306
;

-- 2019-07-29T12:49:54.718Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582464,0,541838,542702,560307,'F',TO_TIMESTAMP('2019-07-29 14:49:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Anhang_',50,0,0,TO_TIMESTAMP('2019-07-29 14:49:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:50:46.392Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Element_ID=572726, Name='Dokumente',Updated=TO_TIMESTAMP('2019-07-29 14:50:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541838
;

-- 2019-07-29T12:50:46.394Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(572726) 
;

-- 2019-07-29T12:50:46.398Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541838)
;

-- 2019-07-29T12:51:35.110Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=575858, Description='Über dieses Feld kann man zu einer editierbaren Version des Datensatzes springen.', Name='Editierbarer Datensatz',Updated=TO_TIMESTAMP('2019-07-29 14:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582464
;

-- 2019-07-29T12:51:35.112Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575858) 
;

-- 2019-07-29T12:51:35.113Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582464
;

-- 2019-07-29T12:51:35.114Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582464)
;

