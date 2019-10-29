import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import Queue from 'simple-promise-queue';
import cx from 'classnames';

import { quickActionsRequest } from '../../api';
import { openModal } from '../../actions/WindowActions';
import keymap from '../../shortcuts/keymap';
import QuickActionsContextShortcuts from '../keyshortcuts/QuickActionsContextShortcuts';
import Tooltips from '../tooltips/Tooltips.js';
import QuickActionsDropdown from './QuickActionsDropdown';

const initialState = {
  actions: [],
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

  /**
   * @method componentDidMount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
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

  /**
   * @method componentWillUnmount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  componentWillUnmount = () => {
    this.mounted = false;
  };

  /**
   * @method UNSAFE_componentWillReceiveProps
   * @summary ToDo: Describe the method
   * @param {*} nextProps
   * @todo Write the documentation
   */
  UNSAFE_componentWillReceiveProps = nextProps => {
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

  /**
   * @method shouldComponentUpdate
   * @summary ToDo: Describe the method
   * @param {*} nextProps
   * @todo Write the documentation
   */
  shouldComponentUpdate(nextProps) {
    return nextProps.shouldNotUpdate !== true;
  }

  /**
   * @method componentDidUpdate
   * @summary ToDo: Describe the method
   * @param {*} prevProps
   * @todo Write the documentation
   */
  componentDidUpdate = prevProps => {
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
   * @method handleClick
   * @summary ToDo: Describe the method
   * @param {*} action
   * @todo Write the documentation
   */
  handleClick = action => {
    const { dispatch, viewId, selected, childView, parentView } = this.props;

    if (action.disabled) {
      return;
    }

    this.setState({
      loading: true,
    });

    dispatch(
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
      )
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
   * @todo Write the documentation
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
    if (!this.mounted) {
      return resolve();
    }

    if (windowId && viewId && childView && parentView) {
      await quickActionsRequest(
        windowId,
        viewId,
        viewProfileId,
        selected,
        childView,
        parentView
      )
        .then(response => {
          if (this.mounted) {
            return this.setState(
              {
                actions: response.data.actions,
                loading: false,
              },
              () => resolve()
            );
          }
        })
        .catch(() => {
          if (this.mounted) {
            return this.setState(
              {
                loading: false,
              },
              () => reject()
            );
          }
        });
    } else {
      if (this.mounted) {
        return this.setState(
          {
            loading: false,
          },
          () => resolve()
        );
      }
    }
  }

  /**
   * @method toggleDropdown
   * @summary ToDo: Describe the method
   * @param {*} option
   * @todo Write the documentation
   */
  toggleDropdown = option => {
    this.setState({
      isDropdownOpen: option,
    });
  };

  /**
   * @method toggleTooltip
   * @summary ToDo: Describe the method
   * @param {*} type
   * @param {*} visible
   * @todo Write the documentation
   */
  toggleTooltip = (type, visible) => {
    this.setState({
      [type]: visible,
    });
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const {
      actions,
      isDropdownOpen,
      btnTooltip,
      listTooltip,
      loading,
    } = this.state;
    const { shouldNotUpdate, processStatus, disabled, className } = this.props;
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
              onMouseEnter={() => this.toggleTooltip('listTooltip', true)}
              onMouseLeave={() => this.toggleTooltip('listTooltip', false)}
              onClick={e => {
                e.preventDefault();

                this.handleClick(actions[0]);
              }}
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
              className={
                'btn-meta-outline-secondary btn-icon-sm ' +
                'btn-inline btn-icon pointer tooltip-parent ' +
                (isDropdownOpen || disabledDuringProcessing
                  ? 'btn-disabled '
                  : '')
              }
              onMouseEnter={() => this.toggleTooltip('btnTooltip', true)}
              onMouseLeave={() => this.toggleTooltip('btnTooltip', false)}
              onClick={() => {
                this.toggleDropdown(!isDropdownOpen);
              }}
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
                handleClickOutside={() => this.toggleDropdown(false)}
                disableOnClickOutside={!isDropdownOpen}
              />
            )}
          </div>
          <QuickActionsContextShortcuts
            handleClick={() =>
              shouldNotUpdate ? null : this.handleClick(actions[0])
            }
            stopPropagation={this.props.stopShortcutPropagation}
            onClick={() => this.toggleDropdown(!isDropdownOpen)}
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
 * @prop {string} [selected]
 * @todo Check title, buttons. Which proptype? Required or optional?
 */
QuickActions.propTypes = {
  // from @connect
  dispatch: PropTypes.func.isRequired,

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
};

export default connect(
  false,
  false,
  false,
  { withRef: true }
)(QuickActions);
