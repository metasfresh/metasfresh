import React, { Component } from 'react';
import { connect } from 'react-redux';

import { getKPIData, getTargetIndicatorsData } from '../../actions/AppActions';
import Loader from '../app/Loader';
import BarChart from './BarChartComponent';
import Indicator from './Indicator';
import PieChart from './PieChartComponent';

class RawChart extends Component {
  constructor(props) {
    super(props);

    this.state = {
      chartData: null,
      intervalId: null,
      err: null,
    };
  }

  componentDidUpdate(prevProps) {
    if (
      prevProps.isMaximized !== this.props.isMaximized ||
      prevProps.index !== this.props.index ||
      prevProps.id !== this.props.id
    ) {
      if (this.props.chartType === 'Indicator') {
        if (this.props.noData) return;
        this.fetchData();
      } else {
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
    const { noData } = this.props;
    this.mounted = true;

    if (noData) return;
    this.init();
  }

  componentWillUnmount() {
    const { intervalId } = this.state;

    this.mounted = false;

    if (intervalId) {
      clearInterval(intervalId);

      this.mounted &&
        this.setState({
          intervalId: null,
        });
    }
  }

  init = () => {
    const { pollInterval } = this.props;
    this.fetchData();

    if (pollInterval) {
      this.mounted &&
        this.setState({
          intervalId: setInterval(() => {
            this.fetchData();
          }, pollInterval * 1000),
        });
    }
  };

  getData() {
    const { id, chartType } = this.props;

    if (chartType === 'Indicator') {
      return getTargetIndicatorsData(id)
        .then(response => {
          return response.data.datasets;
        })
        .catch(err => {
          throw err;
        });
    }

    return getKPIData(id)
      .then(response => {
        return response.data.datasets;
      })
      .catch(err => {
        throw err;
      });
  }

  fetchData() {
    this.getData()
      .then(chartData => {
        this.mounted && this.setState({ chartData: chartData, err: null });
      })
      .catch(err => {
        this.mounted && this.setState({ err });
      });
  }

  renderError() {
    return (
      <div className="error-load-data">
        <h6 className="error-load-text">Error loading data...</h6>
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
      noData,
      onChartOptions,
    } = this.props;
    const { chartData, forceChartReRender } = this.state;
    const data = chartData[0] && chartData[0].values;

    switch (chartType) {
      case 'BarChart':
        return (
          <BarChart
            {...{
              data,
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
              data,
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
            {editmode ? (
              <span
                className="chart-edit-mode"
                onClick={() => onChartOptions(true, caption, id, true)}
              >
                <i className="meta-icon-settings" />
              </span>
            ) : (
              ''
            )}

            <Indicator
              value={
                noData
                  ? ''
                  : data[0][fields[0].fieldName] +
                    (fields[0].unit ? ' ' + fields[0].unit : '')
              }
              {...{ caption, editmode }}
            />
          </div>
        );
      default:
        return <div>{chartType}</div>;
    }
  }

  renderNoData(data) {
    const { chartType } = this.props;

    switch (chartType) {
      case 'Indicator':
        return <Indicator value={'No data'} loader={data === null} />;
      default:
        return <div>{data === null ? <Loader /> : 'No data'}</div>;
    }
  }

  render() {
    const { chartData, err } = this.state;

    return err
      ? this.renderError()
      : chartData && chartData.length > 0
        ? this.renderChart()
        : this.renderNoData(chartData);
  }
}

export default connect()(RawChart);
