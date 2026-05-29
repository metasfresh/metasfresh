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
 * Transforms sales order messages to metasfresh purchase candidate format
 * @param {string} messageToMetasfresh - JSON string containing sales order data
 * @returns {string} JSON string with transformation results or error
 */
function transform(messageToMetasfresh) {
    try {
        const input = JSON.parse(messageToMetasfresh);
        const salesOrders = input?.d2m_so;

        validateInput(salesOrders);

        const results = salesOrders
            .flatMap(transformSalesOrder);

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

// ============================================================================
// Constants
// ============================================================================

const CONFIG = {
    EXT_SYSTEM_CODE: "Dynamics365",
    DEFAULT_CURRENCY: "EUR",
    WAREHOUSE_IDENTIFIER: "Hochregal", // Hardcoded by design - not provided in incoming JSON
    get EXT_PREFIX() {
        return `ext-${this.EXT_SYSTEM_CODE}-`;
    }
};

const ROUTES = {
    CREATE_PURCHASE_CANDIDATE: "metasfresh.create-purchase-candidate-v2.camel.uri",
    ENQUEUE_PURCHASE_CANDIDATE: "To-MF_Enqueue_Purchases_Candidate-Route"
};

const UOM_MAPPING = {
    "ea": "PCE"
    // Add more mappings here as needed
};

// ============================================================================
// Validation
// ============================================================================

/**
 * Validates the input structure
 * @param {*} salesOrders - Sales orders array to validate
 * @throws {Error} If input is invalid
 */
function validateInput(salesOrders) {
    if (!Array.isArray(salesOrders)) {
        throw new Error("Input JSON must contain a 'd2m_so' array.");
    }

    salesOrders.forEach((so, index) => {
        if (!so.orderNumber) {
            throw new Error(`Sales order at index ${index} missing orderNumber`);
        }
        if (!Array.isArray(so.lines) || so.lines.length < 0) {
            throw new Error(`Sales order at index ${index} missing lines array`);
        }
        if (so.lines.some(line => isNaN(line.price) || isNaN(line.qty))) {
            throw new Error(`Sales order at index ${index} has invalid numeric values`);
        }
    });
}

// ============================================================================
// Main Transformation
// ============================================================================

/**
 * Transforms a single sales order into service requests
 * @param {Object} salesOrder - Sales order object
 * @returns {Array} Array of service route requests
 */
function transformSalesOrder(salesOrder) {
    return [
        createPurchaseCandidatesRequest(salesOrder),
        createEnqueueProcessRequest(salesOrder)
    ];
}

// ============================================================================
// Request Builders
// ============================================================================

/**
 * Creates purchase candidates request from sales order
 * @param {Object} salesOrder - Sales order object
 * @returns {Object} Service request object
 */
function createPurchaseCandidatesRequest(salesOrder) {
    const purchaseCandidates = salesOrder.lines.map(line =>
        buildPurchaseCandidate(salesOrder, line)
    );

    const body = {
        jsonPurchaseCandidateCreateRequest: { purchaseCandidates }
    };

    return createServiceRequest(ROUTES.CREATE_PURCHASE_CANDIDATE, body);
}

/**
 * Creates enqueue process request for purchase candidates
 * @param {Object} salesOrder - Sales order object
 * @returns {Object} Service request object
 */
function createEnqueueProcessRequest(salesOrder) {
    const externalLineIds = salesOrder.lines.map(line => line?.orderLineId ?? null);

    const body = {
        purchaseCandidates: [
            {
                externalSystemCode: CONFIG.EXT_SYSTEM_CODE,
                externalHeaderId: salesOrder.orderNumber,
                externalLineIds
            }
        ]
    };

    return createServiceRequest(ROUTES.ENQUEUE_PURCHASE_CANDIDATE, body);
}

// ============================================================================
// Purchase Candidate Builder
// ============================================================================

/**
 * Builds a purchase candidate object from sales order line
 * @param {Object} salesOrder - Parent sales order
 * @param {Object} line - Sales order line
 * @returns {Object} Purchase candidate object
 */
function buildPurchaseCandidate(salesOrder, line) {
    return {
        orgCode: salesOrder.orgCode ?? null,
        externalSystemCode: CONFIG.EXT_SYSTEM_CODE,
        externalHeaderId: salesOrder.orderNumber ?? null,
        externalLineId: line.orderLineId ?? null,
        poReference: salesOrder.orderDocumentNo ?? null,

        isManualPrice: true,
        price: buildPriceInfo(line, salesOrder.currency),

        isManualDiscount: true,
        discount: Number(line.discount ?? 0),

        purchaseDatePromised: toZonedDateTime(salesOrder.datePromised),
        purchaseDateOrdered: toZonedDateTime(salesOrder.dateOrdered),

        vendor: buildVendorInfo(salesOrder.partnerIdentifier),
        warehouseIdentifier: buildWarehouseIdentifier(),

        productIdentifier: buildProductIdentifier(line.productIdentifier),
        qty: buildQuantityInfo(line)
    };
}

/**
 * Builds price information object
 * @param {Object} line - Sales order line
 * @param {string} currency - Currency code from sales order
 * @returns {Object} Price information
 */
function buildPriceInfo(line, currency) {
    return {
        value: Number(line.price ?? 0),
        currencyCode: currency ?? CONFIG.DEFAULT_CURRENCY,
        priceUomCode: mapUom(line.uom)
    };
}

/**
 * Builds vendor information object
 * @param {string} partnerIdentifier - Partner identifier
 * @returns {Object} Vendor information
 */
function buildVendorInfo(partnerIdentifier) {
    return {
        bpartnerIdentifier: `${CONFIG.EXT_PREFIX}${partnerIdentifier || ""}`
    };
}

/**
 * Builds warehouse identifier
 * @returns {string} Warehouse identifier with prefix
 */
function buildWarehouseIdentifier() {
    return `${CONFIG.EXT_PREFIX}${CONFIG.WAREHOUSE_IDENTIFIER}`;
}

/**
 * Builds product identifier
 * @param {string} productIdentifier - Raw product identifier
 * @returns {string} Formatted product identifier
 */
function buildProductIdentifier(productIdentifier) {
    return `val-${productIdentifier}`;
}

/**
 * Builds quantity information object
 * @param {Object} line - Sales order line
 * @returns {Object} Quantity information
 */
function buildQuantityInfo(line) {
    return {
        qty: Number(line.qty ?? 0),
        uomCode: mapUom(line.uom)
    };
}

// ============================================================================
// Helper Functions
// ============================================================================

/**
 * Maps UOM code according to configured mappings
 * @param {string} uom - Input UOM code
 * @returns {string|null} Mapped UOM code or original if no mapping exists
 */
function mapUom(uom) {
    if (!uom) {
        return null;
    }
    return UOM_MAPPING[uom] ?? uom;
}

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
 * Converts date string to zoned datetime format
 * @param {string} dateStr - Date string (ISO or date-only format)
 * @returns {string|null} Zoned datetime string or null
 */
function toZonedDateTime(dateStr) {
    if (!dateStr) {
        return null;
    }

    return dateStr.includes("T")
        ? dateStr
        : `${dateStr}T00:00:00Z`;
}