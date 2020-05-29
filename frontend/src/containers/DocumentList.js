import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { Map as iMap, Set as iSet } from 'immutable';
import currentDevice from 'current-device';
import { get } from 'lodash';

import { LOCATION_SEARCH_NAME } from '../constants/Constants';
import {
  locationSearchRequest,
  deleteStaticFilter,
  getViewRowsByIds,
} from '../api';
import { getTableId } from '../reducers/tables';
import {
  addViewLocationData,
  createView,
  fetchDocument,
  fetchLayout,
  fetchLocationConfig,
  filterView,
  resetView,
  deleteView,
  updateViewData,
} from '../actions/ViewActions';
import { deleteTable } from '../actions/TableActions';
import { clearAllFilters } from '../actions/FiltersActions';
import {
  closeListIncludedView,
  setListId,
  setListIncludedView,
  setPagination as setListPagination,
  setSorting as setListSorting,
} from '../actions/ListActions';
import {
  updateRawModal,
  indicatorState,
  selectTableItems,
  deselectTableItems,
  removeSelectedTableItems,
} from '../actions/WindowActions';
import { connectWS, disconnectWS } from '../utils/websockets';
import { getSelectionDirect } from '../reducers/windowHandler';
import {
  DLpropTypes,
  DLmapStateToProps,
  NO_SELECTION,
  GEO_PANEL_STATES,
  getSortingQuery,
  doesSelectionExist,
  filtersToMap,
  mergeColumnInfosIntoViewRows,
  mergeRows,
  parseToDisplay,
  flattenRows,
} from '../utils/documentListHelper';

import DocumentList from '../components/app/DocumentList';

class DocumentListContainer extends Component {
  constructor(props) {
    super(props);

    // TODO: Why it's not in the state?
    this.pageLength =
      currentDevice.type === 'mobile' || currentDevice.type === 'tablet'
        ? 9999
        : 20;

    this.state = {
      pageColumnInfosByFieldName: null,
      panelsState: GEO_PANEL_STATES[0],
      filtersActive: iMap(),
      initialValuesNulled: iMap(),
      isShowIncluded: false,
      hasShowIncluded: false,
      supportAttribute: false,
    };

    this.fetchLayoutAndData();
  }

  UNSAFE_componentWillMount() {
    const { isModal, windowId, viewId } = this.props;

    this.props.fetchLocationConfig(isModal ? viewId : windowId);
  }

  componentDidMount = () => {
    this.mounted = true;
  };

  componentWillUnmount() {
    const { isModal, windowId, viewId, deleteView, deleteTable } = this.props;

    this.mounted = false;
    disconnectWS.call(this);

    deleteView(isModal ? viewId : windowId);
    deleteTable(getTableId({ windowId, viewId }));
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
    const {
      viewId: nextViewId,
      includedView: nextIncludedView,
      isIncluded: nextIsIncluded,
      refId: nextRefId,
      windowId: nextWindowId,
    } = nextProps;
    const {
      includedView,
      isIncluded,
      refId,
      windowId,
      closeListIncludedView,
      viewId,
      resetView,
      clearAllFilters,
      deleteTable,
    } = this.props;
    const { staticFilterCleared } = this.state;

    const included =
      includedView && includedView.windowType && includedView.viewId;
    const nextIncluded =
      nextIncludedView &&
      nextIncludedView.windowType &&
      nextIncludedView.viewId;
    const location = document.location;

    this.loadSupportAttributeFlag(nextProps);

    if (nextProps.filters.clearAll) {
      this.setState({ filtersActive: iMap() }, () => {
        clearAllFilters(false);
      });
    }
    /*
     * If we browse list of docs, changing type of Document
     * does not re-construct component, so we need to
     * make it manually while the windowId changes.
     * OR
     * We want to refresh the window (generate new viewId)
     * OR
     * The reference ID is changed
     */
    if (
      staticFilterCleared ||
      nextWindowId !== windowId ||
      (nextWindowId === windowId &&
        ((nextViewId !== viewId && isIncluded && nextIsIncluded) ||
          location.hash === '#notification')) ||
      nextRefId !== refId
    ) {
      resetView(windowId);
      deleteTable(getTableId({ windowId, viewId }));

      this.setState(
        {
          filtersActive: iMap(),
          initialValuesNulled: iMap(),
          staticFilterCleared: false,
          panelsState: GEO_PANEL_STATES[0],
        },
        () => {
          if (included) {
            closeListIncludedView(includedView);
          }

          this.fetchLayoutAndData();
        }
      );
    }

    const stateChanges = {};

    if (included && !nextIncluded) {
      stateChanges.isShowIncluded = false;
      stateChanges.hasShowIncluded = false;
    }

    if (Object.keys(stateChanges).length) {
      this.setState({
        ...stateChanges,
      });
    }
  }

