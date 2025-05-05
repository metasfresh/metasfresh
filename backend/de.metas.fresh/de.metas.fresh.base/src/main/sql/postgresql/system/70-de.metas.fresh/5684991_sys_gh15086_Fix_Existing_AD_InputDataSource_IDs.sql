-- 2023-05-30 update AD_InputDataSource_ID to 540234 to have the same ID going forward
UPDATE AD_InputDataSource
SET AD_InputDataSource_ID=540234, Updated='2023-05-30 15:36', updatedby=99
WHERE AD_InputDataSource_ID = 1000005
  AND Value = 'SOURCE.de.metas.rest_api.v2.invoice.InvoicesRestController'
;
