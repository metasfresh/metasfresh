import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { v4 as uuid } from 'uuid';

import { showSpinner, hideSpinner } from '../../actions/WindowActions';

class SpinnerOverlay extends Component {
  timeout = null;

  constructor(props) {
    super(props);

    this.state = {
      show: false,
    };
  }

  componentWillMount() {
    this.ID = uuid();
  }

  componentDidMount() {
    const { showSpinner, spinnerDisplayed, delay, parent } = this.props;

    console.log('componentDidMount: ', spinnerDisplayed, this.props.displayCondition)

    if (this.props.displayCondition && !spinnerDisplayed && !this.timeout) {
      console.log('GO1')
      console.time()
      // this.props.forceRender();
      this.timeout = setTimeout(() => {
        this.setState({
          show: true,
        });

        console.log('SHOW1: ', this.ID);
        console.timeEnd();

        showSpinner(this.ID);

        parent.forceUpdate();
      }, delay);
    }
  }

  /*
   * This whole wizardry here is to force showing the spinner, when timeout
   * already finished but the parent component is not updating (because waiting
   * for data or doing some other operations).
   */
  componentDidUpdate(prevProps) {
    const prevCondition = prevProps.displayCondition;
    const { displayCondition, spinnerDisplayed, hideSpinner, showSpinner, delay, parent } = this.props;

    // console.log('componentDidUpdate: ', prevCondition, displayCondition, spinnerDisplayed)

    // if (prevCondition && !displayCondition) {
    if (!displayCondition && spinnerDisplayed) {
      // const { hideSpinner, spinnerDisplayed } = this.props;

      if (spinnerDisplayed === this.ID) {
        this.setState({
          show: false,
        });
        this.timeout = null;

        console.log('HIDE')
        hideSpinner();
      }
    } else
    if (displayCondition && !spinnerDisplayed && !this.timeout) {
      console.log('SHOW1')
      // this.props.forceRender();
      this.timeout = setTimeout(() => {
        this.setState({
          show: true,
        });

        console.log('SHOW2: ', this.ID);

        showSpinner(this.ID);

        parent.forceUpdate();
      }, delay);
    }
  }

  render() {
    const { iconSize } = this.props;
    const { show } = this.state;

    if (!show) {
      return null;
    }

    if (iconSize) {
      style = {
        width: `${iconSize}px`,
        height: `${iconSize}px`,
      };
    }

    // return (
    //   <div className="screen-freeze screen-prompt-freeze spinner">
    //     <i style={style} className="icon hourglass" />
    //   </div>
    // );

    return (
      <div className="screen-freeze screen-prompt-freeze spinner-wrapper">
        <div className="spinner">
          <div className="bulletouter">
            <div className="bulletinner" />
            <div className="mask" />
            <div className="dot" />
          </div>
        </div>
      </div>
    );
  }
}

SpinnerOverlay.defaultProps = {
  delay: 1000,
  iconSize: 32,
  // displayCondition: false,
};

SpinnerOverlay.propTypes = {
  parent: PropTypes.any.isRequired,
  displayCondition: PropTypes.bool.isRequired,
  hideCondition: PropTypes.bool.isRequired,
  iconSize: PropTypes.number,
  delay: PropTypes.number,
  spinnerDisplayed: PropTypes.string,
  showSpinner: PropTypes.func.isRequired,
  hideSpinner: PropTypes.func.isRequired,
};

const mapStateToProps = ({ windowHandler }) => ({
  spinnerDisplayed: windowHandler.spinner,
});

export default connect(mapStateToProps, {
  showSpinner,
  hideSpinner,
})(SpinnerOverlay);
