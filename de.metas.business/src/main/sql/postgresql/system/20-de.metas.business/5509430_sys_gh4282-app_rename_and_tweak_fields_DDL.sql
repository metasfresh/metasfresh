
SELECT db_alter_table('M_DiscountSchemaBreak', 'ALTER TABLE M_DiscountSchemaBreak RENAME COLUMN Std_AddAmt TO PricingSystemSurchargeAmt');
SELECT db_alter_table('I_DiscountSchema', 'ALTER TABLE I_DiscountSchema RENAME COLUMN Std_AddAmt TO PricingSystemSurchargeAmt');

SELECT db_alter_table('M_DiscountSchemaBreak', 'ALTER TABLE M_DiscountSchemaBreak RENAME COLUMN PriceStd TO PriceStdFixed');
SELECT db_alter_table('I_DiscountSchema', 'ALTER TABLE I_DiscountSchema RENAME COLUMN PriceStd TO PriceStdFixed');

