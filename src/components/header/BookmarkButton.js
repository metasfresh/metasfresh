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
        const {nodeId, updateData} = this.props;
        const {isBookmark} = this.state;

        patchRequest(
            'menu', null, null, null, null, 'favorite', !isBookmark, 'node',
            nodeId
        ).then(response => {
            this.setState({isBookmark: !isBookmark})
            updateData && updateData(response.data);
        });
    }

    render() {
        const {children, alwaysShowed, transparentBookmarks} = this.props;
        const {isBookmarkButtonShowed, isBookmark} = this.state;

        if(transparentBookmarks){
            return children;
        }

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
