-- Migration: me03#29231 — add "de.metas.edi".epcis_has_events(m_inout_id)
-- https://github.com/metasfresh/me03/issues/29231
--
-- Boolean helper for the scripted-adapter outbound-selection WHERE-clause. Returns TRUE only when
-- the EPCIS export for the shipment would contain at least one pallet (SSCC) — i.e. it is worth
-- sending. Returns FALSE for the non-owner sibling of a shared physical pallet (get_epcis_events_json_fn
-- returns '{}') and for shipments with no LU pallets, so metasfresh never transmits an empty EPCIS
-- document to the clearing center. Delegates to get_epcis_events_json_fn (single source of truth).

CREATE OR REPLACE FUNCTION "de.metas.edi".epcis_has_events(p_m_inout_id numeric)
    RETURNS boolean
    LANGUAGE sql
    STABLE
AS
$$
SELECT COALESCE(
               jsonb_typeof(j.payload -> 'pallets') = 'array'
                   AND jsonb_array_length(j.payload -> 'pallets') > 0,
               false
       )
FROM (SELECT "de.metas.edi".get_epcis_events_json_fn(p_m_inout_id) AS payload) j;
$$;
