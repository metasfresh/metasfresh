import counterpart from 'counterpart';
import cx from 'classnames';
import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { PROCESS_NAME } from '../../../constants/Constants';
import {
  NO_VIEW,
  PANEL_WIDTHS,
  GEO_PANEL_STATES,
  filtersToMap,
  DLpropTypes,
} from '../../../utils/documentListHelper';
import Spinner from '../../app/SpinnerOverlay';
import BlankPage from '../../BlankPage';
import DataLayoutWrapper from '../../DataLayoutWrapper';
import Filters from '../../filters/Filters';
import FiltersStatic from '../../filters/FiltersStatic';
import Table from '../../table/Table';
import QuickActions from '../QuickActions';
import SelectionAttributes from '../SelectionAttributes';
import GeoMap from '../../maps/GeoMap';

/**
 * @file Class based component.
 * @module DocumentList
 * @extends Component
 */
export default class DocumentList extends Component {
  constructor(props) {
    super(props);

    this.state = {
      clickOutsideLock: false,
      toggleWidth: 0,
      // in some scenarios we don't want to reload table data
      // after edit, as it triggers request, collapses rows and looses selection
      rowEdited: false,
    };
  }

  /**
   * @method setClickOutsideLock
   * @summary ToDo: Describe the method.
   */
  setClickOutsideLock = (value) => {
    this.setState({
      clickOutsideLock: !!value,
    });
  };

  /**
   * @method collapseGeoPanels
   * @summary Show/hide map and results of geolocation search.
   */
  collapseGeoPanels = () => {
    const { onToggleState, panelsState } = this.props;
    const stateIdx = GEO_PANEL_STATES.indexOf(panelsState);
    const newStateIdx =
      stateIdx + 1 === GEO_PANEL_STATES.length ? 0 : stateIdx + 1;

    onToggleState(GEO_PANEL_STATES[newStateIdx]);
  };

  /**
   * @method updateQuickActions
   * @summary ToDo: Describe the method.
   */
  updateQuickActions = (childSelection) => {
    if (this.quickActionsComponent) {
      this.quickActionsComponent.updateActions(childSelection);
    }
  };

  /**
   * @method setTableRowEdited
   * @summary ToDo: Describe the method.
   */
  setTableRowEdited = (val) => {
    this.setState(
      {
        rowEdited: val,
      },
      () => this.updateQuickActions()
    );
  };

  /**
   * @method adjustWidth
   * @summary Sets the width of the panel to be of % of the available space.
   * Options available in PANEL_WIDTHS constant. Useful for mobile views.
   */
  adjustWidth = () => {
    const widthIdx =
      this.state.toggleWidth + 1 === PANEL_WIDTHS.length
        ? 0
        : this.state.toggleWidth + 1;

    this.setState({
      toggleWidth: widthIdx,
    });
  };

