UPDATE DD_OrderLine ol
SET dd_networkdistribution_id=(SELECT nl.dd_networkdistribution_id
                               FROM dd_networkdistributionline nl
                               WHERE nl.dd_networkdistributionline_id = ol.dd_networkdistributionline_id)
WHERE ol.dd_networkdistribution_id IS NULL
  AND ol.dd_networkdistributionline_id IS NOT NULL
;