  /**
   * @method connectWebSocket
   * @summary ToDo: Describe the method.
   */
  connectWebSocket = (customViewId) => {
    const { windowId, deselectTableItems, updateViewData } = this.props;
    const viewId = customViewId ? customViewId : this.props.viewId;

    connectWS.call(this, `/view/${viewId}`, (msg) => {
      const { fullyChanged, changedIds } = msg;

      if (changedIds) {
        getViewRowsByIds(windowId, viewId, changedIds.join()).then(
          (response) => {

            // TODO: Rewrite to use table reducers
            const { reduxData } = this.props;
            const { pageColumnInfosByFieldName, filtersActive } = this.state;
            const toRows = reduxData.rowData.get('1').toArray();

            // merge changed rows with data in the store
            const { rows, removedRows } = mergeRows({
              toRows,
              fromRows: [...response.data],
              columnInfosByFieldName: pageColumnInfosByFieldName,
              changedIds,
            });

            if (removedRows.length) {
              deselectTableItems(removedRows, windowId, viewId);
            } else {
              if (filtersActive.size) {
                this.filterCurrentView();
              }

              // force updating actions
              this.updateQuickActions();
            }

            updateViewData(windowId, rows);
          }
        );
      }

      if (fullyChanged === true) {
        const { selectTableItems, windowId, selections, viewId } = this.props;
        const selection = getSelectionDirect(selections, windowId, viewId);

        // TODO: Use Table's AC
        // Reload Attributes after QuickAction is done
        selection.length &&
          selectTableItems({
            windowType: windowId,
            viewId,
            ids: [selection[0]],
          });

        this.browseView();
        this.updateQuickActions();
      }
    });
  };

  /**
   * @method updateQuickActions
   * @summary Trigger the QuickActions component to fetch quick actions for the new selection
   */
  updateQuickActions = (childSelection) => {
    if (this.quickActionsComponent) {
      this.quickActionsComponent.updateActions(childSelection);
    }
  };

  /**
   * @method loadSupportAttributeFlag
   * @summary Load supportAttribute of the selected row from the table.
   */
  loadSupportAttributeFlag = ({ selected }) => {
    const {
      reduxData: { rowData },
    } = this.props;

    if (!rowData) {
      return;
    }
    const rows = flattenRows(rowData.get('1'));

    if (selected.length === 1) {
      const selectedRow = rows.find((row) => row.id === selected[0]);

      this.setState({
        supportAttribute: selectedRow && selectedRow.supportAttributes,
      });
    } else {
      this.setState({
        supportAttribute: false,
      });
    }
  };

  // TODO: I think this should be stored in redux too
  /**
   * @method clearStaticFilters
   * @summary ToDo: Describe the method.
   */
  clearStaticFilters = (filterId) => {
    const { push, windowId, viewId } = this.props;

    deleteStaticFilter(windowId, viewId, filterId).then((response) => {
      this.setState({ staticFilterCleared: true }, () =>
        push(`/window/${windowId}?viewId=${response.data.viewId}`)
      );
    });
  };

  // FETCHING LAYOUT && DATA -------------------------------------------------
  /**
   * @method fetchLayoutAndData
   * @summary ToDo: Describe the method.
   */
  fetchLayoutAndData = (isNewFilter, locationAreaSearch) => {
    const {
      windowId,
      type,
      viewProfileId,
      setModalTitle,
      viewId,
      fetchLayout,
      updateRawModal,
      setModalDescription,
      isModal,
    } = this.props;

    fetchLayout(windowId, type, viewProfileId, isModal ? viewId : null)
      .then((response) => {
        if (this.mounted) {
          const { allowedCloseActions } = response;

          if (allowedCloseActions && isModal) {
            updateRawModal(windowId, { allowedCloseActions });
          }

          if (viewId) {
            this.connectWebSocket(viewId);

            if (!isNewFilter) {
              this.browseView();
            } else {
              this.filterCurrentView(locationAreaSearch);
            }
          } else {
            this.createNewView();
          }

          setModalTitle && setModalTitle(response.data.caption);
          if (
            response.data &&
            response.data.description &&
            setModalDescription
          ) {
            setModalDescription(response.data.description);
          }
        }
      })
      .catch(() => {
        // TODO: Should we somehow handle errors here ?
      });
  };

