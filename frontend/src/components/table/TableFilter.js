import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { useCallback, useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import keymap from '../../shortcuts/keymap';
import { TableFilterContextShortcuts } from '../keyshortcuts';
import { openModal } from '../../actions/WindowActions';
import { deleteTopActions, fetchTopActions } from '../../actions/Actions';
import { addNotification } from '../../actions/AppActions';

import Tooltips from '../tooltips/Tooltips';
import TableQuickInput from './TableQuickInput';
import { useSelectedRowIds } from '../../reducers/tables';
import { useTopActions } from '../../reducers/windowHandler';

/**
 * Component displayed above included tab header and contains buttons like 'Add new', 'Batch entry', top actions etc.
 */
const TableFilter = ({
  tabIndex: tabIndexProp,
  docType: windowId,
  tabId,
  docId,
  toggleFullScreen,
  fullScreen,
  wrapperHeight,
  isBatchEntry: isBatchEntryActive,
  handleBatchEntryToggle,
  quickInputSupport,
  newRecordInputMode,
  allowCreateNew,
  openTableModal,
  pending,
}) => {
  const isModalVisible = useSelector(
    (state) => state.windowHandler.modal.visible
  );
  const selectedRowIds = useSelectedRowIds({ windowId, tabId, docId });
  const topActions = useTopActions({ tabId });

  const [shortcutActions, setShortcutActions] = useState([]);
  const [isTooltipShow, setIsTooltipShow] = useState(null);

  const dispatch = useDispatch();

  useEffect(() => {
    if (windowId && tabId && docId) {
      dispatch(fetchTopActions({ windowId, tabId, docId }));
    }
    return () => {
      dispatch(deleteTopActions({ windowId, tabId, docId }));
    };
  }, [windowId, tabId, docId]);

  useEffect(() => {
    setShortcutActions(
      generateShortcutActionsArray(topActions, handleTopActionClick)
    );
  }, [topActions]);

  useEffect(() => {
    openCloseBatchEntryIfNeeded();
  }, [allowCreateNew, quickInputSupport, newRecordInputMode]);

  const openCloseBatchEntryIfNeeded = () => {
    //
    // Automatically open batch entry (if not already opened) when we can add new entries
    if (
      quickInputSupport &&
      newRecordInputMode === 'QUICK_INPUT_ONLY' &&
      allowCreateNew &&
      !isBatchEntryActive
    ) {
      handleBatchEntryToggle();
    }
    //
    // Close batch entry if open but creating new entries is no longer allowed
    else if (quickInputSupport && !allowCreateNew && isBatchEntryActive) {
      handleBatchEntryToggle();
    }
  };

  const handleTopActionClick = useCallback(
    (action) => {
      if (action.disabled) {
        return;
      }

      dispatch(
        openModal({
          title: action.caption,
          windowId: action.processId,
          modalType: 'process',
          viewDocumentIds: selectedRowIds,
        })
      );
    },
    [selectedRowIds]
  );

  /**
   * @summary create and store buttons for actions once, so that we won't redo this on each render
   */
  const renderTopActionButtons = () => {
    return (
      topActions &&
      topActions.map((action) => (
        <ActionButton
          key={`top-action-${action.processId}`}
          tabIndex={tabIndex}
          caption={action.caption}
          description={action.description}
          onClick={() => handleTopActionClick(action)}
          showTooltip={() => showTooltip(action.processId)}
          hideTooltip={hideTooltip}
          disabled={pending}
        >
          {isTooltipShow === action.processId && (
            <Tooltips
              name={action.shortcut ? action.shortcut.replace('-', '+') : ''}
              action={action.caption}
              type={''}
            />
          )}
        </ActionButton>
      ))
    );
  };

  const showTooltip = (name) => {
    setIsTooltipShow(name);
  };

  const hideTooltip = () => {
    setIsTooltipShow(null);
  };

  const tabIndex = fullScreen || isModalVisible ? -1 : tabIndexProp;

  const showNewButton =
    newRecordInputMode === 'ALL_METHODS' && // input mode allows it
    allowCreateNew && // we are allowed to create a new record
    !isBatchEntryActive; // batch entry is not already opened

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
              onMouseEnter={() => showTooltip(keymap.TOGGLE_QUICK_INPUT)}
              onMouseLeave={hideTooltip}
              tabIndex={tabIndex}
              disabled={pending}
            >
              {isBatchEntryActive
                ? quickInputSupport.closeButtonCaption
                : quickInputSupport.openButtonCaption}
              {isTooltipShow === keymap.TOGGLE_QUICK_INPUT && (
                <Tooltips
                  name={keymap.TOGGLE_QUICK_INPUT}
                  action={
                    isBatchEntryActive
                      ? quickInputSupport.closeButtonCaption
                      : quickInputSupport.openButtonCaption
                  }
                  type={''}
                />
              )}
            </button>
          )}
          {!isBatchEntryActive && renderTopActionButtons()}
          {!isBatchEntryActive &&
            (shortcutActions.length ? (
              <TableFilterContextShortcuts shortcutActions={shortcutActions} />
            ) : null)}
        </div>
        {quickInputSupport && isBatchEntryActive && allowCreateNew && (
          <TableQuickInput
            docType={windowId}
            docId={docId}
            tabId={tabId}
            forceHeight={wrapperHeight ? wrapperHeight : null}
            closeBatchEntry={handleBatchEntryToggle}
            addNotification={(...args) => dispatch(addNotification(...args))}
          />
        )}
      </div>

      {
        <button
          className="btn-icon btn-meta-outline-secondary pointer btn-fullscreen"
          onClick={toggleFullScreen}
          onMouseEnter={() => showTooltip(keymap.TOGGLE_EXPAND)}
          onMouseLeave={hideTooltip}
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
};

TableFilter.propTypes = {
  tabIndex: PropTypes.number.isRequired,
  forceHeight: PropTypes.number,
  docType: PropTypes.string,
  tabId: PropTypes.string,
  docId: PropTypes.string,
  toggleFullScreen: PropTypes.func,
  fullScreen: PropTypes.any,
  wrapperHeight: PropTypes.number,
  isBatchEntry: PropTypes.bool,
  handleBatchEntryToggle: PropTypes.func,
  quickInputSupport: PropTypes.object,
  newRecordInputMode: PropTypes.string,
  allowCreateNew: PropTypes.bool,
  openTableModal: PropTypes.func,
  pending: PropTypes.bool,
};

const generateShortcutActionsArray = (actions, handleTopActionClick) => {
  const shortcutActions = [];

  for (let i = 0; i < actions.length; i++) {
    const action = actions[i];

    if (!action.shortcut) {
      continue;
    }

    const shortcut = action.shortcut.replace('-', '+');

    shortcutActions.push({
      name: `FILTER_ACTION_${i}`,
      handler: () => handleTopActionClick(action),
      shortcut,
    });
  }

  return shortcutActions;
};

//
//
//
// -----------------------------------------
//
//
//

const ActionButton = ({
  caption,
  description,
  tabIndex,
  children,
  onClick,
  showTooltip,
  hideTooltip,
  disabled = false,
}) => {
  return (
    <button
      onClick={onClick}
      className="btn btn-meta-outline-secondary btn-distance btn-sm"
      tabIndex={tabIndex}
      title={description}
      onMouseEnter={showTooltip}
      onMouseLeave={hideTooltip}
      disabled={disabled}
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
  disabled: PropTypes.bool,
};

export default TableFilter;
