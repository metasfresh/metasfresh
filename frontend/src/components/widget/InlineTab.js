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
    const { fieldsByName } = this.props;

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
            <span>{fieldsByName.Name.value}</span>&nbsp;&nbsp;
            <span>{fieldsByName.Address.value}</span>
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
  fieldsByName: PropTypes.object,
};
export default InlineTab;
