import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';

import { startProcess } from '../../api/process';
import { processNewRecord } from '../../actions/GenericActions';
import { updateCommentsPanelOpenFlag } from '../../actions/CommentsPanelActions';
import {
  callAPI,
  closeModal,
  createWindow,
  fetchChangeLog,
  fireUpdateData,
  patch,
  printDocument,
  resetPrintingOptions,
} from '../../actions/WindowActions';

import { getSelection, getTableId } from '../../reducers/tables';
import { findViewByViewId } from '../../reducers/viewHandler';

import keymap from '../../shortcuts/keymap';
import ChangeLogModal from '../ChangeLogModal';
import Process from '../Process';
import SectionGroup from '../SectionGroup';
import ModalContextShortcuts from '../keyshortcuts/ModalContextShortcuts';
import Tooltips from '../tooltips/Tooltips.js';
import Indicator from './Indicator';
import OverlayField from './OverlayField';
import CommentsPanel from '../comments/CommentsPanel';
import PrintingOptions from './PrintingOptions';

import SockJs from 'sockjs-client';
import Stomp from 'stompjs/lib/stomp.min.js';
import {
  createProcess,
  handleProcessResponse,
} from '../../actions/ProcessActions';
import ChangeCurrentWorkplace, {
  STATIC_MODAL_TYPE_ChangeCurrentWorkplace,
} from './ChangeCurrentWorkplace';

/**
 * @file Modal is an overlay view that can be opened over the main view.
 * @module Modal
 * @extends Component
 */
class Modal extends Component {
  mounted = false;

  constructor(props) {
    super(props);

    const { rowId, dataId } = props;

    this.state = {
      scrolled: false,
      isNew: rowId === 'NEW',
      isNewDoc: dataId === 'NEW',
      init: false,
      pending: false,
      waitingFetch: false,
      isTooltipShow: false,
    };

    // we do not use global WS connector as we don't want to mess with the logic around it and have undesired side effects
    // WS connectivity for `Modal` has to reside in this class as it is easier to reason about and follow
    // note that we do not initialize here the connection as the websocket is not present in the props now
    // example side effect that was fixed with a hotfix: https://github.com/metasfresh/metasfresh/commit/f680d4c7ee28e924d98bf8581081e426836c33ce
    this.modalWsClient = null;
  }

  componentDidMount() {
    this.mounted = true;

    this.init();

    // Dirty solution, but use only if you need to
    // there is no way to affect body
    // because body is out of react app range
    // and css dont affect parents
    // but we have to change scope of scrollbar
    if (!this.mounted) {
      return;
    }

    document.body.style.overflow = 'hidden';

    this.initEventListeners();
  }

  componentWillUnmount() {
    this.mounted = false;

    this.removeEventListeners();

    // disconnect when the component is unmounted
    this.modalWsClient &&
      this.modalWsClient.connected &&
      this.modalWsClient.disconnect();
  }

  /**
   * @method initModalWsConnection
   * @summary - connect and subscribes to the subscription `websocket` topic (websocket endpoint)
   * @param {string} websocket - subscription topic
   */
  initModalWsConnection = (websocket) => {
    if (this.modalWsClient === null && websocket) {
      this.modalWsClient = Stomp.Stomp.over(new SockJs(config.WS_URL));
      this.modalWsClient.debug = null;
      this.modalWsClient.connect({}, () => {
        this.modalWsClient.connected &&
          this.modalWsClient.subscribe(websocket, (msgFromWs) => {
            this.onWebsocketMessage(msgFromWs);
          });
      });
    }
  };

  /**
   * @method onWebsocketMessage
   * @summary - logic executed when a messages is received via WS for the modal subscription
   * @param {string} websocketMsg
   */
  onWebsocketMessage = (websocketMsg) => {
    const msgBody = JSON.parse(websocketMsg.body);
    const { stale } = msgBody;
    if (stale) {
      const { dispatch, windowId, docId, tabId, rowId, isAdvanced } =
        this.props;

      dispatch(
        fireUpdateData({
          windowId,
          documentId: docId,
          tabId,
          rowId,
          isModal: true,
          fetchAdvancedFields: isAdvanced,
        })
      );
    }
  };

