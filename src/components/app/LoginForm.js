import React, { Component, PropTypes } from 'react';
import {push, goBack} from 'react-router-redux';

import {connect} from 'react-redux';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import RawList from '../widget/RawList';
import Moment from 'moment';

import {
    loginRequest,
    loginSuccess,
    loginCompletionRequest,
    getUserLang
} from '../../actions/AppActions';

class LoginForm extends Component {
    constructor(props){
        super(props);

        this.state = {
            role: "",
            roleSelect: false,
            err: ""
        }
    }

    handleKeyPress = (e) => {
        if(e.key === 'Enter'){
            this.handleLogin();
        }
    }

    handleOnChange = (e) => {
        e.preventDefault();

        this.setState(Object.assign({}, this.state, {
            err: ""
        }))
    }

    handleSuccess = () => {
        const {redirect, dispatch} = this.props;

        dispatch(getUserLang()).then(response => {
            //GET language shall always return a result
            Moment.locale(response.data);

            if(redirect){
                dispatch(goBack());
            }else{
                dispatch(push('/'));
            }
        })

    }

    handleLogin = () => {
        const {dispatch} = this.props;
        const {roleSelect, roles, role} = this.state;

        if(roleSelect){
            dispatch(loginCompletionRequest(role)).then(() => {
                dispatch(loginSuccess());
                this.handleSuccess();
            })
        }else{
            dispatch(loginRequest(this.login.value, this.passwd.value)).then(response =>{
                if(response.data.loginComplete){
                    this.handleSuccess();
                }else{
                    this.setState(Object.assign({}, this.state, {
                        roleSelect: true,
                        roles: response.data.roles,
                        role: response.data.roles[0]
                    }))
                }
            }).catch(err => {
                this.setState(Object.assign({}, this.state, {
                    err: err.response.data.message
                }));
            })
        }
    }

    handleRoleSelect = (option) => {
        this.setState(Object.assign({}, this.state, {
            role: option
        }));
    }

    render() {
        const {roleSelect, roles, err, role} = this.state;
        return (
            <div className="login-form panel panel-spaced-lg panel-shadowed panel-primary" onKeyPress={this.handleKeyPress}>
                <div className="text-xs-center">
                    <img src={logo} className="header-logo mt-2 mb-2" />
                </div>
                {roleSelect ? <div>
                    <div className={"form-control-label"}><small>Select role</small></div>
                        <RawList
                            rank="primary"
                            list={roles}
                            onSelect={option => this.handleRoleSelect(option)}
                            selected={role}
                        />
                    </div>:
                    <div>
                        {
                            err && <div className="input-error">
                                {err}
                            </div>
                        }
                        <div>
                            <div className={"form-control-label"}><small>Login</small></div>
                            <input
                                type="text"
                                onChange={this.handleOnChange}
                                className={
                                    "input-primary input-block " +
                                    (err ? "input-error " : "")}
                                ref={c => this.login = c} />
                        </div>
                        <div>
                            <div className={"form-control-label"}><small>Password</small></div>
                            <input
                                type="password"
                                onChange={this.handleOnChange}
                                className={
                                    "input-primary input-block " +
                                    (err ? "input-error " : "")}
                                ref={c => this.passwd = c}
                            />
                        </div>

                    </div>
                }
                <div className="mt-2">
                    <button className="btn btn-sm btn-block btn-meta-success" onClick={this.handleLogin}>{roleSelect? "Send" : "Login"}</button>
                </div>
            </div>
        )
    }
}

LoginForm.propTypes = {
    dispatch: PropTypes.func.isRequired
};

LoginForm = connect()(LoginForm);

export default LoginForm;
