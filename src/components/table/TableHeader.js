import React, { Component } from 'react';

class TableHeader extends Component {
    constructor(props) {
        super(props);
    }
    renderCols = (cols) => {
        return cols.map((item, index) => <th key={index}>{item.caption}</th>);
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
