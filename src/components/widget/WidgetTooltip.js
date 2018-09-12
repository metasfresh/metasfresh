// import PropTypes from 'prop-types';
import React, { Component } from 'react';
// import { connect } from 'react-redux';
// import { List } from 'immutable';
// import { findKey } from 'lodash';
// import React, { PureComponent } from 'react';
// import { is, List } from 'immutable';
import onClickOutside from 'react-onclickoutside';
import TetherComponent from 'react-tether';
// import PropTypes from 'prop-types';
import classnames from 'classnames';
// import SelectionDropdown from '../SelectionDropdown';

// import {
//   dropdownRequest,
//   dropdownModalRequest,
// } from '../../../actions/GenericActions';
// import { getViewAttributeDropdown } from '../../../actions/ViewAttributesActions';
// import RawList from './RawList';

class WidgetTooltip extends Component {
  previousValue = '';

  constructor(props) {
    super(props);

    this.state = {
    };
  }

  componentDidMount() {
  }

  componentWillReceiveProps(nextProps) {
  }

  componentDidUpdate(prevProps) {
  }

  closeDropdownList = () => {
    // this.setState({
    //   listToggled: false,
    // });
  };

  activate = () => {
    // const { list, listToggled } = this.state;
    // const { lookupList } = this.props;

    // if (list && !listToggled && !(lookupList && list.size < 1)) {
    //   this.setState({
    //     listToggled: true,
    //   });
    // }
  };

  /*
   * Alternative method to open dropdown, in case of disabled opening
   * on focus.
   */
  handleClick = () => {
    // const { onOpenDropdown, isToggled, onCloseDropdown } = this.props;

    // if (!isToggled) {
    //   this.dropdown.focus();
    //   onOpenDropdown();
    // } else {
    //   onCloseDropdown();
    // }
  };

  handleClickOutside(e) {
    // const { isFocused, onCloseDropdown, onBlur, selected } = this.props;
    // const { target } = e;

    // if (isFocused) {
    //   // if target has the dropdown class it means that scrollbar
    //   // was clicked and we skip over it
    //   if (
    //     target.classList &&
    //     target.classList.contains('input-dropdown-list')
    //   ) {
    //     return;
    //   }

    //   this.setState(
    //     {
    //       selected: selected || null,
    //     },
    //     () => {
    //       onCloseDropdown();
    //       onBlur();
    //     }
    //   );
    // }
  }

  render() {
    const { isToggled } = this.props;

    console.log('WidgetTooltip props: ', this.props);

    return (
      <TetherComponent
        attachment="top left"
        targetAttachment="bottom left"
        constraints={[
          {
            to: 'scrollParent',
          },
          {
            to: 'window',
            pin: ['bottom'],
          },
        ]}
      >
        <div
          ref={ref => (this.dropdown = ref)}
          className={classnames('widget-tooltip', {
            opened: isToggled,
          })}
          tabIndex={-1}
          onClick={this.handleClick}
        >
        </div>
        {isToggled && (
          <div></div>
        )}
      </TetherComponent>
    );
  }
}


WidgetTooltip.defaultProps = {
};

WidgetTooltip.propTypes = {
};

const mapStateToProps = state => ({
});

export default onClickOutside(WidgetTooltip);

// export default connect(mapStateToProps, false)(
//   WidgetTooltip
// );
