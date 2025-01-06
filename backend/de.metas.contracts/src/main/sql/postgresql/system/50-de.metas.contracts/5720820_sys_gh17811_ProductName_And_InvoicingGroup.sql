-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.finalinvoice.process.NoModularContract
-- 2024-04-03T16:25:01.909Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545385,0,TO_TIMESTAMP('2024-04-03 19:25:01.692','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Kein Modularer Vertrag ausgewählt ! Bitte wählen Sie mindestens einen Modularen Vertrag !','I',TO_TIMESTAMP('2024-04-03 19:25:01.692','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.finalinvoice.process.NoModularContract')
;

-- 2024-04-03T16:25:01.926Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545385 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.finalinvoice.process.NoModularContract
-- 2024-04-03T16:25:11.946Z
UPDATE AD_Message_Trl SET MsgText='No Modular Contract selected ! Please select at least one Modular Contract !',Updated=TO_TIMESTAMP('2024-04-03 19:25:11.946','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545385
;

-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Candidate.ProductName
-- 2024-04-03T16:54:11.831Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588110,2659,0,10,540270,'ProductName',TO_TIMESTAMP('2024-04-03 19:54:11.698','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Name des Produktes','de.metas.invoicecandidate',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Produktname',0,0,TO_TIMESTAMP('2024-04-03 19:54:11.698','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-03T16:54:11.834Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588110 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-03T16:54:11.838Z
/* DDL */  select update_Column_Translation_From_AD_Element(2659)
;

-- 2024-04-03T16:54:13.831Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN ProductName VARCHAR(255)')
;

-- Column: C_Invoice_Candidate.InvoicingGroup
-- 2024-04-04T08:21:55.483Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588111,582427,0,17,541742,540270,'InvoicingGroup',TO_TIMESTAMP('2024-04-04 11:21:55.336','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoicecandidate',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abrechnungsgruppe',0,0,TO_TIMESTAMP('2024-04-04 11:21:55.336','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T08:21:55.489Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588111 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T08:21:55.503Z
/* DDL */  select update_Column_Translation_From_AD_Element(582427)
;

-- 2024-04-04T08:22:06.151Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN InvoicingGroup VARCHAR(255)')
;

-- Run mode: SWING_CLIENT

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Produktname
-- Column: C_Invoice_Candidate.ProductName
-- 2024-04-04T10:48:40.867Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588110,727319,0,543052,TO_TIMESTAMP('2024-04-04 13:48:40.553','YYYY-MM-DD HH24:MI:SS.US'),100,'Name des Produktes',255,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Produktname',TO_TIMESTAMP('2024-04-04 13:48:40.553','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T10:48:40.876Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727319 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T10:48:40.923Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659)
;

-- 2024-04-04T10:48:40.947Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727319
;

-- 2024-04-04T10:48:40.954Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727319)
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Produktname
-- Column: C_Invoice_Candidate.ProductName
-- 2024-04-04T10:49:28.654Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,727319,0,543052,624027,544370,'F',TO_TIMESTAMP('2024-04-04 13:49:28.479','YYYY-MM-DD HH24:MI:SS.US'),100,'Name des Produktes','Y','N','N','Y','N','N','N',0,'Produktname',81,0,0,TO_TIMESTAMP('2024-04-04 13:49:28.479','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Abrechnungsgruppe
-- Column: C_Invoice_Candidate.InvoicingGroup
-- 2024-04-04T10:49:42.777Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588111,727320,0,543052,TO_TIMESTAMP('2024-04-04 13:49:42.629','YYYY-MM-DD HH24:MI:SS.US'),100,255,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Abrechnungsgruppe',TO_TIMESTAMP('2024-04-04 13:49:42.629','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T10:49:42.779Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727320 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T10:49:42.780Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582427)
;

-- 2024-04-04T10:49:42.787Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727320
;

-- 2024-04-04T10:49:42.788Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727320)
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Abrechnungsgruppe
-- Column: C_Invoice_Candidate.InvoicingGroup
-- 2024-04-04T10:50:04.715Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,727320,0,543052,624028,544370,'F',TO_TIMESTAMP('2024-04-04 13:50:04.567','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Abrechnungsgruppe',82,0,0,TO_TIMESTAMP('2024-04-04 13:50:04.567','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Abrechnungsgruppe
-- Column: C_Invoice_Candidate.InvoicingGroup
-- 2024-04-04T10:51:30.050Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-04-04 13:51:30.05','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624028
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Produktname
-- Column: C_Invoice_Candidate.ProductName
-- 2024-04-04T10:51:47.470Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-04-04 13:51:47.47','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624027
;

-- Run mode: SWING_CLIENT

-- Column: C_InvoiceLine.ProductName
-- 2024-04-04T14:01:47.678Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588113,2659,0,10,333,'ProductName',TO_TIMESTAMP('2024-04-04 17:01:47.42','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Name des Produktes','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Produktname',0,0,TO_TIMESTAMP('2024-04-04 17:01:47.42','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:01:47.698Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588113 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:01:47.749Z
/* DDL */  select update_Column_Translation_From_AD_Element(2659)
;

-- 2024-04-04T14:01:50.489Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN ProductName VARCHAR(255)')
;

-- Run mode: SWING_CLIENT

-- Column: C_InvoiceLine.InvoicingGroup
-- 2024-04-04T14:02:39.556Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588114,582427,0,17,541742,333,'InvoicingGroup',TO_TIMESTAMP('2024-04-04 17:02:39.31','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abrechnungsgruppe',0,0,TO_TIMESTAMP('2024-04-04 17:02:39.31','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T14:02:39.560Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588114 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T14:02:39.571Z
/* DDL */  select update_Column_Translation_From_AD_Element(582427)
;

-- 2024-04-04T14:02:41.198Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN InvoicingGroup VARCHAR(255)')
;

-- Run mode: SWING_CLIENT

-- 2024-04-04T14:12:21.481Z
INSERT INTO t_alter_column values('c_invoiceline','InvoicingGroup','VARCHAR(255)',null,null)
;

-- Column: C_InvoiceLine.InvoicingGroup
-- 2024-04-04T14:15:09.744Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-04-04 17:15:09.744','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588114
;

-- 2024-04-04T14:15:12.835Z
INSERT INTO t_alter_column values('c_invoiceline','InvoicingGroup','VARCHAR(255)',null,null)
;

