UPDATE pp_product_bomline
SET IssuingTolerance_ValueType='P',
    issuingtolerance_perc=COALESCE(issuingtolerance_perc, 0)
WHERE isenforceissuingtolerance = 'Y'
  AND IssuingTolerance_ValueType IS NULL
;

