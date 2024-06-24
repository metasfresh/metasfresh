import React from 'react';
import PropTypes from 'prop-types';
import { useSelector } from 'react-redux';
import { getTable, getTableId } from '../../reducers/tables';

import '../../assets/css/PPOrderCandidateViewHeader.scss';
import { computeNumberOfPages } from '../../utils/tableHelpers';

export const PP_ORDER_CANDIDATE_WINDOW_ID = '541316'; // FIXME HARDCODED
const FIELDNAME_NumberOfResources_ToProcess = 'NumberOfResources_ToProcess';
const FIELDNAME_QtyToProcess = 'QtyToProcess';
const FIELDNAME_QtyProcessed = 'QtyProcessed';

export const PPOrderCandidateViewHeader = ({
  windowId,
  viewId,
  selectedRowIds,
  pageLength,
}) => {
  const tableId = getTableId({ windowId, viewId });
  const table = useSelector((state) => getTable(state, tableId));
  const pages = computeNumberOfPages(table?.size, pageLength);
  const fields = computeFields(table, selectedRowIds, pages > 1);
  //console.log('PPOrderCandidateViewHeader', { table, fields });

  return (
    <div className="ppOrderCandidateViewHeader document-list-header">
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

PPOrderCandidateViewHeader.propTypes = {
  windowId: PropTypes.string.isRequired,
  viewId: PropTypes.string.isRequired,
  selectedRowIds: PropTypes.arrayOf(PropTypes.string),
  pageLength: PropTypes.number.isRequired,
};

const computeFields = (table, selectedRowIds, hasMoreThanOnePage) => {
  if (table?.rows?.length && selectedRowIds?.length > 0) {
    // In case user selected all rows from all pages,
    // better don't show the sums because the sums are computed only from current page.
    const isAllRowsSelected = selectedRowIds.includes('all');
    if (isAllRowsSelected && hasMoreThanOnePage) {
      return [];
    }

    let numberOfResources_ToProcess_SUM = null;
    const qtyToProcess_SUM = {};
    const qtyProcessed_SUM = {};

    const rows = table.rows;
    rows
      .filter((row) => isAllRowsSelected || selectedRowIds.includes(row.id))
      .forEach((row) => {
        const fieldsByName = row.fieldsByName;

        //
        // Number of Resources
        numberOfResources_ToProcess_SUM = addIfNotNull(
          numberOfResources_ToProcess_SUM,
          extractFieldValueAsInteger(
            fieldsByName,
            FIELDNAME_NumberOfResources_ToProcess
          )
        );

        //
        // Qty to process
        addToQtysByUomMap(
          qtyToProcess_SUM,
          extractFieldValueAsQty(fieldsByName, FIELDNAME_QtyToProcess)
        );

        //
        // Qty processed
        addToQtysByUomMap(
          qtyProcessed_SUM,
          extractFieldValueAsQty(fieldsByName, FIELDNAME_QtyProcessed)
        );
      });

    //
    // Assemble the result:
    const result = [];

    if (numberOfResources_ToProcess_SUM != null) {
      const caption = getFieldCaption(
        table,
        FIELDNAME_NumberOfResources_ToProcess
      );
      result.push({
        key: 'numberOfResources_ToProcess_SUM',
        caption,
        value: String(numberOfResources_ToProcess_SUM),
      });
    }

    const qtyToProcess_UOMs = Object.keys(qtyToProcess_SUM);
    if (qtyToProcess_UOMs.length > 0) {
      const caption = getFieldCaption(table, FIELDNAME_QtyToProcess);
      qtyToProcess_UOMs.forEach((uom) => {
        const qtyToProcess = qtyToProcess_SUM[uom];
        result.push({
          key: `qtyToProcess_${qtyToProcess.uom}`,
          caption,
          value: formatQtyToString(qtyToProcess, 2),
        });
      });
    }

    const qtyProcessed_UOMs = Object.keys(qtyProcessed_SUM);
    if (qtyProcessed_UOMs.length > 0) {
      const caption = getFieldCaption(table, FIELDNAME_QtyProcessed);
      qtyProcessed_UOMs.forEach((uom) => {
        const qtyProcessed = qtyProcessed_SUM[uom];
        result.push({
          key: `qtyProcessed_${qtyProcessed.uom}`,
          caption,
          value: formatQtyToString(qtyProcessed, 2),
        });
      });
    }

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

const extractFieldValueAsInteger = (fieldsByName, fieldName) => {
  const value = fieldsByName?.[fieldName]?.value || null;
  return value != null ? parseInt(value) : null;
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

const addIfNotNull = (number1, number2) => {
  if (number1 == null && number2 == null) {
    return null;
  }

  return (number1 ?? 0) + (number2 ?? 0);
};

const addToQtysByUomMap = (map, qty) => {
  if (!qty) {
    return;
  }

  const uom = qty.uom;
  map[uom] = addQtysIfNotNull(map[uom], qty);
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
    <span className="ppOrderCandidateViewHeader-indicator">
      <p className="caption">{caption}:</p>
      <p className="value">{value}</p>
    </span>
  );
};

Indicator.propTypes = {
  caption: PropTypes.string.isRequired,
  value: PropTypes.string,
};
