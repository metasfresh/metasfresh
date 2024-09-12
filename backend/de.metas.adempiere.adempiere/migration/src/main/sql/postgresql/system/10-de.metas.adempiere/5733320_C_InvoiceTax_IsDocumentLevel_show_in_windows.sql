-- Field: Debitoren Rechnungen -> Rechnungs Steuer -> Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-12T14:42:47.390Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588962,729882,0,543633,TO_TIMESTAMP('2024-09-12 17:42:46','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)',1,'D','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','N','N','N','N','N','N','Dokumentbasiert',TO_TIMESTAMP('2024-09-12 17:42:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-12T14:42:47.395Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729882 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T14:42:47.448Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(917) 
;

-- 2024-09-12T14:42:47.487Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729882
;

-- 2024-09-12T14:42:47.491Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729882)
;

-- UI Element: Debitoren Rechnungen -> Rechnungs Steuer.Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-12T14:43:21.614Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729882,0,543633,545274,625336,'F',TO_TIMESTAMP('2024-09-12 17:43:21','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','Y','N','N','Dokumentbasiert',90,0,0,TO_TIMESTAMP('2024-09-12 17:43:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Debitoren Rechnungen -> Rechnungs Steuer.Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-12T14:43:39.213Z
UPDATE AD_UI_Element SET SeqNo=65,Updated=TO_TIMESTAMP('2024-09-12 17:43:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625336
;

-- UI Element: Debitoren Rechnungen -> Rechnungs Steuer.Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-12T14:45:01.956Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-12 17:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625336
;

-- UI Element: Debitoren Rechnungen -> Rechnungs Steuer.Sektion
-- Column: C_InvoiceTax.AD_Org_ID
-- 2024-09-12T14:45:01.980Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-12 17:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=580722
;

-- Field: Kreditoren Rechnungen -> Rechnungs-Steuer -> Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-12T15:18:26.014Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588962,729883,0,543666,TO_TIMESTAMP('2024-09-12 18:18:25','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)',1,'D','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','N','N','N','N','N','N','Dokumentbasiert',TO_TIMESTAMP('2024-09-12 18:18:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-12T15:18:26.018Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729883 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T15:18:26.021Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(917) 
;

-- 2024-09-12T15:18:26.028Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729883
;

-- 2024-09-12T15:18:26.030Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729883)
;

-- UI Element: Kreditoren Rechnungen -> Rechnungs-Steuer.Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-12T15:18:53.282Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729883,0,543666,545334,625337,'F',TO_TIMESTAMP('2024-09-12 18:18:53','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','Y','N','N','Dokumentbasiert',100,0,0,TO_TIMESTAMP('2024-09-12 18:18:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Kreditoren Rechnungen -> Rechnungs-Steuer.Dokumentbasiert
-- Column: C_InvoiceTax.IsDocumentLevel
-- 2024-09-12T15:19:13.239Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-12 18:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625337
;

-- UI Element: Kreditoren Rechnungen -> Rechnungs-Steuer.Sektion
-- Column: C_InvoiceTax.AD_Org_ID
-- 2024-09-12T15:19:13.258Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-12 18:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=581262
;

