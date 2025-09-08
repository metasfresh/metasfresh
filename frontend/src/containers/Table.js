import counterpart from 'counterpart';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { getZoomIntoWindow, deleteRequest } from '../api';
import { containerPropTypes } from '../utils/tableHelpers';
import { mapIncluded } from '../utils/documentListHelper';
import { isGermanLanguage } from '../utils/locale';
import { getTableId, getTable } from '../reducers/tables';
import {
  updateTableSelection,
  deselectTableRows,
  collapseTableRow,
  setActiveSort,
} from '../actions/TableActions';
import { showIncludedView } from '../actions/ViewActions';
import { openModal, updatePropertyValue } from '../actions/WindowActions';

import Table from '../components/table/TableWrapper';

class TableContainer extends PureComponent {
  /**
   * @method getAllLeaves
   * @summary select parent and all it's leaves
   */
  getAllLeaves = () => {
    const { rows, selected } = this.props;
    let leafs = [];
    let leafsIds = [];

    rows.map((item) => {
      if (item.id === selected[0]) {
        leafs = mapIncluded(item);
      }
    });

    leafs.map((item) => {
      leafsIds = leafsIds.concat(item.id);
    });

    this.handleSelect(leafsIds);
  };

  handleSelect = (ids, cb) => {
    const {
      updateTableSelection,
      windowId,
      viewId,
      docId,
      tabId,
      keyProperty,
      isModal,
      parentView,
    } = this.props;
    let newSelected = [];

    if (ids) {
      if (!ids.splice) {
        newSelected = [ids];
      } else {
        newSelected = ids;
      }
    }

    updateTableSelection({
      id: getTableId({ windowId, viewId, docId, tabId }),
      selection: newSelected,
      keyProperty,
      windowId,
      viewId,
      isModal,
      parentView,
    }).then(() => {
      cb && cb();
    });
  };

  handleSelectAll = () => {
    const { keyProperty, rows } = this.props;
    const property = keyProperty ? keyProperty : 'rowId';
    const toSelect = rows.map((item) => item[property]);

    this.handleSelect(toSelect);
  };

  handleDeselect = (id) => {
    const { deselectTableRows, windowId, viewId, docId, tabId, isModal } =
      this.props;
    const tableId = getTableId({ windowId, viewId, docId, tabId });

    deselectTableRows({
      id: tableId,
      selection: [id],
      windowId,
      viewId,
      isModal,
    });
  };

  handleDeselectAll = (callback) => {
    const { deselectTableRows, windowId, viewId, docId, tabId, isModal } =
      this.props;

    callback && callback();

    deselectTableRows({
      id: getTableId({ windowId, viewId, docId, tabId }),
      selection: [],
      windowId,
      viewId,
      isModal,
    });
  };

  /**
   * @method openTableModal
   * @summary Open `Add new` modal
   */
  openTableModal = () => {
    const { openModal, windowId, tabId } = this.props;

    openModal({
      title: 'Add new',
      windowId,
      modalType: 'window',
      tabId,
      rowId: 'NEW',
    });
  };

  /**
   * @method handleAdvancedEdit
   * @summary Handles advanced edit - i.e case when ALT+E key combinations are being used
   *          Active only on subtables
   */
  handleAdvancedEdit = () => {
    const { openModal, windowId, tabId, docId, selected } = this.props;

    if (docId) {
      openModal({
        title: counterpart.translate('window.advancedEdit.caption'),
        windowId,
        modalType: 'window',
        tabId,
        rowId: selected[0],
        isAdvanced: true,
      });
    }
  };

  /**
   * @method handlePromptSubmit
   * @summary delete selected items
   */
  handlePromptSubmit = (selected) => {
    const { windowId, docId, updateDocList, tabId } = this.props;

    this.handleSelect();

    // TODO: This should be an action creator
    deleteRequest(
      'window',
      windowId,
      docId ? docId : null,
      docId ? tabId : null,
      selected
    )
      .then((response) => {
        // TODO: In the future we probably shouldn't refresh the whole list
        if (response.data[0] && response.data[0].includedTabsInfo) {
          Object.keys(response.data[0].includedTabsInfo).includes(tabId) &&
            updateDocList();
        }

        // when something is removed.
        if (!docId) {
          updateDocList();
        }
      })
      // for instance removing a newly added tab row without filling any fields
      .catch(() => {
        if (docId) {
          updateDocList();
        }
      });
  };

  /**
   * @method handleZoomInto
   * @summary open new window with details view of the selected row
   */
  handleZoomInto = (fieldName) => {
    const { entity, windowId, docId, tabId, viewId, selected } = this.props;

    return getZoomIntoWindow(
      entity,
      windowId,
      docId,
      entity === 'window' ? tabId : viewId,
      selected[0],
      fieldName
    ).then((res) => {
      res &&
        res.data &&
        window.open(
          `/window/${res.data.documentPath.windowId}/${res.data.documentPath.documentId}`,
          '_blank'
        );
    });
  };

  /**
   * @method handleRowCollapse
   * @summary toggle table rows
   */
  handleRowCollapse = (node, collapse) => {
    const { collapseTableRow, windowId, viewId, tabId, docId } = this.props;
    const tableId = getTableId({ windowId, viewId, docId, tabId });

    collapseTableRow({ tableId, collapse, node });
  };

  render() {
    return (
      <Table
        {...this.props}
        onHandleZoomInto={this.handleZoomInto}
        onPromptSubmit={this.handlePromptSubmit}
        onSelect={this.handleSelect}
        onSelectAll={this.handleSelectAll}
        onDeselectAll={this.handleDeselectAll}
        onDeselect={this.handleDeselect}
        onRowCollapse={this.handleRowCollapse}
        onGetAllLeaves={this.getAllLeaves}
        onHandleAdvancedEdit={this.handleAdvancedEdit}
        onOpenTableModal={this.openTableModal}
      />
    );
  }
}

TableContainer.propTypes = containerPropTypes;

const mapStateToProps = (state, props) => {
  const { windowId, docId, tabId, viewId } = props;
  const tableId = getTableId({ windowId, viewId, docId, tabId });
  const table = getTable(state, tableId);
  const modalVisible = state.windowHandler.modal.visible;
  let handleShortcuts = state.windowHandler.allowShortcut;

  // we don't have to worry about shortcuts if table is behind a modal
  if (modalVisible && !props.isModal) {
    handleShortcuts = false;
  }

  return {
    tableId,
    rows: table.rows,
    columns: table.columns,
    selected: table.selected,
    collapsedParentRows: table.collapsedParentRows,
    collapsedRows: table.collapsedRows,
    activeSort: table.activeSort,
    emptyText: table.emptyText,
    emptyHint: table.emptyHint,
    indentSupported: table.indentSupported,
    collapsible: table.collapsible,
    keyProperty: table.keyProperty,
    size: table.size,
    navigationActive: table.navigationActive,
    allowShortcut: handleShortcuts,
    allowOutsideClick: state.windowHandler.allowOutsideClick,
    modalVisible,
    isGerman: isGermanLanguage(state.appHandler.me.language),
    pending: table.pending,
  };
};

export { TableContainer };
export default connect(
  mapStateToProps,
  {
    collapseTableRow,
    deselectTableRows,
    openModal,
    updateTableSelection,
    updatePropertyValue,
    showIncludedView,
    setActiveSort,
  },
  false,
  { forwardRef: true }
)(TableContainer);
