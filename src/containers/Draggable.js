import React, { Component, PropTypes } from 'react';
// import { ItemTypes } from './Constants';
import { DragSource } from 'react-dnd';
var Types = {
    CARD: 'card'
};
/**
 * Implements the drag source contract.
 */
var cardSource = {
    beginDrag: function (props) {
        return {
            text: props.text
        };
    }
}

/**
 * Specifies the props to inject into your component.
 */
function collect(connect, monitor) {
    return {
        connectDragSource: connect.dragSource(),
        isDragging: monitor.isDragging()
    };
}


export class Draggable extends Component {
    constructor(props){
        super(props);
    }


    render() {
        var isDragging = this.props.isDragging;
        var connectDragSource = this.props.connectDragSource;
        var text = this.props.text;
        console.log(text);

        return connectDragSource(
            <div>


                <div style={{ color: isDragging ? 'red' : 'blue' }}>
                    {text}
                </div>
            </div>

        );
    }

}

Draggable.propTypes = {
    connectDragSource: PropTypes.func.isRequired,
    isDragging: PropTypes.bool.isRequired
};

export default DragSource(Types.CARD, cardSource, collect)(Draggable);