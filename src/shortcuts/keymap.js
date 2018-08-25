const mod = 'Alt';

const keymaps = {
  /* Global context */
  OPEN_ACTIONS_MENU: `${mod}+1`,
  OPEN_NAVIGATION_MENU: `${mod}+2`,
  OPEN_INBOX_MENU: `${mod}+3`,
  OPEN_AVATAR_MENU: `${mod}+4`,
  OPEN_SIDEBAR_MENU_0: `${mod}+5`,
  OPEN_SIDEBAR_MENU_1: `${mod}+6`,
  OPEN_SIDEBAR_MENU_2: `${mod}+7`,
  DOC_STATUS: `${mod}+I`,

  TEXT_START: 'Home',
  TEXT_END: 'End',

  /* Document context */
  OPEN_ADVANCED_EDIT: `${mod}+E`,
  CLONE_DOCUMENT: `${mod}+W`,
  OPEN_PRINT_RAPORT: `${mod}+P`,
  OPEN_EMAIL: `${mod}+K`,
  OPEN_LETTER: `${mod}+R`,
  DELETE_DOCUMENT: `${mod}+D`,
  NEW_DOCUMENT: `${mod}+N`,
  TOGGLE_EDIT_MODE: `${mod}+O`,

  /* Document list context */
  OPEN_SELECTED: `${mod}+B`, // Open document in new tab
  REMOVE_SELECTED: `${mod}+Y`,
  ADVANCED_EDIT: `${mod}+E`,

  /* POS context */
  SELECT_ALL_LEAFS: `${mod}+S`,
  EXPAND_INDENT: '+',
  COLLAPSE_INDENT: '-',

  /* Table context */
  TOGGLE_QUICK_INPUT: `${mod}+Q`,
  TOGGLE_EXPAND: `${mod}++`,

  /* Pagination context */
  NEXT_PAGE: 'PageDown',
  PREV_PAGE: 'PageUp',
  FIRST_PAGE: 'Home',
  LAST_PAGE: 'End',
  SELECT_ALL_ROWS: `${mod}+A`,

  /* Quick actions context */
  QUICK_ACTION_POS: `${mod}+U`,
  QUICK_ACTION_TOGGLE: `${mod}+L`,

  /* Document status context */
  COMPLETE_STATUS: `${mod}+U`,

  /* Modal context */
  APPLY: `${mod}+Enter`,
  CANCEL: 'Escape',
};

// Combinations that should be disabled when focus is in an input field,
// which means user is typing or we focused the field automatically
const disabledWithFocus = [keymaps.EXPAND_INDENT, keymaps.COLLAPSE_INDENT];

export { disabledWithFocus };
export default keymaps;
