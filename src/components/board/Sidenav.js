import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import InfiniteScroll from 'react-infinite-scroller';
import Card from './Card';
import Loader from '../app/Loader';
import update from 'immutability-helper';

import {getView, createView, getLayout} from '../../actions/BoardActions';

class Sidenav extends Component {
    constructor(props) {
        super(props);

        this.state = {
            view: {},
            emptyText: "",
            emptyHint: "",
            loading: false

        }
    }

    componentWillMount = () => {
        const {boardId, viewId, setViewId} = this.props;

        this.setState({
            loading: true
        });

        if(viewId){
            getView(boardId, viewId, 0).then(res =>
                this.setState({
                    view: res.data,
                    loading: false
                })
            );
        }else{
            createView(boardId).then(res => {
                setViewId(res.data.viewId);
                getView(boardId, res.data.viewId, 0).then(res =>
                    this.setState({
                        view: res.data,
                        loading: false
                    })
                );
            });
        }

    }

    componentDidMount = () => {
        const {boardId} = this.props;
        getLayout(boardId).then(res =>
            this.setState({
                emptyText: res.data.emptyResultText,
                emptyHint: res.data.emptyResultHint
            })
        );
    }

    loadMore = (page) => {
        const {boardId, viewId} = this.props;

        this.setState({
            loading: true
        });

        getView(boardId, viewId, page).then(res =>
            this.setState(prev => update(prev, {
                view: {
                    result: {$push: res.data.result}
                },
                loading: {$set: false}
            }))
        );
    }

    handleClickOutside = () => {
        const {onClickOutside} = this.props;
        onClickOutside();
    }

    render() {
        const {view, emptyText, emptyHint, loading} = this.state;

        return (
            <div
                className="board-sidenav overlay-shadow"
            >
                <InfiniteScroll
                   pageStart={0}
                   loadMore={this.loadMore}
                   initialLoad={false}
                   loader={loading ? <Loader /> : <div></div>}
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
                        {view.result && view.result.length === 0 &&
                            <div className="empty-text">
                                {emptyText}
                                {emptyHint ? '. ' + emptyHint  : ''}
                            </div>
                        }
                    </div>
                </InfiniteScroll>
            </div>
        );
    }
}

export default onClickOutside(Sidenav);
