import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import RawWidget from '../RawWidget';

import {
    findRowByPropName
} from '../../../actions/WindowActions'

class AttributesDropdown extends Component {
    constructor(props) {
        super(props);

        this.state = {
            shouldPropagateClickOutside: false,
            focused: false
        }
    }

    componentWillReceiveProps = (props) => {
        const {shouldPropagateClickOutside} = this.state;

        if(shouldPropagateClickOutside){
            const {onClickOutside} = this.props;

            onClickOutside();
        }
    }

    handleClickOutside = () => {
        const {onClickOutside} = this.props;
        const {focused} = this.state;

        //we need to blur all fields, to patch them before completion
        this.dropdown.focus();

        //we need to wait for fetching all of PATCH fields on blur
        //to complete on updated instance
        if(focused){
            this.setState(Object.assign({}, this.state, {
                shouldPropagateClickOutside: true
            }))
        }else{
            onClickOutside();
        }
    }

    handleFocus = () => {
        this.setState(Object.assign({}, this.state, {
            focused: true
        }))
    }

    handleBlur = (prop, value, attrId) => {
        const {handlePatch} = this.props;

        handlePatch(prop, value, attrId, () => {
            this.setState(Object.assign({}, this.state, {
                focused: false
            }));
        });
    }

    renderFields = () => {
        const {
            tabIndex, layout, data, dataId, attributeType, handlePatch,
            handleChange, attrId
        } = this.props;

        if(layout){
            return layout.map((item, id) => {
                const widgetData = item.fields.map(elem => findRowByPropName(data, elem.field));
                return (<RawWidget
                    entity={attributeType}
                    widgetType={item.widgetType}
                    fields={item.fields}
                    dataId={dataId}
                    widgetData={widgetData}
                    gridAlign={item.gridAlign}
                    key={id}
                    type={item.type}
                    caption={item.caption}
                    handlePatch={(prop, value) => this.handleBlur(prop, value, attrId)}
                    handleFocus={this.handleFocus}
                    handleChange={handleChange}
                    tabIndex={tabIndex}
                />)
            })
        }
    }

    render() {
        return (
            <div
                className="attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced"
                ref={c => {this.dropdown = c; c && c.focus()}}
            >
                {this.renderFields()}
            </div>
        )
    }
}

AttributesDropdown.propTypes = {
    dispatch: PropTypes.func.isRequired
};

AttributesDropdown = connect()(onClickOutside(AttributesDropdown))

export default AttributesDropdown
