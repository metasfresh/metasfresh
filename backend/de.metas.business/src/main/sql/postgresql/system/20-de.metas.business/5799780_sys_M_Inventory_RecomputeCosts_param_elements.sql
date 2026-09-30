-- 2026-04-27 Make the M_Inventory_RecomputeCosts parameter descriptions stick.
--
-- Migration 5799740 set Description directly on AD_Process_Para 542650 + 1000001 (and their
-- AD_Process_Para_Trl) to provide process-specific guidance. But those rows are linked to the
-- generic shared AD_Elements 181 ("Buchführungs-Schema") and 241 ("Kostenrechnungsmethode"),
-- and `update_Process_Para_Translation_From_AD_Element` resyncs Name / Description / Help
-- from the linked element on every after-migration cycle (the `after_migration_sync_translations`
-- step) — so 5799740's overrides got overwritten with element 181/241's generic text.
--
-- Fix: create dedicated AD_Elements for each parameter, link the params to those new elements,
-- and re-set the Description / Help on AD_Process_Para and AD_Process_Para_Trl. The element
-- timestamps are set 1 second later than the parameter timestamps so subsequent propagation
-- cycles see `p.updated <> e_trl.updated` and re-sync from the (correct) new element instead
-- of becoming a no-op.

-- AD_Element 1 — process-specific override for the C_AcctSchema_ID parameter
INSERT INTO AD_Element (
    AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    EntityType, ColumnName, Name, PrintName, Description, Help
) VALUES (
    584788 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-04-27 14:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
    TO_TIMESTAMP('2026-04-27 14:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
    'D',
    'M_Inventory_RecomputeCosts_C_AcctSchema_ID',
    'Buchhaltungs-Schema',
    'Buchhaltungs-Schema',
    'Das Buchhaltungs-Schema, das neu berechnet werden soll. Wählen Sie das Schema, dessen Kostenrechnungsmethode zu Ihrer Kostenanpassung passt (typischerweise „Bestellpreis Durchschnitt").',
    NULL
);

INSERT INTO AD_Element_Trl (
    AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    IsTranslated, Name, PrintName, Description, Help
) VALUES (
    584788, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-04-27 14:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
    TO_TIMESTAMP('2026-04-27 14:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
    'Y',
    'Accounting Schema',
    'Accounting Schema',
    'The accounting schema to recompute. Pick the schema whose costing method matches your cost adjustment (typically "AveragePO").',
    NULL
);

-- AD_Element 2 — process-specific override for the CostingMethod parameter
INSERT INTO AD_Element (
    AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    EntityType, ColumnName, Name, PrintName, Description, Help
) VALUES (
    584789 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-04-27 14:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
    TO_TIMESTAMP('2026-04-27 14:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
    'D',
    'M_Inventory_RecomputeCosts_CostingMethod',
    'Kostenrechnungsmethode',
    'Kostenrechnungsmethode',
    'Optional. Wenn gesetzt, werden nur die Kostenarten dieser Methode neu berechnet. Leer lassen, um alle aktiven Material-Kostenarten neu zu berechnen (Standardfall).',
    NULL
);

INSERT INTO AD_Element_Trl (
    AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    IsTranslated, Name, PrintName, Description, Help
) VALUES (
    584789, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-04-27 14:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
    TO_TIMESTAMP('2026-04-27 14:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
    'Y',
    'Costing Method',
    'Costing Method',
    'Optional. If set, only cost elements of this costing method are recomputed. Leave empty to recompute all active material cost elements (the usual case).',
    NULL
);

-- Re-link the two AD_Process_Para rows to the new elements AND set Description / Name directly
-- so the WebUI shows the correct text immediately after the migration. The Updated timestamp
-- is set 1 second EARLIER than the new element so any subsequent propagation cycle sees
-- `p.updated <> e_trl.updated` and re-syncs the same correct text from the linked element
-- (rather than skipping as a no-op and leaving stale data behind).
UPDATE AD_Process_Para
SET    AD_Element_ID = 584788,
       Name          = 'Buchhaltungs-Schema',
       Description   = 'Das Buchhaltungs-Schema, das neu berechnet werden soll. Wählen Sie das Schema, dessen Kostenrechnungsmethode zu Ihrer Kostenanpassung passt (typischerweise „Bestellpreis Durchschnitt").',
       Help          = NULL,
       Updated       = TO_TIMESTAMP('2026-04-27 14:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy     = 0
WHERE  AD_Process_Para_ID = 542650;     -- C_AcctSchema_ID

UPDATE AD_Process_Para_Trl
SET    Name         = 'Accounting Schema',
       Description  = 'The accounting schema to recompute. Pick the schema whose costing method matches your cost adjustment (typically "AveragePO").',
       Help         = NULL,
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-27 14:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 0
WHERE  AD_Process_Para_ID = 542650
  AND  AD_Language        = 'en_US';

UPDATE AD_Process_Para
SET    AD_Element_ID = 584789,
       Name          = 'Kostenrechnungsmethode',
       Description   = 'Optional. Wenn gesetzt, werden nur die Kostenarten dieser Methode neu berechnet. Leer lassen, um alle aktiven Material-Kostenarten neu zu berechnen (Standardfall).',
       Help          = NULL,
       Updated       = TO_TIMESTAMP('2026-04-27 14:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy     = 0
WHERE  AD_Process_Para_ID = 1000001;    -- CostingMethod

UPDATE AD_Process_Para_Trl
SET    Name         = 'Costing Method',
       Description  = 'Optional. If set, only cost elements of this costing method are recomputed. Leave empty to recompute all active material cost elements (the usual case).',
       Help         = NULL,
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-04-27 14:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 0
WHERE  AD_Process_Para_ID = 1000001
  AND  AD_Language        = 'en_US';
