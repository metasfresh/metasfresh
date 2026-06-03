-- dlm.deactivate_destroyed_hu_attributes(p_max_hus)
-- Soft-archive (IsActive='N') M_HU_Attribute rows of DESTROYED HUs so the picking
-- source-HU search (HUStorageQuery) stays fast and the partial index m_hu_attribute_hu_attr_valnum
-- (WHERE IsActive='Y') stays small / cache-resident.
--
-- Self-provisioning (idempotent): creates the dlm schema, the reversibility log table, and its
-- deactivated_at timestamp column if missing.
--
-- Bounded per run by p_max_hus -> one small transaction, suitable for a weekly AD_Scheduler. The
-- one-time large backlog is handled separately by a manual, per-batch-commit operational runbook.
--
-- SCOPE = HUStatus='D' (Destroyed) ONLY. NOT Active('A')/Shipped('E')/Picked('S')/Planning('P')/
-- Issued('I'): 'A' would become unpickable; 'E' can be REACTIVATED by shipment reversal and would
-- come back attribute-less. Destroyed HUs have no un-destroy path -> safe.
--
-- References public.* explicitly: under DLM the dlm schema shadows public tables with filtered views;
-- this maintenance job must see the REAL tables.

CREATE SCHEMA IF NOT EXISTS dlm;

CREATE OR REPLACE FUNCTION dlm.deactivate_destroyed_hu_attributes(p_max_hus integer DEFAULT 100000)
    RETURNS integer
    LANGUAGE plpgsql
AS $func$
DECLARE
    v_deactivated integer;
BEGIN
    -- self-provisioning (idempotent)
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
