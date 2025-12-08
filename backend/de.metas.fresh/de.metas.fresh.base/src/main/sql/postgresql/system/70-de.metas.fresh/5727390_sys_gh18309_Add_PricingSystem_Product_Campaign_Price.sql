-- Field: Produkt -> Aktionspreise -> Preissystem
-- Column: C_Campaign_Price.M_PricingSystem_ID
-- Field: Produkt(140,D) -> Aktionspreise(541833,D) -> Preissystem
-- Column: C_Campaign_Price.M_PricingSystem_ID
-- 2024-06-27T08:15:52.369Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569386,729008,0,541833,0,TO_TIMESTAMP('2024-06-27 09:15:51','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.',0,'D','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.',0,'Y','Y','Y','N','N','N','N','N','Preissystem',0,140,0,1,1,TO_TIMESTAMP('2024-06-27 09:15:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-27T08:15:52.375Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-27T08:15:52.393Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505135) 
;

-- 2024-06-27T08:15:52.425Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729008
;

-- 2024-06-27T08:15:52.426Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729008)
;

-- UI Element: Produkt -> Aktionspreise.Preissystem
-- Column: C_Campaign_Price.M_PricingSystem_ID
-- UI Element: Produkt(140,D) -> Aktionspreise(541833,D) -> main -> 10 -> default.Preissystem
-- Column: C_Campaign_Price.M_PricingSystem_ID
-- 2024-06-27T08:59:44.858Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729008,0,541833,542699,624956,'F',TO_TIMESTAMP('2024-06-27 09:59:43','YYYY-MM-DD HH24:MI:SS'),100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','N','Y','N','N','N',0,'Preissystem',75,0,0,TO_TIMESTAMP('2024-06-27 09:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

