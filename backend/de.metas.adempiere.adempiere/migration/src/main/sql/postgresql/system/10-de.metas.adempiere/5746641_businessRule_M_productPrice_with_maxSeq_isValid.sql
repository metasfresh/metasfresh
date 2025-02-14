-- Run mode: WEBUI

-- TODO add message

-- 2025-02-14T10:25:53.008Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Message_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsDebug,Name,Updated,UpdatedBy,Validation_Rule_ID) VALUES (540004,0,null/*TODO FIXME*/,0,251,TO_TIMESTAMP('2025-02-14 10:25:52.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','M_ProductPrice with max seqNo is valid',TO_TIMESTAMP('2025-02-14 10:25:52.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540700)
;

-- 2025-02-14T16:49:18.800Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540004,540009,0,0,TO_TIMESTAMP('2025-02-14 16:49:18.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','Y','Y',251,'(SELECT pp.m_productprice_id
        FROM m_productprice pp
                 INNER JOIN
             (SELECT MAX(seqNo) AS seqNo, SUM(COUNT(1)) OVER (PARTITION BY m_product_id, m_pricelist_version_id) AS count, m_product_id, m_pricelist_version_id
              FROM m_productprice
              GROUP BY m_product_id, m_pricelist_version_id) maxSeqRows ON pp.m_product_id = maxSeqRows.m_product_id
                 AND pp.m_pricelist_version_id = maxSeqRows.m_pricelist_version_id
                 AND pp.seqno = maxSeqRows.seqNo
                 AND pp.isactive = ''Y''
                 AND maxSeqRows.count > 1
                 AND pp.m_product_id = m_productprice.m_product_id AND pp.m_pricelist_version_id = m_productprice.m_pricelist_version_id)',TO_TIMESTAMP('2025-02-14 16:49:18.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