  /**
   * @method browseView
   * @summary If viewId exists, than browse that view.
   */
  browseView = () => {
    const { viewId, page, sort } = this.props;
    const { filtersActive } = this.state;
    const locationSearchFilter = filtersActive.has(LOCATION_SEARCH_NAME);

    // in case of redirect from a notification, first call will have viewId empty
    if (viewId) {
      this.getData(viewId, page, sort, locationSearchFilter).catch((err) => {
        if (err.response && err.response.status === 404) {
          this.createNewView();
        }
      });
    }
  };

  /**
   * @method createNewView
   * @summary Create a new view, on visiting the page for the first time
   */
  createNewView = () => {
    const {
      windowId,
      type,
      refType,
      refId,
      refTabId,
      refRowIds,
      page,
      sort,
      createView,
      setModalDescription,
      isModal,
      viewId,
    } = this.props;
    const { filtersActive } = this.state;

    createView({
      windowType: windowId,
      viewType: type,
      filters: filtersActive.toIndexedSeq().toArray(),
      refDocType: refType,
      refDocId: refId,
      refTabId,
      refRowIds,
      inModalId: isModal ? viewId : null,
    })
      .then(({ viewId, data }) => {
        if (data && data.description && setModalDescription) {
          setModalDescription(data.description);
        }

        if (this.mounted) {
          this.connectWebSocket(viewId);
          this.getData(viewId, page, sort);
        }
      })
      .catch(() => {
        // TODO: Should we somehow handle errors here ?
      });
  };

  /**
   * @method filterCurrentView
   * @summary apply filters and re-fetch layout, data. Then rebuild the page
   */
  filterCurrentView = (locationAreaSearch) => {
    const {
      windowId,
      isIncluded,
      page,
      sort,
      viewId,
      setListIncludedView,
      setModalDescription,
      filterView,
      isModal,
    } = this.props;
    const { filtersActive } = this.state;

    filterView(
      windowId,
      viewId,
      filtersActive.toIndexedSeq().toArray(),
      isModal
    )
      .then((response) => {
        const newViewId = response.viewId;

        this.connectWebSocket(newViewId);

        if (response.data && response.data.description && setModalDescription) {
          setModalDescription(response.data.description);
        }

        if (isIncluded) {
          setListIncludedView({ windowType: windowId, viewId: newViewId });
        }

        this.mounted && this.getData(newViewId, page, sort, locationAreaSearch);
      })
      .catch(() => {
        // TODO: Should we somehow handle errors here ?
      });
  };

