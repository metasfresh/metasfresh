
-- Name: C_OrderLine Require Full LU
-- 2025-04-29T14:30:11.446Z
UPDATE AD_Val_Rule SET Code='    /*partner does not require a full LU */
    EXISTS(SELECT 1
           FROM C_BPartner bp
                    JOIN C_Order o ON bp.c_bpartner_id = o.c_bpartner_id
           WHERE o.c_order_id = c_orderline.c_order_id
             AND bp.isfulllurequired = ''N'')
        /* no M_HU_PI_Item exists for the TU on a current LU version */
        OR NOT EXISTS (SELECT 1
                       FROM M_HU_PI_Item tuItemOnLUVersion
                                JOIN M_HU_PI_Version luVersion ON tuItemOnLUVersion.m_hu_pi_version_id = luVersion.m_hu_pi_version_id
                                JOIN m_hu_pi tuPI ON tuItemOnLUVersion.included_hu_pi_id = tuPI.m_hu_pi_id
                                JOIN m_hu_pi_version tuVersion ON tuPI.m_hu_pi_id = tuVersion.m_hu_pi_id
                                JOIN M_HU_PI_Item tuItemOnTUVersion ON tuItemOnTUVersion.m_hu_pi_version_id = tuVersion.m_hu_pi_version_id
                                JOIN M_HU_PI_Item_Product hupip ON tuItemOnTUVersion.m_hu_pi_item_id = hupip.m_hu_pi_item_id
                                JOIN C_Order o ON o.c_order_id = C_OrderLine.c_order_id
                       WHERE luVersion.hu_unittype = ''LU''
                         AND luVersion.iscurrent = ''Y''
                         AND tuItemOnLUVersion.itemtype = ''HU''
                         AND (tuItemOnLUVersion.c_bpartner_id IS NULL OR tuItemOnLUVersion.c_bpartner_id = o.c_bpartner_id)
                         AND tuVersion.hu_unittype = ''TU''
                         AND tuVersion.iscurrent = ''Y''
                         AND tuItemOnTUVersion.itemtype = ''MI''
                         AND (tuItemOnTUVersion.c_bpartner_id IS NULL OR tuItemOnTUVersion.c_bpartner_id = o.c_bpartner_id)
                         AND hupip.m_hu_pi_item_product_id = C_OrderLine.m_hu_pi_item_product_id)
        /* Only one M_HU_PI_Item exists for the TU on a current LU version OR there are multiple but the quantity fits them all*/
        OR EXISTS (SELECT 1
                   FROM M_HU_PI_Item tuItemOnLUVersion
                            JOIN M_HU_PI_Version luVersion ON tuItemOnLUVersion.m_hu_pi_version_id = luVersion.m_hu_pi_version_id
                            JOIN m_hu_pi tuPI ON tuItemOnLUVersion.included_hu_pi_id = tuPI.m_hu_pi_id
                            JOIN m_hu_pi_version tuVersion ON tuPI.m_hu_pi_id = tuVersion.m_hu_pi_id
                            JOIN M_HU_PI_Item tuItemOnTUVersion ON tuItemOnTUVersion.m_hu_pi_version_id = tuVersion.m_hu_pi_version_id
                            JOIN M_HU_PI_Item_Product hupip ON tuItemOnTUVersion.m_hu_pi_item_id = hupip.m_hu_pi_item_id
                            JOIN C_Order o ON o.c_order_id = C_OrderLine.c_order_id
                   WHERE luVersion.hu_unittype = ''LU''
                     AND luVersion.iscurrent = ''Y''
                     AND tuItemOnLUVersion.itemtype = ''HU''
                     AND (tuItemOnLUVersion.c_bpartner_id IS NULL OR tuItemOnLUVersion.c_bpartner_id = o.c_bpartner_id)
                     AND tuVersion.hu_unittype = ''TU''
                     AND tuVersion.iscurrent = ''Y''
                     AND tuItemOnTUVersion.itemtype = ''MI''
                     AND (tuItemOnTUVersion.c_bpartner_id IS NULL OR tuItemOnTUVersion.c_bpartner_id = o.c_bpartner_id)
                     AND hupip.m_hu_pi_item_product_id = C_OrderLine.m_hu_pi_item_product_id
                     AND qtyenteredtu % tuItemOnLUVersion.qty = 0
                     AND NOT EXISTS(SELECT 1
                                    FROM M_HU_PI_Item tuItemOnLUVersion1
                                             JOIN M_HU_PI_Version luVersion1 ON tuItemOnLUVersion1.m_hu_pi_version_id = luVersion1.m_hu_pi_version_id
                                             JOIN m_hu_pi tuPI1 ON tuItemOnLUVersion1.included_hu_pi_id = tuPI1.m_hu_pi_id
                                             JOIN m_hu_pi_version tuVersion1 ON tuPI1.m_hu_pi_id = tuVersion1.m_hu_pi_id
                                             JOIN M_HU_PI_Item tuItemOnTUVersion1 ON tuItemOnTUVersion1.m_hu_pi_version_id = tuVersion1.m_hu_pi_version_id
                                             JOIN M_HU_PI_Item_Product hupip1 ON tuItemOnTUVersion1.m_hu_pi_item_id = hupip1.m_hu_pi_item_id
                                    WHERE luVersion1.hu_unittype = ''LU''
                                      AND luVersion1.iscurrent = ''Y''
                                      AND tuItemOnLUVersion1.itemtype = ''HU''
                                      AND (tuItemOnLUVersion1.c_bpartner_id IS NULL OR tuItemOnLUVersion1.c_bpartner_id = o.c_bpartner_id)
                                      AND tuVersion1.hu_unittype = ''TU''
                                      AND tuVersion1.iscurrent = ''Y''
                                      AND tuItemOnTUVersion1.itemtype = ''MI''
                                      AND (tuItemOnTUVersion1.c_bpartner_id IS NULL OR tuItemOnTUVersion1.c_bpartner_id = o.c_bpartner_id)
                                      AND hupip1.m_hu_pi_item_product_id = C_OrderLine.m_hu_pi_item_product_id
                                      AND qtyenteredtu % tuItemOnLUVersion1.qty != 0))',Updated=TO_TIMESTAMP('2025-04-29 14:30:11.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540724
;
