import React, { Component } from 'react';

class TableHeader extends Component {
    constructor(props) {
        super(props);
    }
    getSizeClass = (widgetType) => {
        const lg = ['List', 'Lookup', 'LongText'];
        const md = ['DateTime', 'Date', 'Time', 'Text'];

        if(lg.indexOf(widgetType) > -1){
            return 'th-lg';
        }else if(md.indexOf(widgetType) > -1){
            return 'th-md';
        }else {
            return 'th-sm';
        }
    }
    renderCols = (cols) => {
        return cols && cols.map((item, index) =>
            <th
                key={index}
                className={
                    this.getSizeClass(item.widgetType)
                }
            >
                {item.caption}
            </th>
        );
    }
    render() {
        const {cols} = this.props;
        return (
            <tr>
                {this.renderCols(cols)}
            </tr>
        )
    }
}

export default TableHeader
