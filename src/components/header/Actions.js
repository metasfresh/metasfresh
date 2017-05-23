import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import Loader from '../app/Loader';

import {
    actionsRequest,
} from '../../actions/GenericActions';

class Actions extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: null
        }
    }

    componentDidMount = () => {
        const {
            dispatch, windowType, entity, docId, rowId, notfound
        } = this.props;

        if(!windowType || docId === 'notfound' || notfound){
            return;
        }

        dispatch(actionsRequest(
            entity, windowType, docId, rowId
        )).then((response) => {
            this.setState({
                data: response.data.actions
            });
        });
    }

    renderData = () => {
        const {closeSubheader, openModal} = this.props;
        const {data} = this.state;

        return (data && data.length) ? data.map((item, key) =>
            <div
                className="subheader-item js-subheader-item"
                onClick={() => {
                    openModal(
                        item.processId + '', 'process', item.caption
                    );
                    closeSubheader()
                }}
                key={key}
                tabIndex={0}
            >
                {item.caption}
            </div>
        ) : <div className="subheader-item subheader-item-disabled">
            There is no actions
        </div>
    }

    render() {
        const {data} = this.state;
        return (
            <div
                className="subheader-column js-subheader-column"
                tabIndex={0}
            >
                <div className="subheader-header">Actions</div>
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
