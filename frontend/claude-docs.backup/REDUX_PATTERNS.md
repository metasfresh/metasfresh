# Redux Patterns

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **State management patterns for metasfresh frontend**

## Key Principle: immutability-helper

**The codebase uses `immutability-helper` library, NOT spread operators.**

```javascript
import update from 'immutability-helper';
```

## Redux Action Pattern

**IMPORTANT**: The codebase uses **plain action creator functions**, not `redux-actions` library (except for PluginActions).

### Standard Action Creators

```javascript
// actions/WindowActions.js
import {
  TOGGLE_OVERLAY,
  OPEN_RAW_MODAL,
  CLOSE_RAW_MODAL
} from '../constants/ActionTypes';

// Simple synchronous action
export function toggleOverlay(data) {
  return {
    type: TOGGLE_OVERLAY,
    data: data,
  };
}

export function closeRawModal() {
  return {
    type: CLOSE_RAW_MODAL,
  };
}

// Async action (with Thunk)
export function openModal(windowId, viewId) {
  return (dispatch) => {
    dispatch({ type: 'MODAL_OPEN_REQUEST' });

    return axios.get(`/api/window/${windowId}/${viewId}`)
      .then(response => {
        dispatch({
          type: 'MODAL_OPEN_SUCCESS',
          payload: response.data
        });
      })
      .catch(error => {
        dispatch({
          type: 'MODAL_OPEN_ERROR',
          error: error.message
        });
      });
  };
}
```

### Dispatching Actions

```javascript
import { useDispatch } from 'react-redux';
import { toggleOverlay, closeRawModal } from '../actions/WindowActions';

function MyComponent() {
  const dispatch = useDispatch();

  const handleOpen = () => {
    dispatch(toggleOverlay({ visible: true }));
  };

  const handleClose = () => {
    dispatch(closeRawModal());
  };
}
```

## Redux Reducer Pattern

### Using immutability-helper

```javascript
import update from 'immutability-helper';
import { createSelector } from 'reselect';

import {
  INIT_LAYOUT_SUCCESS,
  UPDATE_MASTER_DATA,
  TOGGLE_OVERLAY,
  CLEAR_MASTER_DATA
} from '../constants/ActionTypes';

const initialState = {
  showSpinner: false,
  master: {
    layout: null,
    data: [],
    saveStatus: {},
    validStatus: {},
  },
  overlay: {
    visible: false,
    data: null,
  },
};

export default function windowHandler(state = initialState, action) {
  switch (action.type) {
    case INIT_LAYOUT_SUCCESS:
      return update(state, {
        master: {
          layout: { $set: action.payload },
        },
      });

    case UPDATE_MASTER_DATA:
      return update(state, {
        master: {
          data: { $merge: action.payload },
        },
      });

    case TOGGLE_OVERLAY:
      return update(state, {
        overlay: {
          visible: { $set: action.data.visible },
          data: { $set: action.data },
        },
      });

    case CLEAR_MASTER_DATA:
      return update(state, {
        master: { $set: initialState.master },
      });

    default:
      return state;
  }
}
```

### immutability-helper Operations

| Operation | Purpose | Example |
|-----------|---------|---------|
| `$set` | Replace value | `{ field: { $set: newValue } }` |
| `$merge` | Merge object | `{ data: { $merge: newData } }` |
| `$push` | Append to array | `{ items: { $push: [newItem] } }` |
| `$unshift` | Prepend to array | `{ items: { $unshift: [newItem] } }` |
| `$splice` | Splice array | `{ items: { $splice: [[index, 1]] } }` |
| `$apply` | Apply function | `{ count: { $apply: x => x + 1 } }` |

## Selector Pattern (Reselect)

```javascript
import { createSelector } from 'reselect';
import { createCachedSelector } from 're-reselect';

// Simple selector
const getWindowState = state => state.windowHandler;

// Memoized selector
export const getWindowLayout = createSelector(
  [getWindowState],
  windowState => windowState.master.layout
);

// Cached selector with cache key (for dynamic parameters)
export const getWindowFieldById = createCachedSelector(
  [getWindowLayout, (state, fieldId) => fieldId],
  (layout, fieldId) => layout?.fields?.find(f => f.id === fieldId)
)(
  (state, fieldId) => fieldId // cache key function
);
```

### Usage in Components

```javascript
import { useSelector } from 'react-redux';
import { getWindowLayout, getWindowFieldById } from '../reducers/windowHandler';

function MyComponent() {
  const layout = useSelector(getWindowLayout);
  const field = useSelector(state => getWindowFieldById(state, 'fieldId123'));
}
```

## State Management Best Practices

1. **Keep Redux state normalized** - Avoid deep nesting
2. **Use selectors** - Memoize with Reselect for performance
3. **Use immutability-helper** - Preferred pattern in this codebase
4. **Don't store derived data** - Calculate in selectors
5. **Async logic in actions** - Use Thunk middleware

## When Working with Reducers

1. Always use `immutability-helper` (not spread operators)
2. Common operations: `$set`, `$merge`, `$push`, `$splice`
3. Import from: `import update from 'immutability-helper'`
4. Test state updates with Redux DevTools
