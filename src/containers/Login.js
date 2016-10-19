import React, { Component, PropTypes } from 'react';
import {push} from 'react-router-redux';
import {connect} from 'react-redux';
import NotificationHandler from '../components/app/NotificationHandler';
import LoginForm from '../components/app/LoginForm';


class Login extends Component {
    constructor(props){
        super(props);
    }

    render() {
        return (
            <div>
                <NotificationHandler />
                <div className="login-container">
                    <LoginForm />
                </div>
            </div>
        );
    }
}

Login.propTypes = {
    dispatch: PropTypes.func.isRequired
}

function mapStateToProps(state) {
    const { appHandler } = state;

    const {
    } = appHandler || {
    }

    return {

    }
}

Login = connect(mapStateToProps)(Login);

export default Login;
