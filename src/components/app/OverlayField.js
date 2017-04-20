import React, { Component } from 'react';
import MasterWidget from '../widget/MasterWidget';

import {
    findRowByPropName
} from '../../actions/WindowActions';

class OverlayField extends Component {
    constructor(props) {
        super(props);
    }

    handleKeyDown = (e) => {
        const {setFetchOnTrue, removeModal} = this.props;
        switch(e.key) {
            case 'Enter':
                document.activeElement.blur();
                setFetchOnTrue();
                break;
            case 'Escape':
                removeModal();
                break;
        }
    }

    renderElements = (layout, data, type) => {
        const {disabled} = this.props;
        const elements = layout.elements;
        return elements.map((elem, id) => {
            const widgetData = elem.fields.map(item =>
                findRowByPropName(data, item.field)
            );
            return (
                <MasterWidget
                    entity="process"
                    key={'element' + id}
                    windowType={type}
                    dataId={layout.pinstanceId}
                    widgetData={widgetData}
                    isModal={true}
                    disabled={disabled}
                    autoFocus={id === 0}
                    {...elem}
                />
            )
        })
    }

    render() {
        const {data, layout, type} = this.props;
        return (
            <div
                className="overlay-field js-not-unselect"
                onKeyDown={e => this.handleKeyDown(e)}
            >
            {
                layout && layout.elements &&
                this.renderElements(layout, data, type)
            }

            </div>
        )
    }
}

export default OverlayField
