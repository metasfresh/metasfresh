
-- UI Element: Buchungen Export -> Lines.Steuersatz
-- Column: DATEV_ExportLine.C_Tax_Rate
-- 2022-09-26T09:05:20.376Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707328,0,541037,541479,613033,'F',TO_TIMESTAMP('2022-09-26 11:05:20.018','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Steuersatz',220,0,0,TO_TIMESTAMP('2022-09-26 11:05:20.018','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchungen Export -> Lines.C_DocType_Name
-- Column: DATEV_ExportLine.C_DocType_Name
-- 2022-09-26T09:05:39.860Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707329,0,541037,541479,613034,'F',TO_TIMESTAMP('2022-09-26 11:05:39.513','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'C_DocType_Name',230,0,0,TO_TIMESTAMP('2022-09-26 11:05:39.513','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchungen Export -> Lines.C_Tax_Rate
-- Column: DATEV_ExportLine.C_Tax_Rate
-- 2022-09-26T09:05:50.463Z
UPDATE AD_UI_Element SET Name='C_Tax_Rate',Updated=TO_TIMESTAMP('2022-09-26 11:05:50.463','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613033
;
