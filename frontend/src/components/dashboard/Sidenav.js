import React, { Component } from 'react';
import counterpart from 'counterpart';
import { getAvailableKPIsToAdd } from '../../actions/DashboardActions';
import Indicator from '../charts/Indicator';
import ChartWidget from './ChartWidget';
import DndWidget from './DndWidget';

import EntityType from './EntityType';

/**
 * Panel of Target Indicators and KPIs available to add
 */
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
        <div className="board-sidenav-header">
          {counterpart.translate('dashboard.addNew.targetIndicator')}
        </div>
        <div>{this.renderChartList(indicators)}</div>
        <div className="board-sidenav-header">
          {counterpart.translate('dashboard.addNew.kpi')}
        </div>
        <div>{this.renderChartList(cards)}</div>
      </div>
    );
  }

  renderChartList = (charts) => {
    if (!charts) return;
    return charts.map((item, index) => this.renderChartItem(item, index));
  };

  renderChartItem = (item, index) => {
    const isKPI = item.widgetTypes[0] === 'KPI';
    return (
      <DndWidget
        key={item.kpiId}
        id={item.kpiId}
        index={-1}
        entity={isKPI ? EntityType.KPI : EntityType.TARGET_INDICATOR}
        isNew={true}
        transparent={false}
        className="draggable-widget"
      >
        {isKPI ? this.renderKPI(item, index) : this.renderTargetIndicator(item)}
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

export default Sidenav;
