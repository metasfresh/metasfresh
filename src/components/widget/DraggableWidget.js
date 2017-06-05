import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ItemTypes from '../../constants/ItemTypes';
import { DragSource, DropTarget } from 'react-dnd';
import onClickOutside from 'react-onclickoutside';
import RawChart from '../charts/RawChart';

const cardSource = {
    beginDrag(props) {
        return {
            id: props.id,
            index: props.index
        };
    }
};

const cardTarget = {
    hover(props, monitor) {
        const dragIndex = monitor.getItem().index;
        const hoverIndex = props.index;

        // Don't replace items with themselves
        if (dragIndex === hoverIndex) {
            return;
        }

        // Time to actually perform the action
        props.moveCard(dragIndex, hoverIndex);

        // Note: we're mutating the monitor item here!
        // Generally it's better to avoid mutations,
        // but it's good here for the sake of performance
        // to avoid expensive index searches.
        monitor.getItem().index = hoverIndex;
    }
};

function collect(connect, monitor) {
    return {
        connectDragSource: connect.dragSource(),
        isDragging: monitor.isDragging()
    };
}

function connect(connect) {
    return {
        connectDropTarget: connect.dropTarget()
    };
}

export class DraggableWidget extends Component {
    constructor(props) {
        super(props);
        this.state = {
            toggleWidgetMenu: false,
            isMaximize: false,
            height: 400
        };
    }

    componentDidUpdate(prevProps) {
        if(prevProps !== this.props) {
            this.setState({
                forceChartReRender: true,
            }, () => {
                this.setState({
                    forceChartReRender: false
                })
            })
        }
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
            forceChartReRender: true,
            height: 500
        }, () => {
            this.setState({
                forceChartReRender: false
            })
        })
    }

    minimizeWidget = () => {
        this.setState({
            isMaximize: false,
            toggleWidgetMenu: false,
            forceChartReRender: true,
            height: 400
        }, () => {
            this.setState({
                forceChartReRender: false
            })
        })
    }

    render() {
        const {
            text, isDragging, connectDragSource, connectDropTarget,
            hideWidgets, showWidgets, index, idMaximized, id, chartType,
            caption, fields, groupBy, pollInterval
        } = this.props;

        const {
            toggleWidgetMenu, isMaximize, forceChartReRender, height
        } = this.state;

        return connectDragSource(connectDropTarget(
            <div className={
                'draggable-widget' +
                (isMaximize ? ' draggable-widget-maximize' : '') +
                (isDragging ? ' dragging' : '') +
                ((idMaximized !== false) && !isMaximize ? ' hidden-xs-up' : '')
            } >
                <div
                    className="draggable-widget-header"
                    onDoubleClick={isMaximize ?
                        () => {this.minimizeWidget(); showWidgets() } :
                        () => {this.maximizeWidget(); hideWidgets(index)}}
                >
                    {text}
                    <i
                        className="draggable-widget-icon meta-icon-down-1 input-icon-sm"
                        onClick={() => this.toggleMenu()}
                    />
                    {toggleWidgetMenu &&
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
                    <RawChart
                        id={id}
                        chartType={chartType}
                        caption={caption}
                        fields={fields}
                        groupBy={groupBy}
                        pollInterval={pollInterval}
                        reRender={forceChartReRender}
                        responsive={true}
                        height={height}
                        isMaximize={isMaximize}
                        chartTitle={text}
                    />
                </div>
            </div>

            ));
    }
}

DraggableWidget.propTypes = {
    connectDragSource: PropTypes.func.isRequired,
    connectDropTarget: PropTypes.func.isRequired,
    index: PropTypes.number.isRequired,
    isDragging: PropTypes.bool.isRequired,
    id: PropTypes.any.isRequired,
    text: PropTypes.string.isRequired,
    moveCard: PropTypes.func.isRequired
};

DraggableWidget =
    DragSource(ItemTypes.DRAGGABLE_CARD, cardSource, collect)(
        DropTarget(ItemTypes.DRAGGABLE_CARD, cardTarget, connect)(
            onClickOutside(DraggableWidget
        )
    ));

export default DraggableWidget;
