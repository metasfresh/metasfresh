TRUNCATE table i_modcntr_log;

-- Column: I_ModCntr_Log.C_Calendar_ID
-- 2023-11-03T11:49:36.075Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587629,190,0,19,542372,'C_Calendar_ID',TO_TIMESTAMP('2023-11-03 13:49:35.823','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Bezeichnung des Buchführungs-Kalenders','de.metas.contracts',0,10,'"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kalender',0,0,TO_TIMESTAMP('2023-11-03 13:49:35.823','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-11-03T11:49:36.085Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587629 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-03T11:49:36.118Z
/* DDL */  select update_Column_Translation_From_AD_Element(190)
;

-- 2023-11-03T11:49:38.791Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN C_Calendar_ID NUMERIC(10)')
;

-- 2023-11-03T11:49:38.800Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT CCalendar_IModCntrLog FOREIGN KEY (C_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- 2023-11-03T11:50:36.915Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582789,0,'CalendarName',TO_TIMESTAMP('2023-11-03 13:50:36.781','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Kalendername','Kalendername',TO_TIMESTAMP('2023-11-03 13:50:36.781','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-03T11:50:36.917Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582789 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CalendarName
-- 2023-11-03T11:50:53.928Z
UPDATE AD_Element_Trl SET Name='Calendar name', PrintName='Calendar name',Updated=TO_TIMESTAMP('2023-11-03 13:50:53.928','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582789 AND AD_Language='en_US'
;

-- 2023-11-03T11:50:53.931Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582789,'en_US')
;

-- Column: I_ModCntr_Log.CalendarName
-- 2023-11-03T11:55:40.938Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587630,582789,0,10,542372,'CalendarName',TO_TIMESTAMP('2023-11-03 13:55:40.803','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,60,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Kalendername',0,0,TO_TIMESTAMP('2023-11-03 13:55:40.803','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-11-03T11:55:40.939Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587630 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-03T11:55:40.942Z
/* DDL */  select update_Column_Translation_From_AD_Element(582789)
;

-- 2023-11-03T11:55:45.242Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN CalendarName VARCHAR(60)')
;

-- Column: I_ModCntr_Log.CalendarName
-- 2023-11-03T11:59:06.167Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-11-03 13:59:06.167','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587630
;

-- 2023-11-03T11:59:07.908Z
INSERT INTO t_alter_column values('i_modcntr_log','CalendarName','VARCHAR(60)',null,null)
;

-- 2023-11-03T11:59:07.912Z
INSERT INTO t_alter_column values('i_modcntr_log','CalendarName',null,'NOT NULL',null)
;

-- Reference: Modular Contract Type Handler
-- Value: ImportLog
-- ValueName: ImportLog
-- 2023-11-03T12:02:29.569Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543592,TO_TIMESTAMP('2023-11-03 14:02:29.409','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Import Log',TO_TIMESTAMP('2023-11-03 14:02:29.409','YYYY-MM-DD HH24:MI:SS.US'),100,'ImportLog','ImportLog')
;

-- 2023-11-03T12:02:29.570Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543592 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

