import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';

import { deleteViewRequest, advSearchRequest, patchRequest } from '../../api';
import { PATCH_RESET } from '../../constants/ActionTypes';

import { unsetIncludedView } from '../../actions/ViewActions';
import { addNotification } from '../../actions/AppActions';
import {
  closeModal,
  closeRawModal,
  openRawModal,
} from '../../actions/WindowActions';

import keymap from '../../shortcuts/keymap';
import ModalContextShortcuts from '../keyshortcuts/ModalContextShortcuts';
import { renderHeaderProperties } from '../../utils/documentListHelper';
import Tooltips from '../tooltips/Tooltips.js';
import Indicator from './Indicator';
import ModalButton from './ModalButton';

/**
 * @file Class based component.
 * @module RawModal
 * @extends Component
 */
class RawModal extends Component {
  state = {
    scrolled: false,
    visibleTooltips: {},
  };

  componentDidMount() {
    // Dirty solution, but use only if you need to
    // there is no way to affect body
    // because body is out of react app range
    // and css dont affect parents
    // but we have to change scope of scrollbar
    document.body.style.overflow = 'hidden';

    this.initEventListeners();
  }

  componentWillUnmount() {
    const { masterDocumentList } = this.props;

    if (masterDocumentList) {
      masterDocumentList.updateQuickActions();
    }

    this.removeEventListeners();
  }

  UNSAFE_componentWillUpdate(props) {
    if (this.resolve) {
      if (!props.success || props.requests.length === 0) {
        this.resolve(props.success);
      }
    }
  }

  /**
   * @method showTooltip
   * @summary ToDo: Describe the method.
   * @param {*} type
   */
  showTooltip = (type) => {
    this.setState({
      visibleTooltips: {
        ...this.state.visibleTooltips,
        [`${type}`]: true,
      },
    });
  };

