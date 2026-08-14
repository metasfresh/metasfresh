-- Fix carrier-service selection on the C_Order "Lieferweg-Services" labels widget.
-- The services are a Labels widget over the C_Order_Carrier_Service junction. The labels lookup exposes ONLY
-- the host link column (C_Order_ID) as a validation-rule parameter, so the previous rule keyed on
-- @Carrier_Product_ID@ never resolved in that context (it fell back to -1) and no services were selectable.
-- Derive the order's Carrier_Product_ID by joining C_Order on @C_Order_ID@ (the parameter the labels lookup
-- provides), so the services are again constrained to the selected carrier product's allocations.
UPDATE AD_Val_Rule
   SET Code='Carrier_Service_ID IN (SELECT cs.Carrier_Service_ID FROM Carrier_Service cs JOIN Carrier_Product_Service_Alloc a ON a.Carrier_Service_ID = cs.Carrier_Service_ID JOIN C_Order o ON o.C_Order_ID = @C_Order_ID/-1@ WHERE a.IsActive=''Y'' AND a.Carrier_Product_ID = o.Carrier_Product_ID)',
       Updated=TO_TIMESTAMP('2026-08-13 10:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
 WHERE AD_Val_Rule_ID=540794
;
