-- 2023-08-04T05:11:01.560Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582630,0,'SAP_CalculateTax',TO_TIMESTAMP('2023-08-04 08:11:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Calculate tax','Calculate tax',TO_TIMESTAMP('2023-08-04 08:11:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-08-04T05:11:01.562Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582630 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SAP_GLJournalLine.SAP_CalculateTax
-- 2023-08-04T05:11:19.343Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587246,582630,0,20,542276,'XX','SAP_CalculateTax',TO_TIMESTAMP('2023-08-04 08:11:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.acct',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Calculate tax',0,0,TO_TIMESTAMP('2023-08-04 08:11:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-08-04T05:11:19.345Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587246 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-04T05:11:20.007Z
/* DDL */  select update_Column_Translation_From_AD_Element(582630) 
;

-- 2023-08-04T05:11:21.400Z
/* DDL */ SELECT public.db_alter_table('SAP_GLJournalLine','ALTER TABLE public.SAP_GLJournalLine ADD COLUMN SAP_CalculateTax CHAR(1) DEFAULT ''N'' CHECK (SAP_CalculateTax IN (''Y'',''N'')) NOT NULL')
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Calculate tax
-- Column: SAP_GLJournalLine.SAP_CalculateTax
-- 2023-08-04T05:12:13.744Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587246,718617,0,546731,TO_TIMESTAMP('2023-08-04 08:12:13','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.acct','Y','N','N','N','N','N','N','N','Calculate tax',TO_TIMESTAMP('2023-08-04 08:12:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-08-04T05:12:13.746Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=718617 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-04T05:12:13.749Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582630) 
;

-- 2023-08-04T05:12:13.762Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=718617
;

-- 2023-08-04T05:12:13.763Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(718617)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> tax.Calculate tax
-- Column: SAP_GLJournalLine.SAP_CalculateTax
-- 2023-08-04T05:12:27.773Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,718617,0,546731,550195,619282,'F',TO_TIMESTAMP('2023-08-04 08:12:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Calculate tax',40,0,0,TO_TIMESTAMP('2023-08-04 08:12:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Calculate tax
-- Column: SAP_GLJournalLine.SAP_CalculateTax
-- 2023-08-04T05:12:45.929Z
UPDATE AD_Field SET DisplayLogic='@C_Tax_ID/0@>0',Updated=TO_TIMESTAMP('2023-08-04 08:12:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=718617
;

