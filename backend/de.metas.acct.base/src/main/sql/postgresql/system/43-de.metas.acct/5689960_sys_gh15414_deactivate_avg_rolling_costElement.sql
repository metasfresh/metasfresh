
UPDATE M_CostElement
SET IsActive='N', Description='Inactive because moving average invoice does not yet work with production. If you don''t use production, feel free to activate.\n' || Description, Updated='2023-05-31 16:26', Updatedby=99
WHERE CostingMethod = 'M' /*MovingAverageInvoice*/;
