-- Field: Auftrag -> Auftrag -> IncotermLocation
-- Column: C_Order.IncotermLocation
-- 2023-04-24T12:56:42.730Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2023-04-24 13:56:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501621
;

-- Field: Auftrag -> Auftrag -> IncotermLocation
-- Column: C_Order.IncotermLocation
-- 2023-04-24T12:59:38.306Z
UPDATE AD_Field SET DisplayLogic='@C_Incoterms_ID/-1@>0', IsActive='Y',Updated=TO_TIMESTAMP('2023-04-24 13:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501621
;

-- Field: Auftrag -> Auftrag -> IncotermLocation
-- Column: C_Order.IncotermLocation
-- 2023-04-24T12:59:43.371Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-04-24 13:59:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501621
;

-- UI Element: Auftrag -> Auftrag.Incoterm
-- Column: C_Order.C_Incoterms_ID
-- 2023-04-24T13:00:39.391Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2023-04-24 14:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=591844
;

-- UI Element: Auftrag -> Auftrag.Incoterm
-- Column: C_Order.C_Incoterms_ID
-- 2023-04-24T13:00:56.601Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=1000026, SeqNo=70,Updated=TO_TIMESTAMP('2023-04-24 14:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=591844
;

-- UI Element: Auftrag -> Auftrag.Incoterm Standort
-- Column: C_Order.IncotermLocation
-- 2023-04-24T13:01:00.413Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2023-04-24 14:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544771
;

-- UI Element: Auftrag -> Auftrag.Incoterm Standort
-- Column: C_Order.IncotermLocation
-- 2023-04-24T13:01:13.362Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=1000026, SeqNo=80,Updated=TO_TIMESTAMP('2023-04-24 14:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544771
;

-- Field: Auftrag -> Auftrag -> Incoterms
-- Column: C_Order.C_Incoterms_ID
-- 2023-04-24T13:09:01.319Z
UPDATE AD_Field SET AD_FieldGroup_ID=130,Updated=TO_TIMESTAMP('2023-04-24 14:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=661298
;

-- Field: Auftrag -> Auftrag -> Incoterms
-- Column: C_Order.C_Incoterms_ID
-- 2023-04-24T13:09:15.466Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2023-04-24 14:09:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=661298
;

-- UI Element: Auftrag -> Auftrag.Incoterm Standort
-- Column: C_Order.IncotermLocation
-- 2023-04-24T13:23:00.710Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-04-24 14:23:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544771
;

-- UI Element: Auftrag -> Auftrag.Organisation
-- Column: C_Order.AD_Org_ID
-- 2023-04-24T13:23:00.714Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-04-24 14:23:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

