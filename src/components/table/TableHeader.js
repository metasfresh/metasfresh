import React, { Component } from 'react';

class TableHeader extends Component {
    constructor(props) {
        super(props);
    }

    renderSorting = (field, caption) => {
        const {sort, orderBy, deselect, page, tabid} = this.props;
        let sorting = {};
        orderBy && orderBy.map((item) => {
            if(field == item.fieldName){
                sorting.name = item.fieldName;
                sorting.asc = item.ascending;
            }
        })

        return (
            <div
                className="sort-menu"
                onClick={() => {
                    sort(!sorting.asc, field, true, page, tabid);
                    deselect();
                }}
            >
                <span title={caption} className="th-caption">{caption}</span>
                <span
                    className={sorting.name && sorting.asc ?
                        'sort rotate-90 sort-ico' :
                            (sorting.name && !sorting.asc) ?
                                'sort sort-ico' : 'sort-ico'
                    }
                >
                    <i className="meta-icon-chevron-1" />
                </span>
            </div>

        )
    }

    renderCols = (cols) => {
        const {getSizeClass, sort} = this.props;

        return cols && cols.map((item, index) =>
            <th
                key={index}
                className={getSizeClass(item)}
            >
                {sort ?
                    this.renderSorting(item.fields[0].field, item.caption):
                    item.caption
                }
            </th>
        );
    }

    render() {
        const {
            cols, indentSupported
        } = this.props;

        return (
            <tr>
                {indentSupported &&
                    <th key={0} className="indent" />
                }
                {this.renderCols(cols)}
            </tr>
        )
    }
}

export default TableHeader
