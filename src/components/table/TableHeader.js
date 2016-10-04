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

    //<span className={sorting.name && sorting.asc ? 'sort rotate-90' : '' + 'rotate-90'} onClick={() => sort(true, field, true)}><i className="meta-icon-chevron-1" /></span>
    //<span className={sorting.name && !sorting.asc ? 'sort' : ''} onClick={() => sort(false, field, true)}><i className="meta-icon-chevron-1" /></span>
    
    renderSorting = (field) => {
        const {sort,display, orderBy} = this.props;
        // console.log('ordered By ');
        // console.log(orderBy );
        let sorting = {};

        orderBy && orderBy.map((item, index) => {
            
            if(field == item.fieldName){
                sorting.name = item.fieldName;
                sorting.asc = item.ascending;
            }
        })

    
        return (
            <div className="sort-menu" onClick={() => sort(!sorting.asc, field, true)}>
                    <span className={sorting.name && sorting.asc ? 'sort rotate-90' : (sorting.name && !sorting.asc) ? 'sort' : ''}><i className="meta-icon-chevron-1" /></span>
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
        // console.log('cols');
        // console.log(cols);
        return (
            <tr>
                {this.renderCols(cols, mainTable)}
            </tr>
        )
    }
}

export default TableHeader
