import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { PATCH_RESET } from '../../constants/ActionTypes';
import { closeListIncludedView } from '../../actions/ListActions';
import { deleteView } from '../../api';
import { addNotification } from '../../actions/AppActions';
import { closeModal, closeRawModal } from '../../actions/WindowActions';
import keymap from '../../shortcuts/keymap';
import ModalContextShortcuts from '../keyshortcuts/ModalContextShortcuts';
import Tooltips from '../tooltips/Tooltips.js';
import Indicator from './Indicator';

class RawModal extends Component {
  state = {
    scrolled: false,
    isTooltipShow: false,
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

  componentWillUpdate(props) {
    if (this.resolve) {
      if (!props.success || props.requests.length === 0) {
        this.resolve(props.success);
      }
    }
  }

  toggleTooltip = visible => {
    this.setState({
      isTooltipShow: visible,
    });
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

  handleScroll = event => {
    const scrollTop = event.srcElement.scrollTop;

    this.setState({
      scrolled: scrollTop > 0,
    });
  };

  handleClose = async () => {
    const {
      dispatch,
      closeCallback,
      viewId,
      windowType,
      requests,
    } = this.props;

    const { isNew } = this.state;

    if (requests.length > 0) {
      const success = await new Promise(resolve => {
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

    if (closeCallback) {
      await closeCallback(isNew);
    }

    await this.removeModal();

    await deleteView(windowType, viewId);
  };

  removeModal = async () => {
    const { dispatch, modalVisible, windowType, viewId } = this.props;

    await Promise.all(
      [
        closeRawModal(),
        closeModal(),
        closeListIncludedView({
          windowType,
          viewId,
          forceClose: true,
        }),
      ].map(action => dispatch(action))
    );

    if (!modalVisible) {
      document.body.style.overflow = 'auto';
    }
  };

  render() {
    const {
      modalTitle,
      children,
      modalDescription,
      modalVisible,
      rawModalVisible,
    } = this.props;

    const { scrolled, isTooltipShow } = this.state;

    return (
      <div className="screen-freeze raw-modal">
        <div className="panel panel-modal panel-modal-primary">
          <div
            className={
              'panel-modal-header ' + (scrolled ? 'header-shadow' : '')
            }
          >
            <span className="panel-modal-header-title">
              {modalTitle ? modalTitle : 'Modal'}
              <span className="panel-modal-description">
                {modalDescription ? modalDescription : ''}
              </span>
            </span>

            <div className="items-row-2">
              <button
                className="btn btn-meta-outline-secondary btn-distance-3 btn-md"
                onClick={this.handleClose}
                tabIndex={!modalVisible && rawModalVisible ? 0 : -1}
                onMouseEnter={() => this.toggleTooltip(true)}
                onMouseLeave={() => this.toggleTooltip(false)}
              >
                {counterpart.translate('modal.actions.done')}
                {isTooltipShow && (
                  <Tooltips
                    name={keymap.APPLY}
                    action={counterpart.translate('modal.actions.done')}
                    type={''}
                  />
                )}
              </button>
            </div>
          </div>
          <Indicator />
          <div
            className="panel-modal-content js-panel-modal-content"
            ref={c => {
              c && c.focus();
            }}
          >
            {children}
          </div>
          <ModalContextShortcuts apply={this.handleClose} />
        </div>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  modalVisible: state.windowHandler.modal.visible || false,
  rawModalVisible: state.windowHandler.rawModal.visible || false,
  requests: state.windowHandler.patches.requests,
  success: state.windowHandler.patches.success,
});

RawModal.propTypes = {
  dispatch: PropTypes.func.isRequired,
  children: PropTypes.node,
  modalTitle: PropTypes.oneOfType([PropTypes.string, PropTypes.node]),
  modalDescription: PropTypes.oneOfType([PropTypes.string, PropTypes.node]),
  modalVisible: PropTypes.bool,
  rawModalVisible: PropTypes.bool,
  requests: PropTypes.object.isRequired,
  success: PropTypes.bool.isRequired,
};

export default connect(mapStateToProps)(RawModal);
