

SELECT public.db_alter_table('M_Product_LotNumber_Lock','ALTER TABLE M_Product_LotNumber_Lock RENAME TO M_Product_LotNumber_Quarantine');
SELECT public.db_alter_table('M_Product_LotNumber_Quarantine', 'ALTER TABLE M_Product_LotNumber_Quarantine RENAME COLUMN M_Product_LotNumber_Lock_ID TO M_Product_LotNumber_Quarantine_ID');