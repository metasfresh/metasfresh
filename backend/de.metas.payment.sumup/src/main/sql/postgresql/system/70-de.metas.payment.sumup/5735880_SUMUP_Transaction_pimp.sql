-- Column: SUMUP_Transaction.ExternalId
-- Column: SUMUP_Transaction.ExternalId
-- 2024-10-04T15:56:13.220Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2024-10-04 18:56:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589227
;

-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- 2024-10-04T15:56:26.790Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2024-10-04 18:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589229
;

-- Column: SUMUP_Transaction.Timestamp
-- Column: SUMUP_Transaction.Timestamp
-- 2024-10-04T15:56:57.968Z
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2024-10-04 18:56:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589230
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Client Transaction Id
-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.Client Transaction Id
-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- 2024-10-04T18:12:10.545Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-10-04 21:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626135
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Zeitstempel
-- Column: SUMUP_Transaction.Timestamp
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.Zeitstempel
-- Column: SUMUP_Transaction.Timestamp
-- 2024-10-04T18:12:10.561Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-10-04 21:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626134
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Status
-- Column: SUMUP_Transaction.Status
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> status.Status
-- Column: SUMUP_Transaction.Status
-- 2024-10-04T18:12:10.569Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-10-04 21:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626140
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Währungscode
-- Column: SUMUP_Transaction.CurrencyCode
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> amount.Währungscode
-- Column: SUMUP_Transaction.CurrencyCode
-- 2024-10-04T18:12:10.579Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-10-04 21:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626138
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Betrag
-- Column: SUMUP_Transaction.Amount
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> amount.Betrag
-- Column: SUMUP_Transaction.Amount
-- 2024-10-04T18:12:10.589Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-10-04 21:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626139
;

-- UI Element: SumUp Transaction -> SumUp Transaction.POS Order
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.POS Order
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- 2024-10-04T18:12:10.597Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-10-04 21:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626132
;

-- UI Element: SumUp Transaction -> SumUp Transaction.POS Payment
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.POS Payment
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- 2024-10-04T18:12:10.604Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-10-04 21:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626133
;

-- Field: SumUp Transaction -> SumUp Transaction -> Erstellt
-- Column: SUMUP_Transaction.Created
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Erstellt
-- Column: SUMUP_Transaction.Created
-- 2024-10-04T18:12:31.157Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589221,731844,0,547617,TO_TIMESTAMP('2024-10-04 21:12:30','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.payment.sumup','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2024-10-04 21:12:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T18:12:31.161Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T18:12:31.164Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2024-10-04T18:12:31.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731844
;

-- 2024-10-04T18:12:31.410Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731844)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Aktualisiert
-- Column: SUMUP_Transaction.Updated
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Aktualisiert
-- Column: SUMUP_Transaction.Updated
-- 2024-10-04T18:12:44.477Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589224,731845,0,547617,TO_TIMESTAMP('2024-10-04 21:12:44','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.payment.sumup','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2024-10-04 21:12:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T18:12:44.479Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T18:12:44.482Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2024-10-04T18:12:44.563Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731845
;

-- 2024-10-04T18:12:44.565Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731845)
;

-- UI Column: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: created/updated
-- 2024-10-04T18:13:22.983Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547590,552035,TO_TIMESTAMP('2024-10-04 21:13:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','created/updated',20,TO_TIMESTAMP('2024-10-04 21:13:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Erstellt
-- Column: SUMUP_Transaction.Created
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> created/updated.Erstellt
-- Column: SUMUP_Transaction.Created
-- 2024-10-04T18:13:30.732Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731844,0,547617,552035,626146,'F',TO_TIMESTAMP('2024-10-04 21:13:30','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','Y','N','N','Erstellt',10,0,0,TO_TIMESTAMP('2024-10-04 21:13:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Aktualisiert
-- Column: SUMUP_Transaction.Updated
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> created/updated.Aktualisiert
-- Column: SUMUP_Transaction.Updated
-- 2024-10-04T18:13:37.057Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731845,0,547617,552035,626147,'F',TO_TIMESTAMP('2024-10-04 21:13:36','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','Y','N','N','Aktualisiert',20,0,0,TO_TIMESTAMP('2024-10-04 21:13:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: SumUp Transaction -> SumUp Transaction -> SumUp Transaction
-- Column: SUMUP_Transaction.SUMUP_Transaction_ID
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> SumUp Transaction
-- Column: SUMUP_Transaction.SUMUP_Transaction_ID
-- 2024-10-04T18:15:01.863Z
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2024-10-04 21:15:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731829
;

-- Column: SUMUP_Transaction.Created
-- Column: SUMUP_Transaction.Created
-- 2024-10-04T18:17:03.495Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 21:17:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589221
;

