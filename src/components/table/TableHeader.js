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
    renderSorting = (field) => {
        const {sort} = this.props;
        console.log('sorting rendered ' + field);
        return (
            <div className="sort-menu">
                <span className="sort" onClick={() => sort(true, field)}>asc</span>
                <span onClick={() => sort(false, field)}>desc</span>
            </div>
            
        )
    }
    renderCols = (cols, mainTable) => {
        return cols && cols.map((item, index) =>
            <th
                key={index}
                className={
                    this.getSizeClass(item.widgetType)
                }
            >
                {item.caption}
                {mainTable ? this.renderSorting(item.fields[0].field) : ''}
            </th>
        );
    }
    render() {
        const {cols, mainTable} = this.props;
        console.log('cols');
        console.log(cols);
        return (
            <tr>
                {this.renderCols(cols, mainTable)}
            </tr>
        )
    }
}

export default TableHeader
