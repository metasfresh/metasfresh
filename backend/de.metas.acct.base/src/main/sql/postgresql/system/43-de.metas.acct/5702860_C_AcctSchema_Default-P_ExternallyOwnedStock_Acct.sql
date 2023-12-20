-- Run mode: SWING_CLIENT

-- 2023-09-15T08:30:57.315098900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582720,0,'P_ExternallyOwnedStock_Acct',TO_TIMESTAMP('2023-09-15 11:30:57.045','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Externally Owned Stock','Externally Owned Stock',TO_TIMESTAMP('2023-09-15 11:30:57.045','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-15T08:30:57.333631100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582720 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_AcctSchema_Default.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:31:47.041120700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587474,582720,0,25,315,'P_ExternallyOwnedStock_Acct',TO_TIMESTAMP('2023-09-15 11:31:46.846','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externally Owned Stock',0,0,TO_TIMESTAMP('2023-09-15 11:31:46.846','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-15T08:31:47.043205500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587474 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-15T08:31:47.079733Z
/* DDL */  select update_Column_Translation_From_AD_Element(582720)
;

-- 2023-09-15T08:31:48.960323500Z
/* DDL */ SELECT public.db_alter_table('C_AcctSchema_Default','ALTER TABLE public.C_AcctSchema_Default ADD COLUMN P_ExternallyOwnedStock_Acct NUMERIC(10)')
;

-- 2023-09-15T08:31:48.968086600Z
ALTER TABLE C_AcctSchema_Default ADD CONSTRAINT PExternallyOwnedStockA_CAcctSchemaDefault FOREIGN KEY (P_ExternallyOwnedStock_Acct) REFERENCES public.C_ValidCombination DEFERRABLE INITIALLY DEFERRED
;

-- Field: Buchführungs-Schema(125,D) -> Standardwerte(252,D) -> Externally Owned Stock

;

-- Column: C_AcctSchema_Default.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:32:25.477479900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587474,720485,0,252,TO_TIMESTAMP('2023-09-15 11:32:25.262','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Externally Owned Stock',TO_TIMESTAMP('2023-09-15 11:32:25.262','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-15T08:32:25.479588200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720485 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-15T08:32:25.481668600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582720)
;

-- 2023-09-15T08:32:25.492943800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720485
;

-- 2023-09-15T08:32:25.494653800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720485)
;

-- UI Element: Buchführungs-Schema(125,D) -> Standardwerte(252,D) -> main -> 10 -> default.Externally Owned Stock

;

-- Column: C_AcctSchema_Default.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:36:16.949545600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720485,0,252,541334,620475,'F',TO_TIMESTAMP('2023-09-15 11:36:16.724','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Externally Owned Stock',750,0,0,TO_TIMESTAMP('2023-09-15 11:36:16.724','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchführungs-Schema(125,D) -> Standardwerte(252,D) -> main -> 10 -> default.Externally Owned Stock

;

-- Column: C_AcctSchema_Default.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:36:33.103244600Z
UPDATE AD_UI_Element SET SeqNo=265,Updated=TO_TIMESTAMP('2023-09-15 11:36:33.102','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620475
;

