import React, { Component,PropTypes } from 'react';
import {connect} from 'react-redux';
import update from 'react/lib/update';
import DraggableWidget from './DraggableWidget';
import { DragDropContext } from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import {
    getUserDashboard
} from '../../actions/AppActions';

export class DraggableWrapper extends Component {
    constructor(props) {
        super(props);
        this.moveCard = this.moveCard.bind(this);
        this.state = {
            cards: [],
            isVisible: true,
            idMaximized: false,
            widgets: ''
        };
    }

    componentDidMount = () => {
        this.getDashboard();
    }

    getDashboard = () => {
        const {dispatch} = this.props;
        dispatch(getUserDashboard()).then(response => {
            this.setState(Object.assign({}, this.state, {
                cards: response.data.items
            }))
        });
    }

    moveCard = (dragIndex, hoverIndex) => {
        const { cards } = this.state;
        const dragCard = cards[dragIndex];

        this.setState(update(this.state, {
            cards: {
                $splice: [
                    [dragIndex, 1],
                    [hoverIndex, 0, dragCard]
                ]
            }
        }));
    }

    hideWidgets = (id) => {
        this.setState(Object.assign({}, this.state, {
            isVisible: false,
            idMaximized: id
        }))
    }
    showWidgets = (id) => {
        this.setState(Object.assign({}, this.state, {
            isVisible: true,
            idMaximized: false
        }))
    }


    render() {
        const { cards, isVisible, idMaximized } = this.state;

        return (
            <div>
            {cards.map((card, i) => {
                return (
                    (isVisible || (idMaximized===i)) &&
                    <DraggableWidget key={card.id}
                        index={i}
                        id={card.id}
                        text={card.caption}
                        url={card.url}
                        moveCard={this.moveCard}
                        hideWidgets={this.hideWidgets}
                        showWidgets={this.showWidgets}
                    />
                );
            })}
            </div>
        );
    }
}

DraggableWrapper.propTypes = {
    dispatch: PropTypes.func.isRequired
};

DraggableWrapper = connect()(DragDropContext(HTML5Backend)(DraggableWrapper));
export default DraggableWrapper;
