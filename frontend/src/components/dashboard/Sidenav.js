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

  renderChartList = (charts) => {
    const { moveCard } = this.props;
    if (!charts) return;
    return charts.map((item, i) => (
      <DndWidget
        key={i}
        id={item.kpiId}
        index={item.kpiId}
        moveCard={moveCard}
        isNew={true}
        entity={item.widgetTypes[0] === 'KPI' ? 'cards' : 'indicators'}
        transparent={false}
      >
        {item.widgetTypes[0] === 'KPI' ? (
          <ChartWidget
            id={item.kpiId}
            index={i}
            chartType={item.chartType}
            kpi={true}
            text={item.caption}
            framework={true}
            isMaximized={false}
          />
        ) : (
          <Indicator
            fullWidth={true}
            value={item.chartType}
            caption={item.caption}
            framework={true}
          />
        )}
      </DndWidget>
    ));
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
}

Sidenav.propTypes = {
  moveCard: PropTypes.func,
};

export default Sidenav;
