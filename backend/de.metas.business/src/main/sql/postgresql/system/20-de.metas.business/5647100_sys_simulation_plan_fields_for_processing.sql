-- Column: C_SimulationPlan.DocStatus
-- 2022-07-15T11:29:05.387543500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583694,289,0,17,131,542173,'DocStatus',TO_TIMESTAMP('2022-07-15 14:29:05','YYYY-MM-DD HH24:MI:SS'),100,'N','DR','The current status of the document','D',0,2,'The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Belegstatus',0,0,TO_TIMESTAMP('2022-07-15 14:29:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-15T11:29:05.391544Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583694 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-15T11:29:05.396544300Z
/* DDL */  select update_Column_Translation_From_AD_Element(289) 
;

-- 2022-07-15T11:29:06.650648600Z
/* DDL */ SELECT public.db_alter_table('C_SimulationPlan','ALTER TABLE public.C_SimulationPlan ADD COLUMN DocStatus VARCHAR(2) DEFAULT ''DR'' NOT NULL')
;

-- Field: Simulation Plan -> Simulation Plan -> Belegstatus
-- Column: C_SimulationPlan.DocStatus
-- 2022-07-15T11:29:24.744295800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583694,701926,0,546390,TO_TIMESTAMP('2022-07-15 14:29:24','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document',2,'D','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','N','N','N','N','N','Belegstatus',TO_TIMESTAMP('2022-07-15 14:29:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T11:29:24.746294400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701926 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T11:29:24.751294800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289) 
;

-- 2022-07-15T11:29:24.799835Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701926
;

-- 2022-07-15T11:29:24.804834700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701926)
;

-- 2022-07-15T11:30:17.317268200Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546090,549533,TO_TIMESTAMP('2022-07-15 14:30:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','status',20,TO_TIMESTAMP('2022-07-15 14:30:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Simulation Plan.Belegstatus
-- Column: C_SimulationPlan.DocStatus
-- 2022-07-15T11:30:35.645474500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701926,0,546390,610373,549533,'F',TO_TIMESTAMP('2022-07-15 14:30:35','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','Y','N','N','Belegstatus',10,0,0,TO_TIMESTAMP('2022-07-15 14:30:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Simulation Plan.Verarbeitet
-- Column: C_SimulationPlan.Processed
-- 2022-07-15T11:30:53.370393100Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549533, IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-07-15 14:30:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609637
;

-- Field: Simulation Plan -> Simulation Plan -> Belegstatus
-- Column: C_SimulationPlan.DocStatus
-- 2022-07-15T11:31:34.281596800Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 14:31:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701926
;

-- UI Element: Simulation Plan -> Simulation Plan.Verarbeitet
-- Column: C_SimulationPlan.Processed
-- 2022-07-15T11:31:45.609995500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-07-15 14:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609637
;

-- UI Element: Simulation Plan -> Simulation Plan.Belegstatus
-- Column: C_SimulationPlan.DocStatus
-- 2022-07-15T11:31:45.615999200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-07-15 14:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610373
;

-- Column: C_Project_WO_Resource_Simulation.Processed
-- 2022-07-15T15:12:02.929987100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583696,1047,0,20,542176,'Processed',TO_TIMESTAMP('2022-07-15 18:12:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2022-07-15 18:12:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-15T15:12:02.937589600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583696 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-15T15:12:02.943589300Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- 2022-07-15T15:12:21.167328400Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource_Simulation','ALTER TABLE public.C_Project_WO_Resource_Simulation ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- 2022-07-15T15:15:36.832954500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581136,0,'AssignDateFrom_Prev',TO_TIMESTAMP('2022-07-15 18:15:36','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab','D','Beginn Zuordnung','Y','Zuordnung von (previous)','Zuordnung von (previous)',TO_TIMESTAMP('2022-07-15 18:15:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:15:36.837953600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581136 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-15T15:16:18.842917300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581137,0,'AssignDateTo_Previous',TO_TIMESTAMP('2022-07-15 18:16:18','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis','D','Zuordnung endet','Y','Zuordnung bis (previous)','Zuordnung bis (previous)',TO_TIMESTAMP('2022-07-15 18:16:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:16:18.851916Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581137 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-15T15:16:59.283798200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581138,0,'IsAllDay_Prev',TO_TIMESTAMP('2022-07-15 18:16:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','All day (previous)','All day (previous)',TO_TIMESTAMP('2022-07-15 18:16:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:16:59.288788100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581138 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom_Prev
-- 2022-07-15T15:18:55.910216100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583697,581136,0,16,542176,'AssignDateFrom_Prev',TO_TIMESTAMP('2022-07-15 18:18:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Ressource zuordnen ab','D',0,7,'Beginn Zuordnung','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zuordnung von (previous)',0,0,TO_TIMESTAMP('2022-07-15 18:18:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-15T15:18:55.913248800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583697 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-15T15:18:55.915248600Z
/* DDL */  select update_Column_Translation_From_AD_Element(581136) 
;

-- 2022-07-15T15:18:57.394471200Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource_Simulation','ALTER TABLE public.C_Project_WO_Resource_Simulation ADD COLUMN AssignDateFrom_Prev TIMESTAMP WITH TIME ZONE')
;

-- 2022-07-15T15:19:24.557850Z
UPDATE AD_Element SET ColumnName='AssignDateTo_Prev',Updated=TO_TIMESTAMP('2022-07-15 18:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581137
;

-- 2022-07-15T15:19:24.588647900Z
UPDATE AD_Column SET ColumnName='AssignDateTo_Prev', Name='Zuordnung bis (previous)', Description='Ressource zuordnen bis', Help='Zuordnung endet' WHERE AD_Element_ID=581137
;

-- 2022-07-15T15:19:24.589681900Z
UPDATE AD_Process_Para SET ColumnName='AssignDateTo_Prev', Name='Zuordnung bis (previous)', Description='Ressource zuordnen bis', Help='Zuordnung endet', AD_Element_ID=581137 WHERE UPPER(ColumnName)='ASSIGNDATETO_PREV' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-15T15:19:24.595646400Z
UPDATE AD_Process_Para SET ColumnName='AssignDateTo_Prev', Name='Zuordnung bis (previous)', Description='Ressource zuordnen bis', Help='Zuordnung endet' WHERE AD_Element_ID=581137 AND IsCentrallyMaintained='Y'
;

-- Column: C_Project_WO_Resource_Simulation.AssignDateTo_Prev
-- 2022-07-15T15:19:59.270163300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583698,581137,0,16,542176,'AssignDateTo_Prev',TO_TIMESTAMP('2022-07-15 18:19:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Ressource zuordnen bis','D',0,7,'Zuordnung endet','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zuordnung bis (previous)',0,0,TO_TIMESTAMP('2022-07-15 18:19:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-15T15:19:59.277216900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583698 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-15T15:19:59.284204300Z
/* DDL */  select update_Column_Translation_From_AD_Element(581137) 
;

-- 2022-07-15T15:20:01.064030900Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource_Simulation','ALTER TABLE public.C_Project_WO_Resource_Simulation ADD COLUMN AssignDateTo_Prev TIMESTAMP WITH TIME ZONE')
;

-- Column: C_Project_WO_Resource_Simulation.IsAllDay_Prev
-- 2022-07-15T15:20:28.520166200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583699,581138,0,20,542176,'IsAllDay_Prev',TO_TIMESTAMP('2022-07-15 18:20:28','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'All day (previous)',0,0,TO_TIMESTAMP('2022-07-15 18:20:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-15T15:20:28.522166500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583699 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-15T15:20:28.524168Z
/* DDL */  select update_Column_Translation_From_AD_Element(581138) 
;

-- 2022-07-15T15:20:30.115639200Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource_Simulation','ALTER TABLE public.C_Project_WO_Resource_Simulation ADD COLUMN IsAllDay_Prev CHAR(1) DEFAULT ''N'' CHECK (IsAllDay_Prev IN (''Y'',''N'')) NOT NULL')
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Verarbeitet
-- Column: C_Project_WO_Resource_Simulation.Processed
-- 2022-07-15T15:21:15.151519Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583696,701927,0,546393,TO_TIMESTAMP('2022-07-15 18:21:14','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2022-07-15 18:21:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:21:15.154517900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701927 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T15:21:15.156518900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2022-07-15T15:21:15.239222Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701927
;

-- 2022-07-15T15:21:15.243218900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701927)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Zuordnung von (previous)
-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom_Prev
-- 2022-07-15T15:21:15.343642100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583697,701928,0,546393,TO_TIMESTAMP('2022-07-15 18:21:15','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab',7,'D','Beginn Zuordnung','Y','N','N','N','N','N','N','N','Zuordnung von (previous)',TO_TIMESTAMP('2022-07-15 18:21:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:21:15.345657400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701928 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T15:21:15.347658100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581136) 
;

-- 2022-07-15T15:21:15.349640100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701928
;

-- 2022-07-15T15:21:15.349640100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701928)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Zuordnung bis (previous)
-- Column: C_Project_WO_Resource_Simulation.AssignDateTo_Prev
-- 2022-07-15T15:21:15.442353900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583698,701929,0,546393,TO_TIMESTAMP('2022-07-15 18:21:15','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis',7,'D','Zuordnung endet','Y','N','N','N','N','N','N','N','Zuordnung bis (previous)',TO_TIMESTAMP('2022-07-15 18:21:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:21:15.446410300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701929 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T15:21:15.450362Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581137) 
;

-- 2022-07-15T15:21:15.455388900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701929
;

-- 2022-07-15T15:21:15.456358800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701929)
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> All day (previous)
-- Column: C_Project_WO_Resource_Simulation.IsAllDay_Prev
-- 2022-07-15T15:21:15.560322100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583699,701930,0,546393,TO_TIMESTAMP('2022-07-15 18:21:15','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','All day (previous)',TO_TIMESTAMP('2022-07-15 18:21:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:21:15.567316600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701930 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T15:21:15.571377400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581138) 
;

-- 2022-07-15T15:21:15.579325600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701930
;

-- 2022-07-15T15:21:15.580389900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701930)
;

-- 2022-07-15T15:22:01.564348800Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546095,549534,TO_TIMESTAMP('2022-07-15 18:22:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','processed + prev values',20,TO_TIMESTAMP('2022-07-15 18:22:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Verarbeitet
-- Column: C_Project_WO_Resource_Simulation.Processed
-- 2022-07-15T15:22:20.733149200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701927,0,546393,610374,549534,'F',TO_TIMESTAMP('2022-07-15 18:22:20','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2022-07-15 18:22:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Zuordnung von (previous)
-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom_Prev
-- 2022-07-15T15:22:30.745307400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701928,0,546393,610375,549534,'F',TO_TIMESTAMP('2022-07-15 18:22:30','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab','Beginn Zuordnung','Y','N','Y','N','N','Zuordnung von (previous)',20,0,0,TO_TIMESTAMP('2022-07-15 18:22:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Zuordnung bis (previous)
-- Column: C_Project_WO_Resource_Simulation.AssignDateTo_Prev
-- 2022-07-15T15:22:38.928535200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701929,0,546393,610376,549534,'F',TO_TIMESTAMP('2022-07-15 18:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis','Zuordnung endet','Y','N','Y','N','N','Zuordnung bis (previous)',30,0,0,TO_TIMESTAMP('2022-07-15 18:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.All day (previous)
-- Column: C_Project_WO_Resource_Simulation.IsAllDay_Prev
-- 2022-07-15T15:22:48.273508Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701930,0,546393,610377,549534,'F',TO_TIMESTAMP('2022-07-15 18:22:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','All day (previous)',40,0,0,TO_TIMESTAMP('2022-07-15 18:22:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Project Resource
-- Column: C_Project_WO_Resource_Simulation.C_Project_WO_Resource_ID
-- 2022-07-15T15:23:02.470491Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-07-15 18:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609646
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Zuordnung von
-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom
-- 2022-07-15T15:23:02.477474600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-07-15 18:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609647
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Zuordnung bis
-- Column: C_Project_WO_Resource_Simulation.AssignDateTo
-- 2022-07-15T15:23:02.482473900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-07-15 18:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609648
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.All day
-- Column: C_Project_WO_Resource_Simulation.IsAllDay
-- 2022-07-15T15:23:02.487474500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-07-15 18:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609649
;

-- UI Element: Simulation Plan -> WO Project Resource Simulation.Verarbeitet
-- Column: C_Project_WO_Resource_Simulation.Processed
-- 2022-07-15T15:23:02.491474400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-07-15 18:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610374
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Verarbeitet
-- Column: C_Project_WO_Resource_Simulation.Processed
-- 2022-07-15T15:23:14.989395Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 18:23:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701927
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Zuordnung von (previous)
-- Column: C_Project_WO_Resource_Simulation.AssignDateFrom_Prev
-- 2022-07-15T15:23:17.312194200Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 18:23:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701928
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> Zuordnung bis (previous)
-- Column: C_Project_WO_Resource_Simulation.AssignDateTo_Prev
-- 2022-07-15T15:23:18.886179Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 18:23:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701929
;

-- Field: Simulation Plan -> WO Project Resource Simulation -> All day (previous)
-- Column: C_Project_WO_Resource_Simulation.IsAllDay_Prev
-- 2022-07-15T15:23:22.658162200Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 18:23:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701930
;

-- 2022-07-15T15:31:15.297885800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581139,0,'DateStartPlan_Prev',TO_TIMESTAMP('2022-07-15 18:31:15','YYYY-MM-DD HH24:MI:SS'),100,'Planned Start Date','D','Date when you plan to start','Y','Start Plan (Previous)','Start Plan (Previous)',TO_TIMESTAMP('2022-07-15 18:31:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:31:15.299921Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581139 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-15T15:31:52.868963900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581140,0,'DateFinishPlan_Prev',TO_TIMESTAMP('2022-07-15 18:31:52','YYYY-MM-DD HH24:MI:SS'),100,'Planned Finish Date','D','Date when you plan to finish','Y','Finish Plan (Previous)','Finish Plan (Previous)',TO_TIMESTAMP('2022-07-15 18:31:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:31:52.876961100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581140 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Project_Resource_Budget_Simulation.DateStartPlan_Prev
-- 2022-07-15T15:32:17.101955400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583700,581139,0,16,542177,'DateStartPlan_Prev',TO_TIMESTAMP('2022-07-15 18:32:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Planned Start Date','D',0,7,'Date when you plan to start','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Start Plan (Previous)',0,0,TO_TIMESTAMP('2022-07-15 18:32:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-15T15:32:17.109981500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583700 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-15T15:32:17.119980900Z
/* DDL */  select update_Column_Translation_From_AD_Element(581139) 
;

-- 2022-07-15T15:32:21.846815Z
/* DDL */ SELECT public.db_alter_table('C_Project_Resource_Budget_Simulation','ALTER TABLE public.C_Project_Resource_Budget_Simulation ADD COLUMN DateStartPlan_Prev TIMESTAMP WITH TIME ZONE')
;

-- Column: C_Project_Resource_Budget_Simulation.DateFinishPlan_Prev
-- 2022-07-15T15:32:39.470020200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583701,581140,0,16,542177,'DateFinishPlan_Prev',TO_TIMESTAMP('2022-07-15 18:32:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Planned Finish Date','D',0,7,'Date when you plan to finish','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Finish Plan (Previous)',0,0,TO_TIMESTAMP('2022-07-15 18:32:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-15T15:32:39.476017800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583701 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-15T15:32:39.484026300Z
/* DDL */  select update_Column_Translation_From_AD_Element(581140) 
;

-- 2022-07-15T15:32:41.089107500Z
/* DDL */ SELECT public.db_alter_table('C_Project_Resource_Budget_Simulation','ALTER TABLE public.C_Project_Resource_Budget_Simulation ADD COLUMN DateFinishPlan_Prev TIMESTAMP WITH TIME ZONE')
;

-- Column: C_Project_Resource_Budget_Simulation.Processed
-- 2022-07-15T15:34:11.013938400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583702,1047,0,20,542177,'Processed',TO_TIMESTAMP('2022-07-15 18:34:10','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2022-07-15 18:34:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-15T15:34:11.017959500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583702 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-15T15:34:11.021964800Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- 2022-07-15T15:34:12.651803200Z
/* DDL */ SELECT public.db_alter_table('C_Project_Resource_Budget_Simulation','ALTER TABLE public.C_Project_Resource_Budget_Simulation ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Start Plan (Previous)
-- Column: C_Project_Resource_Budget_Simulation.DateStartPlan_Prev
-- 2022-07-15T15:34:27.496521200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583700,701931,0,546394,TO_TIMESTAMP('2022-07-15 18:34:27','YYYY-MM-DD HH24:MI:SS'),100,'Planned Start Date',7,'D','Date when you plan to start','Y','N','N','N','N','N','N','N','Start Plan (Previous)',TO_TIMESTAMP('2022-07-15 18:34:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:34:27.498551900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701931 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T15:34:27.500551700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581139) 
;

-- 2022-07-15T15:34:27.502514400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701931
;

-- 2022-07-15T15:34:27.503515300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701931)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Finish Plan (Previous)
-- Column: C_Project_Resource_Budget_Simulation.DateFinishPlan_Prev
-- 2022-07-15T15:34:27.612829100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583701,701932,0,546394,TO_TIMESTAMP('2022-07-15 18:34:27','YYYY-MM-DD HH24:MI:SS'),100,'Planned Finish Date',7,'D','Date when you plan to finish','Y','N','N','N','N','N','N','N','Finish Plan (Previous)',TO_TIMESTAMP('2022-07-15 18:34:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:34:27.618806800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701932 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T15:34:27.623807900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581140) 
;

-- 2022-07-15T15:34:27.630844900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701932
;

-- 2022-07-15T15:34:27.631813800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701932)
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Verarbeitet
-- Column: C_Project_Resource_Budget_Simulation.Processed
-- 2022-07-15T15:34:27.756149Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583702,701933,0,546394,TO_TIMESTAMP('2022-07-15 18:34:27','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2022-07-15 18:34:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:34:27.762189Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T15:34:27.768145300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2022-07-15T15:34:27.809278200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701933
;

-- 2022-07-15T15:34:27.809278200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701933)
;

-- 2022-07-15T15:34:49.252286600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546097,549535,TO_TIMESTAMP('2022-07-15 18:34:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','process + previous values',20,TO_TIMESTAMP('2022-07-15 18:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Verarbeitet
-- Column: C_Project_Resource_Budget_Simulation.Processed
-- 2022-07-15T15:35:01.624756300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701933,0,546394,610378,549535,'F',TO_TIMESTAMP('2022-07-15 18:35:01','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2022-07-15 18:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Start Plan (Previous)
-- Column: C_Project_Resource_Budget_Simulation.DateStartPlan_Prev
-- 2022-07-15T15:35:10.263393900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701931,0,546394,610379,549535,'F',TO_TIMESTAMP('2022-07-15 18:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Planned Start Date','Date when you plan to start','Y','N','Y','N','N','Start Plan (Previous)',20,0,0,TO_TIMESTAMP('2022-07-15 18:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Finish Plan (Previous)
-- Column: C_Project_Resource_Budget_Simulation.DateFinishPlan_Prev
-- 2022-07-15T15:35:23.475872300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701932,0,546394,610380,549535,'F',TO_TIMESTAMP('2022-07-15 18:35:23','YYYY-MM-DD HH24:MI:SS'),100,'Planned Finish Date','Date when you plan to finish','Y','N','Y','N','N','Finish Plan (Previous)',30,0,0,TO_TIMESTAMP('2022-07-15 18:35:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Project Resource Budget Simulation.Verarbeitet
-- Column: C_Project_Resource_Budget_Simulation.Processed
-- 2022-07-15T15:35:33.519127800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-07-15 18:35:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610378
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Start Plan (Previous)
-- Column: C_Project_Resource_Budget_Simulation.DateStartPlan_Prev
-- 2022-07-15T15:35:55.065624200Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 18:35:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701931
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Finish Plan (Previous)
-- Column: C_Project_Resource_Budget_Simulation.DateFinishPlan_Prev
-- 2022-07-15T15:35:56.690911200Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 18:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701932
;

-- Field: Simulation Plan -> Project Resource Budget Simulation -> Verarbeitet
-- Column: C_Project_Resource_Budget_Simulation.Processed
-- 2022-07-15T15:35:58.754410400Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 18:35:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701933
;

-- Column: C_Project_WO_Resource_Conflict.IsApproved
-- 2022-07-15T15:58:04.136044500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583703,351,0,20,542186,'IsApproved',TO_TIMESTAMP('2022-07-15 18:58:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Zeigt an, ob dieser Beleg eine Freigabe braucht','D',0,1,'Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Freigegeben',0,0,TO_TIMESTAMP('2022-07-15 18:58:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-15T15:58:04.138223300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583703 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-15T15:58:04.142221300Z
/* DDL */  select update_Column_Translation_From_AD_Element(351) 
;

-- 2022-07-15T15:58:05.935959100Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource_Conflict','ALTER TABLE public.C_Project_WO_Resource_Conflict ADD COLUMN IsApproved CHAR(1) DEFAULT ''N'' CHECK (IsApproved IN (''Y'',''N'')) NOT NULL')
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Freigegeben
-- Column: C_Project_WO_Resource_Conflict.IsApproved
-- 2022-07-15T15:58:44.338552600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583703,701934,0,546446,TO_TIMESTAMP('2022-07-15 18:58:44','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Beleg eine Freigabe braucht',1,'D','Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','N','N','N','N','N','N','N','Freigegeben',TO_TIMESTAMP('2022-07-15 18:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T15:58:44.347629300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701934 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T15:58:44.359667800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(351) 
;

-- 2022-07-15T15:58:44.500450500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701934
;

-- 2022-07-15T15:58:44.503450500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701934)
;

-- UI Element: Work Order Resource Conflict -> Work Order Resource Conflict.Freigegeben
-- Column: C_Project_WO_Resource_Conflict.IsApproved
-- 2022-07-15T15:59:14.856026900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701934,0,546446,610381,549526,'F',TO_TIMESTAMP('2022-07-15 18:59:14','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Beleg eine Freigabe braucht','Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','N','Y','N','N','Freigegeben',20,0,0,TO_TIMESTAMP('2022-07-15 18:59:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Work Order Resource Conflict -> Work Order Resource Conflict
-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T16:40:41.646263500Z
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2022-07-15 19:40:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546446
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Sektion
-- Column: C_Project_WO_Resource_Conflict.AD_Org_ID
-- 2022-07-15T16:41:16.092786700Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 19:41:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701902
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Aktiv
-- Column: C_Project_WO_Resource_Conflict.IsActive
-- 2022-07-15T16:41:24.546513Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 19:41:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701903
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Work Order Resource Conflict
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_Conflict_ID
-- 2022-07-15T16:41:28.552950400Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 19:41:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701904
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project_ID
-- 2022-07-15T16:41:30.212110600Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 19:41:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701905
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T16:41:32.328Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 19:41:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701906
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project2_ID
-- 2022-07-15T16:41:34.024923600Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 19:41:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701907
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource2_ID
-- 2022-07-15T16:41:35.510937900Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 19:41:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701908
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Simulation Plan
-- Column: C_Project_WO_Resource_Conflict.C_SimulationPlan_ID
-- 2022-07-15T16:41:37.892719400Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 19:41:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701909
;

-- Field: Work Order Resource Conflict -> Work Order Resource Conflict -> Status
-- Column: C_Project_WO_Resource_Conflict.Status
-- 2022-07-15T16:41:40.962192200Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 19:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701910
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Freigegeben
-- Column: C_Project_WO_Resource_Conflict.IsApproved
-- 2022-07-15T20:24:14.692680500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583703,701935,0,546448,TO_TIMESTAMP('2022-07-15 23:24:14','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Beleg eine Freigabe braucht',1,'D','Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','N','N','N','N','N','N','N','Freigegeben',TO_TIMESTAMP('2022-07-15 23:24:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-15T20:24:14.695679Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=701935 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-15T20:24:14.723761200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(351) 
;

-- 2022-07-15T20:24:14.772486Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=701935
;

-- 2022-07-15T20:24:14.778281100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(701935)
;

-- Tab: Simulation Plan -> Work Order Resource Conflict
-- Table: C_Project_WO_Resource_Conflict
-- 2022-07-15T20:24:24.717278500Z
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2022-07-15 23:24:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546448
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Sektion
-- Column: C_Project_WO_Resource_Conflict.AD_Org_ID
-- 2022-07-15T20:24:31.784022700Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 23:24:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701912
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Aktiv
-- Column: C_Project_WO_Resource_Conflict.IsActive
-- 2022-07-15T20:24:33.332133300Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 23:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701913
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Work Order Resource Conflict
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_Conflict_ID
-- 2022-07-15T20:24:35.517818300Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 23:24:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701914
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project_ID
-- 2022-07-15T20:24:36.982202100Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 23:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701915
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource_ID
-- 2022-07-15T20:24:38.379018100Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 23:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701916
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Projekt
-- Column: C_Project_WO_Resource_Conflict.C_Project2_ID
-- 2022-07-15T20:24:39.696954100Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 23:24:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701917
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Project Resource
-- Column: C_Project_WO_Resource_Conflict.C_Project_WO_Resource2_ID
-- 2022-07-15T20:24:40.958678300Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 23:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701918
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Simulation Plan
-- Column: C_Project_WO_Resource_Conflict.C_SimulationPlan_ID
-- 2022-07-15T20:24:42.530266700Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 23:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701919
;

-- Field: Simulation Plan -> Work Order Resource Conflict -> Status
-- Column: C_Project_WO_Resource_Conflict.Status
-- 2022-07-15T20:24:44.085085900Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-07-15 23:24:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=701920
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Freigegeben
-- Column: C_Project_WO_Resource_Conflict.IsApproved
-- 2022-07-15T20:25:04.413289400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,701935,0,546448,610382,549531,'F',TO_TIMESTAMP('2022-07-15 23:25:04','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Beleg eine Freigabe braucht','Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','N','Y','N','N','Freigegeben',20,0,0,TO_TIMESTAMP('2022-07-15 23:25:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Simulation Plan -> Work Order Resource Conflict.Freigegeben
-- Column: C_Project_WO_Resource_Conflict.IsApproved
-- 2022-07-15T20:26:22.862157Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-07-15 23:26:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610382
;

