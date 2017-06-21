import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import update from 'react/lib/update';
import ChartWidget from './ChartWidget';
import { DragDropContext } from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import RawChart from '../charts/RawChart';
import DndWidget from './DndWidget';
import Sidenav from '../dashboard/Sidenav';
import Indicator from '../charts/Indicator';

import {
    getKPIsDashboard,
    getTargetIndicatorsDashboard
} from '../../actions/AppActions';

export class DraggableWrapper extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cards: [],
            isVisible: true,
            idMaximized: false,
            indicators: [],
            sidenav: null
        };
    }
    
    componentDidMount = () => {
        this.getDashboard();
        this.getIndicators();
    }
    
    getIndicators = () => {
        getTargetIndicatorsDashboard().then(response => {
            this.setState({
                indicators: response.data.items
            });
        });
    }
    
    getDashboard = () => {
        getKPIsDashboard().then(response => {
            this.setState({
                cards: response.data.items
            });
        });
    }
    
    moveCard = (entity, dragIndex, hoverIndex) => {
        const draggedItem = this.state[entity][dragIndex];
        this.setState(prev => update(prev, {
            [entity]: {
                $splice: [
                    [dragIndex, 1],
                    [hoverIndex, 0, draggedItem]
                ]
            }
        }));
    }
    
    removeCard = (entity, index) => {
        this.setState(prev => update(prev, {
            [entity]: {
                $splice: [
                    [index, 1]
                ]
            }
        }));
    }
    
    hideWidgets = (id) => {
        this.setState({
            isVisible: false,
            idMaximized: id
        })
    }
    
    showWidgets = () => {
        this.setState({
            isVisible: true,
            idMaximized: false
        })
    }
    
    renderIndicators = () => {
        const {indicators, idMaximized, editmode} = this.state;
        
        if(!indicators.length && editmode) return (
            <div className='indicators-wrapper'>
                <DndWidget
                    placeholder={true}
                    entity={'indicators'}
                >
                    <Indicator />
                </DndWidget>
            </div>
        );
        
        if(!indicators.length) return false;
        
        return (
            <div
                className={
                    'indicators-wrapper ' +
                    (idMaximized !== false ? 'indicator-hidden' : '')
                }
            >
                {indicators.map((indicator, id) =>
                    <DndWidget
                        key={id}
                        index={id}
                        moveCard={this.moveCard}
                        removeCard={this.removeCard}
                        entity={'indicators'}
                        transparent={!editmode}
                    >
                        <RawChart
                            id={indicator.id}
                            index={id}
                            caption={indicator.caption}
                            fields={indicator.kpi.fields}
                            pollInterval={indicator.kpi.pollIntervalSec}
                            chartType={'Indicator'}
                            kpi={false}
                        />
                    </DndWidget>
                )}
            </div>
        )
    }
    
    renderKpis = () => {
        const {cards, idMaximized, editmode} = this.state;
        
        return (
            <div className="kpis-wrapper">
                {cards.length > 0 ? cards.map((item, id) => {
                    return (
                        <DndWidget
                            key={id}
                            index={id}
                            moveCard={this.moveCard}
                            removeCard={this.removeCard}
                            entity={'cards'}
                            className="draggable-widget"
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
                                pollInterval={item.kpi.pollIntervalSec}
                                kpi={true}
                                moveCard={this.moveCard}
                                hideWidgets={this.hideWidgets}
                                showWidgets={this.showWidgets}
                                idMaximized={idMaximized}
                                text={item.caption}
                            />
                        </DndWidget>
                    );
                }) :
                <div className="dashboard-wrapper dashboard-logo-wrapper">
                    <div className="logo-wrapper">
                        <img src={logo} />
                    </div>
                </div>}
            </div>
        )
    }
    
    render() {
        const {editmode} = this.state;
        
        return (
            <div className="dashboard-cards-wrapper">
                <div className={(editmode ? 'dashboard-edit-mode' : '')}>
                    <div className="dashboard-edit-bar clearfix">
                        <button
                            className="btn btn-meta-outline-secondary btn-xs float-xs-right"
                            onClick={() => this.setState(prev => ({editmode: !prev.editmode}))}
                        >
                            <i className="meta-icon-settings" /> {editmode ? 'Close edit mode' : 'Open edit mode'}
                        </button>
                    </div>
                    {this.renderIndicators()}
                    {this.renderKpis()}
                </div>
                {editmode &&
                    <Sidenav />
                }
            </div>
        );
    }
}
    
DraggableWrapper.propTypes = {
    dispatch: PropTypes.func.isRequired
};

DraggableWrapper = connect()(DragDropContext(HTML5Backend)(DraggableWrapper));

export default DraggableWrapper;
