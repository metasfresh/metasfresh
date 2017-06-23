import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ItemTypes from '../../constants/ItemTypes';
import { DragSource, DropTarget } from 'react-dnd';
import onClickOutside from 'react-onclickoutside';
import RawChart from '../charts/RawChart';

export class ChartWidget extends Component {
    constructor(props) {
        super(props);
        this.state = {
            toggleWidgetMenu: false,
            isMaximize: false,
            height: 400
        };
    }

    handleClickOutside = () => {
        this.setState({
            toggleWidgetMenu: false
        })
    }

    toggleMenu = () => {
        const { toggleWidgetMenu } = this.state;
        this.setState({
            toggleWidgetMenu: !toggleWidgetMenu
        })
    }

    maximizeWidget = () => {
        this.setState({
            isMaximize: true,
            toggleWidgetMenu: false,
            height: 500
        })
    }

    minimizeWidget = () => {
        this.setState({
            isMaximize: false,
            toggleWidgetMenu: false,
            height: 400
        })
    }

    render() {
        const {
            text, framework, noData,
            hideWidgets, showWidgets, index, idMaximized, id, chartType,
            caption, fields, groupBy, pollInterval, editmode
        } = this.props;

        const {
            toggleWidgetMenu, isMaximize, height
        } = this.state;

        return (
            <div className={
                (isMaximize ? ' draggable-widget-maximize' : '') +
                ((idMaximized !== false) && !isMaximize ? ' hidden-xs-up' : '')
            }>
                <div
                    className="draggable-widget-header"
                    onDoubleClick={isMaximize ?
                        () => {this.minimizeWidget(); showWidgets() } :
                        () => {this.maximizeWidget(); hideWidgets(index)}}
                >
                    {text}
                    {!editmode && <i
                        className="draggable-widget-icon meta-icon-down-1 input-icon-sm"
                        onClick={() => this.toggleMenu()}
                    />}
                    {toggleWidgetMenu && !editmode &&
                        <div className="draggable-widget-menu">
                            { isMaximize ?
                                <span
                                    onClick={() => {
                                        this.minimizeWidget();
                                        showWidgets()
                                    }}
                                >
                                    Minimize
                                </span> :
                                <span
                                    onClick={() => {
                                        this.maximizeWidget();
                                        hideWidgets(index)
                                    }}
                                >
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
                            pollInterval, height, isMaximize, id, noData
                        }}
                        responsive={true}
                        chartTitle={text}
                    /> : 
                        <div>{chartType}</div>
                    }
                </div>
            </div>
        );
    }
}

export default ChartWidget;
