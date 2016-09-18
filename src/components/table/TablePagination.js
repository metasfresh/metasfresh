import React, { Component } from 'react';


class TablePagination extends Component {
    constructor(props){
        super(props);

    }

    render() {
        const {size, pageLength, selected, handleSelectAll, handleChangePage, page} = this.props;
        const pages = size ? Math.ceil(size / pageLength) : 0;
        const startPoint = pages > 1 ? (pages - page <= 4 ? pages - 4 : page) : 1;
        const endPoint = pages > 1 ? (startPoint + 4 > pages ? pages : startPoint + 4) : 1;

        let pagination = [];
        for(let i = startPoint; i <= endPoint; i++){
            pagination.push(
                <li className={" page-item " + (page === i ? "active": "")} key={i} onClick={() => handleChangePage(i)}>
                    <a className="page-link">{i}</a>
                </li>
            );
        }

        return (
            <div>
                <div className="pagination-row">
                    <div>
                        <div>{selected.length > 0 ? selected.length + " items selected" : "No items selected"}</div>
                        <div className="pagination-link pointer" onClick={handleSelectAll}>Select all on this page</div>
                    </div>

                    <div className="items-row-2 pagination-part">
                        <div>
                            <div>Sorting by <b>DocNo</b> (1163-1200)</div>
                            <div>Total items {size}</div>
                        </div>
                        <div>
                            <nav>
                                <ul className="pagination pointer">
                                    <li className="page-item">
                                        <a className="page-link" onClick={() => handleChangePage("down")}>
                                            <span>&laquo;</span>
                                        </a>
                                    </li>

                                    {pagination}

                                    <li className={"page-item "}>
                                        <a className="page-link" onClick={() => handleChangePage("up")}>
                                            <span>&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        );

    }
}


export default TablePagination;
