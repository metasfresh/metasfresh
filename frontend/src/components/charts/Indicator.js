import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Loader from '../app/Loader';
import { getTargetIndicatorsDetails } from '../../actions/DashboardActions';
import moment from 'moment';

class Indicator extends Component {
  constructor(props) {
    super(props);
    this.state = { localComputedTimestamp: null };
  }

  static getDerivedStateFromProps({ data: { computedTimestamp } }) {
    return { localComputedTimestamp: computedTimestamp };
  }

  /**
   * @method showDetails
   * @summary Calls the getTargetIndicatorsDetails dashboard action to get the windowId and the viewId and
   *          once those are retrieved it will open the view in a new browser tab
   * @param {string} indicatorId
   */
  showDetails = (indicatorId) => {
    getTargetIndicatorsDetails(indicatorId).then((detailsResp) => {
      const { viewId, windowId } = detailsResp.data;
      let detailsTab = window.open(
        `${window.location.origin}/window/${windowId}?viewId=${viewId}`,
        '_blank'
      );
      detailsTab.focus();
    });
  };

  render() {
    const {
      id,
      amount,
      unit,
      caption,
      loader,
      fullWidth,
      editmode,
      framework,
      zoomToDetailsAvailable,
    } = this.props;
    const { localComputedTimestamp } = this.state;

    if (loader)
      return (
        <div className="indicator">
          <Loader />
        </div>
      );

    return (
      <div
        className={
          'indicator js-indicator ' +
          (editmode || framework ? 'indicator-draggable ' : '')
        }
        style={fullWidth ? { width: '100%' } : {}}
      >
        <div>
          <div className="indicator-kpi-caption">{caption}</div>
          {/* TODO: !!! this needs not to be hardcoded and must be provided by the BE */}
          {zoomToDetailsAvailable && (
            <div
              className="indicator-details-link"
              onClick={() => this.showDetails(id)}
            >
              DETAILS
            </div>
          )}
        </div>
        <div className="indicator-data">
          <div className="indicator-amount">{amount}</div>
          <div className="indicator-unit">{unit}</div>
        </div>
        {localComputedTimestamp && (
          <div className="indicator-last-updated">
            {moment(localComputedTimestamp).fromNow()}
          </div>
        )}
      </div>
    );
  }
}

Indicator.propTypes = {
  id: PropTypes.number,
  value: PropTypes.any,
  caption: PropTypes.string,
  loader: PropTypes.any,
  fullWidth: PropTypes.bool,
  editmode: PropTypes.bool,
  framework: PropTypes.any,
  amount: PropTypes.number,
  unit: PropTypes.string,
  zoomToDetailsAvailable: PropTypes.bool,
  data: PropTypes.object,
};

export default Indicator;
