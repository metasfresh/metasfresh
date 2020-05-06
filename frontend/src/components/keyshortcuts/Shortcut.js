import PropTypes from 'prop-types';
import { PureComponent } from 'react';

/**
 * @file Class based component.
 * @module Shortcut
 * @extends PureComponent
 */
class Shortcut extends PureComponent {
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

/**
 * @typedef {object} Props Component props
 * @prop {string} name
 * @prop {func} handler
 * @todo Check props. Which proptype? Required or optional?
 */
Shortcut.propTypes = {
  name: PropTypes.string.isRequired,
  handler: PropTypes.func.isRequired,
};

Shortcut.contextTypes = {
  shortcuts: PropTypes.shape({
    subscribe: PropTypes.func.isRequired,
    unsubscribe: PropTypes.func.isRequired,
  }).isRequired,
};

export default Shortcut;
