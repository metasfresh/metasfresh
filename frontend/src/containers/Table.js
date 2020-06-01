import update from 'immutability-helper';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

import { getZoomIntoWindow, deleteRequest } from '../api';
import { getTableId, getTable } from '../reducers/tables';

import {
  updateTableSelection,
  deselectTableItems,
  collapseTableRow,
} from '../actions/TableActions';
import { deleteLocal, openModal } from '../actions/WindowActions';

import { containerPropTypes } from '../utils/tableHelpers';
import { mapIncluded } from '../utils/documentListHelper';

import Table from '../components/table/Table';

class TableContainer extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {};
    this._isMounted = false;
  }

  componentDidMount() {
    this._isMounted = true;
  }

  componentDidUpdate(prevProps) {
    const { mainTable, open, rows } = this.props;

    if (rows.length && prevProps.rows.length === 0) {
      document.getElementsByClassName('js-table')[0].focus();

      setTimeout(() => {
        if (this._isMounted) {
          // TODO: Figure a better way to do this https://github.com/metasfresh/metasfresh/issues/1679
          this.setState({
            tableRefreshToggle: !this.state.mounted,
          });
        }
      }, 1);
    }

    if (!this._isMounted) {
      return;
    }

    if (mainTable && open) {
      this.table.focus();
    }
  }

  componentWillUnmount() {
    const {
      showIncludedViewOnSelect,
      viewId,
      windowType,
      isIncluded,
    } = this.props;

    this._isMounted = false;

    this.handleDeselectAll();

    if (showIncludedViewOnSelect && !isIncluded) {
      showIncludedViewOnSelect({
        showIncludedView: false,
        windowType,
        viewId,
      });
    }
  }

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

  handleSelect = (ids) => {
    const { updateTableSelection, windowId, viewId, docId, tabId } = this.props;
    let newSelected = [];

    if (ids) {
      if (!ids.splice) {
        newSelected = [ids];
      } else {
        newSelected = ids;
      }
    }

    updateTableSelection(
      getTableId({ windowId, viewId, docId, tabId }),
      newSelected
    );

    return newSelected;
  };

  handleSelectAll = () => {
    const { keyProperty, rows } = this.props;
    const property = keyProperty ? keyProperty : 'rowId';
    const toSelect = rows.map((item) => item[property]);

    this.handleSelect(toSelect);
  };

  handleDeselect = (id) => {
    const { deselectTableItems, windowId, viewId, selected } = this.props;
    const tableId = getTableId({ windowId, viewId });
    const index = selected.indexOf(id);

    // TODO: Do we need this returned value ? Maybe we can handle
    // this in redux only?
    const newSelected = update(selected, { $splice: [[index, 1]] });

    if (!newSelected.length) {
      deselectTableItems(tableId, [id]);
    }

    return newSelected;
  };

  handleDeselectAll = (callback) => {
    const { deselectTableItems, windowId, viewId, docId, tabId } = this.props;

    callback && callback();

    deselectTableItems(getTableId({ windowId, viewId, docId, tabId }), []);
  };

  showSelectedIncludedView = (selected) => {
    const {
      showIncludedViewOnSelect,
      openIncludedViewOnSelect,
      rows,
    } = this.props;

    if (openIncludedViewOnSelect && selected.length === 1) {
      rows.forEach((item) => {
        if (item.id === selected[0]) {
          showIncludedViewOnSelect({
            showIncludedView: item.supportIncludedViews,
            windowType: item.supportIncludedViews
              ? item.includedView.windowType || item.includedView.windowId
              : null,
            viewId: item.supportIncludedViews ? item.includedView.viewId : '',
          });
        }
      });
    }
  };

  handleItemChange = (rowId, prop, value) => {
    const { mainTable, keyProperty, onRowEdited, rows } = this.props;

    if (mainTable) {
      if (!rows.length) return;

      rows
        .filter((row) => row[keyProperty] === rowId)
        .map((item) => {
          let field = item.fieldsByName[prop];

          if (field) {
            field.value = value;
          }
        });
    }

    onRowEdited && onRowEdited(true);
  };

  openModal = (windowType, tabId, rowId) => {
    const { openModal } = this.props;

    openModal('Add new', windowType, 'window', tabId, rowId);
  };

  openTableModal = () => {
    const { openModal, windowId, tabId } = this.props;

    openModal('Add new', windowId, 'window', tabId, 'NEW');
  };

  /**
   * @method handleAdvancedEdit
   * @summary Handles advanced edit - i.e case when ALT+E key combinations are being used
   *          Active only on subtables
   */
  handleAdvancedEdit = () => {
    const { openModal, windowId, tabId, docId, selected } = this.props;

    if (docId) {
      openModal('Advanced edit', windowId, 'window', tabId, selected[0], true);
    }
  };

  handlePromptSubmit = (selected) => {
    const { deleteLocal, windowId, docId, updateDocList, tabId } = this.props;

    this.handleSelect();

    // TODO: This should be an action creator
    deleteRequest(
      'window',
      windowId,
      docId ? docId : null,
      docId ? tabId : null,
      selected
    ).then((response) => {
      if (docId) {
        // TODO: Check what this is doing
        deleteLocal(tabId, selected, 'master', response);
      } else {
        updateDocList();
      }
    });
  };

  handleZoomInto = (fieldName) => {
    const { entity, windowId, docId, tabId, viewId, selected } = this.props;

    getZoomIntoWindow(
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
          `/window/${res.data.documentPath.windowId}/${
            res.data.documentPath.documentId
          }`,
          '_blank'
        );
    });
  };

  handleRowCollapse = (node, collapse) => {
    const { collapseTableRow, windowId, viewId, tabId, docId } = this.props;
    const tableId = getTableId({ windowId, viewId, docId, tabId });

    collapseTableRow({ tableId, collapse, node });
  };

  render() {
    return (
      <Table
        {...this.props}
        {...this.state}
        onShowSelectedIncludedView={this.showSelectedIncludedView}
        onHandleZoomInto={this.handleZoomInto}
        onPromptSubmit={this.handlePromptSubmit}
        onItemChange={this.handleItemChange}
        onSelect={this.handleSelect}
        onSelectAll={this.handleSelectAll}
        onDeselectAll={this.handleDeselectAll}
        onDeselect={this.handleDeselect}
        onRowCollapse={this.handleRowCollapse}
        onGetAllLeaves={this.getAllLeaves}
      />
    );
  }
}

