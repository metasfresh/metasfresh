import React from 'react';
import PropTypes from 'prop-types';

const DocumentReferenceItem = ({
  caption,
  targetWindowId,
  referenceId,
  filter,
  onClick,
  internalName,
}) => {
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
};

DocumentReferenceItem.propTypes = {
  caption: PropTypes.string.isRequired,
  targetWindowId: PropTypes.string.isRequired,
  targetCategory: PropTypes.string, // added only for troubleshooting
  referenceId: PropTypes.string.isRequired,
  filter: PropTypes.object.isRequired,
  onClick: PropTypes.func.isRequired,
  internalName: PropTypes.string.isRequired,
};

export default DocumentReferenceItem;
