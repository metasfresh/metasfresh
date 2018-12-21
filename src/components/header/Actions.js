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
    plugins: PropTypes.any,
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
    const requests = [this.requestActions()];

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

  getPluginsActions = () => {
    const { plugins } = this.props;
    const actions = [];

    if (plugins.length) {
      plugins.forEach(plugin => {
        if (plugin.headerActions && plugin.headerActions.length) {
          actions.push(...plugin.headerActions);
        }
      });
    }

    return actions;
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
        id={`headerAction_${item.internalName}`}
        className={
          'subheader-item js-subheader-item' +
          (item.disabled ? ' subheader-item-disabled' : '')
        }
        onClick={handleClick}
      >
        {item.caption}
        {item.disabled && item.disabledReason && (
          <p className="one-line">
            <small>({item.disabledReason})</small>
          </p>
        )}
      </div>
    );
  };

  renderPluginAction = identifier => (item, key) => {
    const { closeSubheader, dispatch } = this.props;
    let handleClick = null;

    if (!item.disabled) {
      const handleModal = item.clickHandler;

      handleClick = () => {
        // we're passing dispatch, so that we don't have to hack around
        // the header actions trying to connect them to store
        handleModal(dispatch);

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
        {item.disabled && item.disabledReason && (
          <p className="one-line">
            <small>({item.disabledReason})</small>
          </p>
        )}
      </div>
    );
  };

  renderData = () => {
    const { renderAction, renderPluginAction, getPluginsActions } = this;
    const { actions, rowActions } = this.state;
    const numActions = actions ? actions.length : 0;
    const numRowActions = rowActions ? rowActions.length : 0;
    const separator = <hr key="separator" tabIndex={0} />;
    let retItems = [];

    if (numActions > 0 && numRowActions > 0) {
      retItems = [
        ...actions.map(renderAction('actions')),
        separator,
        ...rowActions.map(renderAction('rowActions')),
      ];
    } else if (numActions > 0) {
      retItems = actions.map(renderAction('actions'));
    } else if (numRowActions > 0) {
      retItems = rowActions.map(renderAction('rowActions'));
    }

    if (retItems.length) {
      const pluginActions = getPluginsActions();

      if (pluginActions.length) {
        retItems.push(
          separator,
          ...pluginActions.map(renderPluginAction('plugins'))
        );
      }

      return retItems;
    }

    return (
      <div className="subheader-item subheader-item-disabled">
        {counterpart.translate('window.actions.emptyText')}
      </div>
    );
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

const mapStateToProps = (state, props) => {
  const includedView = state.listHandler.includedView;

  const result = {
    selected: getSelection({
      state,
      windowType: props.windowType,
      viewId: props.viewId,
    }),
    plugins: state.pluginsHandler.files,
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

export default connect(mapStateToProps)(Actions);
