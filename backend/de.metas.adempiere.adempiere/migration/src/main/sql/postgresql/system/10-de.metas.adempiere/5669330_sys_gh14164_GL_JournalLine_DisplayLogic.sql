-- Field: Hauptbuch Journal(540356,D) -> Position(540855,D) -> Auftrag/Bestellung (Haben)
-- Column: GL_JournalLine.CR_C_Order_ID
-- 2022-12-19T13:41:31.254Z
UPDATE AD_Field SET DisplayLogic='@$Element_OR@=''Y''',Updated=TO_TIMESTAMP('2022-12-19 15:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710007
;

-- Field: Hauptbuch Journal(540356,D) -> Position(540855,D) -> Auftrag/Bestellung (Soll)
-- Column: GL_JournalLine.DR_C_Order_ID
-- 2022-12-19T14:03:20.361Z
UPDATE AD_Field SET DisplayLogic='@$Element_OR@=''Y''',Updated=TO_TIMESTAMP('2022-12-19 16:03:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710006
;

-- Field: Hauptbuch Journal(540356,D) -> Position(540855,D) -> Produkt (Haben)
-- Column: GL_JournalLine.CR_M_Product_ID
-- 2022-12-19T14:03:56.412Z
UPDATE AD_Field SET DisplayLogic='@$Element_PR@=''Y''',Updated=TO_TIMESTAMP('2022-12-19 16:03:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710005
;

-- Field: Hauptbuch Journal(540356,D) -> Position(540855,D) -> Produkt (Soll)
-- Column: GL_JournalLine.DR_M_Product_ID
-- 2022-12-19T14:04:09.984Z
UPDATE AD_Field SET DisplayLogic='@$Element_PR@=''Y''',Updated=TO_TIMESTAMP('2022-12-19 16:04:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710004
;

-- Field: Hauptbuch Journal(540356,D) -> Position(540855,D) -> Sektionskennung (Soll)
-- Column: GL_JournalLine.DR_M_SectionCode_ID
-- 2022-12-19T14:04:19.694Z
UPDATE AD_Field SET DisplayLogic='@$Element_SC@=''Y''',Updated=TO_TIMESTAMP('2022-12-19 16:04:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710008
;

-- Field: Hauptbuch Journal(540356,D) -> Position(540855,D) -> Sektionskennung (Haben)
-- Column: GL_JournalLine.CR_M_SectionCode_ID
-- 2022-12-19T14:04:21.804Z
UPDATE AD_Field SET DisplayLogic='@$Element_SC@=''Y''',Updated=TO_TIMESTAMP('2022-12-19 16:04:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710009
;