TableContainer.propTypes = containerPropTypes;

const mapStateToProps = (state, props) => {
  const { windowId, docId, tabId, viewId } = props;
  const tableId = getTableId({ windowId, viewId, docId, tabId });
  const table = getTable(state, tableId);

  return {
    rows: table.rows,
    columns: table.columns,
    selected: table.selected,
    pending: table.dataPending,
    collapsedParentRows: table.collapsedParentRows,
    collapsedRows: table.collapsedRows,
    collapsedArrayMap: table.collapsedArrayMap,
    activeSort: table.activeSort,
    emptyText: table.emptyText,
    emptyHint: table.emptyHint,
    indentSupported: table.indentSupported,
    collapsible: table.collapsible,
    keyProperty: table.keyProperty,
    size: table.size,

    allowShortcut: state.windowHandler.allowShortcut,
    allowOutsideClick: state.windowHandler.allowOutsideClick,
    modalVisible: state.windowHandler.modal.visible,
    isGerman:
      state.appHandler.me.language && state.appHandler.me.language.key
        ? state.appHandler.me.language.key.includes('de')
        : false,
  };
};

export { TableContainer };
export default connect(
  mapStateToProps,
  {
    collapseTableRow,
    deleteLocal,
    deselectTableItems,
    openModal,
    updateTableSelection,
  },
  false,
  { forwardRef: true }
)(TableContainer);
