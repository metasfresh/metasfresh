import React from 'react';
import PropTypes from 'prop-types';
import { useOAuth2Providers } from '../../auth/useOAuth2Providers';
import { getOAuth2ProviderUrl } from '../../api/login';

const OAuth2LoginButtons = ({ disabled } = {}) => {
  const { providers } = useOAuth2Providers();

  return (
    <div>
      {providers.map((provider) => (
        <OAuth2LoginButton
          key={provider.code}
          code={provider.code}
          caption={provider.caption}
          disabled={disabled}
        />
      ))}
    </div>
  );
};
OAuth2LoginButtons.propTypes = {
  disabled: PropTypes.bool,
};

export default OAuth2LoginButtons;

//
//
//-------------------------------------------------------------------------------
//
//

const OAuth2LoginButton = ({ code, caption, disabled }) => {
  const oAuth2ProviderUrl = getOAuth2ProviderUrl({ code });

  return (
    <button
      disabled={disabled}
      onClick={() => {
        document.location.href = oAuth2ProviderUrl;
      }}
    >
      {caption}
    </button>
  );
};
OAuth2LoginButton.propTypes = {
  code: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  disabled: PropTypes.bool,
};
