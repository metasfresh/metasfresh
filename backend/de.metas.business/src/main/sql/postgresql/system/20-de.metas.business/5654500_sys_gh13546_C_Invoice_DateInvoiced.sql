CREATE INDEX IF NOT EXISTS c_invoice_dateinvoiced
    ON public.c_invoice USING btree
        (dateinvoiced ASC NULLS LAST)
    TABLESPACE pg_default
;

COMMENT ON INDEX public.c_invoice_dateinvoiced
    IS 'Needed for some custom accounting export DB functions'
;