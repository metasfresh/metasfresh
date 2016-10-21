import React, { Component, PropTypes } from 'react';
import {push} from 'react-router-redux';

import {connect} from 'react-redux';
import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

import {
    loginRequest,
    loginSuccess,
    loginCompletionRequest
} from '../../actions/AppActions';

class LoginForm extends Component {
    constructor(props){
        super(props);

        this.state = {
            roleSelect: false
        }
    }

    handleLogin = () => {
        const {dispatch} = this.props;
        const {roleSelect,roles} = this.state;

        if(roleSelect){
            dispatch(loginCompletionRequest(roles[this.role.value])).then(() => {
                dispatch(loginSuccess());
                dispatch(push('/'));
            })
        }else{
            dispatch(loginRequest(this.login.value, this.passwd.value)).then(response =>{
                if(response.data.loginComplete){
                    dispatch(push("/"));
                }else{
                    this.setState(Object.assign({}, this.state, {
                        roleSelect: true,
                        roles: response.data.roles
                    }))
                }
            });
        }
    }

    render() {
        const {roleSelect, roles} = this.state;
        return (
            <div className="login-form panel panel-spaced-lg panel-shadowed panel-primary">
                <div className="text-xs-center">
                    <img src={logo} className="header-logo m-t-2 m-b-2" />
                </div>
                {roleSelect ? <div>
                        Select role
                        <div>
                            <select ref={c => this.role = c}>
                                {roles.map((item, index) => <option key={index} value={index}>{item.caption}</option>)}
                            </select>
                        </div>
                    </div>:
                    <div>
                        <div>
                            <div className={"form-control-label"}><small>E-mail</small></div>
                            <input type="text" className="input-primary input-block" ref={c => this.login = c} />
                        </div>
                        <div>
                            <div className={"form-control-label"}><small>Password</small></div>
                            <input type="password" className="input-primary input-block" ref={c => this.passwd = c} />
                        </div>
                    </div>
                }
                <div className="m-t-2">
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
