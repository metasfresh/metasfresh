import React from 'react';
import PropTypes from 'prop-types';
import { useOAuth2Providers } from '../../auth/useOAuth2Providers';
import googleLogo from '../../assets/images/oauth2/google.svg';
import { EMPTY_PNG } from '../../constants/Constants';
import { trl } from '../../utils/locale';

const OAuth2LoginButtons = ({ disabled, showLoadingView } = {}) => {
  const { providers, onOAuthLoginRequest } = useOAuth2Providers();

  if (!providers?.length) return null;

  return (
    <div className="OAuth2LoginButtons">
      <div className="oauth-top-separator">
        <span>{trl('login.oauth2.separatorText')}</span>
      </div>
      {providers.map((provider) => (
        <OAuth2LoginButton
          key={provider.code}
          providerCode={provider.code}
          providerName={provider.caption}
          logoUrl={provider.logoUrl}
          disabled={disabled}
          onOAuthLoginRequest={({ providerCode }) => {
            showLoadingView();
            onOAuthLoginRequest({ providerCode });
          }}
        />
      ))}
    </div>
  );
};
OAuth2LoginButtons.propTypes = {
  disabled: PropTypes.bool,
  showLoadingView: PropTypes.func.isRequired,
};

export default OAuth2LoginButtons;

//
//
//-------------------------------------------------------------------------------
//
//

const OAuth2LoginButton = ({
  providerCode,
  providerName,
  logoUrl,
  disabled,
  onOAuthLoginRequest,
}) => {
  const caption = trl(`login.oauth2.loginWithCaption`, { name: providerName });

  let imageSrc = EMPTY_PNG;
  if (logoUrl) {
    imageSrc = logoUrl;
  } else if (providerCode === 'google') {
    imageSrc = googleLogo;
  }

  return (
    <button
      className="btn btn-sm btn-block btn-meta-outline-secondary provider-button"
      disabled={disabled}
      onClick={() => onOAuthLoginRequest({ providerCode })}
    >
      {imageSrc && (
        <img className="provider-logo" src={imageSrc} alt={caption} />
      )}
      {caption}
    </button>
  );
};
OAuth2LoginButton.propTypes = {
  providerCode: PropTypes.string.isRequired,
  providerName: PropTypes.string.isRequired,
  logoUrl: PropTypes.string,
  disabled: PropTypes.bool,
  onOAuthLoginRequest: PropTypes.func.isRequired,
};
