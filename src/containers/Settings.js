import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Container from '../components/Container';

class Settings extends Component {
    render() {
        return (
            <Container
                siteName = "Settings"
            >
                <div>hello</div>
            </Container>
        );
    }
}

Settings.propTypes = {
    dispatch: PropTypes.func.isRequired,
};

Settings = connect()(Settings);

export default Settings
