
-- 2022-12-08T12:34:45.820Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581843,0,'IsMainSimulation',TO_TIMESTAMP('2022-12-08 14:34:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hauptsimulation','Hauptsimulation',TO_TIMESTAMP('2022-12-08 14:34:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-08T12:34:45.826Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581843 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-12-08T12:35:02.281Z
UPDATE AD_Element_Trl SET Name='Main simulation', PrintName='Main simulation',Updated=TO_TIMESTAMP('2022-12-08 14:35:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581843 AND AD_Language='en_US'
;

-- 2022-12-08T12:35:02.319Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581843,'en_US') 
;

-- Column: C_SimulationPlan.IsMainSimulation
-- 2022-12-08T12:36:18.312Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585277,581843,0,20,542173,'IsMainSimulation',TO_TIMESTAMP('2022-12-08 14:36:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Hauptsimulation',0,0,TO_TIMESTAMP('2022-12-08 14:36:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-08T12:36:18.316Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585277 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-08T12:36:18.321Z
/* DDL */  select update_Column_Translation_From_AD_Element(581843) 
;

-- 2022-12-08T12:36:20.647Z
/* DDL */ SELECT public.db_alter_table('C_SimulationPlan','ALTER TABLE public.C_SimulationPlan ADD COLUMN IsMainSimulation CHAR(1) DEFAULT ''Y'' CHECK (IsMainSimulation IN (''Y'',''N'')) NOT NULL')
;

