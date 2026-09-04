/**
 * metasfresh AD_Window_ID constants for Playwright tests.
 *
 * Discovery method:
 * 1. Get table record from AD_Table by tablename
 * 2. For tables with both sales/purchase contexts, use po_window_id column
 * 3. Otherwise, find AD_Tab with seqno=0 for the table and get its ad_window_id
 *
 * Last updated: 2026-07-30 via metasfresh-application-dictionary skill
 */

// ============================================================================
// PURCHASE SIDE WINDOWS
// ============================================================================

/**
 * Purchase Order window (Bestellung)
 * Table: C_Order (po_window_id)
 * Window ID: 181
 * Description: Bestellungen eingeben und verwalten
 */
export const PURCHASE_ORDER_WINDOW_ID = 181;

/**
 * Vendor Invoice window (Eingangsrechnung)
 * Table: C_Invoice (po_window_id)
 * Window ID: 183
 * Description: Eingabe von Rechnungen von Lieferanten
 */
export const VENDOR_INVOICE_WINDOW_ID = 183;

/**
 * Invoice Candidate window - Purchase side (Rechnungsdisposition Einkauf)
 * Table: C_Invoice_Candidate (po_window_id)
 * Window ID: 540983
 */
export const INVOICE_CANDIDATE_WINDOW_ID = 540983;

/**
 * Transport Order window (Transport Auftrag)
 * Table: M_ShipperTransportation
 * Window ID: 540020
 * Description: Transport orders (freight bookings); tab 540096 "Speditionslieferung" carries the
 * logistics dates ETD/ETA/ATD/ATA/B-L date.
 */
export const TRANSPORT_ORDER_WINDOW_ID = 540020;

/**
 * Delivery Planning window (Lieferplanung)
 * Table: M_Delivery_Planning
 * Window ID: 541632, tab 546674 (tabLevel 0)
 * Description: One row per order line to be moved; owns the four quantity figures
 * (PlannedLoadedQuantity, PlannedDischargeQuantity, ActualLoadQty, ActualDischargeQuantity) that the
 * delivery instruction's Versandpaket line mirrors.
 */
export const DELIVERY_PLANNING_WINDOW_ID = 541632;

/**
 * Delivery Instruction window (Lieferanweisungen)
 * Table: M_ShipperTransportation
 * Window ID: 541657, root tab 546732; included tab 546736 "Versandpaket" (M_ShippingPackage) carries
 * the mirrored quantity figures.
 * Note: window 540020 "Transport Auftrag" is the OTHER window over the same table.
 */
export const DELIVERY_INSTRUCTION_WINDOW_ID = 541657;

/**
 * Receipt Disposition including Delivery Planning window (Wareneingangsdisposition inkl. Lieferplanung)
 * Table: RV_ReceiptDisposition_DeliveryPlanning (AD_Table_ID=542644) — a union view over incoming delivery plannings and
 * unplanned receipt schedules, single tab.
 * Window ID: 542190
 * Note: window 541954 "Wareneingangsdisposition Logistik" is the OTHER, delivery-planning-only window —
 * both windows' specs read this file, so the two names must not be swapped here.
 */
export const RECEIPT_DISPOSITION_DELIVERY_PLANNING_WINDOW_ID = 542190;

// ============================================================================
// MASTER DATA WINDOWS
// ============================================================================

/**
 * Shipper window (Lieferweg)
 * Table: M_Shipper
 * Window ID: 142
 * Description: Carriers/shipping methods; M_Shipper.C_BPartner_ID links the carrier partner.
 */
export const SHIPPER_WINDOW_ID = 142;

/**
 * Business Partner window
 * Window ID: 123
 * Note: Used in existing business-partner.spec.js tests
 */
export const BUSINESS_PARTNER_WINDOW_ID = 123;

/**
 * Product window
 * Window ID: 140
 * Note: Used in existing product.spec.js tests
 */
export const PRODUCT_WINDOW_ID = 140;

// ============================================================================
// SALES SIDE WINDOWS
// ============================================================================

/**
 * Sales Order window (Auftrag)
 * Table: C_Order (ad_window_id)
 * Window ID: 143
 * Description: Aufträge eingeben und verwalten
 */
export const SALES_ORDER_WINDOW_ID = 143;

/**
 * Shipment Candidates window (Lieferdisposition)
 * Table: M_ShipmentSchedule
 * Window ID: 500221
 * Description: Lieferdisposition verwalten
 */
export const SHIPMENT_CANDIDATES_WINDOW_ID = 500221;

/**
 * Shipment window (Material Delivery / Lieferung)
 * Table: M_InOut
 * Window ID: 169
 * Description: Shipment (Customer) / Material Delivery
 */
export const SHIPMENT_WINDOW_ID = 169;

/**
 * Invoice Candidate window - Sales side (Rechnungsdisposition)
 * Table: C_Invoice_Candidate (ad_window_id)
 * Window ID: 540092
 * Description: Rechnungsdisposition (Verkauf)
 */
export const SALES_INVOICE_CANDIDATE_WINDOW_ID = 540092;

/**
 * Sales Invoice window (Ausgangsrechnung)
 * Table: C_Invoice (ad_window_id)
 * Window ID: 167
 * Description: Sales Invoice / Customer Invoice
 */
