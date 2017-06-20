import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import Card from './Card';

import {getView} from '../../actions/BoardActions';

class Sidenav extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            view: {}
        }
    }
    
    componentWillMount = () => {
        const {boardId} = this.props;
        
        getView(boardId).then(res => {
            getView(boardId, res.data.viewId, 0).then(res =>
                this.setState({
                    view: res.data
                })
            );
        });
    }
    
    handleClickOutside = () => {
        const {onClickOutside} = this.props;
        onClickOutside();
    }

    render() {
        const {view} = this.state;
        const {onHover, onDrop} = this.props;
        return (
            <div
                className="board-sidenav overlay-shadow"
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
            </div>
        );
    }
}

export default onClickOutside(Sidenav);
