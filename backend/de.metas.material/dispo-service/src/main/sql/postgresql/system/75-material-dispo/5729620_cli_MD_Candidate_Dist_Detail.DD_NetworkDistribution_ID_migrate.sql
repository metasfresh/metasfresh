UPDATE md_candidate_dist_detail dd
SET dd_networkdistribution_id=(SELECT nl.dd_networkdistribution_id
                               FROM dd_networkdistributionline nl
                               WHERE nl.dd_networkdistributionline_id = dd.dd_networkdistributionline_id)
WHERE dd.dd_networkdistribution_id IS NULL
  AND dd.dd_networkdistributionline_id IS NOT NULL
;

