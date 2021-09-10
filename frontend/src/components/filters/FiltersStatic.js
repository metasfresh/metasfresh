import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { deleteStaticFilter } from '../../api';
import { clearStaticFilters } from '../../actions/FiltersActions';
import history from '../../services/History';

export class FiltersStatic extends PureComponent {
  clearItemOfStaticFilters = (staticFilterId) => {
    const { filterId, clearStaticFilters, windowId, viewId } = this.props;

    if (filterId) {
      deleteStaticFilter(windowId, viewId, staticFilterId).then((response) => {
        history.push(`/window/${windowId}?viewId=${response.data.viewId}`);

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
};

export default connect(null, { clearStaticFilters })(FiltersStatic);
