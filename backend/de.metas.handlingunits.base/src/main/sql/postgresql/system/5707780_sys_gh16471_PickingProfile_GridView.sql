-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10
-- UI Element Group: settings
-- 2023-10-19T15:02:36.671Z
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2023-10-19 18:02:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551255
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Name
-- Column: MobileUI_UserProfile_Picking.Name
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> default.Name
-- Column: MobileUI_UserProfile_Picking.Name
-- 2023-10-19T15:03:51.136Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-19 18:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621115
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Erstellen von Lieferung Richtlinie
-- Column: MobileUI_UserProfile_Picking.CreateShipmentPolicy
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> settings.Erstellen von Lieferung Richtlinie
-- Column: MobileUI_UserProfile_Picking.CreateShipmentPolicy
-- 2023-10-19T15:03:51.153Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-19 18:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621123
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Aktiv
-- Column: MobileUI_UserProfile_Picking.IsActive
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Aktiv
-- Column: MobileUI_UserProfile_Picking.IsActive
-- 2023-10-19T15:03:51.169Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-19 18:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621116
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Sektion
-- Column: MobileUI_UserProfile_Picking.AD_Org_ID
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> org.Sektion
-- Column: MobileUI_UserProfile_Picking.AD_Org_ID
-- 2023-10-19T15:03:51.184Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-19 18:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621117
;

-- UI Element: Mobile UI Kommissionierprofil -> Kunden.Geschäftspartner
-- Column: MobileUI_UserProfile_Picking_BPartner.C_BPartner_ID
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Kunden(547259,D) -> main -> 10 -> default.Geschäftspartner
-- Column: MobileUI_UserProfile_Picking_BPartner.C_BPartner_ID
-- 2023-10-19T15:04:03.813Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-19 18:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621119
;

-- UI Element: Mobile UI Kommissionierprofil -> Kunden.Sektion
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Org_ID
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Kunden(547259,D) -> main -> 10 -> default.Sektion
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Org_ID
-- 2023-10-19T15:04:03.836Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-19 18:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621120
;

