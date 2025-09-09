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
 * @file This file contains the JavaScript transformation logic for intercompany business objects that are sent *towards* metasfresh.
 *       Developers should implement the 'transform' function according to the specified
 *       JSDoc types for input and output.
 */

/**
 * @typedef {object} ExternalSystemsToMetasfreshCall
 * @property {string} camelServiceRouteID - The ID of the camel-route to invoke (e.g., "To-MF_PushOLCandidates-Route").
 * @property {object} requestBody - The request body as a plain JavaScript object,
 *                                  which will be serialized to JSON for service-route.
 *                                  It needs to be compatible with the respective java-POJOs that the camel-route (and the metasfresh-API!) expects.
 */

/**
 * @typedef {ExternalSystemsToMetasfreshCall[]} ApiCallList
 * A list of API calls to be executed by the platform.
 * Each element in the array must conform to the ApiCall type.
 */

/**
 * Transforms an incoming JSON business object into a list of API calls.
 *
 * This function parses the incoming JSON string and, based on its content,
 * computes the necessary API endpoints and corresponding request bodies.
 *
 * @param {string} messageToMetasfresh - The incoming business object as a JSON string.
 *                                Example: '{"orderId": "ORD-001", "items": [{"productId": "P001", "quantity": 10}]}'
 * @returns {ApiCallList} - An array of `ApiCall` objects, each specifying
 *                          an endpoint and its associated request body.
 *                          Example:
 *                          [
 *                              {
 *                                  "camelServiceRouteID": "To-MF_PushOLCandidates-Route",
 *                                  "requestBody": {
 *                                      "orgCode": "001",
 *                                      "externalLineId": "1555",
 *                                      "externalHeaderId": "1444",
 *                                      "dataSource": "int-Shopware",
 *                                      "bpartner": {
 *                                          "bpartnerIdentifier": "2156425",
 *                                          "bpartnerLocationIdentifier": "2205175",
 *                                          "contactIdentifier": "2188224"
 *                                      },
 *                                      "dateRequired": "2021-08-20",
 *                                      "dateOrdered": "2021-07-20",
 *                                      "orderDocType": "SalesOrder",
 *                                      "paymentTerm": "val-1000002",
 *                                      "productIdentifier": 2005577,
 *                                      "qty": 10,
 *                                      "price": 5,
 *                                      "currencyCode": "EUR",
 *                                      "discount": 0,
 *                                      "poReference": "po_ref_mock",
 *                                      "deliveryViaRule": "S",
 *                                      "deliveryRule": "F",
 *                                      "bpartnerName": "testName"
 *                                  }
 *                              },
 *                              {
 *                                  "camelServiceRouteID": "To-MF_ProcessOLCandidates-Route",
 *                                  "requestBody":{
 *                                    "externalHeaderId": "1444",
 *                                    "closeOrder": false,
 *                                    "invoice": false,
 *                                    "ship": false
 *                                  }
 *                              }
 *                          ]
 */
function transform(messageToMetasfresh) {
        // --- START: Your transformation logic here ---

    // Parse the incoming JSON string into a JavaScript object.
    // It's good practice to wrap this in a try-catch block for robustness in production,
    // but for a simple template, we'll assume valid JSON input.
    const inputData = JSON.parse(messageToMetasfresh);

    // Initialize an empty array to hold the generated API calls.
    const apiCalls = [];

    //
    // Implement your business logic here.
    // Use the 'inputData' object to determine what API calls need to be made
    // and what their request bodies should look like.
    //
    // Example: If 'inputData' contains an order, you might create an API call
    // to create that order in an external system.
    //
    if (inputData && inputData.orderId) {
        apiCalls.push({
            endpoint: `/api/v2/orders/sales/candidates`,
            requestBody: {
                // You would map properties from 'inputData' to your API's expected request body
                externalHeaderId: inputData.orderId
            }
        });

        
    } else {
        // Handle cases where the incoming JSON might not be in the expected format
        // Or if no API calls are needed for certain input types.
        console.warn("Incoming JSON did not contain an 'orderId'. No API calls generated for this input.", messageToMetasfresh);
    }

    // --- END: Your transformation logic here ---

    // The function must return an array of ApiCall objects.
    return apiCalls;
}
