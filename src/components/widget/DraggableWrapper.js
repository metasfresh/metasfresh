import React, { Component,PropTypes } from 'react';
import {connect} from 'react-redux';
import update from 'react/lib/update';
import DraggableWidget from './DraggableWidget';
import { DragDropContext } from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import {
    getUserDashboardWidgets,
    setUserDashboardWidgets,
    getUserDashboardIndicators
} from '../../actions/AppActions';

export class DraggableWrapper extends Component {
    constructor(props) {
        super(props);
        this.moveCard = this.moveCard.bind(this);
        this.state = {
            cards: [],
            isVisible: true,
            idMaximized: false,
            indicators: []
        };
    }

    componentDidMount = () => {
        this.getDashboard();
        this.getIndicators();
    }

    getIndicators = () => {
        const {dispatch} = this.props;
        dispatch(getUserDashboardIndicators()).then(response => {
            this.setState(Object.assign({}, this.state, {
                indicators: response.data.items
            }));
        });
    }

    getDashboard = () => {
        const {dispatch} = this.props;
        dispatch(getUserDashboardWidgets()).then(response => {
            this.setState(Object.assign({}, this.state, {
                cards: response.data.items
            }));
        });
    }

    moveCard = (dragIndex, hoverIndex) => {
        const { cards } = this.state;
        const {dispatch} = this.props;
        const dragCard = cards[dragIndex];

        this.setState(update(this.state, {
            cards: {
                $splice: [
                    [dragIndex, 1],
                    [hoverIndex, 0, dragCard]
                ]
            }
        }), () => {
            const changes = {
                'jsonDashboardChanges': {
                    'dashboardItemIdsOrder': cards.map(item => item.id)
                }
            };
            dispatch(setUserDashboardWidgets(changes));
        });
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


    render() {
        const { cards, idMaximized, indicators } = this.state;
        return (
            <div className={this.props.dashboard == '/'?'dashboard-wrapper':''}> 
                {this.props.dashboard == '/' &&
                    <div className="logo-wrapper">
                        <img src={logo} />
                    </div>
                }
                
                
                
                {false && indicators.length > 0 && <div className={
                    'indicators-wrapper ' +
                    (idMaximized !== false ? 'indicator-hidden' : '')
                }>
                    {false && indicators.map((indicator, id) =>
                        <div
                            className="indicator"
                            key={id}
                        >
                            <iframe
                                src={indicator.url}
                                scrolling="no"
                                frameBorder="no"
                            ></iframe>
                        </div>
                    )}
                </div>}

                {this.props.dashboard != '/' && cards.map((card, i) => {
                    return (
                        <DraggableWidget
                            key={card.id}
                            index={i}
                            id={card.id}
                            text={card.caption}
                            url={card.url}
                            moveCard={this.moveCard}
                            hideWidgets={this.hideWidgets}
                            showWidgets={this.showWidgets}
                            idMaximized={idMaximized}
                            dashboard={this.props.dashboard}
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
