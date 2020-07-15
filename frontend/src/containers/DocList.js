import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';

import { updateUri } from '../actions/AppActions';
import { getWindowBreadcrumb } from '../actions/MenuActions';
import Container from '../components/Container';
import DocumentList from './DocumentList';
import Overlay from '../components/app/Overlay';

const EMPTY_ARRAY = [];
const EMPTY_OBJECT = {};

/**
 * @file Class based component.
 * @module DocList
 * @extends Component
 */
class DocList extends PureComponent {
  state = {};

  /**
   * getDerivedStateFromProps lifecycle - hold in the state of the component the last page
   * @param {obj} props
   * @param {obj} state
   */
  static getDerivedStateFromProps(props, state) {
    return props.query && props.query.page !== state.lastPage
      ? { lastPage: props.query.page }
      : null;
  }

  componentDidMount = () => {
    const { windowId, getWindowBreadcrumb } = this.props;

    getWindowBreadcrumb(windowId);
  };

  componentDidUpdate = (prevProps) => {
    const { windowId, getWindowBreadcrumb } = this.props;

    if (prevProps.windowId !== windowId) {
      getWindowBreadcrumb(windowId);
    }
  };

  /**
   * @method updateUriCallback
   * @summary Update the url with query params if needed (ie add viewId, page etc)
   */
  updateUriCallback = (prop, value) => {
    const { updateUri, query, pathname } = this.props;

    updateUri(pathname, query, prop, value);
  };

  /**
   * @method handleUpdateParentSelectedIds
   * @summary ToDo: Describe the method.
   */
  handleUpdateParentSelectedIds = (childSelection) => {
    this.masterDocumentList.updateQuickActions(childSelection);
  };

  /**
   * @method handleDocListRef
   * @summary Store ref to the main DocumentList
   */
  handleDocListRef = (ref) => {
    this.masterDocumentList = ref;
  };

  render() {
    const {
      windowId,
      query,
      modal,
      rawModal,
      overlay,
      processStatus,
      includedView,
    } = this.props;
    let refRowIds = EMPTY_ARRAY;
    const queryCopy = query ? query : EMPTY_OBJECT;

    if (queryCopy.refRowIds) {
      try {
        refRowIds = JSON.parse(queryCopy.refRowIds);
      } catch (e) {
        refRowIds = null;
      }
    }
    const viewId = queryCopy.viewId ? queryCopy.viewId : null;

    return (
      <Container
        entity="documentView"
        modal={modal}
        rawModal={rawModal}
        windowId={windowId}
        viewId={viewId}
        processStatus={processStatus}
        includedView={includedView}
        modalHidden={!modal.visible && !rawModal.visible}
        masterDocumentList={this.masterDocumentList}
      >
        <Overlay data={overlay.data} showOverlay={overlay.visible} />

        <div
          className={classnames('document-lists-wrapper', {
            'modal-overlay': rawModal.visible,
          })}
        >
          <DocumentList
            ref={this.handleDocListRef}
            type="grid"
            updateUri={this.updateUriCallback}
            windowId={windowId}
            refRowIds={refRowIds}
            includedView={includedView}
            inBackground={rawModal.visible}
            inModal={modal.visible}
            fetchQuickActionsOnInit
            processStatus={processStatus}
            disablePaginationShortcuts={modal.visible || rawModal.visible}
            sort={queryCopy.sort}
            page={queryCopy.page}
            lastPage={this.state.lastPage}
            viewId={queryCopy.viewId}
            referenceId={queryCopy.referenceId}
            refType={queryCopy.refType}
            refDocumentId={queryCopy.refDocumentId}
            refTabId={queryCopy.refTabId}
          />

          {includedView &&
            includedView.viewId &&
            !rawModal.visible &&
            !modal.visible && (
              <DocumentList
                type="includedView"
                windowId={includedView.windowType}
                defaultViewId={includedView.viewId}
                parentWindowType={windowId}
                parentDefaultViewId={viewId}
                updateParentSelectedIds={this.handleUpdateParentSelectedIds}
                viewProfileId={includedView.viewProfileId}
                fetchQuickActionsOnInit
                processStatus={processStatus}
                isIncluded
                inBackground={false}
                inModal={false}
                sort={queryCopy.sort}
                page={queryCopy.page}
                lastPage={this.state.lastPage}
                viewId={queryCopy.viewId}
                referenceId={queryCopy.referenceId}
                refType={queryCopy.refType}
                refDocumentId={queryCopy.refDocumentId}
                refTabId={queryCopy.refTabId}
              />
            )}
        </div>
      </Container>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {array} breadcrumb
 * @prop {object} includedView
 * @prop {string} indicator
 * @prop {object} modal
 * @prop {object} overlay
 * @prop {string} pathname
 * @prop {object} pluginModal
 * @prop {string} processStatus
 * @prop {object} query - routing query
 * @prop {object} rawModal
 * @prop {string} windowId
 */
DocList.propTypes = {
  includedView: PropTypes.object,
  modal: PropTypes.object.isRequired,
  overlay: PropTypes.object,
  processStatus: PropTypes.string.isRequired,
  query: PropTypes.object.isRequired,
  pathname: PropTypes.string.isRequired,
  rawModal: PropTypes.object.isRequired,
  windowId: PropTypes.string,
  getWindowBreadcrumb: PropTypes.func.isRequired,
  updateUri: PropTypes.func.isRequired,
};

/**
 * @method mapStateToProps
 * @summary ToDo: Describe the method.
 * @param {object} state
 */
const mapStateToProps = (state) => {
  return {
    modal: state.windowHandler.modal,
    rawModal: state.windowHandler.rawModal,
    overlay: state.windowHandler.overlay,
    includedView: state.listHandler.includedView.windowType
      ? state.listHandler.includedView
      : null,
    processStatus: state.appHandler.processStatus,
    pathname: state.routing.locationBeforeTransitions.pathname,
  };
};

export default connect(
  mapStateToProps,
  {
    getWindowBreadcrumb,
    updateUri,
  }
)(DocList);
