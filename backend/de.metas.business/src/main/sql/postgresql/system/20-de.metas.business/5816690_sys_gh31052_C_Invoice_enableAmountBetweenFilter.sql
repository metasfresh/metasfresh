-- Run mode: SWING_CLIENT

-- me03#31052 Nach Beträgen suchen können: enable amount from/to (Between) filter

-- Column: C_Invoice.GrandTotal
-- 2026-07-28T10:00:00.000Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-07-28 10:00:00.000','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=3507
;

-- Column: C_Invoice.OpenAmt
-- 2026-07-28T10:00:00.000Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-07-28 10:00:00.000','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589596
;

-- Column: C_Invoice.netsum (Netto Summe)
-- 2026-07-28T10:00:00.000Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-07-28 10:00:00.000','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=544451
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> main -> 10 -> rest.Summe Gesamt
-- Column: C_Invoice.GrandTotal
-- 2026-07-28T13:47:50.687Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2780,0,263,540029,652780,'F',TO_TIMESTAMP('2026-07-28 14:47:49.892','YYYY-MM-DD HH24:MI:SS.US'),100,'Summe über Alles zu diesem Beleg','Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','N','N','N','N','N',0,'Summe Gesamt',60,0,0,TO_TIMESTAMP('2026-07-28 14:47:49.892','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Rechnung_OLD(167,D) -> Rechnung(263,D) -> Offener Betrag
-- Column: C_Invoice.OpenAmt
-- 2026-07-28T13:49:38.557Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589596,781856,0,263,0,TO_TIMESTAMP('2026-07-28 14:49:37.404','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Offener Betrag',0,560,0,1,1,TO_TIMESTAMP('2026-07-28 14:49:37.404','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2026-07-28T13:49:38.669Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781856 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-07-28T13:49:38.768Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1526)
;

-- 2026-07-28T13:49:38.837Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781856
;

-- 2026-07-28T13:49:38.896Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781856)
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> main -> 10 -> rest.Offener Betrag
-- Column: C_Invoice.OpenAmt
-- 2026-07-28T13:50:10.922Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781856,0,263,540029,652781,'F',TO_TIMESTAMP('2026-07-28 14:50:10.033','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','N','N','N','N',0,'Offener Betrag',70,0,0,TO_TIMESTAMP('2026-07-28 14:50:10.033','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> main -> 10 -> rest.Summe Gesamt
-- Column: C_Invoice.GrandTotal
-- 2026-07-28T13:50:40.115Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-07-28 14:50:40.115','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=652780
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> main -> 10 -> rest.Offener Betrag
-- Column: C_Invoice.OpenAmt
-- 2026-07-28T13:50:40.509Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-07-28 14:50:40.509','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=652781
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> main -> 20 -> dates.Belegstatus
-- Column: C_Invoice.DocStatus
-- 2026-07-28T13:50:40.884Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-07-28 14:50:40.883','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=547013
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> main -> 20 -> org.Section Code
-- Column: C_Invoice.M_SectionCode_ID
-- 2026-07-28T13:50:41.252Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-07-28 14:50:41.252','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611343
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> main -> 20 -> posted.Posted
-- Column: C_Invoice.Posted
-- 2026-07-28T13:50:41.615Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-07-28 14:50:41.615','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=564735
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> main -> 20 -> posted.Projekt
-- Column: C_Invoice.C_Project_ID
-- 2026-07-28T13:50:41.989Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-07-28 14:50:41.989','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611329
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> main -> 20 -> org.Sektion
-- Column: C_Invoice.AD_Org_ID
-- 2026-07-28T13:50:42.347Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-07-28 14:50:42.345','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=540791
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> main -> 10 -> preise.Summe Gesamt
-- Column: C_Invoice.GrandTotal
-- 2026-07-28T13:55:36.345Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3337,0,290,540225,652782,'F',TO_TIMESTAMP('2026-07-28 14:55:35.457','YYYY-MM-DD HH24:MI:SS.US'),100,'Summe über Alles zu diesem Beleg','Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','N','N','N','N','N',0,'Summe Gesamt',40,0,0,TO_TIMESTAMP('2026-07-28 14:55:35.457','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> Offener Betrag
-- Column: C_Invoice.OpenAmt
-- 2026-07-28T13:57:36.680Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589596,781857,0,290,0,TO_TIMESTAMP('2026-07-28 14:57:35.684','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Offener Betrag',0,350,0,1,1,TO_TIMESTAMP('2026-07-28 14:57:35.684','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2026-07-28T13:57:36.740Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781857 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-07-28T13:57:36.800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1526)
;

-- 2026-07-28T13:57:36.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781857
;

-- 2026-07-28T13:57:36.923Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781857)
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> main -> 10 -> preise.Offener Betrag
-- Column: C_Invoice.OpenAmt
-- 2026-07-28T13:58:12.463Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781857,0,290,540225,652783,'F',TO_TIMESTAMP('2026-07-28 14:58:11.678','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','N','N','N','N',0,'Offener Betrag',50,0,0,TO_TIMESTAMP('2026-07-28 14:58:11.678','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> main -> 10 -> preise.Offener Betrag
-- Column: C_Invoice.OpenAmt
-- 2026-07-28T13:58:48.698Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-07-28 14:58:48.698','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=652783
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> main -> 20 -> rest.Status
-- Column: C_Invoice.DocStatus
-- 2026-07-28T13:58:49.078Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-07-28 14:58:49.078','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=542729
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> main -> 20 -> posted.Verbucht
-- Column: C_Invoice.Posted
-- 2026-07-28T13:58:49.457Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-07-28 14:58:49.457','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=542660
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> advanced edit -> 10 -> advanced edit.Sales invoice count
-- Column: C_Invoice.Sales_Invoice_Count
-- 2026-07-28T13:58:49.845Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-07-28 14:58:49.845','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=594686
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> main -> 20 -> org.Section Code
-- Column: C_Invoice.M_SectionCode_ID
-- 2026-07-28T13:58:50.249Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-07-28 14:58:50.249','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611346
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Eingangsrechnung(290,D) -> main -> 20 -> org.Sektion
-- Column: C_Invoice.AD_Org_ID
-- 2026-07-28T13:58:50.615Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-07-28 14:58:50.615','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=542725
;