  componentDidUpdate(prevProps) {
    const { windowId, viewId, indicator, websocket } = this.props;

    // initializes the WS connection for the modal if there isn't already an existing one
    websocket && this.initModalWsConnection(websocket);

    const { waitingFetch } = this.state;

    if (prevProps.windowId !== windowId || prevProps.viewId !== viewId) {
      this.init();
    }

    // Case when we have to trigger pending start request
    // in due to some pending patches that are required.
    if (waitingFetch && prevProps.indicator !== indicator) {
      this.setState(
        {
          waitingFetch: false,
        },
        () => {
          this.handleStart();
        }
      );
    }
  }

  /**
   * @method toggleTooltip
   * @summary ToDo: Describe the method.
   * @param {*} key
   */
  toggleTooltip = (key = null) => {
    this.setState({ isTooltipShow: key });
  };

  /**
   * @method initEventListeners
   * @summary ToDo: Describe the method.
   */
  initEventListeners = () => {
    const modalContent = document.querySelector('.js-panel-modal-content');

    if (modalContent) {
      modalContent.addEventListener('scroll', this.handleScroll);
    }
  };

  /**
   * @method removeEventListeners
   * @summary ToDo: Describe the method.
   */
  removeEventListeners = () => {
    const modalContent = document.querySelector('.js-panel-modal-content');

    if (modalContent) {
      modalContent.removeEventListener('scroll', this.handleScroll);
    }
  };

  /**
   * @async
   * @method init
   * @summary ToDo: Describe the method.
   */
  init = async () => {
    const {
      dispatch,
      windowId,
      dataId,
      tabId,
      rowId,
      modalType,
      documentType,
      staticModalType,
      parentSelection,
      isAdvanced,
      viewId,
      modalViewDocumentIds,
      activeTabId,
      childViewId,
      childViewSelectedIds,
      parentViewId,
      viewDocumentIds,
      title,
    } = this.props;

    let request = null;

    switch (modalType) {
      case 'static':
        {
          if (staticModalType === 'about') {
            request = dispatch(fetchChangeLog(windowId, dataId, tabId, rowId));
          }
          if (staticModalType === 'comments') {
            request = dispatch(
              callAPI({
                windowId,
                docId: dataId,
                tabId,
                rowId,
                target: staticModalType,
                verb: 'GET',
              })
            );
          }

          try {
            await request;
          } catch (error) {
            this.handleClose();

            throw error;
          }
        }
        break;

      case 'window':
        try {
          await dispatch(
            createWindow({
              windowId,
              docId: dataId,
              tabId,
              rowId,
              isModal: true,
              isAdvanced,
              title,
            })
          );
        } catch (error) {
          this.handleClose();

          throw error;
        }
        break;

      case 'process':
        // We have 3 cases of processes (prioritized):
        // - with viewDocumentIds: on single page with rawModal
        // - with dataId: on single document page
        // - with parentSelection: on parent gridviews

        try {
          const options = {
            processType: windowId,
            viewId,
            documentType,
            ids: viewId
              ? modalViewDocumentIds
              : dataId
              ? [dataId]
              : parentSelection,
            tabId,
            rowId:
              rowId || (parentSelection.length ? parentSelection[0] : null),
          };

          if (activeTabId) {
            options.selectedTab = {
              tabId: activeTabId,
              rowIds: viewDocumentIds,
            };
          }

          // TODO: Is this ever used on the backend ?
          if (childViewId) {
            options.childViewId = childViewId;
            options.childViewSelectedIds = childViewSelectedIds;
          }

          if (parentViewId && parentSelection.length) {
            options.parentViewId = parentViewId;
            options.parentViewSelectedIds = parentSelection;
          }

          await dispatch(createProcess(options));
        } catch (error) {
          this.handleClose();

          if (error.toString() !== 'Error: close_modal') {
            throw error;
          }
        }

        break;
    }
  };

