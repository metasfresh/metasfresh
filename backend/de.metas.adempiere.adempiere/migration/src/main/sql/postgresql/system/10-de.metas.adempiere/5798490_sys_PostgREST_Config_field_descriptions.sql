-- AD_Window 540933 (PostgREST Konfiguration) — fill in descriptions and help texts
-- for the connection fields. Specifically clarify that the timeout values are
-- expressed in milliseconds.
--
-- Touched elements:
--   Base_url            (AD_Element_ID = 577782)
--   Connection_timeout  (AD_Element_ID = 577783)
--   Read_timeout        (AD_Element_ID = 577784)
--
-- All three previously had Description = NULL and Help = NULL (see
-- 5562850_sys_gh6957_add_S_PostgREST_Config_table.sql).

-- =============================================================================
-- Base_url (AD_Element 577782)
-- =============================================================================

-- de_DE
UPDATE AD_Element_Trl
   SET Description  = 'Basis-URL des PostgREST-Dienstes, z. B. http://postgrest:3000',
       Help         = 'Wird von AD_Processes vom Typ PostgREST verwendet, um REST-Aufrufe abzusetzen. Der in AD_Process.JsonPath hinterlegte Pfad wird an diese URL angehängt.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-17 17:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
 WHERE AD_Element_ID = 577782 AND AD_Language = 'de_DE';

-- sync base-language text into AD_Element itself
SELECT update_ad_element_on_ad_element_trl_update(577782, 'de_DE');

-- de_CH (identical German text)
UPDATE AD_Element_Trl
   SET Description  = 'Basis-URL des PostgREST-Dienstes, z. B. http://postgrest:3000',
       Help         = 'Wird von AD_Processes vom Typ PostgREST verwendet, um REST-Aufrufe abzusetzen. Der in AD_Process.JsonPath hinterlegte Pfad wird an diese URL angehängt.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-17 17:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
 WHERE AD_Element_ID = 577782 AND AD_Language = 'de_CH';

-- en_US
UPDATE AD_Element_Trl
   SET Description  = 'Base URL of the PostgREST service, e.g. http://postgrest:3000',
       Help         = 'Used by AD_Processes of type PostgREST to make REST calls. The path stored in AD_Process.JsonPath is appended to this URL.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-17 17:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
 WHERE AD_Element_ID = 577782 AND AD_Language = 'en_US';

-- propagate Name/Description/Help to AD_Column_Trl, AD_Field_Trl, AD_Process_Para_Trl
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577782);


-- =============================================================================
-- Connection_timeout (AD_Element 577783)
-- =============================================================================

-- de_DE
UPDATE AD_Element_Trl
   SET Description  = 'Maximale Wartezeit beim Verbindungsaufbau zum PostgREST-Dienst in Millisekunden (ms). 0 = unbegrenzt.',
       Help         = 'Bezieht sich auf den TCP-Verbindungsaufbau, nicht auf die Antwortzeit. Für das Warten auf die Antwort siehe das Feld "Read timeout".',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-17 17:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
 WHERE AD_Element_ID = 577783 AND AD_Language = 'de_DE';

SELECT update_ad_element_on_ad_element_trl_update(577783, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
   SET Description  = 'Maximale Wartezeit beim Verbindungsaufbau zum PostgREST-Dienst in Millisekunden (ms). 0 = unbegrenzt.',
       Help         = 'Bezieht sich auf den TCP-Verbindungsaufbau, nicht auf die Antwortzeit. Für das Warten auf die Antwort siehe das Feld "Read timeout".',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-17 17:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
 WHERE AD_Element_ID = 577783 AND AD_Language = 'de_CH';

-- en_US
UPDATE AD_Element_Trl
   SET Description  = 'Maximum wait time in milliseconds (ms) when establishing the connection to the PostgREST service. 0 = unlimited.',
       Help         = 'Applies to the TCP connect phase only, not to the response time. For response waiting see the "Read timeout" field.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-17 17:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
 WHERE AD_Element_ID = 577783 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577783);


-- =============================================================================
-- Read_timeout (AD_Element 577784)
-- =============================================================================

-- de_DE
UPDATE AD_Element_Trl
   SET Description  = 'Maximale Wartezeit auf eine Antwort vom PostgREST-Dienst in Millisekunden (ms). 0 = unbegrenzt.',
       Help         = 'Gilt pro HTTP-GET-Aufruf, nach erfolgreichem Verbindungsaufbau. Langsame, aggregationsintensive Export-Views (z. B. EPCIS-Exporte über scripted Outgoing-Adapter) benötigen oft deutlich höhere Werte.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-17 17:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
 WHERE AD_Element_ID = 577784 AND AD_Language = 'de_DE';

SELECT update_ad_element_on_ad_element_trl_update(577784, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
   SET Description  = 'Maximale Wartezeit auf eine Antwort vom PostgREST-Dienst in Millisekunden (ms). 0 = unbegrenzt.',
       Help         = 'Gilt pro HTTP-GET-Aufruf, nach erfolgreichem Verbindungsaufbau. Langsame, aggregationsintensive Export-Views (z. B. EPCIS-Exporte über scripted Outgoing-Adapter) benötigen oft deutlich höhere Werte.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-17 17:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
 WHERE AD_Element_ID = 577784 AND AD_Language = 'de_CH';

-- en_US
UPDATE AD_Element_Trl
   SET Description  = 'Maximum wait time in milliseconds (ms) for a response from the PostgREST service. 0 = unlimited.',
       Help         = 'Applies per HTTP GET call after the connection has been established. Slow, aggregation-heavy export views (e.g. EPCIS exports via the scripted outgoing adapter) often require considerably higher values.',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-17 17:00','YYYY-MM-DD HH24:MI'),
       UpdatedBy    = 0
 WHERE AD_Element_ID = 577784 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577784);
