
--
-- clean up legacy AD_Column.ColumnClass
--
-- 07.12.2016 15:09
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=500062
;

-- 07.12.2016 15:09
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=500062
;

-- 07.12.2016 15:09
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=500061
;

-- 07.12.2016 15:09
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=500061
;

-- 07.12.2016 15:09
-- URL zum Konzept
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=500060
;

-- 07.12.2016 15:09
-- URL zum Konzept
DELETE FROM AD_Element WHERE AD_Element_ID=500060
;

COMMIT;
alter table AD_Column drop ColumnClass;