  /**
   * @method closeModal
   * @summary ToDo: Describe the method.
   */
  closeModal = (saveStatus) => {
    // TODO: parentDataId (formerly relativeDataId) is not passed in as prop
    const {
      dispatch,
      closeCallback,
      dataId,
      windowId,
      parentDataId,
      triggerField,
      rowId,
      tabId,
      documentType,
    } = this.props;
    const { isNew, isNewDoc } = this.state;

    if (isNewDoc) {
      processNewRecord('window', windowId, dataId).then((response) => {
        dispatch(
          patch(
            'window',
            documentType,
            parentDataId,
            null,
            null,
            triggerField,
            response.data // it's OK to patch using the newly created record ID (instead of key/caption value)
          )
        ).then(() => {
          this.removeModal();
        });
      });
    } else {
      if (closeCallback) {
        closeCallback({
          isNew,
          windowType: windowId,
          documentId: dataId,
          tabId,
          rowId,
          saveStatus,
        });
      }

      this.removeModal();
    }
  };

  /**
   * @method removeModal
   * @summary ToDo: Describe the method
   */
  removeModal = () => {
    const { dispatch, rawModalVisible } = this.props;

    dispatch(closeModal());
    // make sure that on closing the modal for comments `isOpen `flag is closed
    // if you don't do this you will have COLLAPSE_INDENT issue  (see: keymap.js)
    dispatch(updateCommentsPanelOpenFlag(false));
    if (!rawModalVisible) {
      document.body.style.overflow = 'auto';
    }
  };

  /**
   * @method handleClose
   * @summary Handle closing modal when the `done` button is clicked or `{esc}` key pressed
   */
  handleClose = () => {
    const { modalSaveStatus, modalType } = this.props;

    if (modalType === 'process') {
      return this.closeModal(modalSaveStatus);
    }

    if (modalSaveStatus || window.confirm('Do you really want to leave?')) {
      this.closeModal(modalSaveStatus);
    }
  };

  /**
   * @method handleScroll
   * @summary ToDo: Describe the method
   * @param {object} event
   */
  handleScroll = (event) => {
    this.setState((prevState) => {
      const scrolled = event.target.scrollTop > 0;

      // return nothing if state did not change
      if (scrolled !== prevState.scrolled) {
        return { scrolled };
      }
    });
  };

  /**
   * @method handleStart
   * @summary Handler for starting process from a modal
   */
  handleStart = () => {
    const { dispatch, layout, windowId, indicator, parentId } = this.props;

    if (indicator === 'pending') {
      this.setState({ waitingFetch: true, pending: true });
      return;
    }

    this.setState(
      {
        pending: true,
      },
      async () => {
        let response;

        try {
          response = await startProcess(windowId, layout.pinstanceId);

          const action = handleProcessResponse({
            response,
            processId: windowId,
            pinstanceId: layout.pinstanceId,
            parentId,
          });

          await dispatch(action);

          this.removeModal();
        } catch (error) {
          // eslint-disable-next-line no-console
          console.error('Modal.handleStart error: ', error);
        } finally {
          if (this.mounted) {
            // prevent a memory leak
            this.setState({
              pending: false,
            });
          }
        }
      }
    );
  };

  /**
   * @method handlePrinting
   * @summary before printing we check the available parameters from the store and we use those for forming the final printing URI
   */
  handlePrinting = () => {
    const {
      windowId,
      modalViewDocumentIds,
      dataId,
      printingOptions,
      dispatch,
    } = this.props;
    const documentId = dataId;
    const documentNo = modalViewDocumentIds[0] ?? documentId;

    const options = printingOptions.options.reduce((acc, item) => {
      acc[item.internalName] = item.value;
      return acc;
    }, {});

    printDocument({
      windowId,
      documentId,
      documentNo,
      options,
    });

    this.closeModal(true);
    dispatch(resetPrintingOptions());

    return true; // stopPropagation to avoid calling the global alt-P handler
  };

