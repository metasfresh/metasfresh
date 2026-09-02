
DROP INDEX IF EXISTS m_hu_created;

CREATE INDEX m_hu_created
   ON m_hu (created);
COMMENT ON INDEX m_hu_created
  IS 'REquired because the normal gridtabl UI does
  SELECT ...
  FROM M_HU
  WHERE ...
  ORDER BY CREATED
  Limit n
  
and the ordering takes extremely long in the case of M_HU';

-- now we can drop that max records constraint
-- 23.10.2015 06:25
-- URL zum Konzept
UPDATE AD_Tab SET MaxQueryRecords=NULL,Updated=TO_TIMESTAMP('2015-10-23 06:25:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540508
;

