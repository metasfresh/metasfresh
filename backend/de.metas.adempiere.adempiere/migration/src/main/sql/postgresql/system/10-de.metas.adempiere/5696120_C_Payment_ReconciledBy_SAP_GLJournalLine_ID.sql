-- 2023-07-19T05:29:03.569Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582564,0,'ReconciledBy_SAP_GLJournal_ID',TO_TIMESTAMP('2023-07-19 08:29:03','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Reconciled by GL Journal','Reconciled by GL Journal',TO_TIMESTAMP('2023-07-19 08:29:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T05:29:03.593Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582564 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-07-19T05:29:19.121Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582565,0,'ReconciledBy_SAP_GLJournalLine_ID',TO_TIMESTAMP('2023-07-19 08:29:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reconciled by GL Journal Line','Reconciled by GL Journal Line',TO_TIMESTAMP('2023-07-19 08:29:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T05:29:19.125Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582565 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-07-19T05:29:22.945Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2023-07-19 08:29:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582564
;

-- 2023-07-19T05:29:23.047Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582564,'en_US') 
;

-- Name: SAP_GLJournal
-- 2023-07-19T05:30:01.163Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541803,TO_TIMESTAMP('2023-07-19 08:30:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','SAP_GLJournal',TO_TIMESTAMP('2023-07-19 08:30:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-07-19T05:30:01.174Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541803 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Name: SAP_GLJournal
-- 2023-07-19T05:30:04.675Z
UPDATE AD_Reference SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2023-07-19 08:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541803
;

-- Name: SAP_GLJournal
-- 2023-07-19T05:30:08.560Z
UPDATE AD_Reference SET EntityType='D',Updated=TO_TIMESTAMP('2023-07-19 08:30:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541803
;

-- Reference: SAP_GLJournal
-- Table: SAP_GLJournal
-- Key: SAP_GLJournal.SAP_GLJournal_ID
-- 2023-07-19T05:30:18.165Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,585351,0,541803,542275,TO_TIMESTAMP('2023-07-19 08:30:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-19 08:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Payment.ReconciledBy_SAP_GLJournal_ID
-- 2023-07-19T05:30:27.585Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587136,582564,0,30,541803,335,'XX','ReconciledBy_SAP_GLJournal_ID',TO_TIMESTAMP('2023-07-19 08:30:27','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Reconciled by GL Journal',0,0,TO_TIMESTAMP('2023-07-19 08:30:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T05:30:27.601Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587136 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T05:30:28.605Z
/* DDL */  select update_Column_Translation_From_AD_Element(582564) 
;

-- 2023-07-19T05:30:33.450Z
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE public.C_Payment ADD COLUMN ReconciledBy_SAP_GLJournal_ID NUMERIC(10)')
;

-- 2023-07-19T05:30:34.765Z
ALTER TABLE C_Payment ADD CONSTRAINT ReconciledBySAPGLJournal_CPayment FOREIGN KEY (ReconciledBy_SAP_GLJournal_ID) REFERENCES public.SAP_GLJournal DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Payment.ReconciledBy_SAP_GLJournalLine_ID
-- 2023-07-19T05:31:05.778Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587137,582565,0,30,541700,335,'XX','ReconciledBy_SAP_GLJournalLine_ID',TO_TIMESTAMP('2023-07-19 08:31:05','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Reconciled by GL Journal Line',0,0,TO_TIMESTAMP('2023-07-19 08:31:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T05:31:05.781Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587137 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T05:31:06.739Z
/* DDL */  select update_Column_Translation_From_AD_Element(582565) 
;

-- 2023-07-19T05:31:08.063Z
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE public.C_Payment ADD COLUMN ReconciledBy_SAP_GLJournalLine_ID NUMERIC(10)')
;

-- 2023-07-19T05:31:08.785Z
ALTER TABLE C_Payment ADD CONSTRAINT ReconciledBySAPGLJournalLine_CPayment FOREIGN KEY (ReconciledBy_SAP_GLJournalLine_ID) REFERENCES public.SAP_GLJournalLine DEFERRABLE INITIALLY DEFERRED
;

-- Field: Payment(195,D) -> Payment(330,D) -> Reconciled by GL Journal
-- Column: C_Payment.ReconciledBy_SAP_GLJournal_ID
-- 2023-07-19T06:20:50.970Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587136,716708,0,330,TO_TIMESTAMP('2023-07-19 09:20:50','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Reconciled by GL Journal',TO_TIMESTAMP('2023-07-19 09:20:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T06:20:50.981Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716708 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T06:20:50.988Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582564) 
;

-- 2023-07-19T06:20:51.017Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716708
;

-- 2023-07-19T06:20:51.026Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716708)
;

-- Field: Payment(195,D) -> Payment(330,D) -> Reconciled by GL Journal Line
-- Column: C_Payment.ReconciledBy_SAP_GLJournalLine_ID
-- 2023-07-19T06:20:56.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587137,716709,0,330,TO_TIMESTAMP('2023-07-19 09:20:56','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Reconciled by GL Journal Line',TO_TIMESTAMP('2023-07-19 09:20:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T06:20:56.418Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716709 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T06:20:56.424Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582565) 
;

-- 2023-07-19T06:20:56.435Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716709
;

-- 2023-07-19T06:20:56.439Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716709)
;

-- UI Element: Payment(195,D) -> Payment(330,D) -> main -> 20 -> reconciled.Reconciled by GL Journal
-- Column: C_Payment.ReconciledBy_SAP_GLJournal_ID
-- 2023-07-19T06:21:33.066Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716708,0,330,543698,618289,'F',TO_TIMESTAMP('2023-07-19 09:21:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Reconciled by GL Journal',60,0,0,TO_TIMESTAMP('2023-07-19 09:21:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Payment(195,D) -> Payment(330,D) -> main -> 20 -> reconciled.Reconciled by GL Journal Line
-- Column: C_Payment.ReconciledBy_SAP_GLJournalLine_ID
-- 2023-07-19T06:21:40.220Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716709,0,330,543698,618290,'F',TO_TIMESTAMP('2023-07-19 09:21:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Reconciled by GL Journal Line',70,0,0,TO_TIMESTAMP('2023-07-19 09:21:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Payment(195,D) -> Payment(330,D) -> Reconciled by GL Journal
-- Column: C_Payment.ReconciledBy_SAP_GLJournal_ID
-- 2023-07-19T06:22:20.739Z
UPDATE AD_Field SET DisplayLogic='@ReconciledBy_SAP_GLJournal_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-19 09:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716708
;

-- Field: Payment(195,D) -> Payment(330,D) -> Reconciled by GL Journal Line
-- Column: C_Payment.ReconciledBy_SAP_GLJournalLine_ID
-- 2023-07-19T06:22:25.958Z
UPDATE AD_Field SET DisplayLogic='@ReconciledBy_SAP_GLJournalLine_ID/0@>0',Updated=TO_TIMESTAMP('2023-07-19 09:22:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716709
;

-- Field: Payment(195,D) -> Payment(330,D) -> Reconciled by GL Journal
-- Column: C_Payment.ReconciledBy_SAP_GLJournal_ID
-- 2023-07-19T06:23:01.144Z
UPDATE AD_Field SET DisplayLogic='@IsReconciled/N@=Y & @ReconciledBy_SAP_GLJournal_ID/0@>0',Updated=TO_TIMESTAMP('2023-07-19 09:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716708
;

-- Field: Payment(195,D) -> Payment(330,D) -> Reconciled by GL Journal Line
-- Column: C_Payment.ReconciledBy_SAP_GLJournalLine_ID
-- 2023-07-19T06:23:04.534Z
UPDATE AD_Field SET DisplayLogic='@IsReconciled/N@=Y & @ReconciledBy_SAP_GLJournalLine_ID/0@>0',Updated=TO_TIMESTAMP('2023-07-19 09:23:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716709
;

-- Field: Payment(195,D) -> Payment(330,D) -> Reconciled by GL Journal Line
-- Column: C_Payment.ReconciledBy_SAP_GLJournalLine_ID
-- 2023-07-19T06:23:14.551Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-19 09:23:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716709
;

