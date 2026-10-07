-- gh#30934: External Reference window 540901 — show Organization column in single-record + grid view
-- Tab 542376 (S_ExternalReference), UI element group 543614 ("default").
-- AD_Field 599747 (AD_Org_ID / "Sektion") already exists but was hidden.
-- Pattern mirrored from existing single-field elements in group 543614: a plain AD_UI_Element
-- (AD_UI_ElementType='F', AD_Field_ID set, AD_Tab_ID populated). Those siblings have NO
-- AD_UI_ElementField row, so none is created here.
-- AD_UI_Element_ID 652679 allocated from idserver.metas.de on 2026-07-20.

-- A. Enable AD_Field 599747 for display in single-record form and grid
UPDATE AD_Field
SET IsDisplayed      = 'Y',
    IsDisplayedGrid  = 'Y',
    Updated          = TO_TIMESTAMP('2026-07-20 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy        = 100
WHERE AD_Field_ID = 599747
;

-- B. Insert the AD_UI_Element for AD_Org_ID into group 543614 (SeqNo=90 appends after the current max 80)
INSERT INTO AD_UI_Element
    (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID,
     IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, AD_UI_ElementGroup_ID, AD_Field_ID, AD_UI_ElementType,
     Name, SeqNo, SeqNoGrid,
     IsDisplayed, IsDisplayedGrid,
     IsDisplayed_SideList, SeqNo_SideList,
     IsAdvancedField, IsAllowFiltering, WidgetSize)
VALUES
    (652679 /*From ID Server*/, 0, 0,
     'Y',
     TO_TIMESTAMP('2026-07-20 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-07-20 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542376, 543614, 599747, 'F',
     'Sektion', 90, 90,
     'Y', 'Y',
     'N', 0,
     'N', 'N', NULL)
;
