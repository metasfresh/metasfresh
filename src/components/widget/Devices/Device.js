import React, { Component } from 'react';
import SockJs from 'sockjs-client';
import Stomp from 'stompjs/lib/stomp.min.js';

class Device extends Component {
    constructor(props) {
        super(props);

        this.state = {
            value: null
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
                })
            });
        });
    }

    componentWillUnmount() {
        this.sockClient.disconnect();
    }

    handleClick = () => {
        const {handleChange} = this.props;
        const {value} = this.state;

        handleChange(value);
    }

    render() {
        const {value, index, isMore} = this.state;

        if(!!value){
            return (
                <div
                    className={"btn btn-meta-outline-secondary btn-sm btn-inline pointer btn-distance-rev " +
                        (isMore ? "btn-flagged ": "")
                    }
                    onClick={this.handleClick}
                >
                    {isMore && <span className="btn-flag">{index + 1}</span>}
                    {value}
                </div>
            )
        }else{
            return false;
        }
    }
}

export default Device;
