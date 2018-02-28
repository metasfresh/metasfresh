import * as types from '../constants/ListTypes';

export function setListId(viewId, windowType) {
  return {
    type: types.SET_LIST_ID,
    viewId,
    windowType,
  };
}

export function setFilter(filter, windowType) {
  return {
    type: types.SET_LIST_FILTERS,
    filter,
    windowType,
  };
}

export function setSorting(sort, windowType) {
  return {
    type: types.SET_LIST_SORTING,
    sort,
    windowType,
  };
}

export function setPagination(page, windowType) {
  return {
    type: types.SET_LIST_PAGINATION,
    page,
    windowType,
  };
}

export function setListIncludedView(
  { windowType, viewId, viewProfileId = null } = {}
) {
  return {
    type: types.SET_LIST_INCLUDED_VIEW,
    payload: { windowType, viewId, viewProfileId },
  };
}

export function closeListIncludedView(
  { windowType, viewId, forceClose = false } = {}
) {
  return {
    type: types.CLOSE_LIST_INCLUDED_VIEW,
    payload: { windowType, viewId, forceClose },
  };
}
