import { getPage } from './common';
import { expect } from '@playwright/test';

/**
 * WebAPI Validation Utilities
 *
 * These utilities help verify record state and validation by querying the metasfresh WebAPI.
 * They're essential for ensuring records are properly saved before attempting to add child records.
 *
 * CRITICAL: Record Validity Check
 * ===============================
 * The WebAPI returns a `valid` field in the response. If `valid: false`, changes made in the
 * UI WILL NOT BE SAVED. Always call `assertRecordIsValid()` before attempting to modify records.
 *
 * Key Concept:
 * - Records must be VALID before changes can be saved
 * - Records must be SAVED before child records (tabs) can be added
 * - WebAPI provides complete validation status including mandatory fields
 * - Auto-fill: Some mandatory fields are auto-set when key fields (like C_BPartner) are filled
 *
 * Usage Pattern:
 * 1. Navigate to record
 * 2. **MANDATORY**: Call assertRecordIsValid() - if valid=false, changes won't save!
 * 3. Fill parent record fields (e.g., Business Partner in Sales Order)
 * 4. Check validation status via getRecordData()
 * 5. Fill any remaining mandatory fields
 * 6. Verify record is saved via waitForRecordSaved()
 * 7. Only then proceed to add child records (order lines, etc.)
 */

const WEBAPI_BASE_URL = process.env.WEBAPI_BASE_URL || 'http://localhost:8080/rest/api';

/**
 * Get complete record data including validation status from WebAPI.
 *
 * @param {string} windowId - Window ID (e.g., '143' for Sales Order)
 * @param {string} recordId - Record ID (e.g., '1000020')
 * @returns {Promise<Object>} Record data including fieldsByName, validStatus, saveStatus, includedTabsInfo
 *
 * @example
 * const recordData = await getRecordData('143', '1000020');
 * console.log('Is saved:', recordData.saveStatus.saved);
 * console.log('Can add lines:', recordData.includedTabsInfo['AD_Tab-187'].allowCreateNew);
 */
export async function getRecordData(windowId, recordId) {
  try {
    const page = getPage();

    // Use Playwright's page.request to make API call with browser's cookies
    const response = await page.request.get(`${WEBAPI_BASE_URL}/window/${windowId}/${recordId}`, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok()) {
      throw new Error(`HTTP ${response.status()}: ${response.statusText()}`);
    }

    const data = await response.json();

    // Response is an array with one element (the record)
    const recordData = data[0];
    return recordData;
  } catch (error) {
    console.error(`Failed to fetch record data for window ${windowId}, record ${recordId}:`, error.message);
    throw error;
  }
}

/**
 * MANDATORY: Assert that a record is valid before attempting to modify it.
 *
 * CRITICAL: If `valid: false`, changes made in the UI WILL NOT BE SAVED!
 * This is a common cause of silent failures in E2E tests.
 *
 * @param {string} windowId - Window ID (e.g., '123' for Business Partner)
 * @param {string} recordId - Record ID
 * @param {string} [context] - Optional context for error message (e.g., 'before setting PO_PaymentTerm_ID')
 * @throws {Error} If record is not valid
 *
 * @example
 * // ALWAYS call this before modifying a record via API
 * await assertRecordIsValid('123', bpartnerId, 'before API modification');
 * // Note: UI-based methods like BusinessPartnerPage.setPOPaymentTerm() handle this internally
 */
export async function assertRecordIsValid(windowId, recordId, context = '') {
  const recordData = await getRecordData(windowId, recordId);

  const isValid = recordData.valid === true || recordData.validStatus?.valid === true;
  const reason = recordData.validStatus?.reason || 'Unknown reason';

  if (!isValid) {
    const contextMsg = context ? ` (${context})` : '';
    const errorMsg = `Record ${windowId}/${recordId} is NOT VALID${contextMsg}. Reason: ${reason}. Changes will NOT be saved!`;
    console.error(errorMsg);

    // Log additional details for debugging
    if (recordData.validStatus) {
      console.error('validStatus:', JSON.stringify(recordData.validStatus, null, 2));
    }

    // Collect invalid fields for detailed error
    const invalidFields = [];
    if (recordData.fieldsByName) {
      for (const [fieldName, fieldData] of Object.entries(recordData.fieldsByName)) {
        if (fieldData.validStatus && !fieldData.validStatus.valid) {
          invalidFields.push(`${fieldName}: ${fieldData.validStatus.reason}`);
        }
      }
    }
    if (invalidFields.length > 0) {
      console.error('Invalid fields:', invalidFields.join(', '));
    }

    throw new Error(errorMsg);
  }

  console.log(`Record ${windowId}/${recordId} is VALID${context ? ` (${context})` : ''}`);
  return true;
}

