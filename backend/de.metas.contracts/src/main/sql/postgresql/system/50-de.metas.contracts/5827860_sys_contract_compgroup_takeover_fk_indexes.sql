-- Contract compensation groups: SO->PO take-over -- index on the take-over foreign key of the take-over product table,
-- so that deleting a take-over record does not seq-scan the take-over product table for its FK check.

CREATE INDEX IF NOT EXISTS c_compgroup_contractsettings_takeover_product_takeover_id
    ON C_CompensationGroup_ContractSettings_TakeOver_Product (C_CompensationGroup_ContractSettings_TakeOver_ID)
;
