import React, { Component } from 'react';

class Placeholder extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { description, entity } = this.props;
    const height = entity === 'cards' ? '300px' : '100px';

    return (
      <div style={{ height }} className="dnd-placeholder-filling">
        {description}
      </div>
    );
  }
}

export default Placeholder;
