import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import RawWidget from '../RawWidget';

import {
    getPattributeLayout,
    getPattributeInstance,
    pattributeComplete
} from '../../../actions/AppActions';

import {
    findRowByPropName,
    patchRequest
} from '../../../actions/WindowActions'

class ProductAttributesDropdown extends Component {
    constructor(props) {
        super(props);

        this.state = {
            layout: null,
            data: null
        }
    }

    componentDidMount() {
        const {dispatch, tmpId} = this.props;

        dispatch(
            getPattributeInstance(tmpId)
        ).then(response => {
            const {id, fields} = response.data;

            this.setState(Object.assign({}, this.state, {
                data: fields
            }));

            return dispatch(getPattributeLayout(id));
        }).then(response => {
            const {elements} = response.data;

            this.setState(Object.assign({}, this.state, {
                layout: elements
            }));
        });
    }

    handleClickOutside = () => {
        const {toggle, dispatch, patch} = this.props;
        const {data} = this.state;
        const dataId = findRowByPropName(data, "ID").value;

        dispatch(pattributeComplete(dataId)).then(response => {
            patch(response.data);
        });
        toggle(false);
    }

    handlePatch = (prop, value, id) => {
        const {dispatch} = this.props;

        dispatch(patchRequest(null, id, null, null, prop, value, "asi")).then(response => {
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

    renderFields = (layout, data, dataId) => {
        if(layout){
            return layout.map((item, id) => {
                const widgetData = item.fields.map(elem => findRowByPropName(data, elem.field));

                return (<RawWidget
                    entity={'asi'}
                    widgetType={item.widgetType}
                    fields={item.fields}
                    dataId={dataId}
                    widgetData={widgetData}
                    gridAlign={item.gridAlign}
                    key={id}
                    type={item.type}
                    caption={item.caption}
                    handlePatch={(prop, value) => this.handlePatch(prop, value, dataId)}
                />)
            })
        }
    }

    render() {
        const {data,layout} = this.state;
        const dataId = findRowByPropName(data, "ID").value;

        return (
            <div className="product-attributes-dropdown panel-shadowed panel-primary panel-bordered panel-spaced">
                {this.renderFields(layout, data, dataId)}
            </div>
        )
    }
}

ProductAttributesDropdown.propTypes = {
    dispatch: PropTypes.func.isRequired
};

ProductAttributesDropdown = connect()(onClickOutside(ProductAttributesDropdown))

export default ProductAttributesDropdown
