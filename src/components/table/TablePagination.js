import React, { Component } from 'react';
import PaginationContextShortcuts from '../shortcuts/PaginationContextShortcuts';

class TablePagination extends Component {
    constructor(props){
        super(props);
        this.state = {
            firstDotsState: false,
            secondDotsState: false,
            value: ''
        }
    }

    handleValue = (e) => {
        e.preventDefault();
        if(e.target.value){
            this.setState({
                value: e.target.value
            })
        } else {
            this.setState({
                value: ''
            })
        }
    }

    handleSubmit = (e, value, pages) => {
        const {handleChangePage, deselect} = this.props;
        if(e.key === 'Enter'){
            e.preventDefault();

            if(value <= pages && value > 0){

                handleChangePage(Number(value));
                deselect();

                this.setState(Object.assign({}, this.state, {
                    value: '',
                    secondDotsState: false,
                    firstDotsState: false
                }));
            }

        }
    }

    handleFirstDotsState = () => {
        const {firstDotsState} = this.state;
        this.setState(Object.assign({}, this.state, {
            firstDotsState: !firstDotsState
        }), () => {
            if(!firstDotsState){
                this.goToPage.focus();
            }
        });
    }

    handleSecondDotsState = () => {
        const {secondDotsState} = this.state;
        this.setState(Object.assign({}, this.state, {
            secondDotsState: !secondDotsState
        }), () => {
            if(!secondDotsState) {
                this.goToPage.focus();
            }
        });
    }

    renderGoToPage = (pages, value) => {
        return (
            <div className="page-dots-open">
                <span>Go to page</span>
                <input
                    type="number"
                    min="1"
                    ref={c => this.goToPage = c}
                    max={pages}
                    value={value}
                    onChange={e => this.handleValue(e)}
                    onKeyDown={(e) => this.handleSubmit(e, value, pages)}
                />
            </div>
        )
    }

    renderFirstPartPagination = (pagination, pages) => {
        const {handleChangePage, deselect} = this.props;
        const {firstDotsState, value} = this.state;
        pagination.push(
            <li className="page-item" key={1} onClick={() => {handleChangePage(1); deselect()} }>
                <a className="page-link">{1}</a>
            </li>
        );
        pagination.push(
            <li className="page-item page-dots" key={0}>
                { firstDotsState && this.renderGoToPage(pages, value) }
                <a className="page-link" onClick={() => this.handleFirstDotsState()}>{'...'}</a>
            </li>
        );
    }

    renderLastPartPagination = (pagination, pages) => {
        const {handleChangePage, deselect} = this.props;
        const {secondDotsState, value} = this.state;

        pagination.push(
            <li className="page-item page-dots" key={99990}>
                { secondDotsState && this.renderGoToPage(pages, value) }
                <a className="page-link" onClick={() => this.handleSecondDotsState()}>{'...'}</a>
            </li>
        );
        pagination.push(
            <li className="page-item" key={9999} onClick={() => {handleChangePage(pages); deselect()} }>
                <a className="page-link">{pages}</a>
            </li>
        );
    }

    renderPaginationContent = (pagination, page, start, end) => {
        const {handleChangePage, deselect} = this.props;
        for(let i = start; i <= end; i++){
            pagination.push(
                <li
                    className={
                        'page-item ' +
                        (page === i ? 'active': '')
                    }
                    key={i}
                    onClick={() => {handleChangePage(i); deselect()} }
                >
                    <a className="page-link">{i}</a>
                </li>
            );
        }
    }

    render() {
        const {
            size, pageLength, selected, handleSelectAll, handleChangePage, page,
            deselect
        } = this.props;
        const pages = size ? Math.ceil(size / pageLength) : 0;

        let pagination = [];

        if(pages < 8 ) {
            if(pages <= 0){
                this.renderPaginationContent(pagination, page, 1, 1);
            } else {
                this.renderPaginationContent(pagination, page, 1, pages);
            }
        } else {
            if(page <= 4) {
                this.renderPaginationContent(pagination, page, 1, 5);
                this.renderLastPartPagination(pagination, pages);
            } else if(page > pages - 4) {
                this.renderFirstPartPagination(pagination, pages);
                this.renderPaginationContent(pagination, page, pages-4, pages);
            } else {
                this.renderFirstPartPagination(pagination, pages);
                this.renderPaginationContent(pagination, page, page-1, page+1);
                this.renderLastPartPagination(pagination, pages);
            }
        }

        return (
            <div className="pagination-wrapper">
                <div className="pagination-row">
                    <div className="hidden-sm-down">
                        <div>{selected.length > 0 ? selected.length + ' items selected' : 'No items selected'}</div>
                        <div className="pagination-link pointer" onClick={handleSelectAll}>Select all on this page</div>
                    </div>

                    <div className="items-row-2 pagination-part">
                        <div className="hidden-sm-down">
                            <div>Total items {size}</div>
                        </div>
                        <div>
                            <nav>
                                <ul className="pagination pointer">
                                    <li className="page-item">
                                        <a className="page-link" onClick={() => { handleChangePage('down'); deselect()} }>
                                            <span>&laquo;</span>
                                        </a>
                                    </li>

                                    {pagination}

                                    <li className="page-item ">
                                        <a className="page-link" onClick={() => {handleChangePage('up'); deselect()} }>
                                            <span>&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>

                <PaginationContextShortcuts
                    handleFirstPage={() => handleChangePage(1)}
                    handleLastPage={() => handleChangePage(size ? Math.ceil(size / pageLength) : 0)}
                    handleNextPage={() => handleChangePage('up')}
                    handlePrevPage={() => handleChangePage('down')}
                />
            </div>
        );

    }
}

export default TablePagination;
