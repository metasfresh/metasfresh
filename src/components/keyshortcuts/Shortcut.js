import PropTypes from 'prop-types';
import { PureComponent } from 'react';

/**
 * @file Class based component.
 * @module Shortcut
 * @extends PureComponent
 */
class Shortcut extends PureComponent {
  static contextTypes = {
    shortcuts: PropTypes.shape({
      subscribe: PropTypes.func.isRequired,
      unsubscribe: PropTypes.func.isRequired,
    }).isRequired,
  };

  static propTypes = {
    name: PropTypes.string.isRequired,
    handler: PropTypes.func.isRequired,
  };

  /**
   * @method UNSAFE_componentWillMount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  UNSAFE_componentWillMount() {
    const { subscribe } = this.context.shortcuts;
    const { name, handler } = this.props;

    this.name = name;
    this.handler = handler;

    subscribe(name, handler);
  }

  /**
   * @method componentWillUnmount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  componentWillUnmount() {
    const { unsubscribe } = this.context.shortcuts;
    const { name, handler } = this;

    unsubscribe(name, handler);
  }

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    return null;
  }
}

export default Shortcut;
