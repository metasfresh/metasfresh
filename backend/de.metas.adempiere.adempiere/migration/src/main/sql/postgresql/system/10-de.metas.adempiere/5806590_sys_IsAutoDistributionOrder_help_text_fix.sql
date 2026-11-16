-- Correct the Description/Help text of M_Warehouse.IsAutoDistributionOrder.
--
-- The original text (scripts 5804700 / 5804710) stated that the flag
-- "requires MRP_Exclude=Y" ("Setzt voraus, dass auch MRP_Exclude=Ja gesetzt ist").
-- That is no longer true: WarehouseBL.computeIsIgnoreInMaterialDispo returns true
-- (ignore in material disposition) as soon as IsAutoDistributionOrder='Y', BEFORE
-- MRP_Exclude is consulted — so an Auto-Distribution-Order warehouse is automatically
-- excluded from material disposition and setting MRP_Exclude=Y manually is not required.
--
-- This corrects AD_Element (584916) + its de_DE/de_CH/en_US AD_Element_Trl rows and
-- AD_Column (592623), then propagates the corrected text down to AD_Column_Trl,
-- AD_Field_Trl and AD_UI_Element_Trl (all consuming fields have AD_Name_ID IS NULL, so
-- they inherit from the column's element — base Warehouse window 139 and the dt204
-- override window are both covered by the single propagation call).

-- =============================================================================
-- 1. AD_Element (base = de_DE)
-- =============================================================================
UPDATE AD_Element
SET Description = 'Wenn Ja, betreibt dieses Lager den eigenständigen DD_Order-Abgleich für die Kommissionierung — anstelle der allgemeinen Materialdisposition. Setzt voraus, dass ein Verteilungsnetz hinterlegt ist.',
    Help        = 'Markiert dieses Lager als Auto-Verteilungsauftrag. Für solche Lager wird ein dedizierter, idempotenter Reconcile-Flow aktiviert, der pro M_ShipmentSchedule genau einen DD_Order erzeugt. Das Lager wird automatisch von der Materialdisposition ausgeschlossen (MRP_Exclude=Ja ist nicht erforderlich). Ein Verteilungsnetz (DD_NetworkDistribution_ID) muss hinterlegt sein.',
    Updated     = TO_TIMESTAMP('2026-06-05 12:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584916;

-- =============================================================================
-- 2. AD_Element_Trl — de_DE (base language)
-- =============================================================================
UPDATE AD_Element_Trl
SET Description = 'Wenn Ja, betreibt dieses Lager den eigenständigen DD_Order-Abgleich für die Kommissionierung — anstelle der allgemeinen Materialdisposition. Setzt voraus, dass ein Verteilungsnetz hinterlegt ist.',
    Help        = 'Markiert dieses Lager als Auto-Verteilungsauftrag. Für solche Lager wird ein dedizierter, idempotenter Reconcile-Flow aktiviert, der pro M_ShipmentSchedule genau einen DD_Order erzeugt. Das Lager wird automatisch von der Materialdisposition ausgeschlossen (MRP_Exclude=Ja ist nicht erforderlich). Ein Verteilungsnetz (DD_NetworkDistribution_ID) muss hinterlegt sein.',
    IsTranslated = 'N',
    Updated     = TO_TIMESTAMP('2026-06-05 12:00:01','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584916 AND AD_Language = 'de_DE';

-- =============================================================================
-- 3. AD_Element_Trl — de_CH (same as de_DE)
-- =============================================================================
UPDATE AD_Element_Trl
SET Description = 'Wenn Ja, betreibt dieses Lager den eigenständigen DD_Order-Abgleich für die Kommissionierung — anstelle der allgemeinen Materialdisposition. Setzt voraus, dass ein Verteilungsnetz hinterlegt ist.',
    Help        = 'Markiert dieses Lager als Auto-Verteilungsauftrag. Für solche Lager wird ein dedizierter, idempotenter Reconcile-Flow aktiviert, der pro M_ShipmentSchedule genau einen DD_Order erzeugt. Das Lager wird automatisch von der Materialdisposition ausgeschlossen (MRP_Exclude=Ja ist nicht erforderlich). Ein Verteilungsnetz (DD_NetworkDistribution_ID) muss hinterlegt sein.',
    IsTranslated = 'N',
    Updated     = TO_TIMESTAMP('2026-06-05 12:00:02','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584916 AND AD_Language = 'de_CH';

-- =============================================================================
-- 4. AD_Element_Trl — en_US (English translation)
-- =============================================================================
UPDATE AD_Element_Trl
SET Description = 'If Yes, this warehouse runs the dedicated DD_Order reconcile flow for picking — instead of the general material-disposition flow. Requires a distribution network to be set.',
    Help        = 'Marks this warehouse for automatic distribution-order (DD_Order) creation. For such warehouses a dedicated, idempotent reconcile flow is enabled that creates exactly one DD_Order per M_ShipmentSchedule. The warehouse is automatically excluded from material disposition (MRP_Exclude=Y is not required). A distribution network (DD_NetworkDistribution_ID) must be populated.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-06-05 12:00:03','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584916 AND AD_Language = 'en_US';

-- =============================================================================
-- 5. AD_Column (base = de_DE) — keep the column's own text in sync with the element
-- =============================================================================
UPDATE AD_Column
SET Description = 'Wenn Ja, betreibt dieses Lager den eigenständigen DD_Order-Abgleich für die Kommissionierung — anstelle der allgemeinen Materialdisposition. Setzt voraus, dass ein Verteilungsnetz hinterlegt ist.',
    Help        = 'Markiert dieses Lager als Auto-Verteilungsauftrag. Für solche Lager wird ein dedizierter, idempotenter Reconcile-Flow aktiviert, der pro M_ShipmentSchedule genau einen DD_Order erzeugt. Das Lager wird automatisch von der Materialdisposition ausgeschlossen (MRP_Exclude=Ja ist nicht erforderlich). Ein Verteilungsnetz (DD_NetworkDistribution_ID) muss hinterlegt sein.',
    Updated     = TO_TIMESTAMP('2026-06-05 12:00:04','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Column_ID = 592623;

-- =============================================================================
-- 6. Propagate corrected element text → AD_Column_Trl / AD_Field_Trl / AD_UI_Element_Trl
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584916);
