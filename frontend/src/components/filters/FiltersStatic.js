import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { clearStaticFilters } from '../../actions/FiltersActions';
import { connect } from 'react-redux';
import { deleteStaticFilter } from '../../api';
import { push } from 'react-router-redux';

export class FiltersStatic extends PureComponent {
  clearItemOfStaticFilters = (staticFilterId) => {
    const { filterId, clearStaticFilters, windowId, viewId, push } = this.props;

    if (filterId) {
      deleteStaticFilter(windowId, viewId, staticFilterId).then((response) => {
        push(`/window/${windowId}?viewId=${response.data.viewId}`);

        clearStaticFilters({
          filterId,
          data: true,
        });
      });
    }
  };

  render() {
    const { data } = this.props;
    return (
      <div className="filter-wrapper">
        {data.map((item, index) => {
          return (
            <div className="filter-wrapper" key={index}>
              <button
                className={'btn btn-meta-disabled ' + 'btn-distance btn-sm'}
                onClick={() => this.clearItemOfStaticFilters(item.id)}
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

FiltersStatic.propTypes = {
  data: PropTypes.array,
  clearFilters: PropTypes.func,
  filterId: PropTypes.string,
  clearStaticFilters: PropTypes.func,
  windowId: PropTypes.string,
  viewId: PropTypes.string,
  push: PropTypes.func,
};

export default connect(
  null,
  {
    clearStaticFilters,
    push,
  }
)(FiltersStatic);
