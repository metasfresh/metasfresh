-- Refund condition lines (C_Flatrate_RefundConfig): the refund is always computed and booked in the sales currency,
-- i.e. in the currency of the invoice candidate whose sales are refunded. An "amount per unit" (RefundBase='F') is
-- interpreted in that currency, so the currency of the condition line is not used anymore.
-- Hide the currency field in the refund-config tab of the contract-conditions window and drop its mandatory logic,
-- so users are not led to believe that the amount is booked or converted in the line's currency.

-- Column C_Flatrate_RefundConfig.C_Currency_ID: no longer mandatory for RefundBase='F'
UPDATE AD_Column SET MandatoryLogic=NULL, Updated=TO_TIMESTAMP('2026-10-09 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=560757
;

-- UI element "Währung" in tab 541106: hidden in single-row and grid view
UPDATE AD_UI_Element SET IsDisplayed='N', IsDisplayedGrid='N', Updated=TO_TIMESTAMP('2026-10-09 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=552502
;
