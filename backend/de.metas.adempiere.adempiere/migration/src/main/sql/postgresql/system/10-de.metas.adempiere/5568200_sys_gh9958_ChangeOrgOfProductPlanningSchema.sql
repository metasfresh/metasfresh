UPDATE m_product_planningschema
SET ad_org_id = (SELECT o.ad_org_id
                 FROM ad_org o
                 WHERE o.ad_org_id != 0
                   AND o.isactive = 'Y'
                 ORDER BY o.ad_org_id
                 LIMIT 1
)
  , Updated=TO_TIMESTAMP('2020-09-21 12:16:00', 'YYYY-MM-DD HH24:MI:SS')
  , UpdatedBy=100
WHERE ad_org_id = 0
;
