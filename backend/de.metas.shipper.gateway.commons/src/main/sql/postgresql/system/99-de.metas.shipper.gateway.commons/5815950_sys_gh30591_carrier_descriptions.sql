-- gh30591 nShift Lieferweg AC8: add Description + Help for carrier AD_Elements (IsSelfPacked, IsApiCarrierAdvise,
-- IsIncludeCarrierAdviseManual, Carrier_Product_ID, Carrier_Goods_Type_ID, Carrier_Service_ID) and for the
-- M_ShipmentSchedule_Advise_Manual process header.
-- Names are already set; this migration only fills the previously empty Description and Help fields.

-- ============================================================
-- AD_Element 584203: IsSelfPacked (Eigenverpackung)
-- ============================================================

-- de_DE
UPDATE AD_Element_Trl
SET Description = 'Kennzeichnet, dass der Kunde die Ware selbst verpackt und keine Verpackung durch den Spediteur erfolgt.',
    Help        = 'Kennzeichnet, dass der Kunde die Ware selbst verpackt und keine Verpackung durch den Spediteur erfolgt.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584203 AND AD_Language = 'de_DE';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584203, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET Description = 'Kennzeichnet, dass der Kunde die Ware selbst verpackt und keine Verpackung durch den Spediteur erfolgt.',
    Help        = 'Kennzeichnet, dass der Kunde die Ware selbst verpackt und keine Verpackung durch den Spediteur erfolgt.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584203 AND AD_Language = 'de_CH';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584203, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET Description = 'Indicates that the customer packs the goods themselves; no packaging is done by the carrier.',
    Help        = 'Indicates that the customer packs the goods themselves; no packaging is done by the carrier.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584203 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584203, 'en_US');

-- Sync base AD_Element from de_DE Trl (base language)
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584203, 'de_DE');


-- ============================================================
-- AD_Element 584972: IsApiCarrierAdvise (API Lieferweg-Abfrage)
-- ============================================================

-- de_DE
UPDATE AD_Element_Trl
SET Description = 'Steuert, ob die Lieferweg-Abfrage beim Spediteur automatisch über die API erfolgt.',
    Help        = 'Steuert, ob die Lieferweg-Abfrage beim Spediteur automatisch über die API erfolgt.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:11', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584972 AND AD_Language = 'de_DE';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584972, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET Description = 'Steuert, ob die Lieferweg-Abfrage beim Spediteur automatisch über die API erfolgt.',
    Help        = 'Steuert, ob die Lieferweg-Abfrage beim Spediteur automatisch über die API erfolgt.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584972 AND AD_Language = 'de_CH';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584972, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET Description = 'Controls whether the carrier advise is requested automatically from the carrier via the API.',
    Help        = 'Controls whether the carrier advise is requested automatically from the carrier via the API.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:13', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584972 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584972, 'en_US');

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584972, 'de_DE');


-- ============================================================
-- AD_Process 585524 header: Lieferweg-Abfrage Manuell
-- AD_Process has no AD_Element_ID; Description/Help are set directly.
-- ============================================================

UPDATE AD_Process
SET Description = 'Stößt die Lieferweg-Abfrage beim Spediteur manuell für die ausgewählten Versanddispositionen an.',
    Help        = 'Stößt die Lieferweg-Abfrage beim Spediteur manuell für die ausgewählten Versanddispositionen an.',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:21', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Process_ID = 585524;

-- de_DE
UPDATE AD_Process_Trl
SET Description  = 'Stößt die Lieferweg-Abfrage beim Spediteur manuell für die ausgewählten Versanddispositionen an.',
    Help         = 'Stößt die Lieferweg-Abfrage beim Spediteur manuell für die ausgewählten Versanddispositionen an.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-23 10:00:22', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Process_ID = 585524 AND AD_Language = 'de_DE';

-- de_CH
UPDATE AD_Process_Trl
SET Description  = 'Stößt die Lieferweg-Abfrage beim Spediteur manuell für die ausgewählten Versanddispositionen an.',
    Help         = 'Stößt die Lieferweg-Abfrage beim Spediteur manuell für die ausgewählten Versanddispositionen an.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-23 10:00:23', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Process_ID = 585524 AND AD_Language = 'de_CH';

-- en_US
UPDATE AD_Process_Trl
SET Description  = 'Manually triggers the carrier advise at the carrier for the selected shipment schedules.',
    Help         = 'Manually triggers the carrier advise at the carrier for the selected shipment schedules.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-07-23 10:00:24', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Process_ID = 585524 AND AD_Language = 'en_US';


