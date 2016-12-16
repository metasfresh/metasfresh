import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import RawWidget from '../widget/RawWidget';

import {
    findRowByPropName,
    createInstance,
    getData,
    initLayout,
    parseToDisplay,
    patchRequest,
    completeRequest
} from '../../actions/WindowActions';

class TableQuickInput extends Component {
    constructor(props) {
        super(props);

        this.state = {
            layout: null,
            data: null,
            id: null
        }
    }

    componentDidMount() {
        this.initQuickInput();
    }

    initQuickInput = () => {
        const {dispatch, docType, docId, tabId} = this.props;
        const {layout} = this.state;

        this.setState(Object.assign({}, this.state, {
            data: null
        }), () => {
            dispatch(createInstance('window', docType, docId, tabId, 'quickInput')).then(instance => {
                this.setState(Object.assign({}, this.state, {
                    data: parseToDisplay(instance.data.fields),
                    id: instance.data.id
                }));
            }).catch(err => {

            });

            !layout && dispatch(initLayout('window', docType, tabId, 'quickInput', docId)).then(layout => {
                this.setState(Object.assign({}, this.state, {
                    layout: layout.data.elements
                }))
            });
        });
    }

    handleChange = (field, value) => {
        const {data} = this.state;

        this.setState(Object.assign({}, this.state, {
            data: data.map(item => {
                if(field === 'all' || item.field === field){
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
        const {dispatch, docType, docId, tabId} = this.props;
        const {id} = this.state;
        value && dispatch(patchRequest('window', docType, docId, tabId, null, prop, value, 'quickInput', id)).then(response => {
            response.data && response.data[0].fields.map(item => {
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

    renderFields = (layout, data, dataId, attributeType, quickInputId) => {
        const {tabId, docType} = this.props;
        if(layout){
            return layout.map((item, id) => {
                const widgetData = item.fields.map(elem => findRowByPropName(data, elem.field));
                return (<RawWidget
                    entity={attributeType}
                    subentity='quickInput'
                    subentityId={quickInputId}
                    tabId={tabId}
                    windowType={docType}
                    widgetType={item.widgetType}
                    fields={item.fields}
                    dataId={dataId}
                    widgetData={widgetData}
                    gridAlign={item.gridAlign}
                    key={id}
                    type={item.type}
                    caption={item.caption}
                    handlePatch={(prop, value) => this.handlePatch(prop,value)}
                    handleFocus={() => {}}
                    handleChange={this.handleChange}
                    type="primary"
                />)
            })
        }
    }

    onSubmit = (e) => {
        const {dispatch, docType, docId, tabId} = this.props;
        const {id} = this.state;
        e.preventDefault();

        document.activeElement.blur();

        dispatch(completeRequest('window', docType, docId, tabId, null, 'quickInput', id)).then(() => {
            this.initQuickInput();
        });
    }

    render() {
        const {
            docId
        } = this.props;

        const {
            data, layout, id
        } = this.state;

        return (
            <form onSubmit={this.onSubmit} className="quick-input-container">
                {this.renderFields(layout, data, docId, 'window', id)} <div className="hint">(Press 'Enter' to add)</div>
                <button type="submit" className="hidden-xs-up"></button>
            </form>
        )
    }
}

TableQuickInput.propTypes = {
    dispatch: PropTypes.func.isRequired
}

TableQuickInput = connect()(TableQuickInput);

export default TableQuickInput;
