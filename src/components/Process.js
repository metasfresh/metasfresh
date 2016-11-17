import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import MasterWidget from './MasterWidget';

import {
    findRowByPropName
} from '../actions/WindowActions';

export class Process extends Component {
    constructor(props){
        super(props);
    }

    renderElements = (layout, data, type) => {
        const elements = layout.elements;
        return elements.map((elem, id) => {
            const widgetData = elem.fields.map(item => findRowByPropName(data, item.field));
            return (
                <MasterWidget
                    entity="process"
                    key={'element' + id}
                    windowType={type}
                    dataId={layout.pinstanceId}
                    widgetData={widgetData}
                    isModal={true}
                    {...elem} />
            )
        })
    }

    render() {
        const {data, layout, windowType} = this.props;
        return (
            <div key="window" className="window-wrapper">
                {layout && layout.elements && this.renderElements(layout, data, windowType)}
            </div>
        );
    }
}
Process.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Process = connect()(Process);

export default Process
