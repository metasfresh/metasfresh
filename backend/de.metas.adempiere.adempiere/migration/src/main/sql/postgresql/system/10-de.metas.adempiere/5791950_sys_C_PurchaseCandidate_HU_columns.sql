-- C_PurchaseCandidate: add M_HU_PI_Item_Product_ID and QtyEnteredTU columns
-- to transfer packing instructions and TU quantities from SO line to purchase candidate and PO.

-- Column: M_HU_PI_Item_Product_ID
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS M_HU_PI_Item_Product_ID NUMERIC(10);

ALTER TABLE C_PurchaseCandidate ADD CONSTRAINT IF NOT EXISTS mhupiitemproduct_cpurchasecandidate
    FOREIGN KEY (M_HU_PI_Item_Product_ID) REFERENCES M_HU_PI_Item_Product(M_HU_PI_Item_Product_ID);

-- Column: QtyEnteredTU
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS QtyEnteredTU NUMERIC;
