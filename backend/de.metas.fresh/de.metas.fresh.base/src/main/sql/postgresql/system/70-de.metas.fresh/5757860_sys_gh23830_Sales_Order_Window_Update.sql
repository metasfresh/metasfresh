-- UI Element: Auftrag OLD -> Auftrag.Verbucht
-- Column: C_Order.Posted
-- 2025-06-16T15:50:20.471Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-06-16 16:50:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544788
;

-- UI Element: Auftrag OLD -> Auftrag.Incoterm Standort
-- Column: C_Order.IncotermLocation
-- 2025-06-16T15:50:21.091Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-06-16 16:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544771
;

-- UI Element: Auftrag OLD -> Auftrag.Organisation
-- Column: C_Order.AD_Org_ID
-- 2025-06-16T15:50:21.456Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-06-16 16:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

-- 2025-06-16T15:52:32.118Z
UPDATE AD_UI_ElementGroup SET IsActive='N',Updated=TO_TIMESTAMP('2025-06-16 16:52:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543272
;

