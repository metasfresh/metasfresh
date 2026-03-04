-- C_PurchaseCandidate: add M_HU_PI_Item_Product_ID and QtyEnteredTU columns
-- to transfer packing instructions and TU quantities from SO line to purchase candidate and PO.

-- Column: M_HU_PI_Item_Product_ID
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS M_HU_PI_Item_Product_ID NUMERIC(10);

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'mhupiitemproduct_cpurchasecandidate') THEN
        ALTER TABLE C_PurchaseCandidate ADD CONSTRAINT mhupiitemproduct_cpurchasecandidate
            FOREIGN KEY (M_HU_PI_Item_Product_ID) REFERENCES M_HU_PI_Item_Product(M_HU_PI_Item_Product_ID);
    END IF;
END $$;

-- Column: QtyEnteredTU
ALTER TABLE C_PurchaseCandidate ADD COLUMN IF NOT EXISTS QtyEnteredTU NUMERIC;
