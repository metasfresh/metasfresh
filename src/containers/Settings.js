import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Moment from 'moment';

import {
    getAvailableLang,
    setUserLang
} from '../actions/AppActions';

import Container from '../components/Container';
import RawList from '../components/widget/List/RawList';

class Settings extends Component {
    constructor(props){
        super(props);

        this.state={
            langs: [],
            value: '',
            hideHeader: false
        }
    }

    componentDidMount = () => {
        const {dispatch} = this.props;

        dispatch(getAvailableLang()).then(response => {
            const {values, defaultValue} = response.data;
            let chosenLang = '';

            values.map(item => {
                const key = Object.keys(item)[0];
                if(key === defaultValue){
                    chosenLang = item
                }
            })

            this.setState({
                langs: values,
                value: chosenLang
            })
        });
    }

    patch = (value) => {
        const {dispatch} = this.props;

        dispatch(setUserLang(value)).then(() => {
            this.setState({
                value: value
            }, () => {
                Moment.locale(Object.keys(value)[0]);
                this.refresh();
            });
        });
    }

    refresh = () => {
        this.setState({
            hideHeader: true
        }, () => {
            this.setState({
                hideHeader: false
            })
        })
    }

    render() {
        const {langs, value, hideHeader} = this.state;
        return (
            <Container
                hideHeader={hideHeader}
                siteName="Settings"
            >
                <div className="window-wrapper">
                    <div className="row">
                        <div className="col-sm-6">
                            <div
                                tabIndex={0}
                                className="panel panel-spaced panel-distance panel-bordered panel-primary"
                            >
                                <div className="elements-line">
                                    <div className="form-group row">
                                        <div
                                            className="form-control-label col-sm-3"
                                        >
                                            Language
                                        </div>
                                        <div className="col-sm-9">
                                            <RawList
                                                list={langs}
                                                onSelect={option =>
                                                    this.patch(option)
                                                }
                                                selected={value}
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Container>
        );
    }
}

Settings.propTypes = {
    dispatch: PropTypes.func.isRequired,
};

Settings = connect()(Settings);

export default Settings
