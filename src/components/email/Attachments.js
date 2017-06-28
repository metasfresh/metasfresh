import React, { Component } from 'react';

class Attachments extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {attachments} = this.props;
        return (
            <div className="email-attachments-wrapper">
                {attachments.map((item, index) => {
                    <div className="attachment">
                        <div className="attachnemt-text">
                            attachment.pdf <span>(230 kb)</span>
                        </div>
                        <div className="input-icon input-icon-lg">
                            <i className="meta-icon-close-1"/>
                        </div>
                    </div>
                })}
                <div>
                    <i className="meta-icon-attachments"/>
                     Add attachment
                </div>
            </div>
        );
    }
}

export default Attachments;
