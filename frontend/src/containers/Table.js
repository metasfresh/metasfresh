import update from 'immutability-helper';
import * as _ from 'lodash';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

import { getZoomIntoWindow, deleteRequest } from '../api';
import { getTableId, getTable } from '../reducers/tables';

import {
  updateTableSelection,
  collapseTableRows,
} from '../actions/TableActions';
import {
  deleteLocal,
  openModal,
  deselectTableItems,
} from '../actions/WindowActions';

import { containerPropTypes } from '../utils/tableHelpers';
import { mapIncluded } from '../utils/documentListHelper';

import Table from '../components/table/Table';

const EMPTY_ARRAY = [];

class TableContainer extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {};
  }

  // componentDidMount() {
  //   const { /*rowData, tabId*/ rows } = this.props;
  //   //selecting first table elem while getting indent data
  //   // this._isMounted = true;

  //   // if (rowData.get(`${tabId}`)) {
  //   // if (rows.length) {
  //     // console.log('HERE')
  //     // this.getIndentData(true);
  //   // }
  // }

  // TODO: Figure out what this is for and why ?
  // UNSAFE_componentWillReceiveProps(nextProps) {
  //   if (this.state.rows.length && !nextProps.cols) {
  //     this.setState({ rows: [] });
  //   }
  // }

  componentDidUpdate(prevProps, prevState) {
    const {
      // dispatch,
      mainTable,
      open,
      // rowData,
      defaultSelected,
      // disconnectFromState,
      // windowId,
      // refreshSelection,
      openIncludedViewOnSelect,
      viewId,
      // tabId,
      isModal,
      hasIncluded,
      page,

      selected,
      rows,
    } = this.props;
    // const { selected, rows } = this.state;
    // console.log('ROWDATA: ', rowData)

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

    // TODO: Automatically select first row if `selected` is empty
    /**
     * Selection by default of first row if nothing selected
     */
    // if (
    //   (_.isEmpty(defaultSelected) || _.isEmpty(selected)) &&
    //   selected[0] === undefined &&
    //   !_.isEmpty(rows)
    // ) {
      // this.setState({ selected: [rows[0].id] });
      // dispatch(
      //   updateTableSelection({
      //     tableId: getTableId({ windowType: windowId, viewId }),
      //     ids: [rows[0].id],
      //   })
      // );

    // } // end of selection for the first row if nothing selected

    const selectedEqual = _.isEqual(prevState.selected, selected);
    // const defaultSelectedEqual = _.isEqual(
    //   prevProps.defaultSelected,
    //   defaultSelected
    // );

    if (!this._isMounted) {
      return;
    }

    // if (rows && !_.isEqual(prevState.rows, rows)) {
      // this.setState({
      //   collapsedRows: EMPTY_ARRAY,
      //   collapsedParentsRows: EMPTY_ARRAY,
      // });

      if (isModal && !hasIncluded) {
        let firstRow = rows[0];

        if (firstRow) {
          if (openIncludedViewOnSelect) {
            // this.showSelectedIncludedView([firstRow.id]);
          }

          if (firstRow.id && !selectedEqual) {
            // this.selectOneProduct(firstRow.id);
          }
        }
      }
    // }

    // TODO: in reducer/view's action creator call AC to rebuild collapsed rows
    // else if (page !== prevProps.page) {
    // this.setState({
    //   collapsedRows: EMPTY_ARRAY,
    //   collapsedParentsRows: EMPTY_ARRAY,
    // });
    // }

    if (mainTable && open) {
      this.table.focus();
    }

    // TODO: `refreshSelection` seems to be unused now
    // if (
    //   (!defaultSelectedEqual && !selectedEqual) ||
    //   (refreshSelection && prevProps.refreshSelection !== refreshSelection)
    // ) {
    //   this.setState({
    //     selected:
    //       defaultSelected && defaultSelected !== null ? defaultSelected : [],
    //   });

    // TODO: No table will be disconnected from state anymore, so this has to be rewritten
    // } else if (!disconnectFromState && !selectedEqual && selected.length) {
    //   dispatch(
    //     updateTableSelection({
    //       tableId: getTableId({ windowType: windowId, viewId }),
    //       ids: selected,
    //     })
    //   );
    //   dispatch(
    //     selectTableItems({
    //       windowType: windowId,
    //       viewId,
    //       ids: selected,
    //     })
    //   );
    // }

    // TODO I'm pretty sure we can handle this in reducer
    if (prevProps.viewId !== viewId && rows.length) { // && rowData.get(`${tabId}`)) {
      // if (defaultSelected && defaultSelected.length === 0) {
        // this.setState({ selected: [] });

        // dispatch(
        //   updateTableSelection({
        //     tableId: getTableId({ windowType: windowId, viewId }),
        //     ids: EMPTY_ARRAY,
        //   })
        // );
      // }

      // this.setState({
      //   collapsedRows: EMPTY_ARRAY,
      //   collapsedParentsRows: EMPTY_ARRAY,
      // });

      // const firstLoad =
      //   prevProps.rowData.get(`${tabId}`) &&
      //   prevProps.rowData.get(`${tabId}`).size &&
      //   rowData.get(`${tabId}`).size
      //     ? false
      //     : true;

      // this.getIndentData(firstLoad);
    // } else if (rowData.get(`${tabId}`) && !is(prevProps.rowData, rowData)) {
      // let firstLoad = rowData.get(`${tabId}`).size ? false : true;

      // if (
      //   prevProps.rowData.get(`${tabId}`) &&
      //   !prevProps.rowData.get(`${tabId}`).size &&
      //   rowData.get(`${tabId}`).size
      // ) {
      //   firstLoad = true;
      // }

      // this.getIndentData(firstLoad);
    }
  }

  componentWillUnmount() {
    const {
      showIncludedViewOnSelect,
      viewId,
      windowType,
      isIncluded,
    } = this.props;

    // this._isMounted = false;

    this.handleDeselectAll();

    if (showIncludedViewOnSelect && !isIncluded) {
      showIncludedViewOnSelect({
        showIncludedView: false,
        windowType,
        viewId,
      });
    }
  }

  getIndentData = (selectFirst) => {
    // const {
    //   // rowData,
    //   // tabId,
    //   indentSupported,
    //   collapsible,
    //   expandedDepth,
    //   keyProperty,
    //   rows,
    //   selected,
    // } = this.props;
    // const { selected } = this.state;
    // let rowsData = [];


    // TODO: There's a layout property `supportTree`, that we should use


    // if (indentSupported && rows.length) {//rowData.get(`${tabId}`).size) {
      //rowsData = getRowsData(rowData.get(`${tabId}`));

      // let stateChange = {
      //   // rows: rowsData,
      //   pendingInit: !rows.length, //!rowsData,
      // };

      // if (selectFirst) {
      //   stateChange = {
      //     ...stateChange,
      //     // collapsedParentsRows: [],
      //     // collapsedRows: [],
      //   };
      // }

      // this.setState(stateChange, () => {
      // const { rows } = this.state;
      // const firstRow = rows[0];

      // let updatedParentsRows = [...this.state.collapsedParentsRows];
      // let updatedRows = [...this.state.collapsedRows];

      // if (firstRow && selectFirst) {
      //   let selectedIndex = 0;
      //   if (
      //     selected &&
      //     selected.length === 1 &&
      //     selected[0] &&
      //     firstRow.id !== selected[0]
      //   ) {
      //     selectedIndex = _.findIndex(rows, (row) => row.id === selected[0]);
      //   }

      //   if (!selectedIndex) {
      //     this.handleSelectOne(rows[0].id);
      //   }

        // TODO: 
        // document.getElementsByClassName('js-table')[0].focus();
      // }

      // let mapCollapsed = [];

      // if (collapsible && rows && rows.length) {
      //   rows.map((row) => {
      //     if (row.indent.length >= expandedDepth && row.includedDocuments) {
      //       mapCollapsed = mapCollapsed.concat(collapsedMap(row));
      //       updatedParentsRows = updatedParentsRows.concat(row[keyProperty]);
      //     }
      //     if (row.indent.length > expandedDepth) {
      //       updatedRows = updatedRows.concat(row[keyProperty]);
      //     }
      //   });

      //   const updatedState = {
      //     // dataHash: uuid(),
      //   };

      //   if (mapCollapsed.length) {
      //     updatedState.collapsedArrayMap = mapCollapsed;
      //   }
      //   if (updatedRows.length) {
      //     updatedState.collapsedRows = updatedRows;
      //   }
      //   if (updatedParentsRows.length) {
      //     updatedState.collapsedParentsRows = updatedParentsRows;
      //   }

      //   if (Object.keys(updatedState).length) {
      //     this.setState({ ...updatedState });
      //   }
      // }
      // });
    // } else {
      // rowsData =
      //   rowData.get(`${tabId}`) && rowData.get(`${tabId}`).size
      //     ? rowData.get(`${tabId}`).toArray()
      //     : [];

      // this.setState({
      //   // rows: rowsData,
      //   // dataHash: uuid(),
      //   // TODO: What is this for ?
      //   // pendingInit: !rowData.get(`${tabId}`),
      //   pendingInit: !rows.length,
      // });
    // }
  };

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
    const {
      updateTableSelection,
      windowId,
      // disconnectFromState,
      // tabInfo,
      viewId,
      // selected,
      docId,
      tabId,
    } = this.props;
    let newSelected = [];

    if (ids) {
      if (!ids.splice) {
        newSelected = [ids];
      } else {
        newSelected = ids;
      }
    }

    // if (tabInfo) {
    updateTableSelection({
      tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
      ids: newSelected,
    });
    // dispatch(
    //   selectTableItems({
    //     windowType: windowId,
    //     viewId,
    //     ids: newSelected,
    //   })
    // );
    // }

    // if (!disconnectFromState) {
    //     updateTableSelection({
    //       tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
    //       ids: newSelected,
    //     });
    //   // dispatch(
    //   //   selectTableItems({
    //   //     windowType: windowId,
    //   //     viewId,
    //   //     ids: newSelected,
    //   //   })
    //   // );
    // }

    // this.triggerFocus(idFocused, idFocusedDown);
    // });
    // cb && cb();

    return newSelected;
  };

  handleSelectAll = () => {
    const { keyProperty, rows } = this.props;
    const property = keyProperty ? keyProperty : 'rowId';
    const toSelect = rows.map((item) => item[property]);

    this.handleSelect(toSelect);
  };

  handleDeselect = (id) => {
    const {
      deselectTableItems,
      // tabInfo,
      windowId,
      viewId,
      selected,
    } = this.props;

    const index = selected.indexOf(id);

    // TODO: Move to redux only
    const newSelected = update(selected, { $splice: [[index, 1]] });

    if (/*tabInfo ||*/ !newSelected.length) {
      // TODO: Shouldn't this use `updateTableSelection` ?
      deselectTableItems([id], windowId, viewId);
    }

    return newSelected;
  };

  handleDeselectAll = (callback) => {
    const {
      updateTableSelection,
      // tabInfo,
      windowId,
      viewId,
      docId,
      tabId,
    } = this.props;

    callback && callback();

    // if (tabInfo) {
    updateTableSelection({
      tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
      ids: EMPTY_ARRAY,
    });
    // dispatch(
    //   selectTableItems({
    //     windowType: windowId,
    //     viewId,
    //     ids: [],
    //   })
    // );
    // }
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
    const {
      deleteLocal,
      // updateTableSelection,
      windowId,
      docId,
      updateDocList,
      tabId,
      // viewId,
    } = this.props;

    // updateTableSelection({
    //   tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
    //   ids: [],
    // });
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
    const {
      collapseTableRows,
      windowId,
      viewId,
      tabId,
      docId,
      keyProperty,
    } = this.props;
    const tableId = getTableId({ windowId: windowId, viewId, docId, tabId });

    collapseTableRows({ tableId, collapse, node, keyProperty });
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
  const tableId = getTableId({ windowType: windowId, viewId, docId, tabId });
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
    collapseTableRows,
    deleteLocal,
    deselectTableItems,
    openModal,
    updateTableSelection,
  },
  false,
  { forwardRef: true }
)(TableContainer);
