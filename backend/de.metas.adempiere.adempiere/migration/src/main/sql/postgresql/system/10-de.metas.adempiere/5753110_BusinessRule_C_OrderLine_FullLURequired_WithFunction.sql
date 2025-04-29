-- Name: C_OrderLine Require Full LU
-- 2025-04-29T20:35:20.696Z
UPDATE AD_Val_Rule SET Code=' /* partner does not require a full LU */
    EXISTS (SELECT 1
            FROM C_BPartner bp
                     JOIN C_Order o ON bp.c_bpartner_id = o.c_bpartner_id
            WHERE o.c_order_id = C_OrderLine.c_order_id
              AND bp.isfulllurequired = ''N'')

        /* no M_HU_PI_Item exists for the TU on a current LU version */
        OR NOT EXISTS (SELECT 1
                       FROM getTUItemsOnCurrentLUVersions(C_OrderLine.c_orderline_id) tu)

        /* all the M_HU_PI_Items match the qty */
        OR NOT EXISTS (SELECT 1
                       FROM getTUItemsOnCurrentLUVersions(C_OrderLine.c_orderline_id) tu
                       WHERE qtyenteredtu % tu.qty != 0)

        /* Multiple M_HU_PI_Items exist for the TU on a current LU version, both fitting and not fitting the qty */
        OR (
        EXISTS (SELECT 1
                FROM getTUItemsOnCurrentLUVersions(C_OrderLine.c_orderline_id) tu
                WHERE qtyenteredtu % tu.qty != 0)
            AND EXISTS (SELECT 1
                        FROM getTUItemsOnCurrentLUVersions(C_OrderLine.c_orderline_id) tu
                        WHERE qtyenteredtu % tu.qty = 0)
        )',Updated=TO_TIMESTAMP('2025-04-29 20:35:20.696000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540724
;
