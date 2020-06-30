import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import keymap from '../../shortcuts/keymap';
import {
  openModal,
  fetchTopActions,
  deleteTopActions,
} from '../../actions/WindowActions';
import Tooltips from '../tooltips/Tooltips';
import TableQuickInput from './TableQuickInput';
import { TableFilterContextShortcuts } from '../keyshortcuts';

class ActionButton extends Component {
  static propTypes = {
    action: PropTypes.object.isRequired,
    openModal: PropTypes.func.isRequired,
    docId: PropTypes.string,
    tabIndex: PropTypes.number,
    children: PropTypes.any,
    showTooltip: PropTypes.func,
    hideTooltip: PropTypes.func,
  };

  handleClick = () => {
    const { openModal, action, docId } = this.props;

    if (action.disabled) {
      return;
    }

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
    );
  };

  render() {
    const { action, tabIndex, children, showTooltip, hideTooltip } = this.props;

    return (
      <button
        onClick={this.handleClick}
        className="btn btn-meta-outline-secondary btn-distance btn-sm"
        tabIndex={tabIndex}
        title={action.description}
        onMouseEnter={showTooltip}
        onMouseLeave={hideTooltip}
      >
        {action.caption}
        {children}
      </button>
    );
  }
}

class TableFilter extends Component {
  static propTypes = {
    actions: PropTypes.array.isRequired,
    tabIndex: PropTypes.number.isRequired,
    modalVisible: PropTypes.bool.isRequired,
    fetchTopActions: PropTypes.func.isRequired,
    deleteTopActions: PropTypes.func.isRequired,
    forceHeight: PropTypes.number,
    tabId: PropTypes.string,
    docType: PropTypes.string,
    docId: PropTypes.string,
    openModal: PropTypes.func.isRequired,
    toggleFullScreen: PropTypes.func,
    fullScreen: PropTypes.any,
    wrapperHeight: PropTypes.number,
    selected: PropTypes.array,
    isBatchEntry: PropTypes.bool,
    handleBatchEntryToggle: PropTypes.func,
    supportQuickInput: PropTypes.bool,
    allowCreateNew: PropTypes.bool,
    openTableModal: PropTypes.func,
  };

  constructor(props) {
    super(props);

    this.state = {
      isTooltipShow: false,
    };
  }

  componentDidMount() {
    this.getActions();
  }

  componentWillUnmount() {
    this.props.deleteTopActions();
  }

  getActions = () => {
    const { tabId, docType, docId, fetchTopActions } = this.props;

    if (tabId && docType && docId) {
      fetchTopActions(docType, docId, tabId);
    }
  };

  handleClick = (action) => {
    const { openModal, docId } = this.props;

    if (action.disabled) {
      return;
    }

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
    );
  };

  generateShortcuts = () => {
    let { actions } = this.props;
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

  showTooltip = (name) => {
    this.setState({
      isTooltipShow: name,
    });
  };

  hideTooltip = () => {
    this.setState({
      isTooltipShow: null,
    });
  };

  render() {
    const {
      openTableModal,
      openModal,
      actions,
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
    const { isTooltipShow } = this.state;
    const tabIndex = fullScreen || modalVisible ? -1 : this.props.tabIndex;

    return (
      <div className="table-filter-line">
        <div className="form-flex-align">
          <div className="row filter-panel-buttons">
            {!isBatchEntry && allowCreateNew && (
              <button
                className="btn btn-meta-outline-secondary btn-distance btn-sm"
                onClick={openTableModal}
                tabIndex={tabIndex}
              >
                {counterpart.translate('window.addNew.caption')}
              </button>
            )}
            {supportQuickInput && !fullScreen && allowCreateNew && (
              <button
                className="btn btn-meta-outline-secondary btn-distance btn-sm close-batch-entry"
                onClick={handleBatchEntryToggle}
                onMouseEnter={() => this.showTooltip(keymap.TOGGLE_QUICK_INPUT)}
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
              ? actions.map((action) => (
                  <ActionButton
                    {...{
                      openModal,
                      tabIndex,
                      action,
                      docId,
                      tabId,
                      docType,
                      selected,
                    }}
                    showTooltip={() => this.showTooltip(action.processId)}
                    hideTooltip={this.hideTooltip}
                    key={`top-action-${action.processId}`}
                  >
                    {isTooltipShow === action.processId && (
                      <Tooltips
                        name={
                          action.shortcut
                            ? action.shortcut.replace('-', '+')
                            : ''
                        }
                        action={action.caption}
                        type={''}
                      />
                    )}
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
            className="btn-icon btn-meta-outline-secondary pointer btn-fullscreen"
            onClick={toggleFullScreen}
            onMouseEnter={() => this.showTooltip(keymap.TOGGLE_EXPAND)}
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

const mapStateToProps = ({ windowHandler }) => ({
  modalVisible: windowHandler.modal.visible,
  actions: windowHandler.master.topActions.actions,
});

export default connect(
  mapStateToProps,
  { fetchTopActions, deleteTopActions, openModal }
)(TableFilter);
