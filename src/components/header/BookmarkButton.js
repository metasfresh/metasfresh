import React, { Component } from 'react';

class BookmarkButton extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isBookmarkButtonShowed: false
        }
    }
    
    toggleBookmarkButton = () => {
        this.setState(prev => ({
            isBookmarkButtonShowed: !prev.isBookmarkButtonShowed
        }));
    }

    render() {
        return (
            <span
                onMouseEnter={this.toggleBookmarkButton}
                onMouseLeave={this.toggleBookmarkButton}
            >
                {this.props.children} 
                {this.state.isBookmarkButtonShowed &&
                    <i className="meta-icon-star" />
                }
            </span>
        );
    }
}

export default BookmarkButton;
