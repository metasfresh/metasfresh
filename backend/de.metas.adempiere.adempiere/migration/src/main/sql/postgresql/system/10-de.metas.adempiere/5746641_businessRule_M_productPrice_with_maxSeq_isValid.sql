-- Run mode: WEBUI

-- Value: de.metas.bussrule.ProductPriceIgnored
-- 2025-02-17T08:48:08.184Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545499,0,TO_TIMESTAMP('2025-02-17 08:48:07.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Der Standardproduktpreis hat "Preis ignorieren" angehakt.','I',TO_TIMESTAMP('2025-02-17 08:48:07.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.bussrule.ProductPriceIgnored')
;

-- 2025-02-17T08:48:08.186Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545499 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.businessrule.ProductPriceIgnored
-- 2025-02-17T08:48:21.692Z
UPDATE AD_Message SET Value='de.metas.businessrule.ProductPriceIgnored',Updated=TO_TIMESTAMP('2025-02-17 08:48:21.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545499
;

-- Value: de.metas.businessrule.ProductPriceIgnored
-- 2025-02-17T08:48:36.083Z
UPDATE AD_Message_Trl SET MsgText='The default product price has "Ignore Price" ticked.',Updated=TO_TIMESTAMP('2025-02-17 08:48:36.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545499
;

-- 2025-02-17T08:48:36.084Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-14T10:25:53.008Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Message_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsDebug,Name,Updated,UpdatedBy,Validation_Rule_ID) VALUES (540004,0,545499,0,251,TO_TIMESTAMP('2025-02-14 10:25:52.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','M_ProductPrice with max seqNo is valid',TO_TIMESTAMP('2025-02-14 10:25:52.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540700)
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
