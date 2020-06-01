import React, { PureComponent } from 'react';
import onClickOutside from 'react-onclickoutside';
import classnames from 'classnames';
import currentDevice from 'current-device';
import counterpart from 'counterpart';

import {
  getSizeClass,
  handleCopy,
  handleOpenNewTab,
  componentPropTypes,
  constructorFn,
} from '../../utils/tableHelpers';

import Prompt from '../app/Prompt';
import DocumentListContextShortcuts from '../keyshortcuts/DocumentListContextShortcuts';
import TableContextShortcuts from '../keyshortcuts/TableContextShortcuts';
import TableContextMenu from './TableContextMenu';
import TableFilter from './TableFilter';
import TableHeader from './TableHeader';
import TableItem from './TableItem';
import TablePagination from './TablePagination';

const MOBILE_TABLE_SIZE_LIMIT = 30; // subjective number, based on empiric testing
const isMobileOrTablet =
  currentDevice.type === 'mobile' || currentDevice.type === 'tablet';

class Table extends PureComponent {
  constructor(props) {
    super(props);

    const constr = constructorFn.bind(this);
    constr(props);
  }

  componentDidMount() {
    if (this.props.autofocus) {
      this.table.focus();
    }
  }

  setListenTrue = () => {
    this.setState({ listenOnKeys: true });
  };

  setListenFalse = () => {
    this.setState({ listenOnKeys: false });
  };

  closeContextMenu = () => {
    this.setState({
      contextMenu: Object.assign({}, this.state.contextMenu, {
        open: false,
      }),
    });
  };

  handleSelect = (id, idFocused, idFocusedDown, cb) => {
    const { onSelect } = this.props;

    onSelect(id, idFocused, idFocusedDown, cb);

    this.triggerFocus(idFocused, idFocusedDown);
  };

