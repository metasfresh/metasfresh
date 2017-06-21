import React, { Component } from 'react';
import Loader from '../app/Loader';
import RawChart from '../charts/RawChart';
import Indicator from '../charts/Indicator';

import {
    getRequest
} from '../../actions/GenericActions';

class Sidenav extends Component {
    constructor(props) {
        super(props);

        this.state = {
            view: []
        }
    }

    componentWillMount = () => {
        const {entity} = this.props;
        
        getRequest('dashboard', 'available').then(res => {
            this.setState({
                view: res.data
            })
        })
    }

    render() {
        const {view} = this.state;
        const {entity} = this.props;
        return (
            <div
                className="board-sidenav overlay-shadow"
            >
                <div className="board-sidenav-header">
                    Add widgets
                </div>
                <div>
                    {view && view.map((item, i) =>
                        item.chartType === 'kpis' ? 
                            <RawChart key={i} /> : 
                            <Indicator
                                key={i}
                                fullWidth={1}
                                value={item.chartType}
                                caption={item.caption}
                            />
                    )}
                </div>
            </div>
        );
    }
}

export default Sidenav;
