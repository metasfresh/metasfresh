import React, { PureComponent } from 'react';
import classnames from 'classnames';
import PropTypes from 'prop-types';

import {
  shouldRenderColumn,
  getSizeClass,
  COMBOBOX_MIN_WIDTH_PX,
  COMBOBOX_WIDGET_TYPES,
} from '../../utils/tableHelpers';
import { getTableId } from '../../reducers/tables';

const MIN_COLUMN_WIDTH = 50;

/**
 * @method clampColumnWidth
 * @param {object} params
 * @param {string} params.widgetType
 * @param {number} params.px - the candidate drag width
 * @summary Manual drag-resize floor: a combobox (Lookup/List) column cannot be dragged
 * narrower than the combobox minimum-usable width, so its open dropdown editor always stays
 * within the cell. Every other column keeps the flat `MIN_COLUMN_WIDTH` floor.
 */
export function clampColumnWidth({ widgetType, px }) {
  const minWidth =
    COMBOBOX_WIDGET_TYPES.indexOf(widgetType) > -1
      ? COMBOBOX_MIN_WIDTH_PX
      : MIN_COLUMN_WIDTH;

  return Math.max(minWidth, px);
}

export default class TableHeader extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      fields: {},
      resizing: null, // { index, startX, startWidth }
    };

    this.thRefs = {};
  }

  static getDerivedStateFromProps(props) {
    if (props.orderBy) {
      const fields = {};
      props.orderBy &&
        props.orderBy.map((item) => {
          fields[item.fieldName] = item.ascending;
        });

      return {
        fields,
      };
    }

    return null;
  }

  componentWillUnmount() {
    document.removeEventListener('mousemove', this.handleResizeMove);
    document.removeEventListener('mouseup', this.handleResizeEnd);
  }

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
    const stateFields = this.state.fields;
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

    // TODO: We don't have to spread `fields` as it's a new object anyway
    this.setState({
      fields: { ...fields },
    });

    onSortTable(sortingValue, field, true, page, tabId);
    setActiveSort(tableId, true);

    setTimeout(() => {
      setActiveSort(tableId, false);
    }, 1000);
    deselect();
  };

  handleResizeStart = (e, fieldName, widgetType) => {
    e.preventDefault();
    e.stopPropagation();

    const th = this.thRefs[fieldName];
    if (!th) return;

    const startWidth = th.getBoundingClientRect().width;

    this.setState({
      resizing: { fieldName, widgetType, startX: e.clientX, startWidth },
    });

    document.addEventListener('mousemove', this.handleResizeMove);
    document.addEventListener('mouseup', this.handleResizeEnd);
    document.body.style.cursor = 'col-resize';
    document.body.style.userSelect = 'none';
  };

  handleResizeMove = (e) => {
    const { resizing } = this.state;
    if (!resizing) return;

    const { onColumnResize } = this.props;
    const diff = e.clientX - resizing.startX;
    const newWidth = clampColumnWidth({
      widgetType: resizing.widgetType,
      px: resizing.startWidth + diff,
    });

    if (onColumnResize) {
      onColumnResize(resizing.fieldName, newWidth);
    }
  };

  handleResizeEnd = () => {
    const { onColumnResizeEnd } = this.props;

    document.removeEventListener('mousemove', this.handleResizeMove);
    document.removeEventListener('mouseup', this.handleResizeEnd);
    document.body.style.cursor = '';
    document.body.style.userSelect = '';

    this.setState({ resizing: null });

    if (onColumnResizeEnd) {
      onColumnResizeEnd();
    }
  };

  handleResizeDoubleClick = (e, fieldName) => {
    e.preventDefault();
    e.stopPropagation();

    const { onColumnResetWidth } = this.props;
    if (onColumnResetWidth) {
      onColumnResetWidth(fieldName);
    }
  };

  renderSorting = (field, caption, sortable, description) => {
    const { fields } = this.state;
    const fieldSorting = fields[field];

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
            'sort rotate-90': field in fields && fieldSorting,
            sort: field in fields && !fieldSorting,
          })}
        >
          <i className="meta-icon-chevron-1" />
        </span>
      </div>
    );
  };

  renderCols = (cols) => {
    const { onSortTable, columnWidths } = this.props;
    const { resizing } = this.state;

    return (
      cols &&
      cols.map((item, index) => {
        if (shouldRenderColumn(item)) {
          // Use database field name for language-independent column identification
          const fieldName =
            item.fields && item.fields[0] ? item.fields[0].field : null;
          const dataTestId = fieldName ? `column-${fieldName}` : undefined;
          const customWidth =
            fieldName && columnWidths ? columnWidths[fieldName] : null;

          const style = customWidth
            ? {
                width: `${customWidth}px`,
                minWidth: `${customWidth}px`,
                maxWidth: `${customWidth}px`,
              }
            : {};

          return (
            <th
              key={index}
              ref={(el) => {
                if (fieldName) this.thRefs[fieldName] = el;
              }}
              className={classnames(
                { [getSizeClass(item)]: !customWidth },
                {
                  'column-resizing':
                    resizing && resizing.fieldName === fieldName,
                }
              )}
              style={style}
              data-testid={dataTestId}
            >
              {onSortTable
                ? this.renderSorting(
                    item.fields[0].field,
                    item.caption,
                    item.sortable,
                    item.description
                  )
                : item.caption}
              {fieldName && (
                <div
                  className="column-resize-handle"
                  data-testid={`resize-handle-${fieldName}`}
                  onMouseDown={(e) =>
                    this.handleResizeStart(e, fieldName, item.widgetType)
                  }
                  onDoubleClick={(e) =>
                    this.handleResizeDoubleClick(e, fieldName)
                  }
                />
              )}
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
  columnWidths: PropTypes.object,
  onColumnResize: PropTypes.func,
  onColumnResizeEnd: PropTypes.func,
  onColumnResetWidth: PropTypes.func,
};
