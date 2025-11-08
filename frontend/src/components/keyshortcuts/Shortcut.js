import PropTypes from 'prop-types';
import { useShortcut } from './ShortcutProvider';

const Shortcut = ({ name, shortcut, handler }) => {
  useShortcut({ name, shortcut, handler });
  return null;
};

Shortcut.propTypes = {
  name: PropTypes.string.isRequired,
  shortcut: PropTypes.string,
  handler: PropTypes.func.isRequired,
};

export default Shortcut;
