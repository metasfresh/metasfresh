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
            height: 400
        };
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

    render() {
        const {
            text, framework, noData, maximizeWidget,
            hideWidgets, showWidgets, index, idMaximized, id, chartType,
            caption, fields, groupBy, pollInterval, editmode
        } = this.props;

        const {
            toggleWidgetMenu, height
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
                </div>
            </div>
        );
    }
}

export default ChartWidget;
