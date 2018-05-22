import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { getElementBreadcrumb } from '../../actions/MenuActions';
import BookmarkButton from './BookmarkButton';

class MenuOverlayItem extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    const { query } = this.props;
    if (!query && document.getElementsByClassName('js-menu-overlay')[0]) {
      document.getElementsByClassName('js-menu-overlay')[0].focus();
    }
  }

  clickedItem = (e, elementId, nodeId, type) => {
    const { onClickOnFolder, onNewRedirect, openModal, caption } = this.props;

    if (e) {
      e.preventDefault();
      e.stopPropagation();
    }

    if (type === 'newRecord') {
      onNewRedirect(elementId);
    } else if (type === 'window' || type === 'board') {
      this.handleClick(elementId, type);
    } else if (type === 'group') {
      onClickOnFolder(e, nodeId);
    } else if (type === 'report' || type === 'process') {
      openModal(elementId + '', 'process', caption);
    }
  };

  handleClick = (elementId, entity) => {
    const { onRedirect } = this.props;
    onRedirect(elementId, null, entity);
    this.renderBreadcrumb(entity, elementId);
  };

  handleKeyDown = e => {
    const { back, onMenuOverlay } = this.props;
    const overlay = document.getElementsByClassName('js-menu-overlay')[0];

    switch (e.key) {
      case 'ArrowDown':
        e.preventDefault();
        this.handleArrowDown();
        break;
      case 'ArrowUp':
        e.preventDefault();
        this.handleArrowUp();
        break;
      case 'Tab':
        e.preventDefault();
        document.getElementsByClassName('js-menu-item')[0].focus();
        break;
      case 'Backspace':
        e.preventDefault();
        back(e);
        overlay.focus();
        break;
      case 'Enter':
        e.preventDefault();
        document.activeElement.childNodes[0].click();
        overlay && overlay.focus();
        break;
      case 'Escape':
        e.preventDefault();
        onMenuOverlay('', '');
    }
  };

  handleArrowUp() {
    let prevSiblings = document.activeElement.previousSibling;
    if (prevSiblings && prevSiblings.classList.contains('input-primary')) {
      document.getElementById('search-input-query').focus();
    } else if (
      prevSiblings &&
      prevSiblings.classList.contains('js-menu-item') &&
      document.activeElement.parentElement.classList.contains(
        'menu-overlay-query'
      )
    ) {
      document.activeElement.previousSibling.focus();
    }
  }

  handleArrowDown() {
    const nextElem = document.activeElement.nextSibling;
    const parentElem = document.activeElement.parentElement;
    if (nextElem) {
      if (nextElem.classList.contains('js-menu-item')) {
        nextElem.focus();
      } else {
        nextElem.getElementsByClassName('js-menu-item')[0] &&
          nextElem.getElementsByClassName('js-menu-item')[0].focus();
      }
    } else {
      if (parentElem.nextSibling) {
        const listChildren = parentElem.nextSibling.childNodes;
        if (listChildren.length == 1) {
          listChildren[0].focus();
        } else {
          if (listChildren[1].classList.contains('js-menu-item')) {
            listChildren[1].focus();
          } else {
            listChildren[1].getElementsByClassName('js-menu-item')[0].focus();
          }
        }
      } else if (parentElem.parentElement.nextSibling) {
        if (
          parentElem.parentElement.nextSibling.childNodes[1].classList.contains(
            'js-menu-item'
          )
        ) {
          parentElem.parentElement.nextSibling.childNodes[1].focus();
        } else {
          parentElem.parentElement.nextSibling.childNodes[1]
            .getElementsByClassName('js-menu-item')[0]
            .focus();
        }
      } else if (parentElem.parentElement.parentElement.nextSibling) {
        parentElem.parentElement.parentElement.nextSibling.getElementsByClassName(
          'js-menu-item'
        )[0] &&
          parentElem.parentElement.parentElement.nextSibling
            .getElementsByClassName('js-menu-item')[0]
            .focus();
      }
    }
  }

  renderBreadcrumb = (entity, elementId) => {
    const { dispatch } = this.props;

    dispatch(getElementBreadcrumb(entity, elementId));
  };

  render() {
    const {
      nodeId,
      type,
      elementId,
      caption,
      children,
      onClickOnFolder,
      query,
      printChildren,
      favorite,
      updateData,
      transparentBookmarks,
    } = this.props;

    return (
      <span
        tabIndex={0}
        onKeyDown={this.handleKeyDown}
        className={
          'menu-overlay-expanded-link js-menu-item ' +
          (!printChildren ? 'menu-overlay-expanded-link-spaced ' : '')
        }
      >
        {!query && (
          <BookmarkButton
            isBookmark={favorite}
            {...{ updateData, nodeId, transparentBookmarks }}
          >
            <span
              className={children ? 'menu-overlay-expand' : 'menu-overlay-link'}
              onClick={e => {
                children
                  ? onClickOnFolder(e, nodeId)
                  : this.clickedItem(e, elementId, nodeId, type);
              }}
            >
              {caption}
            </span>
          </BookmarkButton>
        )}

        {query && (
          <span
            className={
              children
                ? ''
                : type === 'group'
                  ? 'query-clickable-group'
                  : 'query-clickable-link'
            }
            onClick={
              children ? '' : e => this.clickedItem(e, elementId, nodeId, type)
            }
          >
            {children
              ? children.map((item, id) => (
                  <span key={id} className="query-results">
                    <span className="query-caption">
                      {id === 0 ? caption + ' / ' : '/'}
                    </span>
                    <span
                      title={item.caption}
                      className={
                        type === 'group'
                          ? 'query-clickable-group'
                          : 'query-clickable-link'
                      }
                      onClick={e =>
                        this.clickedItem(
                          e,
                          item.elementId,
                          item.nodeId,
                          item.type
                        )
                      }
                    >
                      {item.caption}
                    </span>
                  </span>
                ))
              : caption}
          </span>
        )}
      </span>
    );
  }
}

MenuOverlayItem.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

export default connect()(MenuOverlayItem);
