import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import {
    initLayout,
    getData,
    patchRequest
} from '../../actions/GenericActions';

import {
    findRowByPropName
} from '../../actions/WindowActions';

import RawWidget from '../widget/RawWidget';

class SelectionAttributes extends Component {
    constructor(props){
        super(props);

        this.state = {
            layout: [],
            data: [],
            dataId: null
        }
    }

    componentDidUpdate = (prevProps) => {
        const {selected} = this.props;

        if(
            JSON.stringify(prevProps.selected) !== JSON.stringify(selected)
        ){
            this.setState(Object.assign({}, this.state, {
                layout: [],
                data: [],
                dataId: null
            }), () => {
                if(selected.length === 1){
                    this.fetchActions();
                }
            })
        }
    }

    fetchActions = () => {
        const {dispatch, windowType, viewId, selected, entity} = this.props;

        dispatch(initLayout(entity, windowType, selected[0], null, viewId)
            ).then(response => {
                this.setState(Object.assign({}, this.state, {
                    layout: response.data.elements
                }));

                return dispatch(getData(entity, windowType, viewId, selected[0]));
            }).then(response => {
                this.setState(Object.assign({}, this.state, {
                    data: response.data.fields,
                    dataId: response.data.id
                }));
            });
    }

    // TODO TO UNIFY
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

    handlePatch = (prop, value) => {
        const {dispatch, entity, windowType, viewId} = this.props;
        const {dataId} = this.state;

        dispatch(patchRequest(
            entity, windowType, dataId, null, null, prop, value, null, null, null, viewId
        )).then(response => {
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

    render() {
        const {
            windowType, viewId
        } = this.props;

        const {
            layout, data, dataId
        } = this.state;

        return (
            <div
                className="attributes-selector table-flex-wrapper"
            >
                <div className="attributes-selector-header">
                    Selection attributes
                </div>
                <div className="attributes-selector-body">
                    {layout && layout.map((item, id) =>
                        <RawWidget
                            entity='documentView'
                            widgetType={item.widgetType}
                            fields={item.fields}
                            dataId={dataId}
                            windowType={windowType}
                            viewId={viewId}
                            widgetData={item.fields.map(elem => findRowByPropName(data, elem.field))}
                            gridAlign={item.gridAlign}
                            key={id}
                            type={item.type}
                            caption={item.caption}
                            handlePatch={this.handlePatch}
                            handleChange={this.handleChange}
                        />
                    )}
                </div>
            </div>
        );
    }
}

SelectionAttributes.propTypes = {
    dispatch: PropTypes.func.isRequired
};

SelectionAttributes = connect()(SelectionAttributes)

export default SelectionAttributes;
