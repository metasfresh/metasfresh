import React, { Component } from 'react';

class FiltersStatic extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { data, clearFilters } = this.props;
    return (
      <div className="filter-wrapper">
        {data.map((item, index) => {
          return (
            <div className="filter-wrapper" key={index}>
              <button
                className={'btn btn-meta-disabled ' + 'btn-distance btn-sm'}
                onClick={() => clearFilters(item.id)}
              >
                <i className="meta-icon-trash" />
                {item.caption}
              </button>
            </div>
          );
        })}
      </div>
    );
  }
}

export default FiltersStatic;
