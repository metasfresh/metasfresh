-- UI Element: Vertragsbedingungen -> Bedingungen.Einheiten-Typ
-- Column: C_Flatrate_Conditions.UOMType
-- 2024-05-02T12:13:09.102Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548163,0,540331,540761,624716,'F',TO_TIMESTAMP('2024-05-02 14:13:08','YYYY-MM-DD HH24:MI:SS'),100,'Dient der Zusammenfassung ähnlicher Maßeinheiten','Y','N','N','Y','N','N','N',0,'Einheiten-Typ',50,0,0,TO_TIMESTAMP('2024-05-02 14:13:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Vertragsbedingungen -> Bedingungen.Maßeinheit
-- Column: C_Flatrate_Conditions.C_UOM_ID
-- 2024-05-02T12:13:39.594Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547846,0,540331,540761,624717,'F',TO_TIMESTAMP('2024-05-02 14:13:39','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',60,0,0,TO_TIMESTAMP('2024-05-02 14:13:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Vertragsbedingungen -> Bedingungen.Produkt für pauschale Berechnung
-- Column: C_Flatrate_Conditions.M_Product_Flatrate_ID
-- 2024-05-02T12:14:47.773Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547835,0,540331,540761,624718,'F',TO_TIMESTAMP('2024-05-02 14:14:47','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, unter dem die pauschal abzurechnenden Leistungen in Rechnung gestellt werden','Y','N','N','Y','N','N','N',0,'Produkt für pauschale Berechnung',70,0,0,TO_TIMESTAMP('2024-05-02 14:14:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ignore this - we still can't create holding-fee contracts (code-wise)
-- 2024-05-02T12:25:10.891Z
--UPDATE AD_Val_Rule SET Code='C_Flatrate_Conditions.Type_Conditions NOT IN (''Subscr'', ''Procuremnt'')', Description='User in process C_Flatrate_Term_Create_For_BPartners to define which C_Flatrate_Conditions are eligible',Updated=TO_TIMESTAMP('2024-05-02 14:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540237
--;

-- 2024-05-02T12:25:27.151Z
UPDATE AD_Val_Rule SET Description='Used in process C_Flatrate_Term_Create_For_BPartners to define which C_Flatrate_Conditions are eligible',Updated=TO_TIMESTAMP('2024-05-02 14:25:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540237
;

-- UI Element: Vertragsbedingungen -> Bedingungen.Einheiten-Typ
-- Column: C_Flatrate_Conditions.UOMType
-- 2024-05-02T12:49:30.642Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624716
;

-- UI Element: Vertragsbedingungen -> Bedingungen.Maßeinheit
-- Column: C_Flatrate_Conditions.C_UOM_ID
-- 2024-05-02T12:49:50.406Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624717
;

-- UI Element: Vertragsbedingungen -> Bedingungen.Produkt für pauschale Berechnung
-- Column: C_Flatrate_Conditions.M_Product_Flatrate_ID
-- 2024-05-02T12:50:03.282Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624718
;

-- 2024-05-02T13:47:47.823Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540264,543678,TO_TIMESTAMP('2024-05-02 15:47:47','YYYY-MM-DD HH24:MI:SS'),100,'Die gemeldete Menge wird abgrechnet. Die Planmenge ist optional und dient nur zur Information.','de.metas.contracts','Y','Gemelde Menge',TO_TIMESTAMP('2024-05-02 15:47:47','YYYY-MM-DD HH24:MI:SS'),100,'RPTD','Reported Quantity')
;

-- 2024-05-02T13:47:47.826Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543678 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-05-02T13:48:24.040Z
UPDATE AD_Ref_List SET Name='Gemeldete Menge',Updated=TO_TIMESTAMP('2024-05-02 15:48:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543678
;

-- Column: C_Flatrate_Term.PlannedQtyPerUnit
-- 2024-05-02T13:54:55.170Z
UPDATE AD_Column SET MandatoryLogic='(@Type_Conditions/''X''@=''FlatFee'' & @Type_Flatrate/''X''@!''RPTD'') | @Type_Conditions/''X''@=''Subscr''', TechnicalNote='About the mandatory logic: we need the quantity for subscr and flatfee - unless it''s a flatfee with Type_Flatrate="Reported Qty".',Updated=TO_TIMESTAMP('2024-05-02 15:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545989
;
