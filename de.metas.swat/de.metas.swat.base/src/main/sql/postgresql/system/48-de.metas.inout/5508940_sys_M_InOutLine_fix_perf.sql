
CREATE INDEX IF NOT EXISTS m_inoutline_M_PackingMaterial_InOutLine_ID
    ON public.m_inoutline USING btree
    (m_packingmaterial_inoutline_id ASC NULLS LAST)
    TABLESPACE pg_default;