-- ============================================================
-- AD_Element 584199: IsIncludeCarrierAdviseManual
-- Para 543021 AD_Element_ID = 584199
-- ============================================================

-- de_DE
UPDATE AD_Element_Trl
SET Description = 'Bezieht auch Versanddispositionen mit Lieferweg-Abfrage-Status »Manuell« in die Verarbeitung ein.',
    Help        = 'Bezieht auch Versanddispositionen mit Lieferweg-Abfrage-Status »Manuell« in die Verarbeitung ein.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:31', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584199 AND AD_Language = 'de_DE';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584199, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET Description = 'Bezieht auch Versanddispositionen mit Lieferweg-Abfrage-Status »Manuell« in die Verarbeitung ein.',
    Help        = 'Bezieht auch Versanddispositionen mit Lieferweg-Abfrage-Status »Manuell« in die Verarbeitung ein.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:32', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584199 AND AD_Language = 'de_CH';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584199, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET Description = 'Also includes shipment schedules with carrier-advise status ''Manual'' in the processing.',
    Help        = 'Also includes shipment schedules with carrier-advise status ''Manual'' in the processing.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:33', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584199 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584199, 'en_US');

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584199, 'de_DE');


-- ============================================================
-- AD_Element 584116: Carrier_Product_ID (Lieferweg-Produkt)
-- Para 543023 AD_Element_ID = 584116
-- ============================================================

-- de_DE
UPDATE AD_Element_Trl
SET Description = 'Das beim Spediteur gebuchte Lieferweg-Produkt für die Versandabwicklung.',
    Help        = 'Das beim Spediteur gebuchte Lieferweg-Produkt für die Versandabwicklung.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:41', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584116 AND AD_Language = 'de_DE';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584116, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET Description = 'Das beim Spediteur gebuchte Lieferweg-Produkt für die Versandabwicklung.',
    Help        = 'Das beim Spediteur gebuchte Lieferweg-Produkt für die Versandabwicklung.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:42', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584116 AND AD_Language = 'de_CH';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584116, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET Description = 'The carrier product booked at the carrier for shipment handling.',
    Help        = 'The carrier product booked at the carrier for shipment handling.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:43', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584116 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584116, 'en_US');

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584116, 'de_DE');


-- ============================================================
-- AD_Element 584112: Carrier_Goods_Type_ID (Materialzuordnung je Lieferweg)
-- Para 543024 AD_Element_ID = 584112
-- ============================================================

-- de_DE
UPDATE AD_Element_Trl
SET Description = 'Die dem Lieferweg-Produkt zugeordnete Materialart für die Versandabwicklung.',
    Help        = 'Die dem Lieferweg-Produkt zugeordnete Materialart für die Versandabwicklung.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:51', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584112 AND AD_Language = 'de_DE';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584112, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET Description = 'Die dem Lieferweg-Produkt zugeordnete Materialart für die Versandabwicklung.',
    Help        = 'Die dem Lieferweg-Produkt zugeordnete Materialart für die Versandabwicklung.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:52', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584112 AND AD_Language = 'de_CH';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584112, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET Description = 'The goods type assigned to the carrier product for shipment handling.',
    Help        = 'The goods type assigned to the carrier product for shipment handling.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:00:53', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584112 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584112, 'en_US');

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584112, 'de_DE');


-- ============================================================
-- AD_Element 584113: Carrier_Service_ID (Lieferweg-Service)
-- Shared by paras 543025 / 543027 / 543028 — same text for all three
-- ============================================================

-- de_DE
UPDATE AD_Element_Trl
SET Description = 'Ein zusätzlicher Lieferweg-Service (z. B. eine Zustelloption) für das gewählte Lieferweg-Produkt.',
    Help        = 'Ein zusätzlicher Lieferweg-Service (z. B. eine Zustelloption) für das gewählte Lieferweg-Produkt.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:01:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584113 AND AD_Language = 'de_DE';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584113, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET Description = 'Ein zusätzlicher Lieferweg-Service (z. B. eine Zustelloption) für das gewählte Lieferweg-Produkt.',
    Help        = 'Ein zusätzlicher Lieferweg-Service (z. B. eine Zustelloption) für das gewählte Lieferweg-Produkt.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:01:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584113 AND AD_Language = 'de_CH';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584113, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET Description = 'An additional carrier service (e.g. a delivery option) for the selected carrier product.',
    Help        = 'An additional carrier service (e.g. a delivery option) for the selected carrier product.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-07-23 10:01:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Element_ID = 584113 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584113, 'en_US');

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584113, 'de_DE');
