import React, { Component } from 'react';

class Separator extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { title } = this.props;
    return (
      <div className="separator col-xs-12">
        <span className="separator-title">{title}</span>
      </div>
    );
  }
}

export default Separator;
