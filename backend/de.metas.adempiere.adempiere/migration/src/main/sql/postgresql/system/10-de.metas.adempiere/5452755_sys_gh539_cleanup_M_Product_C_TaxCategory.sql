
-- clean up M_Product.C_TaxCategory_ID
--
-- DDL
--
ALTER TABLE M_Product DROP COLUMN IF EXISTS C_TaxCategory_ID;

--
-- DML
--

DELETE FROM Exp_formatLine WHERE AD_Column_ID=2019;
DELETE FROM AD_Field WHERE AD_Column_ID=2019;


-- 28.10.2016 08:52
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=2019
;

-- 28.10.2016 08:52
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=2019
;

