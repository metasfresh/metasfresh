-- Raise S_PostgREST_Config.read_timeout to 180000 ms (3 min) where it is
-- currently lower. Rationale: aggregation-heavy export views (e.g. EPCIS /
-- shipment JSON views that traverse the HU tree) can take noticeably longer
-- than the default 30-60s read timeout on cold calls or under DB load, and
-- a RestTemplate SocketTimeoutException aborts the entire outbound process
-- (including scripted outgoing adapters driven by PostgREST processes).
--
-- Rows with read_timeout >= 180000 (including 0 = unlimited) are left
-- untouched. Connection_timeout is not changed — the TCP connect phase is
-- fast regardless of view complexity.

SELECT backup_table('s_postgrest_config', '_read_timeout_bump');

UPDATE s_postgrest_config
   SET read_timeout = 180000,
       Updated      = TO_TIMESTAMP('2026-04-17 17:45','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 99
 WHERE read_timeout > 0
   AND read_timeout < 180000;
