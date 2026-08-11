-- VAT-ID online check: show RequesterMemberStateCode + RequesterNumber (AD_Field 781904/781905)
-- in the VATaxID_Config grid so a multi-org install can see which requester VAT-ID each org uses
-- without opening every row. Slotted before AD_Org_ID (SeqNoGrid=60): 45 and 47. WidgetSize='S'
-- on AD_UI_Element given the short content (2- and 20-char values); AD_Field has no WidgetSize
-- column in this codebase version, so it is set on AD_UI_Element only.

UPDATE AD_Field
SET IsDisplayedGrid = 'Y', SeqNoGrid = 45, Updated = TO_TIMESTAMP('2026-08-11 16:50:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781904;

UPDATE AD_Field
SET IsDisplayedGrid = 'Y', SeqNoGrid = 47, Updated = TO_TIMESTAMP('2026-08-11 16:50:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 781905;

UPDATE AD_UI_Element
SET IsDisplayedGrid = 'Y', SeqNoGrid = 45, WidgetSize = 'S', Updated = TO_TIMESTAMP('2026-08-11 16:50:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652817;

UPDATE AD_UI_Element
SET IsDisplayedGrid = 'Y', SeqNoGrid = 47, WidgetSize = 'S', Updated = TO_TIMESTAMP('2026-08-11 16:50:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 652818;