  /**
   * @method hideTooltip
   * @summary ToDo: Describe the method.
   * @param {*} type
   */
  hideTooltip = (type) => {
    this.setState({
      visibleTooltips: {
        ...this.state.visibleTooltips,
        [`${type}`]: false,
      },
    });
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
   * @method handleScroll
   * @summary ToDo: Describe the method.
   * @param {object} event
   */
  handleScroll = (event) => {
    const scrollTop = event.srcElement.scrollTop;

    this.setState({
      scrolled: scrollTop > 0,
    });
  };

  /**
   * @method handleSearchDone
   * @summary Method executed only for the SEARCH type modal window, calls advSearchRequest from the API
   */
  handleSearchDone = (props) => {
    const {
      parentWindowId,
      parentDocumentId,
      parentFieldId,
      windowId,
      modalTableSelectedId,
    } = props;

    advSearchRequest({
      windowId: parentWindowId,
      documentId: parentDocumentId,
      fieldName: parentFieldId,
      advSearchWindowId: windowId,
      selectedId: modalTableSelectedId,
    }).then((response) => {
      if (!response.data.length) {
        console.error('No data for the selected ID');
        return false;
      }
      let {
        id: docIdToPatch,
        windowId: docTypeToPatch,
        fieldsByName,
      } = response.data[0];

      let valueToPatch = fieldsByName[parentFieldId].value;
      patchRequest({
        docId: docIdToPatch,
        docType: docTypeToPatch,
        entity: 'window',
        isAdvanced: false,
        isEdit: false,
        property: parentFieldId,
        value: valueToPatch,
      });
    });
  };

  /**
   * @async
   * @method handleClose
   * @summary ToDo: Describe the method.
   * @param {*} type
   */
  handleClose = async (type) => {
    const { dispatch, viewId, windowId, requests, rawModal, featureType } =
      this.props;

    featureType === 'SEARCH' &&
      type === 'DONE' &&
      this.handleSearchDone(this.props);

    if (requests.length > 0) {
      const success = await new Promise((resolve) => {
        this.resolve = resolve;
      });

      delete this.resolve;

      if (!success) {
        await dispatch({ type: PATCH_RESET });

        const title = 'Error while saving';
        const message = 'Not all fields have been saved';
        const time = 5000;
        const type = 'error';

        await dispatch(addNotification(title, message, time, type));

        return;
      }
    }

    if (type === 'BACK') {
      await dispatch(
        openRawModal({
          windowId: rawModal.parentWindowId,
          viewId: rawModal.parentViewId,
        })
      );
    } else {
      await this.removeModal();
      await deleteViewRequest(windowId, viewId, type);
    }
  };

  /**
   * @async
   * @method removeModal
   * @summary ToDo: Describe the method.
   */
  removeModal = async () => {
    const { dispatch, modalVisible, windowId, viewId } = this.props;

    await Promise.all(
      [
        closeRawModal(),
        closeModal(),
        unsetIncludedView({
          windowId: windowId,
          viewId,
          forceClose: true,
        }),
      ].map((action) => dispatch(action))
    );

    if (!modalVisible) {
      document.body.style.overflow = 'auto';
    }
  };

  /**
   * @method renderButtons
   * @summary ToDo: Describe the method.
   */
  renderButtons = () => {
    const { modalVisible, rawModal, windowId, modalTableSelectedId } =
      this.props;
    let { allowedCloseActions } = this.props;

    // This is hardcoded for the Search Window feature (injecting cancel button)
    if (windowId === '541045' && allowedCloseActions) {
      !allowedCloseActions.includes('CANCEL') &&
        allowedCloseActions.unshift('CANCEL');
    }

    const rawModalVisible = rawModal.visible || false;
    const buttonsArray = [];

    if (!allowedCloseActions) {
      allowedCloseActions = [];
    }

    for (let i = 0; i < allowedCloseActions.length; i += 1) {
      const name = allowedCloseActions[i];
      const showTooltip = this.state.visibleTooltips[name];
      const selector = `modal.actions.${name.toLowerCase()}`;

      buttonsArray.push(
        <ModalButton
          name={name}
          onClick={this.handleClose}
          tabIndex={!modalVisible && rawModalVisible ? 0 : -1}
          onShowTooltip={this.showTooltip}
          onHideTooltip={this.hideTooltip}
          key={i}
          disabled={
            windowId === '541045' && !modalTableSelectedId && name === 'DONE'
              ? true
              : false
          } // Disable the btn if no selection in src table
        >
          {counterpart.translate(selector)}
          {showTooltip && (
            <Tooltips
              name={keymap[name]}
              action={counterpart.translate(selector)}
              type={''}
            />
          )}
        </ModalButton>
      );
    }

    return buttonsArray;
  };

  /**
   * @method generateShortcuts
   * @summary ToDo: Describe the method.
   */
  generateShortcuts = () => {
    let { allowedCloseActions } = this.props;
    const shortcutActions = {};

    if (!allowedCloseActions) {
      shortcutActions.cancel = this.handleClose;

      allowedCloseActions = [];
    }

    for (let i = 0; i < allowedCloseActions.length; i += 1) {
      const name = allowedCloseActions[i];

      shortcutActions[`${name.toLowerCase()}`] = () => this.handleClose(name);
    }

    return <ModalContextShortcuts {...shortcutActions} />;
  };

  render() {
    const { modalTitle, children, modalDescription, rawModal, indicator } =
      this.props;
    const { scrolled } = this.state;

    if (!children) {
      return null;
    }

    return (
      <div className="screen-freeze raw-modal">
        <div className="click-overlay" onClick={this.removeModal} />
        <div className="modal-content-wrapper">
          <div className="panel panel-modal panel-modal-primary">
            <div
              className={classnames(
                'panel-groups-header',
                'panel-modal-header',
                {
                  'header-shadow': scrolled,
                }
              )}
            >
              <span className="panel-modal-header-title panel-modal-header-title-with-header-properties">
                {modalTitle ? modalTitle : 'Modal'}
                <span className="panel-modal-description">
                  {modalDescription ? modalDescription : ''}
                </span>
              </span>
              {!!rawModal.headerProperties && (
                <div className="optional">
                  {renderHeaderProperties(rawModal.headerProperties.groups)}
                </div>
              )}
              <div className="items-row-2">{this.renderButtons()}</div>
            </div>
            <Indicator indicator={indicator} />
            <div
              className="panel-modal-content js-panel-modal-content"
              ref={(c) => {
                c && c.focus();
              }}
            >
              {children}
            </div>
            {this.generateShortcuts()}
          </div>
        </div>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {func} dispatch
 * @prop {bool} [modalVisible]
 * @prop {object} rawModal
 * @prop {object} requests
 * @prop {bool} success
 * @prop {string} [indicator]
 * @prop {string|node} [modalTitle]
 * @prop {string|node} [modalDescription]
 * @prop {array} [allowedCloseActions]
 * @prop {string} [windowId]
 * @prop {string} [viewId]
 * @prop {string|node} [masterDocumentList]
 * @prop {node} [children]
 */
RawModal.propTypes = {
  dispatch: PropTypes.func.isRequired,
  modalVisible: PropTypes.bool,
  rawModal: PropTypes.object.isRequired,
  requests: PropTypes.object.isRequired,
  success: PropTypes.bool.isRequired,
  indicator: PropTypes.string.isRequired,
  modalTitle: PropTypes.oneOfType([PropTypes.string, PropTypes.node]),
  modalDescription: PropTypes.oneOfType([PropTypes.string, PropTypes.node]),
  allowedCloseActions: PropTypes.array,
  windowId: PropTypes.string,
  viewId: PropTypes.string,
  masterDocumentList: PropTypes.any,
  children: PropTypes.node,
  closeCallback: PropTypes.func,
  featureType: PropTypes.string,
  modalTableSelectedId: PropTypes.oneOfType([
    PropTypes.oneOf([null]),
    PropTypes.string,
  ]),
};

/**
 * @method mapStateToProps
 * @summary ToDo: Describe the method.
 * @param {object} windowHandler
 */
const mapStateToProps = ({ windowHandler, tables }, ownProps) => {
  let selArrInTable = tables[`${ownProps.windowId}_${ownProps.viewId}`];
  let selectedId = selArrInTable ? selArrInTable.selected[0] : null;

  return {
    modalVisible: windowHandler.modal.visible || false,
    rawModal: windowHandler.rawModal,
    requests: windowHandler.patches.requests,
    success: windowHandler.patches.success,
    indicator: windowHandler.indicator,
    modalTableSelectedId: selectedId,
  };
};

export default connect(mapStateToProps)(RawModal);
