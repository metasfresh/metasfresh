/**
 * metasfresh C_DocType_ID constants for Playwright tests.
 *
 * These IDs are used for document type selection in dropdown fields.
 * The frontend uses data-test-id="option-{ID}" format for dropdown options.
 *
 * Discovery method:
 * SELECT c_doctype_id, name, docbasetype, docsubtype, issotrx
 * FROM c_doctype WHERE docbasetype IN ('APP', 'ARR', 'API', 'ARI', 'APC', 'ARC')
 *
 * Last updated: 2025-01-24
 */

// ============================================================================
// PAYMENT DOCUMENT TYPES
// ============================================================================

/**
 * AP Payment (Vendor Payment) - Zahlungsausgang
 * DocBaseType: APP
 * IsSoTrx: N (Purchase side)
 * Use for: Payments TO vendors
 */
export const DOCTYPE_AP_PAYMENT = 1000009;

/**
 * AR Receipt (Customer Receipt) - Zahlungseingang
 * DocBaseType: ARR
 * IsSoTrx: Y (Sales side)
 * Use for: Payments FROM customers
 */
export const DOCTYPE_AR_RECEIPT = 1000008;

// ============================================================================
// INVOICE DOCUMENT TYPES
// ============================================================================

/**
 * AP Invoice (Vendor Invoice) - Eingangsrechnung
 * DocBaseType: API
 * IsSoTrx: N (Purchase side)
 * Use for: Invoices FROM vendors
 */
export const DOCTYPE_AP_INVOICE = 1000005;

/**
 * AR Invoice (Customer Invoice) - Ausgangsrechnung
 * DocBaseType: ARI
 * IsSoTrx: Y (Sales side)
 * Use for: Invoices TO customers
 */
export const DOCTYPE_AR_INVOICE = 1000002;

/**
 * AR Invoice Indirect - Ausgangsrechnung (indirekt)
 * DocBaseType: ARI
 * IsSoTrx: Y (Sales side)
 */
export const DOCTYPE_AR_INVOICE_INDIRECT = 1000003;

// ============================================================================
// CREDIT MEMO DOCUMENT TYPES
// ============================================================================

/**
 * AP Credit Memo (Vendor Credit) - Gutschrift (Lieferant)
 * DocBaseType: APC
 * IsSoTrx: N (Purchase side)
 */
export const DOCTYPE_AP_CREDIT_MEMO = 1000006;

/**
 * AR Credit Memo (Customer Credit) - Gutschrift
 * DocBaseType: ARC
 * IsSoTrx: Y (Sales side)
 */
export const DOCTYPE_AR_CREDIT_MEMO = 1000004;
