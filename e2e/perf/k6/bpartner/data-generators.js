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

import { randomIntBetween, randomItem } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

const cities = ['Berlin', 'Munich', 'Hamburg', 'Frankfurt', 'Cologne', 'Stuttgart', 'Düsseldorf'];
const countries = ['DE', 'AT', 'CH', 'NL', 'FR', 'IT'];

export function generateBPartnerUpsert() {
  const idSuffix = `${Math.random().toString(36).substring(2, 10)}`;
  return {
    requestItems: [
      {
        bpartnerIdentifier: `ext-Shopware6-${idSuffix}`,
        externalReferenceUrl: 'www.ExternalReferenceURL.com',
        bpartnerComposite: {
          bpartner: {
            code: `test_code1-${idSuffix}`,
            name: `test_name-${idSuffix}`,
            companyName: `test_company-${idSuffix}`,
            parentId: null,
            phone: null,
            language: 'de',
            url: null,
            group: 'test-group',
            vatId: `vatId_BPartner001-${idSuffix}`
          },
          locations: {
            requestItems: [
              {
                locationIdentifier: `gln-l11-${idSuffix}`,
                location: {
                  address1: `${randomIntBetween(1, 999)} Sample Street`,
                  address2: 'test_address2',
                  poBox: null,
                  district: null,
                  region: null,
                  city: randomItem(cities),
                  countryCode: randomItem(countries),
                  gln: null,
                  postal: null,
                  vatId: null
                }
              },
              {
                locationIdentifier: `gln-l22-${idSuffix}`,
                location: {
                  address1: null,
                  address2: 'test_address2',
                  poBox: 'test_poBox',
                  district: null,
                  region: 'test_region',
                  city: randomItem(cities),
                  countryCode: randomItem(countries),
                  gln: null,
                  postal: null,
                  vatId: 'vatId_Location_l22'
                }
              }
            ]
          },
          contacts: {
            requestItems: [
              {
                contactIdentifier: `ext-Shopware6-c11-${idSuffix}`,
                contact: {
                  code: 'c11',
                  name: 'test_name_c11-${idSuffix}',
                  email: `jane.doe+${idSuffix}@example.com`,
                  fax: 'fax',
                  invoiceEmailEnabled: false,
                  greeting: {
                    greetingInfo: {
                      greeting: 'test_greeting_2025',
                      letterSalutation: 'test_salutation_2025',
                      name: 'test_name_2025'
                    },
                    identifier: 'ext-Shopware6-greetingTest261023'
                  }
                }
              },
              {
                contactIdentifier: `ext-Shopware6-c22-${idSuffix}`,
                contact: {
                  code: 'c22',
                  name: 'test_name_c22',
                  email: null,
                  fax: 'test_fax',
                  invoiceEmailEnabled: true
                }
              }
            ]
          }
        }
      }
    ],
    syncAdvise: {
      ifNotExists: 'CREATE',
      ifExists: 'UPDATE_MERGE'
    }
  };
}
