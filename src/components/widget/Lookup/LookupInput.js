import React, { Component } from 'react';

class LookupInput extends Component {
    constructor(props) {
        super(props);

        const {properties} = this.props;
    }


    render() {

        return (
            <div>
                <input type="text" className="input-field js-input-field font-weight-semibold" value="Test"/>
            </div>
        )
    }
}

export default LookupInput
