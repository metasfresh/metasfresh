-- Field: Auftrag -> Steuer -> Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-12T15:22:54.112Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588963,729884,0,543623,TO_TIMESTAMP('2024-09-12 18:22:53','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)',1,'D','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','N','N','N','N','N','N','Dokumentbasiert',TO_TIMESTAMP('2024-09-12 18:22:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-12T15:22:54.115Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729884 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T15:22:54.118Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(917) 
;

-- 2024-09-12T15:22:54.125Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729884
;

-- 2024-09-12T15:22:54.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729884)
;

-- UI Element: Auftrag -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-12T15:23:14.421Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729884,0,543623,545249,625338,'F',TO_TIMESTAMP('2024-09-12 18:23:14','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','Y','N','N','Dokumentbasiert',90,0,0,TO_TIMESTAMP('2024-09-12 18:23:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftrag -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-12T15:23:29.314Z
UPDATE AD_UI_Element SET SeqNo=65,Updated=TO_TIMESTAMP('2024-09-12 18:23:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625338
;

-- UI Element: Auftrag -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-12T15:23:37.931Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-12 18:23:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625338
;

-- UI Element: Auftrag -> Steuer.Sektion
-- Column: C_OrderTax.AD_Org_ID
-- 2024-09-12T15:23:37.953Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-12 18:23:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=580490
;

-- Field: Bestellung -> Steuer -> Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-12T15:23:52.503Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588963,729885,0,543638,TO_TIMESTAMP('2024-09-12 18:23:52','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)',1,'D','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','N','N','N','N','N','N','Dokumentbasiert',TO_TIMESTAMP('2024-09-12 18:23:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-12T15:23:52.506Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729885 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T15:23:52.509Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(917) 
;

-- 2024-09-12T15:23:52.515Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729885
;

-- 2024-09-12T15:23:52.517Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729885)
;

-- UI Element: Bestellung -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-12T15:24:14.465Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729885,0,543638,545285,625339,'F',TO_TIMESTAMP('2024-09-12 18:24:14','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','N','Y','N','N','Dokumentbasiert',100,0,0,TO_TIMESTAMP('2024-09-12 18:24:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Bestellung -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-12T15:24:37.612Z
UPDATE AD_UI_Element SET SeqNo=65,Updated=TO_TIMESTAMP('2024-09-12 18:24:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625339
;

-- UI Element: Bestellung -> Steuer.Dokumentbasiert
-- Column: C_OrderTax.IsDocumentLevel
-- 2024-09-12T15:24:43.864Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-12 18:24:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625339
;

-- UI Element: Bestellung -> Steuer.Sektion
-- Column: C_OrderTax.AD_Org_ID
-- 2024-09-12T15:24:43.873Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-12 18:24:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=580834
;

