-- Boni refund config (window 540113, tab 541106):
--   1) M_Product_ID is no longer mandatory: a config of a product category applies to the products of that category
--      and has no product of its own. The database column is already nullable, so there is no DDL;
--      the application dictionary flag was the only thing that required the product.
--   2) The bonus product is required while there is no product (the refund line is booked on it). The server side
--      validates the same, so the REST API and imports are covered as well.
--   3) Grid order: the bonus recipient and the packaging filter flag follow the bonus product, so they are no longer
--      pushed off screen by the less important columns; the packaging filter flag column gets a wider header.

UPDATE AD_Column
SET IsMandatory='N', Updated=TO_TIMESTAMP('2026-10-06 09:10:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 560130 -- C_Flatrate_RefundConfig.M_Product_ID
;

UPDATE AD_Column
SET MandatoryLogic='@M_Product_ID/0@=0', Updated=TO_TIMESTAMP('2026-10-06 09:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 593706 -- C_Flatrate_RefundConfig.Bonus_Product_ID
;

-- WebUI grid order (AD_UI_Element): Product 10, Category 12, Bonus product 14, then the recipient and the filter flag
UPDATE AD_UI_Element
SET SeqNoGrid=16, Updated=TO_TIMESTAMP('2026-10-06 09:10:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 654931 -- BonusRecipient
;

UPDATE AD_UI_Element
SET SeqNoGrid=18, WidgetSize='M', Updated=TO_TIMESTAMP('2026-10-06 09:10:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 654932 -- IsPackingOptionFiltered
;

-- the same order for the legacy client (AD_Field); 35 and 36 are free on this tab
UPDATE AD_Field
SET SeqNoGrid=35, Updated=TO_TIMESTAMP('2026-10-06 09:10:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 785603 -- BonusRecipient
;

UPDATE AD_Field
SET SeqNoGrid=36, Updated=TO_TIMESTAMP('2026-10-06 09:10:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 785604 -- IsPackingOptionFiltered
;
