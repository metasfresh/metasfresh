
CREATE table backup.BKP_M_HU_PI_Attribute_gh11159_25052021
AS
    SELECT * FROM M_HU_PI_Attribute;
	
	


-------------- Packing instructions version for CUs (a.k.a. virtual HUs)




-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000020 -- Gewicht Brutto kg
;

-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000021 -- Gewicht Tara
;


-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=540069 -- Gewicht Netto
;



----Packing Item Template


-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='N', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000023 -- Gewicht Brutto kg
;
-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='N', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000025 -- Gewicht Tara
;
-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000024 -- Gewicht Netto
;


