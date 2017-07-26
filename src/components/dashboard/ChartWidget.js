import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ItemTypes from '../../constants/ItemTypes';
import { DragSource, DropTarget } from 'react-dnd';
import onClickOutside from 'react-onclickoutside';
import RawChart from '../charts/RawChart';

import {changeKPIItem} from '../../actions/AppActions';

export class ChartWidget extends Component {
    constructor(props) {
        super(props);
        this.state = {
            toggleWidgetMenu: false,
            height: 400,
            chartOptions: false
        };
    }

    componentDidMount(){
        const {text} = this.props;

        this.setState({
            captionHandler: text
        });
    }

    handleClickOutside = () => {
        this.setState({
            toggleWidgetMenu: false
        })
    }

    toggleMenu = (opt) => {
        const { toggleWidgetMenu } = this.state;
        this.setState({
            toggleWidgetMenu: typeof opt === 'boolean' ? opt : !toggleWidgetMenu
        })
    }

    handleChange = (e) => {
        this.setState({
            captionHandler: e.target.value
        });
    }

    handleChartOptions = (opened) => {
        this.setState({
            chartOptions: opened
        });
    }

    changeChartData = (path, value) => {
        const {id} = this.props;
        changeKPIItem(id, path, value).then(() => {
            this.handleChartOptions(false);
        });
    }

    render() {
        const {
            text, framework, noData, maximizeWidget,
            hideWidgets, showWidgets, index, idMaximized, id, chartType,
            caption, fields, groupBy, pollInterval, editmode
        } = this.props;

        const {
            toggleWidgetMenu, height, captionHandler, chartOptions
        } = this.state;
        
        const isMaximized = idMaximized === id;
        if(!isMaximized && typeof idMaximized === 'number') return false;
        
        return (
            <div>
                <div
                    className={
                        "draggable-widget-header " +
                        (editmode ? 'draggable-widget-edited ' : '')
                    }
                    onDoubleClick={!editmode && (() => {
                        isMaximized ?
                            maximizeWidget() : maximizeWidget(id);
                        this.toggleMenu(false);
                    })}
                >
                    <p
                        className="draggable-widget-title"
                    >
                        {text}
                        {editmode ? 
                            <span
                                className="chart-edit-mode"
                                onClick={() => this.handleChartOptions(true)}
                            >
                                <i className="meta-icon-settings"></i>
                            </span>
                        : ''}
                    </p>
                    {!editmode && !framework && <i
                        className="draggable-widget-icon meta-icon-down-1 input-icon-sm"
                        onClick={() => this.toggleMenu()}
                    />}
                    {toggleWidgetMenu && !editmode &&
                        <div className="draggable-widget-menu">
                            { isMaximized ?
                                <span onClick={() => {
                                        maximizeWidget();
                                        this.toggleMenu(false)}
                                    }>
                                    Minimize
                                </span> :
                                <span onClick={() => {
                                        maximizeWidget(id);
                                        this.toggleMenu(false)}
                                    }>
                                    Maximize
                                </span>
                            }

                        </div>
                    }
                </div>

                <div className="draggable-widget-body">
                    {!framework ? <RawChart
                        {...{
                            index, chartType, caption, fields, groupBy,
                            pollInterval, height, isMaximized, id, noData
                        }}
                        responsive={true}
                        chartTitle={text}
                    /> : 
                        <div>{chartType}</div>
                    }
                    {  chartOptions && 
                        <div className="chart-options-overlay">
                            <div className="chart-options-wrapper">
                                <div className="chart-options">
                                    <div className="form-group">
                                        <label>caption</label>
                                        <input 
                                            className="input-secondary"
                                            value={captionHandler}
                                            onChange={this.handleChange}
                                        />
                                        
                                    </div>
                                    {/*<div className="form-group">
                                        <label>interval</label>
                                        <input 
                                            className="input-secondary"
                                            value={captionHandler}
                                            onChange={this.handleChange}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label>when</label>
                                        <input 
                                            className="input-secondary"
                                            value={captionHandler}
                                            onChange={this.handleChange}
                                        />
                                    </div>*/}
                                </div>
                                <div className="chart-options-button-wrapper">
                                    <button 
                                        className="btn btn-meta-outline-secondary btn-sm"
                                        onClick={() => this.changeChartData("caption", captionHandler )}
                                    >
                                        Save
                                    </button>
                                </div>
                            </div>
                        </div>
                    }
                    
                    
                </div>
            </div>
        );
    }
}

export default ChartWidget;
