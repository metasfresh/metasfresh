-- 2019-12-11T12:29:53.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=53485, Parent_Column_ID=53659, TabLevel=1,Updated=TO_TIMESTAMP('2019-12-11 14:29:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53036
;

-- 2019-12-11T12:32:59.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=44,Updated=TO_TIMESTAMP('2019-12-11 14:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53036
;

-- 2019-12-11T12:33:02.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=55,Updated=TO_TIMESTAMP('2019-12-11 14:33:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53213
;

-- 2019-12-11T12:37:34.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=24,Updated=TO_TIMESTAMP('2019-12-11 14:37:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53036
;

-- 2019-12-11T12:37:49.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=45,Updated=TO_TIMESTAMP('2019-12-11 14:37:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=53213
;

-- 2019-12-11T12:54:30.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569744,577407,0,19,53022,'AD_WF_Node_Template_ID',TO_TIMESTAMP('2019-12-11 14:54:30','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Workflow Steps Template',0,0,TO_TIMESTAMP('2019-12-11 14:54:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-12-11T12:54:30.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569744 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-11T12:54:30.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577407) 
;

-- 2019-12-11T12:54:34.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Order_Node','ALTER TABLE public.PP_Order_Node ADD COLUMN AD_WF_Node_Template_ID NUMERIC(10)')
;

-- 2019-12-11T12:54:34.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Order_Node ADD CONSTRAINT ADWFNodeTemplate_PPOrderNode FOREIGN KEY (AD_WF_Node_Template_ID) REFERENCES public.AD_WF_Node_Template DEFERRABLE INITIALLY DEFERRED
;

-- 2019-12-11T13:12:44.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569744,593771,0,53036,0,TO_TIMESTAMP('2019-12-11 15:12:44','YYYY-MM-DD HH24:MI:SS'),100,0,'EE01',0,'Y','Y','Y','N','N','N','N','N','Workflow Steps Template',380,380,0,1,1,TO_TIMESTAMP('2019-12-11 15:12:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-11T13:12:44.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-12-11T13:12:44.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577407) 
;

-- 2019-12-11T13:12:44.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593771
;

-- 2019-12-11T13:12:44.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593771)
;

-- 2019-12-11T13:13:09.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLength=10,Updated=TO_TIMESTAMP('2019-12-11 15:13:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593771
;

-- 2019-12-11T13:14:59.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLength=0, IsSameLine='Y',Updated=TO_TIMESTAMP('2019-12-11 15:14:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593771
;

-- 2019-12-11T14:05:39.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=564588
;

-- 2019-12-11T14:05:39.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593488
;

-- 2019-12-11T14:05:39.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=593488
;

-- 2019-12-11T14:05:39.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=593488
;

-- 2019-12-11T14:12:29.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=569712
;

-- 2019-12-11T14:12:29.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=569712
;

-- 2019-12-11T14:12:33.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_workflow','AD_Workflow_ID','NUMERIC(10)',null,null)
;

-- 2019-12-11T14:15:15.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_workflow','PP_Order_Workflow_ID','NUMERIC(10)',null,null)
;

-- 2019-12-11T14:15:37.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_workflow','PP_Order_Workflow_ID','NUMERIC(10)',null,null)
;

-- 2019-12-11T14:21:38.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_order_workflow','PP_Order_Workflow_ID','NUMERIC(10)',null,null)
;

-- 2019-12-11T14:22:39.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE pp_order_workflow
DROP column ad_wf_node_template_id
;

select role_access_update();
select dba_seq_check_native();