  /**
   * @method renderModalBody
   * @summary ToDo: Describe the method
   */
  renderModalBody = () => {
    const {
      data,
      layout,
      tabId,
      rowId,
      dataId,
      modalType,
      windowId,
      isAdvanced,
      staticModalType,
    } = this.props;
    const { pending } = this.state;

    switch (modalType) {
      case 'static': {
        let content = null;
        if (staticModalType === 'about') {
          content = <ChangeLogModal data={data} />;
        } else if (staticModalType === 'comments') {
          content = <CommentsPanel windowId={windowId} docId={dataId} />;
        } else if (staticModalType === 'printing') {
          content = <PrintingOptions windowId={windowId} docId={dataId} />;
        } else if (
          staticModalType === STATIC_MODAL_TYPE_ChangeCurrentWorkplace
        ) {
          content = <ChangeCurrentWorkplace />;
        }
        return (
          <div className="window-wrapper">
            <div className="document-file-dropzone">
              <div className="sections-wrapper">
                <div className="row">{content}</div>
              </div>
            </div>
          </div>
        );
      }
      case 'window':
        return (
          <SectionGroup
            data={data}
            dataId={dataId}
            layout={layout}
            modal
            tabId={tabId}
            rowId={rowId}
            isModal
            isAdvanced={isAdvanced}
            tabsInfo={null}
          />
        );
      case 'process':
        return (
          <Process
            data={data}
            layout={layout}
            type={windowId}
            disabled={pending}
          />
        );
    }
  };

  /**
   * @method renderPanel
   * @summary ToDo: Describe the method
   */
  renderPanel = () => {
    const {
      modalTitle,
      modalType,
      layout,
      staticModalType,
      printingOptions,
      //
      indicator,
      isDocumentNotSaved,
      saveStatus,
    } = this.props;

    const { okButtonCaption: printBtnCaption } = printingOptions;
    const { scrolled, pending, isNewDoc, isTooltipShow } = this.state;

    let applyHandler =
      modalType === 'process' ? this.handleStart : this.handleClose;
    if (staticModalType === 'printing') applyHandler = this.handlePrinting;
    const cancelHandler = isNewDoc ? this.removeModal : this.handleClose;

    return (
      <div className="modal-content-wrapper">
        <div className="panel panel-modal panel-modal-primary">
          <div
            className={classnames('panel-groups-header', 'panel-modal-header', {
              'header-shadow': scrolled,
            })}
          >
            <span className="panel-modal-header-title">
              {modalTitle ? modalTitle : layout.caption}
            </span>

            <div className="items-row-2">
              {isNewDoc && (
                <button
                  className={classnames(
                    'btn btn-meta-outline-secondary btn-distance-3 btn-md',
                    {
                      'tag-disabled disabled ': pending,
                    }
                  )}
                  onClick={this.removeModal}
                  tabIndex={0}
                  onMouseEnter={() => this.toggleTooltip(keymap.CANCEL)}
                  onMouseLeave={this.toggleTooltip}
                >
                  {counterpart.translate('modal.actions.cancel')}

                  {isTooltipShow === keymap.CANCEL && (
                    <Tooltips
                      name={keymap.CANCEL}
                      action={counterpart.translate('modal.actions.cancel')}
                      type=""
                    />
                  )}
                </button>
              )}

              <button
                className={classnames(
                  'btn btn-meta-outline-secondary btn-distance-3 btn-md',
                  {
                    'tag-disabled disabled ': pending,
                  }
                )}
                onClick={this.handleClose}
                tabIndex={0}
                onMouseEnter={() =>
                  this.toggleTooltip(
                    modalType === 'process' ? keymap.CANCEL : keymap.DONE
                  )
                }
                onMouseLeave={this.toggleTooltip}
              >
                {modalType === 'process' || staticModalType === 'printing'
                  ? counterpart.translate('modal.actions.cancel')
                  : counterpart.translate('modal.actions.done')}

                {isTooltipShow ===
                  (modalType === 'process' ? keymap.CANCEL : keymap.DONE) && (
                  <Tooltips
                    name={modalType === 'process' ? keymap.CANCEL : keymap.DONE}
                    action={
                      modalType === 'process'
                        ? counterpart.translate('modal.actions.cancel')
                        : counterpart.translate('modal.actions.done')
                    }
                    type=""
                  />
                )}
              </button>

              {modalType === 'process' && (
                <button
                  className={classnames(
                    'btn btn-meta-outline-secondary btn-distance-3 btn-md',
                    {
                      'tag-disabled disabled ': pending,
                    }
                  )}
                  onClick={this.handleStart}
                  tabIndex={0}
                  onMouseEnter={() => this.toggleTooltip(keymap.DONE)}
                  onMouseLeave={this.toggleTooltip}
                  disabled={indicator === 'error'}
                >
                  {counterpart.translate('modal.actions.start')}

                  {isTooltipShow === keymap.DONE && (
                    <Tooltips
                      name={keymap.DONE}
                      action={counterpart.translate('modal.actions.start')}
                      type=""
                    />
                  )}
                </button>
              )}

              {/* Printing button caption value comes form the store */}
              {staticModalType === 'printing' && printBtnCaption && (
                <button
                  className={classnames(
                    'btn btn-meta-outline-secondary btn-distance-3 btn-md',
                    {
                      'tag-disabled disabled ': pending,
                    }
                  )}
                  onClick={this.handlePrinting}
                  tabIndex={0}
                >
                  {printBtnCaption}
                </button>
              )}
            </div>
          </div>

          <Indicator
            indicator={indicator}
            isDocumentNotSaved={
              staticModalType === 'printing' ||
              staticModalType === STATIC_MODAL_TYPE_ChangeCurrentWorkplace
                ? false
                : isDocumentNotSaved
            }
            error={saveStatus?.error ? saveStatus?.reason : ''}
            exception={saveStatus?.error ? saveStatus?.exception : null}
          />

          <div
            className="panel-modal-content js-panel-modal-content
                          container-fluid"
            ref={(c) => {
              if (c) {
                c.focus();
              }
            }}
          >
            {layout.description && (
              <div className="modal-top-description">{layout.description}</div>
            )}
            {this.renderModalBody()}
          </div>
          {layout.layoutType !== 'singleOverlayField' && (
            <ModalContextShortcuts
              done={applyHandler}
              cancel={cancelHandler}
              isBindPrintActionAsDone={staticModalType === 'printing'}
            />
          )}
        </div>
      </div>
    );
  };

