import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { getWindowBreadcrumb } from '../../actions/MenuActions';
import MenuOverlayItem from './MenuOverlayItem';

class MenuOverlayContainer extends Component {
  constructor(props) {
    super(props);
  }

  handleClick = () => {
    const { dispatch, handleRedirect, elementId } = this.props;
    handleRedirect(elementId);
    dispatch(getWindowBreadcrumb(elementId));
  };

  render() {
    const {
      children,
      elementId,
      caption,
      type,
      handleClickOnFolder,
      handleRedirect,
      handleNewRedirect,
      handlePath,
      printChildren,
      deep,
      back,
      handleMenuOverlay,
      openModal,
      showBookmarks,
      onUpdateData,
      transparentBookmarks,
      onKeyDown,
    } = this.props;
    return (
      <div
        tabIndex={0}
        onKeyDown={onKeyDown}
        className={
          'menu-overlay-node-container js-menu-container ' +
          (deep
            ? 'menu-overlay-node-spaced '
            : 'menu-overlay-expanded-link-spaced js-menu-main-container')
        }
      >
        {type === 'group' && (
          <span
            className={
              'menu-overlay-header ' +
              (!printChildren ? 'menu-overlay-header-spaced ' : '') +
              (!deep ? 'menu-overlay-header-main' : '')
            }
          >
            {caption}
          </span>
        )}

        {type !== 'group' && (
          <MenuOverlayItem
            printChildren={false}
            {...{
              showBookmarks,
              openModal,
              handlePath,
              back,
              type,
              caption,
              elementId,
              onUpdateData,
              transparentBookmarks,
              handleMenuOverlay,
              handleNewRedirect,
              handleRedirect,
              handleClickOnFolder,
            }}
          />
        )}

        {children &&
          children.length > 0 &&
          children.map((subitem, subindex) =>
            subitem.children && printChildren ? (
              <MenuOverlayContainer
                key={subindex}
                printChildren={true}
                deep={true}
                {...subitem}
                {...{
                  showBookmarks,
                  openModal,
                  onUpdateData,
                  transparentBookmarks,
                  handleNewRedirect,
                  handleRedirect,
                  handleClickOnFolder,
                }}
              />
            ) : (
              <MenuOverlayItem
                key={subindex}
                {...subitem}
                {...{
                  showBookmarks,
                  openModal,
                  back,
                  printChildren,
                  handlePath,
                  onUpdateData,
                  transparentBookmarks,
                  handleMenuOverlay,
                  handleNewRedirect,
                  handleRedirect,
                  handleClickOnFolder,
                }}
              />
            )
          )}
      </div>
    );
  }
}

MenuOverlayContainer.propTypes = {
  dispatch: PropTypes.func,
  handleRedirect: PropTypes.func,
  handleClick: PropTypes.func,
  handleClickOnFolder: PropTypes.func,
  handleMenuOverlay: PropTypes.func,
  handleNewRedirect: PropTypes.func,
  handlePath: PropTypes.func,
  elementId: PropTypes.any,
  caption: PropTypes.string,
  type: PropTypes.string,
  children: PropTypes.any,
  printChildren: PropTypes.any,
  deep: PropTypes.any,
  back: PropTypes.any,
  openModal: PropTypes.func,
  showBookmarks: PropTypes.bool,
  onUpdateData: PropTypes.bool,
  transparentBookmarks: PropTypes.bool,
  onKeyDown: PropTypes.func,
};

export default connect()(MenuOverlayContainer);
