-- 2026-08-14
-- Delivery Planning: show the shipper on the Purchase Order window
-- Add M_Shipper_ID (AD_Field 3447) to the Purchase Order window (181) WebUI layout
-- Tab: Bestellung head (AD_Tab_ID=294)
-- Group: incoterms (AD_UI_ElementGroup_ID=547972), SeqNo=30

-- IDs allocated from idserver.metas.de on 2026-08-14:
--   AD_UI_Element 653149 /*From ID Server*/

INSERT INTO AD_UI_Element
(AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
 Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
 IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES
(0, 3447, 0, 294, 547972, 653149 /*From ID Server*/, 'F',
 TO_TIMESTAMP('2026-08-14 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N',
 'N', 0, 'Spediteur', 30, 0, 0,
 TO_TIMESTAMP('2026-08-14 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100)
;
