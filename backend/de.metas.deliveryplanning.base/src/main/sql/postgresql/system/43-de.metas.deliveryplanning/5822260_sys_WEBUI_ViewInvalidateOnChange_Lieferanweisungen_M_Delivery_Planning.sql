-- Task Q14 (delivery planning quantities): TC11 - editing a planning's quantity must make the delivery
-- instruction line show the new figure without a manual reload. Deriving the M_ShippingPackage columns
-- (5822240) makes the value CURRENT; only this registration makes it APPEAR - without it, the WebUI view
-- open on the Lieferanweisungen window keeps serving its cached rows until the user reloads by hand.

INSERT INTO WEBUI_ViewInvalidateOnChange (
    WEBUI_ViewInvalidateOnChange_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Window_ID, AD_Table_ID)
SELECT 540002 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-03 09:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-03 09:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       541657, 542259
WHERE NOT EXISTS (
    SELECT 1 FROM WEBUI_ViewInvalidateOnChange existing
    WHERE existing.AD_Window_ID = 541657 AND existing.AD_Table_ID = 542259 AND existing.IsActive = 'Y'
);
