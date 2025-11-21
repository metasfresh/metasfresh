/**
 * Transforms external purchase order messages to metasfresh sales order line candidates format
 * @param {string} messageToMetasfresh - JSON string containing purchase order data
 * @returns {string} JSON string with transformation results or error
 */
function transform(messageToMetasfresh) {
    try {
        const input = JSON.parse(messageToMetasfresh);
        const purchaseOrders = input?.d2m_po;

        validateInput(purchaseOrders);

        const results = purchaseOrders
            .flatMap(transformPurchaseOrder);

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
    CURRENCY_CODE: "EUR",
    get EXT_PREFIX() {
        return `ext-${this.EXT_SYSTEM_CODE}-`;
    }
};

const ROUTES = {
    UPSERT_BPARTNER: "metasfresh.upsert-bpartner-v2.camel.uri",
    PUSH_OL_CANDIDATES: "To-MF_PushOLCandidates-Route",
    PROCESS_OL_CANDIDATES: "To-MF_ProcessOLCandidates-Route"
};

const SYNC_POLICY = {
    ifNotExists: "CREATE",
    ifExists: "UPDATE_MERGE"
};

// ============================================================================
// Validation
// ============================================================================

function validateInput(purchaseOrders) {
    if (!Array.isArray(purchaseOrders)) {
        throw new Error("Input JSON must contain a 'd2m_po' array.");
    }

    purchaseOrders.forEach((po, index) => {
        if (!po.orderNumber) {
            throw new Error(`Purchase order at index ${index} missing orderNumber`);
        }
        if (!Array.isArray(po.Lines) || po.Lines.length < 0) {
            throw new Error(`Purchase order at index ${index} missing Lines array`);
        }
        if (po.Lines.some(line => isNaN(line.price) || isNaN(line.qty))) {
            throw new Error(`Purchase order at index ${index} has invalid numeric values`);
        }
    });
}

// ============================================================================
// Main Transformation
// ============================================================================

/**
 * Transforms a single purchase order into multiple service requests
 * @param {Object} po - Purchase order object
 * @returns {Array} Array of service route requests
 */
function transformPurchaseOrder(po) {
    const results = [];
    const context = buildTransformContext(po);

    // 1. Upsert drop-ship business partner if present
    if (po.dropShip) {
        results.push(createDropShipBPartnerRequest(po.dropShip, context));
    }

    // 2. Create order line candidates for product lines
    results.push(createOrderLineCandidatesRequest(po.Lines, context));

    // 3. Create order line candidates for charges
    if (po.charges?.length > 0) {
        results.push(createChargeLineCandidatesRequest(po.charges, context));
    }

    // 4. Trigger processing of all candidates
    results.push(createProcessCandidatesRequest(context));

    return results;
}

/**
 * Builds shared context for transformation
 * @param {Object} po - Purchase order
 * @returns {Object} Context object with common fields
 */
function buildTransformContext(po) {
    const firstLine = po.Lines[0];

    return {
        orgCode: po.orgCode ?? null,
        headerId: po.orderNumber ?? null,
        partnerIdentifier: buildIdentifier(po.partnerIdentifier ?? ""),
        dropShipIdentifier: buildIdentifier(po.dropShip?.partnerID ?? ""),
        poReference: po.orderDocumentNo,
        dateOrdered: po.dateOrdered ?? firstLine.dateOrdered ?? null,
        dateRequired: po.dateRequired ?? firstLine.dateRequired ?? null
    };
}

/**
 * Builds external identifier with prefix
 * @param {string} id - Raw identifier
 * @returns {string} Prefixed identifier
 */
function buildIdentifier(id) {
    return `${CONFIG.EXT_PREFIX}${id}`;
}

// ============================================================================
// Request Builders
// ============================================================================

/**
 * Creates drop-ship business partner upsert request
 */
function createDropShipBPartnerRequest(dropShip, context) {
    const body = {
        orgCode: context.orgCode,
        jsonRequestBPartnerUpsert: {
            requestItems: [
                {
                    bpartnerIdentifier: context.dropShipIdentifier,
                    bpartnerComposite: {
                        orgCode: context.orgCode,
                        bpartner: {
                            code: dropShip.partnerValue,
                            name: dropShip.partnerName,
                            companyName: dropShip.partnerName
                        },
                        locations: {
                            requestItems: [
                                {
                                    locationIdentifier: context.dropShipIdentifier,
                                    location: {
                                        address1: dropShip.address1,
                                        address2: dropShip.address2,
                                        address3: dropShip.address3,
                                        address4: dropShip.address4,
                                        postal: dropShip.postal,
                                        city: dropShip.city,
                                        countryCode: dropShip.country
                                    }
                                }
                            ]
                        }
                    }
                }
            ],
            syncAdvise: SYNC_POLICY
        }
    };

    return createServiceRequest(ROUTES.UPSERT_BPARTNER, body);
}

/**
 * Creates order candidates request for product lines
 */
function createOrderLineCandidatesRequest(lines, context) {
    const requestLines = lines.map(line => ({
        orgCode: context.orgCode,
        externalHeaderId: context.headerId,
        externalLineId: line.orderLineId,
        externalSystemCode: CONFIG.EXT_SYSTEM_CODE,
        bpartner: buildBPartnerReference(context.partnerIdentifier),
        dropShipBPartner: buildBPartnerReference(context.dropShipIdentifier),
        poReference: context.poReference,
        dateOrdered: context.dateOrdered,
        dateRequired: context.dateRequired,
        productIdentifier: `val-${line.productIdentifier}`,
        currencyCode: CONFIG.CURRENCY_CODE,
        isManualPrice: true,
        qty: line.qty,
        price: line.price,
        discount: line.discount
    }));

    return createServiceRequest(ROUTES.PUSH_OL_CANDIDATES, { requests: requestLines });
}

/**
 * Creates order candidates request for charges
 */
function createChargeLineCandidatesRequest(charges, context) {
    const requestCharges = charges.map(charge => ({
        orgCode: context.orgCode,
        externalHeaderId: context.headerId,
        externalLineId: `${context.headerId}_${charge.chargeIdentifier}`,
        externalSystemCode: CONFIG.EXT_SYSTEM_CODE,
        bpartner: buildBPartnerReference(context.partnerIdentifier),
        dropShipBPartner: buildBPartnerReference(context.dropShipIdentifier),
        poReference: context.poReference,
        dateOrdered: context.dateOrdered,
        dateRequired: context.dateRequired,
        productIdentifier: buildIdentifier(charge.chargeIdentifier),
        currencyCode: CONFIG.CURRENCY_CODE,
        isManualPrice: true,
        qty: 1,
        price: charge.price
    }));

    return createServiceRequest(ROUTES.PUSH_OL_CANDIDATES, { requests: requestCharges });
}

/**
 * Creates process candidates trigger request
 */
function createProcessCandidatesRequest(context) {
    const body = {
        externalHeaderId: context.headerId,
        externalSystemCode: CONFIG.EXT_SYSTEM_CODE,
        ship: false,
        invoice: false,
        closeOrder: false
    };

    return createServiceRequest(ROUTES.PROCESS_OL_CANDIDATES, body);
}

// ============================================================================
// Helper Functions
// ============================================================================

/**
 * Builds business partner reference object
 */
function buildBPartnerReference(identifier) {
    return {
        bpartnerIdentifier: identifier,
        bpartnerLocationIdentifier: identifier
    };
}

/**
 * Creates a service request object
 */
function createServiceRequest(routeId, body) {
    return {
        camelServiceRouteID: routeId,
        requestBody: JSON.stringify(body)
    };
}