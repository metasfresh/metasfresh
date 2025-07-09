-- Run mode: SWING_CLIENT

-- Tab: Handling Unit(540189,de.metas.handlingunits) -> Handling Unit
-- Table: M_HU
-- 2025-07-07T10:10:08.038Z
UPDATE AD_Tab SET WhereClause='',Updated=TO_TIMESTAMP('2025-07-07 13:10:08.038','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=540508
;

-- Run mode: SWING_CLIENT

-- Tab: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment
-- Table: M_HU_Assignment
-- 2025-07-07T11:08:36.399Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,542385,0,548244,540569,540205,'Y',TO_TIMESTAMP('2025-07-07 14:08:36.257','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','N','N','A','M_HU_Assignment','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'M_HU_Assignment','N',40,1,TO_TIMESTAMP('2025-07-07 14:08:36.257','YYYY-MM-DD HH24:MI:SS.US'),100,'M_HU_Assignment.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table where tablename = ''M_InventoryLine'')  AND M_HU_Assignment.Record_ID = ANY ( (select array(SELECT M_InventoryLine_ID FROM M_InventoryLine where M_Inventory_ID = @M_Inventory_ID@))::integer[])')
;

-- 2025-07-07T11:08:36.429Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548244 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-07-07T11:08:36.498Z
/* DDL */  select update_tab_translation_from_ad_element(542385)
;

-- 2025-07-07T11:08:36.536Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548244)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Mandant
-- Column: M_HU_Assignment.AD_Client_ID
-- 2025-07-07T11:08:40.684Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550370,748931,0,548244,TO_TIMESTAMP('2025-07-07 14:08:40.541','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.handlingunits','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-07-07 14:08:40.541','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:40.690Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748931 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:40.695Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-07-07T11:08:41.041Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748931
;

-- 2025-07-07T11:08:41.042Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748931)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Organisation
-- Column: M_HU_Assignment.AD_Org_ID
-- 2025-07-07T11:08:41.157Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550371,748932,0,548244,TO_TIMESTAMP('2025-07-07 14:08:41.069','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.handlingunits','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2025-07-07 14:08:41.069','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:41.158Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748932 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:41.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-07-07T11:08:41.308Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748932
;

-- 2025-07-07T11:08:41.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748932)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Aktiv
-- Column: M_HU_Assignment.IsActive
-- 2025-07-07T11:08:41.408Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550374,748933,0,548244,TO_TIMESTAMP('2025-07-07 14:08:41.31','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.handlingunits','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-07-07 14:08:41.31','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:41.410Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:41.412Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-07-07T11:08:41.644Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748933
;

-- 2025-07-07T11:08:41.645Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748933)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> M_HU_Assignment
-- Column: M_HU_Assignment.M_HU_Assignment_ID
-- 2025-07-07T11:08:41.745Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550377,748934,0,548244,TO_TIMESTAMP('2025-07-07 14:08:41.647','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','M_HU_Assignment',TO_TIMESTAMP('2025-07-07 14:08:41.647','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:41.747Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748934 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:41.749Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542385)
;

-- 2025-07-07T11:08:41.759Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748934
;

-- 2025-07-07T11:08:41.760Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748934)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Handling Unit
-- Column: M_HU_Assignment.M_HU_ID
-- 2025-07-07T11:08:41.863Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550378,748935,0,548244,TO_TIMESTAMP('2025-07-07 14:08:41.763','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Handling Unit',TO_TIMESTAMP('2025-07-07 14:08:41.763','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:41.865Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748935 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:41.867Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542140)
;

-- 2025-07-07T11:08:41.886Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748935
;

-- 2025-07-07T11:08:41.887Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748935)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> DB-Tabelle
-- Column: M_HU_Assignment.AD_Table_ID
-- 2025-07-07T11:08:41.989Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550379,748936,0,548244,TO_TIMESTAMP('2025-07-07 14:08:41.892','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information',10,'de.metas.handlingunits','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2025-07-07 14:08:41.892','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:41.991Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748936 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:41.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126)
;

-- 2025-07-07T11:08:42.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748936
;

-- 2025-07-07T11:08:42.060Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748936)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Datensatz-ID
-- Column: M_HU_Assignment.Record_ID
-- 2025-07-07T11:08:42.166Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550380,748937,0,548244,TO_TIMESTAMP('2025-07-07 14:08:42.063','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID',10,'de.metas.handlingunits','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2025-07-07 14:08:42.063','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:42.168Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748937 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:42.170Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538)
;

-- 2025-07-07T11:08:42.198Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748937
;

-- 2025-07-07T11:08:42.199Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748937)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Menge
-- Column: M_HU_Assignment.Qty
-- 2025-07-07T11:08:42.300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550425,748938,0,548244,TO_TIMESTAMP('2025-07-07 14:08:42.202','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge',14,'de.metas.handlingunits','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2025-07-07 14:08:42.202','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:42.300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748938 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:42.302Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2025-07-07T11:08:42.318Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748938
;

-- 2025-07-07T11:08:42.319Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748938)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Transfer Packing Materials
-- Column: M_HU_Assignment.IsTransferPackingMaterials
-- 2025-07-07T11:08:42.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551435,748939,0,548244,TO_TIMESTAMP('2025-07-07 14:08:42.32','YYYY-MM-DD HH24:MI:SS.US'),100,'Shall we transfer packing materials along with the HU',1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Transfer Packing Materials',TO_TIMESTAMP('2025-07-07 14:08:42.32','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:42.415Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748939 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:42.416Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542607)
;

-- 2025-07-07T11:08:42.429Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748939
;

-- 2025-07-07T11:08:42.430Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748939)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> LU
-- Column: M_HU_Assignment.M_LU_HU_ID
-- 2025-07-07T11:08:42.538Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551483,748940,0,548244,TO_TIMESTAMP('2025-07-07 14:08:42.433','YYYY-MM-DD HH24:MI:SS.US'),100,'Loading Unit',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','LU',TO_TIMESTAMP('2025-07-07 14:08:42.433','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:42.540Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748940 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:42.542Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542455)
;

