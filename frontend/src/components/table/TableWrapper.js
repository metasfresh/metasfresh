import React, { PureComponent } from 'react';
import onClickOutside from 'react-onclickoutside';
import classnames from 'classnames';
import currentDevice from 'current-device';
import counterpart from 'counterpart';

import { handleOpenNewTab, componentPropTypes } from '../../utils/tableHelpers';

import Prompt from '../app/Prompt';
import DocumentListContextShortcuts from '../keyshortcuts/DocumentListContextShortcuts';
import TableContextShortcuts from '../keyshortcuts/TableContextShortcuts';
import TableContextMenu from './TableContextMenu';
import TableFilter from './TableFilter';
import Table from './Table';
import TablePagination from './TablePagination';

const MOBILE_TABLE_SIZE_LIMIT = 30; // subjective number, based on empiric testing
const isMobileOrTablet =
  currentDevice.type === 'mobile' || currentDevice.type === 'tablet';

class TableWrapper extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      contextMenu: {
        open: false,
        x: 0,
        y: 0,
        fieldName: null,
        supportZoomInto: false,
        supportFieldEdit: false,
      },
      promptOpen: false,
      isBatchEntry: false,
    };
  }

  closeContextMenu = () => {
    this.setState({
      contextMenu: {
        ...this.state.contextMenu,
        open: false,
      },
    });
  };

  /**
   * @method handleSelect
   * @summary select row and focus on cell
   *
   * @param {number} id
   * @param {*} idFocused - index of row's cell to select
   * @param {*} idFocusedDown - index of row's cell to select in case of
   * range selection
   * @param {func} cb - callback function
   */
  handleSelect = (id, idFocused, idFocusedDown, cb) => {
    const { onSelect } = this.props;

    onSelect(id, () => {
      cb && cb();

      this.triggerFocus(idFocused, idFocusedDown);
    });
  };

  setContextMenu = (
    clientX,
    clientY,
    fieldName,
    supportZoomInto,
    supportFieldEdit
  ) => {
    this.setState({
      contextMenu: {
        ...this.state.contextMenu,
        x: clientX,
        y: clientY,
        open: true,
        fieldName,
        supportZoomInto,
        supportFieldEdit,
      },
    });
  };

  setWrapperRef = (ref) => {
    this.wrapper = ref;
  };

  setTableRef = (ref) => {
    this.table = ref;
  };

  triggerFocus = (idFocused, idFocusedDown) => {
    if (this.table) {
      const rowsSelected = this.table.table.getElementsByClassName(
        'row-selected'
      );

      if (rowsSelected.length > 0) {
        if (typeof idFocused === 'number') {
          rowsSelected[0].children[idFocused].focus();
        }
        if (typeof idFocusedDown === 'number') {
          rowsSelected[rowsSelected.length - 1].children[idFocusedDown].focus();
        }
      }
    }
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

  handleClickOutside = (event) => {
    const {
      showIncludedView,
      viewId,
      windowId,
      inBackground,
      allowOutsideClick,
      limitOnClickOutside,
      onDeselectAll,
      isModal,
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

      const identifier = isModal ? viewId : windowId;

      showIncludedView({
        id: identifier,
        showIncludedView: false,
        windowId,
        viewId,
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

  render() {
    const {
      windowId,
      docId,
      tabId,
      readonly,
      size,
      handleChangePage,
      pageLength,
      page,
      mainTable,
      updateDocList,
      orderBy,
      toggleFullScreen,
      fullScreen,
      tabIndex,
      isModal,
      queryLimitHit,
      supportQuickInput,
      tabInfo,
      allowShortcut,
      disablePaginationShortcuts,
      toggleState,
      rows,
      selected,
      onHandleZoomInto,
      onSelect,
      onSelectAll,
      onDeselectAll,
      onGetAllLeaves,
      onHandleAdvancedEdit,
      onOpenTableModal,
    } = this.props;

    const { contextMenu, promptOpen, isBatchEntry } = this.state;

    let showPagination = page && pageLength;
    if (currentDevice.type === 'mobile' || currentDevice.type === 'tablet') {
      showPagination = false;
    }

    this.rowRefs = {};

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
              handleAdvancedEdit={onHandleAdvancedEdit}
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
              openTableModal={onOpenTableModal}
              {...{
                toggleFullScreen,
                fullScreen,
                docId,
                tabIndex,
                isBatchEntry,
                supportQuickInput,
              }}
              docType={windowId}
              tabId={tabId}
              handleBatchEntryToggle={this.handleBatchEntryToggle}
              allowCreateNew={tabInfo && tabInfo.allowCreateNew}
              wrapperHeight={this.wrapper && this.wrapper.offsetHeight}
            />
          )}

          <Table
            {...this.props}
            handleSelect={this.handleSelect}
            onRightClick={this.handleRightClick}
            rowRefs={this.rowRefs}
            ref={this.setTableRef}
          />

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
                ? onHandleAdvancedEdit
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

TableWrapper.propTypes = componentPropTypes;

const clickOutsideConfig = {
  excludeScrollbar: true,
};

export default onClickOutside(TableWrapper, clickOutsideConfig);
