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
import counterpart from 'counterpart';
import { getTable, getTableId } from '../../reducers/tables';

import '../../assets/css/InvoiceToAllocateViewHeader.scss';
import AmountIndicator from './AmountIndicator';

export const INVOICE_TO_ALLOCATE_WINDOW_ID = 'invoicesToAllocate';

export const InvoiceToAllocateViewHeader = ({
  windowId,
  viewId,
  selectedRowIds,
}) => {
  const tableId = getTableId({ windowId, viewId });
  const table = useSelector((state) => getTable(state, tableId));

  const { grandTotal, discountAmt, grandTotalMinusDiscountAmt } = computeFields(
    table,
    selectedRowIds
  );

  return (
    <div className="invoiceToAllocateViewHeader">
      <AmountIndicator
        caption={counterpart.translate('view.invoiceToAllocate.grandTotal')}
        value={grandTotal}
      />
      <AmountIndicator
        caption={counterpart.translate('view.invoiceToAllocate.discountAmt')}
        value={discountAmt}
      />
      <AmountIndicator
        caption={counterpart.translate('view.invoiceToAllocate.netAmt')}
        value={grandTotalMinusDiscountAmt}
      />
    </div>
  );
};

InvoiceToAllocateViewHeader.propTypes = {
  windowId: PropTypes.string.isRequired,
  viewId: PropTypes.string.isRequired,
  selectedRowIds: PropTypes.arrayOf(PropTypes.string),
};

const computeFields = (table, selectedRowIds) => {
  if (table?.rows?.length && selectedRowIds?.length > 0) {
    let grandTotal = 0.0;
    let discountAmt = 0.0;

    const rows = table.rows;
    rows
      .filter((row) => selectedRowIds.includes(row.id))
      .forEach((row) => {
        const fieldsByName = row.fieldsByName;
        //console.log('fieldsByName', fieldsByName);
        grandTotal += parseFloat(fieldsByName.grandTotal?.value || 0);
        discountAmt += parseFloat(fieldsByName.discountAmt?.value || 0);
      });

    return {
      grandTotal: formatNumberToString(grandTotal, 2),
      discountAmt: formatNumberToString(discountAmt, 2),
      grandTotalMinusDiscountAmt: formatNumberToString(
        grandTotal - discountAmt,
        2
      ),
    };
  } else {
    return {
      grandTotal: null,
      discountAmt: null,
      grandTotalMinusDiscountAmt: null,
    };
  }
};

const formatNumberToString = (number, precision) => {
  return parseFloat(number ?? 0).toFixed(precision);
};
