import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { Set as iSet } from 'immutable';
import currentDevice from 'current-device';
import { get, debounce } from 'lodash';

import { LOCATION_SEARCH_NAME } from '../constants/Constants';
import { locationSearchRequest, getViewRowsByIds } from '../api';
import { connectWS, disconnectWS } from '../utils/websockets';
import { deepUnfreeze } from '../utils';

import { getTableId } from '../reducers/tables';
import { getEntityRelatedId } from '../reducers/filters';

import {
  addViewLocationData,
  createView,
  fetchDocument,
  fetchLayout,
  fetchLocationConfig,
  fetchHeaderProperties,
  filterView,
  resetView,
  deleteView,
  showIncludedView,
  setIncludedView,
  unsetIncludedView,
} from '../actions/ViewActions';
import {
  deleteTable,
  updateGridTableData,
  deselectTableRows,
} from '../actions/TableActions';
import {
  setListId,
  setPagination as setListPagination,
  setSorting as setListSorting,
} from '../actions/ListActions';
import { updateRawModal, indicatorState } from '../actions/WindowActions';
import { setBreadcrumb } from '../actions/MenuActions';
import { deleteFilter } from '../actions/FiltersActions';
import { fetchQuickActions, deleteQuickActions } from '../actions/Actions';

import {
  DLpropTypes,
  DLmapStateToProps,
  GEO_PANEL_STATES,
  getSortingQuery,
  mergeColumnInfosIntoViewRows,
  mergeRows,
  parseToDisplay,
} from '../utils/documentListHelper';
import { filtersActiveContains } from '../utils/filterHelpers';

import DocumentList from '../components/app/DocumentList';

