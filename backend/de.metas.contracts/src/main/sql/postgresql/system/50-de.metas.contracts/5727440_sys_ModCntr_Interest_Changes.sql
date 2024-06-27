-- Run mode: SWING_CLIENT

-- Column: ModCntr_Interest.ShippingNotification_ModCntr_Log_ID
-- 2024-06-27T12:54:50.123Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-06-27 15:54:50.123','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588268
;

-- 2024-06-27T12:55:51.975Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583155,0,'InterimContract_ModCntr_Log_ID',TO_TIMESTAMP('2024-06-27 15:55:51.853','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Interim Contract Log','Interim Contract Log',TO_TIMESTAMP('2024-06-27 15:55:51.853','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-27T12:55:51.980Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583155 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ModCntr_Interest.InterimContract_ModCntr_Log_ID
-- 2024-06-27T13:12:42.136Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588629,583155,0,30,541775,542410,'InterimContract_ModCntr_Log_ID',TO_TIMESTAMP('2024-06-27 16:12:41.941','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Interim Contract Log',0,0,TO_TIMESTAMP('2024-06-27 16:12:41.941','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-06-27T13:12:42.138Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588629 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-27T13:12:42.173Z
/* DDL */  select update_Column_Translation_From_AD_Element(583155)
;

-- 2024-06-27T13:12:43.357Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE public.ModCntr_Interest ADD COLUMN InterimContract_ModCntr_Log_ID NUMERIC(10)')
;

-- 2024-06-27T13:12:43.565Z
ALTER TABLE ModCntr_Interest ADD CONSTRAINT InterimContractModCntrLog_ModCntrInterest FOREIGN KEY (InterimContract_ModCntr_Log_ID) REFERENCES public.ModCntr_Log DEFERRABLE INITIALLY DEFERRED
;

