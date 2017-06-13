import React, { Component } from 'react';

class Link extends Component {
    constructor(props) {
        super(props);
    }
    
    handleClick = (url) => {
        window.open(url, '_blank');
    }

    render() {
        const {
            getClassnames, isEdited, widgetProperties, icon, fullScreen, 
            tabIndex, widgetData
        } = this.props;

        return (
            <div className="input-inner-container">
                <div 
                    className={
                        getClassnames() +
                        (isEdited ? 'input-focused ' : '')
                    }
                >
                    <input
                        {...widgetProperties}
                        type="text"
                    />
                    {icon &&
                        <i className="meta-icon-edit input-icon-right"/>
                    }
                </div>
                <div
                    onClick={() => this.handleClick(widgetData[0].value)}
                    className="btn btn-icon btn-meta-outline-secondary btn-inline pointer btn-distance-rev btn-sm"
                >
                    <i className="meta-icon-show" />
                </div>
            </div>
        );
    }
}

export default Link;
