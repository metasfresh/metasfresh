import React, { Component } from 'react';
import classnames from 'classnames';

class InlineTab extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isOpen: false,
    };
  }

  toggleOpen = () => {
    this.setState((prevState) => ({ isOpen: !prevState.isOpen }));
  };

  render() {
    const { isOpen } = this.state;

    return (
      <div>
        <div
          className={classnames(
            { 'inline-tab': !isOpen },
            { 'inline-tab-active': isOpen },
            { 'form-control-label': true }
          )}
          onClick={this.toggleOpen}
        >
          <div className="pull-left">
            <span className="arrow-pointer" />
          </div>
          {/* Header  */}
          <div className="pull-left offset-left">Inline Tab</div>

          {/* Content */}
          {isOpen && <div className="inline-tab-content">Actual content</div>}
        </div>
      </div>
    );
  }
}

export default InlineTab;
