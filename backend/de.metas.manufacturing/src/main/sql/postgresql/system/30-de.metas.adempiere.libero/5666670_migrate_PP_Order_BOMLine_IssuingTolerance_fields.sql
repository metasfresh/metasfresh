UPDATE pp_order_bomline
SET IssuingTolerance_ValueType='P',
    IssuingTolerance_Perc=COALESCE(IssuingTolerance_Perc, 0)
WHERE isEnforceIssuingTolerance = 'Y'
  AND IssuingTolerance_ValueType IS NULL
;
