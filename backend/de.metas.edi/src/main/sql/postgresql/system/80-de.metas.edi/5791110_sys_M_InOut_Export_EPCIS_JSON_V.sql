-- PostgREST view for EPCIS JSON export
-- See ddl/views/epcis_json/M_InOut_Export_EPCIS_JSON_V.sql for the canonical DDL

DROP VIEW IF EXISTS M_InOut_Export_EPCIS_JSON_V;
CREATE OR REPLACE VIEW M_InOut_Export_EPCIS_JSON_V AS
SELECT io.m_inout_id,
       "de.metas.edi".get_epcis_events_json_fn(io.m_inout_id) AS embedded_json
FROM m_inout io
WHERE io.isactive = 'Y'
  AND io.docstatus IN ('CO', 'CL');
