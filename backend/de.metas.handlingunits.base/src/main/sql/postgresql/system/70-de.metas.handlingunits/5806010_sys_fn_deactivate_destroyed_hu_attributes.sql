-- Source DDL: backend/de.metas.handlingunits.base/src/main/sql/postgresql/ddl/functions/deactivate_destroyed_hu_attributes.sql
-- Function that soft-archives (IsActive='N') M_HU_Attribute rows of Destroyed HUs.
-- Self-provisions the dlm schema + dlm.m_hu_attribute_deactivated log (incl. deactivated_at).
-- Wired to a weekly AD_Scheduler in 5806020.

CREATE SCHEMA IF NOT EXISTS dlm;

CREATE OR REPLACE FUNCTION dlm.deactivate_destroyed_hu_attributes(p_max_hus integer DEFAULT 100000)
    RETURNS integer
    LANGUAGE plpgsql
AS $func$
DECLARE
    v_deactivated integer;
BEGIN
    CREATE SCHEMA IF NOT EXISTS dlm;
    CREATE TABLE IF NOT EXISTS dlm.m_hu_attribute_deactivated (
        m_hu_attribute_id numeric(10,0) PRIMARY KEY,
        deactivated_at    timestamp without time zone NOT NULL DEFAULT now()
    );
    ALTER TABLE dlm.m_hu_attribute_deactivated
        ADD COLUMN IF NOT EXISTS deactivated_at timestamp without time zone NOT NULL DEFAULT now();

    WITH huids AS (
        SELECT h.m_hu_id
        FROM public.m_hu h
        WHERE h.hustatus = 'D'
          AND EXISTS (SELECT 1
                      FROM public.m_hu_attribute a
                      WHERE a.m_hu_id = h.m_hu_id
                        AND a.isactive = 'Y')
        LIMIT p_max_hus
    ),
    upd AS (
        UPDATE public.m_hu_attribute a
           SET isactive = 'N', updatedby = 99, updated = now()
        FROM huids
        WHERE a.m_hu_id = huids.m_hu_id
          AND a.isactive = 'Y'
        RETURNING a.m_hu_attribute_id
    ),
    trk AS (
        INSERT INTO dlm.m_hu_attribute_deactivated (m_hu_attribute_id, deactivated_at)
        SELECT m_hu_attribute_id, now() FROM upd
        ON CONFLICT DO NOTHING
    )
    SELECT count(*) INTO v_deactivated FROM upd;

    RETURN COALESCE(v_deactivated, 0);
END;
$func$;

-- Provision dlm schema + log table + deactivated_at column now (deactivates nothing: p_max_hus=0).
SELECT dlm.deactivate_destroyed_hu_attributes(0);
