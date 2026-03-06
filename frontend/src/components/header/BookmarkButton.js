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
   * @method handleClick
   * @summary ToDo: Describe the method.
   */
  handleClick = () => {
    const { nodeId, onUpdateData } = this.props;
    const { isBookmark } = this.state;

    patchRequest({
      entity: 'menu',
      property: 'favorite',
      value: !isBookmark,
      subentity: 'node',
      subentityId: nodeId,
    }).then((response) => {
      this.setState({ isBookmark: !isBookmark });

      if (onUpdateData) {
        onUpdateData(response.data);
      }
    });
  };

  /**
   * @method render
   * @summary ToDo: Describe the method.
   */
  render() {
    const { children, transparentBookmarks } = this.props;
    const { isBookmark } = this.state;

    if (transparentBookmarks) {
      return children;
    }

    return (
      <span className="btn-bookmark">
        {children}
        <i
          onClick={this.handleClick}
          className={
            'btn-bookmark-icon meta-icon-star icon-spaced ' +
            (isBookmark ? 'active ' : '')
          }
        />
      </span>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {*} [children]
 * @prop {*} [transparentBookmarks]
 * @prop {*} [nodeId]
 * @prop {*} [onUpdateData]
 * @prop {*} [isBookmark]
 */
BookmarkButton.propTypes = {
  children: PropTypes.any,
  transparentBookmarks: PropTypes.any,
  nodeId: PropTypes.any,
  onUpdateData: PropTypes.any,
  isBookmark: PropTypes.any,
};
