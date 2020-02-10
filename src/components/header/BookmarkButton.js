import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { patchRequest } from '../../api';

/**
 * @file Class based component.
 * @module BookmarkButton
 * @extends Component
 */
export default class BookmarkButton extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isBookmarkButtonShowed: false,
      isBookmark: props.isBookmark,
    };
  }

  /**
   * @method UNSAFE_componentWillReceiveProps
   * @summary ToDo: Describe the method.
   * @param {object} nextProps
   */
  UNSAFE_componentWillReceiveProps(nextProps) {
    if (nextProps.isBookmark !== this.props.isBookmark) {
      this.setState({ isBookmark: nextProps.isBookmark });
    }
  }

  /**
   * @method handleButtonEnter
   * @summary ToDo: Describe the method.
   */
  handleButtonEnter = () => {
    this.setState({ isBookmarkButtonShowed: true });
  };

  handleButtonLeave = () => {
    this.setState({ isBookmarkButtonShowed: false });
  };

  /**
   * @method handleClick
   * @summary ToDo: Describe the method.
   */
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

  /**
   * @method render
   * @summary ToDo: Describe the method.
   */
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

/**
 * @typedef {object} Props Component props
 * @prop {*} [children]
 * @prop {*} [alwaysShowed]
 * @prop {*} [transparentBookmarks]
 * @prop {*} [nodeId]
 * @prop {*} [updateData]
 * @prop {*} [isBookmark]
 */
BookmarkButton.propTypes = {
  children: PropTypes.any,
  alwaysShowed: PropTypes.any,
  transparentBookmarks: PropTypes.any,
  nodeId: PropTypes.any,
  updateData: PropTypes.any,
  isBookmark: PropTypes.any,
};
