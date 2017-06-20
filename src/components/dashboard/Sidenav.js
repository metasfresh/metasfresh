import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import InfiniteScroll from 'react-infinite-scroller';
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
        
        getRequest('dashboard', entity, 'available').then(res => {
            this.setState({
                view: res.data
            })
        })
    }

    loadMore = (page) => {
    }

    handleClickOutside = () => {
        const {onClickOutside} = this.props;
        onClickOutside();
    }

    render() {
        const {view} = this.state;
        const {entity} = this.props;
        return (
            <div
                className="board-sidenav overlay-shadow"
            >
                <InfiniteScroll
                   pageStart={0}
                   loadMore={this.loadMore}
                   initialLoad={false}
                   loader={<Loader />}
                   hasMore={view.result && view.size >= view.result.length}
                   useWindow={false}
                >
                    <div className="board-sidenav-header">
                        {entity === 'kpis' ? 'Add KPI widgets' : 'Add target indicators'}
                    </div>
                    <div>
                        {view && view.map((item, i) =>
                            entity === 'kpis' ? 
                                <RawChart key={i} /> : 
                                <Indicator key={i} fullWidth={1} value={item.chartType} caption={item.caption} />
                        )}
                    </div>
                </InfiniteScroll>
            </div>
        );
    }
}

export default onClickOutside(Sidenav);
