/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Transforms delivery note messages to metasfresh receipt format
 * @param {string} messageToMetasfresh - JSON string containing delivery note data
 * @returns {string} JSON string with transformation results or error
 */
function transform(messageToMetasfresh) {
    try {
        const input = JSON.parse(messageToMetasfresh);
        const deliveryNotes = input?.d2m_dn;

        validateInput(deliveryNotes);

        const results = deliveryNotes
            .flatMap(transformDeliveryNote);

        return JSON.stringify(results);

    } catch (err) {
        return JSON.stringify({
            errors: [{
                message: err.message,
                code: "TRANSFORMATION_ERROR",
                type: err.name
            }]
        });
    }
}

const CONFIG = {
    EXT_SYSTEM_CODE: "Dynamics365"
};

const ROUTES = {
    CREATE_RECEIPTS: "metasfresh.create-receipts-v2.camel.uri"
};

// ============================================================================
// Validation
// ============================================================================

/**
 * Validates the input structure
 * @param {*} deliveryNotes - Delivery notes array to validate
 * @throws {Error} If input is invalid
 */
function validateInput(deliveryNotes) {
    if (!Array.isArray(deliveryNotes)) {
        throw new Error("Input JSON must contain a 'd2m_dn' array.");
    }

    deliveryNotes.forEach((dn, index) => {
        if (!Array.isArray(dn.lines) || dn.lines.length === 0) {
            throw new Error(`Delivery note at index ${index} missing lines array`);
        }

        // Validate each line has at least one identification method
        dn.lines.forEach((line, lineIndex) => {
            const identificationMethods = countIdentificationMethods(dn, line);
            if (identificationMethods === 0) {
                throw new Error(
                    `Delivery note at index ${index}, line ${lineIndex} has no valid identification method. ` +
                    `Must have either: (orderNumber + externalLineId) or poLineId`
                );
            }

            // Validate quantity
            if (isNaN(line.qty) || Number(line.qty) <= 0) {
                throw new Error(`Delivery note at index ${index}, line ${lineIndex} has invalid quantity`);
            }
        });
    });
}

/**
 * Counts valid identification methods for a delivery note line
 * @param {Object} deliveryNote - Parent delivery note
 * @param {Object} line - Delivery note line
 * @returns {number} Count of valid identification methods
 */
function countIdentificationMethods(deliveryNote, line) {
    let count = 0;

    // Method 1: externalHeaderId + externalLineId
    if (deliveryNote.orderNumber && deliveryNote.orderNumber.trim() !== ''
            && line.externalLineId && line.externalLineId.trim() !== '') {
        count++;
    }

    // Method 2: orderLineId (mapped from poLineId)
    if (line.poLineId && line.poLineId.trim() !== '') {
        count++;
    }

    return count;
}

// ============================================================================
// Main Transformation
// ============================================================================

/**
 * Transforms a single delivery note into service requests
 * @param {Object} deliveryNote - Delivery note object
 * @returns {Array} Array of service route requests
 */
function transformDeliveryNote(deliveryNote) {
    return [
        createReceiptsRequest(deliveryNote)
    ];
}

// ============================================================================
// Request Builders
// ============================================================================

/**
 * Creates receipts request from delivery note
 * @param {Object} deliveryNote - Delivery note object
 * @returns {Object} Service request object
 */
function createReceiptsRequest(deliveryNote) {
    const receiptList = deliveryNote.lines.map(line =>
        buildReceiptInfo(deliveryNote, line)
    );

    const body = {
        jsonCreateReceiptsRequest: {
            receiptList: receiptList,
            returnList: []
        }
    };

    return createServiceRequest(ROUTES.CREATE_RECEIPTS, body);
}

// ============================================================================
// Receipt Info Builder
// ============================================================================

/**
 * Builds a receipt info object from delivery note line
 * Prioritizes orderLineId (poLineId) as primary identification method
 * @param {Object} deliveryNote - Parent delivery note
 * @param {Object} line - Delivery note line
 * @returns {Object} Receipt info object
 */
function buildReceiptInfo(deliveryNote, line) {
    // Prioritize orderLineId - if it exists, clear other identification methods
    const hasOrderLineId = !!(line.poLineId && line.poLineId.trim() !== '');

    return {
        // Identification methods (prioritized: orderLineId first)
        orderLineId: hasOrderLineId ? buildMetasfreshId(line.poLineId) : null,
        externalSystemCode: hasOrderLineId ? null : CONFIG.EXT_SYSTEM_CODE,
        externalHeaderId: hasOrderLineId ? null : (deliveryNote.orderNumber ?? null),
        externalLineId: hasOrderLineId ? null : (line.externalLineId ?? null),

        // Quantity
        movementQuantity: Number(line.qty ?? 0),

        // Dates
        dateReceived: toLocalDateTime(deliveryNote.dateShip),
        movementDate: toLocalDate(deliveryNote.dateShip)
    };
}

// ============================================================================
// Helper Functions
// ============================================================================

/**
 * Creates a service request object
 * @param {string} routeId - Camel route identifier
 * @param {Object} body - Request body
 * @returns {Object} Service request object
 */
function createServiceRequest(routeId, body) {
    return {
        camelServiceRouteID: routeId,
        requestBody: JSON.stringify(body)
    };
}

/**
 * Builds a Metasfresh ID as integer
 * @param {string|number} value - ID value
 * @returns {number|null} Integer ID or null
 */
function buildMetasfreshId(value) {
    if (!value) {
        return null;
    }
    const intValue = parseInt(value, 10);
    if (isNaN(intValue)) {
        return null;
    }
    return intValue;
}

/**
 * Converts date string to LocalDateTime format (ISO 8601 without a timezone)
 * @param {string} dateStr - Date string (ISO or date-only format)
 * @returns {string|null} LocalDateTime string or null
 */
function toLocalDateTime(dateStr) {
    if (!dateStr) {
        return null;
    }

    // If it already has a time component, return as-is (without Z)
    if (dateStr.includes("T")) {
        return dateStr.replace("Z", "");
    }

    // Otherwise add midnight time
    return `${dateStr}T00:00:00`;
}

/**
 * Converts date string to LocalDate format (YYYY-MM-DD)
 * @param {string} dateStr - Date string (ISO or date-only format)
 * @returns {string|null} LocalDate string or null
 */
function toLocalDate(dateStr) {
    if (!dateStr) {
        return null;
    }

    // Extract just the date part if it contains time
    if (dateStr.includes("T")) {
        return dateStr.split("T")[0];
    }

    return dateStr;
}