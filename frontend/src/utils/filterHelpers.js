import { DATE_FIELD_TYPES } from '../constants/Constants';
import { getFormatForDateField, getFormattedDate } from './widgetHelpers';
import { getParentFilterFromFilterData } from '../actions/FiltersActions';

export function formatFilters({ filtersData, filtersActive = [] }) {
  const filters = filtersActive.map((filter) => {
    if (filter.parameters) {
      const filterData = getParentFilterFromFilterData({
        filterId: filter.filterId, // we pass the actual key not the index
        filterData: filtersData,
      });

      filter.parameters = filter.parameters.map((filterParameter) => {
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
            widgetType,
          };
        }

        return filterParameter;
      });
    }

    return filter;
  });

  return filters;
}
