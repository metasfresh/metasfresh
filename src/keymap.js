/* Define your mod button*/

let mod = 'alt';


export default {
    GLOBAL_CONTEXT: {
        OPEN_ACTIONS_MENU: mod + '+' + '1',
        OPEN_NAVIGATION_MENU: mod + '+' + '2',
        OPEN_INBOX_MENU: mod + '+' + '3',
        OPEN_SIDEBAR_MENU: mod + '+' + '4',

        //======== Document Context =================

        DELETE_DOCUMENT: mod + '+' + 'd',
        OPEN_ADVANCED_EDIT: mod + '+' + 'e',
    },
    DOCUMENT_LIST_CONTEXT: {
        NEW_DOCUMENT: mod + '+' + 'n',
        OPEN_SELECTED: mod + '+' + 'm', //open document in new tab
        REMOVE_SELECTED: {
            osx: 'command+backspace',
            windows: 'delete',
            linux: 'delete',
        },
    },
    DOCUMENT_CONTEXT: {
        
        OPEN_PRINT_RAPORT: mod + '+' + 'p',
        FOCUS_FAST_LINE_ENTRY: mod + '+' + 'q',
        REMOVE_SELECTED: {
            osx: 'command+backspace',
            windows: 'delete',
            linux: 'delete',
        }
    },
    TODO_ITEM: {
        MOVE_LEFT: 'alt+w',
        MOVE_RIGHT: 'right',
        MOVE_UP: ['up', 'w'],
        DELETE: {
            osx: ['command+backspace', 'k'],
            windows: 'delete',
            linux: 'delete',
        },
    },
    AAA: {
        MOVE_DOWN: 'down',
        MOVE_RIGHT: 'right',
        MOVE_UP: ['up', 'w'],
        DELETE: {
            osx: ['command+backspace', 'k'],
            windows: 'delete',
            linux: 'delete',
        },
    },
}