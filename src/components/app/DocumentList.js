import counterpart from 'counterpart';
import classnames from 'classnames';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'react-router-redux';
import { Map, List } from 'immutable';
import currentDevice from 'current-device';

import {
  getViewLayout,
  browseViewRequest,
  createViewRequest,
  deleteStaticFilter,
  filterViewRequest,
  getViewRowsByIds,
} from '../../api';
import {
  closeListIncludedView,
  setListId,
  setListIncludedView,
  setPagination,
  setSorting,
} from '../../actions/ListActions';
import {
  mergeColumnInfosIntoViewRows,
  mergeRows,
} from '../../actions/ViewActions';
import {
  connectWS,
  disconnectWS,
  getItemsByProperty,
  getRowsData,
  indicatorState,
  mapIncluded,
  parseToDisplay,
  selectTableItems,
  removeSelectedTableItems,
} from '../../actions/WindowActions';
import { getSelection, getSelectionDirect } from '../../reducers/windowHandler';
import BlankPage from '../BlankPage';
import DataLayoutWrapper from '../DataLayoutWrapper';
import Filters from '../filters/Filters';
import FiltersStatic from '../filters/FiltersStatic';
import Table from '../table/Table';
import QuickActions from './QuickActions';
import SelectionAttributes from './SelectionAttributes';

const NO_SELECTION = [];
const NO_VIEW = {};
const PANEL_WIDTHS = ['1', '.2', '4'];

class DocumentList extends Component {
  static propTypes = {
    // from parent
    windowType: PropTypes.string.isRequired,

    // from <DocList>
    updateParentSelectedIds: PropTypes.func,

    // from @connect
    dispatch: PropTypes.func.isRequired,
    selections: PropTypes.object.isRequired,
    childSelected: PropTypes.array.isRequired,
    parentSelected: PropTypes.array.isRequired,
    selected: PropTypes.array.isRequired,
    isModal: PropTypes.bool,
  };

  static contextTypes = {
    store: PropTypes.object.isRequired,
  };

  constructor(props) {
    super(props);

    const { defaultViewId, defaultPage, defaultSort } = props;

    this.pageLength =
      currentDevice.type === 'mobile' || currentDevice.type === 'tablet'
        ? 9999
        : 20;
    this.supportAttribute = false;

    this.state = {
      data: null, // view result (result, firstRow, pageLength etc)
      layout: null,
      pageColumnInfosByFieldName: null,
      toggleWidth: 0,

      viewId: defaultViewId,
      page: defaultPage || 1,
      sort: defaultSort,
      filters: null,

      clickOutsideLock: false,

      isShowIncluded: false,
      hasShowIncluded: false,

      // in some scenarios we don't want to reload table data
      // after edit, as it triggers request, collapses rows and looses selection
      rowEdited: false,
    };

    this.fetchLayoutAndData();
  }

  componentDidMount = () => {
    this.mounted = true;
  };

  componentWillUnmount() {
    this.mounted = false;
    disconnectWS.call(this);
  }

