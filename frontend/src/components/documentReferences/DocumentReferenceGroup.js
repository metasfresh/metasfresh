import React, { Fragment } from 'react';
import PropTypes from 'prop-types';

import DocumentReferenceItem from './DocumentReferenceItem';

const DocumentReferenceGroup = ({
  caption,
  references,
  onReferenceItemClick,
}) => {
  if (!references) {
    return null;
  }

  return (
    <Fragment>
      <div className="subheader-caption">{caption}</div>
      {references.map((reference) => (
        <DocumentReferenceItem
          key={reference.id}
          referenceId={reference.id}
          caption={reference.caption}
          targetWindowId={reference.targetWindowId}
          targetCategory={reference.targetCategory}
          filter={reference.filter}
          onClick={onReferenceItemClick}
          internalName={reference.internalName}
        />
      ))}
    </Fragment>
  );
};

DocumentReferenceGroup.propTypes = {
  caption: PropTypes.string.isRequired,
  references: PropTypes.array,
  onReferenceItemClick: PropTypes.func.isRequired,
};

export default DocumentReferenceGroup;
