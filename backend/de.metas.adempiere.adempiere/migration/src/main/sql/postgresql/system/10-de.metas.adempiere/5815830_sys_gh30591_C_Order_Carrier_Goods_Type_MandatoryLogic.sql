-- nShift Lieferweg: make Carrier_Goods_Type_ID mandatory on C_Order once a carrier product is selected.
-- AD_Column 592989 (C_Order.Carrier_Goods_Type_ID) gets MandatoryLogic='@Carrier_Product_ID@!0'.
-- Goods type is always available for every carrier product; the field becomes required as soon
-- as the user picks a carrier product. Carrier_Product_ID itself (AD_Column 592988) stays optional
-- (no MandatoryLogic). The services column (AD_Column 592987) stays optional as well.
--
-- IDs allocated from idserver.metas.de on 2026-07-23:
--   AD_MigrationScript 5815830

UPDATE AD_Column
   SET MandatoryLogic = '@Carrier_Product_ID@!0',
       Updated        = TO_TIMESTAMP('2026-07-23 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy      = 100
 WHERE AD_Column_ID = 592989
;
