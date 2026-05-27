-- Add IsAutoDistributionOrder to M_Warehouse
-- Script 1 of 2: AD_Element + AD_Element_Trl (de_DE, de_CH, en_US)

-- =============================================================================
-- 1. AD_Element
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help)
VALUES (584916 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-26 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        'IsAutoDistributionOrder',
        'Auto-Verteilungsauftrag',
        'Auto-Verteilungsauftrag',
        'Wenn Ja, betreibt dieses Lager den eigenständigen DD_Order-Abgleich für die Kommissionierung — anstelle der allgemeinen Materialdisposition. Setzt voraus, dass auch MRP_Exclude=Ja gesetzt ist und ein Verteilungsnetz hinterlegt ist.',
        'Markiert dieses Lager als Auto-Verteilungsauftrag. Für solche Lager wird ein dedizierter, idempotenter Reconcile-Flow aktiviert, der pro M_ShipmentSchedule genau einen DD_Order erzeugt. Damit der Flow korrekt funktioniert, muss am Lager zusätzlich MRP_Exclude=Ja gesetzt sein und ein DD_NetworkDistribution_ID hinterlegt sein.');

-- Skeleton Trl rows
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 584916
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation
-- NOTE: AD_Element_Trl.Updated MUST be later than the downstream AD_Column_Trl / AD_Field_Trl /
-- AD_UI_Element_Trl skeleton inserts (those inherit Updated from their parent AD_Column / AD_Field /
-- AD_UI_Element insert timestamps in scripts 5804710 and beyond — up to 14:02:01). The propagation
-- function update_TRL_Tables_On_AD_Element_TRL_Update only copies AD_Element_Trl down when it is the
-- newer row; if AD_Element_Trl is older than the downstream Trl row, IsTranslated never flips to 'Y'
-- on the downstream side and the WebUI falls back to the base-language Name.
UPDATE AD_Element_Trl
SET Name        = 'Auto Distribution Order',
    PrintName   = 'Auto Distribution Order',
    Description = 'If Yes, this warehouse runs the dedicated DD_Order reconcile flow for picking — instead of the general material-disposition flow. Requires MRP_Exclude=Y and a distribution network to be set.',
    Help        = 'Marks this warehouse for automatic distribution-order (DD_Order) creation. For such warehouses a dedicated, idempotent reconcile flow is enabled that creates exactly one DD_Order per M_ShipmentSchedule. For the flow to function, MRP_Exclude=Y must also be set on the warehouse and DD_NetworkDistribution_ID must be populated.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-05-26 14:03:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584916 AND AD_Language = 'en_US';

-- German translation (de_DE — base language)
UPDATE AD_Element_Trl
SET Name        = 'Auto-Verteilungsauftrag',
    PrintName   = 'Auto-Verteilungsauftrag',
    Description = 'Wenn Ja, betreibt dieses Lager den eigenständigen DD_Order-Abgleich für die Kommissionierung — anstelle der allgemeinen Materialdisposition. Setzt voraus, dass auch MRP_Exclude=Ja gesetzt ist und ein Verteilungsnetz hinterlegt ist.',
    Help        = 'Markiert dieses Lager als Auto-Verteilungsauftrag. Für solche Lager wird ein dedizierter, idempotenter Reconcile-Flow aktiviert, der pro M_ShipmentSchedule genau einen DD_Order erzeugt. Damit der Flow korrekt funktioniert, muss am Lager zusätzlich MRP_Exclude=Ja gesetzt sein und ein DD_NetworkDistribution_ID hinterlegt sein.',
    IsTranslated = 'N',
    Updated     = TO_TIMESTAMP('2026-05-26 14:03:01','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584916 AND AD_Language = 'de_DE';

-- de_CH = same as de_DE
UPDATE AD_Element_Trl
SET Name        = 'Auto-Verteilungsauftrag',
    PrintName   = 'Auto-Verteilungsauftrag',
    Description = 'Wenn Ja, betreibt dieses Lager den eigenständigen DD_Order-Abgleich für die Kommissionierung — anstelle der allgemeinen Materialdisposition. Setzt voraus, dass auch MRP_Exclude=Ja gesetzt ist und ein Verteilungsnetz hinterlegt ist.',
    Help        = 'Markiert dieses Lager als Auto-Verteilungsauftrag. Für solche Lager wird ein dedizierter, idempotenter Reconcile-Flow aktiviert, der pro M_ShipmentSchedule genau einen DD_Order erzeugt. Damit der Flow korrekt funktioniert, muss am Lager zusätzlich MRP_Exclude=Ja gesetzt sein und ein DD_NetworkDistribution_ID hinterlegt sein.',
    IsTranslated = 'N',
    Updated     = TO_TIMESTAMP('2026-05-26 14:03:02','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584916 AND AD_Language = 'de_CH';
