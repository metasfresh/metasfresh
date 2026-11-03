-- epcis_has_events(p_m_inout_id)
--
-- TRUE when the EPCIS export for this shipment would contain at least one pallet (SSCC) — i.e. it is
-- worth sending to the clearing center. FALSE for:
--   * sibling shipments of a shared physical pallet (get_epcis_events_json_fn returns '{}'),
--   * shipments with no LU pallets at all           (pallets => []).
--
-- Intended for the scripted-adapter outbound-selection WHERE-clause, so metasfresh never emits an
-- empty EPCIS document for the non-owner sibling of a shared pallet:
--     ... AND "de.metas.edi".epcis_has_events(m_inout_id)
--
-- Delegates to get_epcis_events_json_fn (single source of truth for the per-physical-SSCC keying),
-- so it can never drift from the actual export logic. me03 #29231.

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
