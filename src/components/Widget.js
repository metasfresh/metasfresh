import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import Datetime from 'react-datetime';
import LookupDropdown from '../components/app/LookupDropdown';
import Dropdown from '../components/app/Dropdown';

class Widget extends Component {
    constructor(props) {
        super(props);
    }
    renderWidget = (type) => {
        switch(type){
            case "Date":
                return (
                    <div className="input-icon-container input-secondary input-block">
                        <Datetime
                            timeFormat={false}
                            dateFormat={true}
                            locale="de"
                            inputProps={{placeholder: "(none)"}}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "DateTime":
                return (
                    <div className="input-icon-container input-secondary input-block">
                        <Datetime
                            timeFormat={true}
                            dateFormat={true}
                            locale="de"
                            inputProps={{placeholder: "(none)"}}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Time":
                return (
                    <div className="input-icon-container input-secondary input-block">
                        <Datetime
                            timeFormat={true}
                            dateFormat={false}
                            locale="de"
                            inputProps={{placeholder: "(none)"}}
                        />
                        <i className="meta-icon-calendar input-icon-right"></i>
                    </div>
                )
            case "Lookup":
                return (
                    <LookupDropdown
                        recent={[]}
                        properties={["C_BPartner_ID"]}
                    />
                )
            case "List":
                return (
                    <Dropdown
                        options={['1','2','3']}
                        defaultValue="(none)"
                        property="HandOver_Partner_ID"
                    />
                )
            case "Text":
                return (
                    <div className="input-primary">
                        <input type="text" className="input-field" />
                    </div>
                )
            case "LongText":
                return (
                    <div className="input-secondary input-block">
                        <textarea className="input-field" />
                    </div>
                )
            case "Integer":
                return (
                    <div className="input-primary">
                        <input type="number" className="input-field" min="0" step="1" />
                    </div>
                )
            case "Number":
                return (
                    <div className="input-primary">
                        <input type="number" className="input-field" />
                    </div>
                )
            case "Amount" :
                return (
                    <div className="input-primary">
                        <input type="number" className="input-field" min="0" step="1" />
                    </div>
                )
            case "Quantity":
                return (
                    <div className="input-primary">
                        <input type="number" className="input-field" min="0" step="1" />
                    </div>
                )
            case "CostPrice":
                return (
                    <div className="input-primary">
                        <input type="number" className="input-field" />
                    </div>
                )
            case "YesNo":
                return (
                    <label className="input-checkbox">
                        <input type="checkbox" />
                        <div className="input-checkbox-tick"/>
                    </label>
                )
            case "Switch":
                return (
                    <label className="input-switch">
                        <input type="checkbox" />
                        <div className="input-slider" />
                    </label>
                )
            case "Label":
                return (
                    <div className="tag tag-warning">Open</div>
                )
            case "Button":
                return (
                    <button className="btn btn-sm btn-meta-primary">Add</button>
                )
            default:
                return (
                    <div>{type}</div>
                )
        }
    }
    render() {
        const {caption, widgetType, description, field} = this.props;
        return (
            <div>
                <div key="title" className="panel-title">{caption}</div>
                {this.renderWidget(widgetType)}
            </div>
        )
    }
}

export default Widget
