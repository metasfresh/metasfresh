
UPDATE AD_ImpFormat_Row r
SET DataType = 'B'
WHERE exists ( select 1 from AD_Column c where c.AD_Column_ID = r.ad_column_ID and c.AD_Reference_ID=20 -- yes/no
) ;