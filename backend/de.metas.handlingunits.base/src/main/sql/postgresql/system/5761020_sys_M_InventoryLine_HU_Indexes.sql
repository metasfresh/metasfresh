

CREATE INDEX IF NOT EXISTS m_inventoryline_m_hu_id
    ON public.m_inventoryline (m_hu_id)
;

CREATE INDEX IF NOT EXISTS m_inventoryline_hu_m_hu_id
    ON public.m_inventoryline_hu (m_hu_id)
;
