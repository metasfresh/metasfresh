UPDATE AD_Sequence
SET restartfrequency = CASE
                           WHEN StartNewMonth='N' OR StartNewMonth IS NULL THEN 'Y'
                           WHEN StartNewMonth='Y' THEN 'M'
                       END
WHERE StartNewYear='Y'
;
