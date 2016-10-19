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
            <div className="login-form panel panel-spaced panel-bordered panel-primary text-xs-center">
            <img src={logo} className="header-logo" />

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
                        <input type="text" className="input-primary" ref={c => this.login = c} />
                    </div>
                    <div>
                        <input type="password" className="input-primary" ref={c => this.passwd = c} />
                    </div>
                </div>
            }
                <div>
                    <button className="btn btn-sm btn-meta-primary" onClick={this.handleLogin}>{roleSelect? "Send" : "Login"}</button>
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
