import React, { Component } from 'react';
import {connect} from 'react-redux';
import PropTypes from 'prop-types';
import {push} from 'react-router-redux';

import Loader from '../app/Loader';

import {
    referencesRequest
} from '../../actions/GenericActions';

import {
    setFilter
} from '../../actions/ListActions';

class Referenced extends Component {
    constructor(props) {
        super(props);

        this.state = {
            data: null
        }
    }

    componentDidMount = () => {
        const {dispatch, windowType, docId} = this.props;

        dispatch(
            referencesRequest('window', windowType, docId)
        ).then(response => {
            this.setState({
                data: response.data.references
            })
        });
    }

    handleReferenceClick = (type, filter) => {
        const {
            dispatch, windowType, docId
        } = this.props;
        dispatch(setFilter(filter, type));
        dispatch(push(
            '/window/' + type +
            '?refType=' + windowType +
            '&refId=' + docId
        ));
    }

    renderData = () => {
        const {data} = this.state;

        return (data && data.length) ?
            data.map((item, key) =>
                <div
                    className="subheader-item js-subheader-item"
                    onClick={() => {
                        this.handleReferenceClick(
                            item.documentType, item.filter
                        )
                    }}
                    key={key}
                    tabIndex={0}
                >
                    {item.caption}
                </div>
        ) : <div className="subheader-item subheader-item-disabled">
            There is no referenced document
        </div>
    }

    render() {
        const {data} = this.state;
        return (
            <div>
                {!data ?
                    <Loader /> :
                    this.renderData()
                }
            </div>
        );
    }
}

Referenced.propTypes = {
    windowType: PropTypes.number.isRequired,
    docId: PropTypes.string.isRequired,
    dispatch: PropTypes.func.isRequired
}

Referenced = connect()(Referenced);

export default Referenced
