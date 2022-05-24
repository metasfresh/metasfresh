import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import Indicator from '../app/Indicator';
import ModalContextShortcuts from '../keyshortcuts/ModalContextShortcuts';

/**
 * General purpose modal dialog.
 */
const ModalComponent = ({
  title,
  description,
  renderHeaderProperties,
  renderButtons,
  shortcutActions = {},
  indicator,
  onClickOutside,
  children,
}) => {
  if (!children) {
    return null;
  }

  const { scrolled, setScrolled } = useState();
  const handleScroll = (event) => {
    const scrollTop = event.srcElement.scrollTop;
    setScrolled(scrollTop > 0);
  };

  useEffect(() => {
    const modalContent = document.querySelector('.js-panel-modal-content');
    if (!modalContent) return;

    // Dirty solution, but use only if you need to
    // there is no way to affect body
    // because body is out of react app range
    // and css dont affect parents
    // but we have to change scope of scrollbar
    document.body.style.overflow = 'hidden';

    modalContent.addEventListener('scroll', handleScroll);
    return () => {
      modalContent.removeEventListener('scroll', handleScroll);
    };
  });

  const renderedHeaderProperties =
    renderHeaderProperties && renderHeaderProperties();

  const renderedButtons = renderButtons && renderButtons();

  return (
    <div className="screen-freeze raw-modal">
      <div
        className="click-overlay"
        onClick={() => onClickOutside && onClickOutside()}
      />
      <div className="modal-content-wrapper">
        <div className="panel panel-modal panel-modal-primary">
          <div
            className={classnames('panel-groups-header', 'panel-modal-header', {
              'header-shadow': scrolled,
            })}
          >
            <span className="panel-modal-header-title panel-modal-header-title-with-header-properties">
              {title ? title : 'Modal'}
              {description && (
                <span className="panel-modal-description">{description}</span>
              )}
            </span>
            {renderedHeaderProperties && (
              <div className="optional">{renderedHeaderProperties}</div>
            )}
            {renderedButtons && (
              <div className="items-row-2">{renderedButtons}</div>
            )}
          </div>
          {indicator && <Indicator indicator={indicator} />}
          <div
            className="panel-modal-content js-panel-modal-content"
            ref={(c) => {
              c && c.focus();
            }}
          >
            {children}
          </div>
          <ModalContextShortcuts {...shortcutActions} />
        </div>
      </div>
    </div>
  );
};

ModalComponent.propTypes = {
  title: PropTypes.string,
  description: PropTypes.string,
  indicator: PropTypes.string,
  renderHeaderProperties: PropTypes.func,
  renderButtons: PropTypes.func,
  shortcutActions: PropTypes.object,
  onClickOutside: PropTypes.func,
  children: PropTypes.node,
};

export default ModalComponent;