// TODO: This can be further simplified by extracting methods that are not responsible
// for fetching data to a child container/component (or maybe back to DocumentList component)
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
      initialValuesNulled: new Map(),
    };

    this.fetchLayoutAndData();
    this.debouncedRefresh = debounce(
      () => {
        this.browseView(true);
      },
      500,
      { maxWait: 10000 }
    );
  }

  UNSAFE_componentWillMount() {
    const { isModal, windowId, fetchLocationConfig } = this.props;

    fetchLocationConfig(windowId, isModal);
  }

  componentDidMount = () => {
    this.mounted = true;
  };

  componentWillUnmount() {
    const { isModal, windowId, viewId, deleteView, deleteTable } = this.props;

    this.mounted = false;
    disconnectWS.call(this);

    deleteTable(getTableId({ windowId, viewId }));
    deleteView(windowId, isModal);
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
    const {
      viewId: nextViewId,
      queryViewId: nextQueryViewId,
      isIncluded: nextIsIncluded,
      refDocumentId: nextRefDocumentId,
      referenceId: nextReferenceId,
      windowId: nextWindowId,
      viedData: nextViewData,
    } = nextProps;
    const {
      includedView,
      isIncluded,
      windowId,
      refDocumentId,
      referenceId,
      unsetIncludedView,
      viewId,
      resetView,
      deleteView,
      deleteTable,
      isModal,
      filters,
      queryViewId,
      viewData: { pending, layoutPending },
      deleteQuickActions,
    } = this.props;
    const staticFilterCleared = filters ? filters.staticFilterCleared : false;
    const included =
      includedView && includedView.windowId && includedView.viewId;
    const location = document.location;

    /*
     * If we browse list of docs, changing type of Document
     * does not re-construct component, so we need to
     * make it manually while the windowId changes.
     * OR
     * We want to refresh the window (generate new viewId)
     * OR
     * The reference ID is changed
     *
     * TODO: This could probably be handled by a combination of
     * middleware reacting to route changes and reducers
     */

    if (
      (queryViewId && !isIncluded && nextViewId !== nextQueryViewId) || // for the case when you applied a filter and come back via browser back button
      staticFilterCleared ||
      nextWindowId !== windowId ||
      (nextWindowId === windowId &&
        ((nextViewId !== viewId && isIncluded && nextIsIncluded) ||
          location.hash === '#notification')) ||
      nextRefDocumentId !== refDocumentId ||
      nextReferenceId !== referenceId
    ) {
      // if view is already loading or reloading (after filtering) don't fetch
      // the data and layout again
      if (
        !(pending || (nextViewData && nextViewData.pending)) &&
        !(layoutPending || (nextViewData && nextViewData.layoutPending))
      ) {
        deleteTable(getTableId({ windowId, viewId }));
        deleteQuickActions(windowId, viewId);

        const entityRelatedId = getEntityRelatedId({ windowId, viewId });
        deleteFilter(entityRelatedId);

        // if for instance we're replacing included view with a completely
        // different view, we have no use of the old one and can safely
        // remove it
        if (nextWindowId === windowId) {
          resetView(windowId, isModal);
        } else {
          deleteView(windowId, isModal);
        }

        this.setState(
          {
            initialValuesNulled: new Map(),
            panelsState: GEO_PANEL_STATES[0],
          },
          () => {
            // TODO: Check if we can just call `showIncludedView` to hide
            // it in the resetView Action Creator
            if (included) {
              unsetIncludedView(includedView);
            }

            this.fetchLayoutAndData();
          }
        );
      }
    }
  }

  /**
   * @method connectWebSocket
   * @summary Subscribe to websocket stream for this view
   */
  connectWebSocket = (customViewId) => {
    const {
      windowId,
      deselectTableRows,
      updateGridTableData,
      fetchHeaderProperties,
      fetchQuickActions,
      isModal,
      viewProfileId,
    } = this.props;
    const viewId = customViewId ? customViewId : this.props.viewId;

    connectWS.call(this, `/view/${viewId}`, (msg) => {
      const { fullyChanged, changedIds, headerPropertiesChanged } = msg;
      const table = this.props.table;

      if (changedIds) {
        getViewRowsByIds(windowId, viewId, changedIds.join()).then(
          (response) => {
            const tableId = getTableId({ windowId, viewId });
            const toRows = table.rows;
            const { pageColumnInfosByFieldName } = this.state;

            // merge changed rows with data in the store
            // TODO: I think we can move this to reducer
            const { rows, removedRows } = mergeRows({
              toRows,
              fromRows: [...response.data],
              columnInfosByFieldName: pageColumnInfosByFieldName,
              changedIds,
            });

            if (removedRows.length) {
              deselectTableRows({
                id: tableId,
                selection: removedRows,
                windowId,
                viewId,
                isModal,
              });
            } else {
              fetchQuickActions({
                windowId,
                viewId,
                selectedIds: table.selected,
                viewProfileId,
                isModal,
              });
            }

            updateGridTableData(tableId, rows);
          }
        );
      }

      if (headerPropertiesChanged) {
        fetchHeaderProperties({ windowId, viewId, isModal });
      }

      if (fullyChanged === true) {
        this.debouncedRefresh();
      }
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

    fetchLayout(windowId, type, viewProfileId, isModal)
      .then((response) => {
        if (this.mounted) {
          const { allowedCloseActions } = response;

          // TODO: Check if we still need to do this
          if (isModal) {
            if (allowedCloseActions) {
              updateRawModal(windowId, { allowedCloseActions });
            }
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

          if (response.data) {
            if (response.data.caption) {
              setModalTitle && setModalTitle(response.data.caption);
            }
            if (response.data.description) {
              setModalDescription &&
                setModalDescription(response.data.description);
            }
          }
        }
      })
      .catch((e) => {
        // TODO: Should we somehow handle errors here ?
        // eslint-disable-next-line no-console
        console.error(e);
      });
  };

  /**
   * @method browseView
   * @summary If viewId exists, than browse that view.
   */
  browseView = (websocketRefresh) => {
    const {
      viewId,
      page,
      sort,
      filters: { filtersActive },
    } = this.props;
    const locationSearchFilter = filtersActiveContains({
      filtersActive,
      key: LOCATION_SEARCH_NAME,
    });

    // in case of redirect from a notification, first call will have viewId empty
    if (viewId) {
      this.getData(
        viewId,
        page,
        sort,
        locationSearchFilter,
        websocketRefresh
      ).catch((err) => {
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
      referenceId,
      refType,
      refDocumentId,
      refTabId,
      refRowIds,
      page,
      sort,
      createView,
      setModalDescription,
      isModal,
      filters: { filtersActive },
    } = this.props;

    createView({
      windowId,
      viewType: type,
      filters: filtersActive,
      referenceId: referenceId,
      refDocType: refType,
      refDocumentId: refDocumentId,
      refTabId,
      refRowIds,
      isModal,
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
      .catch((e) => {
        // TODO: Should we somehow handle errors here ?
        // eslint-disable-next-line no-console
        console.error(e);
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
      sort,
      viewId,
      setIncludedView,
      setModalDescription,
      filterView,
      isModal,
      updateRawModal,
      filters,
      parentDefaultViewId,
      parentWindowType,
      viewProfileId,
    } = this.props;

    let { filtersActive } = filters;
    filtersActive = deepUnfreeze(filtersActive);

    // if we're applying filter, we should reset the page to the first one.
    // Otherwise we might get no results as there are not enough to fill more
    // than a single page.
    const page = 1;

    filterView(windowId, viewId, filtersActive, isModal)
      .then((response) => {
        const newViewId = response.viewId;

        this.connectWebSocket(newViewId);

        if (response.data && response.data.description && setModalDescription) {
          setModalDescription(response.data.description);
        }

        if (isIncluded) {
          const parentId =
            isModal || isModal === undefined
              ? parentWindowType
              : parentDefaultViewId;
          setIncludedView({
            windowId,
            viewId: newViewId,
            parentId,
            viewProfileId,
          });
        }

        if (isModal) {
          updateRawModal(windowId, { viewId: newViewId });
        }

        this.mounted && this.getData(newViewId, page, sort, locationAreaSearch);
      })
      .catch((e) => {
        // TODO: Should we somehow handle errors here ?
        // eslint-disable-next-line no-console
        console.error(e);
      });
  };

  /**
   * @method getData
   * @summary Loads view/included tab data from REST endpoint
   */
  getData = (id, page, sortingQuery, locationAreaSearch, websocketRefresh) => {
    const {
      windowId,
      updateUri,
      fetchDocument,
      indicatorState,
      updateRawModal,
      isModal,
      rawModalVisible,
      filters: { filtersActive },
    } = this.props;

    indicatorState('pending');

    if (updateUri) {
      const updateQuery = {
        viewId: id,
        page,
        sort: sortingQuery,
      };

      updateUri(updateQuery);
    }

    return fetchDocument({
      windowId,
      viewId: id,
      page,
      pageLength: this.pageLength,
      orderBy: sortingQuery,
      isModal,
      websocketRefresh,
    })
      .then((response) => {
        const result = response.result;
        const resultById = {};

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
          const locationSearchFilter = filtersActiveContains({
            filtersActive,
            key: LOCATION_SEARCH_NAME,
          });

          if (locationAreaSearch || locationSearchFilter) {
            this.getLocationData(resultById);
          }

          this.setState({ pageColumnInfosByFieldName });

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
      .catch((e) => {
        // TODO: Should we somehow handle errors here ?
        // eslint-disable-next-line no-console
        console.error(e);
      });
  };

  getLocationData = (resultById) => {
    const {
      windowId,
      viewId,
      viewData: { mapConfig },
      isModal,
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
        addViewLocationData(windowId, locationData, isModal);
      }

      if (mapConfig && mapConfig.provider && locationData.length) {
        // for mobile show map
        // for desktop show half-n-half
        this.setState({ panelsState: GEO_PANEL_STATES[1] });
      }
    });
  };

  /**
   * @method handleChangePage
   * @summary ToDo: Describe the method.
   */
  handleChangePage = (index) => {
    const { table, viewData, sort } = this.props;
    let currentPage = viewData.page;

    switch (index) {
      case 'up':
        currentPage * viewData.pageLength < table.size ? currentPage++ : null;
        break;
      case 'down':
        currentPage !== 1 ? currentPage-- : null;
        break;
      default:
        currentPage = index;
    }

    this.getData(viewData.viewId, currentPage, sort);
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
  handleFilterChange = (filtersActive) => {
    const locationSearchFilter = filtersActiveContains({
      filtersActive,
      key: LOCATION_SEARCH_NAME,
    });

    this.fetchLayoutAndData(true, locationSearchFilter);
  };

  /**
   * @method resetInitialFilters
   * @summary resets the initial filters
   * @param {string} filterId
   * @param {string} parameterName
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
      initialValuesNulled.delete(filterId);
    } else {
      initialValuesNulled.set(filterId, filterParams);
    }

    this.setState({
      initialValuesNulled,
    });
  };

  /**
   * @method toggleState
   *
   * @param {string} state - name of the panels layout to use
   * @summary Changes how the panels are laid out
   */
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
      viewData,
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
      setListId(viewData.viewId, windowId);
    }
  };

  /**
   * @method redirectToNewDocument
   * @summary Redirect to a new document
   */
  redirectToNewDocument = () => {
    const { push, windowId } = this.props;

    push(`/window/${windowId}/new`);
  };

  /**
   * @method showSelectedIncludedView
   * @summary ToDo: Describe the method.
   */
  showSelectedIncludedView = (selected) => {
    const {
      table: { rows },
      layout,
      isModal,
      windowId,
    } = this.props;
    const openIncludedViewOnSelect =
      layout.includedView && layout.includedView.openOnSelect;

    if (openIncludedViewOnSelect && selected.length === 1) {
      rows.forEach((item) => {
        if (item.id === selected[0]) {
          showIncludedView({
            id: windowId,
            showIncludedView: item.supportIncludedViews,
            windowId: item.supportIncludedViews
              ? item.includedView.windowId || item.includedView.windowId
              : null,
            viewId: item.supportIncludedViews ? item.includedView.viewId : '',
            isModal,
          });
        }
      });
    }
  };

  render() {
    const {
      includedView,
      layout,
      layoutPending,
      viewData: { pending },
    } = this.props;

    const hasIncluded =
      layout &&
      layout.includedView &&
      includedView &&
      includedView.windowId &&
      includedView.viewId;
    const triggerSpinner = layoutPending || pending;

    return (
      <DocumentList
        {...this.props}
        {...this.state}
        triggerSpinner={triggerSpinner}
        hasIncluded={hasIncluded}
        onToggleState={this.toggleState}
        pageLength={this.pageLength}
        onGetSelected={this.getSelected}
        onShowSelectedIncludedView={this.showSelectedIncludedView}
        onSortData={this.sortData}
        onFetchLayoutAndData={this.fetchLayoutAndData}
        onChangePage={this.handleChangePage}
        onFilterChange={this.handleFilterChange}
        onRedirectToDocument={this.redirectToDocument}
        onRedirectToNewDocument={this.onRedirectToNewDocument}
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
    indicatorState,
    unsetIncludedView,
    setIncludedView,
    setListPagination,
    setListSorting,
    setListId,
    showIncludedView,
    push,
    updateRawModal,
    deselectTableRows,
    fetchLocationConfig,
    updateGridTableData,
    fetchHeaderProperties,
    setBreadcrumb,
    fetchQuickActions,
    deleteQuickActions,
  },
  null,
  { forwardRef: true }
)(DocumentListContainer);
