-- gh#28206: Add optional M_Warehouse_ID parameter to customer return processes
-- This allows users to override the default quality return warehouse when creating customer returns.
-- If not set, the process falls back to the first quality return warehouse as before.

-- Process: WEBUI_M_HU_ReturnFromCustomer (AD_Process_ID=540797)
-- Parameter: M_Warehouse_ID (optional)
INSERT INTO AD_Process_Para (
    AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Process_Para_ID, AD_Process_ID,
    AD_Element_ID, AD_Reference_ID,
    ColumnName, Name, Description,
    FieldLength, SeqNo,
    IsMandatory, IsRange, IsCentrallyMaintained, IsAutocomplete, IsEncrypted,
    EntityType
) VALUES (
    0, 0, 'Y', now(), 100, now(), 100,
    543138, 540797,
    459, 19,
    'M_Warehouse_ID', 'Lager', 'Optional: Ziellager fuer die Kundenruecklieferung. Wenn nicht gesetzt, wird das Qualitaetsruecklieferungslager verwendet.',
    10, 10,
    'N', 'N', 'Y', 'N', 'N',
    'de.metas.ui.web'
);

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543138
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID);

-- Process: M_InOut_GenerateCustomerReturn (AD_Process_ID=585564)
-- Parameter: M_Warehouse_ID (optional)
INSERT INTO AD_Process_Para (
    AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Process_Para_ID, AD_Process_ID,
    AD_Element_ID, AD_Reference_ID,
    ColumnName, Name, Description,
    FieldLength, SeqNo,
    IsMandatory, IsRange, IsCentrallyMaintained, IsAutocomplete, IsEncrypted,
    EntityType
) VALUES (
    0, 0, 'Y', now(), 100, now(), 100,
    543139, 585564,
    459, 19,
    'M_Warehouse_ID', 'Lager', 'Optional: Ziellager fuer die Kundenruecklieferung. Wenn nicht gesetzt, wird das Qualitaetsruecklieferungslager verwendet.',
    10, 10,
    'N', 'N', 'Y', 'N', 'N',
    'D'
);

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543139
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID);
