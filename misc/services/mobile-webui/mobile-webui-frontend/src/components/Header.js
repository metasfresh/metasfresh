import React, { Component } from 'react';
import { connect } from 'react-redux';
import { networkStatusOffline, networkStatusOnline } from '../actions/NetworkActions';
class Header extends Component {

    networkOffline  = () => { 
      const { networkStatusOffline } = this.props;
      if(networkStatusOffline) {
        networkStatusOffline();
      }
    };
    networkOnline = () => { 
      const { networkStatusOnline } = this.props;
      if (networkStatusOnline) {
            console.log('CALL ONLINE22')
        networkStatusOnline();
      }
    };

    render() {
        const { appName, networkStatus } = this.props;
        return (
            <header className="p-4 navbar is-fixed-top is-flex is-align-items-center is-justify-content-space-around header">
              <div className="header-title">
                <h4 className="title is-4">{appName}</h4>
                <div className="subtitle">network: {networkStatus ? 'online' : 'offline'} </div>
                <button onClick={this.networkOffline}>Offline</button>
                <button onClick={this.networkOnline}>Online</button>
              </div>
            </header>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        networkStatus: state.appHandler.network,
      };
};

export default connect(mapStateToProps, {
  networkStatusOnline,
  networkStatusOffline,
})(Header);