/**
 * Get validation status for a record.
 *
 * @param {string} windowId - Window ID
 * @param {string} recordId - Record ID
 * @returns {Promise<Object>} Validation status { valid, reason, fieldName, missingFields }
 *
 * @example
 * const validation = await getValidationStatus('143', '1000020');
 * if (!validation.valid) {
 *   console.log('Missing fields:', validation.missingFields);
 *   console.log('Reason:', validation.reason);
 * }
 */
export async function getValidationStatus(windowId, recordId) {
  const recordData = await getRecordData(windowId, recordId);

  const validation = {
    valid: recordData.validStatus?.valid || false,
    reason: recordData.validStatus?.reason || null,
    fieldName: recordData.validStatus?.fieldName || null,
    missingFields: [],
  };

  // Collect all invalid mandatory fields
  if (recordData.fieldsByName) {
    for (const [fieldName, fieldData] of Object.entries(recordData.fieldsByName)) {
      if (
        fieldData.mandatory &&
        fieldData.validStatus &&
        !fieldData.validStatus.valid &&
        fieldData.validStatus.reason &&
        fieldData.validStatus.reason !== 'not validated yet'
      ) {
        validation.missingFields.push({
          field: fieldName,
          caption: fieldData.field,
          reason: fieldData.validStatus.reason,
          value: fieldData.value,
        });
      }
    }
  }

  return validation;
}

/**
 * Get save status for a record.
 *
 * @param {string} windowId - Window ID
 * @param {string} recordId - Record ID
 * @returns {Promise<Object>} Save status { saved, presentInDatabase, hasChanges, error, reason }
 *
 * @example
 * const saveStatus = await getSaveStatus('143', '1000020');
 * if (!saveStatus.saved) {
 *   console.log('Record not saved:', saveStatus.reason);
 * }
 */
export async function getSaveStatus(windowId, recordId) {
  const recordData = await getRecordData(windowId, recordId);
  return recordData.saveStatus || {};
}

/**
 * Check if a tab allows creating new records.
 * This is essential before attempting to add child records (e.g., order lines).
 *
 * @param {string} windowId - Window ID
 * @param {string} recordId - Record ID
 * @param {string} tabId - Tab ID (e.g., 'AD_Tab-187' for Sales Order Lines)
 * @returns {Promise<Object>} Tab info { allowCreateNew, allowCreateNewReason, allowDelete }
 *
 * @example
 * const tabInfo = await getTabInfo('143', '1000020', 'AD_Tab-187');
 * if (!tabInfo.allowCreateNew) {
 *   console.log('Cannot add lines:', tabInfo.allowCreateNewReason);
 *   // Common reason: "ParentDocumentNew" - parent must be saved first
 * }
 */
export async function getTabInfo(windowId, recordId, tabId) {
  const recordData = await getRecordData(windowId, recordId);

  if (!recordData.includedTabsInfo || !recordData.includedTabsInfo[tabId]) {
    throw new Error(`Tab ${tabId} not found in window ${windowId} record ${recordId}`);
  }

  return recordData.includedTabsInfo[tabId];
}

/**
 * Wait for a record to be saved (with retries).
 * Useful after filling mandatory fields and triggering save.
 *
 * @param {string} windowId - Window ID
 * @param {string} recordId - Record ID
 * @param {Object} options - Options { maxRetries: 10, retryDelayMs: 500 }
 * @returns {Promise<boolean>} True if saved, throws error if timeout
 *
 * @example
 * // After filling fields and triggering save
 * await waitForRecordSaved('143', '1000020');
 * // Now safe to add order lines
 */
export async function waitForRecordSaved(windowId, recordId, options = {}) {
  const { maxRetries = 10, retryDelayMs = 500 } = options;

  for (let i = 0; i < maxRetries; i++) {
    const saveStatus = await getSaveStatus(windowId, recordId);

    if (saveStatus.saved && !saveStatus.error) {
      console.log(`Record ${windowId}/${recordId} is saved (attempt ${i + 1}/${maxRetries})`);
      return true;
    }

    if (saveStatus.error) {
      const validation = await getValidationStatus(windowId, recordId);
      throw new Error(
        `Record ${windowId}/${recordId} has validation errors: ${saveStatus.reason}. Missing fields: ${JSON.stringify(validation.missingFields)}`
      );
    }

    console.log(
      `Waiting for record ${windowId}/${recordId} to be saved (attempt ${i + 1}/${maxRetries}): ${saveStatus.reason || 'not yet saved'}`
    );
    await new Promise((resolve) => setTimeout(resolve, retryDelayMs));
  }

  const validation = await getValidationStatus(windowId, recordId);
  throw new Error(
    `Timeout waiting for record ${windowId}/${recordId} to be saved after ${maxRetries} retries. Missing fields: ${JSON.stringify(validation.missingFields)}`
  );
}

