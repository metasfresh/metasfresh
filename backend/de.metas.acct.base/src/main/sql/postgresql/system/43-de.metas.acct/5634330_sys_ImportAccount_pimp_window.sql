-- Field: Import Kontenrahmen -> Import - Kontendefinition -> Data Import Run
-- Column: I_ElementValue.C_DataImport_Run_ID
-- 2022-04-09T08:46:13.627358400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568875,691654,0,443,TO_TIMESTAMP('2022-04-09 11:46:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data Import Run',TO_TIMESTAMP('2022-04-09 11:46:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-09T08:46:13.632390600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691654 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-09T08:46:13.638360400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577114) 
;

-- 2022-04-09T08:46:13.651355Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691654
;

-- 2022-04-09T08:46:13.657385800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691654)
;

-- Field: Import Kontenrahmen -> Import - Kontendefinition -> Daten Import
-- Column: I_ElementValue.C_DataImport_ID
-- 2022-04-09T08:46:28.885236800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568876,691655,0,443,TO_TIMESTAMP('2022-04-09 11:46:28','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Daten Import',TO_TIMESTAMP('2022-04-09 11:46:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-09T08:46:28.888280100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691655 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-09T08:46:28.890243200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913) 
;

-- 2022-04-09T08:46:28.894242600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691655
;

-- 2022-04-09T08:46:28.898244800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691655)
;

-- Field: Import Kontenrahmen -> Import - Kontendefinition -> Probleme
-- Column: I_ElementValue.AD_Issue_ID
-- 2022-04-09T08:46:42.330213500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568872,691656,0,443,TO_TIMESTAMP('2022-04-09 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2022-04-09 11:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-09T08:46:42.332212Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691656 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-09T08:46:42.334213700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2022-04-09T08:46:42.355257300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691656
;

-- 2022-04-09T08:46:42.355257300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691656)
;

-- Field: Import Kontenrahmen -> Import - Kontendefinition -> Import Line No
-- Column: I_ElementValue.I_LineNo
-- 2022-04-09T08:46:42.451737500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568873,691657,0,443,TO_TIMESTAMP('2022-04-09 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Import Line No',TO_TIMESTAMP('2022-04-09 11:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-09T08:46:42.453737700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691657 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-09T08:46:42.455737500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577116) 
;

-- 2022-04-09T08:46:42.457737500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691657
;

-- 2022-04-09T08:46:42.457737500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691657)
;

-- Field: Import Kontenrahmen -> Import - Kontendefinition -> Import Line Content
-- Column: I_ElementValue.I_LineContent
-- 2022-04-09T08:46:42.553988100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568874,691658,0,443,TO_TIMESTAMP('2022-04-09 11:46:42','YYYY-MM-DD HH24:MI:SS'),100,4000,'D','Y','N','N','N','N','N','N','N','Import Line Content',TO_TIMESTAMP('2022-04-09 11:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-09T08:46:42.555980Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691658 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-09T08:46:42.556979900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577115) 
;

-- 2022-04-09T08:46:42.558978900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691658
;

-- 2022-04-09T08:46:42.559980500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691658)
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Probleme
-- Column: I_ElementValue.AD_Issue_ID
-- 2022-04-09T08:48:36.034064100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691656,0,443,605312,541652,'F',TO_TIMESTAMP('2022-04-09 11:48:35','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Probleme',170,0,0,TO_TIMESTAMP('2022-04-09 11:48:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Probleme
-- Column: I_ElementValue.AD_Issue_ID
-- 2022-04-09T08:49:01.215495500Z
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2022-04-09 11:49:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605312
;

-- 2022-04-09T08:49:17.934710500Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541008,548793,TO_TIMESTAMP('2022-04-09 11:49:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','Data Import',20,TO_TIMESTAMP('2022-04-09 11:49:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Daten Import
-- Column: I_ElementValue.C_DataImport_ID
-- 2022-04-09T08:49:37.822212Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691655,0,443,605313,548793,'F',TO_TIMESTAMP('2022-04-09 11:49:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Daten Import',10,0,0,TO_TIMESTAMP('2022-04-09 11:49:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Data Import Run
-- Column: I_ElementValue.C_DataImport_Run_ID
-- 2022-04-09T08:49:45.543968900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691654,0,443,605314,548793,'F',TO_TIMESTAMP('2022-04-09 11:49:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Data Import Run',20,0,0,TO_TIMESTAMP('2022-04-09 11:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Import Line No
-- Column: I_ElementValue.I_LineNo
-- 2022-04-09T08:49:52.790135200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691657,0,443,605315,548793,'F',TO_TIMESTAMP('2022-04-09 11:49:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Import Line No',30,0,0,TO_TIMESTAMP('2022-04-09 11:49:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Import Line Content
-- Column: I_ElementValue.I_LineContent
-- 2022-04-09T08:49:59.683452900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691658,0,443,605316,548793,'F',TO_TIMESTAMP('2022-04-09 11:49:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Import Line Content',40,0,0,TO_TIMESTAMP('2022-04-09 11:49:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Import Kontenrahmen -> Import - Kontendefinition -> Probleme
-- Column: I_ElementValue.AD_Issue_ID
-- 2022-04-09T08:50:19.018185800Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-04-09 11:50:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691656
;

-- Field: Import Kontenrahmen -> Import - Kontendefinition -> Import Line No
-- Column: I_ElementValue.I_LineNo
-- 2022-04-09T08:50:20.430747400Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-04-09 11:50:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691657
;

-- Field: Import Kontenrahmen -> Import - Kontendefinition -> Import Line Content
-- Column: I_ElementValue.I_LineContent
-- 2022-04-09T08:50:21.793929Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-04-09 11:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691658
;

-- Field: Import Kontenrahmen -> Import - Kontendefinition -> Data Import Run
-- Column: I_ElementValue.C_DataImport_Run_ID
-- 2022-04-09T08:50:23.307887900Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-04-09 11:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691654
;

-- Field: Import Kontenrahmen -> Import - Kontendefinition -> Daten Import
-- Column: I_ElementValue.C_DataImport_ID
-- 2022-04-09T08:50:26.880674400Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-04-09 11:50:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691655
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Standard Konto
-- Column: I_ElementValue.Default_Account
-- 2022-04-09T08:51:17.000998700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-04-09 11:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552216
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Element
-- Column: I_ElementValue.C_Element_ID
-- 2022-04-09T08:51:17.006999Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-04-09 11:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552202
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Element Name
-- Column: I_ElementValue.ElementName
-- 2022-04-09T08:51:17.011999600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-04-09 11:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552201
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Kontenart
-- Column: I_ElementValue.C_ElementValue_ID
-- 2022-04-09T08:51:17.015999200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-04-09 11:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552197
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Import-Fehlermeldung
-- Column: I_ElementValue.I_ErrorMsg
-- 2022-04-09T08:51:17.021000500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-04-09 11:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552198
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Import - Kontendefinition
-- Column: I_ElementValue.I_ElementValue_ID
-- 2022-04-09T08:51:17.024999400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-04-09 11:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552195
;

-- UI Element: Import Kontenrahmen -> Import - Kontendefinition.Sektion
-- Column: I_ElementValue.AD_Org_ID
-- 2022-04-09T08:51:17.029000100Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-04-09 11:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552200
;

