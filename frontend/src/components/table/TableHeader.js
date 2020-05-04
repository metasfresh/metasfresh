import React, { PureComponent } from 'react';
import classnames from 'classnames';
import { shouldRenderColumn } from '../../utils/tableHelpers';
import PropTypes from 'prop-types';
import { setActiveSort } from '../../actions/TableActions';
import { connect } from 'react-redux';

class TableHeader extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {};
  }

  UNSAFE_componentWillMount() {
    this.setInitialState();
  }

  setInitialState() {
    const { orderBy } = this.props;

    let fields = {};
    orderBy &&
      orderBy.map((item) => {
        fields[item.fieldName] = item.ascending;
      });

    this.setState({
      fields,
    });
  }

  handleClick = (field, sortable) => {
    if (!sortable) {
      return;
    }
    const { sort, deselect, page, tabId, setActiveSort } = this.props;
    const stateFields = this.state.fields;
    let fields = {};
    let sortingValue = null;

    if (field in stateFields) {
      fields = { ...stateFields };
      sortingValue = !fields[field];
      fields[field] = sortingValue;
    } else {
      sortingValue = !Object.values(stateFields).reduce(
        (acc, curr) => acc && curr,
        true
      );
      fields[field] = sortingValue;
    }

    this.setState({
      fields: { ...fields },
    });

    sort(sortingValue, field, true, page, tabId);
    setActiveSort(true);
    setTimeout(() => setActiveSort(false), 1000);
    deselect();
  };

  renderSorting = (field, caption, sortable, description) => {
    const { fields } = this.state;
    const fieldSorting = fields[field];

    return (
      <div
        className={classnames('sort-menu', { 'sort-menu--sortable': sortable })}
        onClick={() => this.handleClick(field, sortable)}
      >
        <span
          title={description || caption}
          className={classnames({ 'th-caption': sortable })}
        >
          {caption}
        </span>
        <span
          className={classnames('sort-ico', {
            'sort rotate-90': field in fields && fieldSorting,
            sort: field in fields && !fieldSorting,
          })}
        >
          <i className="meta-icon-chevron-1" />
        </span>
      </div>
    );
  };

  renderCols = (cols) => {
    const { getSizeClass, sort } = this.props;

    return (
      cols &&
      cols.map((item, index) => {
        if (shouldRenderColumn(item)) {
          return (
            <th key={index} className={getSizeClass(item)}>
              {sort
                ? this.renderSorting(
                    item.fields[0].field,
                    item.caption,
                    item.sortable,
                    item.description
                  )
                : item.caption}
            </th>
          );
        }
      })
    );
  };

  render() {
    const { cols, indentSupported } = this.props;

    return (
      <tr>
        {indentSupported && <th key={0} className="indent" />}
        {this.renderCols(cols)}
      </tr>
    );
  }
}

TableHeader.propTypes = {
  orderBy: PropTypes.array,
  sort: PropTypes.any,
  tabId: PropTypes.any,
  deselect: PropTypes.any,
  page: PropTypes.any,
  getSizeClass: PropTypes.func,
  cols: PropTypes.any,
  indentSupported: PropTypes.any,
  setActiveSort: PropTypes.any,
};

const mapDispatchToProps = (dispatch) => {
  return {
    setActiveSort: (data) => dispatch(setActiveSort(data)),
  };
};

export default connect(
  null,
  mapDispatchToProps
)(TableHeader);
