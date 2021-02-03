import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import cx from 'classnames';

import keymap from '../../shortcuts/keymap';
import QuickActionsContextShortcuts from '../keyshortcuts/QuickActionsContextShortcuts';

import {
  getQuickActions,
  getQuickActionsId,
} from '../../reducers/actionsHandler';
import { openModal } from '../../actions/WindowActions';
import { deleteQuickActions } from '../../actions/Actions';

import Tooltips from '../tooltips/Tooltips.js';
import QuickActionsDropdown from './QuickActionsDropdown';

/**
 * @file Class based component.
 * @module QuickActions
 * @extends Component
 */
export class QuickActions extends Component {
  mounted = false;

  constructor(props) {
    super(props);

    this.state = {
      isDropdownOpen: false,
      btnTooltip: false,
      listTooltip: false,
    };
  }

  componentWillUnmount = () => {
    const { deleteQuickActions, viewId, windowId } = this.props;

    this.mounted = false;

    deleteQuickActions(windowId, viewId);
  };

  /**
   * @method handleClickOPutside
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleClickOutside = () => {
    this.toggleDropdown();
  };

  /**
   * @method onClick
   * @summary Wrapper around the local `handleClick` to be in sync with how it's
   * called via `QuickActionsDropdown`.
   */
  onClick = (e) => {
    e.preventDefault;

    const {
      quickActions: { actions },
    } = this.props;

    this.handleClick(actions[0]);
  };

  /**
   * @method handleClick
   * @summary ToDo: Describe the method
   * @param {*} action
   * @todo Write the documentation
   */
  handleClick = (action) => {
    const { openModal, viewId, selected, childView, parentView } = this.props;

    if (action.disabled) {
      return;
    }

    openModal({
      title: action.caption,
      windowId: action.processId,
      modalType: 'process',
      viewId,
      viewDocumentIds: selected,
      parentViewId: parentView.viewId,
      parentViewSelectedIds: parentView.viewSelectedIds,
      childViewId: childView.viewId,
      childViewSelectedIds: childView.viewSelectedIds,
    });

    this.toggleDropdown(action);
  };

  /**
   * @method toggleDropdown
   * @summary Toggles the dropdown element
   */
  toggleDropdown = (action = null) => {
    const {
      quickActions: { actions },
    } = this.props;

    if (action && actions.length && actions[0].processId === action.processId) {
      this.setState({ isDropdownOpen: false }); // hide the dropdown when first action is clicked

      return;
    }
    this.setState({ isDropdownOpen: !this.state.isDropdownOpen });
  };

  /**
   * @method showListTooltip
   * @summary Forces hide of the dropdown element
   */
  hideDropdown = () => this.setState({ isDropdownOpen: false });

  /**
   * @method showListTooltip
   * @summary Shows list tooltip
   */
  showListTooltip = () => this.setState({ listTooltip: true });

  /**
   * @method hideListTooltip
   * @summary hides list tooltip
   */
  hideListTooltip = () => this.setState({ listTooltip: false });

  /**
   * @method showBtnTooltip
   * @summary Shows button tooltip
   */
  showBtnTooltip = () => this.setState({ btnTooltip: true });

  /**
   * @method hideBtnTooltip
   * @summary Hides button tooltip
   */
  hideBtnTooltip = () => this.setState({ btnTooltip: false });

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { isDropdownOpen, btnTooltip, listTooltip } = this.state;
    const {
      quickActions: { actions, pending },
      shouldNotUpdate,
      processStatus,
      disabled,
      className,
    } = this.props;
    const disabledDuringProcessing = processStatus === 'pending' || pending;

    if (actions.length) {
      return (
        <div
          className={cx(className, 'js-not-unselect ', {
            disabled: disabled,
          })}
        >
          <span className="action-label spacer-right">
            {counterpart.translate('window.quickActions.caption')}:
          </span>
          <div className="quick-actions-wrapper">
            <div
              className={
                'tag tag-success tag-xlg spacer-right ' +
                'quick-actions-tag ' +
                (actions[0].disabled || disabledDuringProcessing
                  ? 'tag-default '
                  : 'pointer ')
              }
              onMouseEnter={this.showListTooltip}
              onMouseLeave={this.hideListTooltip}
              onClick={this.onClick}
            >
              {listTooltip && (
                <Tooltips
                  name={keymap.QUICK_ACTION_POS}
                  action={'Run action'}
                  type={''}
                />
              )}
              {actions[0].caption}
            </div>
            <div
              className={cx(
                'btn-meta-outline-secondary btn-icon-sm',
                'btn-inline btn-icon pointer tooltip-parent',
                {
                  'btn-disabled': isDropdownOpen || disabledDuringProcessing,
                }
              )}
              onMouseEnter={this.showBtnTooltip}
              onMouseLeave={this.hideBtnTooltip}
              onClick={this.toggleDropdown}
            >
              <i className="meta-icon-down-1" />
              {btnTooltip && (
                <Tooltips
                  name={keymap.QUICK_ACTION_TOGGLE}
                  action={'Toggle list'}
                  type={''}
                />
              )}
            </div>
            {isDropdownOpen && (
              <QuickActionsDropdown
                actions={actions}
                handleClick={this.handleClick}
                handleClickOutside={this.hideDropdown}
                disableOnClickOutside={!isDropdownOpen}
              />
            )}
          </div>
          <QuickActionsContextShortcuts
            onAction={shouldNotUpdate ? null : this.onClick}
            stopPropagation={this.props.stopShortcutPropagation}
            onClick={this.toggleDropdown}
          />
        </div>
      );
    } else {
      return null;
    }
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {object} childView
 * @prop {object} parentView
 * @prop {string} windowId
 * @prop {string} [viewId]
 * @prop {string} [viewProfileId]
 * @prop {bool} [inBackground]
 * @prop {bool} [inModal]
 * @prop {bool} [disabled]
 * @prop {bool} [stopShortcutPropagation]
 * @prop {string} [processStatus]
 * @prop {string} [shouldNotUpdate]
 * @prop {any} [selected]
 * @todo Check title, buttons. Which proptype? Required or optional?
 */
QuickActions.propTypes = {
  // from @connect
  quickActions: PropTypes.object,
  openModal: PropTypes.func.isRequired,
  deleteQuickActions: PropTypes.func.isRequired,

  // from <DocumentList>
  childView: PropTypes.object.isRequired,
  parentView: PropTypes.object.isRequired,
  windowId: PropTypes.string.isRequired,
  viewId: PropTypes.string,
  viewProfileId: PropTypes.string,
  inBackground: PropTypes.bool,
  inModal: PropTypes.bool,
  disabled: PropTypes.bool,
  stopShortcutPropagation: PropTypes.bool,
  processStatus: PropTypes.string,
  shouldNotUpdate: PropTypes.any,
  selected: PropTypes.any,
  className: PropTypes.string,
};

const mapStateToProps = (state, { viewId, windowId }) => {
  const id = getQuickActionsId({ windowId, viewId });

  return {
    quickActions: getQuickActions(state, id),
  };
};

export default connect(
  mapStateToProps,
  { openModal, deleteQuickActions },
  false,
  { forwardRef: true }
)(QuickActions);
