import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import '../../assets/css/styles.css';
import loader from '../../assets/images/state2.gif';
import saved from '../../assets/images/state3.png';
import unsaved from '../../assets/images/state1.png';

class Indicator extends Component {
  constructor(props){
      super(props);
  }
  renderPending = () => {
    return (
      <div>
        <img src={loader} />
        <small>Saving</small>
      </div>
    )
  }
  renderError = () => {
    return (
      <div>
        <img src={unsaved} />
        <small className="error">Error</small>
      </div>
    )
  }
  renderSaved = () => {
    return (
      <div>
        <img src={saved} />
        <small className="success saved-animation">Saved</small>
      </div>
    )
  }
  render() {
      return (
        <div className="indicator hidden-sm-down">
          {this.props.indicator === 'saved' ? this.renderSaved() : (this.props.indicator === 'pending' ? this.renderPending() : this.renderError() ) }
        </div>
      )
  }
}

Indicator.propTypes = {
    dispatch: PropTypes.func.isRequired
};
function mapStateToProps(state) {
    return {
    }
}
Indicator = connect(mapStateToProps)(Indicator);

export default Indicator
