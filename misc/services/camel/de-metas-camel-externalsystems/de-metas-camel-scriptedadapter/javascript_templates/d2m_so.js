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

function transform(messageToMetasfresh) {
    try {
        const input = JSON.parse(messageToMetasfresh);

        if (!Array.isArray(input.d2m_so)) {
            throw new Error("Input JSON must contain a 'd2m_so' array.");
        }

        const results = [];
        input.d2m_so.forEach(so => {
                if (Array.isArray(so.lines)) {

                    const requestsArray = so.lines.map(line => {
                        return {
                            orgCode: so.orgCode,
                            externalHeaderId: so.orderNumber,
                            poReference: so.orderDocumentNo,
                            externalLineId: line.orderLineId,
                            isManualPrice: true,
                            price: {
                                value: line.price,
                                currencyCode: so.currency,
                                priceUomCode: line.uom
                            },
                            isManualDiscount: true,
                            discount: line.discount,
                            purchaseDatePromised: toZonedDateTime(so.datePromised),
                            purchaseDateOrdered: toZonedDateTime(so.dateOrdered),
                            vendor: {
                                bpartnerIdentifier: `ext-Other-${so.partnerIdentifier}`,
                                locationIdentifier: `ext-Other-${so.partnerIdentifier}`
                            },
                            warehouseIdentifier: `ext-Other-Hochregal`,
                            productIdentifier: `ext-Other-${line.productIdentifier}`,
                            qty: {
                                qty: line.qty,
                                uomCode: line.uom
                            }
                        };
                    });

                    const purchaseCandidateRequest = {
                        purchaseCandidates: requestsArray
                    };

                    const finalBody = {
                        jsonPurchaseCandidateCreateRequest: purchaseCandidateRequest
                    };

                    results.push({
                        camelServiceRouteID: "metasfresh.create-purchase-candidate-v2.camel.uri",
                        requestBody: JSON.stringify(finalBody)
                    });
                }
            }
        )
        ;

        return JSON.stringify(results);

    } catch
        (err) {
        return JSON.stringify({
            error: "Invalid input",
            details: err.message
        });
    }
}

function toZonedDateTime(dateStr) {
    return `${dateStr}T00:00:00Z`;
}