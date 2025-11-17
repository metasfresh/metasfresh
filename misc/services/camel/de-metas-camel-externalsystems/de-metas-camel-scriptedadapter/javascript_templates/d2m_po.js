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

        if (!Array.isArray(input.d2m_po)) {
            throw new Error("Input JSON must contain a 'd2m_po' array.");
        }

        const results = [];
        input.d2m_po.forEach(po => {
            if (Array.isArray(po.dropShip)) {

                po.dropShip.forEach(
                    ds => {
                        const dropShipBody = {
                            orgCode: po.orgCode,
                            jsonRequestBPartnerUpsert: {
                                requestItems: [
                                    {
                                        bpartnerIdentifier: `ext-Other-${ds.partnerName}`,
                                        bpartnerComposite: {
                                            bpartner: {
                                                code: ds.partnerValue,
                                                name: ds.partnerName,
                                                companyName: ds.partnerName
                                            },
                                            locations: {
                                                requestItems: [
                                                    {
                                                        locationIdentifier: `ext-Other-${ds.partnerName}`,
                                                        location: {
                                                            address1: ds.address1,
                                                            address2: ds.address2,
                                                            address3: ds.address3,
                                                            address4: ds.address4,
                                                            postal: ds.postal,
                                                            city: ds.city,
                                                            countryCode: ds.country
                                                        }
                                                    }
                                                ]
                                            }
                                        }
                                    }
                                ],
                                syncAdvise: {
                                    ifNotExists: "CREATE",
                                    ifExists: "UPDATE_MERGE"
                                }
                            }
                        };

                        results.push({
                            camelServiceRouteID: "metasfresh.upsert-bpartner-v2.camel.uri",
                            requestBody: JSON.stringify(dropShipBody)
                        });
                    }
                )
            }

            if (Array.isArray(po.Lines)) {

                const requestsArray = po.Lines.map(line => {
                    return {
                        orgCode: po.orgCode,
                        externalHeaderId: po.orderNumber,
                        externalLineId: line.orderLineId,
                        externalSystemCode: `Other`,
                        bpartner: {
                            bpartnerIdentifier: `ext-Other-${po.partnerIdentifier}`,
                            bpartnerLocationIdentifier: `ext-Other-${po.partnerIdentifier}`
                        },
                        dropShipBPartner: {
                            bpartnerIdentifier: `ext-Other-${po.dropShip[0].partnerName}`,
                            bpartnerLocationIdentifier: `ext-Other-${po.dropShip[0].partnerName}`
                        },
                        poReference: po.orderDocumentNo,
                        dateOrdered: line.dateOrdered,
                        dateRequired: line.dateRequired,
                        productIdentifier: `ext-Other-${line.productIdentifier}`,
                        currencyCode: `EUR`,
                        isManualPrice: true,
                        qty: line.qty,
                        price: line.price,
                        discount: line.discount
                    };
                });

                const finalBody = {
                    requests: requestsArray
                };

                results.push({
                    camelServiceRouteID: "To-MF_PushOLCandidates-Route",
                    requestBody: JSON.stringify(finalBody)
                });
            }
        });

        return JSON.stringify(results);

    } catch (err) {
        return JSON.stringify({
            error: "Invalid input",
            details: err.message
        });
    }
}

