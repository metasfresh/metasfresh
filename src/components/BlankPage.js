import React, { Component } from 'react';

class BlankPage extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { what } = this.props;

    return (
      <div className="blank-page">
        <h1>404</h1>
        <h3>{what ? what + ' not found.' : 'Not found'}</h3>
      </div>
    );
  }
}

export default BlankPage;
