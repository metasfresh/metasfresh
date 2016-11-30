import React, { Component, PropTypes } from 'react';
import {push} from 'react-router-redux';
import {connect} from 'react-redux';
import NotificationHandler from '../components/Notifications/NotificationHandler';
import LoginForm from '../components/App/LoginForm';


class Login extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {redirect} = this.props;
        return (
            <div>
                <div className="login-container">
                    <LoginForm
                        redirect={redirect}
                     />
                </div>
            </div>
        );
    }
}

Login.propTypes = {
    dispatch: PropTypes.func.isRequired
}

Login = connect()(Login);

export default Login;
