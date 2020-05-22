import React, { Component, Fragment } from 'react';
import PropTypes from 'prop-types';

import DocumentReferenceItem from './DocumentReferenceItem';

export default class DocumentReferenceGroup extends Component {
  render() {
    const { caption, references, onReferenceItemClick } = this.props;

    if (!references) {
      return null;
    }

    return (
      <Fragment>
        <div className="subheader-caption">{caption}</div>
        {references.map((reference) => (
          <DocumentReferenceItem
            key={reference.id}
            caption={reference.caption}
            targetWindowId={reference.targetWindowId}
            filter={reference.filter}
            onClick={onReferenceItemClick}
            internalName={reference.internalName}
          />
        ))}
      </Fragment>
    );
  }
}

DocumentReferenceGroup.propTypes = {
  caption: PropTypes.string.isRequired,
  references: PropTypes.array,
  onReferenceItemClick: PropTypes.func.isRequired,
};
