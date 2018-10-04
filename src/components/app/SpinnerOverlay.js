import PropTypes from 'prop-types';
import React, { Component } from 'react';

export default class SpinnerOverlay extends Component {
  timeout = null;

  constructor(props) {
    super(props);

    this.state = {
      show: false,
    };
  }

  componentDidMount() {
    setTimeout(() => {
      this.setState({
        show: true,
      });
    }, this.props.delay);
  }

  componentWillUnmount() {
    this.setState({
      show: false,
    });
  }

  render() {
    const { show } = this.state;

    if (!show) {
      return null;
    }

    return (
      <div className="screen-freeze screen-prompt-freeze spinner">
        <i className="icon hourglass" />
      </div>
    );
  }
}

SpinnerOverlay.propTypes = {
  // dispatch: PropTypes.func.isRequired,
  // text: PropTypes.string.isRequired,
  // onCancelClick: PropTypes.func,
  // onSubmitClick: PropTypes.func,
  iconSize: PropTypes.number,
  delay: PropTypes.number,
};

// export default connect()(Prompt);
