-- Field: Produkt_OLD -> Aktionspreise -> Preiseinheit
-- Column: C_Campaign_Price.C_UOM_ID
-- Field: Produkt_OLD(140,D) -> Aktionspreise(541833,D) -> Preiseinheit
-- Column: C_Campaign_Price.C_UOM_ID
-- 2023-06-13T14:33:01.329Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569832,716359,542464,0,541833,0,TO_TIMESTAMP('2023-06-13 17:33:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Preiseinheit',0,120,0,1,1,TO_TIMESTAMP('2023-06-13 17:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-13T14:33:01.687Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716359 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T14:33:01.754Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542464) 
;

-- 2023-06-13T14:33:01.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716359
;

-- 2023-06-13T14:33:01.859Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716359)
;

-- UI Element: Produkt_OLD -> Aktionspreise.Preiseinheit
-- Column: C_Campaign_Price.C_UOM_ID
-- UI Element: Produkt_OLD(140,D) -> Aktionspreise(541833,D) -> main -> 10 -> default.Preiseinheit
-- Column: C_Campaign_Price.C_UOM_ID
-- 2023-06-13T14:34:58.520Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716359,0,541833,542699,618000,'F',TO_TIMESTAMP('2023-06-13 17:34:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Preiseinheit',85,0,0,TO_TIMESTAMP('2023-06-13 17:34:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Produkt_OLD -> Aktionspreise -> Abr. Menge basiert auf
-- Column: C_Campaign_Price.InvoicableQtyBasedOn
-- Field: Produkt_OLD(140,D) -> Aktionspreise(541833,D) -> Abr. Menge basiert auf
-- Column: C_Campaign_Price.InvoicableQtyBasedOn
-- 2023-06-13T14:35:50.777Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569814,716360,0,541833,0,TO_TIMESTAMP('2023-06-13 17:35:50','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Abr. Menge basiert auf',0,130,0,1,1,TO_TIMESTAMP('2023-06-13 17:35:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-13T14:35:50.898Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716360 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T14:35:50.937Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576948) 
;

-- 2023-06-13T14:35:50.979Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716360
;

-- 2023-06-13T14:35:51.016Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716360)
;

-- UI Element: Produkt_OLD -> Aktionspreise.Abr. Menge basiert auf
-- Column: C_Campaign_Price.InvoicableQtyBasedOn
-- UI Element: Produkt_OLD(140,D) -> Aktionspreise(541833,D) -> main -> 10 -> default.Abr. Menge basiert auf
-- Column: C_Campaign_Price.InvoicableQtyBasedOn
-- 2023-06-13T14:36:24.384Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716360,0,541833,542699,618001,'F',TO_TIMESTAMP('2023-06-13 17:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','Y','N','N','Y','N','N','N',0,'Abr. Menge basiert auf',95,0,0,TO_TIMESTAMP('2023-06-13 17:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;
