import React, { useCallback, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { DndProvider } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import counterpart from 'counterpart';
import produce from 'immer';

import { useWebsocket } from '../../hooks/useWebsocket';
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

const DraggableWrapper = ({ editmode }) => {
  // Separate useState for each logical piece of state
  const [indicatorsLoaded, setIndicatorsLoaded] = useState(false);
  const [idMaximized, setIdMaximized] = useState(null);
  const [websocketEndpoint, setWebsocketEndpoint] = useState(null);
  const [editingDashboardItemId, setEditingDashboardItemId] = useState(null);
  const [targetIndicators, setTargetIndicators] = useState([]);
  const [kpis, setKpis] = useState([]);

  // Data loading functions - useCallback to prevent useEffect infinite loops
  const loadTargetIndicators = useCallback(() => {
    getTargetIndicatorsDashboard().then((response) => {
      setTargetIndicators(response.data.items);
      setIndicatorsLoaded(true);
    });
  }, []);

  const loadKPIs = useCallback(() => {
    getKPIsDashboard().then((response) => {
      setKpis(response.data.items);
      setWebsocketEndpoint(response.data.websocketEndpoint);
    });
  }, []);

  // WebSocket event handlers - useCallback for complex state updates
  const onDashboardItemDataChanged = useCallback((wsEvent) => {
    setTargetIndicators((prevIndicators) =>
      produce(prevIndicators, (draft) => {
        const index = draft.findIndex(
          (indicator) => indicator.id === wsEvent.itemId
        );
        if (index !== -1) {
          draft[index] = { ...draft[index], data: wsEvent.data };
        }
      })
    );

    setKpis((prevKpis) =>
      produce(prevKpis, (draft) => {
        const index = draft.findIndex((kpi) => kpi.id === wsEvent.itemId);
        if (index !== -1) {
          draft[index] = { ...draft[index], data: wsEvent.data };
        }
      })
    );
  }, []);

  const onDashboardStructureChanged = useCallback(
    (wsEvent) => {
      switch (wsEvent.widgetType) {
        case 'TargetIndicator':
          loadTargetIndicators();
          break;
        case 'KPI':
          loadKPIs();
          break;
      }
    },
    [loadTargetIndicators, loadKPIs]
  );

  const onWebsocketEvent = useCallback(
    (event) => {
      switch (event.changeType) {
        case 'itemDataChanged':
          onDashboardItemDataChanged(event);
          break;
        case 'dashboardChanged':
        case 'itemChanged':
          onDashboardStructureChanged(event);
          break;
      }
    },
    [onDashboardItemDataChanged, onDashboardStructureChanged]
  );

  // Event handlers passed to child components - useCallback to prevent child re-renders
  const onDrop = useCallback(
    ({ entity, id, isNew, droppedOverId }) => {
      const currentItems = entity === EntityType.KPI ? kpis : targetIndicators;
      const position =
        currentItems.findIndex((item) => item.id === droppedOverId) ?? -1;

      console.log('onDrop', {
        entity,
        id,
        isNew,
        droppedOverId,
        position,
      });

      if (isNew) {
        const type = entity === EntityType.KPI ? 'kpis' : 'targetIndicators';
        addDashboardWidget(type, id, position);
      } else {
        if (entity === EntityType.KPI) {
          changeKPIItem(id, { position });
        } else if (entity === EntityType.TARGET_INDICATOR) {
          changeTargetIndicatorsItem(id, { position });
        } else {
          console.warn(`Unknown entity: ${entity}`);
        }
      }
    },
    [kpis, targetIndicators]
  );

  const onRemove = useCallback((entity, index, id) => {
    const type = entity === EntityType.KPI ? 'kpis' : 'targetIndicators';
    removeDashboardWidget(type, id);
  }, []);

  // Simple functions - no useCallback needed
  const maximizeWidget = (id) => {
    setIdMaximized(id);
  };

  const openChartOptions = (editingDashboardItemId) => {
    setEditingDashboardItemId(editingDashboardItemId);
  };

  const closeChartOptions = () => {
    setEditingDashboardItemId(null);
  };

  const getDashboardItemById = (id) => {
    const indicator = targetIndicators.find((item) => item.id === id);
    if (indicator) {
      return { ...indicator, isIndicator: true };
    }

    const kpi = kpis.find((item) => item.id === id);
    if (kpi) {
      return { ...kpi, isIndicator: false };
    }

    return null;
  };

  // WebSocket connection
  useWebsocket({
    topic: websocketEndpoint,
    onMessage: (msg) => {
      msg.event.events.map((event) => onWebsocketEvent(event));
    },
  });

  // Initial data loading
  useEffect(() => {
    loadKPIs();
    loadTargetIndicators();
  }, [loadKPIs, loadTargetIndicators]);

  // Render functions
  const renderIndicators = () => {
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
              onDrop={onDrop}
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
              onDrop={onDrop}
              onRemove={onRemove}
            >
              <RawChart
                key={indicator.id}
                id={indicator.id}
                index={index}
                caption={indicator.caption}
                fields={indicator.kpi.fields}
                zoomToDetailsAvailable={!!indicator.kpi.zoomToDetailsAvailable}
                chartType={'Indicator'}
                kpi={false}
                data={indicator.data}
                noData={indicator.fetchOnDrop}
                openChartOptions={openChartOptions}
                editmode={editmode}
              />
            </DndWidget>
          ))}
        </div>
      );
    }
  };

  const renderEmptyPage = () => {
    // Don't show logo if edit mode is active
    if (editmode) return null;

    // Don't show logo if indicators/KPIs are not loaded yet
    if (!indicatorsLoaded) return null;

    // Don't show logo if we have Target Indicators
    if (targetIndicators.length > 0) return null;

    // Don't show logo if we have KPIs
    if (kpis.length > 0) return null;

    return (
      <div className="dashboard-wrapper dashboard-logo-wrapper">
        <div className="logo-wrapper">
          <img src={logo} alt="logo" />
        </div>
      </div>
    );
  };

  const renderKpis = () => {
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
              onDrop={onDrop}
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
                onDrop={onDrop}
                onRemove={onRemove}
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
                  maximizeWidget={maximizeWidget}
                  text={item.caption}
                  data={item.data}
                  noData={item.fetchOnDrop}
                  openChartOptions={openChartOptions}
                  editmode={editmode}
                />
              </DndWidget>
            );
          })}
        </div>
      );
    }
  };

  // Main render
  const dashboardItem =
    editingDashboardItemId > 0
      ? getDashboardItemById(editingDashboardItemId)
      : null;

  return (
    <DndProvider backend={HTML5Backend}>
      <div className="dashboard-cards-wrapper">
        {dashboardItem && (
          <ChartOptions
            id={dashboardItem.id}
            caption={dashboardItem.caption}
            isIndicator={dashboardItem.isIndicator}
            onClose={closeChartOptions}
          />
        )}
        <div className={editmode ? 'dashboard-edit-mode' : 'dashboard-cards'}>
          {renderIndicators()}
          {renderKpis()}
        </div>
        {editmode && <Sidenav />}
        {renderEmptyPage()}
      </div>
    </DndProvider>
  );
};

DraggableWrapper.propTypes = {
  editmode: PropTypes.bool,
};

export default DraggableWrapper;
