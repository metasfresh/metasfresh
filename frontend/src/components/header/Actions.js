import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import cx from 'classnames';

import {
  actionsRequest,
  rowActionsRequest,
} from '../../actions/GenericActions';
import { getSelection, getTableId } from '../../reducers/tables';

import Loader from '../app/Loader';

/**
 * @file Class based component.
 * @module Actions
 * @extends Component
 */
class Actions extends Component {
  // TODO: Move to redux
  state = {
    actions: null,
    rowActions: null,
  };

  async componentDidMount() {
    const { windowType, entity, docId, notfound, activeTab, selected } =
      this.props;

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
    if (activeTab && selected.length) {
      requests.push(this.requestRowActions());
    }

    const [actions, rowActions] = await Promise.all(requests);

    this.setState({
      actions,
      ...(rowActions && { rowActions }),
    });
  }

  /**
   * @async
   * @method requestActions
   * @summary ToDo: Describe the method.
   */
  requestActions = async () => {
    const {
      windowType,
      viewId,
      childViewId,
      childViewSelectedIds,
      entity,
      docId,
      activeTab,
      selected,
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
        request.selectedIds = selected;
      }

      if (activeTab && selected.length > 0) {
        request.selectedTabId = activeTab;
        request.selectedRowIds = selected;
      }

      const { actions } = (await actionsRequest(request)).data;

      return actions;
    } catch (error) {
      // eslint-disable-next-line no-console
      console.error(error);

      return [];
    }
  };

  /**
   * @async
   * @method requestRowActions
   * @summary ToDo: Describe the method.
   */
  requestRowActions = async () => {
    const { windowType, docId, activeTab, selected } = this.props;

    try {
      const requests = selected.map(async (rowId) => {
        const response = await rowActionsRequest({
          windowId: windowType,
          documentId: docId,
          tabId: activeTab,
          rowId,
        });

        const actions = response.data.actions.map((action) => ({
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

  /**
   * @method getPluginsActions
   * @summary ToDo: Describe the method.
   */
  getPluginsActions = () => {
    const { plugins } = this.props;
    const actions = [];

    if (plugins.length) {
      plugins.forEach((plugin) => {
        if (plugin.headerActions && plugin.headerActions.length) {
          actions.push(...plugin.headerActions);
        }
      });
    }

    return actions;
  };

  /**
   * @method renderAction
   * @summary ToDo: Describe the method.
   * @param {*} identifier
   */
  renderAction = (identifier) => (item, key) => {
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
      <li
        key={identifier + key}
        tabIndex={0}
        id={`headerAction_${item.internalName}`}
        className={cx('subheader-item js-subheader-item', {
          'subheader-item-disabled': item.disabled,
        })}
        onClick={handleClick}
      >
        {item.caption}
        {item.disabled && item.disabledReason && (
          <p className="one-line">
            <small>({item.disabledReason})</small>
          </p>
        )}
      </li>
    );
  };

  /**
   * @method renderPluginAction
   * @summary ToDo: Describe the method.
   * @param {*} identifier
   */
  renderPluginAction = (identifier) => (item, key) => {
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
      <li
        key={identifier + key}
        tabIndex={0}
        className={cx('subheader-item js-subheader-item', {
          'subheader-item-disabled': item.disabled,
        })}
        onClick={handleClick}
      >
        {item.caption}
        {item.disabled && item.disabledReason && (
          <p className="one-line">
            <small>({item.disabledReason})</small>
          </p>
        )}
      </li>
    );
  };

  /**
   * @method renderData
   * @summary ToDo: Describe the method.
   */
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
      <li className="subheader-item subheader-item-disabled">
        {counterpart.translate('window.actions.emptyText')}
      </li>
    );
  };

  /**
   * @method render
   * @summary ToDo: Describe the method.
   */
  render() {
    const { actions } = this.state;

    return (
      <div className="subheader-column js-subheader-column" tabIndex={0}>
        <div className="subheader-header">
          {counterpart.translate('window.actions.caption')}
        </div>
        <div className="subheader-break" />
        <ul className="subheader-items">
          {actions ? this.renderData() : <Loader />}
        </ul>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {string} [windowType]
 * @prop {string} [viewId]
 * @prop {string} [childViewId]
 * @prop {array} [childViewSelectedIds]
 * @prop {func} dispatch
 * @prop {array} selected
 * @prop {*} [plugins]
 */
Actions.propTypes = {
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

const mapStateToProps = (state) => {
  const { includedView } = state.viewHandler;
  const result = {
    plugins: state.pluginsHandler.files,
  };

  if (includedView && includedView.viewId) {
    const childViewTableId = getTableId({
      windowId: includedView.windowId,
      viewId: includedView.viewId,
    });
    const childSelector = getSelection();

    result.childViewId = includedView.viewId;
    result.childViewSelectedIds = childSelector(state, childViewTableId);
  }

  return result;
};

Actions.propTypes = {
  entity: PropTypes.any,
  docId: PropTypes.any,
  notfound: PropTypes.bool,
  activeTab: PropTypes.any,
  closeSubheader: PropTypes.func,
  openModal: PropTypes.func,
  openModalRow: PropTypes.func,
};

export default connect(mapStateToProps)(Actions);
