import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import Queue from 'simple-promise-queue';
import cx from 'classnames';
import _ from 'lodash';
import { quickActionsRequest } from '../../api';
import { getQuickactions } from '../../reducers/windowHandler';
import {
  openModal,
  fetchedQuickActions,
  deleteQuickActions,
} from '../../actions/WindowActions';
import keymap from '../../shortcuts/keymap';
import QuickActionsContextShortcuts from '../keyshortcuts/QuickActionsContextShortcuts';
import Tooltips from '../tooltips/Tooltips.js';
import QuickActionsDropdown from './QuickActionsDropdown';

const initialState = {
  isDropdownOpen: false,
  btnTooltip: false,
  listTooltip: false,
  loading: false,
};

/**
 * @file Class based component.
 * @module QuickActions
 * @extends Component
 */
export class QuickActions extends Component {
  mounted = false;

  constructor(props) {
    super(props);

    this.state = initialState;

    this.fetchActions = this.fetchActions.bind(this);

    this.queue = new Queue({
      autoStart: true,
    });
  }

  componentDidMount = () => {
    this.mounted = true;

    const {
      fetchOnInit,
      selected,
      windowType,
      viewId,
      viewProfileId,
      childView,
      parentView,
    } = this.props;

    if (fetchOnInit) {
      this.queue.pushTask((res, rej) => {
        this.fetchActions(
          windowType,
          viewId,
          viewProfileId,
          selected,
          childView,
          parentView,
          res,
          rej
        );
      });
    }
  };

  componentWillUnmount = () => {
    const { deleteQuickActions, viewId, windowType } = this.props;

    this.mounted = false;

    deleteQuickActions(windowType, viewId);
  };

  UNSAFE_componentWillReceiveProps = (nextProps) => {
    const { selected, viewId, windowType } = this.props;

    if (
      ((selected || nextProps.selected) &&
        JSON.stringify(nextProps.selected) !== JSON.stringify(selected)) ||
      (nextProps.viewId && nextProps.viewId !== viewId) ||
      (nextProps.windowType && nextProps.windowType !== windowType)
    ) {
      this.queue.pushTask((res, rej) => {
        this.fetchActions(
          nextProps.windowType,
          nextProps.viewId,
          nextProps.viewProfileId,
          nextProps.selected,
          nextProps.childView,
          nextProps.parentView,
          res,
          rej
        );
      });
    }
  };

  shouldComponentUpdate(nextProps) {
    return nextProps.shouldNotUpdate !== true;
  }

  componentDidUpdate = (prevProps) => {
    const { inBackground, inModal } = this.props;

    if (inModal === false && prevProps.inModal === true) {
      // gained focus after sub-modal closed
      this.setState({
        loading: false,
      });
    }

    if (inBackground === true && prevProps.inBackground === false) {
      // gained focus after modal closed
      this.setState({
        loading: false,
      });
    }
  };

  /**
   * @method updateActions
   * @summary ToDo: Describe the method
   * @param {*} childSelection
   * @todo Write the documentation
   */
  updateActions = (childSelection = this.props.childView.viewSelectedIds) => {
    const {
      windowType,
      viewId,
      viewProfileId,
      selected,
      childView,
      parentView,
    } = this.props;

    this.queue.pushTask((res, rej) => {
      this.fetchActions(
        windowType,
        viewId,
        viewProfileId,
        selected,
        { ...childView, viewSelectedIds: childSelection },
        parentView,
        res,
        rej
      );
    });
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

    const { actions } = this.props;

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

    this.setState({
      loading: true,
    });

    openModal(
      action.caption,
      action.processId,
      'process',
      null,
      null,
      false,
      viewId,
      selected,
      null,
      null,
      parentView.viewId,
      parentView.viewSelectedIds,
      childView.viewId,
      childView.viewSelectedIds
    );

    this.toggleDropdown();
  };

