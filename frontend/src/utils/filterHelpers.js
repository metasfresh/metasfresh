import Moment from 'moment';

import { DATE_FIELD_TYPES, DATE_FORMAT } from '../constants/Constants';
import { deepUnfreeze } from './index';
import { fieldValueToString } from './tableHelpers';
import { getFormatForDateField, getFormattedDate } from './widgetHelpers';

function formatFilterParameter(filterParameter, filterData) {
  const { parameterName, value, valueTo } = filterParameter;
  const dataFilterParameter = filterData.parameters.find(
    (param) => param.parameterName === parameterName
  );
  const { widgetType } = dataFilterParameter;

  if (DATE_FIELD_TYPES.includes(widgetType)) {
    const dateFormat = getFormatForDateField(widgetType);
    const date = getFormattedDate(value, dateFormat);
    const dateTo = getFormattedDate(valueTo, dateFormat);

    filterParameter = {
      parameterName,
      value: date,
      valueTo: dateTo,
    };
  }

  return filterParameter;
}

/**
 * @method formatFilters
 * @summary This function exists due to the fact, that initial date filters have
 * a data format incompatible with the API, so we have to transform them on init
 *
 * @param {array} filtersData - filters data that comes with the layout
 * @param {array} filtersActive - active filters array
 */
export function formatFilters({ filtersData, filtersActive = [] }) {
  // for inline filters (if they were modified) in the response data we're getting a filter with
  // empty parameters
  return filtersActive.map((filter) => {
    const filterData = getParentFilterFromFilterData({
      filterId: filter.filterId,
      filterData: filtersData,
    });

    if (filter.parameters && filter.parameters.length) {
      filter.parameters = filter.parameters.map((parameter) =>
        formatFilterParameter(parameter, filterData)
      );
    }

    return filter;
  });
}

/**
 * @method getParentFilterFromFilterData
 * @summary as the name suggests the function is retrieving the filter data by key from the filterData
 * @param {string} filterId - key identifying the filter
 * @param {array} filterData array that contains all the filters as they are retrieved from the BE
 */
export function getParentFilterFromFilterData({ filterId, filterData }) {
  let parentFilter = {};

  filterData &&
    filterData.forEach((filter) => {
      if (filter.filterId && filter.filterId === filterId) {
        parentFilter = filter;
      }
      if (filter.includedFilters) {
        filter.includedFilters.forEach((incFilter) => {
          if (incFilter.filterId && incFilter.filterId === filterId) {
            parentFilter = incFilter;
          }
        });
      }
    });
  return parentFilter;
}

/**
 * @method populateFiltersCaptions
 * @summary updates the filtersCaptions object for the corresponding filter branch id in the store
 */
export function populateFiltersCaptions(filters) {
  const filtersCaptions = {};
  if (!filters) return {};

  const { filterData, filtersActive } = filters;
  if (!filtersActive) return {};

  if (filtersActive.length) {
    const removeDefault = {};

    filtersActive.forEach((filter, filterIdx) => {
      const { filterId } = filter;
      let captionsArray = ['', ''];

      if (filter.parameters && filter.parameters.length) {
        filter.parameters.forEach((filterParameter) => {
          const { value, valueTo, parameterName, defaultValue } =
            filterParameter;

          if (!defaultValue && filterData) {
            // we don't want to show captions, nor show filter button as active for default values
            removeDefault[filterIdx] = true;
            const parentFilter = getParentFilterFromFilterData({
              filterId, // we pass the actual key not the index
              filterData,
            });

            const filterParameter = parentFilter.parameters.find(
              (param) => param.parameterName === parameterName
            );
            let captionName = filterParameter.caption;
            let itemCaption = filterParameter.caption;

            switch (filterParameter.widgetType) {
              case 'Text':
                captionName = value;

                if (!value) {
                  captionName = '';
                  itemCaption = '';
                }
                break;
              case 'Lookup':
              case 'List':
                captionName = value && value.caption;
                break;
              case 'Labels':
                captionName = value.values.reduce((caption, item) => {
                  return caption
                    ? `${caption}, ${item.caption}`
                    : `${item.caption}`;
                }, '');
                break;
              case 'YesNo':
                if (value === null) {
                  captionName = '';
                  itemCaption = '';
                } else if (value === false) {
                  // TODO: introduce AD_Process_Param.NameWhenNotSet and propagate it to FE
                  captionName = 'Not ' + filterParameter.caption;
                  itemCaption = captionName;
                }
                break;
              case 'Switch':
              default:
                if (!value && !valueTo) {
                  captionName = '';
                  itemCaption = '';
                }
                break;
            }

            if (captionName) {
              captionsArray[0] = captionsArray[0]
                ? `${captionsArray[0]}, ${captionName}`
                : captionName;
            }

            if (itemCaption) {
              captionsArray[1] = captionsArray[1]
                ? `${captionsArray[1]}, ${itemCaption}`
                : itemCaption;
            }
          }
        });
      } else {
        let originalFilter;

        filterData.forEach((filter) => {
          if (filter.filterId === filterId) {
            originalFilter = filter;
          } else if (filter.includedFilters) {
            filter.includedFilters.forEach((included) => {
              if (included.filterId === filterId) {
                originalFilter = included;
              }
            });
          }
        });

        captionsArray = [originalFilter.caption, originalFilter.caption];
      }

      if (captionsArray.join('').length) {
        filtersCaptions[filterId] = captionsArray;
        filtersCaptions[filterIdx] = captionsArray;
      }
    });
  }

  return filtersCaptions;
}

/**
 * @method setNewFiltersActive
 * @todo name is misleading. It suggests this is an action creator.
 * @summary returns a new array with filters that are going to be the active ones
 */
