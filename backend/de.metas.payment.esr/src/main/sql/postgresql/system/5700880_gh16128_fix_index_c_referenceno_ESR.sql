
drop index if exists "c_referenceno_ESR";

-- before, the index was on "c_referenceno ( ad_org_id, c_referenceno_type_id, referenceno)"
CREATE INDEX c_referenceno_ESR
    ON c_referenceno (referenceno, c_referenceno_type_id, ad_org_id)
;