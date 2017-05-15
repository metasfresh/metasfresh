import React, { Component } from 'react';
import MasterWidget from '../widget/MasterWidget';
import RawWidget from '../widget/RawWidget';

import {
    findRowByPropName
} from '../../actions/WindowActions';

class OverlayField extends Component {
    constructor(props) {
        super(props);
    }

    handleKeyDown = (e) => {
        const {handleSubmit, closeOverlay, clearData} = this.props;
        switch(e.key) {
            case 'Enter':
                document.activeElement.blur();
                handleSubmit();
                break;
            case 'Escape':
                closeOverlay();
                break;
            case 'Delete':
                clearData && clearData();
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

    renderParameters = (layout) => {
        const {
            windowType, viewId, onShow, onHide, handlePatch, handleChange
        } = this.props;
        const parameters = layout.parameters;
        return parameters.map((item, index) => {
            return (
                <RawWidget
                    entity="documentView"
                    subentity="filter"
                    subentityId={layout.filterId}
                    handlePatch={handlePatch}
                    handleChange={handleChange}
                    widgetType={item.widgetType}
                    fields={[item]}
                    windowType={windowType}
                    type={item.type}
                    widgetData={[item]}
                    key={index}
                    id={index}
                    range={item.range}
                    onShow={onShow}
                    onHide={onHide}
                    caption={item.caption}
                    noLabel={false}
                    filterWidget={true}
                    viewId={viewId}
                    autoFocus={index === 0}
                />
            )
        })
    }

    render() {
        const {data, layout, type, filter} = this.props;

        return (
            <div
                className="overlay-field js-not-unselect"
                onKeyDown={e => this.handleKeyDown(e)}
            >
            {
                filter ?
                this.renderParameters(layout) :
                layout && layout.elements &&
                this.renderElements(layout, data, type)
            }

            </div>
        )
    }
}

export default OverlayField
