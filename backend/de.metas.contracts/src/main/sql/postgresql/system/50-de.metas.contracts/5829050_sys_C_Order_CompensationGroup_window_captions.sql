-- Order compensation group window (542196): captions.
--   header field C_Order_ID (785662): groups exist on sales and purchase orders -> caption from the existing neutral
--     element 577439 "Auftrag/Bestellung" / "Sales/Purchase Order" (AD_Name_ID; used as such by two other fields)
--   order-lines field Line (785672): caption from element 1002992 "Zeile Nr." / "Line No", as on the sales order's
--     line tab (AD_Name_ID; the column element reads "SeqNo." in en_US). The en_US rows of both elements carry the
--     same Updated timestamp, which makes the propagation skip the field; the element's en_US row is touched first.
--   order-lines tab caption (element 572851 "Auftragsposition", also the sales order's line tab): the German text is
--     final, so de_DE / de_CH are marked as translated; propagated to the tabs

UPDATE AD_Field
SET AD_Name_ID = 577439, Updated = TO_TIMESTAMP('2026-10-09 19:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785662 /* C_Order_CompensationGroup.C_Order_ID */
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(577439)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785662
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785662)
;

UPDATE AD_Field
SET AD_Name_ID = 1002992, Updated = TO_TIMESTAMP('2026-10-09 19:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785672 /* C_OrderLine.Line on tab 549518 */
;
UPDATE AD_Element_Trl
SET Updated = TO_TIMESTAMP('2026-10-09 19:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 1002992 AND AD_Language = 'en_US'
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(1002992)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 785672
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(785672)
;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 19:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 572851 AND AD_Language IN ('de_DE', 'de_CH')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(572851, 'de_DE')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(572851, 'de_CH')
;
