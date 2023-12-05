import React from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

const BlankPage = ({ what = 'Document', title, description }) => {
  let titleEffective;
  let descriptionEffective;
  if (title) {
    titleEffective = title;
    descriptionEffective = description;
  } else {
    let whatEffective = what ? what : 'Document';
    whatEffective =
      whatEffective.charAt(0).toUpperCase() + whatEffective.slice(1); // capitalize
    titleEffective = counterpart.translate('window.notFound.title', {
      what: whatEffective,
    });
    descriptionEffective = counterpart.translate(
      'window.notFound.description',
      {
        what: whatEffective,
      }
    );
  }

  return (
    <div className="blank-page">
      <h1>{titleEffective}</h1>
      <h3>{descriptionEffective}</h3>
    </div>
  );
};

/**
 * @typedef {object} Props Component props
 * @prop {string} what
 */
BlankPage.propTypes = {
  what: PropTypes.any,
  title: PropTypes.string,
  description: PropTypes.string,
};

export default BlankPage;
