import React, { Component, PropTypes } from 'react';
import ItemTypes from '../../constants/ItemTypes';
import { DragSource, DropTarget } from 'react-dnd';
import onClickOutside from 'react-onclickoutside';

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
            refresh: false
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
            toggleWidgetMenu: false
        })
    }

    minimizeWidget = () => {
        this.setState({
            isMaximize: false,
            toggleWidgetMenu: false
        })
    }

    handleRefresh = () => {
        this.setState({
            refresh: true
        }, () => {
            this.setState({
                refresh: false,
                toggleWidgetMenu: false
            })
        });
    }

    render() {
        const {
            text, url, isDragging, connectDragSource, connectDropTarget,
            hideWidgets, showWidgets, index, idMaximized, id
        } = this.props;
        const { toggleWidgetMenu, isMaximize, refresh } = this.state;

        return connectDragSource(connectDropTarget(
            <div className={
                'draggable-widget' +
                (isMaximize ? ' draggable-widget-maximize' : '') +
                (isDragging ? ' dragging' : '') +
                ((idMaximized !== false) && !isMaximize ? ' hidden-xs-up' : '')
            } >
                <div className="draggable-widget-header">
                    {text}
                    <i className="draggable-widget-icon meta-icon-down-1 input-icon-sm" onClick={() => this.toggleMenu()}></i>
                    {toggleWidgetMenu &&
                        <div className="draggable-widget-menu">

                            { isMaximize ?
                                <span onClick={() => {this.minimizeWidget(); showWidgets()} }>Minimize</span> :
                                <span onClick={() => {this.maximizeWidget(); hideWidgets(index)} }>Maximize</span>
                            }

                            <span onClick={this.handleRefresh}>Refresh</span>
                        </div>
                    }
                </div>
                {this.props.dashboard == '/dashboard2' &&
                    <div className="draggable-widget-body">
                        
                    </div>
                }
                {this.props.dashboard == '/dashboard1' &&
                    <div className="draggable-widget-body">
                        <iframe
                            src={!refresh && url}
                            scrolling="no"
                            frameBorder="no"
                        ></iframe>
                    </div>
                }

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

DraggableWidget = DragSource(ItemTypes.DRAGGABLE_CARD, cardSource, collect)(DropTarget(ItemTypes.DRAGGABLE_CARD, cardTarget, connect)(onClickOutside(DraggableWidget)));

export default DraggableWidget;
