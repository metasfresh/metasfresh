import counterpart from "counterpart";
import PropTypes from "prop-types";
import React, { Component } from "react";
import { connect } from "react-redux";

import { closeListIncludedView } from "../../actions/ListActions";
import { deleteView } from "../../actions/ViewActions";
import { closeModal, closeRawModal } from "../../actions/WindowActions";
import keymap from "../../shortcuts/keymap";
import ModalContextShortcuts from "../shortcuts/ModalContextShortcuts";
import Tooltips from "../tooltips/Tooltips.js";
import Indicator from "./Indicator";

class RawModal extends Component {
  state = {
    scrolled: false,
    isTooltipShow: false
  };

  componentDidMount() {
    // Dirty solution, but use only if you need to
    // there is no way to affect body
    // because body is out of react app range
    // and css dont affect parents
    // but we have to change scope of scrollbar
    document.body.style.overflow = "hidden";

    this.initEventListeners();
  }

  componentWillUnmount() {
    const { masterDocumentList } = this.props;

    if (masterDocumentList) {
      masterDocumentList.updateQuickActions();
    }

    this.removeEventListeners();
  }

  toggleTooltip = visible => {
    this.setState({
      isTooltipShow: visible
    });
  };

  initEventListeners = () => {
    const modalContent = document.querySelector(".js-panel-modal-content");

    if (modalContent) {
      modalContent.addEventListener("scroll", this.handleScroll);
    }
  };

  removeEventListeners = () => {
    const modalContent = document.querySelector(".js-panel-modal-content");

    if (modalContent) {
      modalContent.removeEventListener("scroll", this.handleScroll);
    }
  };

  handleScroll = event => {
    const scrollTop = event.srcElement.scrollTop;

    this.setState({
      scrolled: scrollTop > 0
    });
  };

  handleClose = async () => {
    const { closeCallback, viewId, windowType } = this.props;
    const { isNew } = this.state;

    if (closeCallback) {
      await closeCallback(isNew);
    }

    await deleteView(windowType, viewId);

    await this.removeModal();
  };

  removeModal = async () => {
    const { dispatch, modalVisible, windowType, viewId } = this.props;

    for (const action of [
      closeRawModal(),
      closeModal(),
      closeListIncludedView({
        windowType,
        viewId,
        forceClose: true
      })
    ]) {
      await dispatch(action);
    }

    if (!modalVisible) {
      document.body.style.overflow = "auto";
    }
  };

  render() {
    const { modalTitle, children, modalDescription } = this.props;

    const { scrolled, isTooltipShow } = this.state;

    return (
      <div className="screen-freeze raw-modal">
        <div className="panel panel-modal panel-modal-primary">
          <div
            className={
              "panel-modal-header " + (scrolled ? "header-shadow" : "")
            }
          >
            <span className="panel-modal-header-title">
              {modalTitle ? modalTitle : "Modal"}
              <span className="panel-modal-description">
                {modalDescription ? modalDescription : ""}
              </span>
            </span>

            <div className="items-row-2">
              <button
                className="btn btn-meta-outline-secondary btn-distance-3 btn-md"
                onClick={this.handleClose}
                tabIndex={0}
                onMouseEnter={() => this.toggleTooltip(true)}
                onMouseLeave={() => this.toggleTooltip(false)}
              >
                {counterpart.translate("modal.actions.done")}
                {isTooltipShow && (
                  <Tooltips
                    name={keymap.APPLY}
                    action={counterpart.translate("modal.actions.done")}
                    type={""}
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
  modalVisible: state.windowHandler.modal.visible || false
});

RawModal.propTypes = {
  dispatch: PropTypes.func.isRequired,
  modalVisible: PropTypes.bool
};

export default connect(mapStateToProps)(RawModal);
