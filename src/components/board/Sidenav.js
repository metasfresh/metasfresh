import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import InfiniteScroll from 'react-infinite-scroller';
import Card from './Card';
import Loader from '../app/Loader';
import update from 'immutability-helper';

import {getView, createView} from '../../actions/BoardActions';

class Sidenav extends Component {
    constructor(props) {
        super(props);

        this.state = {
            view: {}
        }
    }

    componentWillMount = () => {
        const {boardId, viewId, setViewId} = this.props;

        if(viewId){
            getView(boardId, viewId, 0).then(res =>
                this.setState({
                    view: res.data
                })
            );
        }else{
            createView(boardId).then(res => {
                setViewId(res.data.viewId);
                getView(boardId, res.data.viewId, 0).then(res =>
                    this.setState({
                        view: res.data
                    })
                );
            });
        }

    }

    loadMore = (page) => {
        const {boardId, viewId} = this.props;

        getView(boardId, viewId, page).then(res =>
            this.setState(prev => update(prev, {
                view: {
                    result: {$push: res.data.result}
                }
            }))
        );
    }

    handleClickOutside = () => {
        const {onClickOutside} = this.props;
        onClickOutside();
    }

    render() {
        const {view} = this.state;
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
                        Add cards
                    </div>
                    <div>
                        {view.result && view.result.map((card, i) => (
                           <Card
                              key={i}
                              index={i}
                              {...card}
                           />
                        ))}
                    </div>
                </InfiniteScroll>
            </div>
        );
    }
}

export default onClickOutside(Sidenav);
