-- Contract compensation groups: SO->PO take-over -- indexes on the take-over foreign keys,
-- so that deleting a take-over record does not seq-scan C_OrderLine / the take-over product table for its FK check.
-- Partial on C_OrderLine: only appended own take-over lines carry the reference.

CREATE INDEX IF NOT EXISTS c_orderline_compgroup_takeover_id
    ON C_OrderLine (C_CompensationGroup_ContractSettings_TakeOver_ID)
    WHERE C_CompensationGroup_ContractSettings_TakeOver_ID IS NOT NULL
;

CREATE INDEX IF NOT EXISTS c_compgroup_contractsettings_takeover_product_takeover_id
    ON C_CompensationGroup_ContractSettings_TakeOver_Product (C_CompensationGroup_ContractSettings_TakeOver_ID)
;
