-- 2017-08-29T17:49:27.451
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=504607
;

-- 2017-08-29T17:49:27.458
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=504607
;

-- 2017-08-29T17:50:33.456
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=504606
;

-- 2017-08-29T17:50:33.458
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=504606
;


-- 2017-08-29T17:51:37.114
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=550244
;

-- 2017-08-29T17:51:37.117
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=550244
;

-- do not drop the physical columns. They might already be filled
-- ALTER TABLE C_Invoice DROP COLUMN Ref_CreditMemo_ID;
COMMENT ON COLUMN C_Invoice.Ref_CreditMemo_ID 
IS 'This column is obsolete since issue "Possibility to easily create more than 1 referenced Doc per Invoice" https://github.com/metasfresh/metasfresh/issues/529';

-- ALTER TABLE C_Invoice DROP COLUMN Ref_AdjustmentCharge_ID;
COMMENT ON COLUMN C_Invoice.Ref_AdjustmentCharge_ID 
IS 'This column is obsolete since issue "Possibility to easily create more than 1 referenced Doc per Invoice" https://github.com/metasfresh/metasfresh/issues/529';


