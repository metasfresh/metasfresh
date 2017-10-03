import React, { Component } from 'react';
import { connect } from 'react-redux';
import { dropdownRequest } from '../../actions/GenericActions';

class Labels extends Component {
    state = {
        values: []
    };

    handleFocus = async () => {
        const { windowId, docId, name } = this.props;

        const response = await dropdownRequest({
            docId,
            entity: 'window',
            propertyName: name,
            viewId: windowId
        });

        const { values } = response.data;

        this.setState({ values });
    }

    render() {
        return (
            <div onFocus={this.handleFocus}>
                <input />
                {JSON.stringify(this.props.selected)}
                {this.props.name}
                {JSON.stringify(this.state.values)}
            </div>
        );
    }
}

export default connect(state => ({
    docId: state.windowHandler.master.docId,
    windowId: state.windowHandler.master.layout.windowId
}))(Labels);
