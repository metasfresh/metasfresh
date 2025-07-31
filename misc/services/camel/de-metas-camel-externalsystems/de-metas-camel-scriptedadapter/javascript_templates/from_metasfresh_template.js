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
 * @file This file contains the JavaScript transformation logic for intercompany business objects that are by metasfresh.
 *       Developers should implement the 'transform' function according to the specified
 *       JSDoc types for input and output.
 */

/**
 * @typedef {string} OutBoundMessage
 */

/**
 * Transforms an incoming JSON business object into a list of API calls.
 *
 * This function parses the incoming JSON string and, based on its content,
 * computes the necessary API endpoints and corresponding request bodies.
 *
 * @param {string} messageFromMetasfresh - The incoming business object as a JSON string.
 * @returns {OutBoundMessage} - the string that is forwarded to the external system
 */
function transform(messageFromMetasfresh) {
    // --- START: Your transformation logic here ---

    // Parse the incoming JSON string into a JavaScript object.
    // It's good practice to wrap this in a try-catch block for robustness in production,
    // but for a simple template, we'll assume valid JSON input.
    const inputData = JSON.parse(messageFromMetasfresh);

    var sum = inputData.numbers.reduce(function(acc, num) { return acc + num; }, 0);
    var result = {
        originalArray: inputData.numbers,
        sum: sum,
        count: inputData.numbers.length,
        average: sum / inputData.numbers.length
    };
    
    // --- END: Your transformation logic here ---

    // The function must return a string
    return JSON.stringify(result);
}
