import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

import keymap from '../../shortcuts/keymap';
import { TableFilterContextShortcuts } from '../keyshortcuts';
import { openModal } from '../../actions/WindowActions';
import { deleteTopActions, fetchTopActions } from '../../actions/Actions';
import { addNotification } from '../../actions/AppActions';

import Tooltips from '../tooltips/Tooltips';
import TableQuickInput from './TableQuickInput';
import { getSelection, getTableId } from '../../reducers/tables';

/**
 * Component displayed above included tab header and contains buttons like 'Add new', 'Batch entry', top actions etc.
 */
class TableFilter extends PureComponent {
  static propTypes = {
    tabIndex: PropTypes.number.isRequired,
    modalVisible: PropTypes.bool.isRequired,
    fetchTopActions: PropTypes.func.isRequired,
    deleteTopActions: PropTypes.func.isRequired,
    forceHeight: PropTypes.number,
    tabId: PropTypes.string,
    docType: PropTypes.string,
    docId: PropTypes.string,
    selectedRowIds: PropTypes.array,
    openModal: PropTypes.func.isRequired,
    toggleFullScreen: PropTypes.func,
    fullScreen: PropTypes.any,
    wrapperHeight: PropTypes.number,
    isBatchEntry: PropTypes.bool,
    handleBatchEntryToggle: PropTypes.func,
    quickInputSupport: PropTypes.object,
    newRecordInputMode: PropTypes.string,
    allowCreateNew: PropTypes.bool,
    openTableModal: PropTypes.func,
    addNotification: PropTypes.func.isRequired,
    pending: PropTypes.bool,
  };

  constructor(props) {
    super(props);

    this.state = {
      topActions: [],
      isTooltipShow: false,
      shortcutActions: [],
    };
  }

  componentDidMount() {
    this.loadTopActions();
    this.openCloseBatchEntryIfNeeded();
  }

  componentWillUnmount() {
    this.props.deleteTopActions();
  }

  componentDidUpdate(prevProps) {
    if (
      this.props.allowCreateNew !== prevProps.allowCreateNew ||
      this.props.quickInputSupport !== prevProps.quickInputSupport ||
      this.props.newRecordInputMode !== prevProps.newRecordInputMode
    ) {
      this.openCloseBatchEntryIfNeeded();
    }
  }

  openCloseBatchEntryIfNeeded = () => {
    const {
      allowCreateNew,
      quickInputSupport,
      newRecordInputMode,
      isBatchEntry,
      handleBatchEntryToggle,
    } = this.props;

    //
    // Automatically open batch entry (if not already opened) when we can add new entries
    if (
      quickInputSupport &&
      newRecordInputMode === 'QUICK_INPUT_ONLY' &&
      allowCreateNew &&
      !isBatchEntry
    ) {
      handleBatchEntryToggle();
    }
    //
    // Close batch entry if open but creating new entries is no longer allowed
    else if (quickInputSupport && !allowCreateNew && isBatchEntry) {
      handleBatchEntryToggle();
    }
  };

  /**
   * @method loadTopActions
   * @summary fetch top actions for the table
   */
  loadTopActions = () => {
    const { tabId, docType, docId, fetchTopActions } = this.props;

    if (tabId && docType && docId) {
      fetchTopActions(docType, docId, tabId).then((topActions) => {
        this.setState({ topActions });
        this.generateShortcuts(topActions);
      });
    }
  };

  handleTopActionClick = (action) => {
    const { openModal, selectedRowIds } = this.props;

    if (action.disabled) {
      return;
    }

    openModal({
      title: action.caption,
      windowId: action.processId,
      modalType: 'process',
      viewDocumentIds: selectedRowIds,
    });
  };

  /**
   * @summary create and store buttons for actions once, so that we won't redo this on each render
   */
  renderTopActionButtons = () => {
    const { tabIndex } = this.props;
    const { topActions, isTooltipShow } = this.state;

    if (!topActions || !topActions.length) {
      return null;
    }

    return topActions.map((action) => (
      <ActionButton
        key={`top-action-${action.processId}`}
        tabIndex={tabIndex}
        caption={action.caption}
        description={action.description}
        onClick={() => this.handleTopActionClick(action)}
        showTooltip={() => this.showTooltip(action.processId)}
        hideTooltip={this.hideTooltip}
      >
        {isTooltipShow === action.processId && (
          <Tooltips
            name={action.shortcut ? action.shortcut.replace('-', '+') : ''}
            action={action.caption}
            type={''}
          />
        )}
      </ActionButton>
    ));
  };

