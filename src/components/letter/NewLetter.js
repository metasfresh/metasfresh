import React, { Component } from 'react';
import {connect} from 'react-redux';
import counterpart from 'counterpart';

import {
    addNotification
} from '../../actions/AppActions';

import {
    patchRequest
} from '../../actions/GenericActions';

import {
    createLetter,
    completeLetter,
    getTemplates
} from '../../actions/LetterActions';

import RawList from '../widget/List/RawList'

class NewLetter extends Component {
    constructor(props){
        super(props);

        this.state = {
            init: false,
            cached: {},
            templates: [],
            template: {}
        }
    }

    componentWillMount = () => {
        const { windowId, docId, handleCloseLetter } = this.props;

        createLetter(windowId, docId).then(res => {
            this.setState({
                ...res.data,
                init: true,
                cached: res.data
            })

            this.getTemplates();
        }).catch(() => {
            handleCloseLetter();
        });
    }

    getTemplates = () => {
        getTemplates().then(res => {
            this.setState({
                templates: res.data.values
            });
        });
    }

    handleChange = ({ target: { value: message } }) => {
        this.setState({
            message
        });
    }

    handleBlur = ({ target: { value: message } }) => {
        const { letterId } = this.state;

        if (this.state.cached.message === message) {
            return;
        }

        (patchRequest('letter', letterId, null, null, null, 'message', message)
            .then(res => {
                this.setState({
                    ...res.data,
                    cached: res.data
                });
            })
        );
    }

    complete = () => {
        const { letterId } = this.state;
        const { handleCloseLetter, dispatch } = this.props;

        completeLetter(letterId).then(() => {
            handleCloseLetter();

            dispatch(addNotification(
                'Letter', 'Letter has been sent.', 5000, 'success'
            ));
        });
    }

    handleTemplate = option => {
        const { letterId, template } = this.state;

        if (template === option) {
            return;
        }

        (patchRequest('letter', letterId, null, null, null, 'templateId', option)
            .then(res => {
               this.setState({
                   ...res.data,
                    template: option
                });
            })
        );
    }

    render() {
        const { handleCloseLetter } = this.props;
        const { init, message, templates, template, letterId } = this.state;

        if (!init) {
            return false;
        }

        return (
            <div className="screen-freeze">
                <div className="panel panel-modal panel-letter panel-modal-primary">
                    <div className="panel-letter-header-wrapper">
                        <div className="panel-letter-header panel-letter-header-top">
                            <span className="letter-headline">
                                {counterpart.translate('window.letter.new')}
                            </span>
                            <a
                                href={`${config.API_URL}/letter/${letterId}/printPreview`}
                                target="_blank"
                                className="input-icon input-icon-lg letter-icon-print"
                                onClick={handleCloseLetter}
                            >
                                <i className="meta-icon-print"/>
                            </a>
                            {templates.length > 0 && (
                                <div className="letter-templates">
                                    <RawList
                                        rank="primary"
                                        list={templates}
                                        onSelect={option => this.handleTemplate(option)}
                                        selected={template}
                                    />
                                </div>
                            )}
                            <div
                                className="input-icon input-icon-lg letter-icon-close"
                                onClick={handleCloseLetter}
                            >
                                <i className="meta-icon-close-1"/>
                            </div>
                        </div>
                    </div>
                    <div className="panel-letter-body">
                        <textarea
                            value={message ? message : ''}
                            onChange={this.handleChange}
                            onBlur={this.handleBlur}
                        />
                    </div>
                    <div className="panel-letter-footer">
                        <button
                            onClick={this.complete}
                            className="btn btn-meta-success btn-sm btn-submit"
                        >
                            {counterpart.translate('window.letter.create')}
                        </button>
                    </div>
                </div>
            </div>
        )
    }
}

NewLetter = connect()(NewLetter);

export default NewLetter;
