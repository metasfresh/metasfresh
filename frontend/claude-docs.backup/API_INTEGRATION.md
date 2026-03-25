# API Integration

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **REST API and WebSocket patterns**

## REST API Pattern

**API modules are in `api/` folder** - Centralize all endpoints here.

### API Module Structure

```javascript
// api/window.js
import { get, post, patch, delete as del } from 'axios';

// Uses global config object
export function topActionsRequest(windowId, documentId, tabId = null) {
  const url = tabId == null
    ? `${config.API_URL}/window/${windowId}/${documentId}/topActions`
    : `${config.API_URL}/window/${windowId}/${documentId}/${tabId}/topActions`;

  return get(url);
}

export function deleteDocument({ windowId, documentId }) {
  return deleteRequest('window', windowId, null, null, [documentId]);
}

export function deleteRequest(entity, docType, docId, tabId, ids) {
  return del(
    `${config.API_URL}/${entity}${docType ? `/${docType}` : ''}${
      docId ? `/${docId}` : ''
    }${tabId ? `/${tabId}` : ''}${ids ? `?ids=${ids}` : ''}`
  );
}

export function getTabLayoutRequest(windowId, tabId, isAdvanced = false) {
  const queryParams = {};
  if (isAdvanced) {
    queryParams.advanced = true;
  }
  const queryParamsString = getQueryString(queryParams);

  return get(
    `${config.API_URL}/window/${windowId}${tabId ? `/${tabId}` : ''}/layout${
      queryParamsString ? `?${queryParamsString}` : ''
    }`
  ).then(({ data }) => data); // unbox
}
```

### Usage in Actions

```javascript
import * as windowApi from '../api/window';

export const loadDocument = (windowId, documentId) => (dispatch) => {
  return windowApi.topActionsRequest(windowId, documentId)
    .then(response => {
      dispatch({ type: 'DOCUMENT_LOADED', payload: response.data });
    })
    .catch(error => {
      dispatch({ type: 'DOCUMENT_ERROR', error });
    });
};
```

## Configuration (config.js)

```javascript
// config.js (from config.js.dist)
const config = {
  API_URL: 'http://localhost:8080/rest/api',  // Note: includes /rest/api path
  WS_URL: 'http://localhost:8080/stomp',
  GOOGLE_API: '',
};
```

**Important Notes:**
- `config.js` is git-ignored. Always use `config.js.dist` as template.
- `API_URL` includes `/rest/api` path - this is required
- `WS_URL` is explicitly configured, not derived from API_URL

## WebSocket (STOMP) Pattern

```javascript
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const client = new Client({
  webSocketFactory: () => new SockJS(config.WS_URL),
  onConnect: () => {
    console.log('WebSocket connected');

    client.subscribe('/topic/notifications', (message) => {
      const notification = JSON.parse(message.body);
      dispatch(addNotification(notification));
    });
  },
  onDisconnect: () => {
    console.log('WebSocket disconnected');
  }
});

client.activate();
```

## Authentication Flow

1. User submits credentials to `/login`
2. Backend returns JWT token
3. Token stored in Redux state + localStorage
4. All subsequent API calls include token: `Authorization: Bearer {token}`

**Implementation**: See `src/hooks/useAuth.js` and `src/api/login.js`

## Common Endpoints

**Base URL**: Configured in `config.js` (default: `http://localhost:8080/rest/api`)

| Endpoint | Purpose |
|----------|---------|
| `/window/{windowId}/layout` | Window metadata |
| `/window/{windowId}/{documentId}` | Document data |
| `/window/{windowId}/new` | Create document |
| `/documentView/{viewId}` | View/list data |
| `/process/{processId}` | Execute process |
| `/login` | Authentication |

## API Integration Best Practices

1. **Centralize API calls** - Keep all endpoints in `api/` folder
2. **Handle errors gracefully** - User-friendly error messages
3. **Loading states** - Show spinners during fetches
4. **Cache when appropriate** - Redux state acts as cache
5. **Use global config object** - `config.API_URL` for all endpoints

## HTTP Client: Axios

```javascript
import axios from 'axios';

// GET request
axios.get(`${config.API_URL}/endpoint`)
  .then(response => response.data);

// POST request
axios.post(`${config.API_URL}/endpoint`, payload)
  .then(response => response.data);

// Error handling
axios.get(url)
  .catch(error => {
    if (error.response) {
      // Server responded with error status
      console.error('Status:', error.response.status);
    } else if (error.request) {
      // Request made but no response
      console.error('No response received');
    } else {
      // Request setup error
      console.error('Error:', error.message);
    }
  });
```
