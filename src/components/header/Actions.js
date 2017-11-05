import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import counterpart from 'counterpart';

import Loader from '../app/Loader';

import {
    actionsRequest,
    rowActionsRequest
} from '../../actions/GenericActions';

class Actions extends Component {
    state = {
        actions: null,
        rowActions: null
    };

    async componentDidMount() {
        const {
            windowType,
            entity,
            docId,
            notfound,
            activeTab,
            activeTabSelected
        } = this.props;

        if (!windowType || docId === 'notfound' || notfound) {
            this.setState({
                actions: []
            });

            return;
        }

        if (entity === 'board') {
            this.setState({
                actions: []
            });

            return;
        }

        const requests = [this.requestActions()];

        if (
            activeTab &&
            activeTabSelected &&
            activeTabSelected.length > 0
        ) {
            requests.push(this.requestRowActions());
        }

        const [actions, rowActions] = await Promise.all(requests);

        this.setState({
            actions,
            ...(rowActions && { rowActions })
        });
    }

    requestActions = async () => {
        const {
            windowType,
            entity,
            docId,
            rowId,
            activeTab,
            activeTabSelected
        } = this.props;

        try {
            const request = {
                entity,
                type: windowType,
                id: docId
            };

            if (entity === 'documentView') {
                request.selectedIds = rowId;
            }

            if (
                activeTab &&
                activeTabSelected &&
                activeTabSelected.length > 0
            ) {
                request.selectedTabId = activeTab;
                request.selectedRowIds = activeTabSelected;
            }

            const { actions } = (await actionsRequest(request)).data;

            return actions;
        } catch (error) {
            console.error(error);

            return [];
        }
    };

    requestRowActions = async () => {
        const {
            windowType,
            docId,
            activeTab,
            activeTabSelected
        } = this.props;

        try {
            const requests = activeTabSelected.map(async rowId => {
                const response = (await rowActionsRequest({
                    windowId: windowType,
                    documentId: docId,
                    tabId: activeTab,
                    rowId
                }));

                const actions = response.data.actions.map(action => ({
                    ...action,
                    tabId: activeTab,
                    rowId
                }));

                return actions;
            });

            const actionsPerTab = await Promise.all(requests);

            const actions = Array.prototype.concat.call(...actionsPerTab);

            return actions;
        } catch (error) {
            console.error(error);

            return [];
        }
    };

    renderData = () => {
        const { closeSubheader, openModal } = this.props;
        const { actions } = this.state;

        if (actions && actions.length) {
            return actions.map((item, key) => (
                <div
                    key={key}
                    tabIndex={0}
                    className={'subheader-item js-subheader-item' + (
                        item.disabled ? ' subheader-item-disabled' : ''
                    )}
                    onClick={item.disabled ? null : () => {
                        openModal(item.processId + '', 'process', item.caption);

                        closeSubheader();
                    }}
                >
                    {item.caption}
                    {item.disabled && item.disabledReason && (
                        <p className="one-line">
                            <small>({item.disabledReason})</small>
                        </p>
                    )}
                </div>
            ));
        } else {
            return (
                <div className="subheader-item subheader-item-disabled">
                    {counterpart.translate('window.actions.emptyText')}
                </div>
            );
        }
    }

    render() {
        const { actions } = this.state;

        return (
            <div
                className="subheader-column js-subheader-column"
                tabIndex={0}
            >
                <div className="subheader-header">
                    {counterpart.translate('window.actions.caption')}
                </div>
                <div className="subheader-break" />
                {!actions ?
                    <Loader /> :
                    this.renderData()
                }
            </div>
        );
    }
}

Actions.propTypes = {
    windowType: PropTypes.string,
    dispatch: PropTypes.func.isRequired
}

Actions = connect()(Actions);

export default Actions;
