import React, { Component } from 'react';
import Widget from '../Widget';
import onClickOutside from 'react-onclickoutside';

import {fieldToString} from '../../actions/WindowActions';
class TableCell extends Component {
    constructor(props) {
        super(props);
    }
    handleClickOutside = (e) => {
        this.props.onClickOutside(e);
    }
    render() {
        const {isEdited, widgetData, item, docId, type, rowId, tabId, onDoubleClick} = this.props;
        return (
            <td
                tabIndex="0"
                onDoubleClick={onDoubleClick}
            >
                {
                    isEdited ?
                        <Widget
                            {...item}
                            dataId={docId}
                            widgetData={[widgetData]}
                            windowType={type}
                            rowId={rowId}
                            tabId={tabId}
                            noLabel={true}
                        />
                    :
                        fieldToString(widgetData.value)
                }
            </td>
        )
    }
}

export default onClickOutside(TableCell)
