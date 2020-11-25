import React, { PureComponent } from 'react';
import classnames from 'classnames';
import PropTypes from 'prop-types';

class InlineTab extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isOpen: false,
    };
  }

  toggleOpen = () => {
    this.setState((prevState) => ({ isOpen: !prevState.isOpen }));
  };

  render() {
    const { isOpen } = this.state;
    const {
      inlineTab: { caption, elements },
    } = this.props;

    return (
      <div>
        <div
          className={classnames(
            { 'inline-tab': !isOpen },
            { 'inline-tab-active': isOpen },
            { 'form-control-label': true }
          )}
          onClick={this.toggleOpen}
        >
          <div className="pull-left">
            <span className="arrow-pointer" />
          </div>
          {/* Header  */}
          <div className="pull-left offset-left">
            {caption}
            {/* 
             TODO: select which exactly will be displayed and by what criteria - talk to BE team
                   currently these are randomly picked
             */}
            {elements &&
              elements.map((item, index) => {
                if (index < 4) {
                  return (
                    <span key={`${index}_${item}`}> {item.caption} -- </span>
                  );
                }
              })}
          </div>

          {/* Content */}
          {isOpen && (
            <div className="inline-tab-content">
              <div className="inline-tab-separator" />
              Actual content
            </div>
          )}
        </div>
      </div>
    );
  }
}

InlineTab.propTypes = {
  inlineTab: PropTypes.string.isRequired,
};
export default InlineTab;
