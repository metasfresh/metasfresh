import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { advSearchRequest, patchRequest } from '../../api';
import { PATCH_RESET } from '../../constants/ActionTypes';

import { closeViewModal } from '../../actions/ViewActions';
import { addNotification } from '../../actions/AppActions';
import { openRawModal } from '../../actions/WindowActions';

import keymap from '../../shortcuts/keymap';
import { renderHeaderPropertiesGroups } from '../../utils/documentListHelper';
import Tooltips from '../tooltips/Tooltips.js';
import ModalButton from '../modal/ModalButton';
import ModalComponent from '../modal/ModalComponent';
import { OIViewHeader_WINDOW_ID } from '../acctOpenItems/OIViewHeader';
import { AcctSimulationViewHeader_WINDOW_ID } from '../acctSimulation/AcctSimulationViewHeader';

/**
 * View modal
 */
class RawModal extends Component {
  state = {
    visibleTooltips: {},
  };

  componentWillUnmount() {
    const { masterDocumentList } = this.props;

    if (masterDocumentList) {
      masterDocumentList.updateQuickActions();
    }
  }

  UNSAFE_componentWillUpdate(props) {
    if (this.resolve) {
      if (!props.success || props.requests.length === 0) {
        this.resolve(props.success);
      }
    }
  }

  showTooltip = (type) => {
    this.setState({
      visibleTooltips: {
        ...this.state.visibleTooltips,
        [`${type}`]: true,
      },
    });
  };

  hideTooltip = (type) => {
    this.setState({
      visibleTooltips: {
        ...this.state.visibleTooltips,
        [`${type}`]: false,
      },
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

  handleClose = async (type) => {
    const { dispatch, requests, rawModal, featureType } = this.props;

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
      // await deleteViewRequest(windowId, viewId, type);
    }
  };

  removeModal = async (closeAction) => {
    const { dispatch, modalVisible, windowId, viewId } = this.props;

    await dispatch(
      closeViewModal({
        windowId,
        viewId,
        modalVisible,
        closeAction: closeAction ?? 'DONE',
      })
    );
  };

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

  generateShortcutActions = () => {
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

    return shortcutActions;
  };

  render() {
    const {
      windowId,
      modalTitle,
      children,
      modalDescription,
      rawModal,
      indicator,
    } = this.props;

    if (!children) {
      return null;
    }

    const isRenderHeaderProperties =
      !!rawModal.headerProperties &&
      String(windowId) !== OIViewHeader_WINDOW_ID &&
      String(windowId) !== AcctSimulationViewHeader_WINDOW_ID;

    return (
      <ModalComponent
        title={modalTitle}
        description={modalDescription}
        indicator={indicator}
        renderHeaderProperties={() =>
          isRenderHeaderProperties
            ? renderHeaderPropertiesGroups(rawModal.headerProperties.groups)
            : null
        }
        renderButtons={this.renderButtons}
        shortcutActions={this.generateShortcutActions()}
        onClickOutside={() => this.removeModal('CANCEL')}
      >
        {children}
      </ModalComponent>
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
