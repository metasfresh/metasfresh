import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import AttributesDropdown from './AttributesDropdown';

import {
    getAttributesInstance
} from '../../../actions/AppActions';

import {
    parseToDisplay
} from '../../../actions/WindowActions';

import {
    patchRequest,
    completeRequest,
    initLayout
} from '../../../actions/GenericActions';

class Attributes extends Component {
    constructor(props) {
        super(props);

        this.state = {
            dropdown: false,
            layout: null,
            data: null
        }
    }

    handleChange = (field, value) => {
        this.setState(prevState => ({
            data: Object.assign({}, prevState.data, {
                [field]: Object.assign({}, prevState.data[field], {value})
            })
        }))
    }

    handlePatch = (prop, value, id, cb) => {
        const {attributeType} = this.props;

        patchRequest({
            entity: attributeType,
            docType: null,
            docId: id,
            property: prop,
            value
        }).then(response => {
            const fields = response.data[0].fieldsByName;
            Object.keys(fields).map(fieldName => {
                this.setState(prevState => ({
                    data: Object.assign({}, prevState.data, {
                        [fieldName]: Object.assign(
                            {}, prevState.data[fieldName], {value}
                        )
                    })
                }), () => cb && cb());
            })
        });
    }

    handleInit = () => {
        const {
            docType, dataId, tabId, rowId, fieldName, attributeType,
            widgetData, entity
        } = this.props;
        const tmpId = Object.keys(widgetData.value)[0];

        getAttributesInstance(
            attributeType, tmpId, docType, dataId, tabId, rowId, fieldName,
            entity
        ).then(response => {
            const {id, fieldsByName} = response.data;

            this.setState({
                data: parseToDisplay(fieldsByName)
            });

            return initLayout(attributeType, id);
        }).then(response => {
            const {elements} = response.data;

            this.setState({
                layout: elements
            });
        }).then(() => {
            this.setState({
                dropdown: true
            });
        });
    }

    handleToggle = (option) => {
        const {handleBackdropLock} = this.props;

        this.setState({
            data: null,
            layout: null,
            dropdown: null
        }, () => {
            //Method is disabling outside click in parents
            //elements if there is some
            handleBackdropLock && handleBackdropLock(!!option);

            if(option){
                this.handleInit();
            }
        })
    }

    handleCompletion = () => {
        const {attributeType, patch} = this.props;
        const {data} = this.state;
        const attrId = data && data.ID ? data.ID.value : -1;

        const mandatory = Object.keys(data).filter(fieldName =>
            data[fieldName].mandatory);
        const valid = !mandatory.filter(field => !data[field].value).length;

        //there are required values that are not set. just close
        if (mandatory.length && !valid){
            if(window.confirm('Do you really want to leave?')){
                this.handleToggle(false);
            }
            return;
        }

        completeRequest(attributeType, attrId).then(response => {
            patch(response.data);
            this.handleToggle(false);
        });
    }

    handleKeyDown = (e) => {
        switch(e.key){
            case 'Escape':
                e.preventDefault();
                this.handleCompletion();
                break;
        }
    }

    render() {
        const {
            widgetData, dataId, rowId, attributeType, tabIndex, readonly
        } = this.props;

        const {
            dropdown, data, layout
        } = this.state;

        const {value} = widgetData;
        const tmpId = Object.keys(value)[0];
        const label = value[tmpId];
        const attrId = data && data.ID ? data.ID.value : -1;

        return (
            <div
                onKeyDown={this.handleKeyDown}
                className={
                    'attributes ' +
                    (rowId ? 'attributes-in-table ' : '')
                }
            >
                <button
                    tabIndex={tabIndex}
                    onClick={() => this.handleToggle(true)}
                    className={
                        'btn btn-block tag tag-lg tag-block tag-secondary ' +
                        'pointer ' +
                        (dropdown ? 'tag-disabled ' : '') +
                        (readonly ? 'tag-disabled disabled ' : '')
                    }
                >
                    {label ? label : 'Edit'}
                </button>
                {dropdown &&
                    <AttributesDropdown
                        attributeType={attributeType}
                        dataId={dataId}
                        tabIndex={tabIndex}
                        onClickOutside={this.handleCompletion}
                        data={data}
                        layout={layout}
                        handlePatch={this.handlePatch}
                        handleChange={this.handleChange}
                        attrId={attrId}
                    />
                }
            </div>
        )
    }
}

Attributes.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Attributes = connect()(Attributes)

export default Attributes
