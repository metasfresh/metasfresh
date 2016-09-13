import React, { Component } from 'react';
import Widget from '../Widget';
import onClickOutside from 'react-onclickoutside';

class TableCell extends Component {
    constructor(props) {
        super(props);
    }
    handleClickOutside = (e) => {
        const {onClickOutside} = this.props;

        //it is important to change focus before collapsing to
        //blur Widget field and patch data
        this.cell && this.cell.focus();

        onClickOutside(e);
    }

    fieldToString = (field, type) => {
        if(field === null){
            return "";
        }else{
            switch(typeof field){
                case "object":
                    return field[Object.keys(field)[0]];
                    break;
                case "boolean":
                    return field ? <i className="meta-icon-checkbox-1" /> : <i className="meta-icon-checkbox" />;
                    break;
                case "string":
                    if(type === "Date" || type === "DateTime" || type === "Time"){
                      let d = new Date(field);
                      let date = d.toLocaleDateString();
                      return date;
                    } else {
                      return field;
                    }
                    break;
                default:
                    return field;
            }
        }
    }

    render() {
        const {isEdited, widgetData, item, docId, type, rowId, tabId, onDoubleClick, onKeyDown} = this.props;
        return (
            <td
                tabIndex="0"
                ref={(c) => this.cell = c}
                onDoubleClick={onDoubleClick}
                onKeyDown={onKeyDown}
                className={
                    (item.gridAlign ? "text-xs-" + item.gridAlign + " " : "") +
                    (widgetData.readonly ? "cell-disabled " : "") +
                    (widgetData.mandatory ? "cell-mandatory " : "") +
                    (item.widgetType)
                }
            >
                {
                    isEdited ?
                        <Widget
                            {...item}
                            dataId={docId}
                            widgetData={widgetData}
                            windowType={type}
                            rowId={rowId}
                            tabId={tabId}
                            noLabel={true}
                            gridAlign={item.gridAlign}
                        />
                    :
                        this.fieldToString(widgetData[0].value, item.widgetType)
                }
            </td>
        )
    }
}

export default onClickOutside(TableCell)
