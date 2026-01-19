-- Field: Produktionsaufträge -> Komponenten -> Kommissionierhinweis
-- Column: PP_Order_BOMLine.PickingInstruction
-- Field: Produktionsaufträge(541267,de.metas.endcustomer.ic114) -> Komponenten(544566,de.metas.endcustomer.ic114) -> Kommissionierhinweis
-- Column: PP_Order_BOMLine.PickingInstruction
-- 2026-01-19T12:31:03.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591856,761690,0,544566,TO_TIMESTAMP('2026-01-19 14:31:01','YYYY-MM-DD HH24:MI:SS'),100,'Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).',2000,'de.metas.endcustomer.ic114','Y','N','N','N','N','N','N','N','Kommissionierhinweis',TO_TIMESTAMP('2026-01-19 14:31:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-01-19T12:31:03.323Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=761690 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-19T12:31:03.661Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584390) 
;

-- 2026-01-19T12:31:03.736Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=761690
;

-- 2026-01-19T12:31:03.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(761690)
;

-- UI Element: Produktionsaufträge -> Komponenten.Kommissionierhinweis
-- Column: PP_Order_BOMLine.PickingInstruction
-- UI Element: Produktionsaufträge(541267,de.metas.endcustomer.ic114) -> Komponenten(544566,de.metas.endcustomer.ic114) -> main -> 10 -> default.Kommissionierhinweis
-- Column: PP_Order_BOMLine.PickingInstruction
-- 2026-01-19T12:36:42.604Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,761690,0,544566,546777,641655,'F',TO_TIMESTAMP('2026-01-19 14:36:40','YYYY-MM-DD HH24:MI:SS'),100,'Hinweise für Lagermitarbeiter und Fertigungspersonal zur Identifizierung, Auswahl und Handhabung dieser Materialkomponente (z.B. visuelle Qualitätskriterien, spezifische Merkmale, die vor der Kommissionierung zu prüfen sind).','Y','N','Y','N','N','Kommissionierhinweis',400,0,0,TO_TIMESTAMP('2026-01-19 14:36:40','YYYY-MM-DD HH24:MI:SS'),100)
;

