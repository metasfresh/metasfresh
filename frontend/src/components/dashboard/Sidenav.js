import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { getAvailableKPIsToAdd } from '../../actions/DashboardActions';
import Indicator from '../charts/Indicator';
import ChartWidget from './ChartWidget';
import DndWidget from './DndWidget';

class Sidenav extends Component {
  constructor(props) {
    super(props);

    this.state = {
      cards: [],
      indicators: [],
    };
  }

  componentDidMount = () => {
    getAvailableKPIsToAdd().then((res) => {
      this.setState({
        indicators: res.data.filter(
          (chart) => chart.widgetTypes[0] === 'TargetIndicator'
        ),
        cards: res.data.filter((chart) => chart.widgetTypes[0] === 'KPI'),
      });
    });
  };

  render() {
    const { indicators, cards } = this.state;

    return (
      <div className="board-sidenav overlay-shadow">
        <div className="board-sidenav-header">Add Target Indicator widget</div>
        <div>{this.renderChartList(indicators)}</div>
        <div className="board-sidenav-header">Add KPI widget</div>
        <div>{this.renderChartList(cards)}</div>
      </div>
    );
  }

  renderChartList = (charts) => {
    if (!charts) return;

    return charts.map((item, i) => this.renderChartItem(item, i));
  };

  renderChartItem = (item, index) => {
    const { moveCard } = this.props;

    return (
      <DndWidget
        key={index}
        id={item.kpiId}
        index={item.kpiId}
        moveCard={moveCard}
        isNew={true}
        entity={item.widgetTypes[0] === 'KPI' ? 'cards' : 'indicators'}
        transparent={false}
      >
        {item.widgetTypes[0] === 'KPI'
          ? this.renderKPI(item, index)
          : this.renderTargetIndicator(item)}
      </DndWidget>
    );
  };

  renderKPI = (item, index) => {
    return (
      <ChartWidget
        id={item.kpiId}
        index={index}
        chartType={item.chartType}
        caption={item.caption}
        fields={item.fields}
        groupBy={item.groupByField}
        kpi={true}
        isMaximized={false}
        text={item.caption}
        data={item.sampleData}
        framework={true}
      />
    );
  };

  renderTargetIndicator = (item) => {
    return (
      <Indicator
        fullWidth={true}
        value={item.chartType}
        caption={item.caption}
        framework={true}
      />
    );
  };
}

Sidenav.propTypes = {
  moveCard: PropTypes.func,
};

export default Sidenav;