  componentWillReceiveProps(nextProps) {
    const {
      defaultPage: nextDefaultPage,
      defaultSort: nextDefaultSort,
      defaultViewId: nextDefaultViewId,
      includedView: nextIncludedView,
      isIncluded: nextIsIncluded,
      refId: nextRefId,
      windowType: nextWindowType,
    } = nextProps;

    const {
      defaultPage,
      defaultSort,
      defaultViewId,
      includedView,
      isIncluded,
      refId,
      windowType,
      dispatch,
    } = this.props;
    const { page, sort, viewId, staticFilterCleared } = this.state;
    const included =
      includedView && includedView.windowType && includedView.viewId;
    const nextIncluded =
      nextIncludedView &&
      nextIncludedView.windowType &&
      nextIncludedView.viewId;
    const location = document.location;

    this.loadSupportAttributeFlag(nextProps);

    /*
         * If we browse list of docs, changing type of Document
         * does not re-construct component, so we need to
         * make it manually while the windowType changes.
         * OR
         * We want to refresh the window (generate new viewId)
         * OR
         * The reference ID is changed
         */
    if (
      staticFilterCleared ||
      nextWindowType !== windowType ||
      (nextDefaultViewId === undefined &&
        nextDefaultViewId !== defaultViewId) ||
      (nextWindowType === windowType &&
        ((nextDefaultViewId !== defaultViewId &&
          isIncluded &&
          nextIsIncluded) ||
          location.hash === '#notification')) ||
      nextRefId !== refId
    ) {
      this.setState(
        {
          data: null,
          layout: null,
          filters: null,
          viewId: location.hash === '#notification' ? this.state.viewId : null,
          staticFilterCleared: false,
        },
        () => {
          if (included) {
            dispatch(closeListIncludedView(includedView));
          }

          this.fetchLayoutAndData();
        }
      );
    }

    const stateChanges = {};

    if (nextDefaultSort !== defaultSort && nextDefaultSort !== sort) {
      stateChanges.sort = nextDefaultSort;
    }

    if (nextDefaultPage !== defaultPage && nextDefaultPage !== page) {
      stateChanges.page = nextDefaultPage || 1;
    }

    if (nextDefaultViewId !== viewId) {
      dispatch(removeSelectedTableItems({ viewId: viewId, windowType }));

      stateChanges.viewId = nextDefaultViewId;
      stateChanges.refreshSelection = true;
    }

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

  shouldComponentUpdate(nextProps, nextState) {
    return !!nextState.layout && !!nextState.data;
  }

  componentDidUpdate(prevProps, prevState) {
    const { setModalDescription } = this.props;
    const { data } = this.state;

    if (prevState.data !== data && setModalDescription) {
      setModalDescription(data.description);
    }
  }

  connectWebSocket = viewId => {
    const { windowType } = this.props;

    connectWS.call(this, `/view/${viewId}`, msg => {
      const { fullyChanged, changedIds } = msg;
      if (changedIds) {
        getViewRowsByIds(windowType, viewId, changedIds.join()).then(
          response => {
            const rows = mergeRows({
              toRows: this.state.data.result.toArray(),
              fromRows: [...response.data],
              columnInfosByFieldName: this.state.pageColumnInfosByFieldName,
            });

            this.setState({
              data: {
                ...this.state.data,
                result: List(rows),
              },
            });
          }
        );
      }

      if (fullyChanged == true) {
        const { dispatch, windowType, selections } = this.props;
        const { viewId } = this.state;
        const selection = getSelectionDirect(selections, windowType, viewId);

        // Reload Attributes after QuickAction is done
        selection.length &&
          dispatch(
            selectTableItems({
              windowType,
              viewId,
              ids: [selection[0]],
            })
          );

        this.browseView();
        this.updateQuickActions();
      }
    });
  };

  updateQuickActions = childSelection => {
    if (this.quickActionsComponent) {
      this.quickActionsComponent.updateActions(childSelection);
    }
  };

  /**
   * load supportAttribute of the selected row from the table
   */
  loadSupportAttributeFlag = ({ selected }) => {
    const { data } = this.state;
    if (!data) {
      return;
    }
    const rows = getRowsData(data.result);

    if (selected.length === 1) {
      const selectedRow = rows.find(row => row.id === selected[0]);

      this.supportAttribute = selectedRow && selectedRow.supportAttributes;
      this.setState({
        supportAttribute: selectedRow && selectedRow.supportAttributes,
      });
    } else {
      this.supportAttribute = false;
      this.setState({
        supportAttribute: false,
      });
    }
  };

  doesSelectionExist({ data, selected, hasIncluded = false } = {}) {
    // When the rows are changing we should ensure
    // that selection still exist
    if (hasIncluded) {
      return true;
    }

    if (selected && selected[0] === 'all') {
      return true;
    }

    let rows = [];

    data &&
      data.result &&
      data.result.map(item => {
        rows = rows.concat(mapIncluded(item));
      });

    return (
      data &&
      data.size &&
      data.result &&
      selected &&
      selected[0] &&
      getItemsByProperty(rows, 'id', selected[0]).length
    );
  }

  getTableData = data => {
    return data;
  };

  redirectToNewDocument = () => {
    const { dispatch, windowType } = this.props;

    dispatch(push(`/window/${windowType}/new`));
  };

  setClickOutsideLock = value => {
    this.setState({
      clickOutsideLock: !!value,
    });
  };

  clearStaticFilters = filterId => {
    const { dispatch, windowType } = this.props;
    const { viewId } = this.state;

    deleteStaticFilter(windowType, viewId, filterId).then(response => {
      this.setState({ staticFilterCleared: true }, () =>
        dispatch(push(`/window/${windowType}?viewId=${response.data.viewId}`))
      );
    });
  };

  // FETCHING LAYOUT && DATA -------------------------------------------------

  fetchLayoutAndData = isNewFilter => {
    const {
      windowType,
      type,
      viewProfileId,
      setModalTitle,
      setNotFound,
    } = this.props;
    const { viewId } = this.state;

    getViewLayout(windowType, type, viewProfileId)
      .then(response => {
        this.mounted &&
          this.setState(
            {
              layout: response.data,
            },
            () => {
              if (viewId) {
                this.connectWebSocket(viewId);

                if (!isNewFilter) {
                  this.browseView();
                } else {
                  this.filterView();
                }
              } else {
                this.createView();
              }
              setModalTitle && setModalTitle(response.data.caption);
            }
          );
      })
      .catch(() => {
        // We have to always update that fields to refresh that view!
        // Check the shouldComponentUpdate method
        this.setState(
          {
            data: 'notfound',
            layout: 'notfound',
          },
          () => {
            setNotFound && setNotFound(true);
          }
        );
      });
  };

  /*
     *  If viewId exist, than browse that view.
     */
  browseView = () => {
    const { viewId, page, sort } = this.state;

    // in case of redirect from a notification, first call will have viewId empty
    if (viewId) {
      this.getData(viewId, page, sort).catch(err => {
        if (err.response && err.response.status === 404) {
          this.createView();
        }
      });
    }
  };

  createView = () => {
    const {
      windowType,
      type,
      refType,
      refId,
      refTabId,
      refRowIds,
    } = this.props;
    const { page, sort, filters } = this.state;

    createViewRequest({
      windowId: windowType,
      viewType: type,
      filters,
      refDocType: refType,
      refDocId: refId,
      refTabId,
      refRowIds,
    }).then(response => {
      this.mounted &&
        this.setState(
          {
            data: response.data,
            viewId: response.data.viewId,
          },
          () => {
            this.connectWebSocket(response.data.viewId);
            this.getData(response.data.viewId, page, sort);
          }
        );
    });
  };

  filterView = () => {
    const { windowType, isIncluded, dispatch } = this.props;
    const { page, sort, filters, viewId } = this.state;

    filterViewRequest(windowType, viewId, filters).then(response => {
      const viewId = response.data.viewId;

      if (isIncluded) {
        dispatch(setListIncludedView({ windowType, viewId }));
      }

      this.mounted &&
        this.setState(
          {
            data: response.data,
            viewId: viewId,
          },
          () => {
            this.getData(viewId, page, sort);
          }
        );
    });
  };

  /**
   * Loads view/included tab data from REST endpoint
   */
  getData = (id, page, sortingQuery) => {
    const {
      dispatch,
      windowType,
      selections,
      updateUri,
      setNotFound,
      type,
      isIncluded,
    } = this.props;
    const { viewId } = this.state;

    if (setNotFound) {
      setNotFound(false);
    }
    dispatch(indicatorState('pending'));

    if (updateUri) {
      id && updateUri('viewId', id);
      page && updateUri('page', page);
      sortingQuery && updateUri('sort', sortingQuery);
    }

    return browseViewRequest({
      windowId: windowType,
      viewId: id,
      page: page,
      pageLength: this.pageLength,
      orderBy: sortingQuery,
    }).then(response => {
      const result = List(response.data.result);
      result.hashCode();

      const selection = getSelectionDirect(selections, windowType, viewId);
      const forceSelection =
        (type === 'includedView' || isIncluded) &&
        response.data &&
        result.size > 0 &&
        (selection.length === 0 ||
          !this.doesSelectionExist({
            data: {
              ...response.data,
              result,
            },
            selected: selection,
          }));

      result.map(row => {
        row.fieldsByName = parseToDisplay(row.fieldsByName);
      });

      const pageColumnInfosByFieldName = response.data.columnsByFieldName;
      mergeColumnInfosIntoViewRows(
        pageColumnInfosByFieldName,
        response.data.result
      );

      if (this.mounted) {
        this.setState(
          {
            data: {
              ...response.data,
              result,
            },
            pageColumnInfosByFieldName: pageColumnInfosByFieldName,
            filters: response.data.filters,
          },
          () => {
            if (forceSelection && response.data && result && result.size > 0) {
              const selection = [result.get(0).id];

              dispatch(
                selectTableItems({
                  windowType,
                  viewId,
                  ids: selection,
                })
              );
            }
          }
        );
      }

      dispatch(indicatorState('saved'));
    });
  };

  // END OF FETCHING LAYOUT && DATA ------------------------------------------

  // MANAGING SORT, PAGINATION, FILTERS --------------------------------------

  handleChangePage = index => {
    const { data, sort, page, viewId } = this.state;
    let currentPage = page;

    switch (index) {
      case 'up':
        currentPage * data.pageLength < data.size ? currentPage++ : null;
        break;
      case 'down':
        currentPage != 1 ? currentPage-- : null;
        break;
      default:
        currentPage = index;
    }

    this.setState(
      {
        page: currentPage,
      },
      () => {
        this.getData(viewId, currentPage, sort);
      }
    );
  };

  getSortingQuery = (asc, field) => (asc ? '+' : '-') + field;

  sortData = (asc, field, startPage) => {
    const { viewId, page } = this.state;

    this.setState(
      {
        sort: this.getSortingQuery(asc, field),
      },
      () => {
        this.getData(
          viewId,
          startPage ? 1 : page,
          this.getSortingQuery(asc, field)
        );
      }
    );
  };

  handleFilterChange = filters => {
    this.setState(
      {
        filters: filters,
        page: 1,
      },
      () => {
        this.fetchLayoutAndData(true);
      }
    );
  };

  // END OF MANAGING SORT, PAGINATION, FILTERS -------------------------------

  setTableRowEdited = val => {
    this.setState({
      rowEdited: val,
    });
  };

  adjustWidth = () => {
    const widthIdx =
      this.state.toggleWidth + 1 === PANEL_WIDTHS.length
        ? 0
        : this.state.toggleWidth + 1;

    this.setState({
      toggleWidth: widthIdx,
    });
  };

  redirectToDocument = id => {
    const { dispatch, isModal, windowType, isSideListShow } = this.props;
    const { page, viewId, sort } = this.state;

    if (isModal) {
      return;
    }

    dispatch(push(`/window/${windowType}/${id}`));

    if (!isSideListShow) {
      // Caching last settings
      dispatch(setPagination(page, windowType));
      dispatch(setSorting(sort, windowType));
      dispatch(setListId(viewId, windowType));
    }
  };

  showIncludedViewOnSelect = ({
    showIncludedView,
    windowType,
    viewId,
    forceClose,
  } = {}) => {
    const { dispatch } = this.props;

    this.setState(
      {
        isShowIncluded: !!showIncludedView,
        hasShowIncluded: !!showIncludedView,
      },
      () => {
        if (showIncludedView) {
          dispatch(setListIncludedView({ windowType, viewId }));
        }
      }
    );

    // can't use setState callback because component might be unmounted and
    // callback is never called
    if (!showIncludedView) {
      dispatch(closeListIncludedView({ windowType, viewId, forceClose }));
    }
  };

  getSelected = () => {
    const {
      selections,
      windowType,
      includedView,
      parentWindowType,
      parentDefaultViewId,
    } = this.props;
    const { viewId } = this.state;

    return {
      selected: getSelectionDirect(selections, windowType, viewId),
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
      windowType,
      open,
      closeOverlays,
      parentDefaultViewId,
      inBackground,
      fetchQuickActionsOnInit,
      isModal,
      processStatus,
      readonly,
      includedView,
      isIncluded,
      disablePaginationShortcuts,
      notfound,
      disconnectFromState,
      autofocus,
      inModal,
      updateParentSelectedIds,
    } = this.props;

    const {
      layout,
      data,
      viewId,
      clickOutsideLock,
      page,
      filters,
      isShowIncluded,
      hasShowIncluded,
      refreshSelection,
      supportAttribute,
      toggleWidth,
      rowEdited,
    } = this.state;
    const { selected, childSelected, parentSelected } = this.getSelected();

    const styleObject = {};
    if (toggleWidth !== 0) {
      styleObject.flex = PANEL_WIDTHS[toggleWidth];
    }

    const hasIncluded =
      layout &&
      layout.includedView &&
      includedView &&
      includedView.windowType &&
      includedView.viewId;

    const selectionValid = this.doesSelectionExist({
      data,
      selected,
      hasIncluded,
    });
    const blurWhenOpen =
      layout && layout.includedView && layout.includedView.blurWhenOpen;

    if (notfound || layout === 'notfound' || data === 'notfound') {
      return (
        <BlankPage what={counterpart.translate('view.error.windowName')} />
      );
    }

    const showQuickActions = Boolean(
      !isModal || inBackground || selectionValid
    );

    if (layout && data) {
      return (
        <div
          className={classnames('document-list-wrapper', {
            'document-list-included': isShowIncluded || isIncluded,
            'document-list-has-included': hasShowIncluded || hasIncluded,
          })}
          style={styleObject}
        >
          {isModal &&
            hasIncluded &&
            hasShowIncluded && (
              <div className="column-size-button col-xxs-3 col-md-0 ignore-react-onclickoutside">
                <button
                  className={classnames(
                    'btn btn-meta-outline-secondary btn-sm ignore-react-onclickoutside',
                    {
                      normal: toggleWidth === 0,
                      narrow: toggleWidth === 1,
                      wide: toggleWidth === 2,
                    }
                  )}
                  onClick={this.adjustWidth}
                />
              </div>
            )}
          {!readonly && (
            <div className="panel panel-primary panel-spaced panel-inline document-list-header">
              <div className={hasIncluded ? 'disabled' : ''}>
                {layout.supportNewRecord &&
                  !isModal && (
                    <button
                      className="btn btn-meta-outline-secondary btn-distance btn-sm hidden-sm-down btn-new-document"
                      onClick={() => this.redirectToNewDocument()}
                      title={layout.newRecordCaption}
                    >
                      <i className="meta-icon-add" />
                      {layout.newRecordCaption}
                    </button>
                  )}

                {layout.filters && (
                  <Filters
                    {...{ windowType, viewId }}
                    filterData={layout.filters}
                    filtersActive={filters}
                    updateDocList={this.handleFilterChange}
                  />
                )}

                {data.staticFilters && (
                  <FiltersStatic
                    {...{ windowType, viewId }}
                    data={data.staticFilters}
                    clearFilters={this.clearStaticFilters}
                  />
                )}
              </div>

              {showQuickActions && (
                <QuickActions
                  processStatus={processStatus}
                  ref={c => {
                    this.quickActionsComponent = c && c.getWrappedInstance();
                  }}
                  selected={selected}
                  viewId={viewId}
                  windowType={windowType}
                  fetchOnInit={fetchQuickActionsOnInit}
                  disabled={hasIncluded && blurWhenOpen}
                  shouldNotUpdate={inBackground && !hasIncluded}
                  inBackground={disablePaginationShortcuts}
                  inModal={inModal}
                  stopShortcutPropagation={isIncluded && !!selected}
                  childView={
                    hasIncluded
                      ? {
                          viewId: includedView.viewId,
                          viewSelectedIds: childSelected,
                        }
                      : NO_VIEW
                  }
                  parentView={
                    isIncluded
                      ? {
                          viewId: parentDefaultViewId,
                          viewSelectedIds: parentSelected,
                        }
                      : NO_VIEW
                  }
                />
              )}
            </div>
          )}
          <div className="document-list-body">
            <Table
              entity="documentView"
              ref={c =>
                (this.table =
                  c &&
                  c.getWrappedInstance() &&
                  c.getWrappedInstance().instanceRef)
              }
              rowData={Map({ 1: data.result })}
              cols={layout.elements}
              collapsible={layout.collapsible}
              expandedDepth={layout.expandedDepth}
              tabid={1}
              type={windowType}
              emptyText={layout.emptyResultText}
              emptyHint={layout.emptyResultHint}
              readonly={true}
              rowEdited={rowEdited}
              onRowEdited={this.setTableRowEdited}
              keyProperty="id"
              onDoubleClick={id => !isIncluded && this.redirectToDocument(id)}
              size={data.size}
              pageLength={this.pageLength}
              handleChangePage={this.handleChangePage}
              onSelectionChanged={updateParentSelectedIds}
              mainTable={true}
              updateDocList={this.fetchLayoutAndData}
              sort={this.sortData}
              orderBy={data.orderBy}
              tabIndex={0}
              indentSupported={layout.supportTree}
              disableOnClickOutside={clickOutsideLock}
              defaultSelected={selected}
              refreshSelection={refreshSelection}
              queryLimitHit={data.queryLimitHit}
              showIncludedViewOnSelect={this.showIncludedViewOnSelect}
              openIncludedViewOnSelect={
                layout.includedView && layout.includedView.openOnSelect
              }
              blurOnIncludedView={blurWhenOpen}
              {...{
                isIncluded,
                disconnectFromState,
                autofocus,
                open,
                page,
                closeOverlays,
                inBackground,
                disablePaginationShortcuts,
                isModal,
                hasIncluded,
                viewId,
                windowType,
              }}
            >
              {layout.supportAttributes &&
                !isIncluded &&
                !hasIncluded && (
                  <DataLayoutWrapper
                    className="table-flex-wrapper attributes-selector js-not-unselect"
                    entity="documentView"
                    {...{ windowType, viewId }}
                    onRowEdited={this.setTableRowEdited}
                  >
                    <SelectionAttributes
                      supportAttribute={supportAttribute}
                      setClickOutsideLock={this.setClickOutsideLock}
                      selected={selectionValid ? selected : undefined}
                      shouldNotUpdate={inBackground}
                    />
                  </DataLayoutWrapper>
                )}
            </Table>
          </div>
        </div>
      );
    } else {
      return false;
    }
  }
}

const mapStateToProps = (state, props) => ({
  selections: state.windowHandler.selections,
  selected: getSelection({
    state,
    windowType: props.windowType,
    viewId: props.defaultViewId,
  }),
  childSelected:
    props.includedView && props.includedView.windowType
      ? getSelection({
          state,
          windowType: props.includedView.windowType,
          viewId: props.includedView.viewId,
        })
      : NO_SELECTION,
  parentSelected: props.parentWindowType
    ? getSelection({
        state,
        windowType: props.parentWindowType,
        viewId: props.parentDefaultViewId,
      })
    : NO_SELECTION,
});

export default connect(mapStateToProps, null, null, { withRef: true })(
  DocumentList
);
