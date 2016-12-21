import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import RawWidget from '../RawWidget';

import {
    getAttributesLayout,
    getAttributesInstance,
    attributesComplete
} from '../../../actions/AppActions';

import {
    findRowByPropName,
    patchRequest,
    parseToDisplay
} from '../../../actions/WindowActions'

class AttributesDropdown extends Component {
    constructor(props) {
        super(props);

        this.state = {
            layout: null,
            data: null
        }
    }

    componentDidMount() {
        const {
            dispatch, tmpId, docType, dataId, tabId, rowId, fieldName, attributeType
        } = this.props;

        dispatch(
            getAttributesInstance(
                attributeType, tmpId, docType, dataId, tabId, rowId, fieldName
            )
        ).then(response => {
            const {id, fields} = response.data;

            this.setState(Object.assign({}, this.state, {
                data: parseToDisplay(fields)
            }));

            return dispatch(getAttributesLayout(attributeType, id));
        }).then(response => {
            const {elements} = response.data;

            this.setState(Object.assign({}, this.state, {
                layout: elements
            }));
        });
    }

    handleClickOutside = () => {
        const {toggle, dispatch, patch, attributeType} = this.props;
        const {data} = this.state;
        const dataId = findRowByPropName(data, "ID").value;

        dispatch(attributesComplete(attributeType,dataId)).then(response => {
            patch(response.data);
        });
        toggle(false);
    }

    handlePatch = (prop, value, id) => {
        const {dispatch, attributeType, docType, tabId, rowId} = this.props;

        // (
        //     entity, docType, docId = "NEW", tabId, rowId, property, value, subentity,
        //     subentityId, isAdvanced
        // )

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
                }));
            })
        })
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

    renderFields = (layout, data, dataId, attributeType) => {
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
                    handlePatch={(prop, value) => this.handlePatch(prop, value, dataId)}
                    handleFocus={() => {}}
                    handleChange={this.handleChange}
                />)
            })
        }
    }

    render() {
        const {attributeType} = this.props;
        const {data,layout} = this.state;
        const dataId = findRowByPropName(data, "ID").value;

        return (
            <div className="attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced">
                {this.renderFields(layout, data, dataId, attributeType)}
            </div>
        )
    }
}

AttributesDropdown.propTypes = {
    dispatch: PropTypes.func.isRequired
};

AttributesDropdown = connect()(onClickOutside(AttributesDropdown))

export default AttributesDropdown
