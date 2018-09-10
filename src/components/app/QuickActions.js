import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

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

class QuickActions extends Component {
  static propTypes = {
    // from @connect
    dispatch: PropTypes.func.isRequired,

    // from <DocumentList>
    childView: PropTypes.object.isRequired,
    parentView: PropTypes.object.isRequired,
    windowType: PropTypes.string.isRequired,
    viewId: PropTypes.string,
    fetchOnInit: PropTypes.bool,
    inBackground: PropTypes.bool,
    inModal: PropTypes.bool,
    disabled: PropTypes.bool,
    stopShortcutPropagation: PropTypes.bool,

    processStatus: PropTypes.string,
  };

  unmounted = false;

  constructor(props) {
    super(props);

    const {
      fetchOnInit,
      selected,
      windowType,
      viewId,
      childView,
      parentView,
    } = props;
    this.state = initialState;

    if (fetchOnInit) {
      this.fetchActions(windowType, viewId, selected, childView, parentView);
    }
  }

  componentWillUnmount = () => {
    this.unmounted = true;
  };

  componentWillReceiveProps = nextProps => {
    const { selected, viewId, windowType } = this.props;

    if (
      (nextProps.selected &&
        JSON.stringify(nextProps.selected) !== JSON.stringify(selected)) ||
      (nextProps.viewId && nextProps.viewId !== viewId) ||
      (nextProps.windowType && nextProps.windowType !== windowType)
    ) {
      this.fetchActions(
        nextProps.windowType,
        nextProps.viewId,
        nextProps.selected,
        nextProps.childView,
        nextProps.parentView
      );
    }
  };

  shouldComponentUpdate(nextProps) {
    return nextProps.shouldNotUpdate !== true;
  }

  updateActions = (childSelection = this.props.childView.viewSelectedIds) => {
    const { windowType, viewId, selected, childView, parentView } = this.props;
    this.fetchActions(
      windowType,
      viewId,
      selected,
      { ...childView, viewSelectedIds: childSelection },
      parentView
    );
  };

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

  handleClickOutside = () => {
    this.toggleDropdown();
  };

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

  fetchActions = (windowType, viewId, selected, childView, parentView) => {
    if (this.unmounted) {
      return;
    }

    if (windowType && viewId && selected && childView && parentView) {
      quickActionsRequest(windowType, viewId, selected, childView, parentView)
        .then(response => {
          this.setState({
            actions: response.data.actions,
            loading: false,
          });
        })
        .catch(() => {
          this.setState({
            loading: false,
          });
        });
    } else {
      this.setState({
        loading: false,
      });
    }
  };

  toggleDropdown = option => {
    this.setState({
      isDropdownOpen: option,
    });
  };

  toggleTooltip = (type, visible) => {
    this.setState({
      [type]: visible,
    });
  };

  render() {
    const {
      actions,
      isDropdownOpen,
      btnTooltip,
      listTooltip,
      loading,
    } = this.state;
    const { shouldNotUpdate, processStatus, disabled } = this.props;
    const disabledDuringProcessing = processStatus === 'pending' || loading;

    if (actions.length) {
      return (
        <div className={'js-not-unselect ' + (disabled ? 'disabled ' : '')}>
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
      return false;
    }
  }
}

export default connect(false, false, false, { withRef: true })(QuickActions);