/**
 * Wait for a tab to allow creating new records (with retries).
 * Essential before attempting to add child records like order lines.
 *
 * @param {string} windowId - Window ID
 * @param {string} recordId - Record ID
 * @param {string} tabId - Tab ID
 * @param {Object} options - Options { maxRetries: 10, retryDelayMs: 500 }
 * @returns {Promise<boolean>} True if tab allows new records, throws error if timeout
 *
 * @example
 * // Before adding order lines
 * await waitForTabAllowsNew('143', '1000020', 'AD_Tab-187');
 * // Now safe to add order lines
 */
export async function waitForTabAllowsNew(windowId, recordId, tabId, options = {}) {
  const { maxRetries = 10, retryDelayMs = 500 } = options;

  for (let i = 0; i < maxRetries; i++) {
    const tabInfo = await getTabInfo(windowId, recordId, tabId);

    if (tabInfo.allowCreateNew) {
      console.log(`Tab ${tabId} in record ${windowId}/${recordId} allows creating new records (attempt ${i + 1}/${maxRetries})`);
      return true;
    }

    console.log(
      `Waiting for tab ${tabId} to allow new records (attempt ${i + 1}/${maxRetries}): ${tabInfo.allowCreateNewReason || 'not yet allowed'}`
    );
    await new Promise((resolve) => setTimeout(resolve, retryDelayMs));
  }

  const tabInfo = await getTabInfo(windowId, recordId, tabId);
  throw new Error(
    `Timeout waiting for tab ${tabId} in record ${windowId}/${recordId} to allow new records after ${maxRetries} retries. Reason: ${tabInfo.allowCreateNewReason}`
  );
}

/**
 * Get detailed field information for a specific field.
 *
 * @param {string} windowId - Window ID
 * @param {string} recordId - Record ID
 * @param {string} fieldName - Field name (e.g., 'M_Warehouse_ID')
 * @returns {Promise<Object>} Field data including value, mandatory, readonly, validStatus
 *
 * @example
 * const warehouseField = await getFieldData('143', '1000020', 'M_Warehouse_ID');
 * console.log('Warehouse value:', warehouseField.value);
 * console.log('Is mandatory:', warehouseField.mandatory);
 * console.log('Is valid:', warehouseField.validStatus.valid);
 */
export async function getFieldData(windowId, recordId, fieldName) {
  const recordData = await getRecordData(windowId, recordId);

  if (!recordData.fieldsByName || !recordData.fieldsByName[fieldName]) {
    throw new Error(`Field ${fieldName} not found in window ${windowId} record ${recordId}`);
  }

  return recordData.fieldsByName[fieldName];
}

/**
 * Debug helper: Print comprehensive record validation info to console.
 * Useful for understanding why a record won't save or why child records can't be added.
 *
 * @param {string} windowId - Window ID
 * @param {string} recordId - Record ID
 *
 * @example
 * await debugRecordValidation('143', '1000020');
 * // Outputs:
 * // Record 143/1000020 Validation Summary:
 * // - Saved: false
 * // - Valid: false
 * // - Reason: Fill mandatory fields: Warehouse
 * // Missing Fields:
 * // - M_Warehouse_ID: Fill mandatory fields: Warehouse
 * // - C_Currency_ID: Fill mandatory fields: Currency
 * // Tabs:
 * // - AD_Tab-187 (Order Lines): allowCreateNew=false (ParentDocumentNew)
 */
export async function debugRecordValidation(windowId, recordId) {
  try {
    const recordData = await getRecordData(windowId, recordId);
    const validation = await getValidationStatus(windowId, recordId);
    const saveStatus = await getSaveStatus(windowId, recordId);

    console.log(`\n=== Record ${windowId}/${recordId} Validation Summary ===`);
    console.log(`Saved: ${saveStatus.saved}`);
    console.log(`Valid: ${validation.valid}`);
    console.log(`Reason: ${validation.reason || 'N/A'}`);

    if (validation.missingFields.length > 0) {
      console.log('\nMissing Fields:');
      validation.missingFields.forEach((field) => {
        console.log(`  - ${field.field}: ${field.reason} (current value: ${JSON.stringify(field.value)})`);
      });
    }

    if (recordData.includedTabsInfo) {
      console.log('\nTabs:');
      for (const [tabId, tabInfo] of Object.entries(recordData.includedTabsInfo)) {
        console.log(
          `  - ${tabId}: allowCreateNew=${tabInfo.allowCreateNew}${tabInfo.allowCreateNewReason ? ` (${tabInfo.allowCreateNewReason})` : ''}`
        );
      }
    }

    console.log('=====================================\n');
  } catch (error) {
    console.error(`Failed to debug record validation: ${error.message}`);
  }
}
