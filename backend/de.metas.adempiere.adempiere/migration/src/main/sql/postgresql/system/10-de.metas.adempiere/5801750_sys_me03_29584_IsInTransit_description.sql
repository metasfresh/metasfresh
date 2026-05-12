-- me03#29584: Supplement IsInTransit description/help (clarify distinction from new IsDropShipWarehouse)

-- German (base language on AD_Element)
UPDATE AD_Element
SET Description = 'Wenn Ja, ist dieses Lager ein Transit-Lager (für Inventar zwischen zwei physischen Lagern). Nicht zu verwechseln mit "Streckengeschäft-Lager" (Dropship): bei IsInTransit handelt es sich um eigene Ware unterwegs, bei Streckengeschäft liefert der Lieferant direkt an den Endkunden.',
    Help = 'Transit-Lager modellieren Inventar, das sich zwischen zwei eigenen Lagern bewegt (z.B. LKW-Ladung, Werksverkehr) und buchhalterisch separat geführt werden muss. Ist dies nicht der Fall — sondern eine Direktbelieferung vom Lieferanten an den Endkunden — verwenden Sie stattdessen "Streckengeschäft-Lager" (IsDropShipWarehouse).',
    Updated = TO_TIMESTAMP('2026-05-11 14:03:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 2397;

-- de_DE
UPDATE AD_Element_Trl
SET Description = 'Wenn Ja, ist dieses Lager ein Transit-Lager (für Inventar zwischen zwei physischen Lagern). Nicht zu verwechseln mit "Streckengeschäft-Lager" (Dropship): bei IsInTransit handelt es sich um eigene Ware unterwegs, bei Streckengeschäft liefert der Lieferant direkt an den Endkunden.',
    Help = 'Transit-Lager modellieren Inventar, das sich zwischen zwei eigenen Lagern bewegt (z.B. LKW-Ladung, Werksverkehr) und buchhalterisch separat geführt werden muss. Ist dies nicht der Fall — sondern eine Direktbelieferung vom Lieferanten an den Endkunden — verwenden Sie stattdessen "Streckengeschäft-Lager" (IsDropShipWarehouse).',
    IsTranslated = 'N',
    Updated = TO_TIMESTAMP('2026-05-11 14:03:01','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 2397 AND AD_Language = 'de_DE';

-- de_CH
UPDATE AD_Element_Trl
SET Description = 'Wenn Ja, ist dieses Lager ein Transit-Lager (für Inventar zwischen zwei physischen Lagern). Nicht zu verwechseln mit "Streckengeschäft-Lager" (Dropship): bei IsInTransit handelt es sich um eigene Ware unterwegs, bei Streckengeschäft liefert der Lieferant direkt an den Endkunden.',
    Help = 'Transit-Lager modellieren Inventar, das sich zwischen zwei eigenen Lagern bewegt (z.B. LKW-Ladung, Werksverkehr) und buchhalterisch separat geführt werden muss. Ist dies nicht der Fall — sondern eine Direktbelieferung vom Lieferanten an den Endkunden — verwenden Sie stattdessen "Streckengeschäft-Lager" (IsDropShipWarehouse).',
    IsTranslated = 'N',
    Updated = TO_TIMESTAMP('2026-05-11 14:03:02','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 2397 AND AD_Language = 'de_CH';

-- en_US
UPDATE AD_Element_Trl
SET Description = 'If Yes, this is a transit warehouse (for inventory between two physical warehouses). Distinct from "Dropship Warehouse" (IsDropShipWarehouse): an in-transit warehouse holds own goods moving between sites, whereas a dropship warehouse routes goods directly from supplier to end customer.',
    Help = 'Transit warehouses model inventory that moves between two of your own physical warehouses (e.g., truck load, inter-site transfer) and must be accounted for separately. If instead you need direct supplier-to-customer delivery, use "Dropship Warehouse" (IsDropShipWarehouse) instead.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-11 14:03:03','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Element_ID = 2397 AND AD_Language = 'en_US';

-- Propagate to all dependent records (AD_Column, AD_Field, etc.)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2397);