  setContextMenu = (
    clientX,
    clientY,
    fieldName,
    supportZoomInto,
    supportFieldEdit
  ) => {
    this.setState({
      contextMenu: Object.assign({}, this.state.contextMenu, {
        x: clientX,
        y: clientY,
        open: true,
        fieldName,
        supportZoomInto,
        supportFieldEdit,
      }),
    });
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

  handleBatchEntryToggle = () => {
    const { isBatchEntry } = this.state;

    this.setState({
      isBatchEntry: !isBatchEntry,
    });
  };

  handleDelete = () => {
    this.setState({
      promptOpen: true,
    });
  };

  handlePromptCancelClick = () => {
    this.setState({
      promptOpen: false,
    });
  };

  handlePromptSubmitClick = (selected) => {
    const { onPromptSubmit } = this.props;

    onPromptSubmit && onPromptSubmit(selected);

    this.setState({ promptOpen: false });
  };

  handleShortcutIndent = (collapse) => {
    const { selected, rows, onRowCollapse } = this.props;
    let node = null;

    selected.length === 1 &&
      rows.map((item) => {
        if (item.id === selected[0]) {
          if (item.includedDocuments) {
            node = item;
          }
        }
      });

    if (node) {
      onRowCollapse(node, collapse);
    }
  };

  handleFieldEdit = () => {
    const { selected } = this.props;
    const { contextMenu } = this.state;

    if (contextMenu.supportFieldEdit && selected.length === 1) {
      const selectedId = selected[0];

      this.closeContextMenu();

      if (this.rowRefs && this.rowRefs[selectedId]) {
        this.rowRefs[selectedId].initPropertyEditor(contextMenu.fieldName);
      }
    }
  };

  setWrapperRef = (ref) => {
    this.wrapper = ref;
  };

  setTableRef = (ref) => {
    this.table = ref;
  };

  setTfootRef = (ref) => {
    this.tfoot = ref;
  };

  triggerFocus = (idFocused, idFocusedDown) => {
    if (this.table) {
      const rowSelected = this.table.getElementsByClassName('row-selected');

      if (rowSelected.length > 0) {
        if (typeof idFocused == 'number') {
          rowSelected[0].children[idFocused].focus();
        }
        if (typeof idFocusedDown == 'number') {
          rowSelected[rowSelected.length - 1].children[idFocusedDown].focus();
        }
      }
    }
  };

  handleClickOutside = (event) => {
    const {
      showIncludedViewOnSelect,
      viewId,
      windowType,
      inBackground,
      allowOutsideClick,
      limitOnClickOutside,
      onDeselectAll,
    } = this.props;
    const parentNode = event.target.parentNode;
    const closeIncluded =
      limitOnClickOutside &&
      (parentNode.className.includes('document-list-wrapper') ||
        event.target.className.includes('document-list-wrapper'))
        ? parentNode.className.includes('document-list-has-included')
        : true;

    if (
      allowOutsideClick &&
      parentNode &&
      parentNode !== document &&
      !parentNode.className.includes('notification') &&
      !inBackground &&
      closeIncluded
    ) {
      const item = event.path || (event.composedPath && event.composedPath());

      if (item) {
        for (let i = 0; i < item.length; i++) {
          if (
            item[i].classList &&
            item[i].classList.contains('js-not-unselect')
          ) {
            return;
          }
        }
      } else if (parentNode.className.includes('js-not-unselect')) {
        return;
      }

      onDeselectAll();

      if (showIncludedViewOnSelect) {
        showIncludedViewOnSelect({
          showIncludedView: false,
          windowType,
          viewId,
        });
      }
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
      onShowSelectedIncludedView,
      collapsedArrayMap,
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
          this.handleSelect(
            array[currentId + 1],
            false,
            idFocused,
            onShowSelectedIncludedView([array[currentId + 1]])
          );
        } else {
          this.handleSelect(array[currentId + 1], false, idFocused);
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
          this.handleSelect(
            array[currentId - 1],
            idFocused,
            false,
            onShowSelectedIncludedView([array[currentId - 1]])
          );
        } else {
          this.handleSelect(array[currentId - 1], idFocused, false);
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

  handleDoubleClick = (id) => {
    const { isIncluded, onDoubleClick } = this.props;

    if (!isIncluded) {
      onDoubleClick && onDoubleClick(id);
    }
  };

  handleClick = (e, item) => {
    const {
      onSelectionChanged,
      openIncludedViewOnSelect,
      showIncludedViewOnSelect,
      keyProperty,
      updateQuickActions,
      selected,
      onSelect,
      onDeselect,
    } = this.props;
    const id = item[keyProperty];
    let selectionValue = false;

    if (e.button === 0) {
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
        updateQuickActions && updateQuickActions(id);
        newSelection = [id];
        onSelect(id);
      }

      if (onSelectionChanged) {
        onSelectionChanged(newSelection);
      }

      selectionValue = newSelection.length > 0;
    }
    selectionValue = true;

    if (openIncludedViewOnSelect) {
      showIncludedViewOnSelect({
        showIncludedView: selectionValue && item.supportIncludedViews,
        forceClose: !selectionValue,
        windowType: item.supportIncludedViews
          ? item.includedView.windowType || item.includedView.windowId
          : null,
        viewId: item.supportIncludedViews ? item.includedView.viewId : '',
      });
    }
  };

  handleRightClick = (e, id, fieldName, supportZoomInto, supportFieldEdit) => {
    e.preventDefault();

    const { selected } = this.props;
    const { clientX, clientY } = e;

    if (selected.indexOf(id) > -1) {
      this.setContextMenu(
        clientX,
        clientY,
        fieldName,
        supportZoomInto,
        supportFieldEdit
      );
    } else {
      this.handleSelect(id, null, null, () => {
        this.setContextMenu(
          clientX,
          clientY,
          fieldName,
          supportZoomInto,
          supportFieldEdit
        );
      });
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

      columns,
      rows,
      selected,
      onItemChange,
      onSelect,
      onRowCollapse,
      collapsedRows,
      collapsedParentRows,
    } = this.props;

    if (!rows.length || !columns.length) {
      return null;
    }

    this.rowRefs = {};

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
          entity,
          windowId,
          mainTable,
          indentSupported,
          selected,
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

            this.rowRefs[keyProp] = c;
          }
        }}
        keyProperty={item[keyProperty]}
        rowId={item[keyProperty]}
        tabId={tabId}
        onDoubleClick={this.handleDoubleClick}
        onClick={this.handleClick}
        handleRightClick={this.handleRightClick}
        changeListenOnTrue={this.setListenTrue}
        changeListenOnFalse={this.setListenFalse}
        newRow={i === rows.length - 1 ? newRow : false}
        isSelected={
          (selected &&
            (selected.indexOf(item[keyProperty]) > -1 ||
              selected[0] === 'all')) ||
          (selected && !selected[0] && focusOnFieldName && i === 0)
        }
        handleSelect={onSelect}
        contextType={item.type}
        caption={item.caption ? item.caption : ''}
        colspan={item.colspan}
        notSaved={item.saveStatus && !item.saveStatus.saved}
        getSizeClass={getSizeClass}
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
      size,
      handleChangePage,
      pageLength,
      page,
      mainTable,
      updateDocList,
      sort,
      orderBy,
      toggleFullScreen,
      fullScreen,
      tabIndex,
      indentSupported,
      isModal,
      queryLimitHit,
      supportQuickInput,
      tabInfo,
      allowShortcut,
      disablePaginationShortcuts,
      hasIncluded,
      blurOnIncludedView,
      toggleState,
      spinnerVisible,
      rows,
      selected,
      onHandleZoomInto,
      onSelect,
      onSelectAll,
      onDeselectAll,
      onGetAllLeaves,
      tableRefreshToggle,
    } = this.props;

    const { contextMenu, promptOpen, isBatchEntry } = this.state;

    let showPagination = page && pageLength;
    if (currentDevice.type === 'mobile' || currentDevice.type === 'tablet') {
      showPagination = false;
    }

    return (
      <div
        ref={this.setWrapperRef}
        className={classnames('table-flex-wrapper', {
          'col-12': toggleState === 'grid' || toggleState == null,
          'col-6': toggleState === 'all',
          'd-none': toggleState === 'map',
        })}
      >
        <div
          className={classnames({
            'table-flex-wrapper-row': mainTable,
          })}
        >
          {contextMenu.open && (
            <TableContextMenu
              {...contextMenu}
              {...{
                docId,
                windowId,
                mainTable,
                updateDocList,
              }}
              selected={selected}
              blur={this.closeContextMenu}
              tabId={tabId}
              deselect={onDeselectAll}
              handleFieldEdit={this.handleFieldEdit}
              handleAdvancedEdit={this.handleAdvancedEdit}
              onOpenNewTab={handleOpenNewTab}
              handleDelete={
                !isModal && (tabInfo && tabInfo.allowDelete)
                  ? this.handleDelete
                  : null
              }
              handleZoomInto={onHandleZoomInto}
            />
          )}
          {!readonly && (
            <TableFilter
              openTableModal={this.openTableModal}
              {...{
                toggleFullScreen,
                fullScreen,
                docId,
                tabIndex,
                isBatchEntry,
                supportQuickInput,
                selected,
              }}
              docType={windowId}
              tabId={tabId}
              handleBatchEntryToggle={this.handleBatchEntryToggle}
              allowCreateNew={tabInfo && tabInfo.allowCreateNew}
              wrapperHeight={this.wrapper && this.wrapper.offsetHeight}
            />
          )}

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
                  }}
                  cols={columns}
                  windowType={windowId}
                  getSizeClass={getSizeClass}
                  deselect={onDeselectAll}
                />
              </thead>
              <tbody>{this.renderTableBody()}</tbody>
              <tfoot ref={this.setTfootRef} />
            </table>

            {!spinnerVisible && this.renderEmptyInfo(rows)}
          </div>

          {
            // Other 'table-flex-wrapped' components
            // like selection attributes
            this.props.children
          }
        </div>
        {showPagination && (
          <div onClick={this.handleClickOutside}>
            <TablePagination
              {...{
                handleChangePage,
                size,
                page,
                orderBy,
                queryLimitHit,
                disablePaginationShortcuts,
              }}
              onChangePage={handleChangePage}
              selected={selected}
              pageLength={pageLength}
              rowLength={rows.length}
              handleSelectAll={onSelectAll}
              handleSelectRange={onSelect}
              onDeselectAll={onDeselectAll}
            />
          </div>
        )}
        {promptOpen && (
          <Prompt
            title="Delete"
            text="Are you sure?"
            buttons={{ submit: 'Delete', cancel: 'Cancel' }}
            onCancelClick={this.handlePromptCancelClick}
            selected={selected}
            onSubmitClick={this.handlePromptSubmitClick}
          />
        )}

        {allowShortcut && (
          <DocumentListContextShortcuts
            windowId={windowId}
            tabId={tabId}
            selected={selected}
            onAdvancedEdit={
              selected && selected.length > 0 && selected[0]
                ? this.handleAdvancedEdit
                : null
            }
            onOpenNewTab={
              selected && selected.length > 0 && selected[0] && mainTable
                ? handleOpenNewTab
                : null
            }
            onDelete={
              selected && selected.length > 0 && selected[0]
                ? this.handleDelete
                : null
            }
            onGetAllLeaves={onGetAllLeaves}
            onIndent={this.handleShortcutIndent}
          />
        )}

        {allowShortcut && !readonly && (
          <TableContextShortcuts
            handleToggleQuickInput={this.handleBatchEntryToggle}
            handleToggleExpand={toggleFullScreen}
          />
        )}
        {isMobileOrTablet && rows.length > MOBILE_TABLE_SIZE_LIMIT && (
          <span className="text-danger">
            {counterpart.translate('view.limitTo', {
              limit: MOBILE_TABLE_SIZE_LIMIT,
              total: rows.length,
            })}
          </span>
        )}
      </div>
    );
  }
}

Table.propTypes = componentPropTypes;

const clickOutsideConfig = {
  excludeScrollbar: true,
};

export default onClickOutside(Table, clickOutsideConfig);