  /**
   * @method getData
   * @summary Loads view/included tab data from REST endpoint
   */
  getData = (id, page, sortingQuery, locationAreaSearch) => {
    const {
      windowId,
      selections,
      updateUri,
      type,
      isIncluded,
      fetchDocument,
      indicatorState,
      selectTableItems,
      updateRawModal,
      viewId,
      isModal,
      rawModalVisible,
    } = this.props;

    indicatorState('pending');

    if (updateUri) {
      id && updateUri('viewId', id);
      page && updateUri('page', page);
      sortingQuery && updateUri('sort', sortingQuery);
    }

    // if we're filtering in a modal we don't want to create another entry in the state,
    // but update the current (modal) view
    let modalId = null;
    if (isModal && id !== viewId) {
      modalId = viewId;
    }

    return fetchDocument({
      windowType: windowId,
      viewId: id,
      page,
      pageLength: this.pageLength,
      orderBy: sortingQuery,
      useViewId: isModal,
      modalId,
    })
      .then((response) => {
        const result = response.result;
        const resultById = {};
        const selection = getSelectionDirect(selections, windowId, viewId);
        const forceSelection =
          (type === 'includedView' || isIncluded) &&
          response &&
          result.length > 0 &&
          (selection.length === 0 ||
            !doesSelectionExist({
              data: result,
              selected: selection,
            }));

        result.map((row) => {
          const parsed = parseToDisplay(row.fieldsByName);
          resultById[`${row.id}`] = parsed;
          row.fieldsByName = parsed;
        });

        const pageColumnInfosByFieldName = response.columnsByFieldName;

        // https://github.com/metasfresh/me03/issues/4734
        mergeColumnInfosIntoViewRows(
          pageColumnInfosByFieldName,
          response.result
        );

        if (this.mounted) {
          const newState = {
            pageColumnInfosByFieldName,
          };

          if (response.filters) {
            newState.filtersActive = filtersToMap(response.filters);
          }

          if (
            locationAreaSearch ||
            (newState.filtersActive &&
              newState.filtersActive.has(LOCATION_SEARCH_NAME))
          ) {
            this.getLocationData(resultById);
          }

          this.setState({ ...newState }, () => {
            if (forceSelection && response && result && result.length > 0) {
              const selection = [result[0].id];

              // TODO: Remove or use `updateTableSelection`
              selectTableItems({
                windowType: windowId,
                viewId,
                ids: selection,
              });
            }
          });

          if (rawModalVisible) {
            // process modal specific
            const { parentViewId, parentWindowId, headerProperties } = response;

            updateRawModal(windowId, {
              parentViewId,
              parentWindowId,
              headerProperties,
            });
          }
        }

        indicatorState('saved');
      })
      .catch(() => {
        // TODO: Should we somehow handle errors here ?
      });
  };

  getLocationData = (resultById) => {
    const {
      windowId,
      viewId,
      reduxData: { mapConfig },
    } = this.props;

    locationSearchRequest({ windowId, viewId }).then(({ data }) => {
      const locationData = data.locations.map((location) => {
        const name = get(
          resultById,
          [location.rowId, 'C_BPartner_ID', 'value', 'caption'],
          location.rowId
        );

        return {
          ...location,
          name,
        };
      });

      if (locationData.length) {
        addViewLocationData(windowId, locationData);
      }

      if (mapConfig && mapConfig.provider && locationData.length) {
        // for mobile show map
        // for desktop show half-n-half
        this.setState({ panelsState: GEO_PANEL_STATES[1] });
      }
    });
  };

  // MANAGING SORT, PAGINATION, FILTERS --------------------------------------

  /**
   * @method handleChangePage
   * @summary ToDo: Describe the method.
   */
  handleChangePage = (index) => {
    const { reduxData } = this.props;
    let currentPage = reduxData.page;

    switch (index) {
      case 'up':
        currentPage * reduxData.pageLength < reduxData.size
          ? currentPage++
          : null;
        break;
      case 'down':
        currentPage !== 1 ? currentPage-- : null;
        break;
      default:
        currentPage = index;
    }

    this.getData(reduxData.viewId, currentPage, reduxData.sort);
  };

  /**
   * @method sortData
   * @summary ToDo: Describe the method.
   */
  sortData = (asc, field, startPage) => {
    const { viewId, page } = this.props;

    this.getData(viewId, startPage ? 1 : page, getSortingQuery(asc, field));
  };

  /**
   * @method handleFilterChange
   * @summary ToDo: Describe the method.
   */
  handleFilterChange = (activeFilters) => {
    const locationSearchFilter = activeFilters.has(LOCATION_SEARCH_NAME);

    // TODO: filters should be kept in the redux state
    this.setState(
      {
        filtersActive: activeFilters,
      },
      () => {
        this.fetchLayoutAndData(true, locationSearchFilter);
      }
    );
  };

  /**
   * @method resetInitialFilters
   * @summary ToDo: Describe the method.
   */
  resetInitialFilters = (filterId, parameterName) => {
    let { initialValuesNulled } = this.state;
    let filterParams = initialValuesNulled.get(filterId);

    if (!filterParams && parameterName) {
      filterParams = iSet([parameterName]);
    } else if (filterParams && parameterName) {
      filterParams = filterParams.add(parameterName);
    }

    if (!parameterName) {
      initialValuesNulled = initialValuesNulled.delete(filterId);
    } else {
      initialValuesNulled = initialValuesNulled.set(filterId, filterParams);
    }

    this.setState({
      initialValuesNulled,
    });
  };

