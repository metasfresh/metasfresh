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
            <div
                className={this.props.className}
                onFocus={this.handleFocus}
            >
                {this.props.selected.map(item => {
                    const [key, value] = Object.entries(item)[0];

                    return (
                        <span
                            key={key}
                            className="labels-label"
                        >
                            {value}
                        </span>
                    );
                })}
            </div>
        );
    }
}

export default connect(state => ({
    docId: state.windowHandler.master.docId,
    windowId: state.windowHandler.master.layout.windowId
}))(Labels);