export function setNewFiltersActive({ storeActiveFilters, filterToAdd }) {
  storeActiveFilters = deepUnfreeze(storeActiveFilters);
  if (
    !storeActiveFilters.length ||
    !foundAmongActiveFilters({ storeActiveFilters, filterToAdd })
  ) {
    storeActiveFilters.push(filterToAdd);
  } else {
    storeActiveFilters.forEach((activeFilter, index) => {
      if (activeFilter.filterId === filterToAdd.filterId) {
        storeActiveFilters[index] = filterToAdd;
      }
    });
  }
  return storeActiveFilters;
}

/**
 * @method foundAmongActiveFilters
 * @summary checks that the filterToAdd is found among the active filters from the store -> storeActiveFilters
 * @param {array} storeActiveFilters is representing the active filters as they are found in the redux store
 * @param {object} filterToAdd - is the filter we are planing to add to the redux store within the active filters array
 * @returns {boolean} indicating the presence of the filterToAdd among the active filters
 */
function foundAmongActiveFilters({ storeActiveFilters, filterToAdd }) {
  let isPresent = false;
  storeActiveFilters.forEach((item) => {
    if (item.filterId === filterToAdd.filterId) isPresent = true;
  });
  return isPresent;
}

/**
 * @method filtersActiveContains
 * @summary returns a boolean value depending on the presence of the key withing the activeFilters passed array
 */
export function filtersActiveContains({ filtersActive, key }) {
  if (!filtersActive || filtersActive.length === 0) return false;
  const isPresent = filtersActive.filter((item) => item.filterId === key);

  return !!isPresent.length;
}

/**
 * @method isFilterActive
 * @summary Check within the active filters array if filterId given as param is active
 * @param {string} filterId
 * @param {array} activeFilter
 */
export function isFilterActive({ filterId, filtersActive }) {
  if (filtersActive) {
    // filters with only defaultValues shouldn't be set to active
    const active = filtersActive.find(
      (item) => item.filterId === filterId && !item.defaultVal
    );

    return typeof active !== 'undefined';
  }

  return false;
}

/**
 * @method annotateFilters
 * @summary Creates caption for active filters to show when the widget is closed
 * @param {array} unannotatedFilters
 * @param {array} filtersActive
 */
export function annotateFilters({ unannotatedFilters, filtersActive }) {
  filtersActive = filtersActive ? filtersActive : [];

  return unannotatedFilters.map((unannotatedFilter) => {
    const parameter =
      unannotatedFilter.parameters && unannotatedFilter.parameters[0];
    const isActive = isFilterActive({
      filterId: unannotatedFilter.filterId,
      filtersActive,
    });
    const currentFilter = filtersActive
      ? filtersActive.find((f) => f.filterId === unannotatedFilter.filterId)
      : null;
    const activeParameter =
      parameter && isActive && currentFilter && currentFilter.parameters[0];

    const filterType =
      unannotatedFilter.parameters && activeParameter
        ? unannotatedFilter.parameters.find(
            (filter) => filter.parameterName === activeParameter.parameterName
          )
        : parameter && parameter.widgetType;

    const captionValue = activeParameter
      ? fieldValueToString({
          fieldValue: activeParameter.valueTo
            ? [activeParameter.value, activeParameter.valueTo]
            : activeParameter.value,
          fieldType: filterType,
        })
      : '';

    return {
      ...unannotatedFilter,
      captionValue,
      isActive,
    };
  });
}

/**
 * @method isFilterValid
 * @summary Function used to check the validity of a filter - returns a boolean value
 * @param {object} filters
 */
export function isFilterValid(filters) {
  if (filters.parameters) {
    return !filters.parameters.filter((item) => item.mandatory && !item.value)
      .length;
  }

  return true;
}

/**
 * @method normalizeFilterValue
 * @summary Sets the value for local filter to null if it's an empty string. This way we can easily identify
 *          if this was edited by the user before sending backend request.
 * @param {array} params
 */
export function normalizeFilterValue(params) {
  return params.reduce((acc, param) => {
    acc.push({
      ...param,
      value: param.value === '' ? null : param.value,
      valueTo: param.valueTo === '' ? null : param.valueTo,
    });

    return acc;
  }, []);
}

const prepareParameterForBackend = (param) => {
  const {
    parameterName,
    widgetType,
    value,
    defaultValue,
    valueTo,
    defaultValueTo,
  } = param;

  return {
    parameterName,
    value: prepareParameterValueForBackend({
      value,
      widgetType,
      defaultValue,
    }),
    valueTo: prepareParameterValueForBackend({
      value: valueTo,
      widgetType,
      defaultValue: defaultValueTo,
    }),
  };
};

export const prepareParameterValueForBackend = ({
  value,
  widgetType,
  defaultValue,
}) => {
  let valuePrepared = value;

  if (widgetType === 'Date' && valuePrepared) {
    valuePrepared = Moment(valuePrepared).format(DATE_FORMAT);
  }

  // filters should always send value to the server - even if it's a defaultValue, not edited by user
  valuePrepared =
    valuePrepared === null && defaultValue ? defaultValue : valuePrepared;

  // case when facets gets cleared:
  valuePrepared =
    valuePrepared &&
    valuePrepared.values &&
    Array.isArray(valuePrepared.values) &&
    valuePrepared.values.length === 0
      ? []
      : valuePrepared;

  return valuePrepared;
};

export const prepareFilterForBackend = ({ filterId, parameters }) => {
  if (parameters && parameters.length) {
    parameters.map((param, index) => {
      param = prepareParameterForBackend(param);
      parameters[index] = param;
    });
  }

  return {
    filterId,
    parameters,
  };
};
