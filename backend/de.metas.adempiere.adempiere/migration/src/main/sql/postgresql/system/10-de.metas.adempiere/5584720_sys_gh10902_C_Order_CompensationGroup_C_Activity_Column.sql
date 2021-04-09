-- 2021-04-05T07:48:35.385Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573341,1005,0,30,540856,'C_Activity_ID',TO_TIMESTAMP('2021-04-05 10:48:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Kostenstelle','D',0,10,'Erfassung der zugehörigen Kostenstelle','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenstelle',0,0,TO_TIMESTAMP('2021-04-05 10:48:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-04-05T07:48:35.431Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573341 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-04-05T07:48:35.494Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(1005) 
;

-- 2021-04-05T07:48:57.986Z
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('C_Order_CompensationGroup','ALTER TABLE public.C_Order_CompensationGroup ADD COLUMN C_Activity_ID NUMERIC(10)')
;

-- 2021-04-05T07:48:58.048Z
-- #298 changing anz. stellen
ALTER TABLE C_Order_CompensationGroup ADD CONSTRAINT CActivity_COrderCompensationGroup FOREIGN KEY (C_Activity_ID) REFERENCES public.C_Activity DEFERRABLE INITIALLY DEFERRED
;

