-- 2025-02-14T17:31:24.737Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583488,0,'Triggering_User_ID',TO_TIMESTAMP('2025-02-14 17:31:24.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Triggering User ID','Triggering User ID',TO_TIMESTAMP('2025-02-14 17:31:24.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-14T17:31:24.740Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583488 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule_Event.Triggering_User_ID
-- 2025-02-14T17:31:50.590Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589671,583488,0,30,110,542459,'XX','Triggering_User_ID',TO_TIMESTAMP('2025-02-14 17:31:50.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Triggering User ID',0,0,TO_TIMESTAMP('2025-02-14 17:31:50.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-14T17:31:50.593Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589671 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-14T17:31:50.596Z
/* DDL */  select update_Column_Translation_From_AD_Element(583488)
;

-- Column: AD_BusinessRule_Event.Triggering_User_ID
-- 2025-02-14T17:31:54.791Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-02-14 17:31:54.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589671
;

-- Column: AD_BusinessRule_Event.Triggering_User_ID
-- 2025-02-14T17:32:05.427Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-02-14 17:32:05.427000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589671
;

-- 2025-02-14T17:32:05.962Z
/* DDL */ SELECT public.db_alter_table('AD_BusinessRule_Event','ALTER TABLE public.AD_BusinessRule_Event ADD COLUMN Triggering_User_ID NUMERIC(10)')
;

-- 2025-02-14T17:32:05.970Z
ALTER TABLE AD_BusinessRule_Event ADD CONSTRAINT TriggeringUser_ADBusinessRuleEvent FOREIGN KEY (Triggering_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Column: AD_BusinessRule_Event.Triggering_User_ID
-- 2025-02-14T17:33:19.150Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-02-14 17:33:19.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589671
;

-- 2025-02-14T17:33:19.638Z
INSERT INTO t_alter_column values('ad_businessrule_event','Triggering_User_ID','NUMERIC(10)',null,null)
;


