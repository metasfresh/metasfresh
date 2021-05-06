import update from 'immutability-helper';
import produce from 'immer';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { DragDropContext } from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import { connect } from 'react-redux';
import { List } from 'immutable';

import { connectWS, disconnectWS } from '../../utils/websockets';
import {
  changeKPIItem,
  changeTargetIndicatorsItem,
  getKPIsDashboard,
  getTargetIndicatorsDashboard,
} from '../../actions/DashboardActions';
import {
  addDashboardWidget,
  removeDashboardWidget,
} from '../../actions/BoardActions';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import RawChart from '../charts/RawChart';
import RawList from '../widget/List/RawList';
import ChartWidget from './ChartWidget';
import DndWidget from './DndWidget';
import Placeholder from './Placeholder';
import Sidenav from './Sidenav';

export class DraggableWrapper extends Component {
  state = {
    cards: [],
    indicators: [],
    idMaximized: null,
    websocketEndpoint: null,
    chartOptions: false,
    captionHandler: '',
    when: '',
    interval: '',
    currentId: '',
    isIndicator: '',
    listFocused: null,
    listToggled: null,
  };

  componentDidMount = () => {
    this.getDashboard();
    this.getIndicators();
  };

  componentDidUpdate = (prevProps, prevState) => {
    const { websocketEndpoint } = this.state;
    if (
      websocketEndpoint !== null &&
      prevState.websocketEndpoint !== websocketEndpoint
    ) {
      connectWS.call(this, websocketEndpoint, (msg) => {
        msg.events.map((event) => this.onWebsocketEvent(event));
      });
    }
  };

  componentWillUnmount = () => {
    disconnectWS.call(this);
  };

  handleFocus = (name) => {
    this.setState({
      listFocused: name,
    });
  };

  handleBlur = () => {
    this.setState({
      listFocused: null,
    });
  };

  closeDropdown = () => {
    this.setState({
      listToggled: null,
    });
  };

  openDropdown = (name) => {
    this.setState({
      listToggled: name,
    });
  };

  onWebsocketEvent = (event) => {
    switch (event.changeType) {
      case 'itemDataChanged':
        this.onDashboardItemDataChanged(event);
        break;
      case 'dashboardChanged':
      case 'itemChanged':
        this.onDashboardStructureChanged(event);
        break;
    }
  };

  onDashboardItemDataChanged = (event) => {
    const { indicators, cards } = this.state;

    const indicatorsNew = produce(indicators, (draft) => {
      const index = draft.findIndex(
        (indicator) => indicator.id === event.itemId
      );
      if (index !== -1) {
        draft[index] = { ...draft[index], data: event.data };
      }
    });

    const cardsNew = produce(cards, (draft) => {
      const index = cards.findIndex((card) => card.id === event.itemId);
      if (index !== -1) {
        draft[index] = { ...draft[index], data: event.data };
      }
    });

    this.setState({ cards: cardsNew, indicators: indicatorsNew });
  };

  onDashboardStructureChanged = (event) => {
    switch (event.widgetType) {
      case 'TargetIndicator':
        this.getIndicators();
        break;
      case 'KPI':
        this.getDashboard();
        break;
    }
  };

  getIndicators = () => {
    getTargetIndicatorsDashboard().then((response) => {
      this.setState({
        indicators: response.data.items,
      });
    });
  };

  getDashboard = () => {
    getKPIsDashboard().then((response) => {
      this.setState({
        cards: response.data.items,
        websocketEndpoint: response.data.websocketEndpoint,
      });
    });
  };

  getType = (entity) => (entity === 'cards' ? 'kpis' : 'targetIndicators');

  addCard = (entity, id) => {
    const tmpItemIndex = this.state[entity].findIndex((i) => i.id === id);
    addDashboardWidget(this.getType(entity), id, tmpItemIndex).then((res) => {
      this.setState((prev) =>
        update(prev, {
          [entity]: {
            [tmpItemIndex]: { $set: res.data },
          },
        })
      );
    });
  };

  onDrop = (entity, id) => {
    const position = this.state[entity].findIndex((i) => i.id === id);
    console.log('entity=%o, id=%o, position=%o', entity, id, position);

    if (entity === 'cards') {
      changeKPIItem(id, 'position', position);
    } else {
      changeTargetIndicatorsItem(id, 'position', position);
    }
  };

