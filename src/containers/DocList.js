import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';

import { updateUri } from '../actions/AppActions';
import { getWindowBreadcrumb } from '../actions/MenuActions';
import {
  selectTableItems,
  setLatestNewDocument,
} from '../actions/WindowActions';
import Container from '../components/Container';
import DocumentList from '../components/app/DocumentList';

const mapStateToProps = state => ({
  modal: state.windowHandler.modal,
  rawModal: state.windowHandler.rawModal,
  latestNewDocument: state.windowHandler.latestNewDocument,
  indicator: state.windowHandler.indicator,
  includedView: state.listHandler.includedView,
  processStatus: state.appHandler.processStatus,
  breadcrumb: state.menuHandler.breadcrumb,
  pathname: state.routing.locationBeforeTransitions.pathname,
});

class DocList extends Component {
  state = {
    modalTitle: '',
    modalDescription: '',
    notfound: false,
  };

  static propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired,
    query: PropTypes.object.isRequired,
    includedView: PropTypes.object.isRequired,
    pathname: PropTypes.string.isRequired,
    modal: PropTypes.object.isRequired,
    rawModal: PropTypes.object.isRequired,
    indicator: PropTypes.string.isRequired,
    processStatus: PropTypes.string.isRequired,
  };

  componentDidMount = () => {
    const { dispatch, windowType, latestNewDocument, query } = this.props;

    dispatch(getWindowBreadcrumb(windowType));

    if (latestNewDocument) {
      dispatch(
        selectTableItems({
          windowType,
          viewId: query.viewId,
          ids: [latestNewDocument],
        })
      );
      dispatch(setLatestNewDocument(null));
    }
  };

  componentDidUpdate = prevProps => {
    const { dispatch, windowType } = this.props;

    if (prevProps.windowType !== windowType) {
      dispatch(getWindowBreadcrumb(windowType));
    }
  };

  updateUriCallback = (prop, value) => {
    const { dispatch, query, pathname } = this.props;

    dispatch(updateUri(pathname, query, prop, value));
  };

  setModalTitle = title => {
    this.setState({ modalTitle: title });
  };

  setModalDescription = desc => {
    this.setState({ modalDescription: desc });
  };

  setNotFound = isNotFound => {
    this.setState({ notfound: isNotFound });
  };

  handleUpdateParentSelectedIds = childSelection => {
    this.masterDocumentList.updateQuickActions(childSelection);
  };

  render() {
    const {
      windowType,
      breadcrumb,
      query,
      modal,
      rawModal,
      indicator,
      processStatus,
      includedView,
    } = this.props;
    const { modalTitle, notfound, modalDescription } = this.state;
    let refRowIds = [];

    if (query && query.refRowIds) {
      try {
        refRowIds = JSON.parse(query.refRowIds);
      } catch (e) {
        refRowIds = [];
      }
    }

    return (
      <Container
        entity="documentView"
        modal={modal}
        rawModal={rawModal}
        breadcrumb={breadcrumb}
        windowType={windowType}
        query={query}
        notfound={notfound}
        indicator={indicator}
        modalTitle={modalTitle}
        processStatus={processStatus}
        includedView={includedView}
        setModalTitle={this.setModalTitle}
        setModalDescription={this.setModalDescription}
        modalDescription={modalDescription}
        showIndicator={!modal.visible && !rawModal.visible}
        masterDocumentList={this.masterDocumentList}
      >
        <div
          className={classnames('document-lists-wrapper', {
            'modal-overlay': rawModal.visible,
          })}
        >
          <DocumentList
            ref={element => {
              this.masterDocumentList = element
                ? element.getWrappedInstance()
                : null;
            }}
            type="grid"
            updateUri={this.updateUriCallback}
            windowType={windowType}
            defaultViewId={query.viewId}
            defaultSort={query.sort}
            defaultPage={parseInt(query.page)}
            refType={query.refType}
            refId={query.refId}
            refTabId={query.refTabId}
            refRowIds={refRowIds}
            includedView={includedView}
            inBackground={rawModal.visible}
            inModal={modal.visible}
            fetchQuickActionsOnInit
            processStatus={processStatus}
            disablePaginationShortcuts={modal.visible || rawModal.visible}
            setNotFound={this.setNotFound}
            notfound={notfound}
          />

          {includedView &&
            includedView.windowType &&
            includedView.viewId &&
            !rawModal.visible &&
            !modal.visible && (
              <DocumentList
                type="includedView"
                windowType={includedView.windowType}
                defaultViewId={includedView.viewId}
                parentWindowType={windowType}
                parentDefaultViewId={query.viewId}
                updateParentSelectedIds={this.handleUpdateParentSelectedIds}
                viewProfileId={includedView.viewProfileId}
                fetchQuickActionsOnInit
                processStatus={processStatus}
                isIncluded
                inBackground={false}
                inModal={false}
              />
            )}
        </div>
      </Container>
    );
  }
}

export default connect(mapStateToProps)(DocList);
