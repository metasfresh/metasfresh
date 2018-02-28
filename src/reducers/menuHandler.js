import * as types from '../constants/MenuTypes';

const initialState = {
  breadcrumb: [],
};

export default function menuHandler(state = initialState, action) {
  switch (action.type) {
    case types.SET_BREADCRUMB: {
      return { ...state, breadcrumb: action.breadcrumb };
    }

    case types.UPDATE_BREADCRUMB: {
      return {
        ...state,
        breadcrumb: state.breadcrumb.map(
          node =>
            node.nodeId === action.node.nodeId
              ? { ...node, children: action.node }
              : node
        ),
      };
    }

    case types.SET_GLOBAL_GRID_FILTER: {
      return { ...state, globalGridFilter: action.filter };
    }

    default: {
      return state;
    }
  }
}
