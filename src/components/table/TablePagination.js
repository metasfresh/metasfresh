import counterpart from 'counterpart';
import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import PaginationContextShortcuts from '../keyshortcuts/PaginationContextShortcuts';

class TablePagination extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      firstDotsState: false,
      secondDotsState: false,
      value: '',
    };
  }

  handleValue = e => {
    e.preventDefault();
    this.setState({
      value: e.target.value ? e.target.value : '',
    });
  };

  handleSelectAll = () => {
    const { selected, rowLength, onSelectAll, onSelectRange } = this.props;
    const selectedWholePage = selected && selected.length === rowLength;

    return selectedWholePage ? onSelectRange(['all']) : onSelectAll();
  };

  handleSubmit = (e, value, pages) => {
    const { onChangePage } = this.props;
    if (e.key === 'Enter') {
      e.preventDefault();

      if (value <= pages && value > 0) {
        onChangePage(Number(value));
        this.setState({
          value: '',
          secondDotsState: false,
          firstDotsState: false,
        });
      }
    }
  };

  handleFirstDotsState = () => {
    const { firstDotsState } = this.state;
    this.setState(
      {
        firstDotsState: !firstDotsState,
      },
      () => {
        if (!firstDotsState) {
          this.goToPage.focus();
        }
      }
    );
  };

  handleSecondDotsState = () => {
    const { secondDotsState } = this.state;
    this.setState(
      {
        secondDotsState: !secondDotsState,
      },
      () => {
        if (!secondDotsState) {
          this.goToPage.focus();
        }
      }
    );
  };

  handleSelectWholePage = value => {
    this.setState({
      selectedWholePage: value,
    });
  };

  resetGoToPage = () => {
    this.setState({
      secondDotsState: false,
      firstDotsState: false,
    });
  };

  renderGoToPage = (pages, value) => {
    return (
      <div className="page-dots-open">
        <span>{counterpart.translate('view.goTo.caption')}</span>
        <input
          type="number"
          min="1"
          ref={c => (this.goToPage = c)}
          max={pages}
          value={value}
          onChange={e => this.handleValue(e)}
          onKeyDown={e => this.handleSubmit(e, value, pages)}
        />
      </div>
    );
  };

  renderFirstPartPagination = (pagination, pages) => {
    const { onChangePage, compressed } = this.props;
    const { firstDotsState, value } = this.state;

    pagination.push(
      <li
        className="page-item"
        key={1}
        onClick={() => {
          this.resetGoToPage();
          onChangePage(1);
        }}
      >
        <a
          className={classnames('page-link', {
            'page-link-compressed': compressed,
          })}
        >
          {1}
        </a>
      </li>
    );

    pagination.push(
      <li className="page-item page-dots" key={0}>
        {firstDotsState && this.renderGoToPage(pages, value)}
        <a
          className={classnames('page-link', {
            'page-link-compressed': compressed,
          })}
          onClick={() => this.handleFirstDotsState()}
        >
          {'...'}
        </a>
      </li>
    );
  };

  renderLastPartPagination = (pagination, pages) => {
    const { onChangePage, compressed } = this.props;
    const { secondDotsState, value } = this.state;

    pagination.push(
      <li className="page-item page-dots" key={99990}>
        {secondDotsState && this.renderGoToPage(pages, value)}
        <a
          className={classnames('page-link', {
            'page-link-compressed': compressed,
          })}
          onClick={() => this.handleSecondDotsState()}
        >
          {'...'}
        </a>
      </li>
    );
    pagination.push(
      <li
        className="page-item"
        key={9999}
        onClick={() => {
          this.resetGoToPage();
          onChangePage(pages);
        }}
      >
        <a
          className={classnames('page-link', {
            'page-link-compressed': compressed,
          })}
        >
          {pages}
        </a>
      </li>
    );
  };

  renderPaginationContent = (pagination, page, start, end) => {
    const { onChangePage, compressed } = this.props;

    for (let i = start; i <= end; i++) {
      pagination.push(
        <li
          className={classnames('page-item', {
            active: page === i,
          })}
          key={i}
          onClick={() => {
            this.resetGoToPage();
            onChangePage(i);
          }}
        >
          <a
            className={classnames('page-link', {
              'page-link-compressed': compressed,
            })}
          >
            {i}
          </a>
        </li>
      );
    }
  };

  renderTotalItems = () => {
    const { size, queryLimitHit } = this.props;

    return (
      <div className="hidden-sm-down">
        <div>
          {counterpart.translate('view.totalItems.caption')} {size}
          {queryLimitHit && <span className="text-danger"> (limited)</span>}
        </div>
      </div>
    );
  };

  renderSelectAll = () => {
    const { selected, size, rowLength, pageLength } = this.props;
    const selectedWholePage = selected && selected.length === rowLength;
    const isShowSelectAllItems = size > pageLength;

    return (
      <div className="hidden-sm-down">
        <div>
          {selected.length > 0
            ? counterpart.translate('view.itemsSelected.caption', {
                size: selected[0] === 'all' ? size : selected.length,
              })
            : counterpart.translate('view.noItemSelected.caption')}
        </div>
        <div
          className="pagination-link pointer"
          onClick={() => {
            this.handleSelectAll();
          }}
          title="Alt+A"
        >
          {selectedWholePage && isShowSelectAllItems
            ? counterpart.translate('view.selectAllItems.caption', {
                size: size,
              })
            : counterpart.translate('view.selectAllOnPage.caption')}
        </div>
      </div>
    );
  };

  renderArrow = left => {
    const { compressed, onChangePage } = this.props;
    return (
      <li className="page-item">
        <a
          className={classnames('page-link', {
            'page-link-compressed': compressed,
          })}
          onClick={() => {
            this.resetGoToPage();
            onChangePage(left ? 'down' : 'up');
          }}
        >
          <span>{left ? '«' : '»'}</span>
        </a>
      </li>
    );
  };

  paginationShortcuts = () => {
    const {
      size,
      pageLength,
      disablePaginationShortcuts,
      onChangePage,
    } = this.props;

    const pages = size ? Math.ceil(size / pageLength) : 0;

    return (
      !disablePaginationShortcuts && {
        onFirstPage: () => onChangePage(1),
        onLastPage: () => onChangePage(size ? Math.ceil(size / pageLength) : 0),
        onNextPage: () => onChangePage('up'),
        onPrevPage: () => onChangePage('down'),
        pages: pages,
      }
    );
  };

  render() {
    const { size, pageLength, page, compressed } = this.props;

    const pages = size ? Math.ceil(size / pageLength) : 0;

    let pagination = [];

    if (pages < 8) {
      if (pages <= 0) {
        this.renderPaginationContent(pagination, page, 1, 1);
      } else {
        this.renderPaginationContent(pagination, page, 1, pages);
      }
    } else {
      if (page <= 4) {
        this.renderPaginationContent(pagination, page, 1, 5);
        this.renderLastPartPagination(pagination, pages);
      } else if (page > pages - 4) {
        this.renderFirstPartPagination(pagination, pages);
        this.renderPaginationContent(pagination, page, pages - 4, pages);
      } else {
        this.renderFirstPartPagination(pagination, pages);
        this.renderPaginationContent(pagination, page, page - 1, page + 1);
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

        <PaginationContextShortcuts
          onSelectAll={() => this.handleSelectAll()}
          {...this.paginationShortcuts()}
        />
      </div>
    );
  }
}

TablePagination.propTypes = {
  selected: PropTypes.array,
  onSelectAll: PropTypes.func,
  onSelectRange: PropTypes.func,
  onChangePage: PropTypes.func,
  rowLength: PropTypes.number.isRequired,
  compressed: PropTypes.any, // Looks like it's not used
  size: PropTypes.number.isRequired,
  queryLimitHit: PropTypes.number,
  pageLength: PropTypes.number.isRequired,
  disablePaginationShortcuts: PropTypes.bool,
  page: PropTypes.number.isRequired,
};

export default TablePagination;
