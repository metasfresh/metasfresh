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
    const { children, alwaysShowed, transparentBookmarks, type } = this.props;
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
        {type === 'newRecord' && <i className="meta-icon-file m-icon-space" />}
        {type === 'window' && (
          <i className="meta-icon-vertragsverwaltung m-icon-space" />
        )}
        {type === 'group' && <i className="meta-icon-report m-icon-space" />}
        {type === 'process' && <i className="meta-icon-issue m-icon-space" />}
        {type === 'report' && (
          <i className="meta-icon-beschaffung m-icon-space" />
        )}
        {type === 'board' && <i className="meta-icon-calendar m-icon-space" />}
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
 * @prop {*} [onUpdateData]
 * @prop {*} [isBookmark]
 */
BookmarkButton.propTypes = {
  children: PropTypes.any,
  alwaysShowed: PropTypes.any,
  transparentBookmarks: PropTypes.any,
  nodeId: PropTypes.any,
  onUpdateData: PropTypes.any,
  isBookmark: PropTypes.any,
};
