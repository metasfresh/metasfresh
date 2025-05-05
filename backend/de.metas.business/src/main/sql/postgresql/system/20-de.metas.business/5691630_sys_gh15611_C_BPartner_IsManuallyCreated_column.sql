-- 2023-06-15T07:39:50.045401400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582439,0,'IsManuallyCreated',TO_TIMESTAMP('2023-06-15 10:39:49.845','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Manually created','Manually created',TO_TIMESTAMP('2023-06-15 10:39:49.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-15T07:39:50.446823900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582439 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsManuallyCreated
-- 2023-06-15T07:40:02.071976500Z
UPDATE AD_Element_Trl SET Name='Manuell erstellt', PrintName='Manuell erstellt',Updated=TO_TIMESTAMP('2023-06-15 10:40:02.071','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582439 AND AD_Language='de_CH'
;

-- 2023-06-15T07:40:02.111809600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582439,'de_CH') 
;

-- Element: IsManuallyCreated
-- 2023-06-15T07:40:05.625538600Z
UPDATE AD_Element_Trl SET Name='Manuell erstellt', PrintName='Manuell erstellt',Updated=TO_TIMESTAMP('2023-06-15 10:40:05.625','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582439 AND AD_Language='de_DE'
;

-- 2023-06-15T07:40:05.628016600Z
UPDATE AD_Element SET Name='Manuell erstellt', PrintName='Manuell erstellt' WHERE AD_Element_ID=582439
;

-- 2023-06-15T07:40:06.370324200Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582439,'de_DE') 
;

-- 2023-06-15T07:40:06.378321200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582439,'de_DE') 
;

-- Element: IsManuallyCreated
-- 2023-06-15T07:40:18.430717500Z
UPDATE AD_Element_Trl SET Name='Manuell erstellt', PrintName='Manuell erstellt',Updated=TO_TIMESTAMP('2023-06-15 10:40:18.429','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582439 AND AD_Language='fr_CH'
;

-- 2023-06-15T07:40:18.431715100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582439,'fr_CH') 
;


-- Column: C_BPartner.IsManuallyCreated
-- 2023-06-15T07:40:44.436217400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586816,582439,0,20,291,'IsManuallyCreated',TO_TIMESTAMP('2023-06-15 10:40:44.289','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Manuell erstellt',0,0,TO_TIMESTAMP('2023-06-15 10:40:44.289','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-15T07:40:44.438266200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586816 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-15T07:40:44.934252800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582439) 
;

-- 2023-06-15T07:40:47.628284100Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsManuallyCreated CHAR(1) DEFAULT ''N'' CHECK (IsManuallyCreated IN (''Y'',''N'')) NOT NULL')
;

