CREATE UNIQUE INDEX c_payment_bankstatementline_ref ON c_payment (c_bankstatementline_ref_id)
    WHERE c_bankstatementline_ref_id IS NOT NULL;

CREATE UNIQUE INDEX c_payment_bankstatementline ON c_payment (c_bankstatementline_id)
    WHERE c_bankstatementline_id IS NOT NULL AND c_bankstatementline_ref_id IS NULL;

