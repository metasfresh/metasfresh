import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { v4 as uuidv4 } from 'uuid';
import PickProductsLine from '../PickProductsLine';

class PickProductsActivity extends Component {
  render() {
    const {
      caption,
      componentProps: { lines },
    } = this.props;

    return (
      <div className="pick-products-container">
        <div className="title is-4 header-caption">{caption}</div>
        <div className="pick-products-active-line">Active line</div>
        {lines.length > 0 &&
          lines.map((lineItem) => {
            let uniqueId = uuidv4();
            return <PickProductsLine key={uniqueId} id={uniqueId} {...lineItem} />;
          })}
      </div>
    );
  }
}

PickProductsActivity.propTypes = {
  caption: PropTypes.string,
  componentProps: PropTypes.object,
};

export default PickProductsActivity;