  moveCard = (entity, dragIndex, hoverIndex, item) => {
    const draggedItem = this.state[entity][dragIndex];
    if (draggedItem) {
      // When we are inserting added
      this.setState((prev) =>
        update(prev, {
          [entity]: {
            $splice: [[dragIndex, 1], [hoverIndex, 0, draggedItem]],
          },
        })
      );
    } else {
      // When we are adding card
      const newItem = {
        id: item.id,
        fetchOnDrop: true,
        kpi: { chartType: this.getType(entity) },
      };
      this.setState((prev) =>
        update(prev, {
          [entity]:
            prev[entity].length === 0
              ? {
                  $set: [newItem],
                }
              : {
                  $splice: [[dragIndex, 1], [hoverIndex, 0, newItem]],
                },
        })
      );
    }
  };

  removeCard = (entity, index, id) => {
    removeDashboardWidget(this.getType(entity), id);
    this.setState((prev) =>
      update(prev, {
        [entity]: {
          $splice: [[index, 1]],
        },
      })
    );
  };

  maximizeWidget = (id) => {
    this.setState({
      idMaximized: id,
    });
  };

  renderIndicators = () => {
    const { indicators } = this.state;
    const { editmode } = this.props;

    if (!indicators.length && editmode)
      return (
        <div className="indicators-wrapper">
          <DndWidget
            moveCard={this.moveCard}
            addCard={this.addCard}
            entity={'indicators'}
            placeholder={true}
            transparent={!editmode}
          >
            <Placeholder
              entity={'indicators'}
              description={'Drop Target Indicator widget here.'}
            />
          </DndWidget>
        </div>
      );

    if (!indicators.length) return false;

    return (
      <div className={'indicators-wrapper'}>
        {indicators.map((indicator, id) => (
          <DndWidget
            key={id}
            id={indicator.id}
            index={id}
            moveCard={this.moveCard}
            addCard={this.addCard}
            onDrop={this.onDrop}
            removeCard={this.removeCard}
            entity={'indicators'}
            transparent={!editmode}
          >
            <RawChart
              id={indicator.id}
              index={id}
              caption={indicator.caption}
              fields={indicator.kpi.fields}
              chartType={'Indicator'}
              kpi={false}
              data={indicator.data}
              noData={indicator.fetchOnDrop}
              handleChartOptions={this.handleChartOptions}
              {...{ editmode }}
            />
          </DndWidget>
        ))}
      </div>
    );
  };

  renderKpis = () => {
    const { cards, idMaximized } = this.state;
    const { editmode } = this.props;

    if (!cards.length && editmode)
      return (
        <div className="kpis-wrapper">
          <DndWidget
            placeholder={true}
            entity={'cards'}
            moveCard={this.moveCard}
            addCard={this.addCard}
            transparent={!editmode}
          >
            <Placeholder
              entity={'cards'}
              description={'Drop KPI widget here.'}
            />
          </DndWidget>
        </div>
      );

    return (
      <div className="kpis-wrapper">
        {cards.length > 0 ? (
          cards.map((item, id) => {
            return (
              <DndWidget
                key={id}
                index={id}
                id={item.id}
                moveCard={this.moveCard}
                addCard={this.addCard}
                onDrop={this.onDrop}
                removeCard={this.removeCard}
                entity={'cards'}
                className={
                  'draggable-widget ' +
                  (idMaximized === item.id ? 'draggable-widget-maximize ' : '')
                }
                transparent={!editmode}
              >
                <ChartWidget
                  key={item.id}
                  id={item.id}
                  index={id}
                  chartType={item.kpi.chartType}
                  caption={item.caption}
                  fields={item.kpi.fields}
                  groupBy={item.kpi.groupByField}
                  kpi={true}
                  moveCard={this.moveCard}
                  idMaximized={idMaximized}
                  maximizeWidget={this.maximizeWidget}
                  text={item.caption}
                  data={item.data}
                  noData={item.fetchOnDrop}
                  handleChartOptions={this.handleChartOptions}
                  {...{ editmode }}
                />
              </DndWidget>
            );
          })
        ) : (
          <div className="dashboard-wrapper dashboard-logo-wrapper">
            <div className="logo-wrapper">
              <img src={logo} />
            </div>
          </div>
        )}
      </div>
    );
  };

