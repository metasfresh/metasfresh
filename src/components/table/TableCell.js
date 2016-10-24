import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import Moment from 'moment';
import MasterWidget from '../MasterWidget';


class TableCell extends Component {
    constructor(props) {
        super(props);
    }

    componentWillReceiveProps(nextProps) {
        const {widgetData, updateRow} = this.props;
        if(widgetData[0].value !== nextProps.widgetData[0].value) {
            updateRow();
        }
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
                        let date = Moment(d).format('DD.MM.YYYY')
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
        const {isEdited, widgetData, item, docId, type, rowId, tabId, onDoubleClick, onKeyDown, readonly, updatedRow} = this.props;
        return (
            <td
                tabIndex="0"
                ref={(c) => this.cell = c}
                onDoubleClick={!readonly && onDoubleClick}
                onKeyDown={onKeyDown}
                className={
                    (item.gridAlign ? "text-xs-" + item.gridAlign + " " : "") +
                    (widgetData[0].readonly ? "cell-disabled " : "") +
                    (widgetData[0].mandatory ? "cell-mandatory " : "") +
                    (item.widgetType==="Lookup" || item.widgetType==="LongText" || item.widgetType==="List" || item.widgetType==="Date" || item.widgetType==="DateTime" || item.widgetType==="Time" ? "td-lg " : "") +
                    (item.widgetType==="ProductAttributes" ? "td-md " : "") +
                    (item.widgetType==="Text" ? "td-md " : "") +
                    (item.widgetType) +
                    ((updatedRow) ? " pulse-on" : " pulse-off")
                }
            >
                {
                    isEdited ?
                        <MasterWidget
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
