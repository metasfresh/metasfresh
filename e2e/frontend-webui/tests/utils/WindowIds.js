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
 * Invoice Candidate window - Sales side (Rechnungsdisposition)
 * Table: C_Invoice_Candidate (ad_window_id)
 * Window ID: 540092
 * Description: Rechnungsdisposition (Verkauf)
 */
export const SALES_INVOICE_CANDIDATE_WINDOW_ID = 540092;

/**
 * Material Receipt Candidates window (Wareneingangsdisposition)
 * Table: M_ReceiptSchedule
 * Window ID: 540196
 * Description: Wareneingangsdisposition verwalten
 */
export const RECEIPT_CANDIDATES_WINDOW_ID = 540196;