  /**
   * @async
   * @method renderCancelButton
   * @summary ToDo: Describe the method
   * @param {*} windowId
   * @param {*} viewId
   * @param {*} viewProfileId
   * @param {*} selected
   * @param {*} childView
   * @param {*} parentView
   * @param {*} resolve
   * @param {*} reject
   * @todo Rewrite this as an action creator
   */
  async fetchActions(
    windowId,
    viewId,
    viewProfileId,
    selected,
    childView,
    parentView,
    resolve,
    reject
  ) {
    const { fetchedQuickActions } = this.props;
    if (!this.mounted) {
      return resolve();
    }

    if (windowId && viewId) {
      await quickActionsRequest(
        windowId,
        viewId,
        viewProfileId,
        selected,
        childView,
        parentView
      )
        .then((result) => {
          const [respRel, resp] = result;

          if (this.mounted) {
            const currentActions =
              resp && resp.data ? resp.data.actions : respRel.data.actions;
            const relatedActions =
              resp && resp.data ? respRel.data.actions : null;

            if (
              (parentView.viewId || childView.viewId) &&
              relatedActions &&
              !_.isEmpty(parentView)
            ) {
              const windowType = parentView.windowType
                ? parentView.windowType
                : childView.windowType;
              const id = parentView.viewId
                ? parentView.viewId
                : childView.viewId;
              fetchedQuickActions(windowType, id, relatedActions);
            }

            fetchedQuickActions(windowId, viewId, currentActions);

            return this.setState(
              {
                loading: false,
              },
              resolve
            );
          }
        })
        .catch((e) => {
          // eslint-disable-next-line no-console
          console.error(e);

          if (this.mounted) {
            return this.setState(
              {
                loading: false,
              },
              reject
            );
          }
        });
    } else {
      if (this.mounted) {
        return this.setState(
          {
            loading: false,
          },
          resolve
        );
      }
    }
  }

  /**
   * @method toggleDropdown
   * @summary Toggles the dropdown element
   */
  toggleDropdown = () => {
    this.setState({
      isDropdownOpen: !this.state.isDropdownOpen,
    });
  };

  /**
   * @method showListTooltip
   * @summary Forces hide of the dropdown element
   */
  hideDropdown = () => {
    this.setState({ isDropdownOpen: false });
  };

  /**
   * @method showListTooltip
   * @summary Shows list tooltip
   */
  showListTooltip = () => {
    this.setState({
      listTooltip: true,
    });
  };

  /**
   * @method hideListTooltip
   * @summary hides list tooltip
   */
  hideListTooltip = () => {
    this.setState({
      listTooltip: false,
    });
  };

  /**
   * @method showBtnTooltip
   * @summary Shows button tooltip
   */
  showBtnTooltip = () => {
    this.setState({
      btnTooltip: true,
    });
  };

  /**
   * @method hideBtnTooltip
   * @summary Hides button tooltip
   */
  hideBtnTooltip = () => {
    this.setState({
      btnTooltip: false,
    });
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { isDropdownOpen, btnTooltip, listTooltip, loading } = this.state;
    const {
      actions,
      shouldNotUpdate,
      processStatus,
      disabled,
      className,
    } = this.props;
    const disabledDuringProcessing = processStatus === 'pending' || loading;

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
            handleClick={shouldNotUpdate ? null : this.handleClick}
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
 * @prop {func} dispatch
 * @prop {object} childView
 * @prop {string} windowType
 * @prop {string} [viewId]
 * @prop {string} [viewProfileId]
 * @prop {bool} [fetchOnInit]
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
  actions: PropTypes.array,
  openModal: PropTypes.func.isRequired,
  fetchedQuickActions: PropTypes.func.isRequired,
  deleteQuickActions: PropTypes.func.isRequired,

  // from <DocumentList>
  childView: PropTypes.object.isRequired,
  parentView: PropTypes.object.isRequired,
  windowType: PropTypes.string.isRequired,
  viewId: PropTypes.string,
  viewProfileId: PropTypes.string,
  fetchOnInit: PropTypes.bool,
  inBackground: PropTypes.bool,
  inModal: PropTypes.bool,
  disabled: PropTypes.bool,
  stopShortcutPropagation: PropTypes.bool,
  processStatus: PropTypes.string,
  shouldNotUpdate: PropTypes.any,
  selected: PropTypes.any,
  className: PropTypes.string,
  onInvalidViewId: PropTypes.func,
};

const mapStateToProps = (state, { viewId, windowType }) => ({
  actions: getQuickactions(state, { viewId, windowType }),
});

export default connect(
  mapStateToProps,
  { openModal, fetchedQuickActions, deleteQuickActions },
  false,
  { forwardRef: true }
)(QuickActions);
