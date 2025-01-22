import React from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

export const BlankPage = ({ what = 'Document', title, description }) => {
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

  titleEffective = normalizeMessage(titleEffective);
  descriptionEffective = normalizeMessage(descriptionEffective);

  return (
    <div className="blank-page">
      {titleEffective && <h1>{titleEffective}</h1>}
      {descriptionEffective && <h3>{descriptionEffective}</h3>}
    </div>
  );
};

BlankPage.propTypes = {
  what: PropTypes.any,
  title: PropTypes.string,
  description: PropTypes.string,
};

const normalizeMessage = (message) => {
  if (!message) return null;

  let messageNorm = message.trim();
  if (!messageNorm) return null;
  if (messageNorm === '-') return null;

  return messageNorm;
};
