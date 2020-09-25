import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { clearStaticFilters } from '../../actions/FiltersActions';
import { connect } from 'react-redux';

export class FiltersStatic extends PureComponent {
  clearItemStaticFilters = (staticFilterId) => {
    const {
      clearFilters,
      filterId,
      clearStaticFilters,
      windowId,
      viewId,
    } = this.props;

    filterId &&
      clearStaticFilters({
        filterId,
        staticFilterId,
        windowId,
        viewId,
        data: true,
      });
    clearFilters(staticFilterId);
    // TO REMOVE clearFilters !
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
                onClick={() => this.clearItemStaticFilters(item.id)}
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

export default connect(
  null,
  { clearStaticFilters }
)(FiltersStatic);
