-- Column: I_Invoice_Candidate.C_Activity_ID
-- Column: I_Invoice_Candidate.C_Activity_ID
-- 2023-02-21T10:23:54.681Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586205,1005,0,19,542207,'C_Activity_ID',TO_TIMESTAMP('2023-02-21 12:23:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Kostenstelle','D',0,10,'Erfassung der zugehörigen Kostenstelle','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenstelle',0,0,TO_TIMESTAMP('2023-02-21 12:23:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-21T10:23:54.685Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586205 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-21T10:23:54.711Z
/* DDL */  select update_Column_Translation_From_AD_Element(1005) 
;

-- 2023-02-21T10:23:55.781Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN C_Activity_ID NUMERIC(10)')
;

-- 2023-02-21T10:23:55.795Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT CActivity_IInvoiceCandidate FOREIGN KEY (C_Activity_ID) REFERENCES public.C_Activity DEFERRABLE INITIALLY DEFERRED
;

-- 2023-02-21T10:24:14.900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582092,0,'C_Activity_Value',TO_TIMESTAMP('2023-02-21 12:24:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kostenstelle Suchschlüssel','Kostenstelle Suchschlüssel',TO_TIMESTAMP('2023-02-21 12:24:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T10:24:14.907Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582092 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_Activity_Value
-- 2023-02-21T10:24:28.134Z
UPDATE AD_Element_Trl SET Name='Activity search key', PrintName='Activity search key',Updated=TO_TIMESTAMP('2023-02-21 12:24:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582092 AND AD_Language='en_US'
;

-- 2023-02-21T10:24:28.136Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582092,'en_US') 
;

-- Column: I_Invoice_Candidate.C_Activity_Value
-- Column: I_Invoice_Candidate.C_Activity_Value
-- 2023-02-21T10:24:44.296Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586206,582092,0,10,542207,'C_Activity_Value',TO_TIMESTAMP('2023-02-21 12:24:44','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenstelle Suchschlüssel',0,0,TO_TIMESTAMP('2023-02-21 12:24:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-21T10:24:44.299Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586206 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-21T10:24:44.302Z
/* DDL */  select update_Column_Translation_From_AD_Element(582092) 
;

-- 2023-02-21T10:24:45.145Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN C_Activity_Value VARCHAR(255)')
;

-- 2023-02-21T10:28:06.977Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,586206,540077,541826,0,TO_TIMESTAMP('2023-02-21 12:28:06','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N','Y','Kostenstelle',220,TO_TIMESTAMP('2023-02-21 12:28:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T10:28:13.089Z
UPDATE AD_ImpFormat_Row SET StartNo=22,Updated=TO_TIMESTAMP('2023-02-21 12:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541826
;

-- Field: Import - Rechnungskandidaten -> Import - Invoice candiate -> Kostenstelle
-- Column: I_Invoice_Candidate.C_Activity_ID
-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Kostenstelle
-- Column: I_Invoice_Candidate.C_Activity_ID
-- 2023-02-21T14:05:29.889Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586205,712691,0,546594,0,TO_TIMESTAMP('2023-02-21 16:05:28','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',0,'D','Erfassung der zugehörigen Kostenstelle',0,'Y','Y','Y','N','N','N','N','N','Kostenstelle',0,30,0,1,1,TO_TIMESTAMP('2023-02-21 16:05:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T14:05:29.897Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712691 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T14:05:29.930Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005)
;

-- 2023-02-21T14:05:29.977Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712691
;

-- 2023-02-21T14:05:29.983Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712691)
;

-- Field: Import - Rechnungskandidaten -> Import - Invoice candiate -> Kostenstelle Suchschlüssel
-- Column: I_Invoice_Candidate.C_Activity_Value
-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Kostenstelle Suchschlüssel
-- Column: I_Invoice_Candidate.C_Activity_Value
-- 2023-02-21T14:05:40.877Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586206,712692,0,546594,0,TO_TIMESTAMP('2023-02-21 16:05:40','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Kostenstelle Suchschlüssel',0,40,0,1,1,TO_TIMESTAMP('2023-02-21 16:05:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T14:05:40.879Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712692 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T14:05:40.881Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582092)
;

-- 2023-02-21T14:05:40.889Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712692
;

-- 2023-02-21T14:05:40.889Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712692)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Kostenstelle Suchschlüssel
-- Column: I_Invoice_Candidate.C_Activity_Value
-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.Kostenstelle Suchschlüssel
-- Column: I_Invoice_Candidate.C_Activity_Value
-- 2023-02-21T14:06:28.195Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712692,0,546594,615898,549841,'F',TO_TIMESTAMP('2023-02-21 16:06:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kostenstelle Suchschlüssel',90,0,0,TO_TIMESTAMP('2023-02-21 16:06:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten -> Import - Invoice candiate.Kostenstelle
-- Column: I_Invoice_Candidate.C_Activity_ID
-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> details.Kostenstelle
-- Column: I_Invoice_Candidate.C_Activity_ID
-- 2023-02-21T14:06:47.388Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712691,0,546594,615899,549841,'F',TO_TIMESTAMP('2023-02-21 16:06:47','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','N','N',0,'Kostenstelle',100,0,0,TO_TIMESTAMP('2023-02-21 16:06:47','YYYY-MM-DD HH24:MI:SS'),100)
;

