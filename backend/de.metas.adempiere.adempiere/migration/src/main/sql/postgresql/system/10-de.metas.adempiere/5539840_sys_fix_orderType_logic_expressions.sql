
UPDATE AD_Column SET readonlyLogic=replace(readonlyLogic, '@OrderType@', '@OrderType/''?''@'), UpdatedBy=99, Updated='2019-12-19 09:03:38.556194+01' WHERE readonlyLogic ilike '%@OrderType@%';

UPDATE AD_Field SET displaylogic=replace(displaylogic, '@OrderType@', '@OrderType/''?''@'), UpdatedBy=99, Updated='2019-12-19 09:03:38.556194+01' where displaylogic ilike '%@OrderType@%';
