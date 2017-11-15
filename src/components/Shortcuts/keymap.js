const mod = 'ctrl';

export default {
    /* Global context */
    OPEN_ACTIONS_MENU: `${mod}+1`,
    OPEN_NAVIGATION_MENU: `${mod}+2`,
    OPEN_INBOX_MENU: `${mod}+3`,
    OPEN_AVATAR_MENU: `${mod}+4`,
    OPEN_SIDEBAR_MENU_0: `${mod}+5`,
    OPEN_SIDEBAR_MENU_1: `${mod}+6`,
    OPEN_SIDEBAR_MENU_2: `${mod}+7`,
    DOC_STATUS: `${mod}+i`,

    /* Document context */
    OPEN_ADVANCED_EDIT: `${mod}+e`,
    CLONE_DOCUMENT: `${mod}+w`,
    OPEN_PRINT_RAPORT: `${mod}+p`,
    OPEN_EMAIL: `${mod}+k`,
    OPEN_LETTER: `${mod}+r`,
    DELETE_DOCUMENT: `${mod}+d`,
    NEW_DOCUMENT: `${mod}+m`,
    TOGGLE_EDIT_MODE: `${mod}+o`,

    /* Document list context */
    OPEN_SELECTED: `${mod}+b`, // Open document in new tab
    REMOVE_SELECTED: `${mod}+y`,
    ADVANCED_EDIT: `${mod}+a`,

    /* POS context */
    SELECT_ALL_LEAFS: `${mod}+s`,
    EXPAND_INDENT: '+',
    COLLAPSE_INDENT: '-',

    /* Table context */
    TOGGLE_QUICK_INPUT: `${mod}+q`,
    TOGGLE_EXPAND: `${mod}+space`,

    /* Pagination context */
    NEXT_PAGE: 'pagedown',
    PREV_PAGE: 'pageup',
    FIRST_PAGE: 'home',
    LAST_PAGE: 'end',
    SELECT_ALL_ROWS: `${mod}+a`,

    /* Quick actions context */
    QUICK_ACTION_POS: `${mod}+u`,
    QUICK_ACTION_TOGGLE: `${mod}+l`,

    /* Document status context */
    COMPLETE_STATUS: `${mod}+u`,

    /* Modal context */
    APPLY: `${mod}+enter`,
    CANCEL: 'esc'
};
