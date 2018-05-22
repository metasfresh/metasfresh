import React, { PureComponent } from 'react';
import classnames from 'classnames';

import { shouldRenderColumn } from './Table';

class TableHeader extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {};
  }

  componentWillMount() {
    this.setInitialState();
  }

  setInitialState() {
    const { orderBy } = this.props;

    let fields = {};
    orderBy &&
      orderBy.map(item => {
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
    const { sort, onDeselect, page, tabid } = this.props;
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

    sort(sortingValue, field, true, page, tabid);
    onDeselect();
  };

  renderSorting = (field, caption, sortable) => {
    const { fields } = this.state;
    const fieldSorting = fields[field];

    return (
      <div
        className={classnames('sort-menu', { 'sort-menu--sortable': sortable })}
        onClick={() => this.handleClick(field, sortable)}
      >
        <span
          title={caption}
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

  renderCols = cols => {
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
                    item.sortable
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

export default TableHeader;
