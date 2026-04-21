/**
 * metasfresh AD_Window_ID constants for Playwright tests.
 *
 * Discovery method:
 * 1. Get table record from AD_Table by tablename
 * 2. For tables with both sales/purchase contexts, use po_window_id column
 * 3. Otherwise, find AD_Tab with seqno=0 for the table and get its ad_window_id
 *
 * Last updated: 2025-11-28 via metasfresh-application-dictionary skill
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

// ============================================================================
// MASTER DATA WINDOWS
// ============================================================================

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
