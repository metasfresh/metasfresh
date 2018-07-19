import counterpart from 'counterpart';
// import Moment from 'moment';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
// import { List } from 'immutable';
// import { connect } from 'react-redux';
import { goBack, push } from 'react-router-redux';
import classnames from 'classnames';

import {
  // getUserLang,
  // localLoginRequest,
  // loginCompletionRequest,
  // loginRequest,
  resetPassword,
  
} from '../../api';
// import { loginSuccess } from '../../actions/AppActions';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';
// import RawList from '../widget/List/RawList';

export default class PasswordRecovery extends Component {
  constructor(props) {
    super(props);

    this.state = {
      // role: '',
      // roleSelect: false,
      err: '',
      pending: false,
      // dropdownToggled: false,
      // dropdownFocused: false,
    };
  }

  componentDidMount() {
    this.focusField.focus();
  }

  // componentWillUpdate(nextProps, nextState) {
  //   if (this.roleSelector && nextState.roleSelect) {
  //     this.roleSelector.instanceRef.dropdown.focus();
  //   }
  // }

  handleKeyPress = e => {
    // if (e.key === 'Enter') {
    //   this.handleLogin();
    // }
  };

  handleOnChange = e => {
    e.preventDefault();

    this.setState({
      err: '',
    });
  };

  // handleSuccess = () => {
  //   const { redirect, dispatch } = this.props;

  //   getUserLang().then(response => {
  //     //GET language shall always return a result
  //     Moment.locale(response.data['key']);

  //     if (redirect) {
  //       dispatch(goBack());
  //     } else {
  //       dispatch(push('/'));
  //     }
  //   });
  // };

  // checkIfAlreadyLogged(err) {
  //   const { router } = this.context;

  //   return localLoginRequest().then(response => {
  //     if (response.data) {
  //       return router.push('/');
  //     }

  //     return Promise.reject(err);
  //   });
  // }

  handleSubmit = () => {
    const { path } = this.props;
    const resetPassword = path === 'resetPassword' ? true : false;

    console.log('submit');
  }

  // handleLogin = () => {
  //   const { dispatch, auth } = this.props;
  //   const { roleSelect, role } = this.state;

  //   this.setState(
  //     {
  //       pending: true,
  //     },
  //     () => {
  //       if (roleSelect) {
  //         return loginCompletionRequest(role)
  //           .then(() => {
  //             dispatch(loginSuccess(auth));
  //             this.handleSuccess();
  //           })
  //           .catch(err => {
  //             this.setState({
  //               err: err.response
  //                 ? err.response.data.message
  //                 : counterpart.translate('login.error.fallback'),
  //               pending: false,
  //             });
  //           });
  //       }

  //       loginRequest(this.login.value, this.passwd.value)
  //         .then(response => {
  //           if (response.data.loginComplete) {
  //             return this.handleSuccess();
  //           }
  //           const roles = List(response.data.roles);

  //           this.setState({
  //             roleSelect: true,
  //             roles,
  //             role: roles.get(0),
  //           });
  //         })
  //         .then(() => {
  //           this.setState({
  //             pending: false,
  //           });
  //         })
  //         .catch(err => {
  //           return this.checkIfAlreadyLogged(err);
  //         })
  //         .catch(err => {
  //           this.setState({
  //             err: err.response
  //               ? err.response.data.message
  //               : counterpart.translate('login.error.fallback'),
  //             pending: false,
  //           });
  //         });
  //     }
  //   );
  // };

  // handleRoleSelect = option => {
  //   this.setState({
  //     role: option,
  //   });
  // };

  // handleForgotPassword = () => {
  //   console.log('clicked')
  // }

  // openDropdown = () => {
  //   this.setState({
  //     dropdownToggled: true,
  //   });
  // };

  // closeDropdown = () => {
  //   this.setState({
  //     dropdownToggled: false,
  //   });
  // };

  // onFocus = () => {
  //   this.setState({
  //     dropdownFocused: true,
  //   });
  // };

  // onBlur = () => {
  //   this.setState({ dropdownFocused: false });
  // };

  renderForgottenPasswordForm = () => {
    const { pending } = this.props;

    return (
      <div>
        <div>
          <div className="form-control-label">
            <small>
              {counterpart.translate('forgotPassword.email.caption')}
            </small>
          </div>
          <input
            type="email"
            name="email"
            className={classnames('input-primary input-block', {
              'input-disabled': pending,
            })}
            disabled={pending}
            ref={c => (this.focusField = c)}
          />
        </div>
      </div>
    );
  };

  renderResetPasswordForm = () => {
    const { err, pending } = this.state;

    return (
      <div>
        {err && <div className="input-error">{err}</div>}
        <div>
          <div className="form-control-label">
            <small>
              {counterpart.translate('forgotPassword.email.caption')}
            </small>
          </div>
          <input
            type="text"
            onChange={this.handleOnChange}
            name="username"
            className={classnames('input-primary input-block', {
              'input-error': err,
              'input-disabled': pending,
            })}
            disabled={pending}
            ref={c => (this.focusField = c)}
          />
        </div>
        <div>
          <div className="form-control-label">
            <small>{counterpart.translate('login.password.caption')}</small>
          </div>
          <input
            type="password"
            name="password"
            className={classnames('input-primary input-block', {
              'input-disabled': pending,
            })}
            disabled={pending}
          />
        </div>
      </div>
    );
  };

  render() {
    const { path } = this.props;
    const { pending } = this.state;
    const resetPassword = path === 'resetPassword' ? true : false;

    return (
      <div
        className="login-form panel panel-spaced-lg panel-shadowed panel-primary"
        onKeyPress={this.handleKeyPress}
      >
        <div className="text-center">
          <img src={logo} className="header-logo mt-2 mb-2" />
        </div>
        {resetPassword
          ? this.renderResetPasswordForm()
          : this.renderForgottenPasswordForm()}
        <div className="mt-2">
          <button
            className="btn btn-sm btn-block btn-meta-success"
            onClick={this.handleSubmit}
            disabled={pending}
          >
            {resetPassword
              ? counterpart.translate(
                  'forgotPassword.changePassword.caption'
                )
              : counterpart.translate(
                  'forgotPassword.sendResetCode.caption'
                )}
          </button>
        </div>
      </div>
    );
  }
}

// LoginForm.propTypes = {
//   dispatch: PropTypes.func.isRequired,
// };

// LoginForm.contextTypes = {
//   router: PropTypes.object.isRequired,
// };

// export default connect()(LoginForm);
