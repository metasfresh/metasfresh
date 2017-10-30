import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import counterpart from 'counterpart';

import Loader from '../app/Loader';

import {
    actionsRequest
} from '../../actions/GenericActions';

class Actions extends Component {
    state = {
        data: null
    };

    async componentDidMount() {
        const {
            windowType,
            entity,
            docId,
            rowId,
            notfound
        } = this.props;

        if(!windowType || docId === 'notfound' || notfound){
            this.setState({
                data: []
            });

            return;
        }

        if (entity === 'board') {
            this.setState({
                data: []
            });

            return;
        }

        try {
            const { actions } = (await actionsRequest({
                entity,
                type: windowType,
                id: docId,
                selected: rowId
            })).data;

            this.setState({
                data: actions
            });
        } catch (error) {
            this.setState({
                data: []
            });
        }
    }

    renderData = () => {
        const { closeSubheader, openModal } = this.props;
        const { data } = this.state;

        return (data && data.length) ? data.map((item, key) => (
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
        )) : (
            <div className="subheader-item subheader-item-disabled">
                {counterpart.translate('window.actions.emptyText')}
            </div>
        );
    }

    render() {
        const {data} = this.state;
        return (
            <div
                className="subheader-column js-subheader-column"
                tabIndex={0}
            >
                <div className="subheader-header">
                    {counterpart.translate('window.actions.caption')}
                </div>
                <div className="subheader-break" />
                {!data ?
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
