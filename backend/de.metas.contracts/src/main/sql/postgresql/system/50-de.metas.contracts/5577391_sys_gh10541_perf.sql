
CREATE INDEX IF NOT EXISTS c_invoice_candidate_C_BPartner_SalesRep_ID_dateordered
    ON public.c_invoice_candidate
        (C_BPartner_SalesRep_ID, DateOrdered);
COMMENT ON INDEX c_invoice_candidate_C_BPartner_SalesRep_ID_dateordered is
    'Added to support finding commission relevant invoice candidates for particular sales reps and time intervals;
see https://github.com/metasfresh/metasfresh/issues/10541';
