import React, { Fragment } from 'react';
import classnames from 'classnames';
import PropTypes from 'prop-types';

const CharacterLimitInfo = ({ charsTyped, maxLength }) => {
  const NO_OF_CHARS_BEFORE_MAX_REACHED = 10;
  const displayGrayedLimit =
    maxLength - charsTyped < NO_OF_CHARS_BEFORE_MAX_REACHED;
  const displayErrorLimit = charsTyped > maxLength;

  return (
    <Fragment>
      {displayGrayedLimit && (
        <div>
          <div
            className={classnames(
              'float-right',
              { 'text-muted': displayGrayedLimit && !displayErrorLimit },
              { 'text-danger': displayErrorLimit }
            )}
          >
            {`${charsTyped} / ${maxLength} Characters`}
          </div>
        </div>
      )}
    </Fragment>
  );
};

CharacterLimitInfo.propTypes = {
  charsTyped: PropTypes.number,
  maxLength: PropTypes.number,
};

export default CharacterLimitInfo;
