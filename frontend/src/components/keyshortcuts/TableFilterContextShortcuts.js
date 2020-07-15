import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { updateKeymap, updateHotkeys } from '../../actions/AppActions';
import { Shortcut } from '../keyshortcuts';

class TableFilterContextShortcuts extends PureComponent {
  constructor() {
    super();

    this.state = {
      shortcutsExtended: false,
    };
  }

  componentDidMount() {
    const { shortcutActions, dispatch } = this.props;
    const updatedKeymap = {};
    const updatedHotkeys = {};

    shortcutActions.forEach((action) => {
      updatedKeymap[action.name] = action.shortcut;
      updatedHotkeys[action.shortcut.toUpperCase()] = [];
    });

    dispatch(updateHotkeys(updatedHotkeys));
    dispatch(updateKeymap(updatedKeymap));

    this.setState({
      shortcutsExtended: true,
    });
  }

  render() {
    const { shortcutActions } = this.props;
    const { shortcutsExtended } = this.state;

    if (shortcutsExtended) {
      return shortcutActions.map((shortcut) => (
        <Shortcut
          key={shortcut.shortcut}
          name={shortcut.name}
          handler={shortcut.handler}
        />
      ));
    }

    return null;
  }
}

TableFilterContextShortcuts.propTypes = {
  shortcutActions: PropTypes.array,
  dispatch: PropTypes.func,
};

export default connect()(TableFilterContextShortcuts);
