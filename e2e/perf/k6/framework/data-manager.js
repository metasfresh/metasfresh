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
import { generateBPartnerUpsert } from './data-generators.js';

export class DataManager {
  constructor({ bpartnerFiles = [] } = {}) {
    this.existingBPartnerUpserts = [];
    for (const filePath of bpartnerFiles) {
      const arr = new SharedArray(`bp-${filePath}`, () => {
        try {
          const data = JSON.parse(open(filePath));
          return Array.isArray(data) ? data : [];
        } catch (e) {
          return [];
        }
      });
      this.existingBPartnerUpserts.push(...arr);
    }
  }

  getBPartnerUpsert(strategy = 'mixed', existingRatio = 0.7) {
    const useExisting =
      strategy === 'existing' || (strategy === 'mixed' && Math.random() < existingRatio);

    const base =
      useExisting && this.existingBPartnerUpserts.length > 0
        ? deepClone(randomItem(this.existingBPartnerUpserts))
        : generateBPartnerUpsert();

    return this._makeUniqueIdentifiers(base);
  }

  extractBPartnerIdentifier(upsertPayload) {
    try {
      return upsertPayload.requestItems?.[0]?.bpartnerIdentifier || null;
    } catch (e) {
      return null;
    }
  }

  _makeUniqueIdentifiers(payload) {
    const uniqueSuffix = `vu${__VU}-it${__ITER}-${Date.now()}`;
    const result = deepClone(payload);

    if (Array.isArray(result.requestItems)) {
      for (const item of result.requestItems) {
        if (item.bpartnerIdentifier) {
          item.bpartnerIdentifier = ensureSuffix(item.bpartnerIdentifier, uniqueSuffix);
        }
        if (Array.isArray(item.locations)) {
          for (const loc of item.locations) {
            if (loc.locationIdentifier) {
              loc.locationIdentifier = ensureSuffix(loc.locationIdentifier, uniqueSuffix);
            }
          }
        }
        if (Array.isArray(item.contacts)) {
          for (const ct of item.contacts) {
            if (ct.contactIdentifier) {
              ct.contactIdentifier = ensureSuffix(ct.contactIdentifier, uniqueSuffix);
            }
            if (ct.email) {
              const [name, domain] = String(ct.email).split('@');
              ct.email = `${name}+${uniqueSuffix}@${domain || 'example.com'}`;
            }
          }
        }
      }
    }
    return result;
  }
}

function deepClone(obj) {
  return JSON.parse(JSON.stringify(obj));
}

function ensureSuffix(value, suffix) {
  if (!value) return value;
  // Avoid doubling the same suffix if the source data already had one
  return `${String(value).replace(/-vu\d+-it\d+-\d+$/, '')}-${suffix}`;
}
