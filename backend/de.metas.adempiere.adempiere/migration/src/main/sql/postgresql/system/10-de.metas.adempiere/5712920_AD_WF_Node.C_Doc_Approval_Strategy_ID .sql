-- 2023-12-08T07:46:46.113211800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720322
;

-- Field: Workflow(113,D) -> Knoten(122,D) -> Approval Strategy
-- Column: AD_WF_Node.ApprovalStrategy
-- 2023-12-08T07:46:46.117204500Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=720322
;

-- 2023-12-08T07:46:46.124517400Z
DELETE FROM AD_Field WHERE AD_Field_ID=720322
;

-- 2023-12-08T07:46:46.128857400Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Node','ALTER TABLE AD_WF_Node DROP COLUMN IF EXISTS ApprovalStrategy')
;

-- Column: AD_WF_Node.ApprovalStrategy
-- 2023-12-08T07:46:46.155417500Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587351
;

-- 2023-12-08T07:46:46.161790100Z
DELETE FROM AD_Column WHERE AD_Column_ID=587351
;

-- Column: AD_WF_Node.C_Doc_Approval_Strategy_ID
-- 2023-12-08T07:47:09.243149100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587716,582841,0,30,129,'C_Doc_Approval_Strategy_ID',TO_TIMESTAMP('2023-12-08 09:47:09.105','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Document Approval Strategy',0,0,TO_TIMESTAMP('2023-12-08 09:47:09.105','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-08T07:47:09.245725900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587716 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-08T07:47:09.765607100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582841) 
;

-- 2023-12-08T07:47:11.337714500Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Node','ALTER TABLE public.AD_WF_Node ADD COLUMN C_Doc_Approval_Strategy_ID NUMERIC(10)')
;

-- 2023-12-08T07:47:11.346625500Z
ALTER TABLE AD_WF_Node ADD CONSTRAINT CDocApprovalStrategy_ADWFNode FOREIGN KEY (C_Doc_Approval_Strategy_ID) REFERENCES public.C_Doc_Approval_Strategy DEFERRABLE INITIALLY DEFERRED
;

-- Field: Workflow(113,D) -> Knoten(122,D) -> Document Approval Strategy
-- Column: AD_WF_Node.C_Doc_Approval_Strategy_ID
-- 2023-12-08T07:50:23.639635900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587716,723139,0,122,TO_TIMESTAMP('2023-12-08 09:50:23.49','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Document Approval Strategy',TO_TIMESTAMP('2023-12-08 09:50:23.49','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-08T07:50:23.641615Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723139 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-08T07:50:23.643614900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582841) 
;

-- 2023-12-08T07:50:23.645615400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723139
;

-- 2023-12-08T07:50:23.647619600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723139)
;

-- Field: Workflow(113,D) -> Knoten(122,D) -> Document Approval Strategy
-- Column: AD_WF_Node.C_Doc_Approval_Strategy_ID
-- 2023-12-08T07:52:42.959764200Z
UPDATE AD_Field SET DisplayLogic='@Action@=C', SeqNo=235,Updated=TO_TIMESTAMP('2023-12-08 09:52:42.959','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723139
;

