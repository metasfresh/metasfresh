import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import AttributesDropdown from './AttributesDropdown';

class Attributes extends Component {
    constructor(props) {
        super(props);

        this.state = {
            dropdown: false,
            layout: null,
            data: null
        }
    }

    handleToggle = (option) => {
        const {handleBackdropLock} = this.props;
        this.setState(Object.assign({}, this.state, {
            dropdown: !!option
        }), () => {
            //Method is disabling outside click in parents
            //elements if there is some
            handleBackdropLock && handleBackdropLock(!!option);
        })
    }

    render() {
        const {
            widgetData,fields, dispatch, docType, dataId, tabId, rowId, patch,
            fieldName, attributeType, tabIndex
        } = this.props;
        const {dropdown} = this.state;
        const {value} = widgetData;
        const tmpId = Object.keys(value)[0];
        const label = value[tmpId];

        return (
            <div className={
                "attributes " +
                (rowId ? "attributes-in-table " : "")
            }>
                <div
                    onFocus={() => this.handleToggle(!dropdown)}
                    tabIndex={tabIndex}
                    className={
                        "tag tag-lg tag-block tag-secondary pointer " +
                        (dropdown ? "tag-disabled " : "")
                    }
                >
                    {label ? label : "Edit attributes"}
                </div>
                {dropdown &&
                    <AttributesDropdown
                        attributeType={attributeType}
                        tmpId={tmpId}
                        toggle={this.handleToggle}
                        patch={patch}
                        docType={docType}
                        dataId={dataId}
                        rowId={rowId}
                        tabId={tabId}
                        fieldName={fieldName}
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
