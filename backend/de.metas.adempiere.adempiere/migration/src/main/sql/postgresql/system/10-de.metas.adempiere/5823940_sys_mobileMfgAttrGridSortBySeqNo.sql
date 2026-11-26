-- MobileUI Manufacturing config — default-sort the editable-attributes child grid by SeqNo ascending.
-- Window 541788, tab 549417 (Merkmale / MobileUI_MFG_Config_Attribute): SeqNo drives the order the
-- attributes are presented on the mobile receive dialog, so the grid must default to SeqNo ascending.
-- AD_Field.SortNo is the signed grid default-sort key (sign = asc(+)/desc(-), magnitude = priority);
-- +1 = primary sort key ascending. Field 783058 = the SeqNo field on that tab.

-- 2026-09-10 09:00:00
UPDATE AD_Field SET SortNo=1, Updated=TO_TIMESTAMP('2026-09-10 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=783058;
