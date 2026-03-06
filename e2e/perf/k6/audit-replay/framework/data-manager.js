/*
 * #%L
 * master
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

import { SharedArray } from 'k6/data';
import { randomItem } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

/**
 * DataManager for API Audit Replay
 * Loads and manages audit request data from extracted JSON files
 */
export class DataManager {
  constructor({ auditDataFile }) {
    if (!auditDataFile) {
      throw new Error('auditDataFile is required');
    }

    // Load audit data
    this.auditRequests = new SharedArray('audit-requests', () => {
      try {
        const data = JSON.parse(open(auditDataFile));
        return data.requests || [];
      } catch (e) {
        console.error(`Failed to load audit data from ${auditDataFile}: ${e.message}`);
        return [];
      }
    });
    this.auditIndex = 0;
  }

  /**
   * Get the next audit request for replay
   * @param {string} mode - 'sequential' or 'random'
   * @returns {Object|null} - Audit request object or null if no data
   */
  getNextAuditRequest(mode = 'sequential') {
    if (!this.auditRequests || this.auditRequests.length === 0) {
      return null;
    }

    if (mode === 'random') {
      return deepClone(randomItem(this.auditRequests));
    }

    // Sequential mode
    const request = this.auditRequests[this.auditIndex % this.auditRequests.length];
    this.auditIndex++;
    return deepClone(request);
  }

  /**
   * Get total count of loaded audit requests
   * @returns {number}
   */
  getAuditRequestCount() {
    return this.auditRequests ? this.auditRequests.length : 0;
  }

  /**
   * Filter audit requests by method
   * @param {string} method - HTTP method (GET, POST, PUT, DELETE, PATCH)
   * @returns {Array} - Filtered audit requests
   */
  getAuditRequestsByMethod(method) {
    if (!this.auditRequests) return [];
    return this.auditRequests.filter(req => req.method === method);
  }

  /**
   * Filter audit requests by path pattern
   * @param {RegExp|string} pattern - Path pattern to match
   * @returns {Array} - Filtered audit requests
   */
  getAuditRequestsByPath(pattern) {
    if (!this.auditRequests) return [];
    const regex = pattern instanceof RegExp ? pattern : new RegExp(pattern);
    return this.auditRequests.filter(req => regex.test(req.path));
  }
}

function deepClone(obj) {
  return JSON.parse(JSON.stringify(obj));
}
