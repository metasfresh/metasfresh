import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import currentDevice from 'current-device';
import { ARROW_DOWN_KEY, ARROW_UP_KEY } from '../../constants/Constants';
import { componentPropTypes, handleCopy } from '../../utils/tableHelpers';
import TableHeader from './TableHeader';
import TableRow from './TableRow';
import Spinner from '../app/SpinnerOverlay';

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
    this.multiSelectionStartIdx = null;
  }

  componentDidMount() {
    this._isMounted = true;
    this.initialPaddingBottom = 100;
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

  getCurrentRowIndex = (arrowOrientation) => {
    const { selected: selectedRowIds } = this.props;

    const allRowIds = this.getAllRowIds();

    // If there is no selection, return right away
    if (!selectedRowIds || selectedRowIds.length === 0) {
      return { currentIdx: null, allRowIds };
    }

    let currentRowId =
      arrowOrientation === ARROW_UP_KEY
        ? selectedRowIds[0]
        : selectedRowIds[selectedRowIds.length - 1];

    const currentIdx = allRowIds.findIndex((rowId) => rowId === currentRowId);

    return { currentIdx, allRowIds };
  };

  getAllRowIds = () => {
    const { keyProperty, rows } = this.props;
    return rows.map((item) => item[keyProperty]);
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

  /**
   * @summary Updates the start reference used for the multi selection when SHIFT + arrow up/down keys are pressed
   * @param {*} currentIdx - the current index in the array of rows
   */
  updateMultiSelectionStartIdx = (currentIdx) => {
    if (this.multiSelectionStartIdx === null) {
      // setting the start index for the first time (when we get a null value - as result of a previous reset, ie. click on a row)
      this.multiSelectionStartIdx = currentIdx;
    }
  };

  clearMultiSelectionStartIdx = () => (this.multiSelectionStartIdx = null);

  handleClick = (e, item) => {
    const { keyProperty, selected, onSelect, onDeselect, featureType } =
      this.props;
    const disableMultiSel = featureType === 'SEARCH' ? true : false;
    const id = item[keyProperty];

    this.clearMultiSelectionStartIdx();
    if (e && e.button === 0) {
      const selectMore = e.metaKey || e.ctrlKey;
      const selectRange = e.shiftKey;
      const isSelected = selected.indexOf(id) > -1;
      const isAnySelected = selected.length > 0;

      if (selectMore || isMobileOrTablet) {
        if (isSelected) {
          onDeselect(id);
        } else {
          // selection with [CTRL + click] happens here
          let newSelectionItems =
            selected && !selected.includes(id) ? [...selected, id] : [id];
          disableMultiSel ? onSelect(id) : onSelect(newSelectionItems);
        }
      } else if (selectRange) {
        // selection using [SHIFT + click] to select a range happens here
        if (isAnySelected && !disableMultiSel) {
          const newSelection = this.getProductRange(id);
          onSelect(newSelection);
        } else {
          onSelect(id);
        }
      } else {
        // if row is not selected or multiple rows are selected
        if (!isSelected || (isSelected && selected.length > 1)) {
          onSelect(id);
        } else {
          onDeselect(id);
        }
      }
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
      mainTable,
      readonly,
      closeOverlays,
      selected,
      showSelectedIncludedView,
      handleSelect,
      navigationActive,
    } = this.props;
    const { listenOnKeys } = this.state;

    if (!listenOnKeys) return;

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
      case ARROW_DOWN_KEY: {
        e.preventDefault();

        const { currentIdx, allRowIds } =
          this.getCurrentRowIndex(ARROW_DOWN_KEY);

        if (currentIdx >= allRowIds.length - 1) return;

        if (!selectRange) {
          handleSelect(
            allRowIds[currentIdx + 1],
            false,
            idFocused,
            showSelectedIncludedView &&
              showSelectedIncludedView([allRowIds[currentIdx + 1]])
          );
          this.clearMultiSelectionStartIdx();
        } else {
          this.updateMultiSelectionStartIdx(currentIdx);

          const downShiftSel = allRowIds.slice(
            this.multiSelectionStartIdx > 0 ? this.multiSelectionStartIdx : 0,
            currentIdx + 2 // +2 because we want to slice up to the next row and include it
          );
          handleSelect(
            downShiftSel,
            false,
            idFocused,
            showSelectedIncludedView && showSelectedIncludedView(downShiftSel)
          );
        }
        break;
      }
      case ARROW_UP_KEY: {
        e.preventDefault();

        const { currentIdx, allRowIds } = this.getCurrentRowIndex(ARROW_UP_KEY);

        if (currentIdx <= 0) return;

        if (!selectRange) {
          handleSelect(
            allRowIds[currentIdx - 1],
            idFocused,
            false,
            showSelectedIncludedView &&
              showSelectedIncludedView([allRowIds[currentIdx - 1]])
          );
          this.clearMultiSelectionStartIdx();
        } else {
          this.updateMultiSelectionStartIdx(currentIdx);

          const upShiftSel = allRowIds.slice(
            currentIdx - 1,
            this.multiSelectionStartIdx + 1
          );
          handleSelect(
            upShiftSel,
            false,
            idFocused,
            showSelectedIncludedView && showSelectedIncludedView(upShiftSel)
          );
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
          if (document.activeElement.nextSibling) {
            e.preventDefault();
            document.activeElement.nextSibling.focus();
          } else {
            const { currentIdx, allRowIds } = this.getCurrentRowIndex();

            if (currentIdx < allRowIds.length - 1) {
              e.preventDefault();

              handleSelect(allRowIds[currentIdx + 1], false, 0);

              const focusedElem =
                document.getElementsByClassName('js-attributes')[0];

              if (focusedElem) {
                focusedElem.getElementsByTagName('input')[0].focus();
              }
            } else {
              // TODO: How we should handle tabbing when we're out of rows ?
              // For now we'll just let the browser do the default
            }
          }

          break;
        } else {
          if (e.shiftKey && navigationActive) {
            e.preventDefault();
            //passing focus over table cells backwards
            this.table.focus();
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
      onSelect,
      onRowCollapse,
      collapsedRows,
      collapsedParentRows,
      onRightClick,
      rowRefs,
      handleFocusAction,
      updatePropertyValue,
      tableId,
      onFastInlineEdit,
    } = this.props;
    const { listenOnKeys } = this.state;

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
      <TableRow
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
          updatePropertyValue,
          tableId,
          listenOnKeys,
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
        rowIndex={i}
        rowId={item[keyProperty]}
        tabId={tabId}
        onDoubleClick={this.handleDoubleClick}
        onFastInlineEdit={onFastInlineEdit}
        onClick={this.handleClick}
        handleRightClick={onRightClick}
        handleFocusAction={handleFocusAction}
        changeListenOnTrue={this.setListenTrue}
        changeListenOnFalse={this.setListenFalse}
        newRow={i === rows.length - 1 ? newRow : false}
        isSelected={
          selected.indexOf(item[keyProperty]) > -1 ||
          selected[0] === 'all' ||
          (!selected[0] && focusOnFieldName && i === 0)
        }
        updateHeight={this.updateHeight}
        handleSelect={onSelect}
        contextType={item.type}
        caption={item.caption ? item.caption : ''}
        colspan={item.colspan}
        notSaved={item.saveStatus && !item.saveStatus.saved}
        hasComments={item.hasComments}
        onRowCollapse={onRowCollapse}
        onCopy={handleCopy}
      />
    ));
  };

  renderEmptyInfo = (rows) => {
    const { emptyText, emptyHint, pending } = this.props;

    // Note: for the case of pending this div has to pe present otherwise it will mess up
    // the rendering of the spinner within the table when there are no rows.
    if (!rows.length) {
      return (
        <div className="empty-info-text">
          <div>
            <h5>{!pending ? emptyText : ''}</h5>
            <p>{!pending ? emptyHint : ''}</p>
          </div>
        </div>
      );
    }

    return false;
  };

  updateHeight = (heightNew) => {
    heightNew = heightNew ? heightNew : 0;
    if (this.tableContainer) {
      this.tableContainer.style.paddingBottom = `${
        this.initialPaddingBottom + heightNew
      }px`;
    }
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
      onSortTable,
      orderBy,
      indentSupported,
      hasIncluded,
      blurOnIncludedView,
      spinnerVisible,
      rows,
      onDeselectAll,
      tableRefreshToggle,
      setActiveSort,
      pending,
    } = this.props;

    return (
      <div
        ref={(ref) => (this.tableContainer = ref)}
        className={classnames(
          'panel panel-primary panel-bordered',
          'panel-bordered-force table-flex-wrapper',
          'document-list-table js-not-unselect',
          {
            'table-content-empty': !rows.length,
          }
        )}
      >
        {pending && !hasIncluded && (
          <div className="spinner-wrapper-in-tab">
            <div>
              <Spinner iconSize={50} spinnerType="modal" />
            </div>
          </div>
        )}
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
                onSortTable,
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
