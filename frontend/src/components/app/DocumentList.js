import counterpart from 'counterpart';
import cx from 'classnames';
import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { PROCESS_NAME } from '../../constants/Constants';
import {
  DLpropTypes,
  GEO_PANEL_STATES,
  NO_VIEW,
  PANEL_WIDTHS,
} from '../../utils/documentListHelper';
import Spinner from './SpinnerOverlay';
import { BlankPage } from '../BlankPage';
import SelectionAttributes from './SelectionAttributes';
import Filters from '../filters/Filters';
import FiltersStatic from '../filters/FiltersStatic';
import Table from '../../containers/Table';
import QuickActions from './QuickActions';
import GeoMap from '../maps/GeoMap';
import {
  INVOICE_TO_ALLOCATE_WINDOW_ID,
  InvoiceToAllocateViewHeader,
} from '../paymentAllocation/InvoiceToAllocateViewHeader';
import {
  PP_ORDER_CANDIDATE_WINDOW_ID,
  PPOrderCandidateViewHeader,
} from '../ppOrderCandidate/PPOrderCandidateViewHeader';
import { connect } from 'react-redux';
import {
  getSettingFromStateAsBoolean,
  getSettingFromStateAsPositiveInt,
} from '../../utils/settings';
import {
  DeliveryPlanningViewHeader,
  getDeliveryPlanningViewHeaderWindowId,
} from '../deliveryPlanning/DeliveryPlanningViewHeader';
import {
  OIViewHeader,
  OIViewHeader_WINDOW_ID,
} from '../acctOpenItems/OIViewHeader';
import { DocumentListHeaderProperties } from './DocumentListHeaderProperties';
import {
  AcctSimulationViewHeader,
  AcctSimulationViewHeader_WINDOW_ID,
} from '../acctSimulation/AcctSimulationViewHeader';

/**
 * @file Class based component.
 * @module DocumentList
 * @extends Component
 */
