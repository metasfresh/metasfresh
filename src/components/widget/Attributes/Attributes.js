import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import AttributesDropdown from './AttributesDropdown';

import {
    getAttributesInstance
} from '../../../actions/AppActions';

import {
    findRowByPropName,
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
        const {data} = this.state;

        this.setState(Object.assign({}, this.state, {
            data: data.map(item => {
                if(item.field === field){
                    return Object.assign({}, item, {
                        value: value
                    })
                }else{
                    return item;
                }
            })
        }))
    }

    handlePatch = (prop, value, id, cb) => {
        const {dispatch, attributeType, docType, tabId, rowId} = this.props;

        dispatch(patchRequest(attributeType, null, id, null, null, prop, value)).then(response => {
            response.data[0].fields.map(item => {
                this.setState(Object.assign({}, this.state, {
                    data: this.state.data.map(field => {
                        if(field.field === item.field){
                            return Object.assign({}, field, item);
                        }else{
                            return field;
                        }
                    })
                }), () => cb && cb());
            })
        })
    }

    handleInit = () => {
        const {
            dispatch, docType, dataId, tabId, rowId, fieldName, attributeType, widgetData
        } = this.props;
        const tmpId = Object.keys(widgetData.value)[0];

        dispatch(
            getAttributesInstance(
                attributeType, tmpId, docType, dataId, tabId, rowId, fieldName
            )
        ).then(response => {
            const {id, fields} = response.data;

            this.setState(Object.assign({}, this.state, {
                data: parseToDisplay(fields)
            }));

            return dispatch(initLayout(attributeType, id));
        }).then(response => {
            const {elements} = response.data;

            this.setState(Object.assign({}, this.state, {
                layout: elements
            }));
        }).then(() => {
            this.setState(Object.assign({}, this.state, {
                dropdown: true
            }));
        });
    }

    handleToggle = (option) => {
        const {handleBackdropLock} = this.props;

        this.setState(Object.assign({}, this.state, {
            data: null,
            layout: null,
            dropdown: null
        }), () => {
            //Method is disabling outside click in parents
            //elements if there is some
            handleBackdropLock && handleBackdropLock(!!option);

            if(!!option){
                this.handleInit();
            }
        })
    }

    handleCompletion = () => {
        const {attributeType, dispatch, patch} = this.props;
        const {data} = this.state;
        const attrId = findRowByPropName(data, "ID").value;

        dispatch(completeRequest(attributeType, attrId)).then(response => {
            patch(response.data);
            this.handleToggle(false);
        });
    }

    handleKeyDown = (e) => {
        switch(e.key){
            case "Escape":
                e.preventDefault();
                this.handleCompletion();
                break;
        }
    }

    render() {
        const {
            widgetData,fields, dispatch, docType, dataId, tabId, rowId, patch,
            fieldName, attributeType, tabIndex, readonly
        } = this.props;

        const {
            dropdown, data, layout
        } = this.state;

        const {value} = widgetData;
        const tmpId = Object.keys(value)[0];
        const label = value[tmpId];
        const attrId = findRowByPropName(data, "ID").value;

        return (
            <div
                onKeyDown={this.handleKeyDown}
                className={
                    "attributes " +
                    (rowId ? "attributes-in-table " : "")
                }
            >
                <button
                    tabIndex={tabIndex}
                    onClick={() => this.handleToggle(true)}
                    className={
                        "btn btn-block tag tag-lg tag-block tag-secondary pointer " +
                        (dropdown ? "tag-disabled " : "") +
                        (readonly ? "disabled " : "")
                    }
                >
                    {label ? label : "Edit attributes"}
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
