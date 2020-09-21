UPDATE m_product_planningschema
SET ad_org_id = (SELECT ad_org_id
                 FROM ad_org
                 WHERE ad_org.ad_org_id != 0
                   AND ad_org.isactive = 'Y'
                 ORDER BY ad_org_id
                 LIMIT 1
)
  , Updated=TO_TIMESTAMP('2020-09-21 12:16:00', 'YYYY-MM-DD HH24:MI:SS')
  , UpdatedBy=100
WHERE ad_org_id = 0
;
