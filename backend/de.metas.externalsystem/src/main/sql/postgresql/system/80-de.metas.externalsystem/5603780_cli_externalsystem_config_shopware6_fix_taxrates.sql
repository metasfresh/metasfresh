
UPDATE externalsystem_config_shopware6
SET freightcost_normalvat_rates='0, 7.7, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 27',
    freightcost_reduced_vat_rates='2.5, 5, 5.5, 6, 7, 8, 9, 10',
    updatedby=99, updated='2021-09-08 10:12'
WHERE externalsystem_config_shopware6_id = 540000
;
