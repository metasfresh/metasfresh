import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import {
  actionsRequest,
  rowActionsRequest,
} from '../../actions/GenericActions';
import Loader from '../app/Loader';
import { getSelection } from '../../reducers/windowHandler';

const mapStateToProps = (state, props) => {
  const includedView = state.listHandler.includedView;

  const result = {
    selected: getSelection({
      state,
      windowType: props.windowType,
      viewId: props.viewId,
    }),
  };

  if (includedView && includedView.viewId) {
    result.childViewId = includedView.viewId;
    result.childViewSelectedIds = getSelection({
      state,
      windowType: includedView.windowType,
      viewId: includedView.viewId,
    });
  }

  return result;
};

class Actions extends Component {
  static propTypes = {
    // from <SubHeader>
    windowType: PropTypes.string,
    viewId: PropTypes.string,

    // from @connect
    childViewId: PropTypes.string,
    childViewSelectedIds: PropTypes.array,
    dispatch: PropTypes.func.isRequired,
    selected: PropTypes.array.isRequired,
  };

  state = {
    actions: null,
    rowActions: null,
  };

  async componentDidMount() {
    const {
      windowType,
      entity,
      docId,
      notfound,
      activeTab,
      activeTabSelected,
    } = this.props;

    if (!windowType || docId === 'notfound' || notfound) {
      this.setState({
        actions: [],
      });

      return;
    }

    if (entity === 'board') {
      this.setState({
        actions: [],
      });

      return;
    }

    const requests = [this.requestActions()];

    if (activeTab && activeTabSelected && activeTabSelected.length > 0) {
      requests.push(this.requestRowActions());
    }

    const [actions, rowActions] = await Promise.all(requests);

    this.setState({
      actions,
      ...(rowActions && { rowActions }),
    });
  }

  requestActions = async () => {
    const {
      windowType,
      viewId,
      childViewId,
      childViewSelectedIds,
      entity,
      docId,
      rowId,
      activeTab,
      activeTabSelected,
    } = this.props;

    try {
      const request = {
        entity,
        type: windowType,
        viewId,
        childViewId,
        childViewSelectedIds,
        id: docId,
      };

      if (entity === 'documentView') {
        request.selectedIds = rowId;
      }

      if (activeTab && activeTabSelected && activeTabSelected.length > 0) {
        request.selectedTabId = activeTab;
        request.selectedRowIds = activeTabSelected;
      }

      const { actions } = (await actionsRequest(request)).data;

      return actions;
    } catch (error) {
      // eslint-disable-next-line no-console
      console.error(error);

      return [];
    }
  };

  requestRowActions = async () => {
    const { windowType, docId, activeTab, activeTabSelected } = this.props;

    try {
      const requests = activeTabSelected.map(async rowId => {
        const response = await rowActionsRequest({
          windowId: windowType,
          documentId: docId,
          tabId: activeTab,
          rowId,
        });

        const actions = response.data.actions.map(action => ({
          ...action,
          tabId: activeTab,
          rowId,
        }));

        return actions;
      });

      const actionsPerTab = await Promise.all(requests);

      const actions = Array.prototype.concat.call(...actionsPerTab);

      return actions;
    } catch (error) {
      // eslint-disable-next-line no-console
      console.error(error);

      return [];
    }
  };

  renderAction = identifier => (item, key) => {
    const {
      closeSubheader,
      openModalRow,
      openModal,
      selected,
      childViewId,
      childViewSelectedIds,
    } = this.props;

    let handleClick = null;

    if (!item.disabled) {
      let handleModal;

      if (item.tabId && item.rowId) {
        handleModal = () =>
          openModalRow(
            item.processId + '',
            'process',
            item.caption,
            item.tabId,
            item.rowId
          );
      } else {
        handleModal = () =>
          openModal(
            item.processId + '',
            'process',
            item.caption,
            false,
            selected,
            childViewId,
            childViewSelectedIds
          );
      }

      handleClick = () => {
        handleModal();

        closeSubheader();
      };
    }

    return (
      <div
        key={identifier + key}
        tabIndex={0}
        className={
          'subheader-item js-subheader-item' +
          (item.disabled ? ' subheader-item-disabled' : '')
        }
        onClick={handleClick}
      >
        {item.caption}
        {item.disabled &&
          item.disabledReason && (
            <p className="one-line">
              <small>({item.disabledReason})</small>
            </p>
          )}
      </div>
    );
  };

  renderData = () => {
    const { renderAction } = this;
    const { actions, rowActions } = this.state;

    const numActions = actions ? actions.length : 0;
    const numRowActions = rowActions ? rowActions.length : 0;

    if (numActions > 0 && numRowActions > 0) {
      const separator = <hr key="separator" tabIndex={0} />;

      return [
        ...actions.map(renderAction('actions')),
        separator,
        ...rowActions.map(renderAction('rowActions')),
      ];
    } else if (numActions > 0) {
      return actions.map(renderAction('actions'));
    } else if (numRowActions > 0) {
      return rowActions.map(renderAction('rowActions'));
    } else {
      return (
        <div className="subheader-item subheader-item-disabled">
          {counterpart.translate('window.actions.emptyText')}
        </div>
      );
    }
  };

  render() {
    const { actions } = this.state;

    return (
      <div className="subheader-column js-subheader-column" tabIndex={0}>
        <div className="subheader-header">
          {counterpart.translate('window.actions.caption')}
        </div>
        <div className="subheader-break" />
        {actions ? this.renderData() : <Loader />}
      </div>
    );
  }
}

export default connect(mapStateToProps)(Actions);