-- 2025-07-07T11:08:42.552Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748940
;

-- 2025-07-07T11:08:42.553Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748940)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> TU
-- Column: M_HU_Assignment.M_TU_HU_ID
-- 2025-07-07T11:08:42.647Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551484,748941,0,548244,TO_TIMESTAMP('2025-07-07 14:08:42.557','YYYY-MM-DD HH24:MI:SS.US'),100,'Trading Unit',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','TU',TO_TIMESTAMP('2025-07-07 14:08:42.557','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:42.649Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748941 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:42.651Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542462)
;

-- 2025-07-07T11:08:42.660Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748941
;

-- 2025-07-07T11:08:42.661Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748941)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> CU
-- Column: M_HU_Assignment.VHU_ID
-- 2025-07-07T11:08:42.766Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551756,748942,0,548244,TO_TIMESTAMP('2025-07-07 14:08:42.665','YYYY-MM-DD HH24:MI:SS.US'),100,'Customer Unit',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','CU',TO_TIMESTAMP('2025-07-07 14:08:42.665','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:42.768Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:42.771Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542583)
;

-- 2025-07-07T11:08:42.784Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748942
;

-- 2025-07-07T11:08:42.786Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748942)
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Produkte
-- Column: M_HU_Assignment.Products
-- 2025-07-07T11:08:42.883Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552291,748943,0,548244,TO_TIMESTAMP('2025-07-07 14:08:42.789','YYYY-MM-DD HH24:MI:SS.US'),100,250,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Produkte',TO_TIMESTAMP('2025-07-07 14:08:42.789','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:08:42.884Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-07T11:08:42.886Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542806)
;

-- 2025-07-07T11:08:42.901Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748943
;

-- 2025-07-07T11:08:42.902Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748943)
;

-- 2025-07-07T11:09:38.663Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748942
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> CU
-- Column: M_HU_Assignment.VHU_ID
-- 2025-07-07T11:09:38.665Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=748942
;

