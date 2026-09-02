import PropTypes from 'prop-types';
import { useShortcut } from './ShortcutProvider';

const DocumentListContextShortcuts = ({
  windowId,
  selected: rowIds,
  commentsOpened,
  supportOpenRecord,
  //
  onOpenNewTab,
  onDelete,
  onAdvancedEdit,
  onGetAllLeaves,
  onIndent,
  onFastInlineEdit,
}) => {
  useShortcut({
    name: 'OPEN_SELECTED',
    handler: (event) => {
      event.preventDefault();

      if (onOpenNewTab && supportOpenRecord) {
        onOpenNewTab({ rowIds, windowId });
      }
    },
    dependencies: [rowIds, windowId, supportOpenRecord],
  });
  useShortcut({
    name: 'FAST_INLINE_EDIT',
    handler: (event) => {
      event.preventDefault();

      if (onFastInlineEdit) {
        onFastInlineEdit();
      }
    },
  });
  useShortcut({
    name: 'REMOVE_SELECTED',
    handler: (event) => {
      event.preventDefault();

      if (onDelete) {
        onDelete();
      }
    },
  });
  useShortcut({
    name: 'ADVANCED_EDIT',
    handler: (event) => {
      event.preventDefault();

      if (onAdvancedEdit) {
        onAdvancedEdit();

        return true;
      }

      return false;
    },
  });
  useShortcut({
    name: 'SELECT_ALL_LEAVES',
    handler: (event) => {
      event.preventDefault();

      if (onGetAllLeaves) {
        onGetAllLeaves();
      }
    },
  });
  useShortcut({
    name: 'EXPAND_INDENT',
    handler: (event) => {
      event.preventDefault();

      if (onIndent) {
        onIndent(true);
      }
    },
  });
  useShortcut({
    name: 'COLLAPSE_INDENT',
    handler: (event) => {
      if (commentsOpened) return false;
      event.preventDefault();

      if (onIndent) {
        onIndent(false);
      }
    },
    dependencies: [commentsOpened],
  });

  return null;
};

DocumentListContextShortcuts.propTypes = {
  onOpenNewTab: PropTypes.func,
  onDelete: PropTypes.func,
  onAdvancedEdit: PropTypes.func,
  onGetAllLeaves: PropTypes.any,
  onIndent: PropTypes.func,
  commentsOpened: PropTypes.bool,
  windowId: PropTypes.string,
  selected: PropTypes.array,
  onFastInlineEdit: PropTypes.func,
  supportOpenRecord: PropTypes.bool,
};

export default DocumentListContextShortcuts;
