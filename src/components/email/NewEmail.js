import React, { Component } from 'react';

class NewEmail extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {handleCloseEmail} = this.props
        return (
            <div className="screen-freeze">
                <div className="panel panel-modal panel-email panel-modal-primary">
                    <div className="panel-email-header-wrapper">
                        <div className="panel-email-header panel-email-header-top">
                            New message
                            <div 
                                className="input-icon input-icon-lg"
                                onClick={handleCloseEmail}
                            >
                                <i className="meta-icon-close-1"/>
                            </div>
                            
                        </div>
                        <div className="panel-email-header panel-email-bright">
                            <div className="panel-email-data-wrapper">
                                <span>To:</span> <input className="email-input" type="text"/>
                            </div>
                        </div>
                        <div className="panel-email-header panel-email-bright">
                            <div className="panel-email-data-wrapper">
                                <span>Topic:</span> <input className="email-input email-input-msg" type="text"/>
                            </div>
                        </div>
                    </div>
                    <div className="panel-email-body">
                        <textarea></textarea>
                    </div>
                    <div className="email-attachments-wrapper">
                        <div className="attachment">
                            <div>
                                attachment.pdf <span>(230 kb)</span>
                            </div>
                            <div className="input-icon input-icon-lg">
                                <i className="meta-icon-close-1"/>
                            </div>
                        </div>
                        <div className="attachment">
                            <div>
                                new_metasfresh.pdf <span>(230 kb)</span>
                            </div>
                            <div className="input-icon input-icon-lg">
                                <i className="meta-icon-close-1"/>
                            </div>
                        </div>
                        <div className="attachment">
                            <div>
                                xxx.pdf <span>(230 kb)</span>
                            </div>
                            <div className="input-icon input-icon-lg">
                                <i className="meta-icon-close-1"/>
                            </div>
                        </div>
                    </div>
                    <div className="panel-email-footer">
                        <div><i className="meta-icon-attachments"/> add attachment</div>
                        <button className="btn btn-meta-success btn-sm btn-submit">Send</button>
                    </div>
                    
                </div>
            </div>
        )
    }
}

export default NewEmail;
