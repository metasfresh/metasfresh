-- C_Flatrate_Term.M_Product_ID (window "Verträge" 540359): a refund term needs no product.
-- Refund terms are category- or bonus-product-based now; the refund engine treats a term without product as "all products"
-- (RefundContractRepository), and a term without product completes (see feature refund_retroactive).
-- The field stays read-only for refund terms, but it was also mandatory for them, so such a term could not be saved.
UPDATE AD_Column
SET MandatoryLogic='@Type_Conditions/''X''@=''Subscr''', Updated=TO_TIMESTAMP('2026-10-06 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 547283 -- C_Flatrate_Term.M_Product_ID
;
