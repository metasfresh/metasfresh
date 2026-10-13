-- SysConfig gate for auto-completion of dropship-warehouse purchase orders auto-created from sales orders.
-- When Y (default), the created dropship PO is completed immediately in the SO transaction (original behaviour).
-- When N, the PO is left in DocStatus=Drafted so procurement can review it before completing manually.
-- This seeds the row with the safe default Y (no behaviour change for existing tenants); the per-instance
-- value (e.g. 'N' to leave POs in draft) is set in the customer repo via set_sysconfig_value once this row exists.

INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
VALUES (0, 0, 541828 /*From ID Server*/, 'O',
        TO_TIMESTAMP('2026-07-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-07-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'Y',
        'de.metas.order.C_Order_CreatePOFromSOs.CompleteDropshipPO',
        'Y',
        'Wenn ''Y'' (Standard), wird die aus einem Verkaufsauftrag automatisch erzeugte Streckengeschäft-Bestellung sofort abgeschlossen. Bei ''N'' bleibt die Bestellung im Status Entwurf, damit der Einkauf sie vor dem Abschluss prüfen kann.')
;
