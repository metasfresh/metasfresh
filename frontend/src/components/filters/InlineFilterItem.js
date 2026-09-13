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

  // `handlePatch` (passed to the widget as `handlePatch={handleApply}`) is called with the freshly
  // produced value, WITHOUT that value having gone through `setValue`/`onChange` first. A Checkbox
  // (a YesNo filter) only ever wires `handlePatch`, so for it that is the ONLY way the value
  // arrives; a Text filter reaches the same path on Enter/blur (RawWidget#handleKeyDown /
  // #handleBlurWithParams -> #handlePatch), where the value happens to match what `onChange` already
  // tracked. So the value actually applied must be the one passed in here when present, falling back
  // to the locally tracked `parameterValue` only when no value was passed. Also keep `parameterValue` itself in sync so a second
  // toggle (e.g. turning the filter back off) does not read a stale value on the next call.
  //
  // Checkbox additionally expects `handlePatch(...)` to return a thenable - same contract as the
  // redux `patch` action creator used for regular document fields - so this must return a Promise,
  // never `undefined`.
  const handleApply = (property, valueFromPatch) => {
    const nextValue =
      valueFromPatch !== undefined ? valueFromPatch : parameterValue;

    if (valueFromPatch !== undefined) {
      setValue(property, valueFromPatch);
    }

    const filter = mergeParameterValueToFilter(
      filterProp,
      parameterName,
      nextValue
    );

    clearFilters(filter, true);
    applyFilters(filter);

    return Promise.resolve();
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
