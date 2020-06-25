import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import currentDevice from 'current-device';

import { handleCopy, componentPropTypes } from '../../utils/tableHelpers';

import TableHeader from './TableHeader';
import TableItem from './TableItem';

const MOBILE_TABLE_SIZE_LIMIT = 30; // subjective number, based on empiric testing
const isMobileOrTablet =
  currentDevice.type === 'mobile' || currentDevice.type === 'tablet';

export default class Table extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      listenOnKeys: true,
      tableRefreshToggle: false,
    };
  }

  componentDidMount() {
    this._isMounted = true;

    if (this.props.autofocus && this.table) {
      this.table.focus();
    }
  }

  componentDidUpdate(prevProps) {
    const { mainTable, open, rows } = this.props;

    if (!this._isMounted) {
      return;
    }

    if (
      ((rows.length && prevProps.rows.length === 0) || (mainTable && open)) &&
      this.table
    ) {
      this.table.focus();

      setTimeout(() => {
        // TODO: Figure a better way to do this https://github.com/metasfresh/metasfresh/issues/1679
        this.setState({
          tableRefreshToggle: !this.state.mounted,
        });
      }, 1);
    }
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  setListenTrue = () => {
    this.setState({ listenOnKeys: true });
  };

  setListenFalse = () => {
    this.setState({ listenOnKeys: false });
  };

  setTableRef = (ref) => {
    this.table = ref;
  };

  setTfootRef = (ref) => {
    this.tfoot = ref;
  };

  getProductRange = (id) => {
    const { keyProperty, rows, selected } = this.props;
    let arrayIndex;
    let selectIdA;
    let selectIdB;

    arrayIndex = rows.map((item) => item[keyProperty]);
    selectIdA = arrayIndex.findIndex((x) => x === id);
    selectIdB = arrayIndex.findIndex((x) => x === selected[0]);

    const selectedArr = [selectIdA, selectIdB];

    selectedArr.sort((a, b) => a - b);
    return arrayIndex.slice(selectedArr[0], selectedArr[1] + 1);
  };

  handleClick = (e, item) => {
    const {
      onSelectionChanged,
      openIncludedViewOnSelect,
      showIncludedView,
      isModal,
      viewId,
      windowId,
      keyProperty,
      updateQuickActions,
      selected,
      onSelect,
      onDeselect,
    } = this.props;
    const id = item[keyProperty];

    if (e && e.button === 0) {
      const selectMore = e.metaKey || e.ctrlKey;
      const selectRange = e.shiftKey;
      const isSelected = selected.indexOf(id) > -1;
      const isAnySelected = selected.length > 0;

      let newSelection;

      if (selectMore || isMobileOrTablet) {
        if (isSelected) {
          newSelection = onDeselect(id);
        } else {
          newSelection = onSelect(id);
        }
      } else if (selectRange) {
        if (isAnySelected) {
          newSelection = this.getProductRange(id);
          onSelect(newSelection);
        } else {
          newSelection = [id];
          onSelect(id);
        }
      } else {
        // if row is not selected or multiple rows are selected
        if (!isSelected || (isSelected && selected.length > 1)) {
          updateQuickActions && updateQuickActions(id);
          newSelection = [id];
          onSelect(id);
        }
      }

      if (onSelectionChanged && newSelection) {
        onSelectionChanged(newSelection);
      }
    }

    if (openIncludedViewOnSelect) {
      const identifier = isModal ? viewId : windowId;

      showIncludedView({
        id: identifier,
        showIncludedView: item.supportIncludedViews,
        forceClose: false,
        windowId: item.supportIncludedViews
          ? item.includedView.windowType || item.includedView.windowId
          : null,
        viewId: item.supportIncludedViews ? item.includedView.viewId : '',
      });
    }
  };

  handleDoubleClick = (id) => {
    const { isIncluded, onDoubleClick } = this.props;

    if (!isIncluded) {
      onDoubleClick && onDoubleClick(id);
    }
  };

  handleKeyDown = (e) => {
    const {
      keyProperty,
      mainTable,
      readonly,
      closeOverlays,
      selected,
      rows,
      showSelectedIncludedView,
      collapsedArrayMap,
      handleSelect,
    } = this.props;
    const { listenOnKeys } = this.state;

    if (!listenOnKeys) {
      return;
    }

    const selectRange = e.shiftKey;
    const nodeList = Array.prototype.slice.call(
      document.activeElement.parentElement.children
    );
    const idActive = nodeList.indexOf(document.activeElement);
    let idFocused = null;

    if (idActive > -1) {
      idFocused = idActive;
    }

    switch (e.key) {
      case 'ArrowDown': {
        e.preventDefault();

        const array =
          collapsedArrayMap.length > 0
            ? collapsedArrayMap.map((item) => item[keyProperty])
            : rows.map((item) => item[keyProperty]);
        const currentId = array.findIndex(
          (x) => x === selected[selected.length - 1]
        );

        if (currentId >= array.length - 1) {
          return;
        }

        if (!selectRange) {
          handleSelect(
            array[currentId + 1],
            false,
            idFocused,
            showSelectedIncludedView &&
              showSelectedIncludedView([array[currentId + 1]])
          );
        } else {
          handleSelect(array[currentId + 1], false, idFocused);
        }
        break;
      }
      case 'ArrowUp': {
        e.preventDefault();

        const array =
          collapsedArrayMap.length > 0
            ? collapsedArrayMap.map((item) => item[keyProperty])
            : rows.map((item) => item[keyProperty]);
        const currentId = array.findIndex(
          (x) => x === selected[selected.length - 1]
        );

        if (currentId <= 0) {
          return;
        }

        if (!selectRange) {
          handleSelect(
            array[currentId - 1],
            idFocused,
            false,
            showSelectedIncludedView &&
              showSelectedIncludedView([array[currentId - 1]])
          );
        } else {
          handleSelect(array[currentId - 1], idFocused, false);
        }
        break;
      }
      case 'ArrowLeft':
        e.preventDefault();
        if (document.activeElement.previousSibling) {
          document.activeElement.previousSibling.focus();
        }
        break;
      case 'ArrowRight':
        e.preventDefault();
        if (document.activeElement.nextSibling) {
          document.activeElement.nextSibling.focus();
        }
        break;
      case 'Tab':
        if (mainTable) {
          e.preventDefault();
          const focusedElem = document.getElementsByClassName(
            'js-attributes'
          )[0];
          if (focusedElem) {
            focusedElem.getElementsByTagName('input')[0].focus();
          }
          break;
        } else {
          if (e.shiftKey) {
            //passing focus over table cells backwards
            this.table.focus();
          } else {
            //passing focus over table cells
            this.tfoot.focus();
          }
        }
        break;
      case 'Enter':
        if (selected.length <= 1 && readonly) {
          e.preventDefault();

          this.handleDoubleClick(selected[selected.length - 1]);
        }
        break;
      case 'Escape':
        closeOverlays && closeOverlays();
        break;
    }
  };

  renderTableBody = () => {
    const {
      tabId,
      windowId,
      docId,
      readonly,
      keyProperty,
      mainTable,
      newRow,
      tabIndex,
      entity,
      indentSupported,
      collapsible,
      viewId,
      supportOpenRecord,
      focusOnFieldName,
      modalVisible,
      isGerman,
      activeSort,
      page,
      lastPage,
      columns,
      selected,
      rows,
      onItemChange,
      onSelect,
      onRowCollapse,
      collapsedRows,
      collapsedParentRows,
      onRightClick,
      rowRefs,
    } = this.props;

    if (!rows.length || !columns.length) {
      return null;
    }

    let renderRows = rows.filter((row) => {
      if (collapsedRows) {
        return collapsedRows.indexOf(row[keyProperty]) === -1;
      }
      return true;
    });

    if (isMobileOrTablet && rows.length > MOBILE_TABLE_SIZE_LIMIT) {
      renderRows = renderRows.slice(0, MOBILE_TABLE_SIZE_LIMIT);
    }

    return renderRows.map((item, i) => (
      <TableItem
        {...item}
        {...{
          page,
          lastPage,
          entity,
          windowId,
          mainTable,
          indentSupported,
          docId,
          tabIndex,
          readonly,
          collapsible,
          viewId,
          supportOpenRecord,
          item,
          focusOnFieldName,
          modalVisible,
          isGerman,
          activeSort,
        }}
        cols={columns}
        key={`row-${i}${viewId ? `-${viewId}` : ''}`}
        dataKey={`row-${i}${viewId ? `-${viewId}` : ''}`}
        collapsed={
          !!(
            collapsedParentRows.length &&
            collapsedParentRows.indexOf(item[keyProperty]) > -1
          )
        }
        odd={i & 1}
        ref={(c) => {
          if (c) {
            const keyProp = item[keyProperty];

            rowRefs[keyProp] = c;
          }
        }}
        keyProperty={item[keyProperty]}
        rowId={item[keyProperty]}
        tabId={tabId}
        onDoubleClick={this.handleDoubleClick}
        onClick={this.handleClick}
        handleRightClick={onRightClick}
        changeListenOnTrue={this.setListenTrue}
        changeListenOnFalse={this.setListenFalse}
        newRow={i === rows.length - 1 ? newRow : false}
        isSelected={
          selected.indexOf(item[keyProperty]) > -1 ||
          selected[0] === 'all' ||
          (!selected[0] && focusOnFieldName && i === 0)
        }
        handleSelect={onSelect}
        contextType={item.type}
        caption={item.caption ? item.caption : ''}
        colspan={item.colspan}
        notSaved={item.saveStatus && !item.saveStatus.saved}
        onRowCollapse={onRowCollapse}
        onItemChange={onItemChange}
        onCopy={handleCopy}
      />
    ));
  };

  renderEmptyInfo = (rows) => {
    const { emptyText, emptyHint, pending } = this.props;

    if (pending) {
      return false;
    }

    if (!rows.length) {
      return (
        <div className="empty-info-text">
          <div>
            <h5>{emptyText}</h5>
            <p>{emptyHint}</p>
          </div>
        </div>
      );
    }

    return false;
  };

  render() {
    const {
      columns,
      windowId,
      docId,
      tabId,
      viewId,
      readonly,
      page,
      sort,
      orderBy,
      indentSupported,
      hasIncluded,
      blurOnIncludedView,
      spinnerVisible,
      rows,
      onDeselectAll,
      tableRefreshToggle,
      setActiveSort,
    } = this.props;

    return (
      <div
        className={classnames(
          'panel panel-primary panel-bordered',
          'panel-bordered-force table-flex-wrapper',
          'document-list-table js-not-unselect',
          {
            'table-content-empty': !rows.length,
          }
        )}
      >
        <table
          className={classnames(
            'table table-bordered-vertically',
            'table-striped js-table',
            {
              'table-read-only': readonly,
              'table-fade-out': hasIncluded && blurOnIncludedView,
              'layout-fix': tableRefreshToggle,
            }
          )}
          onKeyDown={this.handleKeyDown}
          ref={this.setTableRef}
          onCopy={handleCopy}
        >
          <thead>
            <TableHeader
              {...{
                sort,
                orderBy,
                page,
                indentSupported,
                tabId,
                docId,
                viewId,
                setActiveSort,
              }}
              cols={columns}
              windowType={windowId}
              deselect={onDeselectAll}
            />
          </thead>
          <tbody>{this.renderTableBody()}</tbody>
          <tfoot ref={this.setTfootRef} />
        </table>

        {!spinnerVisible && this.renderEmptyInfo(rows)}
      </div>
    );
  }
}

Table.propTypes = {
  ...componentPropTypes,
  onRightClick: PropTypes.func.isRequired,
  handleSelect: PropTypes.func.isRequired,
  rowRefs: PropTypes.object.isRequired,
};
