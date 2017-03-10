import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import update from 'react/lib/update';
import DraggableWidget from './DraggableWidget';
import { DragDropContext } from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
import RawChart from '../charts/RawChart';

import {
    getKPIsDashboard,
    getTargetIndicatorsDashboard
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
        dispatch(getTargetIndicatorsDashboard()).then(response => {
            this.setState(Object.assign({}, this.state, {
                indicators: response.data.items
            }));
        });
    }

    getDashboard = () => {
        const {dispatch} = this.props;
        dispatch(getKPIsDashboard()).then(response => {
            this.setState(Object.assign({}, this.state, {
                cards: response.data.items
            }));
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
        }), () => {
            // const changes = {
            //     'jsonDashboardChanges': {
            //         'dashboardItemIdsOrder': cards.map(item => item.id)
            //     }
            // };
            // dispatch(setUserDashboardWidgets(changes));
            //TO DO: future implementation
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
            <div className="dashboard-cards-wrapper">

                {indicators.length > 0 && <div className={
                        'indicators-wrapper ' +
                        (idMaximized !== false ? 'indicator-hidden' : '')
                    }>
                    {indicators.map((indicator, id) =>
                        <RawChart
                            key={id}
                            id={indicator.id}
                            caption={indicator.caption}
                            kpiCaption={indicator.kpi.caption}
                            fields={indicator.kpi.fields}
                            pollInterval={indicator.kpi.pollInterval}
                            chartType={'Indicator'}
                            kpi={false}

                        />
                    )}
                </div>}

                { cards.length > 0 ?

                    cards.map((item, id) => {
                    return (
                        <DraggableWidget
                            key={item.id}
                            id={item.id}
                            index={id}
                            chartType={item.kpi.chartType}
                            caption={item.kpi.caption}
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
                    );
                }):
                    <div className="dashboard-wrapper dashboard-logo-wrapper">
                        <div className="logo-wrapper">
                            <img src={logo} />
                        </div>
                    </div>
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
