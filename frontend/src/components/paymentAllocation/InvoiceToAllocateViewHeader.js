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
  pageLength,
}) => {
  const tableId = getTableId({ windowId, viewId });
  const table = useSelector((state) => getTable(state, tableId));
  const pages = computeNumberOfPages(table?.size, pageLength);
  //console.log('InvoiceToAllocateViewHeader', { table, pageLength, pages });

  const { grandTotal, discountAmt, grandTotalMinusDiscountAmt } = computeFields(
    table,
    selectedRowIds,
    pages > 1
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
  pageLength: PropTypes.number.isRequired,
};

const computeFields = (table, selectedRowIds, hasMoreThanOnePage) => {
  if (table?.rows?.length && selectedRowIds?.length > 0) {
    // In case user selected all rows from all pages,
    // better don't show the sums because the sums are computed only from current page.
    const isAllRowsSelected = selectedRowIds.includes('all');
    if (isAllRowsSelected && hasMoreThanOnePage) {
      return {};
    }

    let grandTotal = 0.0;
    let discountAmt = 0.0;
    let currencyCode = null;
    let multipleCurrencies = false;

    const rows = table.rows;
    rows
      .filter((row) => isAllRowsSelected || selectedRowIds.includes(row.id))
      .forEach((row) => {
        if (multipleCurrencies) {
          return;
        }

        const fieldsByName = row.fieldsByName;
        //console.log('fieldsByName', fieldsByName);

        const rowCurrencyCode = fieldsByName.currencyCodeString?.value ?? '-';
        //console.log('rowCurrencyCode', rowCurrencyCode);

        if (currencyCode == null) {
          currencyCode = rowCurrencyCode;
        } else if (currencyCode !== rowCurrencyCode) {
          multipleCurrencies = true;
          return;
        }

        grandTotal += parseFloat(fieldsByName.grandTotal?.value || 0);
        discountAmt += parseFloat(fieldsByName.discountAmt?.value || 0);
      });

    if (multipleCurrencies) {
      return {};
    }

    return {
      grandTotal: formatAmountToString(grandTotal, 2, currencyCode),
      discountAmt: formatAmountToString(discountAmt, 2, currencyCode),
      grandTotalMinusDiscountAmt: formatAmountToString(
        grandTotal - discountAmt,
        2,
        currencyCode
      ),
    };
  } else {
    return {};
  }
};

const computeNumberOfPages = (size, pageLength) => {
  if (pageLength > 0) {
    return size ? Math.ceil(size / pageLength) : 0;
  } else {
    return size ? 1 : 0;
  }
};

const formatAmountToString = (amount, precision, currencyCode) => {
  let result = parseFloat(amount ?? 0).toFixed(precision);
  if (currencyCode && currencyCode !== '-') {
    result += ' ' + currencyCode;
  }
  return result;
};
