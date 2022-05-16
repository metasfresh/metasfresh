/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import React from 'react';
import PropTypes from 'prop-types';
import { useSelector } from 'react-redux';
import { getTable, getTableId } from '../../reducers/tables';

export const INVOICE_TO_ALLOCATE_WINDOW_ID = 'invoicesToAllocate';

export const InvoiceToAllocateViewHeader = ({
  viewLayout,
  windowId,
  viewId,
  selectedRowIds,
}) => {
  const tableId = getTableId({ windowId, viewId });
  const table = useSelector((state) => getTable(state, tableId));

  const { grandTotal, openAmt, discountAmt } = computeFields(
    table,
    selectedRowIds
  );

  console.log('InvoiceToAllocateViewHeader', { viewLayout });
  return (
    <table className="invoiceToAllocateViewHeader">
      <thead>
        <tr>
          <th>{getFieldCaption(viewLayout, 'grandTotal')}</th>
          <th>{getFieldCaption(viewLayout, 'openAmt')}</th>
          <th>{getFieldCaption(viewLayout, 'discountAmt')}</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{grandTotal}</td>
          <td>{openAmt}</td>
          <td>{discountAmt}</td>
        </tr>
      </tbody>
    </table>
  );
};

InvoiceToAllocateViewHeader.propTypes = {
  viewLayout: PropTypes.object.isRequired,
  windowId: PropTypes.string.isRequired,
  viewId: PropTypes.string.isRequired,
  selectedRowIds: PropTypes.arrayOf(PropTypes.string),
};

const getFieldCaption = (viewLayout, fieldName) => {
  if (!viewLayout?.elements) {
    return '';
  }

  for (const element of viewLayout.elements) {
    if (!element?.fields) {
      return '';
    }

    for (const field of element.fields) {
      if (field.field === fieldName) {
        return element.caption;
      }
    }
  }

  return '';
};

const computeFields = (table, selectedRowIds) => {
  let grandTotal = 0.0;
  let openAmt = 0.0;
  let discountAmt = 0.0;

  if (table?.rows?.length && selectedRowIds?.length > 0) {
    const rows = table.rows;
    rows
      .filter((row) => selectedRowIds.includes(row.id))
      .forEach((row) => {
        const fieldsByName = row.fieldsByName;
        grandTotal += parseFloat(fieldsByName.grandTotal?.value || 0);
        openAmt += parseFloat(fieldsByName.openAmt?.value || 0);
        discountAmt += parseFloat(fieldsByName.discountAmt?.value || 0);
      });
  }

  return {
    grandTotal: parseFloat(grandTotal).toFixed(2),
    openAmt: parseFloat(openAmt).toFixed(2),
    discountAmt: parseFloat(discountAmt).toFixed(2),
  };
};
