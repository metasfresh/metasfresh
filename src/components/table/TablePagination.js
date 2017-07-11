import React, { Component } from 'react';
import counterpart from 'counterpart';
import PaginationContextShortcuts from
    '../shortcuts/PaginationContextShortcuts';

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
        this.setState({
            value: e.target.value ? e.target.value : ''
        })
    }

    handleSubmit = (e, value, pages) => {
        const {handleChangePage, deselect} = this.props;
        if(e.key === 'Enter'){
            e.preventDefault();

            if(value <= pages && value > 0){
                handleChangePage(Number(value));
                deselect();
                this.setState({
                    value: '',
                    secondDotsState: false,
                    firstDotsState: false
                });
            }

        }
    }

    handleFirstDotsState = () => {
        const {firstDotsState} = this.state;
        this.setState({
            firstDotsState: !firstDotsState
        }, () => {
            if(!firstDotsState){
                this.goToPage.focus();
            }
        });
    }

    handleSecondDotsState = () => {
        const {secondDotsState} = this.state;
        this.setState({
            secondDotsState: !secondDotsState
        }, () => {
            if(!secondDotsState) {
                this.goToPage.focus();
            }
        });
    }

    handleSelectWholePage = (value) => {
        this.setState({
            selectedWholePage: value
        })
    }

    resetGoToPage = () => {
        this.setState({
            secondDotsState: false,
            firstDotsState: false
        })
    }

    renderGoToPage = (pages, value) => {
        return (
            <div className="page-dots-open">
                <span>{counterpart.translate('view.goTo.caption')}</span>
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
        const {handleChangePage, deselect, compressed} = this.props;
        const {firstDotsState, value} = this.state;
        pagination.push(
            <li
                className="page-item"
                key={1}
                onClick={() => {
                    this.resetGoToPage();
                    handleChangePage(1);
                    deselect()} }
            >
                <a
                    className={
                        'page-link ' +
                        (compressed ? 'page-link-compressed ' : '')
                    }
                >{1}</a>
            </li>
        );
        pagination.push(
            <li className="page-item page-dots" key={0}>
                { firstDotsState && this.renderGoToPage(pages, value) }
                <a
                    className={
                        'page-link ' +
                        (compressed ? 'page-link-compressed ' : '')
                    }
                    onClick={() => this.handleFirstDotsState()}
                >{'...'}</a>
            </li>
        );
    }

    renderLastPartPagination = (pagination, pages) => {
        const {handleChangePage, deselect, compressed} = this.props;
        const {secondDotsState, value} = this.state;

        pagination.push(
            <li className="page-item page-dots" key={99990}>
                { secondDotsState && this.renderGoToPage(pages, value) }
                <a
                    className={
                        'page-link ' +
                        (compressed ? 'page-link-compressed ' : '')
                    }
                    onClick={() => this.handleSecondDotsState()}
                >{'...'}</a>
            </li>
        );
        pagination.push(
            <li
                className="page-item"
                key={9999}
                onClick={() => {
                    this.resetGoToPage();
                    handleChangePage(pages);
                    deselect()
                } }
            >
                <a
                    className={
                        'page-link ' +
                        (compressed ? 'page-link-compressed ' : '')
                    }
                >{pages}</a>
            </li>
        );
    }

    renderPaginationContent = (pagination, page, start, end) => {
        const {handleChangePage, deselect, compressed} = this.props;
        for(let i = start; i <= end; i++){
            pagination.push(
                <li
                    className={
                        'page-item ' +
                        (page === i ? 'active': '')
                    }
                    key={i}
                    onClick={() => {
                        this.resetGoToPage();
                        handleChangePage(i);
                        deselect();
                    } }
                >
                    <a
                        className={
                            'page-link ' +
                            (compressed ? 'page-link-compressed ' : '')
                        }>{i}</a>
                </li>
            );
        }
    }

    renderTotalItems = () => {
        const {size, queryLimitHit} = this.props;
        return (
            <div className="hidden-sm-down">
                <div>
                    {counterpart.translate('view.totalItems.caption')} {size}
                    {queryLimitHit &&
                        <span className="text-danger"> (limited)
                        </span>
                    }
                </div>
            </div>
        )
    }

    renderSelectAll = () => {
        const {
            selected, handleSelectAll, handleSelectRange, size, pageLength
        } = this.props;
        const selectedWholePage = selected && (selected.length === pageLength);
        return (
            <div className="hidden-sm-down">
                <div>{selected.length > 0 ?
                        counterpart.translate(
                            'view.itemsSelected.caption',
                            {
                                size: (selected[0] === 'all' ? size :
                                selected.length)
                            }
                        )
                        : counterpart.translate('view.noItemSelected.caption')
                     }
                 </div>
                <div
                    className="pagination-link pointer"
                    onClick={() => {
                        selectedWholePage ?
                             handleSelectRange(['all']) : handleSelectAll()
                    }}
                >
                    {selectedWholePage ?
                        counterpart.translate(
                            'view.selectAllItems.caption',
                            {size: size}) :
                        counterpart.translate('view.selectAllOnPage.caption')
                    }
                </div>
            </div>
        )
    }

    renderArrow = (left) => {
        const {compressed, handleChangePage, deselect} = this.props;
        return (
            <li className="page-item">
                <a
                    className={
                        'page-link ' +
                        (compressed ? 'page-link-compressed ' : '')
                    }
                    onClick={() => {
                        this.resetGoToPage();
                        handleChangePage(left ? 'down' : 'up');
                        deselect();
                    }}
                >
                    <span>{left ? '«' : '»'}</span>
                </a>
            </li>
        )
    }

    render() {
        const {
            size, pageLength, handleChangePage, page, compressed,
            disablePaginationShortcuts
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
            <div className="pagination-wrapper js-not-unselect">
                <div className="pagination-row">
                    {compressed && <div />}
                    {!compressed && this.renderSelectAll()}

                    <div className="items-row-2 pagination-part">
                        {!compressed && this.renderTotalItems()}
                        <div>
                            <nav>
                                <ul className="pagination pointer">
                                    {this.renderArrow(true)}
                                    {pagination}
                                    {this.renderArrow(false)}
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>

                {
                    !disablePaginationShortcuts &&
                    <PaginationContextShortcuts
                        handleFirstPage={() => handleChangePage(1)}
                        handleLastPage={() => handleChangePage(
                            size ? Math.ceil(size / pageLength) : 0)
                        }
                        handleNextPage={() => handleChangePage('up')}
                        handlePrevPage={() => handleChangePage('down')}
                        pages={pages}
                    />
                }
            </div>
        );

    }
}

export default TablePagination;
