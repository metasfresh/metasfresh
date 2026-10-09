import { useEffect, useState } from 'react';
import {
  getPasswordResetAvatarUrl,
  getResetPasswordInfo,
  resetPasswordComplete,
} from '../../../api/login';

export const usePasswordResetToken = () => {
  const urlParams = new URLSearchParams(window.location.search);
  const token = urlParams.get('token');

  const [email, setEmail] = useState('');
  const [fullname, setFullname] = useState('');
  const [isInvalidToken, setIsInvalidToken] = useState(false);
  const [invalidTokenReason, setInvalidTokenReason] = useState('');

  useEffect(() => {
    if (token) {
      getResetPasswordInfo(token)
        .then(({ data }) => {
          setEmail(data.email);
          setFullname(data.fullname);
        })
        .catch(({ data }) => {
          setIsInvalidToken(true);
          setInvalidTokenReason(data.message);
        });
    } else {
      setIsInvalidToken(true);
      setInvalidTokenReason('No token provided');
    }
  }, [token]);

  return {
    token,
    email,
    fullname,
    avatarSrc:
      token && !isInvalidToken ? getPasswordResetAvatarUrl(token) : null,
    isInvalidToken,
    invalidTokenReason,
    changePasswordTo: ({ newPassword }) => {
      return resetPasswordComplete(token, {
        email,
        password: newPassword,
        token,
      });
    },
  };
};
