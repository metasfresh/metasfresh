-- Feature compensation group contracts: en_US captions of the windows Kompensationsgruppen-Vertragseinstellungen (542194),
-- Vertragsbedingungen (540113), Kompensationsgruppe Schema (540415) and Auftrag Kompensationsgruppe (542196) to Title Case.
-- Casing only, wording unchanged; changed at the AD_Element (en_US Name, and PrintName where it equals Name) and propagated.
-- Elements shared with other windows change there too:
--   2414 MinQty: windows 318, 325;  2588 PriceEntered: windows 540072, 540095, 540375, 541952;
--   541449 IsClosingWithActualSum: windows 540359, 540366;  544200 RefundAmt: window 541825;
--   577218 / 579623 / 580267 (commission and license fee settings): windows 540742, 541218, 541342 and their menus.
-- Exception: PassOn_InvoiceProcessingServiceCompany_ID (elements 585538, 585539): en_US label 'Service Fee Of'
-- (mirrors the German 'Servicegebühr von'), and new Description/Help in de_DE, de_CH, en_US (fr_CH: untranslated German copy).

-- 2414 MinQty: Minimum quantity -> Minimum Quantity
UPDATE AD_Element_Trl SET Name='Minimum Quantity', PrintName='Minimum Quantity', Updated=TO_TIMESTAMP('2026-10-14 15:00:00.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=2414 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2414, 'en_US')
;

-- 2588 PriceEntered: Price imp. -> Price Imp.
UPDATE AD_Element_Trl SET Name='Price Imp.', Updated=TO_TIMESTAMP('2026-10-14 15:00:01.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=2588 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2588, 'en_US')
;

-- 541449 IsClosingWithActualSum: Cleat with actual Amount -> Cleat with Actual Amount
UPDATE AD_Element_Trl SET Name='Cleat with Actual Amount', Updated=TO_TIMESTAMP('2026-10-14 15:00:02.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=541449 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(541449, 'en_US')
;

-- 543964 OnFlatrateTermExtend: When extending contract -> When Extending Contract
UPDATE AD_Element_Trl SET Name='When Extending Contract', PrintName='When Extending Contract', Updated=TO_TIMESTAMP('2026-10-14 15:00:03.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=543964 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543964, 'en_US')
;

-- 544088 RefundInvoiceType: Refund per -> Refund Per
UPDATE AD_Element_Trl SET Name='Refund Per', Updated=TO_TIMESTAMP('2026-10-14 15:00:04.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=544088 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(544088, 'en_US')
;

-- 544200 RefundAmt: Refund amount -> Refund Amount
UPDATE AD_Element_Trl SET Name='Refund Amount', PrintName='Refund Amount', Updated=TO_TIMESTAMP('2026-10-14 15:00:05.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=544200 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(544200, 'en_US')
;

-- 544201 RefundBase: Refund based on -> Refund Based On
UPDATE AD_Element_Trl SET Name='Refund Based On', PrintName='Refund Based On', Updated=TO_TIMESTAMP('2026-10-14 15:00:06.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=544201 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(544201, 'en_US')
;

-- 544203 IsUseInProfitCalculation: In profit calculation -> In Profit Calculation
UPDATE AD_Element_Trl SET Name='In Profit Calculation', PrintName='In Profit Calculation', Updated=TO_TIMESTAMP('2026-10-14 15:00:07.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=544203 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(544203, 'en_US')
;

-- 577218 C_HierarchyCommissionSettings_ID: Hierarchy commission settings -> Hierarchy Commission Settings
UPDATE AD_Element_Trl SET Name='Hierarchy Commission Settings', PrintName='Hierarchy Commission Settings', Updated=TO_TIMESTAMP('2026-10-14 15:00:08.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=577218 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577218, 'en_US')
;

-- 579275 M_Product_Exclude_FlatrateConditions_ID: Products excl. from subscription -> Products Excl. from Subscription
UPDATE AD_Element_Trl SET Name='Products Excl. from Subscription', PrintName='Products Excl. from Subscription', Updated=TO_TIMESTAMP('2026-10-14 15:00:09.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=579275 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579275, 'en_US')
;

-- 579623 C_MediatedCommissionSettings_ID: Brokerage commission settings -> Brokerage Commission Settings
UPDATE AD_Element_Trl SET Name='Brokerage Commission Settings', PrintName='Brokerage Commission Settings', Updated=TO_TIMESTAMP('2026-10-14 15:00:10.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=579623 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579623, 'en_US')
;

-- 580267 C_LicenseFeeSettings_ID: License fee settings -> License Fee Settings
UPDATE AD_Element_Trl SET Name='License Fee Settings', PrintName='License Fee Settings', Updated=TO_TIMESTAMP('2026-10-14 15:00:11.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=580267 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(580267, 'en_US')
;

-- 585492 C_CompensationGroup_ContractSettings_ID: Compensation group contract settings -> Compensation Group Contract Settings
UPDATE AD_Element_Trl SET Name='Compensation Group Contract Settings', PrintName='Compensation Group Contract Settings', Updated=TO_TIMESTAMP('2026-10-14 15:00:12.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585492 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585492, 'en_US')
;

-- 585494 : Compensation group contract settings -> Compensation Group Contract Settings
UPDATE AD_Element_Trl SET Name='Compensation Group Contract Settings', PrintName='Compensation Group Contract Settings', Updated=TO_TIMESTAMP('2026-10-14 15:00:13.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585494 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585494, 'en_US')
;

-- 585495 : Document types -> Document Types
UPDATE AD_Element_Trl SET Name='Document Types', PrintName='Document Types', Updated=TO_TIMESTAMP('2026-10-14 15:00:14.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585495 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585495, 'en_US')
;

-- 585496 : Applies to product category -> Applies to Product Category
UPDATE AD_Element_Trl SET Name='Applies to Product Category', PrintName='Applies to Product Category', Updated=TO_TIMESTAMP('2026-10-14 15:00:15.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585496 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585496, 'en_US')
;

-- 585507 : Take over from the sales order -> Take Over from the Sales Order
UPDATE AD_Element_Trl SET Name='Take Over from the Sales Order', PrintName='Take Over from the Sales Order', Updated=TO_TIMESTAMP('2026-10-14 15:00:16.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585507 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585507, 'en_US')
;

-- 585508 : Taken-over customer discount products -> Taken-over Customer Discount Products
UPDATE AD_Element_Trl SET Name='Taken-over Customer Discount Products', PrintName='Taken-over Customer Discount Products', Updated=TO_TIMESTAMP('2026-10-14 15:00:17.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585508 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585508, 'en_US')
;

-- 585509 : Discount product (own line) -> Discount Product (Own Line)
UPDATE AD_Element_Trl SET Name='Discount Product (Own Line)', PrintName='Discount Product (Own Line)', Updated=TO_TIMESTAMP('2026-10-14 15:00:18.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585509 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585509, 'en_US')
;

-- 585510 : Customer discount product -> Customer Discount Product
UPDATE AD_Element_Trl SET Name='Customer Discount Product', PrintName='Customer Discount Product', Updated=TO_TIMESTAMP('2026-10-14 15:00:19.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585510 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585510, 'en_US')
;

-- 585512 Bonus_Product_ID: Bonus product -> Bonus Product
UPDATE AD_Element_Trl SET Name='Bonus Product', PrintName='Bonus Product', Updated=TO_TIMESTAMP('2026-10-14 15:00:20.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585512 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585512, 'en_US')
;

-- 585514 IsPackingOptionFiltered: Filter by packaging -> Filter by Packaging
UPDATE AD_Element_Trl SET Name='Filter by Packaging', PrintName='Filter by Packaging', Updated=TO_TIMESTAMP('2026-10-14 15:00:21.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585514 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585514, 'en_US')
;

-- 585515 C_Flatrate_RefundConfig_PackingOption_ID: Refund packaging option -> Refund Packaging Option
UPDATE AD_Element_Trl SET Name='Refund Packaging Option', PrintName='Refund Packaging Option', Updated=TO_TIMESTAMP('2026-10-14 15:00:22.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585515 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585515, 'en_US')
;

-- 585516 IsDeductedAtPayment: Deducted at payment -> Deducted at Payment
UPDATE AD_Element_Trl SET Name='Deducted at Payment', PrintName='Deducted at Payment', Updated=TO_TIMESTAMP('2026-10-14 15:00:23.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585516 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585516, 'en_US')
;

-- 585519 M_Product_Category_PackingMaterial_ID: Packing material category -> Packing Material Category
UPDATE AD_Element_Trl SET Name='Packing Material Category', PrintName='Packing Material Category', Updated=TO_TIMESTAMP('2026-10-14 15:00:24.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585519 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585519, 'en_US')
;

-- 585538 PassOn_InvoiceProcessingServiceCompany: en_US label and Description/Help in all languages
UPDATE AD_Element_Trl SET Name='Service Fee Of', PrintName='Service Fee Of', Description='Invoice Processing Service Company whose service fee this vendor passes on into drop-ship purchase orders. Empty = no Invoice Processing Service Company fee applies to this vendor''s purchase orders.', Help='If the customer of a drop-ship sales order pays a service fee to the Invoice Processing Service Company set here, the purchase order to this vendor gets a discount line of its own with that fee. Fees of other Invoice Processing Service Companies are not passed on. If the field is empty, no Invoice Processing Service Company fee applies to purchase orders of this vendor. The field has no effect on sales orders.', Updated=TO_TIMESTAMP('2026-10-14 18:03:10.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585538 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET Description='Rechnungsserviceunternehmen, dessen Servicegebühr dieser Lieferant in Bestellungen mit Streckengeschäft übernimmt. Leer = für Bestellungen dieses Lieferanten fällt keine Servicegebühr eines Rechnungsserviceunternehmens an.', Help='Zahlt der Kunde eines Verkaufsauftrags mit Streckengeschäft (Direktlieferung) eine Servicegebühr an das hier angegebene Rechnungsserviceunternehmen, erhält die Bestellung bei diesem Lieferanten eine eigene Rabattzeile mit dieser Gebühr. Gebühren anderer Rechnungsserviceunternehmen werden nicht übernommen. Ist das Feld leer, fällt für Bestellungen dieses Lieferanten keine Servicegebühr eines Rechnungsserviceunternehmens an. Auf Verkaufsaufträge hat das Feld keine Wirkung.', Updated=TO_TIMESTAMP('2026-10-14 18:03:10.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585538 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET Description='Rechnungsserviceunternehmen, dessen Servicegebühr dieser Lieferant in Bestellungen mit Streckengeschäft übernimmt. Leer = für Bestellungen dieses Lieferanten fällt keine Servicegebühr eines Rechnungsserviceunternehmens an.', Help='Zahlt der Kunde eines Verkaufsauftrags mit Streckengeschäft (Direktlieferung) eine Servicegebühr an das hier angegebene Rechnungsserviceunternehmen, erhält die Bestellung bei diesem Lieferanten eine eigene Rabattzeile mit dieser Gebühr. Gebühren anderer Rechnungsserviceunternehmen werden nicht übernommen. Ist das Feld leer, fällt für Bestellungen dieses Lieferanten keine Servicegebühr eines Rechnungsserviceunternehmens an. Auf Verkaufsaufträge hat das Feld keine Wirkung.', Updated=TO_TIMESTAMP('2026-10-14 18:03:10.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585538 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl SET Description='Rechnungsserviceunternehmen, dessen Servicegebühr dieser Lieferant in Bestellungen mit Streckengeschäft übernimmt. Leer = für Bestellungen dieses Lieferanten fällt keine Servicegebühr eines Rechnungsserviceunternehmens an.', Help='Zahlt der Kunde eines Verkaufsauftrags mit Streckengeschäft (Direktlieferung) eine Servicegebühr an das hier angegebene Rechnungsserviceunternehmen, erhält die Bestellung bei diesem Lieferanten eine eigene Rabattzeile mit dieser Gebühr. Gebühren anderer Rechnungsserviceunternehmen werden nicht übernommen. Ist das Feld leer, fällt für Bestellungen dieses Lieferanten keine Servicegebühr eines Rechnungsserviceunternehmens an. Auf Verkaufsaufträge hat das Feld keine Wirkung.', Updated=TO_TIMESTAMP('2026-10-14 18:03:10.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585538 AND AD_Language='fr_CH' AND IsTranslated='N'
;

/* de_DE */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585538, 'de_DE')
;

/* de_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585538, 'de_CH')
;

/* fr_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585538, 'fr_CH')
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585538, 'en_US')
;

-- 585539 PassOn_InvoiceProcessingServiceCompany: en_US label and Description/Help in all languages
UPDATE AD_Element_Trl SET Name='Service Fee Of', PrintName='Service Fee Of', Description='Invoice Processing Service Company whose service fee is passed on into drop-ship purchase orders (from the Compensation Group Contract Settings, display only). Empty = no Invoice Processing Service Company fee applies.', Help='If the customer of a drop-ship sales order pays a service fee to the Invoice Processing Service Company set here, the purchase order to this vendor gets a discount line of its own with that fee. Fees of other Invoice Processing Service Companies are not passed on. If the field is empty, no Invoice Processing Service Company fee applies to purchase orders of this vendor. The field has no effect on sales orders.', Updated=TO_TIMESTAMP('2026-10-14 18:03:10.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585539 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET Description='Rechnungsserviceunternehmen, dessen Servicegebühr in Bestellungen mit Streckengeschäft übernommen wird (aus den Kompensationsgruppen-Vertragseinstellungen, nur Anzeige). Leer = keine Servicegebühr eines Rechnungsserviceunternehmens.', Help='Zahlt der Kunde eines Verkaufsauftrags mit Streckengeschäft (Direktlieferung) eine Servicegebühr an das hier angegebene Rechnungsserviceunternehmen, erhält die Bestellung bei diesem Lieferanten eine eigene Rabattzeile mit dieser Gebühr. Gebühren anderer Rechnungsserviceunternehmen werden nicht übernommen. Ist das Feld leer, fällt für Bestellungen dieses Lieferanten keine Servicegebühr eines Rechnungsserviceunternehmens an. Auf Verkaufsaufträge hat das Feld keine Wirkung.', Updated=TO_TIMESTAMP('2026-10-14 18:03:10.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585539 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET Description='Rechnungsserviceunternehmen, dessen Servicegebühr in Bestellungen mit Streckengeschäft übernommen wird (aus den Kompensationsgruppen-Vertragseinstellungen, nur Anzeige). Leer = keine Servicegebühr eines Rechnungsserviceunternehmens.', Help='Zahlt der Kunde eines Verkaufsauftrags mit Streckengeschäft (Direktlieferung) eine Servicegebühr an das hier angegebene Rechnungsserviceunternehmen, erhält die Bestellung bei diesem Lieferanten eine eigene Rabattzeile mit dieser Gebühr. Gebühren anderer Rechnungsserviceunternehmen werden nicht übernommen. Ist das Feld leer, fällt für Bestellungen dieses Lieferanten keine Servicegebühr eines Rechnungsserviceunternehmens an. Auf Verkaufsaufträge hat das Feld keine Wirkung.', Updated=TO_TIMESTAMP('2026-10-14 18:03:10.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585539 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl SET Description='Rechnungsserviceunternehmen, dessen Servicegebühr in Bestellungen mit Streckengeschäft übernommen wird (aus den Kompensationsgruppen-Vertragseinstellungen, nur Anzeige). Leer = keine Servicegebühr eines Rechnungsserviceunternehmens.', Help='Zahlt der Kunde eines Verkaufsauftrags mit Streckengeschäft (Direktlieferung) eine Servicegebühr an das hier angegebene Rechnungsserviceunternehmen, erhält die Bestellung bei diesem Lieferanten eine eigene Rabattzeile mit dieser Gebühr. Gebühren anderer Rechnungsserviceunternehmen werden nicht übernommen. Ist das Feld leer, fällt für Bestellungen dieses Lieferanten keine Servicegebühr eines Rechnungsserviceunternehmens an. Auf Verkaufsaufträge hat das Feld keine Wirkung.', Updated=TO_TIMESTAMP('2026-10-14 18:03:10.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585539 AND AD_Language='fr_CH' AND IsTranslated='N'
;

/* de_DE */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585539, 'de_DE')
;

/* de_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585539, 'de_CH')
;

/* fr_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585539, 'fr_CH')
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585539, 'en_US')
;
