import PropTypes from 'prop-types';
import { PureComponent } from 'react';

import keymap from '../../shortcuts/keymap';

export default class Shortcut extends PureComponent {
  static contextTypes = {
    shortcuts: PropTypes.shape({
      subscribe: PropTypes.func.isRequired,
      unsubscribe: PropTypes.func.isRequired,
    }).isRequired,
  };

  static propTypes = {
    name: PropTypes.oneOf(Object.keys(keymap)).isRequired,
    handler: PropTypes.func.isRequired,
  };

  constructor(props) {
    super(props);

    this.state = {
      name: props.name,
      handler: props.handler,
    };
  }

  componentWillMount() {
    const { subscribe } = this.context.shortcuts;
    const { name, handler } = this.state;

    subscribe(name, handler);
  }

  componentWillUnmount() {
    const { unsubscribe } = this.context.shortcuts;
    const { name, handler } = this.state;

    unsubscribe(name, handler);
  }

  render() {
    return null;
  }
}
