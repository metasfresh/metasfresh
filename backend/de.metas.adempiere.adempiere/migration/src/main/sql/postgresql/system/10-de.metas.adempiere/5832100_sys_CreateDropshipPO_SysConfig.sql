-- On/off switch for creating dropship purchase orders when generating purchase orders from sales orders.
-- Default 'N' = off. Customers/instances switch it on by setting the value to 'Y'.
--
-- IDs allocated from idserver.metas.de on 2026-10-29:
--   AD_SysConfig 541862 (de.metas.order.C_Order_CreatePOFromSOs.CreateDropshipPO)

INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
SELECT 0, 0, 541862 /*From ID Server*/, 'S',
       TO_TIMESTAMP('2026-10-29 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-10-29 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       'D', 'Y',
       'de.metas.order.C_Order_CreatePOFromSOs.CreateDropshipPO',
       'N',
       'Wenn Y, werden beim Erzeugen von Einkaufsaufträgen aus Verkaufsaufträgen Streckengeschäfts-Einkaufsaufträge erzeugt. Standard: N.'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_SysConfig WHERE Name = 'de.metas.order.C_Order_CreatePOFromSOs.CreateDropshipPO'
);
