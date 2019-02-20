import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';

import { processNewRecord } from '../../actions/GenericActions';
import {
  closeModal,
  createProcess,
  createWindow,
  handleProcessResponse,
  patch,
  startProcess,
} from '../../actions/WindowActions';
import { getSelection } from '../../reducers/windowHandler';
import keymap from '../../shortcuts/keymap';
import Process from '../Process';
import Window from '../Window';
import ModalContextShortcuts from '../keyshortcuts/ModalContextShortcuts';
import Tooltips from '../tooltips/Tooltips.js';
import Indicator from './Indicator';
import OverlayField from './OverlayField';

class Modal extends Component {
  mounted = false;

  static propTypes = {
    dispatch: PropTypes.func.isRequired,
    isNewDoc: PropTypes.bool,
  };

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
  }

  async componentDidMount() {
    this.mounted = true;

    await this.init();

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
  }

  async componentDidUpdate(prevProps) {
    const { windowType, viewId, indicator } = this.props;
    const { waitingFetch } = this.state;

    if (prevProps.windowType !== windowType || prevProps.viewId !== viewId) {
      await this.init();
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

  toggleTooltip = (key = null) => {
    this.setState({ isTooltipShow: key });
  };

  initEventListeners = () => {
    const modalContent = document.querySelector('.js-panel-modal-content');

    if (modalContent) {
      modalContent.addEventListener('scroll', this.handleScroll);
    }
  };

  removeEventListeners = () => {
    const modalContent = document.querySelector('.js-panel-modal-content');

    if (modalContent) {
      modalContent.removeEventListener('scroll', this.handleScroll);
    }
  };

  init = async () => {
    const {
      dispatch,
      windowType,
      dataId,
      tabId,
      rowId,
      modalType,
      parentSelection,
      parentType,
      isAdvanced,
      modalViewId,
      modalViewDocumentIds,
      activeTabId,
      childViewId,
      childViewSelectedIds,
      parentViewId,
      parentViewSelectedIds,
    } = this.props;

    switch (modalType) {
      case 'window':
        try {
          await dispatch(
            createWindow(windowType, dataId, tabId, rowId, true, isAdvanced)
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
            processType: windowType,
            viewId: modalViewId,
            type: parentType,
            ids: modalViewId
              ? modalViewDocumentIds
              : dataId
              ? [dataId]
              : parentSelection,
            tabId,
            rowId,
          };

          if (activeTabId && parentSelection && parentSelection.length) {
            options.selectedTab = {
              tabId: activeTabId,
              rowIds: parentSelection,
            };
          }

          if (childViewId) {
            options.childViewId = childViewId;
            options.childViewSelectedIds = childViewSelectedIds;
          }

          if (parentViewId) {
            options.parentViewId = parentViewId;
            options.parentViewSelectedIds = parentViewSelectedIds;
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

  closeModal = () => {
    // TODO: parentDataId (formerly relativeDataId) is not passed in as prop
    const {
      dispatch,
      closeCallback,
      dataId,
      windowType,
      parentType,
      parentDataId,
      triggerField,
      rowId,
      tabId,
    } = this.props;
    const { isNew, isNewDoc } = this.state;

    if (isNewDoc) {
      processNewRecord('window', windowType, dataId).then(response => {
        dispatch(
          patch(
            'window',
            parentType,
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
          windowType,
          documentId: dataId,
          tabId,
          rowId,
        });
      }

      this.removeModal();
    }
  };

  removeModal = () => {
    const { dispatch, rawModalVisible } = this.props;

    dispatch(closeModal());

    if (!rawModalVisible) {
      document.body.style.overflow = 'auto';
    }
  };

  handleClose = () => {
    const { modalSaveStatus, modalType } = this.props;

    if (modalType === 'process') {
      return this.closeModal();
    }

    if (modalSaveStatus || window.confirm('Do you really want to leave?')) {
      this.closeModal();
    }
  };

  handleScroll = event => {
    this.setState(prevState => {
      const scrolled = event.target.scrollTop > 0;

      // return nothing if state did not change
      if (scrolled !== prevState.scrolled) {
        return { scrolled };
      }
    });
  };

  handleStart = () => {
    const { dispatch, layout, windowType, indicator } = this.props;

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
          response = await startProcess(windowType, layout.pinstanceId);

          const action = handleProcessResponse(
            response,
            windowType,
            layout.pinstanceId
          );

          await dispatch(action);

          this.removeModal();
        } catch (error) {
          throw error;
        } finally {
          this.setState({
            pending: false,
          });
        }
      }
    );
  };

  renderModalBody = () => {
    const {
      data,
      layout,
      tabId,
      rowId,
      dataId,
      modalType,
      windowType,
      isAdvanced,
    } = this.props;
    const { pending } = this.state;

    switch (modalType) {
      case 'window':
        return (
          <Window
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
            type={windowType}
            disabled={pending}
          />
        );
    }
  };

  renderPanel = () => {
    const {
      data,
      modalTitle,
      modalType,
      isDocumentNotSaved,
      layout,
    } = this.props;
    const { scrolled, pending, isNewDoc, isTooltipShow } = this.state;

    const applyHandler =
      modalType === 'process' ? this.handleStart : this.handleClose;
    const cancelHandler =
      modalType === 'process'
        ? this.handleClose
        : isNewDoc
        ? this.removeModal
        : undefined;

    return (
        <div className="modal-content-wrapper">
          <div className="panel panel-modal panel-modal-primary">
            <div
              className={classnames('panel-modal-header', {
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
                  {modalType === 'process'
                    ? counterpart.translate('modal.actions.cancel')
                    : counterpart.translate('modal.actions.done')}

                  {isTooltipShow ===
                    (modalType === 'process' ? keymap.CANCEL : keymap.DONE) && (
                    <Tooltips
                      name={
                        modalType === 'process' ? keymap.CANCEL : keymap.DONE
                      }
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
              </div>
            </div>

            <Indicator isDocumentNotSaved={isDocumentNotSaved} />

            <div
              className="panel-modal-content js-panel-modal-content
                            container-fluid"
              ref={c => {
                if (c) {
                  c.focus();
                }
              }}
            >
              {layout.description && (
                <div className="modal-top-description">
                  {layout.description}
                </div>
              )}
              {this.renderModalBody()}
            </div>
            {layout.layoutType !== 'singleOverlayField' && (
              <ModalContextShortcuts
                apply={applyHandler}
                cancel={cancelHandler}
              />
            )}
          </div>
        </div>
    );
  };

  renderOverlay = () => {
    const { data, layout, windowType, modalType, isNewDoc } = this.props;
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
        type={windowType}
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
    const { layout } = this.props;
    let renderedContent = null;

    if (layout && Object.keys(layout) && Object.keys(layout).length) {
      if (!layout.layoutType || layout.layoutType === 'panel') {
        renderedContent = this.renderPanel();
      } else if (layout.layoutType === 'singleOverlayField') {
        renderedContent = this.renderOverlay();
      }
    }

    if (!Object.keys(layout).length) {
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

const mapStateToProps = (state, props) => ({
  parentSelection: getSelection({
    state,
    windowType: props.parentType,
    viewId: props.viewId,
  }),
  activeTabId: state.windowHandler.master.layout.activeTab,
});

export default connect(mapStateToProps)(Modal);
