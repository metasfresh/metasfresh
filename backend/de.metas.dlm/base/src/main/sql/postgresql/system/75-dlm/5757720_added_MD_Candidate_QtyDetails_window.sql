-- Run mode: SWING_CLIENT

-- 2025-06-13T11:49:50.164Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583731,0,TO_TIMESTAMP('2025-06-13 11:49:49.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.material.dispo','Y','Material Disposition Mengenangaben','Material Disposition Mengenangaben',TO_TIMESTAMP('2025-06-13 11:49:49.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:49:50.169Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583731 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-06-13T11:50:03.814Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Material Schedule Quantity Details', PrintName='Material Schedule Quantity Details',Updated=TO_TIMESTAMP('2025-06-13 11:50:03.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583731 AND AD_Language='en_US'
;

-- 2025-06-13T11:50:03.815Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-13T11:50:04.143Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583731,'en_US')
;

-- Window: Material Disposition Mengenangaben, InternalName=null
-- 2025-06-13T11:50:46.255Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583731,0,541902,TO_TIMESTAMP('2025-06-13 11:50:46.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.material.dispo','Y','N','N','Y','N','N','N','Y','Material Disposition Mengenangaben','N',TO_TIMESTAMP('2025-06-13 11:50:46.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-06-13T11:50:46.256Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541902 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-06-13T11:50:46.258Z
/* DDL */  select update_window_translation_from_ad_element(583731)
;

-- 2025-06-13T11:50:46.267Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541902
;

-- 2025-06-13T11:50:46.268Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541902)
;

-- Tab: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails
-- Table: MD_Candidate_QtyDetails
-- 2025-06-13T11:51:14.330Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583722,0,548203,542503,541902,'Y',TO_TIMESTAMP('2025-06-13 11:51:14.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.material.dispo','N','N','A','MD_Candidate_QtyDetails','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'MD_Candidate_QtyDetails','N',10,0,TO_TIMESTAMP('2025-06-13 11:51:14.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:51:14.332Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548203 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-06-13T11:51:14.334Z
/* DDL */  select update_tab_translation_from_ad_element(583722)
;

-- 2025-06-13T11:51:14.337Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548203)
;

-- Field: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> Mandant
-- Column: MD_Candidate_QtyDetails.AD_Client_ID
-- 2025-06-13T11:51:25.714Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590406,747839,0,548203,TO_TIMESTAMP('2025-06-13 11:51:25.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.material.dispo','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-06-13 11:51:25.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:51:25.715Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-13T11:51:25.716Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-06-13T11:51:25.794Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747839
;

-- 2025-06-13T11:51:25.795Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747839)
;

-- Field: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> Sektion
-- Column: MD_Candidate_QtyDetails.AD_Org_ID
-- 2025-06-13T11:51:25.910Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590407,747840,0,548203,TO_TIMESTAMP('2025-06-13 11:51:25.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.material.dispo','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-06-13 11:51:25.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:51:25.912Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-13T11:51:25.913Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-06-13T11:51:25.972Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747840
;

-- 2025-06-13T11:51:25.973Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747840)
;

-- Field: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> Aktiv
-- Column: MD_Candidate_QtyDetails.IsActive
-- 2025-06-13T11:51:26.088Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590410,747841,0,548203,TO_TIMESTAMP('2025-06-13 11:51:25.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.material.dispo','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-06-13 11:51:25.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:51:26.089Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-13T11:51:26.090Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-06-13T11:51:26.154Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747841
;

-- 2025-06-13T11:51:26.155Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747841)
;

-- Field: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> MD_Candidate_QtyDetails
-- Column: MD_Candidate_QtyDetails.MD_Candidate_QtyDetails_ID
-- 2025-06-13T11:51:26.258Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590413,747842,0,548203,TO_TIMESTAMP('2025-06-13 11:51:26.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','MD_Candidate_QtyDetails',TO_TIMESTAMP('2025-06-13 11:51:26.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:51:26.260Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-13T11:51:26.261Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583722)
;

-- 2025-06-13T11:51:26.263Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747842
;

-- 2025-06-13T11:51:26.264Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747842)
;

-- Field: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> Dispositionskandidat für Bestand
-- Column: MD_Candidate_QtyDetails.Stock_MD_Candidate_ID
-- 2025-06-13T11:51:26.376Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590414,747843,0,548203,TO_TIMESTAMP('2025-06-13 11:51:26.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Dispositionskandidat für Bestand',TO_TIMESTAMP('2025-06-13 11:51:26.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:51:26.377Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-13T11:51:26.379Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583723)
;

-- 2025-06-13T11:51:26.380Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747843
;

-- 2025-06-13T11:51:26.382Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747843)
;

-- Field: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> Detail Dispositionskandidat
-- Column: MD_Candidate_QtyDetails.Detail_MD_Candidate_ID
-- 2025-06-13T11:51:26.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590415,747844,0,548203,TO_TIMESTAMP('2025-06-13 11:51:26.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Detail Dispositionskandidat',TO_TIMESTAMP('2025-06-13 11:51:26.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:51:26.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-13T11:51:26.494Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583724)
;

-- 2025-06-13T11:51:26.497Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747844
;

-- 2025-06-13T11:51:26.498Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747844)
;

-- Field: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> Menge
-- Column: MD_Candidate_QtyDetails.Qty
-- 2025-06-13T11:51:26.605Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590416,747845,0,548203,TO_TIMESTAMP('2025-06-13 11:51:26.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge',10,'de.metas.material.dispo','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2025-06-13 11:51:26.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:51:26.606Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-13T11:51:26.608Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2025-06-13T11:51:26.612Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747845
;

-- 2025-06-13T11:51:26.613Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747845)
;

-- Field: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> Dispositionskandidat
-- Column: MD_Candidate_QtyDetails.MD_Candidate_ID
-- 2025-06-13T11:51:26.737Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590417,747846,0,548203,TO_TIMESTAMP('2025-06-13 11:51:26.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Dispositionskandidat',TO_TIMESTAMP('2025-06-13 11:51:26.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:51:26.738Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747846 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-13T11:51:26.739Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543308)
;

-- 2025-06-13T11:51:26.741Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747846
;

-- 2025-06-13T11:51:26.742Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747846)
;

-- Tab: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo)
-- UI Section:
-- 2025-06-13T11:51:56.355Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548203,546745,TO_TIMESTAMP('2025-06-13 11:51:56.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-06-13 11:51:56.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100) RETURNING Value
;

-- 2025-06-13T11:51:56.357Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546745 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- Tab: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo)
-- UI Section: main
-- 2025-06-13T11:52:08.635Z
UPDATE AD_UI_Section SET Value='main',Updated=TO_TIMESTAMP('2025-06-13 11:52:08.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Section_ID=546745
;

-- UI Section: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main
-- UI Column: 10
-- 2025-06-13T11:52:14.169Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548215,546745,TO_TIMESTAMP('2025-06-13 11:52:13.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-06-13 11:52:13.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main
-- UI Column: 20
-- 2025-06-13T11:52:15.633Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548216,546745,TO_TIMESTAMP('2025-06-13 11:52:15.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-06-13 11:52:15.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 10
-- UI Element Group: main
-- 2025-06-13T11:52:34.442Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548215,553122,TO_TIMESTAMP('2025-06-13 11:52:34.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,'main',TO_TIMESTAMP('2025-06-13 11:52:34.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 10
-- UI Element Group: main
-- 2025-06-13T11:52:40.477Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2025-06-13 11:52:40.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553122
;

-- UI Element: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 10 -> main.Dispositionskandidat
-- Column: MD_Candidate_QtyDetails.MD_Candidate_ID
-- 2025-06-13T11:52:54.341Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747846,0,548203,553122,634145,'F',TO_TIMESTAMP('2025-06-13 11:52:54.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Dispositionskandidat',10,0,0,TO_TIMESTAMP('2025-06-13 11:52:54.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 10 -> main.Dispositionskandidat für Bestand
-- Column: MD_Candidate_QtyDetails.Stock_MD_Candidate_ID
-- 2025-06-13T11:53:07.075Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747843,0,548203,553122,634146,'F',TO_TIMESTAMP('2025-06-13 11:53:06.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Dispositionskandidat für Bestand',20,0,0,TO_TIMESTAMP('2025-06-13 11:53:06.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 10 -> main.Detail Dispositionskandidat
-- Column: MD_Candidate_QtyDetails.Detail_MD_Candidate_ID
-- 2025-06-13T11:53:15.169Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747844,0,548203,553122,634147,'F',TO_TIMESTAMP('2025-06-13 11:53:15.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Detail Dispositionskandidat',30,0,0,TO_TIMESTAMP('2025-06-13 11:53:15.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 10 -> main.Menge
-- Column: MD_Candidate_QtyDetails.Qty
-- 2025-06-13T11:53:42.611Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747845,0,548203,553122,634148,'F',TO_TIMESTAMP('2025-06-13 11:53:42.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','N','N',0,'Menge',40,0,0,TO_TIMESTAMP('2025-06-13 11:53:42.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 20
-- UI Element Group: org
-- 2025-06-13T11:53:56.933Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548216,553123,TO_TIMESTAMP('2025-06-13 11:53:56.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',10,TO_TIMESTAMP('2025-06-13 11:53:56.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 20 -> org.Aktiv
-- Column: MD_Candidate_QtyDetails.IsActive
-- 2025-06-13T11:54:06.966Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747841,0,548203,553123,634149,'F',TO_TIMESTAMP('2025-06-13 11:54:06.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-06-13 11:54:06.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 20 -> org.Sektion
-- Column: MD_Candidate_QtyDetails.AD_Org_ID
-- 2025-06-13T11:54:14.008Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747840,0,548203,553123,634150,'F',TO_TIMESTAMP('2025-06-13 11:54:13.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2025-06-13 11:54:13.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 20 -> org.Sektion
-- Column: MD_Candidate_QtyDetails.AD_Org_ID
-- 2025-06-13T11:54:18.981Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=634150
;

-- UI Column: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 20
-- UI Element Group: org
-- 2025-06-13T11:54:27.664Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548216,553124,TO_TIMESTAMP('2025-06-13 11:54:27.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-06-13 11:54:27.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 20
-- UI Element Group: flags
-- 2025-06-13T11:54:31.336Z
UPDATE AD_UI_ElementGroup SET Name='flags',Updated=TO_TIMESTAMP('2025-06-13 11:54:31.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553123
;

-- UI Element: Material Disposition Mengenangaben(541902,de.metas.material.dispo) -> MD_Candidate_QtyDetails(548203,de.metas.material.dispo) -> main -> 20 -> org.Sektion
-- Column: MD_Candidate_QtyDetails.AD_Org_ID
-- 2025-06-13T11:54:44.774Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747840,0,548203,553124,634151,'F',TO_TIMESTAMP('2025-06-13 11:54:44.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-06-13 11:54:44.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Window: Material Disposition Mengenangaben, InternalName=MD_Candidate_QtyDetails
-- 2025-06-13T11:58:55.968Z
UPDATE AD_Window SET InternalName='MD_Candidate_QtyDetails',Updated=TO_TIMESTAMP('2025-06-13 11:58:55.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541902
;

-- Name: Material Disposition Mengenangaben
-- Action Type: W
-- Window: Material Disposition Mengenangaben(541902,de.metas.material.dispo)
-- 2025-06-13T11:59:02.501Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583731,542226,0,541902,TO_TIMESTAMP('2025-06-13 11:59:02.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.material.dispo','MD_Candidate_QtyDetails','Y','N','N','N','N','Material Disposition Mengenangaben',TO_TIMESTAMP('2025-06-13 11:59:02.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-13T11:59:02.502Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542226 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-06-13T11:59:02.504Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542226, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542226)
;

-- 2025-06-13T11:59:02.512Z
/* DDL */  select update_menu_translation_from_ad_element(583731)
;

-- Reordering children of `Warehouse Management`
-- Node name: `Users assigned to workplace (C_Workplace_User_Assign)`
-- 2025-06-13T11:59:03.094Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542121 AND AD_Tree_ID=10
;

-- Node name: `Workplace (C_Workplace)`
-- 2025-06-13T11:59:03.095Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Node name: `Warehouse (M_Warehouse)`
-- 2025-06-13T11:59:03.096Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type (M_Warehouse_Type)`
-- 2025-06-13T11:59:03.097Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move (M_Movement)`
-- 2025-06-13T11:59:03.098Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme (M_Inventory)`
-- 2025-06-13T11:59:03.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase (Fresh_QtyOnHand)`
-- 2025-06-13T11:59:03.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule (MD_Candidate)`
-- 2025-06-13T11:59:03.101Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast (M_Forecast)`
-- 2025-06-13T11:59:03.101Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Forecast Lines (M_ForecastLine)`
-- 2025-06-13T11:59:03.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542225 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit (MD_Cockpit)`
-- 2025-06-13T11:59:03.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-06-13T11:59:03.104Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-06-13T11:59:03.105Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-06-13T11:59:03.106Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory (M_Inventory)`
-- 2025-06-13T11:59:03.107Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting (M_InventoryLine)`
-- 2025-06-13T11:59:03.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate (M_Inventory_Candidate)`
-- 2025-06-13T11:59:03.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- Node name: `Material Disposition Mengenangaben`
-- 2025-06-13T11:59:03.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542226 AND AD_Tree_ID=10
;

