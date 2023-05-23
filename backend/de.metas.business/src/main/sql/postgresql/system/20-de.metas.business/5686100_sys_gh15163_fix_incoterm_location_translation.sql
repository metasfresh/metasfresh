-- UI Element: Bestellung -> Bestellung.IncotermLocation
-- Column: C_Order.IncotermLocation
-- 2023-04-26T16:06:30.129Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2023-04-26 18:06:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541235
;

-- UI Element: Bestellung -> Bestellung.IncotermLocation
-- Column: C_Order.IncotermLocation
-- 2023-04-26T16:06:55.615Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=547972, SeqNo=30,Updated=TO_TIMESTAMP('2023-04-26 18:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541235
;

-- UI Element: Bestellung -> Bestellung.IncotermLocation
-- Column: C_Order.IncotermLocation
-- 2023-04-26T16:08:46.021Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541235
;

-- Field: Auftrag -> Auftrag -> Incoterms Stadt
-- Column: C_Order.IncotermLocation
-- 2023-04-26T16:11:42.507Z
UPDATE AD_Field SET AD_Name_ID=580529, Description=NULL, Help=NULL, Name='Incoterms Stadt',Updated=TO_TIMESTAMP('2023-04-26 18:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501621
;

-- 2023-04-26T16:11:42.545Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580529) 
;

-- 2023-04-26T16:11:42.554Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=501621
;

-- 2023-04-26T16:11:42.555Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(501621)
;

