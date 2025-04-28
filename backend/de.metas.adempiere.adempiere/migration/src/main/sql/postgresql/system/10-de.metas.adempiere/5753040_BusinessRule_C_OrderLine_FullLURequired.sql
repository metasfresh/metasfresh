
-- Name: C_OrderLine Require Full LU
-- 2025-04-28T19:19:46.286Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540724,TO_TIMESTAMP('2025-04-28 19:19:46.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','C_OrderLine Require Full LU','S',TO_TIMESTAMP('2025-04-28 19:19:46.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: C_OrderLine Require Full LU
-- 2025-04-28T19:19:53.754Z
UPDATE AD_Val_Rule SET Code='EXISTS(SELECT 1
             FROM C_BPartner bp
             WHERE bp.c_bpartner_id = c_orderline.c_bpartner_id
               AND isfulllurequired = ''N'')
   OR EXISTS (SELECT 1
              FROM M_HU_PI_Item tuItemOnLUVersion
                       JOIN M_HU_PI_Version luVersion ON tuItemOnLUVersion.m_hu_pi_version_id = luVersion.m_hu_pi_version_id
                       JOIN m_hu_pi tuPI ON tuItemOnLUVersion.included_hu_pi_id = tuPI.m_hu_pi_id
                       JOIN m_hu_pi_version tuVersion ON tuPI.m_hu_pi_id = tuVersion.m_hu_pi_id
                       JOIN M_HU_PI_Item tuItemOnTUVersion ON tuItemOnTUVersion.m_hu_pi_version_id = tuVersion.m_hu_pi_version_id
                       JOIN M_HU_PI_Item_Product hupip ON tuItemOnTUVersion.m_hu_pi_item_id = hupip.m_hu_pi_item_id
              WHERE luVersion.hu_unittype = ''LU''
                AND luVersion.iscurrent = ''Y''
                AND tuItemOnLUVersion.itemtype = ''HU''
                AND tuVersion.hu_unittype = ''TU''
                AND tuVersion.iscurrent = ''Y''
                AND tuItemOnTUVersion.itemtype = ''MI''
                AND hupip.m_hu_pi_item_product_id = m_hu_pi_item_product_id
                AND qtyenteredtu % tuItemOnLUVersion.qty = 0)',Updated=TO_TIMESTAMP('2025-04-28 19:19:53.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540724
;

-- Name: C_OrderLine Require Full LU
-- 2025-04-28T19:19:59.578Z
UPDATE AD_Val_Rule SET EntityType='D',Updated=TO_TIMESTAMP('2025-04-28 19:19:59.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540724
;

-- Value: C_OrderLine Require Full LU
-- 2025-04-28T19:21:59.065Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545544,0,TO_TIMESTAMP('2025-04-28 19:21:59.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Die eingegebene Menge ist kein Vielfaches der erforderlichen LU-Menge. Bitte passen Sie die Menge so an, dass vollständige LU-Einheiten gemäß den Anforderungen des Partners verwendet werden.','I',TO_TIMESTAMP('2025-04-28 19:21:59.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_OrderLine Require Full LU')
;

-- 2025-04-28T19:21:59.067Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545544 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_OrderLine Require Full LU
-- 2025-04-28T19:22:01.282Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2025-04-28 19:22:01.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545544
;

-- Value: C_OrderLine Require Full LU
-- 2025-04-28T19:22:16.603Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-28 19:22:16.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545544
;

-- Value: C_OrderLine Require Full LU
-- 2025-04-28T19:22:20.840Z
UPDATE AD_Message_Trl SET MsgText='The entered quantity is not a multiple of the required LU quantity. Please adjust the quantity to match full LU units as required by the partner.',Updated=TO_TIMESTAMP('2025-04-28 19:22:20.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545544
;

-- 2025-04-28T19:22:20.846Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-28T19:22:42.020Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsDebug,Name,Updated,UpdatedBy,Validation_Rule_ID,Warning_Message_ID) VALUES (540023,0,0,260,TO_TIMESTAMP('2025-04-28 19:22:41.981000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','C_OrderLine Require Full LU',TO_TIMESTAMP('2025-04-28 19:22:41.981000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540724,545544)
;

-- 2025-04-28T19:22:59.846Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540023,540028,0,0,TO_TIMESTAMP('2025-04-28 19:22:59.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y',260,'C_OrderLine_ID',TO_TIMESTAMP('2025-04-28 19:22:59.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

