import React, { Component, PropTypes } from 'react';
import {push} from 'react-router-redux';
import {connect} from 'react-redux';
import NotificationHandler from '../components/notifications/NotificationHandler';
import LoginForm from '../components/app/LoginForm';


class Login extends Component {
    constructor(props){
        super(props);
    }

    componentWillMount(){
        console.log('component will mount');
        console.log(this.props.logged);
        const {logged, dispatch} = this.props;
        if(logged){
            console.log('you are logged');
            dispatch(push("/"));
        }
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
