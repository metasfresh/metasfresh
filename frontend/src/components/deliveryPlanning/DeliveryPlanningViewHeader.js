import React from 'react';
import PropTypes from 'prop-types';
import { useSelector } from 'react-redux';
import { getTable, getTableId } from '../../reducers/tables';

import '../../assets/css/DeliveryPlanningViewHeader.scss';
import { computeNumberOfPages } from '../../utils/tableHelpers';
import {
  getSettingFromState,
  getSettingFromStateAsBoolean,
} from '../../utils/settings';

const DEFAULT_WINDOW_ID = '541632';
const FIELDNAME_PlannedLoadedQuantity = 'PlannedLoadedQuantity';
const FIELDNAME_ActualLoadQty = 'ActualLoadQty';
const FIELDNAME_PlannedDischargeQuantity = 'PlannedDischargeQuantity';
const FIELDNAME_ActualDischargeQuantity = 'ActualDischargeQuantity';
const FIELDNAMES = [
  FIELDNAME_PlannedLoadedQuantity,
  FIELDNAME_ActualLoadQty,
  FIELDNAME_PlannedDischargeQuantity,
  FIELDNAME_ActualDischargeQuantity,
];

export const getDeliveryPlanningViewHeaderWindowId = (state) => {
  if (!isDeliveryPlanningViewHeaderEnabled(state)) {
    return -1;
  }

  const windowId = getSettingFromState(
    state,
    'DeliveryPlanningViewHeader.windowId'
  );

  return windowId ? String(windowId) : DEFAULT_WINDOW_ID;
};

const isDeliveryPlanningViewHeaderEnabled = (state) => {
  return getSettingFromStateAsBoolean(
    state,
    'DeliveryPlanningViewHeader.enabled',
    true
  );
};

export const DeliveryPlanningViewHeader = ({
  windowId,
  viewId,
  selectedRowIds,
  pageLength,
  precision,
}) => {
  const tableId = getTableId({ windowId, viewId });
  const table = useSelector((state) => getTable(state, tableId));
  const pages = computeNumberOfPages(table?.size, pageLength);
  const fields = computeFields({
    table,
    selectedRowIds,
    hasMoreThanOnePage: pages > 1,
    precision,
  });
  //console.log('DeliveryPlanningViewHeader', { table, fields });

  return (
    <div className="deliveryPlanningViewHeader document-list-header">
      {fields.map((field) => (
        <Indicator
          key={field.key}
          caption={field.caption}
          value={field.value}
        />
      ))}
    </div>
  );
};

DeliveryPlanningViewHeader.propTypes = {
  windowId: PropTypes.string.isRequired,
  viewId: PropTypes.string.isRequired,
  selectedRowIds: PropTypes.arrayOf(PropTypes.string),
  pageLength: PropTypes.number.isRequired,
  precision: PropTypes.number,
};

const computeFields = ({
  table,
  selectedRowIds,
  hasMoreThanOnePage,
  precision,
}) => {
  if (table?.rows?.length && selectedRowIds?.length > 0) {
    // In case user selected all rows from all pages,
    // better don't show the sums because the sums are computed only from current page.
    const isAllRowsSelected = selectedRowIds.includes('all');
    if (isAllRowsSelected && hasMoreThanOnePage) {
      return [];
    }

    const map = {};
    table.rows
      .filter((row) => isAllRowsSelected || selectedRowIds.includes(row.id))
      .forEach((row) => {
        const fieldsByName = row.fieldsByName;

        FIELDNAMES.forEach((fieldName) => {
          addToQtysByUomMap({
            map,
            fieldName,
            fieldsByName,
            table,
          });
        });
      });

    const result = [];
    FIELDNAMES.forEach((fieldName) => {
      const byUOM = map[fieldName];
      if (byUOM) {
        Object.values(byUOM).forEach((qty) => {
          result.push({
            key: `${fieldName}_${qty?.uom}`,
            caption: getFieldCaption(table, fieldName),
            value: formatQtyToString(qty, precision),
          });
        });
      }
    });

    return result;
  } else {
    return [];
  }
};

const getFieldCaption = (table, fieldName) => {
  const columnFound = table.columns.find((column) => {
    return column.fields.find((field) => field.field === fieldName) != null;
  });

  return columnFound?.caption ?? fieldName;
};

const extractFieldValueAsQty = (fieldsByName, fieldName) => {
  const uom = extractUOM(fieldsByName);
  if (!uom) {
    return null;
  }
  const value = fieldsByName?.[fieldName]?.value || null;
  if (value == null) {
    return null;
  }

  return {
    value: parseFloat(value),
    uom,
  };
};

const extractUOM = (fieldsByName) => {
  return fieldsByName?.C_UOM_ID?.value?.caption;
};

const addToQtysByUomMap = ({ map, fieldName, fieldsByName }) => {
  const qty = extractFieldValueAsQty(fieldsByName, fieldName);
  if (!qty) {
    return;
  }

  if (!map[fieldName]) {
    map[fieldName] = {};
  }

  const uom = qty.uom;
  map[fieldName][uom] = addQtysIfNotNull(map[fieldName][uom], qty);
};

const addQtysIfNotNull = (qty1, qty2) => {
  if (qty1 == null && qty2 == null) {
    return null;
  }

  if (qty1 == null) {
    if (qty2 == null) {
      return null;
    } else {
      return qty2;
    }
  } else {
    if (qty2 == null) {
      return qty1;
    } else {
      if (qty1.uom !== qty2.uom) {
        throw `UOM not matching: ${qty1}, ${qty2}`;
      }
      return {
        value: qty1.value + qty2.value,
        uom: qty1.uom,
      };
    }
  }
};

const formatQtyToString = (qty, precision) => {
  return parseFloat(qty.value ?? 0).toFixed(precision) + ' ' + qty.uom;
};

//
//
//
//
//

const Indicator = ({ caption, value }) => {
  if (!value) {
    return null;
  }

  return (
    <span className="deliveryPlanningViewHeader-indicator">
      <p className="caption">{caption}:</p>
      <p className="value">{value}</p>
    </span>
  );
};

Indicator.propTypes = {
  caption: PropTypes.string.isRequired,
  value: PropTypes.string,
};