-- 2025-07-07T11:09:38.676Z
DELETE FROM AD_Field WHERE AD_Field_ID=748942
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Handling Unit
-- Column: M_HU_Assignment.M_HU_ID
-- 2025-07-07T11:09:48.812Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-07-07 14:09:48.812','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748935
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Handling Unit
-- Column: M_HU_Assignment.M_HU_ID
-- 2025-07-07T11:10:06.524Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2025-07-07 14:10:06.524','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748935
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Handling Unit
-- Column: M_HU_Assignment.M_HU_ID
-- 2025-07-07T11:10:24.370Z
UPDATE AD_Field SET IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2025-07-07 14:10:24.37','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748935
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Handling Unit
-- Column: M_HU_Assignment.M_HU_ID
-- 2025-07-07T11:10:38.017Z
UPDATE AD_Field SET SeqNo=10,Updated=TO_TIMESTAMP('2025-07-07 14:10:38.017','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748935
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Aktiv
-- Column: M_HU_Assignment.IsActive
-- 2025-07-07T11:10:42.538Z
UPDATE AD_Field SET SeqNo=0,Updated=TO_TIMESTAMP('2025-07-07 14:10:42.538','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748933
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> LU
-- Column: M_HU_Assignment.M_LU_HU_ID
-- 2025-07-07T11:10:58.661Z
UPDATE AD_Field SET SeqNo=20,Updated=TO_TIMESTAMP('2025-07-07 14:10:58.66','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748940
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> LU
-- Column: M_HU_Assignment.M_LU_HU_ID
-- 2025-07-07T11:11:17.050Z
UPDATE AD_Field SET IsAlwaysUpdateable='', IsDisplayed='Y', IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-07-07 14:11:17.05','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748940
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Handling Unit
-- Column: M_HU_Assignment.M_HU_ID
-- 2025-07-07T11:11:43.658Z
UPDATE AD_Field SET SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-07-07 14:11:43.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748935
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Produkte
-- Column: M_HU_Assignment.Products
-- 2025-07-07T11:12:19.169Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', SeqNo=35, SeqNoGrid=35, SpanX=999,Updated=TO_TIMESTAMP('2025-07-07 14:12:19.169','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748943
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> Transfer Packing Materials
-- Column: M_HU_Assignment.IsTransferPackingMaterials
-- 2025-07-07T11:12:41.309Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', SeqNo=40, SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-07-07 14:12:41.309','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748939
;

-- Field: Materialentnahme(540205,de.metas.swat) -> M_HU_Assignment(548244,de.metas.handlingunits) -> TU
-- Column: M_HU_Assignment.M_TU_HU_ID
-- 2025-07-07T11:13:01.302Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', SeqNo=30, SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-07-07 14:13:01.302','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=748941
;

-- Run mode: SWING_CLIENT

-- Name: Materialentnahme
-- Action Type: W
-- Window: Materialentnahme(540205,de.metas.swat)
-- 2025-07-07T11:18:58.825Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,574174,542235,0,540205,TO_TIMESTAMP('2025-07-07 14:18:58.634','YYYY-MM-DD HH24:MI:SS.US'),100,'Geben Sie Eigenverbrauch aus dem Warenbestand ein','de.metas.swat','M_Inventory_MaterialWithdrawal','Y','N','N','N','N','Materialentnahme',TO_TIMESTAMP('2025-07-07 14:18:58.634','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-07T11:18:58.832Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542235 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-07-07T11:18:58.838Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542235, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542235)
;

-- 2025-07-07T11:18:58.866Z
/* DDL */  select update_menu_translation_from_ad_element(574174)
;

-- Reordering children of `Shipment`
-- Node name: `Shipment (M_InOut)`
-- 2025-07-07T11:18:59.561Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipping Notification (M_Shipping_Notification)`
-- 2025-07-07T11:18:59.563Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542113 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2025-07-07T11:18:59.564Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2025-07-07T11:18:59.566Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2025-07-07T11:18:59.567Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2025-07-07T11:18:59.568Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2025-07-07T11:18:59.569Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2025-07-07T11:18:59.570Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2025-07-07T11:18:59.571Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2025-07-07T11:18:59.572Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2025-07-07T11:18:59.573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2025-07-07T11:18:59.574Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2025-07-07T11:18:59.575Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2025-07-07T11:18:59.576Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2025-07-07T11:18:59.577Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2025-07-07T11:18:59.579Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2025-07-07T11:18:59.580Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-07-07T11:18:59.581Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2025-07-07T11:18:59.582Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-07-07T11:18:59.582Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-07-07T11:18:59.583Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2025-07-07T11:18:59.584Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Node name: `Materialentnahme`
-- 2025-07-07T11:18:59.585Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542235 AND AD_Tree_ID=10
;

-- Reordering children of `Warehouse Management`
-- Node name: `Materialentnahme`
-- 2025-07-07T11:19:24.931Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542235 AND AD_Tree_ID=10
;

-- Node name: `Warehouse (M_Warehouse)`
-- 2025-07-07T11:19:24.932Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type (M_Warehouse_Type)`
-- 2025-07-07T11:19:24.933Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move (M_Movement)`
-- 2025-07-07T11:19:24.933Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme (M_Inventory)`
-- 2025-07-07T11:19:24.933Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase (Fresh_QtyOnHand)`
-- 2025-07-07T11:19:24.934Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule (MD_Candidate)`
-- 2025-07-07T11:19:24.934Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast (M_Forecast)`
-- 2025-07-07T11:19:24.935Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit (MD_Cockpit)`
-- 2025-07-07T11:19:24.935Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-07-07T11:19:24.935Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-07-07T11:19:24.937Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-07-07T11:19:24.937Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory (M_Inventory)`
-- 2025-07-07T11:19:24.937Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting (M_InventoryLine)`
-- 2025-07-07T11:19:24.937Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate (M_Inventory_Candidate)`
-- 2025-07-07T11:19:24.938Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- Run mode: SWING_CLIENT

-- Table: M_Inventory
-- 2025-07-07T14:47:37.275Z
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2025-07-07 17:47:37.274','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=321
;
