-- Follow-up to 5821150_sys_M_Delivery_Planning_Filterability_And_Addresses.sql (already applied;
-- immutable, so corrections ship here instead of editing it in place). Two fixes:
--
-- 1) AD_Element_Trl base-language rows for elements 585384 (IsAllocated), 585385
--    (ShipFrom_Location_ID), 585386 (ShipTo_Location_ID) were wrongly marked
--    IsTranslated='Y' for de_DE/de_CH without any text change -- that mis-marks an
--    auto-copied base-language row as a confirmed translation. Reverted to 'N', matching
--    the de_DE/de_CH convention used two weeks earlier for the same-domain element 585298
--    (see 5819020_sys_gh_DeliveryPlanning_M_Shipper_IsCreateDeliveryPlanning.sql).
--
-- 2) 5821150's own comment claimed all six now-filterable columns on AD_Tab 546674 are
--    already displayed in the grid. That claim is false for three of them: the governing
--    flag for a WebUI grid column is AD_UI_Element.IsDisplayedGrid (AD_Table.AccessLevel=3,
--    not System-only, so the legacy AD_Field.IsDisplayedGrid='Y' on all six is not what the
--    WebUI reads). M_MeansOfTransportation_ID, AD_Org_ID and IsClosed had
--    AD_UI_Element.IsDisplayedGrid='N' / SeqNoGrid=0 -- i.e. usable as a filter but invisible
--    in the result grid. Flipped to 'Y' with a SeqNoGrid placed in the existing reading
--    order (checked against every sibling AD_UI_Element.SeqNoGrid AND every sibling
--    AD_Field.SeqNoGrid on the same tab -- both layers are collision-free):
--      - M_MeansOfTransportation_ID -> 40 (right after TransportDirection/30, its natural
--        neighbour -- transport-mode fields together, ahead of Incoterms/60)
--      - IsClosed                   -> 50 (grouped with the transport-mode/status fields
--        just before Incoterms/60)
--      - AD_Org_ID                  -> 390 (appended after the current highest, Processed/380
--        -- Organisation must be the last grid column per the window design rules)
--
-- IDs allocated from idserver.metas.de on 2026-08-28:
--   AD_MigrationScript 5821160 (this file)

-- ============================================================================
-- 1a) AD_Element_Trl: 585384 (IsAllocated) -- revert de_DE/de_CH to IsTranslated='N'
-- ============================================================================
UPDATE AD_Element_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-28 14:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585384 AND AD_Language='de_CH'
;
/* DDL */ select update_ad_element_on_ad_element_trl_update(585384,'de_CH')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585384,'de_CH')
;

UPDATE AD_Element_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-28 14:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585384 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585384,'de_DE')
;

-- ============================================================================
-- 1b) AD_Element_Trl: 585385 (ShipFrom_Location_ID) -- revert de_DE/de_CH to IsTranslated='N'
-- ============================================================================
UPDATE AD_Element_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-28 14:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585385 AND AD_Language='de_CH'
;
/* DDL */ select update_ad_element_on_ad_element_trl_update(585385,'de_CH')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585385,'de_CH')
;

UPDATE AD_Element_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-28 14:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585385 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585385,'de_DE')
;

-- ============================================================================
-- 1c) AD_Element_Trl: 585386 (ShipTo_Location_ID) -- revert de_DE/de_CH to IsTranslated='N'
-- ============================================================================
UPDATE AD_Element_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-28 14:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585386 AND AD_Language='de_CH'
;
/* DDL */ select update_ad_element_on_ad_element_trl_update(585386,'de_CH')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585386,'de_CH')
;

UPDATE AD_Element_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-28 14:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585386 AND AD_Language='de_DE'
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585386,'de_DE')
;

-- ============================================================================
-- 2) AD_UI_Element: make the three filterable columns visible in the grid too
--    (AD_Tab 546674, AD_Window 541632)
-- ============================================================================
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-08-28 14:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=613935 /* M_MeansOfTransportation_ID */
;
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-08-28 14:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=613916 /* IsClosed */
;
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=390, Updated=TO_TIMESTAMP('2026-08-28 14:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=613478 /* AD_Org_ID */
;
