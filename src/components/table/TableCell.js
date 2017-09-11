import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import Moment from 'moment';
import numeral from 'numeral';
import MasterWidget from '../widget/MasterWidget';

class TableCell extends Component {
    constructor(props) {
        super(props);

        this.state = {
            backdropLock: false
        }
    }

    componentWillReceiveProps(nextProps) {
        const {widgetData, updateRow, readonly, rowId} = this.props;
        // We should avoid highlighting when whole row is exchanged (sorting)
        if(rowId !== nextProps.rowId){
            return;
        }

        if(!readonly &&
            JSON.stringify(widgetData[0].value) !==
            JSON.stringify(nextProps.widgetData[0].value)
        ) {
            updateRow();
        }
    }

    handleBackdropLock = (state) => {
        this.setState(Object.assign({}, this.state, {
            backdropLock: !!state
        }))
    }

    handleClickOutside = (e) => {
        const {onClickOutside} = this.props;
        const {backdropLock} = this.state;

        //We can handle click outside only if
        //nested elements has no click oustide listening pending
        if(!backdropLock){
            //it is important to change focus before collapsing to
            //blur Widget field and patch data
            this.cell && this.cell.focus();

            onClickOutside(e);
        }
    }

    getDateFormat = (type) => {
        switch(type) {
            case 'DateTime':
                return 'DD.MM.YYYY HH:mm:ss';
            case 'Date':
                return 'DD.MM.YYYY';
            case 'Time':
                return 'HH:mm:ss';
            default:
                return 'DD.MM.YYYY';
        }
    }

    createDate = (field, type) => {
        return field ?
            Moment(new Date(field)).format(this.getDateFormat(type)) : '';
    }

    createAmount = (field, type) => {
        let value = numeral(parseFloat(field)).format();
        return field ? value : '';
    }

    fieldToString = (field, type) => {
        if(field === null){
            return '';
        }else{
            switch(typeof field){
                case 'object':
                    if(
                        type === 'Date' ||
                        type === 'DateTime' ||
                        type === 'Time'
                    ){
                        return this.createDate(field, type);
                    } else {
                        return field[Object.keys(field)[0]];
                    }
                case 'boolean':
                    return field ?
                        <i className="meta-icon-checkbox-1" /> :
                        <i className="meta-icon-checkbox" />;
                case 'string':
                    if(
                        type === 'Date' ||
                        type === 'DateTime' ||
                        type === 'Time'
                    ){
                        return this.createDate(field, type);
                    } else if (
                        type === 'CostPrice' ||
                        type === 'Amount'
                    ) {
                        return this.createAmount(field, type);
                    } else {
                        return field;
                    }
                default:
                    return field;
            }
        }

    }

    render() {
        const {
            isEdited, widgetData, item, docId, type, rowId, tabId,
            onDoubleClick, onKeyDown, readonly, updatedRow, tabIndex, entity,
            listenOnKeys, listenOnKeysFalse, closeTableField, getSizeClass,
            handleRightClick
        } = this.props;

        return (
            <td
                tabIndex={tabIndex}
                ref={(c) => this.cell = c}
                onDoubleClick={!readonly && onDoubleClick}
                onKeyDown={onKeyDown}
                onContextMenu={handleRightClick}
                className={
                    (item.gridAlign ? 'text-xs-' + item.gridAlign + ' ' : '') +
                    (widgetData[0].readonly ? 'cell-disabled ' : '') +
                    (widgetData[0].mandatory ? 'cell-mandatory ' : '') +
                    (getSizeClass(item) + ' ') +
                    (item.widgetType) +
                    ((updatedRow) ? ' pulse-on' : ' pulse-off')
                }
            >
                {
                    isEdited ?
                        <MasterWidget
                            {...item}
                            entity={entity}
                            dataId={docId}
                            widgetData={widgetData}
                            windowType={type}
                            rowId={rowId}
                            tabId={tabId}
                            noLabel={true}
                            gridAlign={item.gridAlign}
                            handleBackdropLock={this.handleBackdropLock}
                            listenOnKeys={listenOnKeys}
                            listenOnKeysFalse={listenOnKeysFalse}
                            closeTableField={closeTableField}
                        />
                    :
                       <div className="cell-text-wrapper">
                           {this.fieldToString(
                               widgetData[0].value, item.widgetType
                           )}
                       </div>
                }
            </td>
        )
    }
}

export default onClickOutside(TableCell)
