-- Run mode: WEBUI

-- Name: M_ProductPrice with maxSeq is valid
-- 2025-02-14T08:26:37.287Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540700,TO_TIMESTAMP('2025-02-14 08:26:37.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','M_ProductPrice with maxSeq is valid','S',TO_TIMESTAMP('2025-02-14 08:26:37.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_ProductPrice with maxSeq is valid
-- 2025-02-14T12:44:50.672Z
UPDATE AD_Val_Rule SET Code='EXISTS (SELECT 1
              FROM m_productprice pp
                       LEFT OUTER JOIN (SELECT pp.m_productprice_id, pp.m_product_id, pp.m_pricelist_version_id, pp.isinvalidprice, pp.isattributedependant, pp.m_hu_pi_item_product_id
                                        FROM m_productprice pp
                                                 INNER JOIN
                                             (SELECT MAX(seqNo) AS seqNo, SUM(COUNT(1)) OVER (PARTITION BY m_product_id, m_pricelist_version_id) AS count, m_product_id, m_pricelist_version_id
                                              FROM m_productprice
                                              GROUP BY m_product_id, m_pricelist_version_id) maxSeqRows ON pp.m_product_id = maxSeqRows.m_product_id
                                                 AND pp.m_pricelist_version_id = maxSeqRows.m_pricelist_version_id
                                                 AND pp.seqno = maxSeqRows.seqNo
                                                 AND maxSeqRows.count > 1) maxSeqPPs ON pp.m_product_id = maxSeqPPs.m_product_id AND pp.m_pricelist_version_id = maxSeqPPs.m_pricelist_version_id
              WHERE (
                  maxSeqPPs.m_productprice_id IS NULL
                      OR maxSeqPPs.m_hu_pi_item_product_id IS NOT NULL
                      OR maxSeqPPs.isattributedependant = ''Y''
                      OR maxSeqPPs.isinvalidprice = ''N'')
                AND pp.m_productprice_id = m_productprice.m_productprice_id)',Updated=TO_TIMESTAMP('2025-02-14 12:44:50.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540700
;