  renderOptionModal = () => {
    const {
      chartOptions,
      captionHandler,
      when,
      interval,
      listFocused,
      listToggled,
    } = this.state;

    return (
      chartOptions && (
        <div className="chart-options-overlay">
          <div className="chart-options-wrapper">
            <div className="chart-options">
              <div className="form-group">
                <label>caption</label>
                <input
                  className="input-options input-secondary"
                  value={captionHandler}
                  onChange={this.handleChange}
                />
              </div>
              <div className="form-group">
                <label>interval</label>
                <div className="chart-options-list-wrapper">
                  <RawList
                    onSelect={(option) =>
                      this.handleOptionSelect('interval', option)
                    }
                    tabIndex={0}
                    list={List([{ caption: 'week', value: 'week' }])}
                    selected={interval}
                    isFocused={listFocused === 'interval'}
                    isToggled={listToggled === 'interval'}
                    onFocus={() => this.handleFocus('interval')}
                    onBlur={() => this.handleBlur('interval')}
                    onOpenDropdown={() => this.openDropdown('interval')}
                    onCloseDropdown={() => this.closeDropdown('interval')}
                  />
                </div>
              </div>
              <div className="form-group">
                <label>when</label>
                <div className="chart-options-list-wrapper">
                  <RawList
                    onSelect={(option) =>
                      this.handleOptionSelect('when', option)
                    }
                    list={List([
                      { caption: 'now', value: 'now' },
                      {
                        caption: 'last week',
                        value: 'lastWeek',
                      },
                    ])}
                    tabIndex={0}
                    selected={when}
                    isFocused={listFocused === 'when'}
                    isToggled={listToggled === 'when'}
                    onFocus={() => this.handleFocus('when')}
                    onBlur={() => this.handleBlur('when')}
                    onOpenDropdown={() => this.openDropdown('when')}
                    onCloseDropdown={() => this.closeDropdown('when')}
                  />
                </div>
              </div>
            </div>
            <div className="chart-options-button-wrapper">
              <button
                className="btn btn-meta-outline-secondary btn-sm"
                onClick={() => this.changeChartData('caption', captionHandler)}
              >
                Save
              </button>
            </div>
          </div>
        </div>
      )
    );
  };

  handleChartOptions = (opened, caption, id, isIndicator) => {
    this.setState({
      chartOptions: opened,
      captionHandler: caption,
      currentId: id,
      when: opened ? this.state.when : '',
      interval: opened ? this.state.interval : '',
      isIndicator: !!isIndicator,
    });
  };

  handleOptionSelect = (path, option) => {
    const { currentId, isIndicator } = this.state;
    if (isIndicator) {
      changeTargetIndicatorsItem(currentId, path, option.value).then(() => {
        this.setSelectedOption(path, option);
      });
    } else {
      changeKPIItem(currentId, path, option.value).then(() => {
        this.setSelectedOption(path, option);
      });
    }
  };

  setSelectedOption = (path, option) => {
    const { when, interval } = this.state;
    this.setState({
      when: path === 'when' ? option : when,
      interval: path === 'interval' ? option : interval,
    });
  };

  handleChange = (e) => {
    this.setState({
      captionHandler: e.target.value,
    });
  };

  changeChartData = (path, value) => {
    const { currentId, isIndicator } = this.state;
    if (isIndicator) {
      changeTargetIndicatorsItem(currentId, path, value).then(() => {
        this.handleChartOptions(false);
      });
    } else {
      changeKPIItem(currentId, path, value).then(() => {
        this.handleChartOptions(false);
      });
    }
  };

  render() {
    const { editmode } = this.props;

    return (
      <div className="dashboard-cards-wrapper">
        {this.renderOptionModal()}
        <div className={editmode ? 'dashboard-edit-mode' : 'dashboard-cards'}>
          {this.renderIndicators()}
          {this.renderKpis()}
        </div>
        {editmode && <Sidenav addCard={this.addCard} />}
      </div>
    );
  }
}

DraggableWrapper.propTypes = {
  dispatch: PropTypes.func.isRequired,
  editmode: PropTypes.bool,
};

export default connect()(DragDropContext(HTML5Backend)(DraggableWrapper));
