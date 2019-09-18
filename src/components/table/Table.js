import update from 'immutability-helper';
import { is } from 'immutable';
import * as _ from 'lodash';
import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { connect } from 'react-redux';
import classnames from 'classnames';
import currentDevice from 'current-device';
import counterpart from 'counterpart';
import hash from 'object-hash';

import { deleteRequest } from '../../actions/GenericActions';
import {
  deleteLocal,
  getZoomIntoWindow,
  openModal,
  selectTableItems,
  deselectTableItems,
} from '../../actions/WindowActions';
import Prompt from '../app/Prompt';
import DocumentListContextShortcuts from '../keyshortcuts/DocumentListContextShortcuts';
import TableContextShortcuts from '../keyshortcuts/TableContextShortcuts';
import TableContextMenu from './TableContextMenu';
import TableFilter from './TableFilter';
import TableHeader from './TableHeader';
import TableItem from './TableItem';
import TablePagination from './TablePagination';
import {
  getSizeClass,
  handleCopy,
  handleOpenNewTab,
  propTypes,
  constructorFn,
} from '../../utils/tableHelpers';
import {
  getRowsData,
  mapIncluded,
  collapsedMap,
} from '../../utils/documentListHelper';

const MOBILE_TABLE_SIZE_LIMIT = 30; // subjective number, based on empiric testing
const isMobileOrTablet =
  currentDevice.type === 'mobile' || currentDevice.type === 'tablet';

class Table extends Component {
  _isMounted = false;

  constructor(props) {
    super(props);

    const constr = constructorFn.bind(this);
    constr(props);
  }

  componentDidMount() {
    const { rowData, tabId } = this.props;
    //selecting first table elem while getting indent data
    this._isMounted = true;
    if (rowData.get(`${tabId}`)) {
      this.getIndentData(true);
    }
    if (this.props.autofocus) {
      this.table.focus();
    }
  }

