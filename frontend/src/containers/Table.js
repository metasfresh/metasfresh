import update from 'immutability-helper';
import { is } from 'immutable';
import * as _ from 'lodash';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

import { updateTableSelection } from '../actions/TableActions';
import { deleteRequest } from '../actions/GenericActions';
import { getTableId, getTable } from '../reducers/tables';
import {
  deleteLocal,
  openModal,
  // selectTableItems,
  deselectTableItems,
} from '../actions/WindowActions';
import { getZoomIntoWindow } from '../api';
import {
  // getSizeClass,
  // handleCopy,
  handleOpenNewTab,
  containerPropTypes,
  // constructorFn,
} from '../utils/tableHelpers';
import {
  getRowsData,
  mapIncluded,
  collapsedMap,
} from '../utils/documentListHelper';

import Table from '../components/table/Table';

const EMPTY_ARRAY = [];

class TableContainer extends PureComponent {
  constructor(props) {
    super(props);

    // const constr = constructorFn.bind(this);
    // constr(props);
    this.state = {
      pendingInit: false,
    };
  }

  componentDidMount() {
    const { /*rowData, tabId*/ rows } = this.props;
    //selecting first table elem while getting indent data
    // this._isMounted = true;

    // if (rowData.get(`${tabId}`)) {
    if (rows.length) {
      console.log('HERE')
      this.getIndentData(true);
    }
  }

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
      tabId,
      isModal,
      hasIncluded,
      page,