  render() {
    const {
      windowType,
      viewProfileId,
      open,
      closeOverlays,
      parentDefaultViewId,
      inBackground,
      fetchQuickActionsOnInit,
      // TODO: Check if we need two separate flags
      isModal,
      inModal,
      processStatus,
      readonly,
      includedView,
      isIncluded,
      disablePaginationShortcuts,
      disconnectFromState,
      autofocus,
      updateParentSelectedIds,
      modal,
      parentWindowType,
      reduxData,
      layout,
      page,
      pageLength,
      panelsState,
      onGetSelected,
      onFetchLayoutAndData,
      onChangePage,
      onRedirectToDocument,
      filtersActive,
      isShowIncluded,
      hasShowIncluded,
      refreshSelection,
      mapConfig,
      initialValuesNulled,
      triggerSpinner,
      viewId,
      onFilterChange,
      onResetInitialFilters,
      supportAttribute,
      hasIncluded,
      selectionValid,
      onRedirectToNewDocument,
      onShowIncludedViewOnSelect,
    } = this.props;
    const { rowData, size, staticFilters, orderBy, queryLimitHit } = reduxData;
    const { rowEdited, clickOutsideLock, toggleWidth } = this.state;

    let { selected, childSelected, parentSelected } = onGetSelected();
    const modalType = modal ? modal.modalType : null;
    const stopShortcutPropagation =
      (isIncluded && !!selected) || (inModal && modalType === PROCESS_NAME);

    const styleObject = {};
    if (toggleWidth !== 0) {
      styleObject.flex = PANEL_WIDTHS[toggleWidth];
    }

    if (!selectionValid) {
      selected = null;
    }

    const blurWhenOpen =
      layout && layout.includedView && layout.includedView.blurWhenOpen;

    if (layout.notfound || reduxData.notfound) {
      return (
        <BlankPage what={counterpart.translate('view.error.windowName')} />
      );
    }

    const showQuickActions = true;
    const showModalResizeBtn =
      layout && isModal && hasIncluded && hasShowIncluded;
    const showGeoResizeBtn =
      layout &&
      layout.supportGeoLocations &&
      reduxData &&
      reduxData.locationData;

    return (
      <div
        className={cx('document-list-wrapper', {
          'document-list-included': isShowIncluded || isIncluded,
          'document-list-is-included': isIncluded,
          'document-list-has-included': hasShowIncluded || hasIncluded,
        })}
        style={styleObject}
      >
        {showModalResizeBtn && (
          <div className="column-size-button col-xxs-3 col-md-0 ignore-react-onclickoutside">
            <button
              className={cx(
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

        {layout && !readonly && (
          <div
            className={cx(
              'panel panel-primary panel-spaced panel-inline document-list-header',
              {
                posRelative: showGeoResizeBtn,
              }
            )}
          >
            <div className={cx('header-element', { disabled: hasIncluded })}>
              {layout.supportNewRecord && !isModal && (
                <button
                  className="btn btn-meta-outline-secondary btn-distance btn-sm hidden-sm-down btn-new-document"
                  onClick={onRedirectToNewDocument}
                  title={layout.newRecordCaption}
                >
                  <i className="meta-icon-add" />
                  {layout.newRecordCaption}
                </button>
              )}

              {layout.filters && (
                <Filters
                  {...{
                    windowType,
                    viewId,
                    filtersActive,
                    initialValuesNulled,
                  }}
                  filterData={filtersToMap(layout.filters)}
                  updateDocList={onFilterChange}
                  resetInitialValues={onResetInitialFilters}
                />
              )}

              {rowData && staticFilters && (
                <FiltersStatic
                  {...{ windowType, viewId }}
                  data={staticFilters}
                  clearFilters={this.clearStaticFilters}
                />
              )}
            </div>

            {showGeoResizeBtn && (
              <div className="header-element pane-size-button ignore-react-onclickoutside">
                <button
                  className={cx(
                    'btn btn-meta-outline-secondary btn-sm btn-switch ignore-react-onclickoutside'
                  )}
                  onClick={this.collapseGeoPanels}
                >
                  <i
                    className={cx('icon icon-grid', {
                      greyscaled: panelsState === 'map',
                    })}
                  />
                  <i className="icon text-middle">/</i>
                  <i
                    className={cx('icon icon-map', {
                      greyscaled: panelsState === 'grid',
                    })}
                  />
                </button>
              </div>
            )}

            {rowData && showQuickActions && (
              <QuickActions
                className="header-element align-items-center"
                processStatus={processStatus}
                ref={(c) => {
                  this.quickActionsComponent = c;
                }}
                selected={selected}
                viewId={viewId}
                windowType={windowType}
                viewProfileId={viewProfileId}
                fetchOnInit={fetchQuickActionsOnInit}
                disabled={hasIncluded && blurWhenOpen}
                shouldNotUpdate={inBackground && !hasIncluded}
                inBackground={disablePaginationShortcuts}
                inModal={inModal}
                stopShortcutPropagation={stopShortcutPropagation}
                childView={
                  hasIncluded
                    ? {
                        viewId: includedView.viewId,
                        viewSelectedIds: childSelected,
                        windowType: includedView.windowType,
                      }
                    : NO_VIEW
                }
                parentView={
                  isIncluded
                    ? {
                        viewId: parentDefaultViewId,
                        viewSelectedIds: parentSelected,
                        windowType: parentWindowType,
                      }
                    : NO_VIEW
                }
                onInvalidViewId={onFetchLayoutAndData}
              />
            )}
          </div>
        )}

        <Spinner
          parent={this}
          delay={300}
          iconSize={50}
          displayCondition={!!(layout && triggerSpinner)}
          hideCondition={!!(rowData && !triggerSpinner)}
        />

        {layout && rowData && (
          <div className="document-list-body">
            <div className="row table-row">
              <Table
                entity="documentView"
                ref={(c) => (this.table = c)}
                rowData={rowData}
                cols={layout.elements}
                collapsible={layout.collapsible}
                expandedDepth={layout.expandedDepth}
                tabId={1}
                windowId={windowType}
                emptyText={layout.emptyResultText}
                emptyHint={layout.emptyResultHint}
                readonly={true}
                supportOpenRecord={layout.supportOpenRecord}
                onRowEdited={this.setTableRowEdited}
                keyProperty="id"
                onDoubleClick={onRedirectToDocument}
                handleChangePage={onChangePage}
                onSelectionChanged={updateParentSelectedIds}
                mainTable={true}
                updateDocList={onFetchLayoutAndData}
                sort={this.sortData}
                tabIndex={0}
                indentSupported={layout.supportTree}
                disableOnClickOutside={clickOutsideLock}
                limitOnClickOutside={isModal}
                defaultSelected={selected}
                refreshSelection={refreshSelection}
                queryLimitHit={queryLimitHit}
                showIncludedViewOnSelect={onShowIncludedViewOnSelect}
                openIncludedViewOnSelect={
                  layout.includedView && layout.includedView.openOnSelect
                }
                blurOnIncludedView={blurWhenOpen}
                focusOnFieldName={layout.focusOnFieldName}
                toggleState={panelsState}
                spinnerVisible={triggerSpinner}
                {...{
                  orderBy,
                  rowEdited,
                  size,
                  pageLength,
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
                {layout.supportAttributes && !isIncluded && !hasIncluded && (
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
              <GeoMap
                toggleState={panelsState}
                mapConfig={mapConfig}
                data={reduxData.locationData}
              />
            </div>
          </div>
        )}
      </div>
    );
  }
}

DocumentList.propTypes = {
  ...DLpropTypes,
  mapConfig: PropTypes.object,
  panelsState: PropTypes.string,
  pageLength: PropTypes.number,
  filtersActive: PropTypes.any,
  initialValuesNulled: PropTypes.any,
  isShowIncluded: PropTypes.bool,
  hasShowIncluded: PropTypes.bool,
  triggerSpinner: PropTypes.bool,
  onToggleState: PropTypes.func,
  onGetSelected: PropTypes.func,
  onShowIncludedViewOnSelect: PropTypes.func,
  onSortData: PropTypes.func,
  onFetchLayoutAndData: PropTypes.func,
  onChangePage: PropTypes.func,
  onFilterChange: PropTypes.func,
  onRedirectToDocument: PropTypes.func,
  onClearStaticFilters: PropTypes.func,
  onResetInitialFilters: PropTypes.func,
  onRedirectToNewDocument: PropTypes.func,
};
