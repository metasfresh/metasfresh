UPDATE ad_reference_trl trl2
SET Name = base2.name,
    Updated = now(),
    UpdatedBy = 99
FROM ad_reference base
         JOIN ad_reference_trl trl
              ON base.ad_reference_id = trl.ad_reference_id
         JOIN ad_reference_trl trl2_match
              ON trl.ad_language = trl2_match.ad_language
                  AND trl.name = trl2_match.name
                  AND trl.ad_reference_id > trl2_match.ad_reference_id
         JOIN ad_reference base2
              ON base2.ad_reference_id = trl2_match.ad_reference_id
WHERE trl2.ad_reference_id = trl2_match.ad_reference_id
  AND trl2.ad_language = trl2_match.ad_language
  AND trl2.name <> base2.name
;