  /**
   * @method renderOverlay
   * @summary ToDo: Describe the method
   */
  renderOverlay = () => {
    const { data, layout, windowId, modalType, isNewDoc } = this.props;
    const { pending } = this.state;

    const applyHandler =
      modalType === 'process' ? this.handleStart : this.handleClose;
    const cancelHandler =
      modalType === 'process'
        ? this.handleClose
        : isNewDoc
        ? this.removeModal
        : undefined;

    function defer() {
      let res, rej;

      const promise = new Promise((resolve, reject) => {
        res = resolve;
        rej = reject;
      });

      promise.resolve = res;
      promise.reject = rej;

      return promise;
    }

    const awaitPromise = defer();

    const overlayCallback = (a, b, c, ret) => {
      ret.then(() => {
        awaitPromise.resolve();
      });
    };

    const applyFn = () => {
      awaitPromise.then(() => {
        applyHandler();
      });
    };

    return (
      <OverlayField
        type={windowId}
        disabled={pending}
        data={data}
        layout={layout}
        handleSubmit={applyFn}
        onChange={overlayCallback}
        closeOverlay={cancelHandler}
      />
    );
  };

  render() {
    const { layout, modalType } = this.props;
    let renderedContent = null;

    if (layout && Object.keys(layout) && Object.keys(layout).length) {
      if (!layout.layoutType || layout.layoutType === 'panel') {
        renderedContent = this.renderPanel();
      } else if (layout.layoutType === 'singleOverlayField') {
        renderedContent = this.renderOverlay();
      }
    } else if (modalType === 'static') {
      renderedContent = this.renderPanel();
    } else {
      return null;
    }

    return (
      <div
        className={classnames('screen-freeze js-not-unselect', {
          light: layout.layoutType === 'singleOverlayField',
        })}
      >
        {renderedContent}
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {*} [activeTabId]
 * @prop {*} [childViewId]
 * @prop {*} [closeCallback]
 * @prop {*} [childViewSelectedIds]
 * @prop {shape} [data]
 * @prop {string} [dataId]
 * @prop {func} dispatch Dispatch function
 * @prop {string} [indicator]
 * @prop {shape} [layout]
 * @prop {bool} [isAdvanced]
 * @prop {bool} [isDocumentNotSaved]
 * @prop {bool} [isNewDoc]
 * @prop {string} [modalType]
 * @prop {*} [modalTitle]
 * @prop {*} [modalSaveStatus]
 * @prop {*} [modalViewDocumentIds]
 * @prop {string} [staticModalType]
 * @prop {string} [tabId]
 * @prop {*} [parentSelection]
 * @prop {*} [parentWindowId]
 * @prop {*} [parentViewId]
 * @prop {*} [rawModalVisible]
 * @prop {string} [rowId]
 * @prop {*} [triggerField]
 * @prop {string} [viewId]
 * @prop {string} [windowId]
 * @prop {string} [documentType]
 */
Modal.propTypes = {
  dispatch: PropTypes.func.isRequired,
  isNewDoc: PropTypes.bool,
  staticModalType: PropTypes.string,
  activeTabId: PropTypes.any,
  childViewId: PropTypes.any,
  closeCallback: PropTypes.any,
  // TODO: Is this ever used on the backend ?
  childViewSelectedIds: PropTypes.any,
  data: PropTypes.oneOfType([PropTypes.shape(), PropTypes.array]), // TODO: type here should point to a hidden issue?
  dataId: PropTypes.string,
  indicator: PropTypes.string,
  layout: PropTypes.shape(),
  isAdvanced: PropTypes.bool,
  isDocumentNotSaved: PropTypes.bool,
  modalTitle: PropTypes.any,
  modalType: PropTypes.any,
  saveStatus: PropTypes.object,
  modalSaveStatus: PropTypes.bool,
  modalViewDocumentIds: PropTypes.any,
  tabId: PropTypes.any,
  parentDataId: PropTypes.any,
  parentSelection: PropTypes.any,
  parentWindowId: PropTypes.any,
  parentViewId: PropTypes.any,
  rawModalVisible: PropTypes.any,
  rowId: PropTypes.string,
  triggerField: PropTypes.any,
  viewId: PropTypes.string,
  windowId: PropTypes.string,
  parentId: PropTypes.string,
  documentType: PropTypes.string,
  viewDocumentIds: PropTypes.array,
  printBtnCaption: PropTypes.string,
  printingOptions: PropTypes.object,
  title: PropTypes.string,
  websocket: PropTypes.string,
  windowsType: PropTypes.string,
  docId: PropTypes.string,
};

const mapStateToProps = (state, props) => {
  const { tabId, dataId, rawModalWindowId, viewId, documentType } = props;

  const parentViewId = state.windowHandler.modal.parentViewId
    ? state.windowHandler.modal.parentViewId
    : props.parentViewId;

  const id = parentViewId ? parentViewId : viewId;
  const parentView = id && findViewByViewId(state, id);
  const parentId = parentView ? parentView.windowId : documentType;

  const parentViewTableId = getTableId({
    windowId: rawModalWindowId,
    viewId: parentViewId,
    tabId,
    docId: dataId,
  });

  const parentSelector = getSelection();

  return {
    parentSelection: parentSelector(state, parentViewTableId),
    activeTabId: state.windowHandler.master.layout.activeTab,
    indicator: state.windowHandler.indicator,
    parentViewId,
    parentId,
    printingOptions: state.windowHandler.printingOptions,
  };
};

export { Modal as DisconnectedModal };

export default connect(mapStateToProps)(Modal);
