
CREATE INDEX IF NOT EXISTS c_flatrate_dataentry_c_flatrate_term_id
    ON public.c_flatrate_dataentry USING btree
    (c_flatrate_term_id ASC NULLS LAST)
    TABLESPACE pg_default;
