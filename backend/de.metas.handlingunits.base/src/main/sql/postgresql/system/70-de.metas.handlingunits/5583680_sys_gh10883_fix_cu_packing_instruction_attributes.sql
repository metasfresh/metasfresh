

-- 2021-03-24T11:04:51.137Z
-- URL zum Konzept
INSERT INTO M_HU_PI_Attribute (AD_Client_ID, AD_Org_ID, Created, CreatedBy, HU_TansferStrategy_JavaClass_ID, IsActive, IsDisplayed, IsInstanceAttribute, IsMandatory, IsOnlyIfInProductAttributeSet, IsReadOnly, M_Attribute_ID, M_HU_PI_Attribute_ID, M_HU_PI_Version_ID, PropagationType, SeqNo, Updated, UpdatedBy, UseInASI)
VALUES (0, 0, TO_TIMESTAMP('2021-03-24 12:04:51', 'YYYY-MM-DD HH24:MI:SS'), 100, 540028, 'Y', 'Y', 'N', 'N', 'N', 'N', 540004, 540069, 101, 'TOPD', 130, TO_TIMESTAMP('2021-03-24 12:04:51', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y')
;

-- 2021-03-24T11:04:58.207Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET IsInstanceAttribute='Y', Updated=TO_TIMESTAMP('2021-03-24 12:04:58', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID = 540069
;

-- 2021-03-24T11:05:17.191Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET SeqNo=5, Updated=TO_TIMESTAMP('2021-03-24 12:05:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID = 540069
;


-- 2021-03-24T11:05:46.991Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET SplitterStrategy_JavaClass_ID=540021, Updated=TO_TIMESTAMP('2021-03-24 12:05:46', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID = 540069
;

-- 2021-03-24T11:05:56.981Z
-- URL zum Konzept
UPDATE M_HU_PI_Attribute
SET AggregationStrategy_JavaClass_ID=50000, Updated=TO_TIMESTAMP('2021-03-24 12:05:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE M_HU_PI_Attribute_ID = 540069
;

UPDATE M_HU_PI_Attribute
SET updated='2021-03-24 13:04:05.125539 +01:00', updatedby=99, hu_tansferstrategy_javaclass_id=540026 /*skip*/
WHERE M_HU_PI_Attribute_ID = 1000020; /*WeightGross*/

UPDATE M_HU_PI_Attribute SET updated='2021-03-24 13:04:05.125539 +01:00', updatedby=99, seqno=15 WHERE m_hu_pi_version_id = 101 AND seqno IN (5/*WeightNet*/);

UPDATE M_HU_PI_Attribute SET updated='2021-03-24 13:04:05.125539 +01:00', updatedby=99, c_uom_id=540017 /*KG*/ WHERE m_hu_pi_version_id = 101 AND seqno IN (10/*weightgross*/, 15/*WeightNet*/, 20/*weightare*/);

UPDATE M_HU_PI_Attribute SET updated='2021-03-24 13:04:05.125539 +01:00', updatedby=99, isreadonly='Y' WHERE m_hu_pi_version_id = 101 AND seqno IN (15/*WeightNet*/, 20/*weightare*/);

UPDATE M_HU_PI_Attribute SET updated='2021-03-24 13:04:05.125539 +01:00', updatedby=99, propagationtype='BOTU'/*bottom-up*/, aggregationstrategy_javaclass_id=50000/*SUM*/ WHERE m_hu_pi_version_id = 101 AND seqno IN (20/*weightare*/);