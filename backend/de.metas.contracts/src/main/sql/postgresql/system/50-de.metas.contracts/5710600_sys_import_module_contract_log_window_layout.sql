-- Run mode: SWING_CLIENT

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> flags.Verkaufstransaktion
-- Column: I_ModCntr_Log.IsSOTrx
-- 2023-11-15T10:36:51.267Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720724,0,547233,551185,621281,'F',TO_TIMESTAMP('2023-11-15 12:36:50.964','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','Y','N','N','N',0,'Verkaufstransaktion',30,0,0,TO_TIMESTAMP('2023-11-15 12:36:50.964','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> description.Beschreibung
-- Column: I_ModCntr_Log.Description
-- 2023-11-15T10:41:09.427Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620902
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10
-- UI Element Group: description
-- 2023-11-15T10:41:13.970Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=551219
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Menge
-- Column: I_ModCntr_Log.Qty
-- 2023-11-15T10:41:29.693Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551191, SeqNo=80,Updated=TO_TIMESTAMP('2023-11-15 12:41:29.693','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620630
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Symbol
-- Column: I_ModCntr_Log.UOMSymbol
-- 2023-11-15T10:41:39.965Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551191, SeqNo=90,Updated=TO_TIMESTAMP('2023-11-15 12:41:39.965','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620631
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Einzelpreis
-- Column: I_ModCntr_Log.PriceActual
-- 2023-11-15T10:41:51.447Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551191, SeqNo=100,Updated=TO_TIMESTAMP('2023-11-15 12:41:51.447','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620893
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Preiseinheit
-- Column: I_ModCntr_Log.PriceUOM
-- 2023-11-15T10:42:03.287Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551191, SeqNo=110,Updated=TO_TIMESTAMP('2023-11-15 12:42:03.287','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620891
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Betrag
-- Column: I_ModCntr_Log.Amount
-- 2023-11-15T10:42:08.621Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551191, SeqNo=120,Updated=TO_TIMESTAMP('2023-11-15 12:42:08.621','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620634
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.ISO Währungscode
-- Column: I_ModCntr_Log.ISO_Code
-- 2023-11-15T10:42:14.244Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551191, SeqNo=130,Updated=TO_TIMESTAMP('2023-11-15 12:42:14.244','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620635
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10
-- UI Element Group: qty
-- 2023-11-15T10:42:36.954Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=551189
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Importiert
-- Column: I_ModCntr_Log.I_IsImported
-- 2023-11-15T10:43:30.006Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551184, SeqNo=110,Updated=TO_TIMESTAMP('2023-11-15 12:43:30.006','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620623
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10
-- UI Element Group: import
-- 2023-11-15T10:44:32.624Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=547100, SeqNo=30,Updated=TO_TIMESTAMP('2023-11-15 12:44:32.624','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551186
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Produktschlüssel
-- Column: I_ModCntr_Log.ProductValue
-- 2023-11-15T10:48:21.299Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720726,0,547233,551191,621282,'F',TO_TIMESTAMP('2023-11-15 12:48:21.179','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID','Y','N','N','Y','N','N','N',0,'Produktschlüssel',140,0,0,TO_TIMESTAMP('2023-11-15 12:48:21.179','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Name der Rechnungsstellungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-11-15T10:49:01.125Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721026,0,547233,551191,621283,'F',TO_TIMESTAMP('2023-11-15 12:49:00.975','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name der Rechnungsstellungsgruppe',150,0,0,TO_TIMESTAMP('2023-11-15 12:49:00.975','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Jahr
-- Column: I_ModCntr_Log.FiscalYear
-- 2023-11-15T10:49:51.342Z
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2023-11-15 12:49:51.342','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620621
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Name der Rechnungsstellungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-11-15T10:49:56.990Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-11-15 12:49:56.99','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621283
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Rechnungspartner Suchschlüssel
-- Column: I_ModCntr_Log.Bill_BPartner_Value
-- 2023-11-15T10:50:26.353Z
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2023-11-15 12:50:26.353','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620620
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Symbol
-- Column: I_ModCntr_Log.UOMSymbol
-- 2023-11-15T10:50:30.146Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2023-11-15 12:50:30.146','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620631
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Einzelpreis
-- Column: I_ModCntr_Log.PriceActual
-- 2023-11-15T10:50:44.111Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2023-11-15 12:50:44.111','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620893
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Preiseinheit
-- Column: I_ModCntr_Log.PriceUOM
-- 2023-11-15T10:50:49.799Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2023-11-15 12:50:49.799','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620891
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Betrag
-- Column: I_ModCntr_Log.Amount
-- 2023-11-15T10:51:21.297Z
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2023-11-15 12:51:21.297','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620634
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.ISO Währungscode
-- Column: I_ModCntr_Log.ISO_Code
-- 2023-11-15T10:51:49.293Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720736,0,547233,551191,621284,'F',TO_TIMESTAMP('2023-11-15 12:51:49.138','YYYY-MM-DD HH24:MI:SS.US'),100,'Dreibuchstabiger ISO 4217 Code für die Währung','Für Details - http://www.unece.org/cefact/recommendations/rec09/rec09_ecetrd203.pdf','Y','N','N','Y','N','N','N',0,'ISO Währungscode',100,0,0,TO_TIMESTAMP('2023-11-15 12:51:49.138','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Name der Rechnungsstellungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-11-15T10:52:09.835Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-11-15 12:52:09.835','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621283
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Produktschlüssel
-- Column: I_ModCntr_Log.ProductValue
-- 2023-11-15T10:52:20.315Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-11-15 12:52:20.315','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621282
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Kalendername
-- Column: I_ModCntr_Log.CalendarName
-- 2023-11-15T10:54:46.955Z
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2023-11-15 12:54:46.955','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621213
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Geschäftspartner-Schlüssel
-- Column: I_ModCntr_Log.BPartnerValue
-- 2023-11-15T10:55:50.818Z
UPDATE AD_UI_Element SET AD_Field_ID=720730, Description='Key of the Business Partner', Help=NULL, Name='Geschäftspartner-Schlüssel',Updated=TO_TIMESTAMP('2023-11-15 12:55:50.818','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620635
;

-- Element: CollectionPointValue
-- 2023-11-15T10:56:54.347Z
UPDATE AD_Element_Trl SET Name='Sammelstelle Suchschlüssel', PrintName='Sammelstelle Suchschlüssel',Updated=TO_TIMESTAMP('2023-11-15 12:56:54.347','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582736 AND AD_Language='de_CH'
;

-- 2023-11-15T10:56:54.380Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582736,'de_CH')
;

-- Element: CollectionPointValue
-- 2023-11-15T10:56:57.339Z
UPDATE AD_Element_Trl SET Name='Sammelstelle Suchschlüssel', PrintName='Sammelstelle Suchschlüssel',Updated=TO_TIMESTAMP('2023-11-15 12:56:57.339','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582736 AND AD_Language='de_DE'
;

-- 2023-11-15T10:56:57.340Z
UPDATE AD_Element SET Name='Sammelstelle Suchschlüssel', PrintName='Sammelstelle Suchschlüssel' WHERE AD_Element_ID=582736
;

-- 2023-11-15T10:56:57.604Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582736,'de_DE')
;

-- 2023-11-15T10:56:57.605Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582736,'de_DE')
;

-- Element: CollectionPointValue
-- 2023-11-15T10:56:59.751Z
UPDATE AD_Element_Trl SET Name='Sammelstelle Suchschlüssel', PrintName='Sammelstelle Suchschlüssel',Updated=TO_TIMESTAMP('2023-11-15 12:56:59.751','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582736 AND AD_Language='fr_CH'
;

-- 2023-11-15T10:56:59.752Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582736,'fr_CH')
;

-- Element: CollectionPointValue
-- 2023-11-15T10:57:12.793Z
UPDATE AD_Element_Trl SET Name='Sammelstelle Suchschlüssel', PrintName='Sammelstelle Suchschlüssel',Updated=TO_TIMESTAMP('2023-11-15 12:57:12.793','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582736 AND AD_Language='it_IT'
;

-- 2023-11-15T10:57:12.795Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582736,'it_IT')
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Sammelstelle Suchschlüssel
-- Column: I_ModCntr_Log.CollectionPointValue
-- 2023-11-15T10:58:18.179Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720732,0,547233,551191,621285,'F',TO_TIMESTAMP('2023-11-15 12:58:18.046','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Sammelstelle Suchschlüssel',150,0,0,TO_TIMESTAMP('2023-11-15 12:58:18.046','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Lager
-- Column: I_ModCntr_Log.WarehouseName
-- 2023-11-15T10:58:57.188Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720734,0,547233,551191,621286,'F',TO_TIMESTAMP('2023-11-15 12:58:57.037','YYYY-MM-DD HH24:MI:SS.US'),100,'Lagerbezeichnung','Y','N','N','Y','N','N','N',0,'Lager',160,0,0,TO_TIMESTAMP('2023-11-15 12:58:57.037','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Pauschale - Vertragsperiode
-- Column: I_ModCntr_Log.C_Flatrate_Term_ID
-- 2023-11-15T11:00:08.564Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587539,721842,0,547233,0,TO_TIMESTAMP('2023-11-15 13:00:08.405','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Pauschale - Vertragsperiode',0,100,0,1,1,TO_TIMESTAMP('2023-11-15 13:00:08.405','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:00:08.568Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:00:08.570Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447)
;

-- 2023-11-15T11:00:08.583Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721842
;

-- 2023-11-15T11:00:08.585Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721842)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Produkt
-- Column: I_ModCntr_Log.M_Product_ID
-- 2023-11-15T11:00:18.106Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587532,721843,0,547233,0,TO_TIMESTAMP('2023-11-15 13:00:17.983','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',0,'de.metas.contracts','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','N','Produkt',0,110,0,1,1,TO_TIMESTAMP('2023-11-15 13:00:17.983','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:00:18.108Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:00:18.109Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2023-11-15T11:00:18.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721843
;

-- 2023-11-15T11:00:18.134Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721843)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Rechnungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroup_ID
-- 2023-11-15T11:00:29.351Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587526,721844,0,547233,0,TO_TIMESTAMP('2023-11-15 13:00:29.212','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Rechnungsgruppe',0,120,0,1,1,TO_TIMESTAMP('2023-11-15 13:00:29.212','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:00:29.352Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:00:29.353Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582647)
;

-- 2023-11-15T11:00:29.357Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721844
;

-- 2023-11-15T11:00:29.357Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721844)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Maßeinheit
-- Column: I_ModCntr_Log.C_UOM_ID
-- 2023-11-15T11:00:41.440Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587535,721845,0,547233,0,TO_TIMESTAMP('2023-11-15 13:00:41.324','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit',0,'de.metas.contracts','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','Y','Y','N','N','N','N','N','N','Maßeinheit',0,130,0,1,1,TO_TIMESTAMP('2023-11-15 13:00:41.324','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:00:41.442Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:00:41.443Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2023-11-15T11:00:41.451Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721845
;

-- 2023-11-15T11:00:41.452Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721845)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Währung
-- Column: I_ModCntr_Log.C_Currency_ID
-- 2023-11-15T11:01:00.858Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587536,721847,0,547233,0,TO_TIMESTAMP('2023-11-15 13:01:00.721','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag',0,'de.metas.contracts','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','Y','N','N','N','N','N','N','Währung',0,140,0,1,1,TO_TIMESTAMP('2023-11-15 13:01:00.721','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:01:00.859Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:01:00.860Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2023-11-15T11:01:00.869Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721847
;

-- 2023-11-15T11:01:00.870Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721847)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Erntejahr
-- Column: I_ModCntr_Log.Harvesting_Year_ID
-- 2023-11-15T11:01:18.315Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587534,721848,0,547233,0,TO_TIMESTAMP('2023-11-15 13:01:18.191','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Erntejahr',0,150,0,1,1,TO_TIMESTAMP('2023-11-15 13:01:18.191','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:01:18.316Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721848 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:01:18.318Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471)
;

-- 2023-11-15T11:01:18.320Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721848
;

-- 2023-11-15T11:01:18.321Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721848)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Bausteine
-- Column: I_ModCntr_Log.ModCntr_Module_ID
-- 2023-11-15T11:01:43.500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587545,721850,0,547233,0,TO_TIMESTAMP('2023-11-15 13:01:43.378','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Bausteine',0,160,0,1,1,TO_TIMESTAMP('2023-11-15 13:01:43.378','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:01:43.501Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721850 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:01:43.502Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582426)
;

-- 2023-11-15T11:01:43.506Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721850
;

-- 2023-11-15T11:01:43.507Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721850)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Produzent
-- Column: I_ModCntr_Log.Producer_BPartner_ID
-- 2023-11-15T11:01:56.021Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587533,721851,0,547233,0,TO_TIMESTAMP('2023-11-15 13:01:55.867','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Produzent',0,170,0,1,1,TO_TIMESTAMP('2023-11-15 13:01:55.867','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:01:56.023Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721851 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:01:56.024Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582415)
;

-- 2023-11-15T11:01:56.026Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721851
;

-- 2023-11-15T11:01:56.027Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721851)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Rechnungspartner
-- Column: I_ModCntr_Log.Bill_BPartner_ID
-- 2023-11-15T11:02:04.414Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587537,721852,0,547233,0,TO_TIMESTAMP('2023-11-15 13:02:04.279','YYYY-MM-DD HH24:MI:SS.US'),100,'Geschäftspartner für die Rechnungsstellung',0,'de.metas.contracts','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt',0,'Y','Y','Y','N','N','N','N','N','N','Rechnungspartner',0,180,0,1,1,TO_TIMESTAMP('2023-11-15 13:02:04.279','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:02:04.415Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721852 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:02:04.416Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2039)
;

-- 2023-11-15T11:02:04.418Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721852
;

-- 2023-11-15T11:02:04.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721852)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Sammelstelle
-- Column: I_ModCntr_Log.CollectionPoint_BPartner_ID
-- 2023-11-15T11:02:59.471Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587540,721853,0,547233,0,TO_TIMESTAMP('2023-11-15 13:02:59.31','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Sammelstelle',0,190,0,1,1,TO_TIMESTAMP('2023-11-15 13:02:59.31','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:02:59.472Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721853 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:02:59.473Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582414)
;

-- 2023-11-15T11:02:59.477Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721853
;

-- 2023-11-15T11:02:59.478Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721853)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Lager
-- Column: I_ModCntr_Log.M_Warehouse_ID
-- 2023-11-15T11:03:08.426Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587538,721854,0,547233,0,TO_TIMESTAMP('2023-11-15 13:03:08.284','YYYY-MM-DD HH24:MI:SS.US'),100,'Lager oder Ort für Dienstleistung',0,'de.metas.contracts','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','Y','Y','N','N','N','N','N','N','Lager',0,200,0,1,1,TO_TIMESTAMP('2023-11-15 13:03:08.284','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T11:03:08.427Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721854 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T11:03:08.428Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2023-11-15T11:03:08.434Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721854
;

-- 2023-11-15T11:03:08.435Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721854)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Pauschale - Vertragsperiode
-- Column: I_ModCntr_Log.C_Flatrate_Term_ID
-- 2023-11-15T11:03:53.860Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721842,0,547233,551283,621287,'F',TO_TIMESTAMP('2023-11-15 13:03:53.733','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Pauschale - Vertragsperiode',10,0,0,TO_TIMESTAMP('2023-11-15 13:03:53.733','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Produkt
-- Column: I_ModCntr_Log.M_Product_ID
-- 2023-11-15T11:04:02.358Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721843,0,547233,551283,621288,'F',TO_TIMESTAMP('2023-11-15 13:04:02.24','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',20,0,0,TO_TIMESTAMP('2023-11-15 13:04:02.24','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Rechnungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroup_ID
-- 2023-11-15T11:04:16.949Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721844,0,547233,551283,621289,'F',TO_TIMESTAMP('2023-11-15 13:04:16.799','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungsgruppe',30,0,0,TO_TIMESTAMP('2023-11-15 13:04:16.799','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Maßeinheit
-- Column: I_ModCntr_Log.C_UOM_ID
-- 2023-11-15T11:04:28.989Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721845,0,547233,551283,621290,'F',TO_TIMESTAMP('2023-11-15 13:04:28.845','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',40,0,0,TO_TIMESTAMP('2023-11-15 13:04:28.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Preiseinheit
-- Column: I_ModCntr_Log.Price_UOM_ID
-- 2023-11-15T11:04:45.110Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721028,0,547233,551283,621291,'F',TO_TIMESTAMP('2023-11-15 13:04:44.981','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Preiseinheit',50,0,0,TO_TIMESTAMP('2023-11-15 13:04:44.981','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Währung
-- Column: I_ModCntr_Log.C_Currency_ID
-- 2023-11-15T11:04:55.002Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721847,0,547233,551283,621292,'F',TO_TIMESTAMP('2023-11-15 13:04:54.875','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N',0,'Währung',60,0,0,TO_TIMESTAMP('2023-11-15 13:04:54.875','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Erntejahr
-- Column: I_ModCntr_Log.Harvesting_Year_ID
-- 2023-11-15T11:05:07.353Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721848,0,547233,551283,621293,'F',TO_TIMESTAMP('2023-11-15 13:05:07.206','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Erntejahr',70,0,0,TO_TIMESTAMP('2023-11-15 13:05:07.206','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Kalender
-- Column: I_ModCntr_Log.C_Calendar_ID
-- 2023-11-15T11:05:16.623Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721752,0,547233,551283,621294,'F',TO_TIMESTAMP('2023-11-15 13:05:16.463','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnung des Buchführungs-Kalenders','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','N','Y','N','N','N',0,'Kalender',80,0,0,TO_TIMESTAMP('2023-11-15 13:05:16.463','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Bausteine
-- Column: I_ModCntr_Log.ModCntr_Module_ID
-- 2023-11-15T11:05:27.376Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721850,0,547233,551283,621295,'F',TO_TIMESTAMP('2023-11-15 13:05:27.242','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Bausteine',90,0,0,TO_TIMESTAMP('2023-11-15 13:05:27.242','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Produzent
-- Column: I_ModCntr_Log.Producer_BPartner_ID
-- 2023-11-15T11:05:37.135Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721851,0,547233,551283,621296,'F',TO_TIMESTAMP('2023-11-15 13:05:36.993','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Produzent',100,0,0,TO_TIMESTAMP('2023-11-15 13:05:36.993','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Rechnungspartner
-- Column: I_ModCntr_Log.Bill_BPartner_ID
-- 2023-11-15T11:05:48.205Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721852,0,547233,551283,621297,'F',TO_TIMESTAMP('2023-11-15 13:05:48.067','YYYY-MM-DD HH24:MI:SS.US'),100,'Geschäftspartner für die Rechnungsstellung','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','Y','N','N','N',0,'Rechnungspartner',110,0,0,TO_TIMESTAMP('2023-11-15 13:05:48.067','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Sammelstelle
-- Column: I_ModCntr_Log.CollectionPoint_BPartner_ID
-- 2023-11-15T11:06:00.347Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721853,0,547233,551283,621298,'F',TO_TIMESTAMP('2023-11-15 13:06:00.222','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Sammelstelle',120,0,0,TO_TIMESTAMP('2023-11-15 13:06:00.222','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> resolved.Lager
-- Column: I_ModCntr_Log.M_Warehouse_ID
-- 2023-11-15T11:06:12.257Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721854,0,547233,551283,621299,'F',TO_TIMESTAMP('2023-11-15 13:06:12.12','YYYY-MM-DD HH24:MI:SS.US'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','N','N',0,'Lager',130,0,0,TO_TIMESTAMP('2023-11-15 13:06:12.12','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: dataimport
-- 2023-11-15T11:06:22.868Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547101,551303,TO_TIMESTAMP('2023-11-15 13:06:22.736','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','dataimport',40,TO_TIMESTAMP('2023-11-15 13:06:22.736','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> dataimport.Daten Import Verlauf
-- Column: I_ModCntr_Log.C_DataImport_Run_ID
-- 2023-11-15T11:06:36.971Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720710,0,547233,551303,621300,'F',TO_TIMESTAMP('2023-11-15 13:06:36.827','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Daten Import Verlauf',10,0,0,TO_TIMESTAMP('2023-11-15 13:06:36.827','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Tab: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs
-- Table: I_ModCntr_Log
-- 2023-11-15T11:06:52.695Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2023-11-15 13:06:52.695','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547233
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Collection Point Key
-- Column: I_ModCntr_Log.CollectionPointValue
-- 2023-11-15T11:11:09.234Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620617
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Geschäftspartner-Schlüssel
-- Column: I_ModCntr_Log.BPartnerValue
-- 2023-11-15T11:11:11.898Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620619
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Produktschlüssel
-- Column: I_ModCntr_Log.ProductValue
-- 2023-11-15T11:11:13.987Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620632
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Name der Rechnungsstellungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-11-15T11:11:17.177Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620890
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Lager
-- Column: I_ModCntr_Log.WarehouseName
-- 2023-11-15T11:11:19.182Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620618
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Zeile Nr.
-- Column: I_ModCntr_Log.I_LineNo
-- 2023-11-15T11:11:23.450Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-11-15 13:11:23.45','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620638
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Importiert
-- Column: I_ModCntr_Log.I_IsImported
-- 2023-11-15T11:11:48.438Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-11-15 13:11:48.438','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620623
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> import.Zeileninhalt
-- Column: I_ModCntr_Log.I_LineContent
-- 2023-11-15T11:12:24.391Z
UPDATE AD_UI_Element SET AD_Field_ID=720715, Name='Zeileninhalt',Updated=TO_TIMESTAMP('2023-11-15 13:12:24.39','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620628
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Menge
-- Column: I_ModCntr_Log.Qty
-- 2023-11-15T11:14:24.924Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2023-11-15 13:14:24.924','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620630
;

