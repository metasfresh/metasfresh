DROP INDEX IF EXISTS m_hu_assignment_document_idx;
CREATE INDEX m_hu_assignment_document_idx
    ON public.m_hu_assignment USING btree
    (record_id, ad_table_id)
    TABLESPACE pg_default;
COMMENT ON INDEX m_hu_assignment_document_idx
IS 'have record_id first, because it''s much, much more distinctive than ad_table_id';

