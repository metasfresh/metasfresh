-- Run mode: SWING_CLIENT

-- Tab: Beschaffungsplanung(540285,de.metas.procurement) -> Bestellkandidat
-- Table: PMM_PurchaseCandidate
-- 2026-04-08T07:45:25.929Z
UPDATE AD_Tab SET OrderByClause='',Updated=TO_TIMESTAMP('2026-04-08 07:45:25.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=540725
;

-- Field: Beschaffungsplanung(540285,de.metas.procurement) -> Bestellkandidat(540725,de.metas.procurement) -> Zugesagter Termin
-- Column: PMM_PurchaseCandidate.DatePromised
-- 2026-04-08T07:46:00.289Z
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2026-04-08 07:46:00.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=556657
;

-- Field: Beschaffungsplanung(540285,de.metas.procurement) -> Bestellkandidat(540725,de.metas.procurement) -> Geschäftspartner
-- Column: PMM_PurchaseCandidate.C_BPartner_ID
-- 2026-04-08T07:46:34.036Z
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2026-04-08 07:46:34.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=556653
;

-- Field: Beschaffungsplanung(540285,de.metas.procurement) -> Bestellkandidat(540725,de.metas.procurement) -> Produkt
-- Column: PMM_PurchaseCandidate.M_Product_ID
-- 2026-04-08T07:46:56.672Z
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2026-04-08 07:46:56.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=556654
;

