/* Define your mod button*/

let mod = 'ctrl';

// DO NOT USE:
// mod + l - windows browser focusing address bar

export default {
    GLOBAL_CONTEXT: {
        OPEN_ACTIONS_MENU: mod + '+' + '1',
        OPEN_NAVIGATION_MENU: mod + '+' + '2',
        OPEN_INBOX_MENU: mod + '+' + '3',
        OPEN_AVATAR_MENU: mod + '+' + '4',
        OPEN_SIDEBAR_MENU_0: mod + '+' + '5',
        OPEN_SIDEBAR_MENU_1: mod + '+' + '6',
        OPEN_SIDEBAR_MENU_2: mod + '+' + '7',
        DOC_STATUS: mod + '+' + 'i',

        //=========== Document Context =================

        OPEN_ADVANCED_EDIT: mod + '+' + 'e',
        OPEN_PRINT_RAPORT: mod + '+' + 'p',
        DELETE_DOCUMENT: mod + '+' + 'd',
        NEW_DOCUMENT: mod + '+' + 'm',
        TOGGLE_EDIT_MODE: mod + '+' + 'o'
    },
    DOCUMENT_LIST_CONTEXT: {
        OPEN_SELECTED: mod + '+' + 'b', //open document in new tab
        REMOVE_SELECTED: mod + '+' + 'y',
        ADVANCED_EDIT: mod + '+' + 'a',
        //===================  POS  ======================
        SELECT_ALL_LEAFS: mod + '+' + 's'

    },
    TABLE_CONTEXT: {
        TOGGLE_QUICK_INPUT: mod + '+' + 'q',
        TOGGLE_EXPAND: mod + '+' + 'space'
    },
    PAGINATION_CONTEXT: {
        NEXT_PAGE: 'pagedown',
        PREV_PAGE: 'pageup',
        FIRST_PAGE: 'home',
        LAST_PAGE: 'end'
    },
    QUICK_ACTIONS: {
        QUICK_ACTION_POS: mod + '+' + 'u'
    },
    DOCUMENT_STATUS_CONTEXT: {
        COMPLETE_STATUS: mod + '+' + 'u'
    }
}
