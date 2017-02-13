import React, { Component } from 'react';
import SockJs from 'sockjs-client';
import Stomp from 'stompjs/lib/stomp.min.js';

class Device extends Component {
    constructor(props) {
        super(props);

        this.state = {
            value: null,
            valueDelayed: null
        }
    }

    componentDidMount() {
        const {device} = this.props;

        this.sock = new SockJs(config.WS_URL);
        this.sockClient = Stomp.Stomp.over(this.sock);

        this.sockClient.debug = null;
        this.sockClient.connect({}, frame => {
            this.sockClient.subscribe(device.websocketEndpoint, msg => {
                const body = JSON.parse(msg.body);
                this.setState({
                    value: body.value
                });
            });
        });

        this.delayForDisplayingValue(1000);
    }

    componentWillUnmount() {
        this.sockClient.disconnect();
        clearInterval(this.interval);
    }

    handleClick = () => {
        const {handleChange} = this.props;
        const {valueDelayed} = this.state;

        handleChange(valueDelayed);
    }

    handleMouseEnter = () => {
        this.delayForDisplayingValue(2000);
    }

    handleMouseLeave = () => {
        this.delayForDisplayingValue(1000);
    }

    delayForDisplayingValue = (interval) => {
        const self = this;

        clearInterval(this.interval);

        this.interval = setInterval(() => {
            self.setState({
                valueDelayed: self.state.value
            })
        }, interval);
    }

    render() {
        const {valueDelayed, index, isMore} = this.state;

        if(!!valueDelayed){
            return (
                <div
                    className={"btn btn-meta-outline-secondary btn-sm btn-inline pointer btn-distance-rev " +
                        (isMore ? "btn-flagged ": "")
                    }
                    onClick={this.handleClick}
                    onMouseEnter={this.handleMouseEnter}
                    onMouseLeave={this.handleMouseLeave}
                >
                    {isMore && <span className="btn-flag">{index + 1}</span>}
                    {valueDelayed}
                </div>
            )
        }else{
            return false;
        }
    }
}

export default Device;
