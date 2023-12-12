import produce from 'immer';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import counterpart from 'counterpart';

import { connectWS, disconnectWS } from '../../utils/websockets';
import {
  addDashboardWidget,
  changeKPIItem,
  changeTargetIndicatorsItem,
  getKPIsDashboard,
  getTargetIndicatorsDashboard,
  removeDashboardWidget,
} from '../../actions/DashboardActions';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import RawChart from '../charts/RawChart';
import ChartWidget from './ChartWidget';
import DndWidget from './DndWidget';
import Placeholder from './Placeholder';
import Sidenav from './Sidenav';
import EntityType from './EntityType';
import { ChartOptions } from './ChartOptions';

export class DraggableWrapper extends Component {
  state = {
    indicatorsLoaded: false,
    idMaximized: null,
    websocketEndpoint: null,
    editingDashboardItemId: null,
  };

  componentDidMount = () => {
    this.loadKPIs();
    this.loadTargetIndicators();
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

  onDashboardItemDataChanged = (wsEvent) => {
    const indicators = this.state[EntityType.TARGET_INDICATOR] ?? [];
    const kpis = this.state[EntityType.KPI] ?? [];

    const indicatorsNew = produce(indicators, (draft) => {
      const index = draft.findIndex(
        (indicator) => indicator.id === wsEvent.itemId
      );
      if (index !== -1) {
        draft[index] = { ...draft[index], data: wsEvent.data };
      }
    });

    const kpisNew = produce(kpis, (draft) => {
      const index = kpis.findIndex((kpi) => kpi.id === wsEvent.itemId);
      if (index !== -1) {
        draft[index] = { ...draft[index], data: wsEvent.data };
      }
    });

    this.setState({
      [EntityType.TARGET_INDICATOR]: indicatorsNew,
      [EntityType.KPI]: kpisNew,
    });
  };

  onDashboardStructureChanged = (wsEvent) => {
    switch (wsEvent.widgetType) {
      case 'TargetIndicator':
        this.loadTargetIndicators();
        break;
      case 'KPI':
        this.loadKPIs();
        break;
    }
  };

  loadTargetIndicators = () => {
    getTargetIndicatorsDashboard().then((response) => {
      this.setState({
        [EntityType.TARGET_INDICATOR]: response.data.items,
        indicatorsLoaded: true,
      });
    });
  };

  loadKPIs = () => {
    getKPIsDashboard().then((response) => {
      this.setState({
        [EntityType.KPI]: response.data.items,
        websocketEndpoint: response.data.websocketEndpoint,
      });
    });
  };

  getType = (entity) => {
    return entity === EntityType.KPI ? 'kpis' : 'targetIndicators';
  };

  onDrop = ({ entity, id, isNew, droppedOverId }) => {
    const position =
      this.state[entity]?.findIndex((item) => item.id === droppedOverId) ?? -1;

    console.log('onDrop', {
      entity,
      id,
      isNew,
      droppedOverId,
      position,
      state: this.state,
    });

    if (isNew) {
      addDashboardWidget(this.getType(entity), id, position);
      // NOTE: will be updated via websockets
    } else {
      if (entity === EntityType.KPI) {
        changeKPIItem(id, { position });
        // NOTE: will be updated via websockets
      } else if (entity === EntityType.TARGET_INDICATOR) {
        changeTargetIndicatorsItem(id, { position });
        // NOTE: will be updated via websockets
      } else {
        console.warn(`Unknown entity: ${entity}`);
      }
    }
  };

  onRemove = (entity, index, id) => {
    removeDashboardWidget(this.getType(entity), id);
    // NOTE: will be updated via websockets
  };

  maximizeWidget = (id) => {
    this.setState({ idMaximized: id });
  };

  renderIndicators = () => {
    const { editmode } = this.props;
    const targetIndicators = this.state[EntityType.TARGET_INDICATOR] ?? [];

    if (targetIndicators.length <= 0) {
      if (editmode) {
        return (
          <div className="indicators-wrapper">
            <DndWidget
              id="indicatorsPlaceholder"
              index={-1}
              entity={EntityType.TARGET_INDICATOR}
              placeholder={true}
              transparent={false}
              onDrop={this.onDrop}
            >
              <Placeholder
                entity={EntityType.TARGET_INDICATOR}
                description={counterpart.translate(
                  'dashboard.targetIndicators.dropContainer.caption'
                )}
              />
            </DndWidget>
          </div>
        );
      } else {
        return null;
      }
    } else {
      return (
        <div className={'indicators-wrapper'}>
          {targetIndicators.map((indicator, index) => (
            <DndWidget
              key={indicator.id}
              id={indicator.id}
              index={index}
              entity={EntityType.TARGET_INDICATOR}
              transparent={!editmode}
              className="indicator-card"
              onDrop={this.onDrop}
              onRemove={this.onRemove}
            >
              {
                <RawChart
                  key={indicator.id}
                  id={indicator.id}
                  index={index}
                  caption={indicator.caption}
                  fields={indicator.kpi.fields}
                  zoomToDetailsAvailable={
                    !!indicator.kpi.zoomToDetailsAvailable
                  }
                  chartType={'Indicator'}
                  kpi={false}
                  data={indicator.data}
                  noData={indicator.fetchOnDrop}
                  openChartOptions={this.openChartOptions}
                  editmode={editmode}
                />
              }
            </DndWidget>
          ))}
        </div>
      );
    }
  };

  renderEmptyPage = () => {
    const { editmode } = this.props;
    const { indicatorsLoaded } = this.state;

    // Don't show logo if edit mode is active
    if (editmode) return null;

    // Don't show logo if indicators/KPIs are not loaded yet
    if (!indicatorsLoaded) return null;

    // Don't show logo if we have Target Indicators
    const indicators = this.state[EntityType.TARGET_INDICATOR] ?? [];
    if (indicators.length > 0) return null;

    // Don't show logo if we have KPIs
    const kpis = this.state[EntityType.KPI] ?? [];
    if (kpis.length > 0) return null;

    return (
      <div className="dashboard-wrapper dashboard-logo-wrapper">
        <div className="logo-wrapper">
          <img src={logo} alt="logo" />
        </div>
      </div>
    );
  };

  renderKpis = () => {
    const { editmode } = this.props;
    const { idMaximized } = this.state;
    const kpis = this.state[EntityType.KPI] ?? [];

    if (kpis.length <= 0) {
      if (editmode) {
        return (
          <div className="kpis-wrapper">
            <DndWidget
              id="kpisPlaceholder"
              index={-1}
              placeholder={true}
              entity={EntityType.KPI}
              transparent={false}
              onDrop={this.onDrop}
            >
              <Placeholder
                entity={EntityType.KPI}
                description={counterpart.translate(
                  'dashboard.kpis.dropContainer.caption'
                )}
              />
            </DndWidget>
          </div>
        );
      } else {
        return null;
      }
    } else {
      return (
        <div className="kpis-wrapper">
          {kpis.map((item, index) => {
            return (
              <DndWidget
                key={item.id}
                index={index}
                id={item.id}
                entity={EntityType.KPI}
                className={
                  'draggable-widget ' +
                  (idMaximized === item.id ? 'draggable-widget-maximize ' : '')
                }
                transparent={!editmode}
                onDrop={this.onDrop}
                onRemove={this.onRemove}
              >
                <ChartWidget
                  key={item.id}
                  id={item.id}
                  index={index}
                  chartType={item.kpi.chartType}
                  caption={item.caption}
                  fields={item.kpi.fields}
                  groupBy={item.kpi.groupByField}
                  idMaximized={idMaximized}
                  maximizeWidget={this.maximizeWidget}
                  text={item.caption}
                  data={item.data}
                  noData={item.fetchOnDrop}
                  openChartOptions={this.openChartOptions}
                  editmode={editmode}
                />
              </DndWidget>
            );
          })}
        </div>
      );
    }
  };

  getDashboardItemById = (id) => {
    const indicatorsArray = this.state[EntityType.TARGET_INDICATOR] ?? [];
    const indicator = indicatorsArray.find((item) => item.id === id);
    if (indicator) {
      return { ...indicator, isIndicator: true };
    }

    const kpisArray = this.state[EntityType.KPI] ?? [];
    const kpi = kpisArray.find((item) => item.id === id);
    if (kpi) {
      return { ...kpi, isIndicator: false };
    }

    return null;
  };

  openChartOptions = (editingDashboardItemId) => {
    this.setState({ editingDashboardItemId });
  };

  closeChartOptions = () => {
    this.setState({ editingDashboardItemId: null });
  };

  render() {
    const { editmode } = this.props;
    const { editingDashboardItemId } = this.state;

    const dashboardItem =
      editingDashboardItemId > 0
        ? this.getDashboardItemById(editingDashboardItemId)
        : null;

    return (
      <DndProvider backend={HTML5Backend}>
        <div className="dashboard-cards-wrapper">
          {dashboardItem && (
            <ChartOptions
              id={dashboardItem.id}
              caption={dashboardItem.caption}
              isIndicator={dashboardItem.isIndicator}
              onClose={() => this.closeChartOptions()}
            />
          )}
          <div className={editmode ? 'dashboard-edit-mode' : 'dashboard-cards'}>
            {this.renderIndicators()}
            {this.renderKpis()}
          </div>
          {editmode && <Sidenav />}
          {this.renderEmptyPage()}
        </div>
      </DndProvider>
    );
  }
}

DraggableWrapper.propTypes = {
  editmode: PropTypes.bool,
};

export default DraggableWrapper;