  /**
   * @method generateShortcuts
   * @summary generate table filters shortcuts and store them in state. We're doing
   * this to force a re-render once we have the actions data available.
   */
  generateShortcuts = (actions) => {
    const shortcutActions = [];

    for (let i = 0; i < actions.length; i += 1) {
      const action = actions[i];

      shortcutActions.push({
        name: `FILTER_ACTION_${i}`,
        handler: () => this.handleTopActionClick(action),
        shortcut: action.shortcut ? action.shortcut.replace('-', '+') : '',
      });
    }

    this.setState({ shortcutActions });
  };

  showTooltip = (name) => {
    this.setState({ isTooltipShow: name });
  };

  hideTooltip = () => {
    this.setState({ isTooltipShow: null });
  };

  render() {
    const {
      openTableModal,
      toggleFullScreen,
      fullScreen,
      docType,
      docId,
      tabId,
      isBatchEntry,
      newRecordInputMode,
      handleBatchEntryToggle,
      quickInputSupport,
      allowCreateNew,
      modalVisible,
      wrapperHeight,
      addNotification,
      pending,
    } = this.props;
    const { isTooltipShow, shortcutActions } = this.state;
    const tabIndex = fullScreen || modalVisible ? -1 : this.props.tabIndex;

    const showNewButton =
      newRecordInputMode === 'ALL_METHODS' && // input mode allows it
      allowCreateNew && // we are allowed to create a new record
      !isBatchEntry; // batch entry is not already opened

    const showBatchEntryButton =
      (newRecordInputMode === 'ALL_METHODS' ||
        newRecordInputMode === 'QUICK_INPUT_ONLY') && // input mode allows it
      quickInputSupport && // batch entry is supported by backend
      allowCreateNew && // we are allowed to create a new record
      !fullScreen; // included tab is not in full screen mode

    return (
      <div className="table-filter-line">
        <div className="form-flex-align">
          <div className="row filter-panel-buttons">
            {showNewButton && (
              <button
                className="btn btn-meta-outline-secondary btn-distance btn-sm"
                onClick={openTableModal}
                tabIndex={tabIndex}
                disabled={pending}
              >
                {counterpart.translate('window.addNew.caption')}
              </button>
            )}
            {showBatchEntryButton && (
              <button
                className="btn btn-meta-outline-secondary btn-distance btn-sm close-batch-entry"
                onClick={handleBatchEntryToggle}
                onMouseEnter={() => this.showTooltip(keymap.TOGGLE_QUICK_INPUT)}
                onMouseLeave={this.hideTooltip}
                tabIndex={tabIndex}
                disabled={pending}
              >
                {isBatchEntry
                  ? quickInputSupport.closeButtonCaption
                  : quickInputSupport.openButtonCaption}
                {isTooltipShow === keymap.TOGGLE_QUICK_INPUT && (
                  <Tooltips
                    name={keymap.TOGGLE_QUICK_INPUT}
                    action={
                      isBatchEntry
                        ? quickInputSupport.closeButtonCaption
                        : quickInputSupport.openButtonCaption
                    }
                    type={''}
                  />
                )}
              </button>
            )}
            {!isBatchEntry && this.renderTopActionButtons()}
            {!isBatchEntry &&
              (shortcutActions.length ? (
                <TableFilterContextShortcuts
                  shortcutActions={shortcutActions}
                />
              ) : null)}
          </div>
          {quickInputSupport &&
            (isBatchEntry || fullScreen) &&
            allowCreateNew && (
              <TableQuickInput
                docType={docType}
                docId={docId}
                tabId={tabId}
                forceHeight={wrapperHeight ? wrapperHeight : null}
                closeBatchEntry={handleBatchEntryToggle}
                addNotification={addNotification}
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

const ActionButton = ({
  caption,
  description,
  tabIndex,
  children,
  onClick,
  showTooltip,
  hideTooltip,
}) => {
  return (
    <button
      onClick={onClick}
      className="btn btn-meta-outline-secondary btn-distance btn-sm"
      tabIndex={tabIndex}
      title={description}
      onMouseEnter={showTooltip}
      onMouseLeave={hideTooltip}
    >
      {caption}
      {children}
    </button>
  );
};
ActionButton.propTypes = {
  caption: PropTypes.string.isRequired,
  description: PropTypes.string,
  docId: PropTypes.string,
  tabIndex: PropTypes.number,
  children: PropTypes.any,
  onClick: PropTypes.func.isRequired,
  showTooltip: PropTypes.func,
  hideTooltip: PropTypes.func,
};

const mapStateToProps = (state, props) => {
  const { docType: windowId, docId, tabId } = props;

  const tableId = getTableId({ windowId, docId, tabId });

  return {
    modalVisible: state.windowHandler.modal.visible,
    selectedRowIds: getSelection()(state, tableId),
  };
};

export default connect(mapStateToProps, {
  fetchTopActions,
  deleteTopActions,
  openModal,
  addNotification,
})(TableFilter);