      selected,
      rows,
    } = this.props;
    // const { selected, rows } = this.state;
    // console.log('ROWDATA: ', rowData)

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
    else if (page !== prevProps.page) {
      // this.setState({
      //   collapsedRows: EMPTY_ARRAY,
      //   collapsedParentsRows: EMPTY_ARRAY,
      // });
    }

    if (mainTable && open) {
      this.table.focus();
    }

    // if (
    //   (!defaultSelectedEqual && !selectedEqual) ||
    //   (refreshSelection && prevProps.refreshSelection !== refreshSelection)
    // ) {
    //   this.setState({
    //     selected:
    //       defaultSelected && defaultSelected !== null ? defaultSelected : [],
    //   });
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
      if (defaultSelected && defaultSelected.length === 0) {
        // this.setState({ selected: [] });

        // dispatch(
        //   updateTableSelection({
        //     tableId: getTableId({ windowType: windowId, viewId }),
        //     ids: EMPTY_ARRAY,
        //   })
        // );
      }

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

    this.deselectAllProducts();
    if (showIncludedViewOnSelect && !isIncluded) {
      showIncludedViewOnSelect({
        showIncludedView: false,
        windowType,
        viewId,
      });
    }
  }

  getIndentData = (selectFirst) => {
    const {
      // rowData,
      // tabId,
      indentSupported,
      collapsible,
      expandedDepth,
      keyProperty,
      rows,
      selected,
    } = this.props;
    // const { selected } = this.state;
    let rowsData = [];

    if (indentSupported && rows.length) {//rowData.get(`${tabId}`).size) {
      //rowsData = getRowsData(rowData.get(`${tabId}`));

      let stateChange = {
        // rows: rowsData,
        pendingInit: !rows.length, //!rowsData,
      };

      if (selectFirst) {
        stateChange = {
          ...stateChange,
          // collapsedParentsRows: [],
          // collapsedRows: [],
        };
      }

      this.setState(stateChange, () => {
        // const { rows } = this.state;
        const firstRow = rows[0];

        let updatedParentsRows = [...this.state.collapsedParentsRows];
        let updatedRows = [...this.state.collapsedRows];

        if (firstRow && selectFirst) {
          let selectedIndex = 0;
          if (
            selected &&
            selected.length === 1 &&
            selected[0] &&
            firstRow.id !== selected[0]
          ) {
            selectedIndex = _.findIndex(rows, (row) => row.id === selected[0]);
          }

          if (!selectedIndex) {
            this.selectOneProduct(rows[0].id);
          }

          document.getElementsByClassName('js-table')[0].focus();
        }

        let mapCollapsed = [];

        if (collapsible && rows && rows.length) {
          rows.map((row) => {
            if (row.indent.length >= expandedDepth && row.includedDocuments) {
              mapCollapsed = mapCollapsed.concat(collapsedMap(row));
              updatedParentsRows = updatedParentsRows.concat(row[keyProperty]);
            }
            if (row.indent.length > expandedDepth) {
              updatedRows = updatedRows.concat(row[keyProperty]);
            }
          });

          const updatedState = {
            // dataHash: uuid(),
          };

          if (mapCollapsed.length) {
            updatedState.collapsedArrayMap = mapCollapsed;
          }
          if (updatedRows.length) {
            updatedState.collapsedRows = updatedRows;
          }
          if (updatedParentsRows.length) {
            updatedState.collapsedParentsRows = updatedParentsRows;
          }

          if (Object.keys(updatedState).length) {
            this.setState({ ...updatedState });
          }
        }
      });
    } else {
      // rowsData =
      //   rowData.get(`${tabId}`) && rowData.get(`${tabId}`).size
      //     ? rowData.get(`${tabId}`).toArray()
      //     : [];


      this.setState({
        // rows: rowsData,
        // dataHash: uuid(),

        // TODO: What is this for ?
        // pendingInit: !rowData.get(`${tabId}`),
        pendingInit: !rows.length,
      });
    }

    if (rowsData.length) {
      setTimeout(() => {
        if (this._isMounted) {
          this.setState({
            tableRefreshToggle: !this.state.mounted,
          });
        }
      }, 1);
    }
  };

  handleSelect = (id, idFocused, idFocusedDown) => {
    const {
      dispatch,
      windowId,
      disconnectFromState,
      tabInfo,
      viewId,
      selected,
      docId,
      tabId,
    } = this.props;
    // const { selected } = this.state;

    let newSelected = [];
    if (!selected[0]) {
      newSelected = [id];
    } else {
      newSelected = selected.concat([id]);
    }

    // this.setState({ selected: newSelected }, () => {
      // const { selected } = this.state;

      // if (tabInfo) {
        dispatch(
          updateTableSelection({
            tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
            ids: newSelected,
          })
        );
        // dispatch(
        //   selectTableItems({
        //     windowType: windowId,
        //     viewId,
        //     ids: newSelected,
        //   })
        // );
      // }

      if (!disconnectFromState) {
        dispatch(
          updateTableSelection({
            tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
            ids: newSelected,
          })
        );
        // dispatch(
        //   selectTableItems({
        //     windowType: windowId,
        //     viewId,
        //     ids: newSelected,
        //   })
        // );
      }




      this.triggerFocus(idFocused, idFocusedDown);
    // });

    return newSelected;
  };

  handleSelectRange = (ids) => {
    const { dispatch, tabInfo, windowId, viewId, docId, tabId } = this.props;

    // this.setState({ selected: [...ids] });

    // if (tabInfo) {
      dispatch(
        updateTableSelection({
          tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
          ids,
        })
      );
      // dispatch(
      //   selectTableItems({
      //     windowType: windowId,
      //     viewId,
      //     ids,
      //   })
      // );
    // }
  };

  handleSelectAll = () => {
    const { keyProperty, rows } = this.props;
    // const { rows } = this.state;
    const property = keyProperty ? keyProperty : 'rowId';
    const toSelect = rows.map((item) => item[property]);

    this.handleSelectRange(toSelect);
  };

  handleSelectOne = (id, idFocused, idFocusedDown, cb) => {
    const { dispatch, tabInfo, windowId, viewId, docId, tabId } = this.props;

    if (id === null) {
      id = undefined;
    }

    // this.setState(
    //   {
    //     selected: [id],
    //   },
      // () => {

        // TODO: Figure out how are we using this
        // if (tabInfo) {
          dispatch(
            updateTableSelection({
              tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
              ids: [id],
            })
          );
          // dispatch(
          //   selectTableItems({
          //     windowType: windowId,
          //     viewId,
          //     ids: [id],
          //   })
          // );
        // }







        this.triggerFocus(idFocused, idFocusedDown);
        cb && cb();
      // }
    // );
  };

  handleDeselect = (id) => {
    const { dispatch, tabInfo, windowId, viewId, selected } = this.props;
    // const { selected } = this.state;
    const index = selected.indexOf(id);
    const newSelected = update(selected, { $splice: [[index, 1]] });

    // this.setState({ selected: newSelected }, () => {
      if (tabInfo || !newSelected.length) {
        dispatch(deselectTableItems([id], windowId, viewId));
      }
    // });

    return newSelected;
  };

  handleDeselectAll = (callback) => {
    const { dispatch, tabInfo, windowId, viewId, docId, tabId } = this.props;

    // this.setState(
    //   {
    //     selected: [undefined],
    //   },
    callback && callback()
    // );

    // if (tabInfo) {
      dispatch(
        updateTableSelection({
          tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
          ids: EMPTY_ARRAY,
        })
      );
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
    // const { rows } = this.state;

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
      // const { rows } = this.state;
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
    const { dispatch } = this.props;

    dispatch(openModal('Add new', windowType, 'window', tabId, rowId));
  };

  openTableModal = () => {
    const { dispatch, windowId, tabId } = this.props;

    dispatch(openModal('Add new', windowId, 'window', tabId, 'NEW'));
  };

  /**
   * @method handleAdvancedEdit
   * @summary Handles advanced edit - i.e case when ALT+E key combinations are being used
   *          Active only on subtables
   */
  handleAdvancedEdit = () => {
    const { dispatch, windowId, tabId, docId, selected } = this.props;
    // const { selected } = this.state;

    if (docId) {
      dispatch(
        openModal('Advanced edit', windowId, 'window', tabId, selected[0], true)
      );
    }
  };

  handlePromptSubmit = (selected) => {
    const {
      dispatch,
      windowId,
      docId,
      updateDocList,
      tabId,
      viewId,
    } = this.props;

    dispatch(
      updateTableSelection({
        tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
        ids: [],
      })
    );

    // TODO: This should be an action creator
    deleteRequest(
      'window',
      windowId,
      docId ? docId : null,
      docId ? tabId : null,
      selected
    ).then((response) => {
      if (docId) {
        dispatch(deleteLocal(tabId, selected, 'master', response));
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

  // TODO: This should be an action creator `collapseRows` or something
  handleRowCollapse = (node, collapsed) => {
    const { keyProperty } = this.props;
    const {
      collapsedParentsRows,
      collapsedRows,
      collapsedArrayMap,
    } = this.state;

    this.setState({
      collapsedArrayMap: collapsedMap(node, collapsed, collapsedArrayMap),
    });

    if (collapsed) {
      this.setState((prev) => ({
        collapsedParentsRows: update(prev.collapsedParentsRows, {
          $splice: [[prev.collapsedParentsRows.indexOf(node[keyProperty]), 1]],
        }),
      }));
    } else {
      if (collapsedParentsRows.indexOf(node[keyProperty]) > -1) return;

      this.setState((prev) => ({
        collapsedParentsRows: prev.collapsedParentsRows.concat(
          node[keyProperty]
        ),
      }));
    }

    node.includedDocuments &&
      node.includedDocuments.map((node) => {
        if (collapsed) {
          this.setState((prev) => ({
            collapsedRows: update(prev.collapsedRows, {
              $splice: [[prev.collapsedRows.indexOf(node[keyProperty]), 1]],
            }),
          }));
        } else {
          if (collapsedRows.indexOf(node[keyProperty]) > -1) return;

          this.setState((prev) => ({
            collapsedRows: prev.collapsedRows.concat(node[keyProperty]),
          }));
          node.includedDocuments && this.handleRowCollapse(node, collapsed);
        }
      });
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
        onSelectOne={this.handleSelectOne}
        onSelectRange={this.handleSelectRange}
        onSelectAll={this.handleSelectAll}
        onDeselectAll={this.handleDeselectAll}
        onDeselect={this.handleDeselect}

        onRowCollapse={this.handleRowCollapse}
      />
    );
  }
}

TableContainer.propTypes = containerPropTypes;

const mapStateToProps = (state, props) => {
  const { windowId, docId, tabId, viewId } = props;

  // console.log('windowId, docId, tabId, viewId: ', windowId, docId, tabId, viewId);
  const tableId = getTableId({ windowType: windowId, viewId, docId, tabId });
  const table = getTable(state, tableId);

  return {
    rows: table.rows,
    columns: table.columns,
    selected: table.selected,
    collapsedParentsRows: table.collapsedParentsRows,
    collapsedRows: table.collapsedRows,
    collapsedArrayMap: table.collapsedArrayMap,

    allowShortcut: state.windowHandler.allowShortcut,
    allowOutsideClick: state.windowHandler.allowOutsideClick,
    modalVisible: state.windowHandler.modal.visible,
    isGerman:
      state.appHandler.me.language && state.appHandler.me.language.key
        ? state.appHandler.me.language.key.includes('de')
        : false,
    activeSort: state.table ? state.table.activeSort : false,
  };
};

// const clickOutsideConfig = {
//   excludeScrollbar: true,
// };

export { TableContainer };
export default connect(
  mapStateToProps,
  false,
  false,
  { forwardRef: true }
)(TableContainer);
