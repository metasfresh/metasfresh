import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import Loader from '../app/Loader';
import BarChart from './BarChartComponent';
import Indicator from './Indicator';
import PieChart from './PieChartComponent';
import { URLsChart } from './URLsChart';

class RawChart extends Component {
  constructor(props) {
    super(props);

    this.state = {
      forceChartReRender: false,
    };
  }

  componentDidUpdate(prevProps) {
    if (
      prevProps.isMaximized !== this.props.isMaximized ||
      prevProps.index !== this.props.index ||
      prevProps.id !== this.props.id
    ) {
      if (this.props.chartType !== 'Indicator') {
        this.mounted &&
          this.setState(
            {
              forceChartReRender: true,
            },
            () => {
              this.setState({
                forceChartReRender: false,
              });
            }
          );
      }
    }
  }

  componentDidMount() {
    this.mounted = true;
  }

  componentWillUnmount() {
    this.mounted = false;
  }

  renderError(errorMessage) {
    return (
      <div className="error-load-data">
        <h6 className="error-load-text">{errorMessage}</h6>
        <div className="error-loading" />
      </div>
    );
  }

  renderChart() {
    const {
      id,
      chartType,
      caption,
      fields,
      groupBy,
      height,
      isMaximized,
      chartTitle,
      editmode,
      data,
      noData,
      openChartOptions,
      zoomToDetailsAvailable,
    } = this.props;
    const { forceChartReRender } = this.state;
    const dataset0 =
      data && data.datasets && data.datasets[0] && data.datasets[0].values;
    const dataset0_unit =
      data && data.datasets && data.datasets[0] && data.datasets[0].unit;

    switch (chartType) {
      case 'BarChart':
        return (
          <BarChart
            {...{
              data: dataset0,
              groupBy,
              caption,
              chartType,
              height,
              fields,
              isMaximized,
              chartTitle,
              noData,
            }}
            chartClass={'chart-' + id}
            reRender={forceChartReRender}
            colors={[
              '#89d729',
              '#9aafbd',
              '#7688c9',
              '#c1ea8e',
              '#c9d5dc',
              '#aab5e0',
              '#6aad18',
              '#298216',
              '#32520d',
              '#605a7f',
            ]}
          />
        );
      case 'PieChart':
        return (
          <PieChart
            {...{
              data: dataset0,
              fields,
              groupBy,
              height,
              noData,
              isMaximized,
              chartTitle,
            }}
            chartClass={'chart-' + id}
            responsive={true}
            reRender={forceChartReRender}
            colors={[
              '#89d729',
              '#9aafbd',
              '#7688c9',
              '#c1ea8e',
              '#c9d5dc',
              '#aab5e0',
              '#6aad18',
              '#298216',
              '#32520d',
              '#605a7f',
            ]}
          />
        );
      case 'Indicator':
        return (
          <div>
            {editmode && openChartOptions && (
              <span
                className="chart-edit-mode"
                onClick={() => openChartOptions(id)}
              >
                <i className="meta-icon-settings" />
              </span>
            )}

            <Indicator
              id={id}
              zoomToDetailsAvailable={zoomToDetailsAvailable}
              amount={noData ? '0' : dataset0[0][fields[0].fieldName]}
              unit={dataset0_unit ? dataset0_unit : fields[0].unit}
              {...{
                caption,
                editmode,
                data,
              }}
            />
          </div>
        );
      case 'URLs':
        return <URLsChart data={dataset0} />;
      default:
        return <div>{chartType}</div>;
    }
  }

  renderNoData(showLoader) {
    const { chartType, data, zoomToDetailsAvailable, caption } = this.props;

    switch (chartType) {
      case 'Indicator':
        return (
          <Indicator
            value={'No data'}
            data={data}
            loader={showLoader}
            {...{ caption, zoomToDetailsAvailable }}
          />
        );
      default:
        return <div>{showLoader ? <Loader /> : 'No data'}</div>;
    }
  }

  render() {
    const { data, chartType } = this.props;

    if (!data) {
      return this.renderNoData(true); // loading
    } else if (data.error && chartType !== 'Indicator') {
      return this.renderError(data.error.message);
    } else if (data.datasets && data.datasets.length > 0) {
      return this.renderChart();
    } else {
      return this.renderNoData(false); // no data
    }
  }
}

RawChart.propTypes = {
  isMaximized: PropTypes.bool,
  index: PropTypes.number,
  id: PropTypes.number,
  chartType: PropTypes.string,
  data: PropTypes.object,
  noData: PropTypes.bool,
  caption: PropTypes.string,
  fields: PropTypes.arrayOf(PropTypes.object),
  groupBy: PropTypes.object,
  height: PropTypes.number,
  editmode: PropTypes.bool,
  chartTitle: PropTypes.string,
  zoomToDetailsAvailable: PropTypes.bool,
  //
  openChartOptions: PropTypes.func,
};

export default connect()(RawChart);
