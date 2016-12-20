/* Define your mod button*/

let mod = 'ctrl';


export default {
    GLOBAL_CONTEXT: {
        OPEN_ACTIONS_MENU: mod + '+' + '1',
        OPEN_NAVIGATION_MENU: mod + '+' + '2',
        OPEN_INBOX_MENU: mod + '+' + '3',
        OPEN_SIDEBAR_MENU: mod + '+' + '4',

        //=========== Document Context =================

        OPEN_ADVANCED_EDIT: mod + '+' + 'e',
        OPEN_PRINT_RAPORT: mod + '+' + 'p',
        DELETE_DOCUMENT: mod + '+' + 'd',


    },
    DOCUMENT_LIST_CONTEXT: {
        OPEN_SELECTED: mod + '+' + 'm', //open document in new tab
        REMOVE_SELECTED: {
            osx: 'command+backspace',
            windows: 'delete',
            linux: 'delete',
        },
    },
    DOCUMENT_CONTEXT: {

        //======== Global =================

        NEW_DOCUMENT: mod + '+' + 'n',
        DELETE_DOCUMENT: mod + '+' + 'd',
        OPEN_ADVANCED_EDIT: mod + '+' + 'e',
        OPEN_PRINT_RAPORT: mod + '+' + 'p',
        FOCUS_FAST_LINE_ENTRY: mod + '+' + 'q',

        //===========================================

        REMOVE_SELECTED: {
            osx: 'command+backspace',
            windows: 'delete',
            linux: 'delete',
        }
    },
}