-- 2018-04-05T16:15:22.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=2654, ColumnName='QtyInternalUse', Description='Internal Use Quantity removed from Inventory', FieldLength=10, Help='Quantity of product inventory used internally (positive if taken out - negative if returned)', Name='Internal Use Qty',Updated=TO_TIMESTAMP('2018-04-05 16:15:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8823
;

-- 2018-04-05T16:15:22.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Internal Use Qty', Description='Internal Use Quantity removed from Inventory', Help='Quantity of product inventory used internally (positive if taken out - negative if returned)' WHERE AD_Column_ID=8823
;


/* DDL */ SELECT public.db_alter_table('I_Inventory','ALTER TABLE public.I_Inventory RENAME COLUMN QtyCount TO QtyInternalUse')
;

-- 2018-04-05T16:23:27.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=6705
;

-- 2018-04-05T16:23:27.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=6705
;

delete from ad_impformat_row where ad_column_id = 8825;

-- 2018-04-05T16:24:22.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=8825
;

-- 2018-04-05T16:24:22.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=8825
;

/* DDL */ SELECT public.db_alter_table('I_Inventory','ALTER TABLE public.I_Inventory DROP COLUMN QtyBook')
;

