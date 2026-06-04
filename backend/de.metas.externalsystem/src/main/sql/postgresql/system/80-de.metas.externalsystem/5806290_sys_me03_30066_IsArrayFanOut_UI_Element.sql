-- Run mode: SWING_CLIENT

-- me03#30066 Display IsArrayFanOut on the ExternalSystem_Endpoint tab.
-- AD_Element (5848980), AD_Column (5925820) and AD_Field (7802600) were created in
-- migration 5804010; only the AD_UI_Element was missing, so the field was invisible in WebUI.
-- Placed in column 2 / flags group (553740) at SeqNo=20, just below Active (SeqNo=10).

INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 7802600, 0, 548506, 553740,
        6519990 /*From ID Server*/, 'F',
        now(), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'Array-Fan-Out', 20, 0, 0,
        now(), 100)
ON CONFLICT (AD_UI_Element_ID) DO NOTHING
;
