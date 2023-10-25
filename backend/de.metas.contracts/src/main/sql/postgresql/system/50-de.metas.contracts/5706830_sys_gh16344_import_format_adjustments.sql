-- Run mode: SWING_CLIENT

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> DB-Tabelle
-- Column: I_ModCntr_Log.AD_Table_ID
-- 2023-10-12T02:54:39.709Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-12 03:54:39.709','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720722
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> DB-Tabelle
-- Column: I_ModCntr_Log.AD_Table_ID
-- 2023-10-12T02:54:47.020Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-10-12 03:54:47.02','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720722
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: tableIDs
-- 2023-10-12T02:55:16.935Z
UPDATE AD_UI_ElementGroup SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-12 03:55:16.935','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551190
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20
-- UI Element Group: tableIDs
-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> tableIDs.DB-Tabelle
-- Column: I_ModCntr_Log.AD_Table_ID
-- 2023-10-12T02:55:53.046Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620633
;

-- 2023-10-12T02:55:53.048Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=551190
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Name der Rechnungsstellungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-10-12T03:03:23.890Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587542,721026,0,547233,0,TO_TIMESTAMP('2023-10-12 04:03:23.463','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Name der Rechnungsstellungsgruppe',0,10,0,1,1,TO_TIMESTAMP('2023-10-12 04:03:23.463','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-12T03:03:23.899Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-12T03:03:23.918Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582753)
;

-- 2023-10-12T03:03:23.926Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721026
;

-- 2023-10-12T03:03:23.928Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721026)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Name der Rechnungsstellungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-10-12T03:04:11.667Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721026,0,547233,551191,620890,'F',TO_TIMESTAMP('2023-10-12 04:04:11.291','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name der Rechnungsstellungsgruppe',50,0,0,TO_TIMESTAMP('2023-10-12 04:04:11.291','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Preiseinheit
-- Column: I_ModCntr_Log.PriceUOM
-- 2023-10-12T03:07:43.167Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587543,721027,0,547233,0,TO_TIMESTAMP('2023-10-12 04:07:42.439','YYYY-MM-DD HH24:MI:SS.US'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','N','Preiseinheit',0,20,0,1,1,TO_TIMESTAMP('2023-10-12 04:07:42.439','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-12T03:07:43.170Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-12T03:07:43.174Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582754)
;

-- 2023-10-12T03:07:43.180Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721027
;

-- 2023-10-12T03:07:43.181Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721027)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Preiseinheit
-- Column: I_ModCntr_Log.PriceUOM
-- 2023-10-12T03:07:51.702Z
UPDATE AD_Field SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2023-10-12 04:07:51.702','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721027
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Preiseinheit
-- Column: I_ModCntr_Log.PriceUOM
-- 2023-10-12T03:08:43.744Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721027,0,547233,551189,620891,'F',TO_TIMESTAMP('2023-10-12 04:08:42.149','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Preiseinheit',50,0,0,TO_TIMESTAMP('2023-10-12 04:08:42.149','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Preiseinheit
-- Column: I_ModCntr_Log.PriceUOM
-- 2023-10-12T03:09:23.692Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2023-10-12 04:09:23.691','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620891
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Preiseinheit
-- Column: I_ModCntr_Log.Price_UOM_ID
-- 2023-10-12T03:09:46.370Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587528,721028,0,547233,0,TO_TIMESTAMP('2023-10-12 04:09:45.484','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Preiseinheit',0,30,0,1,1,TO_TIMESTAMP('2023-10-12 04:09:45.484','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-12T03:09:46.373Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-12T03:09:46.376Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542464)
;

-- 2023-10-12T03:09:46.390Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721028
;

-- 2023-10-12T03:09:46.392Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721028)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Preiseinheit
-- Column: I_ModCntr_Log.Price_UOM_ID
-- 2023-10-12T03:10:19.963Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721028,0,547233,551189,620892,'F',TO_TIMESTAMP('2023-10-12 04:10:18.69','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Preiseinheit',50,0,0,TO_TIMESTAMP('2023-10-12 04:10:18.69','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Preiseinheit
-- Column: I_ModCntr_Log.Price_UOM_ID
-- 2023-10-12T03:10:47.572Z
UPDATE AD_UI_Element SET SeqNo=28,Updated=TO_TIMESTAMP('2023-10-12 04:10:47.572','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620892
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Einzelpreis
-- Column: I_ModCntr_Log.PriceActual
-- 2023-10-12T03:16:13.578Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587527,721029,0,547233,0,TO_TIMESTAMP('2023-10-12 04:16:11.591','YYYY-MM-DD HH24:MI:SS.US'),100,'Effektiver Preis',0,'de.metas.contracts','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.',0,'Y','Y','Y','N','N','N','N','N','N','Einzelpreis',0,40,0,1,1,TO_TIMESTAMP('2023-10-12 04:16:11.591','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-12T03:16:13.581Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-12T03:16:13.584Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(519)
;

-- 2023-10-12T03:16:13.610Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721029
;

-- 2023-10-12T03:16:13.611Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721029)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Preiseinheit
-- Column: I_ModCntr_Log.Price_UOM_ID
-- 2023-10-12T03:16:44.856Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620892
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Einzelpreis
-- Column: I_ModCntr_Log.PriceActual
-- 2023-10-12T03:16:57.541Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721029,0,547233,551189,620893,'F',TO_TIMESTAMP('2023-10-12 04:16:57.13','YYYY-MM-DD HH24:MI:SS.US'),100,'Effektiver Preis','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','N','Y','N','N','N',0,'Einzelpreis',50,0,0,TO_TIMESTAMP('2023-10-12 04:16:57.13','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Einzelpreis
-- Column: I_ModCntr_Log.PriceActual
-- 2023-10-12T03:17:02.351Z
UPDATE AD_UI_Element SET SeqNo=24,Updated=TO_TIMESTAMP('2023-10-12 04:17:02.351','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620893
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Zeile Nr.
-- Column: I_ModCntr_Log.I_LineNo
-- 2023-10-12T03:24:44.513Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.513','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620638
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Belegart
-- Column: I_ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-10-12T03:24:44.518Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.518','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620636
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Nr.
-- Column: I_ModCntr_Log.DocumentNo
-- 2023-10-12T03:24:44.522Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.522','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620637
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Collection Point Key
-- Column: I_ModCntr_Log.CollectionPointValue
-- 2023-10-12T03:24:44.526Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.526','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620617
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Geschäftspartner-Schlüssel
-- Column: I_ModCntr_Log.BPartnerValue
-- 2023-10-12T03:24:44.529Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.529','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620619
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Rechnungspartner Suchschlüssel
-- Column: I_ModCntr_Log.Bill_BPartner_Value
-- 2023-10-12T03:24:44.532Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.532','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620620
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Name der Rechnungsstellungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-10-12T03:24:44.535Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.535','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620890
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Produktschlüssel
-- Column: I_ModCntr_Log.ProductValue
-- 2023-10-12T03:24:44.537Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.537','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620632
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Menge
-- Column: I_ModCntr_Log.Qty
-- 2023-10-12T03:24:44.539Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.539','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620630
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Symbol
-- Column: I_ModCntr_Log.UOMSymbol
-- 2023-10-12T03:24:44.542Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.542','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620631
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Einzelpreis
-- Column: I_ModCntr_Log.PriceActual
-- 2023-10-12T03:24:44.544Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.544','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620893
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Preiseinheit
-- Column: I_ModCntr_Log.PriceUOM
-- 2023-10-12T03:24:44.546Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.546','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620891
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Betrag
-- Column: I_ModCntr_Log.Amount
-- 2023-10-12T03:24:44.548Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.548','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620634
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Jahr
-- Column: I_ModCntr_Log.FiscalYear
-- 2023-10-12T03:24:44.550Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620621
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Lager
-- Column: I_ModCntr_Log.WarehouseName
-- 2023-10-12T03:24:44.553Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.553','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620618
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.ISO Währungscode
-- Column: I_ModCntr_Log.ISO_Code
-- 2023-10-12T03:24:44.555Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.555','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620635
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> flags.Aktiv
-- Column: I_ModCntr_Log.IsActive
-- 2023-10-12T03:24:44.557Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.557','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620622
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> flags.Verarbeitet
-- Column: I_ModCntr_Log.Processed
-- 2023-10-12T03:24:44.559Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.559','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620625
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> import.Importiert
-- Column: I_ModCntr_Log.I_IsImported
-- 2023-10-12T03:24:44.561Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.561','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620623
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> import.Import-Fehlermeldung
-- Column: I_ModCntr_Log.I_ErrorMsg
-- 2023-10-12T03:24:44.564Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.564','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620624
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> org.Organisation
-- Column: I_ModCntr_Log.AD_Org_ID
-- 2023-10-12T03:24:44.567Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-10-12 04:24:44.567','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620626
;

-- 2023-10-12T03:29:09.883Z
UPDATE AD_ImpFormat_Row SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-12 04:29:09.882','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541938
;

-- 2023-10-12T03:29:15.233Z
UPDATE AD_ImpFormat_Row SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-12 04:29:15.231','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541936
;

-- 2023-10-12T03:29:19.305Z
UPDATE AD_ImpFormat_Row SET IsActive='N',Updated=TO_TIMESTAMP('2023-10-12 04:29:19.304','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541939
;

-- 2023-10-12T03:38:21.333Z
UPDATE AD_ImpFormat_Row SET IsActive='Y',Updated=TO_TIMESTAMP('2023-10-12 04:38:21.331','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541938
;

-- 2023-10-12T03:38:23.944Z
UPDATE AD_ImpFormat_Row SET IsActive='Y',Updated=TO_TIMESTAMP('2023-10-12 04:38:23.943','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541936
;

-- 2023-10-12T03:38:28.635Z
UPDATE AD_ImpFormat_Row SET IsActive='Y',Updated=TO_TIMESTAMP('2023-10-12 04:38:28.635','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541939
;

-- Process: ImportModularContractLogs(de.metas.contracts.modular.log.impexp.ImportModularContractLog)
-- Table: I_ModCntr_Log
-- EntityType: de.metas.contracts
-- 2023-10-12T04:19:10.593Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2023-10-12 05:19:10.593','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541431
;

-- Value: ImportModularContractLogs
-- Classname: de.metas.contracts.modular.impexp.ImportModularContractLog
-- 2023-10-12T04:21:03.148Z
UPDATE AD_Process SET Classname='de.metas.contracts.modular.impexp.ImportModularContractLog',Updated=TO_TIMESTAMP('2023-10-12 05:21:03.147','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585323
;

-- 2023-10-12T04:27:28.354Z
UPDATE AD_ImpFormat SET IsManualImport='N',Updated=TO_TIMESTAMP('2023-10-12 05:27:28.354','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540092
;

-- Run mode: SWING_CLIENT

-- Column: I_ModCntr_Log.ModCntr_Module_ID
-- 2023-10-12T07:14:11.997Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587545,582426,0,19,542372,'ModCntr_Module_ID',TO_TIMESTAMP('2023-10-12 08:14:11.652','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bausteine',0,0,TO_TIMESTAMP('2023-10-12 08:14:11.652','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-12T07:14:12.006Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587545 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-12T07:14:12.027Z
/* DDL */  select update_Column_Translation_From_AD_Element(582426)
;

-- 2023-10-12T07:14:16.210Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN ModCntr_Module_ID NUMERIC(10)')
;

-- 2023-10-12T07:14:16.219Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT ModCntrModule_IModCntrLog FOREIGN KEY (ModCntr_Module_ID) REFERENCES public.ModCntr_Module DEFERRABLE INITIALLY DEFERRED
;


-- Run mode: SWING_CLIENT

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Contract Module Name
-- Column: I_ModCntr_Log.ContractModuleName
-- 2023-10-12T09:48:29.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587525,721035,0,547233,0,TO_TIMESTAMP('2023-10-12 10:48:27.241','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Contract Module Name',0,50,0,1,1,TO_TIMESTAMP('2023-10-12 10:48:27.241','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-12T09:48:29.287Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-12T09:48:29.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582738)
;

-- 2023-10-12T09:48:29.313Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721035
;

-- 2023-10-12T09:48:29.314Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721035)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Contract Module Name
-- Column: I_ModCntr_Log.ContractModuleName
-- 2023-10-12T09:49:23.400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721035,0,547233,551191,620900,'F',TO_TIMESTAMP('2023-10-12 10:49:23.036','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Contract Module Name',60,0,0,TO_TIMESTAMP('2023-10-12 10:49:23.036','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Contract Module Name
-- Column: I_ModCntr_Log.ContractModuleName
-- 2023-10-12T09:49:45.943Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-10-12 10:49:45.943','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620900
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Produktschlüssel
-- Column: I_ModCntr_Log.ProductValue
-- 2023-10-12T09:49:45.957Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-10-12 10:49:45.956','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620632
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Menge
-- Column: I_ModCntr_Log.Qty
-- 2023-10-12T09:49:45.968Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-10-12 10:49:45.968','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620630
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Symbol
-- Column: I_ModCntr_Log.UOMSymbol
-- 2023-10-12T09:49:45.979Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-10-12 10:49:45.979','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620631
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Einzelpreis
-- Column: I_ModCntr_Log.PriceActual
-- 2023-10-12T09:49:45.993Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-10-12 10:49:45.993','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620893
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Preiseinheit
-- Column: I_ModCntr_Log.PriceUOM
-- 2023-10-12T09:49:46.004Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-10-12 10:49:46.004','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620891
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Betrag
-- Column: I_ModCntr_Log.Amount
-- 2023-10-12T09:49:46.012Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-10-12 10:49:46.012','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620634
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Jahr
-- Column: I_ModCntr_Log.FiscalYear
-- 2023-10-12T09:49:46.016Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-10-12 10:49:46.016','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620621
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Lager
-- Column: I_ModCntr_Log.WarehouseName
-- 2023-10-12T09:49:46.019Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-10-12 10:49:46.019','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620618
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.ISO Währungscode
-- Column: I_ModCntr_Log.ISO_Code
-- 2023-10-12T09:49:46.023Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-10-12 10:49:46.023','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620635
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> flags.Aktiv
-- Column: I_ModCntr_Log.IsActive
-- 2023-10-12T09:49:46.025Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-10-12 10:49:46.025','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620622
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> flags.Verarbeitet
-- Column: I_ModCntr_Log.Processed
-- 2023-10-12T09:49:46.036Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2023-10-12 10:49:46.036','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620625
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> import.Importiert
-- Column: I_ModCntr_Log.I_IsImported
-- 2023-10-12T09:49:46.038Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2023-10-12 10:49:46.038','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620623
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> import.Import-Fehlermeldung
-- Column: I_ModCntr_Log.I_ErrorMsg
-- 2023-10-12T09:49:46.041Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-10-12 10:49:46.041','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620624
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> org.Organisation
-- Column: I_ModCntr_Log.AD_Org_ID
-- 2023-10-12T09:49:46.043Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2023-10-12 10:49:46.043','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620626
;


-- Run mode: SWING_CLIENT

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Vorgangsdatum
-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-12T21:02:05.156Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587544,721036,0,547233,0,TO_TIMESTAMP('2023-10-12 22:01:44.041','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum',0,'de.metas.contracts','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.',0,'Y','Y','Y','N','N','N','N','N','N','Vorgangsdatum',0,60,0,1,1,TO_TIMESTAMP('2023-10-12 22:01:44.041','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-12T21:02:05.168Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-12T21:02:05.186Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1297)
;

-- 2023-10-12T21:02:05.200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721036
;

-- 2023-10-12T21:02:05.201Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721036)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> dates.Vorgangsdatum
-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-12T21:02:54.290Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721036,0,547233,551188,620901,'F',TO_TIMESTAMP('2023-10-12 22:02:52.929','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','Y','N','N','N',0,'Vorgangsdatum',10,0,0,TO_TIMESTAMP('2023-10-12 22:02:52.929','YYYY-MM-DD HH24:MI:SS.US'),100)
;






-- Column: I_ModCntr_Log.Description
-- 2023-10-12T21:44:04.614Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587551,275,0,10,542372,'Description',TO_TIMESTAMP('2023-10-12 22:44:04.387','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2023-10-12 22:44:04.387','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-12T21:44:04.617Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587551 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-12T21:44:04.623Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2023-10-12T21:44:09.752Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN Description VARCHAR(255)')
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Beschreibung
-- Column: I_ModCntr_Log.Description
-- 2023-10-12T21:44:41.211Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587551,721037,0,547233,0,TO_TIMESTAMP('2023-10-12 22:44:40.771','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Beschreibung',0,70,0,1,1,TO_TIMESTAMP('2023-10-12 22:44:40.771','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-12T21:44:41.215Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-12T21:44:41.218Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2023-10-12T21:44:41.488Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721037
;

-- 2023-10-12T21:44:41.488Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721037)
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Beschreibung
-- Column: I_ModCntr_Log.Description
-- 2023-10-12T21:44:44.825Z
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-10-12 22:44:44.825','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721037
;

-- UI Column: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10
-- UI Element Group: description
-- 2023-10-12T21:45:13.252Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547100,551219,TO_TIMESTAMP('2023-10-12 22:45:12.252','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','description',15,TO_TIMESTAMP('2023-10-12 22:45:12.252','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> description.Beschreibung
-- Column: I_ModCntr_Log.Description
-- 2023-10-12T21:45:34.790Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721037,0,547233,551219,620902,'F',TO_TIMESTAMP('2023-10-12 22:45:34.527','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2023-10-12 22:45:34.527','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> dates.Vorgangsdatum
-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-12T21:45:49.006Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.006','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620901
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Collection Point Key
-- Column: I_ModCntr_Log.CollectionPointValue
-- 2023-10-12T21:45:49.019Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.019','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620617
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Geschäftspartner-Schlüssel
-- Column: I_ModCntr_Log.BPartnerValue
-- 2023-10-12T21:45:49.032Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.032','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620619
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Rechnungspartner Suchschlüssel
-- Column: I_ModCntr_Log.Bill_BPartner_Value
-- 2023-10-12T21:45:49.044Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.044','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620620
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Name der Rechnungsstellungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-10-12T21:45:49.054Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.054','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620890
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Contract Module Name
-- Column: I_ModCntr_Log.ContractModuleName
-- 2023-10-12T21:45:49.065Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.064','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620900
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Produktschlüssel
-- Column: I_ModCntr_Log.ProductValue
-- 2023-10-12T21:45:49.075Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.075','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620632
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Menge
-- Column: I_ModCntr_Log.Qty
-- 2023-10-12T21:45:49.086Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.086','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620630
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Symbol
-- Column: I_ModCntr_Log.UOMSymbol
-- 2023-10-12T21:45:49.093Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.093','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620631
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Einzelpreis
-- Column: I_ModCntr_Log.PriceActual
-- 2023-10-12T21:45:49.096Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.096','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620893
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Preiseinheit
-- Column: I_ModCntr_Log.PriceUOM
-- 2023-10-12T21:45:49.099Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.099','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620891
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.Betrag
-- Column: I_ModCntr_Log.Amount
-- 2023-10-12T21:45:49.101Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.101','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620634
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> secondary.Jahr
-- Column: I_ModCntr_Log.FiscalYear
-- 2023-10-12T21:45:49.104Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.104','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620621
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Lager
-- Column: I_ModCntr_Log.WarehouseName
-- 2023-10-12T21:45:49.107Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.107','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620618
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> qty.ISO Währungscode
-- Column: I_ModCntr_Log.ISO_Code
-- 2023-10-12T21:45:49.110Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.11','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620635
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> flags.Aktiv
-- Column: I_ModCntr_Log.IsActive
-- 2023-10-12T21:45:49.113Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.113','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620622
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> flags.Verarbeitet
-- Column: I_ModCntr_Log.Processed
-- 2023-10-12T21:45:49.115Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.115','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620625
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> import.Importiert
-- Column: I_ModCntr_Log.I_IsImported
-- 2023-10-12T21:45:49.117Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.117','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620623
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> import.Import-Fehlermeldung
-- Column: I_ModCntr_Log.I_ErrorMsg
-- 2023-10-12T21:45:49.119Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.119','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620624
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 20 -> org.Organisation
-- Column: I_ModCntr_Log.AD_Org_ID
-- 2023-10-12T21:45:49.121Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2023-10-12 22:45:49.121','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620626
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Name der Rechnungsstellungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-10-12T21:46:41.682Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551184, SeqNo=110,Updated=TO_TIMESTAMP('2023-10-12 22:46:41.682','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620890
;

-- UI Element: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> primary -> 10 -> primary.Name der Rechnungsstellungsgruppe
-- Column: I_ModCntr_Log.ModCntr_InvoicingGroupName
-- 2023-10-12T21:47:23.463Z
UPDATE AD_UI_Element SET SeqNo=95,Updated=TO_TIMESTAMP('2023-10-12 22:47:23.463','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620890
;

-- Table: I_ModCntr_Log
-- 2023-10-12T21:50:06.851Z
UPDATE AD_Table SET Description='Import Vertragsbaustein Log',Updated=TO_TIMESTAMP('2023-10-12 22:50:06.849','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542372
;







