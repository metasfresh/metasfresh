DELETE
FROM AD_Element_Link
WHERE AD_Field_ID IN (SELECT AD_Field_ID
                      FROM AD_Field
                      WHERE ad_column_id = 53408)
;

DELETE
FROM AD_UI_Element
WHERE AD_Field_ID IN (SELECT AD_Field_ID
                      FROM AD_Field
                      WHERE ad_column_id = 53408)
;

DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID IN (SELECT AD_Field_ID
                      FROM AD_Field
                      WHERE ad_column_id = 53408)
;


DELETE
FROM AD_Field
WHERE AD_Field_ID IN (SELECT AD_Field_ID
                      FROM AD_Field
                      WHERE ad_column_id = 53408)
;

DELETE
FROM EXP_FormatLine
WHERE AD_Column_ID = 53408
;

-- Column: M_Product.LowLevel
-- 2024-02-16T09:00:14.430Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53408
;

-- 2024-02-16T09:00:14.435Z
DELETE FROM AD_Column WHERE AD_Column_ID=53408
;

ALTER TABLE M_Product
    DROP COLUMN LowLevel
;

