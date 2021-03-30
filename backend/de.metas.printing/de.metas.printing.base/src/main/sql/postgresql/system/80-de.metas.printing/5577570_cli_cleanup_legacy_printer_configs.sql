
DELETE
FROM ad_printer_config c
WHERE created < '2016-01-01'
  AND NOT EXISTS(SELECT 1 FROM ad_printer_matching m WHERE m.ad_printer_config_id = c.ad_printer_config_id)
;