  componentDidUpdate(prevProps, prevState) {
    const {
      dispatch,
      mainTable,
      open,
      rowData,
      defaultSelected,
      disconnectFromState,
      windowId,
      refreshSelection,
      openIncludedViewOnSelect,
      viewId,
      tabId,
      isModal,
      hasIncluded,
    } = this.props;
    const { selected, rows } = this.state;
    const selectedEqual = _.isEqual(prevState.selected, selected);
    const defaultSelectedEqual = _.isEqual(
      prevProps.defaultSelected,
      defaultSelected
    );

    if (!this._isMounted) {
      return;
    }

    if (rows && !_.isEqual(prevState.rows, rows)) {
      if (isModal && !hasIncluded) {
        let firstRow = rows[0];

        if (firstRow) {
          if (openIncludedViewOnSelect) {
            this.showSelectedIncludedView([firstRow.id]);
          }

          if (firstRow.id && !selectedEqual) {
            this.selectOneProduct(firstRow.id);
          }
        }
      }
    }

    if (mainTable && open) {
      this.table.focus();
    }

    if (
      (!defaultSelectedEqual && !selectedEqual) ||
      (refreshSelection && prevProps.refreshSelection !== refreshSelection)
    ) {
      this.setState({
        selected:
          defaultSelected && defaultSelected !== null ? defaultSelected : [],
      });
    } else if (!disconnectFromState && !selectedEqual && selected.length) {
      dispatch(
        selectTableItems({
          windowType: windowId,
          viewId,
          ids: selected,
        })
      );
    }

    if (prevProps.viewId !== viewId && rowData.get(`${tabId}`)) {
      if (defaultSelected && defaultSelected.length === 0) {
        this.setState({ selected: [] });
      }

      const firstLoad =
        prevProps.rowData.get(`${tabId}`).size && rowData.get(`${tabId}`).size
          ? false
          : true;

      this.getIndentData(firstLoad);
    } else if (rowData.get(`${tabId}`) && !is(prevProps.rowData, rowData)) {
      let firstLoad = rowData.get(`${tabId}`).size ? false : true;

      if (
        prevProps.rowData.get(`${tabId}`) &&
        !prevProps.rowData.get(`${tabId}`).size &&
        rowData.get(`${tabId}`).size
      ) {
        firstLoad = true;
      }

      this.getIndentData(firstLoad);
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

    this.deselectAllProducts();
    if (showIncludedViewOnSelect && !isIncluded) {
      showIncludedViewOnSelect({
        showIncludedView: false,
        windowType,
        viewId,
      });
    }
  }

  showSelectedIncludedView = selected => {
    const { showIncludedViewOnSelect } = this.props;
    const { rows } = this.state;

    if (selected.length === 1) {
      rows.forEach(item => {
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

  getIndentData = selectFirst => {
    const {
      rowData,
      tabId,
      indentSupported,
      collapsible,
      expandedDepth,
      keyProperty,
    } = this.props;
    const { selected } = this.state;
    let rowsData = [];

    if (indentSupported && rowData.get(`${tabId}`).size) {
      rowsData = getRowsData(rowData.get(`${tabId}`));
      let stateChange = {
        rows: rowsData,
        pendingInit: !rowsData,
      };

      if (selectFirst) {
        stateChange = {
          ...stateChange,
          collapsedParentsRows: [],
          collapsedRows: [],
        };
      }

      this.setState(stateChange, () => {
        const { rows } = this.state;
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
            selectedIndex = _.findIndex(rows, row => row.id === selected[0]);
          }

          if (!selectedIndex) {
            this.selectOneProduct(rows[0].id);
          }

          document.getElementsByClassName('js-table')[0].focus();
        }

        let mapCollapsed = [];

        if (collapsible && rows && rows.length && selectFirst) {
          rows.map(row => {
            if (row.indent.length >= expandedDepth && row.includedDocuments) {
              mapCollapsed = mapCollapsed.concat(collapsedMap(row));
              updatedParentsRows = updatedParentsRows.concat(row[keyProperty]);
            }
            if (row.indent.length > expandedDepth) {
              updatedRows = updatedRows.concat(row[keyProperty]);
            }
          });

          const updatedState = {};

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
      rowsData =
        rowData.get(`${tabId}`) && rowData.get(`${tabId}`).size
          ? rowData.get(`${tabId}`).toArray()
          : [];

      this.setState({
        rows: rowsData,
        pendingInit: !rowData.get(`${tabId}`),
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

  getAllLeafs = () => {
    const { rows, selected } = this.state;
    let leafs = [];
    let leafsIds = [];

    rows.map(item => {
      if (item.id == selected[0]) {
        leafs = mapIncluded(item);
      }
    });

    leafs.map(item => {
      leafsIds = leafsIds.concat(item.id);
    });

    this.selectRangeProduct(leafsIds);
  };

  changeListen = listenOnKeys => {
    this.setState({ listenOnKeys: !!listenOnKeys });
  };

  selectProduct = (id, idFocused, idFocusedDown) => {
    const {
      dispatch,
      windowId,
      disconnectFromState,
      tabInfo,
      viewId,
    } = this.props;
    const { selected } = this.state;
    let newSelected = [];
    if (!selected[0]) {
      newSelected = [id];
    } else {
      newSelected = selected.concat([id]);
    }

    this.setState({ selected: newSelected }, () => {
      const { selected } = this.state;

      if (tabInfo) {
        dispatch(
          selectTableItems({
            windowType: windowId,
            viewId,
            ids: selected,
          })
        );
      }

      if (!disconnectFromState) {
        dispatch(
          selectTableItems({
            windowType: windowId,
            viewId,
            ids: selected,
          })
        );
      }

      this.triggerFocus(idFocused, idFocusedDown);
    });

    return newSelected;
  };

  selectRangeProduct = ids => {
    const { dispatch, tabInfo, windowId, viewId } = this.props;

    this.setState({ selected: [...ids] });

    if (tabInfo) {
      dispatch(
        selectTableItems({
          windowType: windowId,
          viewId,
          ids,
        })
      );
    }
  };

  selectAll = () => {
    const { keyProperty } = this.props;
    const { rows } = this.state;
    const property = keyProperty ? keyProperty : 'rowId';
    const toSelect = rows.map(item => item[property]);

    this.selectRangeProduct(toSelect);
  };

  selectOneProduct = (id, idFocused, idFocusedDown, cb) => {
    const { dispatch, tabInfo, windowId, viewId } = this.props;

    if (id === null) {
      id = undefined;
    }

    this.setState(
      {
        selected: [id],
      },
      () => {
        if (tabInfo) {
          dispatch(
            selectTableItems({
              windowType: windowId,
              viewId,
              ids: [id],
            })
          );
        }

        this.triggerFocus(idFocused, idFocusedDown);
        cb && cb();
      }
    );
  };

  deselectProduct = id => {
    const { dispatch, tabInfo, windowId, viewId } = this.props;
    const { selected } = this.state;
    const index = selected.indexOf(id);
    const newSelected = update(selected, { $splice: [[index, 1]] });

    this.setState({ selected: newSelected }, () => {
      if (tabInfo || !newSelected.length) {
        dispatch(deselectTableItems([id], windowId, viewId));
      }
    });

    return newSelected;
  };

  deselectAllProducts = cb => {
    const { dispatch, tabInfo, windowId, viewId } = this.props;

    this.setState(
      {
        selected: [undefined],
      },
      cb && cb()
    );

    if (tabInfo) {
      dispatch(
        selectTableItems({
          windowType: windowId,
          viewId,
          ids: [],
        })
      );
    }
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

  handleClickOutside = event => {
    const {
      showIncludedViewOnSelect,
      viewId,
      windowType,
      inBackground,
      allowOutsideClick,
      limitOnClickOutside,
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

      this.deselectAllProducts();

      if (showIncludedViewOnSelect) {
        showIncludedViewOnSelect({
          showIncludedView: false,
          windowType,
          viewId,
        });
      }
    }
  };

  handleKeyDown = e => {
    const { keyProperty, mainTable, readonly, closeOverlays } = this.props;
    const { selected, rows, listenOnKeys, collapsedArrayMap } = this.state;

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
            ? collapsedArrayMap.map(item => item[keyProperty])
            : rows.map(item => item[keyProperty]);
        const currentId = array.findIndex(
          x => x === selected[selected.length - 1]
        );

        if (currentId >= array.length - 1) {
          return;
        }

        if (!selectRange) {
          this.selectOneProduct(
            array[currentId + 1],
            false,
            idFocused,
            this.showSelectedIncludedView([array[currentId + 1]])
          );
        } else {
          this.selectProduct(array[currentId + 1], false, idFocused);
        }
        break;
      }
      case 'ArrowUp': {
        e.preventDefault();

        const array =
          collapsedArrayMap.length > 0
            ? collapsedArrayMap.map(item => item[keyProperty])
            : rows.map(item => item[keyProperty]);
        const currentId = array.findIndex(
          x => x === selected[selected.length - 1]
        );

        if (currentId <= 0) {
          return;
        }

        if (!selectRange) {
          this.selectOneProduct(
            array[currentId - 1],
            idFocused,
            false,
            this.showSelectedIncludedView([array[currentId - 1]])
          );
        } else {
          this.selectProduct(array[currentId - 1], idFocused, false);
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

  closeContextMenu = () => {
    this.setState({
      contextMenu: Object.assign({}, this.state.contextMenu, {
        open: false,
      }),
    });
  };

  handleDoubleClick = id => {
    const { isIncluded, onDoubleClick } = this.props;

    if (!isIncluded) {
      onDoubleClick && onDoubleClick(id);
    }
  };

  handleClick = (e, keyProperty, item) => {
    const { onSelectionChanged } = this.props;
    const id = item[keyProperty];

    if (e.button === 0) {
      const { selected } = this.state;
      const selectMore = e.metaKey || e.ctrlKey;
      const selectRange = e.shiftKey;
      const isSelected = selected.indexOf(id) > -1;
      const isAnySelected = selected.length > 0;

      let newSelection;

      if (selectMore || isMobileOrTablet) {
        if (isSelected) {
          newSelection = this.deselectProduct(id);
        } else {
          newSelection = this.selectProduct(id);
        }
      } else if (selectRange) {
        if (isAnySelected) {
          newSelection = this.getProductRange(id);
          this.selectRangeProduct(newSelection);
        } else {
          newSelection = [id];
          this.selectOneProduct(id);
        }
      } else {
        newSelection = [id];
        this.selectOneProduct(id);
      }

      if (onSelectionChanged) {
        onSelectionChanged(newSelection);
      }

      return newSelection.length > 0;
    }
    return true;
  };

  handleRightClick = (e, id, fieldName, supportZoomInto, supportFieldEdit) => {
    e.preventDefault();

    const { selected } = this.state;
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
      this.selectOneProduct(id, null, null, () => {
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

  getProductRange = id => {
    const { keyProperty } = this.props;
    const { rows, selected } = this.state;
    let arrayIndex;
    let selectIdA;
    let selectIdB;

    arrayIndex = rows.map(item => item[keyProperty]);
    selectIdA = arrayIndex.findIndex(x => x === id);
    selectIdB = arrayIndex.findIndex(x => x === selected[0]);

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

  openModal = (windowType, tabId, rowId) => {
    const { dispatch } = this.props;
    dispatch(openModal('Add new', windowType, 'window', tabId, rowId));
  };

  handleAdvancedEdit = (windowId, tabId, selected) => {
    const { dispatch } = this.props;

    dispatch(
      openModal('Advanced edit', windowId, 'window', tabId, selected[0], true)
    );
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

  handlePromptSubmitClick = selected => {
    const { dispatch, windowId, docId, updateDocList, tabId } = this.props;

    this.setState(
      {
        promptOpen: false,
        selected: [undefined],
      },
      () => {
        deleteRequest(
          'window',
          windowId,
          docId ? docId : null,
          docId ? tabId : null,
          selected
        ).then(response => {
          if (docId) {
            dispatch(deleteLocal(tabId, selected, 'master', response));
          } else {
            updateDocList();
          }
        });
      }
    );
  };

  handleZoomInto = fieldName => {
    const { entity, windowId, docId, tabId, viewId } = this.props;
    const { selected } = this.state;

    getZoomIntoWindow(
      entity,
      windowId,
      docId,
      entity === 'window' ? tabId : viewId,
      selected[0],
      fieldName
    ).then(res => {
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
      this.setState(prev => ({
        collapsedParentsRows: update(prev.collapsedParentsRows, {
          $splice: [[prev.collapsedParentsRows.indexOf(node[keyProperty]), 1]],
        }),
      }));
    } else {
      if (collapsedParentsRows.indexOf(node[keyProperty]) > -1) return;
      this.setState(prev => ({
        collapsedParentsRows: prev.collapsedParentsRows.concat(
          node[keyProperty]
        ),
      }));
    }

    node.includedDocuments &&
      node.includedDocuments.map(node => {
        if (collapsed) {
          this.setState(prev => ({
            collapsedRows: update(prev.collapsedRows, {
              $splice: [[prev.collapsedRows.indexOf(node[keyProperty]), 1]],
            }),
          }));
        } else {
          if (collapsedRows.indexOf(node[keyProperty]) > -1) return;
          this.setState(prev => ({
            collapsedRows: prev.collapsedRows.concat(node[keyProperty]),
          }));
          node.includedDocuments && this.handleRowCollapse(node, collapsed);
        }
      });
  };

  handleShortcutIndent = expand => {
    const { selected, rows, collapsedParentsRows } = this.state;
    const { keyProperty } = this.props;

    let node = '';
    let isCollapsed = '';
    selected.length === 1 &&
      rows.map(item => {
        if (item.id === selected[0]) {
          if (item.includedDocuments) {
            const keyProp = item[keyProperty];
            node = item;
            isCollapsed = collapsedParentsRows.indexOf(keyProp) > -1;
          }
        }
      });

    if (node) {
      if (isCollapsed && expand) {
        this.handleRowCollapse(node, expand);
      } else if (!isCollapsed && !expand) {
        this.handleRowCollapse(node, expand);
      }
    }
  };

  handleFieldEdit = (selected, fieldName) => {
    this.closeContextMenu();

    const selectedId = selected[0];

    if (this.rowRefs && this.rowRefs[selectedId]) {
      this.rowRefs[selectedId].initPropertyEditor(fieldName);
    }
  };

  handleItemChange = (rowId, prop, value) => {
    const { mainTable, keyProperty, onRowEdited } = this.props;

    if (mainTable) {
      const { rows } = this.state;

      if (!rows || !rows.length) return;

      rows
        .filter(row => row[keyProperty] === rowId)
        .map(item => {
          let field = item.fieldsByName[prop];

          if (field) {
            field.value = value;
          }
        });
    }

    onRowEdited && onRowEdited(true);
  };

  renderTableBody = () => {
    const {
      tabId,
      cols,
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
      showIncludedViewOnSelect,
      openIncludedViewOnSelect,
      viewId,
      supportOpenRecord,
    } = this.props;

    const { selected, rows, collapsedRows, collapsedParentsRows } = this.state;

    if (!rows || !rows.length) return null;

    this.rowRefs = {};

    let renderRows = rows.filter(row => {
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
          entity,
          cols,
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
        }}
        dataKey={hash(item.fieldsByName)}
        key={`${i}-${viewId}`}
        collapsed={
          collapsedParentsRows &&
          collapsedParentsRows.indexOf(item[keyProperty]) > -1
        }
        odd={i & 1}
        ref={c => {
          if (c) {
            const keyProp = item[keyProperty];
            this.rowRefs[keyProp] = c.wrappedInstance;
          }
        }}
        rowId={item[keyProperty]}
        tabId={tabId}
        onDoubleClick={this.handleDoubleClick}
        onClick={e => {
          const selected = this.handleClick(e, keyProperty, item);

          if (openIncludedViewOnSelect) {
            showIncludedViewOnSelect({
              showIncludedView: selected && item.supportIncludedViews,
              forceClose: !selected,
              windowType: item.supportIncludedViews
                ? item.includedView.windowType || item.includedView.windowId
                : null,
              viewId: item.supportIncludedViews ? item.includedView.viewId : '',
            });
          }
        }}
        handleRightClick={(e, fieldName, supportZoomInto, supportFieldEdit) =>
          this.handleRightClick(
            e,
            item[keyProperty],
            fieldName,
            !!supportZoomInto,
            supportFieldEdit
          )
        }
        changeListenOnTrue={() => this.changeListen(true)}
        changeListenOnFalse={() => this.changeListen(false)}
        newRow={i === rows.length - 1 ? newRow : false}
        isSelected={
          selected &&
          (selected.indexOf(item[keyProperty]) > -1 || selected[0] === 'all')
        }
        handleSelect={this.selectRangeProduct}
        contextType={item.type}
        caption={item.caption ? item.caption : ''}
        colspan={item.colspan}
        notSaved={item.saveStatus && !item.saveStatus.saved}
        getSizeClass={getSizeClass}
        handleRowCollapse={() =>
          this.handleRowCollapse(
            item,
            collapsedParentsRows.indexOf(item[keyProperty]) > -1
          )
        }
        onItemChange={this.handleItemChange}
        onCopy={handleCopy}
      />
    ));
  };

  renderEmptyInfo = (data, tabId) => {
    const { emptyText, emptyHint } = this.props;
    const { pendingInit } = this.state;

    if (pendingInit) {
      return false;
    }

    if (
      (data && data.get(`${tabId}`) && data.get(`${tabId}`).size === 0) ||
      !data.get(`${tabId}`)
    ) {
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
      cols,
      windowId,
      docId,
      rowData,
      tabId,
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
    } = this.props;

    const {
      contextMenu,
      selected,
      promptOpen,
      isBatchEntry,
      rows,
      tableRefreshToggle,
    } = this.state;

    let showPagination = page && pageLength;
    if (currentDevice.type === 'mobile' || currentDevice.type === 'tablet') {
      showPagination = false;
    }

    return (
      <div ref={ref => (this.wrapper = ref)} className="table-flex-wrapper">
        <div
          className={classnames('table-flex-wrapper', {
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
              selected={selected || [undefined]}
              blur={this.closeContextMenu}
              tabId={tabId}
              deselect={this.deselectAllProducts}
              handleFieldEdit={() => {
                if (contextMenu.supportFieldEdit && selected.length === 1) {
                  this.handleFieldEdit(selected, contextMenu.fieldName);
                }
              }}
              handleAdvancedEdit={() =>
                this.handleAdvancedEdit(windowId, tabId, selected)
              }
              onOpenNewTab={handleOpenNewTab}
              handleDelete={
                !isModal && (tabInfo && tabInfo.allowDelete)
                  ? this.handleDelete
                  : null
              }
              handleZoomInto={this.handleZoomInto}
            />
          )}
          {!readonly && (
            <div className="row">
              <div className="col-12">
                <TableFilter
                  openModal={() => this.openModal(windowId, tabId, 'NEW')}
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
              </div>
            </div>
          )}

          <div
            className={classnames(
              'panel panel-primary panel-bordered',
              'panel-bordered-force table-flex-wrapper',
              'document-list-table js-not-unselect',
              {
                'table-content-empty':
                  (rowData &&
                    rowData.get(`${tabId}`) &&
                    rowData.get(`${tabId}`).size === 0) ||
                  !rowData.get(`${tabId}`),
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
              ref={c => (this.table = c)}
              onCopy={handleCopy}
            >
              <thead>
                <TableHeader
                  {...{
                    cols,
                    sort,
                    orderBy,
                    page,
                    indentSupported,
                    tabId,
                  }}
                  getSizeClass={getSizeClass}
                  deselect={this.deselectAllProducts}
                />
              </thead>
              <tbody ref={c => (this.tbody = c)}>
                {this.renderTableBody()}
              </tbody>
              <tfoot ref={c => (this.tfoot = c)} />
            </table>

            {this.renderEmptyInfo(rowData, tabId)}
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
              handleChangePage={pages => {
                this.deselectAllProducts();
                handleChangePage(pages);
              }}
              selected={selected || [undefined]}
              pageLength={pageLength}
              rowLength={rows ? rows.length : 0}
              handleSelectAll={this.selectAll}
              handleSelectRange={this.selectRangeProduct}
              deselect={this.deselectAllProducts}
            />
          </div>
        )}
        {promptOpen && (
          <Prompt
            title="Delete"
            text="Are you sure?"
            buttons={{ submit: 'Delete', cancel: 'Cancel' }}
            onCancelClick={this.handlePromptCancelClick}
            onSubmitClick={() => this.handlePromptSubmitClick(selected)}
          />
        )}

        {allowShortcut && (
          <DocumentListContextShortcuts
            handleAdvancedEdit={
              selected && selected.length > 0 && selected[0]
                ? () => this.handleAdvancedEdit(windowId, tabId, selected)
                : undefined
            }
            handleOpenNewTab={
              selected && selected.length > 0 && selected[0] && mainTable
                ? () => handleOpenNewTab(selected, windowId)
                : undefined
            }
            handleDelete={
              selected && selected.length > 0 && selected[0]
                ? this.handleDelete
                : undefined
            }
            getAllLeafs={this.getAllLeafs}
            handleIndent={this.handleShortcutIndent}
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

Table.propTypes = propTypes;

const mapStateToProps = state => ({
  allowShortcut: state.windowHandler.allowShortcut,
  allowOutsideClick: state.windowHandler.allowOutsideClick,
});

const clickOutsideConfig = {
  excludeScrollbar: true,
};

export default connect(
  mapStateToProps,
  false,
  false,
  { withRef: true }
)(onClickOutside(Table, clickOutsideConfig));
