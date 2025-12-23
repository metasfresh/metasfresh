// NOTE to dev: keep in sync with de.metas.ui.web.window.model.DocumentStandardAction

export const DocumentAction = Object.freeze({
  BREADCRUMB_CLICK: 'breadcrumbClick',
  TOGGLE_EDIT_MODE: 'toggleEditMode',
  ABOUT_DOCUMENT: 'aboutDocument',
  DOWNLOAD_SELECTED: 'downloadSelected',
  // Standard actions:
  // keep in sync with de.metas.ui.web.window.model.DocumentStandardAction
  NEW_DOCUMENT: 'new',
  OPEN_ADVANCED_EDIT: 'advancedEdit',
  CLONE_DOCUMENT: 'clone',
  OPEN_EMAIL: 'email',
  OPEN_LETTER: 'letter',
  OPEN_PRINT_RAPORT: 'print',
  DELETE_DOCUMENT: 'delete',
  OPEN_COMMENTS: 'comments',

  isStandardAction: (action) => {
    return (
      action === DocumentAction.NEW_DOCUMENT ||
      action === DocumentAction.OPEN_ADVANCED_EDIT ||
      action === DocumentAction.CLONE_DOCUMENT ||
      action === DocumentAction.OPEN_EMAIL ||
      action === DocumentAction.OPEN_LETTER ||
      action === DocumentAction.OPEN_PRINT_RAPORT ||
      action === DocumentAction.DELETE_DOCUMENT ||
      action === DocumentAction.OPEN_COMMENTS
    );
  },
});
