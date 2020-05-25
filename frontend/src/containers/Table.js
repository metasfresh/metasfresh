import update from 'immutability-helper';
import { is } from 'immutable';
import * as _ from 'lodash';
import React, { Component } from 'react';
// import onClickOutside from 'react-onclickoutside';
import { connect } from 'react-redux';
// import classnames from 'classnames';
// import currentDevice from 'current-device';
// import counterpart from 'counterpart';
// import uuid from 'uuid/v4';

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
  propTypes,
  // constructorFn,
} from '../utils/tableHelpers';
import {
  getRowsData,
  mapIncluded,
  collapsedMap,
} from '../utils/documentListHelper';

import Table from '../components/table/Table';

// const MOBILE_TABLE_SIZE_LIMIT = 30; // subjective number, based on empiric testing
// const isMobileOrTablet =
//   currentDevice.type === 'mobile' || currentDevice.type === 'tablet';
// const EMPTY_ARRAY = [];

// let RENDERS = 0;

class TableContainer extends Component {
  constructor(props) {
    super(props);

    // const constr = constructorFn.bind(this);
    // constr(props);
    this.state = {
      pendingInit: false,
    };
  }

  componentDidMount() {
    const { rowData, tabId } = this.props;
    //selecting first table elem while getting indent data
    // this._isMounted = true;

    if (rowData.get(`${tabId}`)) {
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
      rowData,
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
    if (
      (_.isEmpty(defaultSelected) || _.isEmpty(selected)) &&
      selected[0] === undefined &&
      !_.isEmpty(rows)
    ) {
      // this.setState({ selected: [rows[0].id] });
      // dispatch(
      //   updateTableSelection({
      //     tableId: getTableId({ windowType: windowId, viewId }),
      //     ids: [rows[0].id],
      //   })
      // );

    } // end of selection for the first row if nothing selected

    const selectedEqual = _.isEqual(prevState.selected, selected);
    // const defaultSelectedEqual = _.isEqual(
    //   prevProps.defaultSelected,
    //   defaultSelected
    // );

    if (!this._isMounted) {
      return;
    }

    if (rows && !_.isEqual(prevState.rows, rows)) {
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
    }
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

    if (prevProps.viewId !== viewId && rowData.get(`${tabId}`)) {
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

      const firstLoad =
        prevProps.rowData.get(`${tabId}`) &&
        prevProps.rowData.get(`${tabId}`).size &&
        rowData.get(`${tabId}`).size
          ? false
          : true;

      // this.getIndentData(firstLoad);
    } else if (rowData.get(`${tabId}`) && !is(prevProps.rowData, rowData)) {
      let firstLoad = rowData.get(`${tabId}`).size ? false : true;

      if (
        prevProps.rowData.get(`${tabId}`) &&
        !prevProps.rowData.get(`${tabId}`).size &&
        rowData.get(`${tabId}`).size
      ) {
        firstLoad = true;
      }

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

  showSelectedIncludedView = (selected) => {
    const { showIncludedViewOnSelect, openIncludedViewOnSelect, rows } = this.props;
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

  getIndentData = (selectFirst) => {
    const {
      rowData,
      tabId,
      indentSupported,
      collapsible,
      expandedDepth,
      keyProperty,
      rows,
      selected,
    } = this.props;
    // const { selected } = this.state;
    let rowsData = [];

    if (indentSupported && rowData.get(`${tabId}`).size) {
      rowsData = getRowsData(rowData.get(`${tabId}`));

      let stateChange = {
        // rows: rowsData,
        pendingInit: !rowsData,
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

  // handlePromptSubmitClick = (selected) => {
  //   const { dispatch, windowId, docId, updateDocList, tabId, viewId } = this.props;

  //   dispatch(
  //     updateTableSelection({
  //       tableId: getTableId({ windowType: windowId, viewId, docId, tabId }),
  //       ids: EMPTY_ARRAY,
  //     })
  //   );

  //   this.setState(
  //     {
  //       promptOpen: false,
  //       // selected: [undefined],
  //     },
  //     () => {
  //       deleteRequest(
  //         'window',
  //         windowId,
  //         docId ? docId : null,
  //         docId ? tabId : null,
  //         selected
  //       ).then((response) => {
  //         if (docId) {
  //           dispatch(deleteLocal(tabId, selected, 'master', response));
  //         } else {
  //           updateDocList();
  //         }
  //       });
  //     }
  //   );
  // };

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

  render() {
    return <Table {...this.props} {...this.state} />;
  }
}

TableContainer.propTypes = propTypes;

const mapStateToProps = (state, props) => {
  const { windowId, docId, tabId, viewId } = props;

  // console.log('windowId, docId, tabId, viewId: ', windowId, docId, tabId, viewId);
  const tableId = getTableId({ windowType: windowId, viewId, docId, tabId });
  const table = getTable(state, tableId);

  return {
    rows: table.rows,
    columns: table.columns,
    selected: table.selected,

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
