import React, { Component } from 'react';

import { patchRequest } from '../../actions/GenericActions';

export default class BookmarkButton extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isBookmarkButtonShowed: false,
      isBookmark: props.isBookmark,
    };
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.isBookmark !== this.props.isBookmark) {
      this.setState({ isBookmark: nextProps.isBookmark });
    }
  }

  handleButtonEnter = () => {
    this.setState({ isBookmarkButtonShowed: true });
  };

  handleButtonLeave = () => {
    this.setState({ isBookmarkButtonShowed: false });
  };

  handleClick = () => {
    const { nodeId, updateData } = this.props;
    const { isBookmark } = this.state;

    patchRequest({
      entity: 'menu',
      property: 'favorite',
      value: !isBookmark,
      subentity: 'node',
      subentityId: nodeId,
    }).then(response => {
      this.setState({ isBookmark: !isBookmark });

      if (updateData) {
        updateData(response.data);
      }
    });
  };

  render() {
    const { children, alwaysShowed, transparentBookmarks } = this.props;
    const { isBookmarkButtonShowed, isBookmark } = this.state;

    if (transparentBookmarks) {
      return children;
    }

    return (
      <span
        onMouseEnter={this.handleButtonEnter}
        onMouseLeave={this.handleButtonLeave}
        className="btn-bookmark"
      >
        {children}
        {alwaysShowed ||
          ((isBookmarkButtonShowed || isBookmark) && (
            <i
              onClick={this.handleClick}
              className={
                'btn-bookmark-icon meta-icon-star icon-spaced ' +
                (isBookmark ? 'active ' : '')
              }
            />
          ))}
      </span>
    );
  }
}