export const SALES_INVOICE_WINDOW_ID = 167;

/**
 * Material Receipt Candidates window (Wareneingangsdisposition)
 * Table: M_ReceiptSchedule
 * Window ID: 540196
 * Description: Wareneingangsdisposition verwalten
 */
export const RECEIPT_CANDIDATES_WINDOW_ID = 540196;

// ============================================================================
// TAX WINDOWS
// ============================================================================

/**
 * Tax window (Steuersatz)
 * Table: C_Tax
 * AD_Window_ID: 137
 * Tab: Steuer (AD_Tab_ID 174)
 * Description: Define tax rates and their EN16931 VAT category for e-invoicing
 */
export const TAX_WINDOW_ID = 137;

// ============================================================================
// PAYMENT WINDOWS
// ============================================================================

/**
 * Payment Term window (Zahlungsbedingung)
 * Table: C_PaymentTerm
 * Window ID: 141
 * Description: Define payment terms including discount days and percentages
 */
export const PAYMENT_TERM_WINDOW_ID = 141;

/**
 * Payment window (Zahlung)
 * Table: C_Payment
 * Window ID: 195
 * Description: Payments to vendors and from customers
 */
export const PAYMENT_WINDOW_ID = 195;

// ============================================================================
// PLANNING WINDOWS
// ============================================================================

/**
 * Forecast window (Prognose)
 * Table: M_Forecast (AD_Table_ID=720)
 * Window ID: 328
 * Description: Forecast management with forecast line generation
 */
export const FORECAST_WINDOW_ID = 328;

// ============================================================================
// STATISTICS / READ-ONLY VIEW WINDOWS
// ============================================================================

/**
 * Purchase & Sales Overview window (Ein- und Verkaufsübersicht)
 * Table: C_Order_M_InOut_C_Invoice_Overview_V (AD_Table_ID=542578)
 * Window ID: 542070
 * Description: Read-only grid view combining order, shipment, and invoice lines
 * with current stock on hand. Used for sales & purchase statistics.
 */
export const PURCHASE_SALES_OVERVIEW_WINDOW_ID = 542070;

/**
 * Stock per Week window (Bestand pro Woche)
 * Table: MD_Stock_PerWeek_V (AD_Table_ID=542612)
 * Window ID: 542159
 * Description: Read-only grid view of available-to-promise stock per week.
 * Opens EMPTY (queryIfNoFilters=false via StockPerWeekSqlViewBindingCustomizer);
 * rows load only after a filter (product / warehouse / week range) is applied.
 */
export const STOCK_PER_WEEK_WINDOW_ID = 542159;

// ============================================================================
// PICKING WINDOWS
// ============================================================================

/**
 * Picking Terminal V2 (Kommissionier-Terminal v2)
 * Custom view backed by PackageableViewFactoryV2
 * Window ID: 540485
 * Description: Browse-all picking terminal showing shipment schedules
 */
export const PICKING_TERMINAL_V2_WINDOW_ID = 540485;

/**
 * Picking Terminal V1 (Kommissionier-Terminal)
 * Custom view backed by PackageableView with included PickingSlotView
 * Window ID: 540350
 * Description: Classic picking terminal with HU editor
 */
export const PICKING_TERMINAL_V1_WINDOW_ID = 540350;

// ============================================================================
// SYSTEM ADMINISTRATION WINDOWS
// ============================================================================

/**
 * View Invalidation on Change window (View-Invalidierung bei Änderung)
 * Table: WEBUI_ViewInvalidateOnChange (AD_Table_ID=542631)
 * Window ID: 542178
 * Element: 585139
 * Description: Minimal System-Administration admin window over the
 * WEBUI_ViewInvalidateOnChange config table. Single tab with fields
 * AD_Window_ID (TableDir, mandatory), AD_Table_ID (TableDir, mandatory),
 * IsActive, AD_Org_ID, AD_Client_ID.
 */
export const VIEW_INVALIDATE_ON_CHANGE_WINDOW_ID = 542178;

// ============================================================================
// SYSTEM/TEST WINDOWS
// ============================================================================

/**
 * Test Window - Contains all supported widget types for E2E testing
 * Table: Test (AD_Table_ID=291)
 * Window ID: 127
 * Description: Test window with all widget types for comprehensive testing
 *
 * Widget types available:
 * - Text: Name, Description
 * - LongText: Help (Comment/Help), CharacterData, C_BPartner_Memo
 * - Quantity: T_Qty
 * - Amount: T_Amount
 * - Number: T_Number
 * - Integer: T_Integer, Test_ID
 * - Date: T_Date
 * - ZonedDateTime: T_DateTime
 * - Time: T_Time
 * - Timestamp: Created, Updated
 * - Switch: IsActive
 * - YesNo: Processed
 * - Button: Processing (Process Now)
 * - List: AD_Client_ID, C_Currency_ID, C_UOM_ID, M_HU_PI_Item_Product_ID
 * - Lookup: AD_Org_ID, C_BPartner_ID, M_Product_ID, M_Locator_ID, etc.
 * - Address: C_Location_ID
 * - Image: BinaryData
 * - ProductAttributes: M_AttributeSetInstance_ID
 */
export const TEST_WINDOW_ID = 127;
