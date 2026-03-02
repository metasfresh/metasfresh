-- AD_Process for EPCIS JSON export via PostgREST
-- Follows the same pattern as M_InOut_EDI_Export_JSON (AD_Process_ID=585473)

-- AD_Process
INSERT INTO AD_Process (AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        Value, Name,
                        Classname, JSONPath, PostgrestResponseFormat,
                        IsReport, IsFormatExcelFile, EntityType,
                        AccessLevel, Type, ShowHelp, IsBetaFunctionality)
VALUES (585590, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'M_InOut_EPCIS_Export_JSON', 'EPCIS als JSON exportieren (postgREST)',
        'de.metas.edi.process.export.json.M_InOut_EPCIS_Export_JSON',
        'm_inout_export_epcis_json_v?select=embedded_json&m_inout_id=eq.@M_InOut_ID/0@',
        'json',
        'Y', 'Y', 'de.metas.esb.edi',
        '3', 'Java', 'Y', 'N');

-- AD_Process_Para: M_InOut_ID
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, ColumnName, Name,
                             AD_Reference_ID, IsMandatory, IsRange, SeqNo,
                             EntityType, FieldLength, DefaultValue)
VALUES (543137, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        585590, 1027, 'M_InOut_ID', 'Shipment/Receipt',
        30, 'Y', 'N', 10,
        'de.metas.esb.edi', 0, '@M_InOut_ID/0@');

-- AD_Table_Process: link to M_InOut table (not shown as action — called programmatically by the ScriptedAdapter interceptor)
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                              AD_Process_ID, AD_Table_ID, EntityType)
VALUES (541628, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        585590, 319, 'de.metas.esb.edi');
