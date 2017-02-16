import React, { Component } from 'react';

class Indicator extends Component {
    constructor(props){
        super(props);
    }
    renderIndicator = (state) => {
        switch(state){
            case 'saved':
                return 'indicator-success';
            case 'pending':
                return 'indicator-pending';
            case 'error':
                return 'indicator-error';
        }
    }
    render() {
        const {indicator} = this.props;
        return (
            <div>
                <div className={'indicator-bar indicator-' + indicator} />
            </div>
        )
    }
}

export default Indicator
