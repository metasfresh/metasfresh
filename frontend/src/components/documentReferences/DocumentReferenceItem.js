import React, { Component } from 'react';
import PropTypes from 'prop-types';

export default class DocumentReferenceItem extends Component {
  render() {
    const {
      caption,
      targetWindowId,
      referenceId,
      filter,
      onClick,
      internalName,
    } = this.props;

    return (
      <div
        className="subheader-item js-subheader-item"
        onClick={(e) =>
          onClick({
            targetWindowId,
            referenceId,
            filter,
            ctrlKeyPressed: e.ctrlKey,
          })
        }
        tabIndex={0}
        data-cy={`reference-${internalName}`}
      >
        {caption}
      </div>
    );
  }
}

DocumentReferenceItem.propTypes = {
  caption: PropTypes.string.isRequired,
  targetWindowId: PropTypes.string.isRequired,
  referenceId: PropTypes.string.isRequired,
  filter: PropTypes.object.isRequired,
  onClick: PropTypes.func.isRequired,
  internalName: PropTypes.string.isRequired,
};