class DocumentList extends Component {
  constructor(props) {
    super(props);

    this.state = {
      clickOutsideLock: false,
      toggleWidth: 0,
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

  setTableRef = (ref) => {
    this.table = ref;
  };

  render() {
    const {
      windowId,
      viewProfileId,
      open,
      closeOverlays,
      parentDefaultViewId,
      inBackground,
      // TODO: Check if we need two separate flags
      isModal,
      inModal,
      processStatus,
      readonly,
      includedView,
      isIncluded,
      disablePaginationShortcuts,
      autofocus,
      modal,
      parentWindowType,
      viewData,
      layout,
      layoutNotFound,
      page,
      lastPage,
      pageLength,
      panelsState,
      onFetchLayoutAndData,
      onChangePage,
      onRedirectToDocument,
      filtersActive,
      mapConfig,
      triggerSpinner,
      viewId,
      onFilterChange,
      hasIncluded,
      onRedirectToNewDocument,
      onSortData,
      onShowSelectedIncludedView,
      table,
      childSelected,
      parentSelected,
      filterId,
      featureType,
      isPPOrderCandidateViewHeaderEnabled,
      deliveryPlanningViewHeaderWindowId,
      defaultQtyPrecision,
    } = this.props;
    const {
      staticFilters,
      orderBy,
      queryLimitHit,
      isShowIncluded,
      hasShowIncluded,
      locationData,
      pending,
      layoutPending,
      headerProperties,
    } = viewData;
    const { clickOutsideLock, toggleWidth } = this.state;
    const selected = table.selected;
    const modalType = modal ? modal.modalType : null;
    const stopShortcutPropagation =
      (isIncluded && !!selected) || (inModal && modalType === PROCESS_NAME);

    const styleObject = {};
    if (toggleWidth !== 0) {
      styleObject.flex = PANEL_WIDTHS[toggleWidth];
    }

    const blurWhenOpen =
      layout && layout.includedView && layout.includedView.blurWhenOpen;

    if (layoutNotFound || viewData.notFound) {
      return (
        <BlankPage what={counterpart.translate('view.error.windowName')} />
      );
    }

    // TODO: Are there valid cases when we don't want to show those ?
    const showQuickActions = true;
    const showModalResizeBtn =
      layout && isModal && hasIncluded && hasShowIncluded;
    const showGeoResizeBtn =
      layout && layout.supportGeoLocations && locationData;
    const isRenderHeaderProperties = !isModal;

    return (
      <div
        className={cx('document-list-wrapper', {
          'document-list-included': isShowIncluded || isIncluded,
          'document-list-is-included': isIncluded,
          'document-list-has-included': hasShowIncluded || hasIncluded,
        })}
        style={styleObject}
      >
        {isRenderHeaderProperties && (
          <DocumentListHeaderProperties headerProperties={headerProperties} />
        )}

        {isPPOrderCandidateViewHeaderEnabled &&
          String(windowId) === PP_ORDER_CANDIDATE_WINDOW_ID &&
          viewId && (
            <PPOrderCandidateViewHeader
              windowId={windowId}
              viewId={viewId}
              selectedRowIds={selected}
              pageLength={pageLength}
            />
          )}

        {deliveryPlanningViewHeaderWindowId &&
          String(windowId) === deliveryPlanningViewHeaderWindowId &&
          viewId && (
            <DeliveryPlanningViewHeader
              windowId={windowId}
              viewId={viewId}
              selectedRowIds={selected}
              pageLength={pageLength}
              precision={defaultQtyPrecision}
            />
          )}

        {String(windowId) === OIViewHeader_WINDOW_ID && viewId && (
          <OIViewHeader headerProperties={headerProperties} />
        )}
        {String(windowId) === AcctSimulationViewHeader_WINDOW_ID && viewId && (
          <AcctSimulationViewHeader headerProperties={headerProperties} />
        )}

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

              {windowId === INVOICE_TO_ALLOCATE_WINDOW_ID && (
                <InvoiceToAllocateViewHeader
                  windowId={windowId}
                  viewId={viewId}
                  selectedRowIds={selected}
                  pageLength={pageLength}
                />
              )}

              {layout.filters && (
                <Filters
                  {...{
                    viewId,
                    filtersActive,
                  }}
                  windowType={windowId}
                  updateDocList={onFilterChange}
                />
              )}
              {staticFilters && (
                <FiltersStatic
                  {...{
                    filterId,
                    windowId,
                    viewId,
                  }}
                  data={staticFilters}
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

            {showQuickActions && (
              <QuickActions
                className="header-element align-items-center"
                processStatus={processStatus}
                selected={selected}
                viewId={viewId}
                windowId={windowId}
                viewProfileId={viewProfileId}
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
                        windowType: includedView.windowId,
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
              />
            )}
          </div>
        )}

        {layout && (
          <div className="document-list-body">
            {triggerSpinner && !inBackground && <Spinner iconSize={50} />}
            <div className="row table-context">
              <Table
                entity="documentView"
                ref={this.setTableRef}
                readonly={true}
                supportOpenRecord={layout.supportOpenRecord}
                onDoubleClick={onRedirectToDocument}
                handleChangePage={onChangePage}
                mainTable={true}
                updateDocList={onFetchLayoutAndData}
                onSortTable={onSortData}
                tabIndex={0}
                disableOnClickOutside={clickOutsideLock}
                limitOnClickOutside={isModal}
                queryLimitHit={queryLimitHit}
                showSelectedIncludedView={onShowSelectedIncludedView}
                blurOnIncludedView={blurWhenOpen}
                focusOnFieldName={layout.focusOnFieldName}
                toggleState={panelsState}
                spinnerVisible={triggerSpinner}
                featureType={featureType}
                parentView={
                  isIncluded
                    ? {
                        viewId: parentDefaultViewId,
                        windowId: parentWindowType,
                      }
                    : null
                }
                {...{
                  orderBy,
                  pageLength,
                  isIncluded,
                  autofocus,
                  open,
                  page,
                  lastPage,
                  closeOverlays,
                  inBackground,
                  disablePaginationShortcuts,
                  isModal,
                  hasIncluded,
                  viewId,
                  windowId,
                  pending,
                  layoutPending,
                }}
              >
                {layout.supportAttributes && !isIncluded && !hasIncluded && (
                  <SelectionAttributes
                    entity="documentView"
                    supportAttribute={table.supportAttribute}
                    setClickOutsideLock={this.setClickOutsideLock}
                    {...{ viewId, selected, inBackground, windowId }}
                  />
                )}
              </Table>
              <GeoMap
                toggleState={panelsState}
                mapConfig={mapConfig}
                data={locationData}
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
  isShowIncluded: PropTypes.bool,
  hasShowIncluded: PropTypes.bool,
  triggerSpinner: PropTypes.bool,
  onToggleState: PropTypes.func,
  onGetSelected: PropTypes.func,
  onSortData: PropTypes.func,
  onFetchLayoutAndData: PropTypes.func,
  onChangePage: PropTypes.func,
  onFilterChange: PropTypes.func,
  onRedirectToDocument: PropTypes.func,
  onRedirectToNewDocument: PropTypes.func,
  onUpdateQuickActions: PropTypes.func,
  setQuickActionsComponentRef: PropTypes.func,
};

const mapStateToProps = (state) => {
  return {
    isPPOrderCandidateViewHeaderEnabled: getSettingFromStateAsBoolean(
      state,
      'PPOrderCandidateViewHeader.enabled',
      true
    ),
    defaultQtyPrecision: getSettingFromStateAsPositiveInt(
      state,
      'widget.Quantity.defaultPrecision',
      2
    ),
    deliveryPlanningViewHeaderWindowId:
      getDeliveryPlanningViewHeaderWindowId(state),
  };
};

export default connect(mapStateToProps, null)(DocumentList);
