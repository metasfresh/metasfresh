
-- 2025-12-02T14:09:02.141Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753521
;

-- Field: Bestellung_OLD(181,D) -> Bestellung(294,D) -> Geschäftspartnergruppe
-- Column: C_Order.C_BP_Group_ID
-- 2025-12-02T14:09:02.146Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=753521
;

-- 2025-12-02T14:09:02.153Z
DELETE FROM AD_Field WHERE AD_Field_ID=753521
;

-- 2025-12-02T14:09:02.160Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753524
;

-- Field: Bestellung(541889,D) -> Bestellung(548069,D) -> Geschäftspartnergruppe
-- Column: C_Order.C_BP_Group_ID
-- 2025-12-02T14:09:02.165Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=753524
;

-- 2025-12-02T14:09:02.170Z
DELETE FROM AD_Field WHERE AD_Field_ID=753524
;

-- Column: C_Order.C_BP_Group_ID
-- 2025-12-02T14:09:02.178Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590817
;

-- 2025-12-02T14:09:02.184Z
DELETE FROM AD_Column WHERE AD_Column_ID=590817
;





-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> default.Einkäufer
-- Column: C_Invoice_Candidate.Purchaser_User_ID
-- 2025-12-02T15:09:59.014Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=636960
;

-- 2025-12-02T15:09:59.017Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753522
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Einkäufer
-- Column: C_Invoice_Candidate.Purchaser_User_ID
-- 2025-12-02T15:09:59.023Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=753522
;

-- 2025-12-02T15:09:59.030Z
DELETE FROM AD_Field WHERE AD_Field_ID=753522
;

-- Column: C_Invoice_Candidate.Purchaser_User_ID
-- 2025-12-02T15:09:59.036Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590818
;

-- 2025-12-02T15:09:59.044Z
DELETE FROM AD_Column WHERE AD_Column_ID=590818
;