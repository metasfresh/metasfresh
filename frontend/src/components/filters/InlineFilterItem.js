/**
 * Filter element displayed inline for frequent filters
 * To see how this should behave look at https://github.com/metasfresh/metasfresh-webui-frontend-legacy/issues/1387
 **/
import React, { useMemo, useState } from 'react';
import PropTypes from 'prop-types';

import WidgetWrapper from '../../containers/WidgetWrapper';
import { convertDateToReadable } from '../../utils/dateHelpers';

const InlineFilterItem = ({
  id,
  //rootFilterId,
  filter: filterProp,
  filterParameter,
  filterData,
  windowId,
  viewId,
  onShow,
  onHide,
  applyFilters,
  clearFilters,
}) => {
  const filterId = filterProp.filterId;
  const parameterName = filterParameter.parameterName;

  const [parameterValue, setParameterValue] = useState(() => {
    const parameterData = filterData?.parameters?.find(
      (param) => param.parameterName === parameterName
    );
    return parameterData?.value ?? '';
  });

  const setValue = (property, value) => {
    //console.log('setValue', { property, value, id, valueTo });
    setParameterValue(value ? value : '');
  };

  const handleApply = () => {
    const filter = mergeParameterValueToFilter(
      filterProp,
      parameterName,
      parameterValue
    );

    clearFilters(filter, true);
    applyFilters(filter);
  };

  const widgetFields = useMemo(
    () => [{ ...filterParameter, emptyText: filterParameter.caption }],
    [filterParameter]
  );
  const widgetData = useMemo(
    () => [{ ...filterParameter, value: parameterValue }],
    [filterParameter, parameterValue]
  );

  return (
    <WidgetWrapper
      id={id}
      dataSource="filter-item"
      entity="documentView"
      windowId={windowId}
      viewId={viewId}
      subentity="filter"
      subentityId={filterId}
      handlePatch={handleApply}
      handleChange={setValue}
      widgetType={filterParameter.widgetType}
      fields={widgetFields}
      type={filterParameter.type}
      widgetData={widgetData}
      range={filterParameter.range}
      caption={filterParameter.caption}
      noLabel={true}
      filterWidget={true}
      onShow={onShow}
      onHide={onHide}
    />
  );
};

InlineFilterItem.propTypes = {
  id: PropTypes.number,
  filter: PropTypes.object,
  filterParameter: PropTypes.object,
  filterData: PropTypes.object,
  filtersActive: PropTypes.array,
  windowId: PropTypes.string,
  viewId: PropTypes.string,
  onShow: PropTypes.func,
  onHide: PropTypes.func,
  applyFilters: PropTypes.func,
  clearFilters: PropTypes.func,
  updateInlineFilter: PropTypes.func,
};

export default InlineFilterItem;

//
//
//

const mergeParameterValueToFilter = (filter, parameterName, parameterValue) => {
  return {
    ...filter,
    parameters: filter.parameters.map((param) => {
      if (param.parameterName === parameterName) {
        return {
          ...param,
          value: convertDateToReadable(param.widgetType, parameterValue),
        };
      } else {
        return param;
      }
    }),
  };
};
