import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import keymap from '../../shortcuts/keymap';
import { topActionsRequest } from '../../api';
import { openModal } from '../../actions/WindowActions';
import Tooltips from '../tooltips/Tooltips';
import TableQuickInput from './TableQuickInput';
import { TableFilterContextShortcuts } from '../keyshortcuts';

class ActionButton extends Component {
  static propTypes = {
    action: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired,
    docId: PropTypes.string,
    tabIndex: PropTypes.number,
    children: PropTypes.element,
  };

  handleClick = () => {
    const { dispatch, action, docId } = this.props;

    if (action.disabled) {
      return;
    }

    dispatch(
      openModal(
        action.caption,
        action.processId,
        'process',
        null,
        null,
        false,
        null,
        [docId],
        null,
        null,
        null,
        null
      )
    );
  };

  render() {
    const { action, tabIndex, children } = this.props;

    return (
      <button
        onClick={this.handleClick}
        className="btn btn-meta-outline-secondary btn-distance btn-sm"
        tabIndex={tabIndex}
        title={action.description}
      >
        {action.caption}
        {children}
      </button>
    );
  }
}

class TableFilter extends Component {
  static propTypes = {
    dispatch: PropTypes.func.isRequired,
    tabIndex: PropTypes.number.isRequired,
    modalVisible: PropTypes.bool.isRequired,
    forceHeight: PropTypes.number,
    tabId: PropTypes.string,
    docType: PropTypes.string,
    docId: PropTypes.string,
    openModal: PropTypes.func,
    toggleFullScreen: PropTypes.func,
    fullScreen: PropTypes.any,
    wrapperHeight: PropTypes.number,
    selected: PropTypes.array,
    isBatchEntry: PropTypes.bool,
    handleBatchEntryToggle: PropTypes.func,
    supportQuickInput: PropTypes.bool,
    allowCreateNew: PropTypes.bool,
  };

  constructor(props) {
    super(props);

    this.state = {
      isTooltipShow: false,
      actions: [],
    };
  }

  componentDidMount() {
    this.getActions();
  }

  getActions = () => {
    const { tabId, docType, docId } = this.props;

    if (tabId && docType && docId) {
      topActionsRequest(docType, docId, tabId).then(({ data }) => {
        this.setState({
          actions: data.actions,
        });
      });
    }
  };

  handleClick = action => {
    const { dispatch, docId } = this.props;

    if (action.disabled) {
      return;
    }

    dispatch(
      openModal(
        action.caption,
        action.processId,
        'process',
        null,
        null,
        false,
        null,
        [docId],
        null,
        null,
        null,
        null
      )
    );
  };

  generateShortcuts = () => {
    let { actions } = this.state;
    const shortcutActions = [];

    if (!actions) {
      actions = [];
    }

    for (let i = 0; i < actions.length; i += 1) {
      const action = actions[i];

      shortcutActions.push({
        name: `FILTER_ACTION_${i}`,
        handler: () => this.handleClick(action),
        shortcut: action.shortcut ? action.shortcut.replace('-', '+') : '',
      });
    }

    return <TableFilterContextShortcuts shortcutActions={shortcutActions} />;
  };

  showTooltip = () => {
    this.setState({
      isTooltipShow: keymap.TOGGLE_QUICK_INPUT,
    });
  };

  hideTooltip = (key = null) => {
    this.setState({
      isTooltipShow: key,
    });
  };

  showExpandTooltip = () => {
    this.setState({
      isTooltipShow: keymap.TOGGLE_EXPAND,
    });
  };

  render() {
    const {
      dispatch,
      openModal,
      toggleFullScreen,
      fullScreen,
      docType,
      docId,
      tabId,
      selected,
      isBatchEntry,
      handleBatchEntryToggle,
      supportQuickInput,
      allowCreateNew,
      modalVisible,
      wrapperHeight,
    } = this.props;
    const { isTooltipShow, actions } = this.state;
    const tabIndex = fullScreen || modalVisible ? -1 : this.props.tabIndex;

    return (
      <div className="form-flex-align table-filter-line">
        <div className="form-flex-align">
          <div>
            {!isBatchEntry && allowCreateNew && (
              <button
                className="btn btn-meta-outline-secondary btn-distance btn-sm"
                onClick={openModal}
                tabIndex={tabIndex}
              >
                {counterpart.translate('window.addNew.caption')}
              </button>
            )}
            {supportQuickInput && !fullScreen && allowCreateNew && (
              <button
                className="btn btn-meta-outline-secondary btn-distance btn-sm"
                onClick={handleBatchEntryToggle}
                onMouseEnter={this.showTooltip}
                onMouseLeave={this.hideTooltip}
                tabIndex={tabIndex}
              >
                {isBatchEntry
                  ? counterpart.translate('window.batchEntryClose.caption')
                  : counterpart.translate('window.batchEntry.caption')}
                {isTooltipShow === keymap.TOGGLE_QUICK_INPUT && (
                  <Tooltips
                    name={keymap.TOGGLE_QUICK_INPUT}
                    action={
                      isBatchEntry
                        ? counterpart.translate(
                            'window.batchEntryClose.caption'
                          )
                        : counterpart.translate('window.batchEntry.caption')
                    }
                    type={''}
                  />
                )}
              </button>
            )}
            {!isBatchEntry && actions.length
              ? actions.map(action => (
                  <ActionButton
                    {...{
                      dispatch,
                      tabIndex,
                      action,
                      docId,
                      tabId,
                      docType,
                      selected,
                    }}
                    key={`top-action-${action.processId}`}
                  >
                    <Tooltips
                      name={
                        action.shortcut ? action.shortcut.replace('-', '+') : ''
                      }
                      action={action.caption}
                      type={''}
                    />
                  </ActionButton>
                ))
              : null}
            {!isBatchEntry && actions.length ? this.generateShortcuts() : null}
          </div>
          {supportQuickInput &&
            (isBatchEntry || fullScreen) &&
            allowCreateNew && (
              <TableQuickInput
                closeBatchEntry={handleBatchEntryToggle}
                docType={docType}
                docId={docId}
                tabId={tabId}
                forceHeight={wrapperHeight ? wrapperHeight : null}
              />
            )}
        </div>

        {
          <button
            className="btn-icon btn-meta-outline-secondary pointer"
            onClick={toggleFullScreen}
            onMouseEnter={this.showExpandTooltip}
            onMouseLeave={this.hideTooltip}
            tabIndex="-1"
          >
            {fullScreen ? (
              <i className="meta-icon-collapse" />
            ) : (
              <i className="meta-icon-fullscreen" />
            )}

            {isTooltipShow === keymap.TOGGLE_EXPAND && (
              <Tooltips
                name={keymap.TOGGLE_EXPAND}
                action={
                  fullScreen
                    ? counterpart.translate('window.table.collapse')
                    : counterpart.translate('window.table.expand')
                }
                type={''}
              />
            )}
          </button>
        }
      </div>
    );
  }
}

export default connect(state => ({
  modalVisible: state.windowHandler.modal.visible,
}))(TableFilter);
