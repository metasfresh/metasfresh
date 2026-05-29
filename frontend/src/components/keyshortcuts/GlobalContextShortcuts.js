import PropTypes from 'prop-types';
import { useShortcut } from './ShortcutProvider';
import { DocumentAction } from '../../constants/DocumentAction';

const noOp = () => {};

const GlobalContextShortcuts = ({
  standardActionsAllowed,
  closeOverlays,
  handleClone,
  handleDelete,
  handleDocStatusToggle,
  handleEditModeToggle,
  handleEmail,
  handleComments,
  handleInboxToggle,
  handleLetter,
  handleMenuOverlay,
  handlePrint,
  handleSidelistToggle,
  handleUDToggle,
  handleOpenAdvancedEdit,
  handleNewDocument,
}) => {
  useShortcut({
    name: 'OPEN_AVATAR_MENU',
    handler: (event) => {
      event.preventDefault();

      closeOverlays('isUDOpen', handleUDToggle);
    },
  });
  useShortcut({
    name: 'OPEN_ACTIONS_MENU',
    handler: (event) => {
      event.preventDefault();

      closeOverlays('isSubheaderShow');
    },
  });
  useShortcut({
    name: 'OPEN_NAVIGATION_MENU',
    handler: (event) => {
      event.preventDefault();

      handleMenuOverlay();
    },
  });
  useShortcut({
    name: 'OPEN_INBOX_MENU',
    handler: (event) => {
      event.preventDefault();

      closeOverlays('isInboxOpen', handleInboxToggle);
    },
  });
  useShortcut({
    name: 'OPEN_SIDEBAR_MENU_0',
    handler: (event) => {
      event.preventDefault();

      closeOverlays('isSideListShow', () => {
        handleSidelistToggle(0);
      });
    },
  });
  useShortcut({
    name: 'OPEN_SIDEBAR_MENU_1',
    handler: (event) => {
      event.preventDefault();

      closeOverlays('isSideListShow', () => {
        handleSidelistToggle(1);
      });
    },
  });
  useShortcut({
    name: 'OPEN_SIDEBAR_MENU_2',
    handler: (event) => {
      event.preventDefault();

      closeOverlays('isSideListShow', () => {
        handleSidelistToggle(2);
      });
    },
  });
  useShortcut({
    name: 'DELETE_DOCUMENT',
    handler: (event) => {
      event.preventDefault();

      handleDelete();
    },
    enabled: standardActionsAllowed.includes(DocumentAction.DELETE_DOCUMENT),
  });
  useShortcut({
    name: 'CLONE_DOCUMENT',
    handler: (event) => {
      event.preventDefault();

      handleClone();
    },
    enabled: standardActionsAllowed.includes(DocumentAction.CLONE_DOCUMENT),
  });
  useShortcut({
    name: 'OPEN_ADVANCED_EDIT',
    handler: (event) => {
      event.preventDefault();

      handleOpenAdvancedEdit();

      return true;
    },
  });
  useShortcut({
    name: 'OPEN_PRINT_RAPORT',
    handler: (event) => {
      event.preventDefault();

      handlePrint();
    },
  });
  useShortcut({
    name: 'OPEN_EMAIL',
    handler: (event) => {
      event.preventDefault();

      handleEmail();
    },
  });
  useShortcut({
    key: 'OPEN_LETTER',
    name: 'OPEN_LETTER',
    handler: (event) => {
      event.preventDefault();

      handleLetter();
    },
  });
  useShortcut({
    name: 'NEW_DOCUMENT',
    handler: (event) => {
      event.preventDefault();

      handleNewDocument();
    },
    enabled: standardActionsAllowed.includes(DocumentAction.NEW_DOCUMENT),
  });
  useShortcut({
    name: 'DOC_STATUS',
    handler: (event) => {
      event.preventDefault();

      closeOverlays('dropdown', handleDocStatusToggle);
    },
  });
  useShortcut({
    name: 'TOGGLE_EDIT_MODE',
    handler: (event) => {
      event.preventDefault();

      handleEditModeToggle();
    },
  });
  useShortcut({
    name: 'OPEN_COMMENTS',
    handler: (event) => {
      event.preventDefault();

      handleComments();

      return true;
    },
  });
  useShortcut({
    name: 'TEXT_START',
    handler: (event) => {
      event.preventDefault();

      const activeElement = document.activeElement;

      if (
        activeElement &&
        ((activeElement.nodeName === 'INPUT' &&
          activeElement.type === 'text') ||
          (activeElement.nodeName === 'TEXTAREA' &&
            activeElement.type === 'textarea'))
      ) {
        setCaretPosition(activeElement, 0);

        return true;
      }
    },
  });
  useShortcut({
    name: 'TEXT_END',
    handler: (event) => {
      event.preventDefault();

      const activeElement = document.activeElement;

      if (
        activeElement &&
        ((activeElement.nodeName === 'INPUT' &&
          activeElement.type === 'text') ||
          (activeElement.nodeName === 'TEXTAREA' &&
            activeElement.type === 'textarea'))
      ) {
        setCaretPosition(activeElement, activeElement.value.length);

        return true;
      }
    },
  });

  //
  //
  return null;
};

GlobalContextShortcuts.propTypes = {
  standardActionsAllowed: PropTypes.array,
  closeOverlays: PropTypes.func,
  handleClone: PropTypes.func,
  handleDelete: PropTypes.func,
  handleDocStatusToggle: PropTypes.func,
  handleEditModeToggle: PropTypes.func,
  handleEmail: PropTypes.func,
  handleComments: PropTypes.func,
  handleInboxToggle: PropTypes.func,
  handleLetter: PropTypes.func,
  handleMenuOverlay: PropTypes.func,
  handlePrint: PropTypes.func,
  handleSidelistToggle: PropTypes.func,
  handleUDToggle: PropTypes.func,
  handleOpenAdvancedEdit: PropTypes.func,
  handleNewDocument: PropTypes.func,
};

GlobalContextShortcuts.defaultProps = {
  closeOverlays: noOp,
  handleClone: noOp,
  handleDelete: noOp,
  handleDocStatusToggle: noOp,
  handleEditModeToggle: noOp,
  handleEmail: noOp,
  handleInboxToggle: noOp,
  handleLetter: noOp,
  handleMenuOverlay: noOp,
  handlePrint: noOp,
  handleSidelistToggle: noOp,
  handleUDToggle: noOp,
  handleOpenAdvancedEdit: noOp,
};

export default GlobalContextShortcuts;

//
//
//
//
//

const setCaretPosition = (ctrl, pos) => {
  ctrl.focus();
  ctrl.setSelectionRange(pos, pos);
  ctrl.scrollLeft = pos ? ctrl.scrollWidth : pos;
};
