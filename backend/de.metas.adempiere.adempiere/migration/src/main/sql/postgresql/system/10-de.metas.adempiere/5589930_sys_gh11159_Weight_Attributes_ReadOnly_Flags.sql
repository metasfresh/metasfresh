
CREATE table backup.BKP_M_HU_PI_Attribute_gh11159_25052021
AS
    SELECT * FROM M_HU_PI_Attribute;
	
	


-------------- Packing instructions version for CUs (a.k.a. virtual HUs)




-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='N',
isonlyifinproductattributeset = 'N',
 Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000020 -- Gewicht Brutto kg
;

-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='N', 
isonlyifinproductattributeset = 'N',
Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
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
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000025 -- Gewicht Tara
;
-- 2021-05-25T11:49:28.076Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-05-25 13:49:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID=1000024 -- Gewicht Netto
;





-- 2021-05-26T12:04:46.984Z
-- URL zum Konzept
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_ID,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (1000000,1000000,TO_TIMESTAMP('2021-05-26 15:04:45','YYYY-MM-DD HH24:MI:SS'),100,540017,540027,'Y','Y','Y','N','N','N',540016,540070,100,'NONE',9110,TO_TIMESTAMP('2021-05-26 15:04:45','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2021-05-26T12:05:13.940Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute SET SeqNo=35,Updated=TO_TIMESTAMP('2021-05-26 15:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540070
;

