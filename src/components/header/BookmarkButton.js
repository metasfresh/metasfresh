import React, { Component } from 'react';

import {patchRequest} from '../../actions/GenericActions';

class BookmarkButton extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isBookmarkButtonShowed: false,
            isBookmark: props.isBookmark
        }
    }
    
    componentWillReceiveProps = (next) => {
        if(next.isBookmark !== this.props.isBookmark){
            this.setState({
                isBookmark: next.isBookmark
            })
        }
    }
    
    toggleBookmarkButton = () => {
        this.setState(prev => ({
            isBookmarkButtonShowed: !prev.isBookmarkButtonShowed
        }));
    }
    
    handleClick = () => {
        const {isBookmark} = this.state;
        
        patchRequest(
            "menu", null, null, null, null, "favourite", !isBookmark, "node",
            nodeId
        ).then(response => {
            console.log(response);
        });
    }

    render() {
        const {children, alwaysShowed} = this.props;
        const {isBookmarkButtonShowed, isBookmark} = this.state;
        return (
            <span
                onMouseEnter={this.toggleBookmarkButton}
                onMouseLeave={this.toggleBookmarkButton}
                className="btn-bookmark"
            >
                {children} 
                {alwaysShowed || (isBookmarkButtonShowed || isBookmark) &&
                    <i
                        onClick={this.handleClick}
                        className={
                            'btn-bookmark-icon meta-icon-star icon-spaced ' +
                            (isBookmark ? 'active ' : '')
                        }
                    />
                }
            </span>
        );
    }
}

export default BookmarkButton;