  // END OF MANAGING SORT, PAGINATION, FILTERS -------------------------------

  toggleState = (state) => {
    this.setState({ panelsState: state });
  };

  /**
   * @method redirectToDocument
   * @summary Redirect to document details
   */
  redirectToDocument = (id) => {
    const {
      isModal,
      windowId,
      isSideListShow,
      reduxData,
      push,
      page,
      sort,
      setListSorting,
      setListPagination,
      setListId,
    } = this.props;

    if (isModal) {
      return;
    }

    push(`/window/${windowId}/${id}`);

    if (!isSideListShow) {
      // Caching last settings
      setListPagination(page, windowId);
      setListSorting(sort, windowId);
      setListId(reduxData.viewId, windowId);
    }
  };

  /**
   * @method redirectToDocument
   * @summary Redirect to a new document
   */
  redirectToNewDocument = () => {
    const { push, windowId } = this.props;

    push(`/window/${windowId}/new`);
  };

  /**
   * @method showIncludedView
   * @summary ToDo: Describe the method.
   */
  showIncludedViewOnSelect = ({
    showIncludedView,
    windowType,
    viewId,
    forceClose,
  } = {}) => {
    const { setListIncludedView, closeListIncludedView } = this.props;
    this.setState(
      {
        isShowIncluded: !!showIncludedView,
        hasShowIncluded: !!showIncludedView,
      },
      () => {
        if (showIncludedView) {
          setListIncludedView({ windowType, viewId });
        }
      }
    );

    // can't use setState callback because component might be unmounted and
    // callback is never called
    if (!showIncludedView) {
      closeListIncludedView({ windowType, viewId, forceClose });
    }
  };

  /**
   * @method getSelected
   * @summary TODO: We should get rid of that I think and move to redux only.
   */
  getSelected = () => {
    const {
      selections,
      windowId,
      includedView,
      parentWindowType,
      parentDefaultViewId,
      reduxData: { viewId },
    } = this.props;

    return {
      selected: getSelectionDirect(selections, windowId, viewId),
      childSelected:
        includedView && includedView.windowType
          ? getSelectionDirect(
              selections,
              includedView.windowType,
              includedView.viewId
            )
          : NO_SELECTION,
      parentSelected: parentWindowType
        ? getSelectionDirect(selections, parentWindowType, parentDefaultViewId)
        : NO_SELECTION,
    };
  };

  render() {
    const {
      includedView,
      layout,
      layoutPending,
      reduxData: { pending },
    } = this.props;

    const hasIncluded =
      layout &&
      layout.includedView &&
      includedView &&
      includedView.windowType &&
      includedView.viewId;

    return (
      <DocumentList
        {...this.props}
        {...this.state}
        triggerSpinner={layoutPending || pending}
        hasIncluded={hasIncluded}
        onToggleState={this.toggleState}
        pageLength={this.pageLength}
        onGetSelected={this.getSelected}
        onShowIncludedViewOnSelect={this.showIncludedViewOnSelect}
        onSortData={this.sortData}
        onFetchLayoutAndData={this.fetchLayoutAndData}
        onChangePage={this.handleChangePage}
        onFilterChange={this.handleFilterChange}
        onRedirectToDocument={this.redirectToDocument}
        onRedirectToNewDocument={this.onRedirectToNewDocument}
        onClearStaticFilters={this.clearStaticFilters}
        onResetInitialFilters={this.resetInitialFilters}
      />
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {object} DLpropTypes
 */
DocumentListContainer.propTypes = { ...DLpropTypes };

export default connect(
  DLmapStateToProps,
  {
    resetView,
    deleteView,
    fetchDocument,
    fetchLayout,
    createView,
    filterView,
    deleteTable,
    setListIncludedView,
    indicatorState,
    closeListIncludedView,
    setListPagination,
    setListSorting,
    setListId,
    push,
    updateRawModal,
    selectTableItems,
    deselectTableItems,
    removeSelectedTableItems,
    updateViewData,
    fetchLocationConfig,
    clearAllFilters,
  },
  null,
  { forwardRef: true }
)(DocumentListContainer);
