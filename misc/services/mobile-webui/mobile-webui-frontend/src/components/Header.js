import React, { Component } from 'react';

class Header extends Component {
    render() {
        const { appName, networkStatus } = this.props;
        return (
            <header className="p-4 navbar is-fixed-top is-flex is-align-items-center is-justify-content-space-around header">
              <div className="header-title">
                <h4 className="title is-4">{appName}</h4>
                <div className="subtitle">network: {networkStatus} </div>
              </div>
            </header>
        );
    }
}

export default Header;