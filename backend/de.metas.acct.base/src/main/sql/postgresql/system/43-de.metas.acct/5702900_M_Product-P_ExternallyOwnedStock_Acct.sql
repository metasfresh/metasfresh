-- Run mode: SWING_CLIENT

-- Column: M_Product_Acct.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:48:12.180006800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587477,582720,0,25,273,'P_ExternallyOwnedStock_Acct',TO_TIMESTAMP('2023-09-15 11:48:12.012','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externally Owned Stock',0,0,TO_TIMESTAMP('2023-09-15 11:48:12.012','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-15T08:48:12.182099300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587477 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-15T08:48:12.185228100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582720)
;

-- 2023-09-15T08:48:13.745887700Z
/* DDL */ SELECT public.db_alter_table('M_Product_Acct','ALTER TABLE public.M_Product_Acct ADD COLUMN P_ExternallyOwnedStock_Acct NUMERIC(10)')
;

-- 2023-09-15T08:48:13.753161300Z
ALTER TABLE M_Product_Acct ADD CONSTRAINT PExternallyOwnedStockA_MProductAcct FOREIGN KEY (P_ExternallyOwnedStock_Acct) REFERENCES public.C_ValidCombination DEFERRABLE INITIALLY DEFERRED
;

-- Field: Produkt_OLD(140,D) -> Buchführung(210,D) -> Externally Owned Stock

;

-- Column: M_Product_Acct.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:48:46.327965900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587477,720488,0,210,TO_TIMESTAMP('2023-09-15 11:48:46.145','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Externally Owned Stock',TO_TIMESTAMP('2023-09-15 11:48:46.145','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-15T08:48:46.330056Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720488 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-15T08:48:46.332138600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582720)
;

-- 2023-09-15T08:48:46.334746100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720488
;

-- 2023-09-15T08:48:46.335792400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720488)
;

-- UI Element: Produkt_OLD(140,D) -> Buchführung(210,D) -> main -> 10 -> default.Externally Owned Stock

;

-- Column: M_Product_Acct.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:50:11.779445200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720488,0,210,1000022,620478,'F',TO_TIMESTAMP('2023-09-15 11:50:11.602','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Externally Owned Stock',280,0,0,TO_TIMESTAMP('2023-09-15 11:50:11.602','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Produkt_OLD(140,D) -> Buchführung(210,D) -> main -> 10 -> default.Externally Owned Stock

;

-- Column: M_Product_Acct.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:50:27.763998900Z
UPDATE AD_UI_Element SET SeqNo=225,Updated=TO_TIMESTAMP('2023-09-15 11:50:27.763','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620478
;

