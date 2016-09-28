import React, { Component } from 'react';


class TablePagination extends Component {
    constructor(props){
        super(props);

    }

    handleGoToPage = () => {
        console.log('go to page');
    }

    renderFirstPartPagination = (pagination, page, pages) => {
        const {handleChangePage} = this.props;
        pagination.push(
            <li className="page-item" key={1} onClick={() => handleChangePage(1)}>
                <a className="page-link">{1}</a>
                {}
            </li>
        );
        pagination.push(
            <li className="page-item page-dots" key={0} onClick={() => this.handleGoToPage()}>
                <a className="page-link">{'...'}</a>
                {}
            </li>
        );
    }

    renderLastPartPagination = (pagination, pages) => {
        const {handleChangePage} = this.props;
        console.log('pages: '+pages);
        pagination.push(
            <li className="page-item page-dots" key={99990} onClick={() => this.handleGoToPage()}>
                <div className="page-dots-open">
                    <span>Go to page</span>
                    <input type="text" />
                </div>
                <a className="page-link">{'...'}</a>
                {}
            </li>
        );
        pagination.push(
            <li className="page-item" key={9999} onClick={() => handleChangePage(pages)}>
                <a className="page-link">{pages}</a>
                {}
            </li>
        );
    }

    renderPaginationContent = (pagination, page, start, end) => {
        const {handleChangePage} = this.props;
        for(let i = start; i <= end; i++){
            pagination.push(
                <li className={" page-item " + (page === i ? "active": "")} key={i} onClick={() => handleChangePage(i)}>
                    <a className="page-link">{i}</a>
                </li>
            );
        }
    }

    render() {
        const {size, pageLength, selected, handleSelectAll, handleChangePage, page} = this.props;
        const pages = size ? Math.ceil(size / pageLength) : 0;
        const startPoint = pages > 1 ? (pages - page <= 4 ? (pages - 4 > 0 ? pages - 4 : 1 ) : page) : 1;
        const endPoint = pages > 1 ? (startPoint + 4 > pages ? pages : startPoint + 4) : 1;

        console.log(pages);
        console.log(page);

        let pagination = [];

        if(pages < 8 ) {
            {
                this.renderPaginationContent(pagination, page, 1, pages-1);
            }
        } else {
            if(page <= 4) {
                // for(let i = 1; i <= 5; i++){
                //     pagination.push(
                //         <li className={" page-item " + (page === i ? "active": "")} key={i} onClick={() => handleChangePage(i)}>
                //             <a className="page-link">{i}</a>
                //             {}
                //         </li>
                //     );
                // }
                {
                    this.renderPaginationContent(pagination, page, 1, 5);
                    this.renderLastPartPagination(pagination, pages);
                }
                // pagination.push(
                //     <li className="page-item" key={99990} onClick={() => this.handleGoToPage()}>
                //         <a className="page-link">{'...'}</a>
                //         {}
                //     </li>
                // );
                // pagination.push(
                //     <li className="page-item" key={9999} onClick={() => handleChangePage(pages)}>
                //         <a className="page-link">{pages}</a>
                //         {}
                //     </li>
                // );
                


            } else if(page > pages - 4) {

                // pagination.push(
                //     <li className="page-item" key={1} onClick={() => handleChangePage(1)}>
                //         <a className="page-link">{1}</a>
                //         {}
                //     </li>
                // );
                // pagination.push(
                //     <li className="page-item" key={0} onClick={() => this.handleGoToPage()}>
                //         <a className="page-link">{'...'}</a>
                //         {}
                //     </li>
                // );

                // for(let i = pages-4; i <= pages; i++){
                //     pagination.push(
                //         <li className={" page-item " + (page === i ? "active": "")} key={i} onClick={() => handleChangePage(i)}>
                //             <a className="page-link">{i}</a>
                //             {}
                //         </li>
                //     );
                // }

                {
                    this.renderFirstPartPagination(pagination, pages);
                    this.renderPaginationContent(pagination, page, pages-4, pages);
                }
            } else {
                // pagination.push(
                //     <li className="page-item" key={1} onClick={() => handleChangePage(1)}>
                //         <a className="page-link">{1}</a>
                //         {}
                //     </li>
                // );
                // pagination.push(
                //     <li className="page-item" key={0} onClick={() => this.handleGoToPage()}>
                //         <a className="page-link">{'...'}</a>
                //         {}
                //     </li>
                // );

                // for(let i = page-1; i <= page+1; i++){
                //     pagination.push(
                //         <li className={" page-item " + (page === i ? "active": "")} key={i} onClick={() => handleChangePage(i)}>
                //             <a className="page-link">{i}</a>
                //             {}
                //         </li>
                //     );
                // }

                // pagination.push(
                //     <li className="page-item" key={99990} onClick={() => handleGoToPage()}>
                //         <a className="page-link">{'...'}</a>
                //         {}
                //     </li>
                // );
                // pagination.push(
                //     <li className="page-item" key={9999} onClick={() => handleChangePage(pages)}>
                //         <a className="page-link">{pages}</a>
                //         {}
                //     </li>
                // );

                this.renderFirstPartPagination(pagination, pages);
                this.renderPaginationContent(pagination, page, page-1, page+1);
                this.renderLastPartPagination(pagination, pages);
            }
        }

        // for(let i = startPoint; i <= endPoint; i++){
        //     pagination.push(
        //         <li className={" page-item " + (page === i ? "active": "")} key={i} onClick={() => handleChangePage(i)}>
        //             <a className="page-link">{i}</a>
        //         </li>
        //     );
        // }



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
