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

import http from 'k6/http';

export function createHttpClient({ baseUrl, authToken, timeoutMs = 60000 }) {
  const defaultHeaders = {
    'Content-Type': 'application/json;charset=UTF-8',
    'Accept': 'application/json',
  };
  if (authToken) {
    defaultHeaders['Authorization'] = `${authToken}`;
  }

  function buildUrl(path) {
    if (!path.startsWith('/')) {
      path = `/${path}`;
    }
    return `${baseUrl}${path}`;
  }

  return {
    get(path, { headers = {}, timeout = `${timeoutMs}ms` } = {}) {
      return http.get(buildUrl(path), {
        headers: { ...defaultHeaders, ...headers },
        timeout,
      });
    },
    putJson(path, body, { headers = {}, timeout = `${timeoutMs}ms` } = {}) {
      const payload = typeof body === 'string' ? body : JSON.stringify(body);
      return http.put(buildUrl(path), payload, {
        headers: { ...defaultHeaders, ...headers },
        timeout,
      });
    },
    postJson(path, body, { headers = {}, timeout = `${timeoutMs}ms` } = {}) {
      const payload = typeof body === 'string' ? body : JSON.stringify(body);
      return http.post(buildUrl(path), payload, {
        headers: { ...defaultHeaders, ...headers },
        timeout,
      });
    },
  };
}
