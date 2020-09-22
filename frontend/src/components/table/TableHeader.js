import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import { shouldRenderColumn, getSizeClass } from '../../utils/tableHelpers';
import { getTableId } from '../../reducers/tables';

export class TableHeader extends PureComponent {
  handleClick = (field, sortable) => {
    if (!sortable) {
      return;
    }

    const {
      onSortTable,
      deselect,
      page,
      tabId,
      windowType,
      docId,
      viewId,
      setActiveSort,
    } = this.props;
    const { headersFields: stateFields } = this.props;
    const tableId = getTableId({ windowId: windowType, viewId, docId, tabId });
    let fields = {};
    let sortingValue = null;

    if (field in stateFields) {
      fields = { ...stateFields };
      sortingValue = !fields[field];
      fields[field] = sortingValue;
    } else {
      sortingValue = !Object.values(stateFields).reduce(
        (acc, curr) => acc && curr,
        true
      );
      fields[field] = sortingValue;
    }

    onSortTable(sortingValue, field, true, page, tabId);
    setActiveSort(tableId, true);

    setTimeout(() => {
      setActiveSort(tableId, false);
    }, 1000);
    deselect();
  };

  renderSorting = (field, caption, sortable, description) => {
    const { headersFields } = this.props;
    if (!headersFields) return false;
    const fieldSorting = headersFields[field];

    return (
      <div
        className={classnames('sort-menu', { 'sort-menu--sortable': sortable })}
        onClick={() => this.handleClick(field, sortable)}
      >
        <span
          title={description || caption}
          className={classnames({ 'th-caption': sortable })}
        >
          {caption}
        </span>
        <span
          className={classnames('sort-ico', {
            'sort rotate-90': field in headersFields && fieldSorting,
            sort: field in headersFields && !fieldSorting,
          })}
        >
          <i className="meta-icon-chevron-1" />
        </span>
      </div>
    );
  };

  renderCols = (cols) => {
    const { onSortTable } = this.props;

    return (
      cols &&
      cols.map((item, index) => {
        if (shouldRenderColumn(item)) {
          return (
            <th key={index} className={getSizeClass(item)}>
              {onSortTable
                ? this.renderSorting(
                    item.fields[0].field,
                    item.caption,
                    item.sortable,
                    item.description
                  )
                : item.caption}
            </th>
          );
        }
      })
    );
  };

  render() {
    const { cols, indentSupported } = this.props;

    return (
      <tr>
        {indentSupported && <th key={0} className="indent" />}
        {this.renderCols(cols)}
      </tr>
    );
  }
}

TableHeader.propTypes = {
  orderBy: PropTypes.array,
  onSortTable: PropTypes.func,
  tabId: PropTypes.any,
  windowType: PropTypes.string,
  docId: PropTypes.string,
  viewId: PropTypes.string,
  deselect: PropTypes.any,
  page: PropTypes.any,
  cols: PropTypes.any,
  indentSupported: PropTypes.any,
  setActiveSort: PropTypes.func,
  headersFields: PropTypes.object,
};

const mapStateToProps = (state, ownProps) => {
  const { viewId, windowType: windowId, docId, tabId } = ownProps;
  const tableId = getTableId({ windowId, viewId, docId, tabId });

  return {
    headersFields:
      state.tables && state.tables.sortOptions
        ? state.tables.sortOptions[tableId]
        : {},
  };
};

export default connect(
  mapStateToProps,
  null
)(TableHeader);
