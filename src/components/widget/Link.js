import React, { Component } from 'react';

class Link extends Component {
    constructor(props) {
        super(props);
        
        this.state = {
            clickable: false
        }
    }
    
    handleClick = () => {
        this.setState(prev => ({
            clickable: !prev.clickable
        }))
    }

    render() {
        const {
            getClassnames, isEdited, widgetProperties, icon, fullScreen, 
            tabIndex, widgetData
        } = this.props;
        const {clickable} = this.state;
        return (
            <div className="input-inner-container">
                <div 
                    className={
                        getClassnames() +
                        (isEdited ? 'input-focused ' : '')
                    }
                >
                    {clickable ?
                        <a
                            className="widget-link"
                            tabIndex={fullScreen ? -1 : tabIndex}
                            href={widgetData[0].value}
                            target="_blank"
                        >
                            {widgetData[0].value}
                        </a>
                        : <input
                            {...widgetProperties}
                            type="text"
                        />
                    }
                    {icon &&
                        <i className="meta-icon-edit input-icon-right"/>
                    }
                </div>
                <div
                    onClick={this.handleClick}
                    className="btn btn-icon btn-meta-outline-secondary btn-inline pointer btn-distance-rev btn-sm"
                >
                    <i className={'meta-icon-' + (clickable ? 'edit' : 'show')} />
                </div>
            </div>
        );
    }
}

export default Link;
