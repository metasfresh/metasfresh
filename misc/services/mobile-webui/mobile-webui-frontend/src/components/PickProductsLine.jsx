import React, { Component } from 'react';
import PropTypes from 'prop-types';

class PickProductsLine extends Component {
  /**
   *
   * @param {string} id of the line
   */
  handleClick = (id) => {
    console.log(id);
  };

  render() {
    const { id, caption } = this.props;
    return (
      <div key={id} className="ml-3 mr-3 is-light pick-product" onClick={() => this.handleClick(id)}>
        <div className="box">
          <div className="columns is-mobile">
            <div className="column is-12">
              <div className="columns">
                <div className="column is-size-4-mobile no-p">{caption}</div>
                <div className="column is-size-7 no-p">STEP</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

PickProductsLine.propTypes = {
  id: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
};

export default PickProductsLine;
